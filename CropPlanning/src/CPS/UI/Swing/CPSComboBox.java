/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.UI.Swing;

import CPS.Data.CPSDatum;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

/**
 *
 * @author crcarter
 */
public class CPSComboBox extends JComboBox<String>
                          implements CPSComponent,
                                     ItemListener {

  boolean changed = false;
  boolean inherited = false;

  private Color COLOR_NORMAL;
  private Color COLOR_CHANGED = Color.RED;
  private Color COLOR_INHERITED = Color.BLUE;

  public CPSComboBox(String[] items) {
    super(items);
    setBorder(null);
    COLOR_NORMAL = this.getForeground();
    addItemListener(this);
  }

  @Override
  public String getSelectedItem() {
    return (String) super.getSelectedItem();
  }

  public void setInitialSelection( String s ) {
    setSelectedItem(s);
    setHasChanged( false );
    setBackgroundNormal();
  }

  public void setInitialSelection( String s, CPSDatum.CPSDatumState c ) {
    setInitialSelection(s);
    if ( c.isInherited() ) {
      this.setBackgroundInherited();
    }

  }


  public boolean hasChanged() { return changed; }
  public void setHasChanged(boolean b) {
    changed = b;
    if ( changed )
      setBackgroundChanged();
    else if ( inherited )
      setBackgroundInherited();
    else
      setBackgroundNormal();
  }

  public void setBackgroundInherited() { setForeground( COLOR_INHERITED ); }
  public void setBackgroundCalculated() {}
  public void setBackgroundChanged() { setForeground( COLOR_CHANGED ); }
  public void setBackgroundNormal() { setForeground( COLOR_NORMAL ); }

  public void itemStateChanged(ItemEvent e) {
    if ( e.getSource() == this )
      setHasChanged(true);
  }



}
