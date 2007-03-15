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
import javax.swing.*;
import javax.swing.border.*;

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
	createTextField( cropPanel, 1, 0, "Arugula" );

	createLabel(     cropPanel, 0, 1, "Variety:" );
	createTextField( cropPanel, 1, 1, "EvenStar" );

	createLabel(     cropPanel, 0, 2, "Belongs to Groups:" );
	createTextArea(  cropPanel, 1, 2, "Greenhouse,\nMesclun" );


	/* Column Two */
	createLabel(     cropPanel, 2, 0, "Family:" );
	createTextField( cropPanel, 3, 0, "Brassica" );

	createLabel(     cropPanel, 2, 1, "Mat. Days:" );
	createTextField( cropPanel, 3, 1, "40" );

	createLabel(     cropPanel, 2, 2, "Seeding Method:" );
	createTextField( cropPanel, 3, 2, "DS, TP" );

	JPanel dsPanel = new JPanel();
	dsPanel.setBorder( BorderFactory.createTitledBorder( "DS" ));
	dsPanel.setLayout( new GridBagLayout() );
	
	createLabel(     dsPanel, 0, 0, "Rate (seeds/ft):" );
	createTextField( dsPanel, 1, 0, "12", 5 );

	createLabel(     dsPanel, 0, 1, "Seeder Setting:" );
	createTextField( dsPanel, 1, 1, "10", 5 );

	addSubPanel(     cropPanel, 2, 3, 2, 3, dsPanel );

	JPanel tpPanel = new JPanel();
	tpPanel.setBorder( BorderFactory.createTitledBorder( "TP" ));
	tpPanel.setLayout( new GridBagLayout() );
	
	createLabel(     tpPanel, 0, 0, "Weeks to TP:" );
	createTextField( tpPanel, 1, 0, "3", 5 );

	createLabel(     tpPanel, 0, 1, "Adjust Mat. Days:" );
	createTextField( tpPanel, 1, 1, "-14", 5 );

	createLabel(     tpPanel, 0, 2, "Flat Size:" );
	createTextField( tpPanel, 1, 2, "128", 5 );

	createLabel(     tpPanel, 0, 3, "Pot Up?" );
	createTextField( tpPanel, 1, 3, "No", 5 );

	addSubPanel(     cropPanel, 2, 6, 2, 5, tpPanel );

	/* Column Three */
 	JPanel yieldPanel = new JPanel();
 	yieldPanel.setBorder( BorderFactory.createTitledBorder( "Yield (lb)"));
 	yieldPanel.setLayout( new GridBagLayout() );
		
 	createLabel(     yieldPanel, 0, 0, "Per foot:" );
 	createTextField( yieldPanel, 1, 0, ".33", 5 );

 	createLabel(     yieldPanel, 0, 1, "Per row:" );
 	createTextField( yieldPanel, 1, 1, "16.33", 5 );

 	createLabel(     yieldPanel, 0, 2, "Per bed:" );
 	createTextField( yieldPanel, 1, 2, "49", 5 );

 	addSubPanel(     cropPanel, 5, 0, 2, 4, yieldPanel );

      
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
