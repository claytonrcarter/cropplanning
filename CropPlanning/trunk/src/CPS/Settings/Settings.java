/* Copyright (C) 2008 Clayton Carter */

package CPS.Settings;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class to handle and display the settings for the program.
 * 
 * @author Clayton Carter
 */
public class Settings implements Iterator {

   private CPSModuleSettings globalSettings;
   private ArrayList<CPSModuleSettings> moduleSettings;
   int index;
   
   public Settings() {
      index = 0;
      moduleSettings = new ArrayList<CPSModuleSettings>();
      
      globalSettings = new CPSModuleSettings( "Global Settings" );
      globalSettings.add( new CPSSetting<String>( "Rows or Beds?",
                                                  "Is your farm based on beds or rows?",
                                                  "Beds",
                                                  new String[]{ "Beds", "Rows" } ) );
      globalSettings.add( new CPSSetting<Integer>( "Row or Bed Length (Feet)",
                                          "What is the average length of your beds or rows?",
                                          200,
                                          CPSSetting.SETTING_TYPE_INT ));
      globalSettings.add( new CPSSetting<String>( "Unit of Length",
                                          "In what units do you measure length?",
                                          "Feet",
                                          new String[] { "Feet", "Meters" } ));
      globalSettings.add( new CPSSetting<Boolean>( "Highlight values",
                                          "Do you want 'inherited' and 'auto-calculated' values to be highlighted?",
                                          true,
                                          CPSSetting.SETTING_TYPE_BOOLEAN ));
      globalSettings.add( new CPSSetting<String>( "Data Directory",
                                          "Location on computer to save program information and data",
                                          "",
                                          CPSSetting.SETTING_TYPE_FILE ));
                                          
   }
   
   public void addSettingsForModule( CPSModuleSettings mod ) {
      moduleSettings.add( mod );
   }
   
   public boolean hasNext() {
      return false;
   }
   public CPSModuleSettings next() {
      return null;
   }
   public void remove() {}
   
}
