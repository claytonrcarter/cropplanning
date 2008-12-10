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
import CPS.Module.CPSModule;
import java.util.ArrayList;
import java.util.Date;

public class CPSPlanting extends CPSRecord {

   // Core Data
   public final int PROP_CROP_NAME     = CPSDataModelConstants.PROP_CROP_NAME;
   public final int PROP_VAR_NAME      = CPSDataModelConstants.PROP_VAR_NAME;
   public final int PROP_MATURITY      = CPSDataModelConstants.PROP_MATURITY;
   public final int PROP_LOCATION      = CPSDataModelConstants.PROP_LOCATION;

   // Dates
   // "effective" dates
   public final int PROP_DATE_PLANT    = CPSDataModelConstants.PROP_DATE_PLANT;
   public final int PROP_DATE_TP       = CPSDataModelConstants.PROP_DATE_TP;
   public final int PROP_DATE_HARVEST  = CPSDataModelConstants.PROP_DATE_HARVEST;
   // planned dates
   public final int PROP_DATE_PLANT_PLAN = CPSDataModelConstants.PROP_DATE_PLANT_PLAN;
   public final int PROP_DATE_TP_PLAN = CPSDataModelConstants.PROP_DATE_TP_PLAN;
   public final int PROP_DATE_HARVEST_PLAN = CPSDataModelConstants.PROP_DATE_HARVEST_PLAN;
   // actual dates
   public final int PROP_DATE_PLANT_ACTUAL = CPSDataModelConstants.PROP_DATE_PLANT_ACTUAL;
   public final int PROP_DATE_TP_ACTUAL = CPSDataModelConstants.PROP_DATE_TP_ACTUAL;
   public final int PROP_DATE_HARVEST_ACTUAL = CPSDataModelConstants.PROP_DATE_HARVEST_ACTUAL;

   // Status Booleans
   public final int PROP_DONE_PLANTING = CPSDataModelConstants.PROP_DONE_PLANTING;
   public final int PROP_DONE_TP       = CPSDataModelConstants.PROP_DONE_TP;
   public final int PROP_DONE_HARVEST  = CPSDataModelConstants.PROP_DONE_HARVEST;
   public final int PROP_IGNORE = CPSDataModelConstants.PROP_IGNORE;
   
   // Static Data
   // inheritable
   public    final int PROP_MAT_ADJUST    = CPSDataModelConstants.PROP_MAT_ADJUST;
   protected final int PROP_DS_MAT_ADJUST = CPSDataModelConstants.PROP_DS_MAT_ADJUST;
   protected final int PROP_TP_MAT_ADJUST = CPSDataModelConstants.PROP_TP_MAT_ADJUST;
   public    final int PROP_ROWS_P_BED    = CPSDataModelConstants.PROP_ROWS_P_BED;
   protected final int PROP_DS_ROWS_P_BED = CPSDataModelConstants.PROP_DS_ROWS_P_BED;
   protected final int PROP_TP_ROWS_P_BED = CPSDataModelConstants.PROP_TP_ROWS_P_BED;
   public    final int PROP_ROW_SPACE     = CPSDataModelConstants.PROP_SPACE_BETROW;
   protected final int PROP_DS_ROW_SPACE  = CPSDataModelConstants.PROP_DS_SPACE_BETROW;
   protected final int PROP_TP_ROW_SPACE  = CPSDataModelConstants.PROP_TP_SPACE_BETROW;
   public    final int PROP_CROP_NOTES    = CPSDataModelConstants.PROP_PLANT_NOTES; // Inherited
   protected final int PROP_DS_CROP_NOTES = CPSDataModelConstants.PROP_DS_PLANT_NOTES;
   protected final int PROP_TP_CROP_NOTES = CPSDataModelConstants.PROP_TP_PLANT_NOTES;

   public final int PROP_TIME_TO_TP       = CPSDataModelConstants.PROP_TIME_TO_TP;
   public final int PROP_INROW_SPACE   = CPSDataModelConstants.PROP_SPACE_INROW;
   public final int PROP_FLAT_SIZE     = CPSDataModelConstants.PROP_FLAT_SIZE;
   public final int PROP_PLANTING_NOTES = CPSDataModelConstants.PROP_PLANT_NOTES_SPECIFIC; // Not inherited

   // Calculated Data
   public final int PROP_BEDS_PLANT    = CPSDataModelConstants.PROP_BEDS_PLANT;
   public final int PROP_PLANTS_NEEDED = CPSDataModelConstants.PROP_PLANTS_NEEDED;
   public final int PROP_ROWFT_PLANT   = CPSDataModelConstants.PROP_ROWFT_PLANT;
   public final int PROP_PLANTS_START  = CPSDataModelConstants.PROP_PLANTS_START;
   public final int PROP_FLATS_NEEDED  = CPSDataModelConstants.PROP_FLATS_NEEDED;

   // Yield
   // static
   public final int PROP_YIELD_P_FOOT  = CPSDataModelConstants.PROP_YIELD_P_FOOT;
   public final int PROP_YIELD_NUM_WEEKS = CPSDataModelConstants.PROP_YIELD_NUM_WEEKS;
   public final int PROP_YIELD_P_WEEK  = CPSDataModelConstants.PROP_YIELD_P_WEEK;
   public final int PROP_CROP_UNIT     = CPSDataModelConstants.PROP_CROP_UNIT;
   public final int PROP_CROP_UNIT_VALUE = CPSDataModelConstants.PROP_CROP_UNIT_VALUE;
   // calculated
   public final int PROP_TOTAL_YIELD   = CPSDataModelConstants.PROP_TOTAL_YIELD;

   // Misc Metadata
   // bools
   public final int PROP_DIRECT_SEED   = CPSDataModelConstants.PROP_DIRECT_SEED;
   public final int PROP_FROST_HARDY   = CPSDataModelConstants.PROP_FROST_HARDY;
   // Strings
   public final int PROP_GROUPS        = CPSDataModelConstants.PROP_GROUPS;
   public final int PROP_KEYWORDS      = CPSDataModelConstants.PROP_KEYWORDS;
   public final int PROP_OTHER_REQ     = CPSDataModelConstants.PROP_OTHER_REQ;
   public final int PROP_NOTES         = CPSDataModelConstants.PROP_NOTES;

   public final int PROP_CUSTOM1       = CPSDataModelConstants.PROP_CUSTOM1;
   public final int PROP_CUSTOM2       = CPSDataModelConstants.PROP_CUSTOM2;
   public final int PROP_CUSTOM3       = CPSDataModelConstants.PROP_CUSTOM3;
   public final int PROP_CUSTOM4       = CPSDataModelConstants.PROP_CUSTOM4;
   public final int PROP_CUSTOM5       = CPSDataModelConstants.PROP_CUSTOM5;

   
   /* from CPSDataModelConstants: this is the highest value defined there */
   protected int lastValidProperty() { return PROP_CUSTOM5; }
   
   private CPSDatum<String> crop_name;
   private CPSDatum<String> var_name;
   private CPSDatum<Integer> maturity;
   private CPSDatum<String> location;

   private CPSDatum<Date> date_plant;
   private CPSDatum<Date> date_tp;
   private CPSDatum<Date> date_harvest;
   
