/* CPSRecord.java - Created: December 8, 2007
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

import CPS.Data.CPSDatum.CPSDatumState;
import CPS.Module.CPSDataModelConstants;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * This is an abstract class to define the format used by all "record" data structures
 * such as CPSCrop (holding data about crop entries) and CPSPlanting (holding
 * data about individual crop plan plantings).
 */
public abstract class CPSRecord {
   
   public final int PROP_ID = CPSDataModelConstants.PROP_CROP_ID;
   public final int PROP_COMMON_ID = CPSDataModelConstants.PROP_COMMON_ID;
   
   private boolean representsMultiIDs;
   protected CPSDatum<Integer> recordID;
   protected CPSDatum<ArrayList<Integer>> commonIDs;
   
   protected abstract int lastValidProperty();
   protected ArrayList<Integer> changedProps = new ArrayList<Integer>();
   
   protected abstract CPSDatum getDatum( int prop );
   public abstract String toString(); 
   
   public int getID() { 
       if ( doesRepresentMultipleRecords() )
          return recordID.getDefaultValue();
       else
         return recordID.getDatumAsInt();
   }
   public void setID( int i ) {
       if ( ! doesRepresentMultipleRecords() )
          set( recordID, new Integer( i )); 
    } 
   
   /**
    * Retrieves the lists of IDs that this record represents.  If this record only represents a single
    * record, then this will return an empty list, or whatever is the default value of the commonIDs field.
    * @return An ArrayList of Integers, each of which is a record ID represented by this object.  If
    *         this object only represents a single ID, this list will be empty and method getID() 
    *         should be used.
    * @see getID()
    */
   public ArrayList<Integer> getCommonIDs() {
      if ( doesRepresentMultipleRecords() )
         return commonIDs.getDatum();
      else
         return commonIDs.getDefaultValue();
   }
   /**
    * Record the list of ids which this record represents and sets this record into multiple ID mode.
    * @param ids A ArrayList of Integers which this record represents.
    */
   public void setCommonIDs( ArrayList<Integer> ids ) {
      setRepresentsMultipleRecords();
      set( commonIDs, ids );
   }
    
   /**
    * Sets this object to represent a single record.
    */
   public void setRepresentsSingleRecord() {
      representsMultiIDs = false;
   }
   /**
    * Sets this object to represents multiple records.
    */
   public void setRepresentsMultipleRecords() {
      representsMultiIDs = true;
   }
   /**
    * @return true if this object represents multiple records, false if it represents only one.
    */
   public boolean doesRepresentMultipleRecords() {
      return representsMultiIDs;
   }
   
   public int getInt( int prop ) {
      Integer i = get( prop );
      return i.intValue();
   }
   public String formatInt( int i ) {
      if ( i == -1 )
         return "";
      else 
         return "" + i;
   }
   public boolean getBoolean( int prop ) {
      Boolean b = get( prop );
      return b.booleanValue();
   }
   public float getFloat( int prop ) {
      Float f = get( prop );
      return f.floatValue();
   }
   public String formatFloat( float f ) {
      if ( f < 0 )
         return "";
      else
         return "" + f;
   }
//   public String getAsString( int prop ) {
//      CPSDatum d = getDatum( prop );
//      if ( isNull( d ))
//         return "";
//      else
//         return get( prop ).toString();
//   }
   
   public <T> T get( int prop ) {
      CPSDatum d = getDatum( prop );
      return get( prop, (T) d.getDefaultValue() );
   }
   /** Property retrieval abstraction method.
     *
     * @param prop Property to retrieve
     * @param def Default value, returned if no others found
     * @param chain If property of this object is not valid, should we check "up the chain"?
     */
   public <T> T get( int prop, T def ) {
      
      CPSDatum d = getDatum( prop );
       
      if ( isNull( d ))
         return (T) def;
      else if ( d.isValid() )
         return (T) d.getDatum();
      else {
         if ( def == null )
            System.err.println( "Error, datum undefined: " + d.getDescriptor() );
         return (T) def;
      }
       
   }
   
   public CPSDatumState getStateOf( int prop ) {
      return getDatum( prop ).getState();
   }
   
   private boolean isNull( CPSDatum d ) {
      boolean b = false;
      b |= d == null;
      b |= isObjectNull(d.getDatum());
      return b;
   }
   
   private boolean isObjectNull( Object o ) {
      boolean b = false;
      b |= o == null;
      b |= o instanceof String && ((String) o).equalsIgnoreCase( "null" );
      b |= o instanceof Integer && ((Integer) o).intValue() == -1;
      b |= o instanceof Float && ((Float) o).floatValue() == -1.0;
      b |= ( o instanceof java.util.Date || o instanceof java.sql.Date ) && 
           ((java.util.Date) o).getTime() == 0;
      return b;
   }
   
