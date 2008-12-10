/* LayoutAssist.java - Created: March 15, 2007
 * Copyright (C) 2007, 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
   public static void addVerticalSeparator( JPanel p, int col, int rowStart, int rowSpan ) {
      addComponent( p, col, rowStart, 1, rowSpan, 
                    new JSeparator( JSeparator.VERTICAL ),
                    GridBagConstraints.CENTER,
                    GridBagConstraints.VERTICAL );
   }
   public static void addSeparator( JPanel p, int colStart, int row, int colSpan ) {
      
      addComponent( p, colStart, row, colSpan, 1, 
                    new JSeparator( JSeparator.HORIZONTAL ), 
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL,
                    new Insets( 1, 1, 3, 1 ));
      
//      JSeparator js = new JSeparator( JSeparator.HORIZONTAL );
//
//      /* Insets: top, left, bottom, right */
//      Insets i = new Insets( 1, 1, 3, 1 );
//      GridBagConstraints c = new GridBagConstraints();
//      
//      c.gridx = colStart;
//      c.gridy = row;
//      c.gridwidth = colSpan;
//      c.fill = GridBagConstraints.HORIZONTAL;
//      c.anchor = GridBagConstraints.CENTER;
//      c.insets = i;  
//      
//      p.add( js, c );
      
   }
   
   
   public static void addComponent( JPanel p, int col, int row, JComponent jc ) {
       addComponent( p, col, row, jc, GridBagConstraints.FIRST_LINE_START );
   }
   public static void addComponent( JPanel p, int col, int row, JComponent jc, int align ) {
      addComponent( p, col, row, 1, 1, jc, align );
   }
   public static void addComponent( JPanel p, int col, int row, int colSpan, int rowSpan, JComponent jc ) {
      addComponent( p, col, row, colSpan, rowSpan, jc, GridBagConstraints.FIRST_LINE_START );
   }
   public static void addComponent( JPanel p, int col, int row, int colSpan, int rowSpan, JComponent jc, int align ) {
      addComponent( p, col, row, colSpan, rowSpan, jc, align, new Insets( 0, 2, 2, 5 ) );
   }
   public static void addComponent( JPanel p, int col, int row, int colSpan, int rowSpan, JComponent jc, int align, int fill ) {
      addComponent( p, col, row, colSpan, rowSpan, jc, align, fill, new Insets( 0, 2, 2, 5 ) );   
   }
   public static void addComponent( JPanel p, int col, int row, int colSpan, int rowSpan, JComponent jc, int align, Insets i ) {
      addComponent( p, col, row, colSpan, rowSpan, jc, align, GridBagConstraints.NONE, i );   
   }
   public static void addComponent( JPanel p, 
                                    int col, int row, int colSpan, int rowSpan, 
                                    JComponent jc, 
                                    int align, 
                                    int fill,
                                    Insets i ) {
       
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = col;
      c.gridy = row;
      c.gridwidth  = colSpan;
      c.gridheight = rowSpan;
      c.fill = fill;
      c.anchor = align;
      c.insets = i;
	
      p.add( jc, c );
      
   }
   
   
   
   public static void addButton( JPanel p, int col, int row, AbstractButton b ) {
       addButton( p, col, row, 1, 1, b );
   }
   public static void addButtonRightAlign( JPanel p, int col, int row, AbstractButton b ) {
       addButton( p, col, row, 1, 1, b, GridBagConstraints.FIRST_LINE_END );
   }
   
   public static void addButton( JPanel p, int col, int row, int colSpan, int rowSpan, AbstractButton b ) {
       addButton( p, col, row, colSpan, rowSpan, b, GridBagConstraints.FIRST_LINE_START );
   }
   public static void addButton( JPanel p, int col, int row, int colSpan, int rowSpan,
                                 AbstractButton b,
                                 int align ) {

      addComponent( p, col, row, colSpan, rowSpan, b, align );
//      Insets i = new Insets( 0, 2, 2, 5 );
//      GridBagConstraints c = new GridBagConstraints();
//
//      c.gridx = col;
//      c.gridy = row;
//      c.gridwidth  = colSpan;
//       c.gridheight = rowSpan;
//      c.anchor = GridBagConstraints.FIRST_LINE_START;
//      c.insets = i;
//	
//      p.add( b, c );
      
   }
   
   /**
    * createLabel
    */
   public static JLabel createLabel( JPanel p, int col, int row, String str ) {
      if ( ! str.endsWith(":") )
         str += ":";
      return addLabel( p, col, row, new JLabel( str ));
   }
   
   public static JLabel addLabel( JPanel p, int col, int row, JLabel jl ) {
       return addLabel( p, col, row, 1, 1, jl );
   }
   public static JLabel addLabel( JPanel p, int col, int row, int colSpan, int rowSpan, JLabel jl ) {
      
      Insets i  = new Insets( 0, 0, 0, 5 );
      GridBagConstraints c = new GridBagConstraints();

      c.gridx = col;
      c.gridy = row;
      c.gridwidth = colSpan;
      c.gridheight = rowSpan;
      c.anchor = GridBagConstraints.FIRST_LINE_END;
      c.insets = i;
	
      p.add( jl, c );

      return jl;
   }

   public static JLabel addLabelLeftAlign( JPanel p, int col, int row, JLabel jl ) {
       return addLabelLeftAlign( p, col, row, 1, 1, jl );
   }
   public static JLabel addLabelLeftAlign( JPanel p, int col, int row, int colSpan, int rowSpan, JLabel jl ) {
      
      Insets i  = new Insets( 0, 0, 0, 5 );
      GridBagConstraints c = new GridBagConstraints();

      c.gridx = col;
      c.gridy = row;
      c.gridwidth = colSpan;
      c.gridheight = rowSpan;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = i;
	
      p.add( jl, c );

      return jl;
   }

   private static void createCheckBox( JPanel p, int col, int row, String s, boolean b ) {
      addCheckBox( p, col, row, new JCheckBox( s, b ));
   }
   
   public static void addCheckBox( JPanel p, int col, int row, JCheckBox jc ) {
      Insets i = new Insets( 0, 2, 2, 5 );
      GridBagConstraints c = new GridBagConstraints();
      
      c.gridx = col;
      c.gridy = row;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = i;
      
      p.add( jc, c );
   }
   
   
    /**
     * createTextField
     */
   private static void createTextField( JPanel p, int col, int y ) {
      createTextField( p, col, y, "", 10 );
   }

   private static void createTextField( JPanel p, int col, int row, String str ) {
      createTextField( p, col, row, str, 10 );
   }
    
   private static void createTextField( JPanel p, int col, int row, int cols ) {
      createTextField( p, col, row, "", cols );
   }

   private static void createTextField( JPanel p,
				        int col, int row, 
				        String str, int cols ) {
      addTextField( p, col, row, new JTextField( str, cols ));
   }
   
   public static void addTextField( JPanel p, 
                                    int col, int row,
                                    JTextField tf ) {
      addTextField( p, col, row, 1, 1, tf );
   }
   public static void addTextField( JPanel p, 
                                    int col, int row,
                                    int colSpan, int rowSpan,
                                    JTextField tf ) {

      tf.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
                                                      Color.GRAY,
                                                      Color.WHITE ));
      addComponent( p, col, row, colSpan, rowSpan, tf,
                    GridBagConstraints.FIRST_LINE_START,
                    new Insets( 0, 2, 2, 5 ) );
