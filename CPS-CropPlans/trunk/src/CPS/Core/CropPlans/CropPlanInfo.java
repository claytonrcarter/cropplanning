/* CropDBCropInfo.java - created: March 15, 2007
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

package CPS.Core.CropPlans;

import CPS.Data.CPSDateValidator;
import CPS.Data.CPSRecord;
import CPS.UI.Modules.CPSDetailView;
import CPS.Data.CPSPlanting;
import CPS.UI.Modules.CPSMasterDetailModule;
import CPS.UI.Swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;

public class CropPlanInfo extends CPSDetailView implements ActionListener, ItemListener {

   private CPSTextField tfldCropName, tfldVarName, tfldMatDays, tfldLocation;
   private JComboBox cmbDates;
//   private CPSRadioButton rdoDateEff, rdoDatePlan, rdoDateAct;
   private CPSTextField tfldDatePlant, tfldDateTP, tfldDateHarvest;
   private CPSCheckBox chkDonePlant, chkDoneTP, chkDoneHarvest, chkIgnore, chkFrostHardy;
   private CPSRadioButton rdoDS, rdoTP;
   private JLabel lblDateTP, lblTimeToTP, lblFlatSize, lblFlatsNeeded, lblPlantsToStart;
   private CPSTextField tfldMatAdjust, tfldTimeToTP, tfldRowsPerBed, tfldInRowSpace, tfldBetRowSpace,
                        tfldFlatSize, tfldPlantingNotesCrop, tfldPlantingNotes;
   private CPSTextField tfldBedsToPlant, tfldRowFtToPlant, tfldPlantsNeeded,
                        tfldPlantsToStart, tfldFlatsNeeded;
   private CPSTextField tfldYieldPerFt, tfldTotalYield, tfldYieldNumWeeks, tfldYieldPerWeek,
                        tfldCropYieldUnit, tfldCropYieldUnitValue;
   private CPSTextArea tareGroups, tareKeywords, tareOtherReq, tareNotes;
   private CPSTextField tfldCustom1, tfldCustom2, tfldCustom3, tfldCustom4, tfldCustom5;

   private CPSButtonGroup /* bgDates, */ bgSeedMethod;
   private ArrayList<JLabel> anonLabels = new ArrayList<JLabel>();

   private final String DATE_EFFECTIVE = "Effective Dates";
   private final String DATE_ACTUAL    = "Actual Dates";
   private final String DATE_PLANNED   = "Planned Dates";

   private CPSPlanting displayedPlanting;
   
   CropPlanInfo( CPSMasterDetailModule mdm ) {
      super( mdm, "Planting Info" );
   }

   /** this constructor does nothing and is meant for testing purposes only */
   private CropPlanInfo() {}
   
    public CPSRecord getDisplayedRecord() {
       // TODO we should do some double checking in case the displayed info has changed but
       // has not been saved
       
       if ( displayedPlanting == null )
          return new CPSPlanting();
       else
          return displayedPlanting;
    }
   
    public void displayRecord( CPSRecord r ) { displayRecord( (CPSPlanting) r ); }
    public void displayRecord( CPSPlanting p ) {

        displayedPlanting = p;

        if ( ! isRecordDisplayed() ) {
            setRecordDisplayed();
            rebuildMainPanel();
            updateAutocompletionComponents();
        }

       CropPlans.debug( "CropPlanInfo", "Displaying planting:\n" + displayedPlanting );

       tfldCropName.setInitialText( displayedPlanting.getCropName() );
       tfldVarName.setInitialText( displayedPlanting.getVarietyName(),
                                   displayedPlanting.getVarietyNameState() );
       tfldMatDays.setInitialText( displayedPlanting.getMaturityDaysString(),
                                   displayedPlanting.getMaturityDaysState() );
       tfldLocation.setInitialText( displayedPlanting.getLocation(),
                                    displayedPlanting.getLocationState() );

       displayDates();

       chkDonePlant.setInitialState( displayedPlanting.getDonePlanting(),
                                     displayedPlanting.getDonePlantingState() );
       chkDoneTP.setInitialState( displayedPlanting.getDoneTP(),
                                  displayedPlanting.getDoneTPState() );
       chkDoneHarvest.setInitialState( displayedPlanting.getDoneHarvest(),
                                       displayedPlanting.getDoneHarvestState() );

       chkFrostHardy.setInitialState( displayedPlanting.isFrostHardy(),
                                      displayedPlanting.getFrostHardyState() );

       tfldInRowSpace.setInitialText( displayedPlanting.getInRowSpacingString(),
                                      displayedPlanting.getInRowSpacingState() );
       tfldFlatSize.setInitialText( displayedPlanting.getFlatSize(),
                                   displayedPlanting.getFlatSizeState() );
       tfldTimeToTP.setInitialText( displayedPlanting.getTimeToTPString(),
                                   displayedPlanting.getTimeToTPState() );
       tfldPlantingNotes.setInitialText( displayedPlanting.getPlantingNotes(),
                                         displayedPlanting.getPlantingNotesState() );

       tfldBedsToPlant.setInitialText( displayedPlanting.getBedsToPlantString(),
                                       displayedPlanting.getBedsToPlantState() );
       tfldRowFtToPlant.setInitialText( displayedPlanting.getRowFtToPlantString(),
                                        displayedPlanting.getRowFtToPlantState() );
       tfldPlantsNeeded.setInitialText( displayedPlanting.getPlantsNeededString(),
                                        displayedPlanting.getPlantsNeededState() );
       tfldPlantsToStart.setInitialText( displayedPlanting.getPlantsToStartString(),
                                         displayedPlanting.getPlantsToStartState() );
       tfldFlatsNeeded.setInitialText( displayedPlanting.getFlatsNeededString(),
                                       displayedPlanting.getFlatsNeededState() );
       tfldTotalYield.setInitialText( displayedPlanting.getTotalYieldString(),
                                      displayedPlanting.getTotalYieldState() );
       
       tfldYieldPerFt.setInitialText( displayedPlanting.getYieldPerFootString(),
                                      displayedPlanting.getYieldPerFootState() );
       tfldYieldNumWeeks.setInitialText( displayedPlanting.getYieldNumWeeksString(),
                                         displayedPlanting.getYieldNumWeeksState() );
       tfldYieldPerWeek.setInitialText( displayedPlanting.getYieldPerWeekString(),
                                        displayedPlanting.getYieldPerWeekState() );
       tfldCropYieldUnit.setInitialText( displayedPlanting.getCropYieldUnit(),
                                         displayedPlanting.getCropYieldUnitState() );
       tfldCropYieldUnitValue.setInitialText( displayedPlanting.getCropYieldUnitValueString(),
                                              displayedPlanting.getCropYieldUnitValueState() );
       
       tareGroups.setInitialText( displayedPlanting.getGroups(),
                                   displayedPlanting.getGroupsState() );
       tareOtherReq.setInitialText( displayedPlanting.getOtherRequirments(),
                                    displayedPlanting.getOtherRequirmentsState() );
       tareKeywords.setInitialText( displayedPlanting.getKeywords(),
                                    displayedPlanting.getKeywordsState());
       tareNotes.setInitialText( displayedPlanting.getNotes(),
                                 displayedPlanting.getNotesState());

       tfldCustom1.setInitialText( displayedPlanting.getCustomField1(), displayedPlanting.getCustomField1State() );
       tfldCustom2.setInitialText( displayedPlanting.getCustomField2(), displayedPlanting.getCustomField2State() );
       tfldCustom3.setInitialText( displayedPlanting.getCustomField3(), displayedPlanting.getCustomField3State() );
       tfldCustom4.setInitialText( displayedPlanting.getCustomField4(), displayedPlanting.getCustomField4State() );
       tfldCustom5.setInitialText( displayedPlanting.getCustomField5(), displayedPlanting.getCustomField5State() );
   
       /*
        * These all affect how other things are displayed so we should do them last.
        */
       if ( displayedPlanting.isDirectSeeded() )
          bgSeedMethod.setInitialSelection( rdoDS, true, displayedPlanting.getDirectSeededState() );
       else
          bgSeedMethod.setInitialSelection( rdoTP, true, displayedPlanting.getDirectSeededState() );

       displayDSTPProperties();

       chkIgnore.setInitialState( displayedPlanting.getIgnore(),
                                  displayedPlanting.getIgnoreState() );
       

        setStatus("");
        if ( ! displayedPlanting.isSingleRecord() ) {
           String ids = "";
           for ( Integer i : displayedPlanting.getCommonIDs() )
              ids += i.toString() + ", ";
           ids = ids.substring( 0, ids.lastIndexOf(", ") );
           setStatus( "Displaying common data for records: " + ids );
       }        
           
    }

    @Override
    public void setForEditting() {
        tfldCropName.requestFocus();
    }
   
    
   
    protected void saveChangesToRecord() {
       String selectedPlan = getDisplayedTableName();
       CPSPlanting diff = (CPSPlanting) displayedPlanting.diff( this.asPlanting() );
       if ( diff.getID() == -1 ) {
          CropPlans.debug( "CPInfo", "no changes found (Invalid id) ... not saving;" );
          return;
       }
       
       CropPlans.debug( "CPInfo", "changes found (Valid id) ... attempting to save changes");
       if ( ! displayedPlanting.isSingleRecord() )
          getDataSource().updatePlantings( selectedPlan, diff, displayedPlanting.getCommonIDs() );
       else
          getDataSource().updatePlanting( selectedPlan, diff );
       
       selectRecordInMasterView( displayedPlanting.getID() );
    }

   /** asPlanting - create a planting data struct to represent this detail view
    * 
    * @return a CPSPlanting object populated to represent the planting which is
    * currently displayed
    */
    public CPSPlanting asPlanting() {
      
       CPSPlanting p = new CPSPlanting();

       // ALLOW_NULL is used for values which can be inherited (potentially)
       // this forces the program to save "blank" values as null, enabling
       // inheritance
       boolean ALLOW_NULL = true;
      
       p.setID( displayedPlanting.getID() );
      
       if ( tfldCropName.hasChanged() ) p.setCropName( tfldCropName.getText() );
       if ( tfldVarName.hasChanged() ) p.setVarietyName( tfldVarName.getText(), ALLOW_NULL );
       if ( tfldMatDays.hasChanged() ) p.setMaturityDays( tfldMatDays.getText(), ALLOW_NULL );
       if ( tfldLocation.hasChanged() ) p.setLocation( tfldLocation.getText(), ALLOW_NULL );

       /* We do not need to store the value of the "date radio buttons"
        * group because that button group only acts to control which dates
        * are displayed.  We *do* have to check those radiobuttons to
        * determine how to store any values that the user has changed.
        */
       String s = (String) cmbDates.getSelectedItem();
       if ( tfldDatePlant.hasChanged() )
          if ( s.equalsIgnoreCase( DATE_ACTUAL ))
             p.setDateToPlantActual( tfldDatePlant.getText(), ALLOW_NULL );
          else if ( s.equalsIgnoreCase( DATE_PLANNED ))
             p.setDateToPlantPlanned( tfldDatePlant.getText(), ALLOW_NULL );
          // else do nothing for "effective" dates

       if ( tfldDateTP.hasChanged() )
          if ( s.equalsIgnoreCase( DATE_ACTUAL ))
             p.setDateToTPActual( tfldDateTP.getText(), ALLOW_NULL );
          else if ( s.equalsIgnoreCase( DATE_PLANNED ))
             p.setDateToTPPlanned( tfldDateTP.getText(), ALLOW_NULL );
          // else do nothing for "effective" dates

       if ( tfldDateHarvest.hasChanged() )
          if ( s.equalsIgnoreCase( DATE_ACTUAL ))
             p.setDateToHarvestPlanned( tfldDateHarvest.getText(), ALLOW_NULL );
          else if ( s.equalsIgnoreCase( DATE_PLANNED ))
             p.setDateToHarvestPlanned( tfldDateHarvest.getText(), ALLOW_NULL );
          // else do nothing for "effective" dates

       if ( chkDonePlant.hasChanged() )   p.setDonePlanting( chkDonePlant.isSelected() );
       if ( chkDoneTP.hasChanged() )      p.setDoneTP(       chkDoneTP.isSelected() );
       if ( chkDoneHarvest.hasChanged() ) p.setDoneHarvest(  chkDoneHarvest.isSelected() );
       if ( chkIgnore.hasChanged() )      p.setIgnore(       chkIgnore.isSelected() );

//       if ( rdoDS.hasChanged() || rdoTP.hasChanged() ) p.setDirectSeeded( rdoDS.isSelected() );
       p.setDirectSeeded( rdoDS.isSelected() );
       
       if ( tfldMatAdjust.hasChanged() ) p.setMatAdjust( tfldMatAdjust.getText(), ALLOW_NULL );  
       if ( tfldTimeToTP.hasChanged() ) p.setTimeToTP( tfldTimeToTP.getText(), ALLOW_NULL );
       if ( tfldRowsPerBed.hasChanged() ) p.setRowsPerBed( tfldRowsPerBed.getText(), ALLOW_NULL );
       if ( tfldInRowSpace.hasChanged() ) p.setInRowSpacing( tfldInRowSpace.getText(), ALLOW_NULL );
       if ( tfldBetRowSpace.hasChanged() ) p.setRowSpacing( tfldBetRowSpace.getText(), ALLOW_NULL );
       if ( tfldFlatSize.hasChanged() ) p.setFlatSize( tfldFlatSize.getText(), ALLOW_NULL );
       if ( tfldPlantingNotesCrop.hasChanged() ) p.setPlantingNotesInherited( tfldPlantingNotesCrop.getText(), ALLOW_NULL );
       if ( tfldPlantingNotes.hasChanged() ) p.setPlantingNotes( tfldPlantingNotes.getText(), ALLOW_NULL );

       if ( tfldBedsToPlant.hasChanged() ) p.setBedsToPlant( tfldBedsToPlant.getText(), ALLOW_NULL );
       if ( tfldRowFtToPlant.hasChanged() ) p.setRowFtToPlant( tfldRowFtToPlant.getText(), ALLOW_NULL );
       if ( tfldPlantsNeeded.hasChanged() ) p.setPlantsNeeded( tfldPlantsNeeded.getText(), ALLOW_NULL );
       if ( tfldPlantsToStart.hasChanged() ) p.setPlantsToStart( tfldPlantsToStart.getText(), ALLOW_NULL );
       if ( tfldFlatsNeeded.hasChanged() ) p.setFlatsNeeded( tfldFlatsNeeded.getText(), ALLOW_NULL );
       if ( tfldYieldPerFt.hasChanged() ) p.setYieldPerFoot( tfldYieldPerFt.getText(), ALLOW_NULL );      
       if ( tfldTotalYield.hasChanged() ) p.setTotalYield( tfldTotalYield.getText(), ALLOW_NULL );
       
       if ( tfldYieldNumWeeks.hasChanged() ) p.setYieldNumWeeks( tfldYieldNumWeeks.getText(), ALLOW_NULL );
       if ( tfldYieldPerWeek.hasChanged() ) p.setYieldPerWeek( tfldYieldPerWeek.getText(), ALLOW_NULL );
       if ( tfldCropYieldUnit.hasChanged() ) p.setCropYieldUnit( tfldCropYieldUnit.getText(), ALLOW_NULL );
       if ( tfldCropYieldUnitValue.hasChanged() ) p.setCropYieldUnitValue( tfldCropYieldUnitValue.getText(), ALLOW_NULL );

       if ( tareGroups.hasChanged() ) p.setGroups( tareGroups.getText(), ALLOW_NULL );
       if ( tareOtherReq.hasChanged() ) p.setOtherRequirements( tareOtherReq.getText(), ALLOW_NULL );
       if ( tareKeywords.hasChanged() ) p.setKeywords( tareKeywords.getText(), ALLOW_NULL );
       if ( tareNotes.hasChanged() ) p.setNotes( tareNotes.getText( ) );

       if ( tfldCustom1.hasChanged() ) p.setCustomField1( tfldCustom1.getText(), ALLOW_NULL );
       if ( tfldCustom2.hasChanged() ) p.setCustomField2( tfldCustom2.getText(), ALLOW_NULL );
       if ( tfldCustom3.hasChanged() ) p.setCustomField3( tfldCustom3.getText(), ALLOW_NULL );
       if ( tfldCustom4.hasChanged() ) p.setCustomField4( tfldCustom4.getText(), ALLOW_NULL );
       if ( tfldCustom5.hasChanged() ) p.setCustomField5( tfldCustom5.getText(), ALLOW_NULL );

       CropPlans.debug( "CPInfo", "panel display represents: " + p.toString() );

       return p;
      
   }
   
   protected void buildDetailsPanel() {
       
      ArrayList<String> names = new ArrayList<String>();
      if ( isDataAvailable() )
         names = getDataSource().getCropNameList();

      tfldCropName = new CPSTextField( FIELD_LEN_LONG, names, CPSTextField.MATCH_STRICT );
      tfldVarName = new CPSTextField( FIELD_LEN_LONG );
      tfldMatDays = new CPSTextField( FIELD_LEN_SHORT );
      tfldLocation = new CPSTextField( FIELD_LEN_LONG );

      cmbDates = new JComboBox( new String[] { DATE_EFFECTIVE, DATE_ACTUAL, DATE_PLANNED } );
      cmbDates.addActionListener( this );

      tfldDatePlant = new CPSTextField( FIELD_LEN_LONG );
      tfldDateTP = new CPSTextField( FIELD_LEN_LONG );
      tfldDateHarvest = new CPSTextField( FIELD_LEN_LONG );

      chkDonePlant   = new CPSCheckBox( "", false );
      chkDonePlant.setToolTipText( "Check if planted" );
      chkDonePlant.addItemListener( this );
      chkDoneTP      = new CPSCheckBox( "", false );
      chkDoneTP.setToolTipText( "Check if transplanted (if applicable)" );
      chkDoneTP.addItemListener( this );
      chkDoneHarvest = new CPSCheckBox( "", false );
      chkDoneHarvest.setToolTipText( "Check if harvested" );
      chkDoneHarvest.addItemListener( this );

      chkIgnore = new CPSCheckBox( "Ignore this planting?", false );
      chkIgnore.setToolTipText( "Ignore or skip this planting.  Hides it from view without deleting it." );
      chkIgnore.addItemListener( this );
      chkFrostHardy = new CPSCheckBox( "Frost hardy?", false );

      rdoDS = new CPSRadioButton( "DS", false );
      rdoTP = new CPSRadioButton( "TP", false );
      rdoDS.addItemListener( this );
      rdoTP.addItemListener( this );
      bgSeedMethod = new CPSButtonGroup( new AbstractButton[] { rdoDS, rdoTP } );
      bgSeedMethod.setSelectionModel( CPSButtonGroup.SELECT_ONLY_ONE );

      tfldMatAdjust = new CPSTextField( FIELD_LEN_SHORT );
      tfldRowsPerBed = new CPSTextField( FIELD_LEN_SHORT );
      tfldInRowSpace = new CPSTextField( FIELD_LEN_SHORT );
      tfldBetRowSpace = new CPSTextField( FIELD_LEN_SHORT );
      tfldTimeToTP = new CPSTextField( FIELD_LEN_SHORT );
      tfldFlatSize = new CPSTextField( FIELD_LEN_MED );
      tfldPlantingNotesCrop = new CPSTextField( FIELD_LEN_LONG );
      tfldPlantingNotes = new CPSTextField( FIELD_LEN_LONG );

      tfldBedsToPlant = new CPSTextField( FIELD_LEN_SHORT );
      tfldRowFtToPlant = new CPSTextField( FIELD_LEN_MED );
      tfldPlantsNeeded = new CPSTextField( FIELD_LEN_MED );
      tfldPlantsToStart = new CPSTextField( FIELD_LEN_MED );
      tfldFlatsNeeded = new CPSTextField( FIELD_LEN_SHORT );
      tfldTotalYield = new CPSTextField( FIELD_LEN_MED );

      tfldYieldPerFt = new CPSTextField( FIELD_LEN_SHORT );
      tfldYieldNumWeeks = new CPSTextField( FIELD_LEN_SHORT );
      tfldYieldPerWeek = new CPSTextField( FIELD_LEN_SHORT );
      tfldCropYieldUnit = new CPSTextField( FIELD_LEN_MED );
      tfldCropYieldUnitValue = new CPSTextField( FIELD_LEN_SHORT );
      
      tareGroups = new CPSTextArea( 3, FIELD_LEN_WAY_LONG );
      tareKeywords = new CPSTextArea( 3, FIELD_LEN_WAY_LONG );
      tareOtherReq = new CPSTextArea( 3, FIELD_LEN_WAY_LONG );
      tareNotes = new CPSTextArea( 5, 40 );

      tfldCustom1 = new CPSTextField( FIELD_LEN_LONG );
      tfldCustom2 = new CPSTextField( FIELD_LEN_LONG );
      tfldCustom3 = new CPSTextField( FIELD_LEN_LONG );
      tfldCustom4 = new CPSTextField( FIELD_LEN_LONG );
      tfldCustom5 = new CPSTextField( FIELD_LEN_LONG );


      JPanel columnOne = initPanelWithVerticalBoxLayout();
      JPanel columnTwo = initPanelWithVerticalBoxLayout();
      JPanel columnThree = initPanelWithVerticalBoxLayout();
      JPanel columnFour = initPanelWithVerticalBoxLayout();

      JLabel tempLabel;

      /* the format for these calls is: panel, column, row, component */
      /* ***********************************/
      /* COLUMN ONE                        */
      /* ***********************************/
      JPanel jplName = initPanelWithGridBagLayout();
      jplName.setBorder( BorderFactory.createEmptyBorder() );

      int r = 0;
      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, r, "Crop Name:" ));
      LayoutAssist.addTextField( jplName, 1, r++, tfldCropName );

      anonLabels.add(LayoutAssist.createLabel(  jplName, 0, r, "Variety:" ));
      LayoutAssist.addTextField( jplName, 1, r++, tfldVarName );
      
      anonLabels.add(LayoutAssist.createLabel(  jplName, 0, r, "Maturity Days:" ));
      LayoutAssist.addTextField( jplName, 1, r++, tfldMatDays );

      anonLabels.add(LayoutAssist.createLabel(  jplName, 0, r, "Location" ));
      LayoutAssist.addTextField( jplName, 1, r++, tfldLocation);
      
      
      JPanel jplDates = initPanelWithGridBagLayout();
      jplDates.setBorder( BorderFactory.createTitledBorder( "Dates & Completed" ) );
      r=0;

      tempLabel = LayoutAssist.createLabel( jplDates, 0, r, "Display" );
      tempLabel.setToolTipText( "Select which dates to display" );
      anonLabels.add( tempLabel );
      LayoutAssist.addComponent( jplDates, 1, r++, 2, 1, cmbDates );
