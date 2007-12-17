/*
 * CropPlans.java
 *
 * Created on November 26, 2007, 9:03 PM by Clayton
 */

package CPS.Core.CropPlans;

import CPS.Module.*;
import javax.swing.JPanel;

public class CropPlans extends CPSCoreModule {
   
   private String ModuleName = "CropPlans";
   private String ModuleType = "Core";
   private String ModuleVersion = "0.1";

   private CropPlanUI ui;

   public CropPlans() {
      setModuleName( "CropPlans" );
      setModuleType( "Core" );
      setModuleVersion( "0.1" );
     
      ui = new CropPlanUI();
   }

   public JPanel display() {
	return ui.getUI();
   }

    @Override
   public void setDataSource(CPSDataModel dm) {
      ui.setDataSource(dm);
   }
   
   
}
