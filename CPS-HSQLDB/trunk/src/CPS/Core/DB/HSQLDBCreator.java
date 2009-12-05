/* HSQLDBCreator.java - Created: February 1, 2007
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

import CPS.Data.*;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSModule;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import net.sf.persist.Persist;

/**
 *
 * @author Clayton
 */
public class HSQLDBCreator {

    // TODO eliminate the use of p.executeUpdate() calls in favor of passing objects into Persist

   // private constructor == no instantiation
   private HSQLDBCreator() {}


   /* ********************************************************************************************************* */
   /* Create table methods */
   /* ********************************************************************************************************* */

   // package level access
   static void createTables( Persist p, long currentVersion ) {

       HSQLDB.debug( "HSQLDBCreator", "creating default tables in db" );
       
       // there is currently no POJO for this, so we'll just stick with the raw SQL
       p.executeUpdate( createTableDBMetaData() );
       setLastUpdateVersion( p, currentVersion );
         
       // ditto here: stick with the raw SQL
       p.executeUpdate( createTableCropPlans() );

       createTableCropsAndVarieties( p );
         
   }
   
   protected static String createTableDBMetaData() {
       return statementCreateTable( "CPS_METADATA", HSQLDBSchemas.cpsDbMetaDataSchema() ) + "; " +
              "INSERT INTO CPS_METADATA ( prev_ver ) VALUES ( 0 )";
   }
   
   private static String createTableCropPlans() {
      return statementCreateTable( "CROP_PLANS", HSQLDBSchemas.cropPlansListSchema() );
   }
   
   private static String createTableCropPlan( String name ) {
      return statementCreateTable( name, HSQLDBSchemas.cropPlanSchema() );
   }

   /**
    * Create the table CROPS_VARIETIES, generating the schema from a CPSCrop object.
    * @param p a Persist object connected to the db in which the table will be created.
    */
   private static void createTableCropsAndVarieties( Persist p ) {

       p.create( HSQLDB.CROP_VAR_TABLE, new CPSCrop() );

   }

   /**
    * Given a table name and an SQL schema definition (of field names and data
    * types), will form an SQL state that can be used to create the table in question.
    *
    * @param name name of table to be created
    * @param table_def schema def'n consisting of field names and data types, separated by commas
    * @return an SQL statement that can be used to create the table in question
    */
   private static String statementCreateTable( String name, String table_def ) {

      if ( table_def.endsWith( "," ))
         table_def = table_def.substring( 0, table_def.length() - 1 );
      if ( table_def.endsWith( ", " ))
         table_def = table_def.substring( 0, table_def.length() - 2 );
      
      return "CREATE TABLE " + HSQLDB.escapeTableName( name ) + " ( " + table_def + " ) ";
   }
   
   /* ********************************************************************************************************* */
   /* Table metadata methods */
   /* ********************************************************************************************************* */
   
   /**
    * Updates the DB metadata table to record which program version was the last to update the database.  This
    * figure chould be different from the currect program version if there have been one or more program
    * releases made the last release which required a db structure update.  (eg, LastUpdateVersion could be
    * 1.0.2 while current version is 1.0.6)
    * This method should technically only be called when the db is first created and when explicit updates
    * to the db schema and/or structure are made.
    * @param con - JDBC Connection upon which to execute the version update statement.
    * @param version - long int version number to set
    */
   public static void setLastUpdateVersion( Persist p, long version ) {
       
       String sqlUpdate = "UPDATE " + HSQLDB.escapeTableName( "CPS_METADATA" ) +
                          " SET prev_ver = " + version;
         
       CPSModule.debug( "HSQLDBCreator","Attempting to execute: " + sqlUpdate );
       p.executeUpdate( sqlUpdate );

   }
   
   /* ********************************************************************************************************* */
   /* Crop plan methods */
   /* ********************************************************************************************************* */

