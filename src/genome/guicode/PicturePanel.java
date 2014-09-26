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

  /**
   * Controls what picture will be drawn next
   * 
   * @param imageName
   */
  public void setPicture(String imageName)
  {
    switch (imageName)
    {
    case "Leonardo_da_Vinci-Mona-Lisa-460x363.png":
      currentImage = LoadPictures.bImage1;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight()),
          LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight());
      break;
    case "Claude_Monet-Poppy_Fields-450x338.png":
      currentImage = LoadPictures.bImage2;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage2.getWidth(), LoadPictures.bImage2.getHeight()),
          LoadPictures.bImage2.getWidth(), LoadPictures.bImage2.getHeight());
      break;
    case "Hokusai-Great_Wave_Off_Kanagawa-450x309.png":
      currentImage = LoadPictures.bImage3;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage3.getWidth(), LoadPictures.bImage3.getHeight()),
          LoadPictures.bImage3.getWidth(), LoadPictures.bImage3.getHeight());
      break;
    case "Hokusai-Great_Wave_Off_Kanagawa-200x137.png":
      currentImage = LoadPictures.bImage4;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage4.getWidth(), LoadPictures.bImage4.getHeight()),
          LoadPictures.bImage4.getWidth(), LoadPictures.bImage4.getHeight());
      break;
    case "Carson-408x369.png":
      currentImage = LoadPictures.bImage5;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage5.getWidth(), LoadPictures.bImage5.getHeight()),
          LoadPictures.bImage5.getWidth(), LoadPictures.bImage5.getHeight());
      break;
    case "Gummi_Bears-299x339.png":
      currentImage = LoadPictures.bImage6;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage6.getWidth(), LoadPictures.bImage6.getHeight()),
          LoadPictures.bImage6.getWidth(), LoadPictures.bImage6.getHeight());
      break;
    case "Old_House-300x331.png":
      currentImage = LoadPictures.bImage7;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage7.getWidth(), LoadPictures.bImage7.getHeight()),
          LoadPictures.bImage7.getWidth(), LoadPictures.bImage7.getHeight());
      break;
    case "Hot_Air_Balloon-400x300.png":
      currentImage = LoadPictures.bImage8;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage8.getWidth(), LoadPictures.bImage8.getHeight()),
          LoadPictures.bImage8.getWidth(), LoadPictures.bImage8.getHeight());
      break;
    case "512x512.png":
      currentImage = LoadPictures.bImage9;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage9.getWidth(), LoadPictures.bImage9.getHeight()),
          LoadPictures.bImage9.getWidth(), LoadPictures.bImage9.getHeight());
      break;
    default:
      currentImage = LoadPictures.bImage1;
      MainFrame.trianglePanel.displayTriangles(
          Triangle.randomGenome(200, LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight()),
          LoadPictures.bImage1.getWidth(), LoadPictures.bImage1.getHeight());
      break;
    }

    repaint();
  }

  public static void main(String[] args)
  {
  }
}
