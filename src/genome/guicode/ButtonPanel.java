package genome.guicode;

import genome.logic.Fitness;
import genome.types.Triangle;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.util.Set;

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

  /**
   * All of the components to be displayed within the ButtonPanel
   */
  private JButton pause = new JButton("Pause");
  private JButton next = new JButton("Next");
  private JButton showTable = new JButton("Show Genome Table");
  private JButton readGenome = new JButton("Read Genome File");
  private JButton writeGenome = new JButton("Write Genome File");
  private JButton appendStats = new JButton("Append Stats File");
  private SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 2000, 1);
  private JSpinner tribeSelector = new JSpinner(spinnerModel);
  private JSlider triangleSelector = new JSlider(JSlider.HORIZONTAL, 0, 200, 200);
  private JComboBox<String> pictureSelector;
  private JComboBox<String> genomePicker;
  private JTextField statsFile = new JTextField();

  private JLabel triangleAmountL = new JLabel("Triangle #: 200");
  private JLabel bestTribeL = new JLabel("Best Tribe # 0, fit # 0");
  private JLabel tribeNumL = new JLabel("Tribe #: 1");
  private JLabel timeL = new JLabel("min:sec ");
  private JLabel genNumL = new JLabel("gen ");
  private JLabel genPerSecL = new JLabel("gen/sec ");
  
  /**
   * All of the values that need to be stored for information about the triangles
   */
  private int triangleAmount = 0;
  private int bestTribe = 0;
  private long fit = 0;
  private int tribeNum = 0;
  private int time = 0;
  private int gen = 0;
  private int genPerSec = 0;


  /**
   * Constructor
   * Just sets up the panels
   */
  public ButtonPanel()
  {

    setUpPanel();

  }

  /**
   * To be called in the constructor
   * creates all the components and displays them at the end
   */
  private void setUpPanel()
  {
    this.setLayout(null); // Hand created layout

    Set<String> keys = LoadPictures.picturesMap.keySet();
    String[] pictures = (String[]) keys.toArray(new String[keys.size()]);

    pictureSelector = new JComboBox<String>(pictures);
    pictureSelector.setSelectedIndex(7);
    String[] genomes = new String[2000];
    genomePicker = new JComboBox<String>(genomes);
    pictureSelector.setSelectedIndex(0);
    genomePicker.setSelectedIndex(0);

    
    // ActionListeners
    
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

    
    // Layout the components
    
    int row1 = 5;
    int row2 = 60;
    int row3 = 110;

    Insets insets = this.getInsets();
    Dimension bsize = showTable.getPreferredSize();
    Dimension size;
    int bsizeX = (int) bsize.getWidth() + 15;

    size = pictureSelector.getPreferredSize();
    pictureSelector.setBounds(25 + insets.left, row1 + insets.top, size.width - 30, size.height + 15);
    pictureSelector.setSelectedIndex(7);

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

    triangleAmountL.setBounds(  535 + insets.left,  row1 + insets.top+5, size.width,    size.height);
    tribeNumL.setBounds(        370 + insets.left,  row1 + insets.top+5, size.width,    size.height);
    bestTribeL.setBounds(       880 + insets.left,  row1 + insets.top+5, size.width+20, size.height);
    timeL.setBounds(            50 + insets.left,   row3 + insets.top+5, size.width,    size.height);
    genNumL.setBounds(          200 + insets.left,  row3 + insets.top+5, size.width,    size.height);
    genPerSecL.setBounds(       350 + insets.left,  row3 + insets.top+5, size.width,    size.height);

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