   static void createCropPlan( Persist p, String name, int year, String desc ) {
      
      // TODO error if plan with name already exists
      HSQLDB.debug( "HSQLDBCreator", "Creating crop plan: " + HSQLDB.escapeTableName( name ) + " (" + year + ")" );

       p.create( name, new CPSPlanting() );

       // Record the plan in the table listing all of the plans.
       String s = "INSERT INTO CROP_PLANS( plan_name ) VALUES( " + HSQLDB.escapeValue( name ) + " );";

       System.out.println( "Executing update: " + s );
       p.executeUpdate( s );
         
       updateCropPlan( p, name, year, desc );

   }
   
   static void updateCropPlan( Persist p, String name, int year, String desc ) {

       // Update the plan metadata
       String s = "UPDATE " + HSQLDB.escapeTableName( "CROP_PLANS" ) +
                  " SET year = " + year + ", description = " +  HSQLDB.escapeValue( desc ) +
                  " WHERE plan_name = " + HSQLDB.escapeValue( name );
      
       CPSModule.debug( "HSQLDBCreator", "Executing update: " + s );
       p.executeUpdate( s );
         
   }
   
   static void deleteCropPlan( Persist p, String name ) {

       // Drop the table
       String s = "DROP TABLE " + HSQLDB.escapeTableName( name );

       CPSModule.debug( "HSQLDBCreator", "Executing update: " + s );
       p.executeUpdate( s );

       // Remove the table record from the plan metadata table
       s = "DELETE FROM CROP_PLANS WHERE plan_name = " + HSQLDB.escapeValue( name );
      
       CPSModule.debug( "HSQLDBCreator", "Executing update: " + s );
       p.executeUpdate( s );
       
   }
   

   /* ********************************************************************************************************* */
   /* Crop Methods */
   /* ********************************************************************************************************* */
   
   public static int insertCrop( Persist p, CPSCrop crop ) {

       crop.useRawOutput( true );
       // insert the new crop
       p.insert( HSQLDB.CROP_VAR_TABLE, crop );
       crop.useRawOutput( false );

       // retrieve the new ID
       crop.setID( getLastIdentity( p ) );

       CPSModule.debug( "HSQLDBCreator", "Inserted " + crop.getCropName() + " with id " + crop.getID() );
         
       return crop.getID();

   }
   
   public static void updateCrop( Persist p, CPSCrop crop ) {
       crop.useRawOutput( true );
       p.update( HSQLDB.CROP_VAR_TABLE, crop);
       crop.useRawOutput( false );
   }

   public static void updateCrops( Persist p, HSQLColumnMap map, CPSCrop changes, List<Integer> ids ) {

       CPSCrop tempCrop;
       for ( Integer I : ids ) {
           tempCrop = p.readByPrimaryKey( HSQLDB.CROP_VAR_TABLE, CPSCrop.class, I.intValue() );
           tempCrop.merge( changes );
           tempCrop.useRawOutput( true );
           p.update( HSQLDB.CROP_VAR_TABLE, tempCrop );
           tempCrop.useRawOutput( false );
       }

//      updateRecords( p.getConnection(), CPSDataModelConstants.RECORD_TYPE_CROP, map, HSQLDB.CROP_VAR_TABLE, changes, ids, null );
   }


   /* ********************************************************************************************************* */
   /* Planting methods */
   /* ********************************************************************************************************* */

   public static int insertPlanting( Persist p,
                                     String planName,
                                     CPSPlanting planting ) {

       planting.useRawOutput( true );
       p.insert( planName, planting );
       planting.useRawOutput( false );

       planting.setID( getLastIdentity( p ) );

       CPSModule.debug( "HSQLDBCreator", "Inserted " + planting.getCropName() + " with id " + planting.getID() );

       return planting.getID();   
      
   }

   public static void updatePlanting( Persist p, String planName, CPSPlanting planting ) {
       planting.useRawOutput( true );
       p.update( planName, planting );
       planting.useRawOutput( false );

   }

