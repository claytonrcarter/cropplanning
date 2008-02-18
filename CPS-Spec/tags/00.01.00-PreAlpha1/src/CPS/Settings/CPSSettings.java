/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Settings;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Clayton
 */
public class CPSSettings implements Iterator {

   private ArrayList<CPSModuleSettings> settingsList;
   private int index;
   
   public CPSSettings() {
      settingsList = new ArrayList<CPSModuleSettings>();
      index = 0;
   }
   
   public void addModuleSettings( CPSModuleSettings m ) { settingsList.add(m); }
   public CPSModuleSettings getModuleSettings() { return settingsList.get(index); }
   public CPSModuleSettings getModuleSettings( int i ) {
      if ( i < settingsList.size() )
         index = i;
      return getModuleSettings();
   }
   
   public boolean hasNext() { return index < settingsList.size(); }
   public CPSModuleSettings next() { 
      if ( hasNext() )
         return settingsList.get( ++index ); 
      else
         return null;
   }
   public void remove() {}
   
   
}
