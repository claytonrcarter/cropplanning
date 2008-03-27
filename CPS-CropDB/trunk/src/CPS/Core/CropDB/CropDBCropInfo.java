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
import javax.swing.*;

public class CropDBCropInfo extends CPSDetailView {
   
   private CPSTextField tfldCropName, tfldVarName, tfldFamName;
   private CPSTextField tfldMatDays, tfldRowsPerBed, tfldSpaceInRow, tfldSpaceBetRows;
   private CPSTextField tfldFlatSize, tfldWeeksToTP, tfldMatAdjust, tfldPlanter, tfldPlanterSetting;
   private CPSTextArea tareDesc, tareGroups, tareKeywords, tareOtherReq, tareNotes;
   private CPSTextField tfldYieldPerWeek, tfldYieldWeeks, tfldYieldPerFoot, tfldYieldUnits, tfldYieldUnitValue;
   private JLabel lblSimilar;
   private JComboBox cmbxSimilar = null;
   private CPSRadioButton rdoDS, rdoTP;
   private CPSButtonGroup jbgPlantingMethod;
   
   private CPSCrop displayedCrop;
      
   CropDBCropInfo( CPSMasterDetailModule mdm ) {
       super( mdm, "Crop Info" );
   }
   
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
      
      tfldRowsPerBed.setInitialText( displayedCrop.getRowsPerBedString(),
                                  displayedCrop.getRowsPerBedState() );
      tfldSpaceInRow.setInitialText( displayedCrop.getSpaceInRowString(),
                                  displayedCrop.getSpaceInRowState() );
      tfldSpaceBetRows.setInitialText(  displayedCrop.getSpaceBetweenRowString(),
                                  displayedCrop.getSpaceBetweenRowState() );

      tfldPlanter.setInitialText( displayedCrop.getPlanter(),
                                  displayedCrop.getPlanterState() );
      tfldPlanterSetting.setInitialText( displayedCrop.getPlanterSetting(),
                                  displayedCrop.getPlanterSettingState() );
      
      if ( displayedCrop.isDirectSeeded() ) {
          jbgPlantingMethod.setInitialSelection( rdoDS, true, displayedCrop.getDirectSeededState() );
//          rdoDS.doInitialClick( displayedCrop.getDirectSeededState() );
//          rdoTP.setHasChanged(false);
      }
      else {
          jbgPlantingMethod.setInitialSelection( rdoTP, true, displayedCrop.getDirectSeededState() );
//          rdoTP.doInitialClick( displayedCrop.getDirectSeededState() );
//          rdoDS.setHasChanged(false);
      }
      
      tfldFlatSize.setInitialText( displayedCrop.getFlatSize(),
                                  displayedCrop.getFlatSizeState() );
      tfldWeeksToTP.setInitialText( displayedCrop.getTimeToTPString(),
                                  displayedCrop.getTimeToTPState() );
      tfldMatAdjust.setInitialText( displayedCrop.getMaturityAdjustString(),
                                  displayedCrop.getMaturityAdjustState() );
      
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
      
      if ( tfldRowsPerBed.hasChanged() ) crop.setRowsPerBed( tfldRowsPerBed.getText(), ALLOW_NULL );
      if ( tfldSpaceInRow.hasChanged() ) crop.setSpaceInRow( tfldSpaceInRow.getText(), ALLOW_NULL );
      if ( tfldSpaceBetRows.hasChanged() ) crop.setSpaceBetweenRow( tfldSpaceBetRows.getText(), ALLOW_NULL );
      
      if ( tfldPlanter.hasChanged() ) crop.setPlanter( tfldPlanter.getText(), ALLOW_NULL );
      if ( tfldPlanterSetting.hasChanged() ) crop.setPlanterSetting( tfldPlanterSetting.getText(), ALLOW_NULL );
      
