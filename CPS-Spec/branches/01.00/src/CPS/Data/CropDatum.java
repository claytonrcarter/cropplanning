/* CropDatum.java
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

package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * CPSDatum - This is an abstraction data structure that stores, processes and deals
 * with data and metadata.  Used primarity in the CPSCrop and CPSPlanting classes.
 */
public class CropDatum<T> extends CPSDatum {

   private boolean shouldInherit, isInherited;
   
   public CropDatum() {
      invalidate();
      setShouldBeInherited(true);
      setIsInherited( false );
   }
   
   public CropDatum( String n, int p, String c, T def ) {
      this( n, p, c, def, true);
   }
   
   public CropDatum( String n, int p, String c, T def, boolean chain ) {
      super( n, p, c, def );
      setShouldBeInherited( chain );
      invalidate();
   }

   // TODO this is a dummy; should be removed; only for compatibility
   public CropDatum( String n ) {}
  
   private void setShouldBeInherited( boolean c ) { shouldInherit = c; }
   public boolean shouldBeInherited() { return shouldInherit; }
   
   public void setIsInherited( boolean i ) { isInherited = i; }
   public boolean isInherited() { return isInherited; }
   
   
}
