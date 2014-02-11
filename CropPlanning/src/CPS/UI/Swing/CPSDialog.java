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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTitledPanel;

public abstract class CPSDialog extends JDialog {

    protected JXTitledPanel header;
    private JPanel jplMain;
    protected JPanel jplContents;
    protected JPanel jplButtons;

    protected boolean contentsPanelBuilt = false;
    
    public CPSDialog ( String title ) {
      this( new JPanel(), title );
    }

    public CPSDialog ( JPanel parent, String title ) {

      super( (JFrame) SwingUtilities.getWindowAncestor(parent), true );

      setLocationRelativeTo( parent );
      
        header = new JXTitledPanel();
        header.setBorder(BorderFactory.createEmptyBorder());
        setTitle( title );
        
        initContentsPanel();
        
        initButtonPanel();
        fillButtonPanel();
        
        buildMainPanel();
     
        super.add( jplMain );
        
    }

    
    @Override public Component add( Component arg0 ) {
        if ( ! ( arg0 instanceof JPanel )) {
            JPanel jp = new JPanel( new MigLayout("wrap 1, align left, gapy 0px!, insets 2px") );
            jp.setBorder( BorderFactory.createEmptyBorder(0, 10, 0, 10));
            jp.add( arg0 );
            return jplContents.add( jp );
        }
        else
            return jplContents.add( arg0 ); 
    }
    
    @Override public void setVisible( boolean show ) {
      if ( ! contentsPanelBuilt )
        buildContentsPanel();

        if ( show ) {
          pack();
          setResizable(false);
        }
        super.setVisible( show );
    }
    
    @Override
    public void setTitle( String s ) {
        super.setTitle(s);
        setHeaderTitle( s );
    }

    public void setHeaderTitle( String s ) {
      header.setTitle(s);
    }

    /**
     * @param s Text to be displayed.  This will be treated as HTML.
     */
    public void setDescription( String s ) {
      JPanel jpl = new JPanel( new MigLayout( "insets n n 0px n, fillx") );
      jpl.add( new JLabel( "<html>" + s + "</html>" ), "wrap" );
      jpl.add( new JSeparator( JSeparator.HORIZONTAL ), "growx, wrap" );
      header.setContentContainer(jpl);
    }
    
    protected void buildMainPanel() {
        
        jplMain = new JPanel( new MigLayout("wrap 1, gapy 0px!, insets 2px") );
        jplMain.add( header, "growx" );
        jplMain.add( jplContents, "align center" );
        jplMain.add( jplButtons, "align right" );
        
    }
    
    protected void initContentsPanel() {
        
        jplContents = new JPanel(new MigLayout("gapy 0px!, insets 2px"));
        
    }
    protected abstract void buildContentsPanel();
    
    protected void initButtonPanel() {
        
        jplButtons = new JPanel(new MigLayout("gapy 0px!, insets 2px", "align right"));
        jplButtons.setBorder( BorderFactory.createEmptyBorder(0, 10, 5, 10));
        
    }
    
    protected abstract void fillButtonPanel();
    protected void addButton( Component comp ) {
       jplButtons.add( comp ); 
    }

}
