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
import java.awt.Polygon;

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
  private Triangle[] triangles;
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
   
   // this.triangles = Triangle.randomGenome(200, width, height);
    this.repaint();
  }
    
  public void updateTriangles(List<Triangle> triangles, int width, int height, int count)
  {
    this.width = width;
    this.height = height;
    this.count = count;
    this.triangles = (Triangle[]) triangles.toArray();
    this.repaint();
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
  
  public void updateTriangles(Triangle[] triangles, int width, int height, int count)
  {
    this.width = width;
    this.height = height;
    this.count = count;
    this.triangles = triangles;
    this.repaint();
  }
  
  /**
   * @param triangles
   */
  public void displayTriangles(List<Triangle> triangles)
  {
    this.triangles = (Triangle[]) triangles.toArray();
    this.repaint();
  }
  
  public void displayTriangles(Triangle[] triangles)
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
    
    Triangle[] triangles = Triangle.randomGenome(200, width, height);
    JFrame frame = new JFrame();
    TrianglePanel panel = new TrianglePanel(width,height);
    frame.add(panel);
    frame.setSize(new Dimension(PANEL_W,PANEL_H));
    frame.setVisible(true);

    panel.displayTriangles(triangles);
    try
    {
      Thread.sleep(2000);
    } catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    panel.setTriangleCount(10);
  }
  
  public void paintComponent(Graphics canvas)
  {
    canvas.drawRect (25, 25, width+1, height+1); 
    int i = 0;
    if (triangles == null) return;
    for (Triangle t : triangles)
    {
      if (i++ >= this.count) break;
      if (t == null) continue;
      canvas.setColor(t.getColor());
      
      Polygon p = t.getPolygon();
      p.translate(25, 25);
      canvas.fillPolygon(p);
    }
  }
  

}
