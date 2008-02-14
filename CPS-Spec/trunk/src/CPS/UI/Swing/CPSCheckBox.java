/* CPSCheckBox.java
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

package CPS.UI.Swing;

import CPS.Data.CPSDatum.CPSDatumState;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Clayton
 */
public class CPSCheckBox extends JCheckBox implements CPSComponent {

    boolean changed = false;
    
    public CPSCheckBox() {
        super();
        this.addChangeListener( new CPSCheckBoxChangeListener() );
    }
    
    public void setInitialText( String s ) {};
    public void setInitialText( String s, CPSDatumState c ) {}
    
    public void setInitialState( boolean b ) {
        /* setText triggers document listener, which changes background to pink */ 
       this.setSelected( b );
       setHasChanged( false );
       setBackgroundNormal();
       setToolTipText( null );
    }
     
    
    public boolean hasChanged() { return changed; }

    public void setHasChanged(boolean b) {
        changed = b;
    }
    
    public void setBackgroundInherited() {}
    public void setBackgroundCalculated() {}
    public void setBackgroundChanged() { setBackground( COLOR_CHANGED ); }
    public void setBackgroundNormal() { setBackground( COLOR_NORMAL ); }

    private class CPSCheckBoxChangeListener implements ChangeListener {
      public void stateChanged( ChangeEvent arg0 ) {
         setHasChanged(true);
         setBackgroundChanged();
      }
    }
    
}
