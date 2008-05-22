/* TODOLists.java - created: Jan 30, 2008
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

package CPS.Core.TODOLists;

import CPS.Data.CPSComplexPlantingFilter;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSDisplayableDataUserModule;
import CPS.Module.CPSGlobalSettings;
import CPS.UI.Swing.CPSComplexFilterDialog;
import CPS.UI.Swing.CPSTable;
import CPS.UI.Swing.LayoutAssist;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TODOLists extends CPSDisplayableDataUserModule implements ActionListener, ItemListener, PropertyChangeListener {

    private JPanel jplTodo;
    private JComboBox cmbPlanName;
    private JRadioButton rdoDateThisWeek, rdoDateNextWeek, rdoDateThisNextWeek, rdoDateOther;
    private JDateChooser dtcDateOtherStart, dtcDateOtherEnd;
    private JRadioButton rdoUncompThisWeek, rdoUncompLastWeek, rdoUncompAll;
//    private File outputFile;
    private JLabel lblDirectory;
//    private JTextField fldFile;
    private JFileChooser filFile;
    private JButton btnSelectFile, btnPlantList, btnGHList;
    
    private GregorianCalendar tempCal;
    
    CPSComplexFilterDialog cfd = new CPSComplexFilterDialog();
    
    PDFExporter exporter = new PDFExporter();
    
    public TODOLists() {
        setModuleName( "TODOLists" );
        setModuleType( "Core" );
        setModuleVersion( GLOBAL_DEVEL_VERSION );
        
        tempCal = new GregorianCalendar();
    }

    @Override
    public void setDataSource( CPSDataModel dm ) {
        super.setDataSource( dm );
                
        buildTODOListPanel();
        rdoDateThisWeek.doClick();
        
        dataUpdated();
        
    }
    
    private void buildTODOListPanel() {
        
        jplTodo = new JPanel();
        jplTodo.setLayout( new GridBagLayout() );
        jplTodo.setBorder( BorderFactory.createTitledBorder( "Export planting/seeding lists" ));
        
        cmbPlanName = new JComboBox();
        cmbPlanName.setEditable( false );
//        cmbPlanName.addActionListener(this);
//        cmbPlanName.addPropertyChangeListener(this);
        
        
        rdoDateThisWeek = new JRadioButton( "This week", false );
        rdoDateNextWeek = new JRadioButton( "Next week", false );
        rdoDateThisNextWeek = new JRadioButton( "This week and next", false );
        rdoDateOther = new JRadioButton( "Other date range:", false );
        rdoDateThisWeek.addItemListener( this );
        rdoDateNextWeek.addItemListener( this );
        rdoDateThisNextWeek.addItemListener( this );
        rdoDateOther.addItemListener( this );
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add( rdoDateThisWeek );
        bg1.add( rdoDateNextWeek );
        bg1.add( rdoDateThisNextWeek );
        bg1.add( rdoDateOther );
        
        dtcDateOtherStart = new JDateChooser();
        dtcDateOtherEnd = new JDateChooser();
        dtcDateOtherStart.addPropertyChangeListener( this );
        dtcDateOtherEnd.addPropertyChangeListener( this );
        
        rdoUncompThisWeek = new JRadioButton( "This week only", false );
        rdoUncompLastWeek = new JRadioButton( "This week and last week", false );
        rdoUncompAll = new JRadioButton( "All uncomplete plantings", true );
        rdoUncompThisWeek.addItemListener(this);
        rdoUncompLastWeek.addItemListener( this );
        rdoUncompAll.addItemListener( this );
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add( rdoUncompThisWeek );
        bg2.add( rdoUncompLastWeek );
        bg2.add( rdoUncompAll );
        
        // display label, default filename and button to select new file
//        outputFile = new File( System.getProperty( "user.dir") );
//        fldFile = new JTextField( 20 );
//        fldFile.setText( "ExportFile.pdf" );
        filFile = new JFileChooser();
//        filFile.setCurrentDirectory(outputFile);
        filFile.setSelectedFile( new File( CPSGlobalSettings.getDocumentOutputDir() ));
        filFile.setMultiSelectionEnabled(false);
        filFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        lblDirectory = new JLabel( filFile.getSelectedFile().getPath() );
        
        btnSelectFile = new JButton( "Select Output Directory" );
        btnSelectFile.addActionListener(this);
        
        btnGHList = new JButton( "GH Seeding List" );
        btnPlantList = new JButton( "Field Planting List" );
        btnGHList.addActionListener(this);
        btnPlantList.addActionListener(this);
        
        
        LayoutAssist.addLabelLeftAlign( jplTodo, 0, 0, 4, 1, new JLabel( "Create list from crop plan:" ) );
        LayoutAssist.addComboBox(jplTodo, 1, 1, cmbPlanName );
        
        LayoutAssist.addLabelLeftAlign( jplTodo, 0, 2, 4, 1, new JLabel( "For time period:" ) );
        LayoutAssist.addButton( jplTodo, 1, 3, rdoDateThisWeek );
        LayoutAssist.addButton( jplTodo, 1, 4, rdoDateNextWeek );
        LayoutAssist.addButton( jplTodo, 1, 5, rdoDateThisNextWeek );
        LayoutAssist.addButton( jplTodo, 1, 6, rdoDateOther );
        LayoutAssist.addSubPanel( jplTodo, 2, 6, 1, 1, dtcDateOtherStart );
        LayoutAssist.addSubPanel( jplTodo, 3, 6, 1, 1, dtcDateOtherEnd );
        
        LayoutAssist.addLabelLeftAlign( jplTodo, 0, 7, new JLabel( "Include:" ) );
        LayoutAssist.addLabelLeftAlign( jplTodo, 1, 8, 2, 1, new JLabel( "Uncompleted plantings from:" ) );
        LayoutAssist.addButton( jplTodo, 2, 9,  rdoUncompThisWeek );
        LayoutAssist.addButton( jplTodo, 2, 10, rdoUncompLastWeek );
        LayoutAssist.addButton( jplTodo, 2, 11, rdoUncompAll );
                        
        LayoutAssist.addLabelLeftAlign( jplTodo, 0, 12, 4, 1, new JLabel( "Export files to directory/folder:" ) );
        LayoutAssist.addLabelLeftAlign( jplTodo, 1, 13, 3, 1, lblDirectory );
        LayoutAssist.addButton(         jplTodo, 1, 14, btnSelectFile );
        
        LayoutAssist.addLabelLeftAlign( jplTodo, 0, 15, new JLabel( "Export:" ) );
        LayoutAssist.addButton( jplTodo, 1, 16, btnGHList );
        LayoutAssist.addButton( jplTodo, 1, 17, btnPlantList );
        
    }
    
    protected void updateListOfPlans() {
       if ( ! isDataAvailable() )
          return;
       
       ArrayList<String> l = getDataSource().getListOfCropPlans();
       cmbPlanName.removeAllItems();
       for ( String s : l ) {
          // TODO think about this; possibly remove COMMON_PLANTINGS from list returned by
          // getListOfCropPlans()
          if ( s.equalsIgnoreCase("common_plantings") )
             continue;
          cmbPlanName.addItem(s);
       }
    
    }
    
    private String createOutputFileName( File dir, String prefix ) {
       return createOutputFileName( dir, prefix, new Date() );
    }
    private String createOutputFileName( File dir, String prefix, Date d ) {
        return dir.getAbsolutePath() + File.separator + 
               prefix + " - " + new SimpleDateFormat( "MMM dd yyyy" ).format( d ) + ".pdf";
    }
    
    private void exportGHPlantings( String planName ) {
        
//        String cols = "date_plant, crop_name, var_name, plants_needed, plants_to_start, flat_size, flats_needed";
//        String sortCol = "date_plant";
        ArrayList<Integer> props = new ArrayList<Integer>( Arrays.asList( 
                new Integer[] { CPSDataModelConstants.PROP_DATE_PLANT,
                                CPSDataModelConstants.PROP_CROP_NAME,
                                CPSDataModelConstants.PROP_VAR_NAME,
                                CPSDataModelConstants.PROP_PLANTS_NEEDED,
                                CPSDataModelConstants.PROP_PLANTS_START,
                                CPSDataModelConstants.PROP_FLAT_SIZE,
                                CPSDataModelConstants.PROP_FLATS_NEEDED
                                                 } ));
        // should we explicitly reference the date_plant_plan property, or just rely on the date_plant property?
        int sortProp = CPSDataModelConstants.PROP_DATE_PLANT;
                
//        String filename = "GH Seeding List - " + new SimpleDateFormat( "MMM dd yyyy" ).format( dtcDateOtherStart.getDate() ) + ".pdf";
        String filename = createOutputFileName( filFile.getSelectedFile(),
                                                "GH Seeding List",
                                                dtcDateOtherStart.getDate() );
        
        
        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(true);
        
        // filter out all direct seeded plantings
        filter.setFilterOnPlantingMethod(true);
        filter.setFilterMethodDirectSeed(false);
        
        // show only uncompleted plantings
        filter.setFilterOnPlanting(true);
        filter.setDonePlanting(false);
        
        // show only plantings in the correct date range
       filter.setFilterOnPlantingDate( true );
       filter.setPlantingRangeEnd( dtcDateOtherEnd.getDate() );
        
       if ( rdoUncompLastWeek.isSelected() ) {
          tempCal.setTime( dtcDateOtherStart.getDate() );
          tempCal.add( Calendar.WEEK_OF_YEAR, -1 );
          filter.setPlantingRangeStart( tempCal.getTime() );
       }
       else if ( rdoUncompAll.isSelected() ) {
          tempCal.setTime( dtcDateOtherStart.getDate() );
          tempCal.set( Calendar.WEEK_OF_YEAR, 0 );
          filter.setPlantingRangeStart( tempCal.getTime() );
       }
       else // if ( rdoUncompThistWeek.isSelected() )
          filter.setPlantingRangeStart( dtcDateOtherStart.getDate() ); 
        
        
        CPSTable jt = new CPSTable();
        jt.setModel( getDataSource().getCropPlan( planName, props, sortProp, filter ) );
        jt.setColumnNamesAndToolTips( getDataSource().getPlantingShortNames() );
//        jt.setColumnNamesAndToolTips( getDataSource().getPlantingPrettyNames() );
        
        exporter.export( jt, filename, 
                         CPSGlobalSettings.getFarmName(), 
                         "GH Seedings for " + new SimpleDateFormat( "MMM dd" ).format( dtcDateOtherStart.getDate() ) + " - "
                                            + new SimpleDateFormat( "MMM dd, yyyy" ).format( dtcDateOtherEnd.getDate() )
                                            + ( rdoUncompThisWeek.isSelected() ? "" : "\n(includes previously uncompleted)" ),
                          "GH Seedings" );
    
    }
    
    
    
    private void exportFieldPlantings( String planName ) {
        
//        String cols = "date_plant, crop_name, var_name, location, beds_to_plant, rows_p_bed, rowft_to_plant";
//        cols += ", planter, planter_setting";
        ArrayList<Integer> props = new ArrayList<Integer>( Arrays.asList( 
                new Integer[] { CPSDataModelConstants.PROP_DATE_PLANT,
                                CPSDataModelConstants.PROP_CROP_NAME,
                                CPSDataModelConstants.PROP_VAR_NAME,
                                CPSDataModelConstants.PROP_LOCATION,
                                CPSDataModelConstants.PROP_BEDS_PLANT,
                                CPSDataModelConstants.PROP_ROWS_P_BED,
                                CPSDataModelConstants.PROP_ROWFT_PLANT,
                                CPSDataModelConstants.PROP_PLANT_NOTES,
//                                CPSDataModelConstants.PROP_PLANTER_SETTING
                                                  } ) );
//        String sortCol = "date_plant";
        int sortProp = CPSDataModelConstants.PROP_DATE_PLANT;
        
//        String filename = "Field Planting List.pdf";
        String filename = createOutputFileName( filFile.getSelectedFile(), 
                                                "Field Planting List",
                                                dtcDateOtherStart.getDate() );
        
        /*
         * Select just uncompleted, direct seeded plantings whose PLANTING date
         * is w/i range.
         */
        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(true);
        
        // filter out all NON direct seeded plantings
        filter.setFilterOnPlantingMethod(true);
        filter.setFilterMethodDirectSeed(true);
        
        // show only uncompleted plantings
        filter.setFilterOnPlanting(true);
        filter.setDonePlanting(false);
        
        // show only plantings in the correct date range
        filter.setFilterOnPlantingDate( true );
        filter.setPlantingRangeEnd( dtcDateOtherEnd.getDate() );
        
        if ( rdoUncompLastWeek.isSelected() ) {
           tempCal.setTime( dtcDateOtherStart.getDate() );
           tempCal.add( Calendar.WEEK_OF_YEAR, -1 );
           filter.setPlantingRangeStart( tempCal.getTime() );
        }
        else if ( rdoUncompAll.isSelected() ) {
           tempCal.setTime( dtcDateOtherStart.getDate() );
           tempCal.set( Calendar.WEEK_OF_YEAR, 0 );
           filter.setPlantingRangeStart( tempCal.getTime() );
        }
        else // if ( rdoUncompThistWeek.isSelected() )
           filter.setPlantingRangeStart( dtcDateOtherStart.getDate() );
        
        
        CPSTable jt = new CPSTable();
        jt.setModel( getDataSource().getCropPlan( planName, props, sortProp, filter ) );
        jt.setColumnNamesAndToolTips( getDataSource().getPlantingShortNames() );
