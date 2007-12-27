package CPS.Data;

/**
 * Planting - information about a particular planting of a crop and/or
 *            variety
 */

import CPS.Module.CPSDataModelConstants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class CPSPlanting extends CPSRecord {

   // Unimplemented fields:
//      s += "plant_mtd_id  INTEGER, "; // References PLANTING_METHODS.id
//      s += "successions BOOLEAN, ";
//      s += "fudge     FLOAT, ";
   
   public final int PROP_ID            = CPSDataModelConstants.PROP_PLANTING_ID;
   public final int PROP_CROP_ID       = CPSDataModelConstants.PROP_CROP_ID;
   public final int PROP_CROP_NAME     = CPSDataModelConstants.PROP_CROP_NAME;
   public final int PROP_VAR_NAME      = CPSDataModelConstants.PROP_VAR_NAME;
   public final int PROP_GROUPS        = CPSDataModelConstants.PROP_GROUPS;
   public final int PROP_LOCATION      = CPSDataModelConstants.PROP_LOCATION;
   public final int PROP_KEYWORDS      = CPSDataModelConstants.PROP_KEYWORDS;
   public final int PROP_STATUS        = CPSDataModelConstants.PROP_STATUS;
   public final int PROP_COMPLETED     = CPSDataModelConstants.PROP_COMPLETED;
   public final int PROP_OTHER_REQ     = CPSDataModelConstants.PROP_OTHER_REQ;
   public final int PROP_NOTES         = CPSDataModelConstants.PROP_NOTES;
   public final int PROP_MATURITY      = CPSDataModelConstants.PROP_MATURITY;
   public final int PROP_DATE_PLANT    = CPSDataModelConstants.PROP_DATE_PLANT;
   public final int PROP_DATE_TP       = CPSDataModelConstants.PROP_DATE_TP;
   public final int PROP_DATE_HARVEST  = CPSDataModelConstants.PROP_DATE_HARVEST;
   public final int PROP_BEDS_PLANT    = CPSDataModelConstants.PROP_BEDS_PLANT;
   public final int PROP_PLANTS_NEEDED = CPSDataModelConstants.PROP_PLANTS_NEEDED;
   public final int PROP_ROWFT_PLANT   = CPSDataModelConstants.PROP_ROWFT_PLANT;
   public final int PROP_PLANTS_START  = CPSDataModelConstants.PROP_PLANTS_START;
   public final int PROP_FLATS_NEEDED  = CPSDataModelConstants.PROP_FLATS_NEEDED;
   
   public final int PROP_MAT_ADJUST    = CPSDataModelConstants.PROP_MAT_ADJUST;
   public final int PROP_PLANTING_ADJUST = CPSDataModelConstants.PROP_PLANTING_ADJUST;
//   public final int PROP_DS_ADJUST     = CPSDataModelConstants.PROP_DS_ADJUST;
//   public final int PROP_SEASON_ADJUST = CPSDataModelConstants.PROP_SEASON_ADJUST;
   public final int PROP_TIME_TO_TP    = CPSDataModelConstants.PROP_TIME_TO_TP;
   public final int PROP_MISC_ADJUST   = CPSDataModelConstants.PROP_MISC_ADJUST;
   public final int PROP_ROWS_P_BED    = CPSDataModelConstants.PROP_ROWS_P_BED;
   public final int PROP_INROW_SPACE   = CPSDataModelConstants.PROP_SPACE_INROW;
   public final int PROP_ROW_SPACE     = CPSDataModelConstants.PROP_SPACE_BETROW;
   public final int PROP_FLAT_SIZE     = CPSDataModelConstants.PROP_FLAT_SIZE;
   public final int PROP_PLANTER       = CPSDataModelConstants.PROP_PLANTER;
   public final int PROP_PLANTER_SETTING = CPSDataModelConstants.PROP_PLANTER_SETTING;
   public final int PROP_YIELD_P_FOOT  = CPSDataModelConstants.PROP_YIELD_P_FOOT;
   public final int PROP_TOTAL_YIELD   = CPSDataModelConstants.PROP_TOTAL_YIELD;
   public final int PROP_YIELD_NUM_WEEKS = CPSDataModelConstants.PROP_YIELD_NUM_WEEKS;
   public final int PROP_YIELD_P_WEEK  = CPSDataModelConstants.PROP_YIELD_P_WEEK;
   public final int PROP_CROP_UNIT     = CPSDataModelConstants.PROP_CROP_UNIT;
   public final int PROP_CROP_UNIT_VALUE = CPSDataModelConstants.PROP_CROP_UNIT_VALUE;
   
   protected int lastValidProperty() { return PROP_CROP_UNIT_VALUE; }
   
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
   
   private CPSDatum<Integer> mat_adjust;
   private CPSDatum<Integer> planting_adjust;
   private CPSDatum<Integer> time_to_tp;
   private CPSDatum<Integer> misc_adjust;
   private CPSDatum<Integer> rows_p_bed;
   private CPSDatum<Float> inrow_space;
   private CPSDatum<Float> row_space;
   private CPSDatum<String> flat_size;
   private CPSDatum<String> planter;
   private CPSDatum<String> planter_setting;
   private CPSDatum<Float> yield_p_foot;
   private CPSDatum<Float> total_yield;
   private CPSDatum<Integer> yield_num_weeks;
   private CPSDatum<Float> yield_p_week;
   private CPSDatum<String> crop_unit;
   private CPSDatum<Float> crop_unit_value;

   
   
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
      
      mat_adjust = new CPSDatum<Integer>( "Mat. Adjustment", PROP_MAT_ADJUST, "mat_adjust", new Integer(-1) );
      planting_adjust = new CPSDatum<Integer>( "Add. Adjustment to Mat.", PROP_PLANTING_ADJUST, "planting_adjust", new Integer(-1) );
      time_to_tp = new CPSDatum<Integer>( "Weeks to TP", PROP_TIME_TO_TP, "time_to_tp", new Integer( -1 ) );
      misc_adjust = new CPSDatum<Integer>( "Misc. Adjustment to Mat.", PROP_MISC_ADJUST, "misc_adjust", new Integer( -1 ) );
      rows_p_bed = new CPSDatum<Integer>( "Rows/Bed", PROP_ROWS_P_BED, "rows_p_bed", new Integer(-1) );
      inrow_space = new CPSDatum<Float>( "In-row Spacing", PROP_INROW_SPACE, "inrow_space", new Float( -1.0 ) );
      row_space = new CPSDatum<Float>( "Row Spacing", PROP_ROW_SPACE, "row_space", new Float( -1.0 ) );
      flat_size = new CPSDatum<String>( "Flat size", PROP_FLAT_SIZE, "flat_size", "" );
      planter = new CPSDatum<String>( "Planter", PROP_PLANTER, "planter", "" );
      planter_setting = new CPSDatum<String>( "Planter setting", PROP_PLANTER_SETTING, "planter_setting", "" );
      yield_p_foot = new CPSDatum<Float>( "Yield/Ft", PROP_YIELD_P_FOOT, "yield_p_foot", new Float(-1.0) );
      total_yield = new CPSDatum<Float>( "Total Yield", PROP_TOTAL_YIELD, "total_yield", new Float(-1.0) );
      yield_num_weeks = new CPSDatum<Integer>( "Will Yield for (weeks)", PROP_YIELD_NUM_WEEKS, "yield_num_weeks", new Integer(-1) );
      yield_p_week = new CPSDatum<Float>( "Yield/Week", PROP_YIELD_P_WEEK, "yield_p_week", new Float(-1.0));
      crop_unit = new CPSDatum<String>( "Unit of Yield", PROP_CROP_UNIT, "crop_unit", "" );
      crop_unit_value = new CPSDatum<Float>( "Value per Yield Unit", PROP_CROP_UNIT_VALUE, "crop_unit_value", new Float(-1.0));

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
          
          case PROP_MAT_ADJUST:    return mat_adjust;
          case PROP_PLANTING_ADJUST: return planting_adjust;
          case PROP_TIME_TO_TP:    return time_to_tp;
          case PROP_MISC_ADJUST:   return misc_adjust;
          case PROP_ROWS_P_BED:    return rows_p_bed;
          case PROP_INROW_SPACE:   return inrow_space;
          case PROP_ROW_SPACE:     return row_space;
          case PROP_FLAT_SIZE:     return flat_size;
          case PROP_PLANTER:       return planter;
          case PROP_PLANTER_SETTING: return planter_setting;
          case PROP_YIELD_P_FOOT:  return yield_p_foot;
          case PROP_TOTAL_YIELD:   return total_yield;
          case PROP_YIELD_NUM_WEEKS: return yield_num_weeks;
          case PROP_YIELD_P_WEEK:  return yield_p_week;
          case PROP_CROP_UNIT:     return crop_unit;
          case PROP_CROP_UNIT_VALUE: return crop_unit_value;
          
          default:
             return null;
       }

   }
   
   public ArrayList<Integer> getListOfInheritableProperties() {
      ArrayList<Integer> a = new ArrayList<Integer>();
      a.add( PROP_OTHER_REQ );
      a.add( PROP_MATURITY );
      a.add( PROP_MAT_ADJUST );
      a.add( PROP_TIME_TO_TP );
      a.add( PROP_ROWS_P_BED );
      a.add( PROP_INROW_SPACE );
      a.add( PROP_ROW_SPACE );
      a.add( PROP_FLAT_SIZE );
      a.add( PROP_PLANTER );
      a.add( PROP_PLANTER_SETTING );
      a.add( PROP_YIELD_P_FOOT );
      a.add( PROP_YIELD_NUM_WEEKS );
      a.add( PROP_YIELD_P_WEEK );
      a.add( PROP_CROP_UNIT );
      a.add( PROP_CROP_UNIT_VALUE );
      return a;
   }

   
   
   /* *********************************************************************************************/
   /* GETTERS and SETTERS
   /* *********************************************************************************************/
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

   public int getMaturityDays() { return getInt( PROP_MATURITY ); }
   public String getMaturityDaysString() { return getAsString( PROP_MATURITY ); }
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
   public String getBedsToPlantString() { return getAsString( PROP_BEDS_PLANT ); }
   public void setBedsToPlant( int i ) { setBedsToPlant( i, false ); }
   public void setBedsToPlant( int i, boolean force ) { set( beds_to_plant, new Integer( i ), force ); }

   public int getPlantsNeeded() { return get( PROP_PLANTS_NEEDED, new Integer( -1 )).intValue(); }
   public String getPlantsNeededString() { return getAsString( PROP_PLANTS_NEEDED ); }
   public void setPlantsNeeded( int i ) { setPlantsNeeded( i, false ); }
   public void setPlantsNeeded( int i, boolean force ) { set( plants_needed, new Integer( i ), force ); }

   public int getRowFtToPlant() { return get( PROP_ROWFT_PLANT, new Integer( -1 )).intValue(); }
   public String getRowFtToPlantString() { return getAsString( PROP_ROWFT_PLANT ); }
   public void setRowFtToPlant( int i ) { setRowFtToPlant( i, false ); }
   public void setRowFtToPlant( int i, boolean force ) { set( rowft_to_plant, new Integer( i ), force ); }

   public int getPlantsToStart() { return get( PROP_PLANTS_START, new Integer( -1 )).intValue(); }
   public String getPlantsToStartString() { return getAsString( PROP_PLANTS_START ); }
   public void setPlantsToStart( int i ) { setPlantsToStart( i, false ); }
   public void setPlantsToStart( int i, boolean force ) { set( plants_to_start, new Integer( i ), force ); }

   public int getFlatsNeeded() { return get( PROP_FLATS_NEEDED, new Integer( -1 )).intValue(); }
   public String getFlatsNeededString() { return getAsString( PROP_FLATS_NEEDED ); }
   public void setFlatsNeeded( int i ) { setFlatsNeeded( i, false ); }
   public void setFlatsNeeded( int i, boolean force ) { set( flats_needed, new Integer( i ), force ); }
   
   public int getMatAdjust() { return get( PROP_MAT_ADJUST, new Integer( -1 )).intValue(); }
   public String getMatAdjustString() { return getAsString( PROP_MAT_ADJUST ); }
   public void setMatAdjust( int i ) { setMatAdjust( i, false ); }
   public void setMatAdjust( int i, boolean force ) { set( mat_adjust, new Integer( i ), force ); }

   public int getPlantingAdjust() { return get( PROP_PLANTING_ADJUST, new Integer( -1 )).intValue(); }
   public String getPlantingAdjustString() { return getAsString( PROP_PLANTING_ADJUST ); }
   public void setPlantingAdjust( int i ) { setPlantingAdjust( i, false ); }
   public void setPlantingAdjust( int i, boolean force ) { set( planting_adjust, new Integer( i ), force ); }

   public int getTimeToTP() { return get( PROP_TIME_TO_TP, new Integer( -1 )).intValue(); }
   public String getTimeToTPString() { return getAsString( PROP_TIME_TO_TP ); }
   public void setTimeToTP( int i ) { setTimeToTP( i, false ); }
   public void setTimeToTP( int i, boolean force ) { set( time_to_tp, new Integer( i ), force ); }

   public int getMiscAdjust() { return get( PROP_MISC_ADJUST, new Integer( -1 )).intValue(); }
   public String getMiscAdjustString() { return getAsString( PROP_MISC_ADJUST ); }
   public void setMiscAdjust( int i ) { setMiscAdjust( i, false ); }
   public void setMiscAdjust( int i, boolean force ) { set( misc_adjust, new Integer( i ), force ); }

   public int getRowsPerBed() { return get( PROP_ROWS_P_BED, new Integer( -1 )).intValue(); }
   public String getRowsPerBedString() { return getAsString( PROP_ROWS_P_BED ); }
   public void setRowsPerBed( int i ) { setRowsPerBed( i, false ); }
   public void setRowsPerBed( int i, boolean force ) { set( rows_p_bed, new Integer( i ), force ); }

   public float getInRowSpacing() { return get( PROP_INROW_SPACE, new Float( -1.0 )).floatValue(); }
   public String getInRowSpacingString() { return getAsString( PROP_INROW_SPACE ); }
   public void setInRowSpacing( float i ) { setInRowSpacing( i, false ); }
   public void setInRowSpacing( float i, boolean force ) { set( inrow_space, new Float( i ), force ); }

   public float getRowSpacing() { return get( PROP_ROW_SPACE, new Float( -1.0 )).floatValue(); }
   public String getRowSpacingString() { return getAsString( PROP_ROW_SPACE ); }
   public void setRowSpacing( float i ) { setRowSpacing( i, false ); }
   public void setRowSpacing( float i, boolean force ) { set( row_space, new Float( i ), force ); }

   public String getFlatSize() { return get( PROP_FLAT_SIZE, "" ); }
   public void setFlatSize( String i ) { setFlatSize( i, false ); }
   public void setFlatSize( String i, boolean force ) { set( flat_size, "", force ); }

   public String getPlanter() { return get( PROP_PLANTER, "" ); }
   public void setPlanter( String i ) { setPlanter( i, false ); }
   public void setPlanter( String i, boolean force ) { set( planter, i, force ); }

   public String getPlanterSetting() { return get( PROP_PLANTER_SETTING, "" ); }
   public void setPlanterSetting( String i ) { setPlanterSetting( i, false ); }
   public void setPlanterSetting( String i, boolean force ) { set( planter_setting, i, force ); }

   public float getYieldPerFoot() { return get( PROP_YIELD_P_FOOT, new Float( -1.0 )).floatValue(); }
   public String getYieldPerFootString() { return getAsString( PROP_YIELD_P_FOOT ); }
   public void setYieldPerFoot( float i ) { setYieldPerFoot( i, false ); }
   public void setYieldPerFoot( float i, boolean force ) { set( yield_p_foot, new Float( i ), force ); }

   public float getTotalYield() { return get( PROP_TOTAL_YIELD, new Float( -1.0 )).floatValue(); }
   public String getTotalYieldString() { return getAsString( PROP_TOTAL_YIELD ); }
   public void setTotalYield( float i ) { setTotalYield( i, false ); }
   public void setTotalYield( float i, boolean force ) { set( total_yield, new Float( i ), force ); }

   public int getYieldNumWeeks() { return get( PROP_YIELD_NUM_WEEKS, new Integer( -1 )).intValue(); }
   public String getYieldNumWeeksString() { return getAsString( PROP_YIELD_NUM_WEEKS ); }
   public void setYieldNumWeeks( int i ) { setYieldNumWeeks( i, false ); }
   public void setYieldNumWeeks( int i, boolean force ) { set( yield_num_weeks, new Integer( i ), force ); }

   public float getYieldPerWeek() { return get( PROP_YIELD_P_WEEK, new Float( -1.0 )).floatValue(); }
   public String getYieldPerWeekString() { return getAsString( PROP_YIELD_P_WEEK ); }
   public void setYieldPerWeek( float i ) { setYieldPerWeek( i, false ); }
   public void setYieldPerWeek( float i, boolean force ) { set( yield_p_week, new Float( i ), force ); }

   public String getCropYieldUnit() { return get( PROP_CROP_UNIT, "" ); }
   public void setCropYieldUnit( String i ) { setCropYieldUnit( i, false ); }
   public void setCropYieldUnit( String i, boolean force ) { set( crop_unit, i, force ); }

   public float getCropYieldUnitValue() { return get( PROP_CROP_UNIT_VALUE, new Float( -1.0 )).floatValue(); }
   public String getCropYieldUnitValueString() { return getAsString( PROP_CROP_UNIT_VALUE ); }
   public void setCropYieldUnitValue( float i ) { setCropYieldUnitValue( i, false ); }
   public void setCropYieldUnitValue( float i, boolean force ) { set( crop_unit_value, new Float( i ), force ); }
   
   /* *********************************************************************************************/
   /* META METHODS - Operate on entire objects. */
   /* *********************************************************************************************/
   public CPSRecord diff( CPSRecord thatPlanting ) {
      return super.diff( thatPlanting, new CPSPlanting() );
   }
   
   public CPSRecord inheritFrom( CPSRecord thatRecord ) {
      if ( this.getClass().getName().equalsIgnoreCase( thatRecord.getClass().getName() ))
         super.inheritFrom( thatRecord );
      else if ( ! thatRecord.getClass().getName().equalsIgnoreCase( CPSCrop.class.getName() )) {
         System.err.println("ERROR: CPSPlanting can only inherit from itself and from CPSCrop, " +
                            "not from " + thatRecord.getClass().getName() );
         return this;
      }
      // else thatRecord is a CPSCrop
      CPSCrop thatCrop = (CPSCrop) thatRecord;
      
      for ( Integer integer : changedProps ) {

      }


      
      CPSDatum thisDat = this.getDatum( PROP_MATURITY );
      CPSDatum thatDat = thatCrop.getDatum( thatCrop.PROP_MATURITY );
      if ( ! thisDat.isValid() && thatDat.isValid() )
         this.inherit( PROP_MATURITY, thatDat.getDatum() );
      else
         System.out.println( "NOT INHERITING maturity days" );
         
      return this;
   }
   
   /* *********************************************************************************************/
   /* UTILITY METHODS - Nuts and bolts stuff that's not too exciting */
   /* *********************************************************************************************/
   
   public PlantingIterator iterator() { return new PlantingIterator(); }
   
   public class PlantingIterator extends CPSRecordIterator {
       
      public  boolean ignoreThisProperty() {
         return this.currentProp == PROP_ID || this.currentProp == PROP_CROP_ID;
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
