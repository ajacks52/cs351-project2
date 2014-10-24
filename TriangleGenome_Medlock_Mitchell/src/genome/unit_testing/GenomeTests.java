package genome.unit_testing;

import genome.Constants;
import genome.guicode.MainFrameController;
import genome.types.Genome;
import genome.types.Triangle;
import genome.types.Triangle.GeneType;

import java.awt.Dimension;

/**********************************************************************************
 * @author Adam Mitchell
 * 
 *********************************************************************************/
public class GenomeTests
{

  /********************************************************************************
   * 
   * You must have a set of unit tests (using Java’s assert Keyword) that verify that an input triangle genome is valid
   * under the Triangle Genome Project specs (all values of all 2000 genes are in range).
   * 
   * @param g1
   * 
   * @return
   ********************************************************************************/
  public static boolean ValidGenome(Genome g1)
  {
    Triangle t[] = g1.triangles;
    Dimension dim = new Dimension(MainFrameController.getCurrentPict().getWidth(), 
        MainFrameController.getCurrentPict().getHeight());

    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {

      if (t[i].getGene(GeneType.GREEN) > 255 || t[i].getGene(GeneType.GREEN) < 0)
      {
        System.err.println("Error: green out of range");
        return false;
      }
      if (t[i].getGene(GeneType.RED) > 255 || t[i].getGene(GeneType.RED) < 0)
      {
        System.err.println("Error: red out of range");
        return false;
      }
      if (t[i].getGene(GeneType.BLUE) > 255 || t[i].getGene(GeneType.BLUE) < 0)
      {
        System.err.println("Error: blue out of range");
        return false;
      }
      if (t[i].getGene(GeneType.ALPHA) > 255 || t[i].getGene(GeneType.ALPHA) < 0)
      {
        System.err.println("Error: alpha out of range");
        return false;
      }
      if (t[i].getGene(GeneType.X1) > dim.width || t[i].getGene(GeneType.Y1) > dim.height
          || t[i].getGene(GeneType.X1) < 0 || t[i].getGene(GeneType.Y1) < 0)
      {
        System.err.println("Error: Point1 out of range");
        return false;
      }
      if (t[i].getGene(GeneType.X2) > dim.width || t[i].getGene(GeneType.Y2) > dim.height
          || t[i].getGene(GeneType.X2) < 0 || t[i].getGene(GeneType.Y2) < 0)
      {
        System.err.println("Error: Point2 out of range");
        return false;
      }
      if (t[i].getGene(GeneType.X3) > dim.width || t[i].getGene(GeneType.Y3) > dim.height
          || t[i].getGene(GeneType.X3) < 0 || t[i].getGene(GeneType.Y3) < 0)
      {
        System.err.println("Error: Point3 out of range");
        return false;
      }
    }

    return true;
  }

  /********************************************************************************
   * 
   * 
   * @param g2
   * @param g1
   * 
   * @return
   ********************************************************************************/
  public static int HammingDistance(Genome g1, Genome g2)
  {
    Triangle t1[] = g1.triangles;
    Triangle t2[] = g2.triangles;

    int hammingDist = 0;
    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {
      if (t1[i].getGene(GeneType.GREEN) != t2[i].getGene(GeneType.GREEN))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.RED) != t2[i].getGene(GeneType.RED))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.BLUE) != t2[i].getGene(GeneType.BLUE))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.ALPHA) != t2[i].getGene(GeneType.ALPHA))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.X1) != t2[i].getGene(GeneType.X1))

      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.Y1) != t2[i].getGene(GeneType.Y1))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.X2) != t2[i].getGene(GeneType.X2))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.Y2) != t2[i].getGene(GeneType.Y2))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.X3) != t2[i].getGene(GeneType.X3))
      {
        hammingDist++;
      }
      if (t1[i].getGene(GeneType.Y3) != t2[i].getGene(GeneType.Y3))
      {
        hammingDist++;
      }
    }
    return hammingDist;
  }

  /********************************************************************************
   * 
   * 
   * @param parent1
   * @param parent2
   * @param child1
   * @param child2
   * @param crossover
   * 
   * @return boolean
   *********************************************************************************/
  public static boolean SinglePointCrossover(Genome parent1, Genome parent2, Genome child1, Genome child2, int crossover)
  {
    boolean asserted = true;
    asserted = ValidInput(parent1, parent2, child1, child2, crossover);
    if (!asserted)
    {
      System.err.println("Error: ValidInput returned false");
      return asserted;
    }

    TestSinglePointCrossOver(parent1, parent2, child1, child2, crossover);

    asserted = CorrectCrossover(child1, child2);
    if (!asserted)
    {
      System.err.println("Error: CorrectCrossover returned false");
      return asserted;
    }
    asserted = ValidOutput(parent1, parent2, child1, child2);
    if (!asserted)
    {
      System.err.println("Error: ValidOutput returned false");
      return asserted;
    }

    return asserted;
  }

  /***********************************************************************************
   * Does a naive version of singlepoint crossover 
   ***********************************************************************************/
  private static void TestSinglePointCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2,
      int crossover)
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
  }

  /*********************************************************************************
   * 
   * The pair of parents must not be identical, none of the inputs must be null, all inputs must have different
   * addresses, the crossover point must be valid with respect to the inputs.
   * 
   * @param d
   * @param g4
   * @param g3
   * @param g2
   * @param g1
   * 
   * @return
   *********************************************************************************/
  public static boolean ValidInput(Genome parent1, Genome parent2, Genome child1, Genome child2, int number)
  {
    if (parent1 == null || parent2 == null)
    {
      System.err.println("Error: either parent was null");
      return false;
    }
    if (parent1.equals(parent2))
    {
      System.err.println("Error: parent1 equals parent2");
      return false;
    }
    if (parent1 == parent2)
    {
      System.err.println("Error: parent1 == parent2");
      return false;
    }
    if (number < 0 || number > Constants.GENOME_SIZE)
    {
      System.err.println("Error: cross over number is in wrong format");
      return false;
    }

    return true;
  }

  /**********************************************************************************
   * 
   * Given 6 cases of different preconstructed genomes and crossover points, the test must return true if and only if
   * the two children are the correct crossovers.
   * 
   * For now this is a dummy method...
   * 
   * @param child1
   * @param child2
   * 
   * @return
   *********************************************************************************/
  public static boolean CorrectCrossover(Genome child1, Genome child2)
  {

    return true;
  }

  /**********************************************************************************
   * 
   * Test that returns true if and only if the address of the input children is the same as the address of the output
   * children, that the genes of the two children are not identical to each other and not identical to either parent.
   * 
   * 
   * @param parent1
   * @param parent2
   * @param child1
   * @param child2
   * 
   * @return
   *********************************************************************************/
  public static boolean ValidOutput(Genome parent1, Genome parent2, Genome child1, Genome child2)
  {

    if (parent1.equals(child1) || parent1.equals(child2))
    {
      System.err.println("Error: parent 1 equals a child");
      return false;
    }

    if (parent2.equals(child1) || parent2.equals(child2))
    {
      System.err.println("Error: parent 2 equals a child");
      return false;
    }
    if (child1.equals(child2))
    {
      System.err.println("Error: child 1 equals child 2");
      return false;
    }
    return true;
  }

}
