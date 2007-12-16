/*
 * CPSTextField - A class to wrap JTextField, providing extra functionality
 * primarily surrounding state information.
 */

package CPS.UI.Swing;

import javax.swing.JTextField;

/**
 *
 * @author Clayton
 */
public class CPSTextField extends JTextField implements CPSComponent {

    boolean changed = false;
    
    public CPSTextField( int size ) {
        super(size);
        this.getDocument().addDocumentListener( new CPSDocumentListener(this) );
    }
    
//    @Override
//    public String getText() {
//        String s = super.getText();
//        
//        if ( hasChanged() )
//            return s;
//        else
//            return null;
//    }
    
    public void setInitialText( String s ) {
        setText(s);
        setHasChanged( false );
    }
     
    
    public boolean hasChanged() { return changed; }

    public void setHasChanged(boolean b) {
        changed = b;
    }

}
