package CPS.Data;

/**
 * Planting - information about a particular planting of a crop and/or
 *            variety
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CPSPlanting extends CPSRecord {

   // Unimplemented fields:
//      s += "plant_mtd_id  INTEGER, "; // References PLANTING_METHODS.id
//      s += "successions BOOLEAN, ";
//      s += "fudge     FLOAT, ";
//      s += "mat_adjust      INTEGER, ";
//      s += "planting_adjust INTEGER, ";
//      s += "ds_adjust       INTEGER, ";
//      s += "season_adjust   INTEGER, ";
//      s += "time_to_tp      INTEGER, ";
//      s += "misc_adjust     INTEGER, ";
//      s += "rows_p_bed      INTEGER, ";
//      s += "inrow_space     FLOAT, ";
//      s += "row_space       FLOAT, ";
//      s += "flat_size       VARCHAR, ";
//      s += "planter         VARCHAR, ";
//      s += "planter_setting VARCHAR, ";
   
//      s += "yield_p_foot    FLOAT, ";
//      s += "total_yield     FLOAT, ";
//      s += "yield_num_weeks INTEGER, ";
//      s += "yield_p_week    FLOAT, ";
     
//      s += "crop_unit       VARCHAR, ";
//      s += "crop_unit_value FLOAT, ";
   
   public final int PROP_ID            = 0;
   public final int PROP_CROP_ID       = 1;
   public final int PROP_CROP_NAME     = 2;
   public final int PROP_VAR_NAME      = 3;
   public final int PROP_GROUPS        = 4;
   public final int PROP_LOCATION      = 5;
   public final int PROP_KEYWORDS      = 6;
   public final int PROP_STATUS        = 7;
   public final int PROP_COMPLETED     = 8;
   public final int PROP_OTHER_REQ     = 9;
   public final int PROP_NOTES         = 10;
   public final int PROP_MATURITY      = 11;
   public final int PROP_DATE_PLANT    = 12;
   public final int PROP_DATE_TP       = 13;
   public final int PROP_DATE_HARVEST  = 14;
   public final int PROP_BEDS_PLANT    = 15;
   public final int PROP_PLANTS_NEEDED = 16;
   public final int PROP_ROWFT_PLANT   = 17;
   public final int PROP_PLANTS_START  = 18;
   public final int PROP_FLATS_NEEDED  = 19;
   protected int lastValidProperty() { return PROP_FLATS_NEEDED; }
   
   private CPSDatum<Integer> plantingID;
   private CPSDatum<Integer> cropID;
   private CPSDatum<String> crop_name;
   private CPSDatum<String> var_name;
   private CPSDatum<String> groups;
   private CPSDatum<String> location;
   private CPSDatum<String> keywords;
   private CPSDatum<String> status;
   private CPSDatum<Boolean> completed;
   private CPSDatum<String> other_req;
   private CPSDatum<String> notes;
   private CPSDatum<Integer> maturity;
   private CPSDatum<String> date_plant;
   private CPSDatum<String> date_tp;
   private CPSDatum<String> date_harvest;
//   private CPSDatum<Calendar> date_plant;
//   private CPSDatum<Calendar> date_tp;
//   private CPSDatum<Calendar> date_harvest;
   private CPSDatum<Integer> beds_to_plant;
   private CPSDatum<Integer> plants_needed;
   private CPSDatum<Integer> rowft_to_plant;
   private CPSDatum<Integer> plants_to_start;
   private CPSDatum<Integer> flats_needed;
   
   
   
   public CPSPlanting() {
      
      plantingID = new CPSDatum<Integer>( "Unique ID", PROP_ID, "id", new Integer(-1) );
      cropID = new CPSDatum<Integer>( "Unique ID of Crop", PROP_CROP_ID, "crop_id", new Integer(-1));
      crop_name = new CPSDatum<String>( "Crop name", PROP_CROP_NAME, "crop_name", "" );
      var_name = new CPSDatum<String>( "Variety name", PROP_VAR_NAME, "var_name", "" );
      groups = new CPSDatum<String>( "Groups", PROP_GROUPS, "groups", "" );
      location = new CPSDatum<String>( "Location", PROP_LOCATION, "location", "" );
      keywords = new CPSDatum<String>( "Keywords", PROP_KEYWORDS, "keywords", "" );
      status = new CPSDatum<String>( "Status", PROP_STATUS, "status", "" );
      completed = new CPSDatum<Boolean>( "Completed?", PROP_COMPLETED, "completed", new Boolean( false ));
      other_req = new CPSDatum<String>( "Other Requirements", PROP_OTHER_REQ, "other_req", "" );
      notes = new CPSDatum<String>( "Notes", PROP_NOTES, "notes", "" );
      maturity = new CPSDatum<Integer>( "Maturity Days", PROP_MATURITY, "maturity", new Integer(-1));
      // TODO these should default to first frost free date?
//      date_plant = new CPSDatum<Calendar>( "Planting Date", PROP_DATE_PLANT, "date_plant", new GregorianCalendar() );
//      date_tp = new CPSDatum<Calendar>( "Transplant Date", PROP_DATE_TP, "date_tp", new GregorianCalendar() );
//      date_harvest = new CPSDatum<Calendar>( "Harvest Date", PROP_DATE_HARVEST, "date_harvest", new GregorianCalendar() );
      date_plant = new CPSDatum<String>( "Planting Date", PROP_DATE_PLANT, "date_plant", "" );
      date_tp = new CPSDatum<String>( "Transplant Date", PROP_DATE_TP, "date_tp", "" );
      date_harvest = new CPSDatum<String>( "Harvest Date", PROP_DATE_HARVEST, "date_harvest", "" );
      beds_to_plant = new CPSDatum<Integer>( "Num. Beds to Plants", PROP_BEDS_PLANT, "beds_to_plant", new Integer(-1) );
      plants_needed = new CPSDatum<Integer>( "Num. Plants Needed", PROP_PLANTS_NEEDED, "plants_needed", new Integer(-1) );
      rowft_to_plant = new CPSDatum<Integer>( "Row Feet To Plant", PROP_ROWFT_PLANT, "rowft_to_plant", new Integer(-1) );
      plants_to_start = new CPSDatum<Integer>( "Num. Plants to Start", PROP_PLANTS_START, "plants_to_start", new Integer(-1) );
      flats_needed = new CPSDatum<Integer>( "Num. Flats Needed", PROP_FLATS_NEEDED, "flats_needed", new Integer(-1) );
      
   }


   protected CPSDatum getDatum(int prop) {

      /* very ugly, but this allows us to use the hierarchy of Crop properties */
       switch ( prop ) {
          case PROP_CROP_NAME:     return crop_name;
          case PROP_VAR_NAME:      return var_name;
          case PROP_GROUPS:        return groups;
          case PROP_LOCATION:      return location;
          case PROP_KEYWORDS:      return keywords;
          case PROP_STATUS:        return status;
          case PROP_COMPLETED:     return completed;
          case PROP_OTHER_REQ:     return other_req;
          case PROP_NOTES:         return notes;
          case PROP_MATURITY:      return maturity;
          case PROP_DATE_PLANT:    return date_plant;
          case PROP_DATE_TP:       return date_tp;
          case PROP_DATE_HARVEST:  return date_harvest;
          case PROP_BEDS_PLANT:    return beds_to_plant;
          case PROP_PLANTS_NEEDED: return plants_needed;
          case PROP_ROWFT_PLANT:   return rowft_to_plant;
          case PROP_PLANTS_START:  return plants_needed;
          case PROP_FLATS_NEEDED:  return flats_needed;
          default:
             return null;
       }

   }

   public int getID() { return plantingID.getDatumAsInt(); }
   public void setID(int i) { set( plantingID, new Integer( i )); }

   public String getCropName() { return get(PROP_CROP_NAME, ""); }
   public void setCropName(String s) { set(crop_name, s); }

   public String getVarietyName() { return get( PROP_VAR_NAME, "" ); }
   public void setVarietyName( String s ) { set( var_name, s ); }

   public String getGroups() { return get( PROP_GROUPS, "" ); }
   public void setGroups( String e) { set( groups, e ); }

   public String getLocation() { return get(  PROP_LOCATION, "" ); }
   public void setLocation( String s ) { set( location, s ); }

   public String getKeywords() { return get( PROP_KEYWORDS, "" ); }
   public void setKeywords( String e) { set( keywords, e ); }

   public String getStatus() { return get( PROP_STATUS, "" ); }
   public void setStatus( String e) { set( status, e ); }

   public Boolean getCompleted() { return get( PROP_COMPLETED, new Boolean( false )).booleanValue(); }
   public void setCompleted( Boolean b ) { set( completed, b ); }

   public String getOtherRequirments() { return get( PROP_OTHER_REQ, "" ); }
   public void setOtherRequirements( String e) { set( other_req, e ); }

   public String getNotes() { return get( PROP_NOTES, "" ); }
   public void setNotes( String e) { set( notes, e ); }

   public int getMaturityDays() { return get( PROP_MATURITY, new Integer( -1 )).intValue(); }
   public void setMaturityDays( int i ) { set( maturity, new Integer( i )); }

