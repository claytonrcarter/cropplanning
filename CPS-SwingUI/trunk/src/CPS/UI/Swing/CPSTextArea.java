/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.UI.Swing;

import CPS.Data.CPSDatum.CPSDatumState;
import javax.swing.JTextArea;

/**
 *
 * @author Clayton
 */
public class CPSTextArea extends JTextArea implements CPSComponent {
    
    boolean changed = false;
    
    public CPSTextArea( int rows, int cols ) {
        super( rows, cols );
        setLineWrap( true );
        setWrapStyleWord( false );
        this.getDocument().addDocumentListener( new CPSDocumentChangeListener(this) );
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
          this.setToolTipText( "Inherited" );
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
