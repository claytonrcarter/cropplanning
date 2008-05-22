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
import java.util.ArrayList;
import javax.swing.*;

public class CropDBCropInfo extends CPSDetailView {
   
   private CPSTextField tfldCropName, tfldVarName, tfldFamName;
   private CPSTextField tfldMatDays, tfldRowsPerBed, tfldSpaceInRow, tfldSpaceBetRows;
   private CPSTextField tfldFlatSize, tfldWeeksToTP, tfldMatAdjust, tfldPlanter, tfldPlanterSetting;
   private CPSTextArea tareDesc, tareGroups, tareKeywords, tareOtherReq, tareNotes;
   private CPSTextField tfldYieldPerWeek, tfldYieldWeeks, tfldYieldPerFoot, tfldYieldUnits, tfldYieldUnitValue;
   private JLabel lblSimilar;
   private JComboBox cmbxSimilar = null;
//   private CPSRadioButton rdoDS, rdoTP;
   private JCheckBox chkDS, chkTP;
   private CPSButtonGroup jbgPlantingMethod;
   
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
      
      if ( ! isRecordDisplayed() ) {
         setRecordDisplayed();
         rebuildMainPanel();
         updateAutocompletionComponents();
      }
      
      tfldCropName.setInitialText( displayedCrop.getCropName() );
      tfldVarName.setInitialText( displayedCrop.getVarietyName() );
      
      tfldFamName.setInitialText( displayedCrop.getFamilyName(),
                                  displayedCrop.getFamilyNameState() );
      tareDesc.setInitialText( displayedCrop.getCropDescription() );
      
      tfldMatDays.setInitialText( displayedCrop.getMaturityDaysString(),
                                  displayedCrop.getMaturityDaysState() );
      
      tareGroups.setInitialText( displayedCrop.getGroups(),
                                  displayedCrop.getGroupsState() );
      tareOtherReq.setInitialText( displayedCrop.getOtherRequirments(),
                                  displayedCrop.getOtherRequirmentsState() );
      tareKeywords.setInitialText( displayedCrop.getKeywords(),
                                  displayedCrop.getKeywordsState() );
      tareNotes.setInitialText( displayedCrop.getNotes() );
      
      tfldRowsPerBed.setInitialText( displayedCrop.getTPRowsPerBedString(),
                                  displayedCrop.getTPRowsPerBedState() );
      tfldSpaceInRow.setInitialText( displayedCrop.getTPSpaceInRowString(),
                                  displayedCrop.getTPSpaceInRowState() );
      tfldSpaceBetRows.setInitialText(  displayedCrop.getTPSpaceBetweenRowString(),
                                  displayedCrop.getTPSpaceBetweenRowState() );

      tfldPlanter.setInitialText( displayedCrop.getTPPlantNotes(),
                                  displayedCrop.getTPPlantNotesState() );
//      tfldPlanterSetting.setInitialText( displayedCrop.getPlanterSetting(),
//                                  displayedCrop.getPlanterSettingState() );
      
      if ( displayedCrop.isDirectSeeded() ) {
          jbgPlantingMethod.setInitialSelection( chkDS, true, displayedCrop.getDirectSeededState() );
//          rdoDS.doInitialClick( displayedCrop.getDirectSeededState() );
//          rdoTP.setHasChanged(false);
      }
      else {
          jbgPlantingMethod.setInitialSelection( chkTP, true, displayedCrop.getDirectSeededState() );
//          rdoTP.doInitialClick( displayedCrop.getDirectSeededState() );
//          rdoDS.setHasChanged(false);
      }
      
