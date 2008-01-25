/*
 * CropDBCropList.java
 *
 * Created on March 14, 2007, 1:03 PM
 */

package CPS.Core.CropPlans;

import CPS.Data.CPSCrop;
import CPS.Data.CPSRecord;
import CPS.UI.Modules.CPSMasterView;
import CPS.Module.*;
import CPS.Data.CPSPlanting;
import CPS.UI.Modules.CPSMasterDetailModule;
import CPS.UI.Swing.autocomplete.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.table.*;

// package access
class CropPlanList extends CPSMasterView implements ActionListener {
   
    private JLabel lblPlanName;
    private JComboBox cmbxPlanList, cmbxCropList;
    private JButton btnNewPlan;
    
    private ArrayList<String> listOfValidCropPlans, listOfValidCrops;
    
    public CropPlanList( CPSMasterDetailModule mdm ) {
        super(mdm);
        setSortColumn("date_plant");
    }
    
    /** 
     * Retrieve the details (as a Planting) for a given ID 
     * @param id The integer id of the planting to retrieve.
     * @return The CPSPlanting object representing the retrieved record.
     */
    protected CPSPlanting getDetailsForID( int id ) {
       return getDataSource().getPlanting( getDisplayedTableName(), id );
    }

    protected CPSPlanting getDetailsForIDs( ArrayList<Integer> ids ) {
       return getDataSource().getCommonInfoForPlantings( getDisplayedTableName(), ids );
    }
    
   
    public void setDataSource( CPSDataModel dm ) {
        super.setDataSource(dm);
        updateListOfPlans();
        updateListOfCrops();
   }
    
    
    /**
     * Populates the crop name combobox with the list of valid crop names from CropDB.
     */
    protected void updateListOfCrops() {
       if ( ! isDataAvailable() )
          return;
       
       listOfValidCrops = getDataSource().getCropNames();
       Collections.sort( listOfValidCrops, String.CASE_INSENSITIVE_ORDER);
       cmbxCropList.removeAllItems();
       cmbxCropList.addItem("");
       for ( String s : listOfValidCrops )
          cmbxCropList.addItem(s);
       
    }
    
    protected void updateListOfPlans() {
       if ( ! isDataAvailable() )
          return;
       
       String selected = getSelectedPlanName();
       listOfValidCropPlans = getDataSource().getListOfCropPlans();
       cmbxPlanList.removeAllItems();
       for ( String s : listOfValidCropPlans ) {
          // TODO think about this; possibly remove COMMON_PLANTINGS from list returned by
          // getListOfCropPlans()
          if ( s.equalsIgnoreCase("common_plantings") )
             continue;
          cmbxPlanList.addItem(s);
       }
       if ( selected != null && !selected.equals( "" ) )
          cmbxPlanList.setSelectedItem( selected );
       
       super.dataUpdated();
    }
    
    protected void updateMasterList() {
       super.updateMasterList();

       // find column "crop_name" and set it to use a combobox editor
       for ( int i = 0 ; i < masterTable.getColumnModel().getColumnCount() ; i++ )
          if ( masterTable.getColumnName(i).equalsIgnoreCase("crop_name") )
             break;
       // TODO: continue { (crop_name never found) i = -1 }
       int cropColNum = 0;
       if ( masterTable.getRowCount() > 0 ) // TODO && i != -1
          masterTable.getColumnModel().getColumn(cropColNum)
                     .setCellEditor( new ComboBoxCellEditor( cmbxCropList ) );
        
    }
    protected TableModel getMasterListData() {
        if ( !isDataAvailable() )
            return new DefaultTableModel();
         
        String selectedPlan = getSelectedPlanName();
        System.out.println( "Selected plan is: " + selectedPlan );
       
        if ( selectedPlan != null && listOfValidCropPlans.contains( selectedPlan ) )
            return getDataSource().getCropPlan( selectedPlan, getDisplayedColumnList(), getSortColumn(), getFilterString() );
//            return getDataSource().getCropPlan( selectedPlan, getSortColumn(), getFilterString() );
       else
          // TODO error checking fall through to following call when invalid plan is selected
          return new DefaultTableModel();
       
    }
    
