package CPS.Data;

/**
 * Planting - information about a particular planting of a crop and/or
 *            variety
 */

import java.text.SimpleDateFormat;
import java.util.Date;
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
   private CPSDatum<Date> date_plant;
   private CPSDatum<Date> date_tp;
   private CPSDatum<Date> date_harvest;
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
      date_plant = new CPSDatum<Date>( "Planting Date", PROP_DATE_PLANT, "date_plant", new Date(0) );
      date_tp = new CPSDatum<Date>( "Transplant Date", PROP_DATE_TP, "date_tp", new Date(0) );
      date_harvest = new CPSDatum<Date>( "Harvest Date", PROP_DATE_HARVEST, "date_harvest", new Date(0) );
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
   public void setCropName(String s) { setCropName( s, false ); }
   public void setCropName( String s, boolean force ) { set( crop_name, s, force ); }

   public String getVarietyName() { return get( PROP_VAR_NAME, "" ); }
   public void setVarietyName( String s ) { setVarietyName( s, false ); }
   public void setVarietyName( String s, boolean force ) { set( var_name, s, force ); }

   public String getGroups() { return get( PROP_GROUPS, "" ); }
   public void setGroups( String e) { setGroups( e, false ); }
   public void setGroups( String e, boolean force ) { set( groups, e, force ); }

   public String getLocation() { return get(  PROP_LOCATION, "" ); }
   public void setLocation( String s ) { setLocation( s, false ); }
   public void setLocation( String s, boolean force ) { set( location, s, force ); }

   public String getKeywords() { return get( PROP_KEYWORDS, "" ); }
   public void setKeywords( String e) { setKeywords( e, false ); }
   public void setKeywords( String e, boolean force ) { set( keywords, e, force ); }

   public String getStatus() { return get( PROP_STATUS, "" ); }
   public void setStatus( String e) { setStatus( e, false ); }
   public void setStatus( String e, boolean force ) { set( status, e, force ); }

   public Boolean getCompleted() { return get( PROP_COMPLETED, new Boolean( false )).booleanValue(); }
   public void setCompleted( Boolean b ) { setCompleted( b, false ); }
   public void setCompleted( Boolean b, boolean force ) { set( completed, b, force ); }

   public String getOtherRequirments() { return get( PROP_OTHER_REQ, "" ); }
   public void setOtherRequirements( String e) { setOtherRequirements( e, false ); }
   public void setOtherRequirements( String e, boolean force ) { set( other_req, e, force ); }

   public String getNotes() { return get( PROP_NOTES, "" ); }
   public void setNotes( String e) { setNotes( e, false ); }
   public void setNotes( String e, boolean force ) { set( notes, e, force ); }

   public int getMaturityDays() { return get( PROP_MATURITY, new Integer( -1 )).intValue(); }
   public void setMaturityDays( int i ) { setMaturityDays( i, false ); }
   public void setMaturityDays( int i, boolean force ) { set( maturity, new Integer( i ), force ); }
   public void setMaturityDays( String s ) { setMaturityDays( s, false ); }
   public void setMaturityDays( String s, boolean force ) {
        if ( s == null || s.equalsIgnoreCase("null") || s.equals("") )
            setMaturityDays( -1, force );
        else
            set( maturity, new Integer( s ), force );
    }
   
//          case PROP_DATE_PLANT:    return date_plant;
   public Date getDateToPlant() {
       CPSDatum p = getDatum( PROP_DATE_PLANT );
       CPSDatum m = getDatum( PROP_MATURITY );
       CPSDatum h = getDatum( PROP_DATE_HARVEST );
       
       /* Only calculate the planting date if:
        * DATE_PLANT is *NOT* valid AND
        * DATE_HARVEST AND MATURITY *ARE* valid 
        * otherwise just return the planting date or a default */
       if ( ! p.isValid() && 
              h.isValid() && m.isValid() ) {
           GregorianCalendar c = new GregorianCalendar();
           c.setTime( getDateToHarvest() );
           // -1 ==> subtract
           c.add( GregorianCalendar.DAY_OF_YEAR, -1 * getMaturityDays() );
           return c.getTime();
       }
       else
           return get( PROP_DATE_PLANT, new Date() ); 
   }
   public String getDateToPlantString() { return formatDate( getDateToPlant() ); }
   public void setDateToPlant( Date d ) { setDateToPlant( d, false ); }
   public void setDateToPlant( String d ) { setDateToPlant( parseDate(d), false ); }
   public void setDateToPlant( Date d, boolean force ) { set( date_plant, d, force ); }
   public void setDateToPlant( String d, boolean force ) { setDateToPlant( parseDate(d), force ); }
   