      tfldFlatSize.setInitialText( displayedCrop.getTPFlatSize(),
                                  displayedCrop.getTPFlatSizeState() );
      tfldWeeksToTP.setInitialText( displayedCrop.getTPTimeInGHString(),
                                  displayedCrop.getTPTimeInGHState() );
      tfldMatAdjust.setInitialText( displayedCrop.getTPMaturityAdjustString(),
                                  displayedCrop.getTPMaturityAdjustState() );
      
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
      
   }
   
   @Override
    public void setForEditting() {
        tfldCropName.requestFocus();
    }
   
   
   public CPSCrop asCrop() {
      
      CPSCrop crop = new CPSCrop();
      boolean ALLOW_NULL = true;
      
      crop.setID( displayedCrop.getID() );
      
      if ( tfldCropName.hasChanged() ) crop.setCropName( tfldCropName.getText() );
      if ( tfldVarName.hasChanged() ) crop.setVarietyName( tfldVarName.getText(), ALLOW_NULL );
      if ( tfldFamName.hasChanged() ) crop.setFamilyName( tfldFamName.getText(), ALLOW_NULL );
      
      if ( tfldMatDays.hasChanged() ) crop.setMaturityDays( tfldMatDays.getText(), ALLOW_NULL );
     
//      if ( ! cmbxSimilar.getSelectedItem().toString().equalsIgnoreCase("None") )
//         crop.setSimilarCrop( cmbxSimilar.getSelectedItem().toString() );
      
      if ( tareDesc.hasChanged() ) crop.setCropDescription( tareDesc.getText(), ALLOW_NULL );
      
      if ( tareGroups.hasChanged() ) crop.setGroups( tareGroups.getText(), ALLOW_NULL );
      if ( tareOtherReq.hasChanged() ) crop.setOtherRequirements( tareOtherReq.getText(), ALLOW_NULL );
      if ( tareKeywords.hasChanged() ) crop.setKeywords( tareKeywords.getText(), ALLOW_NULL );
      if ( tareNotes.hasChanged() ) crop.setNotes( tareNotes.getText(), ALLOW_NULL );
      
      if ( tfldRowsPerBed.hasChanged() ) crop.setTPRowsPerBed( tfldRowsPerBed.getText(), ALLOW_NULL );
      if ( tfldSpaceInRow.hasChanged() ) crop.setTPSpaceInRow( tfldSpaceInRow.getText(), ALLOW_NULL );
      if ( tfldSpaceBetRows.hasChanged() ) crop.setTPSpaceBetweenRow( tfldSpaceBetRows.getText(), ALLOW_NULL );
      
      if ( tfldPlanter.hasChanged() ) crop.setTPPlantNotes( tfldPlanter.getText(), ALLOW_NULL );
//      if ( tfldPlanterSetting.hasChanged() ) crop.setPlanterSetting( tfldPlanterSetting.getText(), ALLOW_NULL );
      
      if ( tfldFlatSize.hasChanged() ) crop.setTPFlatSize( tfldFlatSize.getText(), ALLOW_NULL );
      if ( tfldWeeksToTP.hasChanged() ) crop.setTPTimeInGH( tfldWeeksToTP.getText(), ALLOW_NULL );
      if ( tfldMatAdjust.hasChanged() ) crop.setTPMaturityAdjust( tfldMatAdjust.getText(), ALLOW_NULL );
      
      if ( tfldYieldPerWeek.hasChanged() ) crop.setYieldPerWeek( tfldYieldPerWeek.getText(), ALLOW_NULL );
      if ( tfldYieldWeeks.hasChanged() ) crop.setYieldNumWeeks( tfldYieldWeeks.getText(), ALLOW_NULL );
      if ( tfldYieldPerFoot.hasChanged() ) crop.setYieldPerFoot( tfldYieldPerFoot.getText(), ALLOW_NULL );
      if ( tfldYieldUnits.hasChanged() ) crop.setCropYieldUnit( tfldYieldUnits.getText(), ALLOW_NULL );
      if ( tfldYieldUnitValue.hasChanged() ) crop.setCropUnitValue( tfldYieldUnitValue.getText(), ALLOW_NULL );
      
//       if ( rdoDS.hasChanged() ) {
//           if ( rdoDS.isSelected() )
//               crop.setDirectSeeded( rdoDS.isSelected() );
//           else if ( rdoTP.isSelected() )
//               crop.setDirectSeeded( !rdoTP.isSelected() );
//           else
//               crop.setDirectSeeded( null, true );
//       }
      
      return crop;
      
   }
   
   protected void buildDetailsPanel() {
       
      tfldCropName = new CPSTextField(10);
      tfldVarName = new CPSTextField( 10 );
      
      ArrayList<String> families = new ArrayList<String>();
      if ( isDataAvailable() )
         families = getDataSource().getFamilyNameList();
      tfldFamName = new CPSTextField( FIELD_LEN_WAY_LONG, families, CPSTextField.MATCH_PERMISSIVE );
      
      tareDesc = new CPSTextArea( 3, FIELD_LEN_WAY_LONG );
      tareGroups = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareKeywords = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareOtherReq = new CPSTextArea( 2, FIELD_LEN_WAY_LONG );
      tareNotes = new CPSTextArea( 3, 40 );
      
      tfldMatDays = new CPSTextField( FIELD_LEN_SHORT );
      tfldRowsPerBed = new CPSTextField( FIELD_LEN_SHORT );
      tfldSpaceInRow = new CPSTextField( FIELD_LEN_SHORT );
      tfldSpaceBetRows = new CPSTextField( FIELD_LEN_SHORT );

      tfldPlanter = new CPSTextField( FIELD_LEN_MED );
      tfldPlanterSetting = new CPSTextField( FIELD_LEN_MED );
      
      tfldFlatSize = new CPSTextField( FIELD_LEN_LONG );
      tfldWeeksToTP = new CPSTextField( FIELD_LEN_SHORT );
      tfldMatAdjust = new CPSTextField( FIELD_LEN_SHORT );
      
      tfldYieldPerWeek = new CPSTextField( FIELD_LEN_MED );
      tfldYieldWeeks = new CPSTextField( FIELD_LEN_SHORT );
      tfldYieldPerFoot = new CPSTextField( FIELD_LEN_SHORT );
      tfldYieldUnits = new CPSTextField( FIELD_LEN_MED );
      tfldYieldUnitValue = new CPSTextField( FIELD_LEN_SHORT );
      
      chkDS = new JCheckBox( "By Direct Seed?", false );
      chkTP = new JCheckBox( "By Transplant?", false );
      jbgPlantingMethod = new CPSButtonGroup( new AbstractButton[] { chkDS, chkTP } );
      jbgPlantingMethod.setSelectionModel( CPSButtonGroup.SELECT_NONE );
      
      
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
      
      LayoutAssist.createLabel(  jplName, 0, 0, "Crop Name:" );
      LayoutAssist.addTextField( jplName, 1, 0, tfldCropName );

      LayoutAssist.createLabel(  jplName, 0, 1, "Variety:" );
      LayoutAssist.addTextField( jplName, 1, 1, tfldVarName );
      
      LayoutAssist.createLabel(  jplName, 0, 2, "Family:" );
      LayoutAssist.addTextField( jplName, 1, 2, tfldFamName );
      
      // starts in column 0, row 3 and spans 2 columns
      LayoutAssist.addSeparator( jplName, 0, 3, 2 );
      
      LayoutAssist.createLabel(  jplName, 0, 4, "Description:" );
      LayoutAssist.addTextArea(  jplName, 1, 4, tareDesc  );
      
      LayoutAssist.addPanelToColumn( columnOne, jplName );
      LayoutAssist.finishColumn( columnOne );
      
      /* ***********************************/
      /* COLUMN TWO (really two and three) */
      /* ***********************************/
      JPanel jplPlanting = initPanelWithGridBagLayout();
      jplPlanting.setBorder( BorderFactory.createTitledBorder( "Planting Info" ) );
      
      /* Applies to both DS & TP */
      LayoutAssist.createLabel(  jplPlanting, 1, 0, "Maturity Days:" );
      LayoutAssist.addTextField( jplPlanting, 2, 0, 2, 1, tfldMatDays );
      
      /* DS Column */
      LayoutAssist.addButton(    jplPlanting, 0, 1, 2, 1, chkDS, GridBagConstraints.CENTER );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 2, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 1, 2, tfldMatAdjust );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 3, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 1, 3, tfldRowsPerBed );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 4, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 1, 4, tfldSpaceBetRows );
      
      
      
      /* TP Column */
      LayoutAssist.addVerticalSeparator( jplPlanting, 2, 1, 11 );
      LayoutAssist.addButton(    jplPlanting, 3, 1, 2, 1, chkTP, GridBagConstraints.CENTER );
      
      LayoutAssist.createLabel(  jplPlanting, 3, 2, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 4, 2, tfldMatAdjust );
     
      LayoutAssist.createLabel(  jplPlanting, 3, 3, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 4, 3, tfldRowsPerBed );
      
      LayoutAssist.createLabel(  jplPlanting, 3, 4, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 4, 4, tfldSpaceBetRows );
      
      LayoutAssist.createLabel(  jplPlanting, 3, 5, "Plant Spacing" );
      LayoutAssist.addTextField( jplPlanting, 4, 5, tfldSpaceInRow );
      
      LayoutAssist.addSeparator( jplPlanting, 3, 6, 2);
      
      LayoutAssist.createLabel(  jplPlanting, 3, 7, "Flat Size" );
      LayoutAssist.addTextField( jplPlanting, 4, 7, tfldFlatSize );
      
      LayoutAssist.createLabel(  jplPlanting, 3, 8, "Weeks to TP" );
      LayoutAssist.addTextField( jplPlanting, 4, 8, tfldWeeksToTP );
      
      LayoutAssist.addSeparator( jplPlanting, 3, 9, 2);
      
      LayoutAssist.createLabel(  jplPlanting, 3, 10, "Planter:" );
      LayoutAssist.addTextField( jplPlanting, 4, 10, tfldPlanter );
      
      LayoutAssist.createLabel(  jplPlanting, 3, 11, "Planter Setting" );
      LayoutAssist.addTextField( jplPlanting, 4, 11, tfldPlanterSetting );
      
