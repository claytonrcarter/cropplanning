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

import CPS.CSV.CSV;
import CPS.Data.CPSComplexPlantingFilter;
import CPS.Data.CPSPlanting;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSDisplayableDataUserModule;
import CPS.Module.CPSGlobalSettings;
import CPS.UI.Modules.CPSAdvancedTableFormat;
import CPS.UI.Modules.CPSComparator;
import CPS.UI.Swing.CPSComplexFilterDialog;
import CPS.UI.Swing.CPSTable;
import CPS.UI.Swing.LayoutAssist;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.FunctionList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.calculation.Calculation;
import ca.odell.glazedlists.calculation.Calculations;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
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
import java.util.*;
import javax.swing.*;

public class TODOLists extends CPSDisplayableDataUserModule implements ActionListener, ItemListener, PropertyChangeListener {

    private JPanel jplTodo;
    private JComboBox cmbPlanName, cmbWhatToExport;
    private JRadioButton rdoDateThisWeek,  rdoDateNextWeek,  rdoDateThisNextWeek,  rdoDateOther;
    private JDateChooser dtcDateOtherStart,  dtcDateOtherEnd;
    private JRadioButton rdoUncompThisWeek,  rdoUncompLastWeek,  rdoUncompAll;
//    private File outputFile;
    private JLabel lblDirectory;
//    private JTextField fldFile;
    private JFileChooser filFile;
    private JButton btnSelectFile;
    private JButton btnFormatPDF, btnFormatCSV;
    private GregorianCalendar tempCal;

    private BasicEventList<CPSPlanting> data;
    private FilterList<CPSPlanting> dataFiltered;
    private SortedList<CPSPlanting> dataSorted;
    CPSAdvancedTableFormat<CPSPlanting> tableFormat;


    private final String TL_GH_SEEDING = "GH Seeding List (PDF only)";
    private final String TL_FIELD_PLANTING = "Field Planting List (PDF only)";
    private final String TL_ALL_PLANTINGS = "List of All Plantings";
    private final String TL_SEED_ORDER_WORKSHEET = "Seed Order Worksheet";

    private final int TL_FORMAT_PDF = 1;
    private final int TL_FORMAT_CSV = 2;


    CPSComplexFilterDialog cfd = new CPSComplexFilterDialog();
    PDFExporter exporter = new PDFExporter();

    public TODOLists() {
        setModuleName("TODOLists");
        setModuleType("Core");
        setModuleVersion( CPSGlobalSettings.getVersion() );

        tempCal = new GregorianCalendar();

        data = new BasicEventList<CPSPlanting>();
        dataFiltered = new FilterList<CPSPlanting>( data );
        dataSorted = new SortedList<CPSPlanting>( dataFiltered, null );
        
    }

    @Override
    public void setDataSource(CPSDataModel dm) {
        super.setDataSource(dm);

        buildTODOListPanel();
        rdoDateThisWeek.doClick();

        dataUpdated();

    }

