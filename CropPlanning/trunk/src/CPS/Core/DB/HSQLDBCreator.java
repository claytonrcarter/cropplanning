/*
 * HSQLDBCreator.java
 *
 * Created on February 1, 2007, 10:09 PM
 *
 *
 */

package CPS.Core.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Clayton
 */
public class HSQLDBCreator {
   
   // private constructor == no instantiation
   private HSQLDBCreator() {}
   
   // package level access
   static void createTables( Connection con ) {
      
      try {
         Statement st = con.createStatement();
      
         st.executeUpdate( createTableCropPlans() );
         st.executeUpdate( createTableCropsAndVarieties() );
      
         st.executeUpdate( createTableCropPlan( "COMMON_PLANTINGS" ) );
      }
      catch ( SQLException e ) { e.printStackTrace(); }
         
   }
   
   static void createCropPlan( Connection con, String name ) {
      
      // TODO error if plan with name already exists
      
      try {
         Statement st = con.createStatement();
      
         st.executeUpdate( createTableCropPlan( name ) );
      
         String s = "INSERT INTO CROP_PLANS( name ) VALUES( " + name + " )";
      
         st.executeUpdate( s );
      }
      catch ( SQLException e ) { e.printStackTrace(); }
         
   }
   
   
   private static String createTableCropPlans() {
      
      String s;
      
      s  = " id   INTEGER IDENTITY, ";
      s += " name VARCHAR(256) ";
      
      return buildTableCreationString( "CROP_PLANS", s );
      
   }
   
   private static String createTableCropPlan( String name ) {
      
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
      
      return buildTableCreationString( name, s );
      
   }
   
   /* if var is true, then create table for VARIETIES
    * else                 create table for CROPS
    */
   private static String createTableCropsAndVarieties() {
      
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
      
      return buildTableCreationString( "CROPS_VARIETIES", s );
      
   }
   
   private static String buildTableCreationString( String name, String table_def ) {
      // TODO: test if table_def ends with "," or ", " and strip it off
      if ( table_def.endsWith( "," ))
         table_def = table_def.substring( 0, table_def.length() - 1 );
      if ( table_def.endsWith( ", " ))
         table_def = table_def.substring( 0, table_def.length() - 2 );
      
      return "CREATE TABLE " + name + " ( " + table_def + " ) ";
   }
   
}
