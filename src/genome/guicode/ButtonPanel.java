package genome.guicode;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/***
 * 
 * @author Adam Mitchell
 * 
 * The control button for our program 
 * Pause, Next, Show Genome Table, Read Genome File, Write Genome File, Append Stats File,
 * Other control elements including sliders
 *
 */
public class ButtonPanel extends JPanel
{
  ButtonPanel()
  {
    Dimension dim = new Dimension();
    
    dim.height = 200;
    //dim.width = 1100;
    
    this.setPreferredSize(dim);
    this.setVisible(true);
    this.setBackground(Color.YELLOW);
    
  }
  
  
  public static void main(String[] args)
  {
    // TODO Auto-generated method stub
  }
}