//      LayoutAssist.addButtonRightAlign( jplDates, 1, r++, rdoDateEff);
//      LayoutAssist.addButtonRightAlign( jplDates, 1, r++, rdoDateAct);
//      LayoutAssist.addButtonRightAlign( jplDates, 1, r++, rdoDatePlan);

//      tempLabel = LayoutAssist.createLabel( jplDates, 2, r++, "Done?" );
//      tempLabel.setToolTipText( "Check if completed" );

      anonLabels.add( LayoutAssist.createLabel(  jplDates, 0, r, "Planting" ));
      LayoutAssist.addTextField( jplDates, 1, r, tfldDatePlant );
      LayoutAssist.addButton(    jplDates, 2, r++, chkDonePlant );

      lblDateTP = LayoutAssist.createLabel(  jplDates, 0, r, "Transplant" );
      lblDateTP.setToolTipText( "Transplanting" );
      LayoutAssist.addTextField( jplDates, 1, r, tfldDateTP);
      LayoutAssist.addButton(    jplDates, 2, r++, chkDoneTP );

      anonLabels.add( LayoutAssist.createLabel(  jplDates, 0, r, "Harvest" ));
      LayoutAssist.addTextField( jplDates, 1, r, tfldDateHarvest );
      LayoutAssist.addButton(    jplDates, 2, r++, chkDoneHarvest );

      LayoutAssist.addButton( jplDates, 1, r, 2, 1, chkIgnore );
      
