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

import CPS.Data.CPSPlanting;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSModule;
import CPS.UI.Swing.CPSDialog;
import CPS.UI.Swing.LayoutAssist;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class PlanManager extends CPSDialog implements ActionListener {

   private JButton btnNew, btnDelete, btnSave, btnSelect, btnCancel;
   private JComboBox cmboPlanList = null;
   private JTextField tfldDesc;
   private JSpinner spnYear;
   private GregorianCalendar tempCal;
   
   private String selectedPlan = null, oldSelected = null;
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

    cmboPlanList.removeActionListener(this);
    cmboPlanList.removeAllItems();
    for ( String s : listOfValidCropPlans )
      cmboPlanList.addItem( s );
    cmboPlanList.addActionListener(this);

    if ( selectedPlan != null && ! selectedPlan.equals( "" ) &&
         listOfValidCropPlans.contains( selectedPlan ) )
      cmboPlanList.setSelectedItem( selectedPlan );
    else
      cmboPlanList.setSelectedIndex(-1);

   }
   
   private void createPlan() {
//    if ( dm == null ) return;

    CPSBasedOnDialog dia = new CPSBasedOnDialog( getCurrentSelection(),
                                                 listOfValidCropPlans,
                                                 dm );
    dia.setVisible(true);

    if ( dm == null ) return;

    // if they did not Cancel
    if ( dia.selectedCreate() ) {

      // disable the buttons while we work
      btnNew.setEnabled(false);
      btnCancel.setEnabled(false);

      String newPlan = getCurrentSelection();
      // create a blank crop plan no matter what
      dm.createCropPlan( newPlan,
                        getSelectedPlanYear(),
                        getSelectedPlanDescription() );

      // if they didn't select "blank plan"
      if ( ! dia.selectedBlankPlan() ) {

        // get old crop plan
        List<CPSPlanting> ps = dm.getCropPlan( dia.getPlanToBaseOn() );

        // how much to bump to dates
        int y = getSelectedPlanYear() -
                  dm.getCropPlanYear( dia.getPlanToBaseOn() );
        String yearBump = "+" + y + "y";

        // create the new keyword
        String fromTag = dia.getPlanToBaseOn();
        fromTag = "from" + fromTag.replaceAll( "/ /", "" );

        // now add new tag, bump the dates and save the planting in the new plan
        for ( CPSPlanting p : ps ) {
          // add the new tag
          p.setKeywords( p.getKeywords() + " " + fromTag );
          
          // bump the planned dates
          if ( ! p.getDateToPlantPlannedString().equals( "" ) &&
               ! p.getDateToPlantPlannedState().isCalculated() )
            p.setDateToPlantPlanned( p.getDateToPlantPlannedString() + yearBump );
          if ( ! p.getDateToTPPlannedString().equals( "" ) &&
               ! p.getDateToTPPlannedState().isCalculated() )
            p.setDateToTPPlanned( p.getDateToTPPlannedString() + yearBump );
          if ( ! p.getDateToHarvestPlannedString().equals( "" ) &&
               ! p.getDateToHarvestPlannedState().isCalculated() )
            p.setDateToHarvestPlanned( p.getDateToHarvestPlannedString() + yearBump );

          // blank the actual dates
          if ( ! p.getDateToPlantActualString().equals( "" ) )
            p.setDateToPlantActual( "" );
          if ( ! p.getDateToTPActualString().equals( "" ) )
            p.setDateToTPActual( "" );
          if ( ! p.getDateToHarvestActualString().equals( "" ) )
            p.setDateToHarvestActual( "" );

          // mark as not "done"
          p.setDonePlanting(false);
          p.setDoneTP(false);
          p.setDoneHarvest(false);

          // save into the new crop plan
          dm.createPlanting( newPlan, p );
        }

        if ( dia.selectedLockOldPlan() )
          dm.finalizeCropPlan(dia.getPlanToBaseOn());

      }

      selectedPlan = newPlan;
      // these only need to be fiddled with is they did cancel
      updateListOfPlans();
      updateComboBox();
    }
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
      selectedPlan = null;
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
        oldSelected = selectedPlan;
         selectedPlan = (String) cmboPlanList.getSelectedItem();
         setVisible( false ); 
      }
      else if ( source == btnCancel ) {
        // TODO is this right?
         cmboPlanList.setSelectedItem( oldSelected );
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


   class CPSBasedOnDialog extends CPSDialog
                          implements ActionListener,
                                     ItemListener {

     JRadioButton rdoBlank = null, rdoBasedOn;
     JComboBox cmbPlans = null;
     JCheckBox chkLockOld = null;
     JButton btnCreate, btnCancel;

     boolean create = false;

     String newPlan;
     List<String> oldPlans;

     CPSDataModel dm = null;

    public CPSBasedOnDialog( String newPlan,
                             List<String> oldPlans,
                             CPSDataModel dm ) {
      super("Create New Plan");
      setDescription("What kind of plan do you want to create?");

      this.newPlan = newPlan;
      this.oldPlans = oldPlans;
      this.dm = dm;
    }

    public boolean selectedBlankPlan() {
      if ( rdoBlank == null )
        return true;
      else
        return rdoBlank.isSelected();
    }

    public boolean selectedLockOldPlan() {
      if ( chkLockOld == null )
        return false;
      else
        return chkLockOld.isSelected();
    }

    public String getPlanToBaseOn() {
      if ( cmbPlans == null )
        return "";
      else
        return (String) cmbPlans.getItemAt( cmbPlans.getSelectedIndex() );
    }

    public boolean selectedCreate() {
      return create;
    }

    @Override
    protected void buildContentsPanel() {

      rdoBlank = new JRadioButton("a shiney, new, blank crop plan");
      rdoBlank.setSelected(true);
      rdoBasedOn = new JRadioButton("crop plan based on");
      rdoBlank.addItemListener(this);
      rdoBasedOn.addItemListener(this);
      ButtonGroup bg = new ButtonGroup();
      bg.add( rdoBlank );
      bg.add( rdoBasedOn );

      cmbPlans = new JComboBox( 
              oldPlans.toArray( new String[ oldPlans.size() ]) );
      cmbPlans.setEditable(false);
      cmbPlans.setEnabled(false);

      chkLockOld = new JCheckBox("Lock the old plan.");
      chkLockOld.setSelected(false);
      chkLockOld.setToolTipText("If selected, the old plan will be 'locked' so that " +
                                "it can't be editted, but can still be " +
                                "viewed and referenced and used to generate " +
                                "lists." );
      chkLockOld.setEnabled(false);



      JPanel jplCont = new JPanel();
      jplCont.setLayout( new GridBagLayout() );

      JLabel lblBasedOn = new JLabel( "<html><font size=\"-2\">" +
      "If you select the 'based on' option, all of your<br>" +
      "plantings will be copied from the selected plan<br>" +
      "into the new plan with these changes:" +
      "<ol> " +
      "<li>'Planned' dates will be adjusted<br>" +
              "to the selected year." +
      "<li>'Actual' dates (if any) will be blanked." +
      "<li>The 'Done ...' checkboxes will be<br>unselected." +
      "<li>They will all be given a keyword<br>" +
           "of 'from" + newPlan +"' so that you can<br>" +
           "search/filter for them as you<br>update them." +
      "</ol></font></html>");

      JLabel lblLock = new JLabel( "<html><font size=\"-2\">" +
      "If selected, the old plan will be 'locked' so that<br> " +
      "it can't be editted, but can still be viewed and<br>" +
      "used to generate lists and such." +
              "</font></html>" );

      int r = 1;
      LayoutAssist.addButton(         jplCont, 0, r++, 2, 1, rdoBlank );
      LayoutAssist.addButton(         jplCont, 0, r,   rdoBasedOn );
      LayoutAssist.addComboBox(       jplCont, 1, r++, cmbPlans );
      LayoutAssist.addLabelLeftAlign( jplCont, 0, r++, 2, 1, lblBasedOn );
      LayoutAssist.addComponent(      jplCont, 0, r++, 2, 1, chkLockOld,
                                      GridBagConstraints.CENTER );
      LayoutAssist.addLabelLeftAlign( jplCont, 0, r++, 2, 1, lblLock );

      
      jplCont.setBorder( BorderFactory.createEmptyBorder( 10, 10, 0, 10));

      contentsPanelBuilt = true;
      add( jplCont );

    }

    @Override
    protected void fillButtonPanel() {

      btnCreate = new JButton( "Create" );
      btnCancel = new JButton( "Cancel" );

      btnCreate.addActionListener( this );
      btnCancel.addActionListener( this );

      addButton( btnCreate );
      addButton( btnCancel );

    }

    public void actionPerformed(ActionEvent e) {
      Object source = e.getSource();

      if      ( source == btnCreate )
        create = true;
      else if ( source == btnCancel )
        create = false;

      setVisible(false);
    }

    public void itemStateChanged(ItemEvent e) {
      Object source = e.getItemSelectable();

      if ( source == rdoBlank ) {
        cmbPlans.setEnabled(false);
        chkLockOld.setEnabled(false);
      }
      else if ( source == rdoBasedOn ) {
        cmbPlans.setEnabled(true);
        chkLockOld.setEnabled(true);
      }
    }



   }

   
   // For testing.
   public static void main( String[] args ) {
      new PlanManager().setVisible( true );
      System.exit( 0 );
   }
    
}

