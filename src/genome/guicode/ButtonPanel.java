package genome.guicode;

import genome.Constants;
import genome.types.Genome;
import genome.types.Tribe;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

/*******************************************************************************************************
 * 
 * @author Adam Mitchell
 * 
 * The controller for the buttons and drop-down menus in our program Buttons/menu items here are Pause, Next, Show
 * Genome Table, Read Genome File, Write Genome File, Append Stats File, Other control elements including sliders
 ********************************************************************************************************/
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel
{

  /**
   * All of the components to be displayed within the ButtonPanel
   */
  private JButton pauseB = new JButton("Pause");
  private JButton nextB = new JButton("Next");
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
  private SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 2000, 1);
  private JSpinner tribeSpinner = new JSpinner(spinnerModel);
  private JSlider triangleSlider = new JSlider(JSlider.HORIZONTAL, 0, Constants.GENOME_SIZE, Constants.GENOME_SIZE);
  private JComboBox<String> pictureComboBox;
  private JComboBox<String> genomeComboBox;
  private JComboBox<String> tribeComboBox;
  private Vector<String> tribesCB = new Vector<String>();
  private Vector<String> genomesCB = new Vector<String>();
  private DefaultComboBoxModel<String> modelgenomes = new DefaultComboBoxModel<String>(genomesCB);
  private DefaultComboBoxModel<String> modeltribes = new DefaultComboBoxModel<String>(tribesCB);
  JFormattedTextField statsFileTextField = new JFormattedTextField();

  private Color buttonPanelColor = new Color(199, 198, 207);
  private Color pauseButtonColor = new Color(9, 219, 37);

  /*******************************************************************************************************
   * Constructor Just sets up the panels
   *******************************************************************************************************/
  public ButtonPanel()
  {
    setUpPanel();
  }

  /********************************************************************************************************
   * To be called by the constructor creates all the components and displays them at the end
   ********************************************************************************************************/
  private void setUpPanel()
  {
    this.setLayout(null); // Hand created layout
    if (Constants.DEBUG)
    {
      System.out.println(LoadPictures.picturesMap);
    }
    Set<String> keys = LoadPictures.picturesMap.keySet();
    String[] pictures = (String[]) keys.toArray(new String[keys.size()]);
    pictureComboBox = new JComboBox<String>(pictures);
    pictureComboBox.setSelectedIndex(0);
    genomeComboBox = new JComboBox<String>(modelgenomes);
    pictureComboBox.setSelectedIndex(0);
    tribeComboBox = new JComboBox<String>(modeltribes);

    // Colors of the panel
    this.setBackground(buttonPanelColor);
    triangleSlider.setBackground(buttonPanelColor);
    pictureComboBox.setBackground(buttonPanelColor);
    genomeComboBox.setBackground(buttonPanelColor);
    tribeComboBox.setBackground(buttonPanelColor);
    tribeSpinner.setForeground(buttonPanelColor);
    pauseB.setBackground(pauseButtonColor);
    pauseB.setContentAreaFilled(false);
    pauseB.setOpaque(true);
    statsFileTextField.setText("newStatsFile.txt");

    // Layout the components
    int row0 = 5;
    int row = 41;
    int col0 = 15;
    int col = 115;
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

  /*********************************************************************************************************
   * public void setComboxGenomes(Genome[] genomes, int index) Set the genomes in the genome combo box
   * 
   * @param genomes
   * []
   * @param index
   * int
   ********************************************************************************************************/
  public void setComboxGenomes(Genome[] genomes, int index)
  {
    deleteComboxGenomes();
    for (int i = 0; i < genomes.length; i++)
    {
      modelgenomes.addElement("Tribe " + (index + 1) + "   Genome " + (i + 1) + "   fitness: " + " "
          + new Double(genomes[i].getFitness()).toString() + i);
    }
  }

  /*********************************************************************************************************
   * public void setComboxTribe(Tribe tribe) Set the new tribe in the tribe combo box
   * 
   * @param Tribe
   * tribe
   ********************************************************************************************************/
  public void setComboxTribe(Tribe tribe)
  {
    modeltribes.addElement(tribe.getName());
  }

  /*********************************************************************************************************
   * deletes a tribe in the tribe combo box last get deleted first
   * 
   * deleteComboxTribe(int index)
   * 
   * @param int index
   ********************************************************************************************************/
  public void deleteComboxTribe(int index)
  {
    if (Constants.DEBUG)
    {
      System.out.println("*******" + index + "********");
    }

    modeltribes.removeElementAt(index);;
    deleteComboxGenomes();
  }

  /*********************************************************************************************************
   * public void deleteComboxGenomes()
   * 
   ********************************************************************************************************/
  public void deleteComboxGenomes()
  {
    modelgenomes.removeAllElements();
  }

  /*********************************************************************************************************
   * Sets the triangle amount value to the gui public void setTriangleNumber(int i)
   * 
   * @param i
   ********************************************************************************************************/
  public void setTriangleNumber(int i)
  {
    triangleAmountL.setText("Triangles " + i);
  }

  /*********************************************************************************************************
   * Sets the fitness value to the gui public void setFitnessTotal(double bestFit, int bestT)
   * 
   * @param bestFit
   * @param bestT
   ********************************************************************************************************/
  public void setFitnessTotal(double bestFit, int bestT)
  {
    totalBestGeneL.setText("Best Tribe # " + bestT + ", fit # " + bestFit);
  }

  /*********************************************************************************************************
   * public void setFitnessGenome(double genomeFit, int currentT)
   * 
   * @param genomeFit
   * @param currentT
   ********************************************************************************************************/
  public void setFitnessGenome(double genomeFit, int currentT)
  {
    currentTribeBestL.setText("Current Tribe # " + currentT + ", fit # " + genomeFit);
  }

  /*********************************************************************************************************
   * Sets the game in paused state public boolean setPause()
   * 
   * @return boolean
   ********************************************************************************************************/
  public boolean setPause()
  {
    if (pauseB.getText().equalsIgnoreCase("pause"))
    {
      pauseB.setText("Start");
      pauseB.setBackground(pauseButtonColor);

      return true;
    }
    else
    {
      pauseB.setText("Pause");
      pauseB.setBackground(null);
      return false;
    }

  }

  /*********************************************************************************************************
   * disables the buttons / menus public void disableButtons()
   *********************************************************************************************************/
  public void disableButtons()
  {
    nextB.setEnabled(false);
    pictureComboBox.setEnabled(false);
    triangleSlider.setEnabled(false);
  }

  /********************************************************************************************************
   * enables the buttons / menus public void enableButtons()
   *******************************************************************************************************/
  public void enableButtons()
  {
    nextB.setEnabled(true);
    pictureComboBox.setEnabled(true);
    triangleSlider.setEnabled(true);
  }

  /********************************************************************************************************
   * Sets the gui's time public void setTime(int m, int s)
   ********************************************************************************************************/
  public void setTime(int m, int s)
  {
    if (s < 10)
    {
      uptimeL.setText("Uptime min:sec " + m + ":0" + s);
    }
    else

      uptimeL.setText("Uptime min:sec " + m + ":" + s);
  }

  /***************************************************************************************************
   * Updates the gui with the current stats public void updateGUIStats(int total, int hc, int ga, int genomes, int
   * gensPerSec)
   * 
   * @param total
   * @param hc
   * @param ga
   * @param genomes
   * @param gensPerSec
   **************************************************************************************************/
  public void updateGUIStats(int total, int hc, int ga, int genomes, int gensPerSec)
  {
    totalGenerationsL.setText("Total Generations " + total);
    genPerSecL.setText("gen/sec " + gensPerSec);
    hcGensL.setText("Total Mutations " + hc);
    gaGensL.setText("Total Crossovers " + ga);
    totalGenomes.setText("Total Genomes " + genomes);
  }

  /***************************************************************************************************
   * Returns the state of the pause button public boolean getPauseState()
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
}
