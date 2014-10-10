package genome.logic;

import java.awt.Point;

import genome.Constants;
import genome.guicode.PicturePanel;
import genome.types.Genome;
import genome.types.Triangle;

/*******************************************************************
 * 
 * @author Adam
 * 
 * Class to hold the hill climbing methods.
 * 
 * More can be added whenever we see fit.
 *
 *******************************************************************/
public class HillClimbing
{
  private Point size;
  /********************************************************************
   * Constructor 
   *******************************************************************/
  public HillClimbing(Point size)
  {
    this.size = size;
  }
  
  /********************************************************************
   * Changes one of the x coords 
   *******************************************************************/
  public void addX(Triangle t)
  {
    int rand = Constants.random.nextInt(2);
    if(rand == 0 && t.getPoint1().y < size.y) t.getPoint1().x++;
    if(rand == 1 && t.getPoint1().y < size.y) t.getPoint2().x++;
    if(rand == 2 && t.getPoint1().y < size.y) t.getPoint3().x++;
    
  }
  
  /********************************************************************
   * Changes one of the x coords 
   *******************************************************************/
  public void subX(Triangle t)
  {
    int rand = Constants.random.nextInt(2);
    if(rand == 0 && t.getPoint1().y > 0) t.getPoint1().x--;
    if(rand == 1 && t.getPoint2().y > 0) t.getPoint2().x--;
    if(rand == 2 && t.getPoint3().y > 0) t.getPoint3().x--;
    
  }
  
  /********************************************************************
   * Changes one of the y coords 
   *******************************************************************/
  public void addY(Triangle t)
  {
    int rand = Constants.random.nextInt(2);
    if(rand == 0 && t.getPoint1().y < size.y) t.getPoint1().y++;
    if(rand == 1 && t.getPoint2().y < size.y) t.getPoint2().y++;
    if(rand == 2 && t.getPoint3().y < size.y) t.getPoint3().y++;
    
    }
  
  /********************************************************************
   * Changes one of the y coords 
   *******************************************************************/
  public void subY(Triangle t)
  {
    int rand = Constants.random.nextInt(2);
    if(rand == 0 && t.getPoint1().y > 0) t.getPoint1().y--;
    if(rand == 1 && t.getPoint2().y > 0) t.getPoint2().y--;
    if(rand == 2 && t.getPoint3().y > 0) t.getPoint3().y--;
  }
  
  /********************************************************************
   * Increments the red rgb value 
   *******************************************************************/
  public void addRed(Triangle t)
  {
    if(t.getRed() < 255)
    t.setRed(t.getRed()+1);
  }
  
  /********************************************************************
   * Increments the green rgb value 
   *******************************************************************/
  public void addGreen(Triangle t)
  {
    if(t.getGreen() < 255)
    t.setGreen(t.getGreen()+1);  
  }
  
  /********************************************************************
   * Increments the blue rgb value 
   *******************************************************************/
  public void addBlue(Triangle t)
  {
    if(t.getBlue() < 255)
    t.setBlue(t.getBlue()+1);  
  }
  /********************************************************************
   * Increments the alpha rgb value 
   *******************************************************************/
  public void addAlpha(Triangle t)
  {
    if(t.getAlpha() < 255)
    t.setAlpha(t.getAlpha()+1);
  }  
  
  
  /********************************************************************
   * Decrements the red rgb value 
   *******************************************************************/
  public void minusRed(Triangle t)
  {
    if(t.getRed() > 0)
    t.setRed(t.getRed()-1);
  }
  
  /********************************************************************
   * Decrements the green rgb value 
   *******************************************************************/
  public void minusGreen(Triangle t)
  {
    if(t.getGreen() > 0)
    t.setGreen(t.getGreen()-1);  
    }
  
  /********************************************************************
   * Decrements the blue rgb value 
   *******************************************************************/
  public void minusBlue(Triangle t)
  {
    if(t.getBlue() > 0)
    t.setBlue(t.getBlue()-1);
  }
  /********************************************************************
   * Decrements the alpha rgb value 
   *******************************************************************/
  public void minusAlpha(Triangle t)
  {
    if(t.getAlpha() > 0)
    t.setAlpha(t.getAlpha()-1);
  }
  
  
  /********************************************************************
   * Changes the red rgb value randomly 
   *******************************************************************/
  public void randomRed(Triangle t)
  {
    t.setRed(Constants.random.nextInt(255));
  }
  
  /********************************************************************
   * Changes the green rgb value randomly 
   *******************************************************************/
  public void randomGreen(Triangle t)
  {
    t.setGreen(Constants.random.nextInt(255));
  }
  
  /********************************************************************
   * Changes the blue rgb value randomly 
   *******************************************************************/
  public void randomBlue(Triangle t)
  {
    t.setBlue(Constants.random.nextInt(255));
  }
  /********************************************************************
   * Changes the alpha rgb value randomly 
   *******************************************************************/
  public void randomAlpha(Triangle t)
  {
    t.setAlpha(Constants.random.nextInt(255));    
  }
  
  
  /********************************************************************
   * Moves all the x,y points of a triangle
   *******************************************************************/
  public void moveTriangle(Triangle t)
  {
    t.getPoint1().x = Constants.random.nextInt(size.x);
    t.getPoint1().y = Constants.random.nextInt(size.y);
    t.getPoint2().x = Constants.random.nextInt(size.x);
    t.getPoint2().y = Constants.random.nextInt(size.y);
    t.getPoint3().x = Constants.random.nextInt(size.x);
    t.getPoint3().y = Constants.random.nextInt(size.y);
  }
  
  /********************************************************************
   * Changes the position where the triangle is drawn
   * Copies the n-1 triangle and pastes the nth triangle in the 
   * the n-1 spot and then the n-1 triangle to the nth spot (swap)
   *******************************************************************/
  public void reLayerTriangle(Genome g, int triangle)
  {
    if(triangle > 0)
    {
 
      Triangle t = g.getTriangles()[triangle-1].copy();
      
      g.getTriangles()[triangle-1] = g.getTriangles()[triangle];
      g.getTriangles()[triangle] = t;
    }
  }
  
  /********************************************************************
   * aplies one of the previous methods to the genome only once
   *******************************************************************/
  public void oneChange(Genome g)
  {
    int randomNum = Constants.random.nextInt(10);
    
  }

}
