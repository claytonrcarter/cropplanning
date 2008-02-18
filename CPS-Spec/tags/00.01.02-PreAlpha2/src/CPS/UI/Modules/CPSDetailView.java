/* CPSDetailView.java
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

package CPS.UI.Modules;

import CPS.Data.CPSRecord;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelUser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Clayton
 */
public abstract class CPSDetailView extends CPSDataModelUser 
                                    implements ActionListener {
  
   protected final int FIELD_LEN_WAY_LONG = 10;
   protected final int FIELD_LEN_LONG  = 7;
   protected final int FIELD_LEN_MED   = 5;
   protected final int FIELD_LEN_SHORT = 3;
   
    private JPanel mainPanel, jplAboveDetails, jplBelowDetails;
    protected JPanel jplDetails;
   
    private JLabel lblChanges, lblStatus = new JLabel();
    private JButton btnSaveChanges,  btnDiscardChanges;
   
    private boolean displayRecord = false;
   
    protected CPSMasterDetailModule uiManager;
   
    public CPSDetailView ( CPSMasterDetailModule ui ) {
        this( ui, null );
    }
    
    public CPSDetailView( CPSMasterDetailModule ui, String title ) {
        uiManager = ui;
        buildMainPanel( title );
    }
    
    protected boolean isRecordDisplayed() { return displayRecord; }
    protected void setRecordDisplayed() { displayRecord = true; }
    
    public JPanel getJPanel() {
        return getMainPanel();
    }
    protected JPanel getMainPanel() {
        if ( mainPanel == null ) { buildMainPanel( null ); }
        return mainPanel;
    }   
    protected void initMainPanel( String title ) {
        
        mainPanel = new JPanel( new BorderLayout() );
        
        if ( title != null )
            mainPanel.setBorder( BorderFactory.createTitledBorder( title ) );
        else
            mainPanel.setBorder( BorderFactory.createEtchedBorder() );
       
    }
    protected void buildMainPanel( String title ) {
        
        
        if ( ! isRecordDisplayed() ) {
            initMainPanel( title );
            mainPanel.add( lblStatus, BorderLayout.CENTER );    
        }
        else
            rebuildMainPanel();
        
    }
    protected void rebuildMainPanel() {
        mainPanel.removeAll();
        mainPanel.add( getAboveDetailsPanel(), BorderLayout.PAGE_START );
        mainPanel.add( getDetailsPanel(), BorderLayout.CENTER );
        mainPanel.add( getBelowDetailsPanel(), BorderLayout.PAGE_END );
        
        uiManager.signalUIChanged();

    }
   
    protected JPanel getAboveDetailsPanel() {
        if ( jplAboveDetails == null ) { buildAboveDetailsPanel(); }
        return jplAboveDetails;
    }
    protected void initAboveDetailsPanel() {
        jplAboveDetails = new JPanel();
        jplAboveDetails.setLayout( new BoxLayout( jplAboveDetails, BoxLayout.LINE_AXIS ) );    
    }
    protected void buildAboveDetailsPanel() {
        initAboveDetailsPanel();
    }
    
    protected JPanel getBelowDetailsPanel() {
        if ( jplBelowDetails == null ) { buildBelowDetailsPanel(); }
        return jplBelowDetails;       
    }
    protected void initBelowDetailsPanel() {
        jplBelowDetails = new JPanel();
        jplBelowDetails.setLayout( new BoxLayout( jplBelowDetails, BoxLayout.LINE_AXIS ) );
    }
    protected void buildBelowDetailsPanel() {
        
        Insets small = new Insets( 1, 1, 1, 1 );
      
        lblChanges = new JLabel( "Changes: " );
        btnSaveChanges = new JButton( "Save" );
        btnSaveChanges.setMnemonic( java.awt.event.KeyEvent.VK_ENTER );
        btnDiscardChanges = new JButton( "Discard" );
        btnSaveChanges.addActionListener( this );
        btnDiscardChanges.addActionListener( this );
        btnSaveChanges.setMargin( small );
        btnDiscardChanges.setMargin( small );
      
        initBelowDetailsPanel();
        jplBelowDetails.add( lblStatus );
        jplBelowDetails.add( Box.createHorizontalGlue() );
        jplBelowDetails.add( lblChanges );
        jplBelowDetails.add( btnSaveChanges );
        jplBelowDetails.add( btnDiscardChanges );
      
    }
    
    
    protected JPanel getDetailsPanel() {
        if ( jplDetails == null ) { buildDetailsPanel(); }
        return jplDetails;       
    }
    protected abstract void buildDetailsPanel();
    protected void initDetailsPanel() {
       jplDetails = initPanelWithGridBagLayout();
    }
    protected JPanel initPanelWithGridBagLayout() {
       JPanel p  = new JPanel();
       p.setLayout( new GridBagLayout() );
       p.setAlignmentX( JPanel.LEFT_ALIGNMENT );
       p.setAlignmentY( JPanel.TOP_ALIGNMENT );
       return p;
    }
    protected JPanel initPanelWithVerticalBoxLayout() {
       JPanel p  = new JPanel();
       p.setLayout( new BoxLayout( p, BoxLayout.PAGE_AXIS ) );
       p.setAlignmentX( JPanel.LEFT_ALIGNMENT );
       p.setAlignmentY( JPanel.TOP_ALIGNMENT );
       return p;
    }
    
//    public void refreshView() {
//        displayRecord( getDisplayedRecord() );
//    }
    protected void selectRecordInMasterView( int id ) {
        uiManager.selectRecordInMasterView( id );
    }
    public abstract void displayRecord( CPSRecord r );
    public abstract void setForEditting();
    public abstract CPSRecord getDisplayedRecord();
    protected abstract void updateAutocompletionComponents();
   
    protected String getDisplayedTableName() {
        return uiManager.getMasterTableName();
    }
    
    
    @Override
    public void setDataSource(CPSDataModel dm) {
        super.setDataSource(dm);
    }

   protected void setStatus( String s ) {
       if ( s == null )
           s = "";
       if ( s.toLowerCase().indexOf( "status:" ) == -1 && !s.equals( "" ) )
           s = "Status: " + s;
      
       lblStatus.setText( s );
   }    
   
    protected abstract void saveChangesToRecord();
   
    public void actionPerformed( ActionEvent actionEvent ) {
        String action = actionEvent.getActionCommand();
    
        if ( action.equalsIgnoreCase( btnSaveChanges.getText() ) ) {
            if ( ! isDataAvailable() ) {
                System.err.println("ERROR: cannot save changes to record, data unavailable");
                return;
            }
            saveChangesToRecord();
            setStatus("Changes saved.");
        }
        else if ( action.equalsIgnoreCase( btnDiscardChanges.getText() ) ) {
           displayRecord( getDisplayedRecord() );
        }
        
        
    }

}
