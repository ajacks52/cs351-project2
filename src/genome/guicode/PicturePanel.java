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
  private final int PANEL_H = 550;
  private final int PANEL_W = 550;

  // private static Graphics2D canvas;
  private BufferedImage currentImage;
  private ArrayList<Integer> colorList = new ArrayList<Integer>();

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
    this.currentImage = LoadPictures.picturesMap.get(imageName);
    pictureColorValues(currentImage);

    try
    {
      Thread.sleep(100);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    repaint();
    
    colorList = new ArrayList<Integer>();
    for(int x = 0; x < currentImage.getWidth(); x++)
    {
      for(int y = 0; y < currentImage.getHeight(); y++)
      {
        colorList.add(currentImage.getRGB(x, y));
      }
    }
  }
  
  /**
   * 
   * @param bImage
   * @return ArrayList<Integer> of colors
   */
  public ArrayList<Integer> pictureColorValues(BufferedImage bImage)
  {
    colorList = new ArrayList<Integer>();
    for(int x = 0; x < bImage.getWidth(); x++)
    {
      for(int y = 0; y < bImage.getHeight(); y++)
      {
        colorList.add(bImage.getRGB(x, y));
      }
    }
    return colorList;
  }
  
  
  /**
   * 
   * @return ArrayList<Integer> of colors
   */
  public ArrayList<Integer> pictureColorValues()
  {
    return colorList;
  }
  
  
  public ColorModel getColorModelCurrentImage()
  {
    return currentImage.getColorModel();
  }
  
  
  /********************************************************************************
   * 
   * @return
   ********************************************************************************/
  public BufferedImage getCurrentPicture()
  {
    return currentImage;
    
  }

  /********************************************************************************
   * 
   * @return
   ********************************************************************************/
  public Point getCurrentPictureSize()
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
