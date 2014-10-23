package genome.guicode;

import genome.Constants;
import genome.logic.PictureResize;
import genome.types.Genome;
import genome.types.Triangle;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/***************************************************************************************************
 * 
 **************************************************************************************************/
public class GUIActionListeners
{
  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public GUIActionListeners()
  {
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public void setListeners(MainFrame frame)
  {
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
        if (frame.buttonPanel.setPause())
        {
          frame.buttonPanel.enableButtons();
          frame.enableMenu();
        }
        else
        {
          frame.buttonPanel.disableButtons();
          frame.disableMenu();
        }
        MainFrameController.paused = frame.buttonPanel.getPauseState();
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
        MainFrameController.bi = frame.picturePanel.getCurrentPicture();
        MainFrameController.smallBi = PictureResize.resize(MainFrameController.bi, Constants.RESIZED_PICTURE_SIZE,
            Constants.RESIZED_PICTURE_SIZE);

        ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(MainFrameController.smallBi);
        // frame.picturePanel.setColorList(colorList);
        LoadPictures.currentPicture(MainFrameController.bi);

        MainFrameController.restart(MainFrameController.bi, colorList);
        System.out.println("Selected picture: " + pictName);
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
        if (value > MainFrameController.numberOfTribes)
        {
          MainFrameController.birthTribe();
        }
        else
        {
          MainFrameController.killTribe();
        }
        MainFrameController.numberOfTribes = value;

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
        // Genome g = Genome.randomGenome(frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel
        // .getCurrentPicture().getHeight());
        // frame.trianglePanel.displayGenome(g);
        // frame.buttonPanel.setFitness(g.getFitness(frame.picturePanel.getCurrentPicture(), 5));
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
//        new WriteXMLFile().generate();
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
          // Genome g = Genome.randomGenome(frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel
          // .getCurrentPicture().getHeight());
          // frame.trianglePanel.displayGenome(g);
          // frame.buttonPanel.setFitness(g.getFitness(frame.picturePanel.getCurrentPicture(), 5));

        }
        chooser.setCurrentDirectory(f);
        chooser.showOpenDialog(null);
        File curFile = chooser.getSelectedFile();
        if (!(curFile == null))
        {
          System.out.println(curFile.toString());
          ArrayList<Triangle> xmlArrayListTriangle = new XMLParser().parser(curFile);
          if (xmlArrayListTriangle == null)
          {
            JOptionPane.showMessageDialog(null,
                "Sorry the file you selected was not in the correct xml format we expected"
                    + "\n\n click ok to continue");
          }
          else
          {
            // TODO need make a new genome with the arraylist xmlArrayListTriangle and add it to a tribe..
          }
        }
      }
    });

    int width = frame.picturePanel.getCurrentPicture().getWidth();
    int height = frame.picturePanel.getCurrentPicture().getHeight();
    ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(LoadPictures.bImage1);

    /**
     * show table
     */
    frame.addshowTableActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        // TODO need to pass in the current genome not just a random one
//        new TableStats().showTableData(new Genome(Triangle.randomGenome(200, 200, 200,
//            frame.picturePanel.pictureColorValues(frame.picturePanel.getCurrentPicture())), 200, 200));
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
        JOptionPane.showMessageDialog(null, "Message", "File saved", JOptionPane.INFORMATION_MESSAGE);
        try
        {
          MainFrameController.statsfileName = frame.buttonPanel.statsFileTextField.getText();
          new PrintStatsFile(MainFrameController.statsfileName).writeToFile(false, MainFrameController.statsArray);
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    });

    /*
     * 
     */
    frame.buttonPanel.addTribeCBActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        @SuppressWarnings("unchecked")
        JComboBox<String> cb = (JComboBox<String>) e.getSource();

        // if (cb.getSelectedItem() != null)
        // {

        String currentTribe = (String) cb.getSelectedItem();
        String subString = currentTribe.substring(5);
        System.out.println(subString);
        int index = Integer.parseInt(subString.trim());
        frame.buttonPanel.setComboxGenomes(MainFrameController.threads.get(index - 1).genomes, (index - 1));
        MainFrameController.displayedTribe = MainFrameController.threads.get(index - 1);
        System.out.println("Selected tribe: " + currentTribe);
        // }
      }
    });

    /*
     * 
     * 
     */
    frame.buttonPanel.addGenomeCBListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        @SuppressWarnings("unchecked")
        JComboBox<String> cb = (JComboBox<String>) e.getSource();

        if (cb.getSelectedItem() != null)
        {

          String currentGenome = (String) cb.getSelectedItem();

          System.out.println(currentGenome + "\n01234567890123456789012345");

          String subString1 = currentGenome.substring(5, 9);
          String subString2 = currentGenome.substring(16, 19);
          int index1 = Integer.parseInt(subString2.trim());
          int index2 = Integer.parseInt(subString1.trim());
          MainFrameController.displayedGenome = MainFrameController.threads.get(index2-1).genomes[index1 - 1];
          frame.trianglePanel.displayGenome(MainFrameController.displayedGenome);
          System.out.println("Selected tribe: " + currentGenome);
        }
      }
    });

  }
}
