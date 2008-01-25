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
import java.util.Collections;
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
   
   private ArrayList<String> cropColumnList, plantingColumnList;
   private ArrayList<String[]> plantingCropColumnMapping;
   
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
      
      plantingCropColumnMapping = buildPlantingCropColumnMapping();
      cropColumnList = buildCropColumnList();
      buildPlantingColumnList();
      
   }
   
   private synchronized ArrayList<String> getDistinctValuesForColumn( String table, String column ) {
      try {
         String query = "SELECT DISTINCT " + column + " FROM " + table;
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery( query );
      
//         System.out.println("Executed query: " + query );
         
         ArrayList<String> l = new ArrayList<String>();
         while ( rs.next() ) {
            String s = (String) rs.getObject(1);
            if ( s == null || s.equals( "" ) )
               continue;
//            System.out.println("DEBUG Adding item " + (String) rs.getObject(1) );
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
   
   public synchronized ArrayList<String> getCropNames() {
      return getDistinctValuesForColumn( "CROPS_VARIETIES", "crop_name" );
   }
   
   public synchronized ArrayList<String> getVarietyNames( String crop_name ) {
      // FIXME this queries ALL varieties, but should only query varieties of crop `crop_name`
      return getDistinctValuesForColumn( "CROPS_VARIETIES", "var_name" );
   }
   
   public synchronized ArrayList<String> getFamilyNames() {
      return getDistinctValuesForColumn( "CROPS_VARIETIES", "fam_name" );
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
   
   private ArrayList<String> getCropColumnList() {
      return cropColumnList;
   }
   
   private ArrayList<String> buildCropColumnList() {
      ArrayList<String> l = new ArrayList();
      l.add( "id" );
      l.add( "crop_name" );
      l.add( "var_name" );
      l.add( "bot_name" );
      l.add( "description" );
      l.add( "keywords" );
      l.add( "other_req" );
      l.add( "notes" );
      l.add( "maturity" );
      l.add( "time_to_tp" );
      l.add( "rows_p_bed" );
      l.add( "space_inrow" );
      l.add( "space_betrow" );
      l.add( "flat_size" );
      l.add( "planter" );
      l.add( "planter_setting" );
      l.add( "yield_p_foot" );
      l.add( "yield_num_weeks" );
      l.add( "yield_p_week" );
      l.add( "crop_unit" );
      l.add( "crop_unit_value" );
      l.add( "fudge" );
      l.add( "mat_adjust" );
      l.add( "misc_adjust" );
      l.add( "fam_name" );
      l.add( "groups" );
      l.add( "successions" );

      return l;
   }
   
   public ArrayList<String> getCropDisplayableColumns() {
      ArrayList<String> l = getCropColumnList();
      l.remove(0); // remove id from list
      return l;
   }
   
   private ArrayList<String[]> getPlantingCropColumnMapping() {
      return plantingCropColumnMapping;
   }
   
   private ArrayList<String[]> buildPlantingCropColumnMapping() {
      ArrayList<String[]> l = new ArrayList();
      
      // Third column: "how to calculate"; must be in SQL and must fit into a COALESCE expression
      
      l.add( new String[] { "id",              null,              null } );
      l.add( new String[] { "crop_name",       null,              null } );
      l.add( new String[] { "var_name",        null,              null } );
      l.add( new String[] { "groups",          "groups",          null } );
      l.add( new String[] { "successions",     null,              null } );
      l.add( new String[] { "location",        null,              null } );
      
      l.add( new String[] { "keywords",        "keywords",        null } );
      l.add( new String[] { "status",          null,              null } );
      l.add( new String[] { "completed",       null,              null } );
      l.add( new String[] { "other_req",       "other_req",       null } );
      l.add( new String[] { "notes",           null,              null } );
      
      l.add( new String[] { "maturity",        "maturity",        null } );
      l.add( new String[] { "mat_adjust",      "mat_adjust",      null } );
      l.add( new String[] { "planting_adjust", null,              null } );
      l.add( new String[] { "ds_adjust",       null,              null } );
      l.add( new String[] { "season_adjust",   null,              null } );
      l.add( new String[] { "time_to_tp",      "time_to_tp",      null } );
      l.add( new String[] { "misc_adjust",     "misc_adjust",     null } );
      
      l.add( new String[] { "date_plant",      null,              "\"CPS.Core.DB.HSQLCalc.plantFromHarvest\"( date_harvest, maturity ), "
                                                                + "\"CPS.Core.DB.HSQLCalc.plantFromTP\"( date_tp, time_to_tp )" } );
      l.add( new String[] { "date_tp",         null,              "\"CPS.Core.DB.HSQLCalc.TPFromPlant\"( date_plant, time_to_tp )" } );
      l.add( new String[] { "date_harvest",    null,              "\"CPS.Core.DB.HSQLCalc.harvestFromPlant\"( date_plant, maturity ) " } );
      
      l.add( new String[] { "beds_to_plant",   null,              "\"CPS.Core.DB.HSQLCalc.bedsFromRowFt\"( rowft_to_plant, rows_p_bed, 100 ), "
                                                                + "\"CPS.Core.DB.HSQLCalc.bedsFromPlants\"( plants_needed, inrow_space, rows_p_bed, 100 )" } );
      l.add( new String[] { "rows_p_bed",      "rows_p_bed",      null } );
      l.add( new String[] { "plants_needed",   null,              "\"CPS.Core.DB.HSQLCalc.plantsFromBeds\"( beds_to_plant, inrow_space, rows_p_bed, 100 ), "
                                                                + "\"CPS.Core.DB.HSQLCalc.plantsFromRowFt\"( rowft_to_plant, inrow_space )" } );
      l.add( new String[] { "rowft_to_plant",  null,              "\"CPS.Core.DB.HSQLCalc.rowFtFromBeds\"( beds_to_plant, rows_p_bed, 100 ), "
                                                                + "\"CPS.Core.DB.HSQLCalc.rowFtFromPlants\"( plants_needed, inrow_space )" } );
      l.add( new String[] { "inrow_space",     "space_inrow",     null } );
      l.add( new String[] { "row_space",       "space_betrow",    null } );
      l.add( new String[] { "plants_to_start", null,              "\"CPS.Core.DB.HSQLCalc.plantsToStart\"( plants_needed, .25 )" } );
      l.add( new String[] { "flat_size",       "flat_size",       null } );
      l.add( new String[] { "flats_needed",    null,              null } );
//      l.add( new String[] { "flats_needed",    null,              "\"CPS.Core.DB.HSQLCalc.flatsNeeded\"( plants_to_start, flat_size )" } );
      l.add( new String[] { "planter",         "planter",         null } );
      l.add( new String[] { "planter_setting", "planter_setting", null } );
      
      l.add( new String[] { "yield_p_foot",    "yield_p_foot",    null } );
      l.add( new String[] { "total_yield",     null,              "\"CPS.Core.DB.HSQLCalc.totalYieldFromRowFt\"( rowft_to_plant, yield_p_foot )" } );
      l.add( new String[] { "yield_num_weeks", "yield_num_weeks", null } );
      l.add( new String[] { "yield_p_week",    "yield_p_week",    null } );
      
      l.add( new String[] { "crop_unit",       "crop_unit",       null } );
      l.add( new String[] { "crop_unit_value", "crop_unit_value", null } );
      
      return l;
   }
   
   private ArrayList<String> getPlantingColumnList() {
      return plantingColumnList;
   }
   
   private void buildPlantingColumnList() {
      plantingColumnList = new ArrayList();
   
      plantingColumnList.add( "id" );
      plantingColumnList.add( "crop_name" );
      plantingColumnList.add( "var_name" );
      plantingColumnList.add( "maturity" );
      plantingColumnList.add( "date_plant" );
      plantingColumnList.add( "date_tp" );
      plantingColumnList.add( "date_harvest" );
      plantingColumnList.add( "location" );
      plantingColumnList.add( "beds_to_plant" );
      plantingColumnList.add( "plants_needed" );
      plantingColumnList.add( "rowft_to_plant" );
      plantingColumnList.add( "rows_p_bed" );
      plantingColumnList.add( "plants_to_start" );
      plantingColumnList.add( "flat_size" );
      plantingColumnList.add( "flats_needed" );
      plantingColumnList.add( "yield_p_foot" );
      plantingColumnList.add( "total_yield" );
      plantingColumnList.add( "time_to_tp" );
      
      plantingColumnList.add( "inrow_space" );
      plantingColumnList.add( "row_space" );
      plantingColumnList.add( "yield_num_weeks" );
      plantingColumnList.add( "yield_p_week" );
      plantingColumnList.add( "crop_unit" );
      plantingColumnList.add( "crop_unit_value" );
      
      plantingColumnList.add( "groups" );
      plantingColumnList.add( "keywords" );
      plantingColumnList.add( "completed" );
      plantingColumnList.add( "other_req" );
      plantingColumnList.add( "notes" );
      plantingColumnList.add( "planter" );
      plantingColumnList.add( "planter_setting" );
      plantingColumnList.add( "mat_adjust" );
      plantingColumnList.add( "misc_adjust" );
      plantingColumnList.add( "planting_adjust" );
      plantingColumnList.add( "ds_adjust" );
      plantingColumnList.add( "season_adjust" );
      plantingColumnList.add( "successions" );
      plantingColumnList.add( "status" );
   }
   
   public ArrayList<String> getPlantingDisplayableColumns() {
      return new ArrayList<String>( getPlantingColumnList().subList( 1, getPlantingColumnList().size() ) );
   }
   
   
   private String getVarietiesColumnNames() {
      return getCropsColumnNames();
   }
   
   private String getAbbreviatedCropPlanColumnNames() {
      return getMandatoryColumnNames() + ", " + "var_name, maturity, date_plant, date_harvest, completed, location ";
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
//      rsListCache.get
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
      updateDataListeners();
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
      return getCropPlan( plan_name, getAbbreviatedCropPlanColumnNames(), sortCol, filterString );
   }
   
   public TableModel getCropPlan( String plan_name, String columns, String sortCol, String filterString ) {
      String mand = getMandatoryColumnNames();
      if ( ! columns.startsWith(mand) )
         columns = mand + ", " + columns;
      return HSQLQuerier.tableResults( 
              query.submitCalculatedCropPlanQuery( plan_name, 
                                                   getPlantingCropColumnMapping(),
                                                   columns,
                                                   sortCol,
                                                   buildFilterExpression( this.getCropPlanFilterColumnNames(),
                                                                          filterString ) ),
               plan_name );
      
//      return cachedListTableQuery( plan_name, getAbbreviatedCropPlanColumnNames(), 
//                                   null, sortCol, 
//                                   buildFilterExpression( this.getCropPlanFilterColumnNames(), filterString ));
   }

   
   /*
    * CROP AND VARIETY METHODS
    */
   
   public CPSPlanting getCommonInfoForPlantings( String plan_name, ArrayList<Integer> plantingIDs ) {
      try {
         System.out.println("Querying columns: " + getPlantingColumnList() );
         rsPlantCache = query.submitCommonInfoQuery( plan_name, getPlantingColumnList(), plantingIDs );
         
         CPSPlanting p = resultSetAsPlanting( rsPlantCache );
         p.setCommonIDs( plantingIDs );
         return p;
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return null;
      }
   }
   
   public CPSCrop getCommonInfoForCrops( ArrayList<Integer> cropIDs ) {
      try {
         ArrayList<String> columns = getCropColumnList();
         rsCropCache = query.submitCommonInfoQuery( "CROPS_VARIETIES", columns, cropIDs );
         
         CPSCrop c = resultSetAsCrop( rsCropCache );
         c.setCommonIDs( cropIDs );
         return c;
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return null;
      }
   }
   
   /* we make the assumption that we're zero-based, ResultSets are not */
   public CPSCrop getCropInfo( int id ) {
      if ( id != -1 )
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

      return new CPSCrop();
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
            
            crop.setID( rs.getInt( "id" ));
            crop.setCropName( captureString( rs.getString( "crop_name" ) ));            
            crop.setVarietyName( captureString( rs.getString( "var_name" ) ));
            crop.setBotanicalName( captureString( rs.getString( "bot_name" ) ) );
            crop.setFamilyName( captureString( rs.getString( "fam_name" ) ));
            crop.setCropDescription( captureString( rs.getString("description") ));
            
            crop.setMaturityDays( captureInt( rs.getInt( "maturity" )));
            crop.setMaturityAdjust( captureInt( rs.getInt( "mat_adjust" )));
//            crop.setSuccessions( rs.getBoolean("successions") );
            
            crop.setGroups( captureString( rs.getString( "groups" ) ));
            crop.setOtherRequirements( captureString( rs.getString( "other_req" ) ));
            crop.setKeywords( captureString( rs.getString( "keywords" ) ));
            crop.setNotes( captureString( rs.getString( "notes" ) ));

            crop.setTimeToTP( captureInt( rs.getInt( "time_to_tp" )));
            crop.setRowsPerBed( captureInt( rs.getInt( "rows_p_bed" )));
            crop.setSpaceInRow( captureInt( rs.getInt( "space_inrow" )));
            crop.setSpaceBetweenRow( captureInt( rs.getInt( "space_betrow" )));
            crop.setFlatSize( captureString( rs.getString( "flat_size" )));
            crop.setPlanter( captureString( rs.getString( "planter" )));
            crop.setPlanterSetting( captureString( rs.getString( "planter_setting" )));
            crop.setYieldPerFoot( captureFloat( rs.getFloat( "yield_p_foot" )));
            crop.setYieldNumWeeks( captureInt( rs.getInt( "yield_num_weeks" )));
            crop.setYieldPerWeek( captureInt( rs.getInt( "yield_p_week" )));
            crop.setCropYieldUnit( captureString( rs.getString( "crop_unit" )));
            crop.setCropUnitValue( captureFloat( rs.getFloat( "crop_unit_value" )));

            
            /* Now handle the data inheritance */
//            crop.setSimilarCrop( captureString( rs.getString("similar_to") ));
//   
//            CPSCrop similarCrop = getCropInfo( crop.getSimilarCrop() );            
//            crop.inheritFrom( similarCrop );
            
            /* for varieties, inherit info from their crop, too */
            if ( ! crop.getVarietyName().equals("") ) {
               CPSCrop superCrop = getCropInfo( crop.getCropName() );
               crop.inheritFrom( superCrop );            
            }
            
            
         }  catch ( SQLException e ) { e.printStackTrace(); }
      }
      
      return crop;
   }

   public void updateCrop( CPSCrop crop ) {
      HSQLDBCreator.updateCrop( con, crop );
      updateDataListeners();
   }
   
   public void updateCrops( CPSCrop changes, ArrayList<Integer> ids ) {
      HSQLDBCreator.updateCrops( con, changes, ids );
      updateDataListeners();
   }

   public CPSCrop createCrop(CPSCrop crop) {
      int newID = HSQLDBCreator.insertCrop( con, crop );
      // TODO is this really a good idea?
      updateDataListeners();
      if ( newID == -1 )
         return new CPSCrop();
      else
         return getCropInfo( newID );
   }
   
   public void deleteCrop( int cropID ) {
       HSQLDBCreator.deleteRecord(con, "CROPS_VARIETIES", cropID);   
       updateDataListeners();
   }
   
   public void deletePlanting( String planName, int plantingID ) {
       HSQLDBCreator.deleteRecord( con, planName, plantingID );
       updateDataListeners();
   }
   
   public CPSPlanting createPlanting( String planName, CPSPlanting planting ) {
      int newID = HSQLDBCreator.insertPlanting( con, planName, planting );
      updateDataListeners();
      if ( newID == -1 )
         return new CPSPlanting();
      else
         return getPlanting( planName, newID );
   }

   
   /* we make the assumption that we're zero-based, ResultSets are not */
   public CPSPlanting getPlanting( String planName, int id ) {
      
      if ( id != -1 )
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
      
      return new CPSPlanting();
   }

   public void updatePlanting( String planName, CPSPlanting changes ) {
//      CPSCrop c = getVarietyInfo( planting.getCropName(), planting.getVarietyName() );
      ArrayList<Integer> changedID = new ArrayList();
      changedID.add( new Integer( changes.getID() ));
//      HSQLDBCreator.updatePlanting( con, planName, planting, c.getID() );
      updatePlantings( planName, changes, changedID );
      updateDataListeners();
   }
   
   public void updatePlantings( String planName, CPSPlanting changes, ArrayList<Integer> changedIDs ) {
      ArrayList<Integer> cropIDs = new ArrayList();
      for ( Integer i : changedIDs ) {
         CPSPlanting p = getPlanting( planName, i.intValue() );
         if ( changes.getCropNameState().isValid() )
            p.setCropName( changes.getCropName() );
         if ( changes.getVarietyNameState().isValid() )
            p.setVarietyName( changes.getVarietyName() );
         
         int cropID = getVarietyInfo( p.getCropName(), p.getVarietyName() ).getID();
         
         if ( cropID == -1 )
            cropID = getCropInfo( p.getCropName() ).getID();
         
         // TODO error if cropID == -1 again
         
         cropIDs.add( new Integer( cropID  ));
      }
      
      HSQLDBCreator.updatePlantings( con, planName, changes, changedIDs, cropIDs );
      updateDataListeners();
   }
   
   private CPSPlanting resultSetAsPlanting( ResultSet rs ) throws SQLException {
      
      CPSPlanting p = new CPSPlanting();
      
      // move to the first (and only) row
      if ( rs.next() ) {
         try {

            // Not yet implemented:
            // PROP_CROP_ID, PROP_STATUS, PROP_COMPLETED     

            p.setID( rs.getInt( "id" ));
            p.setCropName( captureString( rs.getString( "crop_name" ) ));            
            p.setVarietyName( captureString( rs.getString( "var_name" ) ));

            p.setLocation( captureString( rs.getString( "location" )));
           
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
            
            p.setMatAdjust( captureInt( rs.getInt( "mat_adjust" )));
            p.setPlantingAdjust( captureInt( rs.getInt( "planting_adjust" )));
            p.setMiscAdjust( captureInt( rs.getInt( "misc_adjust" ) ) );
 
            p.setTimeToTP( captureInt( rs.getInt( "time_to_tp" )));
            p.setRowsPerBed( captureInt( rs.getInt( "rows_p_bed" )));
            p.setInRowSpacing( captureInt( rs.getInt( "inrow_space" ) ) );
            p.setRowSpacing( captureInt( rs.getInt( "row_space" )));
            
            p.setFlatSize( captureString( rs.getString( "flat_size" )));
            p.setPlanter( captureString( rs.getString( "planter" )));
            p.setPlanterSetting( captureString( rs.getString( "planter_setting" )));

            p.setYieldPerFoot( captureFloat( rs.getFloat( "yield_p_foot" ) ) );
            p.setTotalYield( captureFloat( rs.getFloat( "total_yield" )));
            p.setYieldNumWeeks( captureInt( rs.getInt( "yield_num_weeks" )));
            p.setYieldPerWeek( captureFloat( rs.getFloat( "yield_p_week" )));
            p.setCropYieldUnit( captureString( rs.getString( "crop_unit" )));
            p.setCropYieldUnitValue( captureFloat( rs.getFloat( "crop_unit_value" )));

            p.setCompleted( rs.getBoolean( "completed" ) );
            
            /* handle data inheritance */
            p.inheritFrom( getCropInfo( rs.getInt( "crop_id" ) ));
//            p.inheritFrom( getCropInfo( p.getCropName() ));
            
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
         if ( ((String) o).equalsIgnoreCase( "null" ) || ((String) o).equals( "" ) )
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
   
   public static float captureFloat( float f ) {
      if ( f <= 0 )
         return (float) -1.0;
      else
         return f;
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
   
   /**
    * Create a comma delimited string of integers from an ArrayList of Integers.
    * @param ids ArrayList<Integer> to convert
    * @return comma delimited string of integers
    */
   public static String intListToIDString( ArrayList<Integer> ids ) {
      String idString = "";
      for ( Integer i : ids )
         idString += i.toString() + ", ";
      idString = idString.substring( 0, idString.lastIndexOf( ", " ));
      return idString;
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
