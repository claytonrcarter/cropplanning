/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.Data.CPSPlanting;
import CPS.UI.Modules.CPSAdvancedTableFormat;

/**
 *
 * @author crcarter
 */
public class AvailabilityTableFormat extends CPSAdvancedTableFormat<CPSPlanting> {

  public int getColumnCount() { return 4; }

  public String getColumnName(int column) {

    CPSPlanting p = new CPSPlanting();

    switch ( column ) {
      case 0: return p.getDatum( CPSPlanting.PROP_CROP_NAME ).getName();
      case 1: return p.getDatum( CPSPlanting.PROP_VAR_NAME ).getName();
      case 2: return "Plantings";
      case 3: return "Harvest Dates";
      default: return "";
    }
    
  }

  public Object getColumnValue( CPSPlanting p, int column) {

    switch ( column ) {
      case 0: return p.getCropName();
      case 1: return p.getVarietyName();
      case 2: return p.getMaturityDays();
      case 3: return p.getCustomField1();
      default: return "";
    }

  }

  @Override
  public boolean isDefaultColumn(int colNum) { return true; }

  @Override
  public int getDefaultSortColumn() { return 0; }

  @Override
  public int getPropNumForColumn(int colNum) {
    switch ( colNum ) {
      case 0: return CPSPlanting.PROP_CROP_NAME;
      case 1: return CPSPlanting.PROP_VAR_NAME;
      case 2: return CPSPlanting.PROP_MAT_ADJUST;
      case 3: return CPSPlanting.PROP_CUSTOM1;
      default: return CPSPlanting.PROP_ID;
    }
  }

  @Override
  public CPSPlanting getBlankRecord() { return new CPSPlanting(); }



}