    // <editor-fold defaultstate="collapsed" desc="buildTODOListPanel">
    private void buildTODOListPanel() {

        jplTodo = new JPanel();
        jplTodo.setLayout(new GridBagLayout());
//        jplTodo.setBorder( BorderFactory.createTitledBorder( "Export planting/seeding lists" ));
        jplTodo.setBorder(BorderFactory.createEmptyBorder());

        cmbPlanName = new JComboBox();
        cmbPlanName.setEditable(false);
//        cmbPlanName.addActionListener(this);
//        cmbPlanName.addPropertyChangeListener(this);


        rdoDateThisWeek = new JRadioButton("This week", false);
        rdoDateNextWeek = new JRadioButton("Next week", false);
        rdoDateThisNextWeek = new JRadioButton("This week and next", false);
        rdoDateOther = new JRadioButton("Other date range:", false);
        rdoDateThisWeek.addItemListener(this);
        rdoDateNextWeek.addItemListener(this);
        rdoDateThisNextWeek.addItemListener(this);
        rdoDateOther.addItemListener(this);
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(rdoDateThisWeek);
        bg1.add(rdoDateNextWeek);
        bg1.add(rdoDateThisNextWeek);
        bg1.add(rdoDateOther);

        dtcDateOtherStart = new JDateChooser();
        dtcDateOtherEnd = new JDateChooser();
        dtcDateOtherStart.addPropertyChangeListener(this);
        dtcDateOtherEnd.addPropertyChangeListener(this);

        rdoUncompThisWeek = new JRadioButton("This week only", false);
        rdoUncompLastWeek = new JRadioButton("This week and last week", false);
        rdoUncompAll = new JRadioButton("All uncomplete plantings", true);
        rdoUncompThisWeek.addItemListener(this);
        rdoUncompLastWeek.addItemListener(this);
        rdoUncompAll.addItemListener(this);
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(rdoUncompThisWeek);
        bg2.add(rdoUncompLastWeek);
        bg2.add(rdoUncompAll);

        // display label, default filename and button to select new file
//        outputFile = new File( System.getProperty( "user.dir") );
//        fldFile = new JTextField( 20 );
//        fldFile.setText( "ExportFile.pdf" );
        filFile = new JFileChooser();
//        filFile.setCurrentDirectory(outputFile);
        filFile.setSelectedFile(new File(CPSGlobalSettings.getDocumentOutputDir()));
        filFile.setMultiSelectionEnabled(false);
        filFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if ( filFile == null ) {
            lblDirectory = new JLabel("<html><em>Hmm, problem displaying directory name. (Error 701)</em></html>");
            debug( "Encountered null JFileChooser, ERROR 701" );
        }
        else if ( filFile.getSelectedFile() == null ) {
            lblDirectory = new JLabel("<html><em>Hmm, problem displaying directory name. (Error 702)</em></html>");
            debug( "JFileChooser returned a null selected file, ERROR 702" );
        }
        else if ( filFile.getSelectedFile().getPath() == null ) {
            lblDirectory = new JLabel("<html><i>Hmm, problem displaying directory name. (Error 703)</i></html>");
            debug( "The path returned from the selected file was null, ERROR 703" );
        }
        else
            lblDirectory = new JLabel( filFile.getSelectedFile().getPath() );

        btnSelectFile = new JButton("Select Output Directory");
        btnSelectFile.addActionListener(this);


        cmbWhatToExport = new JComboBox();
        cmbWhatToExport.setEditable(false);
        cmbWhatToExport.addItem( TL_GH_SEEDING );
        cmbWhatToExport.addItem( TL_FIELD_PLANTING );
        cmbWhatToExport.addItem( TL_ALL_PLANTINGS );
        cmbWhatToExport.addItem( TL_SEED_ORDER_WORKSHEET );

        btnFormatPDF = new JButton( "Export as PDF" );
        btnFormatCSV = new JButton( "Export as CSV" );
        btnFormatPDF.addActionListener(this);
        btnFormatCSV.addActionListener(this);


        LayoutAssist.addLabelLeftAlign(jplTodo, 0, 0, 4, 1, new JLabel("Create list from crop plan:"));
        LayoutAssist.addComboBox(jplTodo, 1, 1, cmbPlanName);

        LayoutAssist.addLabelLeftAlign(jplTodo, 0, 2, 4, 1, new JLabel("For time period:"));
        LayoutAssist.addButton(jplTodo, 1, 3, rdoDateThisWeek);
        LayoutAssist.addButton(jplTodo, 1, 4, rdoDateNextWeek);
        LayoutAssist.addButton(jplTodo, 1, 5, rdoDateThisNextWeek);
        LayoutAssist.addButton(jplTodo, 1, 6, rdoDateOther);
        LayoutAssist.addSubPanel(jplTodo, 2, 6, 1, 1, dtcDateOtherStart);
        LayoutAssist.addSubPanel(jplTodo, 3, 6, 1, 1, dtcDateOtherEnd);

        LayoutAssist.addLabelLeftAlign(jplTodo, 0, 7, new JLabel("Include:"));
        LayoutAssist.addLabelLeftAlign(jplTodo, 1, 8, 2, 1, new JLabel("Uncompleted plantings from:"));
        LayoutAssist.addButton(jplTodo, 2, 9, rdoUncompThisWeek);
        LayoutAssist.addButton(jplTodo, 2, 10, rdoUncompLastWeek);
        LayoutAssist.addButton(jplTodo, 2, 11, rdoUncompAll);

        LayoutAssist.addLabelLeftAlign(jplTodo, 0, 12, 4, 1, new JLabel("Export files to directory/folder:"));
        LayoutAssist.addLabelLeftAlign(jplTodo, 1, 13, 3, 1, lblDirectory);
        LayoutAssist.addButton(jplTodo, 1, 14, btnSelectFile);

        LayoutAssist.addLabelLeftAlign(jplTodo, 0, 15, new JLabel("Export:"));
        LayoutAssist.addComboBox(jplTodo, 1, 16, cmbWhatToExport );
        LayoutAssist.addButton(jplTodo, 1, 17, btnFormatPDF);
//        LayoutAssist.addLabelLeftAlign( jplTodo, 2, 17, new JLabel("(for printing)"));
        LayoutAssist.addButton(jplTodo, 1, 18, btnFormatCSV);
//        LayoutAssist.addLabelLeftAlign( jplTodo, 2, 18, new JLabel("(for loading into a spreadsheet)"));

    }
    // </editor-fold>

