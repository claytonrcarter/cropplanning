/*
 * CSVTableModel.java
 *
 * Created on April 3, 2007, 3:08 PM by Clayton
 */

package CPS;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.csvreader.CsvReader;

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
          
        } catch ( Exception e ) { e.printStackTrace(); }
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
