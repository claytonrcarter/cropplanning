/*
 * HSQLDB.java
 *
 * Created on January 16, 2007, 1:07 PM
 *
 *
 */

package CPS.Core.DB;

import CPS.Data.CPSCrop;
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
   private final String dbFile = "CPSdb";
   
   private ResultSet rsListCache = null;
   private ResultSet rsInfoCache = null;
   public String state = null;
   
   private HSQLQuerier query;
   
   public HSQLDB() {

      con = HSQLConnect.getConnection( dbDir, dbFile, hsqlDriver );
      query = new HSQLQuerier( con );
      
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
   
   
   /** Method to cache results of a query and then return those results as a table */
   private TableModel cachedListTableQuery( String t, String col, String cond ) {
      rsListCache = query.submitQuery( t, col, cond );
      return query.getCachedResultsAsTable();
   }

   /*
    * CROP LIST METHODS
    */
   
   /* TODO create a method that will take a column list, table name and
    * conditional statement and will construct and submit a query, returning
    * a ResultSet
    * TODO create a wrapper method to turn a ResultSet into a TableModel
    */   
   public TableModel getAbbreviatedCropList() {
      return cachedListTableQuery( "CROPS_VARIETIES", 
                                   getAbbreviatedColumnNames( false ),
                                   "var_name IS NULL" );
   }
   
   public TableModel getCropList() { 
      return cachedListTableQuery( "CROPS_VARIETIES", getCropsColumnNames(), null );
   }   

   public TableModel getVarietyList() {
      return cachedListTableQuery( "CROPS_VARIETIES", getVarietiesColumnNames(), null );
   }

   public TableModel getAbbreviatedVarietyList() {
      return cachedListTableQuery( "CROPS_VARIETIES", 
                                   getAbbreviatedColumnNames( true ),
                                   "var_name IS NOT NULL" ); 
   }

   public TableModel getCropAndVarietyList() {
      return cachedListTableQuery( "CROPS_VARIETIES", "*", null );
   }
   
   public TableModel getAbbreviatedCropAndVarietyList() {
      return cachedListTableQuery( "CROPS_VARIETIES", getAbbreviatedColumnNames( true ), null );
   }

   /*
    * CROP PLAN METHODS
    */
   public void createNewCropPlan(String plan_name) {
      HSQLDBCreator.createCropPlan( con, plan_name );
   }

   public void retrieveCropPlan(String plan_name) {
   }

   public void filterCropPlan(String plan_name, String filter) {
   }


   public CPSCrop getCropInfoForRow( int selectedRow ) {
      try {
         rsListCache.absolute( selectedRow );
         int id = rsListCache.getInt( "id" );
         rsInfoCache = query.submitQuery( "CROPS_VARIETIES", "*", "id = " + id );
         // turn rsInfoCache ResultSet into a Crop or Variety Object
         System.out.println("Retrieved info, discarding.");
      }
      catch ( SQLException e ) { e.printStackTrace(); }
      
      return null;
   }

   private CPSCrop resultSetAsCrop( ResultSet rs ) {
      
      CPSCrop crop = new CPSCrop();
      
      
      
      return crop;
   }
   
}