      if ( tfldFlatSize.hasChanged() ) crop.setFlatSize( tfldFlatSize.getText(), ALLOW_NULL );
      if ( tfldWeeksToTP.hasChanged() ) crop.setTimeToTP( tfldWeeksToTP.getText(), ALLOW_NULL );
      if ( tfldMatAdjust.hasChanged() ) crop.setMaturityAdjust( tfldMatAdjust.getText(), ALLOW_NULL );
      
      if ( tfldYieldPerWeek.hasChanged() ) crop.setYieldPerWeek( tfldYieldPerWeek.getText(), ALLOW_NULL );
      if ( tfldYieldWeeks.hasChanged() ) crop.setYieldNumWeeks( tfldYieldWeeks.getText(), ALLOW_NULL );
      if ( tfldYieldPerFoot.hasChanged() ) crop.setYieldPerFoot( tfldYieldPerFoot.getText(), ALLOW_NULL );
      if ( tfldYieldUnits.hasChanged() ) crop.setCropYieldUnit( tfldYieldUnits.getText(), ALLOW_NULL );
      if ( tfldYieldUnitValue.hasChanged() ) crop.setCropUnitValue( tfldYieldUnitValue.getText(), ALLOW_NULL );
      
       if ( rdoDS.hasChanged() ) {
           if ( rdoDS.isSelected() )
               crop.setDirectSeeded( rdoDS.isSelected() );
           else if ( rdoTP.isSelected() )
               crop.setDirectSeeded( !rdoTP.isSelected() );
           else
               crop.setDirectSeeded( null, true );
       }
      
      return crop;
      
   }
   
   protected void buildDetailsPanel() {
       
      tfldCropName = new CPSTextField(10);
      tfldVarName = new CPSTextField( 10 );
      tfldFamName = new CPSTextField( 10, getDataSource().getFamilyNameList(), CPSTextField.MATCH_PERMISSIVE );
      
      tareDesc = new CPSTextArea( 3, 10 );
      tareGroups = new CPSTextArea( 2, 10 );
      tareKeywords = new CPSTextArea( 2, 10 );
      tareOtherReq = new CPSTextArea( 2, 10 );
      tareNotes = new CPSTextArea( 3, 40 );
      
      tfldMatDays = new CPSTextField( 3 );
      tfldRowsPerBed = new CPSTextField( 3 );
      tfldSpaceInRow = new CPSTextField( 3 );
      tfldSpaceBetRows = new CPSTextField( 3 );

      tfldPlanter = new CPSTextField( 5 );
      tfldPlanterSetting = new CPSTextField( 5 );
      
      tfldFlatSize = new CPSTextField( 5 );
      tfldWeeksToTP = new CPSTextField( 3 );
      tfldMatAdjust = new CPSTextField( 3 );
      
      tfldYieldPerWeek = new CPSTextField( 5 );
      tfldYieldWeeks = new CPSTextField( 3 );
      tfldYieldPerFoot = new CPSTextField( 3 );
      tfldYieldUnits = new CPSTextField( 5 );
      tfldYieldUnitValue = new CPSTextField( 3 );
      
      rdoDS = new CPSRadioButton( "DS", false );
      rdoTP = new CPSRadioButton( "TP", false );
//      HERE - change this so that the buttons are exclusive but can be turned off
      jbgPlantingMethod = new CPSButtonGroup( new AbstractButton[] { rdoDS, rdoTP } );
      jbgPlantingMethod.setSelectionModel( CPSButtonGroup.SELECT_NONE );
      
//      cmbxSimilar = new JComboBox( new String[] {"None"} );
//      updateSimilarCropsList();
      
      initDetailsPanel();
      
      /* the format for these calls is: panel, column, row, component */
      /* ***********************************/
      /* COLUMN ONE (really zero and one)  */
      /* ***********************************/
      LayoutAssist.createLabel(  jplDetails, 0, 0, "Crop Name:" );
      LayoutAssist.addTextField( jplDetails, 1, 0, tfldCropName );

      LayoutAssist.createLabel(  jplDetails, 0, 1, "Variety:" );
      LayoutAssist.addTextField( jplDetails, 1, 1, tfldVarName );
      
      LayoutAssist.createLabel(  jplDetails, 0, 2, "Family:" );
      LayoutAssist.addTextField( jplDetails, 1, 2, tfldFamName );
      
      // starts in column 0, row 3 and spans 2 columns
      LayoutAssist.addSeparator( jplDetails, 0, 3, 2 );
      
      LayoutAssist.createLabel(  jplDetails, 0, 4, "Description:" );
      LayoutAssist.addTextArea(  jplDetails, 1, 4, tareDesc  );
      
      /* ***********************************/
      /* COLUMN TWO (really two and three) */
      /* ***********************************/
      JPanel jplPlanting = initPanelWithGridBagLayout();
      jplPlanting.setBorder( BorderFactory.createTitledBorder( "Planting Info" ) );
      
      LayoutAssist.addComponent( jplPlanting, 0, 0, rdoDS, GridBagConstraints.EAST );
      LayoutAssist.addButton(    jplPlanting, 1, 0, rdoTP );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 1, "Maturity Days:" );
      LayoutAssist.addTextField( jplPlanting, 1, 1, tfldMatDays );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 2, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 1, 2, tfldRowsPerBed );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 3, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 1, 3, tfldSpaceBetRows );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 4, "Plant Spacing" );
      LayoutAssist.addTextField( jplPlanting, 1, 4, tfldSpaceInRow );
      
      LayoutAssist.addSeparator( jplPlanting, 0, 5, 2);
      
      LayoutAssist.createLabel(  jplPlanting, 0, 6, "Flat Size" );
      LayoutAssist.addTextField( jplPlanting, 1, 6, tfldFlatSize );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 7, "Weeks to TP" );
      LayoutAssist.addTextField( jplPlanting, 1, 7, tfldWeeksToTP );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 8, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 1, 8, tfldMatAdjust );
      
      LayoutAssist.addSeparator( jplPlanting, 0, 9, 2);
      
      LayoutAssist.createLabel(  jplPlanting, 0, 10, "Planter:" );
      LayoutAssist.addTextField( jplPlanting, 1, 10, tfldPlanter );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 11, "Planter Setting" );
      LayoutAssist.addTextField( jplPlanting, 1, 11, tfldPlanterSetting );
      
      LayoutAssist.addSubPanel(  jplDetails, 2, 1, 2, 13, jplPlanting );
      
      
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
      
      LayoutAssist.addSubPanel(  jplDetails, 4, 1, 2, 6, jplYield );
      
      /* *************************************/
      /* COLUMN FOUR (actually six and seven */
      /* *************************************/
