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
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class ChartPanel extends JPanel {

  private List<Double> values;
  private List<String> names;

  private String title;

  private int barWidth = 0;

  public ChartPanel(List<Double> v, List<String> n, String t) {
    names = n;
    values = v;
    title = t;

    ToolTipManager.sharedInstance().registerComponent(this);
  }

  @Override
  public String getToolTipText(MouseEvent event) {

    if ( barWidth < 1 )
      return "";

    int i = event.getX() / barWidth;

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
    for (int i = 0; i < values.size(); i++) {
      if (minValue > values.get(i))
        minValue = values.get(i);
      if (maxValue < values.get(i))
        maxValue = values.get(i);
    }

    Dimension d = getSize();
    int clientWidth = d.width;
    int clientHeight = d.height;
    barWidth = clientWidth / values.size();

    Font titleFont = new Font("SansSerif", Font.BOLD, 20);
    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
    Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

    int titleWidth = titleFontMetrics.stringWidth(title);
    int y = titleFontMetrics.getAscent();
    int x = (clientWidth - titleWidth) / 2;
    g.setFont(titleFont);
    g.drawString(title, x, y);

    int top = titleFontMetrics.getHeight();
    int bottom = labelFontMetrics.getHeight();
    if (maxValue == minValue)
      return;
    double scale = (clientHeight - top - bottom) / (maxValue - minValue);
    y = clientHeight - labelFontMetrics.getDescent();
    g.setFont(labelFont);

    for (int i = 0; i < values.size(); i++) {
      int valueX = i * barWidth + 1;
      int valueY = top;
      int height = (int) (values.get(i) * scale);
      if (values.get(i) >= 0)
        valueY += (int) ((maxValue - values.get(i)) * scale);
      else {
        valueY += (int) (maxValue * scale);
        height = -height;
      }

      g.setColor(Color.red);
      g.fillRect(valueX, valueY, barWidth - 2, height);
      g.setColor(Color.black);
      g.drawRect(valueX, valueY, barWidth - 2, height);
      int labelWidth = labelFontMetrics.stringWidth(names.get(i));
      x = i * barWidth + (barWidth - labelWidth) / 2;
      g.drawString(names.get(i), x, y);
    }
  }


  
  public static void main(String[] argv) {
    JFrame f = new JFrame();
    f.setSize(400, 300);
    Double[] values = new Double[3];
    String[] names = new String[3];
    values[0] = 1d;
    names[0] = "Item 1";

    values[1] = 2d;
    names[1] = "Item 2";

    values[2] = 4d;
    names[2] = "Item 3";

    f.getContentPane().add(new ChartPanel( Arrays.asList(values),
                                           Arrays.asList(names), "title"));

    WindowListener wndCloser = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };
    f.addWindowListener(wndCloser);
    f.setVisible(true);
  }
}