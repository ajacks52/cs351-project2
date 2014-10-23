package genome.guicode;

import genome.Constants;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;

/*******************************************************************************
 * 
 * @author Adam Mitchell
 * 
 * The control button for our program Pause, Next, Show Genome Table, Read Genome File, Write Genome File, Append Stats
 * File, Other control elements including sliders
 * ********************************************************************************/
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel
{

  /**
   * All of the components to be displayed within the ButtonPanel
   */
  private JButton pauseB = new JButton("Pause");
  private JButton nextB = new JButton("Next");

  private SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 2000, 1);
  private JSpinner tribeSpinner = new JSpinner(spinnerModel);
  private JSlider triangleSlider = new JSlider(JSlider.HORIZONTAL, 0, Constants.GENOME_SIZE, Constants.GENOME_SIZE);
  private JComboBox<String> pictureComboBox;
  private JComboBox<String> genomeComboBox;
  private JComboBox<String> tribeComboBox;
  JFormattedTextField statsFileTextField = new JFormattedTextField();

  private JLabel triangleAmountL = new JLabel("Triangles 200");
  private JLabel tribeNumL = new JLabel("Tribe/Thread Amount");
  private JLabel uptimeL = new JLabel("Total Uptime min:sec 0000000");
  private JLabel genPerSecL = new JLabel("gen/sec 000000000");
  private JLabel hcGensL = new JLabel("Total Mutations 00000000000");
  private JLabel gaGensL = new JLabel("Total Crossovers 00000000000");
  private JLabel totalGenomes = new JLabel("Total Genomes 000000000");
  private JLabel totalGenerationsL = new JLabel("Total Generations 00000000000");
  private JLabel currentTribeBestL = new JLabel("Current Tribe # 0000, fit # 000000000000");
  private JLabel totalBestGeneL = new JLabel("Best Tribe # 0000, fit # 000000000000");
  private JLabel displayedTribe = new JLabel("Displayed Tribe ");
  private JLabel displayedGenome = new JLabel("Displayed Genome ");
  Vector<String> tribesCB = new Vector<String>(1000);
  Vector<String> genomesCB = new Vector<String>(2000);

  final DefaultComboBoxModel<String> modelgenomes = new DefaultComboBoxModel<String>(genomesCB);
  final DefaultComboBoxModel<String> modeltribes = new DefaultComboBoxModel<String>(tribesCB);

  /**
   * All of the values that need to be stored for information about the triangles
   */
  private int triangleAmount = 0;
  private int bestTribe = 0;
  private double fit = 0;
  private int tribeNum = 0;
  private int time = 0;
  private int gen = 0;
  private Color bgcolor = new Color(171, 170, 179);
  private Color bgcolor2 = new Color(199, 198, 207);
  private Color bgcolor3 = new Color(9, 219, 37);
  private Color bgcolor4 = new Color(199, 198, 207);

  /**
   * Constructor Just sets up the panels
   */
  public ButtonPanel()
  {

    setUpPanel();

  }

  /**
   * To be called in the constructor creates all the components and displays them at the end
   */
  private void setUpPanel()
  {

    this.setLayout(null); // Hand created layout
    Set<String> keys = LoadPictures.picturesMap.keySet();
    String[] pictures = (String[]) keys.toArray(new String[keys.size()]);
    System.out.println(LoadPictures.picturesMap);
    pictureComboBox = new JComboBox<String>(pictures);
    pictureComboBox.setSelectedIndex(0);
    genomeComboBox = new JComboBox<String>(modelgenomes);
    pictureComboBox.setSelectedIndex(0);
    // genomeComboBox.setSelectedIndex(0);
    tribeComboBox = new JComboBox<String>(modeltribes);
    // tribeComboBox.setSelectedIndex(0);

    // Layout the components

    int row0 = 5;
    int row = 41;
    int col0 = 15;
    int col = 115;

    this.setBackground(bgcolor2);
    triangleSlider.setBackground(bgcolor2);
    pictureComboBox.setBackground(bgcolor2);
    genomeComboBox.setBackground(bgcolor2);
    tribeComboBox.setBackground(bgcolor2);
    tribeSpinner.setForeground(bgcolor2);
    pauseB.setBackground(bgcolor3);
    pauseB.setContentAreaFilled(false);
    pauseB.setOpaque(true);

    Insets insets = this.getInsets();
    Dimension size;

    size = pauseB.getPreferredSize();
    pauseB.setBounds(col0, row0, size.width, size.height);
    nextB.setBounds(col, row0, size.width, size.height);

    size = pictureComboBox.getPreferredSize();
    pictureComboBox.setBounds(col * 2, row0, size.width - 5, size.height);
    // pictureComboBox.setSelectedIndex(7);

    size = tribeSpinner.getPreferredSize();
    tribeSpinner.setBounds((col * 5) + 10, row + 5, size.width - 15, size.height);

    size = triangleSlider.getPreferredSize();
    triangleSlider.setBounds(col, row + 5, size.width + 100, size.height);

    size = pictureComboBox.getPreferredSize();
    genomeComboBox.setBounds((col * 7) - 5, row0, size.width - 30, size.height);

    size = statsFileTextField.getPreferredSize();
    statsFileTextField.setBounds(col * 8, row * 3, size.width + 160, size.height);

    size = tribeNumL.getPreferredSize();
    tribeNumL.setBounds((col * 4), row + 5, size.width, size.height);
    size = triangleAmountL.getPreferredSize();
    triangleAmountL.setBounds(col0, row + 5, size.width, size.height);
    size = totalBestGeneL.getPreferredSize();
    totalBestGeneL.setBounds(col * 2, row * 2, size.width, size.height);
    size = uptimeL.getPreferredSize();
    uptimeL.setBounds(col0, row * 2, size.width, size.height);
    size = totalGenerationsL.getPreferredSize();
    totalGenerationsL.setBounds(col0, row * 3, size.width, size.height);
    size = genPerSecL.getPreferredSize();
    genPerSecL.setBounds(col * 6, row * 3, size.width, size.height);

    size = hcGensL.getPreferredSize();
    hcGensL.setBounds(col * 2, row * 3, size.width, size.height);
    size = gaGensL.getPreferredSize();
    gaGensL.setBounds(col * 4, row * 3, size.width, size.height);
    size = totalGenomes.getPreferredSize();
    totalGenomes.setBounds(col * 6, row * 2, size.width, size.height);
    size = currentTribeBestL.getPreferredSize();
    currentTribeBestL.setBounds(col * 4, row * 2, size.width, size.height);

    size = pictureComboBox.getPreferredSize();
    tribeComboBox.setBounds((col * 7) - 15, row + 5, size.width - 100, size.height);

    size = displayedTribe.getPreferredSize();
    displayedTribe.setBounds(col * 6, row + 5, size.width, size.height);

    size = displayedGenome.getPreferredSize();
    displayedGenome.setBounds(col * 6, row0, size.width, size.height);

    triangleSlider.setMinorTickSpacing(1);
    triangleSlider.setSnapToTicks(true);

    statsFileTextField.setText("name_of_stats_file.txt");

    statsFileTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        System.out.println("action");
      }
    });

    add(statsFileTextField);
    add(genomeComboBox);
    add(pictureComboBox);
    add(tribeSpinner);
    add(triangleSlider);
    add(triangleAmountL);
    add(totalBestGeneL);
    add(tribeNumL);
    add(uptimeL);
    add(totalGenerationsL);
    add(genPerSecL);
    add(pauseB);
    add(nextB);
    add(hcGensL);
    add(gaGensL);
    add(totalGenomes);
    add(currentTribeBestL);
    add(tribeComboBox);
    add(displayedTribe);
    add(displayedGenome);

    this.setPreferredSize(new Dimension(1200, 150));
    if (this.isBackgroundSet() && this.isForegroundSet())
    {
      this.setVisible(true);
    }
  }

  /**
   * 
   */
  public void setComboxGenomes(Object[] genomes)
  {
    for (int i = 0; i < genomes.length; i++)
    {
      modelgenomes.addElement(" " + i);
    }
  }

  /**
   * 
   */
  public void setComboxTribes(Object[] tribes)
  {
    for (int i = 0; i < tribes.length; i++)
    {
      modeltribes.addElement(" " + i);
    }

  }

  public void setTribeNumber(int i)
  {
    tribeNum = i;
  }

  /**
   * 
   * @param i
   */
  public void setTriangleNumber(int i)
  {
    triangleAmount = i;
    triangleAmountL.setText("Triangles " + i);
  }

  /**
   * 
   * @param d
   */
  public void setFitness(double d)
  {
    fit = d;
    totalBestGeneL.setText("Best Tribe # " + bestTribe + ", fit # " + fit);
  }

  /**
   * 
   * @return
   */
  public boolean setPause()
  {
    if (pauseB.getText().equalsIgnoreCase("pause"))
    {
      pauseB.setText("Start");
      pauseB.setBackground(bgcolor3);

      return true;
    }
    else
    {
      pauseB.setText("Pause");
      pauseB.setBackground(null);
      return false;
    }

  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public void disableButtons()
  {
    nextB.setEnabled(false);
    pictureComboBox.setEnabled(false);
    triangleSlider.setEnabled(false);
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public void enableButtons()
  {
    nextB.setEnabled(true);
    pictureComboBox.setEnabled(true);
    triangleSlider.setEnabled(true);
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public void setTime(int m, int s)
  {
    if (s < 10)
    {
      uptimeL.setText("Uptime min:sec " + m + ":0" + s);
    }
    else

      uptimeL.setText("Uptime min:sec " + m + ":" + s);
  }

  /*
   * 
   * 
   * genPerSecL = "gen/sec " hcGensL = "Total Mutations 0"; gaGensL = "Total Crossovers 0" totalGenomes =
   * "Total Genomes" totalGenerationsL = "Total Generations " currentTribeBestL = "Current Tribe # 0, Best fit # 0"
   * totalBestGeneL = "Best Overall Tribe # 0, Best fit # 0"
   */

  /***************************************************************************************************
   * 
   * @param total
   * @param hc
   * @param ga
   * @param genomes
   * @param gensPerSec
   * @param ct
   * @param ctbf
   * @param bt
   * @param btf
   **************************************************************************************************/
  public void updateGUIStats(int total, int hc, int ga, int genomes, int gensPerSec, int ct, int ctbf, int bt, int btf)
  {
    totalGenerationsL.setText("Total Generations " + total);
    genPerSecL.setText("gen/sec " + gensPerSec);
    hcGensL.setText("Total Mutations " + hc);
    gaGensL.setText("Total Crossovers " + ga);
    totalGenomes.setText("Total Genomes " + genomes);
    currentTribeBestL.setText("Current Tribe # " + ct + ", fit # " + ctbf);
    totalBestGeneL.setText("Best Tribe # " + bt + ", fit # " + btf);
  }

  /***************************************************************************************************
   * 
   **************************************************************************************************/
  public boolean getPauseState()
  {
    return (pauseB.getText().equalsIgnoreCase("start"));
  }

  /*
   * All of the actionlisteners for the delegate
   */
  public void addNextButtonActionListener(ActionListener al)
  {
    nextB.addActionListener(al);
  }

  public void addStatsFileChangedActionListener(ActionListener al)
  {
    statsFileTextField.addActionListener(al);
  }

  public void addTriangleSelectorChangeListener(ChangeListener cl)
  {
    triangleSlider.addChangeListener(cl);
  }

  public void addTribeSelectorActionListener(ChangeListener cl)
  {
    tribeSpinner.addChangeListener(cl);
  }


  public void addPicturePickerActionListener(ActionListener al)
  {
    pictureComboBox.addActionListener(al);
  }

  public void addStartPauseActionListener(ActionListener al)
  {
    pauseB.addActionListener(al);
  }
  
  
  public void addGenomeCBListener(ActionListener al)
  {
    genomeComboBox.addActionListener(al);
  }

  public void addTribeCBActionListener(ActionListener al)
  {
    tribeComboBox.addActionListener(al);
  }

  /********************************************************************************
   * Main for unit testing..
   * 
   * @param args
   ********************************************************************************/
  public static void main(String[] args)
  {
  }
}