//      JPanel jplSucc = initPanelWithGridBagLayout();
//      jplSucc.setBorder( BorderFactory.createTitledBorder( "Successions" ) );
//
//      r=0;
//      LayoutAssist.createLabel(  jplSucc, 0, r, "Part of a succ.?:" );
//      LayoutAssist.addTextField( jplSucc, 1, r++, new JTextField(1) );
//
//      LayoutAssist.createLabel(  jplSucc, 0, r, "Frequency" );
//      LayoutAssist.addTextField( jplSucc, 1, r++, new JTextField(1) );
//
//      LayoutAssist.createLabel(  jplSucc, 0, r, "Succession group" );
//      LayoutAssist.addTextField( jplSucc, 1, r++, new JTextField(1) );
//
//      LayoutAssist.createLabel(  jplSucc, 0, r++, "BTN: Show only this group" );
//      LayoutAssist.createLabel(  jplSucc, 0, r++, "BTN: Jump to next/last" );


      LayoutAssist.addPanelToColumn( columnOne, jplName );
//      LayoutAssist.addPanelToColumn( columnOne, jplSucc );
      LayoutAssist.addPanelToColumn( columnOne, jplDates );
      LayoutAssist.finishColumn( columnOne );
      
      /* ***********************************/
      /* COLUMN TWO (really two and three) */
      /* ***********************************/

      JPanel jplPlanting = initPanelWithGridBagLayout();
      jplPlanting.setBorder( BorderFactory.createTitledBorder( "Planting Info" ) );
      r=0;

      LayoutAssist.addButtonRightAlign( jplPlanting, 0, r, rdoDS );
      LayoutAssist.addButton(           jplPlanting, 1, r++, rdoTP );

      tempLabel = LayoutAssist.createLabel(  jplPlanting, 0, r, "Mat. adj" );
      tempLabel.setToolTipText( "Adjust maturity by so many days" );
      anonLabels.add( tempLabel );
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldMatAdjust );

      anonLabels.add( LayoutAssist.createLabel(  jplPlanting, 0, r, "Rows/Bed" ));
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldRowsPerBed );
      
      anonLabels.add( LayoutAssist.createLabel(  jplPlanting, 0, r, "Row Spacing" ));
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldBetRowSpace );
      
      anonLabels.add( LayoutAssist.createLabel(  jplPlanting, 0, r, "Plant Spacing (in row)" ));
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldInRowSpace );
      
      LayoutAssist.addSeparator( jplPlanting, 0, r++, 2);
      
      lblFlatSize = LayoutAssist.createLabel(  jplPlanting, 0, r, "Flat Size" );
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldFlatSize );
      
      lblTimeToTP = LayoutAssist.createLabel(  jplPlanting, 0, r, "Weeks to TP" );
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldTimeToTP );
      
      LayoutAssist.addSeparator( jplPlanting, 0, r++, 2);
      
      anonLabels.add( LayoutAssist.createLabel(  jplPlanting, 0, r, "Crop Notes" ));
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldPlantingNotesCrop );
      
      anonLabels.add( LayoutAssist.createLabel(  jplPlanting, 0, r, "Planting Notes" ));
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldPlantingNotes );
      
      LayoutAssist.addPanelToColumn( columnTwo, jplPlanting );
      
      
      JPanel jplAdjust = initPanelWithGridBagLayout();
      jplAdjust.setBorder( BorderFactory.createTitledBorder( "Mat. Adjust" ) );

      r=0;
      
