/* CPSImporter.java - Created: Mar 29, 2008
 * Copyright (C) 2008 sm_zl_kimi
 *
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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
/**
 * A simple interface for classes that provide the capabilty to import
 * data to the file system.
 */
public interface CPSImporter {

   public ArrayList<CPSCrop>  importCropsAndVarieties( String filename );

   public ArrayList<CPSPlanting>  importCropPlan( String filename );
   /**
    * Returns the default file extension used by this importer.
    * @return the default file extension WITHOUT a leading period (".")
    */
   public String getImportFileDefaultExtension();

}
