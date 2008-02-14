/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Module;

import java.awt.GridBagLayout;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public abstract class CPSModuleSettings {
    
    protected Preferences prefs;
    protected JPanel configPanel;
   
    
    public CPSModuleSettings( Class c ) {
        prefs = Preferences.userNodeForPackage( c );
    }
    
    public Preferences getModulePreferences() {
        return prefs;
    }
   
    protected void initConfigPanel() {
        configPanel = new JPanel();
        configPanel.setBorder( BorderFactory.createEtchedBorder() );
        configPanel.setLayout( new GridBagLayout() );
    }
       
    
}