//      LayoutAssist.createLabel(  jplAdjust, 0, r, "Adj. for planting meth." );
//      LayoutAssist.addTextField( jplAdjust, 1, r++, tfldMatAdjustPlanting );
//
//      LayoutAssist.createLabel(  jplAdjust, 0, r, "Misc. adj." );
//      LayoutAssist.addTextField( jplAdjust, 1, r++, tfldMatAdjustMisc );
      
      // TODO uncomment line below to add Mat Adjust panel back into the mix
//      LayoutAssist.addPanelToColumn( columnTwo, jplAdjust );
      LayoutAssist.finishColumn( columnTwo );
      
      
      /* *************************************/
      /* COLUMN THREE (really four and five) */
      /* *************************************/
      JPanel jplAmount = initPanelWithGridBagLayout();
      jplAmount.setBorder( BorderFactory.createTitledBorder( "How Much" ) );

      r=0;
      anonLabels.add( LayoutAssist.createLabel(  jplAmount, 0, r, "Beds to Plant" ));
      LayoutAssist.addTextField( jplAmount, 1, r++, tfldBedsToPlant );
      
      anonLabels.add( LayoutAssist.createLabel(  jplAmount, 0, r, "Row Ft to Plant" ));
      LayoutAssist.addTextField( jplAmount, 1, r++, tfldRowFtToPlant );
      
      anonLabels.add( LayoutAssist.createLabel(  jplAmount, 0, r, "Plants Needed" ));
      LayoutAssist.addTextField( jplAmount, 1, r++, tfldPlantsNeeded );
      
      lblPlantsToStart = LayoutAssist.createLabel(  jplAmount, 0, r, "Plants to Start" );
      LayoutAssist.addTextField( jplAmount, 1, r++, tfldPlantsToStart );
      
      lblFlatsNeeded = LayoutAssist.createLabel(  jplAmount, 0, r, "Flats to Start" );
      LayoutAssist.addTextField( jplAmount, 1, r++, tfldFlatsNeeded );
      
      LayoutAssist.addPanelToColumn( columnThree, jplAmount );
      
      JPanel jplYield = initPanelWithGridBagLayout();
      jplYield.setBorder( BorderFactory.createTitledBorder( "Yield Info" ) );

      r=0;
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, r, "Total Yield" ));
      LayoutAssist.addTextField( jplYield, 1, r++, tfldTotalYield );

      /* unit, per foot, weeks, per week, value */
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, r, "Yield Units" ));
      LayoutAssist.addTextField( jplYield, 1, r++, tfldCropYieldUnit );
      
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, r, "Total Yield/Ft" ));
      LayoutAssist.addTextField( jplYield, 1, r++, tfldYieldPerFt );
      
