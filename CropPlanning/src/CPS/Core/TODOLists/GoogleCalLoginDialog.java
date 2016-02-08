/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.UI.Swing.CPSDialog;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author crcarter
 */
public class GoogleCalLoginDialog extends CPSDialog
                                 implements ActionListener {

  JButton btnCancel, btnLogin;

  boolean cancelled = false;
  
  JTextField emailField;
  JPasswordField passwordField;

  public GoogleCalLoginDialog( Component parent, String email ) {
    super( parent, "You need to log in to Google Calendar" );

    emailField = new JTextField(20);
    passwordField = new JPasswordField(20);

    emailField.setText( email );

  }


  public boolean isCancelled() {
    return cancelled;
  }

  public String getEmail() {
    return emailField.getText();
  }

  public char[] getPassword() {
    return passwordField.getPassword();
  }




  @Override
  protected void buildContentsPanel() {

    jplContents.add( new JLabel( "<html><font size=\"-2\">" +
                                 "Please enter the email address and password for the<br>" +
                                 "Google account you want to export your crop plan to.<br>" +
                                 "We use a secure connection and don't save your password." +
                                 "" ),
                     "align center, spanx 2, wrap" );

    jplContents.add( new JLabel("Email Address:"), "align right, split 2" );
    jplContents.add( emailField, "wrap" );

    jplContents.add( new JLabel("Password:"), "align right, split 2" );
    jplContents.add( passwordField, "wrap" );

    contentsPanelBuilt = true;
  }
  
  @Override
  protected void fillButtonPanel() {

    btnLogin = new JButton( "Login" );
    btnCancel = new JButton( "Cancel" );

    btnLogin.addActionListener(this);
    btnCancel.addActionListener(this);

    jplButtons.add( btnCancel );
    jplButtons.add( btnLogin );

  }

  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();

    if ( source == btnCancel ) {
      cancelled = true;
      passwordField.setText( "" );
    }

    setVisible(false);
  }

  public static void main(String[] args) {
    new GoogleCalLoginDialog( new JLabel(), "" ).setVisible(true);
    System.exit(0);
  }
}
