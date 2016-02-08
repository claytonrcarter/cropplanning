/* SettingsDialog.java - created: Feb 13, 2008
 * Copyright (C) 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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

package CPS.Core.UI;

import CPS.Module.CPSConfigurable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SettingsDialog extends JDialog implements ActionListener {

    JPanel jplContent, jplButtons;
    JTabbedPane tabbedPane;
    JButton btnSave, btnCancel;
    
    protected ArrayList<CPSConfigurable> configurables;
    
    
    public SettingsDialog() {
    
        super( (JFrame) null, "Fiddle With Program Settings", true );
        
        configurables = new ArrayList<CPSConfigurable>();
    
        jplContent = new JPanel();
        jplContent.setLayout( new BoxLayout( jplContent, BoxLayout.PAGE_AXIS ) );
        
        tabbedPane = new JTabbedPane();
        
        btnSave = new JButton( "Save" );
        btnSave.addActionListener( this );
        btnCancel = new JButton( "Cancel" );
        btnCancel.addActionListener( this );
        
        jplButtons = new JPanel();
        jplButtons.setLayout( new BoxLayout( jplButtons, BoxLayout.LINE_AXIS ) );
        jplButtons.add( Box.createHorizontalGlue() );
        jplButtons.add( btnSave );
        jplButtons.add( btnCancel );
        
        jplContent.add( tabbedPane );
        jplContent.add( jplButtons );
        this.add( jplContent );
        
    }
    
    public void addModuleConfiguration( CPSConfigurable c ) {
        configurables.add(c);
    }
    
    
    /* p-p */ 
    void buildTabs() {
        
        tabbedPane.removeAll();
        
        for ( CPSConfigurable c : configurables ) {
           JPanel jp = c.getConfigurationDisplay();
           if ( jp != null )
              tabbedPane.addTab( c.getModuleName(), jp );
        }
        
        this.pack();
        
    }

    
    
    @Override
    public void setVisible( boolean arg0 ) {
        
        if ( arg0 )
            buildTabs();
        
        super.setVisible( arg0 );
        
    }

    
    public void actionPerformed( ActionEvent arg0 ) {
        
        String action = arg0.getActionCommand();
        
        if ( action.equalsIgnoreCase( btnSave.getText() ) ) {
            
            // save settings in all modules
            for ( CPSConfigurable c : configurables )
                c.saveConfiguration();
            
        }
        else if ( action.equalsIgnoreCase( btnCancel.getText() )) {
            
            // discard configuration in all modules
            for ( CPSConfigurable c : configurables )
                c.resetConfiguration();
            
        }
        
        // go away
        this.setVisible(false);
        
    }
    
}
