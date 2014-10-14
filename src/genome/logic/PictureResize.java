package genome.logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PictureResize 
{
  BufferedImage resizedImage;
  
  public static BufferedImage resize(BufferedImage original, int width, int height)
  {
    BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(original, 0, 0, width, height, null);
    g.dispose();
    return resizedImage;
  }
  

}
