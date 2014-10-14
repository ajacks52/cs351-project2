package genome.unit_testing;


import genome.types.Genome;
import genome.types.Triangle;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Adam Mitchell
 * 
 * Controller for the Assert Tests tests ValidGenome, HammingDistance, SinglePointCrossover, ValidInput,
 * CorrectCrossover, ValidOutput
 *
 */
public class AssertTests
{

  /************************************************************************************
   * Creates some fake triangles lists and adds them to a genome then does the testing on these fake genomes 
   * @param args
   ************************************************************************************/
  public static void main(String[] args)
  {

    List<Triangle> triangles1 = new ArrayList<Triangle>(200);
    List<Triangle> triangles2 = new ArrayList<Triangle>(200);


    // Initialize the triangles
    for (int i = 0; i < 200; i++)
    {
//      triangles1.add(Triangle.randomTriangleIn(300, 300));
//      triangles2.add(Triangle.randomTriangleIn(200, 200));     
    }

    Genome testGenomeP1 = new Genome(triangles1, 300, 300);
    Genome testGenomeP2 = new Genome(triangles2, 200, 200);
    Genome testGenomeP5 = testGenomeP1;
    
    Genome testGenomeC1 = new Genome(triangles1, 300, 300);
    Genome testGenomeC2 = new Genome(triangles2, 200, 200);

    
    testGenomeC1.getTriangles()[1] = new Triangle(1,2,3,4,5,6,7);
    
    assert GenomeTests.ValidGenome(testGenomeP1);
    assert GenomeTests.ValidGenome(testGenomeP1);
    assert GenomeTests.ValidGenome(testGenomeP1);

    //System.out.println("ham dist " + GenomeTests.HammingDistance(testGenomeP1, testGenomeP2));

    assert (GenomeTests.HammingDistance(testGenomeP1, testGenomeP1) == 0);/* the same */
    assert GenomeTests.HammingDistance(testGenomeP1, testGenomeP5) == 0; /* different addresses yet identical */
    assert GenomeTests.HammingDistance(testGenomeP1, testGenomeP2) > 1000;/* many scattered differences */

    /*
     * of the form: 
     * SinglePointCrossover(Genome parent1, Genome parent2, Genome child1, Genome child2, int crossover)
     */
    assert GenomeTests.SinglePointCrossover(testGenomeP1, testGenomeP2, testGenomeC1, testGenomeC2, 40);
    assert GenomeTests.SinglePointCrossover(testGenomeP1, testGenomeP2, testGenomeC1, testGenomeC2, 55);
    assert GenomeTests.SinglePointCrossover(testGenomeP1, testGenomeP2, testGenomeC1, testGenomeC2, 7);
    assert GenomeTests.SinglePointCrossover(testGenomeP1, testGenomeP2, testGenomeC1, testGenomeC2, 100);
    assert GenomeTests.SinglePointCrossover(testGenomeP1, testGenomeP2, testGenomeC1, testGenomeC2, 160);
    assert GenomeTests.SinglePointCrossover(testGenomeP1, testGenomeP2, testGenomeC1, testGenomeC2, 77);

    System.out.println("Finished no assert errors!");
  }

}
