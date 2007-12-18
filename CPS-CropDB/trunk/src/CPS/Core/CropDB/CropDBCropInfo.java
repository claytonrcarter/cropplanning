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
import javax.swing.*;
import javax.swing.table.TableModel;

public class CropDBCropInfo extends CPSDetailView {
   
   private JTextField tfldCropName, tfldVarName, tfldFamName, tfldDesc;
   private JTextField tfldMatDays;
   private JTextArea tareGroups, tareKeywords, tareOtherReq, tareNotes;
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
      tfldDesc.setText( displayedCrop.getCropDescription() );
      
      if ( displayedCrop.getMaturityDays() > 0 )
         tfldMatDays.setText( "" + displayedCrop.getMaturityDays() );
      else
         tfldMatDays.setText("");
      
      tareGroups.setText( displayedCrop.getGroups() );
      tareOtherReq.setText( displayedCrop.getOtherRequirments() );
      tareKeywords.setText( displayedCrop.getKeywords() );
      tareNotes.setText( displayedCrop.getNotes() );
      
   }
   
    @Override
    protected void saveChangesToRecord() {
       CPSCrop diff = (CPSCrop) displayedCrop.diff( this.asCrop() );
       if ( diff.getID() != -1 ) {
           dataModel.updateCrop( diff );
           updateSimilarCropsList();
       }
    }
   
   public CPSCrop asCrop() {
      
      CPSCrop crop = new CPSCrop();
      
      crop.setID( displayedCrop.getID() );
      
      crop.setCropName( tfldCropName.getText() );
      crop.setVarietyName( tfldVarName.getText() );
      crop.setFamilyName( tfldFamName.getText() );
      
      if ( ! tfldMatDays.getText().equals("") )
         crop.setMaturityDays( Integer.parseInt( tfldMatDays.getText() ));
     
      if ( ! cmbxSimilar.getSelectedItem().toString().equalsIgnoreCase("None") )
         crop.setSimilarCrop( dataModel.getCropInfo( cmbxSimilar.getSelectedItem().toString() ));
      
      crop.setCropDescription( tfldDesc.getText() );
      
      crop.setGroups( tareGroups.getText() );
      crop.setOtherRequirements( tareOtherReq.getText() );
      crop.setKeywords( tareKeywords.getText() );
      crop.setNotes( tareNotes.getText() );
      
      return crop;
      
   }
   
   protected void buildDetailsPanel() {
       
      tfldCropName = new JTextField(10);
      tfldVarName = new JTextField(10);
      tfldFamName = new JTextField(10);
      tfldDesc = new JTextField(10);
      
      tfldMatDays = new JTextField(5);

      tareGroups = new JTextArea( 3, 10 );
      tareKeywords = new JTextArea( 3, 10 );
      tareOtherReq = new JTextArea( 3, 10 );
      tareNotes = new JTextArea( 5, 20 );
      
      cmbxSimilar = new JComboBox( new String[] {"None"} );
      updateSimilarCropsList();
      
      initDetailsPanel();
      
      /* the format for these calls is: panel, column, row, component */
      
      LayoutAssist.createLabel(  jplDetails, 0, 0, "Crop Name:" );
      LayoutAssist.addTextField( jplDetails, 1, 0, tfldCropName );

      // TODO maybe: if ( isVariety )
      LayoutAssist.createLabel(  jplDetails, 0, 1, "Variety:" );
      LayoutAssist.addTextField( jplDetails, 1, 1, tfldVarName );

      lblSimilar = new JLabel( "Similar to:" );
      LayoutAssist.addLabel(     jplDetails, 2, 0, lblSimilar );
      LayoutAssist.addComboBox(  jplDetails, 3, 0, cmbxSimilar );
      
      LayoutAssist.createLabel(  jplDetails, 2, 1, "Family:" );
      LayoutAssist.addTextField( jplDetails, 3, 1, tfldFamName );
      
      LayoutAssist.createLabel(  jplDetails, 0, 2, "Description:" );
      LayoutAssist.addTextField( jplDetails, 1, 2, tfldDesc );
      
      LayoutAssist.createLabel(  jplDetails, 0, 3, "Mat. Days:" );
      LayoutAssist.addTextField( jplDetails, 1, 3, tfldMatDays );
      
      LayoutAssist.createLabel(  jplDetails, 2, 3, "Belongs to Groups:" );
      LayoutAssist.addTextArea(  jplDetails, 3, 3, tareGroups );

      LayoutAssist.createLabel(  jplDetails, 0, 5, "Other Requirements:" );
      LayoutAssist.addTextArea(  jplDetails, 1, 5, tareOtherReq );
      
      LayoutAssist.createLabel(  jplDetails, 2, 5, "Keywords:" );
      LayoutAssist.addTextArea(  jplDetails, 3, 5, tareKeywords );
      
      LayoutAssist.createLabel(  jplDetails, 0, 6, "Notes:" );
      LayoutAssist.addTextArea(  jplDetails, 1, 6, tareNotes );
      
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
