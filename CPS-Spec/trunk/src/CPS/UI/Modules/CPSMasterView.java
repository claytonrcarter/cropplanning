/* CPSMasterView.java
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

package CPS.UI.Modules;

import CPS.Data.CPSComplexFilter;
import CPS.Data.CPSRecord;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelConstants;
import CPS.Module.CPSDataModelUser;
import CPS.UI.Swing.CPSTable;
import CPS.UI.Swing.CPSSearchField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Clayton
 */
public abstract class CPSMasterView extends CPSDataModelUser 
                                    implements ActionListener, 
                                               ListSelectionListener, 
                                               MouseListener, 
                                               TableModelListener {

    protected static final String KEY_DISPLAYED_COLUMNS = "DISPLAYED_COLUMNS";
    protected static final String KEY_DISPLAYED_TABLE = "DISPLAYED_TABLE";
    
    
    private JPanel masterListPanel = null;
    protected JPanel jplAboveList = null;
    private JPanel jplList = null;
    protected JPanel jplFilter = null;
    private JPanel jplBelowList  = null;
    
    private JLabel lblStats;
    
    protected CPSTable masterTable;
    protected JPopupMenu pupColumnList;
//    private ColumnHeaderToolTips headerToolTips = new ColumnHeaderToolTips();
    private int sortColumn = 0;
//    private JTextField tfldFilter;
    private CPSSearchField tfldFilter;
//    private String filterString;
    
//    private JButton btnFilterClear;

    private JButton btnNewRecord, btnDupeRecord, btnDeleteRecord;
    
    private CPSMasterDetailModule uiManager;
    protected CPSComplexFilter filter;
    
    private int detailRow = -1;
    /// selectedID is the ID of the currently selected record (as opposed to
    // the row number of the selected row.
    private int[] selectedRows = {};
    private ArrayList<Integer> selectedIDs = new ArrayList();
    private ArrayList<ColumnNameStruct> columnList = new ArrayList();
    
    
    public CPSMasterView( CPSMasterDetailModule ui ) {
       uiManager = ui;
       filter = new CPSComplexFilter();
//       filterString = "";
//       setSortColumn( CPSDataModelConstants.PROP_ );
       
       buildMainPanel( null );
    }
    
//    public abstract int init();
    public int init() { return 0; }
    protected int saveState() {
       getPrefs().put( KEY_DISPLAYED_COLUMNS, getDisplayedColumnListAsString() );
       if ( getDisplayedTableName() == null )
          getPrefs().remove( KEY_DISPLAYED_TABLE );
       else
          getPrefs().put( KEY_DISPLAYED_TABLE, getDisplayedTableName() );
       return 0;
    }
    public int shutdown() {
       saveState();
       try {
          getPrefs().flush();
       } catch ( Exception e ) { e.printStackTrace(); }
       return 0;
    }
    
    protected Preferences getPrefs() { return uiManager.getPrefs(); }
    
    
    protected abstract String getDisplayedTableName();
    protected abstract CPSRecord getDetailsForID( int id );
    protected abstract CPSRecord getDetailsForIDs( ArrayList<Integer> ids );
    
    protected CPSRecord getRecordToDisplay() {
       if ( selectedIDs.size() < 1 ) {
          System.err.println("ERROR displaying record: no item selected from list");
          return null;
       }
       else if ( selectedIDs.size() == 1 )
          return getDetailsForID( selectedIDs.get(0).intValue() );
       else
          return getDetailsForIDs( selectedIDs );
    }
    
    protected void updateDetailView() {
        if ( selectedIDs.size() < 1 )
            return;
        else if ( selectedIDs.size() == 1 )
           uiManager.displayDetail( getDetailsForID( selectedIDs.get(0).intValue() ) );
        else
           uiManager.displayDetail( getDetailsForIDs( selectedIDs ));
        
    }
    
    // pertinent method for TableModelListener
    // what does it listen for?  general changes to the table?
    public void tableChanged(TableModelEvent e) {
        // TODO this is a potential problem; in case table changes in the middle of an edit
        updateDetailView();
    }

    
    /** 
     * This method is called when a row in the table is selected.  It is from the 
     * ListSelectionListener interface.  In it, we retrieve the list of selected rows,
     * query the db on those rows and update the detail view.
     * 
     * @param e The ListSelectionEvent that describes this selection event.
     */
    public void valueChanged( ListSelectionEvent e ) {
        //Ignore extra messages.
        if ( e.getValueIsAdjusting() )
            return;
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        if ( !lsm.isSelectionEmpty() ) {
            // retrieve the selected row (for single row selection mode)
            // selectedRow = lsm.getMinSelectionIndex();
           
           // retrieve the selected rows (for multi row selection mode)
           selectedRows = masterTable.getSelectedRows();
           selectedIDs.clear();
           for ( int i : selectedRows)
                selectedIDs.add( new Integer( getRecordIDForRow( i ) ) );
           
           // for single row selection mode
//           selectedIDs = Integer.parseInt( masterTable.getValueAt( selectedRow, -1 ).toString() );
           
            updateDetailView();
        }
    }

    private int getRecordIDForRow( int row ) {
       return new Integer( masterTable.getValueAt( row, -1 ).toString() ).intValue();
    }
    
    protected void selectRecord( int id ) {
        setSelectedRowByRecordID( id );
    }
    private void setSelectedRowByRecordID( int recordID ) {
       
       masterTable.clearSelection();
       
       int numRows = masterTable.getRowCount();
       for ( int row = 0; row < numRows; row++ ) {
          if ( getRecordIDForRow( row ) == recordID ) {
             masterTable.setRowSelectionInterval( row, row );
             break;
          }
       }
          
    }
    
    public JPanel getJPanel() {
        return getMainPanel();
    }
    protected JPanel getMainPanel() {
        if ( masterListPanel == null ) { buildMainPanel( null ); }
        return masterListPanel;
    }   
    protected void initMainPanel( String title ) {
        
        masterListPanel = new JPanel( new BorderLayout() );
        
        if ( title != null )
            masterListPanel.setBorder( BorderFactory.createTitledBorder( title ) );
        else
            masterListPanel.setBorder( BorderFactory.createEtchedBorder() );
       
    }
    protected void buildMainPanel( String title ) {
        
        initMainPanel( title );
        
        masterListPanel.add( getAboveListPanel(), BorderLayout.PAGE_START );
        masterListPanel.add( getListPanel(),      BorderLayout.CENTER );
        masterListPanel.add( getBelowListPanel(), BorderLayout.PAGE_END );
       
    }
    
    protected JPanel getAboveListPanel() {
        if ( jplAboveList == null ) { buildAboveListPanel(); }
        return jplAboveList;
    }
    protected void initAboveListPanel() {
        jplAboveList = new JPanel();
        jplAboveList.setLayout( new BoxLayout( jplAboveList, BoxLayout.LINE_AXIS ) );    
    }
    protected void buildAboveListPanel() { buildAboveListPanel( true ); }
    protected void buildAboveListPanel( boolean init ) {
        if ( init )
            initAboveListPanel();
        jplAboveList.add( Box.createHorizontalGlue() );
        jplAboveList.add( buildFilterComponent() );
    }
    
    protected JPanel getBelowListPanel() {
        if ( jplBelowList == null ) { buildBelowListPanel(); }
        return jplBelowList;       
    }
    protected void initBelowListPanel() {
        jplBelowList = new JPanel();
        jplBelowList.setLayout( new BoxLayout( jplBelowList, BoxLayout.LINE_AXIS ) );
    }
    protected void buildBelowListPanel() { buildBelowListPanel( true ); }
    protected void buildBelowListPanel( boolean init ) {
        
        Insets small = new Insets( 1, 1, 1, 1 );
      
        btnNewRecord = new JButton( "New" );
        btnNewRecord.setActionCommand( "NewRecord" );
        btnDupeRecord = new JButton( "Duplicate" );
        btnDeleteRecord = new JButton( "Delete" );
        btnNewRecord.addActionListener( this );
        btnDupeRecord.addActionListener( this );
        btnDeleteRecord.addActionListener( this );
        btnNewRecord.setMargin( small );
        btnDupeRecord.setMargin( small );
        btnDeleteRecord.setMargin( small );
        
        lblStats = new JLabel();
        lblStats.setText("");
        lblStats.setToolTipText("Statistics regarding displayed or selected rows in the table.");
        
        if ( init )
            initBelowListPanel();
        jplBelowList.add( btnNewRecord );
        jplBelowList.add( btnDupeRecord );
        jplBelowList.add( btnDeleteRecord );
        jplBelowList.add( Box.createHorizontalGlue() );
        jplBelowList.add( lblStats );
      
    }
    
    protected void initFilterPanel() {
        jplFilter = new JPanel();
        jplFilter.setLayout( new BoxLayout( jplFilter, BoxLayout.LINE_AXIS ) );
        jplFilter.add( Box.createHorizontalGlue() );
    }
    protected JPanel buildFilterComponent() {
        return buildFilterComponent(true);
    }
    protected JPanel buildFilterComponent( boolean init ) {
        
//        tfldFilter = new JTextField(10);
       tfldFilter = new CPSSearchField( "Filter" );
       tfldFilter.setToolTipText( "Only show records matching ALL of these terms.  Leave blank to show everything." );
        tfldFilter.setMaximumSize(tfldFilter.getPreferredSize());
        // HACK! TODO, improve this; possibly by implementing a delay?
        // from: http://www.exampledepot.com/egs/javax.swing.text/ChangeEvt.html
        tfldFilter.getDocument().addDocumentListener( new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateFilter(); }
            public void removeUpdate(DocumentEvent e) { updateFilter(); }
            public void changedUpdate(DocumentEvent evt) {}
       });
       
       if ( init )
           initFilterPanel();
       
       jplFilter.add( tfldFilter );