   public abstract CPSRecord diff( CPSRecord comparedTo );
   public CPSRecord diff( CPSRecord thatRecord, CPSRecord diffs ) {
       
       System.out.println( "Calculating difference between:\n" +
                           this.toString() + "\n" +
                           thatRecord.toString() );
       
       boolean diffsExists = false;
       
       Iterator<CPSDatum> thisIt = this.iterator();
       Iterator<CPSDatum> thatIt = thatRecord.iterator();
       Iterator<CPSDatum> deltIt = diffs.iterator();
       
       CPSDatum thi, that, delta;
       while ( thisIt.hasNext() && thatIt.hasNext() && deltIt.hasNext() ) {
          thi = thisIt.next();
          that = thatIt.next();
          delta = deltIt.next();
          
          /*
           * if this is CALCULATED and that IS NOT VALID, then skip
           * if this IS NOT valid AND that IS valid OR   (means: new info added)
           * if this IS     valid AND that IS valid AND
           * if this IS NOT equal to that,               (means: info changed)
           * then record the difference
           * if the recorded difference is NOT valid,
           * then record the difference as the default value
           */
          if ( thi.isCalculated() && ! that.isConcrete() ) 
             continue;
          else if ( ( ! thi.isValid() && that.isValid() ) ||
                    ( thi.isValid() && that.isValid() ) &&
                    ! thi.getDatum().equals( that.getDatum() ) ) {
             diffs.set( delta.getPropertyNum(), that.getDatum() );
             if ( ! delta.isValid() )
                diffs.set( delta.getPropertyNum(), that.getDefaultValue(), true );
             diffsExists = true;
          }
       }
       
       // by default, a cropID of -1 means no differences.
       if ( diffsExists ) {
          System.out.println("Differences EXIST: " + diffs.toString() );
          if ( this.getCommonIDs().size() > 0 )
             diffs.setID( 1 );
          else
             diffs.setID( this.getID() );
       }
       
       return diffs;
    }

   
   public abstract ArrayList<Integer> getListOfInheritableProperties();
   public CPSRecord inheritFrom( CPSRecord thatRecord ) {

//       if ( ! this.getClass().getName().equalsIgnoreCase( thatRecord.getClass().getName() ) ) {
//          System.err.println( "ERROR: cannot inherit data from dissimilar record type" );
//          return this;
//       }
       
       
       if ( thatRecord.getID() != -1 ){ 
       
           CPSDatum thisDat, thatDat;
       
           for ( Integer i : getListOfInheritableProperties() ) {
          
               int prop = i.intValue();
               thisDat = this.getDatum( prop );
               thatDat = thatRecord.getDatum( prop );
          
          
//          System.out.print("DEBUG Inheriting " + thisDat.getDescriptor() );
          /* 
           * IF: this IS NOT valid AND that IS valid
           * THEN: this datum will be inherited
           * 
           * IF: this IS valid
           * THEN: ignore this datum, no inheritance needed 
           */
               if ( thisDat.isConcrete() ) {
//             System.out.println(" SKIPPED");
                   continue;
               }
               else if ( ( !thisDat.isValid() || thisDat.isInherited() ) && thatDat.isValid() ) {
//             System.out.println(" DONE");
                   this.inherit( prop, thatDat.getDatum() );
               }
//          else
//             System.out.println(" SKIPPED FOR OTHER REASONS");
           }
       }
       
       return this;
    }
   
   
   
   public boolean equals( Object o ) {
      return ( o instanceof CPSRecord && this.diff( (CPSRecord) o ).getID() == -1 );
   }
   
   public int parseInt ( String s ) {
      if ( isObjectNull(s) || s.equals("") )
         return -1;
      else
         return Integer.parseInt( s );
   }
   public float parseFloat ( String s ) {
      if ( isObjectNull(s) || s.equals("") )
         return -1;
      else
         return Float.parseFloat( s );
   }
   
   public <T> void set( int prop, T value ) { set( prop, value, false ); }
   public <T> void set( int prop, T value, boolean force ) { set( getDatum( prop ), value, force ); }
   public <T> void inherit( int prop, T value ) {
      set( prop, value );
      getDatum( prop ).setInherited( true );
   }
   
   /** Method to abstract datum setting.  Captures "null" values for crop datums.  Values captured:
    * 
    * For Strings: "" and "null" are considered NULL
    * For Integers: -1 is considered NULL
    * For Dates: a millisecond value of 0 is considered null
    * 
    * These can be ignored if the "force" bit is set.
    * 
    * @see HSQLDB method escapeValue
    * @param d datum to be set
    * @param v value to which the datum should be set
    * @param force used to force the setting of datum to value v w/o testing
    *              to see if it is a NULL type value.  Not to be used lightly: it's important that
    *              the calling method understands what they are doing
    */
   public static <T> void set( CPSDatum<T> d, T v ) { set( d, v, false ); }
   /** @see set(CPSDatum<T>,T) */
   protected static <T> void set( CPSDatum<T> d, T v, boolean force  ) {
    
      if ( v == null ||
              // the follow two lines represent a debate as to whether the empty string '""' should
              // be considered a null value.  Current thinking is that blank values, or empty strings
              // can be considered null as long as the "force" param is not set.
           ! force && ( v instanceof String && ( v.equals("") || ((String) v).equalsIgnoreCase("null") )) ||
//           ! force   && ( v instanceof String  && ((String) v).equalsIgnoreCase("null") ) ||
           ! force && ( v instanceof Integer && ((Integer) v).intValue() <= -1 ) ||
           ! force && ( v instanceof Float   && ((Float) v).floatValue() <= -1.0 ) ||
           ! force && ( v instanceof Date    && ((Date) v).getTime() == 0 ))
         d.setDatum( null );
      else
         // if force is true, then we could pass it along to make sure that
         // things are forced through
         d.setDatum( v, force );
   }

   public void addChangedProperty( int prop ) {
       changedProps.add( prop );
   }
   
   public abstract Iterator iterator();
   public abstract class CPSRecordIterator implements Iterator {
       
       protected int currentProp;
       
       public CPSRecordIterator() { currentProp = -1; }
       
       public boolean hasNext() { return currentProp < lastValidProperty(); }

       public CPSDatum next() {

          currentProp++;
          if ( hasNext() &&
               ( ignoreThisProperty() || getDatum( currentProp ) == null ))
             return next();
          else
             return getDatum( currentProp );
          
       }

       public abstract boolean ignoreThisProperty();
       
       public void remove() {}
       
    }
}
