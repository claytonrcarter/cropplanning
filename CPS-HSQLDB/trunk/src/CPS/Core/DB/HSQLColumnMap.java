/* HSQLColumnMap.java - created: January 2008
 * Copyright (C) 2008 Clayton Carter
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

import CPS.Module.CPSDataModelConstants;
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
        ArrayList<ColumnStruct> l = new ArrayList();
        
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
        ArrayList<String[]> als = new ArrayList();
        for ( ColumnStruct cs : l )
            if ( cs.displayable )
                if ( cs.shortColumnName == null )
                    als.add( new String[]{ cs.columnName, cs.prettyColumnName } );
                else
                    als.add( new String[]{ cs.columnName, cs.shortColumnName } );
        
        return als;
    }
    
    
    public ArrayList<String[]> getPlantingPrettyNameMapping() {
        return getColumnNamePrettyNameMapping(plantingColumnMap);
    }
    public ArrayList<String[]> getCropPrettyNameMapping() {
        return getColumnNamePrettyNameMapping(cropColumnMap);
    }
    private ArrayList<String[]> getColumnNamePrettyNameMapping( ArrayList<ColumnStruct> m ) {
        ArrayList<ColumnStruct> l = new ArrayList();
        
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
        ArrayList<String[]> als = new ArrayList();
        for ( ColumnStruct cs : l )
            if ( cs.displayable )
                als.add( new String[] { cs.columnName, cs.prettyColumnName } );
        
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
                l.add( new String[] { cs.columnName, cs.correlatedColumn, cs.calculation } );
        
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
            if ( cs.displayable || cs.mandatory )
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
        ArrayList<ColumnStruct> l = new ArrayList();
        
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
        ArrayList<String> als = new ArrayList();
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
       
       return "Error:NoSuchProperty";
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
        ArrayList<ColumnStruct> l = new ArrayList();

        /*
         * If a column is inheritable from a crop to a var, then duplicate the
         * column name in the "mapsTo" field.
         */
        
