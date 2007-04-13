
package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CropDatum<T> extends CPSDatum {

   private int property;
   private String columnName;
   private boolean valid;
   private boolean chain;
   private T defaultValue;
   
   public CropDatum() {
      invalidate();
      setShouldBeChained(true);
   }
   
   private CropDatum( String n, T d ) {
      setDescriptor(n);
      setDatum(d);
   }
   
   public CropDatum( String n, int p, String c, T def ) {
      this( n, p, c, def, true);
   }
   
   public CropDatum( String n, int p, String c, T def, boolean chain ) {
      setDescriptor(n);
      setDefaultValue(def);
      setDatum( getDefaultValue() );
      setProperty(p);
      setColumnName(c);
      setShouldBeChained( chain );
      invalidate();
   }
   
   // TODO this is a dummy; should be removed; only for compatibility
   public CropDatum( String n ) {}
   
   public void setDatum( T datum ) {
      if ( datum != null ) {
         super.setDatumTo( datum );
         validate();
      }
      else
         invalidate();
   }
   
   
   private void setColumnName( String c ) { columnName = c; }
   public String getColumnName() { return columnName; }
   
   private void setProperty( int p ) { property = p; }
   public int getPropertyNum() { return property; }
   
   public void validate() { valid = true; }
   public void invalidate() { valid = false; }
   public boolean isValid() { return valid; }
   
   private void setShouldBeChained( boolean c ) { chain = c; }
   public boolean shouldBeChained() { return chain; }
   
   private void setDefaultValue( T v ) { defaultValue = v; }
   public T getDefaultValue() { return defaultValue; }
   
   JLabel toLabel() { return new JLabel( getDescriptor() ); }
   JComponent toEditableField() { return new JTextField( getDatum().toString() ); }
   JComponent toStaticField() { return new JLabel( getDatum().toString() ); }

   
}
