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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class CPSPlanting extends CPSRecord {

   // Core Data
   public static final int PROP_CROP_NAME     = CPSDataModelConstants.PROP_CROP_NAME;
   public static final int PROP_VAR_NAME      = CPSDataModelConstants.PROP_VAR_NAME;
   public static final int PROP_MATURITY      = CPSDataModelConstants.PROP_MATURITY;
   public static final int PROP_LOCATION      = CPSDataModelConstants.PROP_LOCATION;

   // Dates
   // "effective" dates
   public static final int PROP_DATE_PLANT    = CPSDataModelConstants.PROP_DATE_PLANT;
   public static final int PROP_DATE_TP       = CPSDataModelConstants.PROP_DATE_TP;
   public static final int PROP_DATE_HARVEST  = CPSDataModelConstants.PROP_DATE_HARVEST;
   // planned dates
   public static final int PROP_DATE_PLANT_PLAN = CPSDataModelConstants.PROP_DATE_PLANT_PLAN;
   public static final int PROP_DATE_TP_PLAN = CPSDataModelConstants.PROP_DATE_TP_PLAN;
   public static final int PROP_DATE_HARVEST_PLAN = CPSDataModelConstants.PROP_DATE_HARVEST_PLAN;
   // actual dates
   public static final int PROP_DATE_PLANT_ACTUAL = CPSDataModelConstants.PROP_DATE_PLANT_ACTUAL;
   public static final int PROP_DATE_TP_ACTUAL = CPSDataModelConstants.PROP_DATE_TP_ACTUAL;
   public static final int PROP_DATE_HARVEST_ACTUAL = CPSDataModelConstants.PROP_DATE_HARVEST_ACTUAL;

   // Status Booleans
   public static final int PROP_DONE_PLANTING = CPSDataModelConstants.PROP_DONE_PLANTING;
   public static final int PROP_DONE_TP       = CPSDataModelConstants.PROP_DONE_TP;
   public static final int PROP_DONE_HARVEST  = CPSDataModelConstants.PROP_DONE_HARVEST;
   public static final int PROP_IGNORE = CPSDataModelConstants.PROP_IGNORE;
   
   // Static Data
   // inheritable
   public    static final int PROP_MAT_ADJUST    = CPSDataModelConstants.PROP_MAT_ADJUST;
   protected static final int PROP_DS_MAT_ADJUST = CPSDataModelConstants.PROP_DS_MAT_ADJUST;
   protected static final int PROP_TP_MAT_ADJUST = CPSDataModelConstants.PROP_TP_MAT_ADJUST;
   public    static final int PROP_ROWS_P_BED    = CPSDataModelConstants.PROP_ROWS_P_BED;
   protected static final int PROP_DS_ROWS_P_BED = CPSDataModelConstants.PROP_DS_ROWS_P_BED;
   protected static final int PROP_TP_ROWS_P_BED = CPSDataModelConstants.PROP_TP_ROWS_P_BED;
   public    static final int PROP_ROW_SPACE     = CPSDataModelConstants.PROP_SPACE_BETROW;
   protected static final int PROP_DS_ROW_SPACE  = CPSDataModelConstants.PROP_DS_SPACE_BETROW;
   protected static final int PROP_TP_ROW_SPACE  = CPSDataModelConstants.PROP_TP_SPACE_BETROW;
   public    static final int PROP_CROP_NOTES    = CPSDataModelConstants.PROP_PLANT_NOTES; // Inherited
   protected static final int PROP_DS_CROP_NOTES = CPSDataModelConstants.PROP_DS_PLANT_NOTES;
   protected static final int PROP_TP_CROP_NOTES = CPSDataModelConstants.PROP_TP_PLANT_NOTES;

   public static final int PROP_TIME_TO_TP       = CPSDataModelConstants.PROP_TIME_TO_TP;
   public static final int PROP_INROW_SPACE   = CPSDataModelConstants.PROP_SPACE_INROW;
   public static final int PROP_FLAT_SIZE     = CPSDataModelConstants.PROP_FLAT_SIZE;
   public static final int PROP_PLANTING_NOTES = CPSDataModelConstants.PROP_PLANT_NOTES_SPECIFIC; // Not inherited

   // Calculated Data
   public static final int PROP_BEDS_PLANT    = CPSDataModelConstants.PROP_BEDS_PLANT;
   public static final int PROP_PLANTS_NEEDED = CPSDataModelConstants.PROP_PLANTS_NEEDED;
   public static final int PROP_ROWFT_PLANT   = CPSDataModelConstants.PROP_ROWFT_PLANT;
   public static final int PROP_PLANTS_START  = CPSDataModelConstants.PROP_PLANTS_START;
   public static final int PROP_FLATS_NEEDED  = CPSDataModelConstants.PROP_FLATS_NEEDED;

   // Yield
   // static
   public static final int PROP_YIELD_P_FOOT  = CPSDataModelConstants.PROP_YIELD_P_FOOT;
   public static final int PROP_YIELD_NUM_WEEKS = CPSDataModelConstants.PROP_YIELD_NUM_WEEKS;
   public static final int PROP_YIELD_P_WEEK  = CPSDataModelConstants.PROP_YIELD_P_WEEK;
   public static final int PROP_CROP_UNIT     = CPSDataModelConstants.PROP_CROP_UNIT;
   public static final int PROP_CROP_UNIT_VALUE = CPSDataModelConstants.PROP_CROP_UNIT_VALUE;
   // calculated
   public static final int PROP_TOTAL_YIELD   = CPSDataModelConstants.PROP_TOTAL_YIELD;

   // Misc Metadata
   // bools
   public static final int PROP_DIRECT_SEED   = CPSDataModelConstants.PROP_DIRECT_SEED;
   public static final int PROP_FROST_HARDY   = CPSDataModelConstants.PROP_FROST_HARDY;
   // Strings
   public static final int PROP_GROUPS        = CPSDataModelConstants.PROP_GROUPS;
   public static final int PROP_KEYWORDS      = CPSDataModelConstants.PROP_KEYWORDS;
   public static final int PROP_OTHER_REQ     = CPSDataModelConstants.PROP_OTHER_REQ;
   public static final int PROP_NOTES         = CPSDataModelConstants.PROP_NOTES;

   public static final int PROP_CUSTOM1       = CPSDataModelConstants.PROP_CUSTOM1;
   public static final int PROP_CUSTOM2       = CPSDataModelConstants.PROP_CUSTOM2;
   public static final int PROP_CUSTOM3       = CPSDataModelConstants.PROP_CUSTOM3;
   public static final int PROP_CUSTOM4       = CPSDataModelConstants.PROP_CUSTOM4;
   public static final int PROP_CUSTOM5       = CPSDataModelConstants.PROP_CUSTOM5;

   
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

   private CPSDatum<Boolean> direct_seed;
   private CPSDatum<Boolean> frost_hardy;

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
      
      // name, desc, value, nullValue, blankValue, propNum, inherited, calculated

      recordID = new CPSDatum<Integer>( "ID", "Unique ID", null, PROP_ID );
      commonIDs = new CPSDatum<ArrayList<Integer>>( "Common IDs", "Planting IDs represented", new ArrayList(), PROP_COMMON_ID );
      
      crop_name = new CPSDatum<String>( "Crop", "Name of crop to be planted", "", PROP_CROP_NAME );
      var_name = new CPSDatum<String>( "Variety", "Name of variety to be planted", "", PROP_VAR_NAME );
      maturity = new CPSDatum<Integer>( "Maturity Days", new Integer(-1), PROP_MATURITY );
      location = new CPSDatum<String>( "Location", "Where will this be planted", "", PROP_LOCATION );

      date_plant = new CPSDatum<Date>( "Planting Date", "When this will be seeded in the greenhouse or field", new Date( 0 ), PROP_DATE_PLANT  );
      date_tp = new CPSDatum<Date>( "Transplant Date", "When this will be transplanted to the field", new Date( 0 ), PROP_DATE_TP );
      date_harvest = new CPSDatum<Date>( "Harvest Date", "When this will be harvested", new Date( 0 ), PROP_DATE_HARVEST );

      date_plant_plan = new CPSDatum<Date>( "Planting Date (Planned)", "When this will be seeded in the greenhouse or field", new Date( 0 ), PROP_DATE_PLANT_PLAN );
      date_tp_plan = new CPSDatum<Date>( "Transplant Date (Planned)", "When this will be transplanted to the field", new Date( 0 ), PROP_DATE_TP_PLAN );
      date_harvest_plan = new CPSDatum<Date>( "Harvest Date (Planned)", "When this will be harvested", new Date( 0 ), PROP_DATE_HARVEST_PLAN );

      date_plant_actual = new CPSDatum<Date>( "Planting Date (Actual)", "When this will be seeded in the greenhouse or field", new Date( 0 ), PROP_DATE_PLANT_ACTUAL );
      date_tp_actual = new CPSDatum<Date>( "Transplant Date (Actual)", "When this will be transplanted to the field", new Date( 0 ), PROP_DATE_TP_ACTUAL );
      date_harvest_actual = new CPSDatum<Date>( "Harvest Date (Actual)", "When this will be harvested", new Date( 0 ), PROP_DATE_HARVEST_ACTUAL );

      done_plant = new CPSDatum<Boolean>( "Planted?", "Has this been planted?", new Boolean( false ), PROP_DONE_PLANTING);
      done_tp = new CPSDatum<Boolean>( "Transplanted?", "Has this been transplanted?", new Boolean( false ), PROP_DONE_TP );
      done_harvest = new CPSDatum<Boolean>( "Harvested?", "Has this been harvested", new Boolean( false ), PROP_DONE_HARVEST );
      ignore = new CPSDatum<Boolean>( "Ignore?", "Ignore this planting", new Boolean( false ), PROP_IGNORE );

      ds_mat_adjust = new CPSDatum<Integer>( "DS Mat. Adj.", new Integer(-1), PROP_DS_MAT_ADJUST );
      tp_mat_adjust = new CPSDatum<Integer>( "TP Mat. Adj.", new Integer(-1), PROP_TP_MAT_ADJUST );
      time_to_tp = new CPSDatum<Integer>( "Weeks to TP", new Integer( -1 ), PROP_TIME_TO_TP );
      ds_rows_p_bed = new CPSDatum<Integer>( "DS Rows/Bed", new Integer(-1), PROP_DS_ROWS_P_BED );
      tp_rows_p_bed = new CPSDatum<Integer>( "TP Rows/Bed", new Integer(-1), PROP_TP_ROWS_P_BED );
      inrow_space = new CPSDatum<Integer>( "In-row Spacing", new Integer( -1 ), PROP_INROW_SPACE );
      ds_row_space = new CPSDatum<Integer>( "DS Row Spacing", new Integer( -1 ), PROP_DS_ROW_SPACE );
      tp_row_space = new CPSDatum<Integer>( "TP Row Spacing", new Integer( -1 ), PROP_TP_ROW_SPACE );
      flat_size = new CPSDatum<String>( "Flat size", "", PROP_FLAT_SIZE );
      ds_crop_notes = new CPSDatum<String>( "DS Planting Notes (from CropDB)", "", PROP_DS_CROP_NOTES );
      tp_crop_notes = new CPSDatum<String>( "TP Planting Notes (from CropDB)", "", PROP_TP_CROP_NOTES );
      planting_notes = new CPSDatum<String>( "Planting Notes", "", PROP_PLANTING_NOTES );

      beds_to_plant = new CPSDatum<Float>( "Num. Beds to Plants", new Float(-1.0), PROP_BEDS_PLANT );
      plants_needed = new CPSDatum<Integer>( "Num. Plants Needed", new Integer(-1), PROP_PLANTS_NEEDED );
      rowft_to_plant = new CPSDatum<Integer>( "Row Feet To Plant", new Integer(-1), PROP_ROWFT_PLANT );
      plants_to_start = new CPSDatum<Integer>( "Num. Plants to Start", new Integer(-1), PROP_PLANTS_START );
      flats_needed = new CPSDatum<Float>( "Num. Flats Needed", new Float(-1.0), PROP_FLATS_NEEDED );
            
      yield_p_foot = new CPSDatum<Float>( "Yield/Ft", new Float(-1.0), PROP_YIELD_P_FOOT );
      yield_num_weeks = new CPSDatum<Integer>( "Will Yield for (weeks)", new Integer(-1), PROP_YIELD_NUM_WEEKS );
      yield_p_week = new CPSDatum<Float>( "Yield/Week", new Float(-1.0), PROP_YIELD_P_WEEK );
      crop_unit = new CPSDatum<String>( "Unit of Yield", "", PROP_CROP_UNIT );
      crop_unit_value = new CPSDatum<Float>( "Value per Yield Unit", new Float(-1.0), PROP_CROP_UNIT_VALUE );
      total_yield = new CPSDatum<Float>( "Total Yield", new Float( -1.0 ), PROP_TOTAL_YIELD );

      direct_seed = new CPSDatum<Boolean>( "Direct seeded?", new Boolean( true ), PROP_DIRECT_SEED );
      frost_hardy = new CPSDatum<Boolean>( "Frost hardy?", new Boolean( false ) , PROP_FROST_HARDY );

      groups = new CPSDatum<String>( "Groups", "", PROP_GROUPS );
      keywords = new CPSDatum<String>( "Keywords", "", PROP_KEYWORDS );
      other_req = new CPSDatum<String>( "Other Requirements", "", PROP_OTHER_REQ );
      notes = new CPSDatum<String>( "Notes", "", PROP_NOTES );

      custom1 = new CPSDatum<String>( "Custom Field 1", "", PROP_CUSTOM1 );
      custom2 = new CPSDatum<String>( "Custom Field 2", "", PROP_CUSTOM2 );
      custom3 = new CPSDatum<String>( "Custom Field 3", "", PROP_CUSTOM3 );
      custom4 = new CPSDatum<String>( "Custom Field 4", "", PROP_CUSTOM4 );
      custom5 = new CPSDatum<String>( "Custom Field 5", "", PROP_CUSTOM5 );
      
      dateValidator = new CPSDateValidator();
      
   }


  @Override
   public CPSDatum getDatum(int prop) {
   
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
             if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
                return getDatum( PROP_DS_MAT_ADJUST );
             else
                return getDatum( PROP_TP_MAT_ADJUST );
          case PROP_DS_MAT_ADJUST:    return ds_mat_adjust;
          case PROP_TP_MAT_ADJUST:    return tp_mat_adjust;
          case PROP_TIME_TO_TP:    return time_to_tp;
          case PROP_ROWS_P_BED:    
             if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
                return getDatum( PROP_DS_ROWS_P_BED );
             else
                return getDatum( PROP_TP_ROWS_P_BED );
          case PROP_DS_ROWS_P_BED:    return ds_rows_p_bed;
          case PROP_TP_ROWS_P_BED:    return tp_rows_p_bed;
          case PROP_INROW_SPACE:   return inrow_space;
          case PROP_ROW_SPACE:
             if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
                return getDatum( PROP_DS_ROW_SPACE );
             else
                return getDatum( PROP_TP_ROW_SPACE );
          case PROP_DS_ROW_SPACE:     return ds_row_space;
          case PROP_TP_ROW_SPACE:     return tp_row_space;
          case PROP_FLAT_SIZE:     return flat_size;
          case PROP_CROP_NOTES:
             if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
                return getDatum( PROP_DS_CROP_NOTES );
             else
                return getDatum( PROP_TP_CROP_NOTES );
          case PROP_DS_CROP_NOTES:       return ds_crop_notes;
          case PROP_TP_CROP_NOTES:       return tp_crop_notes;
          case PROP_PLANTING_NOTES: return planting_notes;

          /* calculated data */
          case PROP_BEDS_PLANT:    return beds_to_plant;
          case PROP_PLANTS_NEEDED: return plants_needed;
          case PROP_ROWFT_PLANT:   return rowft_to_plant;
          case PROP_PLANTS_START:  return plants_to_start;
          case PROP_FLATS_NEEDED:  return flats_needed;
          
          case PROP_YIELD_P_FOOT:    return yield_p_foot;
          case PROP_YIELD_NUM_WEEKS: return yield_num_weeks;
          case PROP_YIELD_P_WEEK:    return yield_p_week;
          case PROP_CROP_UNIT:       return crop_unit;
          case PROP_CROP_UNIT_VALUE: return crop_unit_value;
          case PROP_TOTAL_YIELD:     return total_yield;

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
          
          default:                 return super.getDatum( prop );

       }

   }
   
   public List<Integer> getListOfInheritableProperties() {
      if ( listOfInheritableProperties == null ) {
        listOfInheritableProperties = new ArrayList<Integer>();
        listOfInheritableProperties.add( PROP_MATURITY );
        listOfInheritableProperties.add( PROP_DS_MAT_ADJUST );
        listOfInheritableProperties.add( PROP_TP_MAT_ADJUST );
        listOfInheritableProperties.add( PROP_TIME_TO_TP );
        listOfInheritableProperties.add( PROP_DS_ROWS_P_BED );
        listOfInheritableProperties.add( PROP_TP_ROWS_P_BED );
        listOfInheritableProperties.add( PROP_INROW_SPACE );
        listOfInheritableProperties.add( PROP_DS_ROW_SPACE );
        listOfInheritableProperties.add( PROP_TP_ROW_SPACE );
        listOfInheritableProperties.add( PROP_FLAT_SIZE );
        listOfInheritableProperties.add( PROP_DS_CROP_NOTES );
        listOfInheritableProperties.add( PROP_TP_CROP_NOTES );
        listOfInheritableProperties.add( PROP_YIELD_P_FOOT );
        listOfInheritableProperties.add( PROP_YIELD_NUM_WEEKS );
        listOfInheritableProperties.add( PROP_YIELD_P_WEEK );
        listOfInheritableProperties.add( PROP_CROP_UNIT );
        listOfInheritableProperties.add( PROP_CROP_UNIT_VALUE );
        listOfInheritableProperties.add( PROP_FROST_HARDY  );
        listOfInheritableProperties.add( PROP_OTHER_REQ );
      }
      return listOfInheritableProperties;
   }

   @Override
   public void finishUp () {
   
   }

   @Override
   protected <T> void set( CPSDatum<T> d, T v ) {
     if ( d.isLocked() )
       return;
     
     super.set( d, v );
     d.lock();
     updateCalculations( d.getPropertyNum() );
     d.unlock();
   }


   
   /* *********************************************************************************************/
   /* GETTERS and SETTERS
   /* *********************************************************************************************/
   
   public String getCropName() { return get(PROP_CROP_NAME); }
   public CPSDatumState getCropNameState() { return getStateOf( PROP_CROP_NAME ); }
   public void setCropName(String s) { set( crop_name, s ); }

   public String getVarietyName() { return get( PROP_VAR_NAME ); }
   public CPSDatumState getVarietyNameState() { return getStateOf( PROP_VAR_NAME ); }
   public void setVarietyName( String s ) { set( var_name, s ); }

   public Integer getMaturityDays() { return getInt( PROP_MATURITY ); }
   public String getMaturityDaysString() { return getString( PROP_MATURITY ); }
   public CPSDatumState getMaturityDaysState() { return getStateOf( PROP_MATURITY ); }
   public void setMaturityDays( Integer i ) { set( maturity, i ); }
   public void setMaturityDays( int i ) { setMaturityDays( new Integer( i ) ); }
   public void setMaturityDays( String s ) { setMaturityDays( parseInteger( s ) ); }

   public String getLocation() { return get( PROP_LOCATION ); }
   public CPSDatumState getLocationState() { return getStateOf( PROP_LOCATION ); }
   public void setLocation( String s ) { set( location, s ); }

   /* *********************************************************************************************/
   /* Dates */
   /* *********************************************************************************************/
   protected Date getEffectiveDate( int prop_effective, int prop_actual, int prop_plan ) {
      CPSDatum e = getDatum( prop_effective );
      CPSDatum p = getDatum( prop_plan );
      CPSDatum a = getDatum( prop_actual );

       /* If date_plant_actual is valid, return it
        * else return date_plant_plan
        * or just return a default */
       if ( this.isSingleRecord() && a.isNotNull() ) {

          e.setState( a.getState() );

          switch ( prop_actual ) {
             case PROP_DATE_PLANT_ACTUAL:   return getDateToPlantActual();
             case PROP_DATE_TP_ACTUAL:      return getDateToTPActual();
             case PROP_DATE_HARVEST_ACTUAL: return getDateToHarvestActual();
             default:                       return new Date(0);
          }
       }
       else if ( this.isSingleRecord() && p.isNotNull() ) {
          e.setState( p.getState() );
          switch ( prop_plan ) {
             case PROP_DATE_PLANT_PLAN:   return getDateToPlantPlanned();
             case PROP_DATE_TP_PLAN:      return getDateToTPPlanned();
             case PROP_DATE_HARVEST_PLAN: return getDateToHarvestPlanned();
             default:                     return new Date(0);
          }
       }
       else
          return get( prop_effective );
      
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
      CPSDatum w = getDatum( PROP_TIME_TO_TP );

       /* Only calculate the planting date if:
        * DATE_PLANT is *NOT* valid AND
        * DATE_HARVEST AND MATURITY *ARE* valid
        * otherwise just return the planting date or a default */
       if ( this.isSingleRecord() &&
            ! plant.isConcrete() &&
              tp.isNotNull() && w.isNotNull() ) {
          set( plant,  CPSCalculations.calcDatePlantFromDateTP( (Date) tp.getValue(),
                                                                   w.getValueAsInt() ));
          plant.setCalculated( true );
       }
       else if ( this.isSingleRecord() &&
                 ! plant.isConcrete()  &&
                   harv.isNotNull()    &&
                   m.isNotNull() ) {
          try {
            set( plant, CPSCalculations.calcDatePlantFromDateHarvest( (Date) harv.getValue(),
                                                                        m.getValueAsInt(),
                                                                        getMatAdjust(),
                                                                        w.getValueAsInt() ));
            plant.setCalculated( true );
          } catch ( NullPointerException e ) { /* basically, leave plant as it was */ }
       }
       
      return (Date) get( prop_plant );
//      return (Date) plant.getValue();
   }

   public Date getDateToPlant() {
      return getEffectiveDate( PROP_DATE_PLANT, PROP_DATE_PLANT_ACTUAL, PROP_DATE_PLANT_PLAN );
   }
   public String getDateToPlantString() { return formatDate( getDateToPlant() ); }
   public CPSDatumState getDateToPlantState() { return getStateOf( PROP_DATE_PLANT ); }

   public Date getDateToPlantPlanned() {
      return getDateToPlantAbstract( DATE_TYPE_PLANNED );
   }
   public String getDateToPlantPlannedString() { return formatDate( getDateToPlantPlanned() ); }
   public CPSDatumState getDateToPlantPlannedState() { return getStateOf( PROP_DATE_PLANT_PLAN ); }
   public void setDateToPlantPlanned( Date d ) { set( date_plant_plan, d ); }
   public void setDateToPlantPlanned( String d ) { setDateToPlantPlanned( parseDate(d) ); }

   public Date getDateToPlantActual() {
      return getDateToPlantAbstract( DATE_TYPE_ACTUAL );
   }
   public String getDateToPlantActualString() { return formatDate( getDateToPlantActual() ); }
   public CPSDatumState getDateToPlantActualState() { return getStateOf( PROP_DATE_PLANT_ACTUAL ); }
   public void setDateToPlantActual( Date d ) { set( date_plant_actual, d ); }
   public void setDateToPlantActual( String d ) { setDateToPlantActual( parseDate(d) ); }


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
           ! t.isConcrete() &&
             p.isNotNull() && w.isNotNull() ) {
         set( t, CPSCalculations.calcDateTPFromDatePlant( (Date) p.getValue(),
                                                              w.getValueAsInt() ) );
         t.setCalculated( true );
         
      }
      else if ( this.isSingleRecord() &&
                ! t.isConcrete() &&
                  w.isNotNull() &&
                  h.isNotNull() && m.isNotNull() ) {
        try {
          set( t, CPSCalculations.calcDateTPFromDateHarvest( (Date) h.getValue(),
                                                                m.getValueAsInt(),
                                                                getMatAdjust() ) );
          t.setCalculated( true );
        } catch ( NullPointerException e ) { /* basically, leave t as it was */ }
      }
      
      return (Date) get( prop_tp );
