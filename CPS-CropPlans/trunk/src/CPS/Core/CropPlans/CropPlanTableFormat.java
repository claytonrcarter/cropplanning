/* CropPlanTableFormat.java - Created: Nov 8, 2009
 * Copyright (C) 2009 Clayton Carter
 *
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package CPS.Core.CropPlans;

import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.gui.TableFormat;

public class CropPlanTableFormat implements TableFormat<CPSPlanting> {

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
