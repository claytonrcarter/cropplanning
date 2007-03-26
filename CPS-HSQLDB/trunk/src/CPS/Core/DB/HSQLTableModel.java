/*
 * RSTableModel.java
 *
 * Created on March 14, 2007, 11:46 PM
 *
 */

package CPS.Core.DB;

import com.sun.crypto.provider.RSACipher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import resultsettablemodel.*;

public class HSQLTableModel extends ResultSetTableModel implements TableModelListener {
   
   public HSQLTableModel( ResultSet resSet ) throws SQLException {
      super(resSet);
      if ( results.getType() == ResultSet.TYPE_FORWARD_ONLY )
         throw new SQLException( "RSTableModel does not accept ResultSets of TYPE_FORWARD_ONLY" );
      if ( results.getConcurrency() != ResultSet.CONCUR_READ_ONLY )
         throw new SQLException( "RSTableModel only accepts ResultSets of CONCUR_READ_ONLY" );
   }

   
   
   public void tableChanged( TableModelEvent e ) {
      
      int row = e.getFirstRow();
      int column = e.getColumn();
      TableModel model = (TableModel) e.getSource();
      String columnName = model.getColumnName(column);
      Object data = model.getValueAt(row, column);
      
      System.out.println("Column " + columnName + " changed to " + data.toString() );
      
   }
   
    public boolean isCellEditable(int row, int column) { return true; } 

    // Since its not editable, we don't need to implement these methods
    public void setValueAt(Object value, int row, int column) {
       
       String val = value.toString();
       System.out.println( "Setting column " + column + ", row " + row + ": to " + value.toString() );
      
       try {
         
          if ( metadata.getColumnType(column+1) == java.sql.Types.VARCHAR ||
               metadata.getColumnType(column+1) == java.sql.Types.CHAR    ||
               metadata.getColumnType(column+1) == java.sql.Types.LONGVARCHAR )
             val = HSQLDBCreator.escapeString( val );
          
          results.absolute(row+1);
          String sql = "UPDATE " + metadata.getTableName(1) + " ";
          sql += "SET " + metadata.getColumnName( column+1 ) + " = " + val + " ";
          sql += "WHERE id = " + results.getInt( "id" );
          // sql += "WHERE " + metadata.getColumnName(1) + " = " + results.getInt(1);
      
          System.out.println("Attempting to execute: " + sql );

          // HACK!
          results.getStatement().getConnection().createStatement().executeUpdate( sql );
          // results.refreshRow();
          results = ((PreparedStatement) results.getStatement()).executeQuery();
          fireTableDataChanged();
         
       }
       catch ( SQLException ex ) { ex.printStackTrace(); }
         
    }
    
    public void addTableModelListener(TableModelListener l) {}
    public void removeTableModelListener(TableModelListener l) {}
   
}
