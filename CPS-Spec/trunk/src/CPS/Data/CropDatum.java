/**
 * CPSDatum - This is an abstraction data structure that stores, processes and deals
 * with data and metadata.  Used primarity in the CPSCrop and CPSPlanting classes.
 */

package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CropDatum<T> extends CPSDatum {

   private boolean shouldInherit, isInherited;
   
   public CropDatum() {
      invalidate();
      setShouldBeInherited(true);
      setIsInherited( false );
   }
   
   public CropDatum( String n, int p, String c, T def ) {
      this( n, p, c, def, true);
   }
   
   public CropDatum( String n, int p, String c, T def, boolean chain ) {
      super( n, p, c, def );
      setShouldBeInherited( chain );
      invalidate();
   }

   // TODO this is a dummy; should be removed; only for compatibility
   public CropDatum( String n ) {}
  
   private void setShouldBeInherited( boolean c ) { shouldInherit = c; }
   public boolean shouldBeInherited() { return shouldInherit; }
   
   public void setIsInherited( boolean i ) { isInherited = i; }
   public boolean isInherited() { return isInherited; }
   
   
}
