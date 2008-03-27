/* CPSDatum.java
 * Copyright (C) 2007, 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
         return ((CPSBoolean) datum).booleanValue();
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
      return new CPSDatumState( this.isInherited(), this.isCalculated(), this.isValid() );
   }
   
   public class CPSDatumState {
    
      boolean inherited, calculated, valid;
      
      public CPSDatumState( boolean inherited, boolean calculated, boolean valid ) {
         this.inherited = inherited;
         this.calculated = calculated;
         this.valid = valid;
      }
      
      public boolean isInherited() { return inherited; }
      public boolean isCalculated() { return calculated; }
      public boolean isValid() { return valid; }
      
   }
   
}
