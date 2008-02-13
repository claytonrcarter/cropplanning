/* HSQLConnect.java - Created: March 15, 2007
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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class HSQLConnect {

   private static final String fileSep = System.getProperty("file.separator");
   
   private static final String dbProperties = "";
   private static final String dbOpenOnly = ";" + "ifexists=true";
   private static final String dbPrefix = "jdbc:hsqldb:file:";
   
   private static final boolean DEVEL_SERVER_CONNECTION = false;
   
   private HSQLConnect() {}

   public static Connection getConnection( String dir, String dbFilename, String driver ) {
      loadDriver( driver );
      return establishConnection( buildDBConnectionString( dir, dbFilename ));
   }
   
   public static Connection createDB( String dir, String dbFilename, long currentVersion ) {
      Connection con = connectToNew( buildDBConnectionString( dir, dbFilename ), currentVersion );
      return con;
   }

   /** 
    * Test the existence of a standard table to see if the database is empty.
    * This is only the case when HSQLD is running in server mode.
    * @param con Database connection upon which to operate.
    * @return True if the database is empty, false if it is not.
    */
   public static boolean dbIsEmpty( Connection con ) {
       return ! tableExists( con, "CROP_PLANS" );
   }
   
   /**
    * Simple test to see if a table exists.
    * @param con Connection upon which to operate.
    * @param tableName Name of table whose existence we question.
    * @return true if the table exists, false if it does not.
    */
   public static boolean tableExists( Connection con, String tableName ) {
    
      try {
         Statement st = con.createStatement();
    
//         String query = "select * from  " + tableName + " where 1=0";
         String query = "SELECT count(*) FROM  " + HSQLDB.escapeTableName( tableName );
         st = con.createStatement();
         st.executeQuery( query );
         return true;
      }
      catch ( Exception ignore ) {
         // table does not exist or some other problem
         return false;
      }
    
   }
   
   
   private static String buildDBConnectionString( String dir, String dbFilename ) {
      if ( DEVEL_SERVER_CONNECTION )
         return "jdbc:hsqldb:hsql://localhost/xdb";
      else 
         return dbPrefix + dir + fileSep + dbFilename + dbProperties;
   }
   
   private static void loadDriver( String driver ) {
      try {
         Class.forName( driver );
      } catch (Exception e) {
         System.out.println("ERROR: failed to load HSQLDB JDBC driver: '" + driver + "'");
         e.printStackTrace();
         System.exit( -1 );
      }
      
   }
   
   private static Connection establishConnection( String dbCon ) {

      int status;
      Connection con;
      
      con = connectToExisting( dbCon );

//      if ( con == null ) {  
//         con = connectToNew( dbCon );
//      }

      return con;
   }
   
   private static Connection connectToDB( String db ) {
      Connection con;
      
      try {
         con = DriverManager.getConnection( db, "sa", "" );
      }
      catch ( SQLException ex ) {
         int status = ex.getErrorCode();
         // if connect fails for reason other than DATABASE_NOT_EXISTS
         if ( status != ( -1 * org.hsqldb.Trace.DATABASE_NOT_EXISTS )) {
            System.out.println( "Caught error: " + ex.getSQLState() +
                                " (code: " + status + ") while connecting to " +
                                db );
            ex.printStackTrace();
         }
         con = null;
      }
      
      return con; 
   }
   
   private static Connection connectToExisting( String db ) {
      return connectToDB( db + dbOpenOnly );
   }
   
   private static Connection connectToNew( String db, long currentVersion ) {
      Connection con = connectToDB( db );
      
      if ( con == null ) // error occured
         return con;
      
      HSQLDBCreator.createTables( con, currentVersion );
//      HSQLDBPopulator.populateTables( con );
      
      return con;
   }
   
}
