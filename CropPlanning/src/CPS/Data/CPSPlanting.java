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
import net.sf.persist.annotations.NoColumn;

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

   public static final int PROP_SEEDS_PER_UNIT = CPSDataModelConstants.PROP_SEEDS_PER_UNIT;
   public static final int PROP_SEED_UNIT    = CPSDataModelConstants.PROP_SEED_UNIT;
   public static final int PROP_SEEDS_PER    = CPSDataModelConstants.PROP_SEEDS_PER;
   public static final int PROP_SEEDS_PER_DS = CPSDataModelConstants.PROP_SEEDS_PER_DS;
   public static final int PROP_SEEDS_PER_TP = CPSDataModelConstants.PROP_SEEDS_PER_TP;

   // calculated
   public static final int PROP_TOTAL_YIELD   = CPSDataModelConstants.PROP_TOTAL_YIELD;
   public static final int PROP_SEED_NEEDED   = CPSDataModelConstants.PROP_SEED_NEEDED;

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

    private CPSDatum<Integer> seedsPerUnit;
    private CPSDatum<String>  seedUnit;
    private CPSDatum<Float>   seedsPerDS;
    private CPSDatum<Float>   seedsPerTP;
    private CPSDatum<Float>   seedNeeded;

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

      recordID = new CPSDatum<Integer>( "ID", "Unique ID", new Integer(-1), PROP_ID );
      commonIDs = new CPSDatum<ArrayList<Integer>>( "Common IDs", "Planting IDs represented", new ArrayList<Integer>(), PROP_COMMON_ID );
      
      crop_name = new CPSDatum<String>( "Crop", "Name of crop to be planted", "", PROP_CROP_NAME );
      var_name = new CPSDatum<String>( "Variety", "Name of variety to be planted", "", PROP_VAR_NAME );
      maturity = new CPSDatum<Integer>( "Maturity Days", new Integer(0), PROP_MATURITY );
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

      done_plant = new CPSDatum<Boolean>( "Planted?", "Has this been planted?", Boolean.FALSE, PROP_DONE_PLANTING);
      done_tp = new CPSDatum<Boolean>( "Transplanted?", "Has this been transplanted?", Boolean.FALSE, PROP_DONE_TP );
      done_harvest = new CPSDatum<Boolean>( "Harvested?", "Has this been harvested", Boolean.FALSE, PROP_DONE_HARVEST );
      ignore = new CPSDatum<Boolean>( "Ignore?", "Ignore this planting", Boolean.FALSE, PROP_IGNORE );

      ds_mat_adjust = new CPSDatum<Integer>( "DS Mat. Adj.", new Integer(0), PROP_DS_MAT_ADJUST );
      tp_mat_adjust = new CPSDatum<Integer>( "TP Mat. Adj.", new Integer(0), PROP_TP_MAT_ADJUST );
      time_to_tp = new CPSDatum<Integer>( "Weeks to TP", new Integer(0), PROP_TIME_TO_TP );
      ds_rows_p_bed = new CPSDatum<Integer>( "DS Rows/Bed", new Integer(0), PROP_DS_ROWS_P_BED );
      tp_rows_p_bed = new CPSDatum<Integer>( "TP Rows/Bed", new Integer(0), PROP_TP_ROWS_P_BED );
      inrow_space = new CPSDatum<Integer>( "In-row Spacing", new Integer(0), PROP_INROW_SPACE );
      ds_row_space = new CPSDatum<Integer>( "DS Row Spacing", new Integer(0), PROP_DS_ROW_SPACE );
      tp_row_space = new CPSDatum<Integer>( "TP Row Spacing", new Integer(0), PROP_TP_ROW_SPACE );
      flat_size = new CPSDatum<String>( "Flat size", "", PROP_FLAT_SIZE );
      ds_crop_notes = new CPSDatum<String>( "DS Planting Notes (from CropDB)", "", PROP_DS_CROP_NOTES );
      tp_crop_notes = new CPSDatum<String>( "TP Planting Notes (from CropDB)", "", PROP_TP_CROP_NOTES );
      planting_notes = new CPSDatum<String>( "Planting Notes", "", PROP_PLANTING_NOTES );

      beds_to_plant = new CPSDatum<Float>( "Num. Beds to Plant", new Float(0.0), PROP_BEDS_PLANT );
      plants_needed = new CPSDatum<Integer>( "Num. Plants Needed", new Integer(0), PROP_PLANTS_NEEDED );
      rowft_to_plant = new CPSDatum<Integer>( "Row Feet To Plant", new Integer(0), PROP_ROWFT_PLANT );
      plants_to_start = new CPSDatum<Integer>( "Num. Plants to Start", new Integer(0), PROP_PLANTS_START );
      flats_needed = new CPSDatum<Float>( "Num. Flats Needed", new Float(0.0), PROP_FLATS_NEEDED );
            
      yield_p_foot = new CPSDatum<Float>( "Yield/Ft", new Float(0.0), PROP_YIELD_P_FOOT );
      yield_num_weeks = new CPSDatum<Integer>( "Will Yield for (weeks)", new Integer(0), PROP_YIELD_NUM_WEEKS );
      yield_p_week = new CPSDatum<Float>( "Yield/Week", new Float(0.0), PROP_YIELD_P_WEEK );
      crop_unit = new CPSDatum<String>( "Unit of Yield", "", PROP_CROP_UNIT );
      crop_unit_value = new CPSDatum<Float>( "Value per Yield Unit", new Float(0.0), PROP_CROP_UNIT_VALUE );
      total_yield = new CPSDatum<Float>( "Total Yield", new Float( -1.0 ), PROP_TOTAL_YIELD );

      seedsPerUnit = new CPSDatum<Integer>( "Seeds/Unit", new Integer(-1), PROP_SEEDS_PER_UNIT );
      seedUnit = new CPSDatum<String>( "Units", "", PROP_SEED_UNIT );
      seedsPerDS = new CPSDatum<Float>( "Seeds/RowFt (DS)", new Float( -1.0 ), PROP_SEEDS_PER_DS );
      seedsPerTP = new CPSDatum<Float>( "Seeds/Plant (TP)", new Float( -1.0 ), PROP_SEEDS_PER_TP );
      seedNeeded    = new CPSDatum<Float>( "Seed Units Needed", new Float( -1.0 ), PROP_SEED_NEEDED );

      direct_seed = new CPSDatum<Boolean>( "Direct seeded?", Boolean.TRUE, PROP_DIRECT_SEED );
      frost_hardy = new CPSDatum<Boolean>( "Frost hardy?", Boolean.FALSE , PROP_FROST_HARDY );

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

            // TODO handle a generic "seeds per" request for DS/TP?
          case PROP_SEEDS_PER_UNIT:  return seedsPerUnit;
          case PROP_SEED_UNIT:       return seedUnit;
          case PROP_SEEDS_PER:
             if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
                return getDatum( PROP_SEEDS_PER_DS );
             else
                return getDatum( PROP_SEEDS_PER_TP );
          case PROP_SEEDS_PER_DS: return seedsPerDS;
          case PROP_SEEDS_PER_TP: return seedsPerTP;
          case PROP_SEED_NEEDED:     return seedNeeded;

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

        listOfInheritableProperties.add( PROP_SEEDS_PER_UNIT );
        listOfInheritableProperties.add( PROP_SEED_UNIT );
        listOfInheritableProperties.add( PROP_SEEDS_PER_DS  );
        listOfInheritableProperties.add( PROP_SEEDS_PER_TP );
        
      }
      return listOfInheritableProperties;
   }

   @Override
   public void finishUp () {
   
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

   // *********************************************************************************************/
   // Dates
   // *********************************************************************************************/
   protected CPSDatum<Date> getEffectiveDate( int prop_effective ) {

      CPSDatum<Date> p, a;
      CPSDatum<Date> e = getDatum( prop_effective );

      if ( prop_effective == PROP_DATE_PLANT ) {
        p = getDateToPlantDatum( DATE_TYPE_PLANNED );
        a = getDateToPlantDatum( DATE_TYPE_ACTUAL );
      } else if ( prop_effective == PROP_DATE_TP ) {
        p = getDateToTPDatum( DATE_TYPE_PLANNED );
        a = getDateToTPDatum( DATE_TYPE_ACTUAL );
      } else { // if ( prop_effective == PROP_DATE_HARVEST ) {
        p = getDateToHarvestDatum( DATE_TYPE_PLANNED );
        a = getDateToHarvestDatum( DATE_TYPE_ACTUAL );
      }

       /* If date_plant_actual is valid, return it
        * else return date_plant_plan
        * or just return a default */
       if ( a.isNotNull() ) {
         e.setState( a.getState() );
         return a;
       }
       else if ( p.isNotNull() ) {
         e.setState( p.getState() );
         return p;
       }

       return e;
      
   }


   private CPSDatum<Date> getDateToPlantDatum ( int date_type ) {
      return getDateToPlantDatum ( date_type, new ArrayList() );
   }
   private CPSDatum<Date> getDateToPlantDatum ( int date_type, List source_path ) {

      int prop_plant, prop_tp, prop_harv;

      if ( date_type == DATE_TYPE_ACTUAL ) {

         prop_plant = PROP_DATE_PLANT_ACTUAL;
         prop_tp = PROP_DATE_TP_ACTUAL;
         prop_harv = PROP_DATE_HARVEST_ACTUAL;

      }
      else {

         prop_plant = PROP_DATE_PLANT_PLAN;
         prop_tp = PROP_DATE_TP_PLAN;
         prop_harv = PROP_DATE_HARVEST_PLAN;

      }

      CPSDatum p = getDatum( prop_plant );

      if ( p.isConcrete() ||
           source_path.contains( p.propertyNum ) ||
           this.doesRepresentMultipleRecords() )
        return p;

      source_path.add( p.propertyNum );


      CPSDatum w = getDatum( PROP_TIME_TO_TP );


      if ( ! source_path.contains( prop_tp ) && isTransplanted() ) {

        CPSDatum tp = getDateToTPDatum( date_type, source_path );

        if ( tp.isNotNull() && w.isNotNull() ) {
            set( p,  CPSCalculations.calcDatePlantFromDateTP( (Date) tp.getValue(),
                                                                    w.getValueAsInt() ));
            p.setCalculated( true );
        }
      }
      else if ( ! source_path.contains( prop_harv )) {

        CPSDatum harv = getDateToHarvestDatum( date_type, source_path );
        CPSDatum m = getDatum( PROP_MATURITY );

        if ( harv.isNotNull() && m.isNotNull() ) {
          try {
            set( p, CPSCalculations.calcDatePlantFromDateHarvest( (Date) harv.getValue(),
                                                                        m.getValueAsInt(),
                                                                        getMatAdjust(),
                                                                        w.getValueAsInt() ));
            p.setCalculated( true );
          } catch ( NullPointerException e ) { /* basically, leave plant as it was */ }
        }
      }

      return p;

   }

   public Date getDateToPlant() {
      return getEffectiveDate( PROP_DATE_PLANT ).getValue( useRawOutput() );
   }
   public String getDateToPlantString() { return formatDate( getDateToPlant() ); }
   public CPSDatumState getDateToPlantState() { return getStateOf( PROP_DATE_PLANT ); }

   public Date getDateToPlantPlanned() {
      return getDateToPlantDatum( DATE_TYPE_PLANNED ).getValue( useRawOutput() );
   }
   public String getDateToPlantPlannedString() { return formatDate( getDateToPlantPlanned() ); }
   public CPSDatumState getDateToPlantPlannedState() { return getStateOf( PROP_DATE_PLANT_PLAN ); }
   public void setDateToPlantPlanned( Date d ) { set( date_plant_plan, d ); }
   public void setDateToPlantPlanned( String d ) { setDateToPlantPlanned( parseDate(d) ); }

   public Date getDateToPlantActual() {
      return getDateToPlantDatum( DATE_TYPE_ACTUAL ).getValue( useRawOutput() );
   }
   public String getDateToPlantActualString() { return formatDate( getDateToPlantActual() ); }
   public CPSDatumState getDateToPlantActualState() { return getStateOf( PROP_DATE_PLANT_ACTUAL ); }
   public void setDateToPlantActual( Date d ) { set( date_plant_actual, d ); }
   public void setDateToPlantActual( String d ) { setDateToPlantActual( parseDate(d) ); }



   private CPSDatum<Date> getDateToTPDatum( int date_type ) {
      return getDateToTPDatum( date_type, new ArrayList() );
   }
   private CPSDatum<Date> getDateToTPDatum( int date_type, List source_path ) {

      int prop_plant, prop_tp, prop_harv;
      
      boolean actual = date_type == DATE_TYPE_ACTUAL;

      if ( actual ) {

         prop_plant = PROP_DATE_PLANT_ACTUAL;
         prop_tp = PROP_DATE_TP_ACTUAL;
         prop_harv = PROP_DATE_HARVEST_ACTUAL;

      } 
      else {

         prop_plant = PROP_DATE_PLANT_PLAN;
         prop_tp = PROP_DATE_TP_PLAN;
         prop_harv = PROP_DATE_HARVEST_PLAN;

      }

      CPSDatum t = getDatum( prop_tp );

      if ( t.isConcrete() ||
           source_path.contains( t.propertyNum ) ||
           this.doesRepresentMultipleRecords() )
        return t;

      source_path.add( t.propertyNum );


      CPSDatum w = getDatum( PROP_TIME_TO_TP );


      if ( ! source_path.contains( prop_plant )) {

        CPSDatum p = getDateToPlantDatum( date_type, source_path );

        if ( p.isNotNull() && w.isNotNull() ) {
          set( t, CPSCalculations.calcDateTPFromDatePlant( (Date) p.getValue(),
                                                                w.getValueAsInt() ) );
          t.setCalculated( true );

        }
      }
      else if ( ! source_path.contains( prop_harv )) {

        CPSDatum h = getDateToHarvestDatum( date_type, source_path );
        CPSDatum m = getDatum( PROP_MATURITY );

        if ( w.isNotNull() && h.isNotNull() && m.isNotNull() ) {
          try {
            set( t, CPSCalculations.calcDateTPFromDateHarvest( (Date) h.getValue(),
                                                                  m.getValueAsInt(),
                                                                  getMatAdjust() ) );
            t.setCalculated( true );
          } catch ( NullPointerException e ) { /* basically, leave t as it was */ }
        }
      }

      return t;

   }

   public Date getDateToTP() {
      return getEffectiveDate( PROP_DATE_TP ).getValue( useRawOutput() );
   }
   public String getDateToTPString() { return formatDate( getDateToTP() ); }
   public CPSDatumState getDateToTPState() { return getStateOf( PROP_DATE_TP ); }

   public Date getDateToTPPlanned () {
      return getDateToTPDatum( DATE_TYPE_PLANNED ).getValue( useRawOutput() );
   }
   public String getDateToTPPlannedString() { return formatDate( getDateToTPPlanned() ); }
   public CPSDatumState getDateToTPPlannedState() { return getStateOf( PROP_DATE_TP_PLAN ); }
   public void setDateToTPPlanned( Date d ) { set( date_tp_plan, d ); }
   public void setDateToTPPlanned( String d ) { setDateToTPPlanned( parseDate( d ) ); }

   public Date getDateToTPActual() {
      return getDateToTPDatum( DATE_TYPE_ACTUAL ).getValue( useRawOutput() );
   }
   public String getDateToTPActualString() { return formatDate( getDateToTPActual() ); }
   public CPSDatumState getDateToTPActualState() { return getStateOf( PROP_DATE_TP_ACTUAL ); }
   public void setDateToTPActual( Date d ) { set( date_tp_actual, d ); }
   public void setDateToTPActual( String d ) { setDateToTPActual( parseDate( d ) ); }



   private CPSDatum<Date> getDateToHarvestDatum( int date_type ) {
      return getDateToHarvestDatum( date_type, new ArrayList() );
   }
   private CPSDatum<Date> getDateToHarvestDatum( int date_type, List source_path ) {

      int prop_plant, prop_tp, prop_harv;

      if ( date_type == DATE_TYPE_ACTUAL ) {

         prop_plant = PROP_DATE_PLANT_ACTUAL;
         prop_tp = PROP_DATE_TP_ACTUAL;
         prop_harv = PROP_DATE_HARVEST_ACTUAL;

      }
      else {

         prop_plant = PROP_DATE_PLANT_PLAN;
         prop_tp = PROP_DATE_TP_PLAN;
         prop_harv = PROP_DATE_HARVEST_PLAN;

      }

      CPSDatum<Date> h = getDatum( prop_harv );

      if ( h.isConcrete() ||
           source_path.contains( h.propertyNum ) ||
           this.doesRepresentMultipleRecords() )
        return h;

      source_path.add( h.propertyNum );


      CPSDatum m = getDatum( PROP_MATURITY );

      if ( ! source_path.contains( prop_tp ) && isTransplanted() ) {

        CPSDatum t = getDateToTPDatum( date_type, source_path );

        if ( t.isNotNull() && m.isNotNull() ) {
            try {
              set( h, CPSCalculations.calcDateHarvestFromDateTP( (Date) t.getValue(),
                                                                    m.getValueAsInt(),
                                                                    getMatAdjust() ) );
              h.setCalculated( true );
            } catch ( NullPointerException e ) { /* basically, leave h as null */ }
        }

      }
      else if ( ! source_path.contains( prop_plant )) {

        CPSDatum p = getDateToPlantDatum( date_type, source_path );
        CPSDatum w = getDatum( PROP_TIME_TO_TP );

        // w can be null, ie == 0
        if ( p.isNotNull() && m.isNotNull() ) {
          
            try {
              set( h, CPSCalculations.calcDateHarvestFromDatePlant( (Date) p.getValue(),
                                                                        m.getValueAsInt(),
                                                                        getMatAdjust(),
                                                                        w.getValueAsInt() ));
              h.setCalculated( true );
            } catch ( NullPointerException e ) { /* basically, leave h as null */ }

        }
      }

      return h;

   }

   public Date getDateToHarvest() {
      return getEffectiveDate( PROP_DATE_HARVEST ).getValue(useRawOutput());
   }
   public String getDateToHarvestString() { return formatDate( getDateToHarvest() ); }
   public CPSDatumState getDateToHarvestState() { return getStateOf( PROP_DATE_HARVEST ); }

   public Date getDateToHarvestPlanned() { 
     return getDateToHarvestDatum( DATE_TYPE_PLANNED ).getValue(useRawOutput());
   }
   public String getDateToHarvestPlannedString() { return formatDate( getDateToHarvestPlanned() ); }
   public CPSDatumState getDateToHarvestPlannedState() { return getStateOf( PROP_DATE_HARVEST_PLAN ); }
   public void setDateToHarvestPlanned( Date d ) { set( date_harvest_plan, d ); }
   public void setDateToHarvestPlanned( String d ) { setDateToHarvestPlanned( parseDate( d ) ); }

   public Date getDateToHarvestActual() { 
     return getDateToHarvestDatum( DATE_TYPE_ACTUAL ).getValue(useRawOutput());
   }
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
   @NoColumn
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


   /** Not really deprecated, but you shouldn't be using these!
    *  These are here for Persist. */
   @Deprecated
   public Integer getDSMatAdjust() { return getInt( PROP_DS_MAT_ADJUST ); }
   @Deprecated
   public void    setDSMatAdjust( Integer i ) { set( ds_mat_adjust, i ); }
   @Deprecated
   public Integer getTPMatAdjust() { return getInt( PROP_TP_MAT_ADJUST ); }
   @Deprecated
   public void    setTPMatAdjust( Integer i ) { set( tp_mat_adjust, i ); }



   public Integer getTimeToTP() { return getInt( PROP_TIME_TO_TP ); }
   public String getTimeToTPString() { return getString( PROP_TIME_TO_TP ); }
   public CPSDatumState getTimeToTPState() { return getStateOf( PROP_TIME_TO_TP ); }
   public void setTimeToTP( Integer i ) { set( time_to_tp, i ); }
   public void setTimeToTP( int i ) { setTimeToTP( new Integer( i ) ); }
   public void setTimeToTP( String s ) { setTimeToTP( parseInteger(s) ); }

   @NoColumn
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
   public CPSDatumState getRowsPerBedState() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getStateOf( PROP_DS_ROWS_P_BED );
      else
         return getStateOf( PROP_TP_ROWS_P_BED );
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

   /** Not really deprecated, but you shouldn't be using these!
    *  These are here for Persist. */
   @Deprecated
   public Integer getDSRowsPerBed() { return getInt( PROP_DS_ROWS_P_BED ); }
   @Deprecated
   public void    setDSRowsPerBed( Integer i ) { set( ds_rows_p_bed, i ); }
   @Deprecated
   public Integer getTPRowsPerBed() { return getInt( PROP_TP_ROWS_P_BED ); }
   @Deprecated
   public void    setTPRowsPerBed( Integer i ) { set( tp_rows_p_bed, i ); }


   public Integer getInRowSpacing() { return getInt( PROP_INROW_SPACE ); }
   public String getInRowSpacingString() { return getString( PROP_INROW_SPACE ); }
   public CPSDatumState getInRowSpacingState() { return getStateOf( PROP_INROW_SPACE ); }
   public void setInRowSpacing( Integer i ) { set( inrow_space, i ); }
   public void setInRowSpacing( int i ) { setInRowSpacing( new Integer( i ) ); }
   public void setInRowSpacing( String s ) { setInRowSpacing( parseInteger(s) ); }

   @NoColumn
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

   @Deprecated
   public Integer getDSRowSpacing() { return getInt( PROP_DS_ROW_SPACE ); }
   @Deprecated
   public void    setDSRowSpacing( Integer i ) { set( ds_row_space, i ); }
   @Deprecated
   public Integer getTPRowSpacing() { return getInt( PROP_TP_ROW_SPACE ); }
   @Deprecated
   public void    setTPRowSpacing( Integer i ) { set( tp_row_space, i ); }


   public String getFlatSize() { return get( PROP_FLAT_SIZE ); }
   public Integer getFlatSizeCapacity() {
      return CPSCalculations.extractFlatCapacity( getFlatSize() );
   }
   public CPSDatumState getFlatSizeState() { return getStateOf( PROP_FLAT_SIZE ); }
   public void setFlatSize( String i ) { set( flat_size, parseInheritableString(i) ); }

   @NoColumn
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
         set( ds_crop_notes, parseInheritableString(i) );
      else
         set( tp_crop_notes, parseInheritableString(i) );
   }

   public String getPlantingNotes() { return get( PROP_PLANTING_NOTES ); }
   public CPSDatumState getPlantingNotesState() { return getStateOf( PROP_PLANTING_NOTES ); }
   public void setPlantingNotes( String i ) { set( planting_notes, i ); }

   @Deprecated
   public String getDSPlantingNotes() { return get( PROP_DS_CROP_NOTES ); }
   @Deprecated
   public void   setDSPlantingNotes( String i ) { set( ds_crop_notes, parseInheritableString(i) ); }
   @Deprecated
   public String getTPPlantingNotes() { return get( PROP_TP_CROP_NOTES ); }
   @Deprecated
   public void   setTPPlantingNotes( String i ) { set( tp_crop_notes, parseInheritableString(i) ); }


