/* CPSInfoDialog.java - created: Feb 13, 2014
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
package CPS.UI.Swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CPSInfoDialog extends CPSDialog
                           implements ActionListener {

  JButton btnOK;

  public CPSInfoDialog( Component parent, String header, String message ) {
    super( parent, header );
    setDescription( message );
  }

  public CPSInfoDialog( Component parent, String message) {
    this( parent, "Attention", message );
  }


  @Override
  protected void buildContentsPanel() {
    contentsPanelBuilt = true;
  }

  @Override
  protected void fillButtonPanel() {
    btnOK = new JButton("OK");
    btnOK.addActionListener( this );
    jplButtons.add(btnOK);
  }

  public void actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  public static void main(String[] args) {
    new CPSInfoDialog( new JPanel(), "fart").setVisible(true);
    System.exit(0);
  }

}
