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
import CPS.Module.CPSDataModelConstants;
import ca.odell.glazedlists.gui.TableFormat;

/**
 *
 * @author kendra
 */
public class DSFieldPlantingTableFormat implements TableFormat<CPSPlanting> {

    public int getColumnCount() { return 8; }

    public String getColumnName( int arg0 ) {

        CPSPlanting p = new CPSPlanting();

        switch ( arg0 ) {
            case 0: return p.getDatum( CPSDataModelConstants.PROP_DATE_PLANT ).getName();
            case 1: return p.getDatum( CPSDataModelConstants.PROP_CROP_NAME ).getName();
            case 2: return p.getDatum( CPSDataModelConstants.PROP_VAR_NAME ).getName();
            case 3: return p.getDatum( CPSDataModelConstants.PROP_LOCATION ).getName();
            case 4: return p.getDatum( CPSDataModelConstants.PROP_BEDS_PLANT ).getName();
            case 5: return p.getDatum( CPSDataModelConstants.PROP_ROWS_P_BED ).getName();
            case 6: return p.getDatum( CPSDataModelConstants.PROP_ROWFT_PLANT ).getName();
            case 7: return p.getDatum( CPSDataModelConstants.PROP_PLANT_NOTES ).getName();
            default: return "";
        }
    }

    public Object getColumnValue( CPSPlanting p, int arg1 ) {

        switch ( arg1 ) {
           case 0: return p.getDateToPlant();
           case 1: return p.getCropName();
           case 2: return p.getVarietyName();
           case 3: return p.getLocation();
           case 4: return p.getBedsToPlant();
           case 5: return p.getRowsPerBed();
           case 6: return p.getRowFtToPlant();
           case 7: return p.getPlantingNotes();
           default: return "";
        }
    }

}
