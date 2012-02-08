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
public interface CPSButtonComponent extends CPSComponent {

   public void setInitialState ( boolean b );
   public void setInitialState ( boolean b, CPSDatumState c );

}
