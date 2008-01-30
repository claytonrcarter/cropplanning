/* HSQLTableModel.java - Created: March 14, 2007
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
//       return super.getValueAt( r, c );
       // this was yanked and reworked from superclass; it was just converting
       // everything to a String but we need to keep it as Objects
       Object o = null;
       try {
	    results.absolute(r+1);      // Go to the specified row
	    o = results.getObject(c+1); // Get value of the column
	} catch (SQLException e) { return e.toString(); }
       return o;
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
