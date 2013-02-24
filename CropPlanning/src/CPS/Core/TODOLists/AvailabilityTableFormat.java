/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.Data.CPSDateValidator;
import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.gui.TableFormat;

/**
 *
 * @author crcarter
 */
public class AvailabilityTableFormat implements TableFormat<CPSPlanting> {

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
      case 3: return CPSDateValidator.format( p.getDateToPlantActual() ,
                                              CPSDateValidator.DATE_FORMAT_MON_DAY )
                     + " - " +
                    CPSDateValidator.format( p.getDateToHarvestActual(),
                                             CPSDateValidator.DATE_FORMAT_MON_DAY );
      default: return "";
    }

  }



}
