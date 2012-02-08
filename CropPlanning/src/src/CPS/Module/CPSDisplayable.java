/*
 * 
 */

package CPS.Module;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Clayton
 */
public interface CPSDisplayable {
   
   public abstract JPanel display();
   public abstract Dimension getSize();
   
   /**
    * Add a CPSUIChangeListener to the list of of modules that wish to be notified when this
    * module has updated it's UI.
    * @param ucl The CPSUIChangeListener to be added (and subsequently notified)
    */
   public void addUIChangeListener( CPSUIChangeListener ucl );
   /**
    * this should be protected; FOR INTERNAL USE ONLY
    * This method is called whenever there is a change or update in this modules UI.  It notifies
    * all UI change listeners that the UI has been updated and that they should lay out their UI
    * again.
    */
   public void signalUIChanged();
   
}
