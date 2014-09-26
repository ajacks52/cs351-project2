package genome.guicode;

import genome.types.Triangle;

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
  private final static int PANEL_H = 550;
  private final static int PANEL_W = 550;
  private int height;
  private int width;
  // private static Graphics2D canvas;
  private BufferedImage offScreenImage;
  private static BufferedImage currentImage;

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
    canvas.drawImage(currentImage, 30, 25, currentImage.getWidth(), currentImage.getHeight(), this);
  }

  public void setPicture(String imageName)
  {
    switch (imageName)
    {
    case "Leonardo_da_Vinci-Mona-Lisa-460x363.png":
      currentImage = MainFrame.bImage1;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage1.getWidth(), MainFrame.bImage1.getHeight()));
      break;
    case "Claude_Monet-Poppy_Fields-450x338.png":
      currentImage = MainFrame.bImage2;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage2.getWidth(), MainFrame.bImage2.getHeight()));
      break;
    case "Hokusai-Great_Wave_Off_Kanagawa-450x309.png":
      currentImage = MainFrame.bImage3;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage3.getWidth(), MainFrame.bImage3.getHeight()));
      break;
    case "Hokusai-Great_Wave_Off_Kanagawa-200x137.png":
      currentImage = MainFrame.bImage4;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage4.getWidth(), MainFrame.bImage4.getHeight()));
      break;
    case "Carson-408x369.png":
      currentImage = MainFrame.bImage5;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage5.getWidth(), MainFrame.bImage5.getHeight()));
      break;
    case "Gummi_Bears-299x339.png":
      currentImage = MainFrame.bImage6;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage6.getWidth(), MainFrame.bImage6.getHeight()));
      break;
    case "Old_House-300x331.png":
      currentImage = MainFrame.bImage7;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage7.getWidth(), MainFrame.bImage7.getHeight()));
      break;
    case "Hot_Air_Balloon-400x300.png":
      currentImage = MainFrame.bImage8;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage8.getWidth(), MainFrame.bImage8.getHeight()));
      break;
    case "512x512.png":
      currentImage = MainFrame.bImage9;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage9.getWidth(), MainFrame.bImage9.getHeight()));
      break;
    default:
      currentImage = MainFrame.bImage1;
      MainFrame.trianglePanel.displayTriangles(Triangle.randomGenome(200, MainFrame.bImage1.getWidth(), MainFrame.bImage1.getHeight()));
      break;
    }

    repaint();
  }

  public static void main(String[] args)
  {
  }
}
