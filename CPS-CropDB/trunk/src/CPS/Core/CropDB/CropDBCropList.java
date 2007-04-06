/*
 * CropDBCropList.java
 *
 * Created on March 14, 2007, 1:03 PM
 */

package CPS.Core.CropDB;

import CPS.Module.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

// package access
class CropDBCropList extends CPSDataModelUser implements ItemListener, 
                                                         TableModelListener, 
                                                         ListSelectionListener,
                                                         MouseListener {
   
    private JPanel cropListPanel;
    private JTable cropListTable;
    private String sortColumn;
    private JRadioButton radioAll, radioCrops, radioVar;
    
    private CropDBUI uiManager;
    // private CropDBCropInfo cropInfo;
    private int cropInfoRow = -1;
    
    
    CropDBCropList( CropDBUI ui ) {
       uiManager = ui;
       sortColumn = null;
       buildCropListPane();
    }
   
    private void buildCropListPane() {
       
       cropListPanel = new JPanel( new BorderLayout() );
       cropListPanel.setBorder( BorderFactory.createTitledBorder( "Crop List" ));
       
       radioAll   = new JRadioButton( "All",      true );
       radioCrops = new JRadioButton( "Crops",    false );
       radioVar  = new JRadioButton( "Varieties", false );
       radioAll.addItemListener(this);
       radioCrops.addItemListener(this);
       radioVar.addItemListener(this);
       ButtonGroup bg = new ButtonGroup();
       bg.add(radioAll);
       bg.add(radioCrops);
       bg.add(radioVar);
       
       JPanel jp1 = new JPanel();
       jp1.setLayout( new BoxLayout( jp1, BoxLayout.LINE_AXIS ));
       jp1.add( new JLabel( "Display:" ) );
       jp1.add( radioAll );
       jp1.add( radioCrops );
       jp1.add( radioVar );
       cropListPanel.add( jp1, BorderLayout.PAGE_START ); 
       
       cropListTable = new JTable();
       cropListTable.setPreferredScrollableViewportSize( new Dimension( 500, cropListTable.getRowHeight() * 10 ) );
       cropListTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
       cropListTable.getTableHeader().addMouseListener( this );
       
       //Ask to be notified of selection changes.
       cropListTable.getSelectionModel().addListSelectionListener( this );
       
       JScrollPane scrollPane = new JScrollPane( cropListTable );
	
       cropListPanel.add( scrollPane, BorderLayout.CENTER );
        
       updateCropList(); 
       
    }
    
    protected void updateCropInfoForRow( int i ) {
       cropInfoRow = i;
       updateCropInfo();
    }
    
    protected void updateCropInfo() {
       if ( cropInfoRow == -1 )
          return;
       
       int colNum = 0;
       while ( ! cropListTable.getColumnName( colNum ).equalsIgnoreCase("crop_name") &&
               colNum < cropListTable.getColumnCount() ) {
          colNum++;
       }
       
       uiManager.displayCropInfo( dataModel.getCropInfo( 
                   cropListTable.getValueAt( cropInfoRow, colNum ).toString() ));
    }
    
    protected void updateCropList() {
       if ( isDataAvailable() )
          updateBySelectedButton();
    }
   
    protected void updateBySelectedButton() {
       if ( ! isDataAvailable() )
          return;
       
       if      ( radioAll.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedCropAndVarietyList( sortColumn ) );
       else if ( radioCrops.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedCropList( sortColumn ) );
       else if ( radioVar.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedVarietyList( sortColumn ) );
       else // nothing selected (not useful)
          updateCropListTable( new DefaultTableModel() );
    }
    
    protected void updateCropListTable( TableModel tm ) {
       tm.addTableModelListener( this );
       cropListTable.setModel(tm);
    }
    
    public JPanel getJPanel() {
       return cropListPanel;
    }
    
    // Pertinent method for ItemListener
    public void itemStateChanged( ItemEvent itemEvent ) {
      
       Object source = itemEvent.getItemSelectable();

       if ( source == radioAll || source == radioCrops || source == radioVar ) {
          updateBySelectedButton();
       } 
    }
    
    public void setDataSource( CPSDataModel dm ) {
       super.setDataSource(dm);
       updateCropList();
    }
   
    // Pertinent method for ListSelectionListener
    // gets selected row and sends that data to the cropInfo pane
    public void valueChanged( ListSelectionEvent e ) {
       //Ignore extra messages.
       if ( e.getValueIsAdjusting() ) return;

       ListSelectionModel lsm = ( ListSelectionModel ) e.getSource();
       if ( ! lsm.isSelectionEmpty() ) {
          int selectedRow = lsm.getMinSelectionIndex();
          System.out.println( "Selected row: " + selectedRow +
                      " (id: " + cropListTable.getValueAt( selectedRow, 0 ) + " )" );
          updateCropInfoForRow( selectedRow );
       }
    }

    // Pertinent method for TableModelListener
    public void tableChanged( TableModelEvent e ) {
       updateCropInfo();
    }

   public void mouseClicked( MouseEvent evt ) {
      
      JTable table = ((JTableHeader)evt.getSource()).getTable();
      TableColumnModel colModel = table.getColumnModel();
    
      // TODO implement multiple column sorting
      // this will help figure out if CTRL was pressed
      // evt.getModifiersEx();
                  
      // The index of the column whose header was clicked
      int vColIndex = colModel.getColumnIndexAtX( evt.getX() );
      int mColIndex = table.convertColumnIndexToModel( vColIndex );
    
      // Return if not clicked on any column header
      if (vColIndex == -1) {
         return;
      }
    
      // Determine if mouse was clicked between column heads
      Rectangle headerRect = table.getTableHeader().getHeaderRect(vColIndex);
      if (vColIndex == 0) {
         headerRect.width -= 3;    // Hard-coded constant
      } else {
         headerRect.grow(-3, 0);   // Hard-coded constant
      }
      if (!headerRect.contains(evt.getX(), evt.getY())) {
         // Mouse was clicked between column heads
         // vColIndex is the column head closest to the click

         // vLeftColIndex is the column head to the left of the click
         int vLeftColIndex = vColIndex;
         if (evt.getX() < headerRect.x) {
            vLeftColIndex--;
         }
      }
      
      // TODO: modify the column header to show which column is being sorted
      // table.getTableHeader().getColumn.setBackground( Color.DARK_GRAY );
      // see: http://www.exampledepot.com/egs/javax.swing.table/CustHeadRend.html
      
      if ( sortColumn != null && sortColumn.indexOf( table.getColumnName(vColIndex) ) != -1 ) {
         if      ( sortColumn.indexOf( "DESC" ) != -1 )
            sortColumn = table.getColumnName(vColIndex) + " ASC";
         else 
            sortColumn = table.getColumnName(vColIndex) + " DESC";
      }
      else
         sortColumn = table.getColumnName(vColIndex);
      
      updateBySelectedButton();
      
   }

   public void mousePressed(MouseEvent mouseEvent) {}
   public void mouseReleased(MouseEvent mouseEvent) {}
   public void mouseEntered(MouseEvent mouseEvent) {}
   public void mouseExited(MouseEvent mouseEvent) {}
      
}