//        jt.setColumnNamesAndToolTips( getDataSource().getPlantingPrettyNames() );
        
        exporter.startExport( jt, filename, 
                              CPSGlobalSettings.getFarmName(), 
                              "Field plantings for " + new SimpleDateFormat( "MMM dd" ).format( dtcDateOtherStart.getDate() ) + " - "
                                                     + new SimpleDateFormat( "MMM dd, yyyy" ).format( dtcDateOtherEnd.getDate() )
                                                     + ( rdoUncompThisWeek.isSelected() ? "" : "\n(includes previously uncompleted)" ),
                              "Direct Seeded Field Plantings" );
        
        
        /*
         * Select just uncompleted, transplanted plantings whose TRANSPLANT date
         * is w/i range
         */
        
//        cols = "date_tp, date_plant, crop_name, var_name, location, beds_to_plant, rows_p_bed, rowft_to_plant, inrow_space";
//        sortCol = "date_tp";
        
        props = new ArrayList<Integer>( Arrays.asList( 
                new Integer[] { CPSDataModelConstants.PROP_DATE_TP,
                                CPSDataModelConstants.PROP_DATE_PLANT,
                                CPSDataModelConstants.PROP_CROP_NAME,
                                CPSDataModelConstants.PROP_VAR_NAME,
                                CPSDataModelConstants.PROP_LOCATION,
                                CPSDataModelConstants.PROP_BEDS_PLANT,
                                CPSDataModelConstants.PROP_ROWFT_PLANT,
                                CPSDataModelConstants.PROP_ROWS_P_BED,
                                CPSDataModelConstants.PROP_SPACE_INROW,
                                CPSDataModelConstants.PROP_PLANT_NOTES
                               } ) );
         sortProp = CPSDataModelConstants.PROP_DATE_TP;
        
        // filter out all direct seeded plantings
        filter.setFilterOnPlantingMethod(true);
        filter.setFilterMethodDirectSeed(false);
        
        // show only uncompleted transplantings that have been seeded
        filter.setFilterOnPlanting(true);
        filter.setDonePlanting(true);
        filter.setFilterOnTransplanting(true);
        filter.setDoneTransplanting(false);
        
        // disable planting date filter, enable transplanting date filter
        filter.setFilterOnPlantingDate( false );
        filter.setFilterOnTPDate( true );
        filter.setTpRangeEnd( dtcDateOtherEnd.getDate() );
        
        if ( rdoUncompLastWeek.isSelected() ) {
           tempCal.setTime( dtcDateOtherStart.getDate() );
           tempCal.add( Calendar.WEEK_OF_YEAR, -1 );
           filter.setTpRangeStart( tempCal.getTime() );
        }
        else if ( rdoUncompAll.isSelected() ) {
           tempCal.setTime( dtcDateOtherStart.getDate() );
           tempCal.set( Calendar.WEEK_OF_YEAR, 0 );
           filter.setTpRangeStart( tempCal.getTime() );
        }
        else // if ( rdoUncompThistWeek.isSelected() )
           filter.setTpRangeStart( dtcDateOtherStart.getDate() );
        
        
        jt.setModel( getDataSource().getCropPlan( planName, props, sortProp, filter ) );
        jt.setColumnNamesAndToolTips( getDataSource().getPlantingShortNames() );
