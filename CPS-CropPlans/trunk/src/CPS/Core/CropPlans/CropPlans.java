/* CropPlans.java - Created: November 26, 2007
 * Copyright (C) 2007, 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package CPS.Core.CropPlans;

import CPS.CSV;
import CPS.Module.*;
import CPS.UI.Modules.CPSMasterDetailModule;
import javax.swing.JPanel;

public class CropPlans extends CPSMasterDetailModule implements CPSExportable {
   
   public CropPlans() {
      
      setModuleName( "CropPlans" );
      setModuleType( "Core" );
      setModuleVersion( GLOBAL_DEVEL_VERSION );
      
      //addRequirment( "CPSDriver", "0.1.0" );
      
      setMasterView( new CropPlanList( this ) );
      setDetailView( new CropPlanInfo( this ) );
      
   }

   public JPanel display() {
	return getUI();
   }
 
   public void exportData() {
        if ( isDataAvailable() ) {
            CPSExporter exp = new CSV();
            String planName = this.getMasterTableName();
            String fileName = CPSGlobalSettings.getDocumentOutputDir() + 
                              System.getProperty( "file.separator" ) +
                              "ExportedCropPlan - " + planName + "." + exp.getExportFileDefaultExtension(); 
            exp.exportCropPlan( fileName, planName, getDataSource().getCropPlanAsList( planName ));
        }
        else {
            System.err.println("ERROR(CropPlans): No data exported, no data available.");
        }
            
    }

    public String getExportName() {
        return "Selected crop plan";
    }
    
   
}
