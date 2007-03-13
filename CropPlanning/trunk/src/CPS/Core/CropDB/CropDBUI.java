package CPS.Core.CropDB;

/**
 * Helper class designed to be used only by the Crop DB class.
 * This class will handle all of the UI for the CropDB.
 */

import CPS.Module.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

class CropDBUI extends CPSModule {

    private String moduleName = "CropDBUI";
    private String moduleType = "Helper";
    private String moduleVersion = "0.1";
    private String[] ModuleRequires = { "CropDB 0.1" };

    private CPSDataModel dataModel = null;
    
    private JPanel cropDBPanel;
    private JPanel cropListPanel;
    private JPanel cropInfoPanel;

    protected CropDBUI () {

       setModuleName( "CropDBUI" );
       setModuleType( "Helper" );
       setModuleVersion( "0.1" );
       
       buildUI();

    }

    private void buildUI () {

	cropDBPanel = new JPanel();
	cropDBPanel.setLayout( new BorderLayout() );

	cropListPanel = new JPanel();
	cropListPanel.setBorder( BorderFactory.createTitledBorder( "Crop List" ));

	/**
	 * Create the crop info panel
	 */
	cropInfoPanel = new JPanel();
	cropInfoPanel.setBorder( BorderFactory.createTitledBorder( "Crop Info" ));

        /* add panels to main frame */
	cropDBPanel.add( cropListPanel, BorderLayout.PAGE_START );
	cropDBPanel.add( cropInfoPanel, BorderLayout.CENTER );

    }

    public JPanel getUI () { return cropDBPanel; }

    protected void setDataSource( CPSDataModel c ) {
       dataModel = c;
    }

}