   private CPSDatum<Date> date_plant_plan;
   private CPSDatum<Date> date_tp_plan;
   private CPSDatum<Date> date_harvest_plan;
   
   private CPSDatum<Date> date_plant_actual;
   private CPSDatum<Date> date_tp_actual;
   private CPSDatum<Date> date_harvest_actual;

   private CPSDatum<Boolean> done_plant;
   private CPSDatum<Boolean> done_tp;
   private CPSDatum<Boolean> done_harvest;
   private CPSDatum<Boolean> ignore;

   private CPSDatum<Integer> ds_mat_adjust, tp_mat_adjust;
   private CPSDatum<Integer> time_to_tp;
   private CPSDatum<Integer> ds_rows_p_bed, tp_rows_p_bed;
   private CPSDatum<Integer> inrow_space;
   private CPSDatum<Integer> ds_row_space, tp_row_space;
   private CPSDatum<String> flat_size;
   private CPSDatum<String> ds_crop_notes, tp_crop_notes;
   private CPSDatum<String> planting_notes;

   private CPSDatum<Float> beds_to_plant;
   private CPSDatum<Integer> plants_needed;
   private CPSDatum<Integer> rowft_to_plant;
   private CPSDatum<Integer> plants_to_start;
   private CPSDatum<Float> flats_needed;

   private CPSDatum<Float> yield_p_foot;
   private CPSDatum<Integer> yield_num_weeks;
   private CPSDatum<Float> yield_p_week;
   private CPSDatum<String> crop_unit;
   private CPSDatum<Float> crop_unit_value;
   private CPSDatum<Float> total_yield;

   private CPSDatum<CPSBoolean> direct_seed;
   private CPSDatum<CPSBoolean> frost_hardy;

   private CPSDatum<String> groups;
   private CPSDatum<String> keywords;
   private CPSDatum<String> other_req;
   private CPSDatum<String> notes;
   
   private CPSDatum<String> custom1;
   private CPSDatum<String> custom2;
   private CPSDatum<String> custom3;
   private CPSDatum<String> custom4;
   private CPSDatum<String> custom5;
   
   private CPSDateValidator dateValidator;
   
   protected final static int DATE_TYPE_EFFECTIVE = 0;
   protected final static int DATE_TYPE_PLANNED = 1;
   protected final static int DATE_TYPE_ACTUAL = 2;


   public CPSPlanting() {
      
      recordID = new CPSDatum<Integer>( "Unique ID", PROP_ID, new Integer(-1) );
      commonIDs = new CPSDatum<ArrayList<Integer>>( "Crop IDs represented", PROP_COMMON_ID, new ArrayList() );
      
      crop_name = new CPSDatum<String>( "Crop name", PROP_CROP_NAME, "" );
      var_name = new CPSDatum<String>( "Variety name", PROP_VAR_NAME, "" );
      maturity = new CPSDatum<Integer>( "Maturity Days", PROP_MATURITY, new Integer(-1));
      location = new CPSDatum<String>( "Location", PROP_LOCATION, "" );

      date_plant = new CPSDatum<Date>( "Planting Date", PROP_DATE_PLANT, new Date( 0 ) );
      date_tp = new CPSDatum<Date>( "Transplant Date", PROP_DATE_TP, new Date(0) );
      date_harvest = new CPSDatum<Date>( "Harvest Date", PROP_DATE_HARVEST, new Date(0) );

      date_plant_plan = new CPSDatum<Date>( "Planting Date (Planned)", PROP_DATE_PLANT_PLAN, new Date( 0 ) );
      date_tp_plan = new CPSDatum<Date>( "Transplant Date (Planned)", PROP_DATE_TP_PLAN, new Date(0) );
      date_harvest_plan = new CPSDatum<Date>( "Harvest Date (Planned)", PROP_DATE_HARVEST_PLAN, new Date(0) );

      date_plant_actual = new CPSDatum<Date>( "Planting Date (Actual)", PROP_DATE_PLANT_ACTUAL, new Date( 0 ) );
      date_tp_actual = new CPSDatum<Date>( "Transplant Date (Actual)", PROP_DATE_TP_ACTUAL, new Date(0) );
      date_harvest_actual = new CPSDatum<Date>( "Harvest Date (Actual)", PROP_DATE_HARVEST_ACTUAL, new Date(0) );

      done_plant = new CPSDatum<Boolean>( "Done Planting?", PROP_DONE_PLANTING, new Boolean( false ));
      done_tp = new CPSDatum<Boolean>( "Done Transplanting?", PROP_DONE_TP, new Boolean( false ));
      done_harvest = new CPSDatum<Boolean>( "Done Harvesting?", PROP_DONE_HARVEST, new Boolean( false ));
      ignore = new CPSDatum<Boolean>( "Ignore?", PROP_IGNORE, new Boolean( false ));

      ds_mat_adjust = new CPSDatum<Integer>( "DS Mat. Adj.", PROP_DS_MAT_ADJUST, new Integer(-1) );
      tp_mat_adjust = new CPSDatum<Integer>( "TP Mat. Adj.", PROP_TP_MAT_ADJUST, new Integer(-1) );
      time_to_tp = new CPSDatum<Integer>( "Weeks to TP", PROP_TIME_TO_TP, new Integer( -1 ) );
      ds_rows_p_bed = new CPSDatum<Integer>( "DS Rows/Bed", PROP_DS_ROWS_P_BED, new Integer(-1) );
      tp_rows_p_bed = new CPSDatum<Integer>( "TP Rows/Bed", PROP_TP_ROWS_P_BED, new Integer(-1) );
      inrow_space = new CPSDatum<Integer>( "In-row Spacing", PROP_INROW_SPACE, new Integer( -1 ) );
      ds_row_space = new CPSDatum<Integer>( "DS Row Spacing", PROP_DS_ROW_SPACE, new Integer( -1 ) );
      tp_row_space = new CPSDatum<Integer>( "TP Row Spacing", PROP_TP_ROW_SPACE, new Integer( -1 ) );
      flat_size = new CPSDatum<String>( "Flat size", PROP_FLAT_SIZE, "" );
      ds_crop_notes = new CPSDatum<String>( "DS Planting Notes (from CropDB)", PROP_DS_CROP_NOTES, "" );
      tp_crop_notes = new CPSDatum<String>( "TP Planting Notes (from CropDB)", PROP_TP_CROP_NOTES, "" );
      planting_notes = new CPSDatum<String>( "Planting Notes", PROP_PLANTING_NOTES, "" );

      beds_to_plant = new CPSDatum<Float>( "Num. Beds to Plants", PROP_BEDS_PLANT, new Float(-1.0) );
      plants_needed = new CPSDatum<Integer>( "Num. Plants Needed", PROP_PLANTS_NEEDED, new Integer(-1) );
      rowft_to_plant = new CPSDatum<Integer>( "Row Feet To Plant", PROP_ROWFT_PLANT, new Integer(-1) );
      plants_to_start = new CPSDatum<Integer>( "Num. Plants to Start", PROP_PLANTS_START, new Integer(-1) );
      flats_needed = new CPSDatum<Float>( "Num. Flats Needed", PROP_FLATS_NEEDED, new Float(-1.0) );
            
      yield_p_foot = new CPSDatum<Float>( "Yield/Ft", PROP_YIELD_P_FOOT, new Float(-1.0) );
      yield_num_weeks = new CPSDatum<Integer>( "Will Yield for (weeks)", PROP_YIELD_NUM_WEEKS, new Integer(-1) );
      yield_p_week = new CPSDatum<Float>( "Yield/Week", PROP_YIELD_P_WEEK, new Float(-1.0));
      crop_unit = new CPSDatum<String>( "Unit of Yield", PROP_CROP_UNIT, "" );
      crop_unit_value = new CPSDatum<Float>( "Value per Yield Unit", PROP_CROP_UNIT_VALUE, new Float(-1.0));
      total_yield = new CPSDatum<Float>( "Total Yield", PROP_TOTAL_YIELD, new Float( -1.0 ) );

      direct_seed = new CPSDatum<CPSBoolean>( "Direct seeded?", PROP_DIRECT_SEED, new CPSBoolean( true ) );
      frost_hardy = new CPSDatum<CPSBoolean>( "Frost hardy?", PROP_FROST_HARDY, new CPSBoolean( false )  );

      groups = new CPSDatum<String>( "Groups", PROP_GROUPS, "" );
      keywords = new CPSDatum<String>( "Keywords", PROP_KEYWORDS, "" );
      other_req = new CPSDatum<String>( "Other Requirements", PROP_OTHER_REQ, "" );
      notes = new CPSDatum<String>( "Notes", PROP_NOTES, "" );

      custom1 = new CPSDatum<String>( "Custom Field 1", PROP_CUSTOM1, "" );
      custom2 = new CPSDatum<String>( "Custom Field 2", PROP_CUSTOM2, "" );
      custom3 = new CPSDatum<String>( "Custom Field 3", PROP_CUSTOM3, "" );
      custom4 = new CPSDatum<String>( "Custom Field 4", PROP_CUSTOM4, "" );
      custom5 = new CPSDatum<String>( "Custom Field 5", PROP_CUSTOM5, "" );
      
      dateValidator = new CPSDateValidator();
      
   }


