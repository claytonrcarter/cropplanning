/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Module;

/**
 *
 * @author Clayton
 */
public class CPSDataModelConstants {

   public static final int RECORD_TYPE_CROP     = 0;
   public static final int RECORD_TYPE_PLANTING = 1;
   
   /*
    *  PROPERTIES
    */
   
   /* General info */
   public static final int PROP_CROP_ID = 10;
   public static final int PROP_PLANTING_ID = 11;
   public static final int PROP_CROP_NAME = 12;
   public static final int PROP_VAR_NAME = 13;
   public static final int PROP_PLANTING_NAME = 14;
   public static final int PROP_FAM_NAME = 15;
   public static final int PROP_BOT_NAME = 16;
   public static final int PROP_CROP_DESC = 17;
   public static final int PROP_COMMON_ID = 18;
   
   /* "meta" info */
//   public static final int PROP_SUCCESSIONS = 20;
   public static final int PROP_IGNORE = 20;
   public static final int PROP_GROUPS = 21;
   public static final int PROP_KEYWORDS = 22;
   public static final int PROP_OTHER_REQ = 23;
   public static final int PROP_NOTES = 24;
   public static final int PROP_SIMILAR = 25;
   public static final int PROP_STATUS = 26;
   public static final int PROP_DONE_PLANTING = 27;
   public static final int PROP_DONE_TP = 28;
   public static final int PROP_DONE_HARVEST = 29;
   
   /* maturity day info */
   public static final int PROP_MATURITY = 30;
   public static final int PROP_MAT_ADJUST = 31;
   public static final int PROP_PLANTING_ADJUST = 32;
//   public static final int PROP_MISC_ADJUST   = 33;
   public static final int PROP_TIME_TO_TP    = 34;
   public static final int PROP_DS_MAT_ADJUST = 35;
   public static final int PROP_TP_MAT_ADJUST = 36;
   public static final int PROP_TP_TIME_IN_GH = PROP_TIME_TO_TP;
   
   /* planting date info */
   public static final int PROP_DATE_PLANT          = 40;
   public static final int PROP_DATE_TP             = 41;
   public static final int PROP_DATE_HARVEST        = 42;
   public static final int PROP_DATE_PLANT_PLAN     = 46;
   public static final int PROP_DATE_TP_PLAN        = 47;
   public static final int PROP_DATE_HARVEST_PLAN   = 48;
   public static final int PROP_DATE_PLANT_ACTUAL   = 43;
   public static final int PROP_DATE_TP_ACTUAL      = 44;
   public static final int PROP_DATE_HARVEST_ACTUAL = 45;

   /* plant and spacing info */
   /* general */
   public static final int PROP_ROWS_P_BED      = 50;
   public static final int PROP_SPACE_INROW     = 51;
   public static final int PROP_SPACE_BETROW    = 52;
   public static final int PROP_FLAT_SIZE       = 53;
   public static final int PROP_FROST_HARDY     = 55;
   public static final int PROP_PLANTING_METHOD = 56;
   public static final int PROP_PLANT_NOTES  = 57;
   public static final int PROP_PLANT_NOTES_SPECIFIC = 58;
   
   /* DS */
   public static final int PROP_DIRECT_SEED     = 60;
   public static final int PROP_DS_ROWS_P_BED   = 61;
   public static final int PROP_DS_SPACE_BETROW = 62;
   public static final int PROP_DS_PLANT_NOTES  = 63;
   
   /* TP */
   public static final int PROP_TRANSPLANT      = 70;
   public static final int PROP_TP_ROWS_P_BED   = 71;
   public static final int PROP_TP_SPACE_INROW  = PROP_SPACE_INROW;
   public static final int PROP_TP_SPACE_BETROW = 73;
   public static final int PROP_TP_FLAT_SIZE    = PROP_FLAT_SIZE;
   public static final int PROP_TP_POT_UP       = 74;
   public static final int PROP_TP_POT_UP_NOTES = 75;
//   public static final int PROP_POT_UP          = PROP_TP_POT_UP;
//   public static final int PROP_POT_UP_NOTES    = PROP_TP_POT_UP_NOTES;
   public static final int PROP_TP_PLANT_NOTES  = 76;

   
   /* planting amount info */
   public static final int PROP_BEDS_PLANT    = 80;
   public static final int PROP_PLANTS_NEEDED = 81;
   public static final int PROP_ROWFT_PLANT   = 82;
   public static final int PROP_PLANTS_START  = 83;
   public static final int PROP_FLATS_NEEDED  = 84;   
   
   /* misc planting info */
   public static final int PROP_PLANTER         = 90;
//   public static final int PROP_PLANTER_SETTING = 91;
   public static final int PROP_LOCATION        = 92;
   public static final int PROP_CROP_UNIT       = 93;
   public static final int PROP_CROP_UNIT_VALUE = 94;
   
   /* yield info */
   public static final int PROP_YIELD_P_FOOT    = 100;
   public static final int PROP_TOTAL_YIELD     = 101;
   public static final int PROP_YIELD_NUM_WEEKS = 102;
   public static final int PROP_YIELD_P_WEEK    = 103;
   
   /* custom fields */
   public static final int PROP_CUSTOM1 = 110;
   public static final int PROP_CUSTOM2 = 111;
   public static final int PROP_CUSTOM3 = 112;
   public static final int PROP_CUSTOM4 = 113;
   public static final int PROP_CUSTOM5 = 114;


   public static final int MAX_PROP_VALUE = PROP_CUSTOM5;
   /*
    * 
    */
   
}
