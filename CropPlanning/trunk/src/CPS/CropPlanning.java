/* CropPlanning.java - created: sometime in 2007
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
import java.util.*;
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
       CPSUI ui = mm.getUI();
       // CPSSettings settings = mm.getSettings();

       mm.loadCoreModules();
       for ( int i = 0; i < mm.getNumCoreModules(); i++ ) {
          CPSDisplayableDataUserModule cm2 = mm.getCoreModule(i);
          System.out.println("DRIVER: Initializing module: " + cm2.getModuleName() );
          cm2.setDataSource(dm);
          ui.addModule( cm2.getModuleName(), cm2.display() );
          cm2.addUIChangeListener( ui );
       }
       
       Runtime.getRuntime().addShutdownHook( new Thread(this) );
                   
       ui.showUI();
       
    }

    
   public void run() {
      mm.shutdownModules();
   }

    
}

