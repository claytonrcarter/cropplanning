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
import CPS.UI.Modules.CPSAdvancedTableFormat;
import CPS.UI.Modules.CPSMasterDetailModule;
import CPS.UI.Modules.CPSMasterView;
import ca.odell.glazedlists.TextFilterator;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

// package access
class CropDBCropList extends CPSMasterView implements ItemListener {

    private JRadioButton radioAll, radioCrops, radioVar;
    
    CropDBCropList( CPSMasterDetailModule mdm ) {
       super(mdm);
    }

   @Override
   protected int getTypeOfDisplayedRecord() {
      return CPSDataModelConstants.RECORD_TYPE_CROP;
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
    
    
    protected CPSRecord getDetailsForIDs( List<Integer> ids ) {
       return getDataSource().getCommonInfoForCrops( ids );
    }
    
   
    protected List getMasterListData() {
        
        if ( ! isDataAvailable() )
          return new ArrayList<CPSCrop>();

       if      ( radioAll.isSelected() )
          return getDataSource().getCropAndVarietyList();
       else if ( radioCrops.isSelected() )
          return getDataSource().getCropList();
       else if ( radioVar.isSelected() )
          return getDataSource().getVarietyList();
       else // nothing selected (not useful)
          return new ArrayList<CPSCrop>();
    }

    @Override
    protected CPSAdvancedTableFormat getTableFormat() {
        return new CropDBTableFormat();
    }

    
    @Override
    protected TextFilterator getTextFilterator() {
        return new CropDBFilterator();
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
        // TODO uh oh; this out of here
        return "CROPS_VARIETIES";
    }

    @Override
    public CPSCrop getBlankRecord() {
      return new CPSCrop();
    }

  @Override
  protected void updateRecordInDB(CPSRecord r) {
    if ( r instanceof CPSCrop )
      getDataSource().updateCrop( (CPSCrop) r );
  }

    @Override
    public CPSRecord createNewRecord( CPSRecord r ) {
      CPSCrop c = getDataSource().createCrop( (CPSCrop) r );
      return super.createNewRecord(c);
    }
    
    @Override
    public void deleteRecord( int id ) {
      super.deleteRecord(id);
      clearSelection();
      getDataSource().deleteCrop( id );
    }
    
     @Override
   protected List<String> getDisplayableColumnList() {
      return getDataSource().getCropDisplayablePropertyNames();
   }
   
   @Override
   protected List<Integer> getDefaultDisplayableColumnList() {
      return getDataSource().getCropDefaultProperties();
   }
   
   protected List<String[]> getColumnPrettyNameMap() {
       return getDataSource().getCropPrettyNames();
   }

    @Override
    protected String getTableStatisticsString() {
        return "";
    }
  
}

