/*
 * HSQLDBCreator.java
 *
 * Created on February 1, 2007, 10:09 PM
 *
 *
 */

package CPS.Core.DB;

import CPS.Data.CPSCrop;
import CPS.Data.CropDatum;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

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
         st.executeUpdate( createTablePlantingMethods() );
         
         st.close();
         
         createCropPlan( con, "COMMON_PLANTINGS" );
      }
      catch ( SQLException e ) { e.printStackTrace(); }
         
   }
   
   static void createCropPlan( Connection con, String name ) {
      
      // TODO error if plan with name already exists
      
      try {
         Statement st = con.createStatement();
      
         st.executeUpdate( createTableCropPlan( name ) );
      
         String s = "INSERT INTO CROP_PLANS( plan_name ) VALUES( " + HSQLDB.escapeValue( name ) + " );";
      
         System.out.println("Executing update: " + s );
         st.executeUpdate( s );
         st.close();
      }
      catch ( SQLException e ) { e.printStackTrace(); }
         
   }
   
   
   private static String createTableCropPlans() {
      return statementCreateTable( "CROP_PLANS", HSQLDBSchemas.cropPlansListSchema() );
   }
   
   private static String createTableCropPlan( String name ) {
      return statementCreateTable( name, HSQLDBSchemas.cropPlanSchema() );
   }
   
   private static String createTableCropsAndVarieties() {    
      return statementCreateTable( "CROPS_VARIETIES", HSQLDBSchemas.cropAndVarietySchema() );
   }
   
   private static String createTablePlantingMethods() {
      return statementCreateTable( "PLANTING_METHODS", HSQLDBSchemas.plantingDataSchema() );
   }
   
   private static String statementCreateTable( String name, String table_def ) {
      // TODO: test if table_def ends with "," or ", " and strip it off
      if ( table_def.endsWith( "," ))
         table_def = table_def.substring( 0, table_def.length() - 1 );
      if ( table_def.endsWith( ", " ))
         table_def = table_def.substring( 0, table_def.length() - 2 );
      
      return "CREATE TABLE " + name + " ( " + table_def + " ) ";
   }
   
   public static int insertCrop( Connection con, CPSCrop crop ) {
   
      try {
         
         String cols = "";
         String vals = "";
         
         Iterator<CropDatum> i = crop.iterator();
         CropDatum c;
         
         while ( i.hasNext() ) {
            c = i.next();
            if ( c.isValid() ) {
               // System.out.println(" Processing datum: " + c.getColumnName() );
               cols += c.getColumnName() + ", ";
               vals += HSQLDB.escapeValue( c.getDatum() ) + ", ";
            }
         }
         
//         cols += "similar_to";
//         vals += HSQLDB.escapeValue( crop.getSimilarCrop().getCropName() );
         
         cols = cols.substring( 0, cols.lastIndexOf( ", " ));
         vals = vals.substring( 0, vals.lastIndexOf( ", " ));
         
         //"Fudge","mat_adjust","misc_adjust","seeds_sources","seeds_item_codes","seeds_unit_size"         
         
         String sql = "INSERT INTO CROPS_VARIETIES ( " + cols + " ) VALUES ( " + vals + " )";
         
         System.out.println( "Attempting to execute: " + sql );
         
         Statement st = con.createStatement();
         if ( st.executeUpdate( sql ) == -1 )
            System.err.println( "Error creating crop " + crop.getCropName() );
         
         ResultSet rs = st.executeQuery( "CALL IDENTITY()" );
         rs.next();
         int newID = rs.getInt(1);
         rs.close();
         st.close();
         
         System.out.println( "Inserted " + crop.getCropName() + " with id " + newID );
         
         return newID;
         
      }
      catch ( SQLException ex ) { 
         ex.printStackTrace(); 
         return -1;
      }
   }
   
   public static void updateCrop( Connection con, CPSCrop crop ) {
      
      try {
         
         String sql = "UPDATE " + "CROPS_VARIETIES" + " SET ";
         
         Iterator<CropDatum> i = crop.iterator();
         CropDatum c;
         
         while ( i.hasNext() ) {
            c = i.next();
            if ( c.isValid() )
               sql += c.getColumnName() + " = " + HSQLDB.escapeValue( c.getDatum() ) + ", ";
         }
         
         sql = sql.substring( 0, sql.lastIndexOf( ", " ));
         //sql += "similar_to = " + HSQLDB.escapeValue( crop.getSimilarCrop().getCropName() );
         
         // this space is crucial
         sql += " " + "WHERE id = " + crop.getID();
         
         System.out.println("Attempting to execute: " + sql );

         
         Statement st = con.createStatement();
         st.executeUpdate( sql );
         st.close();
         
      }
      catch ( SQLException ex ) { ex.printStackTrace(); }
   }
   
   
}