    protected void updateListOfPlans() {
        if (!isDataAvailable()) {
            return;
        }

        List<String> l = getDataSource().getListOfCropPlans();
        cmbPlanName.removeAllItems();
        for (String s : l) {
            // TODO think about this; possibly remove COMMON_PLANTINGS from list returned by
            // getListOfCropPlans()
            if (s.equalsIgnoreCase("common_plantings")) {
                continue;
            }
            cmbPlanName.addItem(s);
        }

        // now select/default to the currently selected crop plan
        if ( getMediator() != null )
            cmbPlanName.setSelectedItem( getMediator().getCropPlan() );

    }

    private String createOutputFileName(File dir, String prefix) {
        return createOutputFileName(dir, prefix, new Date());
    }

    private String createOutputFileName(File dir, String prefix, Date d) {
      return createOutputFileName(dir, prefix, d, "pdf" );
    }

    private String createOutputFileName(File dir, String prefix, Date d, int format ) {
      String ext = format == TL_FORMAT_PDF ? "pdf" : "csv";
      return createOutputFileName(dir, prefix, d, ext );
    }
    
    private String createOutputFileName( File dir, String prefix, Date d, String ext ) {
        return dir.getAbsolutePath() + File.separator +
                prefix + " - " + new SimpleDateFormat("MMM dd yyyy").format(d) + "." + ext;
    }

    private void filterAndSortList( List<CPSPlanting> l, CPSComplexPlantingFilter f, int sortProp ) {

      if ( l != null )
        data.clear();

      dataFiltered.setMatcher( f );
      dataSorted.setComparator( new CPSComparator( sortProp ));

      if ( l != null )
        data.addAll( l );

      System.out.println("Entries in full crop plan: " + data.size() );
      System.out.println("Entries in filtered crop plan: " + dataFiltered.size() );

    }


