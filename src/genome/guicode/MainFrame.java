package genome.guicode;

import genome.Constants;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/********************************************************************************
 * 
 * @author Adam Mitchell
 * 
 * The main frame for our program
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
  public JMenuItem showTable = new JMenuItem("Show Current Genome Stats Table");
  public JMenuItem readGenome = new JMenuItem("Read Genome File");
  public JMenuItem writeGenome = new JMenuItem("Write Current Genome File");
  public JMenuItem appendStats = new JMenuItem("Append new Stats File");

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
    trianglePanel = new TrianglePanel();
  }

  @Override
  public void run()
  {
    init();
  }

  /******************************************************************************************************
   * initializes the images and other set up info
   * 
   * @return an int for testing purposes 1 means everything ran smoothly -1 mean an image didn't load
   ******************************************************************************************************/
  public void init()
  {
    Color bgcolor2 = new Color(199, 198, 207);
    mainFrame.setPreferredSize(new Dimension(FRAME_SIZE_X, FRAME_SIZE_Y));
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainFrame.setResizable(false);

    JMenuBar bar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu statsMenu = new JMenu("Statistics");
    bar.add(fileMenu);
    bar.add(statsMenu);
    bar.setBackground(bgcolor2);
    fileMenu.add(readGenome);
    fileMenu.add(new JSeparator());
    fileMenu.add(writeGenome);
    fileMenu.add(new JSeparator());
    fileMenu.add(appendStats);
    statsMenu.add(showTable);
    statsMenu.add(new JSeparator());
    statsMenu.add(appendStats);
    mainFrame.setJMenuBar(bar);
    trianglePanel.setTriangleCount(Constants.GENOME_SIZE);
    containerPanel.add(picturePanel, BorderLayout.WEST);
    containerPanel.add(trianglePanel, BorderLayout.EAST);
    containerPanel.add(buttonPanel, BorderLayout.PAGE_END);
    mainFrame.getContentPane().add(containerPanel);
    mainFrame.pack();
    mainFrame.setVisible(true);
  }

  /*
   * Action listeners for the two menus
   */
  public void addreadGenomeActionListener(ActionListener al)
  {
    readGenome.addActionListener(al);
  }

  public void addwriteGenomeActionListener(ActionListener al)
  {
    writeGenome.addActionListener(al);
  }

  public void addshowTableActionListener(ActionListener al)
  {
    showTable.addActionListener(al);
  }

  public void addAppendStatsActionListener(ActionListener al)
  {
    appendStats.addActionListener(al);
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public void disableMenu()
  {
    showTable.setEnabled(false);
    readGenome.setEnabled(false);
    writeGenome.setEnabled(false);
    appendStats.setEnabled(false);
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public void enableMenu()
  {
    showTable.setEnabled(true);
    readGenome.setEnabled(true);
    writeGenome.setEnabled(true);
    appendStats.setEnabled(true);
  }
}