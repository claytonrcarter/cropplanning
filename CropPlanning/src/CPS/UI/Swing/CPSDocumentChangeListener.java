/* CPSDocumentChangeListener.java
 * Copyright (C) 2007, 2008 Clayton Carter
 * 
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
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
 */

 package CPS.UI.Swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * CPSDocumentListener - a document listener that simply records when a text
 * component has changed from it's ititial state
 */
public class CPSDocumentChangeListener implements DocumentListener {

    CPSComponent comp;
    
    public CPSDocumentChangeListener( CPSComponent c ) {
        comp = c;
    }
    
    private void updateComponentChanged() {
       comp.setHasChanged(true);
       comp.setBackgroundChanged();
    }
    
    public void insertUpdate(DocumentEvent e)  { updateComponentChanged(); }
    public void removeUpdate(DocumentEvent e)  { updateComponentChanged(); }
    public void changedUpdate(DocumentEvent e) {}

}
