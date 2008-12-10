/* TabbedUI.java
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

package CPS.Core.UI;

import CPS.Module.CPSConfigurable;
import CPS.Module.CPSDataModelUser;
import CPS.Module.CPSDisplayableDataUserModule;
import CPS.Module.CPSExportable;
import CPS.Module.CPSExporter;
import CPS.Module.CPSGlobalSettings;
import CPS.Module.CPSImportable;
import CPS.Module.CPSImporter;
import CPS.Module.CPSModule;
import CPS.Module.CPSUI;

import CPS.Module.CPSWizardPage;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;


import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;


public class TabbedUI extends CPSUI implements ActionListener {

    private final String MENU_ITEM_SETTINGS = "Settings...";
    private final String MENU_ITEM_EXIT = "Exit";

    // The preferred size of the demo
    private static final int PREFERRED_WIDTH = 720;
    private static final int PREFERRED_HEIGHT = 640;
    

    // A place to hold on to the visible demo
    // private JPanel contentPane = null;
    JTabbedPane tabbedpane;
    SettingsDialog settings;
    FrameManager fm;
    WizardManager preInitWiz, postInitWiz;
    
    protected JMenuBar menuBar;
    protected JMenu exportMenu = null;
    protected JMenu importMenu = null;
    
    protected ArrayList<CPSExportable> exportables;
    protected ArrayList<CPSExporter> exporters;
    protected ArrayList<CPSImportable> importables;
    protected ArrayList<CPSImporter> importers;
    
    private ArrayList<CPSModule> moduleList;
    private boolean modulesUpdated;

    // To debug or not to debug, that is the question
    private boolean DEBUG = true;
    private int debugCounter = 0;


    /**
     * SwingSet2 Constructors
     */
    public TabbedUI() {	
       
       setModuleName( "CPSTabbedUI" );
       setModuleType( "UI" );
       setModuleVersion( GLOBAL_DEVEL_VERSION );
       
       fm = new FrameManager();
       moduleList = new ArrayList<CPSModule>();
       setModulesUpdated( false );
       
       exportables = new ArrayList<CPSExportable>();
       exporters = new ArrayList<CPSExporter>();
       
       importables = new ArrayList<CPSImportable>();
       importers = new ArrayList<CPSImporter>();
       
       settings = new SettingsDialog();
       if ( CPSGlobalSettings.getFirstTimeRun() ) {
          preInitWiz = new WizardManager( CPSWizardPage.WIZ_TYPE_PRE_INIT );
          postInitWiz = new WizardManager( CPSWizardPage.WIZ_TYPE_POST_INIT );
       }
       
    }

    /**
     * Bring up the SwingSet2 demo by showing the frame (only
     * applicable if coming up as an application, not an applet);
     */
    public void showUI() {
       
       fm.getFrame().setTitle( "CropPlanning" );
       
       fm.getFrame().setJMenuBar( buildMenuBar() );
       
       // initialize tabs
       tabbedpane = new JTabbedPane();
       tabbedpane.setPreferredSize(new Dimension( PREFERRED_WIDTH,
                                                  PREFERRED_HEIGHT ));
       fm.getFrame().getContentPane().add( tabbedpane, BorderLayout.CENTER );

       addModules();
       
       fm.getFrame().setVisible( true );
 
    }

    public String showFirstRunDialog( String defaultDir ) {
        FirstRunDialog dlg = new FirstRunDialog( "Crop Planning Software: Welcome!", defaultDir );
        dlg.setVisible(true);
        return dlg.getOutputDir();
    }

    public boolean showFirstRunPreInitWizard( CPSGlobalSettings globSet ) {
       // ensure that this is the first time we're running
       if ( CPSGlobalSettings.getFirstTimeRun() ) {
          // show the wizard and store the result
          boolean b = preInitWiz.showWizard( globSet );

          if ( ! b ) {
             JOptionPane.showMessageDialog( null,
                                            "The program cannot continue until you complete configuration.  " +
                                            "Please start the program again.",
                                            "Configuation Error",
                                            JOptionPane.ERROR_MESSAGE );
          }
          return b;
       }
       else
          return false;
    }

    public boolean showFirstRunPostInitWizard( CPSGlobalSettings globSet ) {
       // ensure that this is the first time we're running
       if ( CPSGlobalSettings.getFirstTimeRun() ) {
          // show the wizard and store the result
          boolean b = postInitWiz.showWizard( globSet );
          if ( !b ) {
             // user pressed cancel ... that's OK.
          }
          return b;
       }
       else
          return false;
    }

    public void addModule( CPSDisplayableDataUserModule mod ) {
        moduleList.add( mod );
        addModule( mod.getModuleName(), mod.display() );
        if ( mod instanceof CPSExportable ) {
            debug( "Found exportable module: " + mod.getModuleName() );
            exportables.add( (CPSExportable) mod );
            rebuildExportMenu();
        }
        if ( mod instanceof CPSImportable ) {
            debug( "Found importable module: " + mod.getModuleName() );
            importables.add( (CPSImportable) mod );
            rebuildImportMenu();
        }
        if ( mod instanceof CPSConfigurable ) {
            debug( "Found configurable module: " + mod.getModuleName() );
            this.addModuleConfiguration( (CPSConfigurable) mod );
        }
    }
    
    /**
     * Add a module to the UI.
     */
    public void addModule ( String name, JPanel content ) {

	content.setBorder( BorderFactory.createTitledBorder( name ));
        
//        moduleList.add( new ModuleListElement( name, content ));
        setModulesUpdated( true );
        
        addModules();

    }

    @Override
    public void addModuleConfiguration( CPSConfigurable c ) {
        settings.addModuleConfiguration(c);
        if ( CPSGlobalSettings.getFirstTimeRun() ) {
           CPSWizardPage[] wps = c.getConfigurationWizardPages();
           for ( CPSWizardPage wp : wps )
              if ( wp.getWizardType() == CPSWizardPage.WIZ_TYPE_PRE_INIT )
                 preInitWiz.addWizard( wp );
              else
                 postInitWiz.addWizard( wp );
        }
    }

   @Override
   public void addExporter( CPSExporter ex ) {
      exporters.add(ex);
      rebuildExportMenu();
   }

   @Override
   public void addImporter( CPSImporter im ) {
      importers.add(im);
      rebuildImportMenu();
   }
    
    
    private void addModules() {
       if ( tabbedpane == null || ! areModulesUpdated() )
          return;
       
       Iterator i = moduleList.iterator();
       Object mle;
          
       tabbedpane.removeAll();
          
       while ( i.hasNext() ) {
          mle = i.next();
          if ( mle instanceof  CPSDisplayableDataUserModule ) 
               tabbedpane.addTab( ((CPSDisplayableDataUserModule) mle).getModuleName(),
                                  ((CPSDisplayableDataUserModule) mle).display() );
       }

       finishAddingTabs();
       setModulesUpdated( false );
       
    }
    
    private void finishAddingTabs() {

	tabbedpane.getModel().addChangeListener(
	  new ChangeListener() {
	      public void stateChanged(ChangeEvent e) {
		  SingleSelectionModel model =
		      (SingleSelectionModel) e.getSource();
		  if( model.getSelectedIndex() ==
		      tabbedpane.getTabCount() - 1 ) {
		  }
	      }
	  }
	  );


	fm.getFrame().pack();
    }

    private JMenuBar buildMenuBar() {
       menuBar = new JMenuBar();
       JMenu fileMenu = new JMenu( "File" );
       JMenuItem settingsItem = new JMenuItem( MENU_ITEM_SETTINGS );
       settingsItem.addActionListener( this );
//        settingsItem.setEnabled( false );
        
        if ( exportMenu == null )
            initExportMenu();
       
       if ( importMenu == null )
          initImportMenu();
        
        JMenuItem exitItem = new JMenuItem( MENU_ITEM_EXIT );
        exitItem.addActionListener( this );
       
       fileMenu.add( settingsItem );
       fileMenu.add( exportMenu );
       fileMenu.add( importMenu );
       fileMenu.add( new JSeparator() );
       fileMenu.add( exitItem );
    
       menuBar.add( fileMenu );
       // menuBar.add( Horizontal spacer )
       // menuBar.add( aboutItem );
       
       return menuBar;
    }
    
    private void initExportMenu() {
        
        exportMenu = new JMenu( "Export" );
        exportMenu.setEnabled( false );
        
    }
    
    private void rebuildExportMenu() {
       
       if ( exportMenu == null )
          initExportMenu();
       else
          exportMenu.removeAll();
       
       for ( CPSExporter exER : exporters ) {
          
          
          JMenu exporterMenu = new JMenu( "To " + exER.getExportFileDefaultExtension().toUpperCase() );
          exporterMenu.setEnabled(false);
          
          for ( CPSExportable exABLE : exportables ) {
             
             JMenuItem exportItem = new JMenuItem( exABLE.getExportName() );
             exportItem.setActionCommand( "export-" + 
                                          exER.getExportFileDefaultExtension() + "-" +
                                          exABLE.getExportName() );
             exportItem.addActionListener( this );
          
             exporterMenu.add( exportItem );
             exporterMenu.setEnabled( true );
             
          }
          
          exportMenu.add( exporterMenu );
          exportMenu.setEnabled( true );
          
       }
       
    }
    
    private void initImportMenu() {
     
       importMenu = new JMenu( "Import" );
       importMenu.setEnabled(false);
       
    }
    
    private void rebuildImportMenu() {
       
       initImportMenu();
       
       for ( CPSImporter imER : importers ) {
          
          JMenu importerMenu = new JMenu( "From " + imER.getImportFileDefaultExtension().toUpperCase() );
          importerMenu.setEnabled(false);
          
          for ( CPSImportable imABLE : importables ) {
             
             JMenuItem importItem = new JMenuItem( imABLE.getImportName() );
             importItem.setActionCommand( "import-" + 
                                          imER.getImportFileDefaultExtension() + "-" +
                                          imABLE.getImportName() );
             importItem.addActionListener( this );
        
             importerMenu.add( importItem );
             importerMenu.setEnabled( true );
             
          }
          
          importMenu.add( importerMenu );
          importMenu.setEnabled( true );
          
       }
       
    }
    
    private class ModuleListElement {
       private String name;
       private JPanel content;

       public ModuleListElement( String n, JPanel c ) {
          setName(n);
          setContent(c);
       }
      public String getName() { return name; }
      public void setName(String name) { this.name = name; }
      public JPanel getContent() { return content; }
      public void setContent(JPanel content) { this.content = content; }
       
    }

   public boolean areModulesUpdated() {
      return modulesUpdated;
   }

   public void setModulesUpdated( boolean modulesUpdated ) {
      this.modulesUpdated = modulesUpdated;
   }
   
