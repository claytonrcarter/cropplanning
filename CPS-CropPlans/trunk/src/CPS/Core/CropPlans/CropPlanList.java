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
import CPS.UI.Swing.autocomplete.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

// package access
class CropPlanList extends CPSMasterView implements ActionListener {
   
    private JLabel lblPlanName;
    private JComboBox cmbxPlanList, cmbxCropList;
    private JButton btnNewPlan;
    
    private ArrayList<String> listOfValidCropPlans, listOfValidCrops;
    
    public CropPlanList( CropPlanUI ui ) {
        super(ui);
    }
    
    // Retrieve the details (as a Planting) for a given ID
    protected CPSPlanting getDetailsForID( int id ) {
//       CPSPlanting p = dataModel.getPlanting( getDisplayedTableName(), id );
//       CPSCrop c = dataModel.getCropInfo( p.getCropName() );
//       return (CPSPlanting) p.inheritFrom( c );
       return dataModel.getPlanting( getDisplayedTableName(), id );
    }

    protected void updateListOfCrops() {
       if ( ! isDataAvailable() )
          return;
       
       listOfValidCrops = dataModel.getListOfCrops();
       cmbxCropList.removeAllItems();
       cmbxCropList.addItem("");
       for ( String s : listOfValidCrops )
          cmbxCropList.addItem(s);
       
    }
    
    protected void updateListOfPlans() {
       if ( ! isDataAvailable() )
          return;
       
       String selected = getSelectedPlanName();
       listOfValidCropPlans = dataModel.getListOfCropPlans();
       cmbxPlanList.removeAllItems();
       for ( String s : listOfValidCropPlans ) {
          // TODO think about this; possibly remove COMMON_PLANTINGS from list returned by
          // getListOfCropPlans()
          if ( s.equalsIgnoreCase("common_plantings") )
             continue;
          cmbxPlanList.addItem(s);
       }
        if ( selected != null && ! selected.equals( "" ) )
            cmbxPlanList.setSelectedItem(selected);
       
       refreshView();
    }
    
    protected void updateMasterList() {
       super.updateMasterList();

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
            return dataModel.getCropPlan( selectedPlan, getSortColumn(), getFilterString() );
       else
          // TODO error checking fall through to following call when invalid plan is selected
          return new DefaultTableModel();
       
    }
    
    protected String getDisplayedTableName() { return getSelectedPlanName(); }
    String getSelectedPlanName() { return (String) cmbxPlanList.getSelectedItem(); }
   
    public void setDataSource( CPSDataModel dm ) {
        super.setDataSource(dm);
        updateListOfPlans();
        updateListOfCrops();
    }
    
    protected void buildAboveListPanel() {
        initAboveListPanel();
                
        lblPlanName = new JLabel( "Plan Name:");
        btnNewPlan = new JButton( "New" );
        btnNewPlan.setActionCommand( "NewPlan" );
        btnNewPlan.setMargin( new Insets( 1, 1, 1, 1 ) );
        btnNewPlan.addActionListener( new CropPlanBoxActionListener() );
        cmbxPlanList = new JComboBox();
        cmbxPlanList.setEditable( true );
        cmbxPlanList.addActionListener( this );
       
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
               CPSCrop crop = dataModel.getCropInfo( (String) cmbxCropList.getSelectedItem() );
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
        dataModel.createCropPlan( newPlanName );
        updateListOfPlans();
    }
    
    @Override
    public CPSPlanting createNewRecord() {
       if ( getSelectedPlanName().equals("") ) {
          System.err.println("ERROR cannot create record unless a crop plan is selected");
          return null;
       }
       return dataModel.createPlanting( getSelectedPlanName(), new CPSPlanting() );
    }
    
    @Override
    public CPSRecord duplicateRecord( int id ) {
        return dataModel.createPlanting( getSelectedPlanName(),
                                         dataModel.getPlanting( getSelectedPlanName(),
                                                                id ) );     
    }
    
    @Override
    public void deleteRecord( int id ) {
        dataModel.deletePlanting( getSelectedPlanName(), id );
    }
    
}
