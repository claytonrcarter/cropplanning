/*
 * CropDBCropInfo.java
 *
 * Created on March 15, 2007, 11:26 AM by Clayton
 */

package CPS.Core.CropDB;

import CPS.UI.Swing.LayoutAssist;
import CPS.Data.*;
import CPS.Module.*;
import CPS.UI.Modules.CPSDetailView;
import CPS.UI.Swing.CPSTextArea;
import CPS.UI.Swing.CPSTextField;
import javax.swing.*;
import javax.swing.table.TableModel;

public class CropDBCropInfo extends CPSDetailView {
   
   private CPSTextField tfldCropName, tfldVarName, tfldFamName;
   private CPSTextField tfldMatDays, tfldRowsPerBed, tfldSpaceInRow, tfldSpaceBetRows;
   private CPSTextField tfldFlatSize, tfldWeeksToTP, tfldMatAdjust, tfldPlanter, tfldPlanterSetting;
   private CPSTextArea tareDesc, tareGroups, tareKeywords, tareOtherReq, tareNotes;
   private CPSTextField tfldYieldPerWeek, tfldYieldWeeks, tfldYieldPerFoot, tfldYieldUnits, tfldYieldUnitValue;
   private JLabel lblSimilar;
   private JComboBox cmbxSimilar = null;
   
   private CPSCrop displayedCrop;
      
   CropDBCropInfo( CropDBUI ui ) {
       super( ui, "Crop Info" );
   }
   
   public CPSRecord getDisplayedRecord() { return displayedCrop; }
   public void displayRecord( CPSRecord r ) { displayRecord( (CPSCrop) r ); }
   public void displayRecord( CPSCrop crop ) {
      
      displayedCrop = crop;
      
      if ( ! isRecordDisplayed() ) {
         setRecordDisplayed();
         rebuildMainPanel();
      }
      
      tfldCropName.setText( displayedCrop.getCropName() );
      tfldVarName.setText( displayedCrop.getVarietyName() );
      
      if ( displayedCrop.isCrop() ) {

         lblSimilar.setVisible( true );
         cmbxSimilar.setVisible( true );
         
         if ( displayedCrop.getSimilarCrop().getID() != -1 )
            cmbxSimilar.setSelectedItem( displayedCrop.getSimilarCrop().getCropName() );
         else
            cmbxSimilar.setSelectedItem( "None" );
      } 
      else {
         lblSimilar.setVisible( false );
         cmbxSimilar.setVisible( false ); 
      }
      
      tfldFamName.setText( displayedCrop.getFamilyName() );
      tareDesc.setText( displayedCrop.getCropDescription() );
      
      if ( displayedCrop.getMaturityDays() > 0 )
         tfldMatDays.setText( "" + displayedCrop.getMaturityDays() );
      else
         tfldMatDays.setText("");
      
      tareGroups.setText( displayedCrop.getGroups() );
      tareOtherReq.setText( displayedCrop.getOtherRequirments() );
      tareKeywords.setText( displayedCrop.getKeywords() );
      tareNotes.setText( displayedCrop.getNotes() );
      
      tfldRowsPerBed.setText( "" + displayedCrop.getRowsPerBed() );
      tfldSpaceInRow.setText( "" + displayedCrop.getSpaceInRow() );
      tfldSpaceBetRows.setText( "" + displayedCrop.getSpaceBetweenRow() );

      tfldPlanter.setText( displayedCrop.getPlanter() );
      tfldPlanterSetting.setText( displayedCrop.getPlanterSetting() );
      
      tfldFlatSize.setText( displayedCrop.getFlatSize() );
      tfldWeeksToTP.setText( "" + displayedCrop.getTimeToTP() );
      tfldMatAdjust.setText( "" + displayedCrop.getMaturityAdjust() );
      
      tfldYieldPerWeek.setText( "" + displayedCrop.getYieldPerWeek() );
      tfldYieldWeeks.setText( "" + displayedCrop.getYieldNumWeeks() );
      tfldYieldPerFoot.setText( "" + displayedCrop.getYieldPerFoot() );
      tfldYieldUnits.setText( displayedCrop.getCropYieldUnit() );
      tfldYieldUnitValue.setText( "" + displayedCrop.getCropUnitValue() );
      
      
   }
   
