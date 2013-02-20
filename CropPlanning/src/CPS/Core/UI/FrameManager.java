/* FrameManger.java - Created: March 18, 2007
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

import CPS.Module.CPSUIChangeListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import net.miginfocom.swing.MigLayout;

public class FrameManager implements CPSUIChangeListener {
   
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
	JFrame f = new JFrame(gc);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	return f;
    }
    
    public void buildFrame() {
	if( getFrame() != null ) {
	    // put swingset in a frame and show it
	    JFrame f = getFrame();
	    //f.getContentPane().add(f, BorderLayout.CENTER);

	    // Must use a top level container for contentPane,
	    // not wise to use something like tabbedPane
	    contentPane = new JPanel( new MigLayout( "",
                                                     "[grow, fill]",
                                                     "[grow, fill]" ));
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

   public void uiChanged() {
      frame.validate();
      frame.pack();
   }

}
