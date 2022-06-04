package cliente.vista;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

public class Videollamada extends JFrame {
    static final Color BACKGROUND = new Color(0x1a1b26);
    static final Color FOREGROUND = new Color(0xabb2bf);
    static final Color LIGHT_BACKGROUND = new Color(0x2a2b36);
    static final Color MAIN_COLOR = new Color(0x7aa2f7);
    private JPanel videoArea;

    public void setImagen(BufferedImage imagen){
        videoArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagen, 0, 0, null);
            }
        };

        getContentPane().removeAll();
        add(videoArea, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
