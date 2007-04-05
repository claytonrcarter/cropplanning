/*
 * HSQLConnect.java
 *
 * Created on March 15, 2007, 9:51 AM
 */

package CPS.Core.DB;

import CPS.Module.CPSDataModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class HSQLConnect {

   private static final String fileSep = System.getProperty("file.separator");
   
   private static final String dbProperties = "";
   private static final String dbOpenOnly = ";" + "ifexists=true";
   private static final String dbPrefix = "jdbc:hsqldb:file:";
   
   
   private HSQLConnect() {}

   public static Connection getConnection( String dir, String dbFilename, String driver ) {
      loadDriver( driver );
      return establishConnection( buildDBConnectionString( dir, dbFilename ));
   }
   
   public static Connection createDB( String dir, String dbFilename ) {
      Connection con = connectToNew( buildDBConnectionString( dir, dbFilename ));
      return con;
   }
   
   private static String buildDBConnectionString( String dir, String dbFilename ) {
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
   
   private static Connection connectToNew( String db ) {
      Connection con = connectToDB( db );
      
      if ( con == null ) // error occured
         return con;
      
      HSQLDBCreator.createTables( con );
//      HSQLDBPopulator.populateTables( con );
      
      return con;
   }
   
}
