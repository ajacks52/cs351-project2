package genome.guicode;

import genome.types.Triangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


/********************************************************************************
 * 
 * @author Adam Mitchell
 * 
 *         Displays the pictures that our algorithms will try to emulate
 ********************************************************************************/
public class PicturePanel extends JPanel
{
  private final static int PANEL_H = 550;
  private final static int PANEL_W = 550;
  private int height;
  private int width;
  // private static Graphics2D canvas;
  private static BufferedImage currentImage;

  public PicturePanel(int x, int y, BufferedImage bImage)
  {
    this.currentImage = bImage;

    this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
    this.setVisible(true);

  }

 /********************************************************************************
  *  
  ********************************************************************************/
  public void paint(Graphics g)
  {
    Graphics2D canvas = (Graphics2D) g;
    //this.setBorder(new CompoundBorder(new EtchedBorder(), new LineBorder(Color.BLACK)));
    canvas.drawImage(currentImage, 30, 25, currentImage.getWidth(), currentImage.getHeight(), this);
    
  }

  /********************************************************************************
   * Controls what picture will be drawn next
   * 
   * @param imageName
   ********************************************************************************/
  public void setPicture(String imageName)
  {
    currentImage = LoadPictures.picturesMap.get(imageName);
    MainFrame.trianglePanel.displayTriangles(
        Triangle.randomGenome(200, currentImage.getWidth(), currentImage.getHeight()),
        currentImage.getWidth(), currentImage.getHeight());
    repaint();
  }
  
  /********************************************************************************
   * 
   * @return
   ********************************************************************************/
  public static BufferedImage getCurrentPicture()
  {
    return currentImage;
    
  }

  /********************************************************************************
   * 
   ********************************************************************************/
  public static void main(String[] args)
  {
  }
}
