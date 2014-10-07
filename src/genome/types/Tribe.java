package genome.types;

import java.util.ArrayList;

public class Tribe extends Thread
{
  public ArrayList<Genome> genomes;
  public static final int TRIBE_SIZE = 1000;
  public int width;
  public int height;
  
  public Tribe(String name, int width, int height)
  {
    super(name);
    
    this.width = width;
    this.height = height;
    
    genomes = new ArrayList<Genome>(TRIBE_SIZE);
    for (int i=0; i < TRIBE_SIZE; i++)
    {
      genomes.add(Genome.randomGenome(width, height));
    }
  }
  
  public void run()
  {
    // some awful stuff
  }
}
