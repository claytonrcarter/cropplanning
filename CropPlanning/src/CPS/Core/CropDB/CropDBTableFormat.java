/* CropDBTableFormat.java - Created: Nov 9, 2009
 * Copyright (C) 2009 Clayton Carter
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

package CPS.Core.CropDB;

import CPS.Data.CPSCrop;
import CPS.UI.Modules.CPSAdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

public class CropDBTableFormat extends CPSAdvancedTableFormat<CPSCrop>
                               implements WritableTableFormat<CPSCrop> {

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
            case 2: return c.getMaturityDays();

            case 3: return c.getCropDescription();
            case 4: return c.getFamilyName();
            case 5: return c.getBotanicalName();

            case 6: return c.getGroups();
            case 7: return c.getKeywords();
            case 8: return c.getOtherRequirements();
            case 9: return c.getNotes();

            case 10: return c.isFrostHardy();

            case 11: return c.isDirectSeeded();
            case 12: return c.getDSMaturityAdjust();
            case 13: return c.getDSRowsPerBed();
            case 14: return c.getDSSpaceBetweenRow();
            case 15: return c.getDSPlantNotes();

            case 16: return c.isTransplanted();
            case 17: return c.getTPMaturityAdjust();
            case 18: return c.getTPRowsPerBed();
            case 19: return c.getTPSpaceBetweenRow();
            case 20: return c.getTPSpaceInRow();
            case 21: return c.getTPTimeInGH();
            case 22: return c.getTPFlatSize();
            case 23: return c.getTPPlantNotes();
            case 24: return c.isPottedUp();
            case 25: return c.getTPPotUpNotes();

            case 26: return c.getYieldPerFoot();
            case 27: return c.getYieldNumWeeks();
            case 28: return c.getYieldPerWeek();
            case 29: return c.getCropYieldUnit();
            case 30: return c.getCropUnitValue();

            case 31: return c.getSeedsPerUnit();
            case 32: return c.getSeedUnit();
            case 33: return c.getSeedsPerDS();
            case 34: return c.getSeedsPerTP();

            default: return "";
        }
    }

  public boolean isEditable(CPSCrop baseObject, int column) {
    return true;
  }

  public CPSCrop setColumnValue( CPSCrop c,
                                 Object editedValue,
                                 int colNum ) {

    String s = "";
    if ( ( ! ( editedValue instanceof Boolean )) &&
            editedValue != null ) {
      if ( editedValue instanceof Integer )
        s = c.formatInt( (Integer) editedValue );
      else if ( editedValue instanceof Float )
        s = c.formatFloat( (Float) editedValue );
      else
        s = editedValue.toString();
    }

    // do this because this method is called every time a row is unselected
    // as when traversing the table by pressing UP or DOWN, so this just
    // discards the times when there's no actual edit
    if ( editedValue != null &&
         editedValue.equals( getColumnValue( c, colNum ) ) )
      return null;

    switch ( colNum ) {
      case 0: c.setCropName( s ); break;
      case 1: c.setVarietyName( s ); break;
      case 2: c.setMaturityDays( s ); break;

      case 3: c.setCropDescription( s ); break;
      case 4: c.setFamilyName( s ); break;
      case 5: c.setBotanicalName( s ); break;

      case 6: c.setGroups( s ); break;
      case 7: c.setKeywords( s ); break;
      case 8: c.setOtherRequirements( s ); break;
      case 9: c.setNotes( s ); break;

      case 10: c.setFrostHardy( (Boolean) editedValue ); break;

      case 11: c.setDirectSeeded( (Boolean) editedValue ); break;
      case 12: c.setDSMaturityAdjust( s ); break;
      case 13: c.setDSRowsPerBed( s ); break;
      case 14: c.setDSSpaceBetweenRow( s ); break;
      case 15: c.setDSPlantNotes( s ); break;

      case 16: c.setTransplanted( (Boolean) editedValue ); break;
      case 17: c.setTPMaturityAdjust( s ); break;
      case 18: c.setTPRowsPerBed( s ); break;
      case 19: c.setTPSpaceBetweenRow( s ); break;
      case 20: c.setTPSpaceInRow( s ); break;
      case 21: c.setTPTimeInGH( s ); break;
      case 22: c.setTPFlatSize( s ); break;
      case 23: c.setTPPlantNotes( s ); break;
      case 24: c.setTPPottedUp( (Boolean) editedValue ); break;
      case 25: c.setTPPotUpNotes( s ); break;

      case 26: c.setYieldPerFoot( s ); break;
      case 27: c.setYieldNumWeeks( s ); break;
      case 28: c.setYieldPerWeek( s ); break;
      case 29: c.setCropYieldUnit( s ); break;
      case 30: c.setCropUnitValue( s ); break;

      case 31: c.setSeedsPerUnit( s ); break;
      case 32: c.setSeedUnit( s ); break;
      case 33: c.setSeedsPerDS( s ); break;
      case 34: c.setSeedsPerTP( s ); break;

      default: return null;
    }

    return c;

  }




}
