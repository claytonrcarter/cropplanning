/* HSQLColumnMap.java - created: January 2008
 * Copyright (C) 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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

import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSModule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author kendrajm
 */
public class HSQLColumnMap {

    private ArrayList<ColumnStruct> plantingColumnMap;
    private ArrayList<ColumnStruct> cropColumnMap;

    
    public HSQLColumnMap() {
        buildPlantingColumnMap();
        buildCropColumnMap();
    }
    
    
    public ArrayList<String> getPlantingColumnList() {
        return getColumnNameList( plantingColumnMap, false );
    }
    public ArrayList<String> getDefaultPlantingColumnList() {
        return getColumnNameList( plantingColumnMap, true );
    }
    public ArrayList<String> getCropColumnList() {
        return getColumnNameList(cropColumnMap, false );
    }
    public ArrayList<String> getDefaultCropColumnList() {
        return getColumnNameList(cropColumnMap, true );
    }
    private ArrayList<String> getColumnNameList( ArrayList<ColumnStruct> m, boolean justDefaults ) {
        ArrayList<String> s = new ArrayList<String>();
        for ( ColumnStruct cs : m )
            if ( cs.mandatory ||
                 justDefaults && cs.displayByDefault && cs.displayable ||
                 ! justDefaults && cs.displayable )
                s.add( cs.columnName );
        return s;
    }
    
    public ArrayList<Integer> getPlantingPropertyList() {
        return getPropertyList( plantingColumnMap, false );
    }
    public ArrayList<Integer> getDefaultPlantingPropertyList() {
        return getPropertyList( plantingColumnMap, true );
    }
    public ArrayList<Integer> getCropPropertyList() {
        return getPropertyList(cropColumnMap, false );
    }
    public ArrayList<Integer> getDefaultCropPropertyList() {
        return getPropertyList(cropColumnMap, true );
    }
    private ArrayList<Integer> getPropertyList( ArrayList<ColumnStruct> m, boolean justDefaults ) {
        ArrayList<Integer> s = new ArrayList<Integer>();
        for ( ColumnStruct cs : m )
            if ( cs.mandatory ||
                 justDefaults && cs.displayByDefault && cs.displayable ||
                 ! justDefaults && cs.displayable )
                s.add( cs.propertyNum );
        return s;
    }
    
    
    public ArrayList<String> getPlantingFilterColumnNames() {
        return getFilterColumnNames(plantingColumnMap);
    }
    public ArrayList<String> getCropFilterColumnNames() {
        return getFilterColumnNames(cropColumnMap);
    }
    private ArrayList<String> getFilterColumnNames( ArrayList<ColumnStruct> m ) {
        ArrayList<String> l = new ArrayList<String>();
        for ( ColumnStruct cs : m )
            // TODO figure out some way to filter/search for data in columns which are not or cannot be displayed
            if ( cs.filterable && cs.displayable )
                l.add( cs.columnName );
        return l;
    }
    
    
    public ArrayList<String[]> getPlantingShortNameMapping() {
        return getColumnNameShortNameMapping( plantingColumnMap );
    }
    private ArrayList<String[]> getColumnNameShortNameMapping( ArrayList<ColumnStruct> m ) {
        ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();
        
        /* collect displayable columns */
        for ( ColumnStruct cs : m ) {
            if ( cs.displayable )
                l.add( cs );
        }
        
        /* sort the columns to move all of the "most important" to the top */
        Collections.sort( l, new Comparator<ColumnStruct>() { 
                                 public int compare( ColumnStruct c1, ColumnStruct c2 ) {
                                  if      ( c1.mostImportant == c2.mostImportant )
                                      return 0;
                                  else if ( c1.mostImportant && ! c2.mostImportant )
                                      return -1;
                                  else if ( ! c1.mostImportant && c2.mostImportant )
                                      return 1;
                                  else
                                      return 0;
                              }
                             } );
        
        /* now just collect the column names from the sorted list  */
        ArrayList<String[]> als = new ArrayList<String[]>();
        for ( ColumnStruct cs : l )
            if ( cs.displayable )
                if ( cs.shortColumnName == null )
                    als.add( new String[]{ cs.columnName, cs.prettyColumnName, "" + cs.editable } );
                else
                    als.add( new String[]{ cs.columnName, cs.shortColumnName, "" + cs.editable } );
        
        return als;
    }
    
    
    public ArrayList<String[]> getPlantingPrettyNameMapping() {
        return getColumnNamePrettyNameMapping(plantingColumnMap);
    }
    public ArrayList<String[]> getCropPrettyNameMapping() {
        return getColumnNamePrettyNameMapping(cropColumnMap);
    }
    private ArrayList<String[]> getColumnNamePrettyNameMapping( ArrayList<ColumnStruct> m ) {
        ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();
        
        /* collect displayable columns */
        for ( ColumnStruct cs : m ) {
            if ( cs.displayable )
                l.add( cs );
        }
        
        /* sort the columns to move all of the "most important" to the top */
        Collections.sort( l, new Comparator<ColumnStruct>() { 
                                 public int compare( ColumnStruct c1, ColumnStruct c2 ) {
                                  if      ( c1.mostImportant == c2.mostImportant )
                                      return 0;
                                  else if ( c1.mostImportant && ! c2.mostImportant )
                                      return -1;
                                  else if ( ! c1.mostImportant && c2.mostImportant )
                                      return 1;
                                  else
                                      return 0;
                              }
                             } );
        
        /* now just collect the column names from the sorted list  */
        ArrayList<String[]> als = new ArrayList<String[]>();
        for ( ColumnStruct cs : l )
            if ( cs.displayable )
                als.add( new String[] { cs.columnName, cs.prettyColumnName, "" + cs.editable } );
        
        return als;
    }
    
