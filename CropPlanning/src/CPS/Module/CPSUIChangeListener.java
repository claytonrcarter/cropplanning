/* Copyright (C) Jan 18, 2008 Clayton Carter */


package CPS.Module;

/**
 * a CPSUIChangeListener is an object that wishes to be notified of changes which might affect
 * the layout of the UI
 *
 * @author Clayton Carter
 */
public interface CPSUIChangeListener {

   /**
    * This is called whenever a CPSDisplayableModule (to which this object has been added via
    * the CPSDisplayableModule.addUIChangeListener() method) has affect the UI in such a way
    * that it might need to be laidout again.
    * @deprecated 
    */
   public void uiChanged();
   
}
