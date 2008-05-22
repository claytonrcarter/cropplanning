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

import CPS.Data.CPSDateValidator;
import CPS.UI.Swing.LayoutAssist;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.prefs.Preferences;
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
    
    private static final String KEY_FARMNAME = "FARM_NAME";
    private JTextField tfldFarmName;
    
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

    private static final String KEY_LASTFROST = "LAST_FROST";
    private static final String KEY_FIRSTFROST = "FIRST_FROST";
    private JDateChooser jdtcLastFrost, jdtcFirstFrost;
    
    private static final String KEY_FUDGE = "FUDGE_FACTOR";
    private static final String KEY_FUDGE_HIGH = KEY_FUDGE;
    private static final String KEY_FUDGE_LOW = KEY_FUDGE;
    private JTextField tfldFudge, tfldFudgeHigh, tfldFudgeLow;
    private static float prefFudgeDefault = .20f;
    private static float prefFudgeHighDefault = prefFudgeDefault;
    private static float prefFudgeLowDefault = prefFudgeDefault;
    
    private static final String KEY_DEBUG = "DEBUG";
    private JCheckBox chkDebug;
    private static boolean prefDebugDefault = false;
    
    
    public CPSGlobalSettings() {

        globalPrefs = Preferences.userNodeForPackage( thisClass );

//       globalPrefs = Preferences.userNodeForPackage( this.getClass() );

        cmbxPrefRowOrBed = new JComboBox( prefRowOrBedOptions );

        tfldRowOrBedLength = new JTextField( 5 );

        cmbxPrefUnitLength = new JComboBox( prefUnitLengthOptions );

        ckbxPrefHighlight = new JCheckBox();
        
        tfldFarmName = new JTextField( 15 );

        btnPrefOutputDir = new JButton( "Change Output Directory" );
        btnPrefOutputDir.addActionListener( this );
        lblPrefOutputDir = new JLabel();
        flchPrefOutputDir = new JFileChooser();
        flchPrefOutputDir.setMultiSelectionEnabled( false );
        flchPrefOutputDir.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

        jdtcLastFrost = new JDateChooser();
        jdtcFirstFrost = new JDateChooser();
        jdtcLastFrost.setDateFormatString( "MMMMM d" );
        jdtcFirstFrost.setDateFormatString( "MMMMM d" );
        
        tfldFudge = new JTextField(5);
        
        chkDebug = new JCheckBox();
        
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
        CPSModule.debug( "GlobalSettings", "Setting last version used to " + l );
        getGlobalPreferences().putLong( KEY_LASTVERSION, l );
    }

    public static String getFarmName() {
        return getGlobalPreferences().get( KEY_FARMNAME, "" );
    }
    
    public static Date getLastFrostDate() {
        return CPSDateValidator.simpleParse( getGlobalPreferences().get( KEY_LASTFROST, "" ));
    }
    
    public static Date getFirstFrostDate() {
        return CPSDateValidator.simpleParse( getGlobalPreferences().get( KEY_FIRSTFROST, "" ));
    }
    
    public static float getFudgeFactor() {
        return getGlobalPreferences().getFloat( KEY_FUDGE, prefFudgeDefault );
    }
    
    public static float getFudgeFactorHigh() {
        return getGlobalPreferences().getFloat( KEY_FUDGE_HIGH, prefFudgeHighDefault );
    }
    
    public static float getFudgeFactorLow() {
        return getGlobalPreferences().getFloat( KEY_FUDGE_LOW, prefFudgeLowDefault );
    }
    
    public static boolean getDebug() {
       return getGlobalPreferences().getBoolean( KEY_DEBUG, prefDebugDefault );
    }
    
    public static void setDebug( boolean debug ) {
       getGlobalPreferences().putBoolean( KEY_DEBUG, debug );
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
        tfldFarmName.setText( getFarmName() );
        jdtcLastFrost.setDate( getLastFrostDate() );
        jdtcFirstFrost.setDate( getFirstFrostDate() );
        tfldFudge.setText( "" + 100 * getFudgeFactor() );
        chkDebug.setSelected( getDebug() );
    }

    public void resetConfigurationToDefaults() {
        cmbxPrefRowOrBed.setSelectedItem( prefRowOrBedDefault );
        tfldRowOrBedLength.setText( "" + prefRowOrBedLengthDefault );
        cmbxPrefUnitLength.setSelectedItem( prefUnitLengthDefault );
        ckbxPrefHighlight.setSelected( prefHightlightDefault );
        lblPrefOutputDir = new JLabel( flchPrefOutputDir.getFileSystemView().getDefaultDirectory().getAbsolutePath() );
        tfldFarmName.setText( "" );
        jdtcLastFrost.setDate( null );
        jdtcFirstFrost.setDate( null );
        tfldFudge.setText( "" + 100 * prefFudgeDefault );
        chkDebug.setSelected( prefDebugDefault );
    }

    public void saveConfiguration() {
        getGlobalPreferences().put( KEY_ROWSORBEDS, cmbxPrefRowOrBed.getSelectedItem().toString() );
        // TODO check this for improper input
        getGlobalPreferences().putInt( KEY_ROWBEDLENGTH, Integer.parseInt( tfldRowOrBedLength.getText() ) );
        getGlobalPreferences().put( KEY_UNITOFLENGTH, cmbxPrefUnitLength.getSelectedItem().toString() );
        getGlobalPreferences().putBoolean( KEY_HIGHLIGHTFIELDS, ckbxPrefHighlight.isSelected() );
        setOutputDir( lblPrefOutputDir.getText() );
        getGlobalPreferences().put( KEY_FARMNAME, tfldFarmName.getText() );
        getGlobalPreferences().put( KEY_LASTFROST, CPSDateValidator.format( jdtcLastFrost.getDate() ) );
        getGlobalPreferences().put( KEY_FIRSTFROST, CPSDateValidator.format( jdtcFirstFrost.getDate() ));
        getGlobalPreferences().put( KEY_FUDGE, "" + Float.parseFloat( tfldFudge.getText() ) / 100 );
        getGlobalPreferences().putBoolean( KEY_DEBUG, chkDebug.isSelected() );
    }
  
    private void buildConfigPanel() {

        initConfigPanel();

        LayoutAssist.addLabel(     configPanel, 0, 0, new JLabel( "Farm Name" ) );
        LayoutAssist.addTextField( configPanel, 1, 0, tfldFarmName );
        
//       cmbxPrefRowOrBed.setToolTipText( "Is your farm primarily based on rows or beds?" );
//       LayoutAssist.addLabel(    configPanel, 0, 0, new JLabel( "Rows or Beds?" ) );
//       LayoutAssist.addComboBox( configPanel, 1, 0, cmbxPrefRowOrBed );
//       
       tfldRowOrBedLength.setToolTipText( "Default row or bed length which will be used when none is specifed." );
       LayoutAssist.addLabel(     configPanel, 0, 1, new JLabel( "Default Row or Bed Length:" ) );
       LayoutAssist.addTextField( configPanel, 1, 1, tfldRowOrBedLength );
       
       tfldFudge.setToolTipText( "Percetage value (0-100) to add to some calculations to provide a \"margin of error\"." );
       LayoutAssist.addLabel(     configPanel, 0, 2, new JLabel( "Fudge factor (%)" ));
       LayoutAssist.addTextField( configPanel, 1, 2, tfldFudge );
       
       jdtcLastFrost.setToolTipText( "Average or working date of last spring frost." );
       LayoutAssist.addLabel(     configPanel, 0, 3, new JLabel( "Date of last spring frost" ));
       LayoutAssist.addComponent( configPanel, 1, 3, jdtcLastFrost );
       
       jdtcLastFrost.setToolTipText( "Average or working date of first fall frost." );
       LayoutAssist.addLabel(     configPanel, 0, 4, new JLabel( "Date of first fall frost" ));
       LayoutAssist.addComponent( configPanel, 1, 4, jdtcFirstFrost );
       
       
//       cmbxPrefUnitLength.setToolTipText( "Do you measure your plantings in feet or meters?" );
//       LayoutAssist.addLabel(    configPanel, 0, 2, new JLabel( "Unit of Measure:" ) );
//       LayoutAssist.addComboBox( configPanel, 1, 2, cmbxPrefUnitLength );
//       
//       ckbxPrefHighlight.setText( "Highlight special data fields?" );
//       ckbxPrefHighlight.setHorizontalTextPosition( JCheckBox.LEADING );
//       ckbxPrefHighlight.setToolTipText( "Highlight fields which are \"inherited\" or \"auto-calculated\"?" );
//       LayoutAssist.addButton( configPanel, 0, 3, 2, 1, ckbxPrefHighlight );  

       LayoutAssist.addLabelLeftAlign( configPanel, 0, 5, new JLabel( "Output Directory:" ) );
       LayoutAssist.addLabelLeftAlign( configPanel, 0, 6, 2, 1, lblPrefOutputDir );
       LayoutAssist.addButton( configPanel,    1, 7, btnPrefOutputDir );

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
