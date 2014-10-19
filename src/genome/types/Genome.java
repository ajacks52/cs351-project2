package genome.types;

import genome.Constants;
import genome.guicode.MainFrameController;

import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

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
  private long fitness = -1;
  private int lastScale = 5;
 

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
  
  public long getFitness(BufferedImage image, int scaledBy)
  {
    if (fitness != -1 && image == this.image && scaledBy == lastScale) 
    {
//      System.out.println("fast fitness");
      return fitness;
    } 
    System.out.println("slow fitness");
    this.image = image;
    this.lastScale = scaledBy;
    BufferedImage phenome = getImage(200);
//    System.out.println(a + " " + b);
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
    fitness = sum;
    return sum * scaledBy * scaledBy;
  }
  
  /********************************************************************
   * Changes one of the x coords
   *******************************************************************/
  public void addX(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    int rand = Constants.random.nextInt(2);
    if (rand == 0 && t.getPoint1().x < dimension.width)
      t.getPoint1().x++;
    if (rand == 1 && t.getPoint1().x < dimension.width)
      t.getPoint2().x++;
    if (rand == 2 && t.getPoint1().x < dimension.width)
      t.getPoint3().x++;

  }

  /********************************************************************
   * Changes one of the x coords
   *******************************************************************/
  public void subX(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    int rand = Constants.random.nextInt(2);
    if (rand == 0 && t.getPoint1().x > 0)
      t.getPoint1().x--;
    if (rand == 1 && t.getPoint2().x > 0)
      t.getPoint2().x--;
    if (rand == 2 && t.getPoint3().x > 0)
      t.getPoint3().x--;

  }

  /********************************************************************
   * Changes one of the y coords
   *******************************************************************/
  public void addY(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    int rand = Constants.random.nextInt(2);
    if (rand == 0 && t.getPoint1().y < dimension.height)
      t.getPoint1().y++;
    if (rand == 1 && t.getPoint2().y < dimension.height)
      t.getPoint2().y++;
    if (rand == 2 && t.getPoint3().y < dimension.height)
      t.getPoint3().y++;

  }

  /********************************************************************
   * Changes one of the y coords
   *******************************************************************/
  public void subY(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    int rand = Constants.random.nextInt(2);
    if (rand == 0 && t.getPoint1().y > 0)
      t.getPoint1().y--;
    if (rand == 1 && t.getPoint2().y > 0)
      t.getPoint2().y--;
    if (rand == 2 && t.getPoint3().y > 0)
      t.getPoint3().y--;
  }

  /********************************************************************
   * Increments the red rgb value
   *******************************************************************/
  public void addRed(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getRed() < 255)
      t.setRed(t.getRed() + 1);
  }

  /********************************************************************
   * Increments the green rgb value
   *******************************************************************/
  public void addGreen(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getGreen() < 255)
      t.setGreen(t.getGreen() + 1);
  }

  /********************************************************************
   * Increments the blue rgb value
   *******************************************************************/
  public void addBlue(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getBlue() < 255)
      t.setBlue(t.getBlue() + 1);
  }

  /********************************************************************
   * Increments the alpha rgb value
   *******************************************************************/
  public void addAlpha(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getAlpha() < 255)
      t.setAlpha(t.getAlpha() + 1);
  }

  /********************************************************************
   * Decrements the red rgb value
   *******************************************************************/
  public void minusRed(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getRed() > 0)
      t.setRed(t.getRed() - 1);
  }

  /********************************************************************
   * Decrements the green rgb value
   *******************************************************************/
  public void minusGreen(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getGreen() > 0)
      t.setGreen(t.getGreen() - 1);
  }

  /********************************************************************
   * Decrements the blue rgb value
   *******************************************************************/
  public void minusBlue(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getBlue() > 0)
      t.setBlue(t.getBlue() - 1);
  }

  /********************************************************************
   * Decrements the alpha rgb value
   *******************************************************************/
  public void minusAlpha(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    if (t.getAlpha() > 0)
      t.setAlpha(t.getAlpha() - 1);
  }

  /********************************************************************
   * Changes the red rgb value randomly
   *******************************************************************/
  public void randomRed(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    t.setRed(Constants.random.nextInt(255));
  }

  /********************************************************************
   * Changes the green rgb value randomly
   *******************************************************************/
  public void randomGreen(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    t.setGreen(Constants.random.nextInt(255));
  }

  /********************************************************************
   * Changes the blue rgb value randomly
   *******************************************************************/
  public void randomBlue(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    t.setBlue(Constants.random.nextInt(255));
  }

  /********************************************************************
   * Changes the alpha rgb value randomly
   *******************************************************************/
  public void randomAlpha(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    t.setAlpha(Constants.random.nextInt(255));
  }

  /********************************************************************
   * Moves all the x,y points of a triangle
   *******************************************************************/
  public void moveTriangle(int i)
  {
    fitness = -1;
    Triangle t = triangles[i];
    t.getPoint1().x = Constants.random.nextInt(dimension.width);
    t.getPoint1().y = Constants.random.nextInt(dimension.height);
    t.getPoint2().x = Constants.random.nextInt(dimension.width);
    t.getPoint2().y = Constants.random.nextInt(dimension.height);
    t.getPoint3().x = Constants.random.nextInt(dimension.width);
    t.getPoint3().y = Constants.random.nextInt(dimension.height);
  }

  /********************************************************************
   * Changes the position where the triangle is drawn Copies the n-1 triangle and pastes the nth triangle in the the n-1
   * spot and then the n-1 triangle to the nth spot (swap)
   *******************************************************************/
  public void reLayerTriangle(int triangle)
  {
    fitness = -1;
    Genome g = this;
    if (triangle > 0)
    {

      Triangle t = g.getTriangles()[triangle - 1].copy();

      g.getTriangles()[triangle - 1] = g.getTriangles()[triangle];
      g.getTriangles()[triangle] = t;
    }
    else
    {
      Triangle t = g.getTriangles()[triangle + 1].copy();

      g.getTriangles()[triangle + 1] = g.getTriangles()[triangle];
      g.getTriangles()[triangle] = t;
    }
  }
  
  


  /********************************************************************
   * aplies one of the previous methods to the genome only once
   *******************************************************************/
  public void oneChange(int i)
  {
    Triangle t = triangles[i];
    int randomNum = Constants.random.nextInt(14);
    boolean goodOutCome = true;
    

    while (goodOutCome)
    {   
      long fitBefore = getFitness(MainFrameController.getCurrentPict(), 5);
      Triangle undo = t.copy();

      switch (randomNum)
      {
      case 0:
        addAlpha(i);
        break;
      case 1:
        addBlue(i);
        break;
      case 2:
        addGreen(i);
        break;
      case 3:
        addRed(i);
        break;
      case 4:
        addX(i);
        break;
      case 5:
        addY(i);
        break;
      case 7:
        moveTriangle(i);
        break;
      case 8:
        minusAlpha(i);
        break;
      case 9:
        minusBlue(i);
        break;
      case 10:
        minusGreen(i);
        break;
      case 11:
        minusRed(i);
        break;
      case 12:
        subX(i);
        break;
      case 13:
        subY(i);
        break;
      case 14:
        randomAlpha(i);
        break;
      case 15:
        randomRed(i);
        break;
      case 16:
        randomBlue(i);
        break;
      case 17:
        randomGreen(i);
        break;
      case 18:
        reLayerTriangle(i);
        break;
      }
   // checks if the hill climbing was successful
      goodOutCome = checkFit(fitBefore); 
      if(!goodOutCome) {t = undo;}
      System.out.println("option "+randomNum+"\ttriangle # "+i);
      
      MainFrameController.totalmutations++;
    }
    
  }

  private boolean checkFit(long fitBefore)
  {
     long fitAfter = getFitness(MainFrameController.getCurrentPict(), 5);
    
    //System.out.println("fit before " + fitBefore + " fit after "+ fitAfter);
    if (fitAfter < fitBefore)
    {
      System.out.println(fitAfter + " " + fitBefore);

      return true;
    }
    return false;
  }

  
}
