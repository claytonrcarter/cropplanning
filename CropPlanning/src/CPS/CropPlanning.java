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
import barrysoft.twinkle.Twinkle;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.prefs.Preferences;


public class CropPlanning implements Runnable {

   private ModuleManager mm;
   private CPSUI ui;

    public static void main(String[] args) {

       InputStream in;
       Properties props = new Properties();
       String buildnum = "";
       try {
           in = CropPlanning.class.getClass().getResourceAsStream( "/appinfo.properties" );
           
           if ( in == null ) {
               System.err.println( "Uh Oh!  appinfo.properties NOT FOUND!" );
               System.exit( -1 );
           }

           props.load( in );

           CPSGlobalSettings.setVersion( props.getProperty( "program.VERSION" ) );

           buildnum +=
                   props.getProperty( "program.VERSION") + "-" +
                   props.getProperty( "program.BUILDNUM" ) + "-" +
                   props.getProperty( "program.BUILDDATE" );

           in.close();

       } catch ( IOException ex ) {
           ex.printStackTrace(); 
       }

       // parse arguments
       boolean enableDebug = false;
       for ( String s : args ) {
          if ( s.equalsIgnoreCase( "-debug" ))
            enableDebug = true;
          if ( s.startsWith( "-temp-output-dir=") )
             CPSGlobalSettings.setTempOutputDir( s.substring( "-temp-output-dir=".length() ) );
          if ( s.equalsIgnoreCase( "-firstTime" ) )
             CPSGlobalSettings.setFirstTimeRun( true );
          if ( s.equalsIgnoreCase( "-version" ) || s.equalsIgnoreCase( "-versionsimple" )) {
             System.out.println( CPSGlobalSettings.getVersion() );
             System.exit(0);
          }
          if ( s.equalsIgnoreCase( "-versionraw" )) {
             System.out.println( buildnum );
             System.exit(0);
          }

       }
       CPSGlobalSettings.setDebug(enableDebug);

       System.out.println( "Build number: " + buildnum );

       // Check for updates
       String appcastURL = "http://www.failbetterfarm.com/cps/appcast.xml";
       if ( CPSGlobalSettings.getDebug() )
         appcastURL = "http://www.failbetterfarm.com/cps/appcast-test.xml";

       // only check for updates if it's not the first time they've run
       // the app and they haven't turned off update checking
       if ( ! CPSGlobalSettings.getFirstTimeRun() &&
              CPSGlobalSettings.getCheckForUpdates() ) {
         Preferences.userNodeForPackage( CropPlanning.class )
                    .putBoolean( "updater.downloadonly", true );
         Twinkle.getInstance()
                .runUpdate( CropPlanning.class,
                            appcastURL,
                            "/twinkle.properties" );
       }

       // Init the program & modules
       CropPlanning cps = new CropPlanning();
       // this line just for testing the app cast; show a dummy dialog
//       new CPSConfirmDialog("Hi").setVisible(true);

       // and finally show the app
       cps.show();

    }

    public CropPlanning() {

       mm = new ModuleManager();

       //
       // DO NOT conflate all of these module calls into a mm.loadMods, mm.initMods, etc
       //
       // save that for a later version
       //
       CPSGlobalSettings globSet = mm.getGlobalSettings();
       ui = mm.getUI();
       ui.addModuleConfiguration( globSet );

       CPSDataModel dm = mm.getDM();
       // TODO test to see if data is available and then send a dialog to user if not
       if ( dm instanceof CPSConfigurable )
           ui.addModuleConfiguration( (CPSConfigurable) dm );

       // TODO This is no good.  We need to work on a way for modules to 
       // initialize seperately, possibly we should tell them to via an init()
       // and they should signal us with an error
       if ( CPSGlobalSettings.getFirstTimeRun() )
          if ( ! ui.showFirstRunPreInitWizard( globSet ) )
             System.exit(1);

       // be sure to initialize the DM!
       dm.init();

       if ( CPSGlobalSettings.getFirstTimeRun() )
          ui.showFirstRunPostInitWizard( globSet );
       
       mm.loadImportersExporters();
       for ( CPSModule ieMod : mm.getImportersExporters() ) {
          if ( ieMod instanceof CPSExporter )
             ui.addExporter( (CPSExporter) ieMod );
          if ( ieMod instanceof CPSImporter )
             ui.addImporter( (CPSImporter) ieMod );
       }
       
       mm.loadCoreModules();

       for ( CPSDisplayableDataUserModule cm2 : mm.getCoreModules() ) {
          CPSModule.debug( "DRIVER", "Initializing module: " + cm2.getModuleName() );

          cm2.setMediator( mm );

          // apply data source
          cm2.setDataSource(dm);
          
          // add it to the UI
          ui.addModule( cm2 );
          cm2.addUIChangeListener( ui );
       }
       
       Runtime.getRuntime().addShutdownHook( new Thread(this) );
                   
       globSet.setLastVersionUsed( CPSModule.versionAsLongInt( globSet.getVersion() ) );
       
       
    }

    public void show() {

       ui.showUI();

    }
    
   public void run() {
      mm.shutdownModules();
      // always disable debug on exit (so that it won't be turned on upon startup)
      CPSGlobalSettings.setDebug(false);
   }

    
}

