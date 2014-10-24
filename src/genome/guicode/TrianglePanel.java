package genome.guicode;

import genome.Constants;
import genome.types.Genome;
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
  private final static int PANEL_H = 550;
  private final static int PANEL_W = 550;
  private Genome genome;
  private int count = 200;
  private BufferedImage paintCanvas;

  /*******************************************************************************
   * * @param width
   * 
   * @param height
   *******************************************************************************/
  public TrianglePanel()
  {
    this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));

    // this.triangles = Triangle.randomGenome(200, width, height);
    this.repaint();
  }
 

  /**
   * Only use this one!!!!
   * @param g
   */
  public void displayGenome(Genome g)
  {
//    System.out.println("displayGenome");
    this.genome = g;
    this.repaint();
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
    if (genome != null)
    {
      canvas.clearRect(25, 25, Genome.currentImage.getWidth(), Genome.currentImage.getHeight());
      canvas.translate(25, 25);
      genome.drawToGraphics(canvas, count);
    }
  }


}
