package CPS.Module;

import javax.swing.JPanel;

public abstract class CPSCoreModule extends CPSModule {
   
   public CPSCoreModule( CPSUIModule uim ) {}
   
    public abstract JPanel display ();
    
}
