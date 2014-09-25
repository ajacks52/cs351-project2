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
  public static BufferedImage bImage1;
  public static BufferedImage bImage2;
  public static BufferedImage bImage3;
  public static BufferedImage bImage4;
  public static BufferedImage bImage6;
  public static BufferedImage bImage5;
  public static BufferedImage bImage7;
  public static BufferedImage bImage8;
  public static BufferedImage bImage9;

  final static int FRAME_SIZE_X = 1100;
  final static int FRAME_SIZE_Y = 800;
  public static int maxPicX;
  public static int maxPicY;
  public static PicturePanel picturePanel;

  private JFrame mainFrame;
  private ButtonPanel buttonPanel;
  public static TrianglePanel trianglePanel;
  private JPanel containerPanel;

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

    try
    {
      bImage1 = ImageIO.read(LoadPictures.f1);
      bImage2 = ImageIO.read(LoadPictures.f2);
      bImage3 = ImageIO.read(LoadPictures.f3);
      bImage4 = ImageIO.read(LoadPictures.f4);
      bImage5 = ImageIO.read(LoadPictures.f5);
      bImage6 = ImageIO.read(LoadPictures.f6);
      bImage7 = ImageIO.read(LoadPictures.f7);
      bImage8 = ImageIO.read(LoadPictures.f8);
      bImage9 = ImageIO.read(LoadPictures.f9);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return -1;
    }



    mainFrame = new JFrame("Triangle Genome");
    mainFrame.setPreferredSize(new Dimension(FRAME_SIZE_X, FRAME_SIZE_Y));
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    buttonPanel = new ButtonPanel();
    picturePanel = new PicturePanel(bImage1.getWidth(), bImage1.getHeight(), bImage1);
    trianglePanel = new TrianglePanel(bImage1.getWidth(), bImage1.getHeight());
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
