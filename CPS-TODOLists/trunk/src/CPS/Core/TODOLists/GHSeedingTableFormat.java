/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Core.TODOLists;

import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.gui.TableFormat;

/**
 *
 * @author kendra
 */
public class GHSeedingTableFormat implements TableFormat<CPSPlanting> {

    public int getColumnCount() { return 9; }

    public String getColumnName( int arg0 ) {

        CPSPlanting p = new CPSPlanting();

        switch ( arg0 ) {
            case 0: return p.getDatum( p.PROP_CROP_NAME ).getName();
            case 1: return p.getDatum( p.PROP_VAR_NAME ).getName();
            case 2: return p.getDatum( p.PROP_MATURITY ).getName();
            case 3: return p.getDatum( p.PROP_DATE_PLANT ).getName();
            case 4: return p.getDatum( p.PROP_DONE_PLANTING ).getName();
            case 5: return p.getDatum( p.PROP_DATE_TP ).getName();
            case 6: return p.getDatum( p.PROP_DONE_TP ).getName();
            case 7: return p.getDatum( p.PROP_DATE_HARVEST ).getName();
            case 8: return p.getDatum( p.PROP_DONE_HARVEST ).getName();
            default: return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int arg1 ) {

        switch ( arg1 ) {
            case 0: return p.getCropName();
            case 1: return p.getVarietyName();
            case 2: return p.getMaturityDays();
            case 3: return p.getDateToPlant();
            case 4: return p.getDonePlanting();
            case 5: return p.getDateToTP();
            case 6: return p.getDoneTP();
            case 7: return p.getDateToHarvest();
            case 8: return p.getDoneHarvest();
            default: return "";
        }
    }

}
