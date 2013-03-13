/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.UI.Modules.CPSAdvancedTableFormat;

/**
 *
 * @author crcarter
 */
public abstract class CPSExportTableFormat<E> extends CPSAdvancedTableFormat<E> {

  /** returns TRUE if this column should be shown as part of the summary for
   * grouped entries
   */
  public abstract boolean isSummaryColumn( int colNum );

}
