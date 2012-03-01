package CPS.Module;

import java.util.ArrayList;

public abstract class CPSDisplayableDataUserModule extends CPSDataUserModule
                                                   implements CPSDisplayable {
   
   /** An ArrayList of CPSUIChangeListeners which will be notified when this module has changed or 
    * updated its UI in such a way that the overall program might to be laid out again. 
    */   
   protected ArrayList<CPSUIChangeListener> changeListeners = new ArrayList<CPSUIChangeListener>();
   /**
    * Add a CPSUIChangeListener to the list of of modules that wish to be notified when this
    * module has updated it's UI.
    * @param ucl The CPSUIChangeListener to be added (and subsequently notified)
    */
   public void addUIChangeListener( CPSUIChangeListener ucl ) { changeListeners.add( ucl ); }
   /**
    * This method is called whenever there is a change or update in this modules UI.  It notifies
    * all UI change listeners that the UI has been updated and that they should lay out their UI
    * again.
    */
   public void signalUIChanged() {
      for ( CPSUIChangeListener ucl : changeListeners )
         ucl.uiChanged();
   }
 
   
}
