package genome.types;

import genome.Constants;
import genome.guicode.LoadPictures;
import genome.guicode.MainFrameController;
import genome.logic.GeneticAlg;
import genome.logic.PictureResize;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;

public class Tribe extends Thread
{
  public CopyOnWriteArrayList<Genome> genomes;
  public static final int TRIBE_SIZE = 24;
  public int width;
  public int height;
  public BufferedImage bImage;
  private GeneticAlg genAlg = new GeneticAlg();
  public boolean doneSorting = false;
  private volatile boolean running = true; // Run unless told to pause

  public Tribe(String name, int width, int height, BufferedImage bImage, ArrayList<Integer> colorList)
  {
    super(name);

    this.width = width;
    this.height = height;
    this.bImage = bImage;

    genomes = new CopyOnWriteArrayList<Genome>();
    /* copy on write arraylists don't have ConcurrentModificationException when using an Iterators */
    for (int i = 0; i < TRIBE_SIZE; i++)
    {
      // genomes.add(Genome.randomGenome(width, height));
      Genome g = new Genome(Triangle.randomGenome(200, width, height, colorList), width, height);
      g.resizedPhenotype = PictureResize.resize(bImage, Constants.RESIZED_PICTURE_SIZE, Constants.RESIZED_PICTURE_SIZE);
      genomes.add(g);
    }

  }

  private void sortGenomes()
  {
    doneSorting = true;
    System.out.println("slow sort started");
    Collections.sort(genomes, new Comparator<Genome>() {
      @Override
      public int compare(Genome o1, Genome o2)
      {
        long f1 = o1.getFitness(bImage, 5);
        long f2 = o2.getFitness(bImage, 5);
        return (int) (f1 - f2);
      }
    });
    System.out.println("slow sort ended");
    doneSorting = false;
  }

  public void quickSortGenomes()
  {
    synchronized (this)
    {
    Collections.sort(genomes, new Comparator<Genome>() {
      @Override
      public int compare(Genome o1, Genome o2)
      {
        long f1 = o1.getFitness(bImage, 5);
        long f2 = o2.getFitness(bImage, 5);
        return (int) (f1 - f2);
      }
    });
    }
  }

  private void breedGenomes()
  {
    int div4 = TRIBE_SIZE / 4;
    List<Genome> parents = genomes.subList(0, div4 * 2);
    Collections.shuffle(parents);
    for (int i = 0; i < div4; i++)
    {
      genAlg.singlePointCrossOver(parents.get(i), parents.get(i + div4), genomes.get(i + div4 * 2),
          genomes.get(i + div4 * 3), Constants.random.nextInt(200));
      synchronized (this)
      {
        MainFrameController.totalcrossovers++;
      }
    }
    sortGenomes();
  }

  private void mutateAll()
  {

    for (Genome g : genomes)
    {
        for (int i = 0; i < 200; i++)
        {
          g.oneChange(i);
        }
        System.out.println("picking new genome");
      }
      System.out.println("picking new genome");

  }

  public void run()
  {
    System.out.println("running tribe: "+ getName());
    for (int step = 0; !this.isInterrupted(); step++)
    {
      long start = System.currentTimeMillis();
      System.out.println("Sorting genomes: "+step);
      sortGenomes();
      yield();
      System.out.println("Breeding genomes");
      breedGenomes();
      // breed best half of best half with worst half of best half

      System.out.println("best fit " + genomes.indexOf(0));
      System.out.println("Mutating genomes");

      mutateAll();

      // mutate all multiple times
    }
  }

  public static void main(String[] args)
  {
    // System.out.println("Starting");
    // BufferedImage bImage = LoadPictures.bImage1;
    //
    // new Tribe("Tribe 1", bImage.getWidth(), bImage.getHeight(),bImage).start();
  }
}
