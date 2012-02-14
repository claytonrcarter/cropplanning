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

   private ArrayList<JLabel> anonLabels = new ArrayList<JLabel>();

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

      if ( tareDesc.hasChanged() ) changes.setCropDescription( tareDesc.getText() );
      if ( tareGroups.hasChanged() ) changes.setGroups( tareGroups.getText() );
      if ( tareOtherReq.hasChanged() ) changes.setOtherRequirements( tareOtherReq.getText() );
      if ( tareKeywords.hasChanged() ) changes.setKeywords( tareKeywords.getText() );
      if ( tareNotes.hasChanged() ) changes.setNotes( tareNotes.getText() );

      return changes;
      
   }
   
   protected void buildDetailsPanel() {

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
      
      tareDesc = new CPSTextArea( 3, FIELD_LEN_WAY_LONG );
      tareGroups = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareKeywords = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareOtherReq = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareNotes = new CPSTextArea( 3, 40 );
      
      
      JPanel columnOne = initPanelWithVerticalBoxLayout();
      JPanel columnTwo = initPanelWithVerticalBoxLayout();
      JPanel columnThree = initPanelWithVerticalBoxLayout();
      JPanel columnFour = initPanelWithVerticalBoxLayout();
      
      /* the format for these calls is: panel, column, row, component */
      /* ***********************************/
      /* COLUMN ONE (really zero and one)  */
      /* ***********************************/
      JPanel jplName = initPanelWithGridBagLayout();
      jplName.setBorder( BorderFactory.createEmptyBorder() );
      
      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, 0, "Crop Name:" ));
      LayoutAssist.addTextField( jplName, 1, 0, tfldCropName );

      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, 1, "Variety:" ));
      LayoutAssist.addTextField( jplName, 1, 1, tfldVarName );
      
      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, 2, "Family:" ));
      LayoutAssist.addTextField( jplName, 1, 2, tfldFamName );
      
      // starts in column 0, row 3 and spans 2 columns
      LayoutAssist.addSeparator( jplName, 0, 3, 2 );
      
      anonLabels.add( LayoutAssist.createLabel(  jplName, 0, 4, "Description:" ));
      LayoutAssist.addTextArea(  jplName, 1, 4, tareDesc  );
      
      LayoutAssist.addPanelToColumn( columnOne, jplName );
      LayoutAssist.finishColumn( columnOne );
      
      /* ***********************************/
      /* COLUMN TWO (really two and three) */
      /* ***********************************/
      JPanel jplPlanting = initPanelWithGridBagLayout();
      jplPlanting.setBorder( BorderFactory.createTitledBorder( "Planting Info" ) );
      
      /* Applies to both DS & TP */
      anonLabels.add( LayoutAssist.createLabel(  jplPlanting, 1, 0, "Maturity Days:" ));
      LayoutAssist.addTextField( jplPlanting, 2, 0, 2, 1, tfldMatDays );
      
      /* DS Column */
      LayoutAssist.addButton(    jplPlanting, 0, 1, 2, 1, chkDS, GridBagConstraints.CENTER );
      
      lblDSMat = LayoutAssist.createLabel(  jplPlanting, 0, 2, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 1, 2, tfldDSMatAdjust );
      
      lblDSRowsPB = LayoutAssist.createLabel(  jplPlanting, 0, 3, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 1, 3, tfldDSRowsPerBed );
      
      lblDSSpace = LayoutAssist.createLabel(  jplPlanting, 0, 4, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 1, 4, tfldDSSpaceBetRows );
      
      LayoutAssist.addSeparator( jplPlanting, 0, 6, 2 );

      lblDSNotes = LayoutAssist.createLabel(  jplPlanting, 0, 7, "DS Notes" );
      LayoutAssist.addTextField( jplPlanting, 1, 7, tfldDSPlantNotes );

      /* TP Column */
      LayoutAssist.addVerticalSeparator( jplPlanting, 2, 1, 11 );
      LayoutAssist.addButton(    jplPlanting, 3, 1, 2, 1, chkTP, GridBagConstraints.CENTER );

      lblTPMat = LayoutAssist.createLabel(  jplPlanting, 3, 2, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 4, 2, tfldTPMatAdjust );
     
      lblTPRows = LayoutAssist.createLabel(  jplPlanting, 3, 3, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 4, 3, tfldTPRowsPerBed );
      
      lblTPSpaceRow = LayoutAssist.createLabel(  jplPlanting, 3, 4, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 4, 4, tfldTPSpaceBetRows );
      
      lblTPSpace = LayoutAssist.createLabel(  jplPlanting, 3, 5, "Plant Spacing" );
      LayoutAssist.addTextField( jplPlanting, 4, 5, tfldTPSpaceInRow );
      
      LayoutAssist.addSeparator( jplPlanting, 3, 6, 2);
      
      lblTPFlat = LayoutAssist.createLabel(  jplPlanting, 3, 7, "Flat Size" );
      LayoutAssist.addTextField( jplPlanting, 4, 7, tfldTPFlatSize );
      
      lblTPWeeks = LayoutAssist.createLabel(  jplPlanting, 3, 8, "Weeks to TP" );
      LayoutAssist.addTextField( jplPlanting, 4, 8, tfldTPWeeksToTP );
      
      LayoutAssist.addSeparator( jplPlanting, 3, 9, 2);
      
      lblTPNotes = LayoutAssist.createLabel(  jplPlanting, 3, 10, "TP Notes" );
      LayoutAssist.addTextField( jplPlanting, 4, 10, tfldTPPlantNotes );
      
