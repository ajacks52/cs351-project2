package genome.types;

import java.awt.image.BufferedImage;

public class Fitness
{
  private static int[] aRGBs;
  private static int[] bRGBs;
  
  public static double getFitness(BufferedImage a, BufferedImage b, int skip)
  {
    if (a.getWidth()  != b.getWidth())  return -1.0;
    if (a.getHeight() != b.getHeight()) return -2.0;
    
    int width = a.getWidth();
    int height = a.getHeight();
    int area = width * height;
    if (aRGBs == null || aRGBs.length != area) aRGBs = new int[area];
    if (bRGBs == null || bRGBs.length != area) bRGBs = new int[area];
    
    aRGBs = a.getRGB(0, 0, width, height, aRGBs, 0, width);
    bRGBs = b.getRGB(0, 0, width, height, bRGBs, 0, width);
    
    long sum = 0;
    for (int i=0; i < area; i+= skip)
    {
      int aargb = aRGBs[i];
      int bargb = bRGBs[i];
      
      int alpha = ((aargb >> 24) & 0xFF) - ((bargb >> 24) & 0xFF);
      int red   = ((aargb >> 16) & 0xFF) - ((bargb >> 16) & 0xFF);
      int green = ((aargb >>  8) & 0xFF) - ((bargb >>  8) & 0xFF);
      int blue  = ((aargb >>  0) & 0xFF) - ((bargb >>  0) & 0xFF);
      
      sum += alpha*alpha  +  red*red  +  green*green  +  blue*blue;
    }
    return (double) sum / (double) area * (double) skip;
  }
}
