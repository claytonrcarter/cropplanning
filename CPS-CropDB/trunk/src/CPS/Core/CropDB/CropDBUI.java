package CPS.Core.CropDB;

/**
 * Helper class designed to be used only by the Crop DB class.
 * This class will handle all of the UI for the CropDB.
 */

import CPS.Data.CPSCrop;
import CPS.Module.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class CropDBUI extends CPSDataModelUser {
    
    private JPanel cropDBPanel;
    private JSplitPane cropDBSplit;
    
    private CropDBCropList cropList;
    private CropDBCropInfo cropInfo;
    

    protected CropDBUI () {

       cropInfo = new CropDBCropInfo( this );
       cropList = new CropDBCropList( this );
       
       buildUI();

    }

    private void buildUI () {
 
	

        // build split pane
        cropDBSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
                                      cropList.getJPanel(),
                                      cropInfo.getJPanel() );
        cropDBSplit.setDividerSize( 5 );
        cropDBSplit.setDividerLocation( .5 );
        cropDBSplit.setOneTouchExpandable( false );
        cropDBSplit.setContinuousLayout( false ); /* redundant, but useful here in case we want
                                                   * to change it in the future */
        
        /* add panels to main frame */
	cropDBPanel = new JPanel();
        cropDBPanel.add( cropDBSplit );
        
	// cropDBPanel.setLayout( new BorderLayout() );
        // cropDBPanel.add( cropListPanel, BorderLayout.PAGE_START );
	// cropDBPanel.add( cropInfoPanel, BorderLayout.CENTER );

    }
    
    public JPanel getUI () { return cropDBPanel; }    
    public void setDataSource( CPSDataModel dm ) {
       // set the data source for this object
       super.setDataSource(dm);
       
       // set the data source for these panels
       cropList.setDataSource(dm);
       cropInfo.setDataSource(dm);
    }
    
//    protected void updateCropPlanListPane() {
//       if ( ! dataAvail )
//          return;
//       
//       cropListPanel.add( new JLabel( "Available Crop Plans: " ));
//       
//       ArrayList<String> l = dataModel.getListOfCropPlans();
//       for ( int i = 0; i < l.size(); i++ )
//          cropListPanel.add( new JLabel( l.get(i) ));
//    }

   void displayCropInfo( CPSCrop crop ) {
      cropInfo.displayCrop( crop );
   }

   void refreshCropList() {
      cropList.updateCropList();
   }

}
