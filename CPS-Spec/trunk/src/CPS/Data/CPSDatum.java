
package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;

public abstract class CPSDatum<T> {

   protected String descriptor;
   protected T datum;
   
   abstract JLabel toLabel();
   abstract JComponent toEditableField();
   abstract JComponent toStaticField();

//    abstract String toTableHeader();
//    abstract void getTableFormat();
//    abstract void getTableData();

   
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
   
   public boolean getDatumAsBoolean() {
      if ( datum == null || ! ( datum instanceof Boolean ))
         return false;
      else 
         return ((Boolean) datum).booleanValue();
   }
   
   public int getDatumAsInt() {
      if      ( datum == null || ! ( datum instanceof Integer ) && ! ( datum instanceof String ))
         return 0;
      else if ( datum instanceof String )
         return Integer.parseInt( (String) datum );
      else
         return ((Integer) datum).intValue();
   }
   
}
