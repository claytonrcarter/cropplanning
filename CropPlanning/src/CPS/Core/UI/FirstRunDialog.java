/* FirstRunDialog.java - created: Feb 13, 2008
 * Copyright (C) 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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

import CPS.UI.Swing.CPSDialog;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FirstRunDialog extends CPSDialog implements ActionListener {
    
    String defaultDirectory;
    JPanel jplContents;
    JLabel lblOutDir;
    JFileChooser flchOutDir;
    JButton btnChoose, btnDone;
    
    public FirstRunDialog( String title, String defaultDir ) {
        
        super( title );
        setTitle( "Welcome to Crop Planning Software" );
        
        setDescription( "It appears that this is your first time running<br>" +
                        "this program.  Please select the folder or directory<br>" +
                        "where you would like this program to store it's<br>" +
                        "data.  (You can change this later.)  If this is<br>" +
                        "not the first time you've run this program, then<br>" +
                        "it seems that your settings (but not necessarily<br>" +
                        "your data) have been lost.  Just choose the folder<br>" +
                        "or directory you where your data files are stored.<br>" +
                        "(The data files have names that start with \"CPSdb\".)" );
        
        defaultDirectory = defaultDir;
//        flchOutDir.setSelectedFile( new File( defaultDirectory ));
//        lblOutDir.setText( flchOutDir.getSelectedFile().getAbsolutePath() );
        
        
    }

    protected void buildContentsPanel() {
        
        jplContents = new JPanel();
        jplContents.setLayout( new BoxLayout( jplContents, BoxLayout.PAGE_AXIS ) );
        jplContents.setBorder( BorderFactory.createEmptyBorder( 0, 10, 0, 10 ) );
        
        JLabel lblOutDisp = new JLabel( "Output directory:" );
        lblOutDisp.setAlignmentX( Component.RIGHT_ALIGNMENT );
        jplContents.add( lblOutDisp );
        
        flchOutDir = new JFileChooser();
        flchOutDir.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        flchOutDir.setSelectedFile( new File( defaultDirectory ));
        
        lblOutDir = new JLabel( flchOutDir.getSelectedFile().getAbsolutePath() );
        lblOutDir.setAlignmentX( Component.CENTER_ALIGNMENT );
        jplContents.add( lblOutDir );

        if ( ! lblOutDir.getText().equals("") )
          btnDone.setEnabled(true);

        add( jplContents );
        
    }
    
    protected void fillButtonPanel() {

        btnChoose = new JButton( "Choose Output Directory" );
        btnDone = new JButton( "Done" );
        btnDone.setEnabled(false);
        
        btnChoose.addActionListener( this );
        btnDone.addActionListener( this );
        
        addButton( btnChoose );
        addButton( btnDone );
        
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
                btnDone.setEnabled(true);
            }
        }    
        else if ( action.equalsIgnoreCase( btnDone.getText() )) {
            this.setVisible(false);
        }
    }
    
    // For testing
    public static void main(String[] args) {        
        new FirstRunDialog( "Title", "." ).setVisible(true);
        System.exit(0);
    }
    
}