//      return (Date) t.getValue();
   }

   public Date getDateToTP() {
      return getEffectiveDate( PROP_DATE_TP, PROP_DATE_TP_ACTUAL, PROP_DATE_TP_PLAN );
   }
   public String getDateToTPString() { return formatDate( getDateToTP() ); }
   public CPSDatumState getDateToTPState() { return getStateOf( PROP_DATE_TP ); }

   public Date getDateToTPPlanned () {
      return getDateToTPAbstract( DATE_TYPE_PLANNED );
   }
   public String getDateToTPPlannedString() { return formatDate( getDateToTPPlanned() ); }
   public CPSDatumState getDateToTPPlannedState() { return getStateOf( PROP_DATE_TP_PLAN ); }
   public void setDateToTPPlanned( Date d ) { set( date_tp_plan, d ); }
   public void setDateToTPPlanned( String d ) { setDateToTPPlanned( parseDate( d ) ); }

   public Date getDateToTPActual() {
      return getDateToTPAbstract( DATE_TYPE_ACTUAL );
   }
   public String getDateToTPActualString() { return formatDate( getDateToTPActual() ); }
   public CPSDatumState getDateToTPActualState() { return getStateOf( PROP_DATE_TP_ACTUAL ); }
   public void setDateToTPActual( Date d ) { set( date_tp_actual, d ); }
   public void setDateToTPActual( String d ) { setDateToTPActual( parseDate( d ) ); }


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
              h.isNull() &&
              t.isNotNull() && m.isNotNull() ) {
          try {
            set( h, CPSCalculations.calcDateHarvestFromDateTP( (Date) t.getValue(),
                                                                   m.getValueAsInt(),
                                                                   getMatAdjust() ) );
            h.setCalculated( true );
          } catch ( NullPointerException e ) { /* basically, leave h as null */ }
       }
       else if ( this.isSingleRecord() &&
                   h.isNull() &&
                   p.isNotNull() && m.isNotNull() ) {
          try {
            set( h, CPSCalculations.calcDateHarvestFromDatePlant( (Date) p.getValue(),
                                                                      m.getValueAsInt(),
                                                                      getMatAdjust(),
                                                                      w.getValueAsInt() ));
            h.setCalculated( true );
          } catch ( NullPointerException e ) { /* basically, leave h as null */ }
       }

      return (Date) get( prop_harv );
