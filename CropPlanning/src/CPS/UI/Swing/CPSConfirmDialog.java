/* CPSCpnfirmDialog.java
 * Copyright (C) 2013 Clayton Carter
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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CPSConfirmDialog extends CPSDialog
                              implements ActionListener {

  JButton btnYes, btnNo;
  boolean confirmed = false;

  public CPSConfirmDialog(String action) {
    this( new JPanel(), action );
  }

  public CPSConfirmDialog( Component parent, String action) {
    super( parent, "Are you sure?" );
    setDescription( "You really want to " + action + "?" );
  }

  public boolean didConfirm() {
    return confirmed;
  }

  @Override
  protected void buildContentsPanel() {

    btnYes = new JButton( "Yes, I'm Sure" );
    btnNo = new JButton( "No, Cancel That" );

    btnYes.addActionListener(this);
    btnNo.addActionListener(this);

    JPanel jplFoo = new JPanel();
    jplFoo.setLayout( new FlowLayout( FlowLayout.TRAILING ));
    jplFoo.add(btnYes);
    jplFoo.add(btnNo);
    add(jplFoo);
    
    contentsPanelBuilt = true;
  }

  @Override
  protected void fillButtonPanel() {}

  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();

    if ( source == btnYes )
      confirmed = true;

    setVisible(false);
  }

  public static void main(String[] args) {
    new CPSConfirmDialog("fart").setVisible(true);
    System.exit(0);
  }

}
