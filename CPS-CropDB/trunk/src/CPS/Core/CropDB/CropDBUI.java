package CPS.Core.CropDB;

/**
 * Helper class designed to be used only by the Crop DB class.
 * This class will handle all of the UI for the CropDB.
 */

import CPS.Module.*;
import CPS.UI.Modules.CPSMasterDetailModule;

class CropDBUI extends CPSMasterDetailModule {
    
    public CropDBUI( CPSUIModule uim ) {
       super( uim );
       
       setMasterView( new CropDBCropList( this ) );
       setDetailView( new CropDBCropInfo( this ) );
       
    }
    
}
