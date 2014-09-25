package genome.guicode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;

/***
 * 
 * @author Adam Mitchell
 * 
 *         The control button for our program Pause, Next, Show Genome Table,
 *         Read Genome File, Write Genome File, Append Stats File, Other control
 *         elements including sliders
 *
 */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements ActionListener
{
  String[] pictures;

  JButton pause = new JButton("Pause");
  JButton next = new JButton("Next");
  JButton showTable = new JButton("Show Genome Table");
  JButton readGenome = new JButton("Read Genome File");
  JButton writeGenome = new JButton("Write Genome File");
  JButton appendStats = new JButton("Append Stats File");
  JSpinner tribeSelector = new JSpinner();
  JSlider triangleSelector = new JSlider(JSlider.HORIZONTAL,1, 20, 1);

  JComboBox<String> pictureSelector;

  public ButtonPanel()
  {
    pictures = new String[] { LoadPictures.f1.getName(), LoadPictures.f2.getName(),
        LoadPictures.f3.getName(), LoadPictures.f4.getName(), LoadPictures.f5.getName(),
        LoadPictures.f6.getName(), LoadPictures.f7.getName(), LoadPictures.f8.getName(),
        LoadPictures.f9.getName() };
    pictureSelector = new JComboBox<String>(pictures);

    this.setPreferredSize(new Dimension(1200, 200));

    this.setLayout(new GridLayout(3, 5, 30, 10));

    pictureSelector.setSelectedIndex(0);
    pictureSelector.addActionListener(this);

    
    
    triangleSelector.setMajorTickSpacing(99);
    //tribeSelector.setMinorTickSpacing(1);
    triangleSelector.setPaintTicks(true);
    triangleSelector.setPaintLabels(true);
    
    add(pictureSelector);
    add(tribeSelector);
    
    add(pause);
    add(next);
    add(showTable);
    add(readGenome);
    add(writeGenome);
    add(writeGenome);
    add(appendStats);

    this.setVisible(true);

  }

  /**
   * Main for unit testing..
   * 
   * @param args
   */

  @Override
  public void actionPerformed(ActionEvent e)
  {
    JComboBox cb = (JComboBox) e.getSource();

    String pictName = (String) cb.getSelectedItem();
    MainFrame.picturePanel.setPicture(pictName);

    System.out.println(pictName);

  }

  private void updatePicture(String pictName)
  {

  }

  public static void main(String[] args)
  {
  }
}
