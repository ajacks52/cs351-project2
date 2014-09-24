/**
 * 
 */
package genome.guicode;

import genome.types.Triangle;

import java.util.ArrayList;
import java.util.List;
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
  private List<Triangle> triangles = new ArrayList();
  private int count = 200;
  /**
   * @param height
   * @param width
   */
  public TrianglePanel(int width, int height)
  {
    this.width = width;
    this.height = height;
    
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
    ArrayList<Triangle> triangles = new ArrayList<Triangle>(200);
    for (int i=0; i < 200; i++)
    {
      triangles.add(Triangle.randomTriangleIn(width, height));
    }
    
    JFrame frame = new JFrame();
    TrianglePanel panel = new TrianglePanel(width,height);
    frame.add(panel);
    frame.setSize(new Dimension(width+20,height+40));
    panel.displayTriangles(triangles);
    frame.setVisible(true);
    try
    {
      Thread.sleep(2000);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    panel.setTriangleCount(20);
  }
  
  public void paintComponent(Graphics canvas)
  {
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
