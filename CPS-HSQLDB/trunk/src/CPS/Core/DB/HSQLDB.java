/*
 * HSQLDB.java
 *
 * Created on January 16, 2007, 1:07 PM
 *
 *
 */

package CPS.Core.DB;

import CPS.Data.*;
import CPS.Module.CPSDataModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
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
   private ResultSet rsCropCache = null;
   public String state = null;
   
   private HSQLQuerier query;
   
   public HSQLDB() {

      con = HSQLConnect.getConnection( dbDir, dbFile, hsqlDriver );
      boolean newDB = false;
      
      if ( con == null ) { // db DNE
         con = HSQLConnect.createDB( dbDir, dbFile );
         newDB = true;
      }
         
      query = new HSQLQuerier( con );

      if ( newDB ) {
         this.importCropsAndVarieties( HSQLDBPopulator.loadDefaultCropList( dbDir )
                                                      .exportCropsAndVarieties() );
      }
      
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

   private String getMandatoryColumnNames() {
      return "id, crop_name";
   }
   private String getAbbreviatedColumnNames( boolean varieties ) {
      return getMandatoryColumnNames() + ", " +
             ( varieties ? "var_name, " : "" ) + "fam_name, maturity";
   }
   private String getFilterColumnNames() {
      return getAbbreviatedColumnNames( true ) + ", keywords, groups";
   }
   
   private String getCropsColumnNames() {
      return "*";
   }
   
   private String getVarietiesColumnNames() {
      return getCropsColumnNames();
   }
   
   
   /** Method to cache results of a query and then return those results as a table */
   private TableModel cachedListTableQuery( String t, String col, 
                                            String cond, String sort, String filter ) {
      rsListCache = query.storeQuery( t, col, cond, sort, filter );
      // return query.getCachedResultsAsTable();
      return query.tableResults( rsListCache );
   }

   /*
    * CROP LIST METHODS
    */
      
   // TODO all of these non sorting methods can be folded into the sorting methods (ie get() => get(null) )
   public TableModel getAbbreviatedCropList() {
      return getAbbreviatedCropList( null );
   }
   public TableModel getAbbreviatedCropList( String sortCol ) {
      return getAbbreviatedCropList( sortCol, null );
   }
   public TableModel getAbbreviatedCropList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", 
                                   getAbbreviatedColumnNames( false ),
                                   "var_name IS NULL",
                                   sortCol,
                                   buildFilterExpression( getFilterColumnNames(), filterString ));
   }
   
   public TableModel getCropList() { 
      return getCropList( null, null );
   }
   public TableModel getCropList( String sortCol ) { 
      return getCropList( sortCol, null );
   }   
   public TableModel getCropList( String sortCol, String filterString ) { 
      return cachedListTableQuery( "CROPS_VARIETIES", getCropsColumnNames(), null, sortCol,
                                   buildFilterExpression( getFilterColumnNames(), filterString ));
   }
   
   public TableModel getVarietyList() {
      return getVarietyList( null, null );
   }
   public TableModel getVarietyList( String sortCol ) {
      return getVarietyList( sortCol, null );
   }
   public TableModel getVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", getVarietiesColumnNames(), null, sortCol,
                                   buildFilterExpression( getFilterColumnNames(), filterString ));
   }

   public TableModel getAbbreviatedVarietyList() {
      return getAbbreviatedVarietyList( null, null ); 
   }
   public TableModel getAbbreviatedVarietyList( String sortCol ) {
      return getAbbreviatedVarietyList( sortCol, null ); 
   }
   public TableModel getAbbreviatedVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", 
                                   getAbbreviatedColumnNames( true ),
                                   "var_name IS NOT NULL",
                                   sortCol,
                                   buildFilterExpression( getFilterColumnNames(), filterString )); 
   }
   
   public TableModel getCropAndVarietyList() {
      return getCropAndVarietyList( null, null );
   }
   public TableModel getCropAndVarietyList( String sortCol ) {
      return getCropAndVarietyList( sortCol, null );
   }
   public TableModel getCropAndVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", "*", null, sortCol,
                                   buildFilterExpression( getFilterColumnNames(), filterString ));
   }
   
   public TableModel getAbbreviatedCropAndVarietyList() {
      return getAbbreviatedCropAndVarietyList( null, null );
   }
   public TableModel getAbbreviatedCropAndVarietyList( String sortCol ) {
      return getAbbreviatedCropAndVarietyList( sortCol, null );
   }
   public TableModel getAbbreviatedCropAndVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", getAbbreviatedColumnNames( true ), null, sortCol,
                                   buildFilterExpression( getFilterColumnNames(), filterString ) );
   }

   /*
    * CROP PLAN METHODS
    */
   public void createNewCropPlan( String plan_name ) {
      HSQLDBCreator.createCropPlan( con, plan_name );
   }

   public void retrieveCropPlan(String plan_name) {
   }

   public void filterCropPlan(String plan_name, String filter) {
   }

   /* we make the assumption that we're zero-based, ResultSets are not */
   public CPSCrop getCropInfo( int id ) {
      try {
         // TODO figure out better way to handle result caching
         // we could just make this a string based query (ie getCropInfoForRow
         // disabling this makes it a simple query on an ID
         if ( false && rsListCache != null ) {
            rsListCache.absolute( id + 1 );
            id = rsListCache.getInt( "id" );
         } 
         rsCropCache = query.submitQuery( "CROPS_VARIETIES", "*", "id = " + id );
         return resultSetAsCrop( rsCropCache );
      }
      catch ( SQLException e ) { e.printStackTrace(); }

      return null;
   }
   
   
   public CPSCrop getVarietyInfo( String cropName, String varName ) {
      
      if ( cropName == null || cropName.equalsIgnoreCase("null") || cropName.equals("") )
         return new CPSCrop();
      
      String condExp = "crop_name = " + escapeValue( cropName );
      
      varName = escapeValue( varName );
      
      if ( varName == null || varName.equalsIgnoreCase( "null" ) || varName.equals("") )
         condExp += " AND var_name IS NULL";
      else
         condExp += " AND var_name = " + varName;
      
      try {
         return resultSetAsCrop( query.submitQuery( "CROPS_VARIETIES", "*", condExp ));
      } catch ( SQLException e ) { 
         e.printStackTrace(); 
         return new CPSCrop();
      }
   }
   
   // TODO this is where we can implement crop dependencies
   // if a value is blank, leave it blank
   // if a value is null, we can request that info from
   //    for crops: the similar crop (var_name == null)
   //    for varieties: the crop
   private CPSCrop resultSetAsCrop( ResultSet rs ) throws SQLException {
      
      CPSCrop crop = new CPSCrop();
      
      // move to the first (and only) row
      if ( rs.next() ) {
         try {
            
            // TODO, define a null value in the crop.setXX methods
            // and invalidate
            
            crop.setID( rs.getInt( "ID" ));
            crop.setCropName( captureString( rs.getString( "crop_name" ) ));
            
            crop.setVarietyName( captureString( rs.getString( "var_name" ) ));

            crop.setSimilarCrop( getCropInfo( rs.getString("similar_to") ));

            crop.setFamilyName( captureString( rs.getString( "fam_name" ) ));
            crop.setCropDescription( captureString( rs.getString("description") ));

            int i = rs.getInt( "maturity" );
            if ( i <= 0 )
               i = -1;
            crop.setMaturityDays( i );
            
            crop.setSuccessions( rs.getBoolean("successions") );
            
            crop.setGroups( captureString( rs.getString( "groups" ) ));
            crop.setOtherRequirements( captureString( rs.getString( "other_req" ) ));
            crop.setKeywords( captureString( rs.getString( "keywords" ) ));
            crop.setNotes( captureString( rs.getString( "notes" ) ));

            /* Now handle the data chain.
             */
            Iterator<CropDatum> thisCrop = crop.iterator();
            Iterator<CropDatum> superCrop;
            Iterator<CropDatum> similarCrop = crop.getSimilarCrop().iterator();
      
            if ( crop.isVariety() )
               superCrop = getCropInfo( crop.getCropName() ).iterator();
            else
               superCrop = crop.iterator();
            
            CropDatum t, d, s;
            while ( thisCrop.hasNext() && superCrop.hasNext() && similarCrop.hasNext() ) {
               t = thisCrop.next();
               d = superCrop.next();
               s = similarCrop.next();
               if ( ! t.isValid() && t.shouldBeChained() )
                  if ( crop.isVariety() )
                     t.setDatum( d.getDatum() );
                  else
                     t.setDatum( s.getDatum() );
            }
            
         }  catch ( SQLException e ) { e.printStackTrace(); }
      }
      
      return crop;
   }

   public void shutdown() {
      try {
         Statement st = con.createStatement();
         st.execute("SHUTDOWN");
         con.close();
      }
      catch ( SQLException ex ) {
         ex.printStackTrace();
      }
   }

   public void updateCrop( CPSCrop crop ) {
      HSQLDBCreator.updateCrop( con, crop );
   }

   public CPSCrop createCrop(CPSCrop crop) {
      int newID = HSQLDBCreator.insertCrop( con, crop );
      if ( newID == -1 )
         return new CPSCrop();
      else
         return getCropInfo( newID );
   }
   
   
   public ArrayList<CPSCrop> exportCropsAndVarieties() { return null; }
   
   /** Opposite of escapeValue.
    *  Takes an SQL value and converts it to the correct "default" or "null"
    *  value for our program.
    */
   public static String captureString( String s ) {
      if ( s == null || s.equalsIgnoreCase("null") || s.equals("") )
         return null;
      else
         return s;
   }
   
   public static String escapeValue( Object o ) {
      // if the datum doesn't exist, use NULL
      if      ( o == null )
         return "NULL";
      // if the datum is a string and is only "", use NULL, else escape it
      else if ( o instanceof String )
         if ( o.equals("") || ((String) o).equalsIgnoreCase( "null" ) )
            return "NULL";
         else
            return "'" + o.toString() + "'";
      // if the datum is an int whose value is -1, use NULL
      else if ( o instanceof Integer &&
                  ((Integer) o).intValue() == -1 )
         return "NULL";
      else if ( o instanceof CPSCrop )
         return escapeValue( ((CPSCrop) o).getCropName() );
      else
         return o.toString();
   }

   private String buildFilterExpression( String colList, String filterString ) {
      if ( filterString == null )
         return null;
      
      // TODO: if filterString not all lower, then omit LOWER below
      filterString = filterString.toLowerCase();
      
      String exp = "";
      // loop over the list of column names (seperated by commas)
      for( String col : colList.split(",") ) {
         exp += "LOWER( " + col + " ) LIKE " + 
                escapeValue( "%" + filterString + "%" ) + " OR ";
      }
      // strip off the final " OR "
      return exp.substring( 0, exp.lastIndexOf( " OR " ));
   }
   
   
}
