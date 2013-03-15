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
import CPS.Data.CPSDateValidator;
import CPS.Data.CPSPlanting;
import CPS.Data.CPSPlantingComparator;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSDisplayableDataUserModule;
import CPS.Module.CPSGlobalSettings;
import CPS.UI.Modules.CPSAdvancedTableFormat;
import CPS.UI.Swing.CPSComplexFilterDialog;
import CPS.UI.Swing.CPSTable;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.FunctionList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.calculation.Calculation;
import ca.odell.glazedlists.calculation.Calculations;
import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.Cursor;
import java.awt.Dimension;
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
import net.miginfocom.swing.MigLayout;

public class TODOLists extends CPSDisplayableDataUserModule
                       implements ActionListener,
                                  ItemListener,
                                  PropertyChangeListener {

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
    private JLabel lblExportDesc;
    private GregorianCalendar tempCal;

    private BasicEventList<CPSPlanting> data;
    private FilterList<CPSPlanting> dataFiltered;
    private SortedList<CPSPlanting> dataSorted;
    CPSAdvancedTableFormat<CPSPlanting> tableFormat;


    private final String TL_GH_SEEDING = "GH Seeding List (PDF only)";
    private final String TL_FIELD_PLANTING = "Field Planting List (PDF only)";
    private final String TL_ALL_PLANTING_LISTS = "All Weekly Planting Lists (PDF only)";
    private final String TL_ALL_PLANTINGS = "Complete Crop Plan";
    private final String TL_SEED_ORDER_WORKSHEET = "Seed Order Worksheet";
    private final String TL_HARVEST_AVAILABILITY = "Harvest Availabilities";

    protected static final int TL_FORMAT_PDF = 1;
    protected static final int TL_FORMAT_CSV = 2;

    private final String DSC_START = "<html><center>";
    private final String DSC_END = "</center></html>";
    private final String DSC_GH_SEEDING = "Plantings to be seeded into<br>trays in the greenhouse.";
    private final String DSC_FIELD_PLANTING = "Plantings to be seeded or<br>transplanted to the field.";
    private final String DSC_ALL_PLANTING_LISTS = "Batch generate all weekly greenhouse<br>and field planting lists for the<br>whole season.";
    private final String DSC_ALL_PLANTINGS = "Summary of each planting<br>from this list.";
    private final String DSC_SEED_ORDER_WORKSHEET = "Summary of each crop and/or variety<br>in this plan, including amount of seed<br>needed for each variety.";
    private final String DSC_HARVEST_AVAILABILITY = "List of harvest periods for<br>each crop and/or variety.";

    CPSComplexFilterDialog cfd = new CPSComplexFilterDialog();
    PDFExporter pdf = new PDFExporter();

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

        cmbPlanName = new JComboBox();
        cmbPlanName.setEditable(false);


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
        filFile = new JFileChooser();
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
        cmbWhatToExport.addItem( TL_ALL_PLANTING_LISTS );
        cmbWhatToExport.addItem( TL_ALL_PLANTINGS );
        cmbWhatToExport.addItem( TL_SEED_ORDER_WORKSHEET );
        cmbWhatToExport.addItem( TL_HARVEST_AVAILABILITY );
        cmbWhatToExport.addItemListener(this);

        lblExportDesc = new JLabel( DSC_START + DSC_GH_SEEDING + DSC_END );

        btnFormatPDF = new JButton( "Export as PDF" );
        btnFormatCSV = new JButton( "Export as CSV" );
        btnFormatPDF.addActionListener(this);
        btnFormatCSV.addActionListener(this);

        jplTodo = new JPanel( new MigLayout( "align center, gapy 0px!, insets 2px" ));
        jplTodo.setBorder(BorderFactory.createEmptyBorder());

        jplTodo.add( new JLabel("Create list from crop plan:"), "span 2, split 2" );
        jplTodo.add( cmbPlanName, "wrap" );

        jplTodo.add( new JLabel("For time period:") );
        jplTodo.add( rdoDateThisWeek, "wrap" );
        jplTodo.add( rdoDateNextWeek, "skip 1, wrap" );
        jplTodo.add( rdoDateThisNextWeek, "skip 1, span 2, wrap" );
        jplTodo.add( rdoDateOther, "skip 1, span 4, split 4" );
        jplTodo.add( dtcDateOtherStart );
        jplTodo.add( dtcDateOtherEnd, "wrap" );

        jplTodo.add( new JLabel("Include uncompleted plantings from:"), "span 2, wrap" );
        jplTodo.add( rdoUncompThisWeek, "skip 1, span 2, wrap" );
        jplTodo.add( rdoUncompLastWeek, "skip 1, span 2, wrap" );
        jplTodo.add( rdoUncompAll, "skip 1, span 2, wrap" );

        jplTodo.add( new JSeparator(), "growx, span 3, wrap" );

        jplTodo.add( new JLabel("Export files to directory/folder:"), "span 2, wrap" );
        jplTodo.add( lblDirectory, "span 3, align right, wrap" );
        jplTodo.add( btnSelectFile, "span 3, align right, wrap" );

        jplTodo.add( new JSeparator(), "growx, span 3, wrap" );

        jplTodo.add( new JLabel("Export:") );
        jplTodo.add( cmbWhatToExport, "span 2, wrap" );
        jplTodo.add( btnFormatPDF, "skip 1");
        jplTodo.add( lblExportDesc, "span 2 2, align center center, wrap" );
        jplTodo.add( btnFormatCSV, "skip 1, wrap");

    }
    // </editor-fold>


    protected void setDateComponentsEnabled( boolean b ) {

        rdoDateThisWeek.setEnabled(b);
        rdoDateNextWeek.setEnabled(b);
        rdoDateThisNextWeek.setEnabled(b);
        rdoDateOther.setEnabled(b);

        dtcDateOtherStart.setEnabled(b);
        dtcDateOtherEnd.setEnabled(b);

        rdoUncompThisWeek.setEnabled(b);
        rdoUncompLastWeek.setEnabled(b);
        rdoUncompAll.setEnabled(b);

    }



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
      filterAndSortList( l, f, new CPSPlantingComparator( sortProp ) );
    }
    
    private void filterAndSortList( List<CPSPlanting> l, CPSComplexPlantingFilter f, Comparator comp ) {

      if ( l != null )
        data.clear();

      dataFiltered.setMatcher( f );
      dataSorted.setComparator( comp );

      if ( l != null )
        data.addAll( l );

    }

