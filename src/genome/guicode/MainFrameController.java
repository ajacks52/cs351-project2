package genome.guicode;

import genome.Constants;
import genome.logic.Fitness;
import genome.logic.PictureResize;
import genome.types.Genome;
import genome.types.Triangle;
import genome.types.Tribe;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/***********************************************************************************
 * Where are program will start
 ***********************************************************************************/
public class MainFrameController
{
  private MainFrame frame;
  private Tribe tribe;
  private static BufferedImage bi = null;
  private static BufferedImage smallBi = null;

  int width;
  int height;
  private volatile boolean running = true; // Run unless told to pause

  public MainFrameController()
  {
    // SwingUtilities.invokeLater(new MainFrame());
    frame = new MainFrame();
    frame.start(); // needs to be changed so it'll sleep

    /**
     * All the action listeners are below pauseButton, picturePicker, genomePicker, tribeSelector, triangleSelector,
     * nextButton
     */

    /**
     * pauseButton
     */
    frame.buttonPanel.addStartPauseActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        frame.buttonPanel.setPause();
        running = frame.buttonPanel.getPauseState();
      }
    });

    /**
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

    /**
     * genomePicker
     */
    frame.buttonPanel.addGenomePickerActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        System.out.println("Unimplemented method for genomePicker");
      }
    });

    /**
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

    /**
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

    /**
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

    /**
     * write genome button
     */
    frame.buttonPanel.addwriteGenomeButtonActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        WriteXMLFile.generate(new Genome(Triangle.randomGenome(200, 200, 200, frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture())), 200, 200));
      }
    });

    /**
     * read genome button
     */
    frame.buttonPanel.addreadGenomeButtonActionListener(new ActionListener() {
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
          ArrayList<Triangle> xmlArrayListTriangle = XMLParser.parser(curFile);
          //need to add xmlArrayListTriangle to a tribe.
        }
      }
    });

    /**
     * show table button
     */
    frame.buttonPanel.addshowTableButtonActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
//        JOptionPane.showMessageDialog(null, "the current stats" + " blah blah \n\n\n", "The Current Stats",
//            JOptionPane.PLAIN_MESSAGE);
        new TableStats(new Genome(Triangle.randomGenome(200, 200, 200, frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture())), 200, 200));
      }
    });

    startGA_HC();

  }

  private void startGA_HC()
  {

    frame.buttonPanel.disableButtons();
    frame.picturePanel.setPicture("triangles.png");
    bi = frame.picturePanel.getCurrentPicture();
    ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture());
    frame.picturePanel.setColorList(colorList);
    smallBi = PictureResize.resize(bi, Constants.RESIZED_PICTURE_SIZE, Constants.RESIZED_PICTURE_SIZE);
    LoadPictures.currentPicture(frame.picturePanel.getCurrentPicture());

    birthTribe(bi, colorList);

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run()
      {
        if (!tribe.isInterrupted())
        {
          synchronized (tribe)
          {
            System.out.println("\tbest genome's hashcode " + tribe.genomes.get(0).hashCode());
            if (!tribe.doneSorting)
            {
              displayGenome(tribe.genomes.get(0));
            }

          }
        }
      }
    }, 0, 1000L);
  }

  public void displayGenome(Genome g)
  {
    synchronized (frame)
    {
      tribe.quickSortGenomes();
      frame.trianglePanel.displayGenome(g);
      frame.buttonPanel.setFitness(Fitness.getFitness(frame.picturePanel.getCurrentPicture(), g));
    }
  }

  private void birthTribe(BufferedImage bImage, ArrayList<Integer> clist)
  {
    tribe = new Tribe("Tribe 1", bImage.getWidth(), bImage.getHeight(), bImage, clist);
    tribe.start();
  }

  private void killTribe()
  {
    tribe.interrupt();
  }

  private void restart(BufferedImage bImage, ArrayList<Integer> clist)
  {
    System.out.println("Restarting GA / HC with new picture");
    killTribe();
    birthTribe(bImage, clist);
  }

  public static BufferedImage getCurrentPict()
  {
    return bi;
  }

  public static BufferedImage getresizedPict()
  {
    return smallBi;
  }

  public static void main(String[] args)
  {
    new MainFrameController();
  }
}
