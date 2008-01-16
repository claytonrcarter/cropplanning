/*
 * CPSTextField - A class to wrap JTextField, providing extra functionality
 * primarily surrounding state information.
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