    public ArrayList<String[]> getCropInheritanceColumnMapping() {
        ArrayList<String[]> l = new ArrayList<String[]>( cropColumnMap.size() );
        for ( ColumnStruct cs : cropColumnMap )
//            if ( cs.displayable || cs.mandatory )
                l.add( new String[] { cs.columnName, cs.correlatedColumn } );
        
        return l;
    }
    
    public ArrayList<String[]> getPlantingToCropColumnMapping() {
        ArrayList<String[]> l = new ArrayList<String[]>( plantingColumnMap.size() );
        for ( ColumnStruct cs : plantingColumnMap )
//            if ( cs.displayable || cs.mandatory )
                l.add( new String[] { cs.columnName, cs.correlatedColumn, cs.calculation, "" + cs.pseudoColumn } );
        
        return l;
    }
    
    
    public ArrayList<String> getPlantingColumns() {
        return getColumnList(plantingColumnMap);
    }
    public ArrayList<String> getCropColumns() {
        return getColumnList(cropColumnMap);
    }
    
    private ArrayList<String> getColumnList( ArrayList<ColumnStruct> m ) {
        ArrayList<String> l = new ArrayList<String>();
        for ( ColumnStruct cs : m )
//            if ( cs.displayable || cs.mandatory )
            if (( cs.displayable || cs.mandatory ) && ! cs.pseudoColumn )
                l.add( cs.columnName );
        return l;
    }
    
    
    public ArrayList<String> getPlantingDisplayableColumns() {
        return getDisplayableColumns( plantingColumnMap );
    }
    public ArrayList<String> getCropDisplayableColumns() {
        return getDisplayableColumns( cropColumnMap );
    }
    
    private ArrayList<String> getDisplayableColumns( ArrayList<ColumnStruct> m ) {
        ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();
        
        /* collect displayable columns */
        for ( ColumnStruct cs : m ) {
            if ( cs.displayable )
                l.add( cs );
        }
        
        /* sort the columns to move all of the "most important" to the top */
        Collections.sort( l, new Comparator<ColumnStruct>() { 
                                 public int compare( ColumnStruct c1, ColumnStruct c2 ) {
                                  if      ( c1.mostImportant == c2.mostImportant )
                                      return 0;
                                  else if ( c1.mostImportant && ! c2.mostImportant )
                                      return -1;
                                  else if ( ! c1.mostImportant && c2.mostImportant )
                                      return 1;
                                  else
                                      return 0;
                              }
                             } );
        
        /* now just collect the column names from the sorted list  */
        ArrayList<String> als = new ArrayList<String>();
        for ( ColumnStruct cs : l )
            als.add( cs.columnName );
                          
        return als;
        
    }
    
    protected String getCropColumnNameForProperty( int prop ) {
       return getColNameForProperty( prop, cropColumnMap );
    }
    protected String getPlantingColumnNameForProperty( int prop ) {
       return getColNameForProperty( prop, plantingColumnMap );
    }
    
    private String getColNameForProperty( int prop, ArrayList<ColumnStruct> m ) {
       
       for ( ColumnStruct cs : m ) {
          if ( cs.propertyNum == prop )
             return cs.columnName;
       }
       
       CPSModule.debug( "HSQLColumnMap", "Error:NoSuchProperty (" + prop + ")" );
       return "Error:NoSuchProperty (" + prop + ")";
    }
    
    
    protected int getCropPropertyNumFromName( String colName ) {
       return getPropertyNumForColumn( colName, cropColumnMap );
    }
    protected int getPlantingPropertyNumFromName( String colName ) {
       return getPropertyNumForColumn( colName, plantingColumnMap );
    }
    
