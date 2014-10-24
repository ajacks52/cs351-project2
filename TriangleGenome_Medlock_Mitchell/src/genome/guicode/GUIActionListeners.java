package genome.guicode;

import genome.Constants;
import genome.types.Genome;
import genome.types.Triangle;
import genome.types.Tribe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/***************************************************************************************************
 * @author Adam Mitchell Simple class that holds all the actionlisteners used in the program
 **************************************************************************************************/
public class GUIActionListeners
{
  /***************************************************************************************************
   * Constructor called in MainFrameController
   **************************************************************************************************/
  public GUIActionListeners()
  {
  }

  /***************************************************************************************************
   * Simple class that holds all the actionlisteners used in the program
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
          Tribe.pause();
          frame.buttonPanel.enableButtons();
          frame.enableMenu();
        }
        else
        {
          Tribe.unpause();
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
        ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(MainFrameController.bi);
        LoadPictures.currentPicture(MainFrameController.bi);
        MainFrameController.restart(MainFrameController.bi, colorList);
        if (Constants.DEBUG)
        {
          System.out.println("Selected picture: " + pictName);
        }
      }
    });

    /*
     * tribeSelector
     */
    frame.buttonPanel.addTribeSelectorActionListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e)
      {
        int value = (int) ((JSpinner) e.getSource()).getValue();
        if (value > MainFrameController.numberOfTribes)
        {
          MainFrameController.birthTribe();
        }
        else
        {
          MainFrameController.killTribe();
        }
        MainFrameController.numberOfTribes = value;
      }
    });

    /*
     * triangleSelector
     */
    frame.buttonPanel.addTriangleSelectorChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e)
      {
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
        Tribe.next();
      }
    });

    /*
     * write genome
     */
    frame.addwriteGenomeActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        new WriteXMLFile().generate(MainFrameController.getCurrentGenome());
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
          if (Constants.DEBUG)
          {
            System.out.println(curFile.toString());
          }
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
            Genome g = new Genome(Genome.currentImage);
            g.triangles = (Triangle[])xmlArrayListTriangle.toArray();
            Tribe t = new Tribe(Genome.currentImage);
            t.genomes[0] = g;
            MainFrameController.threads.add(t);
          }
        }
      }
    });

    /**
     * show table
     */
    frame.addshowTableActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        new TableStats().showTableData(MainFrameController.getCurrentGenome());
      }
    });

    /*
     * Append Stats
     */
    frame.addAppendStatsActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
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
     * Tribe combo box action listener
     */
    frame.buttonPanel.addTribeCBActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        @SuppressWarnings("unchecked")
        JComboBox<String> cb = (JComboBox<String>) e.getSource();

        String currentTribe = (String) cb.getSelectedItem();
        if (currentTribe != null)
        {
          currentTribe = (String) cb.getSelectedItem();
          String subString = currentTribe.substring(5);
          int index = Integer.parseInt(subString.trim());
          frame.buttonPanel.setComboxGenomes(MainFrameController.threads.get(index - 1).genomes, (index - 1));
          MainFrameController.displayedTribe = MainFrameController.threads.get(index - 1);
          if (Constants.DEBUG)
          {
            System.out.println("Selected tribe: " + currentTribe);
          }
        }
      }
    });

    /*
     * Genome combo box action listener
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
          String subString1 = currentGenome.substring(5, 9);
          String subString2 = currentGenome.substring(16, 19);
          int index1 = Integer.parseInt(subString2.trim());
          int index2 = Integer.parseInt(subString1.trim());
          MainFrameController.displayedGenome = MainFrameController.threads.get(index2 - 1).genomes[index1 - 1];
          frame.trianglePanel.displayGenome(MainFrameController.displayedGenome);
          if (Constants.DEBUG)
          {
            System.out.println("Selected tribe: " + currentGenome);
          }
        }
      }
    });

  }
}
