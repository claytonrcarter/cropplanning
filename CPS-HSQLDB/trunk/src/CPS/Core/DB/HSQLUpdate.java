/* HSQLUpdate.java - created: Feb 11, 2008
 * Copyright (C) 2008 Clayton Carter
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
 * 
 */

package CPS.Core.DB;

import CPS.Module.CPSModule;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A class to handle updating the database between versions.
 * 
 * @author Clayton Carter
 */
public class HSQLUpdate {
    
   protected static void updateDB( Connection con, long currentVersion ) {
       
       long previousVersion;
       
       try { 
           // if the previously used version was so old as to not even
           // have the CPS_METADATA table
           if ( ! HSQLConnect.tableExists( con, "CPS_METADATA" ) ) {
               CPSModule.debug( "HSQLUpdate", "Metadata table DNE, attempting to create");
               Statement st = con.createStatement();
               st.executeUpdate( HSQLDBCreator.createTableDBMetaData() );
               HSQLDBCreator.setLastUsedVersion( con, 0 );
               st.close();
           }
       
           // get previous version number
           previousVersion = HSQLQuerier.getLastUsedVersion( con );
       
           if ( previousVersion == -1 ) {
               System.err.println("Error updating db: error retrieving previously used program version");
               return;
           }
           
           if ( previousVersion > currentVersion ) {
               System.err.println("Error updating db: previously used program version GREATER than current version" );
               return;
           }
           
           if ( previousVersion == currentVersion ) {
               CPSModule.debug( "HSQLUpdate", "DB is up to date!");
               // nothing to do
               return;
           }
           
           // version 0.1.2
           if ( previousVersion < CPSModule.versionAsLongInt( 0, 1, 2 ))
               updateForV000_001_002(con);
       
           // version 0.1.3
           if ( previousVersion < CPSModule.versionAsLongInt( 0, 1, 3 ))
               updateForV000_001_003(con);
           
           if ( previousVersion < CPSModule.versionAsLongInt( 0, 1, 4 ))
             updateForV000_001_004( con );

           if ( previousVersion < CPSModule.versionAsLongInt( 0, 1, 5 ))
              updateForV000_001_005( con );
           
       }
       catch ( Exception ignore ) { ignore.printStackTrace(); }
       
       HSQLDBCreator.setLastUsedVersion( con, currentVersion );
           
   }
   
   
   private static void updateForV000_001_002( Connection con ) throws java.sql.SQLException {
       
       CPSModule.debug( "HSQLUpdate", "Updating DB for changes in version 0.1.2" );
       
       ArrayList<String> plans = HSQLQuerier.getDistinctValuesForColumn( con, "CROP_PLANS", "plan_name" );
       Statement st = con.createStatement();
       String update;
       
       // REMOVE FOREIGN KEY crop_id from crop plans
       // this is heinous and annoying; there is no way to drop an unnamed 
       // FOREIGN KEY, but if we SELECT * INTO then the constraints are not copied
       // so we can work with that.
       for ( String plan : plans ) {
       
           String tempPlan = plan + "temptable";
           // copy the table (removing constraints)
           update  = "SELECT * INTO " + HSQLDB.escapeTableName( tempPlan ) + 
                     " FROM " + HSQLDB.escapeTableName( plan ) + "; ";
           // add the primary key back
           update += "ALTER TABLE " + HSQLDB.escapeTableName( tempPlan ) +
                     " ADD PRIMARY KEY ( id ); ";
           // make it auto-generate
           update += "ALTER TABLE " + HSQLDB.escapeTableName( tempPlan ) +
                     " ALTER COLUMN id INTEGER IDENTITY; ";
           // delete old table
           update += "DROP TABLE " +  HSQLDB.escapeTableName( plan ) + "; ";
           // rename working table to old table
           update += "ALTER TABLE " + HSQLDB.escapeTableName( tempPlan ) + 
                     " RENAME TO " + HSQLDB.escapeTableName( plan ) + "; ";
           CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
           st.executeUpdate(update);
           
       }
       
       // DELETE unused table PLANTING_METHODS
       update = "DROP TABLE planting_methods";
       HSQLDB.debug( "DBUpdater", "Executing update: " + update );
       st.executeUpdate( update );
       
       // DELETE "common_plantings" table and remove it from list of crop plans
       update  = "DROP TABLE common_plantings; ";
       update += "DELETE FROM crop_plans WHERE LOWER( plan_name ) = 'common_plantings'";      
       CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
       st.executeUpdate( update );
       
       st.close();
   }
   
