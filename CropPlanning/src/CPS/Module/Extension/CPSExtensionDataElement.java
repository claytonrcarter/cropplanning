/* CPSExtensionElement.java - created: Dec 30, 2008
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

import CPS.Module.CPSDataModel;

/**
 * An interface for the pieces or elements that make up an extension.  These elements are what actually attach
 * to the extension points of extensible modules.
 */
public interface CPSExtensionDataElement extends CPSExtensionElement {

   public void recieveDataModel( CPSDataModel dm );

   public void newDataFor( int id );
   public void loadDataFor( int id );
   public void saveDataFor( int id );

}
