/* Setting.java - created: Jan 208
 * Copyright (C) 2008 Clayton Carter
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