   protected CPSDatum getDatum(int prop) {

      /* very ugly, but this allows us to use the hierarchy of Crop properties */
       switch ( prop ) {
          case PROP_CROP_NAME:     return crop_name;
          case PROP_VAR_NAME:      return var_name;
          case PROP_MATURITY:      return maturity;
          case PROP_LOCATION:      return location;

          case PROP_DATE_PLANT:    return date_plant;
          case PROP_DATE_TP:       return date_tp;
          case PROP_DATE_HARVEST:  return date_harvest;
          case PROP_DATE_PLANT_PLAN:    return date_plant_plan;
          case PROP_DATE_TP_PLAN:       return date_tp_plan;
          case PROP_DATE_HARVEST_PLAN:  return date_harvest_plan;
          case PROP_DATE_PLANT_ACTUAL:    return date_plant_actual;
          case PROP_DATE_TP_ACTUAL:       return date_tp_actual;
          case PROP_DATE_HARVEST_ACTUAL:  return date_harvest_actual;

          case PROP_DONE_PLANTING: return done_plant;
          case PROP_DONE_TP:       return done_tp;
          case PROP_DONE_HARVEST:  return done_harvest;
          case PROP_IGNORE:        return ignore;

          case PROP_MAT_ADJUST:
             if ( isDirectSeeded() )
                return getDatum( PROP_DS_MAT_ADJUST );
             else
                return getDatum( PROP_TP_MAT_ADJUST );
          case PROP_DS_MAT_ADJUST:    return ds_mat_adjust;
          case PROP_TP_MAT_ADJUST:    return tp_mat_adjust;
          case PROP_TIME_TO_TP:    return time_to_tp;
          case PROP_ROWS_P_BED:    
             if ( isDirectSeeded() )
                return getDatum( PROP_DS_ROWS_P_BED );
             else
                return getDatum( PROP_TP_ROWS_P_BED );
          case PROP_DS_ROWS_P_BED:    return ds_rows_p_bed;
          case PROP_TP_ROWS_P_BED:    return tp_rows_p_bed;
          case PROP_INROW_SPACE:   return inrow_space;
          case PROP_ROW_SPACE:
             if ( isDirectSeeded() )
                return getDatum( PROP_DS_ROW_SPACE );
             else
                return getDatum( PROP_TP_ROW_SPACE );
          case PROP_DS_ROW_SPACE:     return ds_row_space;
          case PROP_TP_ROW_SPACE:     return tp_row_space;
          case PROP_FLAT_SIZE:     return flat_size;
          case PROP_CROP_NOTES:
             if ( isDirectSeeded() )
                return getDatum( PROP_DS_CROP_NOTES );
             else
                return getDatum( PROP_TP_CROP_NOTES );
          case PROP_DS_CROP_NOTES:       return ds_crop_notes;
          case PROP_TP_CROP_NOTES:       return tp_crop_notes;
          case PROP_PLANTING_NOTES: return planting_notes;

          case PROP_BEDS_PLANT:    return beds_to_plant;
          case PROP_PLANTS_NEEDED: return plants_needed;
          case PROP_ROWFT_PLANT:   return rowft_to_plant;
          case PROP_PLANTS_START:  return plants_to_start;
          case PROP_FLATS_NEEDED:  return flats_needed;
          
          case PROP_YIELD_P_FOOT:  return yield_p_foot;
          case PROP_YIELD_NUM_WEEKS: return yield_num_weeks;
          case PROP_YIELD_P_WEEK:  return yield_p_week;
          case PROP_CROP_UNIT:     return crop_unit;
          case PROP_CROP_UNIT_VALUE: return crop_unit_value;
          case PROP_TOTAL_YIELD:   return total_yield;

          case PROP_DIRECT_SEED:   return direct_seed;
          case PROP_FROST_HARDY:   return frost_hardy;
          case PROP_GROUPS:        return groups;
          case PROP_KEYWORDS:      return keywords;
          case PROP_OTHER_REQ:     return other_req;
          case PROP_NOTES:         return notes;
          
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
      a.add( PROP_MATURITY );
      a.add( PROP_DS_MAT_ADJUST );
      a.add( PROP_TP_MAT_ADJUST );
      a.add( PROP_TIME_TO_TP );
      a.add( PROP_DS_ROWS_P_BED );
      a.add( PROP_TP_ROWS_P_BED );
      a.add( PROP_INROW_SPACE );
      a.add( PROP_DS_ROW_SPACE );
      a.add( PROP_TP_ROW_SPACE );
      a.add( PROP_FLAT_SIZE );
      a.add( PROP_DS_CROP_NOTES );
      a.add( PROP_TP_CROP_NOTES );
      a.add( PROP_YIELD_P_FOOT );
      a.add( PROP_YIELD_NUM_WEEKS );
      a.add( PROP_YIELD_P_WEEK );
      a.add( PROP_CROP_UNIT );
      a.add( PROP_CROP_UNIT_VALUE );
      a.add( PROP_FROST_HARDY  );
      a.add( PROP_OTHER_REQ );
      return a;
   }

   @Override
   public void finishUp () {
      // perform a few calculations to make sure that values have worked through the system
      // before we need to work with them
      // TODO: do the other calculated values need to be here?  bedsToPlant and such?
      getDateToPlantActual();
      getDateToPlantPlanned();
      getDateToTPActual();
      getDateToTPPlanned();
      getDateToHarvestActual();
      getDateToHarvestPlanned();
   }



   
   /* *********************************************************************************************/
   /* GETTERS and SETTERS
   /* *********************************************************************************************/
   
   public String getCropName() { return get(PROP_CROP_NAME, ""); }
   public CPSDatumState getCropNameState() { return getStateOf( PROP_CROP_NAME ); }
   public void setCropName(String s) { setCropName( s, false ); }
   public void setCropName( String s, boolean force ) { set( crop_name, s, force ); }

   public String getVarietyName() { return get( PROP_VAR_NAME, "" ); }
   public CPSDatumState getVarietyNameState() { return getStateOf( PROP_VAR_NAME ); }
   public void setVarietyName( String s ) { setVarietyName( s, false ); }
   public void setVarietyName( String s, boolean force ) { set( var_name, s, force ); }

   public int getMaturityDays() { return getInt( PROP_MATURITY ); }
   public String getMaturityDaysString() { return formatInt( getMaturityDays() ); }
   public CPSDatumState getMaturityDaysState() { return getStateOf( PROP_MATURITY ); }
   public void setMaturityDays( int i ) { setMaturityDays( i, false ); }
   public void setMaturityDays( int i, boolean force ) { set( maturity, new Integer( i ), force ); }
   public void setMaturityDays( String s ) { setMaturityDays( s, false ); }
   public void setMaturityDays( String s, boolean force ) { setMaturityDays( parseInt( s ), force ); }

   public String getLocation() { return get(  PROP_LOCATION, "" ); }
   public CPSDatumState getLocationState() { return getStateOf( PROP_LOCATION ); }
   public void setLocation( String s ) { setLocation( s, false ); }
   public void setLocation( String s, boolean force ) { set( location, s, force ); }

   /*
    * Dates
    */
   protected Date getEffectiveDate( int prop_effective, int prop_actual, int prop_plan ) {
      CPSDatum e = getDatum( prop_effective );
      CPSDatum p = getDatum( prop_plan );
      CPSDatum a = getDatum( prop_actual );

       /* If date_plant_actual is valid, return it
        * else return date_plant_plan
        * or just return a default */
       if ( this.isSingleRecord() &&
            a.isAvailable() ) {
          debug( "getting ACTUAL date for " + a.getDescriptor() );
          e.setState( a.getState() );
          // shudder ... I don't like this
          switch ( prop_actual ) {
             case PROP_DATE_PLANT_ACTUAL:   return getDateToPlantActual();
             case PROP_DATE_TP_ACTUAL:      return getDateToTPActual();
             case PROP_DATE_HARVEST_ACTUAL: return getDateToHarvestActual();
             default:                       return new Date(0);
          }
       }
       else if ( this.isSingleRecord() &&
                 p.isAvailable() ) {
          debug( "getting PLANNED date for " + a.getDescriptor() );
          e.setState( p.getState() );
          switch ( prop_plan ) {
             case PROP_DATE_PLANT_PLAN:   return getDateToPlantPlanned();
             case PROP_DATE_TP_PLAN:      return getDateToTPPlanned();
             case PROP_DATE_HARVEST_PLAN: return getDateToHarvestPlanned();
             default:                     return new Date(0);
          }
       }
       else {
//          debug( "neither actual nor planned are available, returning DEFAULT date" );
           return get( prop_effective, new Date(0) );
       }
   }

   protected Date getDateToPlantAbstract ( int date_type ) {

      int prop_plant, prop_tp, prop_harv;

      if ( date_type == DATE_TYPE_ACTUAL ) {
         prop_plant = PROP_DATE_PLANT_ACTUAL;
         prop_tp = PROP_DATE_TP_ACTUAL;
         prop_harv = PROP_DATE_HARVEST_ACTUAL;
      }
      else { // if ( date_type == DATE_TYPE_PLANNED ) {
         prop_plant = PROP_DATE_PLANT_PLAN;
         prop_tp = PROP_DATE_TP_PLAN;
         prop_harv = PROP_DATE_HARVEST_PLAN;
      }

      CPSDatum plant = getDatum( prop_plant );
      CPSDatum tp = getDatum( prop_tp );
      CPSDatum harv = getDatum( prop_harv );
      CPSDatum m = getDatum( PROP_MATURITY );
      CPSDatum a = getDatum( PROP_MAT_ADJUST );
      CPSDatum w = getDatum( PROP_TIME_TO_TP );

       /* Only calculate the planting date if:
        * DATE_PLANT is *NOT* valid AND
        * DATE_HARVEST AND MATURITY *ARE* valid
        * otherwise just return the planting date or a default */
       if ( this.isSingleRecord() &&
            ! plant.isValid() &&
              tp.isValid() && w.isValid() ) {
          getDatum( prop_plant ).setCalculated( true );
          if ( date_type == DATE_TYPE_ACTUAL )
             return CPSCalculations.calcDatePlantFromDateTP( getDateToTPActual(), getTimeToTP() );
          else
             return CPSCalculations.calcDatePlantFromDateTP( getDateToTPPlanned(), getTimeToTP() );
       }
       else if ( this.isSingleRecord() &&
                 ! plant.isValid() &&
                   harv.isValid() && m.isValid() ) {
          getDatum( prop_plant ).setCalculated( true );
          int matAdjust = getMatAdjust();
          if ( matAdjust == -1 )
              matAdjust = 0;
          int mat = getMaturityDays();
//          if ( w.isValid() )
          if ( ! isDirectSeeded() )
              mat += getTimeToTP() * 7;
          if ( date_type == DATE_TYPE_ACTUAL )
             return CPSCalculations.calcDatePlantFromDateHarvest( getDateToHarvestActual(),
                                                                  mat,
                                                                  matAdjust );
          else
             return CPSCalculations.calcDatePlantFromDateHarvest( getDateToHarvestPlanned(),
                                                                  mat,
                                                                  matAdjust );
       }
       else
           return get( prop_plant, new Date(0) );
   }

   public Date getDateToPlant() {
      return getEffectiveDate( PROP_DATE_PLANT, PROP_DATE_PLANT_ACTUAL, PROP_DATE_PLANT_PLAN );
   }
   public String getDateToPlantString() { return formatDate( getDateToPlant() ); }
   public CPSDatumState getDateToPlantState() { return getStateOf( PROP_DATE_PLANT ); }
//   public void setDateToPlant( Date d ) { setDateToPlant( d, false ); }
//   public void setDateToPlant( String d ) { setDateToPlant( parseDate(d), false ); }
//   public void setDateToPlant( Date d, boolean force ) { set( date_plant, d, force ); }
//   public void setDateToPlant( String d, boolean force ) { setDateToPlant( parseDate(d), force ); }

   public Date getDateToPlantPlanned() {
      return getDateToPlantAbstract( DATE_TYPE_PLANNED );
   }
   public String getDateToPlantPlannedString() { return formatDate( getDateToPlantPlanned() ); }
   public CPSDatumState getDateToPlantPlannedState() { return getStateOf( PROP_DATE_PLANT_PLAN ); }
   public void setDateToPlantPlanned( Date d ) { setDateToPlantPlanned( d, false ); }
   public void setDateToPlantPlanned( String d ) { setDateToPlantPlanned( parseDate(d), false ); }
   public void setDateToPlantPlanned( Date d, boolean force ) { set( date_plant_plan, d, force ); }
   public void setDateToPlantPlanned( String d, boolean force ) { setDateToPlantPlanned( parseDate(d), force ); }

   public Date getDateToPlantActual() {
      return getDateToPlantAbstract( DATE_TYPE_ACTUAL );
   }
   public String getDateToPlantActualString() { return formatDate( getDateToPlantActual() ); }
   public CPSDatumState getDateToPlantActualState() { return getStateOf( PROP_DATE_PLANT_ACTUAL ); }
   public void setDateToPlantActual( Date d ) { setDateToPlantActual( d, false ); }
   public void setDateToPlantActual( String d ) { setDateToPlantActual( parseDate(d), false ); }
   public void setDateToPlantActual( Date d, boolean force ) { set( date_plant_actual, d, force ); }
   public void setDateToPlantActual( String d, boolean force ) { setDateToPlantActual( parseDate(d), force ); }


   public Date getDateToTPAbstract( int date_type ) {
      
      int prop_plant, prop_tp, prop_harv;
      
      if ( date_type == DATE_TYPE_ACTUAL ) {
         prop_plant = PROP_DATE_PLANT_ACTUAL;
         prop_tp = PROP_DATE_TP_ACTUAL;
         prop_harv = PROP_DATE_HARVEST_ACTUAL;
      } 
      else { // if ( date_type == DATE_TYPE_PLANNED ) {
         prop_plant = PROP_DATE_PLANT_PLAN;
         prop_tp = PROP_DATE_TP_PLAN;
         prop_harv = PROP_DATE_HARVEST_PLAN;
      }

      CPSDatum p = getDatum( prop_plant );
      CPSDatum t = getDatum( prop_tp );
      CPSDatum h = getDatum( prop_harv );
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
         getDatum( prop_tp ).setCalculated( true );
         if ( date_type == DATE_TYPE_ACTUAL )
            return CPSCalculations.calcDateTPFromDatePlant( getDateToPlantActual(), getTimeToTP() );
         else
            return CPSCalculations.calcDateTPFromDatePlant( getDateToPlantPlanned(), getTimeToTP() );
      }
      else if ( this.isSingleRecord() &&
                ! t.isValid() &&
                  w.isValid() &&
                  h.isValid() && m.isValid() ) {
         getDatum( prop_tp ).setCalculated( true );
         int matAdjust = getMatAdjust();
         if ( matAdjust == -1 )
             matAdjust = 0;
         if ( date_type == DATE_TYPE_ACTUAL )
            return CPSCalculations.calcDateTPFromDateHarvest( getDateToHarvestActual(),
                                                              getMaturityDays(),
                                                              matAdjust );
         else
            return CPSCalculations.calcDateTPFromDateHarvest( getDateToHarvestPlanned(),
                                                              getMaturityDays(),
                                                              matAdjust );
      }
      else
         return get( prop_tp, new Date(0) );
   }

