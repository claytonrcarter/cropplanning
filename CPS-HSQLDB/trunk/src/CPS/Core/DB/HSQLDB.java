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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
   private ResultSet rsPlantCache = null;
   public String state = null;
   
   private HSQLQuerier query;
   
   public HSQLDB() {

      con = HSQLConnect.getConnection( dbDir, dbFile, hsqlDriver );
      boolean newDB = false;
      
      if ( con == null ) { // db DNE
         con = HSQLConnect.createDB( dbDir, dbFile );
         newDB = true;
      }
      /* only needed when we're using a server mode db */
      else if ( HSQLConnect.dbIsEmpty( con ) ) {
         HSQLDBCreator.createTables( con );
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
   private String getAbbreviatedCropVarColumnNames( boolean varieties ) {
      return getMandatoryColumnNames() + ", " +
             ( varieties ? "var_name, " : "" ) + "fam_name, maturity";
   }
   private String getCropVarFilterColumnNames() {
      return getAbbreviatedCropVarColumnNames( true ) + ", keywords, groups";
   }
   
   private String getCropsColumnNames() {
      return "*";
   }
   
   private String getVarietiesColumnNames() {
      return getCropsColumnNames();
   }
   
   private String getAbbreviatedCropPlanColumnNames() {
      return getMandatoryColumnNames() + ", " + "var_name, maturity, location, completed, " + 
                  "date_plant, date_harvest, rows_p_bed";
   }
   private String getCropPlanFilterColumnNames() {
      return getAbbreviatedCropPlanColumnNames() + ", keywords, groups";
   }
   

   /** Method to cache results of a query and then return those results as a table
    *  @param t Table name
    *  @param col list of columns to select
    *  @param cond conditional statement
    *  @param sort sort statement
    *  @param filter filter statement
    */
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
                                   getAbbreviatedCropVarColumnNames( false ),
                                   "var_name IS NULL",
                                   sortCol,
                                   buildFilterExpression( getCropVarFilterColumnNames(), filterString ));
   }
   
   public TableModel getCropList() { 
      return getCropList( null, null );
   }
   public TableModel getCropList( String sortCol ) { 
      return getCropList( sortCol, null );
   }   
   public TableModel getCropList( String sortCol, String filterString ) { 
      return cachedListTableQuery( "CROPS_VARIETIES", getCropsColumnNames(), null, sortCol,
                                   buildFilterExpression( getCropVarFilterColumnNames(), filterString ));
   }
   
   public TableModel getVarietyList() {
      return getVarietyList( null, null );
   }
   public TableModel getVarietyList( String sortCol ) {
      return getVarietyList( sortCol, null );
   }
   public TableModel getVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", getVarietiesColumnNames(), null, sortCol,
                                   buildFilterExpression( getCropVarFilterColumnNames(), filterString ));
   }

   public TableModel getAbbreviatedVarietyList() {
      return getAbbreviatedVarietyList( null, null ); 
   }
   public TableModel getAbbreviatedVarietyList( String sortCol ) {
      return getAbbreviatedVarietyList( sortCol, null ); 
   }
   public TableModel getAbbreviatedVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", 
                                   getAbbreviatedCropVarColumnNames( true ),
                                   "var_name IS NOT NULL",
                                   sortCol,
                                   buildFilterExpression( getCropVarFilterColumnNames(), filterString )); 
   }
   
   public TableModel getCropAndVarietyList() {
      return getCropAndVarietyList( null, null );
   }
   public TableModel getCropAndVarietyList( String sortCol ) {
      return getCropAndVarietyList( sortCol, null );
   }
   public TableModel getCropAndVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", "*", null, sortCol,
                                   buildFilterExpression( getCropVarFilterColumnNames(), filterString ));
   }
   
   public TableModel getAbbreviatedCropAndVarietyList() {
      return getAbbreviatedCropAndVarietyList( null, null );
   }
   public TableModel getAbbreviatedCropAndVarietyList( String sortCol ) {
      return getAbbreviatedCropAndVarietyList( sortCol, null );
   }
   public TableModel getAbbreviatedCropAndVarietyList( String sortCol, String filterString ) {
      return cachedListTableQuery( "CROPS_VARIETIES", getAbbreviatedCropVarColumnNames( true ), null, sortCol,
                                   buildFilterExpression( getCropVarFilterColumnNames(), filterString ) );
   }

   /*
    * CROP PLAN METHODS
    */
   public void createCropPlan( String plan_name ) {
      HSQLDBCreator.createCropPlan( con, plan_name );
   }
   
   // TODO implement updateCropPlan
   public void updateCropPlan( String plan_name ) {
   }
   
   public TableModel getCropPlan(String plan_name) {
      return getCropPlan( plan_name, null, null );
   }
   
   public TableModel getCropPlan( String plan_name, String sortCol ) {
      return getCropPlan( plan_name, sortCol, null );
   }

   public TableModel getCropPlan( String plan_name, String sortCol, String filterString ) {
      return cachedListTableQuery( plan_name, getAbbreviatedCropPlanColumnNames(), 
                                   null, sortCol, 
                                   buildFilterExpression( this.getCropPlanFilterColumnNames(), filterString ));
   }

   
   /*
    * CROP AND VARIETY METHODS
    */
   
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
   
   /* Another TODO: we could totally automate this with an iterator:
    *  if ( datum instanceof String )
    *     datum.setDatum( captureString( rs.getString( datum.getColumnName() ))
    *  and so on...
    */  
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
            
            crop.setID( rs.getInt( "ID" ));
            crop.setCropName( captureString( rs.getString( "crop_name" ) ));            
            crop.setVarietyName( captureString( rs.getString( "var_name" ) ));

            crop.setSimilarCrop( getCropInfo( rs.getString("similar_to") ));

            crop.setFamilyName( captureString( rs.getString( "fam_name" ) ));
            crop.setCropDescription( captureString( rs.getString("description") ));
            
            crop.setMaturityDays( rs.getInt( "maturity" ) );
            
            crop.setSuccessions( rs.getBoolean("successions") );
            
            crop.setGroups( captureString( rs.getString( "groups" ) ));
            crop.setOtherRequirements( captureString( rs.getString( "other_req" ) ));
            crop.setKeywords( captureString( rs.getString( "keywords" ) ));
            crop.setNotes( captureString( rs.getString( "notes" ) ));

            crop.setTimeToTP( rs.getInt( "time_to_tp" ));
            crop.setRowsPerBed( rs.getInt( "rows_p_bed" ));
            crop.setSpaceInRow( rs.getInt( "space_inrow" ));
            crop.setSpaceBetweenRow( rs.getInt( "space_betrow" ));
            crop.setFlatSize( captureString( rs.getString( "flat_size" )));
            crop.setPlanter( captureString( rs.getString( "planter" )));
            crop.setPlanterSetting( captureString( rs.getString( "planter_setting" )));
            crop.setYieldPerFoot( rs.getFloat( "yield_p_foot" ));
            crop.setYieldNumWeeks( rs.getInt( "yield_num_weeks" ));
            crop.setYieldPerWeek( rs.getInt( "yield_p_week" ));
            crop.setCropYieldUnit( captureString( rs.getString( "crop_unit" )));
            crop.setCropUnitValue( rs.getFloat( "crop_unit_value" ));
            
            /* Now handle the data chain.
             */
            Iterator<CropDatum> thisCrop = crop.iterator();
            // Iterator<CropDatum> superCrop;
            Iterator<CropDatum> similarCrop = crop.getSimilarCrop().iterator();
      
            if ( crop.isVariety() )
               // superCrop = getCropInfo( crop.getCropName() ).iterator();
               similarCrop = getCropInfo( crop.getCropName() ).iterator();
            else
               // superCrop = crop.iterator();
               similarCrop = crop.getSimilarCrop().iterator();
            
            CropDatum t, d, s;
            // while ( thisCrop.hasNext() && superCrop.hasNext() && similarCrop.hasNext() ) {
            while ( thisCrop.hasNext() && similarCrop.hasNext() ) {
               t = thisCrop.next();
               // d = superCrop.next();
               s = similarCrop.next();
               if ( ! t.isValid() && t.shouldBeInherited() ) {
//                  if ( crop.isVariety() )
//                     t.setDatum( d.getDatum() );
//                  else
//                     t.setDatum( s.getDatum() );
                  t.setDatum( s.getDatum() );
                  t.setIsInherited(true); // mark this datum as having been inherited
               }
            }
            
         }  catch ( SQLException e ) { e.printStackTrace(); }
      }
      
      return crop;
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
   
   public void deleteCrop( int cropID ) {
       HSQLDBCreator.deleteRecord(con, "CROPS_VARIETIES", cropID);   
   }
   
   public void deletePlanting( String planName, int plantingID ) {
       HSQLDBCreator.deleteRecord( con, planName, plantingID );
   }
   
   public CPSPlanting createPlanting( String planName, CPSPlanting planting ) {
      int newID = HSQLDBCreator.insertPlanting( con, planName, planting );
      if ( newID == -1 )
         return new CPSPlanting();
      else
         return getPlanting( planName, newID );
   }

   
   /* we make the assumption that we're zero-based, ResultSets are not */
   public CPSPlanting getPlanting( String planName, int id ) {
      try {
         // TODO figure out better way to handle result caching
         // we could just make this a string based query (ie getCropInfoForRow
         // disabling this makes it a simple query on an ID
         if ( false && rsListCache != null ) {
            rsListCache.absolute( id + 1 );
            id = rsListCache.getInt( "id" );
         } 
         rsPlantCache = query.submitQuery( planName, "*", "id = " + id );
         return resultSetAsPlanting( rsPlantCache );
      }
      catch ( SQLException e ) { e.printStackTrace(); }

      return null;
   }

   public void updatePlanting( String planName, CPSPlanting planting ) {
      HSQLDBCreator.updatePlanting( con, planName, planting );
   }
   
   // TODO this is where we can implement crop dependencies
   // if a value is blank, leave it blank
   // if a value is null, we can request that info from
   //    for crops: the similar crop (var_name == null)
   //    for varieties: the crop
   private CPSPlanting resultSetAsPlanting( ResultSet rs ) throws SQLException {
      
      CPSPlanting p = new CPSPlanting();
      
      // move to the first (and only) row
      if ( rs.next() ) {
         try {
            
            // TODO, define a null value in the crop.setXX methods
            // and invalidate
            
            p.setID( rs.getInt( "ID" ));
            p.setCropName( captureString( rs.getString( "crop_name" ) ));            
            p.setVarietyName( captureString( rs.getString( "var_name" ) ));

            p.setLocation( captureString( rs.getString( "location" )));

            // not yet implemented
//          case PROP_STATUS:        return status;
//          case PROP_COMPLETED:     return completed;
           
            p.setDateToPlant( captureDate( rs.getDate( "date_plant" )));
            p.setDateToTP( captureDate( rs.getDate( "date_tp" )));
            p.setDateToHarvest( captureDate( rs.getDate( "date_harvest" )));

            p.setBedsToPlant( captureInt( rs.getInt( "beds_to_plant") ));
            p.setPlantsNeeded( captureInt( rs.getInt( "plants_needed") ));
            p.setPlantsToStart( captureInt( rs.getInt( "plants_to_start") ));
            p.setRowFtToPlant( captureInt( rs.getInt( "rowft_to_plant") ));
            p.setFlatsNeeded( captureInt( rs.getInt( "flats_needed") ));
            
            p.setMaturityDays( captureInt( rs.getInt("maturity") ));
            
            p.setGroups( captureString( rs.getString( "groups" ) ));
            p.setOtherRequirements( captureString( rs.getString( "other_req" ) ));
            p.setKeywords( captureString( rs.getString( "keywords" ) ));
            p.setNotes( captureString( rs.getString( "notes" ) ));
            
         }  catch ( SQLException e ) { e.printStackTrace(); }
      }
      
      return p;
   }

   
   public ArrayList<CPSCrop> exportCropsAndVarieties() { return null; }
   
   /** 
    * SQL distinguishes between values that are NULL and those that are just
    * blank.  This method is meant to capture our default values
    * and format them properly so that we might detect proper null values
    * upon read.  We use this to distinguish between null values and just
    * data with no entry.
    * 
    * OK, so what constitutes a null value, blank value, and one that is
    * just a default value?  Let's try to answer that for different data types:
    *   Object: only 'null' objects are SQL NULL
    *   String: a string "null" is read as SQL NULL
    *           anything else is considered a valid entry
    *           Therefore we must decide when to pass back "null" vs "" when
    *             strings w/o entries are encountered.
    *  Integer: a value of -1 is an SQL NULL
    *           anything else is valid
    *  Float:   a value of -1.0 is an SQL NULL, all else valid
    *  Date:    a millisecond date of 0 is an SQL NULL
    *           anything else is valid
    * 
    * @param o The object to test and format.
    * @return A string representing the SQL value of Object o.
    */
   /* Currently handles: null, String, Integer, CPSCrop, CPSPlanting */
   public static String escapeValue( Object o ) {
      // if the datum doesn't exist, use NULL
      if      ( o == null )
         return "NULL";
      // if the datum is a string and is only "", use NULL, else escape it
      else if ( o instanceof String )
         if ( ((String) o).equalsIgnoreCase( "null" ) )
            return "NULL";
         else
            return "'" + o.toString() + "'";
      // if the datum is an int whose value is -1, use NULL
      else if ( o instanceof Integer && ((Integer) o).intValue() == -1 )
         return "NULL";
      else if ( o instanceof Float && ((Float) o).floatValue() == -1.0 )
         return "NULL";
      else if ( o instanceof java.util.Date || o instanceof java.sql.Date ) {
          // cast to util.Date to cover all of our bases, sql.Date is in scope
          // here, so we must use fully qualified name
          if ( ((java.util.Date) o).getTime() == 0 )
              return "NULL";
          else {
              // TODO figure how to make the date format more flexible
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              return "'" + sdf.format((java.util.Date) o) + "'";
          }
      }
      /* Not entirely sure what these are here for.  Actually, the CPSCrop
       * case is probably for data cascading.  Planting case might not be 
       * appropriate */
      else if ( o instanceof CPSCrop )
         return escapeValue( ((CPSCrop) o).getCropName() );
      else if ( o instanceof CPSPlanting )
         return escapeValue( ((CPSPlanting) o).getCropName() );
      else
         return o.toString();
   }

   /** captureValue methods are opposite of escapeValue method.
    *  Takes an SQL value and converts it to the correct "default" or "null"
    *  value for our program.
    */
   public static Date captureDate( Date d ) {
      if ( d == null )
         // PENDING this is totally bogus and needs to have a sane "default" date
         return new Date( 0 );
      else
         return d;
   }
   
   public static int captureInt( int i ) {
      if ( i <= 0 )
         return -1;
      else
         return i;
   }
   
   public static String captureString( String s ) {
      if ( s == null || s.equalsIgnoreCase("null") || s.equals("") )
         return null;
      else
         return s;
   }
   
   
   
   private String buildFilterExpression( String colList, String filterString ) {
      if ( filterString == null || filterString.length() == 0 )
         return null;
      
      /* error if trying to filter all columns, that's bad SQL */
      if ( colList.equalsIgnoreCase( "*" )) {
         String err = "ERROR: cannot filter columns " + colList + "; ";
         colList = getMandatoryColumnNames();
         err += "Filtering only columns: " + colList;
         System.err.println( err );
      }
      
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
   
   public void shutdown() {
      try {
//         Statement st = con.createStatement();
//         st.execute("SHUTDOWN");
         con.close();
      }
      catch ( SQLException ex ) {
         ex.printStackTrace();
      }
   }

}
