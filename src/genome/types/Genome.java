package genome.types;

import genome.Constants;

import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

/************************************************************************************
 * @author Jordan Medlock 
 * @author Adam Mitchell
 ************************************************************************************/
public class Genome
{
  private Triangle[] triangles = new Triangle[Constants.GENOME_SIZE];
  private Dimension dimension = new Dimension();
  private int fitness;

  /************************************************************************************
   * One of two constructors takes an arraylist of triangles
   * 
   * @param tris
   ************************************************************************************/
  public Genome(List<Triangle> tris, int x, int y)
  {
    dimension.width = x;
    dimension.height = y;

    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {
      triangles[i] = tris.get(i);
    }
  }

  public static Genome randomGenome(int width, int height)
  {
    Genome g = new Genome();
    g.dimension.width = width;
    g.dimension.height = height;
    for (int i=0; i < Constants.GENOME_SIZE; i++)
    {
      g.triangles[i] = Triangle.randomTriangleIn(width, height, new ArrayList());
    }
    return g;
  }
  

  public BufferedImage getImage(int count)
  {
    BufferedImage bi = new BufferedImage(dimension.width,dimension.height,BufferedImage.TYPE_INT_RGB);
    Graphics g = bi.getGraphics();
    this.drawToCanvas(g, count);
    return bi;
  }
  
  public int hammingDistance(Genome other)
  { 
    int sum = 0;
    for (int i=0; i < 200; i++)
    {
      Triangle t = this.triangles[i];
      Triangle o = other.triangles[i];
      
      int diff1 = t.getPoint1().x - o.getPoint1().x;
      sum += (diff1==0? 0 : 1);
      diff1 = t.getPoint1().y - o.getPoint1().y;
      sum += (diff1==0? 0 : 1);
      
      diff1 = t.getPoint2().x - o.getPoint2().x;
      sum += (diff1==0? 0 : 1);
      diff1 = t.getPoint2().y - o.getPoint2().y;
      sum += (diff1==0? 0 : 1);

      diff1 = t.getPoint3().x - o.getPoint3().x;
      sum += (diff1==0? 0 : 1);
      diff1 = t.getPoint3().y - o.getPoint3().y;
      sum += (diff1==0? 0 : 1);
      
      diff1 = t.getRed() - o.getRed();
      sum += (diff1==0? 0 : 1);
      
      diff1 = t.getGreen() - o.getGreen();
      sum += (diff1==0? 0 : 1);
      
      diff1 = t.getBlue() - o.getBlue();
      sum += (diff1==0? 0 : 1);
      
      diff1 = t.getAlpha() - o.getAlpha();
      sum += (diff1==0? 0 : 1);
    }
    return sum;
  }
  
  public void mateWith(Genome father, Genome daughter, Genome son, int crossoverPoint)
  {
    Genome mother = this;
    for (int i=0; i < crossoverPoint && i < 200; i++)
    {
      daughter.triangles[i] = mother.triangles[i];
      son.triangles[i] = father.triangles[i];
    }
    
    for (int i=crossoverPoint; i < 200; i++)
    {
      daughter.triangles[i] = father.triangles[i];
      son.triangles[i] = mother.triangles[i];
    }
  }
  
  /************************************************************************************
   * another constructor 
   ************************************************************************************/
  public Genome()
  {
  }

  /************************************************************************************
   * 
   * @param g
   * @param count
   ************************************************************************************/
  public void drawToCanvas(Graphics g, int count)
  {
    int i = 0;
    for (Triangle t : triangles)
    {
      if (i++ >= count)
        break;
      if (t == null)
        continue;
      g.setColor(t.getColor());
      Polygon p = t.getPolygon();
      g.fillPolygon(p);
    }
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Triangle[] getTriangles()
  {
    return triangles;
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Dimension getDimension()
  {
    return dimension;
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Genome copy()
  {
    Genome cp = new Genome();
    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {
      cp.triangles[i] = triangles[i].copy();
    }
    return cp;
  }
  
  public boolean equals(Genome other)
  {
    if (this == other) return true;
    for (int i=0; i < Constants.GENOME_SIZE; i++)
    {
      if (!this.triangles[i].equals(other.triangles[i]))
      {
        return false;
      }
    }
    return true;
  }
}
