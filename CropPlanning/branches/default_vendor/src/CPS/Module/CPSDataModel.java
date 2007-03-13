/*
 * CPSDataModel.java
 *
 * Created on January 16, 2007, 1:03 PM
 *
 * An abstract class meant to define the interface of the CPS Data Model.
 * This will be extended by individual data models, in particular the
 * Core Data Model which is likely to be HSQLDB based.
 * 
 */

package CPS.Module;

/**
 *
 * @author Clayton
 */
public abstract class CPSDataModel extends CPSModule {
   
   public abstract String[] getListOfCropPlans();
   
   public abstract void createNewCropPlan( String plan_name );
   
   public abstract void retrieveCropPlan( String plan_name );
   public abstract void filterCropPlan( String plan_name, String filter );
   
}