    private int getPropertyNumForColumn( String colName, ArrayList<ColumnStruct> m ) {
       
       for ( ColumnStruct cs : m ) {
          if ( colName.equalsIgnoreCase( cs.columnName ))
             return cs.propertyNum;
       }
       
       return 0;
          
    }
    
    
    private void buildCropColumnMap() {
        ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();

        /*
         * If a column is inheritable from a crop to a var, then duplicate the
         * column name in the "mapsTo" field.
         */
//                                                                                  (mapsTo: null == not inherited)
//      l.add( new ColumnStruct( colName,          propNum,                                    mapsTo,            calc, mand,  disp,  filt,  def,   imp,   prettyName ));
        l.add( new ColumnStruct( "id",             CPSDataModelConstants.PROP_CROP_ID,         null,              null, true,  false, false, false, false, "" ));
        l.add( new ColumnStruct( "crop_name",      CPSDataModelConstants.PROP_CROP_NAME,       null,              null, true,  true,  true,  true,  true,  "Crop Name" ));
        l.add( new ColumnStruct( "var_name",       CPSDataModelConstants.PROP_VAR_NAME,        null,              null, false, true,  true,  true,  true,  "Variety Name" ));
        l.add( new ColumnStruct( "bot_name",       CPSDataModelConstants.PROP_BOT_NAME,        "bot_name",        null, false, false, true,  false, false, "Botanical Name" ));
        l.add( new ColumnStruct( "description",    CPSDataModelConstants.PROP_CROP_DESC,       null,              null, false, false, true,  false, false, "Description" ));
        l.add( new ColumnStruct( "keywords",       CPSDataModelConstants.PROP_KEYWORDS,        null,              null, false, true,  true,  false, false, "Keywords" ));
        l.add( new ColumnStruct( "other_req",      CPSDataModelConstants.PROP_OTHER_REQ,       "other_req",       null, false, true,  true,  false, false, "Other Requirements" ));
        l.add( new ColumnStruct( "notes",          CPSDataModelConstants.PROP_NOTES,           null,              null, false, false, true,  false, false, "Notes" ));
        l.add( new ColumnStruct( "maturity",       CPSDataModelConstants.PROP_MATURITY,        "maturity",        null, false, true,  false, true,  true,  "Maturity Days" ));
        l.add( new ColumnStruct( "direct_seed",    CPSDataModelConstants.PROP_DIRECT_SEED,     "direct_seed",     null, false, true,  false, false, true,  "Can be direct seeded?" ));
        l.add( new ColumnStruct( "ds_mat_adjust",  CPSDataModelConstants.PROP_DS_MAT_ADJUST,   "ds_mat_adjust",   null, false, true,  false, false, false, "Mat. Adjustment (DS)" ));
        l.add( new ColumnStruct( "ds_rows_p_bed",  CPSDataModelConstants.PROP_DS_ROWS_P_BED,   "ds_rows_p_bed",   null, false, true,  false, false, true,  "Rows/Bed (DS)" ));
        l.add( new ColumnStruct( "ds_row_space",   CPSDataModelConstants.PROP_DS_SPACE_BETROW, "ds_row_space",    null, false, true,  false, false, false, "Space between Rows (DS)" ));
        l.add( new ColumnStruct( "ds_plant_notes", CPSDataModelConstants.PROP_DS_PLANT_NOTES,  "ds_plant_notes",  null, false, true,  true, false, false, "Planting Notes (DS)" ));
        
        l.add( new ColumnStruct( "transplant",     CPSDataModelConstants.PROP_TRANSPLANT,      "transplant",      null, false, true,  false, false, false, "Can be Transplanted?" ));
        l.add( new ColumnStruct( "tp_mat_adjust",  CPSDataModelConstants.PROP_TP_MAT_ADJUST,   "tp_mat_adjust",   null, false, true,  false, false, false, "Mat. Adjustment (TP)" ));
        l.add( new ColumnStruct( "tp_rows_p_bed",  CPSDataModelConstants.PROP_TP_ROWS_P_BED,   "tp_rows_p_bed",   null, false, true,  false, false, true,  "Rows/Bed (TP)" ));
        l.add( new ColumnStruct( "tp_row_space",   CPSDataModelConstants.PROP_TP_SPACE_BETROW, "tp_row_space",    null, false, true,  false, false, false, "Space between Rows (TP)" ));
        l.add( new ColumnStruct( "tp_inrow_space", CPSDataModelConstants.PROP_TP_SPACE_INROW,  "tp_inrow_space",  null, false, true,  false, false, true,  "Space between Plants (TP)" ));
        l.add( new ColumnStruct( "tp_time_in_gh",  CPSDataModelConstants.PROP_TP_TIME_IN_GH,   "tp_time_in_gh",   null, false, true,  false, false, true,  "Weeks In GH (TP)" ));
        l.add( new ColumnStruct( "tp_flat_size",   CPSDataModelConstants.PROP_TP_FLAT_SIZE,    "tp_flat_size",    null, false, true,  true,  false, false, "Plug Flat Size or Capacity" ));
        l.add( new ColumnStruct( "tp_pot_up",      CPSDataModelConstants.PROP_TP_POT_UP,       "tp_pot_up",       null, false, true,  true,  false, false, "Pot up? (TP)" ));
        l.add( new ColumnStruct( "tp_pot_up_notes",CPSDataModelConstants.PROP_TP_POT_UP_NOTES, "tp_pot_up_notes", null, false, true,  true,  false, false, "Plug Flat Size or Capacity" ));
        l.add( new ColumnStruct( "tp_plant_notes", CPSDataModelConstants.PROP_TP_PLANT_NOTES,  "tp_plant_notes",  null, false, true,  true,  false, false, "Seeding Notes (TP)" ));
        
        l.add( new ColumnStruct( "frost_hardy",    CPSDataModelConstants.PROP_FROST_HARDY,     "frost_hardy",     null, false, true,  false, false, true,  "Frost Hardy", "Hardy" ) );
        l.add( new ColumnStruct( "yield_p_foot",   CPSDataModelConstants.PROP_YIELD_P_FOOT,    "yield_p_foot",    null, false, true,  false, false, true,  "Yield/Foot" ));
        l.add( new ColumnStruct( "yield_num_weeks",CPSDataModelConstants.PROP_YIELD_NUM_WEEKS, "yield_num_weeks", null, false, true,  false, false, false, "Yields For (weeks)" ));
        l.add( new ColumnStruct( "yield_p_week",   CPSDataModelConstants.PROP_YIELD_P_WEEK,    "yield_p_week",    null, false, true,  false, false, false, "Yield/Week" ));
        l.add( new ColumnStruct( "crop_unit",      CPSDataModelConstants.PROP_CROP_UNIT,       "crop_unit",       null, false, true,  true,  false, false, "Yield Unit" ));
        l.add( new ColumnStruct( "crop_unit_value",CPSDataModelConstants.PROP_CROP_UNIT_VALUE, "crop_unit_value", null, false, true,  false, false, true,  "Unit Value" ));
        l.add( new ColumnStruct( "fam_name",       CPSDataModelConstants.PROP_FAM_NAME,        "fam_name",        null, false, true,  true,  true,  true,  "Family Name" ));
        l.add( new ColumnStruct( "groups",         CPSDataModelConstants.PROP_GROUPS,          "groups",          null, false, true,  true,  false, false, "Groups" ));
//      l.add( new ColumnStruct( colName,          mapsTo, calc, mand,  disp,  filt,  def,   imp,   prettyName ));

        cropColumnMap = l;
    }


