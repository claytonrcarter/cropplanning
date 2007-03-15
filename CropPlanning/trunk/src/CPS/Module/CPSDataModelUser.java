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
public abstract class CPSDataModelUser {
   
   protected boolean dataAvail = false;
   protected CPSDataModel dataModel = null;
   
   public void setDataSource( CPSDataModel dm ) {
      if ( dm != null ) {
         dataModel = dm;
         setDataAvailable();
      }
   }
   public CPSDataModel getDataSource() { return dataModel; }
   
   public boolean isDataAvailable() { return dataAvail; }
   public void setDataAvailable( boolean b ) { dataAvail = b; }
   public void setDataAvailable() { setDataAvailable(true); }
   
   
   
}
