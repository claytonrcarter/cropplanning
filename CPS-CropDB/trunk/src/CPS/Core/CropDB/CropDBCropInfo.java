/*
 * CropDBCropInfo.java
 *
 * Created on March 15, 2007, 11:26 AM by Clayton
 */

package CPS.Core.CropDB;

import CPS.Data.*;
import CPS.Module.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CropDBCropInfo extends CPSDataModelUser implements ActionListener {
   
   private JPanel mainPanel, cropInfoPanel;
   
   private JPanel buttonPanel;
   private JLabel lblChanges;
   private JButton btnSaveChanges, btnDiscardChanges, btnDelete;
   
   private boolean noItemSelected = true;
   
   private JCheckBox chkDS, chkTP;
   private JTextField tfldCropName, tfldVarName, tfldFamName;
   private JTextField tfldMatDays;
   
   private CPSCrop displayedCrop;
   
   private CropDBUI uiManager;
   
   CropDBCropInfo( CropDBUI ui ) {
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
      mainPanel.setBorder( BorderFactory.createTitledBorder( "Crop Info" ));
      mainPanel.setLayout( new BorderLayout() );
      
      JLabel lblNoItemSelected = new JLabel( "Select item from table to display detailed information." );
      
      mainPanel.add( lblNoItemSelected, BorderLayout.CENTER );
      
   }
   
   private void buildButtonPanel() {
      
      lblChanges = new JLabel( "Changes: " ); 
      btnSaveChanges = new JButton( "Save" );
      btnDiscardChanges = new JButton( "Discard" );
      btnSaveChanges.addActionListener( this );
      btnDiscardChanges.addActionListener( this );
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.LINE_AXIS ) );
      
      buttonPanel.add( Box.createHorizontalGlue() );
      buttonPanel.add( lblChanges );
      buttonPanel.add( btnSaveChanges );
      buttonPanel.add( btnDiscardChanges );
      
   }
   
   /* this should be renamed */
   public void displayCropInfo() {
      mainPanel.removeAll();
      mainPanel.add( cropInfoPanel, BorderLayout.CENTER );
      mainPanel.add( buttonPanel, BorderLayout.PAGE_END );
      mainPanel.validate();
      // mainPanel.repaint();
   }
   
   public JPanel getJPanel() {
      return mainPanel;
   }

   public void displayCrop( CPSCrop crop ) {
      
      displayedCrop = crop;
      
      if ( noItemSelected ) {
         displayCropInfo();
         noItemSelected = false;
      }
      
      // chkDS.setSelected( displayedCrop.getDS() );
      // chkTP.setSelected( displayedCrop.getTP() ); 
      
      tfldCropName.setText( displayedCrop.getCropName() );
      tfldVarName.setText( displayedCrop.getVarietyName() );
      tfldFamName.setText( displayedCrop.getFamilyName() );
      tfldMatDays.setText( "" + displayedCrop.getMaturityDays() );
      
   }
   
   public CPSCrop asCrop() {
      
      CPSCrop crop = new CPSCrop();
      
      crop.setID( displayedCrop.getID() );
      
      // crop.setDS( chkDS.isSelected() );
      // crop.setTP( chkTP.isSelected() );
      
      crop.setCropName( tfldCropName.getText() );
      crop.setVarietyName( tfldVarName.getText() );
      crop.setFamilyName( tfldFamName.getText() );
      crop.setMaturityDays( Integer.parseInt( tfldMatDays.getText() ));
      
      return crop;
      
   }
   
   public CPSCrop diffCrop( CPSCrop that ) {
      
      CPSCrop diff = new CPSCrop();
      
      //if ( chkDS.isSelected() != that.getDS() )
        // diff.setDS( chkDS.isSelected() );
      
      // and so on ...
      
      return diff;
   }
   
   public JPanel buildCropInfoPanel() {
      
      cropInfoPanel = new JPanel();
      
      chkDS = new JCheckBox("DS?");
      chkTP = new JCheckBox("TP?");
      tfldCropName = new JTextField(10);
      tfldVarName = new JTextField(10);
      tfldFamName = new JTextField(10);
      tfldMatDays = new JTextField(5);
      
      cropInfoPanel.setLayout( new GridBagLayout() );
      GridBagConstraints c = new GridBagConstraints();
      cropInfoPanel.setAlignmentX( JPanel.LEFT_ALIGNMENT );
      cropInfoPanel.setAlignmentY( JPanel.TOP_ALIGNMENT );

      Insets il  = new Insets( 0, 0, 0, 5 );
      Insets ita = new Insets( 0, 2, 2, 5 );

      /* Column One */
      CropDBDataTranslata.createLabel(  cropInfoPanel, 0, 0, "Crop Name:" );
      CropDBDataTranslata.addTextField( cropInfoPanel, 1, 0, tfldCropName );

      CropDBDataTranslata.createLabel(  cropInfoPanel, 0, 1, "Variety:" );
      CropDBDataTranslata.addTextField( cropInfoPanel, 1, 1, tfldVarName );

      //createLabel(     cropInfoPanel, 0, 2, "Belongs to Groups:" );
      //createTextArea(  cropInfoPanel, 1, 2, "Greenhouse,\nMesclun" );


      /* Column Two */
      CropDBDataTranslata.createLabel(  cropInfoPanel, 2, 0, "Family:" );
      CropDBDataTranslata.addTextField( cropInfoPanel, 3, 0, tfldFamName );
      
      CropDBDataTranslata.createLabel(     cropInfoPanel, 2, 1, "Mat. Days:" );
      CropDBDataTranslata.addTextField( cropInfoPanel, 3, 1, tfldMatDays );

      //CropDBDataTranslata.createLabel(     cropInfoPanel, 2, 2, "DS?" );
      CropDBDataTranslata.addCheckBox(  cropInfoPanel, 3, 2, chkDS );

      //CropDBDataTranslata.createLabel(     cropInfoPanel, 2, 3, "TP?" );
      CropDBDataTranslata.addCheckBox(  cropInfoPanel, 3, 3, chkTP );

      return cropInfoPanel;
      
   }

   
   public void actionPerformed(ActionEvent actionEvent) {
      String action = actionEvent.getActionCommand();
      
      if      ( action.equalsIgnoreCase( btnSaveChanges.getText() ) ) {
         if ( isDataAvailable() )
            dataModel.updateCrop( this.asCrop() );
         uiManager.refreshCropList();
         // how to fireTableDataChanged()
      } 
      else if ( action.equalsIgnoreCase( btnDiscardChanges.getText() )) {
         displayCrop( displayedCrop );
         // reset the "this had changed" bit on all components
      }
      
      resetColorsCHANGETHISNAME();
   }
   
   public void resetColorsCHANGETHISNAME() {
      tfldCropName.setBackground( Color.WHITE );
      tfldVarName.setBackground( Color.WHITE );
      tfldFamName.setBackground( Color.WHITE );
      tfldMatDays.setBackground( Color.WHITE );
   }
   
}
