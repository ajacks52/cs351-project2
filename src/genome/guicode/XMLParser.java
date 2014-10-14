package genome.guicode;

import genome.Constants;
import genome.types.Triangle;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

/***************************************************************************************************
 * 
 * @author Adam
 * 
 * used http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/,
 * http://www.java-samples.com/showtutorial.php?tutorialid=152, and
 * http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/ to get an idea on how to parse xml
 **************************************************************************************************/
public class XMLParser
{
  private ArrayList<Triangle> triList = new ArrayList<Triangle>();

  /***************************************************************************************************
   * Parses the given xml file and returns an arraylist of the triangles formed 
   * @param fXmlFile
   * @return Arraylist of triangles
   **************************************************************************************************/
  public  ArrayList<Triangle> parser(File fXmlFile)
  {
    try
    {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);

      doc.getDocumentElement().normalize();

      System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

      NodeList nList = doc.getElementsByTagName("triangle");

      System.out.println("----------------------------");

      for (int temp = 0; temp < nList.getLength(); temp++)
      {

        Node nNode = nList.item(temp);

        System.out.println(nNode.getNodeName());

        if (!nNode.getNodeName().equals("genome"))
        {
          return null;
        }

        if (nNode.getNodeType() == Node.ELEMENT_NODE)
        {
          Triangle t = new Triangle(null, null, null, temp);
          Element data = (Element) nNode;

          if (data.getElementsByTagName("x1") == null)
          {
            return null;
          }

          t.setPoint1(new Point(Integer.valueOf(data.getElementsByTagName("x1").item(0).getTextContent()), Integer
              .valueOf(data.getElementsByTagName("y1").item(0).getTextContent())));
          t.setPoint2(new Point(Integer.valueOf(data.getElementsByTagName("x2").item(0).getTextContent()), Integer
              .valueOf(data.getElementsByTagName("y2").item(0).getTextContent())));
          t.setPoint3(new Point(Integer.valueOf(data.getElementsByTagName("x3").item(0).getTextContent()), Integer
              .valueOf(data.getElementsByTagName("y3").item(0).getTextContent())));
          t.setRed(Integer.valueOf(data.getElementsByTagName("r").item(0).getTextContent()));
          t.setGreen(Integer.valueOf(data.getElementsByTagName("g").item(0).getTextContent()));
          t.setBlue(Integer.valueOf(data.getElementsByTagName("b").item(0).getTextContent()));
          t.setAlpha(Integer.valueOf(data.getElementsByTagName("t").item(0).getTextContent()));
          triList.add(Integer.valueOf(data.getAttribute("id")), t);

          if (Constants.DEBUG)
          {
            System.out.println("index : " + data.getAttribute("id"));
            System.out.println("x1 : " + data.getElementsByTagName("x1").item(0).getTextContent());
            System.out.println("y1 : " + data.getElementsByTagName("y1").item(0).getTextContent());
            System.out.println("x2: " + data.getElementsByTagName("x2").item(0).getTextContent());
            System.out.println("y2 : " + data.getElementsByTagName("y2").item(0).getTextContent());
            System.out.println("x3 : " + data.getElementsByTagName("x3").item(0).getTextContent());
            System.out.println("y3 : " + data.getElementsByTagName("y3").item(0).getTextContent());
            System.out.println("r : " + data.getElementsByTagName("r").item(0).getTextContent());
            System.out.println("g: " + data.getElementsByTagName("g").item(0).getTextContent());
            System.out.println("b : " + data.getElementsByTagName("b").item(0).getTextContent());
            System.out.println("t : " + data.getElementsByTagName("t").item(0).getTextContent());
          }
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return triList;
  }

}