/* HQSLDBSchemas.java - Created: March 13, 2007
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
      s += " plan_name VARCHAR(256), ";
      s += " year      INTEGER, ";
      s += " locked    BOOLEAN DEFAULT false, ";
      s += " description VARCHAR ";
      return s;
      
   }
   
   static String cropPlanSchema() {
      
      String s;
      
      /* this schema, by itself, could be considered a "planting" */
      s  = "id            INTEGER IDENTITY PRIMARY KEY, ";
      s += "crop_id       INTEGER, "; // References CROPS_VARIETIES.id
      s += "plant_mtd_id  INTEGER, "; // References PLANTING_METHODS.id
      
      s += "crop_name   VARCHAR, ";
      s += "var_name    VARCHAR, ";
      s += "groups      VARCHAR, ";
      s += "location    VARCHAR, ";
      
      s += "keywords  VARCHAR, ";
      s += "status    VARCHAR, ";
      s += "fudge     FLOAT, "; // TODO remove
      s += "other_req VARCHAR, ";
      s += "notes     VARCHAR, ";
      
      s += "maturity        INTEGER, ";
      s += "ds_mat_adjust   INTEGER, ";
      s += "tp_mat_adjust   INTEGER, ";
      s += "time_to_tp      INTEGER, ";
      s += "direct_seed     BOOLEAN, ";
      s += "frost_hardy     BOOLEAN, ";
      s += "ignore          BOOLEAN, ";
      
      s += "date_plant_plan     DATE, ";
      s += "done_plant          BOOLEAN, ";
      s += "date_plant_actual   DATE, ";
      s += "date_tp_plan        DATE, ";
      s += "done_tp             BOOLEAN, ";
      s += "date_tp_actual      DATE, ";
      s += "date_harvest_plan   DATE, ";
      s += "done_harvest        BOOLEAN, ";
      s += "date_harvest_actual DATE, ";
      
      s += "beds_to_plant    FLOAT, ";
      s += "ds_rows_p_bed    INTEGER, ";
      s += "tp_rows_p_bed    INTEGER, ";
      s += "plants_needed    INTEGER, ";
      s += "rowft_to_plant   INTEGER, ";
      s += "inrow_space      FLOAT, ";
      s += "ds_row_space     FLOAT, ";
      s += "tp_row_space     FLOAT, ";
      s += "plants_to_start  INTEGER, ";
      s += "flat_size        VARCHAR, ";
      s += "flats_needed     FLOAT, ";
      s += "ds_crop_notes    VARCHAR, ";
      s += "tp_crop_notes    VARCHAR, ";
      s += "plant_notes_spec VARCHAR, ";
      s += "pot_up           BOOLEAN, ";
      s += "pot_up_notes     VARCHAR, ";
//      s += "planter_setting VARCHAR, ";
      
      s += "yield_p_foot    FLOAT, ";
      s += "total_yield     FLOAT, ";
      s += "yield_num_weeks INTEGER, ";
      s += "yield_p_week    FLOAT, ";
      
      s += "crop_unit       VARCHAR, ";
      s += "crop_unit_value FLOAT, ";
      
      s += "custom1         VARCHAR, ";
      s += "custom2         VARCHAR, ";
      s += "custom3         VARCHAR, ";
      s += "custom4         VARCHAR, ";
      s += "custom5         VARCHAR, ";
      
//      s += "FOREIGN KEY ( crop_id ) REFERENCES crops_varieties ( id ), ";
      
      return s;
   }
   
   static String cropAndVarietySchema() {
      
      String s;
      
      s  = "id                 INTEGER IDENTITY PRIMARY KEY, ";
      s += "crop_name          VARCHAR(256), ";
      s += "var_name           VARCHAR, ";
      s += "similar_to         VARCHAR, ";
      s += "bot_name           VARCHAR, ";
      s += "fam_name           VARCHAR, ";
      s += "groups             VARCHAR, ";
      s += "successions        BOOLEAN, ";
      
      s += "description        VARCHAR, ";
      s += "keywords           VARCHAR, ";
      s += "fudge              FLOAT, ";
      s += "other_req          VARCHAR, ";
      s += "notes              VARCHAR, ";
      
      s += "maturity           INTEGER, ";

      s += "direct_seed         BOOLEAN, ";
      s += "ds_mat_adjust       INTEGER, ";
      s += "ds_rows_p_bed       INTEGER, ";
      s += "ds_row_space        FLOAT, ";
      s += "ds_plant_notes      VARCHAR, ";
      
      s += "transplant          BOOLEAN, ";
      s += "tp_mat_adjust       INTEGER, ";
      s += "tp_rows_p_bed       INTEGER, ";
      s += "tp_row_space        FLOAT, ";
      s += "tp_inrow_space      FLOAT, ";
      s += "tp_time_in_gh       INTEGER, ";
      s += "tp_flat_size        VARCHAR, ";
      s += "tp_pot_up           BOOLEAN, ";
      s += "tp_pot_up_notes     VARCHAR, ";
      s += "tp_plant_notes      VARCHAR, ";

//      s += "direct_seed         BOOLEAN, ";
//      s += "mat_adjust          INTEGER, ";
//      s += "misc_adjust         INTEGER, ";
//      s += "rows_p_bed          INTEGER, ";
//      s += "space_inrow         FLOAT, ";
//      s += "space_betrow        FLOAT, ";
//      s += "flat_size           VARCHAR, ";
//      s += "planter             VARCHAR, ";
//      s += "planter_setting     VARCHAR, ";
//      s += "time_to_tp          INTEGER, ";
      
      s += "frost_hardy         BOOLEAN, ";
      
      s += "yield_p_foot        FLOAT, ";
      s += "yield_num_weeks     INTEGER, ";
      s += "yield_p_week        FLOAT, ";
      s += "crop_unit           VARCHAR, ";
      s += "crop_unit_value     FLOAT, ";
      // ==[ DONE ]==
      
      s += "seeds_sources      VARCHAR, "; // TODO remove; factor out
      s += "seeds_item_code    VARCHAR, "; // TODO remove; factor out
      s += "seeds_unit_size    VARCHAR, "; // TODO remove; factor out
      
      s += "custom1         VARCHAR, ";
      s += "custom2         VARCHAR, ";
      s += "custom3         VARCHAR, ";
      s += "custom4         VARCHAR, ";
      s += "custom5         VARCHAR, ";

      
      return s;
   }
   
   static String plantingDataSchema() {
      
      String s = new String();

      s  = "id      INTEGER IDENTITY PRIMARY KEY, ";
      s += "crop_id INTEGER, ";
      s += "method  VARCHAR, ";
      
      s += "mat_adjust INTEGER, ";
      s += "time_to_tp INTEGER, ";
      
      s += "rows_p_bed   INTEGER, ";
      s += "space_inrow  INTEGER, ";
      s += "space_betrow INTEGER, ";
      
      s += "flat_size       VARCHAR, ";
      s += "planter         VARCHAR, ";
      s += "planter_setting VARCHAR, ";

      s += "yield_p_foot    FLOAT, ";
      s += "yield_num_weeks INTEGER, ";
      s += "yield_p_week    FLOAT, ";
      s += "crop_unit       VARCHAR, ";
      s += "crop_unit_value FLOAT, ";

      s += "FOREIGN KEY ( crop_id ) REFERENCES " + "CROPS_VARIETIES" + "( id ), ";
      
      return s;
      
   }
   
   static String cpsDbMetaDataSchema() {
      
       String s = new String();

       s += "prev_ver  BIGINT, ";
       
       return s;
   }
   
}
