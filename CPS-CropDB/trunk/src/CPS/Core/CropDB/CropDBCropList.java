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
import javax.swing.table.*;

// package access
class CropDBCropList extends CPSDataModelUser implements ItemListener {
   
    private JPanel cropListPanel;
    private JTable cropListTable;
    private JRadioButton radioAll, radioCrops, radioVar;
    
    private CropDBCropInfo cropInfo;
    
   
   CropDBCropList( CropDBCropInfo ci ) {
      cropInfo = ci;
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
       
       //Ask to be notified of selection changes.
       ListSelectionModel rowSM = cropListTable.getSelectionModel();
       rowSM.addListSelectionListener(
        new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
             //Ignore extra messages.
             if (e.getValueIsAdjusting()) return;

             ListSelectionModel lsm =
              (ListSelectionModel)e.getSource();
             if ( ! lsm.isSelectionEmpty() ) {
                int selectedRow = lsm.getMinSelectionIndex();
                System.out.println( "Selected row: " + selectedRow + 
                                    " (id: " + cropListTable.getValueAt( selectedRow, 0 ) + " )" );
                cropInfo.updateForCrop( dataModel.getCropInfoForRow( selectedRow ) );
             }
          }
       });
       
       
       
       JScrollPane scrollPane = new JScrollPane( cropListTable );
	
       cropListPanel.add( scrollPane, BorderLayout.CENTER );
        
       updateCropList(); 
       
    }
    
    protected void updateCropList() {
       if ( isDataAvailable() )
          updateBySelectedButton();
    }
   
    protected void updateBySelectedButton() {
       if ( ! isDataAvailable() )
          return;
       
       if      ( radioAll.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedCropAndVarietyList() );
       else if ( radioCrops.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedCropList() );
       else if ( radioVar.isSelected() )
          updateCropListTable( dataModel.getAbbreviatedVarietyList() );
       else // nothing selected (not useful)
          updateCropListTable( new DefaultTableModel() );
    }
    
    protected void updateCropListTable( TableModel tm ) {
       cropListTable.setModel(tm);
    }
    
    public JPanel getJPanel() {
       return cropListPanel;
    }
    
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
   
   
}
