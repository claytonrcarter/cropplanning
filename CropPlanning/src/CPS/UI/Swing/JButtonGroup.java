/* JButtonGroup.java - 2003
 * Copyright (C) 2003 Danial C. Tofan (daniel@danieltofan.org)
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
 * 
 * This code was orginally taken from:
 * http://www.javaworld.com/javaworld/javatips/jw-javatip142.html
 * 
 */
package CPS.UI.Swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/** Extends <code>javax.swing.ButtonGroup</code> to provide methods that allow working with button references instead of button models.
 *	@author Daniel Tofan
 *	@version 1.0	April 2003
 *	@see ButtonGroup
 */
public class JButtonGroup extends ButtonGroup implements ActionListener {

    public static final int SELECT_ONLY_ONE   = 0;
    public static final int SELECT_ALLOW_NONE = 1;
    
    private int selectionModel;
    protected AbstractButton lastSelectedButton = null;
    
    /**
     * Used to deselect all buttons in the group
     */
    private static final AbstractButton dummyButton = new JButton();
    /**
     * Stores a reference to the currently selected button in the group
     */
    private AbstractButton selectedButton;

    /**
     * Creates an empty <code>JButtonGroup</code>
     */
    public JButtonGroup() {
        super();
        selectionModel = SELECT_ONLY_ONE;
        add( dummyButton );
    }

    /**
     * Creates a <code>JButtonGroup</code> object from an array of buttons and adds the buttons to the group
     * No button will be selected initially.
     * @param buttons an array of <code>AbstractButton</code>s
     */
    public JButtonGroup( AbstractButton[] buttons ) {
        add( buttons );
        add( dummyButton );
    }

    /**
     * Adds a button to the group
     * @param button an <code>AbstractButton</code> reference
     */
    @Override
    public void add( AbstractButton button ) {
        if ( button == null || buttons.contains( button ) ) {
            return;
        }
        super.add( button );
        if ( getSelection() == button.getModel() ) {
            selectedButton = button;
        }
        button.addActionListener(this);
    }

    /**
     * Adds an array of buttons to the group
     * @param buttons an array of <code>AbstractButton</code>s
     */
    public void add( AbstractButton[] buttons ) {
        if ( buttons == null ) {
            return;
        }
        for ( int i = 0; i < buttons.length; i++ ) {
            add( buttons[i] );
        }
    }

    /**
     * Removes a button from the group
     * @param button the button to be removed
     */
    @Override
    public void remove( AbstractButton button ) {
        if ( button != null ) {
            if ( selectedButton == button ) {
                selectedButton = null;
            }
            super.remove( button );
        }
    }

    /**
     * Removes all the buttons in the array from the group
     * @param buttons an array of <code>AbstractButton</code>s
     */
    public void remove( AbstractButton[] buttons ) {
        if ( buttons == null ) {
            return;
        }
        for ( int i = 0; i < buttons.length; i++ ) {
            remove( buttons[i] );
        }
    }

    /**
     * Sets the selected button in the group
     * Only one button in the group can be selected
     * @param button an <code>AbstractButton</code> reference
     * @param selected an <code>boolean</code> representing the selection state of the button
     */
    public void setSelected( AbstractButton button, boolean selected ) {
        if ( button != null )
            setSelected( button.getModel(), selected );
    }

    /**
     * Sets the selected button model in the group
     * @param model a <code>ButtonModel</code> reference
     * @param selected an <code>boolean</code> representing the selection state of the button
     */
    @Override
    public void setSelected( ButtonModel model, boolean selected ) {
        AbstractButton button = getButton( model );
        if ( buttons.contains( button ) ) {
            super.setSelected( model, selected );
            if ( getSelection() == button.getModel() )
                selectedButton = button;
        }
    }

    /**
     * Returns the <code>AbstractButton</code> whose <code>ButtonModel</code> is given.
     * If the model does not belong to a button in the group, returns null.
     * @param model a <code>ButtonModel</code> that should belong to a button in the group
     * @return an <code>AbstractButton</code> reference whose model is <code>model</code> if the button belongs to the group, <code>null</code>otherwise
     */
    public AbstractButton getButton( ButtonModel model ) {
        for ( Iterator i = buttons.iterator(); i.hasNext();) {
            AbstractButton ab = (AbstractButton) i.next();
            if ( ab.getModel() == model ) {
                return ab;
            }
        }
        return null;
    }

    /**
     * Returns the selected button in the group.
     * @return a reference to the currently selected button in the group or <code>null</code> if no button is selected
     */
    public AbstractButton getSelected() {
        if ( selectedButton == dummyButton ) {
            return null;
        } else {
            return selectedButton;
        }
    }

    @Override
    public ButtonModel getSelection() {
        if ( selectedButton == dummyButton ) {
            return null;
        } else {
            return super.getSelection();
        }
    }

    /**
     * Returns whether the button is selected
     * @param button an <code>AbstractButton</code> reference
     * @return <code>true</code> if the button is selected, <code>false</code> otherwise
     */
    public boolean isSelected( AbstractButton button ) {
        if ( button == dummyButton ) {
            return false;
        }
        return button == selectedButton;
    }

    @Override
    public int getButtonCount() {
        return super.getButtonCount() - 1;
    }
    
    
    /**
     * Returns the buttons in the group as a <code>List</code>
     * @return a <code>List</code> containing the buttons in the group, in the order they were added to the group
     */
    public List<AbstractButton> getButtons() {
        return Collections.unmodifiableList( getButtonsList() );
    }

    protected List<AbstractButton> getButtonsList() {
        List<AbstractButton> allButtons = new LinkedList<AbstractButton>( buttons );
        allButtons.remove( dummyButton );
        return allButtons;
    }
    
   
    
    public void setSelectionModel( int model ) {
        if ( model == SELECT_ALLOW_NONE ||
             model == SELECT_ONLY_ONE )
            selectionModel = model;
    }
    public int getSelectionModel() {
        return selectionModel;
    }

    /**
     * Checks whether the group contains the given button
     * @return <code>true</code> if the button is contained in the group, <code>false</code> otherwise
     */
    public boolean contains( AbstractButton button ) {
        if ( button == dummyButton ) {
            return false;
        }
        return buttons.contains( button );
    }

    /**
     * unselects all buttons
     */
    public void unselectAll() {
        setSelected( dummyButton, true );
    }

    public void actionPerformed( ActionEvent arg0 ) {
        if ( selectionModel == SELECT_ALLOW_NONE ) {
            if ( lastSelectedButton == null )
                System.out.println( "Last selected button is " + lastSelectedButton );
            else
                System.out.println( "Last selected button is " + lastSelectedButton.getText() );
            if ( lastSelectedButton != null &&
                 (AbstractButton) arg0.getSource() == lastSelectedButton ) {
                System.out.println("Unselecting all buttons in group.");
                unselectAll();
                lastSelectedButton = null;
            } 
            else {
                System.out.println("Recording the last selected button.");
                lastSelectedButton = (AbstractButton) arg0.getSource();
            }
        }
    }
    
    
}
