/* HSQLDBPopulator.java - Created: March 13, 2007
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
