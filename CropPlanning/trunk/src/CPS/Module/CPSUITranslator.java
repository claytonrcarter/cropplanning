/*
 * CPSUITranslator.java
 *
 * Created on March 15, 2007, 12:52 PM by Clayton
 *
 * Class to translate between raw data classes (from the DataModel) and
 * the UI.
 */

package CPS.Module;

import CPS.Data.*;

public abstract class CPSUITranslator<T> {
   
   public abstract <T> T convertCrop( CPSCrop c );
   
}
