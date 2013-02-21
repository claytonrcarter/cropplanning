/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.CropPlanStats;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class ChartPanel extends JPanel {

  private List<Double> values;
  private List<String> names;

  private String title;

  private int topOfChart = 0;
  private int barWidth = 0;

  public ChartPanel( List<Double> v, List<String> n, String t ) {
    names = n;
    values = v;
    title = t;

    ToolTipManager.sharedInstance().registerComponent(this);
  }

  @Override
  public String getToolTipText(MouseEvent event) {

    if ( barWidth < 1 || event.getY() < topOfChart )
      return "";

    int i = ( event.getY() - topOfChart ) / barWidth;

    if ( i >= names.size() )
      return "";

    String s = "Week of " + names.get(i) + ": " + values.get(i) + " beds";

    return s;
  }



  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);

    if (values == null || values.isEmpty() )
      return;

    double minValue = 0;
    double maxValue = 0;
    int longestLabelIndex = 0;

    for (int i = 0; i < values.size(); i++) {
      if ( names.get(i).length() > names.get(longestLabelIndex).length() )
        longestLabelIndex = i;
      if (minValue > values.get(i))
        minValue = values.get(i);
      if (maxValue < values.get(i))
        maxValue = values.get(i);
    }

    Dimension d = getSize();
    int clientWidth = d.width;
    int clientHeight = d.height;

    // Setup the fonts
    Font titleFont = new Font("SansSerif", Font.BOLD, 20);
    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
    Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

    // Draw the title
    if ( ! title.equals("") ) {
      int titleWidth = titleFontMetrics.stringWidth(title);
      topOfChart = titleFontMetrics.getHeight();
      g.setFont(titleFont);
      g.drawString( title,
                    (clientWidth - titleWidth) / 2,
                    titleFontMetrics.getAscent() );
    }
    int labelWidth = labelFontMetrics.stringWidth( names.get(longestLabelIndex) );
    int labelHeight = labelFontMetrics.getHeight();
    
    // determine bar thickness
    barWidth = ( clientHeight - topOfChart - 2 ) / values.size();

    // and a scale factor to make the bars fill the window
    double scale = ( clientWidth - labelWidth - 3 ) / ( maxValue - minValue );

    // set the font (we're only drawing labels from here on
    g.setFont(labelFont);

    for (int i = 0; i < values.size(); i++) {

      int y = i * barWidth + 1 + topOfChart;
      int x = labelWidth + 1;
      int length = (int) (values.get(i) * scale);

      g.setColor(Color.red);
      g.fillRect(x, y, length, barWidth - 2);
      g.setColor(Color.black);
      g.drawRect(x, y, length, barWidth - 2);
      g.drawString( names.get(i), 1, y - 1 + labelHeight / 2 + barWidth / 2 );
    }
  }


  
  public static void main(String[] argv) {
    JFrame f = new JFrame();
    f.setSize(400, 300);

    int size = 10;

    List<Double> values = new ArrayList<Double>( size );
    List<String> names = new ArrayList<String>( size );

    for ( int i = 0; i < size; i++ ) {
      values.add( i * 1d );
      names.add( "Item " + i );
    }

    f.getContentPane().add(new ChartPanel( values,
                                           names, "A title"));

    WindowListener wndCloser = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };
    f.addWindowListener(wndCloser);
    f.setVisible(true);
  }
}