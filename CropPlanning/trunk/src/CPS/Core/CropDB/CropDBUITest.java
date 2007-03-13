package CPS.Core.CropDB;

/**
 * Helper class designed to be used only by the Crop DB class.
 * This class will handle all of the UI for the CropDB.
 */

import CPS.Module.CPSModule;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

class CropDBUITest extends CPSModule {

    private String ModuleName = "CropDBUI";
    private String ModuleType = "Helper";
    private String ModuleVersion = "0.1";
    private String[] ModuleRequires = { "CropDB 0.1" };

    private JPanel cropDBPanel;
    private JPanel cropListPanel;
    private JPanel cropInfoPanel;

    protected CropDBUITest () {

	buildUI();

    }

    public boolean verifyVersion ( String version ) {
	return version == ModuleVersion;
    }

    private void buildUI () {

	cropDBPanel = new JPanel();
	cropDBPanel.setLayout( new BorderLayout() );
// 	cropDBPanel.setLayout( new BoxLayout( cropDBPanel, 
// 					      BoxLayout.PAGE_AXIS ));

	cropListPanel = new JPanel();
	cropListPanel.setBorder( 
	    BorderFactory.createTitledBorder( "Crop List" ));

	String[] columnNames = {"Name", "Family", "Seeding", "Maturity",
				"Spacing", "Height", "Yield" };

	Object[][] data = {
	    { "Arugula", "Brassica", "DS", 
	      new Integer( 40 ), new Integer( 40 ), 
	      new Integer( 40 ), new Integer( 40 )  },
	    { "Lettuce, Head", "Celtuce", "TP", new Integer( 60 ),
	      new Integer( 40 ), new Integer( 40 ), new Integer( 40 ) },
	    { "Kale", "Brassica", "TP", new Integer( 50 ), 
	      new Integer( 40 ), new Integer( 40 ), new Integer( 40 ) },
	    { "Tomato", "Solanum", "TP", new Integer( 80 ),
	      new Integer( 40 ), new Integer( 40 ), new Integer( 40 ) },
	    { "Potato", "Solanum", "DS", new Integer( 100 ),
	      new Integer( 40 ), new Integer( 40 ), new Integer( 40 ) }
	};

	JTable table = new JTable(data, columnNames);

	JScrollPane scrollPane = new JScrollPane(table);
	table.setPreferredScrollableViewportSize( 
	    new Dimension( 500,
			   table.getRowHeight() * 10 ) );

	cropListPanel.add( scrollPane );

	/**
	 * Create the crop info panel
	 */
	cropInfoPanel = new JPanel();
	cropInfoPanel.setBorder( 
	    BorderFactory.createTitledBorder( "Crop Info" ));
	cropInfoPanel.setLayout( new GridBagLayout() );
	GridBagConstraints c = new GridBagConstraints();
	cropInfoPanel.setAlignmentX( JPanel.LEFT_ALIGNMENT );
	cropInfoPanel.setAlignmentY( JPanel.TOP_ALIGNMENT );

	Insets il  = new Insets( 0, 0, 0, 5 );
	Insets ita = new Insets( 0, 2, 2, 5 );

	/* Column One */
	createLabel(     cropInfoPanel, 0, 0, "Crop Name:" );
	createTextField( cropInfoPanel, 1, 0, "Arugula" );

	createLabel(     cropInfoPanel, 0, 1, "Variety:" );
	createTextField( cropInfoPanel, 1, 1, "EvenStar" );

	createLabel(     cropInfoPanel, 0, 2, "Belongs to Groups:" );
	createTextArea(  cropInfoPanel, 1, 2, "Greenhouse,\nMesclun" );


	/* Column Two */
	createLabel(     cropInfoPanel, 2, 0, "Family:" );
	createTextField( cropInfoPanel, 3, 0, "Brassica" );

	createLabel(     cropInfoPanel, 2, 1, "Mat. Days:" );
	createTextField( cropInfoPanel, 3, 1, "40" );

	createLabel(     cropInfoPanel, 2, 2, "Seeding Method:" );
	createTextField( cropInfoPanel, 3, 2, "DS, TP" );

	JPanel dsPanel = new JPanel();
	dsPanel.setBorder( BorderFactory.createTitledBorder( "DS" ));
	dsPanel.setLayout( new GridBagLayout() );
	
	createLabel(     dsPanel, 0, 0, "Rate (seeds/ft):" );
	createTextField( dsPanel, 1, 0, "12", 5 );

	createLabel(     dsPanel, 0, 1, "Seeder Setting:" );
	createTextField( dsPanel, 1, 1, "10", 5 );

	addSubPanel(     cropInfoPanel, 2, 3, 2, 3, dsPanel );

	JPanel tpPanel = new JPanel();
	tpPanel.setBorder( BorderFactory.createTitledBorder( "TP" ));
	tpPanel.setLayout( new GridBagLayout() );
	
	createLabel(     tpPanel, 0, 0, "Weeks to TP:" );
	createTextField( tpPanel, 1, 0, "3", 5 );

	createLabel(     tpPanel, 0, 1, "Adjust Mat. Days:" );
	createTextField( tpPanel, 1, 1, "-14", 5 );

	createLabel(     tpPanel, 0, 2, "Flat Size:" );
	createTextField( tpPanel, 1, 2, "128", 5 );

	createLabel(     tpPanel, 0, 3, "Pot Up?" );
	createTextField( tpPanel, 1, 3, "No", 5 );

	addSubPanel(     cropInfoPanel, 2, 6, 2, 5, tpPanel );

	/* Column Three */
 	JPanel yieldPanel = new JPanel();
 	yieldPanel.setBorder( BorderFactory.createTitledBorder( "Yield (lb)"));
 	yieldPanel.setLayout( new GridBagLayout() );
		
 	createLabel(     yieldPanel, 0, 0, "Per foot:" );
 	createTextField( yieldPanel, 1, 0, ".33", 5 );

 	createLabel(     yieldPanel, 0, 1, "Per row:" );
 	createTextField( yieldPanel, 1, 1, "16.33", 5 );

 	createLabel(     yieldPanel, 0, 2, "Per bed:" );
 	createTextField( yieldPanel, 1, 2, "49", 5 );

 	addSubPanel(     cropInfoPanel, 5, 0, 2, 4, yieldPanel );


	/* add panels to main frame */
	cropDBPanel.add( cropListPanel, BorderLayout.PAGE_START );
	cropDBPanel.add( cropInfoPanel, BorderLayout.CENTER );

    }