    private void exportGHPlantings(String planName, int format ) {

      if ( format != TL_FORMAT_PDF )
        throw new UnsupportedOperationException("Not supported yet.");

        // should we explicitly reference the date_plant_plan property, or just rely on the date_plant property?
        int sortProp = CPSDataModelConstants.PROP_DATE_PLANT;

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
        filter.setFilterOnPlantingDate(true);
        filter.setPlantingRangeEnd(dtcDateOtherEnd.getDate());

        if (rdoUncompLastWeek.isSelected()) {
            tempCal.setTime(dtcDateOtherStart.getDate());
            tempCal.add(Calendar.WEEK_OF_YEAR, -1);
            filter.setPlantingRangeStart(tempCal.getTime());
        } else if (rdoUncompAll.isSelected()) {
            tempCal.setTime(dtcDateOtherStart.getDate());
            tempCal.set(Calendar.WEEK_OF_YEAR, 0);
            filter.setPlantingRangeStart(tempCal.getTime());
        } else // if ( rdoUncompThistWeek.isSelected() )
        {
            filter.setPlantingRangeStart(dtcDateOtherStart.getDate());
        }


        filterAndSortList( getDataSource().getCropPlan( planName ), filter, sortProp );
        tableFormat = new GHSeedingTableFormat();

        CPSTable jt = new CPSTable();
        jt.setModel( new EventTableModel<CPSPlanting>( dataSorted, tableFormat ) );

        TableComparatorChooser.install( jt,
                                        dataSorted,
                                        TableComparatorChooser.SINGLE_COLUMN ).appendComparator( tableFormat.getDefaultSortColumn(), 0, false );


        exporter.export(jt, filename,
                CPSGlobalSettings.getFarmName(),
                "GH Seedings for " + new SimpleDateFormat("MMM dd").format(dtcDateOtherStart.getDate()) + " - " + new SimpleDateFormat("MMM dd, yyyy").format(dtcDateOtherEnd.getDate()) + (rdoUncompThisWeek.isSelected() ? "" : "\n(includes previously uncompleted)"),
                "GH Seedings");

    }

    private void exportFieldPlantings(String planName, int format ) {

      if ( format != TL_FORMAT_PDF )
        throw new UnsupportedOperationException("Not supported yet.");

        int sortProp = CPSDataModelConstants.PROP_DATE_PLANT;

        String filename = createOutputFileName(filFile.getSelectedFile(),
                "Field Planting List",
                dtcDateOtherStart.getDate());

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
        filter.setFilterOnPlantingDate(true);
        filter.setPlantingRangeEnd(dtcDateOtherEnd.getDate());

        if (rdoUncompLastWeek.isSelected()) {
            tempCal.setTime(dtcDateOtherStart.getDate());
            tempCal.add(Calendar.WEEK_OF_YEAR, -1);
            filter.setPlantingRangeStart(tempCal.getTime());
        } else if (rdoUncompAll.isSelected()) {
            tempCal.setTime(dtcDateOtherStart.getDate());
            tempCal.set(Calendar.WEEK_OF_YEAR, 0);
            filter.setPlantingRangeStart(tempCal.getTime());
        } else // if ( rdoUncompThistWeek.isSelected() )
        {
            filter.setPlantingRangeStart(dtcDateOtherStart.getDate());
        }

        filterAndSortList( getDataSource().getCropPlan( planName ), filter, sortProp );
        tableFormat = new DSFieldPlantingTableFormat();

        CPSTable jt = new CPSTable();
        jt.setModel( new EventTableModel<CPSPlanting>( dataSorted, tableFormat ));

        TableComparatorChooser.install( jt,
                                        dataSorted,
                                        TableComparatorChooser.SINGLE_COLUMN ).appendComparator( tableFormat.getDefaultSortColumn(), 0, false );

        exporter.startExport(jt, filename,
                CPSGlobalSettings.getFarmName(),
                "Field plantings for " + new SimpleDateFormat("MMM dd").format(dtcDateOtherStart.getDate()) + " - " + new SimpleDateFormat("MMM dd, yyyy").format(dtcDateOtherEnd.getDate()) + (rdoUncompThisWeek.isSelected() ? "" : "\n(includes previously uncompleted)"),
                "Direct Seeded Field Plantings");


        /*
         * Select just uncompleted, transplanted plantings whose TRANSPLANT date
         * is w/i range
         */
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
        filter.setFilterOnPlantingDate(false);
        filter.setFilterOnTPDate(true);
        filter.setTpRangeEnd(dtcDateOtherEnd.getDate());

        if (rdoUncompLastWeek.isSelected()) {
            tempCal.setTime(dtcDateOtherStart.getDate());
            tempCal.add(Calendar.WEEK_OF_YEAR, -1);
            filter.setTpRangeStart(tempCal.getTime());
        } else if (rdoUncompAll.isSelected()) {
            tempCal.setTime(dtcDateOtherStart.getDate());
            tempCal.set(Calendar.WEEK_OF_YEAR, 0);
            filter.setTpRangeStart(tempCal.getTime());
        } else // if ( rdoUncompThistWeek.isSelected() )
        {
            filter.setTpRangeStart(dtcDateOtherStart.getDate());
        }

        filterAndSortList( null, filter, sortProp );
        tableFormat = new TPFieldPlantingTableFormat();

        jt.setModel( new EventTableModel<CPSPlanting>( dataSorted, tableFormat ));
        TableComparatorChooser.install( jt,
                                        dataSorted,
                                        TableComparatorChooser.SINGLE_COLUMN ).appendComparator( tableFormat.getDefaultSortColumn(), 0, false );



        exporter.addPage( jt, "Transplanted Field Plantings");
        exporter.endExport();

    }

