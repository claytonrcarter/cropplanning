/*
 * CropDBCropInfo.java
 *
 * Created on March 15, 2007, 11:26 AM by Clayton
 */

package CPS.Core.CropDB;

import CPS.Data.*;
import CPS.Module.*;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CropDBCropInfo extends CPSDataModelUser {
   
   private JPanel cropInfoPanel;
   
   private JPanel buttonPanel;
   private JLabel lblChanges;
   private JButton btnSaveChanges, btnDiscardChanges, btnDelete;
   
   private JLabel noItemSelected;
   
   CropDBCropInfo() {
      buildCropInfoPanel();
      buildButtonPanel();
   }
   
   private void buildCropInfoPanel() {
      /*
       * Create the crop info panel
       */
      cropInfoPanel = new JPanel();
      cropInfoPanel.setBorder( BorderFactory.createTitledBorder( "Crop Info" ));
      cropInfoPanel.setLayout( new BorderLayout() );
      
      noItemSelected = new JLabel( "Select item from table to display detailed information." );
      
      cropInfoPanel.add( noItemSelected, BorderLayout.CENTER );
      
   }
   
   private void buildButtonPanel() {
      
      lblChanges = new JLabel( "Changes: " ); 
      btnSaveChanges = new JButton( "Save" );
      btnDiscardChanges = new JButton( "Discard" );
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.LINE_AXIS ) );
      
      buttonPanel.add( Box.createHorizontalGlue() );
      buttonPanel.add( lblChanges );
      buttonPanel.add( btnSaveChanges );
      buttonPanel.add( btnDiscardChanges );
      
   }
   
   public void updateForCrop( CPSCrop c ) {
      cropInfoPanel.removeAll();
      cropInfoPanel.add( CropDBDataTranslata.convertCrop(c), BorderLayout.CENTER );
      cropInfoPanel.add( buttonPanel, BorderLayout.PAGE_END );
      cropInfoPanel.validate();
      // cropInfoPanel.repaint();
   }
   
   public JPanel getJPanel() {
      return cropInfoPanel;
   }
   
}
