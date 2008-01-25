/*
 * HSQLQuery.java
 *
 * Created on March 15, 2007, 9:51 AM by Clayton
 */

package CPS.Core.DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

/**
 * A class to handle the nitty-gritty queries of the database.
 * @author Clayton
 */
public class HSQLQuerier {
   
   Connection con;
   ResultSet rsCache;
   
   HSQLQuerier( Connection c ) {
      con = c;
   }
   
   public ResultSet getCachedResults() {
      return rsCache;
   }
   
   public TableModel getCachedResultsAsTable() {
      return tableResults( getCachedResults() );
   }
   
   public TableModel tableQuery( String table, String columns, String conditional ) {
      return tableResults( storeQuery( table, columns, conditional ));
   }
      
   public ResultSet storeQuery( String table, String columns ) {
      return storeQuery( table, columns, null, null );
   }
   public ResultSet storeQuery( String table, String columns, String conditional ) {
      return storeQuery( table, columns, conditional, null );
   }
   public ResultSet storeQuery( String table, String columns, String conditional, String sort ) {
      return storeQuery( table, columns, conditional, sort, null );
   }
   public ResultSet storeQuery( String table, String columns, 
                                String conditional, String sort, String filter ) {
      return submitQuery( con, table, columns, conditional, sort, filter, true );
   }
   
   public ResultSet submitQuery( String table, String columns ) {
      return submitQuery( table, columns, null, null, null );
   }
   public ResultSet submitQuery( String table, String columns, String conditional ) {
      return submitQuery( table, columns, conditional, null, null );
   }
   public ResultSet submitQuery( String table, String columns, String conditional, String sort ) {
      return submitQuery( table, columns, conditional, sort, null);
   }
   public ResultSet submitQuery( String table, String columns, 
                                 String conditional, String sort, String filter ) {
      return submitQuery( con, table, columns, conditional, sort, filter, false );
   }

   
   /* ****************
    * Static Methods *
    * ****************/
   static String putTogetherConditionalSortAndFilterString( String conditional,
                                                            String sort,
                                                            String filter ) {
      String s = " ";
      
      boolean cond = conditional != null && conditional.length() > 0;
      boolean filt = filter != null      && filter.length() > 0;
      
      if ( cond || filt ) {
         s += " WHERE ( ";
         if ( cond )
            s += conditional;
         
         if ( filt ) {
            if ( cond )
               s += " AND ( " + filter + " )";
            else
               s += filter;
            
         }
         
         s += " ) ";
      }
      
      /* TODO: if sort != crop_name, then make crop_name secondary sort
       * if sort == crop_name, then make var_name secondary sort
       * unless already includes secondary sort 
       */
      if ( sort != null && sort.length() > 0 )
         s += " ORDER BY " + sort;
      
      return s;
   }
   
   static synchronized ResultSet submitQuery( Connection con,
                                              String table,
                                              String columns, 
                                              String conditional,
                                              String sort,
                                              String filter,
                                              boolean prepared ) {
      ResultSet rs;
      String query = "SELECT " + columns + " FROM " + table;
      query += putTogetherConditionalSortAndFilterString( conditional, sort, filter );
      
      return submitRawQuery( con, query, prepared );
   }
   
