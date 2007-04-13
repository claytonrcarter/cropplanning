/*
 * HSQLDBPopulator.java
 *
 * Created on March 13, 2007, 11:28 AM
 *
 *
 */

package CPS.Core.DB;

import CPS.CSV;
import CPS.Data.CPSCrop;
import CPS.Module.CPSDataModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Clayton
 */
public class HSQLDBPopulator {
   
   private HSQLDBPopulator() {}
   
   static void populateTables( Connection con ) {
      populateCropsAndVarieties( con );
   }
   
   static void importCropsAndVarieties( Connection con ) {
      
      // 
      
      
      
   }
      
   static void populateCropsAndVarieties( Connection con ) {
      Object[][] o = new Object[][] 
       {{ new String("Argula"),  null,                       new String("Brassica"), new Integer( 40 ) },
        { new String("Argula"),  new String("Sylvetta"),     new String("Brassica"), new Integer( 40 ) },
        { new String("Corn"),    new String("Luther Hill"),  new String("Graminae"), new Integer( 85 ) },
        { new String("Corn"),    null,                       new String("Graminae"), null              },
        { new String("Corn"),    new String("Bodacious"),    null,                   new Integer( 90 ) },
        { new String("Lettuce"), null,                       new String("Lactuca"),  null              },
        { new String("Lettuce"), new String("New Red Fire"), null,                   new Integer( 60 ) }};
   
      String statement = "INSERT into " + 
                         "CROPS_VARIETIES( crop_name, var_name, fam_name, maturity ) ";
         
      try { 
         Statement st = con.createStatement();
         String s;
         
         for ( int i = 0; i < o.length; i++ ) {
            s = statement + "VALUES( ";
            for ( int j = 0; j < o[i].length; j++ ) {
               if      ( o[i][j] == null )
                  s += "NULL";
               else if ( o[i][j] instanceof String )
                  s += HSQLDB.escapeValue( o[i][j].toString() );
               else 
                  s += o[i][j].toString();
               
               if ( j < o[i].length - 1)
                  s += ", ";
            }
            s += " )";
            
            st.executeUpdate( s );
         }
      
         st.close();
         
      }
      catch ( SQLException e ) {
         e.printStackTrace();
      }
   } 

   public static CPSDataModel loadDefaultCropList( String dir ) {
      
      return new CSV( dir + System.getProperty("file.separator") + "CropVarList.csv");
   }
}
