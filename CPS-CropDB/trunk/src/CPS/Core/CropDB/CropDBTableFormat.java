/* CropDBTableFormat.java - Created: Nov 9, 2009
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

package CPS.Core.CropDB;

import CPS.Data.CPSCrop;
import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.gui.TableFormat;

public class CropDBTableFormat implements TableFormat<CPSCrop> {

    public int getColumnCount() { return 3; }

    public String getColumnName( int colNum ) {

        CPSCrop c = new CPSCrop();

        switch ( colNum ) {
            case 0: return c.getDatum( c.PROP_CROP_NAME ).getName();
            case 1: return c.getDatum( c.PROP_VAR_NAME ).getName();
            case 2: return c.getDatum( c.PROP_MATURITY ).getName();
            default: return "";
        }
    }

    public Object getColumnValue( CPSCrop c, int colNum ) {

        switch ( colNum ) {
            case 0: return c.getCropName();
            case 1: return c.getVarietyName();
            case 2: return c.getMaturityDays();
            default: return "";
        }
    }

}
