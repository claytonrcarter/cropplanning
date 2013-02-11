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

package CPS.Core.CropDB;

import CPS.Data.*;
import CPS.Module.*;
import CPS.UI.Modules.*;
import CPS.UI.Swing.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class CropDBCropInfo extends CPSDetailView implements ItemListener {

// these are from CPSCrop; leave unimplemented for now (11/26/08)
//   public final int PROP_BOT_NAME = CPSDataModelConstants.PROP_BOT_NAME;
//   public final int PROP_FROST_HARDY = CPSDataModelConstants.PROP_FROST_HARDY;
//
// leave these unimplemented, too (11/16/08)
//   public final int PROP_POT_UP = CPSDataModelConstants.PROP_TP_POT_UP;
//   public final int PROP_POT_UP_NOTES = CPSDataModelConstants.PROP_TP_POT_UP_NOTES;

   private CPSTextField tfldCropName, tfldVarName, tfldFamName, tfldMatDays;
   private CPSCheckBox chkDS, chkTP;
   private JLabel lblDSMat, lblDSRowsPB, lblDSSpace, lblDSNotes;
   private CPSTextField tfldDSMatAdjust, tfldDSRowsPerBed, tfldDSSpaceBetRows, tfldDSPlantNotes;
   private JLabel lblTPMat, lblTPRows, lblTPSpace, lblTPSpaceRow, lblTPFlat, lblTPWeeks, lblTPNotes;
   private CPSTextField tfldTPMatAdjust, tfldTPRowsPerBed, tfldTPSpaceInRow, tfldTPSpaceBetRows;
   private CPSTextField tfldTPFlatSize, tfldTPWeeksToTP, tfldTPPlantNotes;
   private CPSTextArea tareDesc, tareGroups, tareKeywords, tareOtherReq, tareNotes;
   private CPSTextField tfldYieldPerWeek, tfldYieldWeeks, tfldYieldPerFoot, tfldYieldUnits, tfldYieldUnitValue;
   private CPSTextField tfldSeedsPerUnit, tfldSeedsPerDS, tfldSeedsPerTP;
   private CPSComboBox cmbSeedUnit;
   private JLabel lblSeedDS, lblSeedTP;

   private ArrayList<JLabel> anonLabels;

   // for the DS/TP checkboxes
//   private CPSButtonGroup jbgPlantingMethod;
   
   private CPSCrop displayedCrop;
      
   CropDBCropInfo( CPSMasterDetailModule mdm ) {
       super( mdm, "Crop Info" );
   }
   
   /** this constructor does nothing and is meant for testing purposes only */
   private CropDBCropInfo() {}
   
   public CPSRecord getDisplayedRecord() { return displayedCrop; }
   public void displayRecord( CPSRecord r ) { displayRecord( (CPSCrop) r ); }
   public void displayRecord( CPSCrop crop ) {

      displayedCrop = crop;
      
      if ( ! isMainPanelBuilt() ) {
         setMainPanelBuilt();
         rebuildMainPanel();
         updateAutocompletionComponents();
      }

      if ( displayedCrop == null ) {
         displayedCrop = new CPSCrop();
         setRecordDisplayed(false);
      }
      else
         setRecordDisplayed( true );
      
      tfldCropName.setInitialText( displayedCrop.getCropName() );
      tfldVarName.setInitialText( displayedCrop.getVarietyName() );
      tfldFamName.setInitialText( displayedCrop.getFamilyName(),
                                  displayedCrop.getFamilyNameState() );
      
      tfldMatDays.setInitialText( displayedCrop.getMaturityDaysString(),
                                  displayedCrop.getMaturityDaysState() );

      tfldDSMatAdjust.setInitialText( displayedCrop.getDSMaturityAdjustString(),
                                      displayedCrop.getDSMaturityAdjustState() );
      tfldDSRowsPerBed.setInitialText( displayedCrop.getDSRowsPerBedString(),
                                       displayedCrop.getDSRowsPerBedState() );
      tfldDSSpaceBetRows.setInitialText( displayedCrop.getDSSpaceBetweenRowString(),
                                         displayedCrop.getDSSpaceBetweenRowState() );
      tfldDSPlantNotes.setInitialText( displayedCrop.getDSPlantNotes(),
                                       displayedCrop.getDSPlantNotesState() );
      
      tfldTPMatAdjust.setInitialText( displayedCrop.getTPMaturityAdjustString(),
                                      displayedCrop.getTPMaturityAdjustState() );
      tfldTPRowsPerBed.setInitialText( displayedCrop.getTPRowsPerBedString(),
                                       displayedCrop.getTPRowsPerBedState() );
      tfldTPSpaceInRow.setInitialText( displayedCrop.getTPSpaceInRowString(),
                                       displayedCrop.getTPSpaceInRowState() );
      tfldTPSpaceBetRows.setInitialText( displayedCrop.getTPSpaceBetweenRowString(),
                                         displayedCrop.getTPSpaceBetweenRowState() );  
      tfldTPFlatSize.setInitialText( displayedCrop.getTPFlatSize(),
                                     displayedCrop.getTPFlatSizeState() );
      tfldTPWeeksToTP.setInitialText( displayedCrop.getTPTimeInGHString(),
                                      displayedCrop.getTPTimeInGHState() );
      tfldTPPlantNotes.setInitialText( displayedCrop.getTPPlantNotes(),
                                       displayedCrop.getTPPlantNotesState() );

      tfldYieldPerWeek.setInitialText( displayedCrop.getYieldPerWeekString(),
                                       displayedCrop.getYieldPerWeekState() );
      tfldYieldWeeks.setInitialText( displayedCrop.getYieldNumWeeksString(),
                                     displayedCrop.getYieldNumWeeksState() );
      tfldYieldPerFoot.setInitialText( displayedCrop.getYieldPerFootString(),
                                       displayedCrop.getYieldPerFootState() );
      tfldYieldUnits.setInitialText( displayedCrop.getCropYieldUnit(),
                                     displayedCrop.getCropYieldUnitState() );
      tfldYieldUnitValue.setInitialText( displayedCrop.getCropUnitValueString(),
                                         displayedCrop.getCropUnitValueState() );

      tfldSeedsPerUnit.setInitialText( displayedCrop.getSeedsPerUnitString() ,
                                       displayedCrop.getSeedsPerUnitState() );
      cmbSeedUnit.setInitialSelection( displayedCrop.getSeedUnit() ,
                                       displayedCrop.getSeedUnitState() );
      tfldSeedsPerDS.setInitialText( displayedCrop.getSeedsPerDSString() ,
                                     displayedCrop.getSeedsPerDSState() );
      tfldSeedsPerTP.setInitialText( displayedCrop.getSeedsPerTPString() ,
                                     displayedCrop.getSeedsPerTPState() );

      tareDesc.setInitialText( displayedCrop.getCropDescription() );
      tareGroups.setInitialText( displayedCrop.getGroups(),
                                 displayedCrop.getGroupsState() );
      tareOtherReq.setInitialText( displayedCrop.getOtherRequirements(),
                                   displayedCrop.getOtherRequirementsState() );
      tareKeywords.setInitialText( displayedCrop.getKeywords(),
                                   displayedCrop.getKeywordsState() );
      tareNotes.setInitialText( displayedCrop.getNotes() );

      chkDS.setInitialState( displayedCrop.isDirectSeeded(),
                             displayedCrop.getDirectSeededState() );
      setDSComponentsEnabled( displayedCrop.isDirectSeeded() );
      
      chkTP.setInitialState( displayedCrop.isTransplanted(),
                             displayedCrop.getTransplantedState() );
      setTPComponentsEnabled( displayedCrop.isTransplanted() );

      setAllComponentsEnabled( isRecordDisplayed() );
   }
   
   @Override
    public void setForEditting() {
        tfldCropName.requestFocus();
    }
   
   
   public CPSCrop asCrop() {
      
      CPSCrop changes = new CPSCrop();
      changes.merge( displayedCrop );
      
      changes.setID( displayedCrop.getID() );

      if ( chkDS.hasChanged() )
         changes.setDirectSeeded( chkDS.isSelected() );
//      else
//         crop.setDirectSeeded( null, true );

      if ( chkTP.hasChanged() )
         changes.setTransplanted( chkTP.isSelected() );
//      else
//         crop.setTransplanted( null, true );

      if ( tfldCropName.hasChanged() ) changes.setCropName( tfldCropName.getText() );
      if ( tfldVarName.hasChanged() ) changes.setVarietyName( tfldVarName.getText() );
      if ( tfldFamName.hasChanged() ) changes.setFamilyName( tfldFamName.getText() );
      if ( tfldMatDays.hasChanged() ) changes.setMaturityDays( tfldMatDays.getText() );

      if ( tfldDSMatAdjust.hasChanged() ) changes.setDSMaturityAdjust( tfldDSMatAdjust.getText() );
      if ( tfldDSRowsPerBed.hasChanged() ) changes.setDSRowsPerBed( tfldDSRowsPerBed.getText() );
      if ( tfldDSSpaceBetRows.hasChanged() ) changes.setDSSpaceBetweenRow( tfldDSSpaceBetRows.getText() );
      if ( tfldDSPlantNotes.hasChanged() ) changes.setDSPlantNotes( tfldDSPlantNotes.getText() );
      
      if ( tfldTPMatAdjust.hasChanged() ) changes.setTPMaturityAdjust( tfldTPMatAdjust.getText() );
      if ( tfldTPRowsPerBed.hasChanged() ) changes.setTPRowsPerBed( tfldTPRowsPerBed.getText() );
      if ( tfldTPSpaceInRow.hasChanged() ) changes.setTPSpaceInRow( tfldTPSpaceInRow.getText() );
      if ( tfldTPSpaceBetRows.hasChanged() ) changes.setTPSpaceBetweenRow( tfldTPSpaceBetRows.getText() );
      if ( tfldTPFlatSize.hasChanged() ) changes.setTPFlatSize( tfldTPFlatSize.getText() );
      if ( tfldTPWeeksToTP.hasChanged() ) changes.setTPTimeInGH( tfldTPWeeksToTP.getText() );
      if ( tfldTPPlantNotes.hasChanged() ) changes.setTPPlantNotes( tfldTPPlantNotes.getText() );

      if ( tfldYieldPerWeek.hasChanged() ) changes.setYieldPerWeek( tfldYieldPerWeek.getText() );
      if ( tfldYieldWeeks.hasChanged() ) changes.setYieldNumWeeks( tfldYieldWeeks.getText() );
      if ( tfldYieldPerFoot.hasChanged() ) changes.setYieldPerFoot( tfldYieldPerFoot.getText() );
      if ( tfldYieldUnits.hasChanged() ) changes.setCropYieldUnit( tfldYieldUnits.getText() );
      if ( tfldYieldUnitValue.hasChanged() ) changes.setCropUnitValue( tfldYieldUnitValue.getText() );

      if ( tfldSeedsPerUnit.hasChanged() ) changes.setSeedsPerUnit( tfldSeedsPerUnit.getText() );
      if ( cmbSeedUnit.hasChanged()     ) changes.setSeedUnit( cmbSeedUnit.getSelectedItem() );
      if ( tfldSeedsPerDS.hasChanged()   ) changes.setSeedsPerDS( tfldSeedsPerDS.getText() );
      if ( tfldSeedsPerTP.hasChanged()   ) changes.setSeedsPerTP( tfldSeedsPerTP.getText() );

      if ( tareDesc.hasChanged() ) changes.setCropDescription( tareDesc.getText() );
      if ( tareGroups.hasChanged() ) changes.setGroups( tareGroups.getText() );
      if ( tareOtherReq.hasChanged() ) changes.setOtherRequirements( tareOtherReq.getText() );
      if ( tareKeywords.hasChanged() ) changes.setKeywords( tareKeywords.getText() );
      if ( tareNotes.hasChanged() ) changes.setNotes( tareNotes.getText() );

      return changes;
      
   }
   
   protected void buildDetailsPanel() {

     int r=0; // r as in row
     JLabel tempLabel;

      tfldCropName = new CPSTextField(10);
      tfldVarName = new CPSTextField( 10 );
      tfldMatDays = new CPSTextField( FIELD_LEN_SHORT );
      
      ArrayList<String> families = new ArrayList<String>();
      if ( isDataAvailable() )
         families = new ArrayList<String>(getDataSource().getFamilyNameList());
      tfldFamName = new CPSTextField( FIELD_LEN_WAY_LONG, families, CPSTextField.MATCH_PERMISSIVE );      
      
      chkDS = new CPSCheckBox( "By Direct Seed?", false );
      chkTP = new CPSCheckBox( "By Transplant?", false );
      chkDS.addItemListener( this );
      chkTP.addItemListener( this );
      
      tfldDSMatAdjust = new CPSTextField( FIELD_LEN_SHORT );
      tfldDSRowsPerBed = new CPSTextField( FIELD_LEN_SHORT );
      tfldDSSpaceBetRows = new CPSTextField( FIELD_LEN_SHORT );
      tfldDSPlantNotes = new CPSTextField( FIELD_LEN_LONG );

      tfldTPMatAdjust = new CPSTextField( FIELD_LEN_SHORT );
      tfldTPRowsPerBed = new CPSTextField( FIELD_LEN_SHORT );
      tfldTPSpaceInRow = new CPSTextField( FIELD_LEN_SHORT );
      tfldTPSpaceBetRows = new CPSTextField( FIELD_LEN_SHORT );
      tfldTPFlatSize = new CPSTextField( FIELD_LEN_LONG );
      tfldTPWeeksToTP = new CPSTextField( FIELD_LEN_SHORT );
      tfldTPPlantNotes = new CPSTextField( FIELD_LEN_LONG );
      
      tfldYieldPerWeek = new CPSTextField( FIELD_LEN_MED );
      tfldYieldWeeks = new CPSTextField( FIELD_LEN_SHORT );
      tfldYieldPerFoot = new CPSTextField( FIELD_LEN_SHORT );
      tfldYieldUnits = new CPSTextField( FIELD_LEN_MED );
      tfldYieldUnitValue = new CPSTextField( FIELD_LEN_SHORT );

      tfldSeedsPerUnit = new CPSTextField( FIELD_LEN_MED );
      cmbSeedUnit      = new CPSComboBox( CPSCrop.SEED_UNIT_STRINGS );
      tfldSeedsPerDS   = new CPSTextField( FIELD_LEN_MED );
      tfldSeedsPerTP   = new CPSTextField( FIELD_LEN_MED );

      tareDesc = new CPSTextArea( 3, FIELD_LEN_WAY_LONG );
      tareGroups = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareKeywords = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareOtherReq = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareNotes = new CPSTextArea( 3, 40 );
      
      
      JPanel columnOne = initPanelWithVerticalBoxLayout();
      JPanel columnTwo = initPanelWithVerticalBoxLayout();
//      JPanel columnThree = initPanelWithVerticalBoxLayout();
      JPanel columnFour = initPanelWithVerticalBoxLayout();
      
      /* the format for these calls is: panel, column, row, component */
      /* ***********************************/
      /* COLUMN ONE (really zero and one)  */
      /* ***********************************/
      JPanel jplName = initPanelWithGridBagLayout();
      jplName.setBorder( BorderFactory.createEmptyBorder() );

      if ( anonLabels == null )
        anonLabels = new ArrayList<JLabel>();

      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, r, "Crop Name:" ));
      LayoutAssist.addTextField( jplName, 1, r++, tfldCropName );

      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, r, "Variety:" ));
      LayoutAssist.addTextField( jplName, 1, r++, tfldVarName );
      
      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, r, "Family:" ));
      LayoutAssist.addTextField( jplName, 1, r++, tfldFamName );
      
      // starts in column 0, row 3 and spans 2 columns
      LayoutAssist.addSeparator( jplName, 0, r++, 2 );
      
      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, r, "Description:" ));
      LayoutAssist.addTextArea(  jplName, 1, r++, tareDesc  );
      
      LayoutAssist.addPanelToColumn( columnOne, jplName );
      LayoutAssist.finishColumn( columnOne );
      
      /* ***********************************/
      /* COLUMN TWO (really two, three, 4 and 5) */
      /* ***********************************/
      JPanel jplPlanting = initPanelWithGridBagLayout();
      jplPlanting.setBorder( BorderFactory.createTitledBorder( "Planting Info" ) );
      r=0;

      /* Applies to both DS & TP */
      anonLabels.add( LayoutAssist.createLabel(  jplPlanting, 1, r, "Maturity Days:" ));
      LayoutAssist.addTextField( jplPlanting, 2, r++, 2, 1, tfldMatDays );
      
      /* DS Column */
      LayoutAssist.addButton(    jplPlanting, 0, r++, 2, 1, chkDS, GridBagConstraints.CENTER );
      
      lblDSMat = LayoutAssist.createLabel(  jplPlanting, 0, r, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldDSMatAdjust );
      
      lblDSRowsPB = LayoutAssist.createLabel(  jplPlanting, 0, r, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldDSRowsPerBed );
      
      lblDSSpace = LayoutAssist.createLabel(  jplPlanting, 0, r, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldDSSpaceBetRows );

      r++; // skip row 5

      LayoutAssist.addSeparator( jplPlanting, 0, r++, 2 );

      lblDSNotes = LayoutAssist.createLabel(  jplPlanting, 0, r, "DS Notes" );
      LayoutAssist.addTextField( jplPlanting, 1, r++, tfldDSPlantNotes );

      /* TP Column */
      r=1;
      LayoutAssist.addVerticalSeparator( jplPlanting, 2, r, 11 );
      LayoutAssist.addButton(    jplPlanting, 3, r++, 2, 1, chkTP, GridBagConstraints.CENTER );

      lblTPMat = LayoutAssist.createLabel(  jplPlanting, 3, r, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 4, r++, tfldTPMatAdjust );
     
      lblTPRows = LayoutAssist.createLabel(  jplPlanting, 3, r, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 4, r++, tfldTPRowsPerBed );
      
      lblTPSpaceRow = LayoutAssist.createLabel(  jplPlanting, 3, r, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 4, r++, tfldTPSpaceBetRows );
      
      lblTPSpace = LayoutAssist.createLabel(  jplPlanting, 3, r, "Plant Spacing" );
      LayoutAssist.addTextField( jplPlanting, 4, r++, tfldTPSpaceInRow );
      
      LayoutAssist.addSeparator( jplPlanting, 3, r++, 2);
      
      lblTPFlat = LayoutAssist.createLabel(  jplPlanting, 3, r, "Flat Size" );
      LayoutAssist.addTextField( jplPlanting, 4, r++, tfldTPFlatSize );
      
      lblTPWeeks = LayoutAssist.createLabel(  jplPlanting, 3, r, "Weeks to TP" );
      LayoutAssist.addTextField( jplPlanting, 4, r++, tfldTPWeeksToTP );
      
      LayoutAssist.addSeparator( jplPlanting, 3, r++, 2);
      
      lblTPNotes = LayoutAssist.createLabel(  jplPlanting, 3, r, "TP Notes" );
      LayoutAssist.addTextField( jplPlanting, 4, r++, tfldTPPlantNotes );
      
      LayoutAssist.addPanelToColumn( columnTwo, jplPlanting );
      LayoutAssist.finishColumn( columnTwo );
      
      // add all of the above labels to the label list
      anonLabels.addAll( Arrays.asList( new JLabel[] { lblDSMat, lblDSRowsPB, lblDSSpace, lblDSNotes,
                                                       lblTPMat, lblTPRows, lblTPSpaceRow, lblTPSpace,
                                                       lblTPFlat, lblTPWeeks, lblTPNotes } ) );

      /**************************************/
      /* COLUMN FOUR (really seven and eight) */
      /**************************************/


      r=0;
      JPanel jplYield = initPanelWithGridBagLayout();
      
      /* unit, per foot, weeks, per week, value */
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, r, "Yield Units" ));
      LayoutAssist.addTextField( jplYield, 1, r++, tfldYieldUnits);
      
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, r, "Total Yield/Ft" ));
      LayoutAssist.addTextField( jplYield, 1, r++, tfldYieldPerFoot );
      
      LayoutAssist.createLabel(  jplYield, 0, r, "Value/Unit" );
      LayoutAssist.addTextField( jplYield, 1, r++, tfldYieldUnitValue );

      // TODO these panels should all line up better than they do
      // would like all fields to start at top of space (not centered)
      // and ideally have


      r=0;
      JPanel jplSeeds = initPanelWithGridBagLayout();

      anonLabels.add(tempLabel = LayoutAssist.createLabel( jplSeeds, 0, r, "Units" ));
      tempLabel.setToolTipText("oz or g, for example");
      LayoutAssist.addComponent( jplSeeds, 1, r++, cmbSeedUnit );

      anonLabels.add(tempLabel = LayoutAssist.createLabel( jplSeeds, 0, r, "Seeds/Unit" ));
      tempLabel.setToolTipText("Seeds/Oz or Seeds/g, for example");
      LayoutAssist.addTextField( jplSeeds, 1, r++, tfldSeedsPerUnit);

      lblSeedDS = LayoutAssist.createLabel( jplSeeds, 0, r, "Seeds/Ft (DS)" );
      LayoutAssist.addTextField( jplSeeds, 1, r++, tfldSeedsPerDS );

      lblSeedTP = LayoutAssist.createLabel( jplSeeds, 0, r, "Seeds/Plant (TP)" );
      LayoutAssist.addTextField( jplSeeds, 1, r++, tfldSeedsPerTP );


      r=0;
      JPanel jplMisc = initPanelWithGridBagLayout();

      anonLabels.add( LayoutAssist.createLabel(  jplMisc, 0, r, 
                                                 "<html>Other <br>Req.:</html>" ));
      LayoutAssist.addTextArea(  jplMisc, 1, r++, 1, 1, tareOtherReq );

      r++; // skip row 1
      anonLabels.add( LayoutAssist.createLabel(  jplMisc, 0, r, "<html>Belongs to <br>Groups:</html>" ));
      LayoutAssist.addTextArea(  jplMisc, 1, r++, 1, 1, tareGroups );

      r++; // skip row 3
      anonLabels.add( LayoutAssist.createLabel(  jplMisc, 0, r, "Keywords:" ));
      LayoutAssist.addTextArea(  jplMisc, 1, r++, 1, 1, tareKeywords );
      
      LayoutAssist.addPanelToColumn(
              columnFour,
              new CPSCardPanel( new String[] { "Yield Info", 
                                               "Seed Info",
                                               "Misc Info" },
                                new JPanel[] { jplYield, jplSeeds, jplMisc } ));

      LayoutAssist.finishColumn( columnFour );
      
      
      /* *************************************/
      /* BOTTOW ROW                          */
      /* *************************************/

      initDetailsPanel();

      LayoutAssist.addSubPanel( jplDetails, 0, 0, 1, 1, columnOne );
      LayoutAssist.addSubPanel( jplDetails, 1, 0, 1, 1, columnTwo );
      LayoutAssist.addSubPanel( jplDetails, 3, 0, 1, 1, columnFour );

      // Notes TextArea is set to span all remaining columns
      JPanel jplNotes = initPanelWithGridBagLayout();
      jplNotes.setBorder( BorderFactory.createEmptyBorder() );
      anonLabels.add( LayoutAssist.createLabel(  jplNotes, 0, 0, "Notes:" ));
      LayoutAssist.addTextArea(  jplNotes, 1, 0, tareNotes );
      LayoutAssist.addSubPanel( jplDetails, 0, 14, 7, 1, jplNotes );

      
      if ( uiManager != null )
         uiManager.signalUIChanged();  
   }

   // Implemented as per ItemListener
   public void itemStateChanged ( ItemEvent arg0 ) {

      Object source = arg0.getItemSelectable();

      if ( source == chkDS )
         setDSComponentsEnabled( chkDS.isSelected() );
      else if ( source == chkTP )
         setTPComponentsEnabled( chkTP.isSelected() );

   }

   private void setDSComponentsEnabled( boolean b ) {
      lblDSMat.setEnabled( b );
      lblDSRowsPB.setEnabled( b );
      lblDSSpace.setEnabled( b );
      lblDSNotes.setEnabled( b );

      tfldDSMatAdjust.setEnabled( b );
      tfldDSRowsPerBed.setEnabled( b );
      tfldDSSpaceBetRows.setEnabled( b );
      tfldDSPlantNotes.setEnabled( b );

      lblSeedDS.setEnabled(b);
      tfldSeedsPerDS.setEnabled(b);
   }

   private void setTPComponentsEnabled( boolean b ) {
      lblTPMat.setEnabled( b );
      lblTPRows.setEnabled( b );
      lblTPSpace.setEnabled( b );
      lblTPSpaceRow.setEnabled( b );
      lblTPFlat.setEnabled( b );
      lblTPWeeks.setEnabled( b );
      lblTPNotes.setEnabled( b );

      tfldTPMatAdjust.setEnabled( b );
      tfldTPRowsPerBed.setEnabled( b );
      tfldTPSpaceInRow.setEnabled( b );
      tfldTPSpaceBetRows.setEnabled( b );
      tfldTPFlatSize.setEnabled( b );
      tfldTPWeeksToTP.setEnabled( b );
      tfldTPPlantNotes.setEnabled( b );

      lblSeedTP.setEnabled(b);
      tfldSeedsPerTP.setEnabled(b);
   }

   @Override
   protected void setAllComponentsEnabled( boolean b ) {

      tfldCropName.setEnabled( b );
      tfldVarName.setEnabled( b );
      tfldFamName.setEnabled( b );
      tfldMatDays.setEnabled( b );
      tfldDSMatAdjust.setEnabled( b );
      tfldDSRowsPerBed.setEnabled( b );
      tfldDSSpaceBetRows.setEnabled( b );
      tfldDSPlantNotes.setEnabled( b );
      tfldTPMatAdjust.setEnabled( b );
      tfldTPRowsPerBed.setEnabled( b );
      tfldTPSpaceInRow.setEnabled( b );
      tfldTPSpaceBetRows.setEnabled( b );
      tfldTPFlatSize.setEnabled( b );
      tfldTPWeeksToTP.setEnabled( b );
      tfldTPPlantNotes.setEnabled( b );
      tfldYieldPerWeek.setEnabled( b );
      tfldYieldWeeks.setEnabled( b );
      tfldYieldPerFoot.setEnabled( b );
      tfldYieldUnits.setEnabled( b );
      tfldYieldUnitValue.setEnabled( b );
      tfldSeedsPerUnit.setEnabled( b );
      cmbSeedUnit.setEnabled( b );
      tareDesc.setEnabled( b );
      tareGroups.setEnabled( b );
      tareOtherReq.setEnabled( b );
      tareKeywords.setEnabled( b );
      tareNotes.setEnabled( b );

      chkDS.setEnabled( b );
      chkTP.setEnabled( b );

      for ( JLabel jl : anonLabels )
         jl.setEnabled( b );

   }


   
    protected void saveChangesToRecord() {

       CPSCrop currentlyDisplayed = this.asCrop();
       CPSCrop diff = (CPSCrop) displayedCrop.diff( currentlyDisplayed );

       if ( diff.getID() == -1 ) {
         displayRecord( displayedCrop );
         return;
       }

       // update items in db
       if ( ! displayedCrop.isSingleRecord() )
         getDataSource().updateCrops( diff, displayedCrop.getCommonIDs() );
       else
         // TODO ideally this would only update the differences, not the whole thing
         getDataSource().updateCrop( currentlyDisplayed );

       // if the crop or var name has changed, then we need to reload the
       // planting to make sure inheritance happens
       if ( tfldCropName.hasChanged() ) {
         diff = getDataSource().getCropInfo( diff.getID() );
       }

       updateRecordInMasterView(diff);

       // this automatically displays the record in the detail panel
       selectRecordsInMasterView( Arrays.asList( displayedCrop.getID() ));

       
    }
 

    @Override
    protected void updateAutocompletionComponents() {
      if ( ! isDataAvailable() )
        return;
      
        tfldCropName.updateAutocompletionList( getDataSource().getCropNameList(),
                                               CPSTextField.MATCH_PERMISSIVE );
        if ( displayedCrop != null )
          tfldVarName.updateAutocompletionList( getDataSource().getVarietyNameList( displayedCrop.getCropName() ),
                                                CPSTextField.MATCH_PERMISSIVE );
        tfldFamName.updateAutocompletionList( getDataSource().getFamilyNameList(),
                                              CPSTextField.MATCH_PERMISSIVE );
        tfldTPFlatSize.updateAutocompletionList( getDataSource().getFlatSizeList(),
                                                 CPSTextField.MATCH_PERMISSIVE );
    }
   
   @Override
   public void dataUpdated() {
      if ( isRecordDisplayed() ) {
         CPSCrop c = getDataSource().getCropInfo( getDisplayedRecord().getID() );

         this.displayRecord( c );
         updateAutocompletionComponents();
         
      }
   }
   
   @Override
   protected int saveState() {
       throw new UnsupportedOperationException( "Not supported yet." );
   }
   
   
   /* for testing only */
   public static void main( String[] args ) {
      // "test" construtor  
      CropDBCropInfo ci = new CropDBCropInfo();
      ci.buildDetailsPanel();
      
     JFrame frame = new JFrame();
     frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
     frame.setContentPane( ci.getDetailsPanel() );
     frame.setTitle( "CropDB Info Layout" );
     frame.pack();
     frame.setVisible(true);
   }

}
