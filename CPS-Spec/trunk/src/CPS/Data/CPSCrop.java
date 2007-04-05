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
   
   private CropDatum<Integer> cropID;
   private CPSCrop similar;
   
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
       
       cropID = new CropDatum<Integer>( "Unique ID", PROP_ID, "id" );
       similar = null;
       
       cropName = new CropDatum<String>( "Crop name", PROP_CROP_NAME, "crop_name" );
       cropDescription = new CropDatum<String>( "Crop description", PROP_CROP_DESC, "description" );
       varName = new CropDatum<String>( "Variety", PROP_VAR_NAME, "var_name" );
       familyName = new CropDatum<String>( "Family name", PROP_FAM_NAME, "fam_name" );
       botanicalName = new CropDatum<String>( "Botanical name", PROP_BOT_NAME, "bot_name" );
        
       successions = new CropDatum<Boolean>( "Succession?", PROP_SUCCESSIONS, "successions" );
        
       maturityDays = new CropDatum<Integer>( "Days to maturity", PROP_MATURITY, "maturity" );
        
       groups = new CropDatum<String>( "Groups", PROP_GROUPS, "groups" );
       keywords = new CropDatum<String>( "Keywords", PROP_KEYWORDS, "keywords" );
       otherRequirements = new CropDatum<String>( "Other Requirements", PROP_OTHER_REQ, "other_req" );
       notes = new CropDatum<String>( "Notes", PROP_NOTES, "notes" );
        
       
       inRowSpacing = new CropDatum<Integer>("In-Row spacing");
       rowSpacing = new CropDatum<Integer>("Between rows");
       rowsPerBed = new CropDatum<Integer>("Rows Per Bed");
       flat = new CropDatum<String>("Flat size");
       ds = new CropDatum<Boolean>( "Direct Seed?" );
       tp = new CropDatum<Boolean>( "Transplant?" );
        
    }

    
    // @param d Datum to retrieve
    // @param def Default value, returned if no others found
    public <T> T get( CropDatum<T> d, T def ) {
       Object t;
       
       /* very ugly, but this allows us to use the hierarchy of Crop properties */
       switch ( d.getPropertyNum() ) {
          case PROP_CROP_NAME:
             t = cropName.getDatum(); break;
          case PROP_CROP_DESC:
             t = cropDescription.getDatum(); break;
          case PROP_VAR_NAME:
             t = varName.getDatum(); break;
          case PROP_FAM_NAME:
             t = familyName.getDatum(); break;
          case PROP_BOT_NAME:
             t = botanicalName.getDatum(); break;
          case PROP_SUCCESSIONS:
             t = successions.getDatum(); break;
          case PROP_MATURITY:
             t = maturityDays.getDatum(); break;
          case PROP_GROUPS:
             t = groups.getDatum(); break;
          case PROP_KEYWORDS:
             t = keywords.getDatum(); break;
          case PROP_NOTES:
             t = notes.getDatum(); break;
          case PROP_OTHER_REQ:
             t = otherRequirements.getDatum(); break;
          default:
             t = null;
       }
       
       if      ( t != null )
          return (T) t;
       else if ( similar != null )
          return similar.get( d, def );
       else if ( def == null )
          System.err.println( "Error, datum undefined: " + d.getDescriptor() );
          
       return (T) def;
       
    }
    
//    public <T> void setter( CropDatum<T> d, T v ) { d.setDatum(v); }

    public int getID() { return cropID.getDatumAsInt(); }
    public void setID( int i ) { cropID.setDatum( new Integer( i )); }
        
    public String getCropName() { return get( cropName, null ); }
    public void setCropName( String s ) { cropName.setDatum( s ); }

    public String getCropDescription() { return get( cropDescription, "" ); }
    public void setCropDescription( String s ) { cropDescription.setDatum( s ); }

    public CPSCrop getSimilarCrop() { return similar; }
    public void setSimilarCrop( CPSCrop c ) { similar = c; }
    
    public String getVarietyName() { return get( varName, "" ); }
    public void setVarietyName( String s ) { varName.setDatum( s ); }
   
    public String getFamilyName() { return get( familyName, "" ); }
    public void setFamilyName( String e) { familyName.setDatum( e ); }

    public String getBotanicalName() { return get( botanicalName, "" ); }
    public void setBotanicalName( String e) { botanicalName.setDatum( e ); }

    public boolean getSuccessions() { return get( successions, new Boolean( false )).booleanValue(); }
    public void setSuccessions( boolean b ) { ds.setDatum( new Boolean(b) ); }
    
    public int getMaturityDays() { return get( maturityDays, new Integer( -1 )).intValue(); }
    public void setMaturityDays( int i ) { maturityDays.setDatum( new Integer( i )); }

    public String getGroups() { return get( groups, "" ); }
    public void setGroups( String e) { groups.setDatum( e ); }
    
    public String getKeywords() { return get( keywords, "" ); }
    public void setKeywords( String e) { keywords.setDatum( e ); }

    public String getNotes() { return get( notes, "" ); }
    public void setNotes( String e) { notes.setDatum( e ); }

    public String getOtherRequirments() { return get( otherRequirements, "" ); }
    public void setOtherRequirements( String e) { otherRequirements.setDatum( e ); }
    
    
    /* private ==> hidden */
    private boolean getDS() { return ds.getDatumAsBoolean(); }
    private void setDS( boolean b ) { ds.setDatum( new Boolean(b) );}
   
    private boolean getTP() { return tp.getDatumAsBoolean(); }
    private void setTP( boolean b ) { tp.setDatum( new Boolean( b ) ); }
   
    
    public CropIterator iterator() { return new CropIterator(); }
    
    
    public class CropIterator implements Iterator {
       
       private int currentProp;
       
       public CropIterator() { currentProp = -1; }
       
       public boolean hasNext() { return currentProp < PROP_NOTES; }

       public CropDatum next() {
          switch ( ++currentProp ) {
             case PROP_CROP_NAME: return cropName;
             case PROP_CROP_DESC: return cropDescription;
             case PROP_VAR_NAME: return varName;
             case PROP_FAM_NAME: return familyName;
             case PROP_BOT_NAME: return botanicalName;
             case PROP_SUCCESSIONS: return successions;
             case PROP_MATURITY: return maturityDays;
             case PROP_GROUPS: return groups;
             case PROP_KEYWORDS: return keywords;
             case PROP_NOTES: return notes;
             case PROP_OTHER_REQ: return otherRequirements;
             default: return null;
          }
       }

       public void remove() {}
       
    }
}
