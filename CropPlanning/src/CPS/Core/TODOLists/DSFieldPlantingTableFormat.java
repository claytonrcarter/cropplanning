/*
 *  DSFieldPlantingTableFormat.java - created: Feb 4, 2010
 *  Copyright (c) 2010 Clayton Carter
 * 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Core.TODOLists;

import CPS.Data.CPSPlanting;
import CPS.UI.Modules.CPSAdvancedTableFormat;

/**
 *
 * @author kendra
 */
public class DSFieldPlantingTableFormat extends CPSExportTableFormat<CPSPlanting> {

  public int getColumnCount() { return 8; }

  public Object getColumnValue( CPSPlanting p, int arg1 ) {

      switch ( arg1 ) {
          case 0: return p.getDateToPlant();
          case 1: return p.getCropName();
          case 2: return p.getVarietyName();
          case 3: return p.getLocation();
          case 4: return p.getBedsToPlant();
          case 5: return p.getRowsPerBed();
          case 6: return p.getRowFtToPlant();
          case 7: return p.getPlantingNotesInherited();
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
      case 0: return CPSPlanting.PROP_DATE_PLANT;
      case 1: return CPSPlanting.PROP_CROP_NAME;
      case 2: return CPSPlanting.PROP_VAR_NAME;
      case 3: return CPSPlanting.PROP_LOCATION;
      case 4: return CPSPlanting.PROP_BEDS_PLANT;
      case 5: return CPSPlanting.PROP_ROWS_P_BED;
      case 6: return CPSPlanting.PROP_ROWFT_PLANT;
      case 7: return CPSPlanting.PROP_PLANTING_NOTES;
      default: return CPSPlanting.PROP_ID;
    }
  }

  @Override
  public boolean isDefaultColumn(int colNum) {
    return true;
  }

  @Override
  public boolean isSummaryColumn( int colNum ) {
    return colNum == 0 || colNum == 1;
  }


}