    private void buildPlantingColumnMap() {
        ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();

//      l.add( new ColumnStruct( colName,          propNum,                                  mapsTo,            calc, mand,  disp,  filt,  def,   imp,   edit,  pseudo,  prettyName,        shortName ));
                                                                                             
        l.add( new ColumnStruct( "id",             CPSDataModelConstants.PROP_PLANTING_ID,   null,              null, true,  false, false, false, false, false, false,   "" ) );
        l.add( new ColumnStruct( "crop_name",      CPSDataModelConstants.PROP_CROP_NAME,     "crop_name",       null, true,  true,  true,  true,  true,  true,  false,   "Crop Name",         "Crop" ));
        l.add( new ColumnStruct( "var_name",       CPSDataModelConstants.PROP_VAR_NAME,      "var_name",        null, false, true,  true,  true,  true,  true,  false,   "Variety Name",      "Variety" ));
        l.add( new ColumnStruct( "groups",         CPSDataModelConstants.PROP_GROUPS,        "groups",          null, false, true,  true,  false, false, true,  false,   "Groups" ) );
        l.add( new ColumnStruct( "location",       CPSDataModelConstants.PROP_LOCATION,      null,              null, false, true,  true,  true,  true,  true,  false,   "Location",          "Loc." ) );
        l.add( new ColumnStruct( "keywords",       CPSDataModelConstants.PROP_KEYWORDS,      "keywords",        null, false, true,  true,  false, false, true,  false,   "Keywords" ) );
        l.add( new ColumnStruct( "other_req",      CPSDataModelConstants.PROP_OTHER_REQ,     "other_req",       null, false, true,  true,  false, false, true,  false,   "Other Requirements" ) );
        l.add( new ColumnStruct( "notes",          CPSDataModelConstants.PROP_NOTES,         null,              null, false, true,  true,  false, false, true,  false,   "Notes" ) );
        l.add( new ColumnStruct( "ignore",         CPSDataModelConstants.PROP_IGNORE,        null,              null, false, true,  false, false, false, true,  false,   "Ignore?", "Ign?" ) );
        
        l.add( new ColumnStruct( "maturity",       CPSDataModelConstants.PROP_MATURITY,      "maturity",        null, false, true,  false, true,  true,  true,  false,   "Maturity Days",     "Mat." ) );
//        l.add( new ColumnStruct( "mat_adjust",     CPSDataModelConstants.PROP_MAT_ADJUST,    "direct_seed, ds_mat_adjust, tp_mat_adjust",
//                                                                                                                null, false, true,  false, false, false, true,  false,   "Mat. Adjustment",   "Mat +/-" ) );
        l.add( new ColumnStruct( "mat_adjust",     CPSDataModelConstants.PROP_MAT_ADJUST,    null,              "CASE WHEN direct_seed THEN ds_mat_adjust ELSE tp_mat_adjust END",
                                                                                                                      false, true,  false, false, false, false, true,    "Mat. Adjustment",   "Mat +/-" ) );
        l.add( new ColumnStruct( "ds_mat_adjust",  CPSDataModelConstants.PROP_DS_MAT_ADJUST, "ds_mat_adjust",   null, false, false, false, false, false, true,  false,   "Mat. Adjustment (DS)",   "Mat +/- (DS)" ) );
        l.add( new ColumnStruct( "tp_mat_adjust",  CPSDataModelConstants.PROP_TP_MAT_ADJUST, "tp_mat_adjust",   null, false, false, false, false, false, true,  false,   "Mat. Adjustment (TP)",   "Mat +/- (TP)" ) );
      l.add( new ColumnStruct(  "direct_seed",     CPSDataModelConstants.PROP_DIRECT_SEED,   "direct_seed",     null, false, true,  false, false, true,  true,  false,   "Direct Seeded", "DS" ));
      l.add( new ColumnStruct(  "frost_hardy",     CPSDataModelConstants.PROP_FROST_HARDY,   "frost_hardy",     null, false, true,  false, false, true,  true,  false,   "Frost Hardy", "Hardy" ));
      l.add( new ColumnStruct(  "time_to_tp",      CPSDataModelConstants.PROP_TIME_TO_TP,    "direct_seed, NULL, tp_time_in_gh",   
                                                                                                                null, false, true,  false, false, true,  true,  false,   "Time in GH (weeks)", "Weeks in GH"   ));
      
                                                                      // the order of these calculations, or at least the order in which they
                                                                      // appear in the COALESCE statement, is important to ensure that values
                                                                      // are calculated from the proper ... other values -- since the the other
                                                                      // values might have to be filled in as we go
//      l.add( new ColumnStruct( colName,              propNum,                                     mapsTo,  calc,          mand,  disp,  filt,  def,   imp,   edit,  pseudo,  prettyName,        shortName ));
      l.add( new ColumnStruct(  "date_plant",          CPSDataModelConstants.PROP_DATE_PLANT,         null,  "COALESCE( date_plant_actual, date_plant_plan )",
                                                                                                                            false, true,  true,  true,  true,  false, true,   "Planting Date (Effective)", "Plant" ));
      l.add( new ColumnStruct(  "date_plant_plan",     CPSDataModelConstants.PROP_DATE_PLANT_PLAN,    null,  "\"CPS.Core.DB.HSQLCalc.plantFromTP\"(      date_tp_plan,      time_to_tp, direct_seed ), "
                                                                                                           + "\"CPS.Core.DB.HSQLCalc.plantFromHarvest\"( date_harvest_plan, maturity,   mat_adjust, time_to_tp, direct_seed ) ",
                                                                                                                            false, true,  true,  true,  true,  true,  false,   "Planting Date (Planned)", "Plant" ));
      l.add( new ColumnStruct(  "date_plant_actual",   CPSDataModelConstants.PROP_DATE_PLANT_ACTUAL,  null,  "\"CPS.Core.DB.HSQLCalc.plantFromTP\"(      date_tp_actual,      time_to_tp, direct_seed ), "
                                                                                                           + "\"CPS.Core.DB.HSQLCalc.plantFromHarvest\"( date_harvest_actual, maturity,   mat_adjust, time_to_tp, direct_seed ) ",
                                                                                                                            false, true,  true,  false,  true, true,  false,    "Planting Date (Actual)", "Plant" ));
      l.add( new ColumnStruct(  "done_plant",          CPSDataModelConstants.PROP_DONE_PLANTING,      null,  null,          false, true,  false, true,   true,  true,  false,   "Planted?"  ));
      /* ** */
      /* ** */
      l.add( new ColumnStruct(  "date_tp",             CPSDataModelConstants.PROP_DATE_TP,            null,  "COALESCE( date_tp_actual, date_tp_plan )",
                                                                                                                            false, true,  true,  true,  true,  false, true,   "Transplant Date (Effective)", "TP" ));
      l.add( new ColumnStruct(  "date_tp_plan",        CPSDataModelConstants.PROP_DATE_TP_PLAN,       null,  "\"CPS.Core.DB.HSQLCalc.TPFromPlant\"(   date_plant_plan,   time_to_tp, direct_seed ), "
                                                                                                           + "\"CPS.Core.DB.HSQLCalc.TPFromHarvest\"( date_harvest_plan, maturity,   mat_adjust, direct_seed ) ",
                                                                                                                            false, true,  true,  true, true,  true,  false,   "Transplant Date (Planned)", "TP" ));
      l.add( new ColumnStruct(  "date_tp_actual",      CPSDataModelConstants.PROP_DATE_TP_ACTUAL,     null,  "\"CPS.Core.DB.HSQLCalc.TPFromPlant\"(   date_plant_actual,   time_to_tp, direct_seed ), "
                                                                                                           + "\"CPS.Core.DB.HSQLCalc.TPFromHarvest\"( date_harvest_actual, maturity,   mat_adjust, direct_seed ) ",
                                                                                                                            false, true,  true,  false, true,  true,  false,   "Transplant Date (Actual)", "TP" ));
      l.add( new ColumnStruct(  "done_tp",             CPSDataModelConstants.PROP_DONE_TP,            null,  null,          false, true,  false, true,  true,  true,  false,   "Transplanted?"  ));
      /* ** */
      /* ** */
      l.add( new ColumnStruct(  "date_harvest",        CPSDataModelConstants.PROP_DATE_HARVEST,       null,  "COALESCE( date_harvest_actual, date_harvest_plan )",
                                                                                                                            false, true,  true,  true,  true,  false, true,   "Harvest Date (Effective)", "Pick" ));
      l.add( new ColumnStruct(  "date_harvest_plan",   CPSDataModelConstants.PROP_DATE_HARVEST_PLAN,  null,  "\"CPS.Core.DB.HSQLCalc.harvestFromTP\"(    date_tp_plan,    maturity, mat_adjust, direct_seed ), "
                                                                                                           + "\"CPS.Core.DB.HSQLCalc.harvestFromPlant\"( date_plant_plan, maturity, mat_adjust, time_to_tp, direct_seed ) ",
                                                                                                                            false, true,  true,  true,  true,  true,  false,   "Harvest Date (Planned)", "Pick" ));
      l.add( new ColumnStruct(  "date_harvest_actual", CPSDataModelConstants.PROP_DATE_HARVEST_ACTUAL,null, "\"CPS.Core.DB.HSQLCalc.harvestFromTP\"(     date_tp_actual,    maturity, mat_adjust, direct_seed ), "
                                                                                                           + "\"CPS.Core.DB.HSQLCalc.harvestFromPlant\"( date_plant_actual, maturity, mat_adjust, time_to_tp, direct_seed ) ",
                                                                                                                            false, true,  true,  false,  true,  true,  false,   "Harvest Date (Actual)", "Pick" ));
      l.add( new ColumnStruct(  "done_harvest",        CPSDataModelConstants.PROP_DONE_HARVEST,       null,  null,          false, true,  false, true,  true,  true,  false,   "Picked?"  ));

      
//      l.add( new ColumnStruct( colName,          propNum,                                  mapsTo,          calc,   mand,  disp,  filt,  def,   imp,   edit,  pseudo,  prettyName,        shortName ));
      l.add( new ColumnStruct(  "beds_to_plant",   CPSDataModelConstants.PROP_BEDS_PLANT,    null,            "\"CPS.Core.DB.HSQLCalc.bedsFromRowFt\"(  rowft_to_plant, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) ), "
                                                                                                            + "\"CPS.Core.DB.HSQLCalc.bedsFromPlants\"( plants_needed,  inrow_space, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) )",
                                                                                                                     false, true,  false, false, true,  true,  false,   "Beds to Plant",         "Beds"  ));
//      l.add( new ColumnStruct(  "rows_p_bed",      CPSDataModelConstants.PROP_ROWS_P_BED,    "direct_seed, ds_rows_p_bed, tp_rows_p_bed",
//                                                                                                               null,  false, true,  false, false, false, true,  false,   "Rows/Bed"  ));
      l.add( new ColumnStruct(  "rows_p_bed",     CPSDataModelConstants.PROP_ROWS_P_BED,    null,              "CASE WHEN direct_seed THEN ds_rows_p_bed ELSE tp_rows_p_bed END",
                                                                                                                     false, true,  false, false, false, false, true,    "Rows/Bed" ) );
      l.add( new ColumnStruct(  "ds_rows_p_bed",  CPSDataModelConstants.PROP_DS_ROWS_P_BED, "ds_rows_p_bed",   null, false, false, false, false, false, true,  false,   "Rows/Bed (DS)" ) );
      l.add( new ColumnStruct(  "tp_rows_p_bed",  CPSDataModelConstants.PROP_TP_ROWS_P_BED, "tp_rows_p_bed",   null, false, false, false, false, false, true,  false,   "Rows/Bed (TP)" ) );
      l.add( new ColumnStruct(  "plants_needed",   CPSDataModelConstants.PROP_PLANTS_NEEDED, null,            "\"CPS.Core.DB.HSQLCalc.plantsFromBeds\"(  beds_to_plant,  inrow_space, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) ), "
                                                                                                            + "\"CPS.Core.DB.HSQLCalc.plantsFromRowFt\"( rowft_to_plant, inrow_space )",
                                                                                                                     false, true,  false, false, true,  true,  false,   "Plants Needed",         "Need"  ));
      l.add( new ColumnStruct(  "plants_to_start", CPSDataModelConstants.PROP_PLANTS_START,  null,            "\"CPS.Core.DB.HSQLCalc.plantsToStart\"( plants_needed )",
                                                                                                                     false, true,  false, false, true,  true,  false,   "Plants to Start (Needed + Fudge)", "Start"  ));
      l.add( new ColumnStruct(  "rowft_to_plant",  CPSDataModelConstants.PROP_ROWFT_PLANT,   null,            "\"CPS.Core.DB.HSQLCalc.rowFtFromBeds\"(   beds_to_plant, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) ), "
                                                                                                            + "\"CPS.Core.DB.HSQLCalc.rowFtFromPlants\"( plants_needed, inrow_space )",
                                                                                                                     false, true,  false, false, true,  true,  false,   "Row Feet to Plant",      "Rowft"  ));
      l.add( new ColumnStruct(  "inrow_space",     CPSDataModelConstants.PROP_SPACE_INROW,  "direct_seed, NULL, tp_inrow_space",  
                                                                                                               null, false, true,  false, false, false, true,  false,   "Spacing between Plants", "Inrow Space"  ));
//      l.add( new ColumnStruct(  "row_space",       CPSDataModelConstants.PROP_SPACE_BETROW, "direct_seed, ds_row_space, tp_row_space",
//                                                                                                               null, false, true,  false, false, false, true,  false,   "Spacing between Rows",   "Row Space"  ));
      l.add( new ColumnStruct(  "row_space",       CPSDataModelConstants.PROP_SPACE_BETROW,  null,             "CASE WHEN direct_seed THEN ds_row_space ELSE tp_row_space END",
                                                                                                                     false, true,  false, false, false, false, true,   "Spacing between Rows",   "Row Space"  ));
      l.add( new ColumnStruct(  "ds_row_space",    CPSDataModelConstants.PROP_DS_SPACE_BETROW, "ds_row_space", null, false, false, false, false, false, true,  false,  "Spacing between Rows (DS)",   "Row Space (DS)"  ));
      l.add( new ColumnStruct(  "tp_row_space",    CPSDataModelConstants.PROP_TP_SPACE_BETROW, "tp_row_space", null, false, false, false, false, false, true,  false,  "Spacing between Rows (TP)",   "Row Space (TP)"  ));
      l.add( new ColumnStruct(  "flat_size",       CPSDataModelConstants.PROP_FLAT_SIZE,    "direct_seed, NULL, tp_flat_size",    
                                                                                                               null, false, true,  true,  false, false, true,  false,   "GH Flat Size or Capacity", "Flat"  ));
      l.add( new ColumnStruct(  "flats_needed",    CPSDataModelConstants.PROP_FLATS_NEEDED, null,              "\"CPS.Core.DB.HSQLCalc.flatsNeeded\"( plants_to_start, flat_size )",
                                                                                                                     false, true,  false, false, true,  true,  false,   "GH Flats Needed",         "Flats"  ));
      
      
//    l.add( new ColumnStruct(   colName,          propNum,                                    mapsTo,                   calc, mand,  disp,  filt,  def,   imp,   edit,  pseudo,  prettyName ));
//      l.add( new ColumnStruct(  "plant_notes_inh", CPSDataModelConstants.PROP_PLANT_NOTES,    "direct_seed, ds_plant_notes, tp_plant_notes",
//                                                                                                                         null, false, true,  true,  false, false, true,  false,   "Notes on Seeding (for crop)", "Notes" ));
      l.add( new ColumnStruct(  "planting_notes",  CPSDataModelConstants.PROP_PLANT_NOTES,    null,                      "CASE WHEN direct_seed THEN ds_crop_notes ELSE tp_crop_notes END",
                                                                                                                               false, true,  true,  false, false, false, true,    "Notes on Seeding (for crop)", "Notes" ));
      l.add( new ColumnStruct(  "ds_crop_notes",   CPSDataModelConstants.PROP_DS_PLANT_NOTES, "ds_plant_notes",          null, false, false, false, false, false, true,  false,   "DS Notes on Seeding (for crop)", "DS Notes" ));
      l.add( new ColumnStruct(  "tp_crop_notes",   CPSDataModelConstants.PROP_TP_PLANT_NOTES, "tp_plant_notes",          null, false, false, false, false, false, true,  false,   "TP Notes on Seeding (for crop)", "TP Notes" ));
      l.add( new ColumnStruct(  "plant_notes_spec",CPSDataModelConstants.PROP_PLANT_NOTES_SPECIFIC, null,                null, false, true,  true,  false, false, true,  false,   "Notes on Planting (for this planting)", "Notes" ));
      l.add( new ColumnStruct(  "pot_up",          CPSDataModelConstants.PROP_TP_POT_UP,      "direct_seed, NULL, tp_pot_up",               
                                                                                                                         null, false, true,  false, false, false, true,  false,   "Pot up? " ));
      l.add( new ColumnStruct(  "pot_up_notes",    CPSDataModelConstants.PROP_TP_POT_UP_NOTES,"direct_seed, NULL, tp_pot_up_notes",         
                                                                                                                         null, false, true,  true,  false, false, true,  false,   "Notes on Potting Up" ));
      l.add( new ColumnStruct(  "yield_p_foot",    CPSDataModelConstants.PROP_YIELD_P_FOOT,   "yield_p_foot",            null, false, true,  false, false, false, true,  false,   "Yield/Foot"  ));
      l.add( new ColumnStruct(  "total_yield",     CPSDataModelConstants.PROP_TOTAL_YIELD,    null,              "\"CPS.Core.DB.HSQLCalc.totalYieldFromRowFt\"( rowft_to_plant, yield_p_foot )",
                                                                                                                               false, true,  false, false,  true, true,  false,   "Total Yield"  ));
      l.add( new ColumnStruct(  "yield_num_weeks", CPSDataModelConstants.PROP_YIELD_NUM_WEEKS,"yield_num_weeks",         null, false, true,  false, false, false, true,  false,   "Yields For (weeks)"  ));
      l.add( new ColumnStruct(  "yield_p_week",    CPSDataModelConstants.PROP_YIELD_P_WEEK,   "yield_p_week",            null, false, true,  false, false, false, true,  false,   "Yield/Week"  ));

      l.add( new ColumnStruct(  "crop_unit",       CPSDataModelConstants.PROP_CROP_UNIT,      "crop_unit",               null, false, true,  false, false, false, true,  false,   "Yield Unit"  ));
      l.add( new ColumnStruct(  "crop_unit_value", CPSDataModelConstants.PROP_CROP_UNIT_VALUE,"crop_unit_value",         null, false, true,  false, false, false, true,  false,   "Unit Value"  ));

      l.add( new ColumnStruct(  "custom1",         CPSDataModelConstants.PROP_CUSTOM1,         null,                     null, false, true,  false, false, false, true,  false,   "Custom1"  ));
      l.add( new ColumnStruct(  "custom2",         CPSDataModelConstants.PROP_CUSTOM2,         null,                     null, false, true,  false, false, false, true,  false,   "Custom2"  ));
      l.add( new ColumnStruct(  "custom3",         CPSDataModelConstants.PROP_CUSTOM3,         null,                     null, false, true,  false, false, false, true,  false,   "Custom3"  ));
      l.add( new ColumnStruct(  "custom4",         CPSDataModelConstants.PROP_CUSTOM4,         null,                     null, false, true,  false, false, false, true,  false,   "Custom4"  ));
      l.add( new ColumnStruct(  "custom5",         CPSDataModelConstants.PROP_CUSTOM5,         null,                     null, false, true,  false, false, false, true,  false,   "Custom5"  ));

      plantingColumnMap = l;
    }


