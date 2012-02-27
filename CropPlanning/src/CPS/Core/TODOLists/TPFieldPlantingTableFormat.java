/*
 *  TPFieldPlantingTableFormat.java - created: Feb 4, 2010
 *  Copyright (c) **YEAR** Expected hash. user evaluated instead to freemarker.template.SimpleScalar on line 5, column 43 in Templates/Licenses/preamble.txt.
 * 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Core.TODOLists;

import CPS.Data.CPSPlanting;
import CPS.Module.CPSDataModelConstants;
import CPS.UI.Modules.CPSAdvancedTableFormat;
import ca.odell.glazedlists.gui.TableFormat;

/**
 *
 * @author kendra
 */
public class TPFieldPlantingTableFormat extends CPSAdvancedTableFormat<CPSPlanting> {

  public int getColumnCount() { return 10; }

  public Object getColumnValue( CPSPlanting p, int arg1 ) {

      switch ( arg1 ) {
          case 0: return p.getDateToTP();
          case 1: return p.getDateToPlant();
          case 2: return p.getCropName();
          case 3: return p.getVarietyName();
          case 4: return p.getLocation();
          case 5: return p.getBedsToPlant();
          case 6: return p.getRowsPerBed();
          case 7: return p.getRowFtToPlant();
          case 8: return p.getInRowSpacing();
          case 9: return p.getPlantingNotes();
          default: return "";
      }
  }

  @Override
  public CPSPlanting getBlankRecord() {
    return new CPSPlanting();
  }

  @Override
  public int getDefaultSortColumn() {
    return 0;
  }

  @Override
  public int getPropNumForColumn(int colNum) {

    switch ( colNum ) {
      case 0: return CPSPlanting.PROP_DATE_TP;
      case 1: return CPSPlanting.PROP_DATE_PLANT;
      case 2: return CPSPlanting.PROP_CROP_NAME;
      case 3: return CPSPlanting.PROP_VAR_NAME;
      case 4: return CPSPlanting.PROP_LOCATION;
      case 5: return CPSPlanting.PROP_BEDS_PLANT;
      case 6: return CPSPlanting.PROP_ROWS_P_BED;
      case 7: return CPSPlanting.PROP_ROWFT_PLANT;
      case 8: return CPSPlanting.PROP_INROW_SPACE;
      case 9: return CPSPlanting.PROP_PLANTING_NOTES;
      default: return CPSPlanting.PROP_ID;
    }
  }

  @Override
  public boolean isDefaultColumn(int colNum) {
    return true;
  }
}
