/* CPSHeaderPanel.java - created: Mar 7, 2008
 * Copyright (C) 2008 Clayton Carter
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
 * 
 */

package CPS.UI.Swing;

import java.awt.Dimension;
import org.jdesktop.swingx.JXHeader;

public class CPSHeaderPanel extends JXHeader {

    int width = -1;
    
    protected void setWidth( int i ) {
        width = i;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        if ( width == -1 )
            return super.getPreferredScrollableViewportSize();
        else
            return new Dimension( width, 
                                  super.getPreferredScrollableViewportSize().height );
    }
    
}
