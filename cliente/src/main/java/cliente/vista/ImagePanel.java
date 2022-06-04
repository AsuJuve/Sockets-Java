package cliente.vista;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
  BufferedImage image;

  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(this.image, 0, 0, null);
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

}