//          case PROP_DATE_TP:       return date_tp;
   public Date getDateToTP() { return get( PROP_DATE_TP, new Date() ); }
   public String getDateToTPString() { return formatDate( getDateToTP() ); }
   public void setDateToTP( Date d ) { setDateToTP( d, false ); }
   public void setDateToTP( String d ) { setDateToTP( parseDate( d ), false ); }
   public void setDateToTP( Date d, boolean force ) { set( date_tp, d, force ); }
   public void setDateToTP( String d, boolean force ) { setDateToTP( parseDate( d ), force ); }
   
   public Date getDateToHarvest() {
       CPSDatum h = getDatum( PROP_DATE_HARVEST );
       CPSDatum p = getDatum(PROP_DATE_PLANT);
       CPSDatum m = getDatum(PROP_MATURITY);
       
       /* Only calculate the harvest date if:
        * DATE_HARVEST is *NOT* valid AND
        * DATE_PLANTING AND MATURITY *ARE* valid 
        * otherwise just return the harvest date or a default */
       if ( ! h.isValid() && 
              p.isValid() && m.isValid() ) {
           GregorianCalendar c = new GregorianCalendar();
           c.setTime( getDateToPlant() );
           c.add( GregorianCalendar.DAY_OF_YEAR, getMaturityDays() );
           return c.getTime();
       }
       else
           return get(PROP_DATE_HARVEST, new Date());
   }
   public String getDateToHarvestString() { return formatDate( getDateToHarvest() ); }
   public void setDateToHarvest( Date d ) { setDateToHarvest( d, false ); }
   public void setDateToHarvest( Date d, boolean force ) { set( date_harvest, d, force ); }
   public void setDateToHarvest( String d ) { setDateToHarvest( parseDate( d ), false ); }
   public void setDateToHarvest( String d, boolean force ) { setDateToHarvest( parseDate( d ), force ); }
   
   public int getBedsToPlant() { return get( PROP_BEDS_PLANT, new Integer( -1 )).intValue(); }
   public void setBedsToPlant( int i ) { setBedsToPlant( i, false ); }
   public void setBedsToPlant( int i, boolean force ) { set( beds_to_plant, new Integer( i ), force ); }

   public int getPlantsNeeded() { return get( PROP_PLANTS_NEEDED, new Integer( -1 )).intValue(); }
   public void setPlantsNeeded( int i ) { setPlantsNeeded( i, false ); }
   public void setPlantsNeeded( int i, boolean force ) { set( plants_needed, new Integer( i ), force ); }

   public int getRowFtToPlant() { return get( PROP_ROWFT_PLANT, new Integer( -1 )).intValue(); }
   public void setRowFtToPlant( int i ) { setRowFtToPlant( i, false ); }
   public void setRowFtToPlant( int i, boolean force ) { set( rowft_to_plant, new Integer( i ), force ); }

   public int getPlantsToStart() { return get( PROP_PLANTS_START, new Integer( -1 )).intValue(); }
   public void setPlantsToStart( int i ) { setPlantsToStart( i, false ); }
   public void setPlantsToStart( int i, boolean force ) { set( plants_to_start, new Integer( i ), force ); }

   public int getFlatsNeeded() { return get( PROP_FLATS_NEEDED, new Integer( -1 )).intValue(); }
   public void setFlatsNeeded( int i ) { setFlatsNeeded( i, false ); }
   public void setFlatsNeeded( int i, boolean force ) { set( flats_needed, new Integer( i ), force ); }

   
   
   public CPSRecord diff( CPSRecord thatPlanting ) {
      return super.diff( thatPlanting, new CPSPlanting() );
   }

   public PlantingIterator iterator() { return new PlantingIterator(); }
   
   public class PlantingIterator extends CPSRecordIterator {
       
      public  boolean ignoreThisProperty( int prop ) {
         return prop == PROP_ID || prop == PROP_CROP_ID;
      }
       
   }

   public String toString() { 
       String s = "";
       
       PlantingIterator i = this.iterator();
       CPSDatum c;
       while ( i.hasNext() ) {
          c = i.next();
          if ( c.isValid() )
              s += c.getDescriptor() + " = '" + c.getDatum() + "', ";
       }
       
       return s;
    }


   public static Date parseDate( String s ) {
       if ( s == null || s.equals("") )
           return new Date(0);
       
       SimpleDateFormat sdf = new SimpleDateFormat( "dd MM yyyy" );
       try {
           return sdf.parse(s);
       } catch ( Exception ignore ) {
           System.err.println( "ERROR parsing date: " + s );
           return null;
       }
   }
   
   public static String formatDate( Date d ) {
       SimpleDateFormat sdf = new SimpleDateFormat( "dd MM yyyy" );
       return sdf.format(d);
   }
   
}
