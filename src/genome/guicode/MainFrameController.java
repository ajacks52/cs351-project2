package genome.guicode;

import javax.swing.SwingUtilities;


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
    
    
  }
}
