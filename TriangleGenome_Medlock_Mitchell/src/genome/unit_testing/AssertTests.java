package genome.unit_testing;

import genome.guicode.LoadPictures;
import genome.guicode.WriteXMLFile;
import genome.types.Fitness;
import genome.types.Genome;

import java.awt.image.BufferedImage;

/**
 * @author Adam Mitchell
 * 
 * Call the static methods that can be tested if they fail 
 * we'll know what we need to fix. The non static method we are testing
 * we just call a fake method in the Genome tests to be sure the fake one 
 * is working.
 *
 */
public class AssertTests
{
  public AssertTests()
  {
    BufferedImage a = LoadPictures.bImage1;
    BufferedImage b = LoadPictures.bImage2;
    Genome g1 = new Genome(a);
    Genome g2 = new Genome(b);
    Genome g3 = new Genome(a);
    Genome g4 = new Genome(a);
    
    crossOverTests( g1,  g2,  g3,  g4, 5);
    hammingDistanceTests( g1,  g2);
    validGenomeTests( g1,  g2,  g3);
    validGenomeTests( g1,  g2,  g3);
    fitnessTests( a,  b,  1);
    fitnessTests( a,  a,  2);
    fitnessTests( b,  b,  5);
  }
  
  
  public void xmlTests(Genome g)
  {
    assert (new WriteXMLFile().generate(g));
  }
  
  public void fitnessTests(BufferedImage a, BufferedImage b, int scaledBy)
  {
    if(a.equals(b))
    {
      assert (Fitness.getFitnessRaster( a, b, scaledBy) == Fitness.getFitness( a,  b,  scaledBy));
    }
    else
    {
      assert (Fitness.getFitnessRaster( a, b, scaledBy) != Fitness.getFitness( a,  b,  scaledBy));
    }
  }
  
  public void crossOverTests(Genome g1, Genome g2, Genome g3, Genome g4, int cr)
  {
    assert GenomeTests.SinglePointCrossover(g1, g2, g3, g4, cr);
    assert GenomeTests.SinglePointCrossover(g1, g2, g3, g4, cr);
    assert GenomeTests.SinglePointCrossover(g1, g2, g3, g4, cr);
    assert GenomeTests.SinglePointCrossover(g1, g2, g3, g4, cr);
    assert GenomeTests.SinglePointCrossover(g1, g2, g3, g4, cr);
    assert GenomeTests.SinglePointCrossover(g1, g2, g3, g4, cr);
  }
  
  public void hammingDistanceTests(Genome g1, Genome g2)
  {
    assert GenomeTests.HammingDistance(g1, g2) > 1000;
    assert (GenomeTests.HammingDistance(g1, g1) == 0);
    assert GenomeTests.HammingDistance(g2, g2) == 0; 
  }
  
  public void validGenomeTests(Genome g1, Genome g2, Genome g3)
  {
    assert GenomeTests.ValidGenome(g1);
    assert GenomeTests.ValidGenome(g2);
    assert GenomeTests.ValidGenome(g3);
  }
  
}
