/*
 *  CPSDatum2.java - created: Nov 14, 2009
 *  Copyright (c) 2009  Clayton Carter
 *
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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

    T value;
    T nullValue;
    T blankValue;

    String name, dTion;
    int propertyNum;
    CPSDatumState state;

    public final int DATA_MODE_RAW = 0;
    public final int DATA_MODE_NORMAL = 1;
    int dataMode = DATA_MODE_NORMAL;

    int precision;
    public final int PRECISION_NA          = 0;
    public final int PRECISION_FULL        = 1;
    public final int PRECISION_ONE_DIGIT   = 2;
    public final int PRECISION_TWO_DIGIT   = 3;
    public final int PRECISION_THREE_DIGIT = 4;

    /*
     * 
     *  Constructors
     * 
     */

    public CPSDatum( String name, String desc,
                     T value, T nullValue, T blankValue,
                     int propertyNum,
                     boolean inherited, boolean calculated ) {
        this( name, desc, value, nullValue, blankValue, propertyNum );
        setInherited( inherited );
        setCalculated( calculated );
    }
    
    public CPSDatum( String name, String desc, 
                     T value, T nullValue, T blankValue,
                     int propertyNum ) {
        this( name, desc, nullValue, blankValue, propertyNum );
        setValue(value);
    }

    public CPSDatum( String name, String desc, T blankValue, int propertyNum ) {
        this( name, desc, null, blankValue, propertyNum );
    }

    public CPSDatum( String name, T blankValue, int propertyNum ) {
       this( name, "", null, blankValue, propertyNum );
    }

    public CPSDatum( String name, String desc,
                     T nullValue, T blankValue, int propertyNum ) {
        this( name, nullValue, blankValue );
        setDescription( desc );
        setPropertyNum( propertyNum );
    }

    public CPSDatum( String name, T nullValue, T blankValue ) {

       setName(name);
       setNullValue( nullValue );
       setBlankValue( blankValue );

       state = new CPSDatumState( false, false );

    }

    /*
     *
     *  Important getters and setters
     *
     */

    public void setValue( T value ) {
        if ( value == null || value.equals( getNullValue() ))
            this.value = getNullValue();
        else
            this.value = value;
    }

    public T getValue() {
       return getValue( false );
    }

    public T getValue( boolean rawAccess ) {
        if ( ( isCalculated() || isInherited() || isNull() ) &&
             ( getDataMode() == DATA_MODE_RAW || rawAccess ) ) {
            return getNullValue();
        }
        else if ( isNull() && ! rawAccess ) {
           return blankValue;
        }
        else {
           return value;
        }
    }

    public boolean getValueAsBoolean() {
      if ( isNull() || ! ( value instanceof Boolean ))
         return false;
      else
         return ((Boolean) value).booleanValue();
   }

   public int getValueAsInt() {
      if      ( isNull() || ! ( value instanceof Integer ) && ! ( value instanceof String ))
         return 0;
      else if ( value instanceof String )
         return Integer.parseInt( (String) value );
      else
         return ((Integer) value).intValue();
   }

   public float getValueAsFloat() {
      if      ( isNull() || ! ( value instanceof Float ) && ! ( value instanceof String ))
         return 0f;
      else if ( value instanceof String )
         return Float.parseFloat( (String) value );
      else
         return ((Float) value).floatValue();
   }

    /*
     * 
     *  Lesser getters and setters
     * 
     */

   /**
    * @return the name (or short description) of this datum
    */
    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }

    /**
     * @return a more verbose description of this datum
     */
    public String getDescription() { return dTion; }
    public void setDescription( String desc ) { this.dTion = desc; }

    public int getPropertyNum() { return propertyNum; }
    public void setPropertyNum( int propertyNum ) { this.propertyNum = propertyNum; }

    public T getBlankValue() { return blankValue; }
    public void setBlankValue( T blankValue ) { this.blankValue = blankValue; }

    public T getNullValue() { return nullValue; }
    public void setNullValue( T nullValue ) { this.nullValue = nullValue; }

    public boolean isCalculated() { return getState().isCalculated(); }
    public void setCalculated( boolean calculated ) { getState().setCalculated( calculated ); }

    public boolean isInherited() { return getState().isInherited(); }
    public void setInherited( boolean inherited ) { getState().setInherited( inherited ); }

    /*
     *
     * Various metadata queries
     *
     */

    /**
     * @return true if this datum has a value set and it is not inherited or calculated
     */
    public boolean isConcrete() { return isNotNull() && ! isInherited() && ! isCalculated(); }
    /**
     * Convenience method to set this Datum as niether inherited nor calculated.  Equivalent to calling
     * both setInherited(false) and setCalculated(false)
     */
    public void setConcrete() {
       setInherited( false );
       setCalculated( false );
    }
    /**
     * @return true if this value has a value set and it is <i>either</i> inherited or calculated
     */
    public boolean isAbstract() { return isNotNull() && ( isInherited() || isCalculated() ); }
    // TODO this == operation could get us into trouble; will probaly have to do
    // some introspection about what type we are and such
    public boolean isNull() { return value == null || value.equals( getNullValue() ); }
    public boolean isNotNull() { return ! isNull(); }
    public boolean isBlank() { return value.equals( getBlankValue() ); }


    /**
     * Retrieve the data access mode.  Data can either be accessed in a simple, low-level
     * way which ignores inherited and calculated values or in a more complex, normal way
     * in which that higher level processing is honored.  This only affects get
     * methods, not set methods.
     *
     * @return one of either DATA_MODE_NORMAL or DATA_MODE_RAW; default value is DATA_MODE_NORMAL
     */
    public int getDataMode() { return dataMode; }

    /**
     * Control the data access mode.  Data can either be accessed in a simple, low-level
     * way which ignores inherited and calculated values or in a more complex, normal way
     * in which that higher level processing is honored.  This only affects get
     * methods, not set methods.
     *
     * @param dataMode one of either DATA_MODE_NORMAL or DATA_MODE_RAW; if an invalid value
     * is specified, the default value of DATA_MODE_NORMAL is used
     */
    public void setDataMode( int dataMode ) {
        if ( dataMode == DATA_MODE_NORMAL ||
             dataMode == DATA_MODE_RAW )
            this.dataMode = dataMode;
        else
            this.dataMode = DATA_MODE_NORMAL;
    }


    public CPSDatumState getState() { return state; }

    public void setState( CPSDatumState c ) {
      this.setInherited( c.isInherited() );
      this.setCalculated( c.isCalculated() );
   }


    public class CPSDatumState {

      boolean inherited, calculated;

      public CPSDatumState( boolean inherited, boolean calculated ) {
         this.inherited = inherited;
         this.calculated = calculated;
      }

      public boolean isInherited() { return inherited; }
      public void setInherited( boolean inherited ) { this.inherited = inherited; }

      public boolean isCalculated() { return calculated; }
      public void setCalculated( boolean calculated ) { this.calculated = calculated; }

   }


}
