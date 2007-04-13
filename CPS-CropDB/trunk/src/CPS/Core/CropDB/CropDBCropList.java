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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JTextField tfldFilter;
    private String filterString;
    
    private CropDBUI uiManager;
    // private CropDBCropInfo cropInfo;
    private int cropInfoRow = -1;
    
    
    CropDBCropList( CropDBUI ui ) {
       uiManager = ui;
       filterString = "";
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
       
       tfldFilter = new JTextField( 10 );
       tfldFilter.setMaximumSize( tfldFilter.getPreferredSize() );
       // HACK! TODO, improve this; possibly by implementing a delay?
       // from: http://www.exampledepot.com/egs/javax.swing.text/ChangeEvt.html
       tfldFilter.getDocument().addDocumentListener( new DocumentListener() {
          public void insertUpdate(DocumentEvent e) { 
             filterString = tfldFilter.getText(); updateBySelectedButton(); }
          public void removeUpdate(DocumentEvent e) {
             filterString = tfldFilter.getText(); updateBySelectedButton(); }
          public void changedUpdate(DocumentEvent evt) {}
       });
       
       JPanel jplAboveList = new JPanel();
       jplAboveList.setLayout( new BoxLayout( jplAboveList, BoxLayout.LINE_AXIS ));
       jplAboveList.add( new JLabel( "Display:" ) );
       jplAboveList.add( radioAll );
       jplAboveList.add( radioCrops );
       jplAboveList.add( radioVar );
       jplAboveList.add( Box.createHorizontalGlue() );
       jplAboveList.add( tfldFilter );
       cropListPanel.add( jplAboveList, BorderLayout.PAGE_START ); 
       
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
       refreshCropInfo();
    }
    
    protected void refreshCropInfo() {
       if ( cropInfoRow == -1 )
          return;
       
       int nameCol = -1;
       int varCol = -1;
       // TODO this is fucked; maybe find these values once and cache them?
       for ( int i = 0; i < cropListTable.getColumnCount(); i++ ) {
          // locate column "crop_name"
          if ( cropListTable.getColumnName( i ).equalsIgnoreCase("crop_name") ) nameCol = i;
          // locate column "var_name"
          if ( cropListTable.getColumnName( i ).equalsIgnoreCase("var_name") ) varCol = i;
          // only break when we've found them both (otherwise, they might still be out there)
          if ( nameCol != -1 && varCol != -1 ) break;
       }
       
       Object varName = cropListTable.getValueAt( cropInfoRow, varCol );
       
       if ( varCol == -1 || varName == null )
          if ( cropListTable.getValueAt( cropInfoRow, nameCol ).toString().equals("") )
             // this is iffy at best
             uiManager.displayCropInfo( dataModel.getCropInfo( cropInfoRow ));
          else
             uiManager.displayCropInfo( dataModel.getCropInfo(
                         cropListTable.getValueAt( cropInfoRow, nameCol ).toString() ));
       else
          uiManager.displayCropInfo( dataModel.getVarietyInfo(
                      cropListTable.getValueAt( cropInfoRow, nameCol ).toString(),
                      varName.toString() ));
    }
    
    protected void updateCropList() {
       if ( isDataAvailable() )
          updateBySelectedButton();
    }
   
    protected void updateBySelectedButton() {
       if ( ! isDataAvailable() )
          return;
       
       if      ( radioAll.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedCropAndVarietyList( sortColumn, filterString ) );
       else if ( radioCrops.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedCropList( sortColumn, filterString ) );
       else if ( radioVar.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedVarietyList( sortColumn, filterString ) );
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
       // TODO this is a potential problem; in case table changes in the middle of an edit
       refreshCropInfo();
    }

   public void mouseClicked( MouseEvent evt ) {
      
      JTable table = ((JTableHeader) evt.getSource() ).getTable();
      TableColumnModel colModel = table.getColumnModel();
    
      // TODO implement multiple column sorting
      // this will help figure out if CTRL was pressed
      // evt.getModifiersEx();
                  
      // The index of the column whose header was clicked
      int vColIndex = colModel.getColumnIndexAtX( evt.getX() );
    
      // Return if not clicked on any column header
      if (vColIndex == -1) return;
    
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

