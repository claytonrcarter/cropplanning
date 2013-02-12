/* PlanManager.java - created: Feb 16, 2008
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

package CPS.Core.CropPlans;

import CPS.Module.CPSDataModel;
import CPS.Module.CPSModule;
import CPS.UI.Swing.CPSDialog;
import CPS.UI.Swing.LayoutAssist;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class PlanManager extends CPSDialog implements ActionListener {

   private JButton btnNew, btnDelete, btnSave, btnSelect, btnCancel;
   private JComboBox cmboPlanList = null;
   private JTextField tfldDesc;
   private JSpinner spnYear;
   private GregorianCalendar tempCal;
   
   private String selectedPlan = null;
   private List<String> listOfValidCropPlans;
   static CPSDataModel dm = null;
   
   
   public PlanManager() {
      super( "Manage and Select Crop Plans" );
      setDescription( "Manage crop plans: create, delete, alter properties and select for editting." );
      
      tempCal = new GregorianCalendar();
      listOfValidCropPlans = new ArrayList<String>();
   }
   
   public PlanManager( CPSDataModel dm ) {
      this();
      setDataModel( dm );
   }
   
   
   public void setDataModel( CPSDataModel dm ) {
      this.dm = dm;
      updateListOfPlans();
      CPSModule.debug( "PlanMan", "List of plans: " + listOfValidCropPlans );
   }
   
   
   public void selectPlan( String planName ) {
     if ( listOfValidCropPlans.contains(planName) )
       selectedPlan = planName;
     if ( contentsPanelBuilt )
       cmboPlanList.setSelectedItem( planName );
   }
   public String getSelectedPlanName() {
      return selectedPlan;
   }
   private String getCurrentSelection() {
      return (String) cmboPlanList.getSelectedItem();      
   }
   
   public String getSelectedPlanDescription() {
      return tfldDesc.getText();
   }
   public int getSelectedPlanYear() {
      tempCal.setTime( (Date) spnYear.getValue() );
      return tempCal.get( Calendar.YEAR );
   }
   
   
   private void updateListOfPlans() {
     if ( dm != null )
       listOfValidCropPlans = dm.getListOfCropPlans();
   }
   private void updateComboBox() {

    if ( ! contentsPanelBuilt )
      return;

    String oldSelection = (String) cmboPlanList.getSelectedItem();

    cmboPlanList.removeActionListener(this);
    cmboPlanList.removeAllItems();
    for ( String s : listOfValidCropPlans )
      cmboPlanList.addItem( s );
    cmboPlanList.addActionListener(this);

    if ( oldSelection != null && ! oldSelection.equals( "" ) &&
         listOfValidCropPlans.contains( oldSelection ) )
      cmboPlanList.setSelectedItem( oldSelection );
    else
      cmboPlanList.setSelectedIndex(-1);

   }
   
   private void createPlan() {
      if ( dm != null )
         dm.createCropPlan( getCurrentSelection(),
                            getSelectedPlanYear(),
                            getSelectedPlanDescription() );
      updateListOfPlans();
      updateComboBox();
   }
   private void updatePlan() {
      if ( dm != null )
         dm.updateCropPlan( getCurrentSelection(),
                            getSelectedPlanYear(),
                            getSelectedPlanDescription() );
   }
   private void deletePlan() {
      if ( dm != null )
         dm.deleteCropPlan( getCurrentSelection() );
      updateListOfPlans();
      updateComboBox();
   }

   
   @Override
   public void setVisible( boolean arg0 ) {
     if ( ! contentsPanelBuilt )
       buildContentsPanel();
     
      updateListOfPlans();
      updateComboBox();

      super.setVisible( arg0 );
   }
   
   
   @Override
   protected void buildContentsPanel() {
      
      cmboPlanList = new JComboBox();
      cmboPlanList.setEditable(true);
      cmboPlanList.addActionListener(this);
      
      spnYear = new JSpinner();
      SpinnerDateModel spnMod = new SpinnerDateModel();
      spnMod.setCalendarField( Calendar.YEAR );
      spnYear.setModel( spnMod );
      spnYear.setEditor( new JSpinner.DateEditor( spnYear, "yyyy" ) );

      spnYear.setPreferredSize( new Dimension( 70, 
                                               spnYear.getPreferredSize().height ));
      tfldDesc = new JTextField( 20 );
      
      JPanel jplCont = new JPanel();
      jplCont.setBorder( BorderFactory.createEmptyBorder() );
      jplCont.setLayout( new GridBagLayout() );
        
      LayoutAssist.addLabel(     jplCont, 0, 0, new JLabel( "Plan:" ) );
      LayoutAssist.addComboBox(  jplCont, 1, 0, cmboPlanList );
      LayoutAssist.addLabel(     jplCont, 0, 1, new JLabel( "Year:" ) );
      LayoutAssist.addComponent( jplCont, 1, 1, spnYear );
      LayoutAssist.addLabel(     jplCont, 0, 2, new JLabel( "Description:" ) );
      LayoutAssist.addTextField( jplCont, 1, 2, tfldDesc );      
      
      jplCont.setBorder( BorderFactory.createEmptyBorder( 10, 10, 0, 10));

      contentsPanelBuilt = true;
      add( jplCont );
      
   }

   
   @Override
   protected void fillButtonPanel() {
      
      JPanel jplTop = new JPanel();
      JPanel jplBottom = new JPanel();
      JPanel jplBtns = new JPanel();
      jplTop.setLayout( new FlowLayout( FlowLayout.TRAILING ));
      jplBottom.setLayout( new FlowLayout( FlowLayout.TRAILING ));
      jplBtns.setLayout( new BoxLayout( jplBtns, BoxLayout.PAGE_AXIS ));
      
      
      btnNew = new JButton( "Create" );
      btnNew.setToolTipText( "Create new plan w/ specified name and properties.");
      btnDelete = new JButton( "Delete" );
      btnDelete.setToolTipText( "Delete selected crop plan." );
      btnSave = new JButton( "Save" );
      btnSave.setToolTipText( "Save property changes to selected crop plan." );
      
      btnSelect = new JButton( "Select & View" );
      btnSelect.setToolTipText( "Open the selected crop plan for viewing and editting." );
      btnCancel = new JButton( "Cancel" );
      btnCancel.setToolTipText( "Discard unsaved changes." );
      
      btnNew.addActionListener( this );
      btnDelete.addActionListener( this );
      btnSave.addActionListener( this );
      
      btnSelect.addActionListener( this );
      btnCancel.addActionListener( this );
      
      jplTop.add( btnNew );
      jplTop.add( btnDelete );
      jplTop.add( btnSave );
      jplBottom.add( btnSelect );
      jplBottom.add( btnCancel );
      
      jplBtns.add( jplTop );
      jplBtns.add( jplBottom );
      
      addButton( jplBtns );
      
   }
   
    
   public void actionPerformed( ActionEvent arg0 ) {
      String action = arg0.getActionCommand();
      Object source = arg0.getSource();

      if ( source.getClass().equals( JComboBox.class ))
        CPSModule.debug( "PlanMan",
                         "Action Performed: " + action +
                         " to " + ((JComboBox)source).getSelectedItem() );
      else
        CPSModule.debug( "PlanMan",
                         "Action Performed: " + action +
                         " on " + ((JButton)source).getText() );

      
      if      ( source == btnNew )    { createPlan(); }
      else if ( source == btnDelete ) { deletePlan(); }
      else if ( source == btnSave )   { updatePlan(); }
      else if ( source == btnSelect ) { 
         selectedPlan = (String) cmboPlanList.getSelectedItem();
         setVisible( false ); 
      }
      else if ( source == btnCancel ) {
         cmboPlanList.setSelectedItem( oldSelection );
         setVisible( false );
      }
      else if ( source == cmboPlanList && dm != null ) {
         
         String planName = getCurrentSelection();
         
         if ( listOfValidCropPlans.contains( planName ) ) {
            // the current selection IS a valid plan
            tempCal.set( Calendar.YEAR, dm.getCropPlanYear( planName ) );
            spnYear.setValue( tempCal.getTime() );
         
            tfldDesc.setText( dm.getCropPlanDescription( planName ) );
            
            btnNew.setEnabled(false);
            btnSave.setEnabled(true);
            btnDelete.setEnabled(true);
            btnSelect.setEnabled(true);
         }
         else {
            // the current selection isn't a valid plan
            btnNew.setEnabled(true);
            btnSave.setEnabled(false);
            btnDelete.setEnabled(false);
            btnSelect.setEnabled(false);
         }
         
      }
   
   }
    
   
   // For testing.
   public static void main( String[] args ) {
      new PlanManager().setVisible( true );
      System.exit( 0 );
   }
    
}

