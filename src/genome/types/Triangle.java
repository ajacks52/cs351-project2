package genome.types;

import genome.Constants;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;

public class Triangle
{
  private Point point1;
  private Point point2;
  private Point point3;

  private int   rgba;

  public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, int rgba)
  {
    point1 = new Point(x1,y1);
    point2 = new Point(x2,y2);
    point3 = new Point(x3,y3);
    this.rgba = rgba;
  }
  
  public Triangle(Point p1, Point p2, Point p3, int rgba)
  {
    point1 = p1;
    point2 = p2;
    point3 = p3;
    this.rgba = rgba;
  }
  
  public static Triangle randomTriangleIn(int width, int height)
  {
    Random rand = Constants.random;

    return new Triangle(rand.nextInt(width), rand.nextInt(height), 
                        rand.nextInt(width), rand.nextInt(height), 
                        rand.nextInt(width), rand.nextInt(height), 
                        rand.nextInt());
  }
  
  
  public Point getPoint1()
  {
    return point1;
  }

  public void setPoint1(Point point1)
  {
    this.point1 = point1;
  }

  public Point getPoint2()
  {
    return point2;
  }

  public void setPoint2(Point point2)
  {
    this.point2 = point2;
  }

  public Point getPoint3()
  {
    return point3;
  }

  public void setPoint3(Point point3)
  {
    this.point3 = point3;
  }

  public int getRed()
  {
    return (rgba >> 12) & 0xFF;
  }

  public void setRed(int red)
  {
    rgba = rgba & 0x00FFFFFF;
    rgba = rgba | ((red & 0xFF) << 12);
  }

  public int getGreen()
  {
    return (rgba >> 8) & 0xFF;
  }

  public void setGreen(int green)
  {
    rgba = rgba & 0xFF00FFFF;
    rgba = rgba | ((green & 0xFF) << 8);
  }

  public int getBlue()
  {
    return (rgba >> 4) & 0xFF;
  }

  public void setBlue(int blue)
  {
    rgba = rgba & 0xFFFF00FF;
    rgba = rgba | ((blue & 0xFF) << 4);
  }

  public int getAlpha()
  {
    return (rgba >> 0) & 0xFF;
  }

  public void setAlpha(int alpha)
  {
    rgba = rgba & 0xFFFFFF00;
    rgba = rgba | ((alpha & 0xFF) << 0);
  }

  public Polygon getPolygon()
  {
    Polygon p = new Polygon();
    p.addPoint(point1.x, point1.y);
    p.addPoint(point2.x, point2.y);
    p.addPoint(point3.x, point3.y);
    return p;
  }
  
  public Color getColor()
  {
    return new Color(getRed(),getGreen(),getBlue(),getAlpha());
  }
}
