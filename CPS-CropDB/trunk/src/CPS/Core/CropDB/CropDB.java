/*
 * CropDB.java
 *
 * Created on March 12, 2007, 10:27 PM
 *
 *
 */

package CPS.Core.CropDB;

import CPS.Module.*;
import CPS.UI.Modules.CPSMasterDetailModule;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Clayton
 */
public class CropDB extends CPSMasterDetailModule {
   
    public CropDB () {
       
       setModuleName( "CropDB" );
       setModuleType( "Core" );
       setModuleVersion( "0.1" );
       
       setMasterView( new CropDBCropList( this ) );
       setDetailView( new CropDBCropInfo( this ) );
       
    }

    public JPanel display () {
	return getUI();
    }
    
}
