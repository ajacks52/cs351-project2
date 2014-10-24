package genome.types;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/****************************************************************************************
 * Main purpose to evaluate fitness two methods used only getFitnessRaster is used because 
 * it is around 10 times faster.
 * 
 * @author Jordan Medlock
 * @author Adam Mitchell
 ****************************************************************************************/
public class Fitness
{
  private static int[] aRGBs;
  private static int[] bRGBs;
  
  /*****************************************************************************************
   * getFitness gets the fitness on the picture compared to the main frame picture 
   * 
   * @param a BufferedImage first image 
   * @param b BufferedImage second image 
   * @param skip int amount to jump while evaluating fitness
   * @return
   ****************************************************************************************/
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
  
  /*****************************************************************************************
   * getFitnessRaster gets the fitness on the picture compared to the main frame picture 
   * used raster graphics to progess the picture saves a lot of time becsue we aren't 
   * using getRGB()
   * 
   * @param a BufferedImage first image 
   * @param b BufferedImage second image 
   * @param skip int amount to jump while evaluating fitness
   * @return
   ****************************************************************************************/
  public static double getFitnessRaster(BufferedImage a, BufferedImage b, int scaledBy)
  {
    /*
     *gets the picture in the correct format 
     */
    BufferedImage a2 = new BufferedImage(a.getWidth(), a.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    a2.getGraphics().drawImage(a, 0, 0, null);
    
    BufferedImage b2 = new BufferedImage(b.getWidth(), b.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    b2.getGraphics().drawImage(b, 0, 0, null);
    int[] pixelsB = ((DataBufferInt) b2.getRaster().getDataBuffer()).getData();
    int[] pixelsA = ((DataBufferInt) a2.getRaster().getDataBuffer()).getData();
    int width = a.getWidth();
    int height = a.getHeight();
     
    long sum = 0;

    for (int pixel = 0; pixel < pixelsA.length; pixel += scaledBy)
    {
      int aargb  = pixelsA[pixel];
      int bargb = pixelsB[pixel];   
      int alpha = ((aargb >> 24) & 0xFF) - ((bargb >> 24) & 0xFF);
      int red   = ((aargb >> 16) & 0xFF) - ((bargb >> 16) & 0xFF);
      int green = ((aargb >>  8) & 0xFF) - ((bargb >>  8) & 0xFF);
      int blue  = ((aargb >>  0) & 0xFF) - ((bargb >>  0) & 0xFF);
      sum += alpha*alpha  +  red*red  +  green*green  +  blue*blue;
    }
    double fitness = ((double) sum * (double) scaledBy / (double) height / (double) width);
    return fitness;
  }
}
