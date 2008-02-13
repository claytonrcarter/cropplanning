package CPS.Module;

import javax.swing.JPanel;

public abstract class CPSUI extends CPSModule implements CPSUIChangeListener {

    // methods
    public abstract void showUI ();
    public abstract String showFirstRunDialog( String defaultDir );
    public abstract void addModule ( CPSDisplayableDataUserModule mod );
    public abstract void addModule ( String name, JPanel content );
    
    public abstract void addModuleConfiguration( CPSConfigurable config );

}
