/*
 * RSTableModel.java
 *
 * Created on March 14, 2007, 11:46 PM
 *
 */

package resultsettablemodel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RSTableModel extends ResultSetTableModel {
   
   public RSTableModel( ResultSet rs ) throws SQLException {
      super(rs);
      if ( rs.getType() == ResultSet.TYPE_FORWARD_ONLY )
         throw new SQLException( "RSTableModel does not accept ResultSets of TYPE_FORWARD_ONLY" );
      if ( rs.getConcurrency() != ResultSet.CONCUR_READ_ONLY )
         throw new SQLException( "RSTableModel only accepts ResultSets of CONCUR_READ_ONLY" );
   }
   
}
