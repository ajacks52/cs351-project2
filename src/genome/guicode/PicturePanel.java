package genome.guicode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/***
 * 
 * @author Adam Mitchell
 * 
 *         Displays the pictures that our algorithms will try to emulate
 */
public class PicturePanel extends JPanel
{
  private final static int PANEL_H = 445;
  private final static int PANEL_W = 550;
  private int height;
  private int width;
  // private static Graphics2D canvas;
  private BufferedImage offScreenImage;
  private BufferedImage currentImage;

  public PicturePanel(int x, int y, BufferedImage bImage)
  {
    this.currentImage = bImage;

    this.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
    this.setVisible(true);

  }

  public void paint(Graphics g)
  {
    Graphics2D canvas = (Graphics2D) g;
    this.setBorder(new CompoundBorder(new EtchedBorder(), new LineBorder(Color.BLACK)));
    canvas.drawImage(currentImage, 50, 25, currentImage.getWidth(), currentImage.getHeight(), this);
  }

  public void setPicture(BufferedImage bImage)
  {
    this.currentImage = bImage;
    repaint();
  }

  public static void main(String[] args)
  {
  }
}
