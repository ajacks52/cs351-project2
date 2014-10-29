package genome.types;

import genome.Constants;
import genome.guicode.LoadPictures;
import genome.guicode.MainFrameController;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/*****************************************************************************************************
 * The tribe object. Holds and created the genomes, gets be paused when mainframe is paused
 * 
 * @author Jordan Medlock
 *
 *****************************************************************************************************/
public class Tribe extends Thread
{
  public Genome[] genomes = new Genome[Constants.TRIBE_SIZE];
  public static BufferedImage currentImage;

  private static volatile boolean isPaused = true;

  private static volatile boolean next = false;

  public boolean fullyPaused = false;

  private int numGenomes = 0;

  /*****************************************************************************************************
   * Tribe constructor takes a name and a buffered image to makes new genomes from
   * 
   * @param name
   * @param currentImage
   *****************************************************************************************************/
  @SuppressWarnings("static-access")
  public Tribe(BufferedImage currentImage)
  {
    this.currentImage = currentImage;
    for (int i = 0; i < Constants.TRIBE_SIZE; i++)
    {
      genomes[i] = new Genome(currentImage);
    }
  }

  /*****************************************************************************************************
   * Tribe constructor takes a name and a buffered image to makes new genomes from
   * 
   * @param name
   * @param currentImage
   * @param list
   *****************************************************************************************************/
  @SuppressWarnings("static-access")
  public Tribe(String name, BufferedImage currentImage, ArrayList<Genome> list)
  {
    super(name);

    this.currentImage = currentImage;
    numGenomes += list.size();
    genomes = (Genome[]) list.toArray();
  }

  public void updateTribesMembers(BufferedImage newImage)
  {
    for (int i = 0; i < Constants.TRIBE_SIZE; i++)
    {
      for(int j = 0; j < Constants.GENOME_SIZE; j++)
      {
        genomes[i].triangles[j].width  = newImage.getWidth();
        genomes[i].triangles[j].height = newImage.getHeight();
      }
     
    }

  }

  /*****************************************************************************************************
   * Tribe constructor takes a name and a buffered image to makes new genomes from
   * 
   * @param name
   * @param currentImage
   * @param list
   *****************************************************************************************************/
  public void addGenome(Genome g)
  {
    if (genomes.length > numGenomes)
    {
      genomes[numGenomes++] = g;
    }
    else
    {
      genomes = Arrays.copyOf(genomes, genomes.length * 2);
      genomes[numGenomes++] = g;
    }
  }

  /******************************************************************************************************
   * Sort the genomes in the tribe lowest fitness to highest
   ******************************************************************************************************/
  public void sortGenomes()
  {
    Arrays.sort(genomes, new Comparator<Genome>() {
      @Override
      public int compare(Genome o1, Genome o2)
      {
        double f1 = o1.getFitness();
        double f2 = o2.getFitness();
        return (int) (f1 - f2);
      }
    });
  }

  /******************************************************************************************************
   * Breads a subset of genomes into the back of the tribes population
   ******************************************************************************************************/
  public void breedGenomes()
  {
    int div4 = Constants.TRIBE_SIZE / 4;
    for (int i = 0; i < div4; i++)
    {
      Genome[] subset = new Genome[] { genomes[i], genomes[i + div4], genomes[i + 2 * div4], genomes[i + 3 * div4] };
      subset[0].hillClimbing();
      subset[1].hillClimbing();

      subset[0].mateWith(subset[1], subset[2], subset[3]);
      MainFrameController.totalcrossovers++;
      MainFrameController.generationspersec++;
    }
  }

  public void run()
  {
    System.out.println("Running tribe: " + getName());
    for (; true;)
    {
      fullyPaused = false;
      if (next)
      {
        System.out.println("next");
        step();
        fullyPaused = true;
        next = false;
      }
      if (isPaused)
        continue;
      step();
      if (isPaused)
        fullyPaused = true;
    }
  }

  /******************************************************************************************************
   * public void step()
   ******************************************************************************************************/
  public void step()
  {
    System.out.println("step");
    genomes[0].hillClimbing();
//    sortGenomes();
//    breedGenomes();
  }

  /******************************************************************************************************
   * For testing only
   * 
   * @param args
   ******************************************************************************************************/
  @SuppressWarnings("serial")
  public static void main(String args[])
  {
    BufferedImage monaLisa = LoadPictures.bImage1;
    int width = monaLisa.getWidth();
    int height = monaLisa.getHeight();
    final Tribe tribe = new Tribe(monaLisa);
    System.out.println(tribe.genomes[0].getFitness());
    JFrame frame = new JFrame();
    final JPanel panel = new JPanel() {
      public void paintComponent(Graphics g)
      {
        tribe.genomes[0].drawToGraphics(g, Constants.GENOME_SIZE);
      }
    };
    tribe.start();
    frame.add(panel);
    frame.setSize(new Dimension(width + 50, height + 50));
    frame.setVisible(true);

    @SuppressWarnings("unused")
    Timer t = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        panel.repaint();
      }
    });
  }

  /******************************************************************************************************
   * public static void pause()
   ******************************************************************************************************/
  public static void pause()
  {
    isPaused = true;
  }

  /******************************************************************************************************
   * public static void unpause()
   ******************************************************************************************************/
  public static void unpause()
  {
    isPaused = false;
  }

  /******************************************************************************************************
   * public static void next()
   *****************************************************************************************************/
  public static void next()
  {
    next = true;
  }
}
