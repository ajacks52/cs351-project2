package genome.guicode;

import genome.types.Triangle;
import java.awt.*;
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

  final static int FRAME_SIZE_X = 1100;
  final static int FRAME_SIZE_Y = 750;

  public static PicturePanel picturePanel;
  private static JFrame mainFrame;
  private static ButtonPanel buttonPanel;
  public static TrianglePanel trianglePanel;
  private static JPanel containerPanel;

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

    mainFrame = new JFrame("Triangle Genome");
    mainFrame.setPreferredSize(new Dimension(FRAME_SIZE_X, FRAME_SIZE_Y));
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainFrame.setResizable(false);

    new LoadPictures();
    picturePanel = new PicturePanel(LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight(),
        LoadPictures.bImage1);
    trianglePanel = new TrianglePanel(LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight());
    trianglePanel.setTriangleCount(200);
    trianglePanel.displayTriangles(
        Triangle.randomGenome(200, LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight()),
        LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight());

    containerPanel = new JPanel(new BorderLayout());
    buttonPanel = new ButtonPanel();
    
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
