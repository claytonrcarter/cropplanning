package CPS;

import CPS.Module.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;

import javax.swing.*;

public class CropPlanning implements Runnable {

   private ModuleManager mm;
   
    public static void main(String[] args) {

	new CropPlanning();

    }

    public CropPlanning() {

	/* PSEUDO CODE
	 * Load all modules in module directory
	 * Catagorize modules
	 * Initialize DATA modules
	 * Initialize LOGIC modules
	 * Initialize UI modules
	 * Initialize EXTENSION modules
	 *  - for modToExtend in EXTENSION.extendsWhat()
	 *      modToExtend.registerExtension(EXTENSION)
         */
       
       mm = new ModuleManager();

       CPSDataModel dm = mm.getDM();
       CPSUIModule ui = mm.getUI();

       mm.loadCoreModules();
       for ( int i = 0; i < mm.getNumCoreModules(); i++ ) {
          CPSCoreModule cm2 = mm.getCoreModule(i);
          cm2.setDataSource(dm);
          ui.addModule( cm2.getModuleName(), cm2.display() );
       }
       
       Runtime.getRuntime().addShutdownHook( new Thread(this) );
                   
       ui.showUI();
       
    }

    
   public void run() {
      mm.shutdownModules();
   }

    
}