    private void exportAllPlantings(String planName, int format ) {

        String filename = createOutputFileName( filFile.getSelectedFile(),
                                                "All Plantings",
                                                dtcDateOtherStart.getDate(),
                                                format );


        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(false);

        CPSTable jt = new CPSTable();

        data.clear();
        data.addAll( getDataSource().getCropPlan( planName ));
        jt.setModel( new EventTableModel<CPSPlanting>( data, new AllPlantingsTableFormat() ));

        if ( format == TL_FORMAT_CSV ) {
          new CSV().exportJTable( filename, "All Plantings for plan \"" + planName + "\"", jt );
        } else {
          exporter.export( jt, filename,
                          CPSGlobalSettings.getFarmName(),
                          "All Plantings for plan \"" + planName + "\"",
                          "All Plantings" );
        }

    }

    private void exportSeedOrderLists( String planName, int format ) {

        String filename = createOutputFileName( filFile.getSelectedFile(),
                                                "Seed Order Info",
                                                dtcDateOtherStart.getDate(),
                                                format );


        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(true);

        // fill up the list w/ the crop plan, then hide anything being ignored
        data.clear();
        data.addAll( getDataSource().getCropPlan( planName ) );


        // setup the list of filters and add an "all" matcher
        BasicEventList<MatcherEditor<CPSPlanting>> filterList = new BasicEventList<MatcherEditor<CPSPlanting>>();
        filterList.add( filter );

        // now setup the thing that will match all of the elements of the filter list
        CompositeMatcherEditor<CPSPlanting> compositeFilter = new CompositeMatcherEditor<CPSPlanting>( filterList );
        compositeFilter.setMode( CompositeMatcherEditor.AND );


        dataFiltered.setMatcherEditor(compositeFilter);


        // sort that plan by crop and var name, excluding anything ignored
        SortedList<CPSPlanting> sortedPlan = new SortedList<CPSPlanting>( dataFiltered, new CropVarComparator() );


        // create another filtered list which we'll use to do our processing
        // base it on the "not ignored" list so we know we can start w/ that.
        FilterList<CPSPlanting> filteredPlan = new FilterList<CPSPlanting>( dataFiltered );

        // set our calculations
        FunctionList<CPSPlanting, Integer> rftList =
              new FunctionList<CPSPlanting, Integer>( filteredPlan,
                                                 new FunctionList.Function<CPSPlanting, Integer>() {
                                                    public Integer evaluate( CPSPlanting p ) {
                                                       return p.getRowFtToPlant();
                                                    }} );

        FunctionList<CPSPlanting, Integer> plantsList =
              new FunctionList<CPSPlanting, Integer>( filteredPlan,
                                                 new FunctionList.Function<CPSPlanting, Integer>() {
                                                    public Integer evaluate( CPSPlanting p ) {
                                                       return p.getPlantsToStart();
                                                    }} );

        FunctionList<CPSPlanting, Float> bedsList =
              new FunctionList<CPSPlanting, Float>( filteredPlan,
                                                  new FunctionList.Function<CPSPlanting, Float>() {
                                                     public Float evaluate( CPSPlanting p ) {
                                                        return p.getBedsToPlant();
                                                     }} );

        FunctionList<CPSPlanting, Float> flatsList =
              new FunctionList<CPSPlanting, Float>( filteredPlan,
                                                  new FunctionList.Function<CPSPlanting, Float>() {
                                                     public Float evaluate( CPSPlanting p ) {
                                                        return p.getFlatsNeeded();
                                                     }} );


        Calculation<Integer> summaryPlantings = Calculations.count( filteredPlan );
        Calculation<Integer> summaryRowFt = Calculations.sumIntegers( rftList );
        Calculation<Integer> summaryPlants = Calculations.sumIntegers( plantsList );
        Calculation<Float> summaryBeds = Calculations.sumFloats( bedsList );
        Calculation<Float> summaryFlats = Calculations.sumFloats( flatsList );


        BasicEventList<CPSPlanting> seedStats = new BasicEventList<CPSPlanting>();

        while ( sortedPlan.size() > 0 ) {
          // get the first entry on the sorted list
          CPSPlanting p = sortedPlan.get(0);

          // filter plan on the crop/var name for that first entry
          CropVarMatcher cvm = new CropVarMatcher(p);
          filteredPlan.setMatcher( cvm );


          // create new planting based on the summary calculations for that filtered list
          CPSPlanting s = new CPSPlanting();
          s.setCropName( p.getCropName() );
          s.setVarietyName( p.getVarietyName() );
          s.setDirectSeeded( p.isDirectSeeded() );

          s.setInRowSpacing( p.getInRowSpacing() );
          s.setFlatSize( p.getFlatSize() );

          s.setRowFtToPlant( summaryRowFt.getValue() );
          s.setPlantsToStart( summaryPlants.getValue() );
          s.setBedsToPlant( summaryBeds.getValue() + .001f );
          s.setFlatsNeeded( summaryFlats.getValue() );
          // this is a dirty dirty hack that is only used because we're
          // controlling the table format for the output
          s.setMaturityDays( summaryPlantings.getValue() );

          // add that new planting to a separate list
          seedStats.add(s);


          // now add this to the list of shit to exclude from our list
          filterList.add( cvm.invert() );

        }

        TableFormat tf = new SeedStatsTableFormat();

        CPSTable jt = new CPSTable();
        jt.setModel( new EventTableModel<CPSPlanting>( seedStats, tf ) );

        if ( format == TL_FORMAT_CSV ) {
          new CSV().exportJTable( filename, "Seed Order Worksheet for plan \"" + planName + "\"", jt );
        } else {
          exporter.export( jt, filename,
                           CPSGlobalSettings.getFarmName(),
                           "Seed Order Worksheet for plan \"" + planName + "\"",
                           null );
        }

    }




