/*
 * 
 */

package CPS.Module;

/**
 *
 * @author Clayton
 */
public interface CPSDataUser {
 
   /**
    * Register the data source for this module and signal that the data is ready to be used.
    * @param dm a CPSDataModel to use as the data source for this module
    */
   public void setDataSource( CPSDataModel dm );
   /** this should be protected; FOR INTERNAL USE ONLY */
   public CPSDataModel getDataSource();
   
   /** this should be protected; FOR INTERNAL USE ONLY */
   public boolean isDataAvailable();
   /** this should be protected; FOR INTERNAL USE ONLY */
   public void setDataAvailable( boolean b );
   /** this should be protected; FOR INTERNAL USE ONLY */
   public void setDataAvailable();
   
   /**
    * This method is called by the DataSource whenever the data has been updated
    * and the DataListeners are updated.  Every CPSDataModelUser should use this
    * method to update and/or refresh the data they are currently using.
    */
   public abstract void dataUpdated();
  
}
