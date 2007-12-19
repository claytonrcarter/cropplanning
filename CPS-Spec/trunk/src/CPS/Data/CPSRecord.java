/*
 * CPSRecord.java
 *
 * Created on December 8, 2007, 6:39 PM by Clayton
 *
 * This is an abstract class to define the format used by all "record" data structures
 * such as CPSCrop (holding data about crop entries) and CPSPlanting (holding
 * data about individual crop plan plantings).
 */

package CPS.Data;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

public abstract class CPSRecord {
   
   protected abstract int lastValidProperty();
   protected ArrayList<Integer> changedProps = new ArrayList<Integer>();
   
   protected abstract CPSDatum getDatum( int prop );
   public abstract String toString(); 
   
   public abstract int getID();
   public abstract void setID( int i );
   
   /** Property retrieval abstraction method.
     *
     * @param prop Property to retrieve
     * @param def Default value, returned if no others found
     * @param chain If property of this object is not valid, should we check "up the chain"?
     */
   public <T> T get( int prop, T def ) {
      
      CPSDatum d = getDatum( prop );
       
      if      ( d == null )
         return (T) def;
      else if ( d.isValid() )
         return (T) d.getDatum();
      else {
         if ( def == null )
            System.err.println( "Error, datum undefined: " + d.getDescriptor() );
         return (T) def;
      }
       
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
           * if this IS NOT valid AND that IS valid OR   (means: new info added)
           * if this IS     valid AND that IS valid AND
           * if this IS NOT equal to that,               (means: info changed)
           * then record the difference
           * if the recorded difference is NOT valid,
           * then record the difference as the default value
           */
          if (( ! thi.isValid() && that.isValid() ) ||
              (   thi.isValid() && that.isValid() ) &&
                ! thi.getDatum().equals( that.getDatum() )) {
             diffs.set( delta, that.getDatum() );
             if ( ! delta.isValid() )
                diffs.set( delta, that.getDefaultValue(), true );
             diffsExists = true;
          }
       }
       
       // by default, a cropID of -1 means no differences.
       if ( diffsExists ) {
          System.out.println("Differences EXIST: " + diffs.toString() );
          diffs.setID( this.getID() );
       }
       
       return diffs;
    }
   
   public boolean equals( Object o ) {
      return ( o instanceof CPSRecord && this.diff( (CPSRecord) o ).getID() == -1 );
   }
   
   /** Method to abstract datum setting.  Captures "null" values for crop datums.  Values captured:
    * 
    * For Strings: "" and "null" are considered NULL
    * For Integers: -1 is considered NULL
    * For Dates: a millisecond value of 0 is considered null
    * 
    * These can be ignored if the "force" bit is set.
    * 
    * @param d datum to be set
    * @param v value to which the datum should be set
    * @param force used to force the setting of datum to value v w/o testing
    *              to see if it is a NULL type value.  Not to be used lightly: it's important that
    *              the calling method understands what they are doing
    */
   public <T> void set( CPSDatum<T> d, T v ) { set( d, v, false ); }
   /** @see set(CPSDatum<T>,T) */
   protected <T> void set( CPSDatum<T> d, T v, boolean force  ) {
    
      if ( v == null ||
              // the follow two lines represent a debate as to whether the empty string '""' should
              // be considered a null value.  Current thinking is that blank values, or empty strings
              // can be considered null as long as the "force" param is not set.
            ! force && ( v instanceof String && ( v.equals("") || ((String) v).equalsIgnoreCase("null") )) ||
//           ! force   && ( v instanceof String  && ((String) v).equalsIgnoreCase("null") ) ||
           ! force   && ( v instanceof Integer && ((Integer) v).intValue() == -1 ) ||
           ! force   && ( v instanceof Date    && ((Date) v).getTime() == 0 ))
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
       
       private int currentProp;
       
       public CPSRecordIterator() { currentProp = -1; }
       
       public boolean hasNext() { return currentProp < lastValidProperty(); }

       public CPSDatum next() {

          currentProp++;
          if ( ignoreThisProperty( currentProp ))
             return next();
          else
             return getDatum( currentProp );
          
       }

       public abstract boolean ignoreThisProperty( int prop );
       
       public void remove() {}
       
    }
}