//      l.add( new ColumnStruct( colName,          propNum,                              mapsTo,            calc, mand,  disp,  filt,  def,   imp,   prettyName ));
        l.add( new ColumnStruct( "id",             CPSDataModelConstants.PROP_CROP_ID,   null,              null, true,  false, false, false, false, "" ));
        l.add( new ColumnStruct( "crop_name",      CPSDataModelConstants.PROP_CROP_NAME, null,              null, true,  true,  true,  true,  true,  "Crop Name" ));
        l.add( new ColumnStruct( "var_name",       CPSDataModelConstants.PROP_VAR_NAME,  null,              null, false, true,  true,  true,  true,  "Variety Name" ));
        l.add( new ColumnStruct( "bot_name",       CPSDataModelConstants.PROP_BOT_NAME,  "bot_name",        null, false, false, true,  false, false, "Botanical Name" ));
        l.add( new ColumnStruct( "description",    CPSDataModelConstants.PROP_CROP_DESC, null,              null, false, false, true,  false, false, "Description" ));
        l.add( new ColumnStruct( "keywords",       CPSDataModelConstants.PROP_KEYWORDS,  null,              null, false, true,  true,  false, false, "Keywords" ));
        l.add( new ColumnStruct( "other_req",      CPSDataModelConstants.PROP_OTHER_REQ, "other_req",       null, false, true,  true,  false, false, "Other Requirements" ));
        l.add( new ColumnStruct( "notes",          CPSDataModelConstants.PROP_NOTES,     null,              null, false, false, true,  false, false, "Notes" ));
        l.add( new ColumnStruct( "maturity",       CPSDataModelConstants.PROP_MATURITY,  "maturity",        null, false, true,  false, true,  true,  "Maturity Days" ));
        l.add( new ColumnStruct( "time_to_tp",     CPSDataModelConstants.PROP_TIME_TO_TP, "time_to_tp",      null, false, true,  false, false, true,  "Time In GH (weeks)" ));
        l.add( new ColumnStruct( "frost_hardy",    CPSDataModelConstants.PROP_FROST_HARDY, "frost_hardy",     null, false, true,  false, false, true,  "Frost Hardy", "Hardy" ) );
        l.add( new ColumnStruct( "direct_seed",    CPSDataModelConstants.PROP_DIRECT_SEED, "direct_seed",     null, false, true,  false, false, true,  "Direct Seeded" ));
        l.add( new ColumnStruct( "rows_p_bed",     CPSDataModelConstants.PROP_ROWS_P_BED, "rows_p_bed",      null, false, true,  false, false, true,  "Rows/Bed" ));
        l.add( new ColumnStruct( "space_inrow",    CPSDataModelConstants.PROP_SPACE_INROW, "space_inrow",     null, false, true,  false, false, true,  "Space between Plants" ));
        l.add( new ColumnStruct( "space_betrow",   CPSDataModelConstants.PROP_SPACE_BETROW, "space_betrow",    null, false, true,  false, false, false, "Space between Rows" ));
        l.add( new ColumnStruct( "flat_size",      CPSDataModelConstants.PROP_FLAT_SIZE, "flat_size",       null, false, true,  true,  false, false, "Plug Flat Size/Capacity" ));
        l.add( new ColumnStruct( "planter",        CPSDataModelConstants.PROP_PLANTER, "planter",         null, false, true,  true,  false, false, "Planter" ));
        l.add( new ColumnStruct( "planter_setting",CPSDataModelConstants.PROP_PLANTER_SETTING,"planter_setting", null, false, true,  true,  false, false, "Planter Setting" ));
        l.add( new ColumnStruct( "yield_p_foot",   CPSDataModelConstants.PROP_YIELD_P_FOOT,"yield_p_foot",    null, false, true,  false, false, true,  "Yield/Foot" ));
        l.add( new ColumnStruct( "yield_num_weeks",CPSDataModelConstants.PROP_YIELD_NUM_WEEKS,"yield_num_weeks", null, false, true,  false, false, false, "Yields For (weeks)" ));
        l.add( new ColumnStruct( "yield_p_week",   CPSDataModelConstants.PROP_YIELD_P_WEEK,"yield_p_week",    null, false, true,  false, false, false, "Yield/Week" ));
        l.add( new ColumnStruct( "crop_unit",      CPSDataModelConstants.PROP_CROP_UNIT,"crop_unit",       null, false, true,  true,  false, false, "Yield Unit" ));
        l.add( new ColumnStruct( "crop_unit_value",CPSDataModelConstants.PROP_CROP_UNIT_VALUE,"crop_unit_value", null, false, true,  false, false, true,  "Unit Value" ));
//        l.add( new ColumnStruct( "fudge",          "fudge",           null, false, false, false, false, false, "Fudge Factor" ));
        l.add( new ColumnStruct( "mat_adjust",     CPSDataModelConstants.PROP_MAT_ADJUST,"mat_adjust",      null, false, true,  false, false, false, "Mat. Adjustment" ));
        l.add( new ColumnStruct( "misc_adjust",    CPSDataModelConstants.PROP_MISC_ADJUST,"misc_adjust",     null, false, true,  false, false, false, "Misc. Mat. Adjustment" ));
        l.add( new ColumnStruct( "fam_name",       CPSDataModelConstants.PROP_FAM_NAME,"fam_name",        null, false, true,  true,  true,  true,  "Family Name" ));
        l.add( new ColumnStruct( "groups",         CPSDataModelConstants.PROP_GROUPS,"groups",          null, false, true,  true,  false, false, "Groups" ));
