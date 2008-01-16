
package CPS.Data;

public class CPSDatum<T> {

   protected String descriptor;
   protected T datum;
   
   private int property;
   private String columnName;
   private boolean stateValid, stateInherited, stateCalculated;
   private T defaultValue;

   public CPSDatum() {
      setCalculated(false);
      setInherited(false);
      invalidate();
   }
   
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
      setInherited(false);
      setCalculated(false);
      invalidate();
   }
   
   public String getDescriptor() { return descriptor; }
   public void setDescriptor( String name ) { this.descriptor = name; }
   
   protected void setColumnName( String c ) { columnName = c; }
   public String getColumnName() { return columnName; }
   
   protected void setProperty( int p ) { property = p; }
   public int getPropertyNum() { return property; }
   
   public void validate() { stateValid = true; }
   public void invalidate() { stateValid = false; }
   public boolean isValid() { return stateValid; }
   public boolean isAvailable() { return stateValid || stateInherited || stateCalculated; }
   public boolean isConcrete() { return stateValid && ! stateInherited && ! stateCalculated; }
   
   public void setInherited( boolean b ) { stateInherited = b; }
   public boolean isInherited() { return stateInherited; }

   public void setCalculated( boolean b ) { stateCalculated = b; }
   public boolean isCalculated() { return stateCalculated; }
   
   protected void setDefaultValue( T v ) { defaultValue = v; }
   public T getDefaultValue() { return defaultValue; }
   
   /**
    * Get and Set Datum methods 
    */
   public T getDatum() { return datum; }
   public void setDatum( T datum ) { setDatum( datum, false ); }
   /** setDatum - records changes to this datum
    * 
    * @param datum - value to record
    * @param overrideValidation - if true, the value is set and the datum is
    * validated regardless of whether the datum is null; if false, the datum is 
    * recorded and validated only if the new datum is not null.  If the new
    * datum is null, this datum is invalidated.
    */
   public void setDatum( T datum, boolean overrideValidation ) {
      if ( overrideValidation || datum != null ) {
         this.datum = datum;
         validate();
         setInherited( false );
         setCalculated( false );
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
   
   public CPSDatumState getState() {
      return new CPSDatumState( this.isInherited(), this.isCalculated() );
   }
   
   public class CPSDatumState {
    
      boolean inherited, calculated;
      
      public CPSDatumState( boolean inherited, boolean calculated ) {
         this.inherited = inherited;
         this.calculated = calculated;
      }
      
      public boolean isInherited() { return inherited; }
      public boolean isCalculated() { return calculated; }
      
   }
   
}
