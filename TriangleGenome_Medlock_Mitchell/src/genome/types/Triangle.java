package genome.types;

import java.awt.Color;

/****************************************************************************************
 * Triangle, the triangle objects for our image processing algorithm 
 * 
 * @author Jordan Medlock
 *
 ****************************************************************************************/
public class Triangle
{
  private short[] arr = new short[10];
  
  /****************************************************************************************
   * width, height are the size of the image you are trying to emulate
   ****************************************************************************************/
  public static int width;
  public static int height;
  
  /****************************************************************************************
   * getGene
   * @param type of the gene that you want
   * @return the value at a particular gene within the triangle
   ****************************************************************************************/
  public short getGene(GeneType type)
  {
    return arr[type.ordinal()];
  }
  
  /****************************************************************************************
   * setGene
   * @param type of the gene you want to set
   * @param value you want to set it to
   * @return true if the value is within the range for that gene
   ****************************************************************************************/
  public boolean setGene(GeneType type, short value)
  {
    if (type.inBounds(value))
    {
      arr[type.ordinal()] = value;
      return true;
    }
    return false;
  }
  
  
  /****************************************************************************************
   * getXs
   * @return an array of ints to be used in the fillPolygon method
   ****************************************************************************************/
  public int[] getXs()
  {
    return new int[] {getGene(GeneType.X1), getGene(GeneType.X2), getGene(GeneType.X3)};
  }
  
  /****************************************************************************************
   * getYs
   * @return an array of ints to be used in the fillPolygon method
   ****************************************************************************************/
  public int[] getYs()
  {
    return new int[] {getGene(GeneType.Y1), getGene(GeneType.Y2), getGene(GeneType.Y3)};
  }
  
  /****************************************************************************************
   * getColor
   * @return a Color object representing the color of the triangle
   ****************************************************************************************/
  public Color getColor()
  {
    return new Color(getGene(GeneType.RED),getGene(GeneType.GREEN),getGene(GeneType.BLUE),getGene(GeneType.ALPHA));
  }
  
  /****************************************************************************************
   * GeneType
   * Holds the type for the individual genes
   ****************************************************************************************/
  public static enum GeneType 
  {
    X1(0,Triangle.width), 
    Y1(0,Triangle.height),
    X2(0,Triangle.width), 
    Y2(0,Triangle.height), 
    X3(0,Triangle.width), 
    Y3(0,Triangle.height), 
    RED(0,256), 
    GREEN(0,256), 
    BLUE(0,256), 
    ALPHA(0,256);
    
    /****************************************************************************************
     * upper and lower bounds for the gene
     * the lower is inclusive
     * the upper is exclusive
     ****************************************************************************************/
    public int lower;
    public int upper;
    
    GeneType(int lower, int upper)
    {
      this.lower = lower;
      this.upper = upper;
    }
    
    /****************************************************************************************
     * inBounds checks if the value is in the bounds for that gene type
     * @param value
     * @return
     ****************************************************************************************/
    public boolean inBounds(short value)
    {
      return (value >= lower) && (value < upper);
    }
  }
}
