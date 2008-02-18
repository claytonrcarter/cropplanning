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
    FrameManager fm;
    JMenuBar menuBar;
    
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
       setModuleVersion( "0.1" );
       
       fm = new FrameManager();
       moduleList = new ArrayList<ModuleListElement>();
       setModulesUpdated( false );
       
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

    /**
     * Add a module to the UI.
     */
    public void addModule ( String name, JPanel content ) {

	content.setBorder( BorderFactory.createTitledBorder( name ));
        
        moduleList.add( new ModuleListElement( name, content ));
        setModulesUpdated( true );
        
        addModules();

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
       JMenuItem settings = new JMenuItem( MENU_ITEM_SETTINGS );
       settings.addActionListener( this );
       JMenuItem exit = new JMenuItem( MENU_ITEM_EXIT );
       exit.addActionListener( this );
       
       fileMenu.add( settings );
       fileMenu.add( new JSeparator() );
       fileMenu.add( exit );
    
       menuBar.add( fileMenu );
       
       return menuBar;
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
      
      if ( action.equalsIgnoreCase( MENU_ITEM_SETTINGS )) {
         
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

