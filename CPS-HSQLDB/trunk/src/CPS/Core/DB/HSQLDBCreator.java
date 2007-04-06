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
      
         String s = "INSERT INTO CROP_PLANS( plan_name ) VALUES( " + escapeString( name ) + " );";
      
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
   
   public static String escapeString( String s ) {
      return "'" + s + "'";
   }
   
}
