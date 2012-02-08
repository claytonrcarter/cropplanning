/* CPSExtension.java - created: Dec 19, 2008
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

package CPS.Module.Extension;

import java.util.List;
import java.util.Map;

/**
 * Defines an extension to the software.  All general extensions should implement this.
 */
public interface CPSExtension {

   public final static int STATUS_OK = 0;
   public final static int STATUS_GENERAL_ERROR = -1;

   /** Called after all other module dependencies are satisified.  (ie, after data models are associated and such)
    * The module constructor can do some basic initializations, but should not call this method.
    * @return {@link STATUS_OK} if initialization completes successfully, {@link STATUS_GENERAL_ERROR} on
    *         general error.  Individiual extensions are free to return their own error codes, so long as
    *         {@link STATUS_OK} always mean "success".
    */
   public int init();

   /** Called when the program is shutting down. */
   public int shutdown();

   /**
    * Retrieve a Map of the modules and extension points that this extension supports.
    * @return A Map object whose keys are the names of modules and whose values are Lists of the extension points
    *         of those modules which are supported by this extension.  All map keys and extension points are stored
    *         as Strings.
    */
   public Map<String, List<String>> getExtensionMap();

   public List<CPSExtensionElement> getExtensionElementsFor( CPSExtensionPoint extensionPoint );

}