//****************************************************************************//
//    Export GH Plantings
//****************************************************************************//
    private void exportGHPlantings(String planName, int format ) {

      if ( format != TL_FORMAT_PDF )
        throw new UnsupportedOperationException("Not supported yet.");

        String filename = createOutputFileName( filFile.getSelectedFile(),
                                                "GH Seeding List",
                                                dtcDateOtherStart.getDate() );


        //********************************************************************//
        // Create the filter
        //  1. transplanted
        //  2. not seeded
        //  3. planting is within the dates specified
        //  3a. (optional) include last weeks (or all seasons' incomplete ones
        //********************************************************************//
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
        } else
          filter.setPlantingRangeStart(dtcDateOtherStart.getDate());


        Comparator<CPSPlanting> comp = new Comparator<CPSPlanting>() {
                  public int compare( CPSPlanting o1, CPSPlanting o2 ) {
                    if ( o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() ) != 0 )
                      return o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() );
                    else
                      return o1.getCropName().compareTo( o2.getCropName() );
                  }
                };

        // this is an ugly, obfuscating crutch
        filterAndSortList( getDataSource().getCropPlan( planName ), filter, comp );

        AdjacentGroupingList<CPSPlanting> gl =
                new AdjacentGroupingList<CPSPlanting>( dataSorted, comp );

        String docTitle =
          "GH Seedings for " +
          new SimpleDateFormat("MMM dd").format(dtcDateOtherStart.getDate()) +
          " - " +
          new SimpleDateFormat("MMM dd, yyyy").format(dtcDateOtherEnd.getDate());
        if ( ! rdoUncompThisWeek.isSelected() )
          docTitle += "\n(includes previously uncompleted)";

        pdf.exportPlantingList( gl, new GHSeedingTableFormat(),
                                filename, CPSGlobalSettings.getFarmName(),
                                docTitle, "GH Seedings");


    }


