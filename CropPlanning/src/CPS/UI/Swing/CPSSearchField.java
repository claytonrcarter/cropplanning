/* SearchField.java - created: Feb 16, 2008
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

/*
 * Cribbed from: http://elliotth.blogspot.com/2004/09/cocoa-like-search-field-for-java.html
 */

package CPS.UI.Swing;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * A text field for search/filter interfaces. The extra functionality includes
 * a placeholder string (when the user hasn't yet typed anything), and a button
 * to clear the currently-entered text.
 * 
 * @author Elliott Hughes
 */

//
// TODO: add a menu of recent searches.
//
public class CPSSearchField extends JTextField {
    
    private static final Border CANCEL_BORDER = new CancelBorder();
    private boolean sendsNotificationForEachKeystroke = true;
    private boolean showingPlaceholderText = false;
    private boolean armed = false;
    private PlaceholderText pt;

    public CPSSearchField(String placeholderText) {
        super(15);
        pt = new PlaceholderText(placeholderText);
        addFocusListener(pt);
        initBorder();
        initKeyListener();
    }
    
    public CPSSearchField() {
        this("Search");
    }
    
    @Override
    public String getText() {
        if (showingPlaceholderText) {
            return "";
        }
        return super.getText();
    }

//    @Override
//    public void setText( String t ) {
//      if ( showingPlaceholderText ) {
//        pt.focusGained( null );
//      }
//      super.setText( t );
//      pt.focusLost( null );
//      postActionEvent();
//    }
    
    private void initBorder() {
        setBorder(new CompoundBorder(getBorder(), CANCEL_BORDER));
        
        MouseInputListener mouseInputListener = new CancelListener();
        addMouseListener(mouseInputListener);
        addMouseMotionListener(mouseInputListener);
    }
    
    private void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                } else if (sendsNotificationForEachKeystroke) {
                    maybeNotify();
                }
            }
        });
    }
    
    private void cancel() {
        super.setText("");
        postActionEvent();
    }
    
    private void maybeNotify() {
        if (showingPlaceholderText) {
            return;
        }
        postActionEvent();
    }
    
    public void setSendsNotificationForEachKeystroke(boolean eachKeystroke) {
        this.sendsNotificationForEachKeystroke = eachKeystroke;
    }    
    
    /**
     * Draws the cancel button as a gray circle with a white cross inside.
     */
    static class CancelBorder extends EmptyBorder {
        private static final Color GRAY = new Color(0.7f, 0.7f, 0.7f);
        
        CancelBorder() {
            super(0, 0, 0, 15);
        }
        
        public void paintBorder(Component c, Graphics oldGraphics, int x, int y, int width, int height) {
            CPSSearchField field = (CPSSearchField) c;
            if (field.showingPlaceholderText || field.getText().length() == 0) {
                return;
            }
            
            Graphics2D g = (Graphics2D) oldGraphics;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            final int circleL = 14;
            final int circleX = x + width - circleL;
            final int circleY = y + (height - 1 - circleL)/2;
            g.setColor(field.armed ? Color.GRAY : GRAY);
            g.fillOval(circleX, circleY, circleL, circleL);
            
            final int lineL = circleL - 8;
            final int lineX = circleX + 4;
            final int lineY = circleY + 4;
            g.setColor(Color.WHITE);
            g.drawLine(lineX, lineY, lineX + lineL, lineY + lineL);
            g.drawLine(lineX, lineY + lineL, lineX + lineL, lineY);
        }
    }    
    /**
     * Handles a click on the cancel button by clearing the text and notifying
     * any ActionListeners.
     */
    class CancelListener extends MouseInputAdapter {
        private boolean isOverButton(MouseEvent e) {
            // If the button is down, we might be outside the component
            // without having had mouseExited invoked.
            if (contains(e.getPoint()) == false) {
                return false;
            }
            
            // In lieu of proper hit-testing for the circle, check that
            // the mouse is somewhere in the border.
            Rectangle innerArea = SwingUtilities.calculateInnerArea(CPSSearchField.this, null);
            return (innerArea.contains(e.getPoint()) == false);
        }
        
        public void mouseDragged(MouseEvent e) { arm(e); }
        public void mouseEntered(MouseEvent e) { arm(e); }
        public void mouseExited(MouseEvent e) { disarm(); }
        public void mousePressed(MouseEvent e) { arm(e); }
        public void mouseReleased(MouseEvent e) {
            if (armed) {
                cancel();
            }
            disarm();
        }
        
        private void arm(MouseEvent e) {
            armed = (isOverButton(e) && SwingUtilities.isLeftMouseButton(e));
            repaint();
        }
        
        private void disarm() {
            armed = false;
            repaint();
        }
    }    
    /**
     * Replaces the entered text with a gray placeholder string when the
     * search field doesn't have the focus. The entered text returns when
     * we get the focus back.
     */
    class PlaceholderText implements FocusListener {
        private String placeholderText;
        private String previousText = "";
        private Color previousColor;

        PlaceholderText(String placeholderText) {
            this.placeholderText = placeholderText;
            focusLost(null);
        }

        public void focusGained(FocusEvent e) {
            setForeground(previousColor);
            CPSSearchField.super.setText(previousText);
            showingPlaceholderText = false;
        }

        public void focusLost(FocusEvent e) {
            previousText = getText();
            previousColor = getForeground();

            if (previousText.length() == 0) {
               showingPlaceholderText = true;
               setForeground( Color.GRAY );
               CPSSearchField.super.setText( placeholderText );
           }

        }
    }
}
