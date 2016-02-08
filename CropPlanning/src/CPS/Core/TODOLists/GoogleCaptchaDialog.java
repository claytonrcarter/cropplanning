/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.UI.Swing.CPSDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author crcarter
 */
public class GoogleCaptchaDialog extends CPSDialog
                                 implements ActionListener  {

  JButton btnCancel, btnLogin;
  JButton btnLaunch;

  URL captchaUrl = null;

  boolean cancelled = false;

  JTextField captchaAnswer;

  public GoogleCaptchaDialog() {
    super("CAPTCHA Required" );
  }

  public void setCaptchaUrl( URL captchaUrl ) {
    this.captchaUrl = captchaUrl;
  }

  public String getCaptchaAnswer() {
    return captchaAnswer.getText();
  }




  @Override
  protected void buildContentsPanel() {

    jplContents.add( new JLabel( "<html><font size=\"-2\">" +
                                 "Google is requesting that you complete a CAPTCHA<br> " +
                                 "(one of those funny fuzzy word puzzles) before you<br> " +
                                 "can continue.  Click the button to view the CAPTCHA<br>" +
                                 "in your web browser, then enter the answer in the<br>" +
                                 "box below." +
                                 "" ),
                     "align center, spanx 2, wrap" );

    btnLaunch = new JButton("View CAPTCHA in Browser");
    btnLaunch.addActionListener( this );
    jplContents.add( btnLaunch, "span 2, align center, wrap ");

    jplContents.add( new JLabel("What's it say?"), "align right" );
    captchaAnswer = new JTextField( 15 );
    jplContents.add( captchaAnswer, "wrap" );

    contentsPanelBuilt = true;
  }

  @Override
  protected void fillButtonPanel() {
  
    btnLogin = new JButton( "OK" );
    btnCancel = new JButton( "Cancel" );

    btnLogin.addActionListener(this);
    btnCancel.addActionListener(this);

    jplButtons.add( btnCancel );
    jplButtons.add( btnLogin );
    
  }

  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();

    if ( source == btnLaunch ) {
      if ( captchaUrl == null ) {
        System.err.println( "No CAPTCHA URL defined" );
      }
      else if( java.awt.Desktop.isDesktopSupported() ) {

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        if( desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
          try {
            desktop.browse( captchaUrl.toURI() );
          } catch ( Exception f ) {
            f.printStackTrace();
          }
        }
      }

    }
    else if ( source == btnLogin ) {
      setVisible(false);
    }
    else if ( source == btnCancel ) {
      cancelled = true;
      setVisible(false);
    }

  }

  public static void main(String[] args) {
    GoogleCaptchaDialog d = new GoogleCaptchaDialog();
    try {
      d.setCaptchaUrl( new URL( "http://www.failbetterfarm.com/" ) );
    } catch ( MalformedURLException e ) {
      System.err.println( e.getMessage() );
    }
    d.setVisible(true);

    System.out.println( "User entered: " + d.getCaptchaAnswer() );

    System.exit(0);
  }

}