//      LayoutAssist.addSubPanel(  jplDetails, 2, 1, 2, 13, jplPlanting );
      LayoutAssist.addPanelToColumn( columnTwo, jplPlanting );
      LayoutAssist.finishColumn( columnTwo );
      
      // add all of the above labels to the label list
      anonLabels.addAll( Arrays.asList( new JLabel[] { lblDSMat, lblDSRowsPB, lblDSSpace, lblDSNotes,
                                                       lblTPMat, lblTPRows, lblTPSpaceRow, lblTPSpace,
                                                       lblTPFlat, lblTPWeeks, lblTPNotes } ) );

      /* *************************************/
      /* COLUMN THREE (really four and five) */
      /* *************************************/
      JPanel jplYield = initPanelWithGridBagLayout();
      jplYield.setBorder( BorderFactory.createTitledBorder( "Yield Info" ) );
      
      /* unit, per foot, weeks, per week, value */
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, 0, "Yield Units" ));
      LayoutAssist.addTextField( jplYield, 1, 0, tfldYieldUnits);
      
      anonLabels.add( LayoutAssist.createLabel(  jplYield, 0, 1, "Total Yield/Ft" ));
      LayoutAssist.addTextField( jplYield, 1, 1, tfldYieldPerFoot );
      
//      LayoutAssist.createLabel(  jplYield, 0, 2, "Weeks of Yield" );
//      LayoutAssist.addTextField( jplYield, 1, 2, tfldYieldWeeks );
      
//      LayoutAssist.createLabel(  jplYield, 0, 3, "Yield/Week" );
//      LayoutAssist.addTextField( jplYield, 1, 3, tfldYieldPerWeek );
      
      LayoutAssist.createLabel(  jplYield, 0, 4, "Value/Unit" );
      LayoutAssist.addTextField( jplYield, 1, 4, tfldYieldUnitValue );
      
//      LayoutAssist.addSubPanel(  jplDetails, 4, 1, 2, 6, jplYield );
      LayoutAssist.addPanelToColumn( columnFour, jplYield );
//      LayoutAssist.finishColumn( columnThree );
      
      /* *************************************/
      /* COLUMN FOUR (actually six and seven */
      /* *************************************/
      
      JPanel jplMisc = initPanelWithGridBagLayout();
      jplMisc.setBorder( BorderFactory.createTitledBorder( "Misc Info" ) );

      anonLabels.add( LayoutAssist.createLabel(  jplMisc, 0, 0, "<html>Other <br>Requirements:</html>" ));
      LayoutAssist.addTextArea(  jplMisc, 1, 0, 1, 1, tareOtherReq );
      
      anonLabels.add( LayoutAssist.createLabel(  jplMisc, 0, 2, "<html>Belongs to <br>Groups:</html>" ));
      LayoutAssist.addTextArea(  jplMisc, 1, 2, 1, 1, tareGroups );
      
      anonLabels.add( LayoutAssist.createLabel(  jplMisc, 0, 4, "Keywords:" ));
      LayoutAssist.addTextArea(  jplMisc, 1, 4, 1, 1, tareKeywords );
      
//      LayoutAssist.addSubPanel( jplDetails, 6, 1, 2, 7, jplMisc);
      LayoutAssist.addPanelToColumn( columnFour, jplMisc );
      LayoutAssist.finishColumn( columnFour );
      
      
      /* *************************************/
      /* BOTTOW ROW                          */
      /* *************************************/

      initDetailsPanel();

      LayoutAssist.addSubPanel( jplDetails, 0, 0, 1, 1, columnOne );
      LayoutAssist.addSubPanel( jplDetails, 1, 0, 1, 1, columnTwo );
      LayoutAssist.addSubPanel( jplDetails, 2, 0, 1, 1, columnThree );
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

       if ( diff.getID() == -1 )
          return; // no differences!
       
       if ( ! displayedCrop.isSingleRecord() )
           // TODO this doesn't work anymore (with Persist)
           // all values will be set to the values of diff, whether they're null or not
          getDataSource().updateCrops( diff, displayedCrop.getCommonIDs() );
       else
           // TODO ideally this would only update the differences, not the whole thing
          getDataSource().updateCrop( currentlyDisplayed );
       
       selectRecordInMasterView( displayedCrop.getID() );
    }
 

    @Override
    protected void updateAutocompletionComponents() {
        tfldCropName.updateAutocompletionList( getDataSource().getCropNameList(),
                                               CPSTextField.MATCH_PERMISSIVE );
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
