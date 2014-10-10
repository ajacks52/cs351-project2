package genome.guicode;

import genome.logic.Fitness;
import genome.types.Genome;
import genome.types.Triangle;
import genome.types.Tribe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
  private MainFrame frame;
  private Tribe tribe;
  
  public MainFrameController()
  {
  //SwingUtilities.invokeLater(new MainFrame());
    frame = new MainFrame();
    
    frame.start();  // needs to be changed so it'll sleep 
    
    synchronized (frame) 
    {
      
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
//          System.out.println("tribeSelector");
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
          Genome g = Genome.randomGenome(frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight());
          frame.trianglePanel.displayGenome(g);
          frame.buttonPanel.setFitness(Fitness.getFitness(frame.picturePanel.getCurrentPicture(), g));
        }
      });
      
      /**
       * pauseButton
       */
      frame.buttonPanel.addStartPauseActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          // TODO Auto-generated method stub
          
        }
      });
      
      int width = frame.picturePanel.getCurrentPicture().getWidth();
      int height = frame.picturePanel.getCurrentPicture().getHeight();
      ArrayList<Integer> colorList = frame.picturePanel.pictureColorValues(LoadPictures.bImage1);
      
      Genome g = Genome.randomGenome(width, height);
      
      frame.trianglePanel.displayGenome(g);
      
      long fitness = Fitness.getFitness(frame.picturePanel.getCurrentPicture(), g);
      frame.buttonPanel.setFitness(fitness);

      tribe = new Tribe("Tribe 1", frame.picturePanel.getCurrentPicture().getWidth(), frame.picturePanel.getCurrentPicture().getHeight(), frame.picturePanel.getCurrentPicture());
      tribe.start();
      
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask()
      {
        @Override
        public void run()
        {
          synchronized (tribe) 
          {
            displayGenome(tribe.genomes.get(0));
          }
        }
      }, 0, 500L);
    }

  }
  
  public void displayGenome(Genome g)
  {
    synchronized(frame)
    {
      frame.trianglePanel.displayGenome(g);
      frame.buttonPanel.setFitness(Fitness.getFitness(frame.picturePanel.getCurrentPicture(), g));
    }
  }
  
  
  public static void main(String[] args)
  {
    new MainFrameController(); 
  }
}
