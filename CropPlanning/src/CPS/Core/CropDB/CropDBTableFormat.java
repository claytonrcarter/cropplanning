/* CropDBTableFormat.java - Created: Nov 9, 2009
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

package CPS.Core.CropDB;

import CPS.Data.CPSCrop;
import CPS.UI.Modules.CPSAdvancedTableFormat;

public class CropDBTableFormat extends CPSAdvancedTableFormat<CPSCrop> {

   public int getColumnCount() { return 35; }

   @Override
   public CPSCrop getBlankRecord() { return new CPSCrop(); }

   @Override
   public int getDefaultSortColumn() {
     return 0; // crop name
   }


   public int getPropNumForColumn( int colNum ) {

      switch ( colNum ) {
            case 0: return CPSCrop.PROP_CROP_NAME;
            case 1: return CPSCrop.PROP_VAR_NAME;
            case 2: return CPSCrop.PROP_MATURITY;

            case 3: return CPSCrop.PROP_CROP_DESC;
            case 4: return CPSCrop.PROP_FAM_NAME;
            case 5: return CPSCrop.PROP_BOT_NAME;

            case 6: return CPSCrop.PROP_GROUPS;
            case 7: return CPSCrop.PROP_KEYWORDS;
            case 8: return CPSCrop.PROP_OTHER_REQ;
            case 9: return CPSCrop.PROP_NOTES;

            case 10: return CPSCrop.PROP_FROST_HARDY;

            case 11: return CPSCrop.PROP_DIRECT_SEED;
            case 12: return CPSCrop.PROP_DS_MAT_ADJUST;
            case 13: return CPSCrop.PROP_DS_ROWS_P_BED;
            case 14: return CPSCrop.PROP_DS_SPACE_BETROW;
            case 15: return CPSCrop.PROP_DS_PLANT_NOTES;

            case 16: return CPSCrop.PROP_TRANSPLANT;
            case 17: return CPSCrop.PROP_TP_MAT_ADJUST;
            case 18: return CPSCrop.PROP_TP_ROWS_BED;
            case 19: return CPSCrop.PROP_TP_SPACE_BETROW;
            case 20: return CPSCrop.PROP_TP_SPACE_INROW;
            case 21: return CPSCrop.PROP_TP_TIME_IN_GH;
            case 22: return CPSCrop.PROP_FLAT_SIZE;
            case 23: return CPSCrop.PROP_TP_PLANT_NOTES;
            case 24: return CPSCrop.PROP_POT_UP;
            case 25: return CPSCrop.PROP_POT_UP_NOTES;

            case 26: return CPSCrop.PROP_YIELD_FOOT;
            case 27: return CPSCrop.PROP_YIELD_WEEKS;
            case 28: return CPSCrop.PROP_YIELD_PER_WEEK;
            case 29: return CPSCrop.PROP_CROP_UNIT;
            case 30: return CPSCrop.PROP_CROP_UNIT_VALUE;

            case 31: return CPSCrop.PROP_SEEDS_PER_UNIT;
            case 32: return CPSCrop.PROP_SEED_UNIT;
            case 33: return CPSCrop.PROP_SEEDS_PER_DS;
            case 34: return CPSCrop.PROP_SEEDS_PER_TP;

            default: return -1;
   }
   }

   public boolean isDefaultColumn( int colNum ) {

       switch ( colNum ) {
            case 0: case 1: case 2:
               return true;

            default:
               return false;
        }

    }

    public Object getColumnValue( CPSCrop c, int colNum ) {

        switch ( colNum ) {
            case 0: return c.getCropName();
            case 1: return c.getVarietyName();
            case 2: return c.getMaturityDaysString();

            case 3: return c.getCropDescription();
            case 4: return c.getFamilyName();
            case 5: return c.getBotanicalName();

            case 6: return c.getGroups();
            case 7: return c.getKeywords();
            case 8: return c.getOtherRequirements();
            case 9: return c.getNotes();

            case 10: return c.isFrostHardy();

            case 11: return c.isDirectSeeded();
            case 12: return c.getDSMaturityAdjustString();
            case 13: return c.getDSRowsPerBedString();
            case 14: return c.getDSSpaceBetweenRowString();
            case 15: return c.getDSPlantNotes();

            case 16: return c.isTransplanted();
            case 17: return c.getTPMaturityAdjustString();
            case 18: return c.getTPRowsPerBedString();
            case 19: return c.getTPSpaceBetweenRowString();
            case 20: return c.getTPSpaceInRowString();
            case 21: return c.getTPTimeInGHString();
            case 22: return c.getTPFlatSize();
            case 23: return c.getTPPlantNotes();
            case 24: return c.isPottedUp();
            case 25: return c.getTPPotUpNotes();

            case 26: return c.getYieldPerFootString();
            case 27: return c.getYieldNumWeeksString();
            case 28: return c.getYieldPerWeekString();
            case 29: return c.getCropYieldUnit();
            case 30: return c.getCropUnitValueString();

            case 31: return c.getSeedsPerUnitString();
            case 32: return c.getSeedUnit();
            case 33: return c.getSeedsPerDSString();
            case 34: return c.getSeedsPerTPString();

            default: return "";
        }
    }


}
