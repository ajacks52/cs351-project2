package genome.guicode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

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

  JButton startB = new JButton();
  JButton endB = new JButton();
  JButton nextB = new JButton();
  JButton showTableB = new JButton();
  JButton writeGenomeFileB = new JButton();
  JButton appendStatsFile = new JButton();
  JComboBox<String> pictureSelector;
  JSlider tribeSelector = new JSlider();

  public ButtonPanel(File f1, File f2, File f3, File f4, File f5, File f6, File f7, File f8)
  {
    pictures = new String[] { f1.getName(), f2.getName(), f3.getName(), f4.getName(), f5.getName(),
        f6.getName(), f7.getName(), f8.getName() };
    pictureSelector = new JComboBox<String>(pictures);

    this.setPreferredSize(new Dimension(1200, 200));
    this.setVisible(true);
    // this.setBackground(Color.YELLOW);
    pictureSelector.setSelectedIndex(0);
    pictureSelector.addActionListener(this);
    add(pictureSelector, BorderLayout.PAGE_START);

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
    updatePicture(pictName);
  }

  private void updatePicture(String pictName)
  {

  }

  public static void main(String[] args)
  {
  }
}
