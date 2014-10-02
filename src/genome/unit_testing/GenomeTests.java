package genome.unit_testing;


import genome.Constants;
import genome.types.Genome;
import genome.types.Triangle;

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
    Triangle t[] = g1.getTriangles();
    Dimension dim = g1.getDimension();

    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {

      if (t[i].getGreen() > 255 || t[i].getGreen() < 0)
      {
        System.err.println("Error: green out of range");
        return false;
      }
      if (t[i].getRed() > 255 || t[i].getRed() < 0)
      {
        System.err.println("Error: red out of range");
        return false;
      }
      if (t[i].getBlue() > 255 || t[i].getBlue() < 0)
      {
        System.err.println("Error: blue out of range");
        return false;
      }
      if (t[i].getAlpha() > 255 || t[i].getAlpha() < 0)
      {
        System.err.println("Error: alpha out of range");
        return false;
      }
      if (t[i].getPoint1().x > dim.width || t[i].getPoint1().y > dim.height)
      {
        System.err.println("Error: Point1 out of range");
        return false;
      }
      if (t[i].getPoint2().x > dim.width || t[i].getPoint2().y > dim.height)
      {
        System.err.println("Error: Point2 out of range");
        return false;
      }
      if (t[i].getPoint3().x > dim.width || t[i].getPoint3().y > dim.height)
      {
        System.err.println("Error: Point3 out of range");
        return false;
      }
    }

    return true;
  }

  /********************************************************************************
   * 
   * You just have set of unit tests that calls a method accepting two input genomes. The tests must pass the method
   * preconstructed gemomes with known Hamming distance and pass if and only if the method returns the correct Hamming
   * distance between the genomes (see http://en.wikipedia.org/wiki/Hamming_distance). A good test set should include
   * tests for genomes with different addresses yet identical genes, a pair with 1 difference, 2 adjacent difference and
   * many scattered differences. Note: for the purpose of this lab, it is not necessary that your method actually works
   * (calculates the correct hamming distance) just that the tests pass if and only if the method works.
   * 
   * @param g2
   * @param g1
   * 
   * @return
   ********************************************************************************/
  public static int HammingDistance(Genome g1, Genome g2)
  {
    Triangle t1[] = g1.getTriangles();
    Triangle t2[] = g2.getTriangles();

    int hammingDist = 0;
    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {
      if (t1[i].getGreen() != t2[i].getGreen())
      {
        hammingDist++;
      }
      if (t1[i].getRed() != t2[i].getRed())
      {
        hammingDist++;
      }
      if (t1[i].getBlue() != t2[i].getBlue())
      {
        hammingDist++;
      }
      if (t1[i].getAlpha() != t2[i].getAlpha())
      {
        hammingDist++;
      }
      if (t1[i].getPoint1().x != t2[i].getPoint1().x)
      {
        hammingDist++;
      }
      if (t1[i].getPoint2().x != t2[i].getPoint2().x)
      {
        hammingDist++;
      }
      if (t1[i].getPoint3().x != t2[i].getPoint3().x)
      {
        hammingDist++;
      }
      if (t1[i].getPoint1().y != t2[i].getPoint1().y)
      {
        hammingDist++;
      }
      if (t1[i].getPoint2().y != t2[i].getPoint2().y)
      {
        hammingDist++;
      }
      if (t1[i].getPoint3().y != t2[i].getPoint3().y)
      {
        hammingDist++;
      }

    }
    return hammingDist;
  }

  /********************************************************************************
   * 
   * You just have set of unit tests that call a method (or dummy method) accepting four input genomes: two parents and
   * two children along with a crossover point. The crossover point may be specified as a gene number or percentage or
   * Hamming distance or however makes most sense in your project design. The test set must test the following cases
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
   * 
   ***********************************************************************************/
  private static void TestSinglePointCrossOver(Genome parent1, Genome parent2, Genome child1, Genome child2,
      int crossover)
  {
    for (int i = 0; i < crossover; i++)
    {
      child1.getTriangles()[i] = parent1.getTriangles()[i];
      child2.getTriangles()[i] = parent2.getTriangles()[i];
    }
    for (int i = crossover; i < Constants.GENOME_SIZE; i++)
    {
      child1.getTriangles()[i] = parent2.getTriangles()[i];
      child2.getTriangles()[i] = parent1.getTriangles()[i];
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
