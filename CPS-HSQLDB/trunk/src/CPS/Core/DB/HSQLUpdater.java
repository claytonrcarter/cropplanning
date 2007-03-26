/*
 * HSQLUpdater.java
 *
 * Created on March 23, 2007, 7:15 PM by Clayton
 */

package CPS.Core.DB;

import java.sql.*;

public class HSQLUpdater {
   
   HSQLUpdater() {
   }
   
   static synchronized void submitUpdate( Statement st,
                                               String update ) throws SQLException {
      
      st.executeUpdate( update );
      
   }
   
}
