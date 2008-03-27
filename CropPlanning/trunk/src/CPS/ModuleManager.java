/* ModuleManager.java - Created: March 18, 2007
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

package CPS;

import CPS.Module.*;
import java.util.ArrayList;


public class ModuleManager {
   
   ArrayList<CPSUI>   uiMods;
   ArrayList<CPSDataModel>  dmMods;
   ArrayList<CPSDisplayableDataUserModule> coreMods;
   
   CPSGlobalSettings globalSettings;
   
   public ModuleManager() {
      uiMods   = new ArrayList<CPSUI>();
      dmMods   = new ArrayList<CPSDataModel>();
      coreMods = new ArrayList<CPSDisplayableDataUserModule>();
      
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
   
   /* Perhaps we should do more error checking here?  What about unfound core modules? */
   public void loadCoreModules() {
      coreMods.add( (CPSDisplayableDataUserModule) loadPlugin( "CPS.Core." + "CropPlans.CropPlans" ) );
      coreMods.add( (CPSDisplayableDataUserModule) loadPlugin( "CPS.Core." + "CropDB.CropDB" ) );
      coreMods.add( (CPSDisplayableDataUserModule) loadPlugin( "CPS.Core." + "TODOLists.TODOLists" ) );
      
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
              System.out.println( "Shutting down module: " + mod.getModuleName() );
              mod.shutdown();
          } catch ( UnsupportedOperationException ignore ) {}
      dmMods.get(0).shutdown();
   }
}
