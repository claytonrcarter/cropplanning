
package CPS.Data;

import javax.swing.JComponent;
import javax.swing.JLabel;

public abstract class CPSDatum {

    abstract JLabel toLabel();
    abstract JComponent toEditableField();
    abstract JComponent toStaticField();

    abstract String toTableHeader();
    abstract void getTableFormat();
    abstract void getTableData();

}