    @Override
    public void dataUpdated() {
        updateListOfPlans();
    }

    public JPanel display() {
        return jplTodo;
    }

    public Dimension getSize() {
        return jplTodo.getSize();
    }

    public void actionPerformed(ActionEvent arg0) {
        String action = arg0.getActionCommand();

        String planName = (String) cmbPlanName.getSelectedItem();
        if ( planName == null ) {
            debug( "ERROR: must select a crop plan" );
            return;
        }

        if ( action.equalsIgnoreCase(btnFormatPDF.getText()) ||
             action.equalsIgnoreCase(btnFormatCSV.getText()) ) {

          int format;
          if ( action.equalsIgnoreCase(btnFormatPDF.getText()) )
            format = TL_FORMAT_PDF;
          else
            format = TL_FORMAT_CSV;

          String whatToExport = (String) cmbWhatToExport.getSelectedItem();

          debug( "Exporting \"" + whatToExport + "\" from plan \"" + planName + "\"" );

          if ( ! isDataAvailable() ) {
            debug( "No data available, nothing to export.");
            return;
          }

          if ( whatToExport.equals( TL_GH_SEEDING ) ) {
            exportGHPlantings( planName, format );
          } else if ( whatToExport.equals( TL_FIELD_PLANTING ) ) {
            exportFieldPlantings( planName, format );
          } else if ( whatToExport.equals( TL_ALL_PLANTINGS ) ) {
            exportAllPlantings( planName, format );
          } else if ( whatToExport.equals( TL_SEED_ORDER_WORKSHEET ) ) {
            exportSeedOrderLists( planName, format );
          }

        } else if (action.equalsIgnoreCase(btnSelectFile.getText())) {
            int status = filFile.showDialog(jplTodo, "Accept");
            if (status == JFileChooser.APPROVE_OPTION) {
                lblDirectory.setText(filFile.getSelectedFile().getPath());
            }
        }

    }

