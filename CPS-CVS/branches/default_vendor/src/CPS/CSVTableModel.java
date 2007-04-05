/*
 * CSVTableModel.java
 *
 * Created on April 3, 2007, 3:08 PM by Clayton
 */

package CPS;

/* From: http://forum.java.sun.com/thread.jspa?threadID=5138036&messageID=9506301
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.csvreader.CsvReader;

class CSVTableModel extends AbstractTableModel {
    RandomAccessFile raf;
    // this keeps a file position (char number) to indicate each line
    ArrayList<Long> lineToPos = new ArrayList<Long>();
    int columnCount = -1;
    String[] colNames = null;
    
    CsvReader csv;
    
    public CSVTableModel(String fileName) {
       
       try {
           
          csv = new CsvReader( fileName );
       
          if ( csv.readHeaders() ) {
             colNames = csv.getHeaders();
             if ( columnCount == -1 )
                columnCount = colNames.length;
          }
          
          // raf = new RandomAccessFile( new File(fileName), "r" );
            
          // commented so that we do not count the header line
          // lineToPos.add(new Long(0));
//          String line = raf.readLine();
//          if ( line != null ) {
//             colNames = line.split(",");
//             if ( columnCount == -1 )
//                columnCount = colNames.length;
//             lineToPos.add( new Long( raf.getFilePointer() ));
//          // }
            
            // figure out the number of lines in the file
            while ((line = raf.readLine()) != null) {
                lineToPos.add( new Long( raf.getFilePointer() ));
            }
            lineToPos.remove(lineToPos.size()-1);
        } catch ( Exception e ) { e.printStackTrace(); }
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
        raf.close();
    }
 
    public int getColumnCount() {
        return columnCount;
    }
 
    public int getRowCount() {
        return lineToPos.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
       
       if ( columnIndex >= columnCount )
          System.err.println("Column out of bounds: " + columnIndex + " greater than " + columnCount );
       if ( rowIndex > lineToPos.size() - 1 )
          System.err.println("Row out of bounds: " + rowIndex + " greater than " + ( lineToPos.size() - 1 ));
       
       try {
          raf.seek(lineToPos.get(rowIndex).longValue());
          String line = raf.readLine();
          String[] strs = line.split( ",", columnCount ); // could also use -1 instead of columnCount
          return strs[columnIndex];
       } catch (IOException e) {
          e.printStackTrace();
       }
       return null;
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