//      LayoutAssist.addSubPanel(  jplDetails, 2, 1, 2, 13, jplPlanting );
      LayoutAssist.addPanelToColumn( columnTwo, jplPlanting );
      LayoutAssist.finishColumn( columnTwo );
      
      /* *************************************/
      /* COLUMN THREE (really four and five) */
      /* *************************************/
      JPanel jplYield = initPanelWithGridBagLayout();
      jplYield.setBorder( BorderFactory.createTitledBorder( "Yield Info" ) );
      
      /* unit, per foot, weeks, per week, value */
      LayoutAssist.createLabel(  jplYield, 0, 0, "Yield Units" );
      LayoutAssist.addTextField( jplYield, 1, 0, tfldYieldUnits);
      
      LayoutAssist.createLabel(  jplYield, 0, 1, "Total Yield/Ft" );
      LayoutAssist.addTextField( jplYield, 1, 1, tfldYieldPerFoot );
      
      LayoutAssist.createLabel(  jplYield, 0, 2, "Weeks of Yield" );
      LayoutAssist.addTextField( jplYield, 1, 2, tfldYieldWeeks );
      
      LayoutAssist.createLabel(  jplYield, 0, 3, "Yield/Week" );
      LayoutAssist.addTextField( jplYield, 1, 3, tfldYieldPerWeek );
      
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

      LayoutAssist.createLabel(  jplMisc, 0, 0, "<html>Other <br>Requirements:</html>" );
      LayoutAssist.addTextArea(  jplMisc, 1, 0, 1, 1, tareOtherReq );
      
      LayoutAssist.createLabel(  jplMisc, 0, 2, "<html>Belongs to <br>Groups:</html>" );
      LayoutAssist.addTextArea(  jplMisc, 1, 2, 1, 1, tareGroups );
      
      LayoutAssist.createLabel(  jplMisc, 0, 4, "Keywords:" );
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
      LayoutAssist.createLabel(  jplNotes, 0, 0, "Notes:" );
      LayoutAssist.addTextArea(  jplNotes, 1, 0, tareNotes );
      LayoutAssist.addSubPanel( jplDetails, 0, 14, 7, 1, jplNotes );

      
      if ( uiManager != null )
         uiManager.signalUIChanged();  
   }

   
    protected void saveChangesToRecord() {
       CPSCrop diff = (CPSCrop) displayedCrop.diff( this.asCrop() );
       if ( diff.getID() == -1 )
          return; // no differences!
       
       if ( ! displayedCrop.isSingleRecord() )
          getDataSource().updateCrops( diff, displayedCrop.getCommonIDs() );
       else
          getDataSource().updateCrop( diff );
       
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
        tfldFlatSize.updateAutocompletionList( getDataSource().getFlatSizeList(),
                                              CPSTextField.MATCH_PERMISSIVE );
    }
   
   @Override
   public void dataUpdated() {
      if ( isRecordDisplayed() ) {
         this.displayRecord( getDataSource().getCropInfo( getDisplayedRecord().getID() ) );
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
