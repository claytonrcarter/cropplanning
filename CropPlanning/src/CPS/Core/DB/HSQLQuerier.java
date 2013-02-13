/* HSQLQuery.java - Created: March 15, 2007
 * Copyright (C) 2007, 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
   
//   public TableModel getCachedResultsAsTable() {
//      return tableResults( getCachedResults() );
//   }
//   
//   public TableModel tableQuery( String table, String columns, String conditional ) {
//      return tableResults( storeQuery( table, columns, conditional ));
//   }
      
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
      
      /* if sort doesn't include crop_name, then make crop_name secondary sort
       * if sort does include crop_name, then make var_name secondary sort
       */
      if ( sort != null && sort.length() > 0 ) {
          if ( sort.indexOf( "crop_name" ) == -1 )
              sort += ", crop_name";
          else if ( sort.indexOf( "var_name" ) == -1 )
              sort += ", var_name";
          
          s += " ORDER BY " + sort;
      }
      
      return s;
   }
   
   static synchronized ResultSet submitQuery( Connection con,
                                              String table,
                                              String columns, 
                                              String conditional,
                                              String sort,
                                              String filter,
                                              boolean prepared ) {
       
       table = HSQLDB.escapeTableName( table );
       
      if ( sort != null && sort.length() > 0 ) {
          String[] tok = sort.split( " " );
          columns += ", CASE WHEN " + tok[0] + " IS NULL ";
          columns +=        "THEN -1 ELSE 1 END ";
          columns += " AS nulls_last ";
          
          sort = "nulls_last DESC, " + sort;
      }
          
      String query = "SELECT " + columns + " FROM " + table;
      query += putTogetherConditionalSortAndFilterString( conditional, sort, filter );
      
      return submitRawQuery( con, query, prepared );
   }
   
   public static synchronized ResultSet submitRawQuery( Connection con, String query, boolean prepared ) {
      ResultSet rs;
      
      if ( query.length() < 500 )
           HSQLDB.debug( "HSQLQuerier", "Submitting query: " + query );
      else
           HSQLDB.debug( "HSQLQuerier", "Submitting query: " + query.substring( 0 , 500 ) + " ... (truncated)" );
      
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
   
   public static synchronized long getLastUpdateVersion( Connection con ) {
       
       try {
           Statement s = con.createStatement();
           String query = "SELECT prev_ver FROM " + 
                          HSQLDB.escapeTableName( "CPS_METADATA" );
           ResultSet rs = s.executeQuery( query );
           
           if ( rs.next() ) {
               long prev_ver = rs.getLong( "prev_ver" );
               return prev_ver;
           }
           else {
               System.out.println("ERROR: couldn't find previous version (no results)");
               return -1;
           }
           
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return -1;
      }
   }
   
   
   public static synchronized List<String> getDistinctValuesForColumn( Connection con, List<String> tables, String column ) {
       
       ArrayList<String> l = new ArrayList<String>();
       Set set = new HashSet();
      
       for ( String table : tables ) {
           set.addAll( HSQLQuerier.getDistinctValuesForColumn( con, table, column ) );
       }
      
       l.clear();
       l.addAll( set );
      
       return l;
       
   }
   
   public static synchronized List<String> getDistinctValuesForColumn( Connection con, String table, String column ) {
       if ( table == null || column == null ) 
           return new ArrayList<String>();
       
      try {
         String query = "SELECT DISTINCT " + column + " FROM " + HSQLDB.escapeTableName( table );
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery( query );
               
         ArrayList<String> l = new ArrayList<String>();
         while ( rs.next() ) {
            String s = (String) rs.getObject(1);
            if ( s == null || s.equals( "" ) )
               continue;
            l.add( s );
         }
         Collections.sort( l, String.CASE_INSENSITIVE_ORDER );
         return l;
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new ArrayList<String>();
      }
      
   }
   
   
   /**
    * A method to create queries which conflate the results such that column values are not null only if EVERY row queried
    * contains identical values.  If any row in the selection contains a different value,
    * then the value for that column in the results is NULL.
    * 
    * @param table Name of the table to query.
    * @param columns ArrayList<String> of all columns to query.
    * @param ids ArrayList<Integer> of all row indices (actually, records with column id == id)
    * @return an SQL statement, ready to be handed off to the db
    */
   public String buildCommonInfoQuery( String table,
                                        String idColumn,
                                        String[] columns,
                                        List<Integer> ids ) {

      String idString = HSQLDB.intListToIDString(ids);
      
      /* start the query string */
      String query = "";
      query += "SELECT ";
      
      for ( String col : columns ) {
         query += "MIN( SELECT ";
         query += "( CASE WHEN count(*)=1 ";
         query += "THEN MIN( " + col + " ) ELSE null END ) ";
         query += "FROM( SELECT DISTINCT " + col + " FROM " + HSQLDB.escapeTableName( table ) + " ";
         query += "WHERE " + idColumn + " IN ( " + idString + " ) ) ) AS " + col + ", ";
      }
      query = query.substring( 0, query. lastIndexOf( ", " ));
      
      query += " FROM " + HSQLDB.escapeTableName( table );

//      HSQLDB.debug( "HSQLQuerier", "COMMON INFO query:\n" + query );
      
      return query;
      
   }

   
   public synchronized ResultSet submitSummedCropPlanQuery( String planName,
                                                              ArrayList<String[]> plantColMap,
                                                              ArrayList<String[]> cropColMap,
                                                              ArrayList<String> displayColumns,
                                                              String filterExp ) {
       String sumCols = "";
       for ( String s : displayColumns )
           sumCols += " SUM( " + s + " ) AS " + s + ", ";
       sumCols = sumCols.substring( 0, sumCols.lastIndexOf( ", " ) );
       
       return submitCalculatedCropPlanQuery( planName, plantColMap, cropColMap, sumCols, null, filterExp);
       
   }
   
   public synchronized ResultSet submitCalculatedCropAndVarQuery( ArrayList<String[]> colMap,
                                                                  String displayColumns,
                                                                  String sortColumn,
                                                                  String filterExp ) {
      
       String filledInQuery = createCoalescedCropAndVarQueryString( colMap );
      
      // using pass2 in parens as the tablename; this is sort of a virtual table
      return storeQuery( "( " + filledInQuery + " )", displayColumns, null, sortColumn, filterExp ); 
      
   }
   
   public synchronized ResultSet submitCalculatedCropPlanQuery( String planName,
                                                                ArrayList<String[]> plantColMap,
                                                                ArrayList<String[]> cropColMap,
                                                                String displayColumns,
                                                                String sortColumn,
                                                                String filterExp ) {
      
       String filledInQuery = createCoalescedCropPlanQueryString( planName, plantColMap, cropColMap );
      
      // using pass2 in parens as the tablename; this is sort of a virtual table
      return storeQuery( "( " + filledInQuery + " )", displayColumns, null, sortColumn, filterExp ); 
      
   }
   
    private String createCoalescedCropAndVarQueryString( ArrayList<String[]> colMap ) {
       
       int CROP_COL = 0;
       int MAP_COL = 1;
       
       String cropFillInQuery, varietySelect, cropSelect;
       /* This string represents the query which will fill in the "static" fields
        * in each crop (w/ variety) from the corresponding fields in the crop (w/o variety) */
       varietySelect = cropSelect = " SELECT ";
       for ( String[] s : colMap ) {
           cropSelect += s[CROP_COL] + ", ";
           if ( s[MAP_COL] == null ) {
               varietySelect += "v." + s[CROP_COL] + ", ";
               continue;
           }
           varietySelect += "COALESCE( v." + s[CROP_COL] + ", ";
           varietySelect += "c." + s[CROP_COL] + " ) AS " + s[CROP_COL];
           varietySelect += ", ";
       }
       varietySelect = varietySelect.substring( 0, varietySelect.lastIndexOf( ", " ) );
       cropSelect = cropSelect.substring( 0, cropSelect.lastIndexOf( ", " ) );
       
//       cropFillInQuery += " FROM crops_varieties AS v, crops_varieties AS c ";
//       cropFillInQuery += " FROM        crops_varieties                                        AS v ";
       
       cropFillInQuery  = varietySelect;
       cropFillInQuery += " FROM      ( SELECT * FROM crops_varieties WHERE var_name IS NOT NULL ) AS v ";
       cropFillInQuery += " LEFT JOIN ( SELECT * FROM crops_varieties WHERE var_name IS     NULL ) AS c ";
       cropFillInQuery += " ON v.crop_name = c.crop_name ";
       cropFillInQuery += " UNION " + cropSelect;
       cropFillInQuery += " FROM       ( SELECT * FROM crops_varieties WHERE var_name IS NOT NULL ) AS v ";
       cropFillInQuery += " RIGHT JOIN ( SELECT * FROM crops_varieties WHERE var_name IS     NULL ) AS c ";
       cropFillInQuery += " ON v.crop_name = c.crop_name ";
       
       return cropFillInQuery;
        
    }
   
   private String createCoalescedCropPlanQueryString( String planName,
                                                      ArrayList<String[]> plantColMap,
                                                      ArrayList<String[]> cropColMap ) {
       
       planName = HSQLDB.escapeTableName( planName );
       
       int PLANT_COL = 0;
       int CROP_COL = 1;
       int CALC = 2;
       int PSEUDO = 3;
      
       String cropFillInQuery = createCoalescedCropAndVarQueryString( cropColMap );
       
       /* This string represents the query which will fill in the "static" fields
        * in each planting from the corresponding fields in the crop */
       String plantingFillInQuery = "SELECT ";
       for ( String[] s : plantColMap ) {
           Boolean pseudo = new Boolean( s[PSEUDO] );

//           // leave date columns for later calculation (this is a HACK!)
//          if ( )
//             plantingFillInQuery += "p." + s[PLANT_COL];
           // if this is a "pseudo" column
          if ( s[PSEUDO].equalsIgnoreCase( "true" ) ) {
             HSQLDB.debug( "HSQLQuerier", "using query for pseduocolumn: " + s[CALC] + " AS " + s[PLANT_COL] );
             plantingFillInQuery += s[CALC] + " AS " + s[PLANT_COL];
          }
           // if there is no correlated crop column
          else if ( s[CROP_COL] == null )
             plantingFillInQuery += "p." + s[PLANT_COL];
           // otherwise, there is a column to inherit from
          else {
             // start the inherit clause (via COALESCE)
             plantingFillInQuery += "COALESCE( p." + s[PLANT_COL] + ", ";
             // if the "crop column" contains commas, then we split it on those commas
             // and assume that:
             // index 0 is a boolen column (from planting)
             // index 1 is the column to inherit if true
             // index 2 is the column to inherit if false
             if ( s[CROP_COL].matches( ".*,.*" ) ) {
                String[] cols = s[CROP_COL].split( "," );
                String temp = " CASE WHEN p." + cols[0].trim();
                
                // if the first column is "NULL", then omit the "c."
                if ( cols[1].trim().equalsIgnoreCase("null") )
                   temp += "      THEN " + cols[1].trim();
                else 
                   temp += "      THEN c." + cols[1].trim();
                
                temp += "      ELSE c." + cols[2].trim();
                temp += " END ";
                plantingFillInQuery += temp;
                HSQLDB.debug( "HSQLQuerier", "using query for conditional inherit: " + temp + " for column " + s[PLANT_COL] );
             }
             else 
                // otherwise (no commas) just use the entry as is
                plantingFillInQuery += "c." + s[CROP_COL] + " ";
             
             // finish the statement
             plantingFillInQuery += ") AS " + s[PLANT_COL];
             
          }
          plantingFillInQuery += ", ";
       }
       plantingFillInQuery = plantingFillInQuery.substring( 0, plantingFillInQuery.lastIndexOf( ", " ) );
//       plantingFillInQuery += " FROM " + planName + " AS p, ( " + cropFillInQuery + " ) AS c ";
       plantingFillInQuery += " FROM " + planName + " AS p "; 
       plantingFillInQuery += " LEFT JOIN ( " + cropFillInQuery + " ) AS c ";
//       plantingFillInQuery += " WHERE p.crop_id = c.id ";
       plantingFillInQuery += " ON p.crop_id = c.id ";

       // Now we handle the calculations and fill ins within the planting
       // This handled rather bluntly by just recursing on the query several times
       String iterateQuery = "SELECT ";
       String staticDates = "";
       String calcDates = "";
       for ( String[] s : plantColMap ) {

          String temp = "";

          // if it's a pseudo column, force calculation
          if ( s[PSEUDO].equalsIgnoreCase( "true" ) ) {
             temp += s[CALC] + " AS " + s[PLANT_COL];
          }
          // if there's no calculation, just use the column as is
          else if ( s[CALC] == null )
             temp += s[PLANT_COL];
          // otherwise, coalesca the column w/ the calculation
          else {
             temp += "COALESCE( " + s[PLANT_COL] + ", ";
             temp += s[CALC] + " ) AS " + s[PLANT_COL];
          }
          temp += ", ";

          if ( s[PLANT_COL].startsWith( "date" ) ) {
             calcDates += temp;
             staticDates += s[PLANT_COL] + ", ";
          }
          else {
             iterateQuery += temp;
          }
          
       }
//       passQuery = passQuery.substring( 0, passQuery.lastIndexOf( ", " ) );
       calcDates = calcDates.substring( 0, calcDates.lastIndexOf( ", " ) );
       staticDates = staticDates.substring( 0, staticDates.lastIndexOf( ", " ) );

       String pass1 = iterateQuery + staticDates + " FROM ( " + plantingFillInQuery + " ) ";
       String pass2 = iterateQuery + calcDates   + " FROM ( " + pass1 + " ) ";
       String pass3 = iterateQuery + calcDates   + " FROM ( " + pass2 + " ) ";
       String pass4 = iterateQuery + calcDates   + " FROM ( " + pass3 + " ) ";
//
//       String pass3 = passQuery + " FROM ( " + pass2 + " ) ";

       return pass4;
       
   }
   
   public static ResultSet getSingleValue( Connection con,
                                           String table, String column,
                                           String limitColumn, String limitValue ) {
      
      ResultSet rs = null;
      
      try {
         String query = "SELECT " + column + 
                        " FROM " + HSQLDB.escapeTableName( table ) +
                        " WHERE " + limitColumn + " = " + HSQLDB.escapeValue( limitValue );
         Statement st = con.createStatement();

         HSQLDB.debug( "HSQLQuerier", "Executing query: " + query );
         rs = st.executeQuery( query );
      
      }
      catch ( SQLException e ) {
         e.printStackTrace();
      }
      
      return rs;
   }
   
   public static int getCropPlanYear( Connection con, String planName ) {
      try {
         ResultSet rs = getSingleValue( con, "CROP_PLANS", "year", "plan_name", planName );
         rs.next();
         return rs.getInt( "year" );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return -1;
      }
   }
   
   public static String getCropPlanDescription( Connection con, String planName ) {
      try {
         ResultSet rs = getSingleValue( con, "CROP_PLANS", "description", "plan_name", planName );
         rs.next();
         return rs.getString( "description" );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return "Uh Oh: Error";
      }
   }
   
   
   /**
    * Creates a new HSQLTableModel for the given ResultSet.
    * 
    * @param rs ResultSet to embed in the new HSQLTableModel
    * @return A new HSQLTableModel wrapped around the given ResultSet
    */
   public static TableModel tableResults( HSQLDB dm, ResultSet rs ) {
      try {
         return new HSQLTableModel( dm, rs );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new DefaultTableModel();
      }
   }
   
   public static TableModel tableResults( HSQLDB dm, ResultSet rs, String tableName ) {
      try {
         return new HSQLTableModel( dm, rs, tableName );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new DefaultTableModel();
      }
   }
   
}
