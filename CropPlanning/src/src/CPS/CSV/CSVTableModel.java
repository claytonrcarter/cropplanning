/* CSVTableModel.java - Created: April 3, 2007
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

package CPS.CSV;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;

class CSVTableModel extends AbstractTableModel {
   
    ArrayList<String[]> lines = new ArrayList<String[]>();
    int columnCount = -1;
    String[] colNames = null;
    
    CsvReader csv;
    
    public CSVTableModel(String fileName) {
       
       try {
           
          csv = new CsvReader( fileName );
          csv.setDelimiter(',');
          csv.setTextQualifier('"');
       
          if ( csv.readHeaders() ) {
             colNames = csv.getHeaders();
             if ( columnCount == -1 )
                columnCount = colNames.length;
          }
          
          while ( csv.readRecord() )
             lines.add( csv.getValues() );
          
        } 
       catch ( FileNotFoundException ignore ) {}
       catch ( IOException ignore ) {}
       
    }
    
    public int getColumnCount() {
        return columnCount;
    }
 
    public int getRowCount() {
        return lines.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
       
       if ( columnIndex >= columnCount )
          System.err.println("Column out of bounds: " + columnIndex + " greater than " + columnCount );
       if ( rowIndex > lines.size() - 1 )
          System.err.println("Row out of bounds: " + rowIndex + " greater than " + ( lines.size() - 1 ));
       
       return lines.get(rowIndex)[columnIndex];
    
    }
    
    public String getStringAt( int r, int c ) {
       String s = (String) getValueAt( r, c );
       if ( s == null )
          return "";
       else
          return s;
    }
    
    public int getIntAt( int r, int c ) {
       String s = getStringAt( r, c );
       if ( s == null || s.equals("") )
          return -1;
       else
          return Integer.parseInt(s);
    }
    
}
