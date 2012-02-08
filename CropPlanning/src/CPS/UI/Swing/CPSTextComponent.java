/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.UI.Swing;

import CPS.Data.CPSDatum.CPSDatumState;

/**
 *
 * @author crcarter
 */
public interface CPSTextComponent extends CPSComponent {

   public void setInitialText( String s );
   public void setInitialText( String s, CPSDatumState c );

}
