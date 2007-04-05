
package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CropDatum<T> extends CPSDatum {

   private int property;
   private String columnName;
   
   private CropDatum( String n, T d ) {
      setDescriptor(n);
      setDatum(d);
   }
   
   public CropDatum( String n, int p, String c ) {
      setDescriptor(n);
      setDatum(null);
      setProperty(p);
      setColumnName(c);
   }
   
   public CropDatum( String n ) {
      this( n, -1, "" );
   }
   
   private void setColumnName( String c ) { columnName = c; }
   public String getColumnName() { return columnName; }
   
   private void setProperty( int p ) { property = p; }
   public int getPropertyNum() { return property; }
   
   JLabel toLabel() { return new JLabel( getDescriptor() ); }
   JComponent toEditableField() { return new JTextField( getDatum().toString() ); }
   JComponent toStaticField() { return new JLabel( getDatum().toString() ); }

   
}
