/* CPSDetailView.java
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

import CPS.Data.CPSRecord;
import CPS.Module.CPSDisplayableDataUserModule;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelUser;
import CPS.Module.CPSUI;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Clayton
 */
public abstract class CPSMasterDetailModule extends CPSDisplayableDataUserModule {
    
    private JPanel mainPanel = null;
    private JSplitPane splitPane;
    private CPSDetailView detail;
    private CPSMasterView master;

    private CPSUI mainUI;
    
    protected String getMasterTableName() {
        return master.getDisplayedTableName();
    }

    protected void setMasterView( CPSMasterView mv ) { master = mv; }
    protected void setDetailView( CPSDetailView dv ) { detail = dv; }
    
    // TODO possibly remove this call an just replace it with
    // with a method to pass a record ID instead of a whole record
    protected void displayDetail( CPSRecord r ) {
        detail.displayRecord(r);
    }
    protected void setDetailViewForEditting() {
        detail.setForEditting();
    }
    protected void selectRecordInMasterView( int id ) {
        master.selectRecord( id );
    }
    
    
    public JPanel getUI() {
        if ( mainPanel == null )
            buildUI();
        return mainPanel;
    }
    protected void initUI() {
        mainPanel = new JPanel();
    }
    protected void buildUI() {
        splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, 
                                    master.getJPanel(),
                                    detail.getJPanel() );
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(0.5);
        splitPane.setOneTouchExpandable(false);
        splitPane.setContinuousLayout(false);
        initUI();
        mainPanel.add(splitPane);
    }
    
    protected void setStatus( String s ) {
       detail.setStatus(s);
    }
    
    @Override
    public void setDataSource(CPSDataModel dm) {
        // set the data source for this object
        super.setDataSource(dm);

        // set the data source for these panels
        master.setDataSource(dm);
        detail.setDataSource(dm);
    }
    
   public Dimension getSize() {
      return splitPane.getPreferredSize();  
   }
   
   @Override
   public void dataUpdated() {
      master.dataUpdated();
      detail.dataUpdated();
   }

}
