/* CropDBCropList.java - created: March 14, 2007
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

package CPS.Core.CropDB;

import CPS.Data.CPSRecord;
import CPS.Module.*;
import CPS.Data.CPSCrop;
import CPS.UI.Modules.CPSMasterDetailModule;
import CPS.UI.Modules.CPSMasterView;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

// package access
class CropDBCropList extends CPSMasterView implements ItemListener {

    private JRadioButton radioAll, radioCrops, radioVar;
    
    CropDBCropList( CPSMasterDetailModule mdm ) {
       super(mdm);
       setSortColumn("crop_name");
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
    
    /**
     * Retrieves the data record for a particular crop id.
     * @param id The integer id of the crop record to retrieve.
     * @return A new CPSCrop object populated to represent the return record.
     */
    protected CPSCrop getDetailsForID( int id ) {
        return getDataSource().getCropInfo( id );
    }
    
    
    protected CPSRecord getDetailsForIDs( ArrayList<Integer> ids ) {
       return getDataSource().getCommonInfoForCrops( ids );
    }
    
   
    protected TableModel getMasterListData() {
       if ( ! isDataAvailable() )
          return new DefaultTableModel();
       
       if      ( radioAll.isSelected() )
          return getDataSource().getAbbreviatedCropAndVarietyList( getDisplayedColumnList(), getSortColumn(), getFilter() );
       else if ( radioCrops.isSelected() )
          return getDataSource().getAbbreviatedCropList( getDisplayedColumnList(), getSortColumn(), getFilter() );
       else if ( radioVar.isSelected() )
          return getDataSource().getAbbreviatedVarietyList( getDisplayedColumnList(), getSortColumn(), getFilter() );
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
        return getDataSource().createCrop( new CPSCrop() );
    }
    
    @Override
    public CPSRecord duplicateRecord( int id ) {
        return getDataSource().createCrop( getDataSource().getCropInfo( id ));
    }
    @Override
    public void deleteRecord( int id ) {
        getDataSource().deleteCrop( id );
    }
    
     @Override
   protected ArrayList<String> getDisplayableColumnList() {
      return getDataSource().getCropDisplayableColumns();
   }
   
   @Override
   protected ArrayList<String> getDefaultDisplayableColumnList() {
      return getDataSource().getCropDefaultColumns();
   }
   
   protected ArrayList<String[]> getColumnPrettyNameMap() {
       return getDataSource().getCropPrettyNames();
   }

    @Override
    protected String getTableStatisticsString() {
        return "";
    }
  
   
    
}

