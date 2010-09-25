/* HSQLDB.java - Created: January 16, 2007
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

import CPS.Data.*;
import CPS.Module.CPSConfigurable;
import CPS.Module.CPSDataModelSQL;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSGlobalSettings;
import CPS.Module.CPSWizardPage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

/**
 *
 * @author Clayton
 */
public class HSQLDB extends CPSDataModelSQL implements CPSConfigurable {
   
    private static final boolean DEBUG = true;
    
   private Connection con = null;
   private String hsqlDriver = "org.hsqldb.jdbcDriver";
   private String dbDir = System.getProperty("user.dir");
   private String dbFile = "CPSdb";
   
   private ResultSet rsListCache = null;
   private ResultSet rsCropCache = null;
   private ResultSet rsPlantCache = null;
   public String state = null;
   
   private HSQLColumnMap columnMap;
   private HSQLQuerier query;
   
   HSQLSettings localSettings;
   
   public HSQLDB() {

       setModuleName( "HSQLDB" );
       setModuleDescription( "A full featured DataModel based on HSQLDB.");
       setModuleVersion( GLOBAL_DEVEL_VERSION );
               
       localSettings = new HSQLSettings();
       
//       init();
   }

   @Override
   public int init () {

      if ( true || localSettings.getUseGlobalDir() )
         dbDir = CPSGlobalSettings.getDataOutputDir();
      else
         dbDir = localSettings.getCustomOutDir();

      con = HSQLConnect.getConnection( dbDir, dbFile, hsqlDriver );
      boolean newDB = false;
      
      if ( con == null ) { // db DNE
         con = HSQLConnect.createDB( dbDir, dbFile, getModuleVersionAsLongInt() );
         newDB = true;
      }
      /* only needed when we're using a server mode db */
      else if ( HSQLConnect.dbIsEmpty( con ) ) {
         HSQLDBCreator.createTables( con, getModuleVersionAsLongInt() );
         newDB = true;
      }
         
      HSQLUpdate.updateDB( con, getModuleVersionAsLongInt() );
      
      query = new HSQLQuerier( con );
      columnMap = new HSQLColumnMap();

//      if ( false && newDB ) {
//         this.importCropsAndVarieties( HSQLDBPopulator.loadDefaultCropList( dbDir )
//                                                      .getCropsAndVarietiesAsList() );
//      } 

      return 0;
   }
   
   public synchronized ArrayList<String> getFlatSizeList( String planName ) {
       return getDistinctValsFromCVAndPlan( planName, CPSDataModelConstants.PROP_FLAT_SIZE );
//       return getDistinctValsFromCVAndPlan( planName, "flat_size" );
   }
   
   public synchronized ArrayList<String> getFieldNameList( String planName ) {
       // TODO should this query all crop plans, or just one.  Just one for now.
       return HSQLQuerier.getDistinctValuesForColumn( con, planName, propNameFromPropNum( CPSDataModelConstants.RECORD_TYPE_PLANTING,
                                                                                          CPSDataModelConstants.PROP_LOCATION ));
//       return HSQLQuerier.getDistinctValuesForColumn( con, planName,  "location" );
   }
   
   public synchronized ArrayList<String> getCropNameList() {
      return HSQLQuerier.getDistinctValuesForColumn( con, "CROPS_VARIETIES", propNameFromPropNum( CPSDataModelConstants.RECORD_TYPE_CROP,
                                                                                                  CPSDataModelConstants.PROP_CROP_NAME ));
//      return HSQLQuerier.getDistinctValuesForColumn( con, "CROPS_VARIETIES", "crop_name" );
   }
   
   public synchronized ArrayList<String> getVarietyNameList( String crop_name, String cropPlan ) {
      return getDistinctValsFromCVAndPlan( cropPlan, CPSDataModelConstants.PROP_VAR_NAME );
//      return getDistinctValsFromCVAndPlan( cropPlan, "var_name" );
   }
   
   private synchronized ArrayList<String> getDistinctValsFromCVAndPlan( String planName, int propNum ) {
       ArrayList<String> l = new ArrayList<String>();
       Set set = new HashSet();
       
       if ( planName != null )
           l.addAll( HSQLQuerier.getDistinctValuesForColumn( con,
                                                             planName,
                                                             propNameFromPropNum( CPSDataModelConstants.RECORD_TYPE_PLANTING,
                                                                                  propNum ) ) );
       l.addAll( HSQLQuerier.getDistinctValuesForColumn( con,
                                                         "CROPS_VARIETIES",
                                                         propNameFromPropNum( CPSDataModelConstants.RECORD_TYPE_CROP,
                                                                              propNum ) ) );
       // list now contains all values, possibly some duplicates
       // this ensures that the values are unique
       set.addAll ( l  );
       l.clear();
       l.addAll( set );
      
       return l;
       
//       ArrayList<String> tables = new ArrayList<String>();
//       if ( planName != null )
//           tables.add( planName );
//       tables.add( "CROPS_VARIETIES" );
//       
//       return HSQLQuerier.getDistinctValuesForColumn( con, tables, column );
   
   }
   
   public synchronized ArrayList<String> getFamilyNameList() {
      return HSQLQuerier.getDistinctValuesForColumn( con, "CROPS_VARIETIES", "fam_name" );
   }
   
   public synchronized ArrayList<String> getListOfCropPlans() {
      
       return HSQLQuerier.getDistinctValuesForColumn( con, "CROP_PLANS", "plan_name" );

   }
   
