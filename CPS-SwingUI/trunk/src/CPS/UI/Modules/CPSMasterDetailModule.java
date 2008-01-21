/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    
//    public CPSMasterDetailModule( CPSUI uim ) {
//       mainUI = uim;
//   }
    
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

//    protected void refreshBothViews() {
//        refreshMasterView();
//        refreshDetailView();
//        mainUI.revalidate();
//    }
//    protected void refreshMasterView() {
//        master.refreshView();
//    }
//    protected void refreshDetailView() {
//       // force detail view to redisplay the record
//       displayDetail( master.getRecordToDisplay() );
//       // detail.refreshView();
//    }

//    protected void revalidate() {
//        mainPanel.revalidate();
//    }
    
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
