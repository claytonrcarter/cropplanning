/*
 * CPSComponent - a simple interface to allow our custom Swing components to 
 * record and retrieve whether they have been altered.
 */

package CPS.UI.Swing;

public interface CPSComponent {
    public void setInitialText( String s );
    public boolean hasChanged();
    public void setHasChanged(boolean b);
}
