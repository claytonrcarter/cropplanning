/*
 * HSQLDBPopulator.java
 *
 * Created on March 13, 2007, 11:28 AM
 *
 *
 */

package CPS.Core.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
   
   static void populateCropsAndVarieties( Connection con ) {
      
      Object[][] o = new Object[][] 
       {{ new String("Argula"),  null,                       new String("Brassica"), new Boolean( true ), new Boolean( false ), new Integer( 40 ) },
        { new String("Argula"),  new String("Sylvetta"),     new String("Brassica"), new Boolean( true ), new Boolean( true ),  new Integer( 40 ) },
        { new String("Corn"),    new String("Luther Hill"),  new String("Graminae"), new Boolean( true ), new Boolean( true ),  new Integer( 85 ) },
        { new String("Corn"),    null,                       new String("Graminae"), new Boolean( true ), new Boolean( true ),  null              },
        { new String("Corn"),    new String("Bodacious"),    null,                   null,                null,                 new Integer( 90 ) },
        { new String("Lettuce"), null,                       new String("Lactuca"),  new Boolean( true ), new Boolean( true ),  null              },
        { new String("Lettuce"), new String("New Red Fire"), null,                   null,                null,                 new Integer( 60 ) }};
   
      String statement = "INSERT into " + 
                         "CROPS_VARIETIES( crop_name, var_name, fam_name, ds, tp, maturity ) ";
         
      try { 
         Statement st = con.createStatement();
         String s;
         
         for ( int i = 0; i < o.length; i++ ) {
            s = statement + "VALUES( ";
            for ( int j = 0; j < o[i].length; j++ ) {
               if      ( o[i][j] == null )
                  s += "NULL";
               else if ( o[i][j] instanceof String )
                  s += HSQLDBCreator.escapeString( o[i][j].toString() );
               else 
                  s += o[i][j].toString();
               
               if ( j < o[i].length - 1)
                  s += ", ";
            }
            s += " )";
            
            st.executeUpdate( s );
         }
      
         
      }
      catch ( SQLException e ) {
         e.printStackTrace();
      }
   } 
}
