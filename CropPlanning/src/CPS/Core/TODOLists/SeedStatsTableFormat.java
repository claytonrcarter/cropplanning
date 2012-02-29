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


    public int getColumnCount() { return 12; }

    public String getColumnName( int arg0 ) {

        CPSPlanting p = new CPSPlanting();

        switch ( arg0 ) {
            case 0: return p.getDatum( CPSPlanting.PROP_CROP_NAME ).getName();
            case 1: return p.getDatum( CPSPlanting.PROP_VAR_NAME ).getName();
            case 2: return "Plantings";
            case 3: return p.getDatum( CPSPlanting.PROP_DIRECT_SEED ).getName();
            case 4: return p.getDatum( CPSPlanting.PROP_BEDS_PLANT ).getName();
            case 5: return p.getDatum( CPSPlanting.PROP_ROWFT_PLANT ).getName();
            case 6: return p.getDatum( CPSPlanting.PROP_INROW_SPACE ).getName();
            case 7: return p.getDatum( CPSPlanting.PROP_PLANTS_START ).getName();
            case 8: return p.getDatum( CPSPlanting.PROP_FLATS_NEEDED ).getName();
            case 9: return p.getDatum( CPSPlanting.PROP_FLAT_SIZE ).getName();
            case 10: return "Seeds per Plug or Plant";
            case 11: return "Seeds Needed";
            default: return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int arg1 ) {

      String s;

        switch ( arg1 ) {
            case 0: s = p.getCropName(); break;
            case 1: s = p.getVarietyName(); break;
            case 2: s = p.getMaturityDaysString(); break;
            case 3: s = p.isDirectSeeded() ? "X" : ""; break;
            case 4: s = p.getBedsToPlantString(); break;
            case 5: s = p.getRowFtToPlantString(); break;
            case 6: s = p.isDirectSeeded() ? "" : p.getInRowSpacingString(); break;
            case 7: s = p.isDirectSeeded() ? "" : p.getPlantsToStartString(); break;
            case 8: s = p.isDirectSeeded() ? "" : p.getFlatsNeededString(); break;
            case 9: s = p.isDirectSeeded() ? "" : p.getFlatSizeCapacity().toString(); break;
            default: s = "";
        }

        return s.equals("0") ? "" : s;
    }


}
