/*
 * CPSDataModel.java
 *
 * Created on January 16, 2007, 1:03 PM
 *
 * An abstract class meant to define the interface of the CPS Data Model.
 * This will be extended by individual data models, in particular the
 * Core Data Model which is likely to be HSQLDB based.
 *
 * At design time, it was decided that it is reasonable to assume that
 * individual data models will cache their results before returning them,
 * instead, say, of continually requerying a db or network connection.
 * This decision led to the methods which ask for information about a
 * particular row of the data set.  This is assumed to be a row from the
 * cached data, not necessarily the original data set.
 * 
 */

package CPS.Module;

import java.util.ArrayList;
import javax.swing.table.TableModel;
import CPS.Data.*;

/**
 *
 * @author Clayton
 */
public abstract class CPSDataModel extends CPSModule {

   
   /* Crop Plan methods */
   /* retrieval */
   /**
    * Retrieves a list of all crop plans currently available.
    * @return an unsorted ArrayList of Strings which represent the names of all available crop plans
    */
   public abstract ArrayList<String> getListOfCropPlans();
   /**
    * Retrieve a crop plan in tabular form.
    * @param plan_name name of the crop plan to retrieve
    * @return a TableModel representing the retrived plan
    */
   public abstract TableModel getCropPlan( String plan_name );
   /**
    * Same as getCropPlan( String plan_name ) except that this plan is sorted by a particular column.
    * @param plan_name name of the crop plan to retrieve
    * @param sortCol the crop plan column name to sort
    * @return a TableModel representing the retrived plan; the underlying data is sorted by column sortCol
    * @see getCropPlan( String plan_name )
    */
   public abstract TableModel getCropPlan( String plan_name, String sortCol );
   /**
    * Same as getCropPlan( String plan_name, String sortCol ) except that
    * the tabular data retrieved is filtered to match a particular character string.  The fields 
    * that are used for the filtering are determined by the particular implementation. 
    * 
    * @param plan_name name of the crop plan to retrieve
    * @param sortCol the column name to sort
    * @param filterString a string to use when filtering the data in the plan
    * @return a TableModel representing the retrived plan; the underlying data is sorted by column sortCol
    * @see getCropPlan( String plan_name, String sortCol )
    */
   public abstract TableModel getCropPlan( String plan_name, String sortCol, CPSComplexPlantingFilter filter );
   public abstract TableModel getCropPlan( String plan_name, String columns, String sortCol, CPSComplexPlantingFilter filter );
   public abstract CPSPlanting getSumsForCropPlan( String plan_name, CPSComplexPlantingFilter filter );
   /* create and update */ 
   public abstract void createCropPlan(String plan_name);
   public abstract void updateCropPlan( String plan_name );
   
   /* Planting methods */
   /* retrieval */
   public abstract ArrayList<String> getPlantingDefaultColumns();
   public abstract ArrayList<String> getPlantingDisplayableColumns();
   public abstract ArrayList<String[]> getPlantingPrettyNames();
   public abstract ArrayList<String[]> getPlantingShortNames();
   public abstract CPSPlanting getPlanting( String planName, int PlantingID );
   public abstract CPSPlanting getCommonInfoForPlantings( String planName, ArrayList<Integer> plantingIDs );
   /* create and update */
   public abstract CPSPlanting createPlanting( String planName, CPSPlanting planting );
   public abstract void updatePlanting( String planName, CPSPlanting planting );
   public abstract void updatePlantings( String planName, CPSPlanting changes, ArrayList<Integer> plantingIDs );
   public abstract void deletePlanting( String planting, int plantingID );
   
   /* Crop and Variety methods */
   /* retrieval */
   public abstract ArrayList<String> getCropDefaultColumns();
   public abstract ArrayList<String> getCropDisplayableColumns();
   public abstract ArrayList<String[]> getCropPrettyNames();
   public abstract ArrayList<String> getCropNames();
   public abstract ArrayList<String> getVarietyNames( String crop_name );
   public abstract ArrayList<String> getFamilyNames();
   public CPSCrop getCropInfo( String cropName ) { return getVarietyInfo( cropName, null ); }
   public abstract CPSCrop getVarietyInfo( String cropName, String varName );
   public abstract CPSCrop getCropInfo( int CropID );
   public abstract CPSCrop getCommonInfoForCrops( ArrayList<Integer> cropIDs );
   /* create and update */
   public abstract CPSCrop createCrop(CPSCrop crop);
   public abstract void updateCrop( CPSCrop crop );
   public abstract void updateCrops( CPSCrop changes, ArrayList<Integer> cropIDs );
   public abstract void deleteCrop( int cropID );
   