   private static void updateForV000_001_003( Connection con ) throws java.sql.SQLException {
       
       CPSModule.debug( "HSQLUpdate", "Updating DB for changes in version 0.1.3" );
       
       Statement st = con.createStatement();
       String update;
       
       // Add columns to the crop_plans table
       update  = "ALTER TABLE crop_plans ADD COLUMN year        INTEGER; ";
       update += "ALTER TABLE crop_plans ADD COLUMN locked      BOOLEAN DEFAULT false; ";
       update += "ALTER TABLE crop_plans ADD COLUMN description VARCHAR; ";
       update += "UPDATE " + HSQLDB.escapeTableName( "CROP_PLANS" ) +
                 " SET year = " + HSQLDB.escapeValue( GregorianCalendar.getInstance().get( Calendar.YEAR ) ) +
                 " WHERE year IS NULL; ";
       
       CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
       st.executeUpdate( update );
       
       // Add columns to the crops_varieties table
       update  = "ALTER TABLE crops_varieties ADD COLUMN direct_seed BOOLEAN; ";
       update += "ALTER TABLE crops_varieties ADD COLUMN frost_hardy BOOLEAN; ";
       
       CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
       st.executeUpdate( update );
       
       ArrayList<String> plans = HSQLQuerier.getDistinctValuesForColumn( con, "CROP_PLANS", "plan_name" );
       update = "";
       for ( String plan : plans ) {
          if ( plan.matches( "^\\p{Alpha}+$" ) )
             update += "UPDATE " + HSQLDB.escapeTableName( "CROP_PLANS" ) +
                       " SET plan_name = " + HSQLDB.escapeValue( plan.toUpperCase() ) +
                       " WHERE plan_name = " + HSQLDB.escapeValue( plan ) + "; ";
       }
       if ( ! update.equals("")) {
          CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
          st.executeUpdate( update );
       }
          
       plans = HSQLQuerier.getDistinctValuesForColumn( con, "CROP_PLANS", "plan_name" );
       for ( String plan : plans ) {
       
           // Add frost_hardy column to each crop plan (direct_seed is already there)
           update = "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN frost_hardy BOOLEAN; ";
       
           CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
           st.executeUpdate(update);
           
       }
       
   }
   
   private static void updateForV000_001_004( Connection con ) throws java.sql.SQLException {
    
      CPSModule.debug( "HSQLUpdate", "Updating DB for changes in version 0.1.4" );
      
      Statement st = con.createStatement();
      String update;
       
      // alter columns in the CropDB for enhanced tracking of DS/TP info
      // add and rename columns
      update  = "ALTER TABLE crops_varieties ALTER COLUMN misc_adjust  RENAME TO ds_mat_adjust; ";
      
      CPSModule.debug( "DBUpdater", "Executing update: " + update );
      st.executeUpdate( update );
      
      update  = "ALTER TABLE crops_varieties ADD   COLUMN ds_rows_p_bed  INTEGER; ";
      update += "ALTER TABLE crops_varieties ADD   COLUMN ds_row_space   FLOAT; ";
      update += "ALTER TABLE crops_varieties ADD   COLUMN ds_plant_notes VARCHAR; ";
      
      update += "ALTER TABLE crops_varieties ADD   COLUMN transplant     BOOLEAN; ";
      update += "ALTER TABLE crops_varieties ALTER COLUMN mat_adjust   RENAME TO tp_mat_adjust; ";
      update += "ALTER TABLE crops_varieties ALTER COLUMN rows_p_bed   RENAME TO tp_rows_p_bed; ";
      update += "ALTER TABLE crops_varieties ALTER COLUMN space_betrow RENAME TO tp_row_space; ";
      update += "ALTER TABLE crops_varieties ALTER COLUMN space_inrow  RENAME TO tp_inrow_space; ";
      update += "ALTER TABLE crops_varieties ALTER COLUMN time_to_tp   RENAME TO tp_time_in_gh; ";
      update += "ALTER TABLE crops_varieties ALTER COLUMN flat_size    RENAME TO tp_flat_size; ";
      update += "ALTER TABLE crops_varieties ADD   COLUMN tp_pot_up           BOOLEAN; ";
      update += "ALTER TABLE crops_varieties ADD   COLUMN tp_pot_up_notes     VARCHAR; ";
      update += "ALTER TABLE crops_varieties ALTER COLUMN planter      RENAME TO tp_plant_notes; ";

      CPSModule.debug( "DBUpdater", "Executing update: " + update );
      st.executeUpdate( update );
           
      
      // save info from column planter_setting before deleting it
      update  = "UPDATE crops_varieties " +
                " SET tp_plant_notes = CONCAT( tp_plant_notes, CONCAT( '; ', planter_setting )) " +
                " WHERE tp_plant_notes IS NOT NULL OR planter_setting IS NOT NULL; ";
      update += "ALTER TABLE crops_varieties DROP COLUMN planter_setting; ";
      
      CPSModule.debug( "DBUpdater", "Executing update: " + update );
      st.executeUpdate( update );


      ArrayList<String> plans = HSQLQuerier.getDistinctValuesForColumn( con, "CROP_PLANS", "plan_name" );
      update = "";
      for ( String plan : plans ) {
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN ignore              BOOLEAN; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN date_plant_actual   DATE; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN date_tp_actual      DATE; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN date_harvest_actual DATE; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ALTER COLUMN date_plant   RENAME TO date_plant_plan; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ALTER COLUMN date_tp      RENAME TO date_tp_plan; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ALTER COLUMN date_harvest RENAME TO date_harvest_plan; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN ds_adjust; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN planting_adjust; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN season_adjust; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN misc_adjust; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN plant_notes_spec VARCHAR; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN pot_up           BOOLEAN; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN pot_up_notes     VARCHAR; ";
         
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ALTER COLUMN planter RENAME TO plant_notes_inh; ";
         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET plant_notes_inh = CONCAT( plant_notes_inh, CONCAT( '; ', planter_setting )) " +
                   " WHERE plant_notes_inh IS NOT NULL OR planter_setting IS NOT NULL; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN planter_setting; ";
      
      }
      if ( ! update.equals( "" ) ) {
         CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
         st.executeUpdate( update );
      }

      
   }