   public Date getDateToTP() {
      return getEffectiveDate( PROP_DATE_TP, PROP_DATE_TP_ACTUAL, PROP_DATE_TP_PLAN );
   }
   public String getDateToTPString() { return formatDate( getDateToTP() ); }
   public CPSDatumState getDateToTPState() { return getStateOf( PROP_DATE_TP ); }
//   public void setDateToTP( Date d ) { setDateToTP( d, false ); }
//   public void setDateToTP( String d ) { setDateToTP( parseDate( d ), false ); }
//   public void setDateToTP( Date d, boolean force ) { set( date_tp, d, force ); }
//   public void setDateToTP( String d, boolean force ) { setDateToTP( parseDate( d ), force ); }

   public Date getDateToTPPlanned () {
      return getDateToTPAbstract( DATE_TYPE_PLANNED );
   }
   public String getDateToTPPlannedString() { return formatDate( getDateToTPPlanned() ); }
   public CPSDatumState getDateToTPPlannedState() { return getStateOf( PROP_DATE_TP_PLAN ); }
   public void setDateToTPPlanned( Date d ) { setDateToTPPlanned( d, false ); }
   public void setDateToTPPlanned( String d ) { setDateToTPPlanned( parseDate( d ), false ); }
   public void setDateToTPPlanned( Date d, boolean force ) { set( date_tp_plan, d, force ); }
   public void setDateToTPPlanned( String d, boolean force ) { setDateToTPPlanned( parseDate( d ), force ); }

