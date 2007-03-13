package CPS;

import CPS.Module.CPSCoreModule;
import CPS.Module.CPSModule;
import CPS.Module.CPSUIModule;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;

import javax.swing.*;

public class CropPlanning {

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

	CPSUIModule ui = (CPSUIModule) testLoadPlugin( "CPS.Core.UI." + "TabbedUI" );
	ui.showUI();

	CPSCoreModule cm = (CPSCoreModule) testLoadPlugin( "CPS.Core.CropDB." + "CropDBTest" );
// 	if ( extensions.existFor( "CropDB" ) ) {
// 	    cm.registerExtensions();
// 	}
	ui.addModule( cm.getModuleName(), cm.display() );

        CPSCoreModule cm2 = (CPSCoreModule) testLoadPlugin( "CPS.Core." + "CropDB.CropDB" );
        ui.addModule( cm2.getModuleName(), cm2.display() );
        
	JLabel l = new JLabel( "Hello World!" );
	JPanel p = new JPanel();
	p.add(l);
	ui.addModule( "CropPlanning Module", p );

    }

    /**
     * Loads a demo from a classname
     */
    CPSModule testLoadPlugin( String classname ) {
	
	Object instance = null;

	try {

	    System.out.println( "Loading class " + classname );
	    Class c = Class.forName( classname );

	    System.out.println( "Retrieving constructor" );
	    Constructor cons = c.getConstructor( new Class[]{} );

	    System.out.println( "Creating new instance of " + classname );
	    instance = cons.newInstance( new Object[]{} );

	} catch (Exception e) {
	    System.out.println("Error occurred loading demo: " + classname);
	    System.out.println(e);
	}

	return (CPSModule) instance;
    }

}