//   public void revalidate() {
//      tabbedpane.setPreferredSize( tabbedpane.getSize() );
//      fm.revalidate();   
//   }
   
   
   public void actionPerformed( ActionEvent ae ) {
      String action = ae.getActionCommand();

      debug( "caught action: " + action );
                
      
      if      ( action.startsWith( "export-" )) {
//          String exportableName = action.substring( "export-".length(), action.length() );
//          System.out.println("DEBUG(TabbedUI): Looking up exportable: " + exportableName );
//          for ( CPSExportable ex : exportables )
//              if ( ex.getExportName().equalsIgnoreCase( exportableName ) ) {
//                  // export and get out of here
//                  ex.exportData();
//                  return;
//              }
         
          // strip off the leading "export-"
          String exportableName = action.substring( "export-".length(), action.length() );
          
          for ( CPSExporter exER : exporters ) {
             
             if ( exportableName.startsWith( exER.getExportFileDefaultExtension() + "-" ) ) {
          
                // strip off the identifier for this exporter
                exportableName = exportableName.substring( exER.getExportFileDefaultExtension().length() + 1,
                                                           exportableName.length() );
                
                // now look up the correct exportable
                debug( "Looking up exportable: " + exportableName );
                for ( CPSExportable exABLE : exportables )
                   if ( exABLE.getExportName().equalsIgnoreCase( exportableName ) ) {
                      // do the deed and get the hell out of here
                      exABLE.exportData( exER );
                      return;
                   }
             }
          }
      }
      else if ( action.startsWith( "import-" )) {
         
          // strip off the leading "import-"
          String importableName = action.substring( "import-".length(), action.length() );
          
          for ( CPSImporter imER : importers ) {
             
             if ( importableName.startsWith( imER.getImportFileDefaultExtension() + "-" ) ) {
          
                // strip off the identifier for this exporter
                importableName = importableName.substring( imER.getImportFileDefaultExtension().length() + 1,
                                                           importableName.length() );
                
                // now look up the correct importable
                debug( "Looking up importable: " + importableName );
                for ( CPSImportable imABLE : importables )
                   if ( imABLE.getImportName().equalsIgnoreCase( importableName ) ) {
                      // do the deed and get the hell out of here
                      imABLE.importData( imER );
                      return;
                   }
             }
          }
      }
      else if ( action.equalsIgnoreCase( MENU_ITEM_SETTINGS )) {
         settings.setVisible(true);
         dataChanged();
      }
      else if ( action.equalsIgnoreCase( MENU_ITEM_EXIT )) {
         // TODO, save data, settings, etc
         Runtime.getRuntime().exit(0);
      }
      
   }
   
   private void dataChanged() {
       for ( Object c : moduleList ) {
           if ( c instanceof CPSDataModelUser )
               ((CPSDataModelUser) c).dataUpdated();
       }
   }
   
   public void uiChanged() {
      Dimension maxDim = new Dimension( 0, 0 );
      
      for ( Object mle : moduleList ) {
          if ( mle instanceof CPSDisplayableDataUserModule ) {
              Dimension d = ((CPSDisplayableDataUserModule) mle).display().getPreferredSize();
        
              maxDim.setSize( Math.max( maxDim.getWidth(), d.getWidth() ),
                              Math.max( maxDim.getHeight(), d.getHeight() ) );
          }
      }
         
      // TODO calculate the size of this tabbed pane to automatically include the tabs, which
      // is what the 25 is in there for
      maxDim.setSize( maxDim.getWidth(), maxDim.getHeight() + 25 );
      tabbedpane.setPreferredSize( maxDim );
      
      // TODO clean this up, add menubar and titlebar height
      fm.getFrame().setMinimumSize(maxDim);
      fm.uiChanged();
      
      
   }
   
   
   @Override
    public int init() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    protected int saveState() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    @Override
    public int shutdown() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
    
   
}