//****************************************************************************//
//    Export ALL weekling planting lists
//****************************************************************************//
    private void exportAllWeeklyPlantings( String planName, int format ) {

      if ( format != TL_FORMAT_PDF )
        throw new UnsupportedOperationException("Not supported yet.");

        // should we explicitly reference the date_plant_plan property, or just rely on the date_plant property?
        int sortProp = CPSDataModelConstants.PROP_DATE_PLANT;

        String filename = createOutputFileName( filFile.getSelectedFile(),
                                                "Weekly Seeding Lists (GH)",
                                                dtcDateOtherStart.getDate() );

        CPSTable jt = new CPSTable();
        jt.setModel( new EventTableModel<CPSPlanting>( dataSorted, new GHSeedingTableFormat() ) );


        pdf.startExport( filename,
                              CPSGlobalSettings.getFarmName(),
                              "Weekly Seeding Lists (GH)",
                              null );


        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(false);

        // figure out what start date to use
        filterAndSortList( getDataSource().getCropPlan( planName ), filter, sortProp );

        Date startDate = dataSorted.get(0).getDateToPlant();
        Date endDate = dataSorted.get(dataSorted.size()-1).getDateToPlant();

        System.out.println("First planting happens on " + startDate );
        System.out.println(" Last planting happens on " + endDate );

        // now start filtering the data
        filter.setViewLimited(true);

        // filter out all direct seeded plantings
        filter.setFilterOnPlantingMethod(true);
        filter.setFilterMethodDirectSeed(false);


        // show only plantings in the correct date range
        filter.setFilterOnPlantingDate(true);


        tempCal.setTime( startDate );
        tempCal.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        
        while ( tempCal.getTime().before( endDate ) ) {

          String s = new SimpleDateFormat("MMM dd").format( tempCal.getTime() ) + " - ";
          filter.setPlantingRangeStart(tempCal.getTime());

          tempCal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
          s += new SimpleDateFormat("MMM dd, yyyy").format( tempCal.getTime() );
          filter.setPlantingRangeEnd( tempCal.getTime() );


          // refilter/sort the list
          filterAndSortList( null, filter, sortProp );

          if ( ! dataFiltered.isEmpty() )
            pdf.addPage( jt, "GH Seedings for " + s );
          
          // bump the day by one
          tempCal.add( Calendar.DAY_OF_YEAR, 1 );

        }

        pdf.endExport();


        //****************************************************************************//
        //
        //****************************************************************************//

        filename = createOutputFileName( filFile.getSelectedFile(),
                                         "Weekly Planting Lists (Field)",
                                         dtcDateOtherStart.getDate());


        pdf.startExport( filename,
                         CPSGlobalSettings.getFarmName(),
                         "Weekly Planting Lists (Field)",
                         null );


        // reset the filter
        filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(true);
        filter.setFilterOnPlantingMethod(true);

        tempCal.setTime( startDate );
        tempCal.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        while ( tempCal.getTime().before( endDate ) ) {

          // set the correct date ranges
          String s = new SimpleDateFormat("MMM dd").format( tempCal.getTime() ) + " - ";
          filter.setPlantingRangeStart(tempCal.getTime());
          filter.setTpRangeStart( tempCal.getTime() );

          tempCal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
          s += new SimpleDateFormat("MMM dd, yyyy").format( tempCal.getTime() );
          filter.setPlantingRangeEnd( tempCal.getTime() );
          filter.setTpRangeEnd( tempCal.getTime() );

          //******************************************************************//
          // First, direct seeded
          //******************************************************************//
          filter.setFilterMethodDirectSeed(true);
          filter.setFilterOnPlantingDate(true);
          filter.setFilterOnTPDate(false);

          filterAndSortList( null, filter, CPSDataModelConstants.PROP_DATE_PLANT );

          if ( ! dataFiltered.isEmpty() ) {
            ((EventTableModel) jt.getModel()).setTableFormat( new DSFieldPlantingTableFormat() );
            pdf.addPage( jt, "Direct Seeded Plantings for " + s );
          }

          //******************************************************************//
          // Then, transplanted
          //******************************************************************//
          filter.setFilterMethodDirectSeed(false);
          filter.setFilterOnPlantingDate(false);
          filter.setFilterOnTPDate(true);

          filterAndSortList( null, filter, CPSDataModelConstants.PROP_DATE_TP );

          if ( ! dataFiltered.isEmpty() ) {
            ((EventTableModel) jt.getModel()).setTableFormat( new TPFieldPlantingTableFormat() );
            pdf.addPage( jt, "Transplanted Field Plantings for " + s );
          }

          // bump the day by one
          tempCal.add( Calendar.DAY_OF_YEAR, 1 );

        }

        pdf.endExport();

    }


