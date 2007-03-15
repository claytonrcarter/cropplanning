
package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CropDatum<T> extends CPSDatum {

   private String descriptor;
   private T datum;
   
   public CropDatum( String n, T d ) {
      setDescriptor(n);
      setDatum(d);
   }

   
   
   public String getDescriptor() {
      return descriptor;
   }

   public void setDescriptor( String name ) {
      this.descriptor = name;
   }

   public T getDatum() {
      return datum;
   }

   public void setDatum( T datum ) {
      this.datum = datum;
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
