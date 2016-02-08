/* HSQLTableModel.java - Created: March 14, 2007
 * Copyright (C) 2007, 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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

import CPS.Module.CPSDataModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import resultsettablemodel.*;

public class HSQLTableModel extends ResultSetTableModel {
   
    private static final boolean DEBUG = true;
    
   private boolean idInResults = false;
   private int nullsLastColNum = -2;
   private String tableName = null;
   
   private HSQLDB dataModel;
   
   public HSQLTableModel( HSQLDB dm, ResultSet resSet ) throws SQLException {
      super(resSet);
      if ( results.getType() == ResultSet.TYPE_FORWARD_ONLY )
         throw new SQLException( "RSTableModel does not accept ResultSets of TYPE_FORWARD_ONLY" );
      if ( results.getConcurrency() != ResultSet.CONCUR_READ_ONLY )
         throw new SQLException( "RSTableModel only accepts ResultSets of CONCUR_READ_ONLY" );
      
      for ( int col = 1; col <= metadata.getColumnCount(); col++ ) {
          String colName = metadata.getColumnLabel( col );
          if ( colName.equalsIgnoreCase( "id" ) )
              idInResults = true;
          else if ( colName.equalsIgnoreCase( "nulls_last" ))
              nullsLastColNum = col;
      }
      
      this.dataModel = dm;
      // TODO find boolean columns and set renderer/editor to a JCheckBox
      // TODO adjust the width of the columns downward
      //      this could be really fancy, averaging the width of all of the contents
      //      or really simple, just leaving as is for Strings, but using preset values for
      //      numbers, booleans and dates
      
   }
   
   public HSQLTableModel( HSQLDB dm, ResultSet rs, String tableName ) throws SQLException {
      this( dm, rs );
      setTableName( tableName );
   }
   
    public boolean isCellEditable(int row, int column) { return true; } 
    
    @Override
    public int getRowCount() {
        try {
            results.last();                      // Move to last row
            return results.getRow();             // How many rows?
        }
        catch ( Exception e ) {
            System.err.println("NonFatal Exception:");
            e.printStackTrace();
            return super.getRowCount();
        }
    }
    
    public int getColumnCount() {
       int cols = super.getColumnCount();
       if ( idInResults )
           if ( nullsLastColNum != -2 )
               return cols - 2;
           else
               return cols - 1;
       else
          return cols;
    }
    
    public String getColumnName( int column ) {
       if ( idInResults || column == nullsLastColNum )
          column++;
       return super.getColumnName( column );
    }
    
    public Object getValueAt( int r, int c ) {
       if ( idInResults || c == nullsLastColNum )
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
    
    public void setValueAt(Object value, int row, int column) {
    
       if ( idInResults || column == nullsLastColNum )
          column++;
       
       
       try {
      
          String val = "";
          if      ( value == null || value.toString().equals( "" ))
             val = "NULL";
          else
             val = HSQLDB.escapeValue( value );

           if ( DEBUG )
               System.out.println( "Setting column " + metadata.getCatalogName( column ) +
                                   " (num: " + column +
                                   ", type: " + metadata.getColumnTypeName( column ) +
                                   "), row " + row + ": to " + val );
          
          results.absolute(row+1);
          String sql = "UPDATE " + HSQLDB.escapeTableName( this.getTableName() ) + " ";
          sql += "SET " + metadata.getColumnName( column+1 ) + " = " + val + " ";
          sql += "WHERE id = " + results.getInt( "id" );
          // sql += "WHERE " + metadata.getColumnName(1) + " = " + results.getInt(1);
          
          if ( DEBUG )
               System.out.println( "Attempting to execute: " + sql );

          // HACK!
          // this makes the change to the DB
          results.getStatement().getConnection().createStatement().executeUpdate( sql );
          // this refreshes our copy of the data from the query stored by PreparedStatement
          results = ((PreparedStatement) results.getStatement()).executeQuery();
          updateMetaData();
          
          fireTableDataChanged();
          dataModel.updateDataListeners();
         
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
       if ( idInResults || column == nullsLastColNum )
          column++;
       
       String type = null;
       
       try {
          type = metadata.getColumnTypeName( column + 1 );
       }
       catch ( Exception e ) { e.printStackTrace(); }
       
//       System.out.println("TabModel: Column " + getColumnName(column-1) + " is a " + type );
       
       if      ( type == null ||
                 type.equalsIgnoreCase("VARCHAR") )
          return new String().getClass();
       else if ( type.equalsIgnoreCase("DATE") )
          return new Date().getClass();
       else if ( type.equalsIgnoreCase("INTEGER") )
          return new Integer(0).getClass();
       else if ( type.equalsIgnoreCase("FLOAT") || 
                 type.equalsIgnoreCase("DOUBLE") )
          return new Double(0).getClass();
       else if ( type.equalsIgnoreCase("BOOLEAN") )
          return new Boolean(true).getClass();
       else
          return new String().getClass();
       
   }
    
    
   private void updateMetaData() {
       try {
           metadata = results.getMetaData();       // Get metadata on them
       }
       catch ( SQLException e ) {
           e.printStackTrace();
       }
   }

    

   
    
    
}