   public abstract TableModel getCropList();
   public abstract TableModel getAbbreviatedCropList();
   public abstract TableModel getVarietyList();
   public abstract TableModel getAbbreviatedVarietyList();
   public abstract TableModel getCropAndVarietyList();
   public abstract TableModel getAbbreviatedCropAndVarietyList();

   public abstract TableModel getCropList( String sortCol );
   public abstract TableModel getAbbreviatedCropList( String sortCol );
   public abstract TableModel getVarietyList( String sortCol );
   public abstract TableModel getAbbreviatedVarietyList( String sortCol );
   public abstract TableModel getCropAndVarietyList( String sortCol );
   public abstract TableModel getAbbreviatedCropAndVarietyList( String sortCol );
   
   public abstract TableModel getCropList( String sortCol, CPSComplexFilter filter );
   public abstract TableModel getAbbreviatedCropList( String sortCol, CPSComplexFilter filter );
   public abstract TableModel getVarietyList( String sortCol, CPSComplexFilter filter );
   public abstract TableModel getAbbreviatedVarietyList( String sortCol, CPSComplexFilter filter );
   public abstract TableModel getCropAndVarietyList( String sortCol, CPSComplexFilter filter );
   public abstract TableModel getAbbreviatedCropAndVarietyList( String sortCol, CPSComplexFilter filter );
   
   public abstract TableModel getCropList( String columns, String sortCol, CPSComplexFilter filter );
   public abstract TableModel getAbbreviatedCropList( String columns, String sortCol, CPSComplexFilter filter );
   public abstract TableModel getVarietyList( String columns, String sortCol, CPSComplexFilter filter );
   public abstract TableModel getAbbreviatedVarietyList( String columns, String sortCol, CPSComplexFilter filter );
   public abstract TableModel getCropAndVarietyList( String columns, String sortCol, CPSComplexFilter filter );
   public abstract TableModel getAbbreviatedCropAndVarietyList( String columns, String sortCol, CPSComplexFilter filter );
   
   public abstract void shutdown();
   
   /** An ArrayList of CPSDataModelUsers which will be notified when the database has changed or been updated. */
   protected ArrayList<CPSDataUser> dataListeners = new ArrayList();
   /**
    * Add a CPSDataModelUser to the list of of modules that wish to be notified when the data has been updated
    * @param dmu The CPSDataModelUser to be added (and subsequently notified)
    */
   public void addDataListener( CPSDataUser dmu ) { dataListeners.add( dmu ); }
   /**
    * This method is called whenever there is a change or update in the database.  It notifies
    * all data listeners that the data has been updated.
    */
   protected void updateDataListeners() {
      for( CPSDataUser dmu : dataListeners )
         dmu.dataUpdated();
   }
   
   public void importCropsAndVarieties( ArrayList<CPSCrop> crops ) {
      ArrayList<CPSCrop> withSimilar = new ArrayList<CPSCrop>();
      CPSCrop temp;
      for ( int i = 0; i < crops.size(); i ++ ) {
         // if this crop already exists, remove it from the list and keep going
         if ( ! getCropInfo( crops.get(i).getCropName() ).getCropName().equals("") ) {
            System.err.println( "Crop already exists: " + crops.get(i).getCropName() );
            crops.remove( i-- );
         }
         else
            /* if this crop doesn't have a similar crop entry,
             * or it does and that similar crop exists
             * then we add it and remove it from the list
             * else we just skip it
             */
            if ( crops.get(i).getSimilarCrop().equals("") ||
                 ! getCropInfo( crops.get(i).getSimilarCrop() ).getCropName().equals("") ) {
               // create the current crop, but decrement i because the ArrayList
               // will shift all indices when we call remove
               System.out.println("Importing data for crop: " + crops.get(i).getCropName() +
                                  " similar to: " + crops.get(i).getSimilarCrop() );
               createCrop( crops.remove(i--) );
            }
            // else leave the crop in the list to be dealt with later
      }
      
      System.out.println("There are " + crops.size() + " remaining crops.");
      
      // now make another pass for crops w/ similar crops that weren't preexisting
      if ( crops.size() > 0 )
         importCropsAndVarieties( crops );
   }
   public abstract ArrayList<CPSCrop> exportCropsAndVarieties();
   
}
