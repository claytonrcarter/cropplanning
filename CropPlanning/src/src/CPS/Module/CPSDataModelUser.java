/*
 * CPSDataModelUser.java
 *
 * Created on March 14, 2007, 1:16 PM
 *
 *
 */

package CPS.Module;

/**
 *
 * @author Clayton
 */
public abstract class CPSDataModelUser implements CPSDataUser {
   
   protected boolean dataAvail = false;
   private CPSDataModel dataModel = null;
   
   /**
    * Register the data source for this module and signal that the data is ready to be used.
    * @param dm a CPSDataModel to use as the data source for this module
    */
   public void setDataSource( CPSDataModel dm ) {
      if ( dm != null ) {
         dataModel = dm;
         setDataAvailable();
         getDataSource().addDataListener( this );
      }
   }
   public CPSDataModel getDataSource() { return dataModel; }
   
   public boolean isDataAvailable() { return dataAvail; }
   public void setDataAvailable( boolean b ) { dataAvail = b; }
   public void setDataAvailable() { setDataAvailable(true); }
   
   /**
    * This method is called by the DataSource whenever the data has been updated
    * and the DataListeners are updated.  Every CPSDataModelUser should use this
    * method to update and/or refresh the data they are currently using.
    */
   public abstract void dataUpdated();
   
}
