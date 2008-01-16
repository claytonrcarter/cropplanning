package CPS.Module;

import java.awt.Dimension;
import javax.swing.JPanel;

public abstract class CPSCoreModule extends CPSModule {
   
   public CPSCoreModule( CPSUI uim ) {}
   
   public abstract JPanel display();
   public abstract Dimension getSize();
   
}