   public Date getDateToTPActual() {
      return getDateToTPAbstract( DATE_TYPE_ACTUAL );
   }
   public String getDateToTPActualString() { return formatDate( getDateToTPActual() ); }
   public CPSDatumState getDateToTPActualState() { return getStateOf( PROP_DATE_TP_ACTUAL ); }
   public void setDateToTPActual( Date d ) { setDateToTPActual( d, false ); }
   public void setDateToTPActual( String d ) { setDateToTPActual( parseDate( d ), false ); }
   public void setDateToTPActual( Date d, boolean force ) { set( date_tp_actual, d, force ); }
   public void setDateToTPActual( String d, boolean force ) { setDateToTPActual( parseDate( d ), force ); }


   public Date getDateToHarvestAbstract( int date_type ) {

      int prop_plant, prop_tp, prop_harv;

      if ( date_type == DATE_TYPE_ACTUAL ) {
         prop_plant = PROP_DATE_PLANT_ACTUAL;
         prop_tp = PROP_DATE_TP_ACTUAL;
         prop_harv = PROP_DATE_HARVEST_ACTUAL;
      }
      else { // if ( date_type == DATE_TYPE_PLANNED ) {
         prop_plant = PROP_DATE_PLANT_PLAN;
         prop_tp = PROP_DATE_TP_PLAN;
         prop_harv = PROP_DATE_HARVEST_PLAN;
      }

      CPSDatum p = getDatum( prop_plant );
      CPSDatum t = getDatum( prop_tp );
      CPSDatum h = getDatum( prop_harv );
      CPSDatum m = getDatum( PROP_MATURITY );
      CPSDatum w = getDatum( PROP_TIME_TO_TP );

       /* Only calculate the harvest date if:
        * DATE_HARVEST is *NOT* valid AND
        * DATE_PLANTING AND MATURITY *ARE* valid
        * otherwise just return the harvest date or a default */
       if ( this.isSingleRecord() &&
            ! h.isValid() &&
              t.isValid() && m.isValid() && w.isValid() ) {
          getDatum( prop_harv ).setCalculated( true );
          int matAdjust = getMatAdjust();
          if ( matAdjust == -1 )
              matAdjust = 0;
          if ( date_type == DATE_TYPE_ACTUAL )
             return CPSCalculations.calcDateHarvestFromDateTP( getDateToTPActual(),
                                                               getMaturityDays(),
                                                               matAdjust );
          else
             return CPSCalculations.calcDateHarvestFromDateTP( getDateToTPPlanned(),
                                                               getMaturityDays(),
                                                               matAdjust );
       }
       else if ( this.isSingleRecord() &&
                 ! h.isValid() &&
                   p.isValid() && m.isValid() ) {
           getDatum( prop_harv ).setCalculated( true );
           int matAdjust = getMatAdjust();
           if ( matAdjust == -1 )
               matAdjust = 0;
           int mat = getMaturityDays();
//           if ( w.isValid() )
           if ( ! isDirectSeeded() )
               mat += getTimeToTP() * 7;
           if ( date_type == DATE_TYPE_ACTUAL )
              return CPSCalculations.calcDateHarvestFromDatePlant( getDateToPlantActual(),
                                                                   mat,
                                                                   matAdjust );
           else
              return CPSCalculations.calcDateHarvestFromDatePlant( getDateToPlantPlanned(),
                                                                   mat,
                                                                   matAdjust );

       }
       else
          return get( prop_harv, new Date(0) );
   }

