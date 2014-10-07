package genome.guicode;

import genome.logic.Fitness;
import genome.types.Triangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/***********************************************************************************
 * Where are program will start
 ***********************************************************************************/
public class MainFrameController
{
  public static void main(String[] args)
  {
    //SwingUtilities.invokeLater(new MainFrame());
    MainFrame frame = new MainFrame();
    
    frame.start();  // needs to be changed so it'll sleep 
    
//    while (frame.buttonPanel == null); // wait until its setup
    
    synchronized (frame) 
    {
      System.out.println("in synchronized");
      /**
       * picturePicker
       */
      frame.buttonPanel.addPicturePickerActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          @SuppressWarnings("unchecked")
          JComboBox<String> cb = (JComboBox<String>) e.getSource();
          String pictName = (String) cb.getSelectedItem();
          frame.picturePanel.setPicture(pictName);
          frame.trianglePanel.displayTriangles(
              Triangle.randomGenome(200, frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight(), frame.picturePanel.getColorList()),
              frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight());
          System.out.println("Selected picture: " + pictName);
        }
      });
      
      /**
       * genomePicker
       */
      frame.buttonPanel.addGenomePickerActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("Unimplemented method for genomePicker");
        } 
      });
      
      /**
       * tribeSelector
       */
      frame.buttonPanel.addTribeSelectorActionListener(new ChangeListener()
      {
        @Override
        public void stateChanged(ChangeEvent e)
        {
          System.out.println("tribeSelector");
          int value = (int) ((JSpinner) e.getSource()).getValue();
          frame.buttonPanel.setTribeNumber(value);
          frame.trianglePanel.displayTriangles(
              Triangle.randomGenome(200, frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight(), frame.picturePanel.getColorList()),
              frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight());
        }
      });
      
      /**
       * triangleSelector
       */
      frame.buttonPanel.addTriangleSelectorChangeListener(new ChangeListener()
      {
        @Override
        public void stateChanged(ChangeEvent e)
        {
//          System.out.println("triangleSelector");
          int value = (int) ((JSlider) e.getSource()).getValue();
          frame.buttonPanel.setTriangleNumber(value);
          frame.trianglePanel.setTriangleCount(value);
        }
      });
      
      /**
       * nextButton
       */
      frame.buttonPanel.addNextButtonActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          frame.trianglePanel.displayTriangles(
              Triangle.randomGenome(200, frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight(), frame.picturePanel.getColorList()),
              frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight());
          frame.buttonPanel.setFitness(Fitness.getFitness(frame.picturePanel.getCurrentPicture(), frame.trianglePanel.getBufferedImage()));
        }
      });
      
      int width = frame.picturePanel.getCurrentPicture().getWidth();
      int height = frame.picturePanel.getCurrentPicture().getHeight();
      ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(LoadPictures.bImage1);
      
      frame.trianglePanel.displayTriangles(Triangle.randomGenome(200, width, height, colorList), width, height);
      
      long fitness = Fitness.getFitness(frame.picturePanel.getCurrentPicture(), frame.trianglePanel.getBufferedImage());
      frame.buttonPanel.setFitness(fitness);
    }
    
    
    
    
  }
}
