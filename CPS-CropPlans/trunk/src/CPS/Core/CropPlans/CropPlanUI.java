package CPS.Core.CropPlans;

/**
 * Helper class designed to be used only by the Crop DB class.
 * This class will handle all of the UI for the CropDB.
 */

import CPS.UI.Modules.CPSMasterDetailModule;
import CPS.Module.*;

class CropPlanUI extends CPSMasterDetailModule {
    
    public CropPlanUI () {
        setMasterView( new CropPlanList( this ) );
        setDetailView( new CropPlanInfo( this ) );
    }
   
}
