package genome.guicode;

import genome.types.Genome;
import java.awt.*;
import javax.swing.JPanel;

/************************************************************************************
 * @author Jordan Medlock
 * Class to display the triangles, displays the triangles currently seleted from the gui
 ************************************************************************************/
@SuppressWarnings("serial")
public class TrianglePanel extends JPanel
{
  private final static int PANEL_H = 550;
  private final static int PANEL_W = 550;
  private Genome genome;
  private int count = 200;

  /*******************************************************************************
   * Constructor gets everything thing set up
   *******************************************************************************/
  public TrianglePanel()
  {
    this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
    this.repaint();
  }
 
  /*******************************************************************************
   * displayGenome displays the genome 
   * Only use this one!!!!
   * @param g
   *******************************************************************************/
  public void displayGenome(Genome g)
  {
    this.genome = g;
    this.repaint();
  }



  /********************************************************************************
   * setTriangleCount used to change the number of triangles displayed
   * 
   * @param count
   *******************************************************************************/
  public void setTriangleCount(int count)
  {
    this.count = count;
    this.repaint();
  }

  /********************************************************************************
   * Does the drawing 
   *******************************************************************************/
  public void paintComponent(Graphics canvas)
  {
    if (genome != null)
    {
      super.paintComponent(canvas);
      canvas.clearRect(25, 25, Genome.currentImage.getWidth(), Genome.currentImage.getHeight());
      canvas.translate(25, 25);
      genome.drawToGraphics(canvas, count);
    }
  }
}
