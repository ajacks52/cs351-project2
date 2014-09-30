package genome.types;

import java.awt.Graphics;
import java.awt.Polygon;

import genome.Constants;

public class Genome
{
  private Triangle[] triangles = new Triangle[Constants.GENOME_SIZE];
  public void drawToCanvas(Graphics g, int count)
  {
    int i = 0;
    for (Triangle t : triangles)
    {
      if (i++ >= count) break;
      if (t == null) continue;
      g.setColor(t.getColor());
      Polygon p = t.getPolygon();
      g.fillPolygon(p);
    }
  }
  
  
  
  public Genome copy()
  {
    Genome cp = new Genome();
    for (int i=0; i < Constants.GENOME_SIZE; i++)
    {
      cp.triangles[i] = triangles[i].copy();
    }
    return cp;
  }
}