    protected String getDisplayedTableName() { return (String) cmbxPlanList.getSelectedItem(); }
    String getSelectedPlanName() { return getDisplayedTableName(); }
    
    protected void buildAboveListPanel() {
        initAboveListPanel();
                
        lblPlanName = new JLabel( "Plan Name:");
        btnNewPlan = new JButton( "New" );
        btnNewPlan.setActionCommand( "NewPlan" );
        btnNewPlan.setMargin( new Insets( 1, 1, 1, 1 ) );
        btnNewPlan.addActionListener( this );
        cmbxPlanList = new JComboBox();
        cmbxPlanList.setEditable( true );
        cmbxPlanList.addActionListener( new CropPlanBoxActionListener() );
       
        jplAboveList.add( lblPlanName );
        jplAboveList.add( cmbxPlanList );
        jplAboveList.add( btnNewPlan );
        
        // false ==> do not initialize panel
        super.buildAboveListPanel(false);
    }
    
    protected void buildListPanel() {
       super.buildListPanel();
       
       cmbxCropList = new JComboBox();
       cmbxCropList.addActionListener( new CropBoxInTableActionListener() );
       AutoCompleteDecorator.decorate( cmbxCropList );
       
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();

        System.out.println("DEBUG Action performed in CropPlanList: " + action);

       if ( action.equalsIgnoreCase( btnNewPlan.getActionCommand() ) ) {
            if ( ! isDataAvailable() ) {
                System.err.println("ERROR: cannot create new plan, no data available" );
                return;
            }
            createNewCropPlan( getSelectedPlanName() );
        }
        else
            super.actionPerformed( actionEvent );    
        
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

         System.out.println( "DEBUG Action registered in CropBoxInTable: " + action );

         if ( action.equalsIgnoreCase( "comboBoxEdited" ) ) {
         // What do we do here? create a new crop?
         }
         else if ( action.equalsIgnoreCase( "comboBoxChanged" ) ) {
            String s = (String) cmbxCropList.getSelectedItem();
            // update mesh this planting w/ the crop selected, but for now just ...
            System.out.println( "Crop name: selected " + s );
            if ( listOfValidCrops.contains( s ) ) {
               CPSCrop crop = getDataSource().getCropInfo( (String) cmbxCropList.getSelectedItem() );
               System.out.println( "Retrieved info for crop " + crop.getCropName() );
            }
            else {
               // TODO handle selection of non-existing crop
               System.err.println("ERROR: Selected non existent crop, what to do?  Create?  For now, nothing." );
            }
         }
       }   
    }
    
    public void createNewCropPlan( String newPlanName ) {
        if ( newPlanName.equalsIgnoreCase( "" ) )
            System.err.println( "Cannot create crop plan with no name" );
        getDataSource().createCropPlan( newPlanName );
        updateListOfPlans();
    }
    
    @Override
    public CPSPlanting createNewRecord() {
       if ( getSelectedPlanName().equals("") ) {
          System.err.println("ERROR cannot create record unless a crop plan is selected");
          return null;
       }
       return getDataSource().createPlanting( getSelectedPlanName(), new CPSPlanting() );
    }
    
    @Override
    public CPSRecord duplicateRecord( int id ) {
        return getDataSource().createPlanting( getSelectedPlanName(),
                                         getDataSource().getPlanting( getSelectedPlanName(),
                                                                id ) );     
    }
    
    @Override
    public void deleteRecord( int id ) {
        getDataSource().deletePlanting( getSelectedPlanName(), id );
    }
    
   @Override
   protected ArrayList<String> getDisplayableColumnList() {
      return getDataSource().getPlantingDisplayableColumns();
   }
   
   @Override
   protected ArrayList<String> getDefaultDisplayableColumnList() {
      ArrayList<String> l = new ArrayList();
      // crop_name is implicit and MANDATORY in dataModel
      l.add( "var_name" );
      l.add( "maturity" );
      l.add( "date_plant" );
      l.add( "date_harvest" );
      l.add( "completed" );
      l.add( "location" );
      
      return l;
   }
    
}
