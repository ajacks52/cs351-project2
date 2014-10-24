package genome.types;

import genome.Constants;
import genome.guicode.LoadPictures;
import genome.guicode.MainFrameController;
import genome.types.Triangle.GeneType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/****************************************************************************************
 * The wrapper container for the Genome object, also controls the hill climbing for each 
 * genome this is done internally with in the object when it's created
 * 
 * @author Jordan Medlock
 *
 ****************************************************************************************/
public class Genome
{
  public Triangle triangles[] = new Triangle[Constants.GENOME_SIZE];
  
  public static BufferedImage currentImage;
  private BufferedImage phenome;
  
  private double fitness = -1.0;
  
  public boolean reset = true;
  
  private static Random random = new Random();
  
  private MutationMethod method = MutationMethod.values()[random.nextInt(3)];
  
  /****************************************************************************************
   * Constructor takes a buffered image the current picture 
   * @param currentImage
   ****************************************************************************************/
  public Genome(BufferedImage currentImage)
  {
    Genome.currentImage = currentImage;
    Triangle.width = currentImage.getWidth();
    Triangle.height = currentImage.getHeight();
    for (int i=0; i < Constants.GENOME_SIZE; i++)
    {
      triangles[i] = new Triangle();
      for (GeneType type : GeneType.values())
      {
        triangles[i].setGene(type, (short) random.nextInt(type.upper));
      }
    }
  }
  
