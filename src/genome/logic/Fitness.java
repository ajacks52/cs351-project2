package genome.logic;

import java.awt.image.BufferedImage;

/************************************************************************************
 * 
 *
 *
 ************************************************************************************/
public class Fitness
{
  /***********************************************************************************
   * 
   ***********************************************************************************/
  public static long getFitness(BufferedImage a, BufferedImage b)
  {
//    System.out.println(a + " " + b);
    if (a.getWidth() != b.getWidth()) return 0;
    if (a.getHeight() != b.getHeight()) return 0;
    long sum = 0;
    for (int j=0; j < a.getHeight(); j++)
    {
      for (int i=0; i < a.getWidth(); i++)
      {
        int argb = a.getRGB(i, j);
        int ared = (argb >> 16) & 0xFF;
        int agreen = (argb >> 8) & 0xFF;
        int ablue = (argb >> 0) & 0xFF;
        int brgb = b.getRGB(i, j);
        int bred = (brgb >> 16) & 0xFF;
        int bgreen = (brgb >> 8) & 0xFF;
        int bblue = (brgb >> 0) & 0xFF;
        sum += Math.sqrt(Math.pow(ared-bred, 2) + Math.pow(agreen-bgreen, 2) + Math.pow(ablue-bblue, 2));
      }
//      System.out.println(sum);
    }
    return sum;
  }
}