//      return (Date) h.getValue();
   }

   public Date getDateToHarvest() {
      return getEffectiveDate( PROP_DATE_HARVEST, PROP_DATE_HARVEST_ACTUAL, PROP_DATE_HARVEST_PLAN );
   }
   public String getDateToHarvestString() { return formatDate( getDateToHarvest() ); }
   public CPSDatumState getDateToHarvestState() { return getStateOf( PROP_DATE_HARVEST ); }

   public Date getDateToHarvestPlanned() { return getDateToHarvestAbstract( DATE_TYPE_PLANNED ); }
   public String getDateToHarvestPlannedString() { return formatDate( getDateToHarvestPlanned() ); }
   public CPSDatumState getDateToHarvestPlannedState() { return getStateOf( PROP_DATE_HARVEST_PLAN ); }
   public void setDateToHarvestPlanned( Date d ) { set( date_harvest_plan, d ); }
   public void setDateToHarvestPlanned( String d ) { setDateToHarvestPlanned( parseDate( d ) ); }

   public Date getDateToHarvestActual() { return getDateToHarvestAbstract( DATE_TYPE_ACTUAL ); }
   public String getDateToHarvestActualString() { return formatDate( getDateToHarvestActual() ); }
   public CPSDatumState getDateToHarvestActualState() { return getStateOf( PROP_DATE_HARVEST_ACTUAL ); }
   public void setDateToHarvestActual( Date d ) { set( date_harvest_actual, d ); }
   public void setDateToHarvestActual( String d ) { setDateToHarvestActual( parseDate( d ) ); }

   /* *********************************************************************************************/
   /* Status Booleans */
   /* *********************************************************************************************/
   public Boolean getDonePlanting() { return get( PROP_DONE_PLANTING ); }
   public CPSDatumState getDonePlantingState() { return getStateOf( PROP_DONE_PLANTING  ); }
   public void setDonePlanting( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDonePlanting( true );
      else
         setDonePlanting( false );
   }
   public void setDonePlanting( Boolean b ) { set( done_plant, b ); }
   public void setDonePlanting( boolean b ) { set( done_plant, new Boolean( b ) ); }

   public Boolean getDoneTP() { return get( PROP_DONE_TP ); }
   public CPSDatumState getDoneTPState() { return getStateOf( PROP_DONE_TP  ); }
   public void setDoneTP( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDoneTP( true );
      else
         setDoneTP( false );
   }
   public void setDoneTP( Boolean b ) { set( done_tp, b ); }
   public void setDoneTP( boolean b ) { set( done_tp, new Boolean( b ) ); }

   public Boolean getDoneHarvest() { return get( PROP_DONE_HARVEST ); }
   public CPSDatumState getDoneHarvestState() { return getStateOf( PROP_DONE_HARVEST  ); }
   public void setDoneHarvest( String s ) { 
      if ( s != null && s.equalsIgnoreCase("true") )
         setDoneHarvest( true );
      else
         setDoneHarvest( false );
   }
   public void setDoneHarvest( Boolean b ) { set( done_harvest, b ); }
   public void setDoneHarvest( boolean b ) { set( done_harvest, new Boolean( b ) ); }

   public Boolean getIgnore() { return get( PROP_IGNORE ); }
   public CPSDatumState getIgnoreState() { return getStateOf( PROP_IGNORE  ); }
   public void setIgnore( String s ) {
      if ( s != null && s.equalsIgnoreCase("true") )
         setIgnore( true );
      else
         setIgnore( false );
   }
   public void setIgnore( Boolean b ) { set( ignore, b ); }
   public void setIgnore( boolean b ) { set( ignore, new Boolean( b ) ); }


   /* *********************************************************************************************/
   /* Static Data */
   /* *********************************************************************************************/
   public Integer getMatAdjust() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getInt( PROP_DS_MAT_ADJUST );
      else
         return getInt( PROP_TP_MAT_ADJUST );
   }
   public String getMatAdjustString() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getString( PROP_DS_MAT_ADJUST );
      else
         return getString( PROP_TP_MAT_ADJUST );
   }
   public CPSDatumState getMatAdjustState() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getStateOf( PROP_DS_MAT_ADJUST );
      else
         return getStateOf( PROP_TP_MAT_ADJUST );
   }
   public void setMatAdjust( Integer i ) {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         set( ds_mat_adjust, i );
      else
         set( tp_mat_adjust, i );
   }
   public void setMatAdjust( int i ) { setMatAdjust( new Integer( i )); }
   public void setMatAdjust( String s ) { setMatAdjust( parseInteger(s) ); }

   public Integer getTimeToTP() { return getInt( PROP_TIME_TO_TP ); }
   public String getTimeToTPString() { return getString( PROP_TIME_TO_TP ); }
   public CPSDatumState getTimeToTPState() { return getStateOf( PROP_TIME_TO_TP ); }
   public void setTimeToTP( Integer i ) { set( time_to_tp, i ); }
   public void setTimeToTP( int i ) { setTimeToTP( new Integer( i ) ); }
   public void setTimeToTP( String s ) { setTimeToTP( parseInteger(s) ); }

   public CPSDatumState getRowsPerBedState() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getStateOf( PROP_DS_ROWS_P_BED );
      else
         return getStateOf( PROP_TP_ROWS_P_BED );
   }
   public Integer getRowsPerBed() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getInt( PROP_DS_ROWS_P_BED );
      else
         return getInt( PROP_TP_ROWS_P_BED );
   }
   public String getRowsPerBedString() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getString( PROP_DS_ROWS_P_BED );
      else
         return getString( PROP_TP_ROWS_P_BED );
   }
   public void setRowsPerBed( Integer i ) {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() ) {
         set( ds_rows_p_bed, i );
      }
      else {
         set( tp_rows_p_bed, i );
      }
   }
   public void setRowsPerBed( int i ) { setRowsPerBed( new Integer( i )); }
   public void setRowsPerBed( String s ) { setRowsPerBed( parseInteger(s) ); }

   public Integer getInRowSpacing() { return getInt( PROP_INROW_SPACE ); }
   public String getInRowSpacingString() { return getString( PROP_INROW_SPACE ); }
   public CPSDatumState getInRowSpacingState() { return getStateOf( PROP_INROW_SPACE ); }
   public void setInRowSpacing( Integer i ) { set( inrow_space, i ); }
   public void setInRowSpacing( int i ) { setInRowSpacing( new Integer( i ) ); }
   public void setInRowSpacing( String s ) { setInRowSpacing( parseInteger(s) ); }

   public Integer getRowSpacing() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getInt( PROP_DS_ROW_SPACE );
      else
         return getInt( PROP_TP_ROW_SPACE );
   }
   public String getRowSpacingString() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getString( PROP_DS_ROW_SPACE );
      else
         return getString( PROP_TP_ROW_SPACE );
   }
   public CPSDatumState getRowSpacingState() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getStateOf( PROP_DS_ROW_SPACE );
      else
         return getStateOf( PROP_TP_ROW_SPACE );
   }
   public void setRowSpacing( Integer i ) {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         set( ds_row_space, i );
      else
         set( tp_row_space, i );
   }
   public void setRowSpacing( int i ) { setRowSpacing( new Integer( i ) ); }
   public void setRowSpacing( String s ) { setRowSpacing( parseInteger(s) ); }

   public String getFlatSize() { return get( PROP_FLAT_SIZE ); }
   public Integer getFlatSizeCapacity() {
      return CPSCalculations.extractFlatCapacity( getFlatSize() );
   }
   public CPSDatumState getFlatSizeState() { return getStateOf( PROP_FLAT_SIZE ); }
   public void setFlatSize( String i ) { set( flat_size, i ); }

   public String getPlantingNotesInherited() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return get( PROP_DS_CROP_NOTES );
      else
         return get( PROP_TP_CROP_NOTES );
   }
   public CPSDatumState getPlantingNotesInheritedState() { 
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getStateOf( PROP_DS_CROP_NOTES );
      else
         return getStateOf( PROP_TP_CROP_NOTES );
   }
   public void setPlantingNotesInherited( String i ) {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         set( ds_crop_notes, i );
      else
         set( tp_crop_notes, i );
   }

   public String getPlantingNotes() { return get( PROP_PLANTING_NOTES ); }
   public CPSDatumState getPlantingNotesState() { return getStateOf( PROP_PLANTING_NOTES ); }
   public void setPlantingNotes( String i ) { set( planting_notes, i ); }


