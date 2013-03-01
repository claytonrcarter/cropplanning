/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Module;

import java.awt.GridBagLayout;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;


public abstract class CPSModuleSettings {
    
    protected Preferences prefs = null;
    protected JPanel configPanel = null;
   
    public CPSModuleSettings() {
//        prefs = Preferences.userNodeForPackage( this.getClass() );
    }
    
    public CPSModuleSettings( Class c ) {
        prefs = Preferences.userNodeForPackage( c );
    }
    
    public Preferences getModulePreferences() {
        return prefs;
    }
   
    protected void initConfigPanel() {
        configPanel = new JPanel( new MigLayout( "", "[align right][]" ) );
        configPanel.setBorder( BorderFactory.createEtchedBorder() );
    }
       
    
}
