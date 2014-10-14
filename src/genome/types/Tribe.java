package genome.types;

import genome.Constants;
import genome.guicode.LoadPictures;
import genome.logic.Fitness;
import genome.logic.GeneticAlg;
import genome.logic.HillClimbing;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

public class Tribe extends Thread
{
  public ArrayList<Genome> genomes;
  public static final int TRIBE_SIZE = 8;
  public int width;
  public int height;
  public BufferedImage bImage;
  private GeneticAlg genAlg = new GeneticAlg();
  
  
  public Tribe(String name, int width, int height, BufferedImage bImage)
  {
    super(name);
    
    this.width = width;
    this.height = height;
    this.bImage = bImage;
    
    genomes = new ArrayList<Genome>(TRIBE_SIZE);
    for (int i=0; i < TRIBE_SIZE; i++)
    {
      genomes.add(Genome.randomGenome(width, height));
    }
  }
  
  private void sortGenomes()
  {
    Collections.sort(genomes, new Comparator<Genome>(){
      @Override
      public int compare(Genome o1, Genome o2)
      {
        long f1 = Fitness.getFitness(bImage, o1, 5);
        long f2 = Fitness.getFitness(bImage, o2, 5);
        return (int) (f1 - f2);
      }
    });
  }
  
  private void breedGenomes()
  {
    List<Genome> parents = genomes.subList(0, 4);
    Collections.shuffle(parents);
    int div4 = TRIBE_SIZE/4;
    for (int i=0; i < div4; i++)
    {
      genAlg.singlePointCrossOver(parents.get(i), parents.get(i+div4), genomes.get(i+div4*2), genomes.get(i+div4*3), Constants.random.nextInt(200));
    }
    sortGenomes();
  }
  
  private void mutateAll()
  {
    HillClimbing hc = new HillClimbing(new Point(bImage.getWidth(), bImage.getHeight()));
    for (Genome g : genomes)
    {
      for (int i=0; i < 200; i++)
      {
        hc.oneChange(g,i);
      }
    }
  }
  
  public void run()
  {
    for (int step=0; !this.isInterrupted(); step++)
    {
      long start = System.currentTimeMillis();
      System.out.println(step);
      sortGenomes();
      yield();
      System.out.println("Breeding genomes");
      breedGenomes();
      // breed best half of best half with worst half of best half
      
      System.out.println("Mutating genomes");
      mutateAll();
      // mutate all multiple times
    }
  }
  public static void main(String[] args)
  {
    System.out.println("Starting");
    BufferedImage bImage = LoadPictures.bImage1;
    
    new Tribe("Tribe 1", bImage.getWidth(), bImage.getHeight(),bImage).start();
  }
}
