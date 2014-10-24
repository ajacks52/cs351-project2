package genome.guicode;

import genome.Constants;
import genome.types.Triangle;
import genome.types.Triangle.GeneType;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

/***************************************************************************************************
 * @author Adam
 * 
 * Class to Parses the given xml file and returns an arraylist of the triangles formed
 * 
 * used http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/,
 * 
 **************************************************************************************************/
public class XMLParser
{
  private ArrayList<Triangle> triList = new ArrayList<Triangle>();

  /***************************************************************************************************
   * Parses the given xml file and returns an arraylist of the triangles formed
   * 
   * @param fXmlFile
   * @return Arraylist of triangles
   **************************************************************************************************/
  public ArrayList<Triangle> parser(File fXmlFile)
  {
    try
    {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);
      doc.getDocumentElement().normalize();
      
      if (Constants.DEBUG)
      {
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
      }
      
      NodeList nList = doc.getElementsByTagName("triangle");
      if (doc.getElementsByTagName("triangle") == null)
      {
        return null;
      }

      if (Constants.DEBUG)
      {
        System.out.println("----------------------------");
      }

      for (int temp = 0; temp < nList.getLength(); temp++)
      {
        Node nNode = nList.item(temp);
        if (Constants.DEBUG)
        {
          System.out.println(nNode.getNodeName());
        }

        if (nNode.getNodeType() == Node.ELEMENT_NODE)
        {
          Triangle t = new Triangle();
          Element data = (Element) nNode;

          if (data.getElementsByTagName(GeneType.X1.toString()) == null)
          {
            return null;
          }

          for (GeneType type : GeneType.values())
          {
            t.setGene(type, Short.valueOf(data.getElementsByTagName(type.toString()).item(0).getTextContent()));
          }
          triList.add(Integer.valueOf(data.getAttribute("id")), t);

          if (Constants.DEBUG)
          {
            System.out.println("index : " + data.getAttribute("id"));
            for (GeneType type : GeneType.values())
            {
              System.out.println(type.toString() + " : "
                  + data.getElementsByTagName(type.toString()).item(0).getTextContent());
            }
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