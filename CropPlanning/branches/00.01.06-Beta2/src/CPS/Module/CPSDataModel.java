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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    * @return an unsorted ArrayList of Strings which represent the names of all available crop plans
    */
   public abstract ArrayList<String> getListOfCropPlans();
   public abstract TableModel getPlanSummary( String plan_name );
   public abstract TableModel getPlanSummary( String plan_name, boolean just_completed );
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
   protected abstract TableModel getCropPlan( String plan_name, String sortCol );
   public abstract TableModel getCropPlan( String plan_name, int sortProperty );
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
   protected abstract TableModel getCropPlan( String plan_name, String sortCol, CPSComplexPlantingFilter filter );
   public abstract TableModel getCropPlan( String plan_name, int sortProp, CPSComplexPlantingFilter filter );
   protected abstract TableModel getCropPlan( String plan_name, String columns, String sortCol, CPSComplexPlantingFilter filter );
   public abstract TableModel getCropPlan( String plan_name, ArrayList<Integer> properties, int sortProp, CPSComplexPlantingFilter filter );
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
   
   /* Planting methods */
   /* retrieval */
   public abstract ArrayList<String> getFieldNameList( String planName );
   public abstract ArrayList<String> getFlatSizeList( String planName );
   public abstract ArrayList<String> getPlantingDefaultPropertyNames();
   public abstract ArrayList<Integer> getPlantingDefaultProperties();
   public abstract ArrayList<String> getPlantingDisplayablePropertyNames();
   public abstract ArrayList<Integer> getPlantingDisplayableProperties();
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
   public abstract ArrayList<String> getCropDefaultPropertyNames();
   public abstract ArrayList<String> getCropDisplayablePropertyNames();
   public abstract ArrayList<Integer> getCropDefaultProperties();
   public abstract ArrayList<Integer> getCropDisplayableProperties();
   public abstract ArrayList<String[]> getCropPrettyNames();
   public abstract ArrayList<String> getCropNameList();
   public ArrayList<String> getVarietyNameList() { return getVarietyNameList( null, null ); }
   public ArrayList<String> getVarietyNameList( String crop_name ) { return getVarietyNameList( crop_name, null ); }
   public abstract ArrayList<String> getVarietyNameList( String crop_name, String cropPlan );
   public abstract ArrayList<String> getFamilyNameList();
   public ArrayList<String> getFlatSizeList() { return getFlatSizeList( null ); }
   public CPSCrop getCropInfo( String cropName ) { return getVarietyInfo( cropName, null ); }
   public abstract CPSCrop getVarietyInfo( String cropName, String varName );
   public abstract CPSCrop getCropInfo( int CropID );
   public abstract CPSCrop getCommonInfoForCrops( ArrayList<Integer> cropIDs );
   /* create and update */
   public abstract CPSCrop createCrop(CPSCrop crop);
   public abstract void updateCrop( CPSCrop crop );
   public abstract void updateCrops( CPSCrop changes, ArrayList<Integer> cropIDs );
   public abstract void deleteCrop( int cropID );
   
   public abstract TableModel getCropTable();
   public abstract TableModel getVarietyTable();
   public abstract TableModel getCropAndVarietyTable();
   
   protected abstract TableModel getCropTable( String sortCol );
   protected abstract TableModel getVarietyTable( String sortCol );
   protected abstract TableModel getCropAndVarietyTable( String sortCol );
   public abstract TableModel getCropTable( int sortProp );
   public abstract TableModel getVarietyTable( int sortProp );
   public abstract TableModel getCropAndVarietyTable( int sortProp );
   
   protected abstract TableModel getCropTable( String sortCol, CPSComplexFilter filter );
   protected abstract TableModel getVarietyTable( String sortCol, CPSComplexFilter filter );
   protected abstract TableModel getCropAndVarietyTable( String sortCol, CPSComplexFilter filter );
   public abstract TableModel getCropTable( int sortProp, CPSComplexFilter filter );
   public abstract TableModel getVarietyTable( int sortProp, CPSComplexFilter filter );
   public abstract TableModel getCropAndVarietyTable( int sortProp, CPSComplexFilter filter );
   
   protected abstract TableModel getCropTable( String columns, String sortCol, CPSComplexFilter filter );
   protected abstract TableModel getVarietyTable( String columns, String sortCol, CPSComplexFilter filter );
   protected abstract TableModel getCropAndVarietyTable( String columns, String sortCol, CPSComplexFilter filter );
   public abstract TableModel getCropTable( ArrayList<Integer> properties, int sortProp, CPSComplexFilter filter );
   public abstract TableModel getVarietyTable( ArrayList<Integer> properties, int sortProp, CPSComplexFilter filter );
   public abstract TableModel getCropAndVarietyTable( ArrayList<Integer> properties, int sortProp, CPSComplexFilter filter );
   
   
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
   
   public abstract ArrayList<CPSCrop> getCropsAndVarietiesAsList();
   public abstract ArrayList<CPSPlanting> getCropPlanAsList( String planName );
   
   public void importCropPlan( String planName, ArrayList<CPSPlanting> importedPlan ) {
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
   
   public void importCropsAndVarieties( ArrayList<CPSCrop> importedCrops ) {
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
