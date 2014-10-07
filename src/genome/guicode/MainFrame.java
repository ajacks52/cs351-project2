package genome.guicode;

import genome.types.Triangle;
import java.awt.*;
import javax.swing.*;

/********************************************************************************
 * 
 * @author Adam Mitchell
 * 
 *         The main frame for our program
 *
 ********************************************************************************/
public class MainFrame extends Thread
{

  final static int FRAME_SIZE_X = 1100;
  final static int FRAME_SIZE_Y = 740;

  public PicturePanel picturePanel;
  private static JFrame mainFrame;
  public ButtonPanel buttonPanel;
  public TrianglePanel trianglePanel;
  private static JPanel containerPanel;

  /**
   * constructor
   */
  public MainFrame()
  {

    mainFrame = new JFrame("Triangle Genome");
    containerPanel = new JPanel(new BorderLayout());
    buttonPanel = new ButtonPanel();
    
    picturePanel = new PicturePanel(LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight(),
        LoadPictures.bImage1);
    trianglePanel = new TrianglePanel(LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight());
    
  }

  /********************************************************************************
   * initializes the images and other set up info
   * 
   * @return an int for testing purposes 1 means everything ran smoothly -1 mean
   *         an image didn't load
   ********************************************************************************/
  public int init()
  {

    mainFrame.setPreferredSize(new Dimension(FRAME_SIZE_X, FRAME_SIZE_Y));
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainFrame.setResizable(false);

    
    trianglePanel.setTriangleCount(200);
//    trianglePanel.displayTriangles(
//        Triangle.randomGenome(200, LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight()),
//        LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight());

    
    containerPanel.add(picturePanel, BorderLayout.WEST);
    containerPanel.add(trianglePanel, BorderLayout.EAST);
    containerPanel.add(buttonPanel, BorderLayout.PAGE_END);
    mainFrame.getContentPane().add(containerPanel);

    mainFrame.pack();
    mainFrame.setVisible(true);
    return 1;
  }

  /********************************************************************************
   * Main for unit testing
   * 
   * @param args
   ********************************************************************************/
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
