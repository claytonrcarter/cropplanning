/*
 * CSV.java
 *
 * Created on April 3, 2007, 2:58 PM by Clayton
 */

package CPS;

import CPS.Data.CPSCrop;
import CPS.Module.CPSDataModel;
import java.util.ArrayList;
import javax.swing.table.TableModel;

public class CSV extends CPSDataModel {
   
   CSVTableModel ctm;
   
   public CSV( String file ) {
      ctm = new CSVTableModel( file );
   }


   public ArrayList<CPSCrop> exportCropsAndVarieties() {
      ArrayList<CPSCrop> l = new ArrayList<CPSCrop>();
      CPSCrop temp;
       
      System.out.println( "Exporting data." );
        
      for ( int i = 0; i < ctm.getRowCount(); i++ ) {
         l.add( this.getCropInfoForRow(i) );
      }
      
      return l;
   }

   
   public CPSCrop getCropInfoForRow( int selectedRow ) {
        
      CPSCrop temp = new CPSCrop();
         
      //"crop_name","var_name","similar_to","bot_name","fam_name",
      //"Groups","Successions","Desc","Keywords","Fudge","other_req","Notes",
      //"Maturity","mat_adjust","misc_adjust",
      //"seeds_sources","seeds_item_codes","seeds_unit_size"
         
      //temp.setID( (int) ( Math.random() * 10000 ) );
      temp.setID( selectedRow );
      int col = 0;
      temp.setCropName( ctm.getStringAt( selectedRow, col++ ) );
      temp.setVarietyName( ctm.getStringAt( selectedRow, col++ ));

      temp.setSimilarCrop( this.getCropInfo( ctm.getStringAt( selectedRow, col++ ) ));
      
      temp.setBotanicalName( ctm.getStringAt( selectedRow, col++ ));
      temp.setFamilyName( ctm.getStringAt( selectedRow, col++ ));

      temp.setGroups( ctm.getStringAt( selectedRow, col++ ));
      temp.setSuccessions( Boolean.parseBoolean( ctm.getStringAt( selectedRow, col++ )));
      temp.setCropDescription( ctm.getStringAt( selectedRow, col++ ));
      temp.setKeywords( ctm.getStringAt( selectedRow, col++ ));
      col++; // fudge
      temp.setOtherRequirements( ctm.getStringAt( selectedRow, col++ ));
      temp.setNotes( ctm.getStringAt( selectedRow, col++ ));
         
      temp.setMaturityDays( ctm.getIntAt( selectedRow, col++ ));
      
      return temp;

   }
   
   public CPSCrop getCropInfo( String cropName ) {
   
      CPSCrop temp = new CPSCrop();
      
      if ( ! cropName.equals("") )
         for ( int i = 0; i < ctm.getRowCount(); i ++ ) {
            // match crop_name == cropName and var_name == ""
            if ( ctm.getStringAt( i, 0 ).equalsIgnoreCase( cropName ) &&
                 ctm.getStringAt( i, 1 ).equalsIgnoreCase( "" ) ) {
               temp = getCropInfoForRow( i );
               break;
            }
         }
      
      return temp;
   }
   
   public TableModel getCropAndVarietyList() { return null; }
   public ArrayList<String> getListOfCropPlans() { return null; }
   public void createNewCropPlan(String plan_name) {}
   public void retrieveCropPlan(String plan_name) {}
   public void filterCropPlan(String plan_name, String filter) {}
   public TableModel getCropList() { return null; }
   public TableModel getAbbreviatedCropList() { return null; }
   public TableModel getVarietyList() { return null; }
   public TableModel getAbbreviatedVarietyList() { return null; }
   public TableModel getAbbreviatedCropAndVarietyList() { return null; }
   public void shutdown() {}
   
   public void updateCrop(CPSCrop crop) {}
   public void createCrop(CPSCrop crop) {}

   public TableModel getCropList(String sortCol) { return null; }
   public TableModel getAbbreviatedCropList(String sortCol) { return null; }
   public TableModel getVarietyList(String sortCol) { return null; }
   public TableModel getAbbreviatedVarietyList(String sortCol) { return null; }
   public TableModel getCropAndVarietyList(String sortCol) { return null; }
   public TableModel getAbbreviatedCropAndVarietyList(String sortCol) { return null; }
   
}
