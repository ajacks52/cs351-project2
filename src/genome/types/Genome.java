package genome.types;

import genome.Constants;
import genome.guicode.LoadPictures;
import genome.guicode.MainFrameController;
import genome.guicode.TrianglePanel;
import genome.logic.GeneticAlg;

import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/************************************************************************************
 * @author Jordan Medlock 
 * @author Adam Mitchell
 ************************************************************************************/
public class Genome
{
  public Triangle[] triangles = new Triangle[Constants.GENOME_SIZE];
  private Dimension dimension = new Dimension();
  public BufferedImage phenotype;
  public BufferedImage resizedPhenotype;
  private BufferedImage image;
  private double fitness = -1.0;
  private int lastScale = 5;
  private GeneticAlg geneticAlg = new GeneticAlg();
 

  /************************************************************************************
   * One of 3 constructors takes an arraylist of triangles
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
    phenotype = new BufferedImage(dimension.width,dimension.height,BufferedImage.TYPE_INT_RGB);
    Graphics g = phenotype.getGraphics();
    this.drawToCanvas(g, count);
    return phenotype;
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
    geneticAlg.singlePointCrossOver(this, father, daughter, son, crossoverPoint);
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
  
  public double getFitness(BufferedImage image, int scaledBy)
  {
    if (fitness != -1 && image == this.image && scaledBy == lastScale) 
    {
//      System.out.println("fast fitness: " + fitness + " image: " + image + " scaledBy: " + scaledBy);
      return fitness;
    } 
    System.out.println("slow fitness");
    this.image = image;
    this.lastScale = scaledBy;
    BufferedImage phenome = getImage(200);
    System.out.println("phenome: " + phenome + "\nimage: " + image);
    if (image.getWidth() != phenome.getWidth()) return 0;
    if (image.getHeight() != phenome.getHeight()) return 0;
    long sum = 0;
    
    for (int j=0; j < image.getHeight(); j+= scaledBy)
    {
      for (int i=0; i < image.getWidth(); i+= scaledBy)
      {
        int argb = image.getRGB(i, j);
        int aalpha = (argb >> 24) & 0xff;
        int ared = (argb >> 16) & 0xFF;
        int agreen = (argb >> 8) & 0xFF;
        int ablue = (argb >> 0) & 0xFF;
        
        int brgb = phenome.getRGB(i, j);
        int balpha = (argb >> 24) & 0xff;
        int bred = (brgb >> 16) & 0xFF;
        int bgreen = (brgb >> 8) & 0xFF;
        int bblue = (brgb >> 0) & 0xFF;
        //sum += Math.sqrt(Math.pow(ared-bred, 2) + Math.pow(agreen-bgreen, 2) + Math.pow(ablue-bblue, 2));
        sum += (Math.abs(ared-bred) + Math.abs(agreen-bgreen) + Math.abs(ablue-bblue) + Math.abs(aalpha-balpha));

      }
//      System.out.println(sum);
    }
    fitness = sum * scaledBy * scaledBy / image.getHeight() / image.getWidth();
    return fitness;
  }
  
  
  private boolean changeX1(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    Point p = t.getPoint1();
    if (p.x + delta >= dimension.width) return false;
    if (p.x + delta < 0) return false;
    p.x += delta;
    return true;
  }
  
  private boolean changeY1(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    Point p = t.getPoint1();
    if (p.y + delta >= dimension.height) return false;
    if (p.y + delta < 0) return false;
    p.y += delta;
    return true;
  }
  
  private boolean changeX2(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    Point p = t.getPoint2();
    if (p.x + delta >= dimension.width) return false;
    if (p.x + delta < 0) return false;
    p.x += delta;
    return true;
  }
  
  private boolean changeY2(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    Point p = t.getPoint2();
    if (p.y + delta >= dimension.height) return false;
    if (p.y + delta < 0) return false;
    p.y += delta;
    return true;
  }
  
  private boolean changeX3(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    Point p = t.getPoint3();
    if (p.x + delta >= dimension.width) return false;
    if (p.x + delta < 0) return false;
    p.x += delta;
    return true;
  }
  
  private boolean changeY3(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    Point p = t.getPoint3();
    if (p.y + delta >= dimension.height) return false;
    if (p.y + delta < 0) return false;
    p.y += delta;
    return true;
  }
  
  private boolean changeRed(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    int red = t.getRed();
    if (red + delta > 255) return false;
    if (red + delta < 0) return false;
    t.setRed(red + delta);
    return true;
  }
  
  private boolean changeGreen(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    int green = t.getGreen();
    if (green + delta > 255) return false;
    if (green + delta < 0) return false;
    t.setGreen(green + delta);
    return true;
  }
  
  private boolean changeBlue(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    int blue = t.getBlue();
    if (blue + delta > 255) return false;
    if (blue + delta < 0) return false;
    t.setBlue(blue + delta);
    return true;
  }
  
  private boolean changeAlpha(int triangle, int delta)
  {
    fitness = -1;
    Triangle t = triangles[triangle];
    int alpha = t.getAlpha();
    if (alpha + delta > 255) return false;
    if (alpha + delta < 0) return false;
    t.setAlpha(alpha + delta);
    return true;
  }
  
  private int getValue(int t, int dir)
  {
    switch (dir)
    {
    case 0:
      return triangles[t].getPoint1().x;
    case 1:
      return triangles[t].getPoint1().y;
    case 2:
      return triangles[t].getPoint2().x;
    case 3:
      return triangles[t].getPoint2().y;
    case 4:
      return triangles[t].getPoint3().x;
    case 5:
      return triangles[t].getPoint3().y;
    case 6:
      return triangles[t].getRed();
    case 7:
      return triangles[t].getGreen();
    case 8:
      return triangles[t].getBlue();
    case 9:
      return triangles[t].getAlpha();
    }
    return 0;
  }
  
  
  /********************************************************************
   * aplies one of the previous methods to the genome only once
   *******************************************************************/
  public void hillClimbing()
  {
    for (int t=0; t < 200; t++)
    {
      for (int dir=0; dir < 10; dir++)
      {
        int delta = 100 * (Constants.random.nextInt(2) == 0 ? -1 : 1);
        int last = 0;
        double lastFitness = getFitness(image, 5);
        boolean stop = false;
        boolean success = false;
        while (delta > 1 || delta < -1)
        {
          System.out.println("delta: " + delta);
          // make change
          success = changeValue(t,dir,delta);
          
          // decision tree
          if (success)
          {
            success = checkFit(lastFitness);
            if (success)
            {
              System.out.println("Lastfitness: " + lastFitness + " Fitness: " + fitness + " delta: " + (lastFitness - fitness));
              last += delta;
            }
            else
            {
              changeValue(t,dir,-delta); // reset
              
              if (!stop)
              {
                last = 0;
                delta *= -1;
                stop = true;
              }
              else
              {
                break;
              }
            }
          }
          else
          {
            delta /= 2;
          }
        }
        
      }
    }
  }

