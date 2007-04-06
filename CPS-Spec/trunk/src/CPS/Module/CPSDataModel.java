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
   
   public abstract ArrayList<String> getListOfCropPlans();
   
   public abstract void createNewCropPlan( String plan_name );
   
   public abstract void retrieveCropPlan( String plan_name );
   public abstract void filterCropPlan( String plan_name, String filter );

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
   
   public abstract CPSCrop getCropInfo( String cropName );
   public abstract CPSCrop getCropInfoForRow( int row );
   
   public abstract void shutdown();

   public abstract void createCrop( CPSCrop crop );
   public abstract void updateCrop( CPSCrop crop );
   

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
            if ( crops.get(i).getSimilarCrop().getCropName().equals("") ||
                 ! getCropInfo( crops.get(i).getSimilarCrop().getCropName() ).getCropName().equals("") ) {
               // create the current crop, but decrement i because the ArrayList
               // will shift all indices when we call remove
               System.out.println("Importing data for crop: " + crops.get(i).getCropName() +
                                  " similar to: " + crops.get(i).getSimilarCrop().getCropName() );
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
