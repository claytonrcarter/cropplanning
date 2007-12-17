package CPS.Core.CropPlans;

/**
 * Helper class designed to be used only by the Crop DB class.
 * This class will handle all of the UI for the CropDB.
 */

import CPS.UI.Modules.CPSMasterDetailModule;
import CPS.Module.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

class CropPlanUI extends CPSMasterDetailModule {
    
    public CropPlanUI () {
        setMasterView( new CropPlanList( this ) );
        setDetailView( new CropPlanInfo( this ) );
    }
   
}
