/*
 * CropDBCropInfo.java
 *
 * Created on March 15, 2007, 11:26 AM by Clayton
 */

package CPS.Core.CropPlans;

import CPS.UI.Swing.LayoutAssist;
import CPS.Data.CPSPlanting;
import CPS.Module.*;
import CPS.UI.Swing.CPSTextArea;
import CPS.UI.Swing.CPSTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CropPlanInfo extends CPSDataModelUser implements ActionListener {
   
   private JPanel mainPanel, cropInfoPanel;
   
   private JPanel buttonPanel;
   private JLabel lblChanges;
   private JButton btnSaveChanges, btnDiscardChanges;
   
   private boolean noItemSelected = true;
   
   private CPSTextField tfldCropName, tfldVarName;
   private CPSTextField tfldDatePlant, tfldDateTP, tfldDateHarvest;
   private CPSTextField tfldMatDays;
   private CPSTextField tfldBedsToPlant, tfldRowFtToPlant, tfldPlantsNeeded,
                      tfldPlantsToStart, tfldFlatsNeeded;
   private CPSTextArea tareGroups, tareKeywords, tareOtherReq, tareNotes;
   private CPSTextField tfldLocation, tfldStatus;
   
// yet to implement:
   //          case PROP_COMPLETED:     return completed; 
           
   private CPSPlanting displayedPlanting;
   
   private CropPlanUI uiManager;
   
   CropPlanInfo( CropPlanUI ui ) {
      uiManager = ui;
      buildMainPanel();
      buildCropInfoPanel();
      buildButtonPanel();
   }
   
   private void buildMainPanel() {
      /*
       * Create the crop info panel
       */
      mainPanel = new JPanel();
      mainPanel.setBorder( BorderFactory.createTitledBorder( "Planting Info" ));
      mainPanel.setLayout( new BorderLayout() );
      
      JLabel lblNoItemSelected = new JLabel( "Select item from table to display detailed information." );
      
      mainPanel.add( lblNoItemSelected, BorderLayout.CENTER );
      
   }
   
   private void buildButtonPanel() {
      
      Insets small = new Insets( 1, 1, 1, 1 );
      
      lblChanges = new JLabel( "Changes: " ); 
      btnSaveChanges = new JButton( "Save" );
      btnDiscardChanges = new JButton( "Discard" );
      btnSaveChanges.addActionListener( this );
      btnDiscardChanges.addActionListener( this );
      btnSaveChanges.setMargin( small );
      btnDiscardChanges.setMargin( small );
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.LINE_AXIS ) );
      
      buttonPanel.add( Box.createHorizontalGlue() );
      buttonPanel.add( lblChanges );
      buttonPanel.add( btnSaveChanges );
      buttonPanel.add( btnDiscardChanges );
      
   }
   
   /* this should be renamed, perhaps: build plantingInfoPanel? */
   public void displayPlantingInfo() {
      mainPanel.removeAll();
      mainPanel.add( cropInfoPanel, BorderLayout.CENTER );
      mainPanel.add( buttonPanel, BorderLayout.PAGE_END );
      mainPanel.revalidate();
      uiManager.revalidate();
   }
   
   public JPanel getJPanel() {
      return mainPanel;
   }

   // add new crop data here
   public void displayPlanting( CPSPlanting p ) {
      
      displayedPlanting = p;
      
      if ( noItemSelected ) {
         displayPlantingInfo();
         noItemSelected = false;
      }
      
      tfldCropName.setInitialText( displayedPlanting.getCropName() );
      tfldVarName.setInitialText( displayedPlanting.getVarietyName() );
      
      if ( displayedPlanting.getMaturityDays() > 0 )
         tfldMatDays.setInitialText( "" + displayedPlanting.getMaturityDays() );
      else
         tfldMatDays.setInitialText("");
      
      tfldDatePlant.setInitialText( displayedPlanting.getDateToPlantString() );
      tfldDateHarvest.setInitialText( displayedPlanting.getDateToHarvestString() );
      
      tareGroups.setText( displayedPlanting.getGroups() );
      tareOtherReq.setText( displayedPlanting.getOtherRequirments() );
      tareKeywords.setText( displayedPlanting.getKeywords() );
      tareNotes.setText( displayedPlanting.getNotes() );
      
   }
   
   // add new crop data here
   public CPSPlanting asPlanting() {
      
      CPSPlanting p = new CPSPlanting();
      
      p.setID( displayedPlanting.getID() );
      
      p.setCropName( tfldCropName.getText() );
      p.setVarietyName( tfldVarName.getText() );
      
      p.setMaturityDays( tfldMatDays.getText() );
     
      p.setDateToPlant( tfldDatePlant.getText() );
      if ( tfldDateHarvest.hasChanged() ) {
          System.out.println("Harvest date has changed to: '" + tfldDateHarvest.getText() + "'" );
          p.addChangedProperty( p.PROP_DATE_HARVEST );
          p.setDateToHarvest(tfldDateHarvest.getText());
      }
 
      p.setGroups( tareGroups.getText() );
      p.setOtherRequirements( tareOtherReq.getText() );
      p.setKeywords( tareKeywords.getText() );
      p.setNotes( tareNotes.getText() );
      
      return p;
      
   }
   
   // add new crop data here
   public JPanel buildCropInfoPanel() {
      
      cropInfoPanel = new JPanel();
      
      tfldCropName = new CPSTextField(10);
      tfldVarName = new CPSTextField(10);
      
      tfldMatDays = new CPSTextField(5);

      tfldDatePlant = new CPSTextField(10);
      tfldDateHarvest = new CPSTextField(10);
      
      tareGroups = new CPSTextArea( 3, 10 );
      tareKeywords = new CPSTextArea( 3, 10 );
      tareOtherReq = new CPSTextArea( 3, 10 );
      tareNotes = new CPSTextArea( 5, 20 );
      
      cropInfoPanel.setLayout( new GridBagLayout() );
      GridBagConstraints c = new GridBagConstraints();
      cropInfoPanel.setAlignmentX( JPanel.LEFT_ALIGNMENT );
      cropInfoPanel.setAlignmentY( JPanel.TOP_ALIGNMENT );

      Insets il  = new Insets( 0, 0, 0, 5 );
      Insets ita = new Insets( 0, 2, 2, 5 );

      
      /* the format for these calls is: panel, column, row, component */
      
      LayoutAssist.createLabel(  cropInfoPanel, 0, 0, "Crop Name:" );
      LayoutAssist.addTextField( cropInfoPanel, 1, 0, tfldCropName );

      LayoutAssist.createLabel(  cropInfoPanel, 2, 0, "Mat. Days:" );
      LayoutAssist.addTextField( cropInfoPanel, 3, 0, tfldMatDays );
      
      // TODO maybe: if ( isVariety )
      LayoutAssist.createLabel(  cropInfoPanel, 0, 1, "Variety:" );
      LayoutAssist.addTextField( cropInfoPanel, 1, 1, tfldVarName );

      LayoutAssist.createLabel(  cropInfoPanel, 2, 1, "Planting Date:" );
      LayoutAssist.addTextField( cropInfoPanel, 3, 1, tfldDatePlant );
      
      LayoutAssist.createLabel(  cropInfoPanel, 2, 2, "Harvest Date:" );
      LayoutAssist.addTextField( cropInfoPanel, 3, 2, tfldDateHarvest );
      
      
      LayoutAssist.createLabel(  cropInfoPanel, 2, 5, "Belongs to Groups:" );
      LayoutAssist.addTextArea(  cropInfoPanel, 3, 5, tareGroups );

      LayoutAssist.createLabel(  cropInfoPanel, 0, 5, "Other Requirements:" );
      LayoutAssist.addTextArea(  cropInfoPanel, 1, 5, tareOtherReq );
      
      LayoutAssist.createLabel(  cropInfoPanel, 2, 7, "Keywords:" );
      LayoutAssist.addTextArea(  cropInfoPanel, 3, 7, tareKeywords );
      
      LayoutAssist.createLabel(  cropInfoPanel, 0, 6, "Notes:" );
      LayoutAssist.addTextArea(  cropInfoPanel, 1, 6, tareNotes );
      
      return cropInfoPanel;
      
   }

   @Override
   public void setDataSource( CPSDataModel dm ) {
      super.setDataSource(dm);
      
      // now fill in the SimilarTo combobox
//      TableModel tm = dataModel.getCropList( "crop_name" );
//      int nameCol = 0;
//      // find the column storing the crop names
//      while ( ! tm.getColumnName( nameCol ).equalsIgnoreCase("crop_name") && 
//              nameCol < tm.getColumnCount() ) {
//         nameCol++;
//      }
//      // add the crop names to the combo box
//      for ( int i = 0; i < tm.getRowCount(); i++ )
//         cmbxSimilar.addItem( tm.getValueAt( i, nameCol ) );
      
   }
   
   public void actionPerformed(ActionEvent actionEvent) {
      String action = actionEvent.getActionCommand();
      String selectedPlan = uiManager.getSelectedPlanName();
      
      if      ( action.equalsIgnoreCase( btnSaveChanges.getText() ) ) {
         if ( isDataAvailable() ) {
            // update only the differences between the originally displayed crop
            // and what is currently displayed
            CPSPlanting diff = (CPSPlanting) displayedPlanting.diff( this.asPlanting() );
            if ( diff.getID() != -1 )
               dataModel.updatePlanting( selectedPlan, diff );
         }
         uiManager.refreshPlanList();
      } 
      else if ( action.equalsIgnoreCase( btnDiscardChanges.getText() )) {
         displayPlanting( displayedPlanting );
         // reset the "this had changed" bit on all components
      }
      
      
      // resetColorsCHANGETHISNAME();
   }
   
   public void resetColorsCHANGETHISNAME() {
      Component[] allComponents = cropInfoPanel.getComponents();
      
      for ( Component c : allComponents )
         c.setBackground( Color.WHITE );
   }
   
}
