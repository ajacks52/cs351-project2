package genome.guicode;

import genome.Constants;
import genome.types.Genome;
import genome.types.Tribe;
import genome.unit_testing.AssertTests;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/*******************************************************************************************************
 * MainFrameController
 * 
 * @author Adam Mitchell
 * @author Jordan Medlock
 * 
 * Where are program will start and the brains of the program
 *******************************************************************************************************/
public class MainFrameController
{
  private static MainFrame frame;
  public static BufferedImage bi = null;

  static String statsfileName = "statsFile";
  public static int totalgenerations = 0;
  public static int generationspersec = 0;
  public volatile static int totalmutations = 0;
  public volatile static int totalcrossovers = 0;
  volatile static int totalgenomes = 0;
  public static ArrayList<Tribe> threads = new ArrayList<Tribe>();
  volatile static boolean paused = false; // Run unless told to pause
  int minutes;
  int seconds;
  double bestfit;
  long timeNow;
  long startTime;
  long deltaTime = 0;
  long stoppedTime = 0;
  boolean append = false;
  static Genome displayedGenome;
  static Tribe displayedTribe;
  static int thribesAmount = 0;
  public static int numberOfTribes = 1;
  static int[] statsArray = new int[10];
  public static boolean stop = false;


  /*******************************************************************************************************
   * Constructor for the main frame controller Gets everything started calls startGA_HC() setTimers()
   ******************************************************************************************************/
  public MainFrameController()
  {
    if(Constants.ASSERT)
    {
      new AssertTests();
    }
    frame = new MainFrame();
    frame.start();
    startTime = System.currentTimeMillis();
    new GUIActionListeners().setListeners(frame);
    startGA_HC();
    setTimers();

  }

