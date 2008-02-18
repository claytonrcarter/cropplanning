/* CPSComplexFilterDialog.java - created: Jan 31, 2008
 * Copyright (C) *** Jan 31, 2008 *** kendrajm ***
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

import CPS.Data.CPSComplexPlantingFilter;
import CPS.Data.CPSDateValidator;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class CPSComplexFilterDialog extends JDialog implements ItemListener, ActionListener {

    private JPanel jplMain, jplStatus, jplDates, jplButtons;
    JRadioButton rdoNoLimit, rdoLimit; 
    JLabel lblPlanted;
    JRadioButton rdoPlantYes, rdoPlantNo, rdoPlantAll;
    JLabel lblTPed;
    JRadioButton rdoTPYes, rdoTPNo, rdoTPAll;
    JLabel lblHarvested;
    JRadioButton rdoHarvestYes, rdoHarvestNo, rdoHarvestAll;
    JLabel lblPlantDate, lblPlantDateRange;
    JButton btnPlantDateRange;
    DateRangeDialog dlgPlantRange;
    JLabel lblTPDate, lblTPDateRange;
    JButton btnTPDateRange;
    DateRangeDialog dlgTPRange;
    JLabel lblHarvestDate, lblHarvestDateRange;
    JButton btnHarvestDateRange;
    DateRangeDialog dlgHarvestRange;
    
    
    JButton btnSetView, btnCancel;
    
    CPSComplexPlantingFilter savedFilter;
    CPSDateValidator dateValidator;
    
    
    public CPSComplexFilterDialog() {
    
        super( (JFrame) null, "Set View Limit", true );
        
        savedFilter = new CPSComplexPlantingFilter();
        dateValidator = new CPSDateValidator();
        
        buildMainPanel();
        rdoNoLimit.doClick();
//        setComponentsEnabled(false); // disable by default
        add( jplMain );
        pack();
        
    }
    
    
    public CPSComplexPlantingFilter getFilter() {
        return savedFilter;
    }
    
    public void resetFilter() {
        savedFilter = new CPSComplexPlantingFilter();
    }
    
    public void setFilter( CPSComplexPlantingFilter f ) {
        savedFilter = f;
        // TODO, update the state of the dialog to display this filter
    }
    
    
    private CPSComplexPlantingFilter asFilter() {
        
        CPSComplexPlantingFilter f = new CPSComplexPlantingFilter();
        
        if ( ! rdoNoLimit.isSelected() ) {
            
            f.setViewLimited(true);
            
            /* status */
            if ( f.setFilterOnPlanting( ! rdoPlantAll.isSelected() ))
                f.setDonePlanting( rdoPlantYes.isSelected() );
            if ( f.setFilterOnTransplanting( ! rdoTPAll.isSelected() ))
                f.setDoneTransplanting( rdoTPYes.isSelected() );
            if ( f.setFilterOnHarvest( ! rdoHarvestAll.isSelected() ))
                f.setDoneHarvesting( rdoHarvestYes.isSelected() );

            /* dates */
            if ( f.setFilterOnPlantingDate( dlgPlantRange.isDateRangeSet() ) ) {
               f.setPlantingRangeStart( dlgPlantRange.getStartDate() );
               f.setPlantingRangeEnd( dlgPlantRange.getEndDate() ) ;
            }
            if ( f.setFilterOnTPDate( dlgTPRange.isDateRangeSet() ) ) {
               f.setTpRangeStart( dlgTPRange.getStartDate() );
               f.setTpRangeEnd( dlgTPRange.getEndDate() ) ;
            }
            if ( f.setFilterOnHarvestDate( dlgHarvestRange.isDateRangeSet() ) ) {
               f.setHarvestDateStart( dlgHarvestRange.getStartDate() );
               f.setHarvestDateEnd( dlgHarvestRange.getEndDate() ) ;
            }
            
        }
        
        return f;

    }
    
    
    private void buildMainPanel() {
        
        rdoNoLimit = new JRadioButton( "Do not limit; show everything.", true );
        rdoLimit = new JRadioButton( "<html>Limit view based upon the following<br> criteria:</html>", false );
        rdoNoLimit.addItemListener(this);
        rdoLimit.addItemListener(this);
        ButtonGroup bgLimit = new ButtonGroup();
        bgLimit.add( rdoNoLimit );
        bgLimit.add( rdoLimit );
        
        
        jplStatus = new JPanel();
        jplStatus.setBorder( BorderFactory.createTitledBorder( "Status of Planting" ));
        jplStatus.setLayout( new GridBagLayout() );
        
        LayoutAssist.addLabel( jplStatus, 1, 0, new JLabel("Yes") );
        LayoutAssist.addLabel( jplStatus, 2, 0, new JLabel("No") );
        LayoutAssist.addLabel( jplStatus, 3, 0, new JLabel("All") );
        
        rdoPlantYes = new JRadioButton();
        rdoPlantNo = new JRadioButton();
        rdoPlantAll = new JRadioButton();
        rdoPlantAll.setSelected(true);
        rdoPlantYes.addItemListener(this);
        rdoPlantNo.addItemListener( this );
        rdoPlantAll.addItemListener(this);
        ButtonGroup bgPlant = new ButtonGroup();
        bgPlant.add( rdoPlantYes );
        bgPlant.add( rdoPlantNo );
        bgPlant.add( rdoPlantAll );
        
        LayoutAssist.addLabel(  jplStatus, 0, 1, new JLabel( "Has been planted/seeded?" ));
        LayoutAssist.addButton( jplStatus, 1, 1, rdoPlantYes );
        LayoutAssist.addButton( jplStatus, 2, 1, rdoPlantNo );
        LayoutAssist.addButton( jplStatus, 3, 1, rdoPlantAll );
        
        rdoTPYes = new JRadioButton();
        rdoTPNo = new JRadioButton();
        rdoTPAll = new JRadioButton();
        rdoTPAll.setSelected(true);
        rdoTPYes.addItemListener(this);
        rdoTPNo.addItemListener( this );
        rdoTPAll.addItemListener(this);
        ButtonGroup bgTP = new ButtonGroup();
        bgTP.add( rdoTPYes );
        bgTP.add( rdoTPNo );
        bgTP.add( rdoTPAll );
        
        LayoutAssist.addLabel(  jplStatus, 0, 2, new JLabel( "Has been transplanted?" ));
        LayoutAssist.addButton( jplStatus, 1, 2, rdoTPYes );
        LayoutAssist.addButton( jplStatus, 2, 2, rdoTPNo );
        LayoutAssist.addButton( jplStatus, 3, 2, rdoTPAll );
        
        rdoHarvestYes = new JRadioButton();
        rdoHarvestNo = new JRadioButton();
        rdoHarvestAll = new JRadioButton();
        rdoHarvestAll.setSelected(true);
        rdoHarvestYes.addItemListener(this);
        rdoHarvestNo.addItemListener( this );
        rdoHarvestAll.addItemListener(this);
        ButtonGroup bgHarvest = new ButtonGroup();
        bgHarvest.add( rdoHarvestYes );
        bgHarvest.add( rdoHarvestNo );
        bgHarvest.add( rdoHarvestAll );
        
        LayoutAssist.addLabel(  jplStatus, 0, 3, new JLabel( "Has been harvested?" ));
        LayoutAssist.addButton( jplStatus, 1, 3, rdoHarvestYes );
        LayoutAssist.addButton( jplStatus, 2, 3, rdoHarvestNo );
        LayoutAssist.addButton( jplStatus, 3, 3, rdoHarvestAll );
        
        
        jplDates = new JPanel();
        jplDates.setBorder( BorderFactory.createTitledBorder( "Date Ranges" ));
        jplDates.setLayout( new GridBagLayout() );
        
        lblPlantDateRange = new JLabel( "Show all" );
        btnPlantDateRange = new JButton( "Set" );
        btnPlantDateRange.addActionListener(this);
        dlgPlantRange = new DateRangeDialog( "Planting date range" );
        LayoutAssist.addLabel(           jplDates, 0, 0, new JLabel( "Planting date:" ) );
        LayoutAssist.addLabelLeftAlign( jplDates, 1, 0, lblPlantDateRange );
        LayoutAssist.addButton(          jplDates, 2, 0, btnPlantDateRange );
        
        lblTPDateRange = new JLabel( "Show all" );
        btnTPDateRange = new JButton( "Set" );
        btnTPDateRange.addActionListener(this);
        dlgTPRange = new DateRangeDialog( "TP date range" );
        LayoutAssist.addLabel(           jplDates, 0, 1, new JLabel( "Transplant date:" ) );
        LayoutAssist.addLabelLeftAlign( jplDates, 1, 1, lblTPDateRange );
        LayoutAssist.addButton(          jplDates, 2, 1, btnTPDateRange );
        
        lblHarvestDateRange = new JLabel( "Show all" );
        btnHarvestDateRange = new JButton( "Set" );
        btnHarvestDateRange.addActionListener(this);
        dlgHarvestRange = new DateRangeDialog( "Harvest date range" );
        LayoutAssist.addLabel(           jplDates, 0, 2, new JLabel( "Harvest date:" ) );
        LayoutAssist.addLabelLeftAlign( jplDates, 1, 2, lblHarvestDateRange );
        LayoutAssist.addButton(          jplDates, 2, 2, btnHarvestDateRange );
        
        jplButtons = new JPanel();
        jplButtons.setLayout( new BoxLayout( jplButtons, BoxLayout.LINE_AXIS ) );
        btnSetView = new JButton( "Set View" );
        btnCancel = new JButton( "Cancel" );
        btnSetView.addActionListener(this);
        btnCancel.addActionListener(this);
        jplButtons.add( Box.createHorizontalGlue() );
        jplButtons.add( btnSetView );
        jplButtons.add( btnCancel );
        
        
        
        jplMain = new JPanel();
        jplMain.setLayout( new BoxLayout( jplMain, BoxLayout.PAGE_AXIS ) );
        
        JLabel l = new JLabel( "<html>Use these controls to define a<br>" +
                                 "default view filter.  View filters<br>" +
                                 "are used to \"limit\" the data<br>" +
                                 "which is displayed in the maine table.</html>" );
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        jplMain.add( l );
        rdoNoLimit.setAlignmentX(Component.LEFT_ALIGNMENT);
        jplMain.add( rdoNoLimit );
        rdoLimit.setAlignmentX(Component.LEFT_ALIGNMENT);
        jplMain.add( rdoLimit );
        jplStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        jplMain.add( jplStatus );
        jplDates.setAlignmentX(Component.LEFT_ALIGNMENT);
        jplMain.add( jplDates );
        jplButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
        jplMain.add( jplButtons );
        
        
        
    }

    public void itemStateChanged( ItemEvent arg0 ) {
        Object source = arg0.getSource();
        
        if      ( source == rdoNoLimit || source == rdoLimit ) {
            setComponentsEnabled( ! rdoNoLimit.isSelected() );
        }
        
    }
    
    private void setComponentsEnabled( boolean enable ) {
        rdoPlantYes.setEnabled(enable);
        rdoPlantNo.setEnabled( enable );
        rdoPlantAll.setEnabled( enable );
    
        rdoTPYes.setEnabled( enable );
        rdoTPNo.setEnabled( enable );
        rdoTPAll.setEnabled( enable );

        rdoHarvestYes.setEnabled( enable );
        rdoHarvestNo.setEnabled( enable );
        rdoHarvestAll.setEnabled( enable );

        btnPlantDateRange.setEnabled( enable );
        btnTPDateRange.setEnabled( enable );
        btnHarvestDateRange.setEnabled( enable );
    }

    public void actionPerformed( ActionEvent arg0 ) {
        String action = arg0.getActionCommand();
        
        if      ( action.equalsIgnoreCase( btnSetView.getText() )) {
            savedFilter = this.asFilter();
            setVisible(false);
        }
        else if ( action.equalsIgnoreCase( btnCancel.getText() )) {
            setVisible(false);
        }
        else if ( action.equalsIgnoreCase( btnPlantDateRange.getText() )) {
            dlgPlantRange.setVisible(true);
            if ( dlgPlantRange.isDateRangeSet() )
                this.lblPlantDateRange.setText( dateValidator.format( dlgPlantRange.getStartDate() ) + " - " +
                                                dateValidator.format( dlgPlantRange.getEndDate() ) );
            // now do something w/ the dates
        }
        else if ( action.equalsIgnoreCase( btnTPDateRange.getText() )) {
            dlgTPRange.setVisible(true);
            this.lblTPDateRange.setText( dateValidator.format( dlgTPRange.getStartDate() ) + " - " +
                                         dateValidator.format( dlgTPRange.getEndDate() ) );
            // now do something w/ the dates
        }
        else if ( action.equalsIgnoreCase( btnHarvestDateRange.getText() )) {
            dlgHarvestRange.setVisible(true);
            this.lblHarvestDateRange.setText( dateValidator.format( dlgHarvestRange.getStartDate() ) + " - " +
                                              dateValidator.format( dlgHarvestRange.getEndDate() ) );
            // now do something w/ the dates
        }
        
    }
    
    
    /*
     * Inner Class Def'n
     * 
     * 
     * 
     * 
     * 
     */
    public class DateRangeDialog extends JDialog implements ItemListener, ActionListener {
        
        private Date startDate, endDate;
        private boolean dateRangeSet;
        private JPanel jplMain, jplButtons;
        private JRadioButton rdoAllDates, rdoLimitDates;
        private JDateChooser startDateChooser, endDateChooser;
        private JButton btnSetRange, btnCancel;
        
        public DateRangeDialog( String title ) {
            
            super( (JFrame) null, title, true );
            
            setDateRangeSet( false );
            
            buildMainPanel();
            rdoAllDates.doClick();
//            setComponentsEnabled(false);
            add(jplMain);
            pack();
            
        }

        
        public boolean isDateRangeSet() { return dateRangeSet; }
        private void setDateRangeSet( boolean dateRangeSet ) { this.dateRangeSet = dateRangeSet; }

        public void setEndDate( Date endDate ) { this.endDate = endDate; }
        private Date getEndDate() { return endDate; }

        public void setStartDate( Date startDate ) { this.startDate = startDate; }
        private Date getStartDate() { return startDate; }
        
        
        private void buildMainPanel() {
            
            jplMain = new JPanel();
            jplMain.setLayout( new GridBagLayout() );
            
            rdoAllDates = new JRadioButton( "Display all dates.", true );
            rdoLimitDates = new JRadioButton( "Limit view to the following date range:", false );
            rdoAllDates.addItemListener(this);
            rdoLimitDates.addItemListener(this);
            ButtonGroup bg = new ButtonGroup();
            bg.add( rdoAllDates );
            bg.add( rdoLimitDates );
            
            startDateChooser = new JDateChooser();
            endDateChooser = new JDateChooser();
            
            btnSetRange = new JButton( "Set Range" );
            btnCancel = new JButton( "Cancel" );
            btnSetRange.addActionListener( this );
            btnCancel.addActionListener( this );
            
            
            LayoutAssist.addButton( jplMain, 0, 0, 2, 1,  rdoAllDates );
            LayoutAssist.addButton( jplMain, 0, 1, 2, 1, rdoLimitDates );
            
            LayoutAssist.addLabel(    jplMain, 0, 2, new JLabel( "Show dates after" ));
            LayoutAssist.addSubPanel( jplMain, 1, 2, 1, 1, startDateChooser );
            
            LayoutAssist.addLabel(    jplMain, 0, 3, new JLabel( "Show dates before" ));
            LayoutAssist.addSubPanel( jplMain, 1, 3, 1, 1, endDateChooser );
            
            jplButtons = new JPanel();
            jplButtons.setLayout( new BoxLayout( jplButtons, BoxLayout.LINE_AXIS ));
            jplButtons.add( Box.createHorizontalGlue() );
            jplButtons.add( btnSetRange );
            jplButtons.add( btnCancel );
            
            LayoutAssist.addSubPanel( jplMain, 0, 4, 2, 1, jplButtons );
            
        }
        
        
        public void itemStateChanged( ItemEvent arg0 ) {
            Object source = arg0.getSource();
        
            if ( source == rdoAllDates || source == rdoLimitDates ) {
                setDateRangeSet( rdoLimitDates.isSelected() );
                setComponentsEnabled( rdoLimitDates.isSelected() );
            }
        
        }
    
        private void setComponentsEnabled( boolean enable ) {
            startDateChooser.setEnabled( enable );
            endDateChooser.setEnabled( enable );
        }
     
        public void actionPerformed( ActionEvent arg0 ) {
            String action = arg0.getActionCommand();
        
            if ( action.equalsIgnoreCase( btnSetRange.getText() ) ) {
                setStartDate( startDateChooser.getDate() );
                setEndDate(   endDateChooser.getDate() );
                setVisible( false );
            }
            else if ( action.equalsIgnoreCase( btnCancel.getText() ) ) {
                setVisible( false );
            }
        
        }
        
        
    }
    
}
