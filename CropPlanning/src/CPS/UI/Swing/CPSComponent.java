/* CPSComponent.java
 * Copyright (C) 2007, 2008 Clayton Carter
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
 */

package CPS.UI.Swing;

import java.awt.Color;

/**
 * CPSComponent - a simple interface to allow our custom Swing components to 
 * record and retrieve whether they have been altered.
 */
public interface CPSComponent {
   
   Color SKY_BLUE = new Color( 135, 206, 235 );
   Color LIGHT_BLUE = new Color( 173, 216, 230 ); /* same color as text selection */
   Color LIGHT_GREEN = new Color( 152, 251, 152 );
   Color LIGHT_RED = new Color( 255, 192, 203 );
   Color COLOR_INHERITED = SKY_BLUE;
   Color COLOR_CALCULATED = LIGHT_GREEN;
   Color COLOR_CHANGED = LIGHT_RED;
   Color COLOR_NORMAL = Color.WHITE;
   
   public boolean hasChanged();
   public void setHasChanged( boolean b );
   public void setBackgroundInherited();
   public void setBackgroundCalculated();
   public void setBackgroundChanged();
   public void setBackgroundNormal();
   
}
