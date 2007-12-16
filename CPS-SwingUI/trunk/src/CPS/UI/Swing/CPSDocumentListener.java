/*
 * CPSDocumentListener - a document listener that simply records when a text
 * component has changed from it's ititial state
 */

package CPS.UI.Swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Clayton
 */
public class CPSDocumentListener implements DocumentListener {

    CPSComponent comp;
    
    public CPSDocumentListener( CPSComponent c ) {
        comp = c;
    }
    
    public void insertUpdate(DocumentEvent e)  { comp.setHasChanged( true ); }
    public void removeUpdate(DocumentEvent e)  { comp.setHasChanged( true ); }
    public void changedUpdate(DocumentEvent e) {}

}