/* *********************************************************************************************/
/* Calculated Values */
/* *********************************************************************************************/
   protected CPSDatum getBedsToPlantDatum() { return getBedsToPlantDatum( new ArrayList() ); }
   protected CPSDatum getBedsToPlantDatum( List source_path ) {

      CPSDatum b = getDatum( PROP_BEDS_PLANT );

      if ( b.isConcrete() || source_path.contains( b.propertyNum ) )
        return b;
      
      source_path.add( b.propertyNum );

      if ( ! source_path.contains( PROP_ROWS_P_BED )) {

        CPSDatum rpb = getDatum( PROP_ROWS_P_BED );
        CPSDatum r = getRowFtToPlantDatum( source_path );
        int bedLength = CPSCalculations.extractBedLength( getLocation() );

        if ( r.isNotNull() && rpb.isNotNull() ) {
           set( b, CPSCalculations.calcBedsToPlantFromRowFtToPlant( r.getValueAsInt(),
                                                                        rpb.getValueAsInt(),
                                                                        bedLength ));
           b.setCalculated( true );
        }
      }
      
      return b;
   }
   public Float getBedsToPlant() { return (Float) getBedsToPlantDatum().getValue( useRawOutput() ); }
   public String getBedsToPlantString() { return formatFloat( getBedsToPlant(), 3 ); }
   public CPSDatumState getBedsToPlantState() { return getStateOf( PROP_BEDS_PLANT ); }
   public void setBedsToPlant( Float i ) { set( beds_to_plant, i ); }
   public void setBedsToPlant( float i ) { setBedsToPlant( new Float( i ) ); }
   public void setBedsToPlant( String s ) { setBedsToPlant( parseFloatBigF(s) ); }


