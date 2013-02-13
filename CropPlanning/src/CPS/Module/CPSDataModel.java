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

import CPS.Data.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Clayton
 */
public abstract class CPSDataModel extends CPSModule {

   public abstract int propNumFromPropName( int recordType, String propertyName );
   public abstract String propNameFromPropNum( int recordType, int propertyNum );
   
   /* Crop Plan methods */
   /* retrieval */
   /**
    * Retrieves a list of all crop plans currently available.
    * @return an unsorted List of Strings which represent the names of all available crop plans
    */
   public abstract List<String> getListOfCropPlans();
   /**
    * Retrieve a crop plan in tabular form.
    * @param plan_name name of the crop plan to retrieve
    * @return a TableModel representing the retrived plan
    */
//   public abstract TableModel getCropPlan( String plan_name );
   public abstract List<CPSPlanting> getCropPlan( String plan_name );

   /**
    * @deprecated
    */
   public abstract CPSPlanting getSumsForCropPlan( String plan_name, CPSComplexPlantingFilter filter );
   /* create and update */ 
   public void createCropPlan( String plan_name ) { 
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime( new Date() );
      createCropPlan( plan_name, gc.get( Calendar.YEAR ), "" ); 
   }
   public abstract void createCropPlan( String planName, int year, String desc );
   public abstract void updateCropPlan( String planName, int year, String desc );
   public abstract void deleteCropPlan( String planName );
   public abstract int getCropPlanYear( String planName );
   public abstract String getCropPlanDescription( String planName );
   public abstract void finalizeCropPlan( String planName );
   
   /* Planting methods */
   /* retrieval */
   public abstract List<String> getFieldNameList( String planName );
   public abstract List<String> getFlatSizeList( String planName );
   public abstract List<String> getPlantingDefaultPropertyNames();
   public abstract List<Integer> getPlantingDefaultProperties();
   public abstract List<String> getPlantingDisplayablePropertyNames();
   public abstract List<Integer> getPlantingDisplayableProperties();
   public abstract List<String[]> getPlantingPrettyNames();
   public abstract List<String[]> getPlantingShortNames();
   public abstract CPSPlanting getPlanting( String planName, int PlantingID );
   public abstract CPSPlanting getCommonInfoForPlantings( String planName, List<Integer> plantingIDs );
   /* create and update */
   public abstract CPSPlanting createPlanting( String planName, CPSPlanting planting );
   public abstract void updatePlanting( String planName, CPSPlanting planting );
   public abstract void updatePlantings( String planName, CPSPlanting changes, List<Integer> plantingIDs );
   public abstract void deletePlanting( String planting, int plantingID );
   
   /* Crop and Variety methods */
   /* retrieval */
   public abstract List<String> getCropDefaultPropertyNames();
   public abstract List<String> getCropDisplayablePropertyNames();
   public abstract List<Integer> getCropDefaultProperties();
   public abstract List<Integer> getCropDisplayableProperties();
   public abstract List<String[]> getCropPrettyNames();
   public abstract List<String> getCropNameList();
   public List<String> getVarietyNameList() { return getVarietyNameList( null, null ); }
   public List<String> getVarietyNameList( String crop_name ) { return getVarietyNameList( crop_name, null ); }
   public abstract List<String> getVarietyNameList( String crop_name, String cropPlan );
   public abstract List<String> getFamilyNameList();
   public List<String> getFlatSizeList() { return getFlatSizeList( null ); }
   public CPSCrop getCropInfo( String cropName ) { return getVarietyInfo( cropName, null ); }
   public abstract CPSCrop getVarietyInfo( String cropName, String varName );
   public abstract CPSCrop getCropInfo( int CropID );
   public abstract CPSCrop getCommonInfoForCrops( List<Integer> cropIDs );
   /* create and update */
   public abstract CPSCrop createCrop(CPSCrop crop);
   public abstract void updateCrop( CPSCrop crop );
   public abstract void updateCrops( CPSCrop changes, List<Integer> cropIDs );
   public abstract void deleteCrop( int cropID );
   
   public abstract List<CPSCrop> getCropList();
   public abstract List<CPSCrop> getVarietyList();
   public abstract List<CPSCrop> getCropAndVarietyList();   
   
   /** An List of CPSDataModelUsers which will be notified when the database has changed or been updated. */
   protected List<CPSDataUser> dataListeners = new ArrayList<CPSDataUser>();
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
      
   public void importCropPlan( String planName, List<CPSPlanting> importedPlan ) {
      int ip = 0;
      
      if ( getListOfCropPlans().contains(planName) ) {
         System.err.println("Cannot import crop plan: a plan already exists with that name!" );
         return;
      }
      
      createCropPlan(planName);
      
      for ( CPSPlanting p : importedPlan ) {
         createPlanting( planName, p );
         ip++;
      }
      
      System.out.println( "Imported " + ip + " plantings into crop plan " + planName );
   }
   
   public void importCropsAndVarieties( List<CPSCrop> importedCrops ) {
      int ic = 0, iv = 0;
      int skipped = 0;
      
      for ( CPSCrop c : importedCrops ) {

         // if the crop or var already exists, skip it
         if ( c.isCrop()    && getCropInfo(    c.getCropName() ).getID()                     != -1 ||
              c.isVariety() && getVarietyInfo( c.getCropName(), c.getVarietyName() ).getID() != -1 ) {
            System.err.println( "Crop already exists: " + c.getCropName() + " " + c.getVarietyName() );
            skipped++;
            continue;
         }
         
         System.out.println( "Importing data for crop: " + c.getCropName() + " " + c.getVarietyName() );
         createCrop( c );
         if ( c.isCrop() )
            ic++;
         else
            iv++;
      
      }
      
      System.out.println( "Imported " + ic + " crops and " + iv + " varieties.  Skipped " + skipped + " duplicates." );
      
   }
   
}
