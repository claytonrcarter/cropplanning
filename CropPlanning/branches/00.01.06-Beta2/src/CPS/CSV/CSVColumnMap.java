/* CSVColumnMap.java - created: Mar 29 2008
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

package CPS.CSV;

import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSModule;
import java.util.ArrayList;


public class CSVColumnMap {

   private ArrayList<ColumnStruct> plantingColumnMap;
   private ArrayList<ColumnStruct> cropColumnMap;


   public CSVColumnMap() {
       buildPlantingColumnMap();
       buildCropColumnMap();
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

      CPSModule.debug( "CSVColumnMap", "Error:NoSuchProperty (" + prop + ")" );
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
         {
            return cs.propertyNum;
         }
      }
      return 0;

   }


   private void buildCropColumnMap() {
       ArrayList<ColumnStruct> l = new ArrayList();


//      l.add( new ColumnStruct( colName,          propNum));
       l.add( new ColumnStruct( "id",             CPSDataModelConstants.PROP_CROP_ID));
       l.add( new ColumnStruct( "crop_name",      CPSDataModelConstants.PROP_CROP_NAME));
       l.add( new ColumnStruct( "var_name",       CPSDataModelConstants.PROP_VAR_NAME));
       l.add( new ColumnStruct( "bot_name",       CPSDataModelConstants.PROP_BOT_NAME));
       l.add( new ColumnStruct( "description",    CPSDataModelConstants.PROP_CROP_DESC));
       l.add( new ColumnStruct( "keywords",       CPSDataModelConstants.PROP_KEYWORDS));
       l.add( new ColumnStruct( "other_req",      CPSDataModelConstants.PROP_OTHER_REQ));
       l.add( new ColumnStruct( "notes",          CPSDataModelConstants.PROP_NOTES));
       l.add( new ColumnStruct( "maturity",       CPSDataModelConstants.PROP_MATURITY));
       l.add( new ColumnStruct( "time_to_tp",     CPSDataModelConstants.PROP_TIME_TO_TP));
       l.add( new ColumnStruct( "frost_hardy",    CPSDataModelConstants.PROP_FROST_HARDY));
       l.add( new ColumnStruct( "direct_seed",    CPSDataModelConstants.PROP_DIRECT_SEED));
       l.add( new ColumnStruct( "rows_p_bed",     CPSDataModelConstants.PROP_ROWS_P_BED));
       l.add( new ColumnStruct( "space_inrow",    CPSDataModelConstants.PROP_SPACE_INROW));
       l.add( new ColumnStruct( "space_betrow",   CPSDataModelConstants.PROP_SPACE_BETROW));
       l.add( new ColumnStruct( "similar_to",     CPSDataModelConstants.PROP_SIMILAR));
       l.add( new ColumnStruct( "flat_size",      CPSDataModelConstants.PROP_FLAT_SIZE));
       l.add( new ColumnStruct( "planter",        CPSDataModelConstants.PROP_PLANTER));
//       l.add( new ColumnStruct( "planter_setting",CPSDataModelConstants.PROP_PLANTER_SETTING));
       l.add( new ColumnStruct( "yield_p_foot",   CPSDataModelConstants.PROP_YIELD_P_FOOT));
       l.add( new ColumnStruct( "yield_num_weeks",CPSDataModelConstants.PROP_YIELD_NUM_WEEKS));
       l.add( new ColumnStruct( "yield_p_week",   CPSDataModelConstants.PROP_YIELD_P_WEEK));
       l.add( new ColumnStruct( "crop_unit",      CPSDataModelConstants.PROP_CROP_UNIT));
       l.add( new ColumnStruct( "crop_unit_value",CPSDataModelConstants.PROP_CROP_UNIT_VALUE));
       l.add( new ColumnStruct( "mat_adjust",     CPSDataModelConstants.PROP_MAT_ADJUST));
       l.add( new ColumnStruct( "fam_name",       CPSDataModelConstants.PROP_FAM_NAME));
       l.add( new ColumnStruct( "groups",         CPSDataModelConstants.PROP_GROUPS));

       cropColumnMap = l;
   }


   private void buildPlantingColumnMap() {
       ArrayList<ColumnStruct> l = new ArrayList<ColumnStruct>();

//      l.add( new ColumnStruct( colName,          propNum,

       l.add( new ColumnStruct( "id",             CPSDataModelConstants.PROP_PLANTING_ID));
       l.add( new ColumnStruct( "crop_name",      CPSDataModelConstants.PROP_CROP_NAME));
       l.add( new ColumnStruct( "var_name",       CPSDataModelConstants.PROP_VAR_NAME));
       l.add( new ColumnStruct( "groups",         CPSDataModelConstants.PROP_GROUPS));
       l.add( new ColumnStruct( "location",       CPSDataModelConstants.PROP_LOCATION));
       l.add( new ColumnStruct( "keywords",       CPSDataModelConstants.PROP_KEYWORDS));
       l.add( new ColumnStruct( "other_req",      CPSDataModelConstants.PROP_OTHER_REQ));
       l.add( new ColumnStruct( "notes",          CPSDataModelConstants.PROP_NOTES));
       l.add( new ColumnStruct( "maturity",       CPSDataModelConstants.PROP_MATURITY));
       l.add( new ColumnStruct( "mat_adjust",     CPSDataModelConstants.PROP_MAT_ADJUST));
       l.add( new ColumnStruct(  "planting_adjust", CPSDataModelConstants.PROP_PLANTING_ADJUST));
       l.add( new ColumnStruct(  "direct_seed",     CPSDataModelConstants.PROP_DIRECT_SEED));
       l.add( new ColumnStruct(  "frost_hardy",     CPSDataModelConstants.PROP_FROST_HARDY));
       l.add( new ColumnStruct(  "time_to_tp",      CPSDataModelConstants.PROP_TIME_TO_TP));
//       l.add( new ColumnStruct(  "misc_adjust",     CPSDataModelConstants.PROP_MISC_ADJUST));
       l.add( new ColumnStruct(   "status",        CPSDataModelConstants.PROP_STATUS));

     l.add( new ColumnStruct(  "date_plant",      CPSDataModelConstants.PROP_DATE_PLANT));
     l.add( new ColumnStruct(  "done_plant",      CPSDataModelConstants.PROP_DONE_PLANTING));
     l.add( new ColumnStruct(  "date_tp",         CPSDataModelConstants.PROP_DATE_TP));

     l.add( new ColumnStruct(  "done_tp",         CPSDataModelConstants.PROP_DONE_TP));
     l.add( new ColumnStruct(  "date_harvest",    CPSDataModelConstants.PROP_DATE_HARVEST));

     l.add( new ColumnStruct(  "done_harvest",    CPSDataModelConstants.PROP_DONE_HARVEST));
     l.add( new ColumnStruct(  "beds_to_plant",   CPSDataModelConstants.PROP_BEDS_PLANT));
     l.add( new ColumnStruct(  "rows_p_bed",      CPSDataModelConstants.PROP_ROWS_P_BED));
     l.add( new ColumnStruct(  "plants_needed",   CPSDataModelConstants.PROP_PLANTS_NEEDED));
     l.add( new ColumnStruct(  "plants_to_start", CPSDataModelConstants.PROP_PLANTS_START));
     l.add( new ColumnStruct(  "rowft_to_plant",  CPSDataModelConstants.PROP_ROWFT_PLANT));
     l.add( new ColumnStruct(  "inrow_space",     CPSDataModelConstants.PROP_SPACE_BETROW));
     l.add( new ColumnStruct(  "row_space",       CPSDataModelConstants.PROP_SPACE_INROW));
     l.add( new ColumnStruct(  "flat_size",       CPSDataModelConstants.PROP_FLAT_SIZE));
     l.add( new ColumnStruct(  "flats_needed",    CPSDataModelConstants.PROP_FLATS_NEEDED));
     l.add( new ColumnStruct(  "planter",         CPSDataModelConstants.PROP_PLANTER));
//     l.add( new ColumnStruct(  "planter_setting", CPSDataModelConstants.PROP_PLANTER_SETTING));

     l.add( new ColumnStruct(  "yield_p_foot",    CPSDataModelConstants.PROP_YIELD_P_FOOT));
     l.add( new ColumnStruct(  "total_yield",     CPSDataModelConstants.PROP_TOTAL_YIELD));
     l.add( new ColumnStruct(  "yield_num_weeks", CPSDataModelConstants.PROP_YIELD_NUM_WEEKS));
     l.add( new ColumnStruct(  "yield_p_week",    CPSDataModelConstants.PROP_YIELD_P_WEEK));

     l.add( new ColumnStruct(  "crop_unit",       CPSDataModelConstants.PROP_CROP_UNIT));
     l.add( new ColumnStruct(  "crop_unit_value", CPSDataModelConstants.PROP_CROP_UNIT_VALUE));

     l.add( new ColumnStruct(  "custom1",         CPSDataModelConstants.PROP_CUSTOM1));
     l.add( new ColumnStruct(  "custom2",         CPSDataModelConstants.PROP_CUSTOM2));
     l.add( new ColumnStruct(  "custom3",         CPSDataModelConstants.PROP_CUSTOM3));
     l.add( new ColumnStruct(  "custom4",         CPSDataModelConstants.PROP_CUSTOM4));
     l.add( new ColumnStruct(  "custom5",         CPSDataModelConstants.PROP_CUSTOM5));

     plantingColumnMap = l;
   }


   public class ColumnStruct {

       public String columnName;
       public int propertyNum;


       public ColumnStruct( String colName,int propNum ) {
           this.columnName = colName;
           this.propertyNum = propNum;
       }
   }
}