//****************************************************************************//
   protected CPSDatum getPlantsNeededDatum() { return getPlantsNeededDatum( new ArrayList() ); }
   protected CPSDatum getPlantsNeededDatum( List source_path ) {

      CPSDatum p = getDatum( PROP_PLANTS_NEEDED );

      if ( p.isConcrete() || source_path.contains( p.propertyNum ))
        return p;

      source_path.add( p.propertyNum );


      if ( ! source_path.contains( PROP_ROWFT_PLANT )) {

        CPSDatum irs = getDatum( PROP_INROW_SPACE );
        CPSDatum r = getRowFtToPlantDatum( source_path );

        if ( r.isNotNull() && irs.isNotNull() ) {
           set( p, CPSCalculations.calcPlantsNeededFromRowFtToPlant( r.getValueAsInt(),
                                                                         irs.getValueAsInt() ) );
           p.setCalculated( true );
        }
      }
      else if ( ! source_path.contains( PROP_PLANTS_START )) {

        CPSDatum ps = getPlantsToStartDatum( source_path );

        if ( ps.isNotNull() ) {
          set( p, CPSCalculations.calcPlantsNeededFromPlantsToStart( ps.getValueAsInt() ) );
          p.setCalculated( true );
        }
      }
      
      return p;
   }
   public Integer getPlantsNeeded() { return (Integer) getPlantsNeededDatum().getValue( useRawOutput() ); }
   public String getPlantsNeededString() { return formatInt( getPlantsNeeded() ); }
   public CPSDatumState getPlantsNeededState() { return getStateOf( PROP_PLANTS_NEEDED ); }
   public void setPlantsNeeded( Integer i ) { set( plants_needed, i ); }
   public void setPlantsNeeded( int i ) { setPlantsNeeded( new Integer( i ) ); }
   public void setPlantsNeeded( String s ) { setPlantsNeeded( parseInteger(s) ); }


