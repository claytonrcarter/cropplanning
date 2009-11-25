/* CPSTextField.java
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
import CPS.UI.Swing.autocomplete.AutoCompleteDecorator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 * CPSTextField - A class to wrap JTextField, providing extra functionality
 * primarily surrounding state information.
 */
public class CPSTextField extends JTextField implements CPSTextComponent {

   public static final boolean MATCH_STRICT = true;
   public static final boolean MATCH_PERMISSIVE = false;
   
   private boolean changed = false;
   private boolean strictMatch = false;
    
    public CPSTextField( int size ) {
        super(size);
        addChangeListener();
    }
    
    /**
     * Initialize this text field and set it to support autocompletion of a given list of values.
     * @param size width of the text field
     * @param autocompleteList list of values to match the autocompletion against
     * @param strictMatch if true only match values in the list, otherwise allow maverick input
     */
    public CPSTextField( int size, List autocompleteList, boolean strictMatch ) {
       super(size);
       updateAutocompletionList( autocompleteList, strictMatch );
    }
    
    private void addChangeListener() {
       this.getDocument().addDocumentListener( new CPSDocumentChangeListener(this) );
    }
    
    /**
     * Used to determine if this text box matches autocomplete item strictly or not.  If not,
     * maverick entries are allowed.
     * @return true is matching string, false otherwise.
     */
    public boolean isMatchingStrict() { return strictMatch; }
    public void updateAutocompletionList( List autocompleteList, boolean strictMatch ) {
       this.strictMatch = strictMatch;
       String s = this.getText();
       AutoCompleteDecorator.decorate( this, autocompleteList, this.strictMatch );
       this.setText(s);
       addChangeListener();
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

}
