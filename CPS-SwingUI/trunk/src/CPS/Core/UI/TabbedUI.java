package CPS.Core.UI;

import CPS.Module.CPSUIModule;

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


public class TabbedUI extends CPSUIModule {

    String ModuleName = "TabbedUI";
    String ModuleType = "UI";
    String ModuleVersion = ".1";


    // The preferred size of the demo
    private static final int PREFERRED_WIDTH = 720;
    private static final int PREFERRED_HEIGHT = 640;
    

    // A place to hold on to the visible demo
    // private JPanel contentPane = null;
    JTabbedPane tabbedpane;
    FrameManager fm;
    
    private ArrayList<ModuleListElement> moduleList;
    private boolean modulesUpdated;

    // To debug or not to debug, that is the question
    private boolean DEBUG = true;
    private int debugCounter = 0;


    /**
     * SwingSet2 Constructors
     */
    public TabbedUI() {	
       
       fm = new FrameManager();
       moduleList = new ArrayList<ModuleListElement>();
       setModulesUpdated( false );
       
    }

    /**
     * Bring up the SwingSet2 demo by showing the frame (only
     * applicable if coming up as an application, not an applet);
     */
    public void showUI() {
       
       fm.getFrame().setTitle( ModuleName );
       
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

	content.setBorder( BorderFactory.createTitledBorder( name + 
							     " border" ));
        
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

    /**
     * methods required to implement CPSModule
     */
    public String getModuleName() {
	return ModuleName;
    }

    public String getModuleType() {
	return ModuleType;
    }

    public String getModuleVersion() {
	return ModuleVersion;
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
   
   public void revalidate() {
      tabbedpane.setPreferredSize( tabbedpane.getSize() );
      fm.revalidate();   
   }
}

