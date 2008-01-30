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
        for ( ColumnStruct cs : m )
            if ( cs.displayable )
                als.add( new String[] { cs.columnName, cs.prettyColumnName } );
        
        return als;
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
    
    
    private void buildCropColumnMap() {
        ArrayList<ColumnStruct> l = new ArrayList();

//      l.add( new ColumnStruct( colName,          mapsTo, calc, mand,  disp,  filt,  def,   imp,   prettyName ));
        l.add( new ColumnStruct( "id",             null,   null, true,  false, false, false, false, "" ));
        l.add( new ColumnStruct( "crop_name",      null,   null, true,  true,  true,  true,  true,  "Crop Name" ));
        l.add( new ColumnStruct( "var_name",       null,   null, false, true,  true,  true,  true,  "Variety Name" ));
        l.add( new ColumnStruct( "bot_name",       null,   null, false, false, true,  false, false, "Botanical Name" ));
        l.add( new ColumnStruct( "description",    null,   null, false, false, true,  false, false, "Description" ));
        l.add( new ColumnStruct( "keywords",       null,   null, false, true,  true,  false, false, "Keywords" ));
        l.add( new ColumnStruct( "other_req",      null,   null, false, true,  true,  false, false, "Other Requirements" ));
        l.add( new ColumnStruct( "notes",          null,   null, false, false, true,  false, false, "Notes" ));
        l.add( new ColumnStruct( "maturity",       null,   null, false, true,  false, true,  true , "Maturity Days" ));
        l.add( new ColumnStruct( "time_to_tp",     null,   null, false, true,  false, false, true , "Time In GH (weeks)" ));
        l.add( new ColumnStruct( "rows_p_bed",     null,   null, false, true,  false, false, true , "Rows/Bed" ));
        l.add( new ColumnStruct( "space_inrow",    null,   null, false, true,  false, false, true , "Space between Plants" ));
        l.add( new ColumnStruct( "space_betrow",   null,   null, false, true,  false, false, false, "Space between Rows" ));
        l.add( new ColumnStruct( "flat_size",      null,   null, false, true,  true,  false, false, "Plug Flat Size/Capacity" ));
        l.add( new ColumnStruct( "planter",        null,   null, false, true,  true,  false, false, "Planter" ));
        l.add( new ColumnStruct( "planter_setting",null,   null, false, true,  true,  false, false, "Planter Setting" ));
        l.add( new ColumnStruct( "yield_p_foot",   null,   null, false, true,  false, false, true , "Yield/Foot" ));
        l.add( new ColumnStruct( "yield_num_weeks",null,   null, false, true,  false, false, false, "Yields For (weeks)" ));
        l.add( new ColumnStruct( "yield_p_week",   null,   null, false, true,  false, false, false, "Yield/Week" ));
        l.add( new ColumnStruct( "crop_unit",      null,   null, false, true,  true,  false, false, "Yield Unit" ));
        l.add( new ColumnStruct( "crop_unit_value",null,   null, false, true,  false, false, true , "Unit Value" ));
        l.add( new ColumnStruct( "fudge",          null,   null, false, false, false, false, false, "Fudge Factor" ));
        l.add( new ColumnStruct( "mat_adjust",     null,   null, false, true,  false, false, false, "Mat. Adjustment" ));
        l.add( new ColumnStruct( "misc_adjust",    null,   null, false, true,  false, false, false, "Misc. Mat. Adjustment" ));
        l.add( new ColumnStruct( "fam_name",       null,   null, false, true,  true,  true,  true,  "Family Name" ));
        l.add( new ColumnStruct( "groups",         null,   null, false, true,  true,  false, false, "Groups" ));
//        l.add( new ColumnStruct( "successions",    null,   null, false, false, false, false, false, "Successions" ));
//      l.add( new ColumnStruct( colName,          mapsTo, calc, mand,  disp,  filt,  def,   imp,   prettyName ));

        cropColumnMap = l;
    }


    private void buildPlantingColumnMap() {
        ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();

//      l.add( new ColumnStruct( colName,          mapsTo,            calc, mand,  disp,  filt,  def,   imp,  prettyName ));

        l.add( new ColumnStruct( "id",             null,              null, true,  false, false, false, false, "" ) );
        l.add( new ColumnStruct( "crop_name",      "crop_name",       null, true,  true,  true,  true,  true,  "Crop Name" ));
        l.add( new ColumnStruct( "var_name",       "var_name",        null, false, true,  true,  true,  true,  "Variety Name" ));
        l.add( new ColumnStruct( "groups",         "groups",          null, false, true,  true,  false, false, "Groups" ) );
//        l.add( new ColumnStruct( "successions",  null,
        l.add( new ColumnStruct( "location",       null,              null, false, true,  true,  true,  true,  "Location" ) );
        l.add( new ColumnStruct( "keywords",       "keywords",        null, false, true,  true,  false, false, "Keywords" ) );
//        l.add( new ColumnStruct( "status",       null, null ) );
        l.add( new ColumnStruct( "other_req",      "other_req",       null, false, true,  true,  false, false, "Other Requirements" ) );
        l.add( new ColumnStruct( "notes",          null,              null, false, false, true,  false, false, "Notes" ) );
        l.add( new ColumnStruct( "maturity",       "maturity",        null, false, true,  false, true,  true,  "Maturity Days" ) );
        l.add( new ColumnStruct( "mat_adjust",     "mat_adjust",      null, false, true,  false, false, false, "Mat. Adjustment" ) );
      l.add( new ColumnStruct(  "planting_adjust", null,              null, false, true,  false, false, false, "Mat. Adjustment for Planting" ));
//    l.add( new ColumnStruct(  "ds_adjust",       null,              null, false, true,  false, false, false, "Mat. Adjustment due to direct seeding"  ));
      l.add( new ColumnStruct(  "season_adjust",   null,              null, false, true,  false, false, false, "Seasonal Mat. Adj." ));
      l.add( new ColumnStruct(  "time_to_tp",      "time_to_tp",      null, false, true,  false, false, true,  "Time in GH (weeks)"  ));
      l.add( new ColumnStruct(  "misc_adjust",     "misc_adjust",     null, false, true,  false, false, false, "Misc. Mat. Adjustment"  ));

      l.add( new ColumnStruct(  "date_plant",      null,              "\"CPS.Core.DB.HSQLCalc.plantFromHarvest\"( date_harvest, maturity ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.plantFromTP\"( date_tp, time_to_tp )",
                                                                            false, true,  true,  true,  true,  "Planting Date" ));
      l.add( new ColumnStruct(  "done_plant",      null,              null, false, true,  false, true,  true,  "Planted?"  ));
      l.add( new ColumnStruct(  "date_tp",         null,              "\"CPS.Core.DB.HSQLCalc.TPFromPlant\"( date_plant, time_to_tp )",
                                                                            false, true,  true,  false, true,  "Transplant Date" ));
      l.add( new ColumnStruct(  "done_tp",         null,              null, false, true,  false, false, true,  "Transplanted?"  ));
      l.add( new ColumnStruct(  "date_harvest",    null,              "\"CPS.Core.DB.HSQLCalc.harvestFromPlant\"( date_plant, maturity ) ",
                                                                            false, true,  true,  true,  true,  "Harvest Date" ));
      l.add( new ColumnStruct(  "done_harvest",    null,              null, false, true,  false, true,  true,  "Picked?"  ));

//      l.add( new ColumnStruct( colName,          mapsTo,            calc, mand,  disp,  filt,  def,   imp,  prettyName ));
      l.add( new ColumnStruct(  "beds_to_plant",   null,              "\"CPS.Core.DB.HSQLCalc.bedsFromRowFt\"( rowft_to_plant, rows_p_bed, 100 ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.bedsFromPlants\"( plants_needed, inrow_space, rows_p_bed, 100 )",
                                                                            false, true,  false, false, true,  "Beds to Plant"  ));
      l.add( new ColumnStruct(  "rows_p_bed",      "rows_p_bed",      null, false, true,  false, false, false, "Rows/Bed"  ));
      l.add( new ColumnStruct(  "plants_needed",   null,              "\"CPS.Core.DB.HSQLCalc.plantsFromBeds\"( beds_to_plant, inrow_space, rows_p_bed, 100 ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.plantsFromRowFt\"( rowft_to_plant, inrow_space )",
                                                                            false, true,  false, false, true,  "Plants Needed"  ));
      l.add( new ColumnStruct(  "plants_to_start", null,              "\"CPS.Core.DB.HSQLCalc.plantsToStart\"( plants_needed, .25 )",
                                                                            false, true,  false, false, true,  "Plants to Start (Needed + Fudge)"  ));
      l.add( new ColumnStruct(  "rowft_to_plant",  null,              "\"CPS.Core.DB.HSQLCalc.rowFtFromBeds\"( beds_to_plant, rows_p_bed, 100 ), "
                                                                    + "\"CPS.Core.DB.HSQLCalc.rowFtFromPlants\"( plants_needed, inrow_space )",
                                                                            false, true,  false, false, true,  "Row Feet to Plant"  ));
      l.add( new ColumnStruct(  "inrow_space",     "space_inrow",     null, false, true,  false, false, false, "Spacing between Plants"  ));
      l.add( new ColumnStruct(  "row_space",       "space_betrow",    null, false, true,  false, false, false, "Spacing between Rows"  ));
      l.add( new ColumnStruct(  "flat_size",       "flat_size",       null, false, true,  true,  false, false, "GH Flat Size or Capacity"  ));
//      l.add( new ColumnStruct(  "flats_needed",    null,              null, false, true,  false, false, true,  "GH Flats Needed"  ));
      l.add( new ColumnStruct(  "flats_needed",    null,              "\"CPS.Core.DB.HSQLCalc.flatsNeeded\"( plants_to_start, flat_size )",
                                                                            false, true,  false, false, true,  "GH Flats Needed"  ));
      l.add( new ColumnStruct(  "planter",         "planter",         null, false, true,  true,  false, false, "Planter"  ));
      l.add( new ColumnStruct(  "planter_setting", "planter_setting", null, false, true,  true,  false, false, "Planter Setting"  ));

//      l.add( new ColumnStruct( colName,          mapsTo,            calc, mand,  disp,  filt,  def,   imp,  prettyName ));
      l.add( new ColumnStruct(  "yield_p_foot",    "yield_p_foot",    null, false, true,  false, false, false, "Yield/Foot"  ));
      l.add( new ColumnStruct(  "total_yield",     null,              "\"CPS.Core.DB.HSQLCalc.totalYieldFromRowFt\"( rowft_to_plant, yield_p_foot )",
                                                                            false, true,  false, false,  true, "Total Yield"  ));
      l.add( new ColumnStruct(  "yield_num_weeks", "yield_num_weeks", null, false, true,  false, false, false, "Yields For (weeks)"  ));
      l.add( new ColumnStruct(  "yield_p_week",    "yield_p_week",    null, false, true,  false, false, false, "Yield/Week"  ));

      l.add( new ColumnStruct(  "crop_unit",       "crop_unit",       null, false, true,  false, false, false, "Yield Unit"  ));
      l.add( new ColumnStruct(  "crop_unit_value", "crop_unit_value", null, false, true,  false, false, false, "Unit Value"  ));

      l.add( new ColumnStruct(  "custom1",         null,              null, false, true,  false, false, false, "Custom1"  ));
      l.add( new ColumnStruct(  "custom2",         null,              null, false, true,  false, false, false, "Custom2"  ));
      l.add( new ColumnStruct(  "custom3",         null,              null, false, true,  false, false, false, "Custom3"  ));
      l.add( new ColumnStruct(  "custom4",         null,              null, false, true,  false, false, false, "Custom4"  ));
      l.add( new ColumnStruct(  "custom5",         null,              null, false, true,  false, false, false, "Custom5"  ));

      plantingColumnMap = l;
    }


    public class ColumnStruct {

        public String columnName;
        public String prettyColumnName;
        public String columnDescription;
        public String correlatedColumn;
        public String calculation;
        public boolean mandatory;
        public boolean displayable;
        public boolean filterable;
        public boolean displayByDefault;
        public boolean mostImportant;

        public ColumnStruct( String colName,
                             String mapsTo,
                             String calc,
                             boolean mand,
                             boolean disp,
                             boolean filt,
                             boolean def,
                             boolean imp,
                             String prettyName ) {
            this.columnName = colName;
            this.prettyColumnName = prettyName;
            this.columnDescription = "";
            this.correlatedColumn = mapsTo;
            this.calculation = calc;
            this.mandatory = mand;
            this.displayable = disp;
            this.filterable = filt;
            this.displayByDefault = def;
            this.mostImportant = imp;
        }

    }
}
