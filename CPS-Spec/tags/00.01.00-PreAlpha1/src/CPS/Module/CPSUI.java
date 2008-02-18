package CPS.Module;

import javax.swing.JPanel;

public abstract class CPSUI extends CPSModule implements CPSUIChangeListener {

    // methods
    public abstract void showUI ();
    public abstract void addModule ( String name, JPanel content );

}
