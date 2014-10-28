package genome.guicode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;

import javax.swing.*;


/********************************************************************************************************
 * 
 * @author Adam Mitchell
 * 
 * Displays the pictures that our algorithms will try to emulate
 ********************************************************************************************************/
@SuppressWarnings("serial")
public class PicturePanel extends JPanel
{
  private final int PANEL_H = 550;
  private final int PANEL_W = 550;
  private BufferedImage currentImage;
  private ArrayList<Integer> colorList = new ArrayList<Integer>();

  public PicturePanel(int x, int y, BufferedImage bImage)
  {
    this.currentImage = bImage;
    this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
    this.setVisible(true);
  }

  /********************************************************************************************************
   * Controls what picture will be drawn next
   * 
   * @param imageName
   ********************************************************************************************************/
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
  
  
  /********************************************************************************************************
   *  Paints the picture starts it 25 by 25 off from the boarder
   ********************************************************************************************************/
   public void paint(Graphics g)
   {
     Graphics2D canvas = (Graphics2D) g;
     super.paintComponent(canvas);
     canvas.drawImage(currentImage, 30, 25, currentImage.getWidth(), currentImage.getHeight(), this); 
   }
  
  /********************************************************************************************************
   * ArrayList<Integer> pictureColorValues(BufferedImage bImage)
   * Returns an array of the colors in the picture
   * @param bImage
   * @return ArrayList<Integer> of colors
   ********************************************************************************************************/
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
  
  /********************************************************************************************************
   *  Returns an array of the colors in the picture
   * @return ArrayList<Integer> of colors
   ********************************************************************************************************/
  public ArrayList<Integer> pictureColorValues()
  {
    return colorList;
  }
  
  public ColorModel getColorModelCurrentImage()
  {
    return currentImage.getColorModel();
  }
  
  /********************************************************************************************************
   * Returns the current picture
   * getCurrentPicture()
   * @return
   *******************************************************************************************************/
  public BufferedImage getCurrentPicture()
  {
    return currentImage;  
  }

  /********************************************************************************************************
   * Returns a point of with dimension the size of the current picture
   * getCurrentPictureSize()
   * 
   * @return
   ********************************************************************************************************/
  public Point getCurrentPictureSize()
  {
    Point p = new Point(currentImage.getWidth(), currentImage.getHeight());
    return p; 
  }
}
