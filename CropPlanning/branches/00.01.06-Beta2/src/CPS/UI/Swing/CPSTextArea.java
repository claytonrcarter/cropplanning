/* CPSTextArea.java
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
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;

/**
 *
 * @author Clayton
 */
public class CPSTextArea extends JTextArea implements CPSTextComponent {
    
    boolean changed = false;
    
    public CPSTextArea( int rows, int cols ) {
        super( rows, cols );
        setLineWrap( true );
        setWrapStyleWord( false );
        this.getDocument().addDocumentListener( new CPSDocumentChangeListener(this) );
        
        // these are taken from http://forum.java.sun.com/thread.jspa?forumID=57&threadID=609727
        // also make a change in LayoutAssist, set ScrollPane to not focusable
        this.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        this.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null );

        // Add actions to make TAB move focus
//        this.getActionMap().put( nextFocusAction.getValue( Action.NAME ), nextFocusAction );
//        this.getActionMap().put( prevFocusAction.getValue( Action.NAME ), prevFocusAction );
    
    }
         
    public void setInitialText( String s ) {
        /* setText triggers document listener, which changes background to pink */ 
       setText(s);
       setHasChanged( false );
       setBackgroundNormal();
       setToolTipText( null );
    }
     
    public void setInitialText( String s, CPSDatumState c ) {
       setInitialText(s);
       if      ( c.isInherited() ) {
          this.setBackgroundInherited();
          this.setToolTipText( "Inherited from CropDB" );
       }
       else if ( c.isCalculated() ) {
          this.setBackgroundCalculated();
          this.setToolTipText( "Calculated" );
       }
       else {
          // this.setBackgroundNormal();
          // this.setToolTipText( null );   
       }
    }
    
    public boolean hasChanged() { return changed; }

    public void setHasChanged(boolean b) {
        changed = b;
    }

    public void setBackgroundInherited() { setBackground( COLOR_INHERITED ); }
    public void setBackgroundCalculated() { setBackground( COLOR_CALCULATED ); }
    public void setBackgroundChanged() { setBackground( COLOR_CHANGED ); }
    public void setBackgroundNormal() { setBackground( COLOR_NORMAL ); }
    
    @Override
    public boolean isManagingFocus() { return false; }
    
    private Action nextFocusAction = new AbstractAction("Move Focus Forwards") {
        public void actionPerformed(ActionEvent evt) {
            ((Component)evt.getSource()).transferFocus();
        }
    };
    private Action prevFocusAction = new AbstractAction("Move Focus Backwards") {
        public void actionPerformed(ActionEvent evt) {
            ((Component)evt.getSource()).transferFocusBackward();
        }
    };
    
}
