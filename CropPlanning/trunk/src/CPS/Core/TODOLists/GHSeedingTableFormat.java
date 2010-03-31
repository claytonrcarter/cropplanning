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

    public int getColumnCount() { return 7; }

    public String getColumnName( int arg0 ) {

        CPSPlanting p = new CPSPlanting();

        switch ( arg0 ) {
            case 0: return p.getDatum( CPSPlanting.PROP_DATE_PLANT ).getName();
            case 1: return p.getDatum( CPSPlanting.PROP_CROP_NAME ).getName();
            case 2: return p.getDatum( CPSPlanting.PROP_VAR_NAME ).getName();
            case 3: return p.getDatum( CPSPlanting.PROP_PLANTS_NEEDED ).getName();
            case 4: return p.getDatum( CPSPlanting.PROP_PLANTS_START ).getName();
            case 5: return p.getDatum( CPSPlanting.PROP_FLAT_SIZE ).getName();
            case 6: return p.getDatum( CPSPlanting.PROP_FLATS_NEEDED ).getName();
            default: return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int arg1 ) {

        switch ( arg1 ) {
            case 0: return p.getDateToPlant();
            case 1: return p.getCropName();
            case 2: return p.getVarietyName();
            case 3: return p.getPlantsNeeded();
            case 4: return p.getPlantsToStart();
            case 5: return p.getFlatSize();
            case 6: return p.getFlatsNeeded();
            default: return "";
        }
    }

}
