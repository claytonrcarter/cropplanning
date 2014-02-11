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
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage;


public class CPSGlobalSettings extends CPSModuleSettings implements CPSConfigurable,
                                                                    ActionListener {

    public static final String PREF_BEDS = "Beds";
    public static final String PREF_ROWS = "Rows";
    public static final String PREF_IMPERIAL = "Imperial (ft/in)";
    public static final String PREF_SI = "Metric (m/cm)";
    public static final String PREF_DATE_US = "US (mm/dd/yy)";
    public static final String PREF_DATE_UK = "UK (dd/mm/yy)";
       
    private static final Class thisClass = CPSGlobalSettings.class;
   
    protected static Preferences globalPrefs = null;
   
    private static final String KEY_ROWSORBEDS = "ROWS_OR_BEDS";
    private JComboBox cmbxPrefRowOrBed;
    private final String[] prefRowOrBedOptions = new String[]{ PREF_BEDS, PREF_ROWS };
    private static final String prefRowOrBedDefault = PREF_BEDS;
    
    private static final String KEY_ROWBEDLENGTH = "BED_LENGTH";
    private JTextField tfldRowOrBedLength;
    private static final int prefRowOrBedLengthDefault = 100;

    private static final String KEY_MEASUREMENT_SYSTEM = "MEASUREMENT_SYSTEM";
    private JComboBox cmbxPrefMeasurementSystem;
    private final List<String> prefMeasurementSystemOptions =
        new ArrayList<String>( Arrays.asList( PREF_IMPERIAL, PREF_SI ) );
    private static final String prefMeasurementSystemDefault = PREF_IMPERIAL;
 
    private static final String KEY_DATE_FORMAT = "DATE_FORMAT";
    private JComboBox cmbxPrefDateFormat;
    private final List<String> prefDateFormatOptions =
        new ArrayList<String>( Arrays.asList( PREF_DATE_US, PREF_DATE_UK ) );
    private static final String prefDateFormatDefault = PREF_DATE_US;

    private static final String KEY_HIGHLIGHTFIELDS = "HIGHLIGHT_FIELDS";
    private JCheckBox ckbxPrefHighlight;
    private static final boolean prefHightlightDefault = true;
    
    private static final String KEY_FARMNAME = "FARM_NAME";
    private JTextField tfldFarmName;
    
    private static final String KEY_OUTPUTDIR = "OUTPUT_DIR";
    private static final String KEY_DOCS_OUTDIR = KEY_OUTPUTDIR;
    private static final String KEY_DATA_OUTDIR = KEY_OUTPUTDIR;
    private JLabel lblPrefOutputDir;
    private JButton btnPrefOutputDir;
    private static JFileChooser flchPrefOutputDir = null;
    private static String sTempOutputDir = null;
    // This should default to JFileChooser.FileSystemView.getDefaultDirectory()
//    private static File prefOutputDirDefault = null;
    private static final String KEY_FIRSTTIME = "FIRST_TIME";
    private static final String KEY_LASTVERSION = "LAST_VERSION";
    private static boolean firstTimeRunFake = false;

    private static final String KEY_LASTFROST = "LAST_FROST";
    private static final String KEY_FIRSTFROST = "FIRST_FROST";
    private JDateChooser jdtcLastFrost, jdtcFirstFrost;
    
    private static final String KEY_FUDGE = "FUDGE_FACTOR";
    private static final String KEY_FUDGE_HIGH = KEY_FUDGE;
    private static final String KEY_FUDGE_LOW = KEY_FUDGE;
    private JTextField tfldFudge, tfldFudgeHigh, tfldFudgeLow;
    private static final float prefFudgeDefault = .20f;
    private static final float prefFudgeHighDefault = prefFudgeDefault;
    private static final float prefFudgeLowDefault = prefFudgeDefault;

    private static final String KEY_CHECK_UPDATES = "CHECK_UPDATES";
    private JCheckBox chkCheckUpdates;

    private static final String KEY_DEBUG = "DEBUG";
    private JCheckBox chkDebug;
    private static final boolean prefDebugDefault = false;

    private static final String KEY_VERSION = "VERSION";
    private static final String cpsMainVersionDefault = "0.0.0";
    private static final String cpsMainVersion = cpsMainVersionDefault;

    
    public CPSGlobalSettings() {

        globalPrefs = Preferences.userNodeForPackage( thisClass );

//       globalPrefs = Preferences.userNodeForPackage( this.getClass() );

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

    public void setBedLength( int i ) {
       getGlobalPreferences().putInt( KEY_ROWBEDLENGTH, i );
    }
    public static boolean getHighlightFields() {
        return getGlobalPreferences().getBoolean( KEY_HIGHLIGHTFIELDS, prefHightlightDefault );
    }


    /** @returns one of either CPSGlobalSettings.PREF_IMPERIAL or CPSGlobalSettings.PREF_SI
     */
    public static String getMeasurementUnit() {
        return getGlobalPreferences().get( KEY_MEASUREMENT_SYSTEM, prefMeasurementSystemDefault );
    }
    protected void setMeasurementUnit( String unit ) {
      if ( prefMeasurementSystemOptions.contains(unit) )
        getGlobalPreferences().put( KEY_MEASUREMENT_SYSTEM, unit );
    }
    
    /**
     * I'm lazy and this is a convenience method for getMeasurementUnit().equals( PREF_SI );
     */
    public static boolean useMetric() {
      return getMeasurementUnit().equals( PREF_SI );
    }

    
    /**
     * @return one of either CPSGlobalSettings.PREF_DATE_US or PREF_DATE_UK
     */
    public static String getDateFormat() {
      return getGlobalPreferences().get( KEY_DATE_FORMAT, prefDateFormatDefault );
    }
    protected void setDateFormat( String format ) {
      if ( prefDateFormatOptions.contains( format ))
        getGlobalPreferences().put( KEY_DATE_FORMAT, format );
    }

    public static boolean useUSDates() {
      return getDateFormat().equals( PREF_DATE_US );
    }



    public static String getDocumentOutputDir() {
        return getOutputDir();
    }
    
    public static String getDataOutputDir() {
        return getOutputDir();
    }
    
    public static String getOutputDir() {
        if ( sTempOutputDir != null )
          return sTempOutputDir;
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

    public static void setTempOutputDir( String tempDir ) {
        sTempOutputDir = tempDir;
    }

    public static String getRowsOrBeds() {
        return getGlobalPreferences().get( KEY_ROWSORBEDS, prefRowOrBedDefault );
    }

    public static boolean getFirstTimeRun() {
        long l = getLastVersionRun();
        return l == -1 || firstTimeRunFake;
    }

    public static void setFirstTimeRun( boolean b ) {
       firstTimeRunFake = b;
    }

    /**
     * @return A long int representing the version of the software when the software
     *         was last run.  Generally, this will be the same as the current version
     *         but will be different, of course, when the user downloads a newer version.
     */
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

    public void setFarmName( String s ) {
       getGlobalPreferences().put( KEY_FARMNAME, s );
    }

    public static Date getLastFrostDate() {
        return CPSDateValidator.simpleParse( getGlobalPreferences().get( KEY_LASTFROST, "" ));
    }

    public void setLastFrostDate( Date d ) {
       getGlobalPreferences().put( KEY_LASTFROST, CPSDateValidator.format( d ) );
    }
    
    public static Date getFirstFrostDate() {
        return CPSDateValidator.simpleParse( getGlobalPreferences().get( KEY_FIRSTFROST, "" ));
    }

    public void setFirstFrostDate( Date d ) {
       getGlobalPreferences().put( KEY_FIRSTFROST, CPSDateValidator.format( d ) );
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

    public static String getVersion() {
       return getGlobalPreferences().get( KEY_VERSION, cpsMainVersionDefault );
    }

    public static void setVersion( String s ) {
       getGlobalPreferences().put( KEY_VERSION, s );
    }


    public static String getModifierKey() {
      if ( System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0 )
        return "meta";
      else
        return "alt";
    }

    public static boolean getCheckForUpdates() {
      return getGlobalPreferences().getBoolean( KEY_CHECK_UPDATES, true );
    }


    public static void setCheckForUpdates( boolean check ) {
      getGlobalPreferences().putBoolean( KEY_CHECK_UPDATES, check );
    }

    /*
     * Methods for CPSConfigurable.
     * Used for displaying a dialog to setup the settings.
     * 
     * 
     */
    
    public JPanel getConfigurationDisplay() {
        resetConfiguration();
        if ( configPanel == null )
          buildConfigPanel();
        return configPanel;
    }

   public CPSWizardPage[] getConfigurationWizardPages () {
      return new CPSWizardPage[] { new OutDirWizardPage(),
                                   new GeneralSettingsWizardPage() };
   }


    public String getModuleName() {
        return "Global Settings";
    }

    public void resetConfiguration() {
      if ( configPanel == null )
          buildConfigPanel();
        cmbxPrefRowOrBed.setSelectedItem( getRowsOrBeds() );
        tfldRowOrBedLength.setText( "" + getBedLength() );
        cmbxPrefMeasurementSystem.setSelectedItem( getMeasurementUnit() );
        cmbxPrefDateFormat.setSelectedItem( getDateFormat() );
        ckbxPrefHighlight.setSelected( getHighlightFields() );
        lblPrefOutputDir.setText( getOutputDir() );
        tfldFarmName.setText( getFarmName() );
        System.out.println( "Last frost date is: " + getLastFrostDate() );
        jdtcLastFrost.setDate( getLastFrostDate() );
        jdtcFirstFrost.setDate( getFirstFrostDate() );
        tfldFudge.setText( "" + 100 * getFudgeFactor() );
        chkDebug.setSelected( getDebug() );
        chkCheckUpdates.setSelected( getCheckForUpdates() );
    }

    public void resetConfigurationToDefaults() {
      if ( configPanel == null )
          buildConfigPanel();
        cmbxPrefRowOrBed.setSelectedItem( prefRowOrBedDefault );
        tfldRowOrBedLength.setText( "" + prefRowOrBedLengthDefault );
        cmbxPrefMeasurementSystem.setSelectedItem( prefMeasurementSystemDefault );
        cmbxPrefDateFormat.setSelectedItem( prefDateFormatDefault );
        ckbxPrefHighlight.setSelected( prefHightlightDefault );
        lblPrefOutputDir = new JLabel( flchPrefOutputDir.getFileSystemView().getDefaultDirectory().getAbsolutePath() );
        tfldFarmName.setText( "" );
        jdtcLastFrost.setDate( null );
        jdtcFirstFrost.setDate( null );
        tfldFudge.setText( "" + 100 * prefFudgeDefault );
        chkDebug.setSelected( prefDebugDefault );
        chkCheckUpdates.setSelected(true);
    }

    public void saveConfiguration() {
      if ( configPanel == null )
          buildConfigPanel();
        getGlobalPreferences().put( KEY_ROWSORBEDS, cmbxPrefRowOrBed.getSelectedItem().toString() );
        // TODO check this for improper input
        setBedLength( Integer.parseInt( tfldRowOrBedLength.getText() ) );
        getGlobalPreferences().put( KEY_MEASUREMENT_SYSTEM, cmbxPrefMeasurementSystem.getSelectedItem().toString() );
        getGlobalPreferences().put( KEY_DATE_FORMAT, cmbxPrefDateFormat.getSelectedItem().toString() );
        getGlobalPreferences().putBoolean( KEY_HIGHLIGHTFIELDS, ckbxPrefHighlight.isSelected() );
        setOutputDir( lblPrefOutputDir.getText() );
        setFarmName( tfldFarmName.getText() );
        setLastFrostDate( jdtcLastFrost.getDate() );
        setFirstFrostDate( jdtcFirstFrost.getDate() );
        getGlobalPreferences().put( KEY_FUDGE, "" + Float.parseFloat( tfldFudge.getText() ) / 100 );
        setDebug( chkDebug.isSelected() );
        setCheckForUpdates( chkCheckUpdates.isSelected() );

    }


  @Override
  protected void initConfigPanel() {
    super.initConfigPanel();


    cmbxPrefRowOrBed = new JComboBox( prefRowOrBedOptions );

    tfldRowOrBedLength = new JTextField( 5 );

    cmbxPrefMeasurementSystem =
        new JComboBox( prefMeasurementSystemOptions.toArray( new String[] {} ));

    cmbxPrefDateFormat =
        new JComboBox( prefDateFormatOptions.toArray( new String[] {} ));

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

    chkCheckUpdates = new JCheckBox("Check for updates?");
   
  }

    private void buildConfigPanel() {

        initConfigPanel();

        int r = 0;

        configPanel.add( new JLabel( "Farm Name" ) );
        configPanel.add( tfldFarmName, "wrap" );

       JLabel tempLabel = new JLabel( "Default Row or Bed Length:" );
       tempLabel.setToolTipText( "Default row or bed length in feet or meters." );
       configPanel.add( tempLabel );
       configPanel.add( tfldRowOrBedLength, "wrap" );

       configPanel.add( new JLabel( "Measurement Units:" ) );
       configPanel.add( cmbxPrefMeasurementSystem, "wrap" );

       configPanel.add( new JLabel( "Date Format" ));
       configPanel.add( cmbxPrefDateFormat, "wrap" );
       
       tfldFudge.setToolTipText( "Percetage value (0-100) to add to some calculations to provide a \"margin of error\"." );
       configPanel.add( new JLabel( "Fudge factor (%)" ));
       configPanel.add( tfldFudge, "wrap" );

       configPanel.add( chkCheckUpdates, "span 2, align center, wrap" );

       configPanel.add( new JLabel( "Output Directory:" ), "align left, wrap" );
       configPanel.add( lblPrefOutputDir, "span 2, align left, wrap" );
       configPanel.add( btnPrefOutputDir, "skip 1, align right, wrap" );

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

       /* for testing only */
   public static void main( String[] args ) {

     boolean b = false;

     if ( b ) {

       JFrame frame = new JFrame();
       frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
       frame.setContentPane( new CPSGlobalSettings().getConfigurationDisplay() );
       frame.setTitle( "Test Layout" );
       frame.pack();
       frame.setVisible(true);

     } else {

//       Wizard wiz = WizardPage.createWizard( new CPSGlobalSettings().getConfigurationWizardPages(),
       Wizard wiz = WizardPage.createWizard( new WizardPage[]{ new OutDirWizardPage(), new GeneralSettingsWizardPage() },
                                             new WizardPage.WizardResultProducer() {

                                               public Object finish ( Map settings ) throws WizardException {
                                                  System.out.println( settings );
                                                  return settings;
                                               }

                                               public boolean cancel ( Map settings ) {
                                                  return true;
                                               }
                                            } );
      WizardDisplayer.showWizard( wiz );

     }
   }

}


//****************************************************************************//
//****************************************************************************//
class OutDirWizardPage extends CPSWizardPage {

   static final String PAGE_OUT_DIR = "outDir";
   static final String SETTING_OUT_DIR = PAGE_OUT_DIR;

   private JFileChooser flchOutDir;
   private boolean outDirIsSelected;
   private Dimension panelDim = new Dimension( 400, 400 );
   private Dimension fileDim = new Dimension( 400, 250 );

   public OutDirWizardPage () {
      super( PAGE_OUT_DIR, getDescription(), CPSWizardPage.WIZ_TYPE_PRE_INIT );

      setLongDescription( getDescription() );

      setPreferredSize( panelDim );
      setMaximumSize( panelDim );

      JLabel lblOutDir = new JLabel( "<html><body style='width: 300px'>" +
                                     "<b>Select a folder or directory to store all of your crop planning data.</b>" +
                                     "<p><p>If you have already used this program to create crop plans and would like to load that information, " +
                                     "please select the folder or directory which contains your existing data. " +
                                     "(The data files have names that start with \"CPSdb\".)" +
                                     "" );
      lblOutDir.setAlignmentX( Component.CENTER_ALIGNMENT );

      String defaultDirectory = CPSGlobalSettings.getOutputDir();
      flchOutDir = new JFileChooser();
      flchOutDir.setSelectedFile( new File( defaultDirectory ) );
      flchOutDir.setName( SETTING_OUT_DIR );
      flchOutDir.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
      flchOutDir.setMultiSelectionEnabled( false );
      flchOutDir.setControlButtonsAreShown( false );
      flchOutDir.addPropertyChangeListener( new PropertyChangeListener() {

         public void propertyChange ( PropertyChangeEvent evt ) {
            if ( evt.getPropertyName().equals( JFileChooser.SELECTED_FILE_CHANGED_PROPERTY ) ) {
               outDirIsSelected = ( evt.getNewValue() != null ) ? true : false;
               userInputReceived( flchOutDir, evt );
            }
         }
      } ); // new PropChangeListener
      flchOutDir.setPreferredSize( fileDim );
      flchOutDir.setMaximumSize( fileDim );

      add( lblOutDir );
      add( flchOutDir );
   }

   @Override
   protected String validateContents ( Component component, Object event ) {
      if ( component == null ||
           ( component == flchOutDir && !outDirIsSelected ) ) {
         return "You must select a directory or folder.";
      }
      else {
         getWizardDataMap().put( SETTING_OUT_DIR, flchOutDir.getSelectedFile().getPath() );
         return null;
      }
   }

   @Override
   public void finishWizard ( CPSGlobalSettings globSet ) {
      globSet.setOutputDir( (String) getWizardDataMap().get( SETTING_OUT_DIR ) );
   }

   public static String getDescription () { return "Output Directory"; }
}



//****************************************************************************//
//****************************************************************************//
class GeneralSettingsWizardPage extends CPSWizardPage {

   protected final static String PAGE_GEN_SET = "genSettings";

   protected final static String SETTING_FARM_NAME = "farmName";
   protected final static String SETTING_BED_LENGTH = "bedLength";
   protected final static String SETTING_MEASUREMENT_UNITS = "measurementUnits";
   protected final static String SETTING_LAST_FROST = "lastFrost";
   protected final static String SETTING_FIRST_FROST = "firstFrost";

   private JTextField tfldFarmName,  tfldBedLength;
   private JComboBox cmbxMeasurementSystem;
   private JDateChooser calLastFrost,  calFirstFrost;


   public GeneralSettingsWizardPage () {
      super( PAGE_GEN_SET, getDescription(), CPSWizardPage.WIZ_TYPE_PRE_INIT );

      setLongDescription( getDescription() );
      setLayout( new MigLayout( "", "[align right][]" ) );

      tfldFarmName = new JTextField( 15 );
      tfldFarmName.setName( SETTING_FARM_NAME );
      tfldFarmName.setText( CPSGlobalSettings.getFarmName() );

      add( new JLabel( "The name of your farm:" ) );
      add( tfldFarmName, "wrap" );

      tfldBedLength = new JTextField( 5 );
      tfldBedLength.setName( SETTING_BED_LENGTH );
      tfldBedLength.setText( Integer.toString( CPSGlobalSettings.getBedLength() ) );

      add( new JLabel( "<html><body style='text-align:right'>Default row or bed&nbsp;<br>length (ft or meters):" ) );
      add( tfldBedLength, "wrap" );

      cmbxMeasurementSystem = new JComboBox( new String[] { CPSGlobalSettings.PREF_IMPERIAL,
                                                            CPSGlobalSettings.PREF_SI });
      cmbxMeasurementSystem.setSelectedItem( CPSGlobalSettings.getMeasurementUnit() );
      cmbxMeasurementSystem.setName( SETTING_MEASUREMENT_UNITS );
      add( new JLabel( "Measurement Units:" ) );
      add( cmbxMeasurementSystem, "wrap" );


      calLastFrost = new JDateChooser( CPSGlobalSettings.getLastFrostDate() );
      calLastFrost.setDateFormatString( "MMMMM d" );
      calLastFrost.setName( SETTING_LAST_FROST );
      calLastFrost.addPropertyChangeListener( new PropertyChangeListener() {
                                                        public void propertyChange ( PropertyChangeEvent evt ) {
                                                           if ( evt.getPropertyName().equals( "date" ))
                                                              userInputReceived( calLastFrost, evt );
                                                        }
                                                  } );

      add( new JLabel( "Last spring frost (optional):" ) );
      add( calLastFrost, "wrap" );

      calFirstFrost = new JDateChooser( CPSGlobalSettings.getFirstFrostDate() );
      calFirstFrost.setDateFormatString( "MMMMM d" );
      calFirstFrost.setName( SETTING_FIRST_FROST );
      calFirstFrost.addPropertyChangeListener( new PropertyChangeListener() {
                                                        public void propertyChange ( PropertyChangeEvent evt ) {
                                                           if ( evt.getPropertyName().equals( "date" ))
                                                              userInputReceived( calFirstFrost, evt );
                                                        }
                                                  } );

      add( new JLabel( "First fall frost (optional):" ) );
      add( calFirstFrost, "wrap" );

   }

   @Override
   protected String validateContents ( Component component, Object event ) {
      if ( component == null ||
              ( component == tfldFarmName && tfldFarmName.getText().trim().length() == 0 ) ) {
         return "You must enter a farm name.";
      }
      else if ( component == tfldBedLength ) {
         try {
            if ( Integer.parseInt( tfldBedLength.getText() ) < 1 )
               return "Row or bed length must be greater than 0.";
         }
         catch ( NumberFormatException e ) {
            return "Row or bed length must be an integer (whole number).";
         }
      }
      else if ( component == calLastFrost ) {
         getWizardDataMap().put( SETTING_LAST_FROST, CPSDateValidator.format( calLastFrost.getDate() ) );
      }
      else if ( component == calFirstFrost ) {
         getWizardDataMap().put( SETTING_FIRST_FROST, CPSDateValidator.format( calFirstFrost.getDate() ) );
      }

      // else no problems
      return null;
   }

   @Override
   public void finishWizard ( CPSGlobalSettings globSet ) {
      globSet.setFarmName( (String) getWizardDataMap().get( SETTING_FARM_NAME ) );
      globSet.setBedLength( Integer.parseInt( (String) getWizardDataMap().get( SETTING_BED_LENGTH ) ) );
      globSet.setMeasurementUnit( (String) getWizardDataMap().get( SETTING_MEASUREMENT_UNITS ) );
      globSet.setLastFrostDate( CPSDateValidator.simpleParse( (String) getWizardDataMap().get( SETTING_LAST_FROST ) ) );
      globSet.setFirstFrostDate( CPSDateValidator.simpleParse( (String) getWizardDataMap().get( SETTING_FIRST_FROST ) ) );
   }

   public static String getDescription () { return "General Settings"; }

}

