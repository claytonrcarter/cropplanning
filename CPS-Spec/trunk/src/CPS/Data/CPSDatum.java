
package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CPSDatum<T> {

   protected String descriptor;
   protected T datum;
   
   private int property;
   private String columnName;
   private boolean valid;
   private T defaultValue;

   public CPSDatum() { invalidate(); }
   
   private CPSDatum( String n, T d ) {
      setDescriptor(n);
      setDatum(d);
   }
   
   public CPSDatum( String n, int p, String c, T def ) {
      setDescriptor(n);
      setDefaultValue(def);
      setDatum( getDefaultValue() );
      setProperty(p);
      setColumnName(c);
      invalidate();
   }
   
   public String getDescriptor() { return descriptor; }
   public void setDescriptor( String name ) { this.descriptor = name; }
   
   protected void setColumnName( String c ) { columnName = c; }
   public String getColumnName() { return columnName; }
   
   protected void setProperty( int p ) { property = p; }
   public int getPropertyNum() { return property; }
   
   public void validate() { valid = true; }
   public void invalidate() { valid = false; }
   public boolean isValid() { return valid; }

   protected void setDefaultValue( T v ) { defaultValue = v; }
   public T getDefaultValue() { return defaultValue; }
   
   /**
    * Get and Set Datum methods 
    */
   public T getDatum() { return datum; }
   public void setDatum( T datum ) {
      if ( datum != null ) {
         this.datum = datum;
         validate();
      }
      else
         invalidate();
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
   
   /**
    * Datum to Swing component conversion methods.
    */
   JLabel toLabel() { return new JLabel( getDescriptor() ); }
   JComponent toEditableField() { return new JTextField( getDatum().toString() ); }
   JComponent toStaticField() { return new JLabel( getDatum().toString() ); }

}
