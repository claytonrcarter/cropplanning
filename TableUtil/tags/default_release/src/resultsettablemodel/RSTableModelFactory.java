/*
 * RSTableModelFactory.java
 *
 * Created on March 14, 2007, 10:17 AM
 *
 *
 */

package resultsettablemodel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.*;

/**
 *
 * @author Clayton
 */
public class RSTableModelFactory extends ResultSetTableModelFactory {
   
   ResultSet rSet = null;
   
    /** Constructor method to use when program already has a valid database connection */
    public RSTableModelFactory( Connection con ) {
       // must call super()
       super.connection = con;
    }
    
    public RSTableModelFactory( ResultSet rs ) {
       rSet = rs;
    }
    
    public TableModel getResultSetTableModel() {
	if ( rSet != null )
           try {
              return new ResultSetTableModel( rSet );
           }
           catch ( SQLException e ) { e.printStackTrace(); }
        
        // following statement will fire in case of "else" and "catch"
        return (TableModel) new DefaultTableModel();   
    }
   
    // do nothing when garbage collecting
    public void finalize() {}
}
