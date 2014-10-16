package genome.guicode;

import genome.Constants;
import genome.logic.Fitness;
import genome.logic.PictureResize;
import genome.types.Genome;
import genome.types.Triangle;
import genome.types.Tribe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

/***********************************************************************************
 * Where are program will start and the brains of the program
 ***********************************************************************************/
public class MainFrameController
{
  private MainFrame frame;
  private Tribe tribe;
  private static BufferedImage bi = null;
  private static BufferedImage smallBi = null;
  private Timer totalRunningTime = new Timer();

  int width;
  int height;
  private volatile boolean paused = false; // Run unless told to pause
  private volatile int generations = 0;

  public MainFrameController()
  {
    // SwingUtilities.invokeLater(new MainFrame());
    frame = new MainFrame();
    frame.start(); // needs to be changed so it'll sleep

    /*
     * All the action listeners are below pauseButton, picturePicker, genomePicker, tribeSelector, triangleSelector,
     * nextButton
     */

    /*
     * pauseButton
     */
    frame.buttonPanel.addStartPauseActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if(frame.buttonPanel.setPause())
        {
         
          frame.buttonPanel.enableButtons();
          frame.enableMenu();
        }
        else
        {
          frame.buttonPanel.disableButtons();   
          frame.disableMenu();
        }
        paused = frame.buttonPanel.getPauseState();
      }
    });

    /*
     * picturePicker
     */
    frame.buttonPanel.addPicturePickerActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        @SuppressWarnings("unchecked")
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String pictName = (String) cb.getSelectedItem();
        frame.picturePanel.setPicture(pictName);
        bi = frame.picturePanel.getCurrentPicture();
        smallBi = PictureResize.resize(bi, Constants.RESIZED_PICTURE_SIZE, Constants.RESIZED_PICTURE_SIZE);

        ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(bi);
        frame.picturePanel.setColorList(colorList);
        LoadPictures.currentPicture(bi);

        restart(bi, colorList);
        System.out.println("Selected picture: " + pictName);
      }
    });

    /*
     * genomePicker
     */
    frame.buttonPanel.addGenomePickerActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        System.out.println("Unimplemented method for genomePicker");
      }
    });

    /*
     * tribeSelector
     */
    frame.buttonPanel.addTribeSelectorActionListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e)
      {
        // System.out.println("tribeSelector");
        int value = (int) ((JSpinner) e.getSource()).getValue();
        frame.buttonPanel.setTribeNumber(value);

        // frame.trianglePanel.displayTriangles( add stuff to display the current tribes triangels);
      }
    });

    /*
     * triangleSelector
     */
    frame.buttonPanel.addTriangleSelectorChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e)
      {
        // System.out.println("triangleSelector");
        int value = (int) ((JSlider) e.getSource()).getValue();
        frame.buttonPanel.setTriangleNumber(value);
        frame.trianglePanel.setTriangleCount(value);
      }
    });

    /*
     * nextButton
     */
    frame.buttonPanel.addNextButtonActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Genome g = Genome.randomGenome(frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel
            .getCurrentPicture().getHeight());
        frame.trianglePanel.displayGenome(g);
        frame.buttonPanel.setFitness(Fitness.getFitness(frame.picturePanel.getCurrentPicture(), g));
      }
    });

    /*
     * write genome 
     */
    frame.addwriteGenomeActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        // TODO needs to be the current genome
        new WriteXMLFile().generate(new Genome(Triangle.randomGenome(200, 200, 200,
            frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture())), 200, 200));
      }
    });

    /*
     * read genome 
     */
    frame.addreadGenomeActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("XML files only", "xml");
        chooser.setFileFilter(filter);
        File f = null;
        try
        {
          f = new File(new File(".").getCanonicalPath());
        }
        catch (IOException e1)
        {
        }
        chooser.setCurrentDirectory(f);
        chooser.showOpenDialog(null);
        File curFile = chooser.getSelectedFile();
        if (!(curFile == null))
        {
          System.out.println(curFile.toString());
          ArrayList<Triangle> xmlArrayListTriangle = new XMLParser().parser(curFile);
//          if (xmlArrayListTriangle == null)
//          {
//            JOptionPane.showMessageDialog(null,
//                "Sorry the file you selected was not in the correct xml format we expected"
//                    + "\n\n click ok to continue");
//          }
          //else
          {
            // TODO need make a new genome with the arraylist xmlArrayListTriangle and add it to a tribe..
          }
        }
      }
    });

    /*
     * show table 
     */
    frame.addshowTableActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        // TODO need to pass in the current genome not just a random one
        new TableStats().showTableData(new Genome(Triangle.randomGenome(200, 200, 200,
            frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture())), 200, 200));
      }
    });

    /*
     * Append Stats 
     */
    frame.addAppendStatsActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        // TODO need to pass in the current genome not just a random one
        JOptionPane.showMessageDialog(null, "About Swing", "About Box V2.0",
            JOptionPane.INFORMATION_MESSAGE);
      }
    });
    
    
    startGA_HC();

  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  private void startGA_HC()
  {

    frame.buttonPanel.disableButtons();
    frame.disableMenu();
    frame.picturePanel.setPicture("triangles.png");
    bi = frame.picturePanel.getCurrentPicture();
    ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture());
    frame.picturePanel.setColorList(colorList);
    smallBi = PictureResize.resize(bi, Constants.RESIZED_PICTURE_SIZE, Constants.RESIZED_PICTURE_SIZE);
    LoadPictures.currentPicture(frame.picturePanel.getCurrentPicture());

    birthTribe(bi, colorList);

    Timer timer = new Timer();
    
    timer.scheduleAtFixedRate(new TimerTask() {
      int sec = 0;
      int min = 0;
      @Override
      public void run()
      {
        if (!tribe.isInterrupted() && !paused)
        {
          synchronized (tribe)
          {
            System.out.println("\tbest genome's hashcode " + tribe.genomes.get(0).hashCode());
            if (!tribe.doneSorting)
            {
              displayGenome(tribe.genomes.get(0));
            }
            if (sec == 59)
            {
              sec = 0;
              min++;
            }
            sec++;
            frame.buttonPanel.setTime(min,sec);
           // frame.buttonPanel.setGen(generations,hc_generations, ga_generations);
          }
        }
      }
    }, 0, 1000L); 
  }


  /***************************************************************************************************
   * 
   * @param g
   **************************************************************************************************/
  public void displayGenome(Genome g)
  {
    synchronized (frame)
    {
      tribe.quickSortGenomes();
      frame.trianglePanel.displayGenome(g);
      frame.buttonPanel.setFitness(Fitness.getFitness(frame.picturePanel.getCurrentPicture(), g));
    }
  }

  /***************************************************************************************************
   * 
   * @param bImage
   * @param clist
   **************************************************************************************************/
  private void birthTribe(BufferedImage bImage, ArrayList<Integer> clist)
  {
    tribe = new Tribe("Tribe 1", bImage.getWidth(), bImage.getHeight(), bImage, clist);
    tribe.start();
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  private void killTribe()
  {
    tribe.interrupt();
  }

  /***************************************************************************************************
   * 
   * @param bImage
   * @param clist
   **************************************************************************************************/
  private void restart(BufferedImage bImage, ArrayList<Integer> clist)
  {
    System.out.println("Restarting GA / HC with new picture");
    killTribe();
    birthTribe(bImage, clist);
  }

  /***************************************************************************************************
   * 
   * @return BufferedImage
   **************************************************************************************************/
  public static BufferedImage getCurrentPict()
  {
    return bi;
  }

  /***************************************************************************************************
   * 
   * @return
   **************************************************************************************************/
  public static BufferedImage getresizedPict()
  {
    return smallBi;
  }

  /***************************************************************************************************
   * 
   * Main starts the whole program..
   **************************************************************************************************/
  public static void main(String[] args)
  {
    new MainFrameController();
  }
}