//      LayoutAssist.createLabel(  jplYield, 0, r, "Weeks of Yield" );
//      LayoutAssist.addTextField( jplYield, 1, r++, tfldYieldNumWeeks );
      
//      LayoutAssist.createLabel(  jplYield, 0, r, "Yield/Week" );
//      LayoutAssist.addTextField( jplYield, 1, r++, tfldYieldPerWeek );
      
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, r, "Value/Unit" ));
      LayoutAssist.addTextField( jplYield, 1, r++, tfldCropYieldUnitValue );
      
      LayoutAssist.addPanelToColumn( columnThree, jplYield );
      LayoutAssist.finishColumn( columnThree );
      
      /* *************************************/
      /* COLUMN FOUR (actually six and seven */
      /* *************************************/
      JPanel jplMisc = initPanelWithGridBagLayout();
      jplMisc.setBorder( BorderFactory.createTitledBorder( "Misc Info" ) );

      r=0;
      tempLabel = LayoutAssist.createLabel( jplMisc, 0, r, "Groups:" );
      tempLabel.setToolTipText( "Groups to which this planting belongs" );
      anonLabels.add( tempLabel );
      LayoutAssist.addTextArea( jplMisc, 1, r, 1, 1, tareGroups );

      tempLabel = LayoutAssist.createLabel(  jplMisc, 0, r+=3, "Other Req" );
      tempLabel.setToolTipText( "Other Requirements" );
      anonLabels.add( tempLabel );
      LayoutAssist.addTextArea(  jplMisc, 1, r, 1, 1, tareOtherReq );

      anonLabels.add( LayoutAssist.createLabel(  jplMisc, 0, r+=3, "Keywords:" ));
      LayoutAssist.addTextArea(  jplMisc, 1, r, 1, 1, tareKeywords );
      
      LayoutAssist.addPanelToColumn( columnFour, jplMisc );
      LayoutAssist.finishColumn( columnFour );
      
      /* *************************************/
      /* BOTTOW ROW                          */
      /* *************************************/
      // Notes TextArea is set to span all remaining columns
      // TODO