    public JPanel getUI () { return cropDBPanel; }

    /**
     * Component create shortcuts
     */

    /**
     * createLabel
     */
    private void createLabel( JPanel p, int x, int y, String str ) {

	Insets i  = new Insets( 0, 0, 0, 5 );
	GridBagConstraints c = new GridBagConstraints();
	JLabel l = new JLabel( str );

	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.FIRST_LINE_END;
	c.insets = i;
	
	p.add( l, c );

    }

    /**
     * createTextField
     */
    private void createTextField( JPanel p, int x, int y ) {
	createTextField( p, x, y, "", 10 );
    }

    private void createTextField( JPanel p, int x, int y, String str ) {
	createTextField( p, x, y, str, 10 );
    }
    
    private void createTextField( JPanel p, int x, int y, int col ) {
	createTextField( p, x, y, "", col );
    }

    private void createTextField( JPanel p, 
				  int x, int y, 
				  String str, int col ) {

	Insets i  = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();
	JTextField tf = new JTextField( str, col );

	tf.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
							Color.GRAY,
							Color.WHITE ));

	c.gridx = x;
	c.gridy = y;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.insets = i;
	
	p.add( tf, c );

    }

    /**
     * createTextArea
     */
    private void createTextArea( JPanel p, int x, int y ) {
	createTextArea( p, x, y, "", 3, 10 );
    }

    private void createTextArea( JPanel p, int x, int y, String str ) {
	createTextArea( p, x, y, str, 3, 10 );
    }

    private void createTextArea( JPanel p,
				 int x, int y, int row, int col ) {
	createTextArea( p, x, y, "", row, col );
    }
    
    private void createTextArea( JPanel p, 
				 int x, int y, 
				 String str,
				 int rows, int cols ) {

	Insets i  = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();
	JTextArea ta = new JTextArea( str, rows, cols );

	ta.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.RAISED,
							Color.GRAY,
							Color.WHITE ));

	c.gridx = x;
	c.gridy = y;
	c.gridheight = rows - 1;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.insets = i;
	
	p.add( ta, c );

    }

    private void addSubPanel( JPanel p, 
			      int x, int y, int xspan, int yspan,
			      JPanel newP ) {

	Insets i = new Insets( 0, 2, 2, 5 );
	GridBagConstraints c = new GridBagConstraints();

	c.gridx = x;
	c.gridy = y;
	c.gridwidth  = xspan;
	c.gridheight = yspan;
	c.anchor = GridBagConstraints.FIRST_LINE_START;
	c.fill = GridBagConstraints.BOTH;
	c.insets = i;

	p.add( newP, c );

    }


}
