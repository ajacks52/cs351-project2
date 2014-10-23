package genome.logic;

import genome.Constants;
import genome.guicode.MainFrameController;
import genome.types.Genome;

public class GeneticAlg
{

  public GeneticAlg()
  {
  }

  /*********************************************************************************
   * singlePointCrossOver
   * @param parent1
   * @param parent2
   * @param child1
   * @param child2
   * @param crossover
   *********************************************************************************/
  public void singlePointCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2, int crossover)
  {
   
    for (int i = 0; i < crossover; i++)
    {
      child1.triangles[i] = parent1.triangles[i];
      child2.triangles[i] = parent2.triangles[i];
    }
    for (int i = crossover; i < Constants.GENOME_SIZE; i++)
    {
      child1.triangles[i] = parent2.triangles[i];
      child2.triangles[i] = parent1.triangles[i];
    }
    synchronized ( MainFrameController.threads)
    {
      MainFrameController.totalcrossovers+=2;
      MainFrameController.generationspersec+=2;
    }
  }

  /*********************************************************************************
   * doublePointCrossOver
   * @param parent1
   * @param parent2
   * @param child1
   * @param child2
   * @param cross1
   * @param cross2
   *********************************************************************************/
  public void doublePointCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2, int cross1, int cross2)
  {

    for (int i = 0; i < cross1; i++)
    {
      child1.triangles[i] = parent1.triangles[i];
      child2.triangles[i] = parent2.triangles[i];
    }
    for (int i = cross1; i < cross2; i++)
    {
      child1.triangles[i] = parent2.triangles[i];
      child2.triangles[i] = parent1.triangles[i];
    }
    for (int i = cross2; i < Constants.GENOME_SIZE; i++)
    {
      child1.triangles[i] = parent2.triangles[i];
      child2.triangles[i] = parent1.triangles[i];
    }

  }

  /**********************************************************************************
   * uniformCrossOver
   * @param parent1
   * @param parent2
   * @param child1
   * @param child2
   **********************************************************************************/
  public void uniformCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2)
  {
    int n = 0;
    int stop = 2;

    while (n < Constants.GENOME_SIZE)
    {
      for (; n < stop; n++)
      {
        child1.triangles[n] = parent2.triangles[n];
        child2.triangles[n] = parent1.triangles[n];
      }
      stop += 2;
      for (; n < stop; n++)
      {
        child1.triangles[n] = parent2.triangles[n];
        child2.triangles[n] = parent1.triangles[n];
      }
    }
  }
}
