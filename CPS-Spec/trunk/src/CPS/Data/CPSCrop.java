package CPS.Data;


/**
 * Crop - data structure to hold information about a crop
 *        NOT for a planting of a crop, but just for the crop
 */

import java.util.*;

public class CPSCrop {

   // Fields from schema that are as-of-yet unimplemented:
   // "Fudge", "mat_adjust", "misc_adjust", 
   // "seeds_sources","seeds_item_codes","seeds_unit_size"
   
   public final int PROP_ID = 0;
   public final int PROP_CROP_NAME = 1;
   public final int PROP_CROP_DESC = 2;
   public final int PROP_VAR_NAME = 3;
   public final int PROP_FAM_NAME = 4;
   public final int PROP_BOT_NAME = 5;
   public final int PROP_SUCCESSIONS = 6;
   public final int PROP_MATURITY = 7;
   public final int PROP_GROUPS = 8;
   public final int PROP_KEYWORDS = 9;
   public final int PROP_OTHER_REQ = 10;
   public final int PROP_NOTES = 11;
   public final int PROP_SIMILAR = 12;
   
   private CropDatum<Integer> cropID;
   // private CPSCrop similar;
   private CropDatum<CPSCrop> similar;
   
   private CropDatum<String> cropName;
   private CropDatum<String> cropDescription;
   private CropDatum<String> varName;    
   private CropDatum<String> familyName;
   private CropDatum<String> botanicalName;

   private CropDatum<Boolean> successions;

   private CropDatum<Integer> maturityDays;    
   
   private CropDatum<String> groups;
   private CropDatum<String> keywords;
   private CropDatum<String> otherRequirements;
   private CropDatum<String> notes;
   

   /* pseudo-un-implemented */
   private CropDatum<String> flat;    
   private CropDatum<Integer> inRowSpacing;
   private CropDatum<Integer> rowSpacing;
   private CropDatum<Integer> rowsPerBed;
   private CropDatum<Boolean> ds;
   private CropDatum<Boolean> tp;
   

    public CPSCrop () {
       
       cropID = new CropDatum<Integer>( "Unique ID", PROP_ID, "id", new Integer(-1) );
       similar = new CropDatum<CPSCrop>( "Similar to", PROP_SIMILAR, "similar_to", null );
       
       cropName = new CropDatum<String>( "Crop name", PROP_CROP_NAME, "crop_name", "" );
       cropDescription = new CropDatum<String>( "Crop description", PROP_CROP_DESC, "description", "" );
       varName = new CropDatum<String>( "Variety", PROP_VAR_NAME, "var_name", "", false );
       familyName = new CropDatum<String>( "Family name", PROP_FAM_NAME, "fam_name", "" );
       botanicalName = new CropDatum<String>( "Botanical name", PROP_BOT_NAME, "bot_name", "" );
        
       successions = new CropDatum<Boolean>( "Succession?", PROP_SUCCESSIONS, "successions", new Boolean(false) );
        
       maturityDays = new CropDatum<Integer>( "Days to maturity", PROP_MATURITY, "maturity", new Integer(-1) );
        
       groups = new CropDatum<String>( "Groups", PROP_GROUPS, "groups", "" );
       keywords = new CropDatum<String>( "Keywords", PROP_KEYWORDS, "keywords", "" );
       otherRequirements = new CropDatum<String>( "Other Requirements", PROP_OTHER_REQ, "other_req", "" );
       notes = new CropDatum<String>( "Notes", PROP_NOTES, "notes", "" );
        
       
       inRowSpacing = new CropDatum<Integer>("In-Row spacing");
       rowSpacing = new CropDatum<Integer>("Between rows");
       rowsPerBed = new CropDatum<Integer>("Rows Per Bed");
       flat = new CropDatum<String>("Flat size");
       ds = new CropDatum<Boolean>( "Direct Seed?" );
       tp = new CropDatum<Boolean>( "Transplant?" );
        
    }

    
    /** Property retrieval abstraction method.
     *
     * @param prop Property to retrieve
     * @param def Default value, returned if no others found
     * @param chain If property of this object is not valid, should we check "up the chain"?
     */
    public <T> T get( int prop, T def ) {

       CropDatum d = getCropDatum( prop );
       
       if      ( d == null )
          return (T) def;
       else if ( d.isValid() )
          return (T) d.getDatum();
       // else if ( isVariety() && ) ... handle variety based data chaining
       // otherwise query the similar crop (unless we asked for the similar crop)
       else if ( isCrop() && d.shouldBeChained() && similar.isValid() && prop != PROP_SIMILAR ) {
          // get the similar crop and call it's getProperty routine
          CPSCrop sim = (CPSCrop) similar.getDatum();
          return sim.get( prop , def );
       }
       else {
          if ( def == null )
             System.err.println( "Error, datum undefined: " + d.getDescriptor() );
          return (T) def;
       }
       
    }
    
    protected CropDatum getCropDatum( int prop ) {
       
       /* very ugly, but this allows us to use the hierarchy of Crop properties */
       switch ( prop ) {
          case PROP_CROP_NAME:   return cropName;
          case PROP_CROP_DESC:   return cropDescription;
          case PROP_VAR_NAME:    return varName;
          case PROP_FAM_NAME:    return familyName;
          case PROP_BOT_NAME:    return botanicalName;
          case PROP_SUCCESSIONS: return successions;
          case PROP_MATURITY:    return maturityDays;
          case PROP_GROUPS:      return groups;
          case PROP_KEYWORDS:    return keywords;
          case PROP_NOTES:       return notes;
          case PROP_OTHER_REQ:   return otherRequirements;
          case PROP_SIMILAR:     return similar;
          default:
             return null;
       }

    }
    
