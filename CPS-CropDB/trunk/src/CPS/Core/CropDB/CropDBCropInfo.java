/*
 * CropDBCropInfo.java
 *
 * Created on March 15, 2007, 11:26 AM by Clayton
 */

package CPS.Core.CropDB;

import CPS.Data.*;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CropDBCropInfo {
   
   private JPanel cropInfoPanel;
   
   private JLabel noItemSelected;
   
   CropDBCropInfo() {
      buildCropInfoPanel();
   }
   
   private void buildCropInfoPanel() {
      /*
       * Create the crop info panel
       */
      cropInfoPanel = new JPanel();
      cropInfoPanel.setBorder( BorderFactory.createTitledBorder( "Crop Info" ));
      
      noItemSelected = new JLabel( "Select item from table to display detailed information." );
      
      cropInfoPanel.add( noItemSelected );
      
   }
   
   public void updateForCrop( Crop c ) {
      cropInfoPanel.removeAll();
   }
   
   public JPanel getJPanel() {
      return cropInfoPanel;
   }
   
}