//****************************************************************************//
   protected CPSDatum getRowFtToPlantDatum() { return getRowFtToPlantDatum( new ArrayList() ); }
   protected CPSDatum getRowFtToPlantDatum( List source_path ) {

      CPSDatum r = getDatum( PROP_ROWFT_PLANT );

      if ( r.isConcrete() || source_path.contains( r.propertyNum ))
        return r;

      source_path.add( r.propertyNum );

      if ( ! source_path.contains( PROP_BEDS_PLANT )) {

        CPSDatum rpb = getDatum( PROP_ROWS_P_BED );
        CPSDatum b = getBedsToPlantDatum( source_path );

        if ( ! r.isConcrete() &&
               b.isNotNull() && rpb.isNotNull() ) {
            /* we don't care if location is valid, this will return the default
             * bed length if location is inValid */
           int bedLength = CPSCalculations.extractBedLength( getLocation() );
           set( r, CPSCalculations.calcRowFtToPlantFromBedsToPlant( b.getValueAsFloat(),
                                                                        rpb.getValueAsInt(),
                                                                        bedLength ));
           r.setCalculated( true );
        }
      }
      else if ( ! source_path.contains( PROP_PLANTS_NEEDED )) {

          CPSDatum ps = getDatum( PROP_INROW_SPACE );
          CPSDatum p = getPlantsNeededDatum( source_path );

          if ( ! r.isConcrete() &&
                    p.isNotNull() && ps.isNotNull() ) {
             set( r, CPSCalculations.calcRowFtToPlantFromPlantsNeeded( p.getValueAsInt(),
                                                                           ps.getValueAsInt() ));
             r.setCalculated( true );
          }
      }
      else if ( ! source_path.contains( PROP_TOTAL_YIELD )) {

        CPSDatum yf = getDatum( PROP_YIELD_P_FOOT );
        CPSDatum ty = getTotalYieldDatum( source_path );

        if ( ! r.isConcrete() &&
                  ty.isNotNull() && yf.isNotNull() ) {
           set( r, CPSCalculations.calcRowFtToPlantFromTotalYield( ty.getValueAsFloat(),
                                                                       yf.getValueAsFloat() ));
           r.setCalculated( true );
        }
      }
      
      return r;
   }
   public Integer getRowFtToPlant() { return (Integer) getRowFtToPlantDatum().getValue( useRawOutput() ); }
   public String getRowFtToPlantString() { return formatInt( getRowFtToPlant() ); }
   public CPSDatumState getRowFtToPlantState() { return getStateOf( PROP_ROWFT_PLANT ); }
   public void setRowFtToPlant( Integer i ) { set( rowft_to_plant, i ); }
   public void setRowFtToPlant( int i ) { setRowFtToPlant( new Integer( i ) ); }
   public void setRowFtToPlant( String s ) { setRowFtToPlant( parseInteger(s) ); }


