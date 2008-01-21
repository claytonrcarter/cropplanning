/*
 * CropPlans.java
 *
 * Created on November 26, 2007, 9:03 PM by Clayton
 */

package CPS.Core.CropPlans;

import CPS.Module.*;
import CPS.UI.Modules.CPSMasterDetailModule;
import java.awt.Dimension;
import javax.swing.JPanel;

public class CropPlans extends CPSMasterDetailModule {
   
   public CropPlans() {
      
      setModuleName( "CropPlans" );
      setModuleType( "Core" );
      setModuleVersion( "0.1" );
      
      setMasterView( new CropPlanList( this ) );
      setDetailView( new CropPlanInfo( this ) );
      
   }

   public JPanel display() {
	return getUI();
   }
   
}