   public static synchronized ResultSet submitRawQuery( Connection con, String query, boolean prepared ) {
      ResultSet rs;
      
      System.out.println("DEBUG Submitting query: " + query );
      
      try {
         if ( prepared ) {
            PreparedStatement ps = con.prepareStatement( query,
                                                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                         ResultSet.CONCUR_READ_ONLY );
            rs = ps.executeQuery();
         }
         else {            // These parameters are cribbed from ResultSetTableModelFactory
            Statement s = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY );
            rs = s.executeQuery( query );
         }  
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         rs = null;
      }
      return rs;
   }
   
   
   /**
    * A method to create and submit queries which conflate the results and return a
    * single row ResultSet whose column values are not null only if EVERY row queried 
    * contains identical values.  If any row in the selection contains a different value,
    * then the value for that column in the ResultSet is NULL.
    * 
    * @param table Name of the table to query.
    * @param columns ArrayList<String> of all columns to query.
    * @param ids ArrayList<Integer> of all row indices (actually, records with column id == id)
    * @return A ResultSet with only one row whose column values are either NULL (for heterogeneous
    *         column values data) or the value which is homogeneous across all queried rows.
    */
   public synchronized ResultSet submitCommonInfoQuery( String table,
                                                         ArrayList<String> columns,
                                                         ArrayList<Integer> ids ) {
      String idString = HSQLDB.intListToIDString(ids);
      
      /* start the query string */
      String query = "";
      query += "SELECT ";
      
      for ( String col : columns ) {
         query += "MIN( SELECT ";
         query += "( CASE WHEN count(*)=1 ";
         query += "THEN MIN( " + col + " ) ELSE null END ) ";
         query += "FROM( SELECT DISTINCT " + col + " FROM " + table + " ";
         query += "WHERE id IN ( " + idString + " ) ) ) AS " + col + ", "; 
      }
      query = query.substring( 0, query. lastIndexOf( ", " ));
      
      query += " FROM " + table;
      
      System.out.println("DEBUG Submitting query: " + query );
      
      return submitRawQuery( con, query, false );
   }
   
   public synchronized ResultSet submitCalculatedCropPlanQuery( String planName,
                                                                ArrayList<String[]> colMap,
                                                                String displayColumns,
                                                                String sortColumn,
                                                                String filterExp ) {
      
      int PLANT_COL = 0;
      int CROP_COL = 1;
      int CALC = 2;
      
      /* This string represents the query which will fill in the "static" fields
       * in each crop (w/ variety) from the corresponding fields in the crop (w/o variety) */
      String cropFillInQuery = " SELECT id, ";
      for ( String[] s : colMap ) {
         if ( s[CROP_COL] == null )
            continue;
         cropFillInQuery += "COALESCE( c1." + s[CROP_COL] + ", ";
         cropFillInQuery +=           "c2." + s[CROP_COL] + " ) AS " + s[CROP_COL];
         cropFillInQuery += ", ";
      }
      cropFillInQuery = cropFillInQuery.substring( 0, cropFillInQuery.lastIndexOf( ", " ));
      cropFillInQuery += " FROM crops_varieties AS c1, crops_varieties AS c2 ";
      cropFillInQuery += " WHERE c1.crop_name = c2.crop_name AND c2.var_name IS NULL";
      
      /* This string represents the query which will fill in the "static" fields
       * in each planting from the corresponding fields in the crop */
      String plantingFillInQuery = "SELECT ";
      for ( String[] s : colMap ) {
         if ( s[CROP_COL] == null )
            plantingFillInQuery += "p." + s[PLANT_COL];
         else {
            plantingFillInQuery += "COALESCE( p." + s[PLANT_COL] + ", ";
            plantingFillInQuery +=           "c." + s[CROP_COL] + " ) AS " + s[PLANT_COL];
         }
         plantingFillInQuery += ", ";
      }
      plantingFillInQuery = plantingFillInQuery.substring( 0, plantingFillInQuery.lastIndexOf( ", " ));
      plantingFillInQuery += " FROM " + planName + " AS p, ( " + cropFillInQuery + " ) AS c ";
//      plantingFillInQuery += " FROM " + planName + " AS p, crops_varieties AS c ";
//      fillInQuery += "WHERE p.crop_name = c.crop_name AND c.var_name IS NULL ";
      plantingFillInQuery += "WHERE p.crop_id = c.id ";
      
/**      fillInQuery += "p.id, p.crop_name, p.var_name, ";
      fillInQuery += "p.date_plant, p.date_tp, p.date_harvest, ";
      fillInQuery += "COALESCE( p.maturity, c.maturity ) AS maturity, ";
      fillInQuery += "COALESCE( p.rows_p_bed, c.rows_p_bed ) AS rows_p_bed, ";
      fillInQuery += "COALESCE( p.time_to_tp, c.time_to_tp ) AS time_to_tp, ";
      fillInQuery += "location, completed ";
      fillInQuery += "FROM " + planName + " AS p, crops_varieties AS c ";
      fillInQuery += "WHERE p.crop_name = c.crop_name AND c.var_name IS NULL";
 */
      
      String passQuery = "SELECT ";
      for ( String[] s : colMap ) {
         if ( s[CALC] == null )
            passQuery += s[PLANT_COL];
         else {
            passQuery += "COALESCE( " + s[PLANT_COL] + ", ";
            passQuery +=                s[CALC] + " ) AS " + s[PLANT_COL];
         }
         passQuery += ", ";
      }
      passQuery = passQuery.substring( 0, passQuery.lastIndexOf( ", " ));
     
/**      passQuery += "id, crop_name, var_name, maturity, location, completed, ";
      passQuery += "COALESCE( date_plant, ";
      passQuery += "\"CPS.Core.DB.HSQLCalc.plantFromHarvest\"( date_harvest, maturity ) ";
      passQuery += ", \"CPS.Core.DB.HSQLCalc.plantFromTP\"( date_tp, time_to_tp )";
      passQuery += " ) AS date_plant, ";
//      passQuery += " date_tp, ";
      passQuery += "COALESCE( date_tp, ";
      passQuery += "\"CPS.Core.DB.HSQLCalc.TPFromPlant\"( date_plant, time_to_tp )";
      passQuery += " ) AS date_tp, ";
      passQuery += "COALESCE( date_harvest, ";
      passQuery += "\"CPS.Core.DB.HSQLCalc.harvestFromPlant\"( date_plant, maturity )";
      passQuery += " ) AS date_harvest, ";
      passQuery += " rows_p_bed, time_to_tp ";
 */  
 
      String pass1 = passQuery + " FROM ( " + plantingFillInQuery + " ) ";
      String pass2 = passQuery + " FROM ( " + pass1 + " ) ";
      
//      return submitQuery( "( " + pass2 + " )", displayColumns, null, sortColumn, filterExp ); 
      return storeQuery( "( " + pass2 + " )", displayColumns, null, sortColumn, filterExp ); 
      
   }
   
   /**
    * Creates a new HSQLTableModel for the given ResultSet.
    * 
    * @param rs ResultSet to embed in the new HSQLTableModel
    * @return A new HSQLTableModel wrapped around the given ResultSet
    */
   public static TableModel tableResults( ResultSet rs ) {
      try {
         return new HSQLTableModel( rs );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new DefaultTableModel();
      }
   }
   
   public static TableModel tableResults( ResultSet rs, String tableName ) {
      try {
         return new HSQLTableModel( rs, tableName );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new DefaultTableModel();
      }
   }
   
}
