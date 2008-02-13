/* CPSGlobalSettings.java - created: Feb 13, 2008
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
 * 
 */

package CPS.Module;

import java.util.prefs.Preferences;

public abstract class CPSGlobalSettings extends CPSModuleSettings {

    public static final String PREF_BEDS = "Beds";
    public static final String PREF_ROWS = "Rows";
    public static final String PREF_FEET = "Feet";
    public static final String PREF_METERS = "Meters";
       
    public CPSGlobalSettings( Class c ) {
        super(c);
    }
    
    protected Preferences getGlobalPreferences() {
        return super.getModulePreferences();
    }
    public abstract String getRowsOrBeds(); 
    public abstract int getBedLength();
    public abstract String getMeasurementUnit();
    public abstract boolean getHighlightFields();
    public abstract String getOutputDir();
    public abstract boolean getFirstTimeRun();
    public abstract long getLastVersionRun();
    
}
