/* CPSNumberFormatErrorDialog.java - created: Feb 13, 2014
/*
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
 */

package CPS.UI.Swing;

import javax.swing.JPanel;

/**
 *
 * @author crcarter
 */
public class CPSNumberFormatErrorDialog extends CPSErrorDialog {

  public CPSNumberFormatErrorDialog( JPanel parent ) {
    super( parent,
           "Cannot Save Changes",
           "<center>Couldn't understand one of the numbers<br>" +
           "you entered. Numbers should be entered<br>" +
           "without units. (eg 12 instead of 12in or 12\")<br> " +
           "<b>No changes are being saved."  );
  }



}
