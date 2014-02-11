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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CPSErrorDialog extends CPSDialog
                           implements ActionListener {

  JButton btnOK;
  
  public CPSErrorDialog() {
    this("");
  }

  public CPSErrorDialog(String problem) {
    this( new JPanel(), problem );
  }

  public CPSErrorDialog( String problem, String header ) {
    this( new JPanel(), problem, header );
  }
  public CPSErrorDialog( JPanel parent, String problem, String header ) {
    this( parent, problem );
    setHeaderTitle( header );
  }

  public CPSErrorDialog( JPanel parent, String problem) {
    super(parent,"Error");
    if ( problem.equals( "" ) )
      setDescription( "Sorry, but we encountered a problem." );
    else
      setDescription( "Sorry, but we encountered a problem:<br>"+problem );
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
    new CPSErrorDialog("fart").setVisible(true);
    System.exit(0);
  }

}
