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

import CPS.Data.CPSBoolean;
import CPS.Data.CPSDatum.CPSDatumState;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Clayton
 */
public class CPSCheckBox extends JCheckBox implements CPSButtonComponent {

    boolean changed = false;
    private CPSCheckBoxChangeListener changeListener = new CPSCheckBoxChangeListener();
    
    public CPSCheckBox() {
        super();
        this.addChangeListener( changeListener );
    }

    public CPSCheckBox( String label, boolean selected ) {
       super( label, selected );
       this.addChangeListener( changeListener );
    }

    public void setInitialState( boolean b ) {
       this.setSelected( b );
       setHasChanged( false );
       setBackgroundNormal();
       setToolTipText( null );
    }
    public void setInitialState( boolean b, CPSDatumState c ) {
       setInitialState( b, c, (String) null );
    }
    public void setInitialState( CPSBoolean b, CPSDatumState c ) {
       if ( b == null || b.isNull() )
          setInitialState( false, c, (String) null );
       else
          setInitialState( b.booleanValue(), c, (String) null );
    }
    
    /**
     * Set the initial state of the button.
     * @param checked
     * @param state
     * @param inheritedFrom - this will be displayed as a tooltip in the form "Inherited from ..."
     */
    public void setInitialState( boolean b, CPSDatumState c, String s ) {

       String tt = "Inherited";
       if ( s != null )
          tt += " from " + s;

       setInitialState( b );

       if      ( c.isInherited() ) {
          this.setBackgroundInherited();
          this.setToolTipText( tt );
       }
    }
        
    public boolean hasChanged() { return changed; }

    public void setHasChanged(boolean b) {
        changed = b;
    }

   @Override
   public void setEnabled ( boolean arg0 ) {
      // remove and then re-add the change listener so that we can enable and disable
      // the checkboxes without having to worry about the component being tagged "changed"
      this.removeChangeListener( changeListener );
      super.setEnabled( arg0 );
      this.addChangeListener( changeListener );
   }



    public void setBackgroundInherited() { setBackground( COLOR_INHERITED ); }
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
