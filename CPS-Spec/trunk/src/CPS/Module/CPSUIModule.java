package CPS.Module;

import javax.swing.JPanel;

public abstract class CPSUIModule extends CPSModule {

    // methods
    public abstract void showUI ();
    public abstract void addModule ( String name, JPanel content );
    public abstract void revalidate();

}
