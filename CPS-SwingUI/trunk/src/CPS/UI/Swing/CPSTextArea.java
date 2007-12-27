/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.UI.Swing;

import javax.swing.JTextArea;

/**
 *
 * @author Clayton
 */
public class CPSTextArea extends JTextArea implements CPSComponent {
    
    boolean changed = false;
    
    public CPSTextArea( int rows, int cols ) {
        super( rows, cols );
        this.getDocument().addDocumentListener( new CPSDocumentListener(this) );
    }
         
    public void setInitialText( String s ) {
        setText(s);
        setHasChanged( false );
    }
     
    
    public boolean hasChanged() { return changed; }

    public void setHasChanged(boolean b) {
        changed = b;
    }


}
