/* CPSPlanting.java
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

/**
 * Planting - information about a particular planting of a crop and/or
 *            variety
 */

import CPS.Data.CPSDatum.CPSDatumState;
import CPS.Module.CPSDataModelConstants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class CPSPlanting extends CPSRecord {

   // Unimplemented fields:
//      s += "plant_mtd_id  INTEGER, "; // References PLANTING_METHODS.id
//      s += "successions BOOLEAN, ";
//      s += "fudge     FLOAT, ";

//   public final int PROP_CROP_ID       = CPSDataModelConstants.PROP_CROP_ID;
   public final int PROP_CROP_NAME     = CPSDataModelConstants.PROP_CROP_NAME;
   public final int PROP_VAR_NAME      = CPSDataModelConstants.PROP_VAR_NAME;
   public final int PROP_GROUPS        = CPSDataModelConstants.PROP_GROUPS;
   public final int PROP_LOCATION      = CPSDataModelConstants.PROP_LOCATION;
   public final int PROP_KEYWORDS      = CPSDataModelConstants.PROP_KEYWORDS;
   public final int PROP_STATUS        = CPSDataModelConstants.PROP_STATUS;
   public final int PROP_DONE_PLANTING = CPSDataModelConstants.PROP_DONE_PLANTING;
   public final int PROP_DONE_TP       = CPSDataModelConstants.PROP_DONE_TP;
   public final int PROP_DONE_HARVEST  = CPSDataModelConstants.PROP_DONE_HARVEST;
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
   
   public final int PROP_CUSTOM1       = CPSDataModelConstants.PROP_CUSTOM1;
   public final int PROP_CUSTOM2       = CPSDataModelConstants.PROP_CUSTOM2;
   public final int PROP_CUSTOM3       = CPSDataModelConstants.PROP_CUSTOM3;
   public final int PROP_CUSTOM4       = CPSDataModelConstants.PROP_CUSTOM4;
   public final int PROP_CUSTOM5       = CPSDataModelConstants.PROP_CUSTOM5;
   
   /* from CPSDataModelConstants: this is the highest value defined there */
   protected int lastValidProperty() { return PROP_CUSTOM5; }
   
//   private final int CONST_BED_LENGTH = 100;
   private final double CONST_FUDGE = .25;
   
//   private CPSDatum<Integer> plantingID;
//   private CPSDatum<Integer> cropID;
   private CPSDatum<String> crop_name;
   private CPSDatum<String> var_name;
   private CPSDatum<String> groups;
   private CPSDatum<String> location;
   private CPSDatum<String> keywords;
   private CPSDatum<String> status;
   private CPSDatum<Boolean> done_plant;
   private CPSDatum<Boolean> done_tp;
   private CPSDatum<Boolean> done_harvest;
   private CPSDatum<String> other_req;
   private CPSDatum<String> notes;
   private CPSDatum<Integer> maturity;
   private CPSDatum<Date> date_plant;
   private CPSDatum<Date> date_tp;
   private CPSDatum<Date> date_harvest;
   private CPSDatum<Float> beds_to_plant; //LATER convert to float
   private CPSDatum<Integer> plants_needed;
   private CPSDatum<Integer> rowft_to_plant;
   private CPSDatum<Integer> plants_to_start;
   private CPSDatum<Float> flats_needed; //LATER convert to float
   
   private CPSDatum<Integer> mat_adjust;
   private CPSDatum<Integer> planting_adjust;
   private CPSDatum<Integer> time_to_tp;
   private CPSDatum<Integer> misc_adjust;
   private CPSDatum<Integer> rows_p_bed;
   private CPSDatum<Integer> inrow_space;
   private CPSDatum<Integer> row_space;
   private CPSDatum<String> flat_size;
   private CPSDatum<String> planter;
   private CPSDatum<String> planter_setting;
   private CPSDatum<Float> yield_p_foot;
   private CPSDatum<Float> total_yield;
   private CPSDatum<Integer> yield_num_weeks;
   private CPSDatum<Float> yield_p_week;
   private CPSDatum<String> crop_unit;
   private CPSDatum<Float> crop_unit_value;

   private CPSDatum<String> custom1;
   private CPSDatum<String> custom2;
   private CPSDatum<String> custom3;
   private CPSDatum<String> custom4;
   private CPSDatum<String> custom5;
   
   private CPSDateValidator dateValidator;
   
   public CPSPlanting() {
      
      recordID = new CPSDatum<Integer>( "Unique ID", PROP_ID, "id", new Integer(-1) );
//      cropID = new CPSDatum<Integer>( "Unique ID of Crop", PROP_CROP_ID, "crop_id", new Integer(-1));
      commonIDs = new CPSDatum<ArrayList<Integer>>( "Crop IDs represented", PROP_COMMON_ID, "column_DNE", new ArrayList() );
       
      
      crop_name = new CPSDatum<String>( "Crop name", PROP_CROP_NAME, "crop_name", "" );
      var_name = new CPSDatum<String>( "Variety name", PROP_VAR_NAME, "var_name", "" );
      groups = new CPSDatum<String>( "Groups", PROP_GROUPS, "groups", "" );
      location = new CPSDatum<String>( "Location", PROP_LOCATION, "location", "" );
      keywords = new CPSDatum<String>( "Keywords", PROP_KEYWORDS, "keywords", "" );
      status = new CPSDatum<String>( "Status", PROP_STATUS, "status", "" );
      done_plant = new CPSDatum<Boolean>( "Done Planting?", PROP_DONE_PLANTING, "done_plant", new Boolean( false ));
      done_tp = new CPSDatum<Boolean>( "Done Transplanting?", PROP_DONE_TP, "done_tp", new Boolean( false ));
      done_harvest = new CPSDatum<Boolean>( "Done Harvesting?", PROP_DONE_HARVEST, "done_harvest", new Boolean( false ));
      other_req = new CPSDatum<String>( "Other Requirements", PROP_OTHER_REQ, "other_req", "" );
      notes = new CPSDatum<String>( "Notes", PROP_NOTES, "notes", "" );
      maturity = new CPSDatum<Integer>( "Maturity Days", PROP_MATURITY, "maturity", new Integer(-1));
      date_plant = new CPSDatum<Date>( "Planting Date", PROP_DATE_PLANT, "date_plant", new Date(0) );
      date_tp = new CPSDatum<Date>( "Transplant Date", PROP_DATE_TP, "date_tp", new Date(0) );
      date_harvest = new CPSDatum<Date>( "Harvest Date", PROP_DATE_HARVEST, "date_harvest", new Date(0) );
      beds_to_plant = new CPSDatum<Float>( "Num. Beds to Plants", PROP_BEDS_PLANT, "beds_to_plant", new Float(-1.0) );
      plants_needed = new CPSDatum<Integer>( "Num. Plants Needed", PROP_PLANTS_NEEDED, "plants_needed", new Integer(-1) );
      rowft_to_plant = new CPSDatum<Integer>( "Row Feet To Plant", PROP_ROWFT_PLANT, "rowft_to_plant", new Integer(-1) );
      plants_to_start = new CPSDatum<Integer>( "Num. Plants to Start", PROP_PLANTS_START, "plants_to_start", new Integer(-1) );
      flats_needed = new CPSDatum<Float>( "Num. Flats Needed", PROP_FLATS_NEEDED, "flats_needed", new Float(-1.0) );
      
      mat_adjust = new CPSDatum<Integer>( "Mat. Adjustment", PROP_MAT_ADJUST, "mat_adjust", new Integer(-1) );
      planting_adjust = new CPSDatum<Integer>( "Add. Adjustment to Mat.", PROP_PLANTING_ADJUST, "planting_adjust", new Integer(-1) );
      time_to_tp = new CPSDatum<Integer>( "Weeks to TP", PROP_TIME_TO_TP, "time_to_tp", new Integer( -1 ) );
      misc_adjust = new CPSDatum<Integer>( "Misc. Adjustment to Mat.", PROP_MISC_ADJUST, "misc_adjust", new Integer( -1 ) );
      rows_p_bed = new CPSDatum<Integer>( "Rows/Bed", PROP_ROWS_P_BED, "rows_p_bed", new Integer(-1) );
      inrow_space = new CPSDatum<Integer>( "In-row Spacing", PROP_INROW_SPACE, "inrow_space", new Integer( -1 ) );
      row_space = new CPSDatum<Integer>( "Row Spacing", PROP_ROW_SPACE, "row_space", new Integer( -1 ) );
      flat_size = new CPSDatum<String>( "Flat size", PROP_FLAT_SIZE, "flat_size", "" );
      planter = new CPSDatum<String>( "Planter", PROP_PLANTER, "planter", "" );
      planter_setting = new CPSDatum<String>( "Planter setting", PROP_PLANTER_SETTING, "planter_setting", "" );
      yield_p_foot = new CPSDatum<Float>( "Yield/Ft", PROP_YIELD_P_FOOT, "yield_p_foot", new Float(-1.0) );
      total_yield = new CPSDatum<Float>( "Total Yield", PROP_TOTAL_YIELD, "total_yield", new Float(-1.0) );
      yield_num_weeks = new CPSDatum<Integer>( "Will Yield for (weeks)", PROP_YIELD_NUM_WEEKS, "yield_num_weeks", new Integer(-1) );
      yield_p_week = new CPSDatum<Float>( "Yield/Week", PROP_YIELD_P_WEEK, "yield_p_week", new Float(-1.0));
      crop_unit = new CPSDatum<String>( "Unit of Yield", PROP_CROP_UNIT, "crop_unit", "" );
      crop_unit_value = new CPSDatum<Float>( "Value per Yield Unit", PROP_CROP_UNIT_VALUE, "crop_unit_value", new Float(-1.0));

      custom1 = new CPSDatum<String>( "Custom Field 1", PROP_CUSTOM1, "custom1", "" );
      custom2 = new CPSDatum<String>( "Custom Field 2", PROP_CUSTOM2, "custom2", "" );
      custom3 = new CPSDatum<String>( "Custom Field 3", PROP_CUSTOM3, "custom3", "" );
      custom4 = new CPSDatum<String>( "Custom Field 4", PROP_CUSTOM4, "custom4", "" );
      custom5 = new CPSDatum<String>( "Custom Field 5", PROP_CUSTOM5, "custom5", "" );
      
      dateValidator = new CPSDateValidator();
      
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
          case PROP_DONE_PLANTING: return done_plant;
          case PROP_DONE_TP:       return done_tp;
          case PROP_DONE_HARVEST:  return done_harvest;
          case PROP_OTHER_REQ:     return other_req;
          case PROP_NOTES:         return notes;
          case PROP_MATURITY:      return maturity;
          case PROP_DATE_PLANT:    return date_plant;
          case PROP_DATE_TP:       return date_tp;
          case PROP_DATE_HARVEST:  return date_harvest;
          case PROP_BEDS_PLANT:    return beds_to_plant;
          case PROP_PLANTS_NEEDED: return plants_needed;
          case PROP_ROWFT_PLANT:   return rowft_to_plant;
          case PROP_PLANTS_START:  return plants_to_start;
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
          
          case PROP_CUSTOM1:       return custom1;
          case PROP_CUSTOM2:       return custom2;
          case PROP_CUSTOM3:       return custom3;
          case PROP_CUSTOM4:       return custom4;
          case PROP_CUSTOM5:       return custom5;
          
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
//   public int getCropID() { return get( PROP_CROP_ID, new Integer( -1 ) ); }
//   public void setCropID( int i ) { set( cropID, i, false ); }
   
   public String getCropName() { return get(PROP_CROP_NAME, ""); }
   public CPSDatumState getCropNameState() { return getStateOf( PROP_CROP_NAME ); }
   public void setCropName(String s) { setCropName( s, false ); }
   public void setCropName( String s, boolean force ) { set( crop_name, s, force ); }

   public String getVarietyName() { return get( PROP_VAR_NAME, "" ); }
   public CPSDatumState getVarietyNameState() { return getStateOf( PROP_VAR_NAME ); }
   public void setVarietyName( String s ) { setVarietyName( s, false ); }
   public void setVarietyName( String s, boolean force ) { set( var_name, s, force ); }

   public String getGroups() { return get( PROP_GROUPS, "" ); }
   public CPSDatumState getGroupsState() { return getStateOf( PROP_GROUPS ); }
   public void setGroups( String e) { setGroups( e, false ); }
   public void setGroups( String e, boolean force ) { set( groups, e, force ); }

   public String getLocation() { return get(  PROP_LOCATION, "" ); }
   public CPSDatumState getLocationState() { return getStateOf( PROP_LOCATION ); }
   public void setLocation( String s ) { setLocation( s, false ); }
   public void setLocation( String s, boolean force ) { set( location, s, force ); }

   public String getKeywords() { return get( PROP_KEYWORDS, "" ); }
   public CPSDatumState getKeywordsState() { return getStateOf( PROP_KEYWORDS ); }
   public void setKeywords( String e) { setKeywords( e, false ); }
   public void setKeywords( String e, boolean force ) { set( keywords, e, force ); }

   public String getStatus() { return get( PROP_STATUS, "" ); }
   public CPSDatumState getStatusState() { return getStateOf( PROP_STATUS ); }
   public void setStatus( String e) { setStatus( e, false ); }
   public void setStatus( String e, boolean force ) { set( status, e, force ); }

   public Boolean getDonePlanting() { return get( PROP_DONE_PLANTING, new Boolean( false )).booleanValue(); }
   public CPSDatumState getDonePlantingStrate() { return getStateOf( PROP_DONE_PLANTING  ); }
   public void setDonePlanting( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDonePlanting( true );
      else
         setDonePlanting( false );
   }
   public void setDonePlanting( Boolean b ) { setDonePlanting( b, false ); }
   public void setDonePlanting( Boolean b, boolean force ) { set( done_plant, b, force ); }

   public Boolean getDoneTP() { return get( PROP_DONE_PLANTING, new Boolean( false )).booleanValue(); }
   public CPSDatumState getDoneTPStrate() { return getStateOf( PROP_DONE_PLANTING  ); }
   public void setDoneTP( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDoneTP( true );
      else
         setDoneTP( false );
   }
   public void setDoneTP( Boolean b ) { setDoneTP( b, false ); }
   public void setDoneTP( Boolean b, boolean force ) { set( done_plant, b, force ); }

   public Boolean getDoneHarvest() { return get( PROP_DONE_PLANTING, new Boolean( false )).booleanValue(); }
   public CPSDatumState getDoneHarvestStrate() { return getStateOf( PROP_DONE_PLANTING  ); }
   public void setDoneHarvest( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDoneHarvest( true );
      else
         setDoneHarvest( false );
   }
   public void setDoneHarvest( Boolean b ) { setDoneHarvest( b, false ); }
   public void setDoneHarvest( Boolean b, boolean force ) { set( done_plant, b, force ); }

   public String getOtherRequirments() { return get( PROP_OTHER_REQ, "" ); }
   public CPSDatumState getOtherRequirmentsState() { return getStateOf( PROP_OTHER_REQ ); }
   public void setOtherRequirements( String e) { setOtherRequirements( e, false ); }
   public void setOtherRequirements( String e, boolean force ) { set( other_req, e, force ); }

   public String getNotes() { return get( PROP_NOTES, "" ); }
   public CPSDatumState getNotesState() { return getStateOf( PROP_NOTES ); }
   public void setNotes( String e) { setNotes( e, false ); }
   public void setNotes( String e, boolean force ) { set( notes, e, force ); }

   public int getMaturityDays() { return getInt( PROP_MATURITY ); }
   public String getMaturityDaysString() { return formatInt( getMaturityDays() ); }
   public CPSDatumState getMaturityDaysState() { return getStateOf( PROP_MATURITY ); }
   public void setMaturityDays( int i ) { setMaturityDays( i, false ); }
   public void setMaturityDays( int i, boolean force ) { set( maturity, new Integer( i ), force ); }
   public void setMaturityDays( String s ) { setMaturityDays( s, false ); }
   public void setMaturityDays( String s, boolean force ) { setMaturityDays( parseInt( s ), force ); }
   
//          case PROP_DATE_PLANT:    return date_plant;
   public Date getDateToPlant() {
       CPSDatum p = getDatum( PROP_DATE_PLANT );
       CPSDatum m = getDatum( PROP_MATURITY );
       CPSDatum h = getDatum( PROP_DATE_HARVEST );
       CPSDatum t = getDatum( PROP_DATE_TP );
       CPSDatum w = getDatum( PROP_TIME_TO_TP );
       CPSDatum a = getDatum( PROP_MAT_ADJUST );
       
       /* Only calculate the planting date if:
        * DATE_PLANT is *NOT* valid AND
        * DATE_HARVEST AND MATURITY *ARE* valid 
        * otherwise just return the planting date or a default */
       if ( this.isSingleRecord() &&
            ! p.isValid() && 
              t.isValid() && w.isValid() ) {
          getDatum( PROP_DATE_PLANT ).setCalculated( true );
          return CPSCalculations.calcDatePlantFromDateTP( getDateToTP(), getTimeToTP() );
       }
       else if ( this.isSingleRecord() &&
                 ! p.isValid() && 
                   h.isValid() && m.isValid() ) {
          getDatum( PROP_DATE_PLANT ).setCalculated( true );
          int matAdjust = getMatAdjust();
          if ( matAdjust == -1 )
              matAdjust = 0;
          int mat = getMaturityDays();
          if ( w.isValid() )
              mat += getTimeToTP() * 7;
          return CPSCalculations.calcDatePlantFromDateHarvest( getDateToHarvest(), 
                                                               mat,
                                                               matAdjust );
       }
       else
           return get( PROP_DATE_PLANT, new Date(0) ); 
   }
   public String getDateToPlantString() { return formatDate( getDateToPlant() ); }
   public CPSDatumState getDateToPlantState() { return getStateOf( PROP_DATE_PLANT ); }
   public void setDateToPlant( Date d ) { setDateToPlant( d, false ); }
   public void setDateToPlant( String d ) { setDateToPlant( parseDate(d), false ); }
   public void setDateToPlant( Date d, boolean force ) { set( date_plant, d, force ); }
   public void setDateToPlant( String d, boolean force ) { setDateToPlant( parseDate(d), force ); }
   
//          case PROP_DATE_TP:       return date_tp;
   public Date getDateToTP() { 
      CPSDatum p = getDatum( PROP_DATE_PLANT );
      CPSDatum t = getDatum( PROP_DATE_TP );
      CPSDatum h = getDatum( PROP_DATE_HARVEST );
      CPSDatum w = getDatum( PROP_TIME_TO_TP );
      CPSDatum m = getDatum( PROP_MATURITY );
      
      /* If DATE_TP valid, return
       * If DATE_PLANT valid
       *   If TIME_TP valid, add TIME_TP to DATE_PLANT
       *   Else return null
       * Else return null
       * LATER throw DATE_HARVEST into the mix
       */
      if ( this.isSingleRecord() &&
           ! t.isValid() &&
             p.isValid() && w.isValid() ) {
         getDatum( PROP_DATE_TP ).setCalculated( true );
         return CPSCalculations.calcDateTPFromDatePlant( getDateToPlant(), getTimeToTP() );
      }
      else if ( this.isSingleRecord() &&
                ! t.isValid() && 
                  w.isValid() &&
                  h.isValid() && m.isValid() ) {
         getDatum( PROP_DATE_TP ).setCalculated( true );
         int matAdjust = getMatAdjust();
         if ( matAdjust == -1 )
             matAdjust = 0;
         return CPSCalculations.calcDateTPFromDateHarvest( getDateToHarvest(),
                                                           getMaturityDays(),
                                                           matAdjust );
      }
      else
         return get( PROP_DATE_TP, new Date(0) );
   }
   public String getDateToTPString() { return formatDate( getDateToTP() ); }
   public CPSDatumState getDateToTPState() { return getStateOf( PROP_DATE_TP ); }
   public void setDateToTP( Date d ) { setDateToTP( d, false ); }
   public void setDateToTP( String d ) { setDateToTP( parseDate( d ), false ); }
   public void setDateToTP( Date d, boolean force ) { set( date_tp, d, force ); }
   public void setDateToTP( String d, boolean force ) { setDateToTP( parseDate( d ), force ); }
   
   public Date getDateToHarvest() {
      CPSDatum h = getDatum( PROP_DATE_HARVEST );
      CPSDatum p = getDatum( PROP_DATE_PLANT );
      CPSDatum m = getDatum( PROP_MATURITY );
      CPSDatum t = getDatum( PROP_DATE_TP );
      CPSDatum w = getDatum( PROP_TIME_TO_TP );
      
      
       /* Only calculate the harvest date if:
        * DATE_HARVEST is *NOT* valid AND
        * DATE_PLANTING AND MATURITY *ARE* valid 
        * otherwise just return the harvest date or a default */
       if ( this.isSingleRecord() &&
            ! h.isValid() && 
              t.isValid() && m.isValid() && w.isValid() ) {
          getDatum( PROP_DATE_HARVEST ).setCalculated( true );
          int matAdjust = getMatAdjust();
          if ( matAdjust == -1 )
              matAdjust = 0;
          return CPSCalculations.calcDateHarvestFromDateTP( getDateToTP(), 
                                                            getMaturityDays(),
                                                            matAdjust );
       }
       else if ( this.isSingleRecord() &&
                 ! h.isValid() && 
                   p.isValid() && m.isValid() ) {
           getDatum( PROP_DATE_HARVEST ).setCalculated( true );
           int matAdjust = getMatAdjust();
           if ( matAdjust == -1 )
               matAdjust = 0;
           int mat = getMaturityDays();
           if ( w.isValid() )
               mat += getTimeToTP() * 7;
           return CPSCalculations.calcDateHarvestFromDatePlant( getDateToPlant(), 
                                                                mat, 
                                                                matAdjust );
       }
       else
           return get(PROP_DATE_HARVEST, new Date(0));
   }
   public String getDateToHarvestString() { return formatDate( getDateToHarvest() ); }
   public CPSDatumState getDateToHarvestState() { return getStateOf( PROP_DATE_HARVEST ); }
   public void setDateToHarvest( Date d ) { setDateToHarvest( d, false ); }
   public void setDateToHarvest( Date d, boolean force ) { set( date_harvest, d, force ); }
   public void setDateToHarvest( String d ) { setDateToHarvest( parseDate( d ), false ); }
   public void setDateToHarvest( String d, boolean force ) { setDateToHarvest( parseDate( d ), force ); }
   
   public float getBedsToPlant() {
      CPSDatum b = getDatum( PROP_BEDS_PLANT );
      CPSDatum r = getDatum( PROP_ROWFT_PLANT );
      CPSDatum rpb = getDatum( PROP_ROWS_P_BED );
      CPSDatum p = getDatum( PROP_PLANTS_NEEDED );
      CPSDatum ps = getDatum( PROP_INROW_SPACE );
      CPSDatum ty = getDatum( PROP_TOTAL_YIELD );
      CPSDatum yf = getDatum( PROP_YIELD_P_FOOT );
      
      /* we don't care if location is valid, this will return the default 
       * bed length if location is inValid */
      int bedLength = CPSCalculations.extractBedLength( getLocation() );
      
      /* if BEDS_PLANT valid, return
       * if ROWFT_PLANT and ROWS_P_BED valid
       * if PLANTS_NEEDED, ROWS_P_BED and INROW_SPACE valid 
       */
      if ( ! b.isValid() &&
             r.isAvailable() && rpb.isAvailable() ) {
         getDatum( PROP_BEDS_PLANT ).setCalculated( true );
         return CPSCalculations.calcBedsToPlantFromRowFtToPlant( getRowFtToPlant(),
                                                                 getRowsPerBed(),
                                                                 bedLength );
      }
      else if ( ! b.isValid() && 
                  p.isValid() && ps.isValid() && rpb.isValid() ) {
         getDatum( PROP_BEDS_PLANT ).setCalculated( true );
         return CPSCalculations.calcBedsToPlantFromPlantsNeeded( getPlantsNeeded(),
                                                                 getInRowSpacing(),
                                                                 getRowsPerBed(),
                                                                 bedLength );
      }
      else if ( ! b.isValid() &&
                  ty.isValid() && yf.isValid() &&
                  rpb.isValid() ) {
          getDatum( PROP_BEDS_PLANT ).setCalculated( true );
         return CPSCalculations.calcBedsToPlantFromTotalYield( getTotalYield(),
                                                               getYieldPerFoot(),
                                                               getRowsPerBed(),
                                                               bedLength );
      }
      else
         return get( PROP_BEDS_PLANT, new Float( -1.0 ) ).floatValue(); 
   }
   public String getBedsToPlantString() { return formatFloat( getBedsToPlant() ); }
   public CPSDatumState getBedsToPlantState() { return getStateOf( PROP_BEDS_PLANT ); }
   public void setBedsToPlant( float i ) { setBedsToPlant( i, false ); }
   public void setBedsToPlant( float i, boolean force ) { set( beds_to_plant, new Float( i ), force ); }
   public void setBedsToPlant( String s ) { setBedsToPlant( s, false ); }
   public void setBedsToPlant( String s, boolean force ) { setBedsToPlant( parseFloat(s), force ); }
   
   public int getPlantsNeeded() {
      CPSDatum p = getDatum( PROP_PLANTS_NEEDED );
      CPSDatum ps = getDatum( PROP_INROW_SPACE );
      CPSDatum b = getDatum( PROP_BEDS_PLANT );
      CPSDatum r = getDatum( PROP_ROWFT_PLANT );
      CPSDatum rpb = getDatum( PROP_ROWS_P_BED );
      CPSDatum ty = getDatum( PROP_TOTAL_YIELD );
      CPSDatum yf = getDatum( PROP_YIELD_P_FOOT );
      
      /* if PLANTS_NEEDED valid, return
       * if BEDS_PLANT, ROWS_P_BED, INROW_SPACE valid
       * if ROWFT_PLANT and INROW_SPACE valid 
       */
      if ( ! p.isValid() &&
             b.isValid() && rpb.isValid() && ps.isValid() ) {
         getDatum( PROP_PLANTS_NEEDED ).setCalculated( true );
          /* we don't care if location is valid, this will return the default 
           * bed length if location is inValid */
          int bedLength = CPSCalculations.extractBedLength( getLocation() );
      
         return CPSCalculations.calcPlantsNeededFromBedsToPlant( getBedsToPlant(), 
                                                                 getInRowSpacing(),
                                                                 getRowsPerBed(),
                                                                 bedLength );
      }
      else if ( ! p.isValid() && 
                  r.isValid() && ps.isValid() ) {
         getDatum( PROP_PLANTS_NEEDED ).setCalculated( true );
         return CPSCalculations.calcPlantsNeededFromRowFtToPlant( getRowFtToPlant(), getInRowSpacing() );
      }
      else if ( ! p.isValid() &&
                  ty.isValid() && yf.isValid() && ps.isValid() ) {
          getDatum( PROP_PLANTS_NEEDED ).setCalculated( true );
         return CPSCalculations.calcPlantsNeededFromTotalYield( getTotalYield(),
                                                                getYieldPerFoot(),
                                                                getInRowSpacing() );
      }
      else
         return get( PROP_PLANTS_NEEDED, new Integer( -1 ) ).intValue();
   }
   public String getPlantsNeededString() { return formatInt( getPlantsNeeded() ); }
   public CPSDatumState getPlantsNeededState() { return getStateOf( PROP_PLANTS_NEEDED ); }
   public void setPlantsNeeded( int i ) { setPlantsNeeded( i, false ); }
   public void setPlantsNeeded( int i, boolean force ) { set( plants_needed, new Integer( i ), force ); }
   public void setPlantsNeeded( String s ) { setPlantsNeeded( s, false ); }
   public void setPlantsNeeded( String s, boolean force ) { setPlantsNeeded( parseInt(s), force ); }
   
   public int getRowFtToPlant() { 
      CPSDatum r = getDatum( PROP_ROWFT_PLANT );
      CPSDatum rpb = getDatum( PROP_ROWS_P_BED );
      CPSDatum p = getDatum( PROP_PLANTS_NEEDED );
      CPSDatum ps = getDatum( PROP_INROW_SPACE );
      CPSDatum b = getDatum( PROP_BEDS_PLANT );
      CPSDatum ty = getDatum( PROP_TOTAL_YIELD );
      CPSDatum yf = getDatum( PROP_YIELD_P_FOOT );
      
      /* if ROWFT_PLANT valid, return
       * if BEDS_PLANT and ROWS_P_BED valid
       * if PLANTS_NEEDED and INROW_SPACE valid 
       */
      if ( ! r.isValid() &&
             b.isValid() && rpb.isValid() ) {
         getDatum( PROP_ROWFT_PLANT ).setCalculated( true );
          /* we don't care if location is valid, this will return the default 
           * bed length if location is inValid */
          int bedLength = CPSCalculations.extractBedLength( getLocation() );
         return CPSCalculations.calcRowFtToPlantFromBedsToPlant( getBedsToPlant(),
                                                                 getRowsPerBed(),
                                                                 bedLength );
      }
      else if ( ! r.isValid() && 
                  p.isValid() && ps.isValid() ) {
         getDatum( PROP_ROWFT_PLANT ).setCalculated( true );
         return CPSCalculations.calcRowFtToPlantFromPlantsNeeded( getPlantsNeeded(),
                                                                  getInRowSpacing() );
      }
      else if ( ! r.isValid() && 
                  ty.isValid() && yf.isValid() ) {
         getDatum( PROP_ROWFT_PLANT ).setCalculated( true );
         return CPSCalculations.calcRowFtToPlantFromTotalYield( getTotalYield(),
                                                                getYieldPerFoot() ); 
      }
      else
         return get( PROP_ROWFT_PLANT, new Integer( -1 )).intValue();
   }
   public String getRowFtToPlantString() { return formatInt( getRowFtToPlant() ); }
   public CPSDatumState getRowFtToPlantState() { return getStateOf( PROP_ROWFT_PLANT ); }
   public void setRowFtToPlant( int i ) { setRowFtToPlant( i, false ); }
   public void setRowFtToPlant( int i, boolean force ) { set( rowft_to_plant, new Integer( i ), force ); }
   public void setRowFtToPlant( String s ) { setRowFtToPlant( s, false ); }
   public void setRowFtToPlant( String s, boolean force ) { setRowFtToPlant( parseInt(s), force ); }

   public CPSDatumState getPlantsToStartState() { return getStateOf( PROP_PLANTS_START ); }
   public int getPlantsToStart() {
      CPSDatum s = getDatum( PROP_PLANTS_START );
      CPSDatum n = getDatum( PROP_PLANTS_NEEDED );
      
      /* if PLANTS_START valid, return
       * if PLANTS_NEEDED valid 
       */
      if ( ! s.isValid() && n.isAvailable() ) {
         getDatum( PROP_PLANTS_START ).setCalculated( true );
         return CPSCalculations.calcPlantsToStart( getPlantsNeeded(), CONST_FUDGE );
      }
      else
         return get( PROP_PLANTS_START, new Integer( -1 ) ).intValue();
   }
   public String getPlantsToStartString() { return formatInt( getPlantsToStart() ); }
   public void setPlantsToStart( int i ) { setPlantsToStart( i, false ); }
   public void setPlantsToStart( int i, boolean force ) { set( plants_to_start, new Integer( i ), force ); }
   public void setPlantsToStart( String s ) { setPlantsToStart( s, false ); }
   public void setPlantsToStart( String s, boolean force ) { setPlantsToStart( parseInt(s), force ); }

   public float getFlatsNeeded() {
      CPSDatum n = getDatum( PROP_FLATS_NEEDED );
      CPSDatum p = getDatum( PROP_PLANTS_START );
      CPSDatum s = getDatum( PROP_FLAT_SIZE );
      
      if ( ! n.isValid() && 
             p.isAvailable() && s.isAvailable() ) {
         getDatum( PROP_FLATS_NEEDED ).setCalculated( true );
         // TODO Math.ceil()
         return CPSCalculations.calcFlatsNeeded( getPlantsToStart(),
                                                       getFlatSizeCapacity() );
      }
      return get( PROP_FLATS_NEEDED, new Float( -1.0 )).floatValue(); 
   }
   public String getFlatsNeededString() { return formatFloat( getFlatsNeeded() ); }
   public CPSDatumState getFlatsNeededState() { return getStateOf( PROP_FLATS_NEEDED ); }
   public void setFlatsNeeded( float i ) { setFlatsNeeded( i, false ); }
   public void setFlatsNeeded( float i, boolean force ) { set( flats_needed, new Float( i ), force ); }
   public void setFlatsNeeded( String s ) { setFlatsNeeded( s, false ); }
   public void setFlatsNeeded( String s, boolean force ) { setFlatsNeeded( parseFloat(s), force ); }
   
   public int getMatAdjust() { return get( PROP_MAT_ADJUST, new Integer( -1 )).intValue(); }
   public String getMatAdjustString() { return formatInt( getMatAdjust() ); }
   public CPSDatumState getMatAdjustState() { return getStateOf( PROP_MAT_ADJUST ); }
   public void setMatAdjust( int i ) { setMatAdjust( i, false ); }
   public void setMatAdjust( int i, boolean force ) { set( mat_adjust, new Integer( i ), force ); }
   public void setMatAdjust( String s ) { setMatAdjust( s, false ); }
   public void setMatAdjust( String s, boolean force ) { setMatAdjust( parseInt(s), force ); }

   public int getPlantingAdjust() { return get( PROP_PLANTING_ADJUST, new Integer( -1 )).intValue(); }
   public String getPlantingAdjustString() { return formatInt( getPlantingAdjust() ); }
   public CPSDatumState getPlantingAdjustState() { return getStateOf( PROP_PLANTING_ADJUST ); }
   public void setPlantingAdjust( int i ) { setPlantingAdjust( i, false ); }
   public void setPlantingAdjust( int i, boolean force ) { set( planting_adjust, new Integer( i ), force ); }
   public void setPlantingAdjust( String s ) { setPlantingAdjust( s, false ); }
   public void setPlantingAdjust( String s, boolean force ) { setPlantingAdjust( parseInt(s), force ); }

   public int getTimeToTP() { return get( PROP_TIME_TO_TP, new Integer( -1 )).intValue(); }
   public String getTimeToTPString() { return formatInt( getTimeToTP() ); }
   public CPSDatumState getTimeToTPState() { return getStateOf( PROP_TIME_TO_TP ); }
   public void setTimeToTP( int i ) { setTimeToTP( i, false ); }
   public void setTimeToTP( int i, boolean force ) { set( time_to_tp, new Integer( i ), force ); }
   public void setTimeToTP( String s ) { setTimeToTP( s, false ); }
   public void setTimeToTP( String s, boolean force ) { setTimeToTP( parseInt(s), force ); }

   public int getMiscAdjust() { return get( PROP_MISC_ADJUST, new Integer( -1 )).intValue(); }
   public String getMiscAdjustString() { return formatInt( getMiscAdjust() ); }
   public CPSDatumState getMiscAdjustState() { return getStateOf( PROP_MISC_ADJUST ); }
   public void setMiscAdjust( int i ) { setMiscAdjust( i, false ); }
   public void setMiscAdjust( int i, boolean force ) { set( misc_adjust, new Integer( i ), force ); }
   public void setMiscAdjust( String s ) { setMiscAdjust( s, false ); }
   public void setMiscAdjust( String s, boolean force ) { setMiscAdjust( parseInt(s), force ); }

   public CPSDatumState getRowsPerBedState() { return getStateOf( PROP_ROWS_P_BED ); }
   public int getRowsPerBed() { return get( PROP_ROWS_P_BED, new Integer( -1 )).intValue(); }
   public String getRowsPerBedString() { return formatInt( getRowsPerBed() ); }
   public void setRowsPerBed( int i ) { setRowsPerBed( i, false ); }
   public void setRowsPerBed( int i, boolean force ) { set( rows_p_bed, new Integer( i ), force ); }
   public void setRowsPerBed( String s ) { setRowsPerBed( s, false ); }
   public void setRowsPerBed( String s, boolean force ) { setRowsPerBed( parseInt(s), force ); }

   public int getInRowSpacing() { return get( PROP_INROW_SPACE, new Integer( -1 )).intValue(); }
   public String getInRowSpacingString() { return formatInt( getInRowSpacing() ); }
   public CPSDatumState getInRowSpacingState() { return getStateOf( PROP_INROW_SPACE ); }
   public void setInRowSpacing( int i ) { setInRowSpacing( i, false ); }
   public void setInRowSpacing( int i, boolean force ) { set( inrow_space, new Integer( i ), force ); }
   public void setInRowSpacing( String s ) { setInRowSpacing( s, false ); }
   public void setInRowSpacing( String s, boolean force ) { setInRowSpacing( parseInt(s), force ); }

   public int getRowSpacing() { return get( PROP_ROW_SPACE, new Integer( -1 )).intValue(); }
   public String getRowSpacingString() { return formatInt( getRowSpacing() ); }
   public CPSDatumState getRowSpacingState() { return getStateOf( PROP_ROW_SPACE ); }
   public void setRowSpacing( int i ) { setRowSpacing( i, false ); }
   public void setRowSpacing( int i, boolean force ) { set( row_space, new Integer( i ), force ); }
   public void setRowSpacing( String s ) { setRowSpacing( s, false ); }
   public void setRowSpacing( String s, boolean force ) { setRowSpacing( parseInt(s), force ); }

   public String getFlatSize() { return get( PROP_FLAT_SIZE, "" ); }
   public int getFlatSizeCapacity() {
      return CPSCalculations.extractFlatCapacity( getFlatSize() );
   }
   public CPSDatumState getFlatSizeState() { return getStateOf( PROP_FLAT_SIZE ); }
   public void setFlatSize( String i ) { setFlatSize( i, false ); }
   public void setFlatSize( String i, boolean force ) { set( flat_size, i, force ); }

   public String getPlanter() { return get( PROP_PLANTER, "" ); }
   public CPSDatumState getPlanterState() { return getStateOf( PROP_PLANTER ); }
   public void setPlanter( String i ) { setPlanter( i, false ); }
   public void setPlanter( String i, boolean force ) { set( planter, i, force ); }

   public String getPlanterSetting() { return get( PROP_PLANTER_SETTING, "" ); }
   public CPSDatumState getPlanterSettingState() { return getStateOf( PROP_PLANTER_SETTING ); }
   public void setPlanterSetting( String i ) { setPlanterSetting( i, false ); }
   public void setPlanterSetting( String i, boolean force ) { set( planter_setting, i, force ); }

   public float getYieldPerFoot() { return get( PROP_YIELD_P_FOOT, new Float( -1.0 )).floatValue(); }
   public String getYieldPerFootString() { return formatFloat( getYieldPerFoot() ); }
   public CPSDatumState getYieldPerFootState() { return getStateOf( PROP_YIELD_P_FOOT ); }
   public void setYieldPerFoot( float i ) { setYieldPerFoot( i, false ); }
   public void setYieldPerFoot( float i, boolean force ) { set( yield_p_foot, new Float( i ), force ); }
   public void setYieldPerFoot( String s ) { setYieldPerFoot( s, false ); }
   public void setYieldPerFoot( String s, boolean force ) { setYieldPerFoot( parseFloat(s), force ); }

   public float getTotalYield() { 
      CPSDatum t = getDatum( PROP_TOTAL_YIELD );
      CPSDatum y = getDatum( PROP_YIELD_P_FOOT );
      CPSDatum r = getDatum( PROP_ROWFT_PLANT );
      
      if ( ! t.isValid() &&
             y.isAvailable() && r.isAvailable() ) {
         getDatum( PROP_TOTAL_YIELD ).setCalculated( true );
         return CPSCalculations.calcTotalYieldFromRowFtToPlant( getRowFtToPlant(), getYieldPerFoot() );
      }
      else
         return get( PROP_TOTAL_YIELD, new Float( -1.0 ) ).floatValue(); 
   }
   public String getTotalYieldString() { return formatFloat( getTotalYield() ); }
   public CPSDatumState getTotalYieldState() { return getStateOf( PROP_TOTAL_YIELD ); }
   public void setTotalYield( float i ) { setTotalYield( i, false ); }
   public void setTotalYield( float i, boolean force ) { set( total_yield, new Float( i ), force ); }
   public void setTotalYield( String s ) { setTotalYield( s, false ); }
   public void setTotalYield( String s, boolean force ) { setTotalYield( parseFloat(s), force ); }

   public int getYieldNumWeeks() { return get( PROP_YIELD_NUM_WEEKS, new Integer( -1 )).intValue(); }
   public String getYieldNumWeeksString() { return formatInt( getYieldNumWeeks() ); }
   public CPSDatumState getYieldNumWeeksState() { return getStateOf( PROP_YIELD_NUM_WEEKS ); }
   public void setYieldNumWeeks( int i ) { setYieldNumWeeks( i, false ); }
   public void setYieldNumWeeks( int i, boolean force ) { set( yield_num_weeks, new Integer( i ), force ); }
   public void setYieldNumWeeks( String s ) { setYieldNumWeeks( s, false ); }
   public void setYieldNumWeeks( String s, boolean force ) { setYieldNumWeeks( parseInt(s), force ); }

   public float getYieldPerWeek() { return get( PROP_YIELD_P_WEEK, new Float( -1.0 )).floatValue(); }
   public String getYieldPerWeekString() { return formatFloat( getYieldPerWeek() ); }
   public CPSDatumState getYieldPerWeekState() { return getStateOf( PROP_YIELD_P_WEEK ); }
   public void setYieldPerWeek( float i ) { setYieldPerWeek( i, false ); }
   public void setYieldPerWeek( float i, boolean force ) { set( yield_p_week, new Float( i ), force ); }
   public void setYieldPerWeek( String s ) { setYieldPerWeek( s, false ); }
   public void setYieldPerWeek( String s, boolean force ) { setYieldPerWeek( parseFloat(s), force ); }

   public String getCropYieldUnit() { return get( PROP_CROP_UNIT, "" ); }
   public CPSDatumState getCropYieldUnitState() { return getStateOf( PROP_CROP_UNIT ); }
   public void setCropYieldUnit( String i ) { setCropYieldUnit( i, false ); }
   public void setCropYieldUnit( String i, boolean force ) { set( crop_unit, i, force ); }

   public float getCropYieldUnitValue() { return get( PROP_CROP_UNIT_VALUE, new Float( -1.0 )).floatValue(); }
   public String getCropYieldUnitValueString() { return formatFloat( getCropYieldUnitValue() ); }
   public CPSDatumState getCropYieldUnitValueState() { return getStateOf( PROP_CROP_UNIT_VALUE ); }
   public void setCropYieldUnitValue( float i ) { setCropYieldUnitValue( i, false ); }
   public void setCropYieldUnitValue( float i, boolean force ) { set( crop_unit_value, new Float( i ), force ); }
   public void setCropYieldUnitValue( String s ) { setCropYieldUnitValue( s, false ); }
   public void setCropYieldUnitValue( String s, boolean force ) { setCropYieldUnitValue( parseFloat(s), force ); }
   
   public String getCustomField1() { return get( PROP_CUSTOM1, "" ); }
   public String getCustomField2() { return get( PROP_CUSTOM2, "" ); }
   public String getCustomField3() { return get( PROP_CUSTOM3, "" ); }
   public String getCustomField4() { return get( PROP_CUSTOM4, "" ); }
   public String getCustomField5() { return get( PROP_CUSTOM5, "" ); }
   public CPSDatumState getCustomField1State() { return getStateOf( PROP_CUSTOM1 ); }
   public CPSDatumState getCustomField2State() { return getStateOf( PROP_CUSTOM2 ); }
   public CPSDatumState getCustomField3State() { return getStateOf( PROP_CUSTOM3 ); }
   public CPSDatumState getCustomField4State() { return getStateOf( PROP_CUSTOM4 ); }
   public CPSDatumState getCustomField5State() { return getStateOf( PROP_CUSTOM5 ); }
   public void setCustomField1( String s ) { setCustomField1( s, false ); }
   public void setCustomField2( String s ) { setCustomField2( s, false ); }
   public void setCustomField3( String s ) { setCustomField3( s, false ); }
   public void setCustomField4( String s ) { setCustomField4( s, false ); }
   public void setCustomField5( String s ) { setCustomField5( s, false ); }
   public void setCustomField1( String s, boolean force ) { set( custom1, s, force ); }
   public void setCustomField2( String s, boolean force ) { set( custom2, s, force ); }
   public void setCustomField3( String s, boolean force ) { set( custom3, s, force ); }
   public void setCustomField4( String s, boolean force ) { set( custom4, s, force ); }
   public void setCustomField5( String s, boolean force ) { set( custom5, s, force ); }
   
   /* *********************************************************************************************/
   /* META METHODS - Operate on entire objects. */
   /* *********************************************************************************************/
   public CPSRecord diff( CPSRecord thatPlanting ) {
      return super.diff( thatPlanting, new CPSPlanting() );
   }
   
   public CPSRecord inheritFrom( CPSRecord thatRecord ) {
      // case 1: both objects are of same class
      if ( this.getClass().getName().equalsIgnoreCase( thatRecord.getClass().getName() ))
         return super.inheritFrom( thatRecord );
      // case 2: this object class != that object class
      else if ( ! thatRecord.getClass().getName().equalsIgnoreCase( CPSCrop.class.getName() )) {
         System.err.println("ERROR: CPSPlanting can only inherit from itself and from CPSCrop, " +
                            "not from " + thatRecord.getClass().getName() );
         return this;
      }
      // else thatRecord is a CPSCrop, proceed w/ inherit
      return super.inheritFrom( thatRecord );
   }
   
   /* *********************************************************************************************/
   /* UTILITY METHODS - Nuts and bolts stuff that's not too exciting */
   /* *********************************************************************************************/
   
   public PlantingIterator iterator() { return new PlantingIterator(); }
   
   public class PlantingIterator extends CPSRecordIterator {
       
      public  boolean ignoreThisProperty() {
//         return this.currentProp == PROP_ID || this.currentProp == PROP_CROP_ID;
         return this.currentProp == PROP_ID;
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


   public Date parseDate( String s ) {
       if ( s == null || s.equals("") )
           return new Date(0);
     
       return dateValidator.parse(s);
       
//      try {
//         return DateFormat.getDateInstance( DateFormat.SHORT ).parse( s );
//      } catch ( Exception e ) {
//         System.err.println( "ERROR parsing date: " + s );
//         return null;
//      }
       
//       SimpleDateFormat sdf = new SimpleDateFormat( "dd MM yyyy" );
//       try {
//           return sdf.parse(s);
//       } catch ( Exception ignore ) {
//           System.err.println( "ERROR parsing date: " + s );
//           return null;
//       }
   }
   
   public String formatDate( Date d ) {
       if ( d.getTime() == 0 )
         return "";
       else
           return dateValidator.format( d );
       
//      else
//         return DateFormat.getDateInstance( DateFormat.SHORT ).format( d );
          
          
//      else {
//         SimpleDateFormat sdf = new SimpleDateFormat( "dd MM yyyy" );
//         return sdf.format( d );
//      }
   }
   
}
