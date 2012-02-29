/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.gui.TableFormat;

/**
 *
 * @author crcarter
 */
public class SeedStatsTableFormat implements TableFormat<CPSPlanting> {


    public int getColumnCount() { return 7; }

    public String getColumnName( int arg0 ) {

        CPSPlanting p = new CPSPlanting();

        switch ( arg0 ) {
            case 0: return p.getDatum( CPSPlanting.PROP_CROP_NAME ).getName();
            case 1: return p.getDatum( CPSPlanting.PROP_VAR_NAME ).getName();
            case 2: return "Plantings";
            case 3: return p.getDatum( CPSPlanting.PROP_BEDS_PLANT ).getName();
            case 4: return p.getDatum( CPSPlanting.PROP_ROWFT_PLANT ).getName();
            case 5: return p.getDatum( CPSPlanting.PROP_PLANTS_START ).getName();
            case 6: return p.getDatum( CPSPlanting.PROP_FLATS_NEEDED ).getName();
            default: return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int arg1 ) {

      String s;

        switch ( arg1 ) {
            case 0: s = p.getCropName(); break;
            case 1: s = p.getVarietyName(); break;
            case 2: s = p.getMaturityDaysString(); break;
            case 3: s = p.getBedsToPlantString(); break;
            case 4: s = p.getRowFtToPlantString(); break;
            case 5: s = p.getPlantsToStartString(); break;
            case 6: s = p.getFlatsNeededString(); break;
            default: s = "";
        }

        return s.equals("0") ? "" : s;
    }


}
