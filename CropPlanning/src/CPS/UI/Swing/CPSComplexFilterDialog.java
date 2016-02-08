/* CPSComplexFilterDialog.java - created: Jan 31, 2008
 * Copyright (C) *** Jan 31, 2008 *** kendrajm ***
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

package CPS.UI.Swing;

import CPS.Data.CPSComplexPlantingFilter;
import CPS.Data.CPSDateValidator;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;

public class CPSComplexFilterDialog extends CPSDialog implements ItemListener, 
                                                                 ActionListener,
                                                                 WindowListener {

  private final String migDefaults = "gapy 0px!, insets 2px";

    private static final String SHOW_ALL = "Show all";
    
    private JPanel jplStatus, jplDates;
    JRadioButton rdoNoLimit, rdoLimit; 
    JLabel lblPlantingMethod;
    JRadioButton rdoDS, rdoTP, rdoDSAndTP;
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
    
    
    public CPSComplexFilterDialog( Component parent ) {
    
        super( parent, "Set View Limit" );
        
        setDescription( "Use these controls to define a<br>" +
                        "default view filter.  View filters are<br>" +
                        "used to \"limit\" the data which is<br>" +
                        "displayed in the maintable." );

        addWindowListener( this );

        savedFilter = new CPSComplexPlantingFilter();
        dateValidator = new CPSDateValidator();
        
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
        updateFilter( f );
        return f;
        
    }

    public void updateFilter ( CPSComplexPlantingFilter f ) {
       
       f.reset();

        if ( ! rdoNoLimit.isSelected() ) {
            
            f.setViewLimited(true);
            
            if ( f.setFilterOnPlantingMethod( ! rdoDSAndTP.isSelected() ))
                f.setFilterMethodDirectSeed( rdoDS.isSelected() );
            
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
               f.setHarvestRangeStart( dlgHarvestRange.getStartDate() );
               f.setHarvestRangeEnd( dlgHarvestRange.getEndDate() ) ;
            }
            
        }

       f.changed();

    }
    
    private void fromFilter( CPSComplexPlantingFilter f ) {
        
        boolean y, n, a;
        y = n = a = false;
        
        if ( ! f.filterOnPlantingMethod() )
            a = true;
        else if ( f.filterMethodDirectSeed() )
            y = true;
        else 
            n = true;
        
        rdoDSAndTP.setSelected(a);
        rdoDS.setSelected(y);
        rdoTP.setSelected(n);
        
        if ( ! f.filterOnPlanting() )
            a = true;
        else if ( f.isDonePlanting() )
            y = true;
        else
            n = true;
        
        rdoPlantAll.setSelected(a);
        rdoPlantYes.setSelected(y);
        rdoPlantNo.setSelected(n);
        
        y = n = a = false;
        if ( ! f.filterOnTransplanting() )
            a = true;
        else if ( f.isDoneTransplanting() )
            y = true;
        else
            n = true;
        
        rdoTPAll.setSelected(a);
        rdoTPYes.setSelected(y);
        rdoTPNo.setSelected(n);
 
        
        y = n = a = false;
        if ( ! f.filterOnHarvest() )
            a = true;
        else if ( f.isDoneHarvesting() )
            y = true;
        else
            n = true;
            
        rdoHarvestAll.setSelected(a);
        rdoHarvestYes.setSelected(y);
        rdoHarvestNo.setSelected(n);
        
        if ( f.filterOnPlantingDate() ) {
            dlgPlantRange.setDateRangeSet(true);
            dlgPlantRange.setStartDate( f.getPlantingRangeStart() );
            dlgPlantRange.setEndDate( f.getPlantingRangeEnd() );
        }
        
        if ( f.filterOnTPDate() ) {
            dlgTPRange.setDateRangeSet(true);
            dlgTPRange.setStartDate( f.getTpRangeStart() );
            dlgTPRange.setEndDate( f.getTpRangeEnd() );
        }
        
        if ( f.filterOnHarvestDate() ) {
            dlgHarvestRange.setDateRangeSet(true);
            dlgHarvestRange.setStartDate( f.getHarvestRangeStart() );
            dlgHarvestRange.setEndDate( f.getHarvestRangeEnd() );
        }   
        
        if ( f.isViewLimited() )
            rdoLimit.doClick();
        else
            rdoNoLimit.doClick();
        
    }


    @Override
    protected void fillButtonPanel() {
       
        btnSetView = new JButton( "Set View" );
        btnCancel = new JButton( "Cancel" );
        btnSetView.addActionListener(this);
        btnCancel.addActionListener(this);
        addButton( btnSetView );
        addButton( btnCancel );
        
    }


    @Override
    protected void buildContentsPanel() {
        
        rdoNoLimit = new JRadioButton( "Do not limit; show everything.", false );
        rdoLimit = new JRadioButton( "<html>Limit view based upon the<br>following criteria:</html>", false );
        rdoNoLimit.setSelected(true);
        rdoNoLimit.addItemListener(this);
        rdoLimit.addItemListener(this);
        ButtonGroup bgLimit = new ButtonGroup();
        bgLimit.add( rdoNoLimit );
        bgLimit.add( rdoLimit );
        
        JPanel jplMethod = new JPanel( new MigLayout( migDefaults + ", align center", "[align right][]" ));
        jplMethod.setBorder( BorderFactory.createTitledBorder( "Planting Method" ));
        
        rdoDSAndTP = new JRadioButton( "all", true );
        rdoDS = new JRadioButton( "direct seeded", false );
        rdoTP = new JRadioButton( "transplanted", false );
        ButtonGroup bgMethod = new ButtonGroup();
        bgMethod.add( rdoDSAndTP );
        bgMethod.add( rdoDS );
        bgMethod.add( rdoTP );
        
        jplMethod.add( new JLabel( "Show only:"), "align right" );
        jplMethod.add( rdoDS, "wrap" );
        jplMethod.add( rdoTP, "skip 1, wrap" );
        jplMethod.add( rdoDSAndTP, "skip 1, wrap" );
        
        
        jplStatus = new JPanel( new MigLayout( migDefaults + ", align center", "[align right][align center]" ));
        jplStatus.setBorder( BorderFactory.createTitledBorder( "Status of Planting" ));
        
        jplStatus.add( new JLabel("Done"), "skip 1, aligny bottom" );
        jplStatus.add( new JLabel("<html><center>Not<br>Done</center></html>") );
        jplStatus.add( new JLabel("<html><center>Show<br>All</center></html>"), "wrap" );
        
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
        
        jplStatus.add( new JLabel( "Planting" ));
        jplStatus.add( rdoPlantYes );
        jplStatus.add( rdoPlantNo );
        jplStatus.add( rdoPlantAll, "wrap" );
        
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
        
        jplStatus.add( new JLabel( "Transplanting" ));
        jplStatus.add( rdoTPYes );
        jplStatus.add( rdoTPNo );
        jplStatus.add( rdoTPAll, "wrap" );
        
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
        
        jplStatus.add( new JLabel( "Harvest" ));
        jplStatus.add( rdoHarvestYes );
        jplStatus.add( rdoHarvestNo );
        jplStatus.add( rdoHarvestAll, "wrap" );
        
        
        jplDates = new JPanel( new MigLayout( migDefaults + ", align center", "[align right][align center]" ));
        jplDates.setBorder( BorderFactory.createTitledBorder( "Date Ranges" ));
        
        lblPlantDateRange = new JLabel( "Show all" );
        btnPlantDateRange = new JButton( "Set" );
        btnPlantDateRange.setActionCommand( btnPlantDateRange.getText() + "-Plant" );
        btnPlantDateRange.addActionListener(this);
        dlgPlantRange = new DateRangeDialog( btnPlantDateRange, "Planting date range" );

        jplDates.add( new JLabel( "Planting date:" ) );
        jplDates.add( lblPlantDateRange );
        jplDates.add( btnPlantDateRange, "wrap" );
        
        lblTPDateRange = new JLabel( "Show all" );
        btnTPDateRange = new JButton( "Set" );
        btnTPDateRange.setActionCommand( btnTPDateRange.getText() + "-TP" );
        btnTPDateRange.addActionListener(this);
        dlgTPRange = new DateRangeDialog( btnTPDateRange, "TP date range" );

        jplDates.add( new JLabel( "Transplant date:" ) );
        jplDates.add( lblTPDateRange );
        jplDates.add( btnTPDateRange, "wrap" );
        
        lblHarvestDateRange = new JLabel( "Show all" );
        btnHarvestDateRange = new JButton( "Set" );
        btnHarvestDateRange.setActionCommand( btnHarvestDateRange.getText() + "-Harvest" );
        btnHarvestDateRange.addActionListener(this);
        dlgHarvestRange = new DateRangeDialog( btnHarvestDateRange, "Harvest date range" );

        jplDates.add( new JLabel( "Harvest date:" ) );
        jplDates.add( lblHarvestDateRange );
        jplDates.add( btnHarvestDateRange, "wrap" );
        
        
        rdoNoLimit.setAlignmentX(Component.LEFT_ALIGNMENT);
        add( rdoNoLimit );
        rdoLimit.setAlignmentX(Component.LEFT_ALIGNMENT);
        add( rdoLimit );
        add( jplMethod );
        add( jplStatus );
        add( jplDates );


        setComponentsEnabled( false );
        contentsPanelBuilt = true;
        
    }
    
    private void setComponentsEnabled( boolean enable ) {
        rdoDSAndTP.setEnabled(enable);
        rdoDS.setEnabled(enable);
        rdoTP.setEnabled(enable);
        
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


    /* ***************************************************************************************** */
    /* Interface Methods */
    /* ***************************************************************************************** */

    public void actionPerformed( ActionEvent arg0 ) {
        String action = arg0.getActionCommand();
        
        if      ( action.equalsIgnoreCase( btnSetView.getText() )) {
            savedFilter = this.asFilter();
            setVisible(false);
        }
        else if ( action.equalsIgnoreCase( btnCancel.getText() )) {
            setVisible(false);
            this.fromFilter( savedFilter );
        }
        else if ( action.equalsIgnoreCase( btnPlantDateRange.getActionCommand() )) {
            dlgPlantRange.setVisible(true);
            if ( dlgPlantRange.isDateRangeSet() )
                this.lblPlantDateRange.setText( CPSDateValidator.format( dlgPlantRange.getStartDate() ) + " - " +
                                                CPSDateValidator.format( dlgPlantRange.getEndDate() ) );
            else
                this.lblPlantDateRange.setText( SHOW_ALL );
            this.pack();
        }
        else if ( action.equalsIgnoreCase( btnTPDateRange.getActionCommand() )) {
            dlgTPRange.setVisible(true);
            if ( dlgTPRange.isDateRangeSet() )
                this.lblTPDateRange.setText( CPSDateValidator.format( dlgTPRange.getStartDate() ) + " - " +
                                             CPSDateValidator.format( dlgTPRange.getEndDate() ) );
            else
                this.lblTPDateRange.setText( SHOW_ALL );
            this.pack();
        }
        else if ( action.equalsIgnoreCase( btnHarvestDateRange.getActionCommand() )) {
            dlgHarvestRange.setVisible(true);
            if ( dlgHarvestRange.isDateRangeSet() )
                this.lblHarvestDateRange.setText( CPSDateValidator.format( dlgHarvestRange.getStartDate() ) + " - " +
                                                  CPSDateValidator.format( dlgHarvestRange.getEndDate() ) );
            else
                this.lblHarvestDateRange.setText( SHOW_ALL );
            this.pack();
        }
        
    }
    
    public void itemStateChanged( ItemEvent arg0 ) {
        Object source = arg0.getSource();
        
        if      ( source == rdoNoLimit || source == rdoLimit ) {
            setComponentsEnabled( ! rdoNoLimit.isSelected() );
        }
        
    }


   // if the window is closed w/ the filter being "set", we should go ahead and "reset" in case
   // some of the buttons and what not have been altered
   public void windowClosed( WindowEvent e ) {
     if ( contentsPanelBuilt )
       fromFilter( savedFilter );
   }
   public void windowActivated( WindowEvent e ) { /* do nothing */ }
   public void windowClosing( WindowEvent e ) { /* do nothing */ }
   public void windowDeactivated( WindowEvent e ) { /* do nothing */ }
   public void windowDeiconified( WindowEvent e ) { /* do nothing */ }
   public void windowIconified( WindowEvent e ) { /* do nothing */ }
   public void windowOpened( WindowEvent e ) { /* do nothing */ }




    /* ***************************************************************************************** */
    /* Inner Class Def'n
    /* ***************************************************************************************** */
    public class DateRangeDialog extends CPSDialog
                                 implements ItemListener,
                                            ActionListener,
                                            PropertyChangeListener {
        
        private Date startDate, endDate;
        private boolean dateRangeSet = false;
        private JRadioButton rdoAllDates, rdoLimitDates;
        private JDateChooser startDateChooser, endDateChooser;
        private JButton btnSetRange, btnCancel;
        
        public DateRangeDialog( Component parent, String title ) {
            
            super( parent, title );
            
            setDateRangeSet( false );
                        
        }

        
        public boolean isDateRangeSet() { return dateRangeSet; }
        private void setDateRangeSet( boolean dateRangeSet ) { this.dateRangeSet = dateRangeSet; }

        public void setEndDate( Date endDate ) { this.endDate = endDate; }
        private Date getEndDate() { return endDate; }

        public void setStartDate( Date startDate ) { this.startDate = startDate; }
        private Date getStartDate() { return startDate; }

        protected void fillButtonPanel() {
            btnSetRange = new JButton( "Set Range" );
            btnCancel = new JButton( "Cancel" );
            btnSetRange.addActionListener( this );
            btnCancel.addActionListener( this );
            addButton( btnSetRange );
            addButton( btnCancel );
        }
        
        protected void buildContentsPanel() {
            
            rdoAllDates = new JRadioButton( "Display all dates.", false );
            rdoLimitDates = new JRadioButton( "Limit view to the following date range:", false );
            rdoAllDates.addItemListener(this);
            rdoLimitDates.addItemListener(this);
            ButtonGroup bg = new ButtonGroup();
            bg.add( rdoAllDates );
            bg.add( rdoLimitDates );
            
            startDateChooser = new JDateChooser();
            endDateChooser = new JDateChooser();
            startDateChooser.addPropertyChangeListener(this);
            endDateChooser.addPropertyChangeListener(this);
            
            JPanel jp = new JPanel( new MigLayout( migDefaults ));
            
            jp.add( rdoAllDates, "wrap, span 2" );
            jp.add( rdoLimitDates, "wrap, span 2" );
            
            jp.add( new JLabel( "Show dates after" ), "align right" );
            jp.add( startDateChooser, "wrap" );
            
            jp.add( new JLabel( "Show dates before" ), "align right" );
            jp.add( endDateChooser, "wrap" );

            contentsPanelBuilt = true;
            rdoAllDates.setSelected(true);
            add(jp);
            
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
                // TODO revert to initial state
            }
        
        }

        public void propertyChange( PropertyChangeEvent arg0 ) {
            
            if      ( arg0.getSource() == startDateChooser ) {
                endDateChooser.setMinSelectableDate( startDateChooser.getDate() );
            }
            else if ( arg0.getSource() == endDateChooser ) {
                startDateChooser.setMaxSelectableDate( endDateChooser.getDate() );
            }
            
        }
        
    }
    
    // For testing.
    public static void main(String[] args) {        
        new CPSComplexFilterDialog( new JPanel() ).setVisible(true);
       System.exit( 0 );
    }
    
}
