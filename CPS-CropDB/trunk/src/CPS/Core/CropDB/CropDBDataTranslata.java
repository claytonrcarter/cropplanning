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

   public static JPanel convertCrop( CPSCrop crop ) {
      
      System.out.println("Converting crop");
      
      JPanel cropPanel = new JPanel();
      
      cropPanel.setLayout( new GridBagLayout() );
      GridBagConstraints c = new GridBagConstraints();
      cropPanel.setAlignmentX( JPanel.LEFT_ALIGNMENT );
      cropPanel.setAlignmentY( JPanel.TOP_ALIGNMENT );

      Insets il  = new Insets( 0, 0, 0, 5 );
      Insets ita = new Insets( 0, 2, 2, 5 );

      /* Column One */
      createLabel(     cropPanel, 0, 0, "Crop Name:" );
      createTextField( cropPanel, 1, 0, crop.getCropName() );

      createLabel(     cropPanel, 0, 1, "Variety:" );
      createTextField( cropPanel, 1, 1, crop.getVarietyName() );

      //createLabel(     cropPanel, 0, 2, "Belongs to Groups:" );
      //createTextArea(  cropPanel, 1, 2, "Greenhouse,\nMesclun" );


      /* Column Two */
      createLabel(     cropPanel, 2, 0, "Family:" );
      createTextField( cropPanel, 3, 0, crop.getFamName() );
      
      createLabel(     cropPanel, 2, 1, "Mat. Days:" );
      createTextField( cropPanel, 3, 1, "" + crop.getMaturityDays() );

      //createLabel(     cropPanel, 2, 2, "DS?" );
      createCheckBox(  cropPanel, 3, 2, "DS?", crop.getDS() );

      //createLabel(     cropPanel, 2, 3, "TP?" );
      createCheckBox(  cropPanel, 3, 3, "TP?", crop.getTP() );


      
      return cropPanel;
      
   }
   
   
   /*
    * CREATION UTILITY METHODS
    */
   /**
    * createLabel
    */
   private static void createLabel( JPanel p, int x, int y, String str ) {

      Insets i  = new Insets( 0, 0, 0, 5 );
      GridBagConstraints c = new GridBagConstraints();
      JLabel l = new JLabel( str );

      c.gridx = x;
      c.gridy = y;
      c.anchor = GridBagConstraints.FIRST_LINE_END;
      c.insets = i;
	
      p.add( l, c );

   }

   private static void createCheckBox( JPanel p, int x, int y, String s, boolean b ) {
      Insets i = new Insets( 0, 2, 2, 5 );
      GridBagConstraints c = new GridBagConstraints();
      JCheckBox jc = new JCheckBox( s, b );
      
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

	Insets i  = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();
	JTextField tf = new JTextField( str, col );

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
