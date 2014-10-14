package genome.guicode;

import genome.Constants;
import genome.types.Genome;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;

/**
 * 
 * @author Adam
 * 
 * used http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/,
 * http://www.java-samples.com/showtutorial.php?tutorialid=152, and
 * http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/ to get an idea on how to parse xml
 */
public class WriteXMLFile
{

  public static void generate(Genome genome)
  {
    try
    {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      // root elements
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("genome");
      doc.appendChild(rootElement);

      for (int i = 0; i < Constants.GENOME_SIZE; i++)
      {
        // triangle element
        Element triangle = doc.createElement("triangle");
        rootElement.appendChild(triangle);

        // set attribute to staff element
        triangle.setAttribute("id", Integer.toString(i));

        // x1 element
        Element x1 = doc.createElement("x1");
        x1.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getPoint1().x))));
        triangle.appendChild(x1);
        // y1 element
        Element y1 = doc.createElement("y1");
        y1.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getPoint1().y))));
        triangle.appendChild(y1);
        // x2 element
        Element x2 = doc.createElement("x2");
        x2.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getPoint2().x))));
        triangle.appendChild(x2);
        // y2 element
        Element y2 = doc.createElement("y2");
        y2.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getPoint2().y))));
        triangle.appendChild(y2);
        // x3 element
        Element x3 = doc.createElement("x3");
        x3.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getPoint3().x))));
        triangle.appendChild(x3);
        // y3 element
        Element y3 = doc.createElement("y3");
        y3.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getPoint3().y))));
        triangle.appendChild(y3);
        // r element
        Element r = doc.createElement("r");
        r.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getRed()))));
        triangle.appendChild(r);
        // g element
        Element g = doc.createElement("g");
        g.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getBlue()))));
        triangle.appendChild(g);
        // b element
        Element b = doc.createElement("b");
        b.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getGreen()))));
        triangle.appendChild(b);
        // t element
        Element t = doc.createElement("t");
        t.appendChild(doc.createTextNode(Integer.toString((genome.triangles[i].getAlpha()))));
        triangle.appendChild(t);
      }

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("data/file.xml"));


      
      transformer.transform(source, result);

      System.out.println("File saved!");

    }
    catch (ParserConfigurationException pce)
    {
      pce.printStackTrace();
    }
    catch (TransformerException tfe)
    {
      tfe.printStackTrace();
    }
  }
}