   /* COLUMN NAMES */

   private String getMandatoryColumnNames() {
      return "id, crop_name";
   }
   private String getAbbreviatedCropVarColumnNames( boolean varieties ) {
      return listToCSVString( columnMap.getDefaultCropColumnList() );
//       return getMandatoryColumnNames() + ", " +
//             ( varieties ? "var_name, " : "" ) + "fam_name, maturity";
   }
   private ArrayList<String> getCropVarFilterColumnNames() {
      return columnMap.getCropFilterColumnNames();
//       return getAbbreviatedCropVarColumnNames( true ) + ", keywords, groups";
   }
   
   private String getCropsColumnNames() {
      return  listToCSVString( columnMap.getCropColumnList() );
//       return "*";
   }
   
   private String getVarietiesColumnNames() {
      return getCropsColumnNames();
   }
   
   
   private ArrayList<String> getCropColumnList() {
       return columnMap.getCropColumns();
   }

   public ArrayList<Integer> getCropDefaultProperties() {
      return columnMap.getDefaultCropPropertyList();
   }

   public ArrayList<Integer> getCropDisplayableProperties() {
      return columnMap.getCropPropertyList();
   }
   
   public ArrayList<String> getCropDefaultPropertyNames() {
      return columnMap.getDefaultCropColumnList();
   }
   
   public ArrayList<String> getCropDisplayablePropertyNames() {
      return columnMap.getCropDisplayableColumns();
   }
   public ArrayList<String[]> getCropPrettyNames() {
      return columnMap.getCropPrettyNameMapping(); 
   }
   
   private ArrayList<String[]> getCropInheritanceColumnMapping() {
       return columnMap.getCropInheritanceColumnMapping();
   }
   
   private ArrayList<String[]> getPlantingCropColumnMapping() {
      return columnMap.getPlantingToCropColumnMapping();
//       return plantingCropColumnMapping;
   }

   public ArrayList<Integer> getPlantingDefaultProperties() {
      return columnMap.getDefaultPlantingPropertyList();
   }

   public ArrayList<Integer> getPlantingDisplayableProperties() {
      return columnMap.getPlantingPropertyList();
   }
   
   public ArrayList<String> getPlantingDefaultPropertyNames() {
      return columnMap.getDefaultPlantingColumnList();
   }
   
   public ArrayList<String> getPlantingDisplayablePropertyNames() {
      return columnMap.getPlantingDisplayableColumns();
   }
   
   public ArrayList<String[]> getPlantingPrettyNames() {
      return columnMap.getPlantingPrettyNameMapping(); 
   }
   
   public ArrayList<String[]> getPlantingShortNames() {
      return columnMap.getPlantingShortNameMapping(); 
   }
   
   private String getAbbreviatedCropPlanColumnNames() {
       return listToCSVString( columnMap.getDefaultPlantingColumnList() );
   }
   private ArrayList<String> getCropPlanFilterColumnNames() {
       return columnMap.getPlantingFilterColumnNames();
   }
   
   private String includeMandatoryColumns( String columns ) {
       if ( columns.indexOf( "crop_name" ) == -1 )
           columns = "crop_name, " + columns;
       if ( columns.indexOf( "id" ) == -1 )
           columns = "id, " + columns;
       return columns;
   }
   
