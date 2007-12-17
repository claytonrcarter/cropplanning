/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.UI.Modules;

import CPS.Data.CPSRecord;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSDataModelUser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Clayton
 */
public abstract class CPSMasterDetailModule extends CPSDataModelUser {
    
    private JPanel mainPanel = null;
    private JSplitPane splitPane;
    private CPSDetailView detail;
    private CPSMasterView master;

    public CPSMasterDetailModule() {}
    
    protected String getMasterTableName() {
        return master.getDisplayedTableName();
    }

    protected void setMasterView( CPSMasterView mv ) { master = mv; }
    protected void setDetailView( CPSDetailView dv ) { detail = dv; }
    
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

    protected void refreshBothViews() {
        master.refreshView();
        detail.refreshView();
    }
    protected void refreshMasterView() {
        master.updateMasterList();
    }
    protected void refreshDetailView() {
        detail.refreshView();
    }

    protected void revalidate() {
        mainPanel.revalidate();
    }
    @Override
    public void setDataSource(CPSDataModel dm) {
        // set the data source for this object
        super.setDataSource(dm);

        // set the data source for these panels
        master.setDataSource(dm);
        detail.setDataSource(dm);
    }
    

}
