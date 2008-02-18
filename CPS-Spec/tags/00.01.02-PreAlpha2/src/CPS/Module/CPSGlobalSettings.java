/* CPSGlobalSettings.java - created: Feb 13, 2008
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

package CPS.Module;

import CPS.UI.Swing.LayoutAssist;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CPSGlobalSettings extends CPSModuleSettings implements CPSConfigurable,
                                                                    ActionListener {

    public static final String PREF_BEDS = "Beds";
    public static final String PREF_ROWS = "Rows";
    public static final String PREF_FEET = "Feet";
    public static final String PREF_METERS = "Meters";
       
    private static Class thisClass = CPSGlobalSettings.class;
   
    protected static Preferences globalPrefs = null;
   
    private static final String KEY_ROWSORBEDS = "ROWS_OR_BEDS";
    private JComboBox cmbxPrefRowOrBed;
    private String[] prefRowOrBedOptions = new String[]{ PREF_BEDS, PREF_ROWS };
    private static String prefRowOrBedDefault = PREF_BEDS;
    private static final String KEY_ROWBEDLENGTH = "BED_LENGTH";
    private JTextField tfldRowOrBedLength;
    private static int prefRowOrBedLengthDefault = 100;
    private static final String KEY_UNITOFLENGTH = "LENGTH_UNIT";
    private JComboBox cmbxPrefUnitLength;
    private String[] prefUnitLengthOptions = new String[]{ PREF_FEET, PREF_METERS };
    private static String prefUnitLengthDefault = PREF_FEET;
    private static final String KEY_HIGHLIGHTFIELDS = "HIGHLIGHT_FIELDS";
    private JCheckBox ckbxPrefHighlight;
    private static boolean prefHightlightDefault = true;
    private static final String KEY_OUTPUTDIR = "OUTPUT_DIR";
    private static final String KEY_DOCS_OUTDIR = KEY_OUTPUTDIR;
    private static final String KEY_DATA_OUTDIR = KEY_OUTPUTDIR;
    private JLabel lblPrefOutputDir;
    private JButton btnPrefOutputDir;
    private static JFileChooser flchPrefOutputDir = null;
    // This should default to JFileChooser.FileSystemView.getDefaultDirectory()
//    private static File prefOutputDirDefault = null;
    private static final String KEY_FIRSTTIME = "FIRST_TIME";
    private static final String KEY_LASTVERSION = "LAST_VERSION";

    
    public CPSGlobalSettings() {

        globalPrefs = Preferences.userNodeForPackage( thisClass );

//       globalPrefs = Preferences.userNodeForPackage( this.getClass() );

        cmbxPrefRowOrBed = new JComboBox( prefRowOrBedOptions );

        tfldRowOrBedLength = new JTextField( 5 );

        cmbxPrefUnitLength = new JComboBox( prefUnitLengthOptions );

        ckbxPrefHighlight = new JCheckBox();

        btnPrefOutputDir = new JButton( "Change Output Directory" );
        btnPrefOutputDir.addActionListener( this );
        lblPrefOutputDir = new JLabel();
        flchPrefOutputDir = new JFileChooser();
        flchPrefOutputDir.setMultiSelectionEnabled( false );
        flchPrefOutputDir.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

        buildConfigPanel();

    }

    protected static Preferences getGlobalPreferences() {
        if ( globalPrefs == null ) {
            return Preferences.userNodeForPackage( thisClass );
        }
        else
            return globalPrefs;
//            return super.getModulePreferences();
    }

    public static int getBedLength() {
        return getGlobalPreferences().getInt( KEY_ROWBEDLENGTH, prefRowOrBedLengthDefault );
    }

    public static boolean getHighlightFields() {
        return getGlobalPreferences().getBoolean( KEY_HIGHLIGHTFIELDS, prefHightlightDefault );
    }

    public static String getMeasurementUnit() {
        return getGlobalPreferences().get( KEY_UNITOFLENGTH, prefUnitLengthDefault );
    }

    public static String getDocumentOutputDir() {
        return getOutputDir();
    }
    
    public static String getDataOutputDir() {
        return getOutputDir();
    }
    
    public static String getOutputDir() {
        if ( flchPrefOutputDir == null )    
            return getGlobalPreferences().get( KEY_OUTPUTDIR,
                                               new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath() );
        else
            return getGlobalPreferences().get( KEY_OUTPUTDIR,
                                               flchPrefOutputDir.getFileSystemView().getDefaultDirectory().getAbsolutePath() );
    }

    public void setOutputDir( String dir ) {
        getGlobalPreferences().put( KEY_OUTPUTDIR, dir );
    }

    public static String getRowsOrBeds() {
        return getGlobalPreferences().get( KEY_ROWSORBEDS, prefRowOrBedDefault );
    }

    public static boolean getFirstTimeRun() {
        long l = getLastVersionRun();
        return l == -1;
    }

    public static long getLastVersionRun() {
        return getGlobalPreferences().getLong( KEY_LASTVERSION, -1 );
    }

    public void setLastVersionUsed( long l ) {
        System.out.println("Setting last version used to " + l );
        getGlobalPreferences().putLong( KEY_LASTVERSION, l );
    }

    /*
     * Methods for CPSConfigurable.
     * Used for displaying a dialog to setup the settings.
     * 
     * 
     */
    
    public JPanel getConfigurationDisplay() {
        resetConfiguration();
        return configPanel;
    }

    public String getModuleName() {
        return "Global Settings";
    }

    public void resetConfiguration() {
        cmbxPrefRowOrBed.setSelectedItem( getRowsOrBeds() );
        tfldRowOrBedLength.setText( "" + getBedLength() );
        cmbxPrefUnitLength.setSelectedItem( getMeasurementUnit() );
        ckbxPrefHighlight.setSelected( getHighlightFields() );
        lblPrefOutputDir.setText( getOutputDir() );
    }

    public void resetConfigurationToDefaults() {
        cmbxPrefRowOrBed.setSelectedItem( prefRowOrBedDefault );
        tfldRowOrBedLength = new JTextField( "" + prefRowOrBedLengthDefault, 5 );
        cmbxPrefUnitLength.setSelectedItem( prefUnitLengthDefault );
        ckbxPrefHighlight.setSelected( prefHightlightDefault );
        lblPrefOutputDir = new JLabel( flchPrefOutputDir.getFileSystemView().getDefaultDirectory().getAbsolutePath() );
    }

    public void saveConfiguration() {
        getGlobalPreferences().put( KEY_ROWSORBEDS, cmbxPrefRowOrBed.getSelectedItem().toString() );
        getGlobalPreferences().putInt( KEY_ROWBEDLENGTH, Integer.parseInt( tfldRowOrBedLength.getText() ) );
        getGlobalPreferences().put( KEY_UNITOFLENGTH, cmbxPrefUnitLength.getSelectedItem().toString() );
        getGlobalPreferences().putBoolean( KEY_HIGHLIGHTFIELDS, ckbxPrefHighlight.isSelected() );
        setOutputDir( lblPrefOutputDir.getText() );
    }
  
    private void buildConfigPanel() {

        initConfigPanel();

//       cmbxPrefRowOrBed.setToolTipText( "Is your farm primarily based on rows or beds?" );
//       LayoutAssist.addLabel(    configPanel, 0, 0, new JLabel( "Rows or Beds?" ) );
//       LayoutAssist.addComboBox( configPanel, 1, 0, cmbxPrefRowOrBed );
//       
       tfldRowOrBedLength.setToolTipText( "Default row or bed length which will be used when none is specifed." );
       LayoutAssist.addLabel(     configPanel, 0, 1, new JLabel( "Default Row or Bed Length:" ) );
       LayoutAssist.addTextField( configPanel, 1, 1, tfldRowOrBedLength );
       
//       cmbxPrefUnitLength.setToolTipText( "Do you measure your plantings in feet or meters?" );
//       LayoutAssist.addLabel(    configPanel, 0, 2, new JLabel( "Unit of Measure:" ) );
//       LayoutAssist.addComboBox( configPanel, 1, 2, cmbxPrefUnitLength );
//       
//       ckbxPrefHighlight.setText( "Highlight special data fields?" );
//       ckbxPrefHighlight.setHorizontalTextPosition( JCheckBox.LEADING );
//       ckbxPrefHighlight.setToolTipText( "Highlight fields which are \"inherited\" or \"auto-calculated\"?" );
//       LayoutAssist.addButton( configPanel, 0, 3, 2, 1, ckbxPrefHighlight );  

       LayoutAssist.addLabelLeftAlign( configPanel, 0, 4, new JLabel( "Output Directory:" ) );
       LayoutAssist.addLabelLeftAlign( configPanel, 0, 5, 2, 1, lblPrefOutputDir );
       LayoutAssist.addButton( configPanel,    1, 6, btnPrefOutputDir );

//        LayoutAssist.addLabelLeftAlign( configPanel, 0, 0, new JLabel( "Output Directory:" ) );
//        LayoutAssist.addLabelLeftAlign( configPanel, 0, 1, 2, 1, lblPrefOutputDir );
//        LayoutAssist.addButton( configPanel, 1, 2, btnPrefOutputDir );

    }

    public void actionPerformed( ActionEvent arg0 ) {
        String action = arg0.getActionCommand();

        if ( action.equalsIgnoreCase( btnPrefOutputDir.getText() ) ) {
            flchPrefOutputDir.setVisible( true );
            int status = flchPrefOutputDir.showDialog( configPanel, "Accept" );
            if ( status == JFileChooser.APPROVE_OPTION ) {
                lblPrefOutputDir.setText( flchPrefOutputDir.getSelectedFile().getPath() );
            }
        }
    }
   
   
    
}
