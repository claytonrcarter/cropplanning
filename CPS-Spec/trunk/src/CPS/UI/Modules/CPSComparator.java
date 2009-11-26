/* CPSComparator.java - Created: Nov 8, 2009
 * Copyright (C) 2007, 2008 Clayton Carter
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

package CPS.UI.Modules;

import CPS.Data.CPSRecord;
import java.util.Comparator;

public class CPSComparator implements Comparator<CPSRecord> {

    int propNum = -1;

    public CPSComparator( int pNum ) {
        setPropertyNum( pNum );
    }

    public void setPropertyNum( int pNum ) {
        propNum = pNum;
    }

    public int compare( CPSRecord a, CPSRecord b ) {
        Object oa = a.get( propNum );

        if ( oa instanceof Integer ) {
            return a.getInt( propNum ) - b.getInt( propNum );
        }
        else if ( oa instanceof Float ) {
            return (int) Math.floor( a.getFloat( propNum ) - b.getFloat( propNum ) );
        }
        else if ( oa instanceof Boolean ) {
            return ((Boolean) oa).compareTo( (Boolean) b.get(propNum) );
        }
        else if ( oa instanceof String ) {
            return ((String) oa).compareToIgnoreCase( (String) b.get( propNum ));
        }
        else
            // otherwise don't sort and leave as is
            return 0;
        
    }



}
