/* CPSCardPanel.java
 * Copyright (C) 2013 Clayton Carter
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

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 */
public class CPSCardPanel extends JPanel implements ItemListener {

  private JComboBox<String> cb;
  private JPanel cards;

  public CPSCardPanel( String[] elems,
                       JPanel[] panels ) {

    this.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS ));
    this.setBorder( BorderFactory.createEtchedBorder() );


    cb = new JComboBox<String>(elems);
    cb.setEditable(false);
    cb.addItemListener(this);
    cb.setMaximumSize( cb.getPreferredSize() );
    this.add( cb );

    Dimension dim = new Dimension();
    Dimension jpDim;

    cards = new JPanel( new CardLayout() );
    for ( int i = 0; i < panels.length; i++ ) {
      jpDim = panels[i].getPreferredSize();
      // make sure each panel can't expand
      //panels[i].setMaximumSize( jpDim );

      // find out biggest panel
      if ( jpDim.getWidth() > dim.getWidth() )
        dim.setSize( jpDim.getWidth()+10, dim.getHeight() );
      if ( jpDim.getHeight() > dim.getHeight() )
        dim.setSize( dim.getWidth(), jpDim.getHeight() );

      cards.add( panels[i], elems[i] );
    }
    
    cards.setPreferredSize(dim);
//    this.setMaximumSize(dim);
    this.add( cards );

  }

  public void itemStateChanged(ItemEvent evt) {
    CardLayout cl = (CardLayout)(cards.getLayout());
    cl.show(cards, (String)evt.getItem());
    cards.revalidate();
    cards.repaint();
  }


  public static void main( String[] args ) {
    // "test" construtor

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    JPanel jp1 = new JPanel();
    jp1.add( new JLabel( "Just testing" ));

    JPanel jp2 = new JPanel();
    jp2.add( new JLabel( "Just testing again" ));

    JPanel content = new CPSCardPanel( new String[] { "testing", "testing again" },
                                       new JPanel[] { jp1, jp2 } );

    frame.setContentPane(content);
    frame.setTitle( "Testing CPSComboBoxTitledBorder" );
    frame.pack();
    frame.setVisible(true);

  }
}
