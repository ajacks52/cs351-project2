//package genome.logic;
//
//import genome.Constants;
//import genome.guicode.MainFrameController;
//import genome.types.Genome;
//
///*********************************************************************************
// * 
// * @author Adam Mitchell
// *
// * Class to do three types of cross over single point, double point, and uniform 
// * cross over.
// ********************************************************************************/
//public class GeneticAlg
//{
//  /*********************************************************************************
//   * Does the single Point CrossOver from the given crossover point 
//   * @param parent1
//   * @param parent2
//   * @param child1
//   * @param child2
//   * @param crossover
//   *********************************************************************************/
//  public void singlePointCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2, int crossover)
//  {
//   
//    for (int i = 0; i < crossover; i++)
//    {
//      child1.triangles[i] = parent1.triangles[i];
//      child2.triangles[i] = parent2.triangles[i];
//    }
//    for (int i = crossover; i < Constants.GENOME_SIZE; i++)
//    {
//      child1.triangles[i] = parent2.triangles[i];
//      child2.triangles[i] = parent1.triangles[i];
//    }
//    synchronized ( MainFrameController.threads)
//    {
//      MainFrameController.totalcrossovers+=2;
//      MainFrameController.generationspersec+=2;
//    }
//  }
//
//  /*********************************************************************************
//   * Does the double Point CrossOver from the given crossover points  
//   * @param parent1
//   * @param parent2
//   * @param child1
//   * @param child2
//   * @param cross1
//   * @param cross2
//   *********************************************************************************/
//  public void doublePointCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2, int cross1, int cross2)
//  {
//
//    for (int i = 0; i < cross1; i++)
//    {
//      child1.triangles[i] = parent1.triangles[i];
//      child2.triangles[i] = parent2.triangles[i];
//    }
//    for (int i = cross1; i < cross2; i++)
//    {
//      child1.triangles[i] = parent2.triangles[i];
//      child2.triangles[i] = parent1.triangles[i];
//    }
//    for (int i = cross2; i < Constants.GENOME_SIZE; i++)
//    {
//      child1.triangles[i] = parent2.triangles[i];
//      child2.triangles[i] = parent1.triangles[i];
//    }
//
//  }
//
//  /**********************************************************************************
//   * Preforms uniform CrossOver on the two given genomes 
//   * @param parent1
//   * @param parent2
//   * @param child1
//   * @param child2
//   **********************************************************************************/
//  public void uniformCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2)
//  {
//    int n = 0;
//    int stop = 2;
//
//    while (n < Constants.GENOME_SIZE)
//    {
//      for (; n < stop; n++)
//      {
//        child1.triangles[n] = parent2.triangles[n];
//        child2.triangles[n] = parent1.triangles[n];
//      }
//      stop += 2;
//      for (; n < stop; n++)
//      {
//        child1.triangles[n] = parent2.triangles[n];
//        child2.triangles[n] = parent1.triangles[n];
//      }
//    }
//  }
//}
