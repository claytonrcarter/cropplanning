
package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CropDatum<T> extends CPSDatum {

   
   public CropDatum( String n, T d ) {
      setDescriptor(n);
      setDatum(d);
   }
   
   public CropDatum( String n ) {
      setDescriptor(n);
      setDatum(null);
   }
   
   JLabel toLabel() {
      return new JLabel( getDescriptor() );
   }

   JComponent toEditableField() {
      return new JTextField( getDatum().toString() );
   }

   JComponent toStaticField() {
      return new JLabel( getDatum().toString() );
   }

   
}