   public Date getDateToHarvest() {
      return getEffectiveDate( PROP_DATE_HARVEST, PROP_DATE_HARVEST_ACTUAL, PROP_DATE_HARVEST_PLAN );
   }
   public String getDateToHarvestString() { return formatDate( getDateToHarvest() ); }
   public CPSDatumState getDateToHarvestState() { return getStateOf( PROP_DATE_HARVEST ); }
//   public void setDateToHarvest( Date d ) { setDateToHarvest( d, false ); }
//   public void setDateToHarvest( Date d, boolean force ) { set( date_harvest, d, force ); }
//   public void setDateToHarvest( String d ) { setDateToHarvest( parseDate( d ), false ); }
//   public void setDateToHarvest( String d, boolean force ) { setDateToHarvest( parseDate( d ), force ); }

   public Date getDateToHarvestPlanned() { return getDateToHarvestAbstract( DATE_TYPE_PLANNED ); }
   public String getDateToHarvestPlannedString() { return formatDate( getDateToHarvestPlanned() ); }
   public CPSDatumState getDateToHarvestPlannedState() { return getStateOf( PROP_DATE_HARVEST_PLAN ); }
   public void setDateToHarvestPlanned( Date d ) { setDateToHarvestPlanned( d, false ); }
   public void setDateToHarvestPlanned( Date d, boolean force ) { set( date_harvest_plan, d, force ); }
   public void setDateToHarvestPlanned( String d ) { setDateToHarvestPlanned( parseDate( d ), false ); }
   public void setDateToHarvestPlanned( String d, boolean force ) { setDateToHarvestPlanned( parseDate( d ), force ); }

   public Date getDateToHarvestActual() { return getDateToHarvestAbstract( DATE_TYPE_ACTUAL ); }
   public String getDateToHarvestActualString() { return formatDate( getDateToHarvestActual() ); }
   public CPSDatumState getDateToHarvestActualState() { return getStateOf( PROP_DATE_HARVEST_ACTUAL ); }
   public void setDateToHarvestActual( Date d ) { setDateToHarvestActual( d, false ); }
   public void setDateToHarvestActual( Date d, boolean force ) { set( date_harvest_actual, d, force ); }
   public void setDateToHarvestActual( String d ) { setDateToHarvestActual( parseDate( d ), false ); }
   public void setDateToHarvestActual( String d, boolean force ) { setDateToHarvestActual( parseDate( d ), force ); }

   /*
    * Status Booleans
    */
   public Boolean getDonePlanting() { return get( PROP_DONE_PLANTING, new Boolean( false )).booleanValue(); }
   public CPSDatumState getDonePlantingState() { return getStateOf( PROP_DONE_PLANTING  ); }
   public void setDonePlanting( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDonePlanting( true );
      else
         setDonePlanting( false );
   }
   public void setDonePlanting( Boolean b ) { setDonePlanting( b, false ); }
   public void setDonePlanting( Boolean b, boolean force ) { set( done_plant, b, force ); }

   public Boolean getDoneTP() { return get( PROP_DONE_TP, new Boolean( false )).booleanValue(); }
   public CPSDatumState getDoneTPState() { return getStateOf( PROP_DONE_TP  ); }
   public void setDoneTP( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDoneTP( true );
      else
         setDoneTP( false );
   }
   public void setDoneTP( Boolean b ) { setDoneTP( b, false ); }
   public void setDoneTP( Boolean b, boolean force ) { set( done_tp, b, force ); }

   public Boolean getDoneHarvest() { return get( PROP_DONE_HARVEST, new Boolean( false )).booleanValue(); }
   public CPSDatumState getDoneHarvestState() { return getStateOf( PROP_DONE_HARVEST  ); }
   public void setDoneHarvest( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDoneHarvest( true );
      else
         setDoneHarvest( false );
   }
   public void setDoneHarvest( Boolean b ) { setDoneHarvest( b, false ); }
   public void setDoneHarvest( Boolean b, boolean force ) { set( done_harvest, b, force ); }

   public Boolean getIgnore() { return get( PROP_IGNORE, new Boolean( false )).booleanValue(); }
   public CPSDatumState getIgnoreState() { return getStateOf( PROP_IGNORE  ); }
   public void setIgnore( String s ) {
      if ( s != null && s.equalsIgnoreCase("true") )
         setIgnore( true );
      else
         setIgnore( false );
   }
   public void setIgnore( Boolean b ) { setIgnore( b, false ); }
   public void setIgnore( Boolean b, boolean force ) { set( ignore, b, force ); }


   /*
    * Static Data
    */
   public int getMatAdjust() { 
      if ( isDirectSeeded() )
         return get( PROP_DS_MAT_ADJUST, new Integer( -1 ) ).intValue();
      else
         return get( PROP_TP_MAT_ADJUST, new Integer( -1 ) ).intValue();
   }
   public String getMatAdjustString() { return formatInt( getMatAdjust() ); }
   public CPSDatumState getMatAdjustState() {
      if ( isDirectSeeded() )
         return getStateOf( PROP_DS_MAT_ADJUST );
      else
         return getStateOf( PROP_TP_MAT_ADJUST );
   }
   public void setMatAdjust( int i ) { setMatAdjust( i, false ); }
   public void setMatAdjust( int i, boolean force ) { 
      if ( isDirectSeeded() )
         set( ds_mat_adjust, new Integer( i ), force );
      else
         set( tp_mat_adjust, new Integer( i ), force );
   }
   public void setMatAdjust( String s ) { setMatAdjust( s, false ); }
   public void setMatAdjust( String s, boolean force ) { setMatAdjust( parseInt(s), force ); }

//   protected int getDSMatAdjust() { return get( PROP_DS_MAT_ADJUST, new Integer( -1 )).intValue(); }
//   protected String getDSMatAdjustString() { return formatInt( getDSMatAdjust() ); }
//   protected CPSDatumState getDSMatAdjustState() { return getStateOf( PROP_DS_MAT_ADJUST ); }
//   protected void setDSMatAdjust( int i ) { setDSMatAdjust( i, false ); }
//   protected void setDSMatAdjust( int i, boolean force ) { set( ds_mat_adjust, new Integer( i ), force ); }
//   protected void setDSMatAdjust( String s ) { setDSMatAdjust( s, false ); }
//   protected void setDSMatAdjust( String s, boolean force ) { setDSMatAdjust( parseInt(s), force ); }
//
//   protected int getTPMatAdjust() { return get( PROP_TP_MAT_ADJUST, new Integer( -1 )).intValue(); }
//   protected String getTPMatAdjustString() { return formatInt( getTPMatAdjust() ); }
//   protected CPSDatumState getTPMatAdjustState() { return getStateOf( PROP_TP_MAT_ADJUST ); }
//   protected void setTPMatAdjust( int i ) { setTPMatAdjust( i, false ); }
//   protected void setTPMatAdjust( int i, boolean force ) { set( tp_mat_adjust, new Integer( i ), force ); }
//   protected void setTPMatAdjust( String s ) { setTPMatAdjust( s, false ); }
//   protected void setTPMatAdjust( String s, boolean force ) { setTPMatAdjust( parseInt(s), force ); }

