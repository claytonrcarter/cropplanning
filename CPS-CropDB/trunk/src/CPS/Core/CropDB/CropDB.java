/*
 * CropDB.java
 *
 * Created on March 12, 2007, 10:27 PM
 *
 *
 */

package CPS.Core.CropDB;

import CPS.Module.*;
import javax.swing.JPanel;

/**
 *
 * @author Clayton
 */
public class CropDB extends CPSCoreModule {
   
    private String ModuleName = "CropDB";
    private String ModuleType = "Core";
    private String ModuleVersion = "0.1";

    private CropDBUI ui;

    public CropDB ( CPSUIModule uim ) {
       super(uim);
       
       setModuleName( "CropDB" );
       setModuleType( "Core" );
       setModuleVersion( "0.1" );
     
       ui = new CropDBUI( uim );

    }

    public JPanel display () {
	return ui.getUI();
    }
    
    /* no special init required; just pass it to the UI and let it
     * worry about it. */
    public void setDataSource( CPSDataModel dm ) {
       ui.setDataSource(dm);
    }

}
