package genome.guicode;

import genome.types.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/************************************************************************************
 * @author jem
 *
 ************************************************************************************/
public class TrianglePanel extends JPanel
{
  private int height;
  private int width;
  private final static int PANEL_H = 550;
  private final static int PANEL_W = 550;
  private Triangle[] triangles;
  private int count = 200;
  private BufferedImage paintCanvas;

  /*******************************************************************************
   * * @param width
   * 
   * @param height
   *******************************************************************************/
  public TrianglePanel(int width, int height)
  {
    this.width = width;
    this.height = height;
    this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));

    // this.triangles = Triangle.randomGenome(200, width, height);
    this.repaint();
  }
  
  /*******************************************************************************
   * 
   * @param triangles
   * @param width
   * @param height
   * @param count
   */
  public void updateTriangles(List<Triangle> triangles, int width, int height, int count)
  {
    this.width = width;
    this.height = height;
    this.count = count;
    this.triangles = (Triangle[]) triangles.toArray();
    this.repaint();
  }

  /*******************************************************************************
 * 
 * @param triangles
 * @param width
 * @param height
 * @param count
 */
  public void updateTriangles(Triangle[] triangles, int width, int height, int count)
  {
    this.width = width;
    this.height = height;
    this.count = count;
    this.triangles = triangles;
    this.repaint();
  }

  /********************************************************************************
   * @param triangles
   *********************************************************************************/
  public void displayTriangles(List<Triangle> triangles)
  {
    this.triangles = (Triangle[]) triangles.toArray();
    this.repaint();
  }

  /*********************************************************************************
   * gets the triangles and displays them
   ********************************************************************************/
  public void displayTriangles(Triangle[] triangles, int width, int height)
  {
    this.width = width;
    this.height = height;
    this.triangles = triangles;
    this.repaint();
  }

  /********************************************************************************
   * just to draw the image
   * 
   * @param graphics
   *******************************************************************************/
  private void drawGraphics(Graphics2D graphics)
  {
    int i = 0;
    if (triangles == null)
      return;
    for (Triangle t : triangles)
    {
      if (i++ >= this.count)
        break;
      if (t == null)
        continue;
      graphics.setColor(t.getColor());

      Polygon p = t.getPolygon();
      graphics.fillPolygon(p);
    }
  }

  /********************************************************************************
   * setTriangleCount used to change the level of the number of triangles
   * 
   * @param count
   *******************************************************************************/
  public void setTriangleCount(int count)
  {
    this.count = count;
    this.repaint();
  }

  /********************************************************************************
   * @param args
   *******************************************************************************/
  public static void main(String[] args)
  {
//    int width = 512;
//    int height = 413;
//
//    Triangle[] triangles = Triangle.randomGenome(200, width, height);
//    JFrame frame = new JFrame();
//    TrianglePanel panel = new TrianglePanel(width, height);
//    frame.add(panel);
//    frame.setSize(new Dimension(PANEL_W, PANEL_H));
//    frame.setVisible(true);
//
//    panel.displayTriangles(triangles, width, height);
//    try
//    {
//      Thread.sleep(2000);
//    }
//    catch (InterruptedException e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    panel.setTriangleCount(10);
  }

  /********************************************************************************
   * 
   *******************************************************************************/
  public void paintComponent(Graphics canvas)
  {
    if (paintCanvas == null || paintCanvas.getHeight() != height || paintCanvas.getWidth() != width)
    {
      paintCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    Graphics2D graphics = paintCanvas.createGraphics();

    graphics.setPaint(new Color(255, 255, 255));
    graphics.fillRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());
    this.drawGraphics(graphics);
    if (canvas != null)
    {
      super.paintComponent(canvas);
      canvas.drawImage(paintCanvas, 25, 25, null);
    }
  }

  /********************************************************************************
   * @return the current image
   ********************************************************************************/
  public BufferedImage getBufferedImage()
  {
    if (this.paintCanvas == null)
    {
      paintComponent(null);
    }
    return this.paintCanvas;
  }

}
