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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class CropDBCropInfo extends CPSDetailView implements ItemListener {

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

     JLabel tempLabel;
     String migPanelDefaults = "gapy 0px!, insets 2px";

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

      tareDesc = new CPSTextArea( 8, FIELD_LEN_WAY_LONG );
      tareGroups = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareKeywords = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareOtherReq = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareNotes = new CPSTextArea( 12, 15 );
      
            
      /* the format for these calls is: panel, column, row, component */
      /* ***********************************/
      /* COLUMN ONE (really zero and one)  */
      /* ***********************************/
      JPanel jplName = new JPanel( new MigLayout( migPanelDefaults ));

      if ( anonLabels == null )
        anonLabels = new ArrayList<JLabel>();

      tempLabel = new JLabel("Crop Name:");
      jplName.add( tempLabel,    "right align" );
      jplName.add( tfldCropName, "wrap" );

      tempLabel = new JLabel( "Variety:" );
      jplName.add( tempLabel,    "right align" );
      jplName.add( tfldVarName, "wrap" );

      tempLabel = new JLabel( "Family:" );
      jplName.add( tempLabel,    "right align" );
      jplName.add( tfldFamName, "wrap" );

      jplName.add( new JSeparator(), "growx, span 2, wrap" );

      tempLabel = new JLabel( "Description:" );
      jplName.add( tempLabel,    "right align" );
//      jplName.add( tareDesc, "wrap" );
      jplName.add( new JScrollPane( tareDesc ), "wrap" );


      /* ***********************************/
      /* COLUMN TWO
      /* ***********************************/
      JPanel jplPlanting = new JPanel( new MigLayout( migPanelDefaults ) );
      jplPlanting.setBorder( BorderFactory.createTitledBorder( "Planting Info" ) );
      
      tempLabel = new JLabel( "Maturity Days:" );
      jplPlanting.add( tempLabel, "align center, split 2, span 5 " );
      jplPlanting.add( tfldMatDays, "wrap" );
      
      jplPlanting.add( chkDS, "align center, span 2" );
      jplPlanting.add( new JSeparator( JSeparator.VERTICAL ), "growy, spany" );
      jplPlanting.add( chkTP, "align center, span 2, wrap" );


      lblDSMat = new JLabel( "Adjust Mat." );
      jplPlanting.add( lblDSMat, "align right " );
      jplPlanting.add( tfldDSMatAdjust, "" );
      lblTPMat = new JLabel( "Adjust Mat." );
      jplPlanting.add( lblTPMat, "align right" );
      jplPlanting.add( tfldTPMatAdjust, "wrap" );
      
      lblDSRowsPB = new JLabel( "Rows/Bed" );
      jplPlanting.add( lblDSRowsPB, "align right" );
      jplPlanting.add( tfldDSRowsPerBed, "" );
      lblTPRows = new JLabel( "Rows/Bed" );
      jplPlanting.add( lblTPRows, "align right, skip 1" );
      jplPlanting.add( tfldTPRowsPerBed, "wrap" );

      lblDSSpace = new JLabel( "Row Spacing" );
      jplPlanting.add( lblDSSpace, "align right" );
      jplPlanting.add( tfldDSSpaceBetRows, "" );
      lblTPSpaceRow = new JLabel( "Row Spacing" );
      jplPlanting.add( lblTPSpaceRow, "align right, skip 1" );
      jplPlanting.add( tfldTPSpaceBetRows, "wrap" );

      lblTPSpace = new JLabel( "Plant Spacing" );
      jplPlanting.add( lblTPSpace, "skip 2, align right" );
      jplPlanting.add( tfldTPSpaceInRow, "wrap" );

      jplPlanting.add( new JSeparator(), "growx, span 2" );
      jplPlanting.add( new JSeparator(), "growx, span 2, wrap" );

      lblDSNotes = new JLabel( "DS Notes" );
      jplPlanting.add( lblDSNotes, "align right" );
      jplPlanting.add( tfldDSPlantNotes, "" );
      lblTPFlat = new JLabel( "Flat Size" );
      jplPlanting.add( lblTPFlat, "align right" );
      jplPlanting.add( tfldTPFlatSize, "wrap" );


      lblTPWeeks = new JLabel( "Weeks to TP" );
      jplPlanting.add( lblTPWeeks, "skip 2, align right" );
      jplPlanting.add( tfldTPWeeksToTP, "wrap" );

      jplPlanting.add( new JSeparator(), "growx, skip 2, span 2, wrap" );

      lblTPNotes = new JLabel( "TP Notes" );
      jplPlanting.add( lblTPNotes, "align right, skip 2" );
      jplPlanting.add( tfldTPPlantNotes, "wrap" );
      

      // add all of the above labels to the label list
      anonLabels.addAll( Arrays.asList( new JLabel[] { lblDSMat, lblDSRowsPB, lblDSSpace, lblDSNotes,
                                                       lblTPMat, lblTPRows, lblTPSpaceRow, lblTPSpace,
                                                       lblTPFlat, lblTPWeeks, lblTPNotes } ) );

      /**************************************/
      /* COLUMN THREE
      /**************************************/
      /* Page 1
      /**************************************/
      JPanel jplYield = new JPanel( new MigLayout( migPanelDefaults ) );
      
      /* unit, per foot, weeks, per week, value */
      tempLabel = new JLabel( "Yield Units" );
      jplYield.add( tempLabel, "align right" );
      jplYield.add( tfldYieldUnits, "wrap" );
      
      tempLabel = new JLabel( "Total Yield/Ft" );
      jplYield.add( tempLabel, "align right" );
      jplYield.add( tfldYieldPerFoot, "wrap" );
      
      tempLabel = new JLabel( "Value/Unit" );
      jplYield.add( tempLabel, "align right" );
      jplYield.add( tfldYieldUnitValue, "wrap" );


      /**************************************/
      /* Page 2
      /**************************************/
      JPanel jplSeeds = new JPanel( new MigLayout( migPanelDefaults ) );

      tempLabel = new JLabel( "Units" );
      jplSeeds.add( tempLabel, "align right" );
      tempLabel.setToolTipText("oz or g, for example");
      jplSeeds.add( cmbSeedUnit, "wrap" );

      tempLabel = new JLabel( "Seeds/Unit" );
      jplSeeds.add( tempLabel, "align right" );
      tempLabel.setToolTipText("Seeds/Oz or Seeds/g, for example");
      jplSeeds.add( tfldSeedsPerUnit, "wrap" );

      lblSeedDS = new JLabel( "Seeds/Ft (DS)" );
      jplSeeds.add( lblSeedDS, "align right" );
      jplSeeds.add( tfldSeedsPerDS, "wrap" );

      lblSeedTP = new JLabel( "Seeds/Plant (TP)" );
      jplSeeds.add( lblSeedTP, "align right" );
      jplSeeds.add( tfldSeedsPerTP, "wrap" );


      /**************************************/
      /* Page 3
      /**************************************/
      JPanel jplMisc = new JPanel( new MigLayout( migPanelDefaults ) );

      tempLabel = new JLabel( "<html>Other <br>Req.:</html>" );
      jplMisc.add( tempLabel, "align right top" );
      jplMisc.add( new JScrollPane( tareOtherReq ), "wrap" );

      tempLabel = new JLabel( "<html>Belongs to <br>Groups:</html>" );
      jplMisc.add( tempLabel, "align right top" );
      jplMisc.add( new JScrollPane( tareGroups ), "wrap" );

      tempLabel = new JLabel( "Keywords:" );
      jplMisc.add( tempLabel, "align right top" );
      jplMisc.add( new JScrollPane( tareKeywords ), "wrap" );


      /**************************************/
      /* Page 3
      /**************************************/
      JPanel jplNotes = new JPanel( new MigLayout( migPanelDefaults ) );

      tempLabel = new JLabel( "Notes:" );
      jplNotes.add( tempLabel, "align right top" );
      jplNotes.add( new JScrollPane( tareNotes ), "wrap" );
      jplNotes.add( new JButton("Add Date"), "span 2, align right" );

      
      CPSCardPanel columnFour =
              new CPSCardPanel( new String[] {
                                               "Notes",
                                               "Yield Info",
                                               "Seed Info",
                                               "Misc Info" },
                                new JPanel[] {
                                               jplNotes,
                                               jplYield,
                                               jplSeeds,
                                               jplMisc } );
      
      /* *************************************/
      /* BOTTOW ROW                          */
      /* *************************************/
      jplDetails = new JPanel( new MigLayout( "gap 0px!, insets 2px" ) );

      jplDetails.add( jplName, "aligny top" );
      jplDetails.add( jplPlanting, "aligny top" );
      jplDetails.add( columnFour, "aligny top, wrap" );
      



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
       if ( ! displayedCrop.isSingleRecord() ) {
         // multiple selection
         List<Integer> ids = displayedCrop.getCommonIDs();
         // this triggers an update of all of the lists
         getDataSource().updateCrops( diff, ids );
         selectRecordsInMasterView(ids);
       }
       else {
         int id = displayedCrop.getID();
         // single selection
         // this triggers an update of all of the lists
         getDataSource().updateCrop( currentlyDisplayed );


         // reload the crop/var in case inheritance needs to be updated
         // reusing diff
         //diff = getDataSource().getCropInfo( diff.getID() );
         //updateRecordInMasterView(diff);

         // this automatically displays the record in the detail panel
         selectRecordsInMasterView( Arrays.asList( id ));
       }


       
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
