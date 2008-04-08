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
   public static final String CPS_CORE_VERSION = CPSModule.GLOBAL_DEVEL_VERSION;
   
    public static void main(String[] args) {

	new CropPlanning();

    }

    public CropPlanning() {
       
       mm = new ModuleManager();

       CPSGlobalSettings globSet = mm.getGlobalSettings();
       CPSUI ui = mm.getUI();
       ui.addModuleConfiguration( globSet );
       
       // TODO This is no good.  We need to work on a way for modules to 
       // initialize seperately, possibly we should tell them to via an init()
       // and they should signal us with an error
       if ( globSet.getFirstTimeRun() )
           globSet.setOutputDir( ui.showFirstRunDialog( globSet.getDataOutputDir() ));
       
       
       CPSDataModel dm = mm.getDM();
       // TODO test to see if data is available and then send a dialog to user if not
       if ( dm instanceof CPSConfigurable )
           ui.addModuleConfiguration( (CPSConfigurable) dm );
       
       mm.loadImportersExporters();
       for ( CPSModule ieMod : mm.getImportersExporters() ) {
          if ( ieMod instanceof CPSExporter )
             ui.addExporter( (CPSExporter) ieMod );
          if ( ieMod instanceof CPSImporter )
             ui.addImporter( (CPSImporter) ieMod );
       }
       
       mm.loadCoreModules();
//       for ( int i = 0; i < mm.getNumCoreModules(); i++ ) {
//           // get the module (which was loaded and inited by ModuleManager)
//          CPSDisplayableDataUserModule cm2 = mm.getCoreModule(i);
       for ( CPSDisplayableDataUserModule cm2 : mm.getCoreModules() ) {
          System.out.println("DRIVER: Initializing module: " + cm2.getModuleName() );
          // apply data source
          cm2.setDataSource(dm);
          // add it to the UI
          ui.addModule( cm2 );
          cm2.addUIChangeListener( ui );
       }
       
       Runtime.getRuntime().addShutdownHook( new Thread(this) );
                   
       globSet.setLastVersionUsed( CPSModule.versionAsLongInt( CPS_CORE_VERSION ) );
       
       ui.showUI();
       
    }

    
   public void run() {
      mm.shutdownModules();
   }

    
}

