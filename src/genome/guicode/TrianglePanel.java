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
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;

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
  private BufferedImage paintCanvas;

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
    this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));

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
    for (int i = 0; i < 200; i++)
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

  public void displayTriangles(Triangle[] triangles, int x, int y)
  {
    /*
     * added this buffered image
     */
    paintCanvas = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = paintCanvas.createGraphics();

    graphics.setPaint(new Color(255, 255, 255));
    graphics.fillRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());

    this.triangles = triangles;
    this.repaint();
  }

  /**
   * setTriangleCount used to change the level of the number of triangles
   * 
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
    TrianglePanel panel = new TrianglePanel(width, height);
    frame.add(panel);
    frame.setSize(new Dimension(PANEL_W, PANEL_H));
    frame.setVisible(true);

    panel.displayTriangles(triangles, width, height);
    try
    {
      Thread.sleep(2000);
    }
    catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    panel.setTriangleCount(10);
  }

  public void paintComponent(Graphics canvas)
  {
    super.paintComponent(canvas);
    canvas.drawImage(paintCanvas, 25, 25, null);
    int i = 0;
    if (triangles == null)
      return;
    for (Triangle t : triangles)
    {
      if (i++ >= this.count)
        break;
      if (t == null)
        continue;
      canvas.setColor(t.getColor());

      Polygon p = t.getPolygon();
      p.translate(25, 25);
      canvas.fillPolygon(p);
    }
  }

}