//        l.add( new ColumnStruct( "successions",    null,   null, false, false, false, false, false, "Successions" ));
//      l.add( new ColumnStruct( colName,          mapsTo, calc, mand,  disp,  filt,  def,   imp,   prettyName ));

        cropColumnMap = l;
    }


    private void buildPlantingColumnMap() {
        ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();

//      l.add( new ColumnStruct( colName,          propNum,                                  mapsTo,            calc, mand,  disp,  filt,  def,   imp,  prettyName,        shortName ));
                                                                                             
        l.add( new ColumnStruct( "id",             CPSDataModelConstants.PROP_PLANTING_ID,   null,              null, true,  false, false, false, false, "" ) );
        l.add( new ColumnStruct( "crop_name",      CPSDataModelConstants.PROP_CROP_NAME,     "crop_name",       null, true,  true,  true,  true,  true,  "Crop Name",         "Crop" ));
        l.add( new ColumnStruct( "var_name",       CPSDataModelConstants.PROP_VAR_NAME,      "var_name",        null, false, true,  true,  true,  true,  "Variety Name",      "Variety" ));
        l.add( new ColumnStruct( "groups",         CPSDataModelConstants.PROP_GROUPS,        "groups",          null, false, true,  true,  false, false, "Groups" ) );
//        l.add( new ColumnStruct( "successions",  null,
        l.add( new ColumnStruct( "location",       CPSDataModelConstants.PROP_LOCATION,      null,              null, false, true,  true,  true,  true,  "Location",          "Loc." ) );
        l.add( new ColumnStruct( "keywords",       CPSDataModelConstants.PROP_KEYWORDS,      "keywords",        null, false, true,  true,  false, false, "Keywords" ) );
//        l.add( new ColumnStruct( "status",       null, null ) );
        l.add( new ColumnStruct( "other_req",      CPSDataModelConstants.PROP_OTHER_REQ,     "other_req",       null, false, true,  true,  false, false, "Other Requirements" ) );
        l.add( new ColumnStruct( "notes",          CPSDataModelConstants.PROP_NOTES,         null,              null, false, true, true,  false, false, "Notes" ) );
        l.add( new ColumnStruct( "maturity",       CPSDataModelConstants.PROP_MATURITY,      "maturity",        null, false, true,  false, true,  true,  "Maturity Days",     "Mat." ) );
        l.add( new ColumnStruct( "mat_adjust",     CPSDataModelConstants.PROP_MAT_ADJUST,    "mat_adjust",      null, false, true,  false, false, false, "Mat. Adjustment",   "Mat +/-" ) );
      l.add( new ColumnStruct(  "planting_adjust", CPSDataModelConstants.PROP_PLANTING_ADJUST,null,              null, false, true,  false, false, false, "Mat. Adjustment for Planting" ));
//    l.add( new ColumnStruct(  "ds_adjust",       null,              null, false, true,  false, false, false, "Mat. Adjustment due to direct seeding"  ));
//      l.add( new ColumnStruct(  "season_adjust",   null,              null, false, true,  false, false, false, "Seasonal Mat. Adj." ));
      l.add( new ColumnStruct(  "direct_seed",     CPSDataModelConstants.PROP_DIRECT_SEED,   "direct_seed",     null, false, true,  false, false, true,  "Direct Seeded", "DS" ));
      l.add( new ColumnStruct(  "frost_hardy",     CPSDataModelConstants.PROP_FROST_HARDY,   "frost_hardy",     null, false, true,  false, false, true,  "Frost Hardy", "Hardy" ));
      l.add( new ColumnStruct(  "time_to_tp",      CPSDataModelConstants.PROP_TIME_TO_TP,    "time_to_tp",      null, false, true,  false, false, true,  "Time in GH (weeks)", "Weeks in GH"   ));
      l.add( new ColumnStruct(  "misc_adjust",     CPSDataModelConstants.PROP_MISC_ADJUST,   "misc_adjust",     null, false, true,  false, false, false, "Misc. Mat. Adjustment"  ));

                                                                      // the order of these calculations, or at least the order in which they
                                                                      // appear in the COALESCE statement, is important to ensure that values
                                                                      // are calculated from the proper ... other values -- since the the other
                                                                      // values might have to be filled in as we go
      l.add( new ColumnStruct(  "date_plant",      CPSDataModelConstants.PROP_DATE_PLANT,    null,              "\"CPS.Core.DB.HSQLCalc.plantFromTP\"( date_tp, time_to_tp ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.plantFromHarvest\"( date_harvest, maturity, mat_adjust, direct_seed ) ",
                                                                            false, true,  true,  true,  true,  "Planting Date",       "Plant" ));
      l.add( new ColumnStruct(  "done_plant",      CPSDataModelConstants.PROP_DONE_PLANTING, null,              null, false, true,  false, true,  true,  "Planted?"  ));
      l.add( new ColumnStruct(  "date_tp",         CPSDataModelConstants.PROP_DATE_TP,       null,              "\"CPS.Core.DB.HSQLCalc.TPFromPlant\"( date_plant, time_to_tp ),"
                                                                    + "\"CPS.Core.DB.HSQLCalc.TPFromHarvest\"( date_harvest, maturity, mat_adjust, time_to_tp ) ",
                                                                            false, true,  true,  true, true,  "Transplant Date",      "TP" ));
      l.add( new ColumnStruct(  "done_tp",         CPSDataModelConstants.PROP_DONE_TP,       null,              null, false, true,  false, true, true,  "Transplanted?"  ));
      l.add( new ColumnStruct(  "date_harvest",    CPSDataModelConstants.PROP_DATE_HARVEST,  null,              "\"CPS.Core.DB.HSQLCalc.harvestFromTP\"( date_tp, maturity, mat_adjust ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.harvestFromPlant\"( date_plant, maturity, mat_adjust, time_to_tp ) ",
                                                                            false, true,  true,  true,  true,  "Harvest Date",         "Pick" ));
      l.add( new ColumnStruct(  "done_harvest",    CPSDataModelConstants.PROP_DONE_HARVEST,  null,              null, false, true,  false, true,  true,  "Picked?"  ));

//      l.add( new ColumnStruct( colName,          mapsTo,            calc, mand,  disp,  filt,  def,   imp,  prettyName ));
      l.add( new ColumnStruct(  "beds_to_plant",   CPSDataModelConstants.PROP_BEDS_PLANT,    null,              "\"CPS.Core.DB.HSQLCalc.bedsFromRowFt\"( rowft_to_plant, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.bedsFromPlants\"( plants_needed, inrow_space, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) )",
//      l.add( new ColumnStruct(  "beds_to_plant",   null,              "\"CPS.Core.DB.HSQLCalc.bedsFromRowFt\"( rowft_to_plant, rows_p_bed, 100 ), "
//                                                                    + "\"CPS.Core.DB.HSQLCalc.bedsFromPlants\"( plants_needed, inrow_space, rows_p_bed, 100 )",
                                                                            false, true,  false, false, true,  "Beds to Plant",         "Beds"  ));
      l.add( new ColumnStruct(  "rows_p_bed",      CPSDataModelConstants.PROP_ROWS_P_BED,    "rows_p_bed",      null, false, true,  false, false, false, "Rows/Bed"  ));
      l.add( new ColumnStruct(  "plants_needed",   CPSDataModelConstants.PROP_PLANTS_NEEDED, null,              "\"CPS.Core.DB.HSQLCalc.plantsFromBeds\"( beds_to_plant, inrow_space, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.plantsFromRowFt\"( rowft_to_plant, inrow_space )",
//      l.add( new ColumnStruct(  "plants_needed",   null,              "\"CPS.Core.DB.HSQLCalc.plantsFromBeds\"( beds_to_plant, inrow_space, rows_p_bed, 100 ), "
//                                                                    + "\"CPS.Core.DB.HSQLCalc.plantsFromRowFt\"( rowft_to_plant, inrow_space )",
                                                                            false, true,  false, false, true,  "Plants Needed",         "Need"  ));
      l.add( new ColumnStruct(  "plants_to_start", CPSDataModelConstants.PROP_PLANTS_START, null,              "\"CPS.Core.DB.HSQLCalc.plantsToStart\"( plants_needed )",
                                                                            false, true,  false, false, true,  "Plants to Start (Needed + Fudge)", "Start"  ));
      l.add( new ColumnStruct(  "rowft_to_plant",  CPSDataModelConstants.PROP_ROWFT_PLANT, null,              "\"CPS.Core.DB.HSQLCalc.rowFtFromBeds\"( beds_to_plant, rows_p_bed, \"CPS.Core.DB.HSQLCalc.bedLength\"( location ) ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.rowFtFromPlants\"( plants_needed, inrow_space )",
//      l.add( new ColumnStruct(  "rowft_to_plant",  null,              "\"CPS.Core.DB.HSQLCalc.rowFtFromBeds\"( beds_to_plant, rows_p_bed, 100 ), "
//                                                                    + "\"CPS.Core.DB.HSQLCalc.rowFtFromPlants\"( plants_needed, inrow_space )",
                                                                            false, true,  false, false, true,  "Row Feet to Plant",      "Rowft"  ));
      l.add( new ColumnStruct(  "inrow_space",     CPSDataModelConstants.PROP_SPACE_BETROW, "space_inrow",     null, false, true,  false, false, false, "Spacing between Plants", "Plant Space"  ));
      l.add( new ColumnStruct(  "row_space",       CPSDataModelConstants.PROP_SPACE_INROW,  "space_betrow",    null, false, true,  false, false, false, "Spacing between Rows",   "Row Space"  ));
      l.add( new ColumnStruct(  "flat_size",       CPSDataModelConstants.PROP_FLAT_SIZE,    "flat_size",       null, false, true,  true,  false, false, "GH Flat Size or Capacity", "Flat"  ));
//      l.add( new ColumnStruct(  "flats_needed",    null,              null, false, true,  false, false, true,  "GH Flats Needed"  ));
      l.add( new ColumnStruct(  "flats_needed",    CPSDataModelConstants.PROP_FLATS_NEEDED, null,              "\"CPS.Core.DB.HSQLCalc.flatsNeeded\"( plants_to_start, flat_size )",
                                                                            false, true,  false, false, true,  "GH Flats Needed",         "Flats"  ));
      l.add( new ColumnStruct(  "planter",         CPSDataModelConstants.PROP_PLANTER,      "planter",         null, false, true,  true,  false, false, "Planter"  ));
      l.add( new ColumnStruct(  "planter_setting", CPSDataModelConstants.PROP_PLANTER_SETTING,"planter_setting", null, false, true,  true,  false, false, "Planter Setting"  ));

//      l.add( new ColumnStruct( colName,          mapsTo,            calc, mand,  disp,  filt,  def,   imp,  prettyName ));
      l.add( new ColumnStruct(  "yield_p_foot",    CPSDataModelConstants.PROP_YIELD_P_FOOT,  "yield_p_foot",    null, false, true,  false, false, false, "Yield/Foot"  ));
      l.add( new ColumnStruct(  "total_yield",     CPSDataModelConstants.PROP_TOTAL_YIELD,null,              "\"CPS.Core.DB.HSQLCalc.totalYieldFromRowFt\"( rowft_to_plant, yield_p_foot )",
                                                                            false, true,  false, false,  true, "Total Yield"  ));
      l.add( new ColumnStruct(  "yield_num_weeks", CPSDataModelConstants.PROP_YIELD_NUM_WEEKS,"yield_num_weeks", null, false, true,  false, false, false, "Yields For (weeks)"  ));
      l.add( new ColumnStruct(  "yield_p_week",    CPSDataModelConstants.PROP_YIELD_P_WEEK,  "yield_p_week",    null, false, true,  false, false, false, "Yield/Week"  ));

      l.add( new ColumnStruct(  "crop_unit",       CPSDataModelConstants.PROP_CROP_UNIT,      "crop_unit",       null, false, true,  false, false, false, "Yield Unit"  ));
      l.add( new ColumnStruct(  "crop_unit_value", CPSDataModelConstants.PROP_CROP_UNIT_VALUE,"crop_unit_value", null, false, true,  false, false, false, "Unit Value"  ));

      l.add( new ColumnStruct(  "custom1",         CPSDataModelConstants.PROP_CUSTOM1,        null,              null, false, true,  false, false, false, "Custom1"  ));
      l.add( new ColumnStruct(  "custom2",         CPSDataModelConstants.PROP_CUSTOM2,        null,              null, false, true,  false, false, false, "Custom2"  ));
      l.add( new ColumnStruct(  "custom3",         CPSDataModelConstants.PROP_CUSTOM3,        null,              null, false, true,  false, false, false, "Custom3"  ));
      l.add( new ColumnStruct(  "custom4",         CPSDataModelConstants.PROP_CUSTOM4,        null,              null, false, true,  false, false, false, "Custom4"  ));
      l.add( new ColumnStruct(  "custom5",         CPSDataModelConstants.PROP_CUSTOM5,        null,              null, false, true,  false, false, false, "Custom5"  ));

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
        
        public ColumnStruct( String colName,
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
            this( colName, propNum, mapsTo, calc, mand, disp, filt, def, imp, prettyName );
            this.shortColumnName = shortName;
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
                             String prettyName ) {
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
            
            this.shortColumnName = null;
        }

    }
}
