/*
 * HSQLQuery.java
 *
 * Created on March 15, 2007, 9:51 AM by Clayton
 */

package CPS.Core.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import resultsettablemodel.RSTableModel;

public class HSQLQuerier {
   
   Connection con;
   ResultSet rsCache;
   
   HSQLQuerier( Connection c ) {
      con = c;
   }
   
   public ResultSet getCachedResults() {
      return rsCache;
   }
   
   public TableModel getCachedResultsAsTable() {
      return tableResults( getCachedResults() );
   }
   
   public ResultSet submitQuery( String table, String columns, String conditional ) {
      rsCache = submitQuery( con, table, columns, conditional );
      return getCachedResults();
   }
   
   public TableModel tableQuery( String table, String columns, String conditional ) {
      return tableResults( submitQuery( table, columns, conditional ));
   }
   
   /*
    * Static Methods
    */
   static synchronized ResultSet submitQuery( Connection con,
                                              String table,
                                              String columns, 
                                              String conditional ) {
      ResultSet rs;
      String query = "SELECT " + columns + " FROM " + table;
      
      if ( conditional != null && conditional.length() > 0 )
         query += " WHERE ( " + conditional + " ) ";
      
      try {
         // These parameters are cribbed from ResultSetTableModelFactory
         Statement s = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
				            ResultSet.CONCUR_READ_ONLY );
         rs = s.executeQuery( query );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         rs = null;
      }
      return rs;
   }
   
   public static TableModel tableResults( ResultSet rs ) {
      try {
         return new RSTableModel( rs );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new DefaultTableModel();
      }
   }
   
   
}
