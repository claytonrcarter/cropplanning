/* FirstRunDialog.java - created: Feb 13, 2008
 * Copyright (C) 2008 Clayton Carter
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
 * 
 */

package CPS.Core.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class FirstRunDialog extends JDialog implements ActionListener {
    
    String defaultDirectory;
    JPanel jplContents;
    JLabel lblOutDir;
    JFileChooser flchOutDir;
    JButton btnChoose, btnDone;
    
    public FirstRunDialog( String title, String defaultDir ) {
        super( (JFrame) null, title, true );
        
        defaultDirectory = defaultDir;
        buildMainPanel();
        
        add( jplContents );
        pack();
        
    }

    private void buildMainPanel() {
        
        jplContents = new JPanel();
        jplContents.setLayout( new BoxLayout( jplContents, BoxLayout.PAGE_AXIS ) );
        jplContents.setBorder( BorderFactory.createTitledBorder( "Welcome to Crop Planning Software" ));
        
        String s = "";
        s += "<html><center>It appears that this is your first time running<br> ";
        s += "this program.  Please select the <b>folder or directory</b> <br>";
        s += "where you would like this program to store it's data. <br>";
        s += "(<i>You can change this later.</i>)  If this is <b>not</b> the <br>";
        s += "first time you've run this program, then it seems <br>";
        s += "that your settings (but not necessarily your data) <br>";
        s += "have been lost.  Just choose the folder or directory <br>";
        s += "you where you data files are stored.  (The data files  <br>";
        s += "have names that start with \"CPSdb\".)<br><br>";
        s += "";
        s += "</center></html>";
        
        JLabel lblWelcome = new JLabel( s );
        lblWelcome.setAlignmentX( Component.CENTER_ALIGNMENT );
//        lblWelcome.setMaximumSize(   new Dimension( 350, 500 ));
//        lblWelcome.setPreferredSize( new Dimension( 350, 50 ));
        jplContents.add( lblWelcome );
                
        JLabel lblOutDisp = new JLabel( "Output directory:" );
        lblOutDisp.setAlignmentX( Component.RIGHT_ALIGNMENT );
        jplContents.add( lblOutDisp );
        
        flchOutDir = new JFileChooser();
        flchOutDir.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        flchOutDir.setSelectedFile( new File( defaultDirectory ));
        
        lblOutDir = new JLabel( flchOutDir.getSelectedFile().getAbsolutePath() );
        lblOutDir.setAlignmentX( Component.CENTER_ALIGNMENT );
        jplContents.add( lblOutDir );
        
        btnChoose = new JButton( "Choose Output Directory" );
        btnChoose.addActionListener( this );
        btnChoose.setAlignmentX( Component.CENTER_ALIGNMENT );
        jplContents.add( btnChoose );
        
        btnDone = new JButton( "Done" );
        btnDone.addActionListener( this );
        btnDone.setAlignmentX( Component.CENTER_ALIGNMENT );
        jplContents.add( btnDone );
        
    }
    
    public String getOutputDir() {
        return lblOutDir.getText();
    }
    
    public void actionPerformed( ActionEvent arg0 ) {
        String action = arg0.getActionCommand();
       
        if ( action.equalsIgnoreCase( btnChoose.getText() ) ) {
           flchOutDir.setVisible(true);
           int status = flchOutDir.showDialog( jplContents, "Accept" ); 
            if ( status == JFileChooser.APPROVE_OPTION ) {
                lblOutDir.setText( flchOutDir.getSelectedFile().getPath() );   
            }
        }    
        else if ( action.equalsIgnoreCase( btnDone.getText() )) {
            this.setVisible(false);
        }
    }
    
}