  /*******************************************************************************************************
   * The timers the control some of the flow of the program timer statsFileTimer
   ******************************************************************************************************/
  private void setTimers()
  {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {
        timeNow = System.currentTimeMillis();
        if (paused)
        {
          startTime = System.currentTimeMillis() - deltaTime;
        }
        else if (!paused)
        {
          deltaTime = (timeNow - startTime);
          int minutes = (int) ((deltaTime) / 60000);
          int seconds = (int) ((deltaTime) * 0.001);
          seconds %= 60;
          statsArray[0] = minutes;
          statsArray[1] = seconds;
          frame.buttonPanel.setTime(minutes, seconds);
        }
      }
    }, 0, 500L);

    Timer timer2 = new Timer();
    timer2.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {
        if (!paused)
        {
          displayGenome();
        }
      }
    }, 0, 1000L);

    Timer timer3 = new Timer();
    timer3.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {
        if (!paused)
        {
          if (threads.size() > 1)
          {
            Tribe.pause();
            for (int i = 0; i < threads.size() - 1; i += 2)
            {
              Tribe t1 = threads.get(i);
              Tribe t2 = threads.get(i + 1);
              while (!t1.fullyPaused || !t1.fullyPaused);
              int div2 = Constants.TRIBE_SIZE / 2;
              for (int j = 0; j < div2; j++)
              {
                t1.genomes[j].mateWith(t2.genomes[j], t1.genomes[j + div2], t2.genomes[j + div2]);
                totalcrossovers++;
                generationspersec++;
              }
            }
          }
            totalgenerations = totalmutations + totalcrossovers;
            frame.buttonPanel.updateGUIStats(totalgenerations, totalmutations, totalcrossovers, totalgenomes,
                generationspersec);       
         
            statsArray[2] = totalgenerations;
            statsArray[3] = totalmutations;
            statsArray[4] = totalcrossovers;
            statsArray[5] = numberOfTribes; 
            statsArray[6] = totalgenomes; 
            statsArray[9] = generationspersec;
            generationspersec = 0;
        }
      }
    }, 0, 1000);

    Timer statsFileTimer = new Timer();
    // prints every 6 minutes
    statsFileTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {
        if (!paused)
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
      }
    }, 360000L, 360000L);
  }

  /*******************************************************************************************************
   * startGA_HC() Start the genetic algorithm and the hill climbing by calling birth tribe
   ******************************************************************************************************/
  private void startGA_HC()
  {
    frame.buttonPanel.disableButtons();
    frame.disableMenu();
    frame.picturePanel.setPicture("Leonardo_da_Vinci-Mona-Lisa-460x363.png");
    bi = frame.picturePanel.getCurrentPicture();
    LoadPictures.currentPicture(frame.picturePanel.getCurrentPicture());
    birthTribe();
    frame.buttonPanel.setTime(0, 0);
    frame.buttonPanel.setFitnessGenome(0, 0);
    frame.buttonPanel.setFitnessTotal(0, 0);
    frame.buttonPanel.updateGUIStats(0, 0, 0, 0, 0);
  }

  /*******************************************************************************************************
   * displayGenome() Gets called every .5 second and shows the currently selected genome in the triangle panel
   * 
   * @param g
   *******************************************************************************************************/
  public void displayGenome()
  {
    Genome bestG = null;
    synchronized (frame)
    {
      double bestfit = 10000000000L; // really big number

      int bestIndex = 0;
      if (threads.get(0).genomes != null)
      {
        for (int i = 0; i < threads.size(); i++)
        {
          Genome genome = threads.get(i).genomes[0]; // assuming index 0 is the most fit
          double current = genome.getFitness();
          if (current < bestfit)
          {
            bestfit = current;
            bestG = genome;
          }
        }
        statsArray[7] = (int) bestfit;
        frame.buttonPanel.setFitnessTotal(bestfit, bestIndex);
      }
    }
    if (displayedGenome != null)
    {
      frame.trianglePanel.displayGenome(displayedGenome);
      frame.buttonPanel.setFitnessGenome(displayedGenome.getFitness(),
          Integer.valueOf(displayedTribe.getName().substring(6).trim()));
    }
    else
    {
      frame.trianglePanel.displayGenome(bestG);
    }
  }

  /*******************************************************************************************************
   * birthTribe() Births a new tribe adds it the tribe combo box
   *******************************************************************************************************/
  static void birthTribe()
  {
    BufferedImage bImage = bi;
    System.out.println("picture size " + bImage.getWidth());

    Tribe tribe = new Tribe(bImage);
    
    tribe.start();
    tribe.setName("Tribe " + (new Integer(++thribesAmount).toString()));
    threads.add(tribe);
    frame.buttonPanel.setComboxTribe(tribe);
    totalgenomes += Constants.TRIBE_SIZE;

  }

  
  /*******************************************************************************************************
   * Kills a tribe removes it from the tribe combo box
   *******************************************************************************************************/
  static void killTribe()
  {
    threads.get(threads.size() - 1).interrupt();
    frame.buttonPanel.deleteComboxTribe(thribesAmount - 1);
    threads.remove(threads.size() - 1);
    thribesAmount--;
    totalgenomes -= Constants.TRIBE_SIZE;
  }
  
  /*******************************************************************************************************
   *  Kills a tribe 
   *******************************************************************************************************/
  static void killTribeRestart()
  {
    threads.get(threads.size() - 1).interrupt();
    threads.get(threads.size() - 1).updateTribesMembers(bi);
    //threads.remove(threads.size() - 1);
    //thribesAmount--;
    //totalgenomes -= Constants.TRIBE_SIZE;
  }

  /*******************************************************************************************************
   * restart(BufferedImage bImage, ArrayList<Integer> clist) Sets up a new picture in the triangle panel restarts all
   * the treads/tribes clears out the tribe and genome combo boxes
   * 
   * @param bImage
   * @param clist
   *******************************************************************************************************/
  static void restart(BufferedImage bImage, ArrayList<Integer> clist)
  {
    if(Constants.DEBUG)
    {
      System.out.println("Restarting GA / HC with new picture");
    }
    
    if(bImage.getWidth() != bi.getWidth() || bImage.getHeight() != bi.getHeight())
    {
      try
      {
        Thread.sleep(100);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    stop = true;
    frame.buttonPanel.deleteComboxTribeAll();
    
    for (int i = 0; i < threads.size(); i++)
    {
      killTribeRestart();
    }
    
    for (int i = 0; i < numberOfTribes; i++)
    {
      birthTribe();
    }
    stop = false;

  }

  /*******************************************************************************************************
   * getCurrentPict() gets the current picture
   * 
   * @return BufferedImage
   *******************************************************************************************************/
  public static BufferedImage getCurrentPict()
  {
    return bi;
  }
  
  /*******************************************************************************************************
   * getCurrentPict() gets the current genome on display
   * 
   * @return BufferedImage
   *******************************************************************************************************/
  public static Genome getCurrentGenome()
  {
    return displayedGenome;
  }

  /*******************************************************************************************************
   * Main starts the whole program..
   *******************************************************************************************************/
  public static void main(String[] args)
  {
    new MainFrameController();
  }
}
