/*
 * CropDBCropList.java
 *
 * Created on March 14, 2007, 1:03 PM
 */

package CPS.Core.CropPlans;

import CPS.Module.*;
import CPS.Data.CPSPlanting;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

// package access
class CropPlanList extends CPSDataModelUser implements TableModelListener, 
                                                         ListSelectionListener,
                                                         MouseListener,
                                                         ActionListener {
   
    private JPanel planListPanel;
    private JTable cropPlanTable;
    private String sortColumn;
    private JLabel lblPlanName;
    private JComboBox cmbxPlanList;
    private JTextField tfldFilter;
    private String filterString;
    
    private JButton btnFilterClear;
    private JButton btnNewPlan;
    
    private JPanel buttonPanel;
    private JButton btnNewPlanting, btnDupePlanting, btnDeletePlanting;
    
    private ArrayList<String> listOfValidCropPlans;
    
    private CropPlanUI uiManager;
    // private CropDBCropInfo cropInfo;
    private int plantingRow = -1, selectedRow = -1;
    /// selectedID is the ID of the currently selected record (as opposed to
    // the row number of the selected row.
    private int selectedID = -1;
    
    
    CropPlanList( CropPlanUI ui ) {
       uiManager = ui;
       filterString = "";
       sortColumn = null;
       
       /* We must build the button panel first so that it can be added
        * to the crop list panel during following call */
       buildButtonPanel(); 
       buildPlanListPane();
    }
   
    private void buildPlanListPane() {
       
       planListPanel = new JPanel( new BorderLayout() );
       planListPanel.setBorder( BorderFactory.createTitledBorder( "Crop List" ));
       
       lblPlanName = new JLabel( "Plan Name:");
       btnNewPlan = new JButton( "New" );
       btnNewPlan.setActionCommand( "NewPlan" );
       btnNewPlan.setMargin( new Insets( 1, 1, 1, 1 ) );
       btnNewPlan.addActionListener(this);
       cmbxPlanList = new JComboBox();
       cmbxPlanList.setEditable(true);
       cmbxPlanList.addActionListener(this);
       
       tfldFilter = new JTextField( 10 );
       tfldFilter.setMaximumSize( tfldFilter.getPreferredSize() );
       // HACK! TODO, improve this; possibly by implementing a delay?
       // from: http://www.exampledepot.com/egs/javax.swing.text/ChangeEvt.html
       tfldFilter.getDocument().addDocumentListener( new DocumentListener() {
          public void insertUpdate(DocumentEvent e) { 
             filterString = tfldFilter.getText(); updateBySelectedPlan(); }
          public void removeUpdate(DocumentEvent e) {
             filterString = tfldFilter.getText(); updateBySelectedPlan(); }
          public void changedUpdate(DocumentEvent evt) {}
       });
       
       btnFilterClear = new JButton( "X" );
       btnFilterClear.setMargin( new Insets( 0, 0, 0, 0 ));
       btnFilterClear.setContentAreaFilled(false);
       btnFilterClear.setFocusPainted(false);
       btnFilterClear.setBorderPainted(false);
       btnFilterClear.addActionListener(this);
       
       JPanel jplAboveList = new JPanel();
       jplAboveList.setLayout( new BoxLayout( jplAboveList, BoxLayout.LINE_AXIS ));
       jplAboveList.add( new JLabel( "Plan Name:" ) );
       jplAboveList.add( cmbxPlanList );
       jplAboveList.add( btnNewPlan );
       jplAboveList.add( Box.createHorizontalGlue() );
       jplAboveList.add( tfldFilter );
       jplAboveList.add( btnFilterClear );
       
       cropPlanTable = new JTable();
       cropPlanTable.setPreferredScrollableViewportSize( new Dimension( 500, cropPlanTable.getRowHeight() * 10 ) );
       cropPlanTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
       cropPlanTable.getTableHeader().addMouseListener( this );
       
       //Ask to be notified of selection changes.
       cropPlanTable.getSelectionModel().addListSelectionListener( this );
       
       JScrollPane scrollPane = new JScrollPane( cropPlanTable  );
	
       planListPanel.add( jplAboveList, BorderLayout.PAGE_START ); 
       planListPanel.add( scrollPane, BorderLayout.CENTER );
       planListPanel.add( buttonPanel, BorderLayout.PAGE_END );
       
       updatePlanList(); 
       
    }
    
    private void buildButtonPanel() {
      
      Insets small = new Insets( 1, 1, 1, 1 );
      
      btnNewPlanting = new JButton( "New" );
      btnNewPlanting.setActionCommand( "NewPlanting" );
      btnDupePlanting = new JButton( "Duplicate" );
      btnDeletePlanting = new JButton( "Delete" );
      btnNewPlanting.addActionListener( this );
      btnDupePlanting.addActionListener( this );
      btnDeletePlanting.addActionListener( this );
      btnNewPlanting.setMargin( small );
      btnDupePlanting.setMargin( small );
      btnDeletePlanting.setMargin( small );
        
      buttonPanel = new JPanel();
      buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.LINE_AXIS ) );
      
      buttonPanel.add( btnNewPlanting );
      buttonPanel.add( btnDupePlanting );
      buttonPanel.add( btnDeletePlanting );
      buttonPanel.add( Box.createHorizontalGlue() );
      
   }
    
    protected void updateCropInfoForRow( int i ) {
       plantingRow = i;
       refreshPlantingInfo();
    }
    
    protected void refreshPlantingInfo() {
       if ( plantingRow == -1 )
          return;
       
       uiManager.displayPlanting( dataModel.getPlanting(getSelectedPlanName(), plantingRow ));
    
    }
   
    protected void updatePlanList() {
       if ( isDataAvailable() ) {
          updateListOfPlans(); // TODO, this is way inefficient, remove it to elsewhere
          updateBySelectedPlan();
          refreshPlantingInfo();
       }
    }
   
    protected void updateListOfPlans() {
       if ( ! isDataAvailable() )
          return;
       
       listOfValidCropPlans = dataModel.getListOfCropPlans();
       cmbxPlanList.removeAllItems();
       for ( String s : listOfValidCropPlans ) {
          // TODO think about this; possibly remove COMMON_PLANTINGS from list returned by
          // getListOfCropPlans()
          if ( s.equalsIgnoreCase("common_plantings") )
             continue;
          cmbxPlanList.addItem(s);
       }
    }
    
    protected void updateBySelectedPlan() {
       if ( ! isDataAvailable() )
          return;

       String selectedPlan = (String) cmbxPlanList.getSelectedItem();
       System.out.println( "Selected plan is: " + selectedPlan );
       
       if ( selectedPlan != null )
          updatePlanTable(dataModel.getCropPlan((String) cmbxPlanList.getSelectedItem(), sortColumn, filterString));
       else
          // TODO error checking fall through to following call when invalid plan is selected
          updatePlanTable(new DefaultTableModel());
       
    }
    
    protected void updatePlanTable( TableModel tm ) {
       tm.addTableModelListener( this );
       cropPlanTable.setModel(tm);
    }
    
    String getSelectedPlanName() { return (String) cmbxPlanList.getSelectedItem(); }
    
    public JPanel getJPanel() {
       return planListPanel;
    }
        
    @Override
    public void setDataSource( CPSDataModel dm ) {
       super.setDataSource(dm);
       updatePlanList();
    }
   
    // Pertinent method for ListSelectionListener
    // gets selected row and sends that data to the cropInfo pane
    public void valueChanged( ListSelectionEvent e ) {
       //Ignore extra messages.
       if ( e.getValueIsAdjusting() ) return;

       ListSelectionModel lsm = ( ListSelectionModel ) e.getSource();
       if ( ! lsm.isSelectionEmpty() ) {
          selectedRow = lsm.getMinSelectionIndex();
          selectedID = Integer.parseInt( cropPlanTable.getValueAt( selectedRow, -1 ).toString() );
          System.out.println( "Selected row: " + selectedRow +
                      " (name: " + cropPlanTable.getValueAt( selectedRow, 0 ) +
                      ", id: " + selectedID + " )" );
          updateCropInfoForRow( selectedID );
       }
    }

    // Pertinent method for TableModelListener
    public void tableChanged( TableModelEvent e ) {
       // TODO this is a potential problem; in case table changes in the middle of an edit
       refreshPlantingInfo();
    }

    // pertinent method for getting the column which was clicked upon for sorting
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
      
      updateBySelectedPlan();
      
   }

   public void mousePressed(MouseEvent mouseEvent) {}
   public void mouseReleased(MouseEvent mouseEvent) {}
   public void mouseEntered(MouseEvent mouseEvent) {}
   public void mouseExited(MouseEvent mouseEvent) {}

   public void actionPerformed(ActionEvent actionEvent) {   
      String action = actionEvent.getActionCommand();
            
      System.out.println("DEBUG Action performed in CropPlanList: " + action );
      
      /* Button FILTER CLEAR */
      if      ( action.equalsIgnoreCase( btnFilterClear.getText() )) {
         tfldFilter.setText("");
      }
      /* Button CROP PLAN COMBOBOX */
      else if ( action.equalsIgnoreCase( "comboBoxChanged" )) {
         updateBySelectedPlan();
      }
      /* Button NEW PLAN */
      else if ( action.equalsIgnoreCase( btnNewPlan.getActionCommand() )) {
               
         if ( getSelectedPlanName().equalsIgnoreCase("") )
            System.err.println("Cannot create crop plan with no name");
         
         if ( isDataAvailable() ) {
            System.out.println("DEBUG attempting to create new plan: " + getSelectedPlanName() );
            dataModel.createCropPlan( getSelectedPlanName() );
            updatePlanList();
         }
      }
      /* Button NEW PLANTING */
      else if ( action.equalsIgnoreCase( btnNewPlanting.getActionCommand() )) {
          if ( ! isDataAvailable() ) {
              System.err.println("ERROR: cannot create new planting, data unavailable");
              return;
          }
          
          int newID = dataModel.createPlanting( getSelectedPlanName(), new CPSPlanting() ).getID();
          updatePlanList();
          // Select the new entry
          // TODO figure out how to make this work.
//          cropPlanTable.changeSelection( newID, cropPlanTable.getSelectedColumn(),
//                                         false, false );
      }
      /* Button DUPLICATE PLANTING */
      else if ( action.equalsIgnoreCase( btnDupePlanting.getText() )) {
          if ( ! isDataAvailable() ) {
              System.err.println("ERROR: cannot duplicate planting, data unavailable");
              return;
          }
          int newID = dataModel.createPlanting( getSelectedPlanName(),
                                                dataModel.getPlanting( getSelectedPlanName(), 
                                                                       selectedID )).getID();
          updatePlanList();
          // Select the new entry
          // TODO figure out how to make this work.
//          cropPlanTable.changeSelection( newID, cropPlanTable.getSelectedColumn(),
//                                         false, false );
      }
      /* Button DELETE PLANTING */
      else if ( action.equalsIgnoreCase( btnDeletePlanting.getText() )) {
          if ( ! isDataAvailable() ) {
              System.err.println("ERROR: cannon delete entry, data unavailable");
              return;
          }
          dataModel.deletePlanting( getSelectedPlanName(), selectedID );
          updatePlanList();
      }
      
   }
   
}

