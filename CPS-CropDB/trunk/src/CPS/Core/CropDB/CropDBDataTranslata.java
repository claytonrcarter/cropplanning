/*
 * DataTranslata.java
 *
 * Created on March 15, 2007, 12:50 PM by Clayton
 */

package CPS.Core.CropDB;

import CPS.Data.CPSCrop;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;

public class CropDBDataTranslata {
   
   public CropDBDataTranslata() {}

   /*
    * CREATION UTILITY METHODS
    */
   /**
    * createLabel
    */
   public static void createLabel( JPanel p, int x, int y, String str ) {
      addLabel( p, x, y, new JLabel( str ));
   }
   
   public static void addLabel( JPanel p, int x, int y, JLabel jl ) {
      
      Insets i  = new Insets( 0, 0, 0, 5 );
      GridBagConstraints c = new GridBagConstraints();

      c.gridx = x;
      c.gridy = y;
      c.anchor = GridBagConstraints.FIRST_LINE_END;
      c.insets = i;
	
      p.add( jl, c );

   }

   private static void createCheckBox( JPanel p, int x, int y, String s, boolean b ) {
      addCheckBox( p, x, y, new JCheckBox( s, b ));
   }
   
   public static void addCheckBox( JPanel p, int x, int y, JCheckBox jc ) {
      Insets i = new Insets( 0, 2, 2, 5 );
      GridBagConstraints c = new GridBagConstraints();
      
      c.gridx = x;
      c.gridy = y;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = i;
      
      p.add( jc, c );
   }
   
   
    /**
     * createTextField
     */
   private static void createTextField( JPanel p, int x, int y ) {
      createTextField( p, x, y, "", 10 );
   }

   private static void createTextField( JPanel p, int x, int y, String str ) {
      createTextField( p, x, y, str, 10 );
   }
    
   private static void createTextField( JPanel p, int x, int y, int col ) {
      createTextField( p, x, y, "", col );
   }

   private static void createTextField( JPanel p,
				        int x, int y, 
				        String str, int col ) {
      addTextField( p, x, y, new JTextField( str, col ));
   }
   
   public static void addTextField( JPanel p, 
                                     int x, int y,
				     JTextField tf ) {

	Insets i  = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();

        tf.getKeymap().setDefaultAction( new ColorChangeAction( tf ) );
        
        
        
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
    private static void createTextArea( JPanel p, int x, int y ) {
	createTextArea( p, x, y, "", 3, 10 );
    }

    private static void createTextArea( JPanel p, int x, int y, String str ) {
	createTextArea( p, x, y, str, 3, 10 );
    }

    private static void createTextArea( JPanel p,
				 int x, int y, int row, int col ) {
	createTextArea( p, x, y, "", row, col );
    }
    
    private static void createTextArea( JPanel p, 
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

    private static void addSubPanel( JPanel p, 
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
