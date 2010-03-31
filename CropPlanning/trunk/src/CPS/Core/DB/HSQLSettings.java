/* HSQLSettings.java - created: Feb 13, 2008
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

package CPS.Core.DB;

import CPS.Module.CPSModule;
import CPS.Module.CPSModuleSettings;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class HSQLSettings extends CPSModuleSettings implements ActionListener,
                                                               ItemListener {
    
    public static final String KEY_USEGLOBALDIR = "USE_GLOBAL";
    public static final String KEY_CUSTOMOUTDIR = "CUSTOM_DIR";
    
    protected JRadioButton rdoUseGlobalDir, rdoUseCustomDir;
    protected boolean prefUseGlobalDirDefault = true;
    protected JLabel lblCustomOutDir;
    protected JFileChooser flchCustomDir;
    protected JButton btnCustomDir;
    
    
    public HSQLSettings() {
        super( HSQLDB.class );
        CPSModule.debug( "HSQLSettings", "using pref node:" + HSQLDB.class.toString() );
        
        rdoUseGlobalDir = new JRadioButton( "Use global output directory", false );
        rdoUseGlobalDir.addItemListener(this);
        rdoUseCustomDir = new JRadioButton( "Use other directory:", false );
        rdoUseCustomDir.addItemListener(this);
        ButtonGroup bg = new ButtonGroup();
        bg.add( rdoUseCustomDir );
        bg.add( rdoUseGlobalDir );
        
        lblCustomOutDir = new JLabel();
        
        flchCustomDir = new JFileChooser();
        flchCustomDir.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        
        btnCustomDir = new JButton( "Choose Output Directory" );
        btnCustomDir.addActionListener(this);
        
        buildConfigPanel();
        rdoUseGlobalDir.doClick();
        
    }
    
    public boolean getUseGlobalDir() {
        return getModulePreferences().getBoolean( KEY_USEGLOBALDIR, prefUseGlobalDirDefault );
    }
    
    public String getCustomOutDir() {
        // TODO if we were really suave, this would default to the global output directory
        // for now, though, this should be fine
        return getModulePreferences().get( KEY_CUSTOMOUTDIR, 
                                          flchCustomDir.getFileSystemView().getDefaultDirectory().getAbsolutePath() );
    }
    
    
    private void buildConfigPanel() {
        
        initConfigPanel();
        
        GridBagConstraints c = new GridBagConstraints();  
        c.insets = new Insets( 0, 0, 0, 0 );
	
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        configPanel.add( new JLabel("Database output directory:"), c );
        
//        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridy = 1;
        configPanel.add( rdoUseGlobalDir, c );
        c.gridy = 2;
        configPanel.add( rdoUseCustomDir, c );
        
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 3;
        c.gridwidth = 2;
        configPanel.add( lblCustomOutDir, c );
        
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridy = 4;
        c.gridwidth = 1;
        configPanel.add( btnCustomDir, c );
        
    }

    public JPanel getConfigurationDisplay() {
        resetConfiguration();
        return configPanel;
    }

    public void resetConfiguration() {
        if ( getUseGlobalDir() )
            rdoUseGlobalDir.doClick();
        else
            rdoUseCustomDir.doClick();
        
        lblCustomOutDir.setText( getCustomOutDir() );
    }

    public void resetConfigurationToDefaults() {
        rdoUseGlobalDir.doClick();
        lblCustomOutDir.setText( flchCustomDir.getFileSystemView().getDefaultDirectory().getAbsolutePath() );
    }

    public void saveConfiguration() {
        getModulePreferences().putBoolean( KEY_USEGLOBALDIR, rdoUseGlobalDir.isSelected() );
        getModulePreferences().put( KEY_CUSTOMOUTDIR, lblCustomOutDir.getText() );
    }

    public void itemStateChanged( ItemEvent arg0 ) {
        Object source = arg0.getSource();
        
        if ( source == rdoUseGlobalDir ) {
            btnCustomDir.setEnabled(false);
        }
        else if ( source == rdoUseCustomDir ) {
            btnCustomDir.setEnabled(true);
        }
        
    }

    
    
    public void actionPerformed( ActionEvent arg0 ) {
        String action = arg0.getActionCommand();
       
        if ( action.equalsIgnoreCase( btnCustomDir.getText() ) ) {
           flchCustomDir.setVisible(true);
           int status = flchCustomDir.showDialog( configPanel, "Accept" ); 
            if ( status == JFileChooser.APPROVE_OPTION ) {
                lblCustomOutDir.setText( flchCustomDir.getSelectedFile().getPath() );   
            }
        }    
        
    }
    
}
