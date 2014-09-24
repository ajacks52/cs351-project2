package genome.guicode;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/***
 * 
 * @author Adam Mitchell
 * 
 *         The main frame for our program
 *
 */
public class MainFrame implements Runnable
{
  public BufferedImage bImage1;
  public BufferedImage bImage2;
  public BufferedImage bImage3;
  public BufferedImage bImage4;

  private JFrame mainFrame;
  private ButtonPanel buttonPanel;
  private TrianglePanel trianglePanel;
  private PicturePanel picturePanel;
  private JPanel containerPanel;

  final int FRAME_SIZE_X = 1100;
  final int FRAME_SIZE_Y = 700;
  int maxPicX;
  int maxPicY;
  private BufferedImage bImage6;
  private BufferedImage bImage5;
  private BufferedImage bImage7;
  private BufferedImage bImage8;

  /**
   * constructor
   */
  public MainFrame()
  {
  }

  /**
   * initializes the images and other set up info
   * 
   * @return an int for testing purposes 1 means everything ran smoothly -1 mean
   *         an image didn't load
   */
  public int init()
  {
    
    File f1 = new File("images/Leonardo_da_Vinci-Mona-Lisa-460x363.png");
    File f2 = new File("images/Claude_Monet-Poppy_Fields-450x338.png");
    File f3 = new File("images/Hokusai-Great_Wave_Off_Kanagawa-450x309.png");
    File f4 = new File("images/Hokusai-Great_Wave_Off_Kanagawa-200x137.png");
    File f5 = new File("images/Carson-408x369.png");
    File f6 = new File("images/Gummi_Bears-299x339.png");
    File f7 = new File("images/Old_House-300x331.png");
    File f8 = new File("images/Hot_Air_Balloon-400x300.png");
  
    try
    {
      bImage1 = ImageIO.read(f1);
      bImage2 = ImageIO.read(f2);
      bImage3 = ImageIO.read(f3);
      bImage4 = ImageIO.read(f4);
      bImage5 = ImageIO.read(f5);
      bImage6 = ImageIO.read(f6);
      bImage7 = ImageIO.read(f7);
      bImage8 = ImageIO.read(f8);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return -1;
    }
    maxPicX = Math.max(bImage1.getWidth(),
        Math.max(bImage2.getWidth(), Math.max(bImage3.getWidth(), bImage4.getWidth())));
    maxPicY = Math.max(bImage1.getHeight(),
        Math.max(bImage2.getHeight(), Math.max(bImage3.getHeight(), bImage4.getHeight())));

    System.out.println("max x: " + maxPicX + ", max y: " + maxPicY);

    mainFrame = new JFrame("Triangle Genome");
    mainFrame.setPreferredSize(new Dimension(FRAME_SIZE_X, FRAME_SIZE_Y));
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
   
    buttonPanel = new ButtonPanel(f1, f2, f3, f4, f5, f6, f7, f8);
    picturePanel = new PicturePanel(bImage1.getWidth(), bImage1.getHeight(), bImage1);
    trianglePanel = new TrianglePanel(bImage1.getWidth(),  bImage1.getHeight());
    containerPanel = new JPanel(new BorderLayout());
    
    containerPanel.add(picturePanel, BorderLayout.WEST);
    containerPanel.add(trianglePanel, BorderLayout.EAST);
    containerPanel.add(buttonPanel, BorderLayout.PAGE_END);
    mainFrame.getContentPane().add(containerPanel);
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
    SwingUtilities.invokeLater(new MainFrame());
  }

  @Override
  public void run()
  {
    init();
  }
}
