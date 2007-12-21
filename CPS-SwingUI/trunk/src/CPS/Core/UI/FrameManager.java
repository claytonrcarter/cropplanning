/*
 * FrameManger.java
 *
 * Created on March 18, 2007, 1:34 PM by Clayton
 */

package CPS.Core.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FrameManager {
   
   private JFrame frame;
   private JPanel contentPane;
   
   public FrameManager() {
      
      frame = createFrame( GraphicsEnvironment.getLocalGraphicsEnvironment().
                           getDefaultScreenDevice().getDefaultConfiguration() );
      
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

    public void buildFrame() {
	if( getFrame() != null ) {
	    // put swingset in a frame and show it
	    JFrame f = getFrame();
	    //f.getContentPane().add(f, BorderLayout.CENTER);

	    // Must use a top level container for contentPane,
	    // not wise to use something like tabbedPane
	    contentPane = new JPanel( new BorderLayout() );
	    f.setContentPane( contentPane );

	    
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
	} 
    }

    void showFrame() {
       getFrame().setVisible(true);
    }

    /**
     * Returns the frame instance
     */
    public JFrame getFrame() {
	return frame;
    }
    
    public void revalidate() {
       frame.validate();
       frame.pack();
    }

}
