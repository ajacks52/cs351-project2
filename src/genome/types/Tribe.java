package genome.types;

import genome.Constants;
import genome.logic.Fitness;
import genome.logic.GeneticAlg;
import genome.logic.HillClimbing;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tribe extends Thread
{
  public ArrayList<Genome> genomes;
  public static final int TRIBE_SIZE = 1000;
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
        long f1 = Fitness.getFitness(bImage, o1.getImage(200));
        long f2 = Fitness.getFitness(bImage, o2.getImage(200));
        return (int) (f2 - f1);
      }
    });
  }
  
  private void breedGenomes()
  {
    int div4 = TRIBE_SIZE/4;
    for (int i=0; i < div4; i++)
    {
      genAlg.singlePointCrossOver(genomes.get(i), genomes.get(i+div4), genomes.get(i+div4*2), genomes.get(i+div4*3), Constants.random.nextInt(200));
    }
  }
  
  private void mutateAll()
  {
    HillClimbing hc = new HillClimbing(new Point(bImage.getWidth(), bImage.getHeight()));
    for (Genome g : genomes)
    {
      
    }
  }
  
  public void run()
  {
    for (int step=0; this.isInterrupted(); step++)
    {
      sortGenomes();
      
      breedGenomes();
      // breed best half of best half with worst half of best half
      
      // mutate all multiple times
    }
  }
}
