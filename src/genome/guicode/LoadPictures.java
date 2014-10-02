package genome.guicode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;


/************************************************************************************
 * 
 * @author Adam
 *
 ************************************************************************************/
public class LoadPictures
{
  public static BufferedImage bImage1;
  public static BufferedImage bImage2;
  public static BufferedImage bImage3;
  public static BufferedImage bImage4;
  public static BufferedImage bImage6;
  public static BufferedImage bImage5;
  public static BufferedImage bImage7;
  public static BufferedImage bImage8;
  public static BufferedImage bImage9;

  public static File f1 = new File("images/Leonardo_da_Vinci-Mona-Lisa-460x363.png");
  public static File f2 = new File("images/Claude_Monet-Poppy_Fields-450x338.png");
  public static File f3 = new File("images/Hokusai-Great_Wave_Off_Kanagawa-450x309.png");
  public static File f4 = new File("images/Hokusai-Great_Wave_Off_Kanagawa-200x137.png");
  public static File f5 = new File("images/Carson-408x369.png");
  public static File f6 = new File("images/Gummi_Bears-299x339.png");
  public static File f7 = new File("images/Old_House-300x331.png");
  public static File f8 = new File("images/Hot_Air_Balloon-400x300.png");
  public static File f9 = new File("images/triangles.png");
  
  public static Map<String,BufferedImage> picturesMap = new TreeMap<String,BufferedImage>();
  
  int maxPicX;
  int maxPicY;


  LoadPictures()
  {
    try
    {
      bImage1 = ImageIO.read(f1);
      bImage2 = ImageIO.read(f2);
      bImage3 = ImageIO.read(f3);
      bImage4 = ImageIO.read(f4);
      bImage5 = ImageIO.read(f5);
      bImage6 = ImageIO.read(f6);
      bImage7 = ImageIO.read(f7);
      bImage8 = ImageIO.read(f8);
      bImage9 = ImageIO.read(f9);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    maxPicX = Math.max(bImage1.getWidth(),
        Math.max(bImage2.getWidth(), Math.max(bImage3.getWidth(), bImage4.getWidth())));
    maxPicY = Math.max(bImage1.getHeight(),
        Math.max(bImage2.getHeight(), Math.max(bImage3.getHeight(), bImage4.getHeight())));

    picturesMap.put(f1.getName(), bImage1);
    picturesMap.put(f2.getName(), bImage2);
    picturesMap.put(f3.getName(), bImage3);
    picturesMap.put(f4.getName(), bImage4);
    picturesMap.put(f5.getName(), bImage5);
    picturesMap.put(f6.getName(), bImage6);
    picturesMap.put(f7.getName(), bImage7);
    picturesMap.put(f8.getName(), bImage8);
    picturesMap.put(f9.getName(), bImage9);
  }

  /************************************************************************************
   * 
   * @param args
   ************************************************************************************/
  public static void main(String[] args)
  {

  }

}
