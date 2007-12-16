package CPS.Core.CropPlans;

/**
 * Helper class designed to be used only by the Crop DB class.
 * This class will handle all of the UI for the CropDB.
 */

import CPS.Data.CPSPlanting;
import CPS.Module.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class CropPlanUI extends CPSDataModelUser {
    
    private JPanel cropPlanPanel;
    private JSplitPane cropPlanSplit;
    
    private CropPlanList planList;
    private CropPlanInfo planInfo;
    

    protected CropPlanUI () {

       planInfo = new CropPlanInfo( this );
       planList = new CropPlanList( this );
       
       buildUI();

    }

    private void buildUI () {
 
	

        // build split pane
        cropPlanSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
                                        planList.getJPanel(),
                                        planInfo.getJPanel() );
        cropPlanSplit.setDividerSize( 5 );
        cropPlanSplit.setDividerLocation( .5 );
        cropPlanSplit.setOneTouchExpandable( false );
        cropPlanSplit.setContinuousLayout( false ); /* redundant, but useful here in case we want
                                                     * to change it in the future */
        
        /* add panels to main frame */
	cropPlanPanel = new JPanel();
        cropPlanPanel.add( cropPlanSplit );
//        cropPlanPanel.add( planList.getJPanel() );
                                                   
	// cropPlanPanel.setLayout( new BorderLayout() );
        // cropPlanPanel.add( planListPanel, BorderLayout.PAGE_START );
	// cropPlanPanel.add( planInfoPanel, BorderLayout.CENTER );

    }
    
    public JPanel getUI () { return cropPlanPanel; }    
    public void setDataSource( CPSDataModel dm ) {
       // set the data source for this object
       super.setDataSource(dm);
       
       // set the data source for these panels
       planList.setDataSource(dm);
       planInfo.setDataSource(dm);
    }
    
//    protected void updateCropPlanListPane() {
//       if ( ! dataAvail )
//          return;
//       
//       planListPanel.add( new JLabel( "Available Crop Plans: " ));
//       
//       ArrayList<String> l = dataModel.getListOfCropPlans();
//       for ( int i = 0; i < l.size(); i++ )
//          planListPanel.add( new JLabel( l.get(i) ));
//    }

   void displayPlanting( CPSPlanting p ) {
      planInfo.displayPlanting(p);
   }

   void refreshPlanList() {
      planList.updatePlanList();
   }
   
   String getSelectedPlanName() {
      return planList.getSelectedPlanName();
   }

   void revalidate() { cropPlanPanel.revalidate(); }
   
}