//****************************************************************************//
   protected CPSDatum getPlantsToStartDatum() { return getPlantsToStartDatum( new ArrayList() ); }
   protected CPSDatum getPlantsToStartDatum( List source_path ) {

      CPSDatum ps = getDatum( PROP_PLANTS_START );

      if ( ps.isConcrete() || source_path.contains( ps.propertyNum ))
        return ps;

      source_path.add( ps.propertyNum );


      if ( ! source_path.contains( PROP_PLANTS_NEEDED )) {

        CPSDatum pn = getPlantsNeededDatum( source_path );
        
        if ( pn.isNotNull() ) {
           set( ps, CPSCalculations.calcPlantsToStart( pn.getValueAsInt() ) );
           ps.setCalculated( true );
        }
      }
      else if ( ! source_path.contains( PROP_FLATS_NEEDED )) {

        CPSDatum fz = getDatum( PROP_FLAT_SIZE );
        CPSDatum fn = getFlatsNeededDatum( source_path );

        if ( fn.isNotNull() && fz.isNotNull() ) {
          set( ps, CPSCalculations.calcPlantsToStart( fn.getValueAsFloat(), getFlatSizeCapacity() ));
          ps.setCalculated( true );
        }
      }

      return ps;
   }
   public Integer getPlantsToStart() { return (Integer) getPlantsToStartDatum().getValue( useRawOutput() ); }
   public String getPlantsToStartString() { return formatInt( getPlantsToStart() ); }
   public CPSDatumState getPlantsToStartState() { return getStateOf( PROP_PLANTS_START ); }
   public void setPlantsToStart( Integer i ) { set( plants_to_start, i ); }
   public void setPlantsToStart( int i ) { setPlantsToStart( new Integer( i ) ); }
   public void setPlantsToStart( String s ) { setPlantsToStart( parseInteger(s) ); }


//****************************************************************************//
   protected CPSDatum getFlatsNeededDatum() { return getFlatsNeededDatum( new ArrayList() ); }
   protected CPSDatum getFlatsNeededDatum( List source_path ) {

      CPSDatum n = getDatum( PROP_FLATS_NEEDED );

      if ( n.isConcrete() || source_path.contains( n.propertyNum ))
        return n;

      source_path.add( n.propertyNum );


      if ( ! source_path.contains( PROP_PLANTS_START )) {

        CPSDatum s = getDatum( PROP_FLAT_SIZE );
        CPSDatum p = getPlantsToStartDatum( source_path );

        if ( p.isNotNull() && s.isNotNull() ) {
           set( n, CPSCalculations.calcFlatsNeeded( p.getValueAsInt(),
                                                        getFlatSizeCapacity() ) );
           n.setCalculated( true );
        }
      }

      return n;
   }
   public Float getFlatsNeeded() { return (Float) getFlatsNeededDatum().getValue( useRawOutput() ); }
   public String getFlatsNeededString() { return formatFloat( getFlatsNeeded(), 3 ); }
   public CPSDatumState getFlatsNeededState() { return getStateOf( PROP_FLATS_NEEDED ); }
   public void setFlatsNeeded( Float i ) { set( flats_needed, i ); }
   public void setFlatsNeeded( float i ) { setFlatsNeeded( new Float( i ) ); }
   public void setFlatsNeeded( String s ) { setFlatsNeeded( parseFloatBigF(s) ); }


