/*
 * HSQLQuery.java
 *
 * Created on March 15, 2007, 9:51 AM by Clayton
 */

package CPS.Core.DB;

import java.sql.Connection;
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
   
   
   static synchronized ResultSet submitQuery( Connection con,
                                              String table,
                                              String columns, 
                                              String conditional,
                                              String sort,
                                              String filter,
                                              boolean prepared ) {
      ResultSet rs;
      String query = "SELECT " + columns + " FROM " + table;
      
      boolean cond = conditional != null && conditional.length() > 0;
      boolean filt = filter != null      && filter.length() > 0;
      
      if ( cond || filt ) {
         query += " WHERE ( ";
         if ( cond )
            query += conditional;
         
         if ( filt ) {
            if ( cond )
               query += " AND ( " + filter + " )";
            else
               query += filter;
            
         }
         
         query += " ) ";
      }
      
      /* TODO: if sort != crop_name, then make crop_name secondary sort
       * if sort == crop_name, then make var_name secondary sort
       * unless already includes secondary sort 
       */
      if ( sort != null && sort.length() > 0 )
         query += " ORDER BY " + sort;
      
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
       ResultSet rs = null;
      
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
      
      try {
         Statement s = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                            ResultSet.CONCUR_READ_ONLY );
         rs = s.executeQuery( query );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         rs = null;
      }
      
      return rs;
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
   
   
}
