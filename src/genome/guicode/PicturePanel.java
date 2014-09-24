package genome.guicode;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/***
 * 
 * @author Adam Mitchell
 * 
 * Displays the pictures that our algorithms will try to emulate
 */
public class PicturePanel extends JPanel
{
  
  public PicturePanel()
  {
    Dimension dim = new Dimension();
    dim.height = 400;
    dim.width = 450;
    
    this.setMinimumSize(dim);
    this.setVisible(true);
    this.setBackground(new Color(255, 255, 255));
    
  }
  
  
  public static void main(String[] args)
  {

  }
}
