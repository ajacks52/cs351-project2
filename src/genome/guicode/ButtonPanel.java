package genome.guicode;

import genome.logic.Fitness;
import genome.types.Triangle;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/***
 * 
 * @author Adam Mitchell
 * 
 * The control button for our program Pause, Next, Show Genome Table, Read
 * Genome File, Write Genome File, Append Stats File, Other control elements
 * including sliders
 * */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel
{

  JButton pause = new JButton("Pause");
  JButton next = new JButton("Next");
  JButton showTable = new JButton("Show Genome Table");
  JButton readGenome = new JButton("Read Genome File");
  JButton writeGenome = new JButton("Write Genome File");
  JButton appendStats = new JButton("Append Stats File");
  SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 2000, 1);
  JSpinner tribeSelector = new JSpinner(spinnerModel);
  JSlider triangleSelector = new JSlider(JSlider.HORIZONTAL, 0, 200, 200);
  JComboBox<String> pictureSelector;
  JComboBox<String> genomePicker;
  JTextField statsFile = new JTextField();

  int triangleAmount = 0;
  int bestTribe = 0;
  long fit = 0;
  int tribeNum = 0;
  int time = 0;
  int gen = 0;
  int genPerSec = 0;

  JLabel triangleAmountL = new JLabel("Triangle #: 200");
  JLabel bestTribeL = new JLabel("Best Tribe # " + bestTribe + ", fit # " + fit);
  JLabel tribeNumL = new JLabel("Tribe " + 1);
  JLabel timeL = new JLabel("min:sec ");
  JLabel genNumL = new JLabel("gen ");
  JLabel genPerSecL = new JLabel("gen/sec ");

  public ButtonPanel()
  {

    setUpPanel();

  }

  private void setUpPanel()
  {
    this.setLayout(null);

    String[] pictures = new String[] { LoadPictures.f1.getName(), LoadPictures.f2.getName(),
        LoadPictures.f3.getName(), LoadPictures.f4.getName(), LoadPictures.f5.getName(),
        LoadPictures.f6.getName(), LoadPictures.f7.getName(), LoadPictures.f8.getName(),
        LoadPictures.f9.getName() };

    pictureSelector = new JComboBox<String>(pictures);
    String[] genomes = new String[2000];
    genomePicker = new JComboBox<String>(genomes);
    pictureSelector.setSelectedIndex(0);
    genomePicker.setSelectedIndex(0);

    /***
     * pictureSelector
     */
    pictureSelector.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String pictName = (String) cb.getSelectedItem();
        MainFrame.picturePanel.setPicture(pictName);
        System.out.println(pictName);
      }
    });

    /**
     * genomePicker
     */
    genomePicker.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        System.out.println("action");
      }
    });

    /**
     * tribeSelectorListener
     */
    ChangeListener tribeSelectorListener = new ChangeListener() {
      public void stateChanged(ChangeEvent e)
      {

        int value = (int) tribeSelector.getValue();
        tribeNumL.setText(("Tribe #: " + value));
        MainFrame.trianglePanel.displayTriangles(
            Triangle.randomGenome(200, PicturePanel.getCurrentPicture().getWidth(), PicturePanel.getCurrentPicture().getHeight()),
            PicturePanel.getCurrentPicture().getWidth(), PicturePanel.getCurrentPicture().getHeight());
      }
    };

    /**
     * triangleSelectorListener
     */
    ChangeListener triangleSelectorListener = new ChangeListener() {
      public void stateChanged(ChangeEvent e)
      {

        int value = (int) triangleSelector.getValue();
        triangleAmountL.setText(("Triangle #: " + value));
        MainFrame.trianglePanel.setTriangleCount(value);

      }
    };
    
    /**
     * Next button
     */
    next.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        MainFrame.trianglePanel.displayTriangles(
            Triangle.randomGenome(200, PicturePanel.getCurrentPicture().getWidth(), PicturePanel.getCurrentPicture().getHeight()),
            PicturePanel.getCurrentPicture().getWidth(), PicturePanel.getCurrentPicture().getHeight());
        fit = Fitness.getFitness(PicturePanel.getCurrentPicture(), MainFrame.trianglePanel.getBufferedImage());
        bestTribeL.setText("Best Tribe # " + bestTribe + ", fit # " + fit);
      }
    });

    
    tribeSelector.addChangeListener(tribeSelectorListener);
    triangleSelector.addChangeListener(triangleSelectorListener);

    int row1 = 5;
    int row2 = 70;
    int row3 = 120;

    Insets insets = this.getInsets();
    Dimension bsize = showTable.getPreferredSize();
    Dimension size;
    int bsizeX = (int) bsize.getWidth() + 15;

    size = pictureSelector.getPreferredSize();
    pictureSelector.setBounds(25 + insets.left, row1 + insets.top, size.width + 5, size.height + 15);

    size = tribeSelector.getPreferredSize();
    tribeSelector.setBounds(445 + insets.left, row1 + insets.top, size.width + 5, size.height + 15);

    size = triangleSelector.getPreferredSize();
    triangleSelector.setBounds(620 + insets.left, row1 + insets.top, size.width + 60, size.height + 20);

    size = bsize;
    pause.setBounds(25 + insets.left, row2 + insets.top, size.width, size.height);
    next.setBounds(bsizeX + 30 + insets.left, row2 + insets.top, size.width, size.height);
    showTable.setBounds(bsizeX * 2 + 30 + insets.left, row2 + insets.top, size.width, size.height);
    readGenome.setBounds(bsizeX * 3 + 30 + insets.left, row2 + insets.top, size.width, size.height);
    writeGenome.setBounds(bsizeX * 4 + 30 + insets.left, row2 + insets.top, size.width, size.height);
    appendStats.setBounds(bsizeX * 5 + 30 + insets.left, row2 + insets.top, size.width, size.height);
    genomePicker.setBounds(bsizeX * 3 + 30 + insets.left, row3 + insets.top, size.width + 160,
        size.height);
    statsFile.setBounds(bsizeX * 5 + 30 + insets.left, row3 + insets.top, size.width, size.height);

    triangleAmountL.setBounds(535 + insets.left, row1 + insets.top, size.width, size.height);
    tribeNumL.setBounds(370 + insets.left, row1 + insets.top, size.width, size.height);
    bestTribeL.setBounds(880 + insets.left, row1 + insets.top, size.width+20, size.height);
    timeL.setBounds(50 + insets.left, row3 + insets.top, size.width, size.height);
    genNumL.setBounds(200 + insets.left, row3 + insets.top, size.width, size.height);
    genPerSecL.setBounds(350 + insets.left, row3 + insets.top, size.width, size.height);

    triangleSelector.setMinorTickSpacing(1);
    triangleSelector.setPaintTicks(true);
    triangleSelector.setSnapToTicks(true);

    statsFile.setText("Type Text Here");
    statsFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        System.out.println("action");
      }
    });

    add(statsFile);
    add(genomePicker);
    add(pictureSelector);
    add(tribeSelector);
    add(triangleSelector);
    add(triangleAmountL);
    add(bestTribeL);
    add(tribeNumL);
    add(timeL);
    add(genNumL);
    add(genPerSecL);
    add(pause);
    add(next);
    add(showTable);
    add(readGenome);
    add(writeGenome);
    add(appendStats);

    this.setPreferredSize(new Dimension(1200, 150));
    if (this.isBackgroundSet() && this.isForegroundSet())
    {
      this.setVisible(true);
    }

  }

  /**
   * Main for unit testing..
   * 
   * @param args
   */
  public static void main(String[] args)
  {
  }
}
