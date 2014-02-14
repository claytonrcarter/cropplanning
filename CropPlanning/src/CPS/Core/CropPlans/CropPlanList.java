/* CropDBCropList.java - Created: March 14, 2007
 * Copyright (C) 2007, 2008 Clayton Carter
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
 */

package CPS.Core.CropPlans;

import CPS.Data.*;
import CPS.UI.Modules.CPSMasterView;
import CPS.Module.*;
import CPS.UI.Modules.CPSAdvancedTableFormat;
import CPS.UI.Modules.CPSMasterDetailModule;
import CPS.UI.Swing.CPSComplexFilterDialog;
import CPS.UI.Swing.CPSErrorDialog;
import CPS.UI.Swing.CPSTable.CPSComboBoxCellEditor;
import CPS.UI.Swing.autocomplete.*;
import ca.odell.glazedlists.FunctionList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.calculation.Calculation;
import ca.odell.glazedlists.calculation.Calculations;
import ca.odell.glazedlists.event.ListEvent;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

// package access
class CropPlanList extends CPSMasterView implements ActionListener,
                                                    ItemListener,
                                                    PropertyChangeListener {
   
   protected static final String KEY_FILTER = "LAST_FILTER";
   
    private JLabel lblPlanName;
//    private JComboBox cmbxPlanList;
    private PlanManager planMan;
    private JComboBox cmbxCropList, cmbxFieldList;
    private JButton btnChangePlans;
    
    private JComboBox cmbxLimit;
    protected static final String LIMIT_ALL       = "all";
    protected static final String LIMIT_ALL_UNP   = "all: not planted";
    protected static final String LIMIT_THIS_WEEK = "all: this week";
    protected static final String LIMIT_NEXT_WEEK = "all: next week";
    protected static final String LIMIT_THIS_NEXT = "all: this and next week";
    protected static final String LIMIT_DS        = "just DS";
    protected static final String LIMIT_DS_UNP    = "just DS: not planted";
    protected static final String LIMIT_TP        = "just TP";
    protected static final String LIMIT_TP_NOT_S  = "just TP: not seeded";
    protected static final String LIMIT_TP_NOT_P  = "just TP: seeded, not planted";
    protected static final String LIMIT_CUSTOM    = "custom..."; // TODO, this is a terrible name
    private CPSComplexFilterDialog dlgFilter;

    private CPSComplexPlantingFilter plantingFilter;

    private List<String> listOfValidCrops, listOfFields;

    private Calculation<Integer> summaryPlantings = null;
    private Calculation<Integer> summaryRowFt = null;
    private Calculation<Float> summaryBeds = null, summaryFlats = null;

    
    public CropPlanList( CPSMasterDetailModule mdm ) {
        super(mdm);
        plantingFilter = new CPSComplexPlantingFilter();
        addFilter( plantingFilter );
        dlgFilter = new CPSComplexFilterDialog( getAboveListPanel() );
        planMan = new PlanManager( getAboveListPanel() );
    }
    
    
   @Override
    public void setDataSource( CPSDataModel dm ) {
       
      super.setDataSource(dm);
      
      planMan.setDataModel(dm);
      String plan = getPrefs().get( KEY_DISPLAYED_TABLE, "" );
      if ( !plan.equals( "" ) ) {
        CPSModule.debug( "CPList", "Found last displayed table:" + plan );
        planMan.selectPlan( plan );
      }

      cmbxLimit.setSelectedItem( getPrefs().get( KEY_FILTER, LIMIT_ALL ) );
      
      dataUpdated();
      
      updateListOfCrops();
      updateListOfFieldNames();


      // setup for the summary string


      FunctionList<CPSRecord, Float> bedsList =
              new FunctionList<CPSRecord, Float>( masterListFiltered,
                                                  new FunctionList.Function<CPSRecord, Float>() {
                                                     public Float evaluate( CPSRecord p ) {
                                                        return ( (CPSPlanting) p ).getBedsToPlant();
                                                     }} );

      FunctionList<CPSRecord, Float> flatsList =
              new FunctionList<CPSRecord, Float>( masterListFiltered,
                                                  new FunctionList.Function<CPSRecord, Float>() {
                                                     public Float evaluate( CPSRecord p ) {
                                                        return ( (CPSPlanting) p ).getFlatsNeeded();
                                                     }} );

      FunctionList<CPSRecord, Integer> rftList =
              new FunctionList<CPSRecord, Integer>( masterListFiltered,
                                                 new FunctionList.Function<CPSRecord, Integer>() {
                                                    public Integer evaluate( CPSRecord p ) {
                                                       return ( (CPSPlanting) p ).getRowFtToPlant();
                                                    }} );

      summaryPlantings = Calculations.count( masterListFiltered );
      summaryBeds = Calculations.sumFloats( bedsList );
      summaryFlats = Calculations.sumFloats( flatsList );
      summaryRowFt = Calculations.sumIntegers( rftList );

      // this propertyChangeListener will notice when this calculation is updated
      // and will fire the property change to update the stat string
      summaryRowFt.addPropertyChangeListener( this );
      updateStatisticsLabel();

   }

   @Override
   protected int saveState() {
      
      String selectedFilter = (String) cmbxLimit.getSelectedItem();
      if ( ! selectedFilter.equals( LIMIT_CUSTOM ))
         getPrefs().put( KEY_FILTER, selectedFilter );
         
      return super.saveState();
   }

   @Override
   protected int getTypeOfDisplayedRecord() {
      return CPSDataModelConstants.RECORD_TYPE_PLANTING;
   }
   
    
    protected CPSPlanting getDetailsForIDs( List<Integer> ids ) {
       return getDataSource().getCommonInfoForPlantings( getDisplayedTableName(), ids );
    }
    
    
    
    /**
     * Populates the crop name combobox with the list of valid crop names from CropDB.
     */
    protected void updateListOfCrops() {
       if ( ! isDataAvailable() )
          return;
       
       listOfValidCrops = getDataSource().getCropNameList();
       Collections.sort( listOfValidCrops, String.CASE_INSENSITIVE_ORDER);
       cmbxCropList.removeAllItems();
       cmbxCropList.addItem("");
       for ( String s : listOfValidCrops )
          cmbxCropList.addItem(s);
       
    }
    
    protected void updateListOfFieldNames() {
       if ( ! isDataAvailable() )
          return;
       
        listOfFields = getDataSource().getFieldNameList( getDisplayedTableName() );
       Collections.sort( listOfFields, String.CASE_INSENSITIVE_ORDER);
       cmbxFieldList.removeAllItems();
       cmbxFieldList.addItem("");
       for ( String s : listOfFields )
          cmbxFieldList.addItem(s);
        
    }


    protected List getMasterListData() {
        if ( ! isDataAvailable() ||
             ! getDataSource().cropPlanExists( getDisplayedTableName() ))
            return new ArrayList<CPSPlanting>();

        String selectedPlan = getDisplayedTableName();
        if ( selectedPlan != null )
            return getDataSource().getCropPlan( selectedPlan );
       else
          // TODO error checking fall through to following call when invalid plan is selected
          return new ArrayList();

    }

    @Override
    protected CPSAdvancedTableFormat getTableFormat() {
        return new CropPlanTableFormat();
    }


    @Override
    protected TextFilterator getTextFilterator() {
        return new CropPlanFilterator();
    }
       
    
    protected String getDisplayedTableName() { return planMan.getSelectedPlanName(); }

    
    @Override
    protected void buildAboveListPanel() {
        initAboveListPanel();
                
        lblPlanName = new JLabel( "Plan Name: " );
        btnChangePlans = new JButton( "Change Plans" );
        btnChangePlans.setActionCommand( "ChangePlans" );
        btnChangePlans.setMargin( new Insets( 1, 5, 1, 5 ) );
        btnChangePlans.addActionListener( this );
       
        jplAboveList.add( lblPlanName );
        jplAboveList.add( btnChangePlans );
        
        // false ==> do not initialize panel
        super.buildAboveListPanel(false);
    }

    @Override
    protected void buildListPanel() {
       super.buildListPanel();
       
       cmbxCropList = new JComboBox();
       cmbxCropList.addActionListener( new CropBoxInTableActionListener() );
       AutoCompleteDecorator.decorate( cmbxCropList );
       
       cmbxFieldList = new JComboBox();
       cmbxFieldList.setEditable(true);
       AutoCompleteDecorator.decorate(cmbxFieldList);
       


    }

    @Override
    protected JPanel buildFilterComponent() {
        initFilterPanel();
        
//        btnLimit = new JButton( "Limit View" );
//        btnLimit.setMargin( new Insets( 1, 1, 1, 1 ));
//        btnLimit.addActionListener(this);
        
        cmbxLimit = new JComboBox();
        cmbxLimit.setMaximumRowCount(15);
        cmbxLimit.addItem( LIMIT_ALL );
        cmbxLimit.addItem( LIMIT_ALL_UNP );
//        cmbxLimit.addItem( LIMIT_THIS_WEEK );
//        cmbxLimit.addItem( LIMIT_NEXT_WEEK );
//        cmbxLimit.addItem( LIMIT_THIS_NEXT );
        cmbxLimit.addItem( LIMIT_DS );
        cmbxLimit.addItem( LIMIT_DS_UNP );
        cmbxLimit.addItem( LIMIT_TP );
        cmbxLimit.addItem( LIMIT_TP_NOT_S  );
        cmbxLimit.addItem( LIMIT_TP_NOT_P  );
        cmbxLimit.addItem( LIMIT_CUSTOM );
        cmbxLimit.setMaximumSize( cmbxLimit.getPreferredSize() );
        cmbxLimit.addItemListener(this);
        cmbxLimit.addActionListener(this);
                
        jplFilter.add( new JLabel( "Show" ));
//        jplFilter.add( btnLimit );
        jplFilter.add( cmbxLimit );
        jplFilter.add( new JLabel( "matching"));
        
        return super.buildFilterComponent( false );
    }
    
    public class CropPlanBoxActionListener implements ActionListener {
      public void actionPerformed( ActionEvent actionEvent ) {
         String action = actionEvent.getActionCommand();

         System.out.println( "DEBUG Action performed in CropPlanList: " + action );

         if ( action.equalsIgnoreCase( "comboBoxEdited" ) ) {
         // What do we do here? create a new plan?
         }
         else if ( action.equalsIgnoreCase( "comboBoxChanged" ) ) {
            updateMasterList();
         }
       }   
    }
    public class CropBoxInTableActionListener implements ActionListener {
      public void actionPerformed( ActionEvent actionEvent ) {
         String action = actionEvent.getActionCommand();

         CPSModule.debug( "CropBoxInTable", "Action registered: " + action );

         if ( action.equalsIgnoreCase( "comboBoxEdited" ) ) {
         // What do we do here? create a new crop?
         }
         else if ( action.equalsIgnoreCase( "comboBoxChanged" ) ) {
            String s = (String) cmbxCropList.getSelectedItem();
            // update mesh this planting w/ the crop selected, but for now just ...
//            CPSModule.debug( "CropBoxInTable", "Crop name: selected " + s );
            if ( listOfValidCrops.contains( s ) ) {
               CPSCrop crop = getDataSource().getCropInfo( (String) cmbxCropList.getSelectedItem() );
//               CPSModule.debug( "CropBoxInTable", "Retrieved info for crop " + crop.getCropName() );
            }
            else {
               // TODO handle selection of non-existing crop
               CPSModule.debug( "CropBoxInTable", "ERROR: Selected non existent crop, what to do?  Create?  For now, nothing." );
            }
         }
       }   
    }


    @Override
    public CPSPlanting getBlankRecord() {
      return new CPSPlanting();
    }

    @Override
    protected void updateRecordInDB( CPSRecord r ) {
      if ( r instanceof CPSPlanting )
        getDataSource().updatePlanting( getDisplayedTableName(), (CPSPlanting) r );
    }

    @Override
    protected CPSPlanting createNewRecord( CPSRecord r ) {
       if ( getDisplayedTableName().equals("") ) {
        new CPSErrorDialog( this.getMainPanel(),
                   "Cannot Create Entry",
                   "<center>We can only create new planting entries<br>" +
                   "after you have selected a crop plan.<br>" +
                   "<b>No record created." ).setVisible( true );
          return null;
       }
       CPSPlanting p = getDataSource().createPlanting( getDisplayedTableName(), (CPSPlanting) r );
       return (CPSPlanting) super.createNewRecord(p);
    }
    
    @Override
    public void deleteRecord( int id ) {
      super.deleteRecord(id);
      getDataSource().deletePlanting( getDisplayedTableName(), id );
    }
    
   @Override
   protected List<String> getDisplayableColumnList() {
      return getDataSource().getPlantingDisplayablePropertyNames();
   }
   
   @Override
   protected List<Integer> getDefaultDisplayableColumnList() {
      return getDataSource().getPlantingDefaultProperties();
   }
   
   protected List<String[]> getColumnPrettyNameMap() {
       return getDataSource().getPlantingPrettyNames();
   }

    @Override
    protected String getTableStatisticsString() {
        if ( ! isDataAvailable() || getDisplayedTableName() == null )
            return "";

       String s = "";
       
       if ( summaryPlantings != null && summaryPlantings.getValue() > 0 ) {
           s += "Plantings:" + summaryPlantings.getValue();

           String t = "" + CPSCalculations.precision3( summaryBeds.getValue() + .001f );
           if ( ! t.equals("") ) 
               s += "/Beds:" + t;
           
           t = summaryRowFt.getValue().toString();
           if ( ! t.equals("") ) {
             if ( CPSGlobalSettings.useMetric() )
               s += "/RowMeters:" + t;
             else
               s += "/RowFeet:" + t;
           }
//       s += "/Plants:" + p.getPlantsNeededString();
           
           t = summaryFlats.getValue().toString();
           if ( ! t.equals("") )
               s += "/Flats:" + t;
           
//           t = p.getTotalYieldString();
//           if ( ! t.equals("") )
//               s += "/Yield:" + t;
       }

       return s;
    }
    
    protected void updateSelectedPlanLabel() {
       String plan = getDisplayedTableName();
       
       if ( plan == null )
          plan = "No Plan Selected!";
       
       lblPlanName.setText( "Plan Name: " + plan + "  " );
    }
    
    @Override
    public void dataUpdated() {
        super.dataUpdated();
        
        updateSelectedPlanLabel();
        
        if ( getDisplayedTableName() == null ) {
            setStatus( CPSMasterView.STATUS_NO_PLAN_SELECTED );
            enableRecordButtons(false);
        }
        else {
            updateListOfFieldNames();
            enableRecordButtons(true);
        }
        
        
    }

    protected void updateAutocompletionComponents() {
       updateListOfCrops();
       updateListOfFieldNames();
   }
    
    
    public void itemStateChanged( ItemEvent arg0 ) {
        Object source = arg0.getSource();
        
        if ( source == cmbxLimit ) {
            CPSModule.debug( "CropPlanList", "view limit combobox changed to: " + cmbxLimit.getSelectedItem() );
            String s = (String) cmbxLimit.getSelectedItem();
            if      ( s.equalsIgnoreCase( LIMIT_ALL_UNP ))
               plantingFilter.setAsAllNotPlantedFilter();
            else if ( s.equalsIgnoreCase( LIMIT_THIS_WEEK ))
               plantingFilter.setAsThisWeekFilter();
            else if ( s.equalsIgnoreCase( LIMIT_NEXT_WEEK ))
               plantingFilter.setAsNextWeekFilter();
            else if ( s.equalsIgnoreCase( LIMIT_THIS_NEXT ))
               plantingFilter.setAsThisAndNextWeekFilter();
            else if ( s.equalsIgnoreCase( LIMIT_DS ))
               plantingFilter.setAsDirectSeededFilter();
            else if ( s.equalsIgnoreCase( LIMIT_DS_UNP ))
               plantingFilter.setAsDSNotPlantedFilter();
            else if ( s.equalsIgnoreCase( LIMIT_TP ))
               plantingFilter.setAsTransplatedFilter();
            else if ( s.equalsIgnoreCase( LIMIT_TP_NOT_S  ))
               plantingFilter.setAsTPNotSeededFilter();
            else if ( s.equalsIgnoreCase( LIMIT_TP_NOT_P  ))
               plantingFilter.setAsTPSeededNotPlantedFilter();
            else {
               plantingFilter.reset();
               if ( s.equalsIgnoreCase( LIMIT_ALL ))
                  plantingFilter.changed();
            }
            updateFilter();
        }
    }
    
    
   @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();

       if ( action.equalsIgnoreCase( btnChangePlans.getActionCommand() ) ) {
            if ( ! isDataAvailable() ) {
                CPSModule.debug( "CropPlanList", "ERROR: cannot create new plan, no data available" );
                return;
            }
            String s = planMan.getSelectedPlanName();
            planMan.setVisible(true);
            dataUpdated();
        }
       else if ( actionEvent.getSource() == cmbxLimit &&
                ((String) cmbxLimit.getSelectedItem()).equalsIgnoreCase( LIMIT_CUSTOM ) ) {
           dlgFilter.setVisible( true );
           // dialog is modal, wait for it to return
           if ( dlgFilter.getFilter().isViewLimited() ) {
               dlgFilter.updateFilter( plantingFilter );
           }
           else
               cmbxLimit.setSelectedItem( LIMIT_ALL );
       }
        else
            super.actionPerformed( actionEvent );    
        
    }

   // for addPropertyChangeListener
   public void propertyChange( PropertyChangeEvent evt ) {
      updateStatisticsLabel();
   }
   
}
