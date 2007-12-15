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

import java.util.Iterator;

public abstract class CPSRecord {
   
   protected abstract int lastValidProperty();
   
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
           * if this IS NOT valid AND that IS valid OR
           * if this IS     valid AND that IS valid AND
           * if this IS NOT equal to that,
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
          System.out.println("Differences EXIST");
          diffs.setID( this.getID() );
       }
       
       return diffs;
    }
   
   public boolean equals( Object o ) {
      return ( o instanceof CPSRecord && this.diff( (CPSRecord) o ).getID() == -1 );
   }
   
   /** Method to abstract datum setting.  Captures "null" values for crop datums.
    */
   public <T> void set( CPSDatum<T> d, T v ) { set( d, v, false ); }
   protected <T> void set( CPSDatum<T> d, T v, boolean override ) {
    
      if ( v == null ||
//            ! override && ( v instanceof String && ( v.equals("") || ((String) v).equalsIgnoreCase("null") )) ||
            ! override && ( v instanceof String && ((String) v).equalsIgnoreCase("null") ) ||
            ! override && ( v instanceof Integer && ((Integer) v).intValue() == -1 ) )
         d.setDatum(null);
      else
         d.setDatum(v);
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
