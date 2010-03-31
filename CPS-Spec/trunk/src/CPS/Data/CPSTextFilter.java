/* CPSTextFilter.java - created: Feb 1, 2008
 * Copyright (C) 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
 *    email:   cropplanning@gmail.com 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package CPS.Data;

import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import javax.swing.text.JTextComponent;

public class CPSTextFilter<T> extends TextComponentMatcherEditor<CPSRecord> {

    private String filterString = "";
    private MatcherEditor<T> textMatcher;
   
    public CPSTextFilter( JTextComponent comp, TextFilterator<CPSRecord> filt ) {
       super( comp, filt, true );
    }

    public MatcherEditor<T> getTextMatcherEditor() {
       return textMatcher;
    }

    public MatcherEditor<T> getMatcherEditor() { return getTextMatcherEditor(); }

    @Deprecated
    public String getFilterString() { return filterString; }

    @Deprecated
    public void setFilterString( String filterString ) { this.filterString = filterString; }

    
    
}
