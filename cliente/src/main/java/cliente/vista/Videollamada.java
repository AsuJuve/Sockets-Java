package cliente.vista;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Videollamada extends JFrame {
  static final Color BACKGROUND = new Color(0x1a1b26);
  static final Color FOREGROUND = new Color(0xabb2bf);
  static final Color LIGHT_BACKGROUND = new Color(0x2a2b36);
  static final Color MAIN_COLOR = new Color(0x7aa2f7);
  private ImagePanel videoArea;

  public Videollamada() {
    this.videoArea = new ImagePanel();
    add(videoArea, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setSize(600, 600);
  }

  public void setImagen(BufferedImage imagen) {
    this.videoArea.setImage(imagen);
    videoArea.repaint();
    if (!isVisible()) {
      setVisible(true);
    }
  }
}