    /** Method to abstract datum setting.  Captures "null" values for crop datums.
     */
    public <T> void set( CropDatum<T> d, T v ) { set( d, v, false ); }
    private <T> void set( CropDatum<T> d, T v, boolean override ) {
    
       if ( v == null ||
//            ! override && ( v instanceof String && ( v.equals("") || ((String) v).equalsIgnoreCase("null") )) ||
            ! override && ( v instanceof String && ((String) v).equalsIgnoreCase("null") ) ||
            ! override && ( v instanceof Integer && ((Integer) v).intValue() == -1 ) )
          d.setDatum(null);
       else
          d.setDatum(v);
       
    }

    public int getID() { return cropID.getDatumAsInt(); }
    public void setID( int i ) { set( cropID, new Integer( i )); }
        
    public String getCropName() { return get( PROP_CROP_NAME, "" ); }
    public void setCropName( String s ) { set( cropName, s ); }

    public String getCropDescription() { return get( PROP_CROP_DESC, "" ); }
    public void setCropDescription( String s ) { set( cropDescription, s ); }

    public CPSCrop getSimilarCrop() {
       if ( isCrop() )
          return get( PROP_SIMILAR, new CPSCrop() );
       else
          return new CPSCrop();
    }
    public void setSimilarCrop( CPSCrop c ) { 
       if ( isCrop() )
          set( similar, c ); 
       else
          System.err.println("Similar crops are not supported for entries other than crops.");
    }
    
    public String getVarietyName() { return get( PROP_VAR_NAME, "" ); }
    public void setVarietyName( String s ) { set( varName, s ); }
   
    public String getFamilyName() { return get( PROP_FAM_NAME, "" ); }
    public void setFamilyName( String e) { set( familyName, e ); }

    public String getBotanicalName() { return get( PROP_BOT_NAME, "" ); }
    public void setBotanicalName( String e) { set( botanicalName, e ); }

    public boolean getSuccessions() { return get( PROP_SUCCESSIONS, 
                                                  new Boolean( false ) ).booleanValue(); }
    public void setSuccessions( boolean b ) { set( ds, new Boolean(b) ); }
    
    public int getMaturityDays() { return get( PROP_MATURITY, new Integer( -1 )).intValue(); }
    public void setMaturityDays( int i ) { set( maturityDays, new Integer( i )); }

    public String getGroups() { return get( PROP_GROUPS, "" ); }
    public void setGroups( String e) { set( groups, e ); }
    
    public String getKeywords() { return get( PROP_KEYWORDS, "" ); }
    public void setKeywords( String e) { set( keywords, e ); }

    public String getNotes() { return get( PROP_NOTES, "" ); }
    public void setNotes( String e) { set( notes, e ); }

    public String getOtherRequirments() { return get( PROP_OTHER_REQ, "" ); }
    public void setOtherRequirements( String e) { set( otherRequirements, e ); }
    
    
    /* private ==> hidden */
    private boolean getDS() { return ds.getDatumAsBoolean(); }
    private void setDS( boolean b ) { ds.setDatum( new Boolean(b) );}
   
    private boolean getTP() { return tp.getDatumAsBoolean(); }
    private void setTP( boolean b ) { tp.setDatum( new Boolean( b ) ); }
   
    
    public CPSCrop diff( CPSCrop thatCrop ) {
       
       System.out.println( "Calculating difference between:\n" +
                           this.toString() + "\n" +
                           thatCrop.toString() );
       
       CPSCrop d = new CPSCrop();
       boolean diffsExists = false;
       
       Iterator<CropDatum> thisIt = this.iterator();
       Iterator<CropDatum> thatIt = thatCrop.iterator();
       Iterator<CropDatum> deltIt = d.iterator();
       
       CropDatum thi, that, delta;
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
             d.set( delta, that.getDatum() );
             if ( ! delta.isValid() )
                d.set( delta, that.getDefaultValue(), true );
             diffsExists = true;
          }
       }
       
       // by default, a cropID of -1 means no differences.
       if ( diffsExists ) {
          System.out.println("Differences EXIST");
          d.setID( this.getID() );
       }
       
       return d;
    }
    
    /* Iterator */
    public CropIterator iterator() { return new CropIterator(); }
    
    public class CropIterator implements Iterator {
       
       private int currentProp;
       
       public CropIterator() { currentProp = -1; }
       
       public boolean hasNext() { return currentProp < PROP_SIMILAR; }

       public CropDatum next() {

          switch ( ++currentProp ) {
             case PROP_ID: currentProp++; // increment and fall through; could just return next()
             default: 
                return getCropDatum( currentProp );
          }
       }

       public void remove() {}
       
    }
    
    public String toString() {
       String s = "";
       
       CropIterator i = this.iterator();
       CropDatum c;
       while ( i.hasNext() ) {
          c = i.next();
          if ( c.isValid() )
             if ( c.getDatum() instanceof CPSCrop )
                s += c.getDescriptor() + " = '" + ((CPSCrop) c.getDatum()).getCropName() + "', ";
             else
                s += c.getDescriptor() + " = '" + c.getDatum() + "', ";
       }
       
       return s;
    }
    
    public boolean equals( Object o ) {
       return ( o instanceof CPSCrop && this.diff( (CPSCrop) o ).getID() == -1 );
    }
    
    /** Returns true if this CPSCrop object represents a "crop".
     *
     * Returns true if and only if the crop name is valid and the variety name is invalid.
     */
    public boolean isCrop() {
       return cropName.isValid() && ! varName.isValid();
    }
    
    /** Returns true if this CPSCrop object represents a "variety".
     *
     * Returns true if and only if both the crop name and the variety name are valid.
     */
    public boolean isVariety() {
       return cropName.isValid() && varName.isValid();
    }
}
