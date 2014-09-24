package genome.guicode;

import genome.types.Triangle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

/***
 * 
 * @author Adam Mitchell
 * 
 *         Displays the pictures that our algorithms will try to emulate
 */
public class PicturePanel extends JPanel
{
  private int height;
  private int width;
  // private static Graphics2D canvas;
  private BufferedImage offScreenImage;
  private BufferedImage bImage;

  
  public PicturePanel(int x, int y, BufferedImage bImage)
  {
    this.bImage = bImage;
    this.height = y;
    this.width = x;
    this.setPreferredSize(new Dimension(width, height));
    this.setVisible(true);
    // this.setBorder(new CompoundBorder(new EtchedBorder(), new
    // LineBorder(Color.BLACK)));
    
    // this.setBackground(Color.BLUE);
  }

  public void paint(Graphics g)
  {
    Graphics2D canvas = (Graphics2D) g;
    this.setBorder(new CompoundBorder(new EtchedBorder(), new
         LineBorder(Color.BLACK)));
    //canvas.drawRect(5, 5, width, height);
    canvas.drawImage(bImage, 25, 25, bImage.getWidth(), bImage.getHeight(), this);

  }

  public void setPicture(BufferedImage bImage)
  {
  }

  public static void main(String[] args)
  {}
}
