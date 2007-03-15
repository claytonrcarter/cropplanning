/*
 * CropDBUILayout.java
 *
 * Created on March 12, 2007, 10:58 PM
 *
 *
 */

package CPS.Core.CropDB;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author Clayton
 */
public class CropDBUILayout {
   
   public CropDBUILayout() {}
      
   /**
    * Component create shortcuts
    */

   /**
    * createLabel
    */
    private void createLabel( JPanel p, int x, int y, String str ) {

	Insets i  = new Insets( 0, 0, 0, 5 );
	GridBagConstraints c = new GridBagConstraints();
	JLabel l = new JLabel( str );

	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.FIRST_LINE_END;
	c.insets = i;
	
	p.add( l, c );

    }

    /**
     * createTextField
     */
    private void createTextField( JPanel p, int x, int y ) {
	createTextField( p, x, y, "", 10 );
    }

    private void createTextField( JPanel p, int x, int y, String str ) {
	createTextField( p, x, y, str, 10 );
    }
    
    private void createTextField( JPanel p, int x, int y, int col ) {
	createTextField( p, x, y, "", col );
    }

    private void createTextField( JPanel p, 
				  int x, int y, 
				  String str, int col ) {

	Insets i  = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();
	JTextField tf = new JTextField( str, col );

	tf.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
							Color.GRAY,
							Color.WHITE ));

	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.insets = i;
	
	p.add( tf, c );

    }

    /**
     * createTextArea
     */
    private void createTextArea( JPanel p, int x, int y ) {
	createTextArea( p, x, y, "", 3, 10 );
    }

    private void createTextArea( JPanel p, int x, int y, String str ) {
	createTextArea( p, x, y, str, 3, 10 );
    }

    private void createTextArea( JPanel p,
				 int x, int y, int row, int col ) {
	createTextArea( p, x, y, "", row, col );
    }
    
    private void createTextArea( JPanel p, 
				 int x, int y, 
				 String str,
				 int rows, int cols ) {

	Insets i  = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();
	JTextArea ta = new JTextArea( str, rows, cols );

	ta.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
							Color.GRAY,
							Color.WHITE ));

	c.gridx = x;
	c.gridy = y;
	c.gridheight = rows - 1;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.insets = i;
	
	p.add( ta, c );

    }

    private void addSubPanel( JPanel p, 
			      int x, int y, int xspan, int yspan,
			      JPanel newP ) {

	Insets i = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();

	c.gridx = x;
	c.gridy = y;
	c.gridwidth  = xspan;
	c.gridheight = yspan;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.fill = GridBagConstraints.BOTH;
	c.insets = i;

	p.add( newP, c );

    }
   
   
}
