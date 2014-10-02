package genome.types;

import genome.Constants;

import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

/************************************************************************************
 * @author Jordan Medlock 
 * @author Adam Mitchell
 ************************************************************************************/
public class Genome
{
  private Triangle[] triangles = new Triangle[Constants.GENOME_SIZE];
  private Dimension dimension = new Dimension();
  private int fitness;

  /************************************************************************************
   * One of two constructors takes an arraylist of triangles
   * 
   * @param tris
   ************************************************************************************/
  public Genome(List<Triangle> tris, int x, int y)
  {
    dimension.width = x;
    dimension.height = y;

    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {
      triangles[i] = tris.get(i);
    }
  }

  /************************************************************************************
   * another constructor 
   ************************************************************************************/
  public Genome()
  {
  }

  /************************************************************************************
   * 
   * @param g
   * @param count
   ************************************************************************************/
  public void drawToCanvas(Graphics g, int count)
  {
    int i = 0;
    for (Triangle t : triangles)
    {
      if (i++ >= count)
        break;
      if (t == null)
        continue;
      g.setColor(t.getColor());
      Polygon p = t.getPolygon();
      g.fillPolygon(p);
    }
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Triangle[] getTriangles()
  {
    return triangles;
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Dimension getDimension()
  {
    return dimension;
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Genome copy()
  {
    Genome cp = new Genome();
    for (int i = 0; i < Constants.GENOME_SIZE; i++)
    {
      cp.triangles[i] = triangles[i].copy();
    }
    return cp;
  }
}