  private boolean setValue(int t, int dir, int value)
  {
    int delta = value - getValue(t,dir);
    boolean success = true;
    switch (dir)
    {
    case 0:
      success = changeX1(t, delta);
      break;
    case 1:
      success = changeY1(t, delta);
      break;
    case 2:
      success = changeX2(t, delta);
      break;
    case 3:
      success = changeY2(t, delta);
      break;
    case 4:
      success = changeX3(t, delta);
      break;
    case 5:
      success = changeY3(t, delta);
      break;
    case 6:
      success = changeRed(t, delta);
      break;
    case 7:
      success = changeGreen(t, delta);
      break;
    case 8:
      success = changeBlue(t, delta);
      break;
    case 9:
      success = changeAlpha(t, delta);
      break;
    }
    return success;
  }
  
  private boolean changeValue(int t, int dir, int delta)
  {
    boolean success = true;
    switch (dir)
    {
    case 0:
      success = changeX1(t, delta);
      break;
    case 1:
      success = changeY1(t, delta);
      break;
    case 2:
      success = changeX2(t, delta);
      break;
    case 3:
      success = changeY2(t, delta);
      break;
    case 4:
      success = changeX3(t, delta);
      break;
    case 5:
      success = changeY3(t, delta);
      break;
    case 6:
      success = changeRed(t, delta);
      break;
    case 7:
      success = changeGreen(t, delta);
      break;
    case 8:
      success = changeBlue(t, delta);
      break;
    case 9:
      success = changeAlpha(t, delta);
      break;
    }
    return success;
  }
  
  private boolean checkFit(double fitBefore)
  {
     double fitAfter = getFitness(image, 5);
    
    System.out.println("fit before " + fitBefore + " fit after "+ fitAfter);
    if (fitAfter < fitBefore)
    {
//      System.out.println(fitAfter + " " + fitBefore);

      return true;
    }
    return false;
  }

  public static void main(String[] args)
  {
    BufferedImage image = LoadPictures.bImage9;
    int width = image.getWidth();
    int height = image.getHeight();
    JFrame frame = new JFrame();
    frame.setPreferredSize(new Dimension(width, height));
    TrianglePanel panel = new TrianglePanel(width, height);
    
    frame.add(panel);
    frame.setVisible(true);
    Genome g = Genome.randomGenome(width, height);
    System.out.println(g.getFitness(image, 5));
    for (int i=0; i < 100; i++)
    {
      g.hillClimbing();
      
      System.out.println("fitness: " + g.getFitness(image, 5));
      
      panel.displayGenome(g);
    }

  }
  
}
