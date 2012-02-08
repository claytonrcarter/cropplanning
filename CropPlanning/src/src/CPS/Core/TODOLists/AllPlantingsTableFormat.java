/*
 *  AllPlantingsTableFormat.java - created: Feb 4, 2010
 *  Copyright (c) **YEAR** Expected hash. user evaluated instead to freemarker.template.SimpleScalar on line 5, column 43 in Templates/Licenses/preamble.txt.
 * 
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
public class AllPlantingsTableFormat implements TableFormat<CPSPlanting> {

    public int getColumnCount() { return 13; }

    public String getColumnName( int arg0 ) {

        CPSPlanting p = new CPSPlanting();

        switch ( arg0 ) {
            case 0: return p.getDatum( CPSPlanting.PROP_CROP_NAME ).getName();
            case 1: return p.getDatum( CPSPlanting.PROP_VAR_NAME ).getName();
            case 2: return p.getDatum( CPSPlanting.PROP_IGNORE ).getName();
            case 3: return p.getDatum( CPSPlanting.PROP_DATE_PLANT ).getName();
            case 4: return p.getDatum( CPSPlanting.PROP_DONE_PLANTING ).getName();
            case 5: return p.getDatum( CPSPlanting.PROP_DATE_TP ).getName();
            case 6: return p.getDatum( CPSPlanting.PROP_DONE_TP ).getName();
            case 7: return p.getDatum( CPSPlanting.PROP_DIRECT_SEED ).getName();
            case 8: return p.getDatum( CPSPlanting.PROP_BEDS_PLANT ).getName();
            case 9: return p.getDatum( CPSPlanting.PROP_ROWFT_PLANT ).getName();
            case 10: return p.getDatum( CPSPlanting.PROP_PLANTS_NEEDED ).getName();
            case 11: return p.getDatum( CPSPlanting.PROP_FLATS_NEEDED ).getName();
            case 12: return p.getDatum( CPSPlanting.PROP_TOTAL_YIELD ).getName();
            default: return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int arg1 ) {

        switch ( arg1 ) {
            case 0: return p.getCropName();
            case 1: return p.getVarietyName();
            case 2: return p.getIgnore();
            case 3: return p.getDateToPlant();
            case 4: return p.getDonePlanting();
            case 5: return p.getDateToTP();
            case 6: return p.getDoneTP();
            case 7: return p.isDirectSeeded();
            case 8: return p.getBedsToPlant();
            case 9: return p.getRowFtToPlant();
            case 10: return p.getPlantsNeeded();
            case 11: return p.getFlatsNeeded();
            case 12: return p.getTotalYield();
            default: return "";
        }
    }

}

