package genome.guicode;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.lang.*;

/***
 * 
 * @author Adam Mitchell
 * 
 * The main frame for our program
 *
 */
public class MainFrame implements Runnable
{
  BufferedImage bImage1;
  BufferedImage bImage2;
  BufferedImage bImage3;
  BufferedImage bImage4 = null;
  JFrame mainFrame;
  JPanel buttonPanel;
  JPanel trianglePanel;
  JPanel picturePanel;
  private JPanel panel;
  private GridBagConstraints gbc;
  int maxPictSizeX;
  int maxPictSizeY;

  /**
   * constructor
   */
  public MainFrame()
  {}

  /**
   * initializes the images and other set up info
   * 
   * @return an int for testing purposes 1 means everything ran smoothly -1 mean
   *         an image didn't load
   */
  public int init()
  {
    try
    {
      bImage1 = ImageIO.read(new File("resources/mona-lisa-cropted-512x413.png"));
      bImage2 = ImageIO.read(new File("resources/poppyfields-512x384.png"));
      bImage3 = ImageIO.read(new File("resources/the_great_wave_off_kanagawa-512x352.png"));
      bImage4 = ImageIO.read(new File("resources/mona-lisa-cropted-512x413.png")); 

    }
    catch (IOException e)
    {
      e.printStackTrace();
      return -1;
    }
    maxPictSizeY = Math.max(bImage1.getHeight(),
        Math.max(bImage2.getHeight(), Math.max(bImage3.getHeight(), bImage4.getHeight())));
    maxPictSizeX = Math.max(bImage1.getWidth(),
        Math.max(bImage2.getWidth(), Math.max(bImage3.getWidth(), bImage4.getWidth())));
    
    System.out.println("x " + maxPictSizeX + " y " + maxPictSizeY );
    
    mainFrame = new JFrame("Triangle Genome");
    mainFrame.setPreferredSize(new Dimension(1200, 700));
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    buttonPanel = new ButtonPanel();
    picturePanel = new PicturePanel();
//    trianglePanel = new PicturePanel();

    trianglePanel = new TrianglePanel(550, maxPictSizeY);
    
    panel = new JPanel(new BorderLayout());
    gbc = new GridBagConstraints();

    panel.add(picturePanel,BorderLayout.WEST);
    panel.add(trianglePanel,BorderLayout.EAST);
    panel.add(buttonPanel,BorderLayout.PAGE_END);
    
    mainFrame.getContentPane().add(panel);
    mainFrame.pack();
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
    // MainFrame genomeGui = new MainFrame();
    SwingUtilities.invokeLater(new MainFrame());
  }

  @Override
  public void run()
  {
    init();
  }

}
