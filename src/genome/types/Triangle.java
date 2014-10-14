package genome.types;

import genome.Constants;
import genome.guicode.PicturePanel;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.Random;

import com.sun.corba.se.impl.orbutil.closure.Constant;

/**********************************************************************************
 * @author Jordan Medlock 
 * @author Adam Mitchell
 * 
 **********************************************************************************/
public class Triangle
{
  private Point point1;
  private Point point2;
  private Point point3;

  private int rgba;

  /************************************************************************************
   * 
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @param x3
   * @param y3
   * @param rgba
   ************************************************************************************/
  public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, int rgba)
  {
    point1 = new Point(x1, y1);
    point2 = new Point(x2, y2);
    point3 = new Point(x3, y3);
    this.rgba = rgba;
  }

  /************************************************************************************
   * 
   * @param p1
   * @param p2
   * @param p3
   * @param rgba
   ***********************************************************************************/
  public Triangle(Point p1, Point p2, Point p3, int rgba)
  {
    point1 = p1;
    point2 = p2;
    point3 = p3;
    this.rgba = rgba;
  }

  /************************************************************************************
   *
   * 
   * @param width
   * @param height
   * @return
   *************************************************************************************/
  public static Triangle randomTriangleIn(int width, int height, ArrayList<Integer> colorList)
  {
    Random rand = Constants.random;
    
//    System.out.println(colorList.size());
    int rgb;
    if (colorList.size() == 0) 
    {
      rgb = Constants.random.nextInt();
    } 
    else 
    {
      rgb = colorList.get(rand.nextInt(colorList.size()));
    }
    Color c = new Color(rgb);
    int red = c.getRed();
    int green = c.getGreen();
    int blue = c.getBlue();
    
    
    Triangle t = new Triangle(scaledRandom(width), scaledRandom(height), scaledRandom(width),
        scaledRandom(height), scaledRandom(width), scaledRandom(height), rand.nextInt());
    t.setRed(red);
    t.setGreen(green);
    t.setBlue(blue);
    return t;
  }
  
  /************************************************************************************
   * 
   * @param width
   * @return
   *************************************************************************************/
  private static int scaledRandom(int width)
  {
    double x = Constants.random.nextInt(width) / (double) width;
    x = x - 0.5;
    x *= x * (Constants.random.nextInt()%2==0? -1.0: 1.0);
    x *= 4.0;
    x += (x < 0? 1.0 : 0.0);
    x *= width;
    return (int)x;
  }

  /************************************************************************************
   * 
   * @param count
   * @param width
   * @param height
   * @return
   *************************************************************************************/
  public static ArrayList<Triangle> randomGenome(int count, int width, int height, ArrayList<Integer> colorList)
  {
    ArrayList<Triangle> ts = new ArrayList<Triangle>(count);
    Point p = new Point(Constants.random.nextInt(width), Constants.random.nextInt(height)); 
    

    
   ts.add(new Triangle(0,0,0,height,p.x,p.y,colorList.get(Constants.random.nextInt(colorList.size()))));
   ts.add(new Triangle(width,height,width,0,p.x,p.y,colorList.get(Constants.random.nextInt(colorList.size()))));
   ts.add(new Triangle(0,0,width,0,p.x,p.y,colorList.get(Constants.random.nextInt(colorList.size()))));
   ts.add(new Triangle(width,height,0,height,p.x,p.y,colorList.get(Constants.random.nextInt(colorList.size()))));
   
   
    for (int i = 4; i < count; i++)
    {
      ts.add(randomTriangleIn(width, height, colorList));
    }
    return ts;
  }
  

  /************************************************************************************
   * 
   * @return Point
   ************************************************************************************/
  public Point getPoint1()
  {
    return point1;
  }

  /************************************************************************************
   * 
   * @param point1
   ************************************************************************************/
  public void setPoint1(Point point1)
  {
    this.point1 = point1;
  }

  /**
   * 
   * @return Point
   */
  public Point getPoint2()
  {
    return point2;
  }

  /************************************************************************************
   * 
   * @param point2
   ************************************************************************************/
  public void setPoint2(Point point2)
  {
    this.point2 = point2;
  }

  /************************************************************************************
   * 
   * @return Point
   ************************************************************************************/
  public Point getPoint3()
  {
    return point3;
  }

  /************************************************************************************
   * 
   * @param point3
   ************************************************************************************/
  public void setPoint3(Point point3)
  {
    this.point3 = point3;
  }

  /************************************************************************************
   * 
   * @return int red rgb value
   ************************************************************************************/
  public int getRed()
  {
    return (rgba >> 24) & 0xFF;
  }

  /************************************************************************************
   * 
   * @param red
   ************************************************************************************/
  public void setRed(int red)
  {
    rgba = rgba & 0x00FFFFFF;
    rgba = rgba | ((red & 0xFF) << 24);
  }

  /************************************************************************************
   * 
   * @return int green rgb value
   ************************************************************************************/
  public int getGreen()
  {
    return (rgba >> 6) & 0xFF;
  }

  /************************************************************************************
   * 
   * @param green
   ************************************************************************************/
  public void setGreen(int green)
  {
    rgba = rgba & 0xFF00FFFF;
    rgba = rgba | ((green & 0xFF) << 16);
  }

  /************************************************************************************
   * 
   * @return int blue rgb value
   ************************************************************************************/
  public int getBlue()
  {
    return (rgba >> 8) & 0xFF;
  }

  /************************************************************************************
   * 
   * @param blue
   ************************************************************************************/
  public void setBlue(int blue)
  {
    rgba = rgba & 0xFFFF00FF;
    rgba = rgba | ((blue & 0xFF) << 8);
  }

  /************************************************************************************
   * 
   * @return int alpha
   ************************************************************************************/
  public int getAlpha()
  {
    return (rgba >> 0) & 0xFF;
  }

  /************************************************************************************
   * 
   * @param alpha
   ************************************************************************************/
  public void setAlpha(int alpha)
  {
    rgba = rgba & 0xFFFFFF00;
    rgba = rgba | ((alpha & 0xFF) << 0);
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Polygon getPolygon()
  {
    Polygon p = new Polygon();
    p.addPoint(point1.x, point1.y);
    p.addPoint(point2.x, point2.y);
    p.addPoint(point3.x, point3.y);
    return p;
  }

  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Color getColor()
  {
    return new Color(getRed(), getGreen(), getBlue(), getAlpha());
  }
  
  /************************************************************************************
   * 
   * @return
   ************************************************************************************/
  public Triangle copy()
  {
    return new Triangle(point1,point2,point3,rgba);
  }
}
