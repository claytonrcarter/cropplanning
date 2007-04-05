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

   public abstract CPSCrop getCropInfo( String cropName );
   public abstract CPSCrop getCropInfoForRow( int row );
   
   public abstract void shutdown();

   public abstract void createCrop( CPSCrop crop );
   public abstract void updateCrop( CPSCrop crop );
   

   public void importCropsAndVarieties( ArrayList<CPSCrop> crops ) {
      for ( int i = 0; i < crops.size(); i ++ ) {
         System.out.println("Importing data for crop: " + crops.get(i).getCropName() );
         if ( getCropInfo( crops.get(i).getCropName() ) != null )
            System.err.println("Crop already exists: " + crops.get(i).getCropName() );
         else
            createCrop( crops.get(i) );
      }
   }
      
   public abstract ArrayList<CPSCrop> exportCropsAndVarieties();
   
}
