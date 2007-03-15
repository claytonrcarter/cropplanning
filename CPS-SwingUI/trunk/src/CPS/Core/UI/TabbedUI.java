package CPS.Core.UI;

import CPS.Module.CPSUIModule;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;

import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.lang.reflect.*;
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

    private static final String windows  =
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    // The current Look & Feel
    private static String currentLookAndFeel = windows;

    // The preferred size of the demo
    private static final int PREFERRED_WIDTH = 720;
    private static final int PREFERRED_HEIGHT = 640;
    
    // Box spacers
    private Dimension HGAP = new Dimension(1,5);
    private Dimension VGAP = new Dimension(5,1);

    // A place to hold on to the visible demo
    // private JPanel contentPane = null;
    JTabbedPane tabbedpane;

    // Used only if swingset is an application 
    private JFrame frame = null;
    private JWindow splashScreen = null;

    // To debug or not to debug, that is the question
    private boolean DEBUG = true;
    private int debugCounter = 0;

    // contentPane cache, saved from the applet or application frame
    Container contentPane = null;

    /**
     * SwingSet2 Constructors
     */
    public TabbedUI() {	
       
       frame = createFrame( GraphicsEnvironment.
	                    getLocalGraphicsEnvironment().
			     getDefaultScreenDevice().
			     getDefaultConfiguration() );
	// showTabbedUI();
    }

    /**
     * Create a frame for SwingSet2 to reside in if brought up
     * as an application.
     */
    public static JFrame createFrame(GraphicsConfiguration gc) {
	JFrame frame = new JFrame(gc);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	WindowListener l = new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	        }
	    };
	frame.addWindowListener(l);
	return frame;
    }

    /**
     * Bring up the SwingSet2 demo by showing the frame (only
     * applicable if coming up as an application, not an applet);
     */
    public void showUI() {
	if(getFrame() != null) {
	    // put swingset in a frame and show it
	    JFrame f = getFrame();
	    f.setTitle(getString( ModuleName ));
	    //f.getContentPane().add(f, BorderLayout.CENTER);

	    // Must use a top level container for contentPane,
	    // not wise to use something like tabbedPane
	    contentPane = new JPanel( new BorderLayout() );
	    f.setContentPane( contentPane );

	    // initialize tabs
	    tabbedpane = new JTabbedPane();
	    tabbedpane.setPreferredSize(new Dimension( PREFERRED_WIDTH,
						       PREFERRED_HEIGHT ));
	    f.getContentPane().add(tabbedpane, BorderLayout.CENTER);

	    finishAddingTab( f );
	    
	    Rectangle screenRect = f.getGraphicsConfiguration().getBounds();
            Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                    f.getGraphicsConfiguration());

            // Make sure we don't place the demo off the screen.
            int centerWidth = screenRect.width < f.getSize().width ?
                    screenRect.x :
                    screenRect.x + screenRect.width/2 -	f.getSize().width/2;
            int centerHeight = screenRect.height < f.getSize().height ?
                    screenRect.y :
                    screenRect.y + screenRect.height/2 - f.getSize().height/2;

            centerHeight = centerHeight < screenInsets.top ?
                    screenInsets.top : centerHeight;

            f.setLocation(centerWidth, centerHeight);
	    f.setVisible( true );
	} 
    }

    /**
     * Add a module to the UI.
     */
    public void addModule ( String name, JPanel content ) {

	content.setBorder( BorderFactory.createTitledBorder( name + 
							     " border" ));
	tabbedpane.addTab( name, content );

	finishAddingTab( getFrame() );

    }

    private void finishAddingTab( JFrame f ) {

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


	f.pack();
    }

    /**
     * Returns the frame instance
     */
    public JFrame getFrame() {
	return frame;
    }

    /**
     * This method returns a string from the demo's resource bundle.
     */
    public String getString(String key) {
	return key;
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

}

