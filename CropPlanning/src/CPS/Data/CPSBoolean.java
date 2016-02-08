/* CPSBoolean.java - created: Mar 8, 2008
 * Copyright (C) 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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
 * 
 */

package CPS.Data;

public class CPSBoolean {

    private Boolean value;
    
    public CPSBoolean( Boolean b ) {
        value = b;
    }
    
    public CPSBoolean( boolean b ) {
        this( new Boolean(b) );
    }

    
    public CPSBoolean not() {
       if ( isNull() )
          return new CPSBoolean( null );
       else
          return new CPSBoolean( ! booleanValue() );
    }


    public boolean isNull() {
        return value == null;
    }



    public boolean booleanValue() {
        if ( isNull() )
            return false;
        else
            return value.booleanValue();
    }
    
    @Override
    public String toString() {
        if ( isNull() )
            return "null";
        else
            return value.toString();
    }
    
}
