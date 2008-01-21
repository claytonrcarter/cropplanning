/*
 * ModuleManager.java
 *
 * Created on March 18, 2007, 1:08 PM by Clayton
 */

package CPS;

import CPS.Module.*;
import java.util.ArrayList;


public class ModuleManager {
   
   ArrayList<CPSUI>   uiMods;
   ArrayList<CPSDataModel>  dmMods;
   ArrayList<CPSDisplayableDataUserModule> coreMods;
   
   public ModuleManager() {
      uiMods   = new ArrayList<CPSUI>();
      dmMods   = new ArrayList<CPSDataModel>();
      coreMods = new ArrayList<CPSDisplayableDataUserModule>();
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
    
    private CPSModule loadPlugin( String classname, CPSUI uim ) {
       
       Object instance = null;

	try {

           if ( uim == null )
              instance = Class.forName( classname )
                              .getConstructor( new Class[] {} )
                              .newInstance( new Object[]{} );
           else
              instance = Class.forName( classname )
                              .getConstructor( new Class[] { CPSUI.class } )
                              .newInstance( new Object[]{ uim } );
            
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
      dmMods.get(0).shutdown();
   }
}