   public CPSCrop asCrop() {
      
      CPSCrop crop = new CPSCrop();
      
      crop.setID( displayedCrop.getID() );
      
      crop.setCropName( tfldCropName.getText(), tfldCropName.hasChanged() );
      crop.setVarietyName( tfldVarName.getText(), tfldVarName.hasChanged() );
      crop.setFamilyName( tfldFamName.getText(), tfldFamName.hasChanged() );
      
      if ( ! tfldMatDays.getText().equals("") )
         crop.setMaturityDays( Integer.parseInt( tfldMatDays.getText() ), tfldMatDays.hasChanged() );
     
      if ( ! cmbxSimilar.getSelectedItem().toString().equalsIgnoreCase("None") )
         crop.setSimilarCrop( dataModel.getCropInfo( cmbxSimilar.getSelectedItem().toString() ) );
      
      crop.setCropDescription( tareDesc.getText(), tareDesc.hasChanged() );
      
      crop.setGroups( tareGroups.getText(), tareGroups.hasChanged() );
      crop.setOtherRequirements( tareOtherReq.getText(), tareOtherReq.hasChanged() );
      crop.setKeywords( tareKeywords.getText(), tareKeywords.hasChanged() );
      crop.setNotes( tareNotes.getText(), tareNotes.hasChanged() );
      
      crop.setRowsPerBed( tfldRowsPerBed.getText(), tfldRowsPerBed.hasChanged() );
      crop.setSpaceInRow( tfldSpaceInRow.getText(), tfldSpaceInRow.hasChanged() );
      crop.setSpaceBetweenRow( tfldSpaceBetRows.getText(), tfldSpaceBetRows.hasChanged() );
      
      crop.setPlanter( tfldPlanter.getText(), tfldPlanter.hasChanged() );
      crop.setPlanterSetting( tfldPlanterSetting.getText(), tfldPlanterSetting.hasChanged() );
      
      crop.setFlatSize( tfldFlatSize.getText(), tfldFlatSize.hasChanged() );
      crop.setTimeToTP( tfldWeeksToTP.getText(), tfldWeeksToTP.hasChanged() );
      crop.setMaturityAdjust( tfldMatAdjust.getText(), tfldMatAdjust.hasChanged() );
      
      crop.setYieldPerWeek( tfldYieldPerWeek.getText(), tfldYieldPerWeek.hasChanged() );
      crop.setYieldNumWeeks( tfldYieldWeeks.getText(), tfldYieldWeeks.hasChanged() );
      crop.setYieldPerFoot( tfldYieldPerFoot.getText(), tfldYieldPerFoot.hasChanged() );
      crop.setCropYieldUnit( tfldYieldUnits.getText(), tfldYieldUnits.hasChanged() );
      crop.setCropUnitValue( tfldYieldUnitValue.getText(), tfldYieldUnitValue.hasChanged() );
      
      return crop;
      
   }
   
   protected void buildDetailsPanel() {
       
      tfldCropName = new CPSTextField(10);
      tfldVarName = new CPSTextField(10);
      tfldFamName = new CPSTextField(10);
      
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
      
      cmbxSimilar = new JComboBox( new String[] {"None"} );
      updateSimilarCropsList();
      
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
      
      LayoutAssist.createLabel(  jplPlanting, 0, 0, "Maturity Days:" );
      LayoutAssist.addTextField( jplPlanting, 1, 0, tfldMatDays );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 1, "Rows/Bed" );
      LayoutAssist.addTextField( jplPlanting, 1, 1, tfldRowsPerBed );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 2, "Row Spacing" );
      LayoutAssist.addTextField( jplPlanting, 1, 2, tfldSpaceBetRows );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 3, "Plant Spacing" );
      LayoutAssist.addTextField( jplPlanting, 1, 3, tfldSpaceInRow );
      
      LayoutAssist.addSeparator( jplPlanting, 0, 4, 2);
      
      LayoutAssist.createLabel(  jplPlanting, 0, 5, "Flat Size" );
      LayoutAssist.addTextField( jplPlanting, 1, 5, tfldFlatSize );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 6, "Weeks to TP" );
      LayoutAssist.addTextField( jplPlanting, 1, 6, tfldWeeksToTP );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 7, "Adjust Mat. Days" );
      LayoutAssist.addTextField( jplPlanting, 1, 7, tfldMatAdjust );
      
      LayoutAssist.addSeparator( jplPlanting, 0, 8, 2);
      
      LayoutAssist.createLabel(  jplPlanting, 0, 9, "Planter:" );
      LayoutAssist.addTextField( jplPlanting, 1, 9, tfldPlanter );
      
      LayoutAssist.createLabel(  jplPlanting, 0, 10, "Planter Setting" );
      LayoutAssist.addTextField( jplPlanting, 1, 10, tfldPlanterSetting );
      
      LayoutAssist.addSubPanel(  jplDetails, 2, 1, 2, 12, jplPlanting );
      
      
      /* *************************************/
      /* COLUMN THREE (really four and five) */
      /* *************************************/
      lblSimilar = new JLabel( "Similar to:" );
      LayoutAssist.addLabel(     jplDetails, 4, 0, lblSimilar );
      LayoutAssist.addComboBox(  jplDetails, 5, 0, cmbxSimilar );
      
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
      LayoutAssist.createLabel(  jplDetails, 0, 13, "Notes:" );
      LayoutAssist.addTextArea(  jplDetails, 1, 13, 7, tareNotes );
      
      
      
   }

   
    protected void saveChangesToRecord() {
       CPSCrop diff = (CPSCrop) displayedCrop.diff( this.asCrop() );
       if ( diff.getID() != -1 ) {
           dataModel.updateCrop( diff );
           updateSimilarCropsList();
       }
    }
 
   
   // query the db and populate the combobox of similar crops
   // TODO should be called as a hook when the Master list adds a new entry
   protected void updateSimilarCropsList() {
      if ( cmbxSimilar == null || ! isDataAvailable() )
          return;
       
      // TODO updated whenever a new crop is created.
      // TODO should filter out blanks (done) and duplicates
      // now fill in the SimilarTo combobox
      TableModel tm = dataModel.getCropList( "crop_name" );
      
      int nameCol = 0;
      // find the column storing the crop names
      while ( ! tm.getColumnName( nameCol ).equalsIgnoreCase("crop_name") && 
              nameCol < tm.getColumnCount() ) {
         nameCol++;
      }
      // add the crop names to the combo box
      for ( int i = 0; i < tm.getRowCount(); i++ ) {
          String name = tm.getValueAt( i, nameCol ).toString();
          if ( name.equals("") )
              continue;
          cmbxSimilar.addItem( name );
      }      
   }
   
}
