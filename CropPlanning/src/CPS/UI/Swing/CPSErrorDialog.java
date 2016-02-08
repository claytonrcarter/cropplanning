/* CPSCpnfirmDialog.java
 * Copyright (C) 2013 Clayton Carter
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
 */
package CPS.UI.Swing;

import java.awt.Component;
import javax.swing.JLabel;

public class CPSErrorDialog extends CPSInfoDialog {


  public CPSErrorDialog( Component parent, String problem ) {
    this( parent, "Error", problem );
  }
  
  public CPSErrorDialog( Component parent, String header, String problem ) {
    super( parent, header,
           "Sorry, but we encountered a problem" +
           ( problem.equals( "" ) ? "." : ":<br>" + problem ));
  }

  public static void main(String[] args) {
    new CPSErrorDialog( new JLabel(), "fart").setVisible(true);
    System.exit(0);
  }

}