//          case PROP_DATE_PLANT:    return date_plant;
   public String getDateToPlant() { return get( PROP_DATE_PLANT, "" ); }
   public void setDateToPlant( String d ) { set( date_plant, d ); }
   
//          case PROP_DATE_TP:       return date_tp;
   public String getDateToTP() { return get( PROP_DATE_TP, "" ); }
   public void setDateToTP( String d ) { set( date_tp, d ); }

//          case PROP_DATE_HARVEST:  return date_harvest;
   public String getDateToHarvest() { return get( PROP_DATE_HARVEST, "" ); }
   public void setDateToHarvest( String d ) { set( date_harvest, d ); }

   public int getBedsToPlant() { return get( PROP_BEDS_PLANT, new Integer( -1 )).intValue(); }
   public void setBedsToPlant( int i ) { set( beds_to_plant, new Integer( i )); }

   public int getPlantsNeeded() { return get( PROP_PLANTS_NEEDED, new Integer( -1 )).intValue(); }
   public void setPlantsNeeded( int i ) { set( plants_needed, new Integer( i )); }

   public int getRowFtToPlant() { return get( PROP_ROWFT_PLANT, new Integer( -1 )).intValue(); }
   public void setRowFtToPlant( int i ) { set( rowft_to_plant, new Integer( i )); }

   public int getPlantsToStart() { return get( PROP_PLANTS_START, new Integer( -1 )).intValue(); }
   public void setPlantsToStart( int i ) { set( plants_to_start, new Integer( i )); }

   public int getFlatsNeeded() { return get( PROP_FLATS_NEEDED, new Integer( -1 )).intValue(); }
   public void setFlatsNeeded( int i ) { set( flats_needed, new Integer( i )); }

   
   
   public CPSRecord diff( CPSRecord thatPlanting ) {
      return super.diff( thatPlanting, new CPSPlanting() );
   }

   public PlantingIterator iterator() { return new PlantingIterator(); }
   
   public class PlantingIterator extends CPSRecordIterator {
       
      public  boolean ignoreThisProperty( int prop ) {
         return prop == PROP_ID || prop == PROP_CROP_ID;
      }
       
   }


   public String toString() { return null; }

}
