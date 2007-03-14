/*
 * HSQLDB.java
 *
 * Created on January 16, 2007, 1:07 PM
 *
 *
 */

package CPS.Core.DB;

import CPS.Module.CPSDataModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.hsqldb.*;
import resultsettablemodel.*;

/**
 *
 * @author Clayton
 */
public class HSQLDB extends CPSDataModel {
   
   private Connection con;
   private final String hsqlDriver = "org.hsqldb.jdbcDriver";
   private final String dbDir = System.getProperty("user.dir");
   private final String fileSep = System.getProperty("file.separator");
   private final String dbFile = "CPSdb";
   private String dbPath, dbCon;
   private final String dbProperties = "";
   private final String dbOpenOnly = ";" + "ifexists=true";
   private final String dbPrefix = "jdbc:hsqldb:file:";
   
   
   public String state = null;
   
   
   public HSQLDB() {

      buildDBPaths();

      // Load HSQLDB driver
      loadDriver();

      // Establish connection to database, possibly creating the database.
      establishConnection();


   }
   
   /**
    * A constructor only useful for testing.  Creates an uninitialized
    * instance.
    */
   public HSQLDB( boolean test ) {
   }
      
   private void buildDBPaths() {
      
      dbPath = dbDir + fileSep + dbFile;
      dbCon  = dbPrefix + dbPath + dbProperties;
   
      state = "paths built";
      
   }
   
   private void loadDriver() {
      try {
         Class.forName( hsqlDriver );
      } catch (Exception e) {
         System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
         e.printStackTrace();
         System.exit( -1 );
      }
      
      state = "driver loaded";
   }
   
   private void establishConnection() {

      int status;
      
      status = connectToDB( dbCon + dbOpenOnly );
      
      // if status == 0, then db opened succesfully, otherwise ...
      
      if ( status == ( -1 * org.hsqldb.Trace.DATABASE_NOT_EXISTS )) {
         // no db found
         System.out.println( "No database found, must create one." );
         status = connectToDB( dbCon );
         
         // TODO check to see if db creation failed
         // if db creation failed, this will be bypassed and will be caught
         // by the below (... != 0) statement
         if ( status == 0 )
            createTables();
         
      }
      
      if ( status != 0 ) {
         System.err.println( "Unknown error: " + status );
         System.exit( status );
      }

   }
   
   /** 
    * connectToDB - connect to DB, error otherwise
    *
    * @return 0 on success, -1 on any failure
    */
   private int connectToDB( String db ) {
      
      int sqlErrorCode = 0;
      
      try {

         con = DriverManager.getConnection( db, "sa", "");
         state = "db connected";
      
      } catch ( SQLException ex ) {

         sqlErrorCode = ex.getErrorCode();
         if ( sqlErrorCode != ( -1 * org.hsqldb.Trace.DATABASE_NOT_EXISTS )) {
            System.out.println( "Caught error: " + ex.getSQLState() +
                                " (code: " + sqlErrorCode + ") while connecting to " +
                                db );
            ex.printStackTrace();
         }

         state = "db NOT connected";
         con = null;
      }

      return sqlErrorCode;

   }
   
   private int createTables() {
      System.out.println( "Creating database tables.");
      state = "creating tables";
      HSQLDBCreator.createTables( con );
      state = "tables created";
      HSQLDBPopulator.populateTables( con );
      state = "tables populated";
      return 0;
   }

   public synchronized ArrayList<String> getListOfCropPlans() {
      
      try {
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery( "SELECT plan_name FROM CROP_PLANS" );
      
         System.out.println("Executed query: " + "SELECT plan_name FROM CROP_PLANS" );
         
         ArrayList<String> l = new ArrayList<String>();
         while ( rs.next() ) {
            System.out.println("Found table entry: " + (String) rs.getObject(1) );
            l.add( (String) rs.getObject(1) );
         }
      
         return l;
      } 
      catch ( SQLException e ) { 
         e.printStackTrace();
         return new ArrayList<String>();
      }
      
   }

   private String getAbbreviatedColumnNames( boolean varieties ) {
      return "id, crop_name, " + ( varieties ? "var_name, " : "" ) + "fam_name, ds, tp, maturity";
   }
   
   private String getCropsColumnNames() {
      return "*";
   }
   
   private String getVarietiesColumnNames() {
      return getCropsColumnNames();
   }
   
   private synchronized TableModel submitQuery( String table, 
                                                String columns, 
                                                String conditional ) {
      String query = "SELECT " + columns + " FROM " + table;
      
      if ( conditional != null && conditional.length() > 0 )
         query += " WHERE ( " + conditional + " ) ";
      
      try {
         RSTableModelFactory rstmf = new RSTableModelFactory( con );
         return rstmf.getResultSetTableModel( query );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new DefaultTableModel();
      }
   }
   
   /* TODO create a method that will take a column list, table name and
    * conditional statement and will construct and submit a query, returning
    * a ResultSet
    * TODO create a wrapper method to turn a ResultSet into a TableModel
    */
   public TableModel getAbbreviatedCropList() {
      return submitQuery( "CROPS_VARIETIES", 
                          getAbbreviatedColumnNames( false ),
                          "var_name IS NULL" );
   }
   
   public TableModel getCropList() { 
      return submitQuery( "CROPS_VARIETIES", getCropsColumnNames(), null );
   }   

   public TableModel getVarietyList() {
      return submitQuery( "CROPS_VARIETIES", getVarietiesColumnNames(), null );
   }

   public TableModel getAbbreviatedVarietyList() {
      return submitQuery( "CROPS_VARIETIES", 
                          getAbbreviatedColumnNames( true ), 
                          "var_name IS NOT NULL" ); 
   }

   public TableModel getCropAndVarietyList() {
      return submitQuery( "CROPS_VARIETIES", "*", null );
   }

   
   public void createNewCropPlan(String plan_name) {
      HSQLDBCreator.createCropPlan( con, plan_name );
   }

   public void retrieveCropPlan(String plan_name) {
   }

   public void filterCropPlan(String plan_name, String filter) {
   }

   public TableModel getAbbreviatedCropAndVarietyList() {
      return submitQuery( "CROPS_VARIETIES", getAbbreviatedColumnNames( true ), null );
   }

}