/* *********************************************************************************************/
/* Yield Data */
/* *********************************************************************************************/
   public Float getYieldPerFoot() { return getFloat( PROP_YIELD_P_FOOT ); }
   public String getYieldPerFootString() { return formatFloat( (Float) get( PROP_YIELD_P_FOOT ), 3 ); }
   public CPSDatumState getYieldPerFootState() { return getStateOf( PROP_YIELD_P_FOOT ); }
   public void setYieldPerFoot( Float i ) { set( yield_p_foot, i ); }
   public void setYieldPerFoot( float i ) { setYieldPerFoot( new Float( i ) ); }
   public void setYieldPerFoot( String s ) { setYieldPerFoot( parseFloatBigF(s) ); }

   public Integer getYieldNumWeeks() { return getInt( PROP_YIELD_NUM_WEEKS ); }
   public String getYieldNumWeeksString() { return formatInt( (Integer) get( PROP_YIELD_NUM_WEEKS ) ); }
   public CPSDatumState getYieldNumWeeksState() { return getStateOf( PROP_YIELD_NUM_WEEKS ); }
   public void setYieldNumWeeks( Integer i ) { set( yield_num_weeks, i ); }
   public void setYieldNumWeeks( int i ) { setYieldNumWeeks( new Integer( i ) ); }
   public void setYieldNumWeeks( String s ) { setYieldNumWeeks( parseInteger(s) ); }

   public Float getYieldPerWeek() { return getFloat( PROP_YIELD_P_WEEK ); }
   public String getYieldPerWeekString() { return formatFloat( (Float) get( PROP_YIELD_P_WEEK )); }
   public CPSDatumState getYieldPerWeekState() { return getStateOf( PROP_YIELD_P_WEEK ); }
   public void setYieldPerWeek( Float i ) { set( yield_p_week, i ); }
   public void setYieldPerWeek( float i ) { setYieldPerWeek( new Float( i ) ); }
   public void setYieldPerWeek( String s ) { setYieldPerWeek( parseFloatBigF(s) ); }

   public String getCropYieldUnit() { return get( PROP_CROP_UNIT ); }
   public CPSDatumState getCropYieldUnitState() { return getStateOf( PROP_CROP_UNIT ); }
   public void setCropYieldUnit( String i ) { set( crop_unit, i ); }

   public Float getCropYieldUnitValue() { return getFloat( PROP_CROP_UNIT_VALUE ); }
   public String getCropYieldUnitValueString() { return formatFloat( (Float) get( PROP_CROP_UNIT_VALUE ) ); }
   public CPSDatumState getCropYieldUnitValueState() { return getStateOf( PROP_CROP_UNIT_VALUE ); }
   public void setCropYieldUnitValue( Float i ) { set( crop_unit_value, i ); }
   public void setCropYieldUnitValue( float i ) { setCropYieldUnitValue( new Float( i ) ); }
   public void setCropYieldUnitValue( String s ) { setCropYieldUnitValue( parseFloatBigF(s) ); }

   protected CPSDatum getTotalYieldDatum() { return getTotalYieldDatum( new ArrayList() ); }
   protected CPSDatum getTotalYieldDatum( List source_path ) {

      CPSDatum t = getDatum( PROP_TOTAL_YIELD );

      if ( t.isConcrete() || source_path.contains( t.propertyNum ))
        return t;

      source_path.add( t.propertyNum );

      if ( ! source_path.contains( PROP_ROWFT_PLANT )) {

        CPSDatum y = getDatum( PROP_YIELD_P_FOOT );
        CPSDatum r = getRowFtToPlantDatum( source_path );

        if ( ! t.isConcrete() &&
               y.isNotNull() && r.isNotNull() ) {
           set( t, CPSCalculations.calcTotalYieldFromRowFtToPlant( r.getValueAsInt(), y.getValueAsFloat() ));
           t.setCalculated( true );
        }
      }
      
      return t;
   }
   public Float getTotalYield() { return (Float) getTotalYieldDatum().getValue( useRawOutput() ); }
   public String getTotalYieldString() { return formatFloat( getTotalYield(), 3 ); }
   public CPSDatumState getTotalYieldState() { return getStateOf( PROP_TOTAL_YIELD ); }
   public void setTotalYield( Float i ) { set( total_yield, i ); }
   public void setTotalYield( float i ) { setTotalYield( new Float( i ) ); }
   public void setTotalYield( String s ) { setTotalYield( parseFloatBigF(s) ); }

   /* *********************************************************************************************/
   /* Misc Metadata */
   /* *********************************************************************************************/
   /**
    * @return Whether or not this planting is direct seeded.  If call when useRawouput() == true, this
    * could return null.
    */
   public Boolean isDirectSeeded() { return getBoolean( PROP_DIRECT_SEED ); }
   public Boolean isTransplanted() {
      if ( isDirectSeeded() == null ) 
         return null;
      else 
         return ! isDirectSeeded().booleanValue();
   }
   public CPSDatumState getDirectSeededState() { return getStateOf( PROP_DIRECT_SEED ); }
   public void setDirectSeeded( String s ) {
      if ( s != null && s.equalsIgnoreCase("true") )
         setDirectSeeded( Boolean.TRUE );
      else
         setDirectSeeded( Boolean.FALSE );
   }
   public void setTransplanted( Boolean b ) { 
      if ( b == null )
         setDirectSeeded( (Boolean) null );
      else
         setDirectSeeded( (Boolean) ! b.booleanValue() );
   }
   public void setDirectSeeded( Boolean b ) { set( direct_seed, b ); }
   public void setDirectSeeded( boolean b ) { setDirectSeeded( new Boolean( b )); }

   public Boolean isFrostHardy() { return getBoolean( PROP_FROST_HARDY ); }
   public Boolean isFrostTender() { return ! isFrostHardy().booleanValue(); }
   public CPSDatumState getFrostHardyState() { return getStateOf( PROP_FROST_HARDY ); }
   public void setFrostHardy( String s ) {
      if ( s != null && s.equalsIgnoreCase( "true" ) )
         setFrostHardy( Boolean.TRUE );
      else
         setFrostHardy( Boolean.FALSE );
   }
   public void setFrostHardy( Boolean b ) { set( frost_hardy, b ); }
   public void setFrostHardy( boolean b ) { setFrostHardy( new Boolean( b )); }

   public String getGroups() { return get( PROP_GROUPS ); }
   public CPSDatumState getGroupsState() { return getStateOf( PROP_GROUPS ); }
   public void setGroups( String e ) { set( groups, e ); }

   public String getKeywords() { return get( PROP_KEYWORDS ); }
   public CPSDatumState getKeywordsState() { return getStateOf( PROP_KEYWORDS ); }
   public void setKeywords( String e ) { set( keywords, e ); }

   public String getOtherRequirements() { return get( PROP_OTHER_REQ ); }
   public CPSDatumState getOtherRequirementsState() { return getStateOf( PROP_OTHER_REQ ); }
   public void setOtherRequirements( String e ) { set( other_req, e ); }

   public String getNotes() { return get( PROP_NOTES ); }
   public CPSDatumState getNotesState() { return getStateOf( PROP_NOTES ); }
   public void setNotes( String e ) { set( notes, e ); }

   public String getCustomField1() { return get( PROP_CUSTOM1 ); }
   public String getCustomField2() { return get( PROP_CUSTOM2 ); }
   public String getCustomField3() { return get( PROP_CUSTOM3 ); }
   public String getCustomField4() { return get( PROP_CUSTOM4 ); }
   public String getCustomField5() { return get( PROP_CUSTOM5 ); }
   public CPSDatumState getCustomField1State() { return getStateOf( PROP_CUSTOM1 ); }
   public CPSDatumState getCustomField2State() { return getStateOf( PROP_CUSTOM2 ); }
   public CPSDatumState getCustomField3State() { return getStateOf( PROP_CUSTOM3 ); }
   public CPSDatumState getCustomField4State() { return getStateOf( PROP_CUSTOM4 ); }
   public CPSDatumState getCustomField5State() { return getStateOf( PROP_CUSTOM5 ); }
   public void setCustomField1( String s ) { set( custom1, s ); }
   public void setCustomField2( String s ) { set( custom2, s ); }
   public void setCustomField3( String s ) { set( custom3, s ); }
   public void setCustomField4( String s ) { set( custom4, s ); }
   public void setCustomField5( String s ) { set( custom5, s ); }

   /* *********************************************************************************************/
   /* *********************************************************************************************/

   // don't need this any more
   protected void updateCalculations( int propNum ) { }

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
          if ( c.isNotNull() ) {
             s += c.getName() + " = '" + c.getValue() + "'";
             if ( c.isInherited() )
                s += "(i)";
             if ( c.isCalculated() )
                s += "(c)";
             s += ", ";
          }
       }
       
       return s;
    }


   public Date parseDate( String s ) {
       if ( s == null || s.equals("") )
           return null;
     
       return dateValidator.parse(s);
   }
   
   public String formatDate( Date d ) {
       if ( d == null || d.getTime() == 0 )
         return "";
       else
           return CPSDateValidator.format( d );
   }

}