//       jplFilter.add( btnFilterClear );
       return jplFilter;
       
    }
    
    protected JPanel getListPanel() {
        if ( jplList == null ) { buildListPanel(); }
        return jplList;       
    }
    protected void initListPanel() {
        jplList = new JPanel();
        jplList.setLayout( new BoxLayout( jplList, BoxLayout.LINE_AXIS ) );
    }
    protected void buildListPanel() {
       
       masterTable = new CPSTable();
       Dimension d = new Dimension( 500, masterTable.getRowHeight() * 10 );
       masterTable.setPreferredScrollableViewportSize( d );
       masterTable.setMaximumSize( d );
       masterTable.getTableHeader().addMouseListener( this );
       
       // Ask to be notified of selection changes (see method: valueChanged)
       masterTable.getSelectionModel().addListSelectionListener( this );
       
       initListPanel(); // init listPanel
       jplList.add( new JScrollPane( masterTable ) );
       
    }
    
    protected abstract String getTableStatisticsString();
    protected void updateStatisticsLabel() {
        String stats = getTableStatisticsString();
        
        if ( stats == null || stats.equals("") ) {
            lblStats.setText("");
            lblStats.setToolTipText(null);
        }
        else {
            lblStats.setText( stats );
            lblStats.setToolTipText("Statistics regarding displayed or selected rows in the table.");
        }
        
    }
    
    protected abstract ArrayList<String[]> getColumnPrettyNameMap();
    protected abstract ArrayList<String> getDisplayableColumnList();
    protected abstract ArrayList<Integer> getDefaultDisplayableColumnList();
    protected void buildColumnListPopUpMenu() {
       pupColumnList = new JPopupMenu();
      
       ArrayList<Integer> defaultCols;
       String savedCols = getPrefs().get( KEY_DISPLAYED_COLUMNS, "" );
       if ( savedCols.equals( "" ) )
           defaultCols = getDefaultDisplayableColumnList();
       else {
          List<String> tempCols = Arrays.asList( savedCols.trim().split( ", " ) );
          defaultCols = new ArrayList<Integer>();
          for ( String s : tempCols )
//             defaultCols.add( getDataSource().propNumFromPropName( getTypeOfDisplayedRecord(),
//                                                                   s ));
             defaultCols.add( new Integer( s ) );
       }

       
       ArrayList<String[]> columnNames = getColumnPrettyNameMap();
       
       // retrieve list of columns, build tuple list w/ column and ISSELECTED
       for ( String[] s : columnNames ) {
          if ( defaultCols.contains( getDataSource().propNumFromPropName( getTypeOfDisplayedRecord(), s[0] ) ))
             columnList.add( new ColumnNameStruct( s[0], s[1], true ) );
          else
             columnList.add( new ColumnNameStruct( s[0], s[1], false ) );
       }

       updateColumnListPopUpMenu();
       // AL should set column to SELECTED
       // then update popup list and master list
       // this class shouldn't do anything else, I don't think, but subclasses should 
       
    }
    
    protected void updateColumnListPopUpMenu() {
       pupColumnList.removeAll();
       
       // We will only "feature" the first 20 entries, the rest will be buried in
       // a submenu
       
       // Create and populate the submenu
       JMenu subMenu = new JMenu( "More ..." );
       for ( ColumnNameStruct c : columnList.subList( 20, columnList.size() ) ) {
          JMenuItem menuItem;
          if ( c.selected )
             menuItem = new JMenuItem( "<html><b>" + c.prettyName + "</b></html>" );
          else
             menuItem = new JMenuItem( c.prettyName );
          menuItem.setActionCommand( "popup-" + c.columnName );
          menuItem.addActionListener( this );
          subMenu.add( menuItem );
       }
       
       // now create and populate the actual PopupMenu, w/ BOLD == selected
       for ( ColumnNameStruct c : columnList.subList( 0, 20 ) ) {
          JMenuItem menuItem;
          if ( c.selected )
             menuItem = new JMenuItem( "<html><b>" + c.prettyName + "</b></html>" );
          else
             menuItem = new JMenuItem( c.prettyName );
          menuItem.setActionCommand( "popup-" + c.columnName );
          menuItem.addActionListener( this );
          pupColumnList.add( menuItem );
       }
       if ( subMenu.getItemCount() > 0 )
           pupColumnList.add( subMenu );
       
    }
    
    protected void selectColumnForDisplay( String selectedCol ) {
        for ( ColumnNameStruct c : columnList ) {
            if ( selectedCol.equalsIgnoreCase( c.columnName ) ) {
                c.selected = true;
                break;
            }
        } 
    }
    
    protected void toggleColumnForDisplay( String selectedCol ) {
        
        for ( ColumnNameStruct c : columnList ) {
            if ( selectedCol.equalsIgnoreCase( c.columnName ) ) {
                c.selected = ! c.selected;
                break;
            }
        } 
        
    }
    
    
    
    protected ArrayList<Integer> getDisplayedColumnList() {
       ArrayList<Integer> l = new ArrayList<Integer>();
       for ( ColumnNameStruct c : columnList ) {
          if ( c.selected )
             l.add( getDataSource().propNumFromPropName( getTypeOfDisplayedRecord(),
                                                         c.columnName ) );
       }
       return l;
    }
    protected String getDisplayedColumnListAsString() {
       String s = "";
       ArrayList<Integer> l = getDisplayedColumnList();
       for ( Integer i : l )
          s += i.intValue() + ", ";
       s = s.substring( 0, s.lastIndexOf(", ") );
       return s;
    }
    
    // This might happen at any time.  So we need to update our view of the data
    // whenever it happens.
    public void setDataSource( CPSDataModel dm ) {
       super.setDataSource( dm );
       buildColumnListPopUpMenu();
       dataUpdated();
    }
    
    // Reset the table to display new data, encapsulated in a new TableModel
    // We should consider renaming this as the name is rather ambiguous.  
    // Perhaps updateMasterTable or updateMasterListTable?
    private void updateListTable( TableModel tm ) {
        tm.addTableModelListener(this);
        masterTable.setModel(tm);    
        masterTable.setColumnNamesAndToolTips( getColumnPrettyNameMap() );
    }
    
    // retrieve fresh data and display it
    protected void updateMasterList() {
        if ( !isDataAvailable() )
            return;

        updateListTable( getMasterListData() );
        
    }
    
    protected void setStatus( String s ) {
        uiManager.setStatus(s);
    }
    
    /// Abstract method to retrieve new data and then hand it off to the
    // JTable to display.  Overriding class should do the fancy work of
    // figuring out which table to query, etc.  Returns a TableModel.
    protected abstract TableModel getMasterListData();
    protected void setSortColumn( int prop ) { 
//       if ( s == null ) s = ""; 
       sortColumn = prop;
    }
    protected int getSortColumn() { return sortColumn; }
    protected CPSComplexFilter getFilter() { return filter; }
    protected void updateFilter() {
        filter.setFilterString( tfldFilter.getText() ); 
        dataUpdated();
    }

    protected abstract int getTypeOfDisplayedRecord();
    public void mouseClicked(MouseEvent evt) {
        JTable table = ((JTableHeader) evt.getSource()).getTable();
        TableColumnModel colModel = table.getColumnModel();

        // TODO implement multiple column sorting
        // this will help figure out if CTRL was pressed
        // evt.getModifiersEx();
        // The index of the column whose header was clicked
        int vColIndex = colModel.getColumnIndexAtX(evt.getX());

        // Return if not clicked on any column header
        if (vColIndex == -1)
            return;
        
        if ( evt.getButton() == evt.BUTTON1 && ! evt.isControlDown() ) {
           if ( getSortColumn() != 0 ) {
              int propOfClickedColumn = getDataSource().propNumFromPropName( getTypeOfDisplayedRecord(),
                                                                             table.getColumnName( vColIndex ) );
              if ( Math.abs( getSortColumn() ) == propOfClickedColumn )
                 propOfClickedColumn *= -1;
              setSortColumn( propOfClickedColumn );
           }
           // TODO: modify the column header to show which column is being sorted
           //table.getTableHeader().getColumn.setBackground( Color.DARK_GRAY );
           // see: http://www.exampledepot.com/egs/javax.swing.table/CustHeadRend.html
//           if ( getSortColumn() != null &&
//                getSortColumn().indexOf( table.getColumnName(vColIndex).toLowerCase() ) != -1 ) {
//              if ( getSortColumn().indexOf( "desc" ) != -1 )
//                   setSortColumn( table.getColumnName(vColIndex) + " asc" );
//              else
//                   setSortColumn( table.getColumnName(vColIndex) + " desc" );
//           }
//           else
//              setSortColumn( table.getColumnName( vColIndex ) );
        }
        else if ( evt.getButton() == evt.BUTTON3 || // RIGHT mouse button on Windows
                  evt.getButton() == evt.BUTTON1 && evt.isControlDown() // CTRL + click on Mac
                ) {
           pupColumnList.show( evt.getComponent(),
                               evt.getX(), evt.getY() );
           
        }
        
        updateMasterList();
    }
    public void mouseEntered(MouseEvent mouseEvent) {}
    public void mouseExited(MouseEvent mouseEvent) {}
    public void mousePressed(MouseEvent mouseEvent) {}
    public void mouseReleased(MouseEvent mouseEvent) {}

    
    public abstract CPSRecord createNewRecord();
    public abstract CPSRecord duplicateRecord( int id );
    public abstract void deleteRecord( int id );
    
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();

        System.out.println("DEBUG Action performed in CPSMasterView: " + action);

        /*
         * FILTER BUTTON CLEAR
         */