  /****************************************************************************************
   * Returns the triangle drawn to a buffered image that make up the genomes picture
   * @param count the amount of triangles drawn 
   * @return
   ****************************************************************************************/
  public BufferedImage getPhenome(int count)
  {
    if (reset)
    {
      if (phenome == null || phenome.getWidth() != currentImage.getWidth() || phenome.getHeight() != currentImage.getHeight()) 
      {
        phenome = new BufferedImage(currentImage.getWidth(), currentImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        phenome.getGraphics().setColor(Color.WHITE);
        phenome.getGraphics().fillRect(0, 0, currentImage.getWidth(), currentImage.getHeight());
      }
      else
      {
        phenome.getGraphics().setColor(Color.WHITE);
        phenome.getGraphics().fillRect(0, 0, currentImage.getWidth(), currentImage.getHeight());
      }
    }
    
    drawToGraphics(phenome.getGraphics(), count);
    
    return phenome;
  }
  
  public void updateCurrentImage(BufferedImage cI)
  {
    Genome.currentImage = cI; 
  }
  
  /****************************************************************************************
   * Draws the given genome to the triangle panel 
   * 
   * @param g genome 
   * @param count number of triangles to draw
   ****************************************************************************************/
  public void drawToGraphics(Graphics g, int count)
  {
    int i=0; 
    for (Triangle t : triangles)
    {
      if (i++ >= count) break;
      g.setColor(t.getColor());
      g.fillPolygon(t.getXs(), t.getYs(), 3);
    }
  }
  
  /****************************************************************************************
   * Returns the fitness either raster version of normal getRGB version
   * @return
   ***************************************************************************************/
  public double getFitness()
  {
    if (reset)
    {
      if (Constants.RASTER)
      {
        fitness = Fitness.getFitnessRaster(getPhenome(Constants.GENOME_SIZE), currentImage, 1);
      } 
      else 
      {
        fitness = Fitness.getFitness(getPhenome(Constants.GENOME_SIZE), currentImage, 1);
      }
      reset = false;
      return fitness;
    }
    else
    {
      return fitness;
    }
  }
  
  /****************************************************************************************
   * Makes a change to the given triangle and the given GeneType to the value given
   * @param triangle
   * @param gene
   * @param value
   * @return
   ***************************************************************************************/
  public boolean makeChange(int triangle, GeneType gene, short value)
  {
    reset = true;
    return triangles[triangle].setGene(gene, value);
  }
  
  /*****************************************************************************************
   * Makes a change to the given triangle and the given GeneType according to the delta given
   * @param triangle
   * @param gene
   * @param delta
   * @return
   ****************************************************************************************/
  public boolean changeWithDelta(int triangle, GeneType gene, short delta)
  {
    reset = true;
    Triangle t = triangles[triangle];
    return t.setGene(gene, (short) (t.getGene(gene) + delta));
  }
  
  /*****************************************************************************************
   * hillClimbOnce does one hillclimbing iteration 
   * @param method
   * @return
   ****************************************************************************************/
  public boolean hillClimbOnce(MutationMethod method)
  {
    double fitBefore = getFitness();
    int t = random.nextInt(Constants.GENOME_SIZE);
    GeneType type = GeneType.values()[random.nextInt(10)];
    short delta = 0;
    boolean success = false;
    switch (method)
    {
    case GAUSSIAN:
      delta = (short) (random.nextGaussian() * 25.6);
      success = changeWithDelta(t, type, delta);
      break;
    case RANDOM:
      delta = (short) random.nextInt(type.upper);
      success = makeChange(t, type, delta);
      break;
    case INCREMENT:
      delta = (short) (random.nextInt(2)==0?-20:20);
      success = changeWithDelta(t, type, delta);
      break;
    }
    if (success)
    {
      success = getFitness() < fitBefore;
      
      if (!success)
      {
        changeWithDelta(t,type,(short) -delta);
      }
    }
    MainFrameController.totalmutations++;
    MainFrameController.generationspersec++;
    return success;
  }
  

  public void hillClimbing()
  {
    for (int i=0; i < 1000; i++)
    {
      if(MainFrameController.stop)
      {
        break;
      }
      hillClimbOnce(method);
      if (i%100==0)
      {
        method = MutationMethod.values()[(method.ordinal() + 1)%3];
      }

    }
    method = MutationMethod.values()[(method.ordinal() + 1)%3];
  }
  
  /*****************************************************************************************
   * Preforms the cross over single point crossover. A son and daughter genome are added to 
   * the back of the population.
   * 
   * @param father Genome
   * @param son Genome
   * @param daughter Genome
   ****************************************************************************************/
  public void mateWith(Genome father, Genome son, Genome daughter)
  {
    int crossover = (int) ((random.nextGaussian() * 15.0) + 100.0);
    while (crossover < 0 || crossover >= Constants.GENOME_SIZE)
    {
      crossover = (int) ((random.nextGaussian() * 15.0) + 100.0);
    }
    for (int i = 0; i < crossover; i++)
    {
      son.triangles[i] = this.triangles[i];
      daughter.triangles[i] = father.triangles[i];
    }
    for (int i = crossover; i < Constants.GENOME_SIZE; i++)
    {
      son.triangles[i] = father.triangles[i];
      daughter.triangles[i] = this.triangles[i];
    }
  }
  
  /****************************************************************************************************
   * The MutationMethod method enums GAUSSIAN: a Gaussian distributed change, 
   * RANDOM: a randomly distributed change, INCREMENT: a incremental change
   *****************************************************************************************************/
  private static enum MutationMethod
  {
    GAUSSIAN, RANDOM, INCREMENT
  }
  
  /*
   * Main for testing only
   * @param args
   */
  @SuppressWarnings("serial")
  public static void main(String[] args)
  {
    BufferedImage monaLisa = LoadPictures.bImage1;
    int width = monaLisa.getWidth();
    int height = monaLisa.getHeight();
    final Genome genome = new Genome(monaLisa);
    JFrame frame = new JFrame();
    JPanel panel = new JPanel()
    {
      public void paintComponent(Graphics g)
      {
        genome.drawToGraphics(g,Constants.GENOME_SIZE);
      }
    };
    frame.add(panel);
    frame.setSize(new Dimension(width + 50, height + 50));
    frame.setVisible(true);
    for (;true;)
    {
      genome.hillClimbing();
      panel.repaint();
    }
  }
}