   private static void updateForV000_001_005( Connection con ) throws java.sql.SQLException {

      CPSModule.debug( "HSQLUpdate", "Updating DB for changes in version 0.1.5" );

      Statement st = con.createStatement();
      String update;

      ArrayList<String> plans = HSQLQuerier.getDistinctValuesForColumn( con, "CROP_PLANS", "plan_name" );
      update = "";
      for ( String plan : plans ) {
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN ds_mat_adjust  INTEGER; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN ds_rows_p_bed  INTEGER; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN ds_row_space   INTEGER; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN ds_crop_notes  VARCHAR; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN tp_mat_adjust  INTEGER; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN tp_rows_p_bed  INTEGER; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN tp_row_space   INTEGER; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " ADD COLUMN tp_crop_notes  VARCHAR; ";

         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET ds_mat_adjust = mat_adjust " +
                   " WHERE mat_adjust IS NOT NULL AND direct_seed = TRUE; ";
         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET tp_mat_adjust = mat_adjust " +
                   " WHERE mat_adjust IS NOT NULL AND direct_seed = FALSE; ";

         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET ds_rows_p_bed = rows_p_bed " +
                   " WHERE rows_p_bed IS NOT NULL AND direct_seed = TRUE; ";
         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET tp_rows_p_bed = rows_p_bed " +
                   " WHERE rows_p_bed IS NOT NULL AND direct_seed = FALSE; ";

         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET ds_row_space = row_space " +
                   " WHERE row_space IS NOT NULL AND direct_seed = TRUE; ";
         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET tp_row_space = row_space " +
                   " WHERE row_space IS NOT NULL AND direct_seed = FALSE; ";

         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET ds_crop_notes = plant_notes_inh " +
                   " WHERE plant_notes_inh IS NOT NULL AND direct_seed = TRUE; ";
         update += "UPDATE " + HSQLDB.escapeTableName( plan ) +
                   " SET tp_crop_notes = plant_notes_inh " +
                   " WHERE plant_notes_inh IS NOT NULL AND direct_seed = FALSE; ";

         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN mat_adjust; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN rows_p_bed; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN row_space; ";
         update += "ALTER TABLE " + HSQLDB.escapeTableName( plan ) + " DROP COLUMN plant_notes_inh; ";
      }

      if ( ! update.equals( "" ) ) {
         CPSModule.debug( "HSQLUpdate", "Executing update: " + update );
         st.executeUpdate( update );
      }
   }
}