   public static void updatePlantings( Persist p,
                                       HSQLColumnMap map,
                                       String planName,
                                       CPSPlanting changes, 
                                       List<Integer> ids,
                                       List<Integer> cropIDs ) {
       
       CPSPlanting tempPlant;
       for ( Integer I : ids ) {
           tempPlant = p.readByPrimaryKey( planName, CPSPlanting.class, I.intValue() );
           tempPlant.merge( changes );
           tempPlant.useRawOutput( true);
           p.update( planName, tempPlant );
           tempPlant.useRawOutput( false );
       }

//      updateRecords( con, CPSDataModelConstants.RECORD_TYPE_PLANTING, map, planName, changes, ids, cropIDs );
   }

   /* ********************************************************************************************************* */
   /* Generic methods */
   /* ********************************************************************************************************* */

   /**
    * Update a list of (one or more) records.
    * 
    * @param con A connection upon which to perform the update.
    * @param tableName The table to update.
    * @param changes A "sparse" CPSRecord object containing the data to update.
    * @param changedIDs A list of record ids to update.
    * @param cropIDs A list ids from table CROPS_VARIETIES that correspond to the list of record ids.
    *                This may be "null" if the records being updated are CPSCrops, but MUST NOT be 
    *                "null" when CPSPlantings are being updated.  The list must correspond in length
    *                and order to the list of records to be updated.
    */
   private static void updateRecords( Connection con, 
                                      int recordType,
                                      HSQLColumnMap map,
                                      String tableName,
                                      CPSRecord changes, 
                                      List<Integer> changedIDs,
                                      List<Integer> cropIDs ) {
      // To the developer: a simpler structure for this method would be to just create one
      // UPDATE statement with the condition if "WHERE id IN ( changedIDs )", but we have to
      // do it this way in order to support the crop_id FOREIGN KEY in the crop plan schema
      
      try {
         
         // Build the list of changes to commit to each record
         String sqlChanges = "";
         Iterator<CPSDatum> iter = changes.iterator();
         CPSDatum c;
         while ( iter.hasNext() ) {
            c = iter.next();
            if ( c.isNotNull() ) {
               if ( recordType == CPSDataModelConstants.RECORD_TYPE_CROP )
                  sqlChanges += map.getCropColumnNameForProperty( c.getPropertyNum() );
               else // if ( recordType == CPSDataModelConstants.RECORD_TYPE_PLANTING )
                  sqlChanges += map.getPlantingColumnNameForProperty( c.getPropertyNum() );
                  
               sqlChanges += " = " + HSQLDB.escapeValue( c.getValue() ) + ", ";
            }
         }
         sqlChanges = sqlChanges.substring( 0, sqlChanges.lastIndexOf( ", " ) );

         
         // Build the overall update statement for each record id
         String sqlUpdate = "";
         for ( int i = 0; i < changedIDs.size(); i++ ) {
       
            sqlUpdate += "UPDATE " + HSQLDB.escapeTableName( tableName ) + " SET " + sqlChanges;
            
            if ( changes instanceof CPSPlanting )
               sqlUpdate += ", crop_id = " + cropIDs.get(i).intValue() ;
         
            // the first space character on the following line is CRUCIAL
            sqlUpdate += " " + "WHERE id = " + changedIDs.get(i).intValue() + " ; ";
         
         }
         
         System.out.println("Attempting to execute: " + sqlUpdate );
         Statement st = con.createStatement();
         st.executeUpdate( sqlUpdate );
         st.close();
         
      }
      catch ( SQLException ex ) { ex.printStackTrace(); }
   }

   public static void deleteRecord( Persist p, String table, int row ) {

       String s = "DELETE FROM " + HSQLDB.escapeTableName( table ) + " WHERE id = " + row;

       CPSModule.debug( "HSQLDBCreator", "Executing update: " + s );
       p.executeUpdate( s );

   }

   /* ********************************************************************************************************* */
   /* Utility methods */
   /* ********************************************************************************************************* */

   private static int getLastIdentity( Persist p ) {

       int i = -1;

       try {
           Statement st = p.getConnection().createStatement();
           ResultSet rs = st.executeQuery( "CALL IDENTITY()" );

           rs.next();
           i = rs.getInt(1);

           rs.close();
           st.close();
       }
       catch ( SQLException e ) {
           e.printStackTrace();
       }

       return i;
       
   }
}
