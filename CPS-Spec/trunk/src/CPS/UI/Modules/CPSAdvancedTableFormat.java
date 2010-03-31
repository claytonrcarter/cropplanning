/*
 *  CPSAdvancedTableFormat.java - created: Dec 9, 2009
 *  Copyright (c) **YEAR** Expected hash. user evaluated instead to freemarker.template.SimpleScalar on line 5, column 43 in Templates/Licenses/preamble.txt.
 * 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.UI.Modules;

import CPS.Data.CPSRecord;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.AdvancedTableFormat;
import java.util.Comparator;

public abstract class CPSAdvancedTableFormat<T> implements AdvancedTableFormat<T> {

   /** @return true is the column at index colNum should be displayed by default, false otherwise */
   public abstract boolean isDefaultColumn( int colNum );

   /** @return the property number for the data displayed in column at index colNum */
   public abstract int getPropNumForColumn( int colNum );

   public abstract T getBlankRecord();

   public String getColumnName( int colNum ) {

      return ((CPSRecord) getBlankRecord()).getDatum( getPropNumForColumn( colNum ) ).getName();

   }

   public Class getColumnClass( int colNum ) {

      return getColumnValue( getBlankRecord(), colNum ).getClass();

   }

   public Comparator getColumnComparator( int colNum ) {

//      return GlazedLists.comparableComparator();
      return new CPSComparator( getPropNumForColumn( colNum ) );

   }


}
