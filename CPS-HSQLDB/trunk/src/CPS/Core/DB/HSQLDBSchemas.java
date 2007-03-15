/*
 * HQSLDBSchemas.java
 *
 * Created on March 13, 2007, 11:15 AM
 *
 *
 */

package CPS.Core.DB;

/**
 *
 * @author Clayton
 */
public class HSQLDBSchemas {
   
   private HSQLDBSchemas() {}
   
   static String cropPlansListSchema() {
      
      String s;
      
      s  = " id        INTEGER IDENTITY, ";
      s += " plan_name VARCHAR(256) ";
      
      return s;
      
   }
   
   static String cropPlanSchema() {
      
      String s;
      
      /* this schema, by itself, could be considered a "planting" */
      s  = " id          INTEGER IDENTITY, ";
      s += " crop_name   VARCHAR, ";
      s += " var_name    VARCHAR, ";
      s += " groups      VARCHAR, ";
      s += " ds          BOOLEAN, ";
      s += " tp          BOOLEAN, ";
      s += " successions BOOLEAN, ";
      s += " location    VARCHAR, ";
      
      s += " keywords  VARCHAR, ";
      s += " status    VARCHAR, ";
      s += " completed VARCHAR, ";
      s += " fudge     FLOAT, ";
      s += " other_req VARCHAR, ";
      s += " notes     VARCHAR, ";
      
      s += " maturity      INTEGER, ";
      s += " mat_adjust    INTEGER, ";
      s += " tp_adjust     INTEGER, ";
      s += " ds_adjust     INTEGER, ";
      s += " season_adjust INTEGER, ";
      s += " time_to_tp    INTEGER, ";
      s += " misc_adjust   INTEGER, ";
      s += " date_plant    DATE, ";
      s += " date_tp       DATE, ";
      s += " date_harvest  DATE, ";
      
      s += " beds_to_plant   INTEGER, ";
      s += " ds_rows_p_bed   INTEGER, ";
      s += " tp_rows_p_bed   INTEGER, ";
      s += " plants_needed   INTEGER, ";
      s += " rowft_to_plant  INTEGER, ";
      s += " ds_inrow_space  FLOAT, ";
      s += " ds_row_space    FLOAT, ";
      s += " tp_inrow_space  FLOAT, ";
      s += " tp_row_space    FLOAT, ";
      s += " plants_to_start INTEGER, ";
      s += " flat_size       VARCHAR, ";
      s += " flats_needed    INTEGER, ";
      s += " planter         VARCHAR, ";
      s += " planter_setting VARCHAR, ";
      
      s += " ds_yield_p_foot    FLOAT, ";
      s += " tp_yield_p_foot    FLOAT, ";
      s += " total_yield        FLOAT, ";
      s += " tp_yield_num_weeks INTEGER, ";
      s += " tp_yield_p_week    FLOAT, ";
      s += " ds_yield_num_weeks INTEGER, ";
      s += " ds_yield_p_week    FLOAT, ";
      
      s += " ds_crop_unit       VARCHAR, ";
      s += " ds_crop_unit_value FLOAT, ";
      s += " tp_crop_unit       VARCHAR, ";
      s += " tp_crop_unit_value FLOAT, ";
      
      return s;
   }
   
   static String cropAndVarietySchema() {
      
      String s;
      
      s   = "id                 INTEGER IDENTITY, ";
      s  += "crop_name          VARCHAR(256), ";
      s  += "var_name           VARCHAR, ";
      s  += "bot_name           VARCHAR, ";
      s  += "fam_name           VARCHAR, ";
      s  += "groups             VARCHAR, ";
      s  += "ds                 BOOLEAN, ";
      s  += "tp                 BOOLEAN, ";
      s  += "successions        BOOLEAN, ";
      
      s  += "keywords           VARCHAR, ";
      s  += "fudge              FLOAT, ";
      s  += "other_req          VARCHAR, ";
      s  += "notes              VARCHAR, ";
      
      s  += "maturity           INTEGER, ";
      s  += "mat_adjust         INTEGER, ";
      s  += "tp_adjust          INTEGER, ";
      s  += "ds_adjust          INTEGER, ";
      s  += "season_adjust      INTEGER, ";
      s  += "time_to_tp         INTEGER, ";
      s  += "misc_adjust        INTEGER, ";
      
      s  += "ds_rows_p_bed      INTEGER, ";
      s  += "tp_rows_p_bed      INTEGER, ";
      s  += "ds_inrow_space     INTEGER, ";
      s  += "ds_row_space       INTEGER, ";
      s  += "tp_inrow_space     INTEGER, ";
      s  += "tp_row_space       INTEGER, ";
      s  += "flat_size          VARCHAR, ";
      s  += "planter            VARCHAR, ";
      s  += "planter_setting    VARCHAR, ";
      
      s  += "ds_yield_p_foot    FLOAT, ";
      s  += "tp_yield_p_foot    FLOAT, ";
      s  += "tp_yield_num_weeks INTEGER, ";
      s  += "tp_yield_p_week    FLOAT, ";
      s  += "ds_yield_num_weeks INTEGER, ";
      s  += "ds_yield_p_week    FLOAT, ";
      s  += "ds_crop_unit       VARCHAR, ";
      s  += "ds_crop_unit_value FLOAT, ";
      s  += "tp_crop_unit       VARCHAR, ";
      s  += "tp_crop_unit_value FLOAT, ";
      
      s  += "seeds_sources      VARCHAR, ";
      s  += "seeds_item_code    VARCHAR, ";
      s  += "seeds_unit_size    VARCHAR, ";
      
      return s;
   }
   
}
