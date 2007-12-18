/*
 * CropDBCropList.java
 *
 * Created on March 14, 2007, 1:03 PM
 */

package CPS.Core.CropDB;

import CPS.Data.CPSRecord;
import CPS.Module.*;
import CPS.Data.CPSCrop;
import CPS.UI.Modules.CPSMasterView;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

// package access
class CropDBCropList extends CPSMasterView implements ItemListener {

    private JRadioButton radioAll, radioCrops, radioVar;
    
    CropDBCropList( CropDBUI ui ) {
       super(ui);
    }
    
    @Override
    protected void buildAboveListPanel() {
        
        initAboveListPanel();
       
        radioAll = new JRadioButton( "All", true );
        radioCrops = new JRadioButton( "Crops", false );
        radioVar = new JRadioButton( "Varieties", false );
        radioAll.addItemListener( this );
        radioCrops.addItemListener( this );
        radioVar.addItemListener( this );
        ButtonGroup bg = new ButtonGroup();
        bg.add( radioAll );
        bg.add( radioCrops );
        bg.add( radioVar );
       
        jplAboveList.add( new JLabel( "Display:" ) );
        jplAboveList.add( radioAll );
        jplAboveList.add( radioCrops );
        jplAboveList.add( radioVar );
    
        // false ==> do not initialize panel
        super.buildAboveListPanel( false );
       
    }
    
    protected CPSCrop getDetailsForID( int id ) {
        return dataModel.getCropInfo( id );
    }
   
    protected TableModel getMasterListData() {
       if ( ! isDataAvailable() )
          return new DefaultTableModel();
       
       if      ( radioAll.isSelected() )
          return dataModel.getAbbreviatedCropAndVarietyList( getSortColumn(), getFilterString() );
       else if ( radioCrops.isSelected() )
          return dataModel.getAbbreviatedCropList( getSortColumn(), getFilterString() );
       else if ( radioVar.isSelected() )
          return dataModel.getAbbreviatedVarietyList( getSortColumn(), getFilterString() );
       else // nothing selected (not useful)
          return new DefaultTableModel();
    }
    
    // Pertinent method for ItemListener
    public void itemStateChanged( ItemEvent itemEvent ) {
       Object source = itemEvent.getItemSelectable();

       if ( source == radioAll || source == radioCrops || source == radioVar ) {
          updateMasterList();
       }
    }
    
    @Override
    protected String getDisplayedTableName() {
        return "CROPS_VARIETIES";
    }
    
    @Override
    public CPSRecord createNewRecord() {
        return dataModel.createCrop( new CPSCrop() );
    }
    
    @Override
    public CPSRecord duplicateRecord( int id ) {
        return dataModel.createCrop( dataModel.getCropInfo( id ));
    }
    @Override
    public void deleteRecord( int id ) {
        dataModel.deleteCrop( id );
    }
    
}