/* *********************************************************************************************/
/* Calculated Values */
/* *********************************************************************************************/
   protected CPSDatum<Float> getBedsToPlantDatum() { return getBedsToPlantDatum( new ArrayList() ); }
   protected CPSDatum<Float> getBedsToPlantDatum( List source_path ) {

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
   public Float getBedsToPlant() { return getBedsToPlantDatum().getValue( useRawOutput() ); }
   public String getBedsToPlantString() { return formatFloat( getBedsToPlantDatum(), 3 ); }
   public CPSDatumState getBedsToPlantState() { return getStateOf( PROP_BEDS_PLANT ); }
   public void setBedsToPlant( Float i ) { set( beds_to_plant, i ); }
   public void setBedsToPlant( float i ) { setBedsToPlant( new Float( i ) ); }
   public void setBedsToPlant( String s ) { setBedsToPlant( parseFloatBigF(s) ); }


//****************************************************************************//
   protected CPSDatum<Integer> getPlantsNeededDatum() { return getPlantsNeededDatum( new ArrayList() ); }
   protected CPSDatum<Integer> getPlantsNeededDatum( List source_path ) {

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
   public Integer getPlantsNeeded() { return getPlantsNeededDatum().getValue( useRawOutput() ); }
   public String getPlantsNeededString() { return formatInt( getPlantsNeededDatum() ); }
   public CPSDatumState getPlantsNeededState() { return getStateOf( PROP_PLANTS_NEEDED ); }
   public void setPlantsNeeded( Integer i ) { set( plants_needed, i ); }
   public void setPlantsNeeded( int i ) { setPlantsNeeded( new Integer( i ) ); }
   public void setPlantsNeeded( String s ) { setPlantsNeeded( parseInteger(s) ); }


//****************************************************************************//
   protected CPSDatum<Integer> getRowFtToPlantDatum() { return getRowFtToPlantDatum( new ArrayList() ); }
   protected CPSDatum<Integer> getRowFtToPlantDatum( List source_path ) {

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
   public Integer getRowFtToPlant() { return getRowFtToPlantDatum().getValue( useRawOutput() ); }
   public String getRowFtToPlantString() { return formatInt( getRowFtToPlantDatum() ); }
   public CPSDatumState getRowFtToPlantState() { return getStateOf( PROP_ROWFT_PLANT ); }
   public void setRowFtToPlant( Integer i ) { set( rowft_to_plant, i ); }
   public void setRowFtToPlant( int i ) { setRowFtToPlant( new Integer( i ) ); }
   public void setRowFtToPlant( String s ) { setRowFtToPlant( parseInteger(s) ); }


//****************************************************************************//
   protected CPSDatum<Integer> getPlantsToStartDatum() { return getPlantsToStartDatum( new ArrayList() ); }
   protected CPSDatum<Integer> getPlantsToStartDatum( List source_path ) {

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
   public Integer getPlantsToStart() { return getPlantsToStartDatum().getValue( useRawOutput() ); }
   public String getPlantsToStartString() { return formatInt( getPlantsToStartDatum() ); }
   public CPSDatumState getPlantsToStartState() { return getStateOf( PROP_PLANTS_START ); }
   public void setPlantsToStart( Integer i ) { set( plants_to_start, i ); }
   public void setPlantsToStart( int i ) { setPlantsToStart( new Integer( i ) ); }
   public void setPlantsToStart( String s ) { setPlantsToStart( parseInteger(s) ); }


//****************************************************************************//
   protected CPSDatum<Float> getFlatsNeededDatum() { return getFlatsNeededDatum( new ArrayList() ); }
   protected CPSDatum<Float> getFlatsNeededDatum( List source_path ) {

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
   public Float getFlatsNeeded() { return getFlatsNeededDatum().getValue( useRawOutput() ); }
   public String getFlatsNeededString() { return formatFloat( getFlatsNeededDatum(), 3 ); }
   public CPSDatumState getFlatsNeededState() { return getStateOf( PROP_FLATS_NEEDED ); }
   public void setFlatsNeeded( Float i ) { set( flats_needed, i ); }
   public void setFlatsNeeded( float i ) { setFlatsNeeded( new Float( i ) ); }
   public void setFlatsNeeded( String s ) { setFlatsNeeded( parseFloatBigF(s) ); }


/* *********************************************************************************************/
/* Yield Data */
/* *********************************************************************************************/
   public Float getYieldPerFoot() { return getFloat( PROP_YIELD_P_FOOT ); }
   public String getYieldPerFootString() { return formatFloat( getDatum( PROP_YIELD_P_FOOT ), 2 ); }
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
   public void setCropYieldUnit( String i ) { set( crop_unit, parseInheritableString(i) ); }

   public Float getCropYieldUnitValue() { return getFloat( PROP_CROP_UNIT_VALUE ); }
   public String getCropYieldUnitValueString() { return formatFloat( getDatum( PROP_CROP_UNIT_VALUE ), 2 ); }
   public CPSDatumState getCropYieldUnitValueState() { return getStateOf( PROP_CROP_UNIT_VALUE ); }
   public void setCropYieldUnitValue( Float i ) { set( crop_unit_value, i ); }
   public void setCropYieldUnitValue( float i ) { setCropYieldUnitValue( new Float( i ) ); }
   public void setCropYieldUnitValue( String s ) { setCropYieldUnitValue( parseFloatBigF(s) ); }

   protected CPSDatum<Float> getTotalYieldDatum() { return getTotalYieldDatum( new ArrayList() ); }
   protected CPSDatum<Float> getTotalYieldDatum( List source_path ) {

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
   public Float getTotalYield() { return getTotalYieldDatum().getValue( useRawOutput() ); }
   public String getTotalYieldString() { return formatFloat( getTotalYieldDatum(), 3 ); }
   public CPSDatumState getTotalYieldState() { return getStateOf( PROP_TOTAL_YIELD ); }
   public void setTotalYield( Float i ) { set( total_yield, i ); }
   public void setTotalYield( float i ) { setTotalYield( new Float( i ) ); }
   public void setTotalYield( String s ) { setTotalYield( parseFloatBigF(s) ); }


    public Integer       getSeedsPerUnit() {            return getInt( seedsPerUnit.getPropertyNum() ); }
    public String        getSeedsPerUnitString() {       return getString( seedsPerUnit.getPropertyNum() ); }
    public CPSDatumState getSeedsPerUnitState() {        return getStateOf( seedsPerUnit.getPropertyNum() ); }
    public void          setSeedsPerUnit( Integer i ) { set( seedsPerUnit, i ); }
    public void          setSeedsPerUnit( int i ) {     setSeedsPerUnit( new Integer( i )); }
    public void          setSeedsPerUnit( String s ) {  setSeedsPerUnit( parseInteger(s) ); }

    public String        getSeedUnit() {           return get( seedUnit.getPropertyNum() ); }
    public CPSDatumState getSeedUnitState() {       return getStateOf( seedUnit.getPropertyNum() ); }
    public void          setSeedUnit( String s ) { set( seedUnit, parseInheritableString(s) ); }

    @NoColumn
    public Float getSeedsPer() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getFloat( seedsPerDS.getPropertyNum() );
      else
         return getFloat( seedsPerTP.getPropertyNum() );
    }
    public String getSeedsPerString() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getString( seedsPerDS.getPropertyNum() );
      else
         return getString( seedsPerTP.getPropertyNum() );
    }
    public CPSDatumState getSeedsPerState() {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         return getStateOf( seedsPerDS.getPropertyNum() );
      else
         return getStateOf( seedsPerTP.getPropertyNum() );
    }
    public void setSeedsPer( Float i ) {
      if ( isDirectSeeded() == null || isDirectSeeded().booleanValue() )
         set( seedsPerDS, i );
      else
         set( seedsPerTP, i );
    }
    public void setSeedsPer( float i ) { setSeedsPer( new Float( i )); }
    public void setSeedsPer( String s ) { setSeedsPer( parseFloatBigF(s) ); }

    @Deprecated
    public Float getSeedsPerDS() { return getFloat( seedsPerDS.getPropertyNum() ); }
    @Deprecated
    public void  setSeedsPerDS( Float i ) { set( seedsPerDS, i ); }
    @Deprecated
    public Float getSeedsPerTP() { return getFloat( seedsPerTP.getPropertyNum() ); }
    @Deprecated
    public void  setSeedsPerTP( Float f ) { set( seedsPerTP, f ); }


    protected CPSDatum<Float> gettSeedNeededDatum() { return gettSeedNeededDatum( new ArrayList() ); }
    protected CPSDatum<Float> gettSeedNeededDatum( List source_path ) {

      CPSDatum n = getDatum( PROP_SEED_NEEDED );

      if ( n.isConcrete() || source_path.contains( n.propertyNum ))
        return n;

      source_path.add( n.propertyNum );

//      if ( ! source_path.contains( PROP_OF_DATUM_THAT_MIGHT_CALL_THIS )) {

      CPSDatum d = getDatum( seedsPerDS.getPropertyNum() );
      CPSDatum r = getRowFtToPlantDatum( source_path );
      CPSDatum t = getDatum( seedsPerTP.getPropertyNum() );
      CPSDatum p = getPlantsNeededDatum( source_path );
      CPSDatum u = getDatum( seedsPerUnit.getPropertyNum() );

      Boolean ds = this.isDirectSeeded();
      ds = ds == null || ds.booleanValue();

      if ( ! n.isConcrete() && u.isNotNull() ) {
        if ( ds &&
             d.isNotNull() && r.isNotNull() ) {
          set( n,
               CPSCalculations.precision3( ( d.getValueAsFloat() *
                                             r.getValueAsInt() ) /
                                            u.getValueAsInt() ));
          n.setCalculated( true );
        }
        else if ( ! ds &&
                  t.isNotNull() && p.isNotNull() ) {
          set( n,
               CPSCalculations.precision3( ( t.getValueAsFloat() *
                                             p.getValueAsInt() ) /
                                            u.getValueAsInt() ));
          n.setCalculated( true );
        }
      }

      return n;
    }
    public Float         getSeedNeeded() { return gettSeedNeededDatum().getValue( useRawOutput() ); }
    public String        getSeedNeededString() { return formatFloat( gettSeedNeededDatum(), 3 ); }
    public CPSDatumState getSeedNeededState() { return getStateOf( seedNeeded.getPropertyNum() ); }
    public void          setSeedNeeded( Float f ) {  set( seedNeeded, f ); }
    public void          setSeedNeeded( float f ) {  setSeedNeeded( new Float( f )); }
    public void          setSeedNeeded( String s ) { setSeedNeeded( parseFloatBigF(s) ); }


   /* *********************************************************************************************/
   /* Misc Metadata */
   /* *********************************************************************************************/
   /**
    * @return Whether or not this planting is direct seeded.  If call when useRawouput() == true, this
    * could return null.
    */
   public Boolean isDirectSeeded() { return getBoolean( PROP_DIRECT_SEED ); }
   @NoColumn
   public Boolean isTransplanted() {
      if ( isDirectSeeded() == null ) 
         return Boolean.FALSE;
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
   protected void updateCalculations() { }

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
         return this.currentProp == PROP_ID      ||
                this.currentProp == PROP_DATE_PLANT ||
                this.currentProp == PROP_DATE_TP ||
                this.currentProp == PROP_DATE_HARVEST ||
                this.currentProp == PROP_SEEDS_PER ||
                this.currentProp == PROP_MAT_ADJUST ||
                this.currentProp == PROP_ROWS_P_BED ||
                this.currentProp == PROP_ROW_SPACE ||
                this.currentProp == PROP_CROP_NOTES;
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
