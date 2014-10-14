package genome.logic;

import genome.types.Genome;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/************************************************************************************
 * 
 *
 *
 ************************************************************************************/
public class Fitness
{
  private static HashMap<Genome,Long> map = new HashMap<Genome,Long>();
  
  
  /***********************************************************************************
   * 
   ***********************************************************************************/
  public static long getFitness(BufferedImage image, Genome g, int scaledBy)
  {
    
    BufferedImage phenome = g.getImage(200);
//    System.out.println(a + " " + b);
    if (image.getWidth() != phenome.getWidth()) return 0;
    if (image.getHeight() != phenome.getHeight()) return 0;
    long sum = 0;
    for (int j=0; j < image.getHeight(); j+= scaledBy)
    {
      for (int i=0; i < image.getWidth(); i+= scaledBy)
      {
        int argb = image.getRGB(i, j);
        int ared = (argb >> 16) & 0xFF;
        int agreen = (argb >> 8) & 0xFF;
        int ablue = (argb >> 0) & 0xFF;
        int brgb = phenome.getRGB(i, j);
        int bred = (brgb >> 16) & 0xFF;
        int bgreen = (brgb >> 8) & 0xFF;
        int bblue = (brgb >> 0) & 0xFF;
        sum += Math.sqrt(Math.pow(ared-bred, 2) + Math.pow(agreen-bgreen, 2) + Math.pow(ablue-bblue, 2));
      }
//      System.out.println(sum);
    }
    return sum * scaledBy * scaledBy;
  }
}