    public void itemStateChanged(ItemEvent arg0) {

        Object source = arg0.getSource();
        GregorianCalendar temp = new GregorianCalendar();
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(new Date());
        temp.setTime(now.getTime());


        if (source == rdoDateThisWeek || source == rdoDateNextWeek || source == rdoDateThisNextWeek) {

            if (source == rdoDateNextWeek) {
                temp.add(Calendar.WEEK_OF_YEAR, 1);
            }

            temp.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            dtcDateOtherStart.setDate(temp.getTime());

            if (source == rdoDateThisNextWeek) {
                temp.add(Calendar.WEEK_OF_YEAR, 1);
            }

            temp.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            dtcDateOtherEnd.setDate(temp.getTime());

            dtcDateOtherEnd.setEnabled(false);
            dtcDateOtherStart.setEnabled(false);

        } else if (source == rdoDateOther) {
            dtcDateOtherEnd.setEnabled(true);
            dtcDateOtherStart.setEnabled(true);
        }

    }

    public void propertyChange(PropertyChangeEvent arg0) {
        Object source = arg0.getSource();
        GregorianCalendar temp = new GregorianCalendar();

        if ( source == null )
          return;
        if ( source == dtcDateOtherStart ) {
            if (dtcDateOtherEnd.getDate() == null ||
                    dtcDateOtherEnd.getDate().getTime() <= dtcDateOtherStart.getDate().getTime()) {
                temp.setTime(dtcDateOtherStart.getDate());
                temp.add(Calendar.DAY_OF_YEAR, 1);
                dtcDateOtherEnd.setDate(temp.getTime());
            }
//            dtcDateOtherEnd.setMinSelectableDate( dtcDateOtherStart.getDate() );
        } else if ( source == dtcDateOtherEnd ) {
            if (dtcDateOtherStart.getDate() == null ||
                    dtcDateOtherEnd.getDate().getTime() <= dtcDateOtherStart.getDate().getTime()) {
                temp.setTime(dtcDateOtherEnd.getDate());
                temp.add(Calendar.DAY_OF_YEAR, -1);
                dtcDateOtherStart.setDate(temp.getTime());
            }
//            dtcDateOtherStart.setMaxSelectableDate( dtcDateOtherEnd.getDate() );
        }
    }

    @Override
    public int init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected int saveState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int shutdown() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    private class CropVarComparator implements Comparator<CPSPlanting> {

      public int compare( CPSPlanting t, CPSPlanting t1 ) {
        int i = t.getCropName().compareTo(t1.getCropName());
        if ( i == 0 )
          return t.getVarietyName().compareTo(t1.getVarietyName());
        else
          return i;
      }

    }

    private class CropVarMatcher extends AbstractMatcherEditor<CPSPlanting> implements Matcher<CPSPlanting> {

      private CPSPlanting p;
      private boolean invert = false;

      public CropVarMatcher( CPSPlanting p ) {
        this.p = p;
      }

      public boolean matches(CPSPlanting item) {
        boolean b = p.getCropName().equals(item.getCropName()) && p.getVarietyName().equals(item.getVarietyName());
        if ( invert )
          return !b;
        else
          return b;
      }

      @Override
      public Matcher<CPSPlanting> getMatcher() {
        return this;
      }

      public MatcherEditor<CPSPlanting> invert() {
        invert = ! invert;
        return this;
      }




    }

       /* for testing only */
   public static void main( String[] args ) {
      // "test" construtor
      TODOLists tl = new TODOLists();
      tl.buildTODOListPanel();
      tl.rdoDateThisWeek.doClick();

     JFrame frame = new JFrame();
     frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
     frame.setContentPane( tl.display() );
     frame.setTitle( "TODOList Layout" );
     frame.pack();
     frame.setVisible(true);
   }


}
