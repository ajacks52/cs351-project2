/**
 * 
 */
package genome.guicode;

import genome.types.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author jem
 *
 */
public class TrianglePanel extends JPanel
{
  private int height;
  private int width;
  private final static int PANEL_H = 550;
  private final static int PANEL_W = 550;
  private static List<Triangle> triangles = new ArrayList();
  private int count = 200;
  
  /**
   * @param height
   * @param width
   * @param panel 
   * @param panel 
   */
  public TrianglePanel(int width, int height)
  {
    this.width = width;
    this.height = height;
    this.setPreferredSize(new Dimension(PANEL_W,PANEL_H));    
        
    ArrayList<Triangle> triangles = new ArrayList<Triangle>(200);
    for (int i=0; i < 200; i++)
    {
      triangles.add(Triangle.randomTriangleIn(width, height));
    }
    
    this.displayTriangles(triangles);
    try
    {
      Thread.sleep(2000);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    this.setTriangleCount(200);
    
    this.setVisible(true);
    
  }
    
    
  public void updataPanel(int width, int height)
  {
    this.width = width; 
    this.height = height;
    
    
    ArrayList<Triangle> triangles = new ArrayList<Triangle>(200);
    for (int i=0; i < 200; i++)
    {
      triangles.add(Triangle.randomTriangleIn(width, height));
    }
   displayTriangles(triangles); 
  }
  
  
  /**
   * @param triangles
   */
  public void displayTriangles(List<Triangle> triangles)
  {
    this.triangles = triangles;
    this.repaint();
  }
  
  /**
   * setTriangleCount
   * used to change the level of the number of triangles
   * @param count
   */
  public void setTriangleCount(int count)
  {
    this.count = count;
    this.repaint();
  }
  
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    int width = 512;
    int height = 413;
    
    JFrame frame = new JFrame();
    TrianglePanel panel = new TrianglePanel(width,height);
    frame.add(panel);
    frame.setSize(new Dimension(PANEL_W,PANEL_H));
    panel.displayTriangles(triangles);
    frame.setVisible(true);
  }
  
  public void paintComponent(Graphics canvas)
  {
    canvas.drawRect (25, 25, width+1, height+1); 
    int i = 0;
    for (Triangle t : triangles)
    {
      if (i++ >= this.count) break;
      if (t == null) continue;
      canvas.setColor(t.getColor());
      canvas.fillPolygon(t.getPolygon());
    }
  }

}