   public int getTimeToTP() { return get( PROP_TIME_TO_TP, new Integer( -1 )).intValue(); }
   public String getTimeToTPString() { return formatInt( getTimeToTP() ); }
   public CPSDatumState getTimeToTPState() { return getStateOf( PROP_TIME_TO_TP ); }
   public void setTimeToTP( int i ) { setTimeToTP( i, false ); }
   public void setTimeToTP( int i, boolean force ) { set( time_to_tp, new Integer( i ), force ); }
   public void setTimeToTP( String s ) { setTimeToTP( s, false ); }
   public void setTimeToTP( String s, boolean force ) { setTimeToTP( parseInt(s), force ); }

   public CPSDatumState getRowsPerBedState() {
      if ( isDirectSeeded() )
         return getStateOf( PROP_DS_ROWS_P_BED );
      else
         return getStateOf( PROP_TP_ROWS_P_BED );
   }
   public int getRowsPerBed() {
      if ( isDirectSeeded() )
         return get( PROP_DS_ROWS_P_BED, new Integer( -1 ) ).intValue();
      else
         return get( PROP_TP_ROWS_P_BED, new Integer( -1 ) ).intValue();
   }
   public String getRowsPerBedString() { return formatInt( getRowsPerBed() ); }
   public void setRowsPerBed( int i ) { setRowsPerBed( i, false ); }
   public void setRowsPerBed( int i, boolean force ) {
      if ( isDirectSeeded() ) {
         CPSModule.debug( "CPSPlanting", "planting is DIRECT SEEDED, recording rows per bed as " + i );
         set( ds_rows_p_bed, new Integer( i ), force );
      }
      else {
         CPSModule.debug( "CPSPlanting", "planting is TRANSPLANTED, recording rows per bed as " + i );
         set( tp_rows_p_bed, new Integer( i ), force );
      }
   }
   public void setRowsPerBed( String s ) { setRowsPerBed( s, false ); }
   public void setRowsPerBed( String s, boolean force ) { setRowsPerBed( parseInt(s), force ); }

   public int getInRowSpacing() { return get( PROP_INROW_SPACE, new Integer( -1 )).intValue(); }
   public String getInRowSpacingString() { return formatInt( getInRowSpacing() ); }
   public CPSDatumState getInRowSpacingState() { return getStateOf( PROP_INROW_SPACE ); }
   public void setInRowSpacing( int i ) { setInRowSpacing( i, false ); }
   public void setInRowSpacing( int i, boolean force ) { set( inrow_space, new Integer( i ), force ); }
   public void setInRowSpacing( String s ) { setInRowSpacing( s, false ); }
   public void setInRowSpacing( String s, boolean force ) { setInRowSpacing( parseInt(s), force ); }

   public int getRowSpacing() { 
      if ( isDirectSeeded() )
         return get( PROP_DS_ROW_SPACE, new Integer( -1 ) ).intValue();
      else
         return get( PROP_TP_ROW_SPACE, new Integer( -1 ) ).intValue();
   }
   public String getRowSpacingString() { return formatInt( getRowSpacing() ); }
   public CPSDatumState getRowSpacingState() {
      if ( isDirectSeeded() )
         return getStateOf( PROP_DS_ROW_SPACE );
      else
         return getStateOf( PROP_TP_ROW_SPACE );
   }
   public void setRowSpacing( int i ) { setRowSpacing( i, false ); }
   public void setRowSpacing( int i, boolean force ) {
      if ( isDirectSeeded() )
         set( ds_row_space, new Integer( i ), force );
      else
         set( tp_row_space, new Integer( i ), force );
   }
   public void setRowSpacing( String s ) { setRowSpacing( s, false ); }
   public void setRowSpacing( String s, boolean force ) { setRowSpacing( parseInt(s), force ); }

   public String getFlatSize() { return get( PROP_FLAT_SIZE, "" ); }
   public int getFlatSizeCapacity() {
      return CPSCalculations.extractFlatCapacity( getFlatSize() );
   }
   public CPSDatumState getFlatSizeState() { return getStateOf( PROP_FLAT_SIZE ); }
   public void setFlatSize( String i ) { setFlatSize( i, false ); }
   public void setFlatSize( String i, boolean force ) { set( flat_size, i, force ); }

   public String getPlantingNotesInherited() {
      if ( isDirectSeeded() )
         return get( PROP_DS_CROP_NOTES, "" );
      else
         return get( PROP_TP_CROP_NOTES, "" );
   }
   public CPSDatumState getPlantingNotesInheritedState() { 
      if ( isDirectSeeded() )
         return getStateOf( PROP_DS_CROP_NOTES );
      else
         return getStateOf( PROP_TP_CROP_NOTES );
   }
   public void setPlantingNotesInherited( String i ) { setPlantingNotesInherited( i, false ); }
   public void setPlantingNotesInherited( String i, boolean force ) {
      if ( isDirectSeeded() )
         set( ds_crop_notes, i, force );
      else
         set( tp_crop_notes, i, force );
   }

   public String getPlantingNotes() { return get( PROP_PLANTING_NOTES, "" ); }
   public CPSDatumState getPlantingNotesState() { return getStateOf( PROP_PLANTING_NOTES ); }
   public void setPlantingNotes( String i ) { setPlantingNotes( i, false ); }
   public void setPlantingNotes( String i, boolean force ) { set( planting_notes, i, force ); }


   /*
    * Calculated Values
    */
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
   public String getBedsToPlantString() { return formatFloat( getBedsToPlant(), 3 ); }
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

