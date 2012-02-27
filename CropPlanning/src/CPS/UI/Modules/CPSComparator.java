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
import CPS.Module.CPSDataModelConstants;
import java.util.Comparator;
import java.util.Date;

public class CPSComparator implements Comparator {

    protected int propNum = -1;

    public CPSComparator() {
        propNum = CPSDataModelConstants.PROP_CROP_NAME;
    }

    public CPSComparator( int pNum ) {
        propNum = pNum;
    }

    public void setPropertyNum( int pNum ) {
        propNum = pNum;
    }

    public int compare( Object a, Object b ) {
       if ( a instanceof Integer ) {
          return (Integer) a - (Integer) b;
       }
       else if ( a instanceof Float ) {
          return (int) Math.floor( (Float) a - (Float) b );
       }
       else if ( a instanceof Boolean ) {
          return ( (Boolean) a ).compareTo( (Boolean) b );
       }
       else if ( a instanceof String ) {
          return ( (String) a ).compareToIgnoreCase( (String) b );
       }
       else if ( a instanceof Date ) {
          return ( (Date) a ).compareTo( (Date) b );
       }
//       else if ( a instanceof CPSRecord ) {
//         return compare( ((CPSRecord) a).get( propNum ), ((CPSRecord) b).get( propNum ) );
//       }
       else
          // otherwise don't sort and leave as is
          return 0;
        
    }



}
