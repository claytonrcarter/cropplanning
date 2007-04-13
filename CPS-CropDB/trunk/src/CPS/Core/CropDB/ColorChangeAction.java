package CPS.Core.CropDB;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;

public class ColorChangeAction extends AbstractAction {
   Action defAction;
   
   public ColorChangeAction(JTextComponent jt) {
      super("Color Change Action");
      defAction = findDefaultAction(jt);
   }
   
   public void actionPerformed(ActionEvent e) {

      JTextComponent jt = (JTextComponent) e.getSource();
      jt.setBackground( Color.PINK );
    
      // Now call the installed default action
      if (defAction != null) {
         defAction.actionPerformed(e);
      }
   }
       
   public static Action findDefaultAction( JTextComponent c ) {
      // Look for default action
      // Check local keymap
      Keymap kmap = c.getKeymap();
      if (kmap.getDefaultAction() != null) {
         return kmap.getDefaultAction();
      }

      // Check parent keymaps
      kmap = kmap.getResolveParent();
      while (kmap != null) {
         if (kmap.getDefaultAction() != null) {
            return kmap.getDefaultAction();
         }
         kmap = kmap.getResolveParent();
      }
      return null;
   }
}