   public int getPlantsToStart() {
      CPSDatum s = getDatum( PROP_PLANTS_START );
      CPSDatum n = getDatum( PROP_PLANTS_NEEDED );
      
      /* if PLANTS_START valid, return
       * if PLANTS_NEEDED valid 
       */
      if ( ! s.isValid() && n.isAvailable() ) {
         getDatum( PROP_PLANTS_START ).setCalculated( true );
         return CPSCalculations.calcPlantsToStart( getPlantsNeeded() );
      }
      else
         return get( PROP_PLANTS_START, new Integer( -1 ) ).intValue();
   }
   public String getPlantsToStartString() { return formatInt( getPlantsToStart() ); }
   public CPSDatumState getPlantsToStartState() { return getStateOf( PROP_PLANTS_START ); }
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
   public String getFlatsNeededString() { return formatFloat( getFlatsNeeded(), 3 ); }
   public CPSDatumState getFlatsNeededState() { return getStateOf( PROP_FLATS_NEEDED ); }
   public void setFlatsNeeded( float i ) { setFlatsNeeded( i, false ); }
   public void setFlatsNeeded( float i, boolean force ) { set( flats_needed, new Float( i ), force ); }
   public void setFlatsNeeded( String s ) { setFlatsNeeded( s, false ); }
   public void setFlatsNeeded( String s, boolean force ) { setFlatsNeeded( parseFloat(s), force ); }

   /*
    * Yield Data
    */
   public float getYieldPerFoot() { return get( PROP_YIELD_P_FOOT, new Float( -1.0 )).floatValue(); }
   public String getYieldPerFootString() { return formatFloat( getYieldPerFoot(), 3 ); }
   public CPSDatumState getYieldPerFootState() { return getStateOf( PROP_YIELD_P_FOOT ); }
   public void setYieldPerFoot( float i ) { setYieldPerFoot( i, false ); }
   public void setYieldPerFoot( float i, boolean force ) { set( yield_p_foot, new Float( i ), force ); }
   public void setYieldPerFoot( String s ) { setYieldPerFoot( s, false ); }
   public void setYieldPerFoot( String s, boolean force ) { setYieldPerFoot( parseFloat(s), force ); }

   public int getYieldNumWeeks() { return get( PROP_YIELD_NUM_WEEKS, new Integer( -1 )).intValue(); }
   public String getYieldNumWeeksString() { return formatInt( getYieldNumWeeks() ); }
   public CPSDatumState getYieldNumWeeksState() { return getStateOf( PROP_YIELD_NUM_WEEKS ); }
   public void setYieldNumWeeks( int i ) { setYieldNumWeeks( i, false ); }
   public void setYieldNumWeeks( int i, boolean force ) { set( yield_num_weeks, new Integer( i ), force ); }
   public void setYieldNumWeeks( String s ) { setYieldNumWeeks( s, false ); }
   public void setYieldNumWeeks( String s, boolean force ) { setYieldNumWeeks( parseInt(s), force ); }

   public float getYieldPerWeek() { return get( PROP_YIELD_P_WEEK, new Float( -1.0 )).floatValue(); }
   public String getYieldPerWeekString() { return formatFloat( getYieldPerWeek(), 3 ); }
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
   public String getCropYieldUnitValueString() { return formatFloat( getCropYieldUnitValue(), 3 ); }
   public CPSDatumState getCropYieldUnitValueState() { return getStateOf( PROP_CROP_UNIT_VALUE ); }
   public void setCropYieldUnitValue( float i ) { setCropYieldUnitValue( i, false ); }
   public void setCropYieldUnitValue( float i, boolean force ) { set( crop_unit_value, new Float( i ), force ); }
   public void setCropYieldUnitValue( String s ) { setCropYieldUnitValue( s, false ); }
   public void setCropYieldUnitValue( String s, boolean force ) { setCropYieldUnitValue( parseFloat(s), force ); }

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
   public String getTotalYieldString() { return formatFloat( getTotalYield(), 3 ); }
   public CPSDatumState getTotalYieldState() { return getStateOf( PROP_TOTAL_YIELD ); }
   public void setTotalYield( float i ) { setTotalYield( i, false ); }
   public void setTotalYield( float i, boolean force ) { set( total_yield, new Float( i ), force ); }
   public void setTotalYield( String s ) { setTotalYield( s, false ); }
   public void setTotalYield( String s, boolean force ) { setTotalYield( parseFloat(s), force ); }

   /*
    * Misc Metadata
    */
   public boolean isDirectSeeded() { return get( PROP_DIRECT_SEED, new CPSBoolean(false)).booleanValue(); }
   public CPSDatumState getDirectSeededState() { return getStateOf( PROP_DIRECT_SEED ); }
   public void setDirectSeeded( String s ) {
      if ( s != null && s.equalsIgnoreCase("true") )
         setDirectSeeded( true );
      else
         setDirectSeeded( false );
   }
   public void setDirectSeeded( Boolean b ) { setDirectSeeded( b, false ); }
   public void setDirectSeeded( Boolean b, boolean force ) { set( direct_seed, new CPSBoolean(b), force ); }

   // public void setTransplanted ...

   public boolean isFrostHardy() { return get( PROP_FROST_HARDY, new CPSBoolean(false)).booleanValue(); }
   public boolean isFrostTender() { return ! isFrostHardy(); }
   public CPSDatumState getFrostHardyState() { return getStateOf( PROP_FROST_HARDY ); }
   public void setFrostHardy( String s ) {
      if ( s != null && s.equalsIgnoreCase( "true" ) )
         setFrostHardy( true );
      else
         setFrostHardy( false );
   }
   public void setFrostHardy( Boolean b ) { setFrostHardy( b, false ); }
   public void setFrostHardy( Boolean b, boolean force ) { set( frost_hardy, new CPSBoolean(b), force ); }

   public String getGroups() { return get( PROP_GROUPS, "" ); }
   public CPSDatumState getGroupsState() { return getStateOf( PROP_GROUPS ); }
   public void setGroups( String e) { setGroups( e, false ); }
   public void setGroups( String e, boolean force ) { set( groups, e, force ); }

   public String getKeywords() { return get( PROP_KEYWORDS, "" ); }
   public CPSDatumState getKeywordsState() { return getStateOf( PROP_KEYWORDS ); }
   public void setKeywords( String e) { setKeywords( e, false ); }
   public void setKeywords( String e, boolean force ) { set( keywords, e, force ); }

   public String getOtherRequirments() { return get( PROP_OTHER_REQ, "" ); }
   public CPSDatumState getOtherRequirmentsState() { return getStateOf( PROP_OTHER_REQ ); }
   public void setOtherRequirements( String e) { setOtherRequirements( e, false ); }
   public void setOtherRequirements( String e, boolean force ) { set( other_req, e, force ); }

   public String getNotes() { return get( PROP_NOTES, "" ); }
   public CPSDatumState getNotesState() { return getStateOf( PROP_NOTES ); }
   public void setNotes( String e) { setNotes( e, false ); }
   public void setNotes( String e, boolean force ) { set( notes, e, force ); }

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
   
   @Override
   public CPSRecord inheritFrom( CPSRecord thatRecord ) {
      // case 1: both objects are of same class
      if ( this.getClass().getName().equalsIgnoreCase( thatRecord.getClass().getName() ))
         return super.inheritFrom( thatRecord );
      // case 2: this object class != that object class AND it the other record isn't a CPSCrop
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
         return this.currentProp == PROP_ID      || this.currentProp == PROP_DATE_PLANT ||
                this.currentProp == PROP_DATE_TP || this.currentProp == PROP_DATE_HARVEST;
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
   }
   
   public String formatDate( Date d ) {
       if ( d.getTime() == 0 )
         return "";
       else
           return dateValidator.format( d );       
   }

}
