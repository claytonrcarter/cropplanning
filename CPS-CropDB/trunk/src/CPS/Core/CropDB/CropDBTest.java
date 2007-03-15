package CPS.Core.CropDB;

import CPS.Module.CPSCoreModule;
import javax.swing.JPanel;

public class CropDBTest extends CPSCoreModule {

    private String ModuleName = "CropDB";
    private String ModuleType = "Core";
    private String ModuleVersion = "0.1";

    private CropDBUITest ui;

    public CropDBTest () {
	ui = new CropDBUITest();
	if ( ui.verifyVersion( ModuleVersion ) ) { /* Hooray! */ } 
	else { /* Oops */ }
    }

    public JPanel display () {
	return ui.getUI();
    }

    public String getModuleName () { return ModuleName; }
    public String getModuleType () { return ModuleType; }
    public String getModuleVersion () { return ModuleVersion; }

}
