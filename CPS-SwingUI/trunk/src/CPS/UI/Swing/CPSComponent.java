/*
 * CPSComponent - a simple interface to allow our custom Swing components to 
 * record and retrieve whether they have been altered.
 */

package CPS.UI.Swing;

import CPS.Data.CPSDatum.CPSDatumState;
import java.awt.Color;

public interface CPSComponent {
   
   Color SKY_BLUE = new Color( 135, 206, 235 );
   Color LIGHT_BLUE = new Color( 173, 216, 230 ); /* same color as text selection */
   Color LIGHT_GREEN = new Color( 152, 251, 152 );
   Color LIGHT_RED = new Color( 255, 192, 203 );
   Color COLOR_INHERITED = SKY_BLUE;
   Color COLOR_CALCULATED = LIGHT_GREEN;
   Color COLOR_CHANGED = LIGHT_RED;
   Color COLOR_NORMAL = Color.WHITE;
   
   public void setInitialText( String s );
   public void setInitialText( String s, CPSDatumState c );
   public boolean hasChanged();
   public void setHasChanged( boolean b );
   public void setBackgroundInherited();
   public void setBackgroundCalculated();
   public void setBackgroundChanged();
   public void setBackgroundNormal();
   
}