   /* END COLUMN NAMES */

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
      return HSQLQuerier.tableResults( this, rsListCache );
   }
   
   /*
    * CROP LIST METHODS
    */

   public TableModel getCropTable() { 
      return getCropTable( null, null );
   }
   public TableModel getCropTable( String sortCol ) { 
      return getCropTable( sortCol, null );
   }   
   public TableModel getCropTable( int sortProp ) { 
      return getCropTable( sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp) );
   }
   public TableModel getCropTable( String sortCol, CPSComplexFilter filter ) { 
      return getCropTable( getCropsColumnNames(), sortCol, filter );
   }
   public TableModel getCropTable( int sortProp, CPSComplexFilter filter ) { 
      return getCropTable( sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ), filter );
   }
   public TableModel getCropTable( String columns, String sortCol, CPSComplexFilter filter ) { 
      return cachedListTableQuery( "CROPS_VARIETIES", includeMandatoryColumns(columns), "var_name IS NULL", sortCol,
                                   buildGeneralFilterExpression( getCropVarFilterColumnNames(), filter ));
   }
   public TableModel getCropTable( ArrayList<Integer> properties , int sortProp, CPSComplexFilter filter ) { 
      return getCropTable( propertyNumListToColumnString( CPSDataModelConstants.RECORD_TYPE_CROP, properties),
                           sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ),
                           filter );
   }
   
   public TableModel getVarietyTable() {
      return getVarietyTable( null, null );
   }
   public TableModel getVarietyTable( String sortCol ) {
      return getVarietyTable( sortCol, null );
   }
   public TableModel getVarietyTable( int sortProp ) { 
      return getVarietyTable( sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ));
   }
   public TableModel getVarietyTable( String sortCol, CPSComplexFilter filter ) {
       return getVarietyTable( getVarietiesColumnNames(), sortCol, filter );
   }
   public TableModel getVarietyTable( int sortProp, CPSComplexFilter filter ) { 
      return getVarietyTable( sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ), filter );
   }
   public TableModel getVarietyTable( String columns, String sortCol, CPSComplexFilter filter ) {
      return cachedListTableQuery( "CROPS_VARIETIES", 
                                   includeMandatoryColumns(columns),
                                   "var_name IS NOT NULL", 
                                   sortCol,
                                   buildGeneralFilterExpression( getCropVarFilterColumnNames(), filter ));
   }
   public TableModel getVarietyTable( ArrayList<Integer> properties, int sortProp, CPSComplexFilter filter ) { 
      return getVarietyTable( propertyNumListToColumnString( CPSDataModelConstants.RECORD_TYPE_CROP, properties),
                              sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ),
                              filter );
   }
   
   public TableModel getCropAndVarietyTable() {
      return getCropAndVarietyTable( null, null );
   }
   public TableModel getCropAndVarietyTable( String sortCol ) {
      return getCropAndVarietyTable( sortCol, null );
   }
   public TableModel getCropAndVarietyTable( int sortProp ) { 
      return getCropAndVarietyTable( sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ) );
   }
   public TableModel getCropAndVarietyTable( String sortCol, CPSComplexFilter filter ) {
       return getCropAndVarietyTable( getCropsColumnNames(), sortCol, filter );
   }
   public TableModel getCropAndVarietyTable( int sortProp, CPSComplexFilter filter ) { 
      return getCropAndVarietyTable( sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ), filter );
   }
   public TableModel getCropAndVarietyTable( String columns, String sortCol, CPSComplexFilter filter ) {
      return HSQLQuerier.tableResults( this, 
                                       query.submitCalculatedCropAndVarQuery( getCropInheritanceColumnMapping(),
                                                                              includeMandatoryColumns( columns ),
                                                                              sortCol,
                                                                              buildGeneralFilterExpression( getCropVarFilterColumnNames(),
                                                                                                            filter ) ),
                                       "CROPS_VARIETIES" );
//      return cachedListTableQuery( "CROPS_VARIETIES", includeMandatoryColumns(columns), null, sortCol,
//                                   buildGeneralFilterExpression( getCropVarFilterColumnNames(), filter ) );
   }
   public TableModel getCropAndVarietyTable( ArrayList<Integer> properties, int sortProp, CPSComplexFilter filter ) { 
      return getCropAndVarietyTable( propertyNumListToColumnString( CPSDataModelConstants.RECORD_TYPE_CROP, properties),
                                     sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_CROP, sortProp ),
                                     filter );
   }
   
   /*
    * CROP PLAN METHODS
    */
   public void createCropPlan( String planName, int year, String desc ) {
      HSQLDBCreator.createCropPlan( con, planName, year, desc );
      updateDataListeners();
   }
   
   public void updateCropPlan( String planName, int year, String desc ) {
      HSQLDBCreator.updateCropPlan( con, planName, year, desc );
      // TODO commented out because I can't imagine a time (yet) when 
      // we'll need to update when a plan changes metadata
      // updateDataListeners();
   }
   
   public void deleteCropPlan( String planName ) {
      HSQLDBCreator.deleteCropPlan( con, planName );
      updateDataListeners();
   }
   
   public int getCropPlanYear( String planName ) {
      return HSQLQuerier.getCropPlanYear( con, planName );
   }
   
   public String getCropPlanDescription( String planName ) {
      return HSQLQuerier.getCropPlanDescription( con, planName );
   }
   
   public TableModel getCropPlan(String plan_name) {
      return getCropPlan( plan_name, null, null );
   }
   
   public TableModel getCropPlan( String plan_name, String sortCol ) {
      return getCropPlan( plan_name, sortCol, null );
   }
   public TableModel getCropPlan( String plan_name, int sortProp ) {
      return getCropPlan( plan_name, sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_PLANTING, sortProp ) );
   }

   public TableModel getCropPlan( String plan_name, String sortCol, CPSComplexPlantingFilter filter ) {
      return getCropPlan( plan_name, getAbbreviatedCropPlanColumnNames(), sortCol, filter );
   }
   public TableModel getCropPlan( String plan_name, int sortProp, CPSComplexPlantingFilter filter ) {
      return getCropPlan( plan_name, 
                          sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_PLANTING, sortProp ),
                          filter );
   }
   
   public TableModel getCropPlan( String plan_name, String columns, String sortCol, CPSComplexPlantingFilter filter ) {
      return HSQLQuerier.tableResults( this, 
              query.submitCalculatedCropPlanQuery( plan_name, 
                                                   getPlantingCropColumnMapping(),
                                                   getCropInheritanceColumnMapping(),
                                                   includeMandatoryColumns(columns),
                                                   sortCol,
                                                   buildComplexFilterExpression( this.getCropPlanFilterColumnNames(),
                                                                                 filter ) ),
               plan_name );
      
   }
   public TableModel getCropPlan( String plan_name, ArrayList<Integer> properties, int sortProp, CPSComplexPlantingFilter filter ) {
      return getCropPlan( plan_name,
                          propertyNumListToColumnString( CPSDataModelConstants.RECORD_TYPE_PLANTING, properties),
                          sortColumnFromPropertyNum( CPSDataModelConstants.RECORD_TYPE_PLANTING, sortProp ),
                          filter );
   }

      
   public CPSPlanting getSumsForCropPlan( String plan_name, CPSComplexPlantingFilter filter ) {
       ArrayList<String> colsToSum = new ArrayList<String>( 4 );
       colsToSum.add( "beds_to_plant" );
       colsToSum.add( "rowft_to_plant" );
       colsToSum.add( "plants_needed" );
       colsToSum.add( "plants_to_start" );
       colsToSum.add( "flats_needed" ); 
       colsToSum.add( "total_yield" );
       
       try {
           return resultSetAsPlanting( query.submitSummedCropPlanQuery( plan_name,
                                                                        getPlantingCropColumnMapping(),
                                                                        getCropInheritanceColumnMapping(),
                                                                        colsToSum,
                                                                        buildComplexFilterExpression( this.getCropPlanFilterColumnNames(),
                                                                                                      filter )));
       }
       catch ( SQLException e ) {
           e.printStackTrace();
           return new CPSPlanting();
       }
   }



   public TableModel getPlanSummary( String plan_name ) {
      return getPlanSummary( plan_name, false );
   }

   public TableModel getPlanSummary( String plan_name, boolean just_completed ) {

      return HSQLQuerier.tableResults( this, query.getPlanSummary( plan_name,
                                                                   getPlantingCropColumnMapping(),
                                                                   getCropInheritanceColumnMapping(), just_completed ), plan_name );

   }


   
   /*
    * CROP AND VARIETY METHODS
    */
   
   public CPSPlanting getCommonInfoForPlantings( String plan_name, ArrayList<Integer> plantingIDs ) {
      try {
         rsPlantCache = query.submitCommonInfoQuery( plan_name, columnMap.getPlantingColumns(), plantingIDs );
         
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

// Not yet implemented (11/29/08)
//        l.add( new ColumnStruct( "tp_pot_up",      CPSDataModelConstants.PROP_TP_POT_UP,       "tp_pot_up",       null, false, true,  true,  false, false, "Pot up? (TP)" ));
//        l.add( new ColumnStruct( "tp_pot_up_notes",CPSDataModelConstants.PROP_TP_POT_UP_NOTES, "tp_pot_up_notes", null, false, true,  true,  false, false, "Plug Flat Size or Capacity" ));

      
      // move to the first (and only) row
      if ( rs.next() ) {
         try {
            
            crop.setID( rs.getInt( "id" ));
            crop.setCropName( captureString( rs.getString( "crop_name" ) ));            
            crop.setVarietyName( captureString( rs.getString( "var_name" ) ));
            crop.setFamilyName( captureString( rs.getString( "fam_name" ) ));
         
            crop.setMaturityDays( getInt( rs, "maturity" ) );

         } catch ( SQLException ignore ) { HSQLDB.debug( "HSQLDB", "WARNING: " + ignore.getMessage() ); }
         
         try {
            
            
            // we have to go through these acrobatics because these fields
            // are inheritable and can be null
            boolean b = rs.getBoolean( "direct_seed" );
            if ( rs.wasNull() )
                 crop.setDirectSeeded( (Boolean) null );
            else
                 crop.setDirectSeeded( b );

            crop.setDSMaturityAdjust( getInt( rs, "ds_mat_adjust" ));
            crop.setDSRowsPerBed( getInt( rs, "ds_rows_p_bed" ) );
            crop.setDSSpaceBetweenRow( getInt( rs, "ds_row_space" ) );
            crop.setDSPlantNotes( captureString( rs.getString( "ds_plant_notes" )));
            
          } catch ( SQLException ignore ) { HSQLDB.debug( "HSQLDB", "WARNING: " + ignore.getMessage() ); }
         
         try {

            boolean b = rs.getBoolean( "transplant" );
            if ( rs.wasNull() )
               crop.setTransplanted( (Boolean) null );
            else
               crop.setTransplanted( b );
            
            crop.setTPMaturityAdjust( getInt( rs, "tp_mat_adjust" ));
            crop.setTPRowsPerBed( getInt( rs, "tp_rows_p_bed" ));
            crop.setTPSpaceInRow( getInt( rs, "tp_inrow_space" ));
            crop.setTPSpaceBetweenRow( getInt( rs, "tp_row_space" ));
            crop.setTPFlatSize( captureString( rs.getString( "tp_flat_size" )));
            crop.setTPTimeInGH( getInt( rs, "tp_time_in_gh" ));
            crop.setTPPlantNotes( captureString( rs.getString( "tp_plant_notes" )));

         } catch ( SQLException ignore ) { HSQLDB.debug( "HSQLDB", "WARNING: " + ignore.getMessage() ); }
         
         try {
            
            crop.setYieldPerFoot( getFloat( rs, "yield_p_foot" ));
            crop.setYieldNumWeeks( getInt( rs, "yield_num_weeks" ));
            crop.setYieldPerWeek( getInt( rs, "yield_p_week" ));
            crop.setCropYieldUnit( captureString( rs.getString( "crop_unit" )));
            crop.setCropUnitValue( getFloat( rs, "crop_unit_value" ));

         } catch ( SQLException ignore ) { HSQLDB.debug( "HSQLDB", "WARNING: " + ignore.getMessage() ); }
         
         try {

            boolean b = rs.getBoolean( "frost_hardy" );
            if ( rs.wasNull() )
                 crop.setFrostHardy( (Boolean) null );
            else
                 crop.setFrostHardy( b );

            crop.setBotanicalName( captureString( rs.getString( "bot_name" ) ) );
            crop.setCropDescription( captureString( rs.getString("description") ));
            crop.setGroups( captureString( rs.getString( "groups" ) ));
            crop.setOtherRequirements( captureString( rs.getString( "other_req" ) ));
            crop.setKeywords( captureString( rs.getString( "keywords" ) ));
            crop.setNotes( captureString( rs.getString( "notes" ) ));
            
          } catch ( SQLException ignore ) { HSQLDB.debug( "HSQLDB", "WARNING: " + ignore.getMessage() ); }
         
         
          /* for varieties, inherit info from their crop, too */
          if ( ! crop.getVarietyName().equals( "" ) ) {
              CPSCrop superCrop = getCropInfo( crop.getCropName() );
              crop.inheritFrom( superCrop );            
          }
            
      }
      
      return crop;
   }

   public void updateCrop( CPSCrop crop ) {
      HSQLDBCreator.updateCrop( con, columnMap, crop );
      updateDataListeners();
   }
   
   public void updateCrops( CPSCrop changes, ArrayList<Integer> ids ) {
      HSQLDBCreator.updateCrops( con, columnMap, changes, ids );
      updateDataListeners();
   }

   public CPSCrop createCrop(CPSCrop crop) {
      int newID = HSQLDBCreator.insertCrop( con, columnMap, crop );
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
   
   // also called with "empty" or new CPSPlantings, so should handle case where
   // the "cropID" is not valid
   public CPSPlanting createPlanting( String planName, CPSPlanting planting ) {
      int cropID = getVarietyInfo( planting.getCropName(), planting.getVarietyName() ).getID();
      int newID = HSQLDBCreator.insertPlanting( con, columnMap, planName, planting, cropID );
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
//      updateDataListeners();
   }
   
   public void updatePlantings( String planName, CPSPlanting changes, ArrayList<Integer> changedIDs ) {

      debug( "saving changes to plan " + planName + ": " + changes.toString() );

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
      
      HSQLDBCreator.updatePlantings( con, columnMap, planName, changes, changedIDs, cropIDs );
      updateDataListeners();
   }
   
   private CPSPlanting resultSetAsPlanting( ResultSet rs ) throws SQLException {
      return resultSetAsPlanting( rs, false );
   }
   private CPSPlanting resultSetAsPlanting( ResultSet rs, boolean summedPlanting ) throws SQLException {
      
      CPSPlanting p = new CPSPlanting();

//      debug( "converting result to planting:\n" + toXML( rs ) );
//      rs.beforeFirst();

      // move to the first (and only) row
      if ( rs.next() ) {
         
          // sometimes it's OK for some columns not to exist.  Instead of surrounding each "get..."
          // with a try clause, we'll group this into logical components
         try {

            p.setID( rs.getInt( "id" ));
            p.setCropName( captureString( rs.getString( "crop_name" ) ));            
            p.setVarietyName( captureString( rs.getString( "var_name" ) ));
            p.setLocation( captureString( rs.getString( "location" )));

          } catch ( SQLException ignore ) { 
            if ( !summedPlanting )
                HSQLDB.debug( "HSQLDB", ignore.getMessage() ); 
          }
         
         try {           

            p.setDateToPlantPlanned( captureDate( rs.getDate( "date_plant_plan" )));
            p.setDateToTPPlanned( captureDate( rs.getDate( "date_tp_plan" )));
            p.setDateToHarvestPlanned( captureDate( rs.getDate( "date_harvest_plan" )));

            p.setDateToPlantActual( captureDate( rs.getDate( "date_plant_actual" )));
            p.setDateToTPActual( captureDate( rs.getDate( "date_tp_actual" )));
            p.setDateToHarvestActual( captureDate( rs.getDate( "date_harvest_actual" )));

            p.setDonePlanting( rs.getBoolean( "done_plant" ));
            p.setDoneTP( rs.getBoolean( "done_TP" ));
            p.setDoneHarvest( rs.getBoolean( "done_harvest" ));
            p.setIgnore( rs.getBoolean( "ignore" ));

          } catch ( SQLException ignore ) { 
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }

         try {

            // record DS only info

            p.setDirectSeeded( true );

            p.setMatAdjust( getInt( rs, "ds_mat_adjust" ));
            p.setRowsPerBed( getInt( rs, "ds_rows_p_bed" ));
            p.setRowSpacing( getInt( rs, "ds_row_space" ));
            p.setPlantingNotesInherited( captureString( rs.getString( "ds_crop_notes" )));

         } catch ( SQLException ignore ) {
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }

         try {

            // record TP only info

            p.setDirectSeeded( false );

            p.setMatAdjust( getInt( rs, "tp_mat_adjust" ));
            p.setRowsPerBed( getInt( rs, "tp_rows_p_bed" ));
            p.setInRowSpacing( getInt( rs, "inrow_space" ) );
            p.setRowSpacing( getInt( rs, "tp_row_space" ));
            p.setFlatSize( captureString( rs.getString( "flat_size" )));
            p.setTimeToTP( getInt( rs, "time_to_tp" ));
            p.setPlantingNotesInherited( captureString( rs.getString( "tp_crop_notes" )));

         } catch ( SQLException ignore ) {
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }

         try {

            // now start over and set direct_seed to what it "should" be
            boolean b = rs.getBoolean( "direct_seed" );
            if ( rs.wasNull() )
                 p.setDirectSeeded( (Boolean) null );
            else
                 p.setDirectSeeded( b );

            p.setMaturityDays( getInt( rs, "maturity") );
            p.setPlantingNotes( captureString( rs.getString( "plant_notes_spec" )));

         } catch ( SQLException ignore ) {
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }

         try {

            // record "general" info that might be DS or TP, based on whether planting is DS or TP

            p.setMatAdjust( getInt( rs, "mat_adjust" ));
            p.setRowsPerBed( getInt( rs, "rows_p_bed" ));
            p.setRowSpacing( getInt( rs, "row_space" ));
            p.setPlantingNotesInherited( captureString( rs.getString( "planting_notes" )));

         } catch ( SQLException ignore ) {
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }

         try {
             
             // These are all used for the "summed" plantings
            p.setBedsToPlant( getFloat( rs, "beds_to_plant") );
            p.setPlantsNeeded( getInt( rs, "plants_needed") );
            p.setPlantsToStart( getInt( rs, "plants_to_start") );
            p.setRowFtToPlant( getInt( rs, "rowft_to_plant") );
            p.setFlatsNeeded( getFloat( rs, "flats_needed") );
            p.setTotalYield( getFloat( rs, "total_yield" ));
            
          } catch ( SQLException ignore ) { 
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }
         
         
         try {

            p.setYieldPerFoot( getFloat( rs, "yield_p_foot" ) ) ;
            p.setYieldNumWeeks( getInt( rs, "yield_num_weeks" ));
            p.setYieldPerWeek( getFloat( rs, "yield_p_week" ));
            p.setCropYieldUnit( captureString( rs.getString( "crop_unit" )));
            p.setCropYieldUnitValue( getFloat( rs, "crop_unit_value" ));

            boolean b = rs.getBoolean( "frost_hardy" );
            if ( rs.wasNull() )
                 p.setFrostHardy( (Boolean) null );
            else
                 p.setFrostHardy( b );

            p.setGroups( captureString( rs.getString( "groups" ) ));
            p.setOtherRequirements( captureString( rs.getString( "other_req" ) ));
            p.setKeywords( captureString( rs.getString( "keywords" ) ));
            p.setNotes( captureString( rs.getString( "notes" ) ));
          } catch ( SQLException ignore ) { 
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }
         
         try {            
            p.setCustomField1( rs.getString( "custom1" ));
            p.setCustomField2( rs.getString( "custom2" ));
            p.setCustomField3( rs.getString( "custom3" ));
            p.setCustomField4( rs.getString( "custom4" ));
            p.setCustomField5( rs.getString( "custom5" ));
          } catch ( SQLException ignore ) { 
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }
         
         try {
            
            /* handle data inheritance */
            // we have to call a get... method before we can call wasNull
            int cid = rs.getInt( "crop_id" );
            if ( ! rs.wasNull() )
                 p.inheritFrom( getCropInfo( cid ) );
            
//          } catch ( SQLException ignore ) {}
          } catch ( SQLException ignore ) { 
            if ( !summedPlanting )
               HSQLDB.debug( "HSQLDB", ignore.getMessage() );
         }
         
      }
      
      p.finishUp();

      HSQLDB.debug( "HSQLDB", "Returning planting:\n" + p.toString() );
      return p;
   }

   public ArrayList<CPSPlanting> getCropPlanAsList( String planName ) {
       
       ArrayList<CPSPlanting> l = new ArrayList<CPSPlanting>();
       
       // this is excessive but functional; might be better/simple to create a
       // custom query that just returns the id column as a list or something
       TableModel tm = getCropPlan( planName );
       
       for ( int row = 0; row < tm.getRowCount(); row++ ) {
           // the results will contain the crop_id, which the HSQLTableModel will
           // try to hide, so we have to "trick" it into actually giving it to us
           l.add( this.getPlanting( planName, ((Integer) tm.getValueAt( row, -1 ) ).intValue() ) );
       }
      
       return l;
   }
   
   public ArrayList<CPSCrop> getCropsAndVarietiesAsList() { 
   
       ArrayList<CPSCrop> l = new ArrayList<CPSCrop>();
        
       TableModel tm = getCropAndVarietyTable();
       
       for ( int row = 0; row < tm.getRowCount(); row++ ) {
           // the results will contain the crop_id, which the HSQLTableModel will
           // try to hide, so we have to "trick" it into actually giving it to us
           l.add( this.getCropInfo( ((Integer) tm.getValueAt( row, -1 ) ).intValue() ) );
       }
      
       return l;

   }
   
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
         else {
            String val = o.toString();
            val = val.replaceAll( "'", "''" );
//            val = val.replaceAll( "\"", "\"\"" );
            return "'" + val + "'";
         }
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

   public static String escapeTableName( String t ) {       
       // if the "table" is an embedded select statement (enclosed in "()")
       // then don't quote it
       if ( t.trim().matches( "\\(.*\\)" ))
           return t;
       else {
           // if the string contains a quotation mark, escape all quotation marks
           if ( t.matches( ".*\".*" ) )
               t = t.replaceAll( "\\\"", "\"\"" );
           return "\"" + t + "\"";
       }
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
   
   public static float getFloat( ResultSet rs, String columnName ) throws SQLException {
       
       float f = rs.getFloat( columnName );
       
       if ( rs.wasNull() )
           f = -1f;
       return captureFloat( f );
   }
   
   public static float captureFloat( float f ) {
//      if ( f <= 0 )
      if ( f == -1 )
         return (float) -1.0;
      else
         return f;
   }
   
   
   public static int getInt( ResultSet rs, String columnName ) throws SQLException {
//       captureInt( rs.getInt( "time_to_tp" ) );
       int i = rs.getInt( columnName );
       
       if ( rs.wasNull() )
           i = -1;
       return captureInt( i );
   }
   
   public static int captureInt( int i ) {
//      if ( i <= 0 )
      if ( i == -1 )
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
   
   public static String listToCSVString( ArrayList l ) {
       String s = "";
       for ( Object o : l )
           s += o.toString() + ", ";
       s = s.substring( 0, s.lastIndexOf( ", " ));
       return s;
   }

   
   private String sortColumnFromPropertyNum( int recordType, int prop ) {
      String sortCol = " asc";
      
      if ( prop < 0 ) {
         sortCol = " desc";
         prop = -1 * prop;
      }
              
      if      ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
         sortCol = columnMap.getCropColumnNameForProperty(prop) + sortCol;
      else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
         sortCol = columnMap.getPlantingColumnNameForProperty(prop) + sortCol;
      else
         sortCol = "";
      
      return sortCol;
   }
   
   public String propNameFromPropNum( int recordType, int propertyNum ) {
      if      ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
         return columnMap.getCropColumnNameForProperty( propertyNum );
      else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
         return columnMap.getPlantingColumnNameForProperty( propertyNum );
      else
         return "UnknownProperty";
   }

   public int propNumFromPropName( int recordType, String propertyName ) {
      if      ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
         return columnMap.getCropPropertyNumFromName( propertyName );
      else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
         return columnMap.getPlantingPropertyNumFromName( propertyName );
      else
         return 0;
   }

   
   
   public String propertyNumListToColumnString( int recordType, ArrayList<Integer> props ) {
      String cols = "";
      for ( Integer prop : props )
         if ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
            cols += columnMap.getCropColumnNameForProperty( prop ) + ", ";
         else if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
            cols += columnMap.getPlantingColumnNameForProperty( prop ) + ", ";
      cols = cols.substring( 0, cols.lastIndexOf( ", " ));
      return cols;
   }
   
   
   /* based upon a complex filter including various boolean values and a
    * space delimited uesr input, create an 
    * SQL expression that can be used as a WHERE clause.  Does not include
    * the WHERE keyword
    */
   private String buildComplexFilterExpression( ArrayList<String> colList, CPSComplexPlantingFilter filter ) {
       
       String filterString = buildGeneralFilterExpression( colList, filter );
       
       if ( filterString == null )
           filterString = "";
       
       
       if ( filter != null && filter.isViewLimited() ) {
          
           if ( ! filterString.equals( "" ) )
               filterString += " AND ";
       
           // operator <> means not equal
           if ( true )
//              filterString += " ignore <> TRUE ";
              filterString += " ( ignore = FALSE OR ignore IS NULL )";
           filterString += " AND ";
           
           if ( filter.filterOnPlantingMethod() ) {
//               filterString += "time_to_tp " + (( filter.filterMethodDirectSeed() ) ? " IS " : " IS NOT " ) + " NULL AND ";
//               filterString += "direct_seed = " + 
//                               (( filter.filterMethodDirectSeed() ) ? " TRUE " : " FALSE OR direct_seed IS NULL " ) + 
//                               " AND ";
              if ( filter.filterMethodDirectSeed() )
                 filterString += " direct_seed = TRUE ";
              else
//                 filterString += " direct_seed <> TRUE ";
                 filterString += " ( direct_seed = FALSE OR direct_seed IS NULL )";
              filterString += " AND ";
           }
           
           // TODO use the propNumToPropName method to abstract out the column names
           
           if ( filter.filterOnPlanting() ) {
//               filterString += "done_plant = " + (( filter.isDonePlanting() ) ? "TRUE " : "FALSE " ) + " AND ";
              if ( filter.isDonePlanting() )
                 filterString += "done_plant = TRUE ";
              else
                 filterString += " ( done_plant = FALSE OR done_plant IS NULL ) ";
              filterString += " AND ";
           }
           if ( filter.filterOnTransplanting() ) {
//               filterString += "done_tp = " + (( filter.isDoneTransplanting() ) ? "TRUE " : "FALSE " ) + " AND ";
              if ( filter.isDoneTransplanting() )
                 filterString += "done_tp = TRUE ";
              else
                 filterString += " ( done_tp = FALSE OR done_tp IS NULL ) ";
              filterString += " AND ";
           }
           if ( filter.filterOnHarvest() ) {
//               filterString += "done_harvest = " + (( filter.isDoneHarvesting() ) ? "TRUE " : "FALSE " ) + " AND ";
                if ( filter.isDoneHarvesting() )
                 filterString += "done_harvest = TRUE ";
              else
                 filterString += " ( done_harvest = FALSE OR done_harvest IS NULL ) ";
              filterString += " AND ";
           }
           
           if ( filter.filterOnAnyDate() ) {
               if      ( filter.getAnyDateRangeEnd() == null )
                   filterString += " ( date_plant   >= " + escapeValue( filter.getAnyDateRangeStart() ) + " OR " +
                                   "   date_tp      >= " + escapeValue( filter.getAnyDateRangeStart() ) + " OR " +
                                   "   date_harvest >= " + escapeValue( filter.getAnyDateRangeStart() ) + " ) ";
               else if ( filter.getAnyDateRangeStart() == null )
                   filterString += " ( date_plant   <= " + escapeValue( filter.getAnyDateRangeEnd() ) + " OR " +
                                   "   date_tp      <= " + escapeValue( filter.getAnyDateRangeEnd() ) + " OR " +
                                   "   date_harvest <= " + escapeValue( filter.getAnyDateRangeEnd() ) + " ) ";
               else { // both != null
                   filterString += " ( date_plant   BETWEEN " + escapeValue( filter.getAnyDateRangeStart() ) + " AND " +
                                                                escapeValue( filter.getAnyDateRangeEnd() )   + " OR ";
                   filterString += "   date_tp      BETWEEN " + escapeValue( filter.getAnyDateRangeStart() ) + " AND " +
                                                                escapeValue( filter.getAnyDateRangeEnd() )   + " OR ";
                   filterString += "   date_harvest BETWEEN " + escapeValue( filter.getAnyDateRangeStart() ) + " AND " +
                                                                escapeValue( filter.getAnyDateRangeEnd() )   + " ) ";
               }
              filterString += " AND ";
           }
           else {
              if ( filter.filterOnPlantingDate() ) {
                 if ( filter.getPlantingRangeEnd() == null )
                    filterString += "date_plant >= " + escapeValue( filter.getPlantingRangeStart() );
                 else if ( filter.getPlantingRangeStart() == null )
                    filterString += "date_plant <= " + escapeValue( filter.getPlantingRangeEnd() );
                 else // both != null
                    filterString += "date_plant BETWEEN " + escapeValue( filter.getPlantingRangeStart() ) + " AND " +
                                                            escapeValue( filter.getPlantingRangeEnd() );
                 filterString += " AND ";
              }
           
              if ( filter.filterOnTPDate() ) {
                 if ( filter.getTpRangeEnd() == null )
                    filterString += "date_tp >= " + escapeValue( filter.getTpRangeStart() );
                 else if ( filter.getTpRangeStart() == null )
                    filterString += "date_tp <= " + escapeValue( filter.getTpRangeEnd() );
                 else // both != null
                    filterString += "date_tp BETWEEN " + escapeValue( filter.getTpRangeStart() ) + " AND " +
                                                         escapeValue( filter.getTpRangeEnd() );
                 filterString += " AND ";
              }
           
              if ( filter.filterOnHarvestDate() ) {
                 if ( filter.getHarvestDateEnd() == null )
                    filterString += "date_harvest >= " + escapeValue( filter.getHarvestDateStart() );
                 else if ( filter.getHarvestDateStart() == null )
                    filterString += "date_harvest <= " + escapeValue( filter.getHarvestDateEnd() );
                 else // both != null
                    filterString += "date_harvest BETWEEN " + escapeValue( filter.getHarvestDateStart() ) + " AND " +
                                                              escapeValue( filter.getHarvestDateEnd() );
                 filterString += " AND ";
              }
           }
           
           // remove the last instance of " AND ", which is at the end of the string
           filterString = filterString.substring( 0, filterString.lastIndexOf( " AND " ));
       }
           
       debug( "Using filter string: " + filterString );
       
       return filterString;
       
   }
   
   /* based upon a simple filter of space delimited uesr input, create an 
    * SQL expression that can be used as a WHERE clause.  Does not include
    * the WHERE
    */
   private String buildGeneralFilterExpression( ArrayList<String> colList, CPSComplexFilter filter ) {
      
      if ( filter == null )
          return null;
      
      String filterString = filter.getFilterString();
       
      if ( filterString == null || filterString.length() == 0 )
         return null;
      
      // TODO: if filterString not all lower, then omit LOWER below
      filterString = filterString.toLowerCase();
      
      String exp = "";
      // loop over the filter string (seperated by spaces)
      for ( String filt : filterString.split(" ") ) {
          
          String innerExp = "";
                  
          // for cases where just spaces or double spaces are entered
          if ( filt.length() == 0 )
              continue;
          
          // loop over the list of column names (seperated by commas)
          for ( String col : colList ) {
              if ( col.startsWith("date") )
                  col = "MONTHNAME( " + col + " )";
              innerExp += "LOWER( " + col + " ) LIKE " +
                          escapeValue( "%" + filt + "%" ) + " OR ";
          }
          // remove the last " OR " and tack on an " AND "
          exp += " ( " + innerExp.substring( 0, innerExp.lastIndexOf( " OR " )) + " ) AND ";
      }

      // strip off the final " AND "
      if ( exp.length() > 0 )
           exp = exp.substring( 0, exp.lastIndexOf( " AND " ) );
      
       return exp;
       
   }
   
   public int shutdown() {
      try {
          // TODO check to see which which mode we're in: if server, don't do the following
         Statement st = con.createStatement();
         st.execute("SHUTDOWN");
         con.close();
      }
      catch ( SQLException ex ) {
         ex.printStackTrace();
      }
      return 0;
   }

    @Override
    protected void updateDataListeners() {
        super.updateDataListeners();
    }
    
    @Override
    protected int saveState() { throw new UnsupportedOperationException( "Not supported yet." ); }

   public JPanel getConfigurationDisplay () { return null; }

   public CPSWizardPage[] getConfigurationWizardPages () {
      return new CPSWizardPage[] { new NewPlanWizardPage( this ) };
   }

   public void resetConfiguration () {}
   public void resetConfigurationToDefaults () {}
   public void saveConfiguration () {}

   /**
    * @return The JDBC Connection that this SQL DataModel is using.  Could be null.
    */
   @Override
   public Connection getConnection () {
      return con;
   }

   private static String toXML(ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        StringBuffer xml = new StringBuffer();
        xml.append("<Results>\n");

        while (rs.next())
        {
            xml.append("  <Row>\n");

            for (int i = 1; i <= colCount; i++)
            {
               String columnName = rsmd.getColumnName( i );
               Object value = rs.getObject( i );
               if ( rs.wasNull() )
                  continue;
               xml.append( "    <" + columnName + ">" );
               xml.append( value.toString().trim() );
               xml.append( "</" + columnName + ">\n" );
            }
            xml.append(" </Row>\n");
        }

        xml.append("</Results>\n");

        return xml.toString();
    }


}
