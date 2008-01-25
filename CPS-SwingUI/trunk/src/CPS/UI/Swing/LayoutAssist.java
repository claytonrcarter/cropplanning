/*
 * DataTranslata.java
 *
 * Created on March 15, 2007, 12:50 PM by Clayton
 */

package CPS.UI.Swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.*;

public class LayoutAssist {

   public LayoutAssist() {}

   /*
    * CREATION UTILITY METHODS
    */
   public static void addSeparator( JPanel p, int colStart, int row, int colSpan ) {
      
      JSeparator js = new JSeparator( JSeparator.HORIZONTAL );
      
      Insets i = new Insets( 1, 1, 3, 1 );
      GridBagConstraints c = new GridBagConstraints();
      
      c.gridx = colStart;
      c.gridy = row;
      c.gridwidth = colSpan;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.CENTER;
      c.insets = i;  
      
      p.add( js, c );
      
   }
   
   
   /**
    * createLabel
    */
   public static void createLabel( JPanel p, int x, int y, String str ) {
      if ( ! str.endsWith(":") )
         str += ":";
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

        // TODO this needs to affect individual components, not all components
        // tf.getKeymap().setDefaultAction( new ColorChangeAction( tf ) );        
        
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
	addTextArea( p, x, y, new JTextArea( "", 3, 10 ));
    }

    private static void createTextArea( JPanel p, int x, int y, String str ) {
	addTextArea( p, x, y, new JTextArea( str, 3, 10 ));
    }

    private static void createTextArea( JPanel p,
				 int x, int y, int row, int col ) {
	addTextArea( p, x, y, new JTextArea( "", row, col ));
    }
    
    public static void addTextArea( JPanel p, int x, int y, JTextArea ta ) {
       addTextArea( p, x, y, 1, ta.getHeight(), ta );
    }
    public static void addTextArea( JPanel p, int colStart, int rowStart, int colSpan, JTextArea ta ) {
       addTextArea( p, colStart, rowStart, colSpan, ta.getHeight(), ta );
    }
    public static void addTextArea( JPanel p, int colStart, int rowStart, int colSpan, int rowSpan, JTextArea ta ) {
 
	Insets i  = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();

        // needed for when we're not using a scrollpane
//	ta.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
//							Color.GRAY,
//							Color.WHITE ));

	c.gridx = colStart;
	c.gridy = rowStart;
        c.gridwidth = colSpan;
	c.gridheight = rowSpan;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.insets = i;
	
        JScrollPane sp = new JScrollPane( ta );
        
	p.add( sp, c );
//	p.add( ta, c );

    }

    public static void addComboBox( JPanel p, int x, int y, JComboBox cb ) {
      
       Insets i  = new Insets( 0, 2, 2, 5 );
       GridBagConstraints c = new GridBagConstraints();

       cb.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
							Color.GRAY,
							Color.WHITE ));

       c.gridx = x;
       c.gridy = y;
       c.anchor = GridBagConstraints.FIRST_LINE_START;
       c.insets = i;
	
       p.add( cb, c );

    }
    
    public static void finishColumn( JPanel column ) {
       column.add( Box.createVerticalGlue() );
    }
    
    public static void addPanelToColumn( JPanel column, JPanel p ) {
       p.setMaximumSize( p.getPreferredSize() );
       column.add( p );
    }
    
    public static void addSubPanel( JPanel p, 
                                    int colStart, int rowStart, int colSpan, int rowSpan,
                                    JPanel newP ) {

	Insets i = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();

	c.gridx = colStart;
	c.gridy = rowStart;
	c.gridwidth  = colSpan;
	c.gridheight = rowSpan;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.fill = GridBagConstraints.BOTH;
	c.insets = i;

	p.add( newP, c );

    }

    
   
    
}