//      lblSimilar = new JLabel( "Similar to:" );
//      LayoutAssist.addLabel(     jplDetails, 6, 0, lblSimilar );
//      LayoutAssist.addComboBox(  jplDetails, 7, 0, cmbxSimilar );
      
      JPanel jplMisc = initPanelWithGridBagLayout();
      jplMisc.setBorder( BorderFactory.createTitledBorder( "Misc Info" ) );

      LayoutAssist.createLabel(  jplMisc, 0, 0, "<html>Other <br>Requirements:</html>" );
      LayoutAssist.addTextArea(  jplMisc, 1, 0, 1, 1, tareOtherReq );
      
      LayoutAssist.createLabel(  jplMisc, 0, 2, "<html>Belongs to <br>Groups:</html>" );
      LayoutAssist.addTextArea(  jplMisc, 1, 2, 1, 1, tareGroups );
      
      LayoutAssist.createLabel(  jplMisc, 0, 4, "Keywords:" );
      LayoutAssist.addTextArea(  jplMisc, 1, 4, 1, 1, tareKeywords );
      
      LayoutAssist.addSubPanel( jplDetails, 6, 1, 2, 7, jplMisc);
      
      
      /* *************************************/
      /* BOTTOW ROW                          */
      /* *************************************/
      // Notes TextArea is set to span all remaining columns
      LayoutAssist.createLabel(  jplDetails, 0, 14, "Notes:" );
      LayoutAssist.addTextArea(  jplDetails, 1, 14, 7, tareNotes );
      
      
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
    
}
