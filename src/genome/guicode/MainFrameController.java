package genome.guicode;

import genome.Constants;
import genome.logic.PictureResize;
import genome.types.Genome;
import genome.types.Tribe;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;


/***********************************************************************************
 * Where are program will start and the brains of the program
 ***********************************************************************************/
public class MainFrameController
{
  private static MainFrame frame;
  static BufferedImage bi = null;
  static BufferedImage smallBi = null;
  volatile int totalgenerations = 0;
  public volatile static int generationspersec = 0;
  public volatile static int totalmutations = 0;
  public volatile static int totalcrossovers = 0;
  public static int numberOfTribes = 1;
  volatile static int totalgenomes = 0;
  static int[] statsArray = new int[10];
  static String statsfileName = "statsFile";
  int sec = 0;
  int min = 0;
  boolean append = false;
  public static ArrayList<Tribe> threads = new ArrayList<Tribe>();
  volatile static boolean paused = false; // Run unless told to pause

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public MainFrameController()
  {
    // SwingUtilities.invokeLater(new MainFrame());
    frame = new MainFrame();
    frame.start(); // needs to be changed so it'll sleep

    new GUIActionListeners().setListeners(frame);

    startGA_HC();
    setTimers();

  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  private void setTimers()
  {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {
        synchronized (threads)
        {
          totalgenerations = totalmutations + totalcrossovers;
          frame.buttonPanel.updateGUIStats(totalgenerations, totalmutations, totalcrossovers, totalgenomes,
              generationspersec, 99, 99, 99, 99);
          statsArray[0] = min;
          statsArray[1] = sec;
          statsArray[2] = totalgenerations;
          statsArray[3] = totalmutations;
          statsArray[4] = totalcrossovers;
          statsArray[5] = 0; // numberOfTribes;
          statsArray[6] = 0; // totalgenomes;
          statsArray[7] = 0; // bestfit;
          statsArray[8] = 0; // averagefitness;
          statsArray[9] = generationspersec;
          generationspersec = 0;
        }
        System.out.println("1****\n****\n****\n");
        // if (!tribe.isInterrupted())
        if (!paused)
        {
          System.out.println("2****\n****\n****\n");

          // synchronized (tribe.genomes)
          {
            // System.out.println("\tbest genome's hashcode " + tribe.genomes.get(0).hashCode());
            // displayGenome(tribe.genomes.get(0));
          }
          if (sec == 59)
          {
            sec = 0;
            min++;
          }
          sec++;
          frame.buttonPanel.setTime(min, sec);
          // frame.buttonPanel.setGen(generations,hc_generations, ga_generations);
          System.out.println("3****\n****\n****\n");

        }
      }
    }, 0, 1000L);

    Timer displayGenome = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {
        // synchronized (tribe)

        if (!paused)
        {
          // synchronized (tribe)
          {
            // if (!tribe.doneSorting)
            {
              displayGenome();
            }

          }
        }
      }
    }, 0, 2000L);

    Timer statsFileTimer = new Timer();
    statsFileTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {

        try
        {
          new PrintStatsFile(statsfileName).writeToFile(append, statsArray);
          append = true;
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }, 0, 6000L);
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

    ArrayList<Integer> colorList = new ArrayList(); // frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture());
    // frame.picturePanel.setColorList(colorList);
    smallBi = PictureResize.resize(bi, Constants.RESIZED_PICTURE_SIZE, Constants.RESIZED_PICTURE_SIZE);
    LoadPictures.currentPicture(frame.picturePanel.getCurrentPicture());

    birthTribe();

  }

  /***************************************************************************************************
   * 
   * @param g
   **************************************************************************************************/
  public void displayGenome()
  {
    synchronized (frame)
    {
      double best = 1000000000L; // really big number
      Genome bestG = null;
      if (threads.get(0).genomes != null)
      {
        for (int i = 0; i < threads.size(); i++)
        {
          Genome genome = threads.get(i).genomes.get(0);
          double current = genome.getFitness(genome.getImage(200), 10);
          if (current < best)
          {
            best = current;
            bestG = genome;
          }
        }
        frame.trianglePanel.displayGenome(bestG);
        frame.buttonPanel.setFitness(best);
      }
    }

  }

  /***************************************************************************************************
   * 
   * @param bImage
   * @param clist
   **************************************************************************************************/
  static void birthTribe()
  {
    BufferedImage bImage = frame.picturePanel.getCurrentPicture();
    System.out.println("in tribe's picture is " + bImage.getHeight());
    Tribe tribe = new Tribe("Tribe 1", bImage.getWidth(), bImage.getHeight(), bImage,
        frame.picturePanel.pictureColorValues());
    tribe.start();
    totalgenomes += tribe.genomes.size();
    threads.add(tribe);
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  static void killTribe()
  {
    totalgenomes -= threads.get(threads.size() - 1).genomes.size();
    threads.get(threads.size() - 1).interrupt();
    threads.remove(threads.size() - 1);
  }

  /***************************************************************************************************
   * 
   * @param bImage
   * @param clist
   **************************************************************************************************/
  static void restart(BufferedImage bImage, ArrayList<Integer> clist)
  {
    System.out.println("Restarting GA / HC with new picture");

    for (int i = 0; i < threads.size(); i++)
    {
      killTribe();
    }
    System.out.println("Number of tribes to be made " + numberOfTribes);
    for (int i = 0; i < numberOfTribes; i++)
    {
      birthTribe();
    }

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