//        if (action.equalsIgnoreCase(btnFilterClear.getText())) {
//            tfldFilter.setText("");
//            tfldFilter.requestFocus();
//            return;
//        }
        
        /*
         * COLUMN SELECTION POPUP WINDOW
         */
        if ( action.startsWith( "popup-" ) ) {
           String selectedCol = action;
           // remove the popup- and leave the column name
           selectedCol = selectedCol.replaceFirst( "popup-", "" );
           
//           for ( ColumnNameStruct c : columnList ) {
//              if ( selectedCol.equalsIgnoreCase( c.columnName )) {
//                 c.selected = ! c.selected;
//                 break;
//              }
//           }           
           toggleColumnForDisplay( selectedCol );
           updateColumnListPopUpMenu();
           dataUpdated();
           return;
        }
        
        /*
         * OTHER BUTTONS: New, Dupe, Delete
         */
        // Note the return above, this implies that the following list of if's
        // should really start with an "else"
        if ( action.equalsIgnoreCase( btnNewRecord.getActionCommand() ) ) {
            if ( !isDataAvailable() ) {
                System.err.println("ERROR: cannot create new planting, data unavailable");
                return;
            }
            CPSRecord newRecord = createNewRecord();
            int newID = newRecord.getID();
            uiManager.displayDetail( newRecord );
            uiManager.setDetailViewForEditting();
            setSelectedRowByRecordID( newID );
            setStatus( "Editing new record; save changes to add to list." );
        }
        else if (action.equalsIgnoreCase(btnDupeRecord.getText())) {
            if (!isDataAvailable()) {
                System.err.println("ERROR: cannot duplicate planting, data unavailable");
                return;
            }
            else if ( selectedIDs.size() != 1 ) {
               // TODO, support mupltiple row duplication
               System.err.println("ERROR: at present, can only duplicate single rows");
               return;
            }
            CPSRecord newRecord = duplicateRecord( selectedIDs.get(0).intValue() );
            int newID = newRecord.getID();
            uiManager.displayDetail( newRecord );
            uiManager.setDetailViewForEditting();
            setSelectedRowByRecordID( newID );
        }
        else if (action.equalsIgnoreCase(btnDeleteRecord.getText())) {
            if (!isDataAvailable()) {
                System.err.println("ERROR: cannon delete entry, data unavailable");
                return;
            }
            else if ( selectedIDs.size() != 1 ) {
               // TODO support mupltiple row duplication
               System.err.println("ERROR: at present, can only delete single rows");
               return;
            }
            deleteRecord( selectedIDs.get(0).intValue() );
        }
        
    }
    
   @Override
   public void dataUpdated() {
      updateMasterList();
      updateStatisticsLabel();
      
      // no data in table
      if ( masterTable.getRowCount() < 1 ) {
          // if no records returned, we can't very well duplicate or delete any
          btnDupeRecord.setEnabled(false);
          btnDeleteRecord.setEnabled(false);
          
          // if the filter string is empty, then there really are no records
          // else we're just created an incorrect or too restrictive filter
          if ( getFilter().getFilterString().equals("") ) {
              tfldFilter.setEnabled(false);
              if ( getDisplayedTableName() == null || getDisplayedTableName().equals("") )
                  btnNewRecord.setEnabled( false );
              else
                  btnNewRecord.setEnabled( true );
              setStatus( "No records found.  Use \"New\" button to create some." );
          }
          else {
              btnNewRecord.setEnabled(false);
              tfldFilter.setEnabled(true);
              setStatus( "Filter returned no records.  Check spelling or be less specific." );
          }
      }
      // table contains data; undo anything we might have just done (in the "if" clause) 
      else {
          btnNewRecord.setEnabled(true);
          btnDeleteRecord.setEnabled(true);
          btnDupeRecord.setEnabled(true);
          tfldFilter.setEnabled(true);
          
          if ( selectedIDs.size() > 0 ) {
             // check that selected items are in table
             // if not, clear selection and display nothing
              setStatus( null );
          }
          else
              setStatus( "No records selected.  Select item from table above to display detailed information." );
      }
          
   }
    
   
   private class ColumnNameStruct {
      public String columnName;
      public String prettyName;
      public boolean selected;
      
      public ColumnNameStruct( String name, String pretty, boolean b ) {
         columnName = name;
         prettyName = pretty;
         selected = b;
      }
   }

}
