/*
 * HSQLQuery.java
 *
 * Created on March 15, 2007, 9:51 AM by Clayton
 */

package CPS.Core.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import CPS.Core.DB.HSQLTableModel;

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
   
   public TableModel tableQuery( String table, String columns, String conditional ) {
      return tableResults( storeQuery( table, columns, conditional ));
   }
      
   public ResultSet storeQuery( String table, String columns, String conditional ) {
      return submitQuery( con, table, columns, conditional, true );
   }
   public ResultSet submitQuery( String table, String columns, String conditional ) {
      return submitQuery( con, table, columns, conditional, false );
   }
   
   private ResultSet submitQuery( String table, String columns, String conditional, boolean store ) {
      rsCache = submitQuery( con, table, columns, conditional, store );
      return getCachedResults();
   }
   
   /*
    * Static Methods
    */
   static synchronized ResultSet submitQuery( Connection con,
                                              String table,
                                              String columns, 
                                              String conditional,
                                              boolean prepared ) {
      ResultSet rs;
      String query = "SELECT " + columns + " FROM " + table;
      
      if ( conditional != null && conditional.length() > 0 )
         query += " WHERE ( " + conditional + " ) ";
      
      System.out.println("Submitting query: " + query );
      
      try {
         if ( prepared ) {
            PreparedStatement ps = con.prepareStatement( query,
                                                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                         ResultSet.CONCUR_READ_ONLY );
            rs = ps.executeQuery();
         }
         else {            // These parameters are cribbed from ResultSetTableModelFactory
            Statement s = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY );
            rs = s.executeQuery( query );
         }
            
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         rs = null;
      }
      return rs;
   }
   
   public static TableModel tableResults( ResultSet rs ) {
      try {
         return new HSQLTableModel( rs );
      }
      catch ( SQLException e ) {
         e.printStackTrace();
         return new DefaultTableModel();
      }
   }
   
   
}