//        jt.setColumnNamesAndToolTips( getDataSource().getPlantingPrettyNames() );
        
        exporter.addPage( jt, "Transplanted Field Plantings" );
        exporter.endExport();
    
    }
   
    
    @Override
    public void dataUpdated() {
        updateListOfPlans();
    }
    
    public JPanel display() { return jplTodo; }
    public Dimension getSize() { return jplTodo.getSize(); }

    
    
    public void actionPerformed( ActionEvent arg0 ) {
        String action = arg0.getActionCommand();
        
        String planName = (String) cmbPlanName.getSelectedItem();
        if ( planName == null ) {
            System.err.println("ERROR: must select a crop plan");
            return;
        }
        
        if ( action.equalsIgnoreCase( btnPlantList.getText() ))
            exportFieldPlantings( planName );
        else if ( action.equalsIgnoreCase( btnGHList.getText() ))
            exportGHPlantings( planName );
        else if ( action.equalsIgnoreCase( btnSelectFile.getText() )) {
            int status = filFile.showDialog( jplTodo, "Accept" ); 
            if ( status == JFileChooser.APPROVE_OPTION ) {
                lblDirectory.setText( filFile.getSelectedFile().getPath() );   
            }
        }
            
    }
    
    public void itemStateChanged( ItemEvent arg0 ) {

        Object source = arg0.getSource();
        GregorianCalendar temp = new GregorianCalendar();
        GregorianCalendar now = new GregorianCalendar();
        now.setTime( new Date() );
        temp.setTime( now.getTime() );
        
        
        if ( source == rdoDateThisWeek || source == rdoDateNextWeek || source == rdoDateThisNextWeek ) {
            
            if ( source == rdoDateNextWeek )
                temp.add( Calendar.WEEK_OF_YEAR, 1 );
            
            temp.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY );
            dtcDateOtherStart.setDate( temp.getTime() );
            
            if ( source == rdoDateThisNextWeek )
                temp.add( Calendar.WEEK_OF_YEAR, 1 );
            
            temp.set( Calendar.DAY_OF_WEEK, Calendar.SATURDAY );
            dtcDateOtherEnd.setDate( temp.getTime() );
            
            dtcDateOtherEnd.setEnabled(false);
            dtcDateOtherStart.setEnabled(false);
            
        }
        else if ( source == rdoDateOther ) {
            dtcDateOtherEnd.setEnabled(true);
            dtcDateOtherStart.setEnabled(true);
        }
           
    }

    public void propertyChange( PropertyChangeEvent arg0 ) {
        Object source = arg0.getSource();
        GregorianCalendar temp = new GregorianCalendar();
        
        if      ( source == dtcDateOtherStart ) {
            if ( dtcDateOtherEnd.getDate() == null ||
                 dtcDateOtherEnd.getDate().getTime() <= dtcDateOtherStart.getDate().getTime() ) {
                temp.setTime( dtcDateOtherStart.getDate() );
                temp.add( Calendar.DAY_OF_YEAR, 1 );
                dtcDateOtherEnd.setDate( temp.getTime() );
            }
//            dtcDateOtherEnd.setMinSelectableDate( dtcDateOtherStart.getDate() );
        }
        else if ( source == dtcDateOtherEnd ) {
            if ( dtcDateOtherStart.getDate() == null ||
                 dtcDateOtherEnd.getDate().getTime() <= dtcDateOtherStart.getDate().getTime() ) {
                temp.setTime( dtcDateOtherEnd.getDate() );
                temp.add( Calendar.DAY_OF_YEAR, -1 );
                dtcDateOtherStart.setDate( temp.getTime() );
            }
//            dtcDateOtherStart.setMaxSelectableDate( dtcDateOtherEnd.getDate() );
        }
    }
    
    @Override
    public int init() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    protected int saveState() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public int shutdown() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
    

}
