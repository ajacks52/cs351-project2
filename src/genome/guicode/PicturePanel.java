package genome.guicode;

import genome.types.Triangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;

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
  private static ArrayList<Integer> colorList = new ArrayList<Integer>();

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
    super.paintComponent(canvas);
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
    pictureColorValues(currentImage);

    try
    {
      Thread.sleep(100);
    }
    catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    MainFrame.trianglePanel.displayTriangles(
        Triangle.randomGenome(200, currentImage.getWidth(), currentImage.getHeight()),
        currentImage.getWidth(), currentImage.getHeight());
    
    repaint();
  }
  
  /**
   * 
   * @param bImage
   */
  private void pictureColorValues(BufferedImage bImage)
  {
    colorList = new ArrayList<Integer>();
    for(int x = 0; x < bImage.getWidth(); x++)
    {
      for(int y = 0; y < bImage.getHeight(); y++)
      {
        colorList.add(bImage.getRGB(x, y));
      }
    }
  }
  
  public static ColorModel getColorModelCurrentImage()
  {
    return currentImage.getColorModel();
  }
  
  /**
   * 
   */
  public static ArrayList<Integer> getColorList()
  {
    return colorList;
  }
  
  /***
   * 
   */
  public static int getColorListSize()
  {
    return colorList.size();
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
   * @return
   ********************************************************************************/
  public static Point getCurrentPictureSize()
  {
    Point p = new Point(currentImage.getWidth(), currentImage.getHeight());
    return p;
    
  }
  
  /********************************************************************************
   * 
   ********************************************************************************/
  public static void main(String[] args)
  {
  }
}
