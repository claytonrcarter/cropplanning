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
public class CPSDocumentChangeListener implements DocumentListener {

    CPSComponent comp;
    
    public CPSDocumentChangeListener( CPSComponent c ) {
        comp = c;
    }
    
    private void updateComponentChanged() {
       comp.setHasChanged(true);
       comp.setBackgroundChanged();
    }
    
    public void insertUpdate(DocumentEvent e)  { updateComponentChanged(); }
    public void removeUpdate(DocumentEvent e)  { updateComponentChanged(); }
    public void changedUpdate(DocumentEvent e) {}

}
