/* ModuleManager.java - Created: March 18, 2007
 * Copyright (C) 2007, 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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

package CPS;

import CPS.Module.*;
import CPS.UI.Modules.CPSMasterDetailModule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ModuleManager implements CPSModuleMediator {
   
   ArrayList<CPSUI>   uiMods;
   ArrayList<CPSDataModel>  dmMods;
   ArrayList<CPSDisplayableDataUserModule> coreMods;
   ArrayList<CPSModule> importExportMods;
   
   CPSGlobalSettings globalSettings;

   private CPSMasterDetailModule cropPlansModule = null;

   public ModuleManager() {
      uiMods   = new ArrayList<CPSUI>();
      dmMods   = new ArrayList<CPSDataModel>();
      coreMods = new ArrayList<CPSDisplayableDataUserModule>();
      importExportMods = new ArrayList<CPSModule>();
      
      globalSettings = new CPSGlobalSettings();
      
   }

   public CPSGlobalSettings getGlobalSettings() {
       return globalSettings;
   }
   
   public CPSUI getUI() {
      uiMods.add( (CPSUI) loadPlugin( "CPS.Core.UI." + "TabbedUI" ) );
      return uiMods.get(0);
   }
   
   public CPSDataModel getDM() {
      dmMods.add( (CPSDataModel) loadPlugin( "CPS.Core.DB." + "HSQLDB" ) );
      return dmMods.get(0);
   }
   
   public void loadImportersExporters() {
      importExportMods.add( (CPSModule) loadPlugin( "CPS." + "CSV.CSV" ));
   }
   
   public List<CPSModule> getImportersExporters() {
      return Collections.unmodifiableList( importExportMods );
   }
   
   /* Perhaps we should do more error checking here?  What about unfound core modules? */
   public void loadCoreModules() {

     java.util.Date start;
     java.util.Date direct;
//     java.util.Date reflect;

     start = new java.util.Date();

     coreMods.add( new CPS.Core.CropPlans.CropPlans() );
     coreMods.add( new CPS.Core.CropDB.CropDB() );
     coreMods.add( new CPS.Core.TODOLists.TODOLists() );
     coreMods.add( new CPS.Core.CropPlanStats.CropPlanStats() );

     direct = new java.util.Date();

      System.out.println( "Time to instantiate modules: " + ( direct.getTime() - start.getTime() ) );
      
      for ( CPSModule mod : coreMods ) {
          CPSModule.debug( "ModuleManager", "Examining module: " + mod.getModuleName() );
          if ( mod.getModuleName().equals( "Crop Plans" )) {
              cropPlansModule = (CPSMasterDetailModule) mod;
              CPSModule.debug( "ModuleManager", "Found what we're looking for: Crop Plans" );
          }
      }

   }
   
   public List<CPSDisplayableDataUserModule> getCoreModules() {
      return Collections.unmodifiableList( coreMods );
   }
   
   public int getNumCoreModules() {
      return coreMods.size();
   }
   
   public CPSDisplayableDataUserModule getCoreModule( int i ) {
      if ( i < 0 || i > getNumCoreModules() )
         return null; // ERROR
      else
         return coreMods.get(i);
   }
   
   
    /**
     * Loads a demo from a classname
     */
    private CPSModule loadPlugin( String classname ) {
       return loadPlugin( classname, (String) null );
    }
    
    private CPSModule loadPlugin( String classname, Object arg ) {
       
       Object instance = null;

	try {

           if ( arg == null )
              instance = Class.forName( classname )
                              .getConstructor( new Class[] {} )
                              .newInstance( new Object[]{} );
           else
              instance = Class.forName( classname )
                              .getConstructor( new Class[] { CPSGlobalSettings.class } )
                              .newInstance( new Object[]{ arg } );
            
	} catch (Exception e) {
	    System.out.println("Error occurred loading demo w/ UI Module: " + classname);
            e.printStackTrace();
	}

	return (CPSModule) instance;
    }
    
    private CPSModule loadPlugin( String classname, String arg ) {
       
	Object instance = null;

	try {

//	    Class c = Class.forName( classname );
//	    Constructor cons = c.getConstructor( new Class[] { String.class } );
//	    instance = cons.newInstance( new Object[]{} );

           if ( arg == null )
              instance = Class.forName( classname )
                              .getConstructor( new Class[] {} )
                              .newInstance( new Object[]{} );
           else
              instance = Class.forName( classname )
                              .getConstructor( new Class[] { String.class } )
                              .newInstance( new Object[]{ arg } );
            
	} catch (Exception e) {
	    System.out.println("Error occurred loading demo: " + classname);
            e.printStackTrace();
	}

	return (CPSModule) instance;
    }

   void shutdownModules() {
      for ( CPSModule mod : coreMods )
          try {
              CPSModule.debug( "ModuleManager", "Shutting down module: " + mod.getModuleName() );
              mod.shutdown();
          } catch ( UnsupportedOperationException ignore ) {}
      dmMods.get(0).shutdown();
   }


    public String getCropPlan() {
        if ( cropPlansModule != null )
            return cropPlansModule.getMasterTableName();
        else
            return null;
    }

    public String getPlantingCropName() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
