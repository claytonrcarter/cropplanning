/* CPSExporter.java - Created: Feb 5, 2008
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

import CPS.Data.CPSCrop;
import CPS.Data.CPSPlanting;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple interface for classes that provide the capabilty to export
 * data to the file system.
 */
public interface CPSExporter {

    public void exportCropsAndVarieties( String filename, 
                                         List<CPSCrop> crops );
    public void exportCropPlan( String filename,
                                String planName, 
                                List<CPSPlanting> plantings );
    /**
     * Returns the default file extension used by this exporter.
     * @return the default file extension WITHOUT a leading period (".")
     */
    public String getExportFileDefaultExtension();
    
}
