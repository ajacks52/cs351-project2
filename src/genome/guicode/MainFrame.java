package genome.guicode;

import java.lang.*;
import java.io.File;
import java.io.IOException;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;




/***
 * 
 * @author Adam Mitchell
 * 
 * The main frame for our program
 *
 */
public class MainFrame
{
  BufferedImage bImage1; 
  BufferedImage bImage2; 
  BufferedImage bImage3; 
  BufferedImage bImage4 = null; 
  JFrame mainFrame;
  JPanel buttonPanel;
  JPanel trianglePanel;
  JPanel picturePanel;
  
  /**
   * constructor 
   */
  public MainFrame()
  {
    init();
  }
  
  /**
   * initializes the images and other set up info
   * @return an int 
   * for testing purposes 1 means everything ran smoothly -1 mean an image didn't load 
   */
  public int init()
  {

    
    try
    {
      bImage1 = ImageIO.read(new File("resources/mona-lisa-cropted-512x413.png"));
      bImage2 = ImageIO.read(new File("resources/poppyfields-512x384.png"));
      bImage3 = ImageIO.read(new File("resources/the_great_wave_off_kanagawa-512x352.png"));
      bImage4 = ImageIO.read(new File("resources/mona-lisa-cropted-512x413.png")); // just for now till we add our 4th picture
    } catch (IOException e)
    {
      e.printStackTrace();
      return -1;
    }
    
    int maxPictSizeY = Math.max(bImage1.getHeight(), Math.max(bImage2.getHeight(), Math.max(bImage3.getHeight(), bImage4.getHeight()))); 
    int maxPictSizeX = Math.max(bImage1.getWidth(), Math.max(bImage2.getWidth(), Math.max(bImage3.getWidth(), bImage4.getWidth()))); 

    
    mainFrame = new JFrame("Triangle Genome");
    mainFrame.setSize(1100, 700);
    mainFrame.setLayout(new GridBagLayout());
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    buttonPanel = new ButtonPanel();
    picturePanel = new PicturePanel();
    trianglePanel = new TrianglePanel(maxPictSizeX, maxPictSizeY); 
    
    try
    {
      Thread.sleep(2000);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    
    
    mainFrame.add(picturePanel);
    mainFrame.add(trianglePanel);
    mainFrame.setVisible(true); 
    return 1;
  }
  
  /**
   * Main for unit testing 
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    MainFrame genomeGui = new MainFrame();
  }
}
