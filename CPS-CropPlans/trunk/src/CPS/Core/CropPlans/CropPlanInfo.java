/*
 * CropDBCropInfo.java
 *
 * Created on March 15, 2007, 11:26 AM by Clayton
 */

package CPS.Core.CropPlans;

import CPS.Data.CPSRecord;
import CPS.UI.Modules.CPSDetailView;
import CPS.UI.Swing.LayoutAssist;
import CPS.Data.CPSPlanting;
import CPS.Module.*;
import CPS.UI.Swing.CPSTextArea;
import CPS.UI.Swing.CPSTextField;
import javax.swing.*;

public class CropPlanInfo extends CPSDetailView {
   
   
   private CPSTextField tfldCropName, tfldVarName;
   private CPSTextField tfldDatePlant, tfldDateTP, tfldDateHarvest;
   private CPSTextField tfldMatDays;
   private CPSTextField tfldBedsToPlant, tfldRowFtToPlant, tfldPlantsNeeded,
                      tfldPlantsToStart, tfldFlatsNeeded;
   private CPSTextArea tareGroups, tareKeywords, tareOtherReq, tareNotes;
   private CPSTextField tfldLocation, tfldStatus;
   
// yet to implement:
   //          case PROP_COMPLETED:     return completed; 
           
   private CPSPlanting displayedPlanting;
   
   CropPlanInfo( CropPlanUI ui ) {
      super( ui, "Planting Info" );
   }
   
   @Override
    public void displayRecord( CPSRecord r ) {
        displayRecord( (CPSPlanting) r );
    }
   
    @Override
    public CPSRecord getDisplayedRecord() {
        return displayedPlanting;
    }
   
   public void displayRecord( CPSPlanting p ) {

        displayedPlanting = p;

        if ( ! isRecordDisplayed() ) {
            setRecordDisplayed();
            rebuildMainPanel();
        }

        tfldCropName.setInitialText(displayedPlanting.getCropName());
        tfldVarName.setInitialText(displayedPlanting.getVarietyName());

        if (displayedPlanting.getMaturityDays() > 0)
            tfldMatDays.setInitialText("" + displayedPlanting.getMaturityDays());
        else
            tfldMatDays.setInitialText("");

        tfldDatePlant.setInitialText(displayedPlanting.getDateToPlantString());
        tfldDateHarvest.setInitialText(displayedPlanting.getDateToHarvestString());

        tareGroups.setText(displayedPlanting.getGroups());
        tareOtherReq.setText(displayedPlanting.getOtherRequirments());
        tareKeywords.setText(displayedPlanting.getKeywords());
        tareNotes.setText(displayedPlanting.getNotes());
    }
   
   
    protected void saveChangesToRecord() {
        String selectedPlan = getDisplayedTableName();
        CPSPlanting diff = (CPSPlanting) displayedPlanting.diff( this.asPlanting() );
        if ( diff.getID() != -1 )
            dataModel.updatePlanting( selectedPlan, diff );
    }

   // add new crop data here
   public CPSPlanting asPlanting() {
      
      CPSPlanting p = new CPSPlanting();
      
      p.setID( displayedPlanting.getID() );
      
      p.setCropName( tfldCropName.getText() );
      p.setVarietyName( tfldVarName.getText() );
      
      p.setMaturityDays( tfldMatDays.getText() );
     
      p.setDateToPlant( tfldDatePlant.getText() );
      if ( tfldDateHarvest.hasChanged() ) {
          System.out.println("Harvest date has changed to: '" + tfldDateHarvest.getText() + "'" );
          p.addChangedProperty( p.PROP_DATE_HARVEST );
          p.setDateToHarvest(tfldDateHarvest.getText());
      }
 
      p.setGroups( tareGroups.getText() );
      p.setOtherRequirements( tareOtherReq.getText() );
      p.setKeywords( tareKeywords.getText() );
      p.setNotes( tareNotes.getText() );
      
      return p;
      
   }
   
   protected void buildDetailsPanel() {
       
        tfldCropName = new CPSTextField(10);
        tfldVarName = new CPSTextField(10);
        tfldMatDays = new CPSTextField(5);
        tfldDatePlant = new CPSTextField(10);
        tfldDateHarvest = new CPSTextField(10);
        tareGroups = new CPSTextArea(3, 10);
        tareKeywords = new CPSTextArea(3, 10);
        tareOtherReq = new CPSTextArea(3, 10);
        tareNotes = new CPSTextArea(5, 20);

        initDetailsPanel();
        
        /* the format for these calls is: panel, column, row, component */
        LayoutAssist.createLabel(jplDetails, 0, 0, "Crop Name:");
        LayoutAssist.addTextField(jplDetails, 1, 0, tfldCropName);

        LayoutAssist.createLabel(jplDetails, 2, 0, "Mat. Days:");
        LayoutAssist.addTextField(jplDetails, 3, 0, tfldMatDays);

        // TODO maybe: if ( isVariety )
        LayoutAssist.createLabel(jplDetails, 0, 1, "Variety:");
        LayoutAssist.addTextField(jplDetails, 1, 1, tfldVarName);

        LayoutAssist.createLabel(jplDetails, 2, 1, "Planting Date:");
        LayoutAssist.addTextField(jplDetails, 3, 1, tfldDatePlant);

        LayoutAssist.createLabel(jplDetails, 2, 2, "Harvest Date:");
        LayoutAssist.addTextField(jplDetails, 3, 2, tfldDateHarvest);


        LayoutAssist.createLabel(jplDetails, 2, 5, "Belongs to Groups:");
        LayoutAssist.addTextArea(jplDetails, 3, 5, tareGroups);

        LayoutAssist.createLabel(jplDetails, 0, 5, "Other Requirements:");
        LayoutAssist.addTextArea(jplDetails, 1, 5, tareOtherReq);

        LayoutAssist.createLabel(jplDetails, 2, 7, "Keywords:");
        LayoutAssist.addTextArea(jplDetails, 3, 7, tareKeywords);

        LayoutAssist.createLabel(jplDetails, 0, 6, "Notes:");
        LayoutAssist.addTextArea(jplDetails, 1, 6, tareNotes);

   }
    
}