//      LayoutAssist.createLabel(  jplDetails, 0, 13, "Notes:" );
//      LayoutAssist.addTextArea(  jplDetails, 1, 13, 7, tareNotes );
        
      initDetailsPanel();
      
      LayoutAssist.addSubPanel( jplDetails, 0, 0, 1, 1, columnOne );
      LayoutAssist.addSubPanel( jplDetails, 1, 0, 1, 1, columnTwo );
      LayoutAssist.addSubPanel( jplDetails, 2, 0, 1, 1, columnThree );
      LayoutAssist.addSubPanel( jplDetails, 3, 0, 1, 1, columnFour );

      if ( uiManager != null )
         uiManager.signalUIChanged();
   }

   @Override
   protected void buildBelowDetailsPanel() {
      super.buildBelowDetailsPanel();
      btnSaveChanges.setText( "Save & Recalculate" );
   }
   
   protected void updateAutocompletionComponents() {
       tfldCropName.updateAutocompletionList( getDataSource().getCropNameList(),
                                              CPSTextField.MATCH_PERMISSIVE );
       tfldVarName.updateAutocompletionList( getDataSource().getVarietyNameList( displayedPlanting.getCropName(), getDisplayedTableName() ),
                                             CPSTextField.MATCH_PERMISSIVE );
       tfldLocation.updateAutocompletionList( getDataSource().getFieldNameList( this.getDisplayedTableName() ),
                                              CPSTextField.MATCH_PERMISSIVE );
       tfldFlatSize.updateAutocompletionList( getDataSource().getFlatSizeList( this.getDisplayedTableName() ),
                                              CPSTextField.MATCH_PERMISSIVE );
   }
   
   @Override
   public void dataUpdated() {
      if ( isRecordDisplayed() ) {
         this.displayRecord( getDataSource().getPlanting( getDisplayedTableName(),
                                                          getDisplayedRecord().getID() ) );
         updateAutocompletionComponents();
      }
   }

    @Override
    protected int saveState() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
    
   // Implemented as per ItemListener
   public void itemStateChanged ( ItemEvent arg0 ) {

      Object source = arg0.getItemSelectable();

      if ( source == rdoDS ) {
         setTPComponentsEnabled( ! rdoDS.isSelected() );
         // redisplay the DS/TP values
         displayDSTPProperties();
      }
      else if ( source == rdoTP ) {
         setTPComponentsEnabled( rdoTP.isSelected() );
         // redisplay the DS/TP values
         displayDSTPProperties();
      }
      else if ( source == chkIgnore ) {
         setAllComponentsEnabled( ! chkIgnore.isSelected() );
      }
      else if ( source == chkDonePlant ) {
         // IF  the actual dates are displayed
         // AND the textbox is blank OR the textbox is calculated
         if ( ((String) cmbDates.getSelectedItem()).equalsIgnoreCase( DATE_ACTUAL ) &&
               ( tfldDatePlant.getText().equalsIgnoreCase( "" ) ||
                 ! displayedPlanting.getDateToPlantState().isCalculated() ))
            tfldDatePlant.setText( CPSDateValidator.format( new java.util.Date() ));
      }
      else if ( source == chkDoneTP ) {
         // IF  the actual dates are displayed
         // AND the textbox is blank OR the textbox is calculated
         if ( ((String) cmbDates.getSelectedItem()).equalsIgnoreCase( DATE_ACTUAL ) &&
               ( tfldDateTP.getText().equalsIgnoreCase( "" ) || 
                 ! displayedPlanting.getDateToTPState().isCalculated() )) 
            tfldDateTP.setText( CPSDateValidator.format( new java.util.Date() ));
      }
      else if ( source == chkDoneHarvest ) {
         // IF  the actual dates are displayed
         // AND the textbox is blank OR the textbox is calculated
         if ( ((String) cmbDates.getSelectedItem()).equalsIgnoreCase( DATE_ACTUAL ) &&
               ( tfldDateHarvest.getText().equalsIgnoreCase( "" ) ||
                 ! displayedPlanting.getDateToHarvestState().isCalculated() ))
            tfldDateHarvest.setText( CPSDateValidator.format( new java.util.Date() ));
      }

   }

   private void setTPComponentsEnabled( boolean b ) {
      lblDateTP.setEnabled( b );
      lblTimeToTP.setEnabled( b );
      lblFlatSize.setEnabled( b );
      lblFlatsNeeded.setEnabled( b );
      lblPlantsToStart.setEnabled( b );

      tfldDateTP.setEnabled( b );
      chkDoneTP.setEnabled( b );
      tfldTimeToTP.setEnabled( b );
      tfldFlatSize.setEnabled( b );
      tfldFlatsNeeded.setEnabled( b );
      tfldPlantsToStart.setEnabled( b );
   }

   private void setAllComponentsEnabled( boolean b ) {
      
      tfldCropName.setEnabled( b );
      tfldVarName.setEnabled( b );
      tfldMatDays.setEnabled( b );
      tfldLocation.setEnabled( b );
      cmbDates.setEnabled( b );
      tfldDatePlant.setEnabled( b );
      tfldDateHarvest.setEnabled( b );
      chkDonePlant.setEnabled( b );
      chkDoneHarvest.setEnabled( b );
      
      chkFrostHardy.setEnabled( b );
      rdoDS.setEnabled( b );
      rdoTP.setEnabled( b );
      tfldMatAdjust.setEnabled( b );
      tfldRowsPerBed.setEnabled( b );
      tfldInRowSpace.setEnabled( b );
      tfldBetRowSpace.setEnabled( b );
      tfldPlantingNotesCrop.setEnabled( b );
      tfldPlantingNotes.setEnabled( b );
      tfldBedsToPlant.setEnabled( b );
      tfldRowFtToPlant.setEnabled( b );
      tfldPlantsNeeded.setEnabled( b );
      tfldYieldPerFt.setEnabled( b );
      tfldTotalYield.setEnabled( b );
      tfldYieldNumWeeks.setEnabled( b );
      tfldYieldPerWeek.setEnabled( b );
      tfldCropYieldUnit.setEnabled( b );
      tfldCropYieldUnitValue.setEnabled( b );
      tareGroups.setEnabled( b );
      tareKeywords.setEnabled( b );
      tareOtherReq.setEnabled( b );
      tareNotes.setEnabled( b );
      tfldCustom1.setEnabled( b );
      tfldCustom2.setEnabled( b );
      tfldCustom3.setEnabled( b );
      tfldCustom4.setEnabled( b );
      tfldCustom5.setEnabled( b );
      
      setTPComponentsEnabled( b );

      for ( JLabel jl : anonLabels )
         jl.setEnabled( b );
   }

   @Override
   public void actionPerformed ( ActionEvent actionEvent ) {

      if ( actionEvent.getSource() != cmbDates )
         super.actionPerformed( actionEvent );
      else {
         displayDates();
      }
   }

   private void displayDates() {

      if ( ! isDataAvailable() )
         return;
      
      String s = (String) cmbDates.getSelectedItem();
      // editTBox = should text boxes be edittable, enableCBox = should checkboxes be enabled
      boolean editTBox, enableCBox;
      if ( s.equalsIgnoreCase( DATE_EFFECTIVE ) ) {
         // "effective" dates
         editTBox = false;
         enableCBox = true;
         tfldDatePlant.setInitialText( displayedPlanting.getDateToPlantString(),
                                       displayedPlanting.getDateToPlantState() );
         tfldDateTP.setInitialText( displayedPlanting.getDateToTPString(),
                                    displayedPlanting.getDateToTPState() );
         tfldDateHarvest.setInitialText( displayedPlanting.getDateToHarvestString(),
                                         displayedPlanting.getDateToHarvestState());
      } else if ( s.equalsIgnoreCase( DATE_ACTUAL ) ) {
         // actual dates
         editTBox = enableCBox = true;
         tfldDatePlant.setInitialText( displayedPlanting.getDateToPlantActualString(),
                                       displayedPlanting.getDateToPlantActualState() );
         tfldDateTP.setInitialText( displayedPlanting.getDateToTPActualString(),
                                    displayedPlanting.getDateToTPActualState() );
         tfldDateHarvest.setInitialText( displayedPlanting.getDateToHarvestActualString(),
                                         displayedPlanting.getDateToHarvestActualState());
      } else {
         // default to planned dates
         editTBox = true;
         enableCBox = false;
         tfldDatePlant.setInitialText( displayedPlanting.getDateToPlantPlannedString(),
                                       displayedPlanting.getDateToPlantPlannedState() );
         tfldDateTP.setInitialText( displayedPlanting.getDateToTPPlannedString(),
                                    displayedPlanting.getDateToTPPlannedState() );
         tfldDateHarvest.setInitialText( displayedPlanting.getDateToHarvestPlannedString(),
                                         displayedPlanting.getDateToHarvestPlannedState() );
      }

      tfldDatePlant.setEditable( editTBox );
      tfldDateTP.setEditable( editTBox );
      tfldDateHarvest.setEditable( editTBox );

      chkDonePlant.setEnabled( enableCBox );
      if ( rdoTP.isSelected() )
         chkDoneTP.setEnabled( enableCBox );
      chkDoneHarvest.setEnabled( enableCBox );

   }

   private void displayDSTPProperties() {
      if ( ! isDataAvailable() ) {
//      if ( displayedPlanting == null ) {
         CropPlans.debug( "CPInfo", "data unavailable, not displaying DS/TP values" );
         return;
      }
      
      CPSPlanting tempPlanting = displayedPlanting;
      
      tempPlanting.setDirectSeeded( rdoDS.isSelected() );

      tfldMatAdjust.setInitialText( tempPlanting.getMatAdjustString(),
                                    tempPlanting.getMatAdjustState() );
      tfldRowsPerBed.setInitialText( tempPlanting.getRowsPerBedString(),
                                     tempPlanting.getRowsPerBedState() );
      tfldBetRowSpace.setInitialText( tempPlanting.getRowSpacingString(),
                                      tempPlanting.getRowSpacingState() );
      tfldPlantingNotesCrop.setInitialText( tempPlanting.getPlantingNotesInherited(),
                                            tempPlanting.getPlantingNotesInheritedState() );
   }

   /* for testing only */
   public static void main( String[] args ) {
      // "test" construtor  
      CropPlanInfo ci = new CropPlanInfo();
      ci.buildDetailsPanel();
      
     JFrame frame = new JFrame();
     frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
     frame.setContentPane( ci.getDetailsPanel() );
     frame.setTitle( "CropPlan Info Layout" );
     frame.pack();
     frame.setVisible(true);
   }

}
