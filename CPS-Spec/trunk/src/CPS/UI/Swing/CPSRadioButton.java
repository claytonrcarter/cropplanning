/* CPSRadioButton.java - created: Mar 6, 2008
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

import CPS.Data.CPSDatum.CPSDatumState;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JRadioButton;

public class CPSRadioButton extends JRadioButton implements CPSButtonComponent, ItemListener {

    boolean changed = false;
    boolean inherited = false;
    private Color COLOR_NORMAL;
    private Color COLOR_CHANGED = Color.RED;
    
    public CPSRadioButton( String s, boolean b ) {
        super( s, b );
        COLOR_NORMAL = this.getForeground();
        addItemListener( this );
    }

    public boolean hasChanged() { return changed; }

    public void setBackgroundInherited() { setForeground( COLOR_INHERITED ); }
    public void setBackgroundCalculated() {}
    public void setBackgroundChanged() { setForeground( COLOR_CHANGED ); }
    public void setBackgroundNormal() { setForeground( COLOR_NORMAL ); }
    
    public void resetState() {
        setHasChanged( false );
        inherited = false;
        setBackgroundNormal();
        setToolTipText( null );
    }
    
    public void setHasChanged( boolean b ) { 
        changed = b; 
        if ( changed )
            setBackgroundChanged();
        else if ( inherited )
            setBackgroundInherited();
        else
            setBackgroundNormal();
    }

    public void doInitialClick() {
        doClick();
        resetState();
    }
    
    public void doInitialClick( CPSDatumState c ) {
        doInitialClick();
        if ( c.isInherited() ) {
            inherited = true;
            this.setBackgroundInherited();
            this.setToolTipText( "Inherited" );
        }
        else
            inherited = false;
    }
    
    
    public void setInitialState( boolean b ) { throw new UnsupportedOperationException( "Not supported yet." ); }
    public void setInitialState( boolean b, CPSDatumState c ) { throw new UnsupportedOperationException( "Not supported yet." ); }

    
    public void itemStateChanged( ItemEvent arg0 ) {
        if ( arg0.getSource() == this )
            setHasChanged(true);
    }
    
}
