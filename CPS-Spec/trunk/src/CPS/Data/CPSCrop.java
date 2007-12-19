package CPS.Data;

/**
 * Crop - data structure to hold information about a crop
 *        NOT for a planting of a crop, but just for the crop
 */

import java.util.*;

public class CPSCrop extends CPSRecord {

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
   protected int lastValidProperty() { return PROP_SIMILAR; }
    
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
   @Override
    public <T> T get( int prop, T def ) {

       CropDatum d = getDatum( prop );
       
       /* these are the first two conditions from the supermethod
        * these MUST fail before we do our special calls */
       // TODO this is crude but works for now; need a more elegant solution
       if ( d != null && ! d.isValid() &&
            isCrop() && d.shouldBeInherited() && similar.isValid() && prop != PROP_SIMILAR ) {
          // get the similar crop and call it's getProperty routine
          CPSCrop sim = (CPSCrop) similar.getDatum();
          return sim.get( prop , def );
       }
       else {
          return super.get( prop, def );
       }
       
    }
    
    protected CropDatum getDatum( int prop ) {
       
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
    
    public int getID() { return cropID.getDatumAsInt(); }
    public void setID( int i ) { set( cropID, new Integer( i )); }
        
    public String getCropName() { return get( PROP_CROP_NAME, "" ); }
    public void setCropName( String s ) { setCropName( s, false ); }
    public void setCropName( String s, boolean force  ) { set( cropName, s, force ); }

    public String getCropDescription() { return get( PROP_CROP_DESC, "" ); }
    public void setCropDescription( String s ) { setCropDescription( s, false ); }
    public void setCropDescription( String s, boolean force  ) { set( cropDescription, s, force ); }

    public CPSCrop getSimilarCrop() {
       if ( isCrop() )
          return get( PROP_SIMILAR, new CPSCrop() );
       else
          return new CPSCrop();
    }
    public void setSimilarCrop( CPSCrop c ) { setSimilarCrop( c, false); }
    public void setSimilarCrop( CPSCrop c, boolean force  ) { 
       if ( isCrop() )
          set( similar, c, force ); 
       else
          System.err.println("Similar crops are not supported for entries other than crops.");
    }
    
    public String getVarietyName() { return get( PROP_VAR_NAME, "" ); }
    public void setVarietyName( String s ) { setVarietyName( s, false ); }
    public void setVarietyName( String s, boolean force ) { set( varName, s, force ); }
   
    public String getFamilyName() { return get( PROP_FAM_NAME, "" ); }
    public void setFamilyName( String e) { setFamilyName( e, false ); }
    public void setFamilyName( String e, boolean force ) { set( familyName, e, force ); }

    public String getBotanicalName() { return get( PROP_BOT_NAME, "" ); }
    public void setBotanicalName( String e) { setBotanicalName( e, false ); }
    public void setBotanicalName( String e, boolean force ) { set( botanicalName, e, force ); }

    public boolean getSuccessions() { return get( PROP_SUCCESSIONS, 
                                                  new Boolean( false ) ).booleanValue(); }
    public void setSuccessions( boolean b ) { set( ds, new Boolean(b) ); }
    
    public int getMaturityDays() { return get( PROP_MATURITY, new Integer( -1 )).intValue(); }
    public void setMaturityDays( int i ) { setMaturityDays( i, false ); }
   public void setMaturityDays( int i, boolean force ) {
      set( maturityDays, new Integer( i ), force );
   }

    public String getGroups() { return get( PROP_GROUPS, "" ); }
    public void setGroups( String e) { setGroups( e, false ); }
    public void setGroups( String e, boolean force ) { set( groups, e, force ); }
    
    public String getKeywords() { return get( PROP_KEYWORDS, "" ); }
    public void setKeywords( String e) { setKeywords( e, false ); }
    public void setKeywords( String e, boolean force ) { set( keywords, e, force ); }

    public String getNotes() { return get( PROP_NOTES, "" ); }
    public void setNotes( String e) { setNotes( e, false ); }
    public void setNotes( String e, boolean force ) { set( notes, e, force ); }

    public String getOtherRequirments() { return get( PROP_OTHER_REQ, "" ); }
    public void setOtherRequirements( String e) { setOtherRequirements( e, false ); }
   public void setOtherRequirements( String e, boolean force ) {
      set( otherRequirements, e, force );
   }
      
    
    public CPSRecord diff( CPSRecord thatCrop ) {
       return super.diff( thatCrop, new CPSCrop() );
    }
    
    /* Iterator */
    public CropIterator iterator() { return new CropIterator(); }
    
    public class CropIterator extends CPSRecordIterator {
       
       public  boolean ignoreThisProperty( int prop ) {
          return prop == PROP_ID;
       }
       
    }
    
    public String toString() {
       String s = "";
       
       CropIterator i = this.iterator();
       CPSDatum c;
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