//	Insets i  = new Insets( 0, 2, 2, 5 );
//	GridBagConstraints c = new GridBagConstraints();
//
//        // TODO this needs to affect individual components, not all components
//        // tf.getKeymap().setDefaultAction( new ColorChangeAction( tf ) );        
//        
//	tf.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
//							Color.GRAY,
//							Color.WHITE ));
//	c.gridx = col;
//	c.gridy = row;
//	c.anchor = GridBagConstraints.FIRST_LINE_START;
//	c.insets = i;
//	
//	p.add( tf, c );

    }

    /**
     * createTextArea
     */
    private static void createTextArea( JPanel p, int col, int row ) {
	addTextArea( p, col, row, new JTextArea( "", 3, 10 ));
    }

    private static void createTextArea( JPanel p, int col, int row, String str ) {
	addTextArea( p, col, row, new JTextArea( str, 3, 10 ));
    }

    private static void createTextArea( JPanel p,
				 int col, int row, int rows, int cols ) {
	addTextArea( p, col, row, new JTextArea( "", rows, cols ));
    }
    
    public static void addTextArea( JPanel p, int col, int row, JTextArea ta ) {
       addTextArea( p, col, row, 1, ta.getHeight(), ta );
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
        
        // make the scrollpane ignore focus on the scroll bar
        // from http://forum.java.sun.com/thread.jspa?forumID=57&threadID=609727
        // also made changes in CPSTextArea
        sp.getVerticalScrollBar().setFocusable(false);
        
	p.add( sp, c );
//	p.add( ta, c );

    }

    public static void addComboBox( JPanel p, int col, int row, JComboBox cb ) {
      
       Insets i  = new Insets( 0, 2, 2, 5 );
       GridBagConstraints c = new GridBagConstraints();

       cb.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
							Color.GRAY,
							Color.WHITE ));

       c.gridx = col;
       c.gridy = row;
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
