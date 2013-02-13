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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.netbeans.spi.wizard.WizardPage;







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
    private static float prefFudgeDefault = .20f;
    private static float prefFudgeHighDefault = prefFudgeDefault;
    private static float prefFudgeLowDefault = prefFudgeDefault;
    
    private static final String KEY_DEBUG = "DEBUG";
    private JCheckBox chkDebug;
    private static boolean prefDebugDefault = false;

    private static final String KEY_VERSION = "VERSION";
    private static final String cpsMainVersionDefault = "0.0.0";
    private static String cpsMainVersion = cpsMainVersionDefault;

    
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

    public static String getModifierKey() {
      if ( System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0 )
        return "meta";
      else
        return "alt";
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
        cmbxPrefUnitLength.setSelectedItem( getMeasurementUnit() );
        ckbxPrefHighlight.setSelected( getHighlightFields() );
        lblPrefOutputDir.setText( getOutputDir() );
        tfldFarmName.setText( getFarmName() );
        System.out.println( "Last frost date is: " + getLastFrostDate() );
        jdtcLastFrost.setDate( getLastFrostDate() );
        jdtcFirstFrost.setDate( getFirstFrostDate() );
        tfldFudge.setText( "" + 100 * getFudgeFactor() );
        chkDebug.setSelected( getDebug() );
    }

    public void resetConfigurationToDefaults() {
      if ( configPanel == null )
          buildConfigPanel();
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
      if ( configPanel == null )
          buildConfigPanel();
        getGlobalPreferences().put( KEY_ROWSORBEDS, cmbxPrefRowOrBed.getSelectedItem().toString() );
        // TODO check this for improper input
        setBedLength( Integer.parseInt( tfldRowOrBedLength.getText() ) );
        getGlobalPreferences().put( KEY_UNITOFLENGTH, cmbxPrefUnitLength.getSelectedItem().toString() );
        getGlobalPreferences().putBoolean( KEY_HIGHLIGHTFIELDS, ckbxPrefHighlight.isSelected() );
        setOutputDir( lblPrefOutputDir.getText() );
        setFarmName( tfldFarmName.getText() );
        setLastFrostDate( jdtcLastFrost.getDate() );
        setFirstFrostDate( jdtcFirstFrost.getDate() );
        getGlobalPreferences().put( KEY_FUDGE, "" + Float.parseFloat( tfldFudge.getText() ) / 100 );
        setDebug( chkDebug.isSelected() );
    }


  @Override
  protected void initConfigPanel() {
    super.initConfigPanel();


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

      JLabel lblOutDir = new JLabel( "<html><table width=400><tr><td>" +
                                     "<b>Select a folder or directory to store all of your crop planning data.</b>" +
                                     "<p><p>If you have already used this program to create crop plans and would like to load that information, " +
                                     "please select the folder or directory which contains your existing data. " +
                                     "(The data files have names that start with \"CPSdb\".)" +
                                     "</td></tr></table></html>" );
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


class GeneralSettingsWizardPage extends CPSWizardPage {

   protected final static String PAGE_GEN_SET = "genSettings";

   protected final static String SETTING_FARM_NAME = "farmName";
   protected final static String SETTING_BED_LENGTH = "bedLength";
   protected final static String SETTING_LAST_FROST = "lastFrost";
   protected final static String SETTING_FIRST_FROST = "firstFrost";

   private JTextField tfldFarmName,  tfldBedLength;
   private JDateChooser calLastFrost,  calFirstFrost;

   public GeneralSettingsWizardPage () {
      super( PAGE_GEN_SET, getDescription(), CPSWizardPage.WIZ_TYPE_PRE_INIT );

      setLongDescription( getDescription() );

      JPanel jpl;

      tfldFarmName = new JTextField( 15 );
      tfldFarmName.setName( SETTING_FARM_NAME );

      jpl = new JPanel();
      jpl.add( new JLabel( "The name of your farm:" ) );
      jpl.add( tfldFarmName );

      add( Box.createVerticalGlue() );
      add( jpl );

      tfldBedLength = new JTextField( 5 );
      tfldBedLength.setName( SETTING_BED_LENGTH );
      tfldBedLength.setText( Integer.toString( CPSGlobalSettings.getBedLength() ) );

      jpl = new JPanel();
      jpl.add( new JLabel( "Default row or bed length:" ) );
      jpl.add( tfldBedLength );

      add( jpl );

      calLastFrost = new JDateChooser( CPSGlobalSettings.getLastFrostDate() );
      calLastFrost.setDateFormatString( "MMMMM d" );
      calLastFrost.setName( SETTING_LAST_FROST );
      calLastFrost.addPropertyChangeListener( new PropertyChangeListener() {
                                                        public void propertyChange ( PropertyChangeEvent evt ) {
                                                           if ( evt.getPropertyName().equals( "date" ))
                                                              userInputReceived( calLastFrost, evt );
                                                        }
                                                  } );

      jpl = new JPanel();
      jpl.add( new JLabel( "Date of last spring frost (optional):" ) );
      jpl.add( calLastFrost );

      add( jpl );

      calFirstFrost = new JDateChooser( CPSGlobalSettings.getFirstFrostDate() );
      calFirstFrost.setDateFormatString( "MMMMM d" );
      calFirstFrost.setName( SETTING_FIRST_FROST );
      calFirstFrost.addPropertyChangeListener( new PropertyChangeListener() {
                                                        public void propertyChange ( PropertyChangeEvent evt ) {
                                                           if ( evt.getPropertyName().equals( "date" ))
                                                              userInputReceived( calFirstFrost, evt );
                                                        }
                                                  } );

      jpl = new JPanel();
      jpl.add( new JLabel( "Date of first fall frost (optional):" ) );
      jpl.add( calFirstFrost );

      add( jpl );
      add( Box.createVerticalGlue() );

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
      globSet.setLastFrostDate( CPSDateValidator.simpleParse( (String) getWizardDataMap().get( SETTING_LAST_FROST ) ) );
      globSet.setFirstFrostDate( CPSDateValidator.simpleParse( (String) getWizardDataMap().get( SETTING_FIRST_FROST ) ) );
   }

   public static String getDescription () { return "General Settings"; }
}

