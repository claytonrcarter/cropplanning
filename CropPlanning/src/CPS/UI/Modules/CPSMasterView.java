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

import CPS.Data.CPSPlanting;
import CPS.Data.CPSRecord;
import CPS.Data.CPSTextFilter;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelUser;
import CPS.Module.CPSModule;
import CPS.UI.Swing.CPSConfirmDialog;
import CPS.UI.Swing.CPSSearchField;
import CPS.UI.Swing.CPSTable;
import ca.odell.glazedlists.*;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;
import ca.odell.glazedlists.matchers.*;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Clayton
 */
public abstract class CPSMasterView extends CPSDataModelUser 
                                    implements ActionListener, 
                                               ListSelectionListener, 
                                               MouseListener, 
                                               TableModelListener,
                                               ListEventListener {

    protected static final String KEY_DISPLAYED_COLUMNS = "DISPLAYED_COLUMNS";
    protected static final String KEY_DISPLAYED_TABLE = "DISPLAYED_TABLE";

    protected static final String STATUS_NO_PLAN_SELECTED = "No plan selected.  Select a plan to display or use \"New Plan\" button to create a new one.";
    protected static final String STATUS_NEW_RECORD = "Editing new record; save changes to add to list.";
    protected static final String STATUS_NO_RECORDS = "No records found.  Use \"New\" button to create some.";
    protected static final String STATUS_FILTER_NO_RECORDS = "Filter returned no records.  Check spelling or be less specific.";

    private JPanel masterListPanel = null;
    protected JPanel jplAboveList = null;
    private JPanel jplList = null;
    protected JPanel jplFilter = null;
    private JPanel jplBelowList  = null;
    
    protected JLabel lblStats;
    
    protected CPSTable masterTable;
    protected JPopupMenu pupColumnList;
    private CPSSearchField tfldFilter = null;

    private JButton btnNewRecord, btnDupeRecord, btnDeleteRecord;
    
    private CPSMasterDetailModule uiManager;

    private int detailRow = -1;
    /// selectedID is the ID of the currently selected record (as opposed to
    // the row number of the selected row.
    private int[] selectedRows = {};
    private ArrayList<ColumnNameStruct> columnList = new ArrayList<ColumnNameStruct>();


    // lists and such for the GlazedList sorting, filtering and such
    protected BasicEventList<CPSRecord> masterList = new BasicEventList<CPSRecord>();
    protected FilterList<CPSRecord> masterListFiltered = new FilterList<CPSRecord>( masterList );
    protected SortedList<CPSRecord> masterListSorted = new SortedList<CPSRecord>( masterListFiltered, new CPSComparator( CPSPlanting.PROP_ID ));
    EventSelectionModel<CPSRecord> selectModel = new EventSelectionModel( masterListSorted );

    protected CompositeMatcherEditor<CPSRecord> compositeFilter = null;
    protected EventList<MatcherEditor<CPSRecord>> filterList;
    protected CPSTextFilter<CPSRecord> textFilter;


    public CPSMasterView( CPSMasterDetailModule ui ) {
       uiManager = ui;
       
       buildMainPanel( null );

       selectModel.setSelectionMode( ListSelection.MULTIPLE_INTERVAL_SELECTION );

       masterListSorted.setMode(SortedList.AVOID_MOVING_ELEMENTS);
    }
    
    public int init() { return 0; }
    protected int saveState() {
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

    protected CPSRecord getDetailsForID( int id ) {
      int i = findRecord(id, masterList );

      if ( i != -1 )
        return masterList.get(i);
      else
        return null;
    }
    
    protected abstract CPSRecord getDetailsForIDs( List<Integer> ids );

    
    protected CPSRecord getRecordToDisplay() {
       if ( selectModel.getSelected().size() < 1 )
          return null;
       else if ( selectModel.getSelected().size() == 1 )
          return selectModel.getSelected().get(0);
       else {
         List<Integer> ids = new ArrayList<Integer> ();
         for ( CPSRecord r : selectModel.getSelected() )
           ids.add( r.getID() );
         return getDetailsForIDs( ids );
       }
    }
    
    protected void updateDetailView() {
        if ( selectModel.getSelected().size() < 1 )
            uiManager.clearDetailDisplay();
        else if ( selectModel.getSelected().size() == 1 )
           uiManager.displayDetail( selectModel.getSelected().get(0) );
        else {
          List<Integer> ids = new ArrayList<Integer> ();
          for ( CPSRecord r : selectModel.getSelected() )
            ids.add( r.getID() );
           uiManager.displayDetail( getDetailsForIDs( ids ));
        }
        
    }



    public abstract CPSRecord getBlankRecord();
    public CPSRecord createNewRecord() {
      return createNewRecord( getBlankRecord() );
    }
    protected CPSRecord createNewRecord( CPSRecord r ) {
      masterList.add(r);
      return r;
    }

    public CPSRecord duplicateRecord( int id ) {
      return duplicateRecord( getDetailsForID(id) );
    }
    protected CPSRecord duplicateRecord( CPSRecord d ) {

      if ( d == null )
        return null;

      int id = d.getID();
      int i = findRecord( id, masterListFiltered );

      if ( i != -1 ) {
        return createNewRecord( masterListFiltered.get(i) );
      }
      else {
        i = findRecord( id, masterList );
        if ( i != -1 ) {
          return createNewRecord( masterList.get(i) );
        }
      }

      return null;
    }

    public void deleteRecord( int id ) {

      int i = findRecord( id, masterList );

      if ( i != -1 ) {
        masterList.remove(i);
      }

    }

    protected abstract void updateRecordInDB( CPSRecord r );

    protected void updateRecord( CPSRecord r ) {

      int i = findRecord( r.getID(), masterListFiltered );
      
      if ( i != -1 )
        masterListFiltered.set( i, r );
      else {
        i = findRecord( r.getID(), masterList );
        if ( i != -1 )
          masterList.set( i, r );
      }

    }


    /** Find record by ID, not List index. */
    private int findRecord( int id, List<CPSRecord> l ) {

      int found = -1;

      if ( id != -1 )
        for ( int i = 0; i < l.size(); i++ ) {

          if ( l.get(i).getID() == id )
            return i;

        }

      return found;

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

        if ( ! lsm.isSelectionEmpty() )
            updateDetailView();

    }
    
    protected void selectRecords( List<Integer> ids ) {
      setSelection( ids );
    }

    protected void selectRecord( int id ) {
      setSelection( id );
    }

    /**
     * Selects a single row in the table.  If recordID doesn't exist in the
     * displayed record, unselects everything and clears detail display.
     * @param recordID the record id (not row number) of the item to select
     */
    private void setSelection( int recordID ) {
       setSelection( Arrays.asList( recordID ));          
    }
    private void setSelection( List<Integer> ids ) {

      ListSelectionModel lsm = masterTable.getSelectionModel();
      lsm.clearSelection();

      int s = 0;
      int i = 0;
      for ( CPSRecord r : masterListSorted ) {
        if ( ids.contains( r.getID() )) {
          lsm.addSelectionInterval( i, i );
          s++;
          if ( ids.size() == s ) // break if we've selected them all
            break;
        }
        i++; // count rows
      }

      masterTable.setSelectionModel( lsm );

      // if the record to select did not exist in the list, then
      // clear the detail display
      if ( masterTable.getSelectedRowCount() == 0 )
        uiManager.clearDetailDisplay();

      
    }



    private void setUnselectedRowByRecordID( int recordID ) {

       int i = 0;
       for ( CPSRecord r : masterListSorted ) {
           if ( r.getID() == recordID ) {
//               CPSModule.debug( "CPSMasterView", "Unselecting row: " + i );
               selectModel.removeIndexInterval( i, i );
               break;
           }
           i++; // count rows
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
        
        masterListPanel = new JPanel( new MigLayout( "insets 2px",
                                                     "[grow, fill]",
                                                     "[][grow, fill][]") );
        
        if ( title != null )
            masterListPanel.setBorder( BorderFactory.createTitledBorder( title ) );
        else
            masterListPanel.setBorder( BorderFactory.createEtchedBorder() );
       
    }
    protected void buildMainPanel( String title ) {
        
        initMainPanel( title );
        
        masterListPanel.add( getAboveListPanel(), "dock north, wrap" );
        masterListPanel.add( getListPanel(),      "wrap" );
        masterListPanel.add( getBelowListPanel(), "dock south, wrap" );
       
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
    protected void enableRecordButtons( boolean enabled ) {
      btnNewRecord.setEnabled(enabled);
      btnDupeRecord.setEnabled(enabled);
      btnDeleteRecord.setEnabled(enabled);
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

       // build compositeFilter text box
       tfldFilter = new CPSSearchField( "Filter" );
       tfldFilter.setToolTipText( "Only show records matching ALL of these terms.  Leave blank to show everything." );
       tfldFilter.setMaximumSize( tfldFilter.getPreferredSize() );

       // setup the text filtering mechanism
       textFilter = new CPSTextFilter<CPSRecord>( tfldFilter, getTextFilterator() );
       // TODO add call to textFilter.setFields( .... ) to enable advanced field matching

       // setup the list of filters and add an "all" matcher
       filterList = new BasicEventList<MatcherEditor<CPSRecord>>();
       filterList.add( new AbstractMatcherEditor<CPSRecord>()
                          {
                            @Override
                            public Matcher<CPSRecord> getMatcher() {
                               return Matchers.trueMatcher();
                            }
                          } );

       // now setup the thing that will match all of the elements of the filter list
       compositeFilter = new CompositeMatcherEditor<CPSRecord>( filterList );
       compositeFilter.setMode( CompositeMatcherEditor.AND );

       // setup the filtered list
       masterListFiltered.setMatcherEditor( compositeFilter );

       // add the focus listener so that the the compositeFilter box behaves correctly
       tfldFilter.addFocusListener( new FocusListener()
                               {
                                 public void focusGained( FocusEvent arg0 ) {
                                    if ( ! filterList.contains( textFilter ))
                                       filterList.add( textFilter );
                                 }

                                 public void focusLost( FocusEvent arg0 ) {
                                    if ( tfldFilter.getText().equals( "" ) ) {
                                       filterList.remove( textFilter );
                                    }
                                 }
                               } );

       if ( init )
         masterListFiltered.addListEventListener( this );

       // finally, setup the compositeFilter panel
       if ( init )
           initFilterPanel();
       
       jplFilter.add( tfldFilter );
       return jplFilter;
       
    }
    protected JTextComponent getFilterComponent() { return tfldFilter; }
    
    protected JPanel getListPanel() {
        if ( jplList == null ) { buildListPanel(); }
        return jplList;       
    }
    protected void initListPanel() {
        jplList = new JPanel();
        jplList.setLayout( new BoxLayout( jplList, BoxLayout.LINE_AXIS ) );
    }
    protected void buildListPanel() {

       masterTable = new CPSTable( new EventTableModel<CPSRecord>( masterListSorted, getTableFormat() ) );
       masterList.addListEventListener(this);
       
       Dimension d = new Dimension( 500, masterTable.getRowHeight() * 10 );
       masterTable.getTableHeader().addMouseListener( this );
       
       // Ask to be notified of selection changes (see method: valueChanged)
       selectModel.addListSelectionListener( this );
       masterTable.setSelectionModel( selectModel );

       TableComparatorChooser tcc =
       TableComparatorChooser.install( masterTable, masterListSorted, TableComparatorChooser.SINGLE_COLUMN );

       // specify the default sort column
       tcc.appendComparator( getTableFormat().getDefaultSortColumn(), 0, false );

       initListPanel(); // init listPanel
       jplList.add( new JScrollPane( masterTable ) );

//       jplList.getInputMap( jplList.WHEN_IN_FOCUSED_WINDOW )
//                      .put( KeyStroke.getKeyStroke( CPSGlobalSettings.getModifierKey() + " UP" ), "prev" );
//       jplList.getInputMap( jplList.WHEN_IN_FOCUSED_WINDOW )
//                      .put( KeyStroke.getKeyStroke( CPSGlobalSettings.getModifierKey() + " DOWN" ), "next" );
//       jplList.getActionMap().put( "prev",
//                                   new AbstractAction() {
//                                     public void actionPerformed(ActionEvent e) {
//                                       int i = selectModel.getMinSelectionIndex();
//                                       if ( i > 0 )
//                                         selectModel.setSelectionInterval(i-1, i-1);
//                                     }
//                                   });
//       jplList.getActionMap().put( "next",
//                                   new AbstractAction() {
//                                     public void actionPerformed(ActionEvent e) {
//                                       int i = selectModel.getMaxSelectionIndex();
//                                       if ( i < masterListSorted.size()-1 )
//                                         selectModel.setSelectionInterval(i+1, i+1);
//                                     }
//                                   });

       buildColumnListPopUpMenu();

    }



    protected void clearSelection() {
        selectModel.clearSelection();
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
            lblStats.setToolTipText("Statistics regarding displayed or selected rows.");
        }
        
    }
    
    protected abstract List<String[]> getColumnPrettyNameMap();
    protected abstract List<String> getDisplayableColumnList();
    protected abstract List<Integer> getDefaultDisplayableColumnList();
    protected void buildColumnListPopUpMenu() {

       pupColumnList = new JPopupMenu();
       //  create the empty submenu
       JMenu subMenu = new JMenu( "More ..." );
       
       // grab the column model and column count
       DefaultTableColumnModel model = (DefaultTableColumnModel) masterTable.getColumnModel();
       int nrColumns = model.getColumnCount();

       // generate all of the menu entries
       List<ColumnMenuItem> columnListItems = new ArrayList<ColumnMenuItem>( nrColumns );
       for ( int i = 0; i < nrColumns; i++ )
          columnListItems.add( new ColumnMenuItem( model, i ) );

       // process the entries and add them to the correct menu or submenu
       int j = 0;
       for ( ColumnMenuItem ci : columnListItems ) {

         if ( ! ci.isDefaultColumn() )
           model.removeColumn( ci.column );

          // We will only "feature" the first 20 entries, the rest will be buried in a submenu
          if ( j++ < 20 )
             pupColumnList.add( ci );
          else
             subMenu.add( ci );

       }

       if ( subMenu.getItemCount() > 0 )
           pupColumnList.add( subMenu );
       
    }
    
    // This might happen at any time.  So we need to update our view of the data
    // whenever it happens.
   @Override
    public void setDataSource( CPSDataModel dm ) {
       super.setDataSource( dm );
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

        CPSModule.debug( "CPSMasterView", "Removing items from masterList: " + masterList.size() );

        // obtaining locks on the list before we edit it seems to have
        // fixed sporadic NullPointerExceptions related to concurrency
        masterList.removeListEventListener(this);
        masterList.getReadWriteLock().writeLock().lock();
        masterList.clear();
        masterList.addAll( getMasterListData() );
        masterList.getReadWriteLock().writeLock().unlock();
        masterList.addListEventListener(this);

        CPSModule.debug( "CPSMasterView", "Items in masterList:         " + masterList.size() );
        CPSModule.debug( "CPSMasterView", "Items in masterListFiltered: " + masterListFiltered.size() );

    }
    
    protected void setStatus( String s ) {
        uiManager.setStatus(s);
    }
    
    /// Abstract method to retrieve new data and then hand it off to the
    // JTable to display.  Overriding class should do the fancy work of
    // figuring out which table to query, etc.  Returns a TableModel.
    protected abstract List getMasterListData();
    protected abstract CPSAdvancedTableFormat getTableFormat();
    protected abstract TextFilterator<CPSRecord> getTextFilterator();


    protected void addFilter( MatcherEditor me ) {
       filterList.add(me);
    }
    protected void removeFilter( MatcherEditor me ) {
       filterList.remove( me );
    }
    protected MatcherEditor getFilter() {
       return compositeFilter;
    }
    protected void updateFilter() {}

    protected abstract int getTypeOfDisplayedRecord();

    public void mouseClicked(MouseEvent evt) {
        JTable table = ((JTableHeader) evt.getSource()).getTable();
        TableColumnModel colModel = table.getColumnModel();

        // The index of the column whose header was clicked
        int vColIndex = colModel.getColumnIndexAtX(evt.getX());

        // Return if not clicked on any column header
        if (vColIndex == -1)
            return;
        
       if ( evt.getButton() == evt.BUTTON3 || // RIGHT mouse button on Windows
            evt.getButton() == evt.BUTTON1 && evt.isControlDown() // CTRL + click on Mac
                ) {
           pupColumnList.show( evt.getComponent(),
                               evt.getX(), evt.getY() );
           
        }
        
    }
    public void mouseEntered(MouseEvent mouseEvent) {}
    public void mouseExited(MouseEvent mouseEvent) {}
    public void mousePressed(MouseEvent mouseEvent) {}
    public void mouseReleased(MouseEvent mouseEvent) {}


    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();

        if ( actionEvent.getSource().getClass().equals( JComboBox.class ))
          CPSModule.debug( "CPSMasterView",
                           "Action Performed: " + action +
                           " to " + ((JComboBox)actionEvent.getSource()).getSelectedItem() );
        else
          CPSModule.debug( "CPSMasterView",
                           "Action Performed: " + action +
                           " on " + ((JButton)actionEvent.getSource()).getText() );

//****************************************************************************//
//      New
//****************************************************************************//
        if ( action.equalsIgnoreCase( btnNewRecord.getActionCommand() ) ) {
            if ( !isDataAvailable() ) {
                System.err.println("ERROR: cannot create new planting, data unavailable");
                return;
            }
            CPSRecord newRecord = createNewRecord();
            int newID = newRecord.getID();
            uiManager.displayDetail( newRecord );
            uiManager.setDetailViewForEditting();
            setSelection( newID );
            setStatus( STATUS_NEW_RECORD );
        
        } 
//****************************************************************************//
//      Duplicate
//****************************************************************************//
        else if (action.equalsIgnoreCase(btnDupeRecord.getText())) {

            if (!isDataAvailable()) {

                System.err.println("ERROR: cannot duplicate planting, data unavailable");
                return;

            } else if ( selectModel.getSelected().size() != 1 ) {
              
               // TODO, support mupltiple row duplication
               System.err.println("ERROR: at present, can only duplicate single rows");
               return;
            }

            CPSRecord newRecord = duplicateRecord( selectModel.getSelected().get(0) );
            int newID = newRecord.getID();
            uiManager.displayDetail( newRecord );
            uiManager.setDetailViewForEditting();
            setSelection( newID );

        }
//****************************************************************************//
//      DELETE
//****************************************************************************//
        else if (action.equalsIgnoreCase(btnDeleteRecord.getText())) {

            if (!isDataAvailable()) {
                System.err.println("ERROR: cannon delete entry, data unavailable");
                return;
            }

            int[] is = new int[selectModel.getSelected().size()];

            CPSConfirmDialog dia =
                    new CPSConfirmDialog( "delete " + is.length + " record" +
                                          ( ( is.length > 1 ) ? "s" : "" ));
            dia.setVisible(true);

            if ( dia.didConfirm() ) {

              masterList.getReadWriteLock().writeLock().lock();
              int i = 0;
              for ( CPSRecord r : selectModel.getSelected() )
                is[i++] = r.getID();
              for ( i = 0; i < is.length; i++ )
                deleteRecord( is[i] );
              masterList.getReadWriteLock().writeLock().unlock();

              uiManager.clearDetailDisplay();
              
            }

        }
        
    }
    
   @Override
   public void dataUpdated() {
      updateMasterList();
      updateDetailView();
   }


   // for addListEventListener
   public void listChanged( ListEvent listChanges ) {

      Object source = listChanges.getSourceList();

      // filtered list is monitored to know if we have data to display
      // in the table and thus how to display buttons, etc
      if ( source == masterListFiltered ) {

         // no data in table
         if ( masterListFiltered.size() < 1 ) {

            // if no records returned, we can't very well duplicate or delete any
            btnDupeRecord.setEnabled( false );
            btnDeleteRecord.setEnabled( false );

            // if the compositeFilter string is empty, then there really are no records
            // else we're just created an incorrect or too restrictive compositeFilter
            if ( tfldFilter.getText().equals( "" ) ) {
               if ( getDisplayedTableName() == null || getDisplayedTableName().equals( "" ) ) {
                  btnNewRecord.setEnabled( false );
               } else {
                  btnNewRecord.setEnabled( true );
               }
               setStatus( STATUS_NO_RECORDS );
            } else {
               btnNewRecord.setEnabled( false );
               tfldFilter.setEnabled( true );
               setStatus( STATUS_FILTER_NO_RECORDS );
            }
            
         } // table contains data; undo anything we might have just done (in the "if" clause)
         else {

            btnNewRecord.setEnabled( true );
            btnDeleteRecord.setEnabled( true );
            btnDupeRecord.setEnabled( true );
            tfldFilter.setEnabled( true );


             // check that selected items are in table
             // if not, clear selection and display nothing
            if ( selectModel.getSelected().size() > 0 ) {
              setStatus( null );
            } else {
               setStatus( CPSMasterDetailModule.STATUS_NO_SELECTION );
            }

         }
      }
      // when items are updated in the table view, it's done to the
      // sorted table
      else if ( source == masterList ) {
        if ( masterList.size() > 0 ) {
          listChanges.next();
          if ( listChanges.getType() == ListEvent.UPDATE ) {
            // make sure the change goes back to the database
            updateRecordInDB( masterList.get( listChanges.getIndex() ));
            // make sure the record is selected (in case sort changes)
            setSelection( masterList.get( listChanges.getIndex() ).getID() );
          }
        }
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

   
   class ColumnMenuItem extends JCheckBoxMenuItem implements ActionListener {

      private DefaultTableColumnModel columnModel;
      public final TableColumn column;
      private boolean defaultColumn = true;
      
      public ColumnMenuItem( DefaultTableColumnModel columnModel, int columnIndex ) {
         // first arg to super: column name
         super( columnModel.getColumn( columnIndex ).getHeaderValue().toString(),
                true );
         this.columnModel = columnModel;
         this.column = columnModel.getColumn( columnIndex );

         // record whether this column should be displayed by default
         if ( getTableFormat() instanceof CPSAdvancedTableFormat ) {
           defaultColumn = ( (CPSAdvancedTableFormat) getTableFormat() ).isDefaultColumn( columnIndex );
           this.setSelected(defaultColumn);
         }
         addActionListener( this );

      }

      public void actionPerformed( ActionEvent e ) {
         if ( isSelected() )
            columnModel.addColumn( column );
         else
            columnModel.removeColumn( column );
      }

      /** @return true if this column should be displayed by default */
      public boolean isDefaultColumn() {
         return defaultColumn;
      }

   }


}
