/* CropPlanTableFormat.java - Created: Nov 8, 2009
 * Copyright (C) 2009 Clayton Carter
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

package CPS.Core.CropPlans;

import CPS.Data.CPSPlanting;
import CPS.UI.Modules.CPSAdvancedTableFormat;

public class CropPlanTableFormat extends CPSAdvancedTableFormat<CPSPlanting> {

   public int getColumnCount() { return 47; }

   @Override
   public CPSPlanting getBlankRecord() {
      return new CPSPlanting();
   }

   @Override
   public int getPropNumForColumn( int colNum ) {

      switch ( colNum ) {
         case 0: return CPSPlanting.PROP_CROP_NAME;
         case 1: return CPSPlanting.PROP_VAR_NAME     ;
         case 2: return CPSPlanting.PROP_MATURITY     ;
         case 3: return CPSPlanting.PROP_LOCATION     ;

         // Dates
         // "effective" dates
         case 4: return CPSPlanting.PROP_DATE_PLANT   ;
         case 5: return CPSPlanting.PROP_DATE_TP      ;
         case 6: return CPSPlanting.PROP_DATE_HARVEST ;
         // planned dates
         case 7: return CPSPlanting.PROP_DATE_PLANT_PLAN;
         case 8: return CPSPlanting.PROP_DATE_TP_PLAN;
         case 9: return CPSPlanting.PROP_DATE_HARVEST_PLAN;
         // actual dates
         case 10: return CPSPlanting.PROP_DATE_PLANT_ACTUAL;
         case 11: return CPSPlanting.PROP_DATE_TP_ACTUAL;
         case 12: return CPSPlanting.PROP_DATE_HARVEST_ACTUAL;

         // Status Booleans
         case 13: return CPSPlanting.PROP_DONE_PLANTING;
         case 14: return CPSPlanting.PROP_DONE_TP      ;
         case 15: return CPSPlanting.PROP_DONE_HARVEST ;
         case 16: return CPSPlanting.PROP_IGNORE;

         // Static Data
         // inheritable
         case 17: return CPSPlanting.PROP_MAT_ADJUST   ;
         case 18: return CPSPlanting.PROP_ROWS_P_BED   ;
         case 19: return CPSPlanting.PROP_ROW_SPACE    ;
         case 20: return CPSPlanting.PROP_CROP_NOTES   ;

         case 21: return CPSPlanting.PROP_TIME_TO_TP      ;
         case 22: return CPSPlanting.PROP_INROW_SPACE  ;
         case 23: return CPSPlanting.PROP_FLAT_SIZE    ;
         case 24: return CPSPlanting.PROP_PLANTING_NOTES;

         // Calculated Data
         case 25: return CPSPlanting.PROP_BEDS_PLANT   ;
         case 26: return CPSPlanting.PROP_PLANTS_NEEDED;
         case 27: return CPSPlanting.PROP_ROWFT_PLANT  ;
         case 28: return CPSPlanting.PROP_PLANTS_START ;
         case 29: return CPSPlanting.PROP_FLATS_NEEDED ;

         // Yield
         // static
         case 30: return CPSPlanting.PROP_YIELD_P_FOOT ;
         case 31: return CPSPlanting.PROP_YIELD_NUM_WEEKS;
         case 32: return CPSPlanting.PROP_YIELD_P_WEEK ;
         case 33: return CPSPlanting.PROP_CROP_UNIT    ;
         case 34: return CPSPlanting.PROP_CROP_UNIT_VALUE;
         // calculated
         case 35: return CPSPlanting.PROP_TOTAL_YIELD  ;

         // Misc Metadata
         // bools
         case 36: return CPSPlanting.PROP_DIRECT_SEED  ;
         case 37: return CPSPlanting.PROP_FROST_HARDY  ;
         // Strings
         case 38: return CPSPlanting.PROP_GROUPS       ;
         case 39: return CPSPlanting.PROP_KEYWORDS     ;
         case 40: return CPSPlanting.PROP_OTHER_REQ    ;
         case 41: return CPSPlanting.PROP_NOTES        ;

         case 42: return CPSPlanting.PROP_CUSTOM1      ;
         case 43: return CPSPlanting.PROP_CUSTOM2      ;
         case 44: return CPSPlanting.PROP_CUSTOM3      ;
         case 45: return CPSPlanting.PROP_CUSTOM4      ;
         case 46: return CPSPlanting.PROP_CUSTOM5      ;

         default: return CPSPlanting.PROP_ID;
      }
   }

   @Override
   public boolean isDefaultColumn( int colNum ) {

      switch ( colNum ) {
         case 0: case 1: case 2: case 3:
         case 4: case 5: case 6:
            return true;

         default: return false;
      }
   }

   public Object getColumnValue( CPSPlanting p, int colNum ) {

      switch ( colNum ) {
         case 0: return p.getCropName();
         case 1: return p.getVarietyName();
         case 2: return p.getMaturityDays();
         case 3: return p.getLocation();

         // Dates
         // "effective" dates
         case 4: return p.getDateToPlant();
         case 5: return p.getDateToTP();
         case 6: return p.getDateToHarvest();
         // planned dates
         case 7: return p.getDateToPlantPlanned();
         case 8: return p.getDateToTPPlanned();
         case 9: return p.getDateToHarvestPlanned();
         // actual dates
         case 10: return p.getDateToPlantActual();
         case 11: return p.getDateToTPActual();
         case 12: return p.getDateToHarvestActual();

         // Status Booleans
         case 13: return p.getDonePlanting();
         case 14: return p.getDoneTP();
         case 15: return p.getDoneHarvest();
         case 16: return p.getIgnore();

         // Static Data
         // inheritable
         case 17: return p.getMatAdjust();
         case 18: return p.getRowsPerBed();
         case 19: return p.getRowSpacing();
         case 20: return p.getNotes();

         case 21: return p.getTimeToTP();
         case 22: return p.getInRowSpacing();
         case 23: return p.getFlatSize();
         case 24: return p.getPlantingNotes();

         // Calculated Data
         case 25: return p.getBedsToPlant();
         case 26: return p.getPlantsNeeded();
         case 27: return p.getRowFtToPlant();
         case 28: return p.getPlantsToStart();
         case 29: return p.getFlatsNeeded();

         // Yield
         // static
         case 30: return p.getYieldPerFoot();
         case 31: return p.getYieldNumWeeks();
         case 32: return p.getYieldPerWeek();
         case 33: return p.getCropYieldUnit();
         case 34: return p.getCropYieldUnitValue();
         // calculated
         case 35: return p.getTotalYield();

         // Misc Metadata
         // bools
         case 36: return p.isDirectSeeded();
         case 37: return p.isFrostHardy();
         // Strings
         case 38: return p.getGroups();
         case 39: return p.getKeywords();
         case 40: return p.getOtherRequirements();
         case 41: return p.getNotes();

         case 42: return p.getCustomField1();
         case 43: return p.getCustomField2();
         case 44: return p.getCustomField3();
         case 45: return p.getCustomField4();
         case 46: return p.getCustomField5();
         
         default: return "";
      }
    }




}
