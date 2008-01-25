/*
 * RSTableModel.java
 *
 * Created on March 14, 2007, 11:46 PM
 *
 */

package CPS.Core.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import resultsettablemodel.*;

public class HSQLTableModel extends ResultSetTableModel {
   
   private boolean idInResults = false;
   private String tableName = null;
   
   public HSQLTableModel( ResultSet resSet ) throws SQLException {
      super(resSet);
      if ( results.getType() == ResultSet.TYPE_FORWARD_ONLY )
         throw new SQLException( "RSTableModel does not accept ResultSets of TYPE_FORWARD_ONLY" );
      if ( results.getConcurrency() != ResultSet.CONCUR_READ_ONLY )
         throw new SQLException( "RSTableModel only accepts ResultSets of CONCUR_READ_ONLY" );
      
      if ( metadata.getColumnLabel( 1 ).equalsIgnoreCase( "id" ) )
         idInResults = true;
      
      // TODO find boolean columns and set renderer/editor to a JCheckBox
      // TODO adjust the width of the columns downward
      //      this could be really fancy, averaging the width of all of the contents
      //      or really simple, just leaving as is for Strings, but using preset values for
      //      numbers, booleans and dates
      
   }

   public HSQLTableModel( ResultSet rs, String tableName ) throws SQLException {
      this( rs );
      setTableName( tableName );
   }
   
    public boolean isCellEditable(int row, int column) { return true; } 
    public int getColumnCount() {
       int cols = super.getColumnCount();
       if ( idInResults )
          return cols - 1;
       else
          return cols;
    }
    
    public String getColumnName( int column ) {
       if ( idInResults )
          column++;
       return super.getColumnName( column );
    }
    
    public Object getValueAt( int r, int c ) {
       if ( idInResults )
          c++;
       return super.getValueAt( r, c );
    }
    
    // Since its not editable, we don't need to implement these methods
    public void setValueAt(Object value, int row, int column) {
    
       if ( idInResults )
          column++;
       
       
       try {
      
           String val = value.toString();
           System.out.println("Setting column " + metadata.getCatalogName(column) +
                              " (num: " + column +
                              ", type: " + metadata.getColumnTypeName( column ) +
                              "), row " + row + ": to " + value.toString());
        
          if      ( val.equals( "" ))
             val = "NULL";
          else if ( metadata.getColumnType(column+1) == java.sql.Types.VARCHAR ||
                    metadata.getColumnType(column+1) == java.sql.Types.CHAR    ||
                    metadata.getColumnType(column+1) == java.sql.Types.LONGVARCHAR ) 
             val = HSQLDB.escapeValue( val );
          else if ( metadata.getColumnType(column+1) == java.sql.Types.DATE ) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
              val = HSQLDB.escapeValue(sdf.parse((String) val));
          }
 
          System.out.println( "Value escaped for SQL as: \"" + val + "\"" );
          
          results.absolute(row+1);
          String sql = "UPDATE " + this.getTableName() + " ";
          sql += "SET " + metadata.getColumnName( column+1 ) + " = " + val + " ";
          sql += "WHERE id = " + results.getInt( "id" );
          // sql += "WHERE " + metadata.getColumnName(1) + " = " + results.getInt(1);
          
          System.out.println( "Attempting to execute: " + sql );

          // HACK!
          // this makes the change to the DB
          results.getStatement().getConnection().createStatement().executeUpdate( sql );
          // this refreshes our copy of the data from the query stored by PreparedStatement
          results = ((PreparedStatement) results.getStatement()).executeQuery();
          
          fireTableDataChanged();
         
       }
       catch ( Exception ex ) { ex.printStackTrace(); }
         
    }
    
    private String getTableName() throws SQLException {
       if ( tableName == null )
          return metadata.getTableName(1);
       else
          return tableName;
    }
    private void setTableName( String s ) {
       tableName = s;
    }
   
    @Override
   public Class getColumnClass( int column ) {
       if ( idInResults )
          column++;
       
       String type = null;
       
       try {
          type = metadata.getColumnTypeName( column + 1 );
       }
       catch ( Exception e ) { e.printStackTrace(); }
       
       if      ( type == null ||
                 type.equalsIgnoreCase("VARCHAR") ||
                 type.equalsIgnoreCase("DATE") )
          return new String().getClass();
       else if ( type.equalsIgnoreCase("INTEGER") )
          return new Integer(0).getClass();
       else if ( type.equalsIgnoreCase("FLOAT") )
          return new Double(0).getClass();
       else if ( type.equalsIgnoreCase("BOOLEAN") )
          return new Boolean(true).getClass();
       else
          return new String().getClass();
       
   }
    
    
}
