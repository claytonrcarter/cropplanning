/* CPSDialog.java - created: Mar 7, 2008
 * Copyright (C) 2008 Clayton Carter
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
 * 
 */

package CPS.UI.Swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXHeader;

public abstract class CPSDialog extends JDialog {

    protected JXHeader header;
    private JPanel jplMain;
    protected JPanel jplContents;
    private JPanel jplButtons;

    protected boolean contentsPanelBuilt = false;
    
    public CPSDialog ( String title ) {
        
        super( (JFrame) null, true );
        header = new JXHeader();
        setTitle( title );
//        header.setOpaque(false);
//        header.setIcon(null);
        
        initContentsPanel();
//        buildContentsPanel();
        
        initButtonPanel();
        fillButtonPanel();
        
        buildMainPanel();
     
        super.add( jplMain );
        
    }

    
    @Override public Component add( Component arg0 ) {
        if ( ! ( arg0 instanceof JPanel )) {
            JPanel jp = new JPanel();
            jp.setLayout( new BoxLayout( jp, BoxLayout.LINE_AXIS ));
            jp.setBorder( BorderFactory.createEmptyBorder(0, 10, 0, 10));
            jp.add( arg0 );
            jp.add( Box.createHorizontalGlue());
            jp.setAlignmentX( Component.LEFT_ALIGNMENT );
            return jplContents.add( jp );
        }
        else
            return jplContents.add( arg0 ); 
    }
    
    @Override public void setVisible( boolean show ) {
      if ( ! contentsPanelBuilt )
        buildContentsPanel();

        if ( show ) {
            
            // I really don't get all of this, it would seem easier to just
            // have a setWidth method, but we don't have that option.  Having tried
            // a number of different approaches, this seems to work, more or less.
            
            // limit the width of the header, forcing the description to wrap itself
            header.setMaximumSize( new Dimension( jplContents.getPreferredSize().width, 
                                                  Integer.MAX_VALUE ) );
            
            // now reset everything for the limited width
            pack();
            
            // now set the preferred height for the dialog, using the new
            // height for the header (which was just reset by the pack)
            setPreferredSize( new Dimension( jplContents.getPreferredSize().width, 
                                             header.getPreferredSize().height +
                                             jplContents.getPreferredSize().height +
                                             jplButtons.getPreferredSize().height ) );
            
            // and redraw again, recalculating everything again
            pack();

            // and again we reset the preferred size
            setPreferredSize( new Dimension( jplContents.getPreferredSize().width, 
                                             header.getPreferredSize().height +
                                             jplContents.getPreferredSize().height +
//                                             jplButtons.getPreferredSize().height ) );
                                             jplButtons.getPreferredSize().height + 25 ) );
            
            // one last pack will ready everything for viewing.
            pack();
                        
        }
        super.setVisible( show );
    }
    
    @Override
    public void setTitle( String s ) {
        super.setTitle(s);
        header.setTitle(s); 
    }
    public void setDescription( String s ) { header.setDescription(s); }
    
    protected void buildMainPanel() {
        
        jplMain = new JPanel();
        jplMain.setLayout( new BoxLayout( jplMain, BoxLayout.PAGE_AXIS ) );
        jplMain.add( header );
        jplContents.setAlignmentX( Component.CENTER_ALIGNMENT );
        jplMain.add( jplContents );
        jplMain.add( jplButtons );
        
    }
    
    protected void initContentsPanel() {
        
        jplContents = new JPanel();
        jplContents.setLayout( new BoxLayout( jplContents, BoxLayout.PAGE_AXIS ));
        
    }
    protected abstract void buildContentsPanel();
    
    protected void initButtonPanel() {
        
        jplButtons = new JPanel();
        jplButtons.setLayout( new FlowLayout( FlowLayout.TRAILING ));
//        jplButtons.setLayout( new BoxLayout( jplButtons, BoxLayout.LINE_AXIS ));
        jplButtons.setBorder( BorderFactory.createEmptyBorder(0, 10, 5, 10));
//        jplButtons.add( Box.createHorizontalGlue() );
        
    }
    
    protected abstract void fillButtonPanel();
    protected void addButton( Component comp ) {
       jplButtons.add( comp ); 
    }
    
}
