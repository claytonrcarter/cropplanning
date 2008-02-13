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
import CPS.Module.CPSDisplayableDataUserModule;
import CPS.Module.CPSExportable;
import CPS.Module.CPSUI;

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
    
    protected JMenuBar menuBar;
    protected JMenu exportMenu = null;
    protected ArrayList<CPSExportable> exportables;
    
    private ArrayList<ModuleListElement> moduleList;
    private boolean modulesUpdated;

    // To debug or not to debug, that is the question
    private boolean DEBUG = true;
    private int debugCounter = 0;


    /**
     * SwingSet2 Constructors
     */
    public TabbedUI() {	
       
       setModuleName( "CPS" );
       setModuleType( "UI" );
       setModuleVersion( GLOBAL_DEVEL_VERSION );
       
       fm = new FrameManager();
       moduleList = new ArrayList<ModuleListElement>();
       setModulesUpdated( false );
       
       exportables = new ArrayList<CPSExportable>();
       settings = new SettingsDialog();
       
    }

    /**
     * Bring up the SwingSet2 demo by showing the frame (only
     * applicable if coming up as an application, not an applet);
     */
    public void showUI() {
       
       fm.getFrame().setTitle( getModuleName() );
       
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
    
    public void addModule( CPSDisplayableDataUserModule mod ) {
        addModule( mod.getModuleName(), mod.display() );
        if ( mod instanceof CPSExportable ) {
            System.out.println("DEBUG(TabbedUI): Found exportable module: " + mod.getModuleName() );
            this.addExportMenuItem( (CPSExportable) mod );
        }
        if ( mod instanceof CPSConfigurable ) {
            System.out.println("DEBUG(TabbedUI): Found configurable module: " + mod.getModuleName() );
            this.addModuleConfiguration( (CPSConfigurable) mod );
        }
    }
    
    /**
     * Add a module to the UI.
     */
    public void addModule ( String name, JPanel content ) {

	content.setBorder( BorderFactory.createTitledBorder( name ));
        
        moduleList.add( new ModuleListElement( name, content ));
        setModulesUpdated( true );
        
        addModules();

    }

    public void addModuleConfiguration( CPSConfigurable c ) {
        settings.addModuleConfiguration(c);
    }
    
    private void addModules() {
       if ( tabbedpane == null || ! areModulesUpdated() )
          return;
       
       Iterator i = moduleList.iterator();
       ModuleListElement mle;
          
       tabbedpane.removeAll();
          
       while ( i.hasNext() ) {
          mle = (ModuleListElement) i.next();
          tabbedpane.addTab( mle.getName(), mle.getContent() );
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
        
        JMenuItem exitItem = new JMenuItem( MENU_ITEM_EXIT );
        exitItem.addActionListener( this );
       
       fileMenu.add( settingsItem );
       fileMenu.add( exportMenu );
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
    
    public void addExportMenuItem( CPSExportable ex ) {
        
        JMenuItem exportItem = new JMenuItem( ex.getExportName() );
        exportItem.setActionCommand( "export-" + ex.getExportName() ); 
        exportItem.addActionListener(this);
        
        exportables.add( ex );
        
        if ( exportMenu == null )
            initExportMenu();
        exportMenu.add( exportItem );
        exportMenu.setEnabled(true);
        
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
      
      if ( action.startsWith( "export-" )) {
          String exportableName = action.substring( "export-".length(), action.length() );
          System.out.println("DEBUG(TabbedUI): Looking up exportable: " + exportableName );
          for ( CPSExportable ex : exportables )
              if ( ex.getExportName().equalsIgnoreCase( exportableName ) ) {
                  // export and get out of here
                  ex.exportData();
                  return;
              }
      }
      else if ( action.equalsIgnoreCase( MENU_ITEM_SETTINGS )) {
         settings.setVisible(true);
      }
      else if ( action.equalsIgnoreCase( MENU_ITEM_EXIT )) {
         // TODO, save data, settings, etc
         Runtime.getRuntime().exit(0);
      }
      
   }
   
   public void uiChanged() {
      Dimension maxDim = new Dimension( 0, 0 );
      
      for ( ModuleListElement mle : moduleList ) {
        Dimension d = mle.getContent().getPreferredSize();
        
        maxDim.setSize( Math.max( maxDim.getWidth(),  d.getWidth() ),
                        Math.max( maxDim.getHeight(), d.getHeight() ));
        
      }
         
      // TODO calculate the size of this tabbed pane to automatically include the tabs, which
      // is what the 25 is in there for
      maxDim.setSize( maxDim.getWidth(), maxDim.getHeight() + 25 );
      tabbedpane.setPreferredSize( maxDim );
      
      // TODO clean this up, add menubar and titlebar height
      fm.getFrame().setMinimumSize(maxDim);
      fm.uiChanged();
      
      
   }
   
   
}

