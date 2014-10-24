package genome.guicode;

import genome.Constants;
import genome.types.Genome;
import genome.types.Triangle.GeneType;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/***************************************************************************************************
 * @author Adam
 * 
 * Writes an xml file based on the given genome. In our awesomely formatted genome genetic algorithm
 * xml format.
 **************************************************************************************************/
public class WriteXMLFile
{

  /***************************************************************************************************
   * Writes an xml file based on the given genome. In our awesomely formatted genome genetic algorithm
   * xml format.
   * @param genome
   **************************************************************************************************/
  public void generate(Genome genome)
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
        triangle.setAttribute("id", Integer.toString(i));

        for (GeneType t : GeneType.values())
        {
          Element x1 = doc.createElement(t.toString());
          x1.appendChild(doc.createTextNode(Short.toString((genome.triangles[i].getGene(t)))));
          triangle.appendChild(x1);
        }
      }
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("data/genomeFile.xml"));      
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