//****************************************************************************//
//    Export Field Planting List
//****************************************************************************//
    private void exportFieldPlantings(String planName, int format ) {

      if ( format != TL_FORMAT_PDF )
        throw new UnsupportedOperationException("Not supported yet.");

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
          filter.setPlantingRangeStart(dtcDateOtherStart.getDate());


        Comparator<CPSPlanting> comp = new Comparator<CPSPlanting>() {
          public int compare( CPSPlanting o1, CPSPlanting o2 ) {
            if ( o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() ) != 0 )
              return o1.getDateToPlantPlanned().compareTo( o2.getDateToPlantPlanned() );
            else
              return o1.getCropName().compareTo( o2.getCropName() );
          }
        };


        filterAndSortList( getDataSource().getCropPlan( planName ), filter, comp );

        AdjacentGroupingList<CPSPlanting> gl =
                new AdjacentGroupingList<CPSPlanting>( dataSorted, comp );

        String docTitle =
          "Field plantings for " +
          new SimpleDateFormat("MMM dd").format(dtcDateOtherStart.getDate()) +
          " - " +
          new SimpleDateFormat("MMM dd, yyyy").format(dtcDateOtherEnd.getDate());
        if ( ! rdoUncompThisWeek.isSelected() )
          docTitle += "\n(includes previously uncompleted)";

        pdf.startExport( filename, CPSGlobalSettings.getFarmName(), docTitle );
        pdf.addTable( pdf.convertPlantingList( gl, new DSFieldPlantingTableFormat() ),
                      "Direct Seeded Field Plantings" );

        /*
         * Select just uncompleted, transplanted plantings whose TRANSPLANT date
         * is w/i range
         */
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

        comp = new Comparator<CPSPlanting>() {
            public int compare( CPSPlanting o1, CPSPlanting o2 ) {
              if ( o1.getDateToTPPlanned().compareTo( o2.getDateToTPPlanned() ) != 0 )
                return o1.getDateToTPPlanned().compareTo( o2.getDateToTPPlanned() );
              else
                return o1.getCropName().compareTo( o2.getCropName() );
            }
          };

        gl.dispose();
        filterAndSortList( null, filter, comp );

        gl = new AdjacentGroupingList<CPSPlanting>( dataSorted, comp );

        pdf.addTableOnNewPage( pdf.convertPlantingList( gl, new TPFieldPlantingTableFormat() ),
                               "Transplanted Field Plantings");
        pdf.endExport();

    }

    private void exportAllPlantings(String planName, int format ) {

      int sortProp = CPSDataModelConstants.PROP_DATE_PLANT;
      
        String filename = createOutputFileName( filFile.getSelectedFile(),
                                                "All Plantings",
                                                dtcDateOtherStart.getDate(),
                                                format );


        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(false);

        CPSTable jt = new CPSTable();

        filterAndSortList( getDataSource().getCropPlan( planName ), filter, sortProp );

        jt.setModel( new EventTableModel<CPSPlanting>( dataSorted, new AllPlantingsTableFormat() ));

        if ( format == TL_FORMAT_CSV ) {
          new CSV().exportJTable( filename, "All Plantings for plan \"" + planName + "\"", jt );
        } else {
          pdf.exportLandscape( jt, filename,
                               CPSGlobalSettings.getFarmName(),
                               "All Plantings for plan \"" + planName + "\"",
                               "All Plantings" );
        }

    }

    private void exportAvailabilityList( String planName, int format ) {

      String filename = createOutputFileName( filFile.getSelectedFile(),
                                              "Harvest Availability List",
                                              dtcDateOtherStart.getDate(),
                                              format );

        // clear the master list and fill it up with our crop plan
        data.clear();
        data.addAll( getDataSource().getCropPlan( planName ) );

        // create a basic filter and set it to exclude "ignored" plantings
        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(true);

        // create the list that will hold all of our accumulated filters
        // and start it out with the basic filter from above
        BasicEventList<MatcherEditor<CPSPlanting>> listOfFilters =
                new BasicEventList<MatcherEditor<CPSPlanting>>();
        listOfFilters.add( filter );

        // create a Composite filter that will match all (see the AND)
        // of the fitlers in the 'listOfFilters'
        CompositeMatcherEditor<CPSPlanting> compositeFilter =
                new CompositeMatcherEditor<CPSPlanting>( listOfFilters );
        compositeFilter.setMode( CompositeMatcherEditor.AND );

        // finally set the filtered list to match against this new compositeFilter
        dataFiltered.setMatcherEditor(compositeFilter);

        // create a new list from our crop plan and sort it by crop and var name
        dataSorted = new SortedList<CPSPlanting>( dataFiltered,
                                                  new CropVarComparator() );

        // create a second filtered list based on the filtered master list
        // this is the list we will use to do all of our crop/var calculations
        FilterList<CPSPlanting> singleCropVarList =
                new FilterList<CPSPlanting>( dataFiltered );

        // create the list that will hold the "plantings" we will manufacture
        // from the stats being calculated
        BasicEventList<CPSPlanting> availabilityList = new BasicEventList<CPSPlanting>();

        // now create a sorted list based on our filtered single crop/var list
        // and set it to initially sort on harvest start date
        SortedList<CPSPlanting> singleListSorted =
          new SortedList<CPSPlanting>( singleCropVarList,
                                       new CPSPlantingComparator(
                                         CPSDataModelConstants.PROP_DATE_HARVEST ));

        // now loop while we have data in our master filtered list
        while ( dataFiltered.size() > 0 ) {

          // get the first entry on the sorted list
          CPSPlanting p = dataSorted.get(0);

          // create a crop/var filter to match the crop/var of this first entry
          // and set our working list to match on that (ie contain only
          // plantings of this crop/var)
          CropVarMatcher cvm = new CropVarMatcher(p);
          singleCropVarList.setMatcher( cvm );

          // TODO should also match on when a Crop+Var has a different seed count or weight unit

          // create new empty planting to represent our computations
          // and populate it with data from this crop/var AND
          // the summary calculations
          CPSPlanting s = new CPSPlanting();
          s.setCropName( p.getCropName() );
          s.setVarietyName( p.getVarietyName() );
          // use MatDays to hold the # of plantings of this crop/var
          s.setMaturityDays( singleCropVarList.size() );

          String str = "";
          boolean inAHarvestWindow = false;

          for ( int i = 0; i < singleListSorted.size(); i++ ) {

            if ( ! inAHarvestWindow ) {
              if ( ! str.equals("") )
                str += ", ";
              str += CPSDateValidator.format( singleListSorted.get(i).getDateHarvestStart(),
                                              CPSDateValidator.DATE_FORMAT_MON_DAY )
                     + " - ";
              inAHarvestWindow = true;
            }

            if ( i == singleListSorted.size() - 1 ||
                 singleListSorted.get(i)
                                 .getDateHarvestEnd()
                                 .before( singleListSorted.get(i+1)
                                                          .getDateHarvestStart() )) {
              str += CPSDateValidator.format( singleListSorted.get(i).getDateHarvestEnd(),
                                              CPSDateValidator.DATE_FORMAT_MON_DAY );
              inAHarvestWindow = false;
            }

          }

          // use custom 1 to hold our availabilty string
          s.setCustomField1(str);
          
          // now save this newly manufactured "planting"
          availabilityList.add(s);

          // finally, invert the filter for this crop/var (ie make it match
          // "everything BUT this crop/var") and add it to the list of filters
          // being applied to the master list
          // this effectively removes plantings of this crop/var from
          // our master list so that on the next iteration of this loop,
          // the first entry of the sorted list will be the next crop/var
          listOfFilters.add( cvm.invert() );

        }

        AvailabilityTableFormat tf = new AvailabilityTableFormat();

        CPSTable jt = new CPSTable();
        jt.setModel( new EventTableModel<CPSPlanting>( availabilityList, tf ) );

      if ( format == TL_FORMAT_CSV ) {
        new CSV().exportJTable( filename, "Harvest Availability for plan \"" + planName + "\"", jt );
      } else {
        pdf.export( jt, filename,
                     CPSGlobalSettings.getFarmName(),
                     "Harvest Availability for plan \"" + planName + "\"",
                     "Harvest Availability" );
      }


    }

    private void exportSeedOrderLists( String planName, int format ) {

        String filename = createOutputFileName( filFile.getSelectedFile(),
                                                "Seed Order Info",
                                                dtcDateOtherStart.getDate(),
                                                format );

        // clear the master list and fill it up with our crop plan
        data.clear();
        data.addAll( getDataSource().getCropPlan( planName ) );

        // create a basic filter and set it to exclude "ignored" plantings
        CPSComplexPlantingFilter filter = new CPSComplexPlantingFilter();
        filter.setViewLimited(true);

        // create the list that will hold all of our accumulated filters
        // and start it out with the basic filter from above
        BasicEventList<MatcherEditor<CPSPlanting>> listOfFilters =
                new BasicEventList<MatcherEditor<CPSPlanting>>();
        listOfFilters.add( filter );

        // create a Composite filter that will match all (see the AND)
        // of the fitlers in the 'listOfFilters'
        CompositeMatcherEditor<CPSPlanting> compositeFilter = 
                new CompositeMatcherEditor<CPSPlanting>( listOfFilters );
        compositeFilter.setMode( CompositeMatcherEditor.AND );

        // finally set the filtered list to match against this new compositeFilter
        dataFiltered.setMatcherEditor(compositeFilter);

        // create a new list from our crop plan and sort it by crop and var name
        dataSorted = new SortedList<CPSPlanting>( dataFiltered,
                                                  new CropVarComparator() );

        // create a second filtered list based on the filtered master list
        // this is the list we will use to do all of our crop/var calculations
        FilterList<CPSPlanting> singleCropVarList =
                new FilterList<CPSPlanting>( dataFiltered );

        // set up our calculations based on the new filtered list
        FunctionList<CPSPlanting, Integer> rftList =
              new FunctionList<CPSPlanting, Integer>( singleCropVarList,
                                                 new FunctionList.Function<CPSPlanting, Integer>() {
                                                    public Integer evaluate( CPSPlanting p ) {
                                                       return p.getRowFtToPlant();
                                                    }} );

        FunctionList<CPSPlanting, Integer> plantsList =
              new FunctionList<CPSPlanting, Integer>( singleCropVarList,
                                                 new FunctionList.Function<CPSPlanting, Integer>() {
                                                    public Integer evaluate( CPSPlanting p ) {
                                                       return p.getPlantsToStart();
                                                    }} );

        FunctionList<CPSPlanting, Float> bedsList =
              new FunctionList<CPSPlanting, Float>( singleCropVarList,
                                                  new FunctionList.Function<CPSPlanting, Float>() {
                                                     public Float evaluate( CPSPlanting p ) {
                                                        return p.getBedsToPlant();
                                                     }} );

        FunctionList<CPSPlanting, Float> flatsList =
              new FunctionList<CPSPlanting, Float>( singleCropVarList,
                                                  new FunctionList.Function<CPSPlanting, Float>() {
                                                     public Float evaluate( CPSPlanting p ) {
                                                        return p.getFlatsNeeded();
                                                     }} );
        FunctionList<CPSPlanting, Float> seedsList =
              new FunctionList<CPSPlanting, Float>( singleCropVarList,
                                                  new FunctionList.Function<CPSPlanting, Float>() {
                                                     public Float evaluate( CPSPlanting p ) {
                                                       if ( p.getSeedNeeded() > 0 )
                                                        return p.getSeedNeeded();
                                                       else
                                                         return 0f;
                                                     }} );


        Calculation<Integer> summaryPlantings = Calculations.count( singleCropVarList );
        Calculation<Integer> summaryRowFt = Calculations.sumIntegers( rftList );
        Calculation<Integer> summaryPlants = Calculations.sumIntegers( plantsList );
        Calculation<Float> summaryBeds = Calculations.sumFloats( bedsList );
        Calculation<Float> summaryFlats = Calculations.sumFloats( flatsList );
        Calculation<Float> summarySeeds = Calculations.sumFloats( seedsList );


        // create the list that will hold the "plantings" we will manufacture
        // from the stats being calculated
        BasicEventList<CPSPlanting> seedStatsList = new BasicEventList<CPSPlanting>();

        // now loop while we have data in our master filtered list
        while ( dataFiltered.size() > 0 ) {

          // get the first entry on the sorted list
          CPSPlanting p = dataSorted.get(0);

          // create a crop/var filter to match the crop/var of this first entry
          // and set our working list to match on that (ie contain only
          // plantings of this crop/var)
          CropVarMatcher cvm = new CropVarMatcher(p);
          singleCropVarList.setMatcher( cvm );

          // TODO should also match on when a Crop+Var has a different seed count or weight unit

          // create new empty planting to represent our summary
          // and populate it with data from this crop/var AND
          // the summary calculations
          CPSPlanting s = new CPSPlanting();
          s.setCropName( p.getCropName() );
          s.setVarietyName( p.getVarietyName() );
          // this is a dirty dirty hack that is only used because we're
          // controlling the table format for the output
          // use MatDays to hold the # of plantings of this crop/var
          s.setMaturityDays( summaryPlantings.getValue() );
          s.setDirectSeeded( p.isDirectSeeded() );

          s.setBedsToPlant( summaryBeds.getValue() + .001f );
          s.setRowFtToPlant( summaryRowFt.getValue() );

          s.setInRowSpacing( p.getInRowSpacing() );
          s.setPlantsToStart( summaryPlants.getValue() );
          s.setFlatsNeeded( summaryFlats.getValue() );
          s.setFlatSize( p.getFlatSize() );

          if ( p.getSeedsPerUnit() > 0 )
            s.setSeedsPerUnit( p.getSeedsPerUnit() );
          s.setSeedUnit( p.getSeedUnit() );
          s.setSeedsPer( p.getSeedsPer() );
          s.setSeedNeeded( summarySeeds.getValue() );

          // now save this newly manufactured "planting"
          seedStatsList.add(s);

          // finally, invert the filter for this crop/var (ie make it match
          // "everything BUT this crop/var") and add it to the list of filters
          // being applied to the master list
          // this effectively removes plantings of this crop/var from
          // our master list so that on the next iteration of this loop,
          // the first entry of the sorted list will be the next crop/var
          listOfFilters.add( cvm.invert() );

        }

        
        SeedStatsTableFormat tf = new SeedStatsTableFormat();
        tf.setOutputFormat( format ); // TL_FORMAT_CSV or TL_FORMAT_PDF

        CPSTable jt = new CPSTable();
        jt.setModel( new EventTableModel<CPSPlanting>( seedStatsList, tf ) );

        if ( format == TL_FORMAT_CSV ) {
          new CSV().exportJTable( filename, "Seed Order Worksheet for plan \"" + planName + "\"", jt );
        } else {
          pdf.exportLandscape( jt, filename,
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

          jplTodo.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          if ( whatToExport.equals( TL_GH_SEEDING ) ) {
            exportGHPlantings( planName, format );
          } else if ( whatToExport.equals( TL_FIELD_PLANTING ) ) {
            exportFieldPlantings( planName, format );
          } else if ( whatToExport.equals( TL_ALL_PLANTING_LISTS ) ) {
            exportAllWeeklyPlantings(planName, format);
          } else if ( whatToExport.equals( TL_ALL_PLANTINGS ) ) {
            exportAllPlantings( planName, format );
          } else if ( whatToExport.equals( TL_SEED_ORDER_WORKSHEET ) ) {
            exportSeedOrderLists( planName, format );
          } else if ( whatToExport.equals( TL_HARVEST_AVAILABILITY ) ) {
            exportAvailabilityList( planName, format );
          }
          jplTodo.setCursor(Cursor.getDefaultCursor());

        } else if (action.equalsIgnoreCase(btnSelectFile.getText())) {
            int status = filFile.showDialog(jplTodo, "Accept");
            if (status == JFileChooser.APPROVE_OPTION) {
                lblDirectory.setText(filFile.getSelectedFile().getPath());
            }
        }

    }

    // for ItemListener
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
        } else if ( source == cmbWhatToExport ) {

          String s = (String) cmbWhatToExport.getSelectedItem();
          String t = "";
          if ( s.equals( TL_GH_SEEDING ) )
            t = DSC_GH_SEEDING;
          else if ( s.equals( TL_FIELD_PLANTING ) )
            t = DSC_FIELD_PLANTING;
          else if ( s.equals( TL_ALL_PLANTING_LISTS ) )
            t = DSC_ALL_PLANTING_LISTS;
          else if ( s.equals( TL_ALL_PLANTINGS ) )
            t = DSC_ALL_PLANTINGS;
          else if ( s.equals( TL_SEED_ORDER_WORKSHEET ) )
            t = DSC_SEED_ORDER_WORKSHEET;
          else if ( s.equals( TL_HARVEST_AVAILABILITY ) )
            t = DSC_HARVEST_AVAILABILITY;

          setDateComponentsEnabled( t.equals( DSC_GH_SEEDING ) ||
                                    t.equals( DSC_FIELD_PLANTING ) );

          lblExportDesc.setText( DSC_START + t + DSC_END );

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



    /**
     * Compares a planting and sorts them based on crop AND var name.
     */
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