    public class ColumnStruct {

        public String columnName;
        public int propertyNum;
        public String prettyColumnName;
        public String shortColumnName;
        public String columnDescription;
        public String correlatedColumn;
        public String calculation;
        public boolean mandatory;
        public boolean displayable;
        public boolean filterable;
        public boolean displayByDefault;
        public boolean mostImportant;
        public boolean editable;
        public boolean pseudoColumn;
        

        // w/o edit, pseudo or shortname
       public ColumnStruct(  String colName,
                             int propNum,
                             String mapsTo,
                             String calc,
                             boolean mand,
                             boolean disp,
                             boolean filt,
                             boolean def,
                             boolean imp,
                             String prettyName ) {
           this( colName, propNum, mapsTo, calc, mand, disp, filt, def, imp, true, false, prettyName, null );
       }
        
        // w/o edit, pseudo but w/ shortname
       public ColumnStruct(  String colName,
                             int propNum,
                             String mapsTo,
                             String calc,
                             boolean mand,
                             boolean disp,
                             boolean filt,
                             boolean def,
                             boolean imp,
                             String prettyName,
                             String shortName ) {
           this( colName, propNum, mapsTo, calc, mand, disp, filt, def, imp, true, false, prettyName, shortName );
       }
        
        // w/ edit and pseudo but w/o shortname
       public ColumnStruct(  String colName,
                             int propNum,
                             String mapsTo,
                             String calc,
                             boolean mand,
                             boolean disp,
                             boolean filt,
                             boolean def,
                             boolean imp,
                             boolean edit,
                             boolean pseudo,
                             String prettyName ) {
           this( colName, propNum, mapsTo, calc, mand, disp, filt, def, imp, edit, pseudo, prettyName, null );
       }
        
        public ColumnStruct( String colName,
                             int propNum,
                             String mapsTo,
                             String calc,
                             boolean mand,
                             boolean disp,
                             boolean filt,
                             boolean def,
                             boolean imp,
                             boolean edit,
                             boolean pseudo,
                             String prettyName,
                             String shortName  ) {
          
            this.columnName = colName;
            this.propertyNum = propNum;
            this.prettyColumnName = prettyName;
            this.columnDescription = "";
            this.correlatedColumn = mapsTo;
            this.calculation = calc;
            this.mandatory = mand;
            this.displayable = disp;
            this.filterable = filt;
            this.displayByDefault = def;
            this.mostImportant = imp;
            
            this.editable = edit;
            this.pseudoColumn = pseudo;
            
            this.shortColumnName = shortName;
            
        }

    }
}
