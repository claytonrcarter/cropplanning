/* CPSTable.java - created: Jan 30, 2008
 * Copyright (C) 2008 Clayton Carter
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
 * 
 */

package CPS.UI.Swing;

import CPS.Data.CPSDateValidator;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CPSTable extends JTable {
   
    private CPSDateValidator dateValidator;
    
    public CPSTable() {
        super();
        
       /* setup selection parameters */
       // enable row selection, disable column selection (default)
       this.setColumnSelectionAllowed( false );
       this.setRowSelectionAllowed( true );
       // allow multiple rows to be selected
       this.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
       
        
        dateValidator = new CPSDateValidator();
        dateValidator.addFormat( CPSDateValidator.DATE_FORMAT_SQL );
    }
    
    
    @Override
    public void setModel( TableModel tm ) {
        super.setModel( tm );
        
        setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        
        /* reset column widths for certain column types */
        for ( int colIndex = 0; colIndex < getColumnCount(); colIndex++ ) {
           Class c = getColumnClass(colIndex);
           // Boolean
           if ( c.getName().equals( new Boolean(true).getClass().getName() ) ) {
               getColumnModel().getColumn( colIndex ).setMaxWidth( 20 );
              getColumnModel().getColumn( colIndex ).setPreferredWidth( 20 );
           }
           // Integer and Double
           else if ( c.getName().equals( new Integer(0).getClass().getName() ) ||
                     c.getName().equals( new Double(0).getClass().getName() ) )
              getColumnModel().getColumn( colIndex ).setPreferredWidth( 40 );
        }
        
         // install custom table renderes and editors
       for ( int i = 0 ; i < getColumnModel().getColumnCount() ; i++ ) {
          // install date renderers and editors on all Date columns
           if ( getColumnClass( i ).equals( new Date().getClass() ) ) {
              getColumnModel().getColumn(i).setCellRenderer( new DateCellRenderer() );
              getColumnModel().getColumn(i).setCellEditor(   new DateCellEditor() );
          }
       }
        
    }

    @Override
    public void setRowSelectionInterval( int arg0, int arg1 ) {
        super.setRowSelectionInterval( arg0, arg1 );
        ensureRowIsVisible(arg0);
    }
    
    // Assumes table is contained in a JScrollPane. Scrolls the
    // cell (rowIndex, vColIndex) so that it is visible within the viewport.
    public void ensureRowIsVisible( int rowIndex ) {
        if ( !( this.getParent() instanceof JViewport ) ) {
            return;
        }
        JViewport viewport = (JViewport) this.getParent();
    
        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = this.getCellRect( rowIndex, 1, true );
    
        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();
    
        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation( rect.x - pt.x, rect.y - pt.y );
    
        // Scroll the area into view
        viewport.scrollRectToVisible( rect );
    }
    
    
    public void setColumnNamesAndToolTips( ArrayList<String[]> prettyNames ) {
        ColumnHeaderToolTips tips = new ColumnHeaderToolTips();
//        ArrayList<String[]> prettyNames = getColumnPrettyNameMap();
        int COLNAME = 0;
        int PRETTYNAME = 1;
        
        // Assign a tooltip for each of the columns
        for ( int c = 0; c < getColumnCount(); c++ ) {
            String colName = getColumnModel().getColumn( c ).getHeaderValue().toString().toLowerCase();
            String s = null;
            
            for ( int l = 0; l < prettyNames.size(); l++ )
                if ( colName.equals( prettyNames.get(l)[COLNAME]) ) {
                    s = prettyNames.get(l)[PRETTYNAME];
                    break;
                }
            
            // Change the name to the "pretty name"
            if ( s != null )
                getColumnModel().getColumn( c ).setHeaderValue( s );
            // set the tool tip to the "pretty name"
            tips.setToolTip( getColumnModel().getColumn( c ), s );
        }
        getTableHeader().addMouseMotionListener( tips );
    }

    
    private class DateCellRenderer extends JLabel implements TableCellRenderer {
        // This method is called each time a cell in a column
        // using this renderer needs to be rendered.
        public Component getTableCellRendererComponent( JTable table, Object value,
                                                        boolean isSelected, boolean hasFocus, 
                                                        int rowIndex, int vColIndex ) {
            // 'value' is value contained in the cell located at (rowIndex, vColIndex)
    
            // Configure the component with the specified value
            // in case, we display a formated string
            setText( dateValidator.format( (Date) value ));
//            setToolTipText((String)value);
            return this;
        }
    
        // The following methods override the defaults for performance reasons
        public void validate() {}
        public void revalidate() {}
        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
        public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
    }
    
    private class DateCellEditor extends AbstractCellEditor implements TableCellEditor {
        // This is the component that will handle the editing of the cell value
        JComponent component = new JTextField();

        // This method is called when a cell value is edited by the user.
        // 'value' is value contained in the cell located at (rowIndex, vColIndex)
        public Component getTableCellEditorComponent( JTable table, Object value,
                                                      boolean isSelected, int rowIndex, int vColIndex ) {
            // Configure the component with the specified value
            // In ths case, we accept a Date and fill the text field with a formated String.
            ( (JTextField) component ).setText( dateValidator.format( (Date) value ));
            return component;
        }

        // This method is called when editing is completed.
        // It must return the new value to be stored in the cell.
        // In this case, we return a Date
        public Object getCellEditorValue() {
            if ( ((JTextField) component ).getText().equals( "" ))
                return null;
            
            String dateText = ( (JTextField) component ).getText();
//            // handles addition of negative numbers
//            if ( dateText.matches( ".+\\+.+") ) {
//                String[] s = dateText.split("\\+");
//                GregorianCalendar cal = new GregorianCalendar();
//                cal.setTime( dateValidator.parse( s[0].trim() ) );
//                cal.add( GregorianCalendar.DAY_OF_YEAR, Integer.parseInt( s[1].trim() ));
//                return cal.getTime();
//            }
//            // does NOT handle subtract of negative numbers
//            else if ( dateText.matches( ".+-.+") ) {
//                String[] s = dateText.split("-");
//                // if we split into two, then there was just one -
//                if ( s.length == 2 ) {
//                    GregorianCalendar cal = new GregorianCalendar();
//                    cal.setTime( dateValidator.parse( s[0].trim() ) );
//                    cal.add( GregorianCalendar.DAY_OF_YEAR, -1 * Integer.parseInt( s[1].trim() ) );
//                    return cal.getTime();
//                }
//                else
//                    System.err.println("ERROR(CPSTable): Can't understand date:" + dateText + " [Too many '-'s]" );
//            }
            
            return dateValidator.parse( dateText.trim() );
        }
    }
       
    private class ColumnHeaderToolTips extends MouseMotionAdapter {
        // Current column whose tooltip is being displayed.
        // This variable is used to minimize the calls to setToolTipText().
        TableColumn curCol;
    
        // Maps TableColumn objects to tooltips
        Map tips = new HashMap();
    
        // If tooltip is null, removes any tooltip text.
        public void setToolTip(TableColumn col, String tooltip) {
            if (tooltip == null) {
                tips.remove(col);
            } else {
                tips.put(col, tooltip);
            }
        }
    
        public void mouseMoved(MouseEvent evt) {
            TableColumn col = null;
            JTableHeader header = (JTableHeader)evt.getSource();
            JTable table = header.getTable();
            TableColumnModel colModel = table.getColumnModel();
            int vColIndex = colModel.getColumnIndexAtX(evt.getX());
    
            // Return if not clicked on any column header
            if (vColIndex >= 0) {
                col = colModel.getColumn(vColIndex);
            }
    
            if (col != curCol) {
                header.setToolTipText((String)tips.get(col));
                curCol = col;
            }
        }
   }

}
