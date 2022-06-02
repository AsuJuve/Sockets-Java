package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import controlador.Controlador;

public class GUI extends JFrame {
  private Controlador controlador;

  static final Color BACKGROUND = new Color(0x1a1b26);
  static final Color FOREGROUND = new Color(0xabb2bf);
  static final Color LIGHT_BACKGROUND = new Color(0x2a2b36);
  static final Color MAIN_COLOR = new Color(0x7aa2f7);

  private JPanel mainPanel;
  private JPanel ipPanel;
  private JLabel ipLabel;
  private JTextField ipField;
  private JTextArea chatArea;
  private JPanel messagePanel;
  private JLabel messageLabel;
  private JTextField messageField;
  private JButton messageButton;
  private JButton fileButton;
  private JButton videoButton;

  public GUI(Controlador controlador) {

    this.controlador = controlador;

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBackground(BACKGROUND);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    Font biggerFont = mainPanel.getFont().deriveFont(17f);

    // IP Panel

    ipPanel = new JPanel();
    ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.X_AXIS));
    ipPanel.setMaximumSize(new Dimension(480, 40));

    ipLabel = new JLabel("IP", JLabel.CENTER);
    ipLabel.setPreferredSize(new Dimension(80, 40));
    ipLabel.setFont(biggerFont);
    ipField = new JTextField();
    ipField.setPreferredSize(new Dimension(400, 40));
    ipField.setBackground(LIGHT_BACKGROUND);
    ipField.setForeground(FOREGROUND);
    ipField.setFont(biggerFont);

    ipPanel.add(ipLabel);
    ipPanel.add(ipField);

    // Chat Area

    chatArea = new JTextArea(10, 10);
    chatArea.setEditable(false);
    chatArea.setBackground(LIGHT_BACKGROUND);
    chatArea.setForeground(FOREGROUND);
    chatArea.setMaximumSize(new Dimension(480, 160));
    chatArea.setFont(biggerFont);

    JScrollPane scroll = new JScrollPane(chatArea);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setMaximumSize(new Dimension(480, 160));

    // Message Panel

    messagePanel = new JPanel();
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
    messagePanel.setMaximumSize(new Dimension(480, 40));

    messageLabel = new JLabel("Mensaje", JLabel.CENTER);
    messageLabel.setPreferredSize(new Dimension(80, 40));
    messageLabel.setFont(biggerFont);
    messageField = new JTextField();
    messageField.setPreferredSize(new Dimension(400, 40));
    messageField.setBackground(LIGHT_BACKGROUND);
    messageField.setForeground(FOREGROUND);
    messageField.setFont(biggerFont);

    messagePanel.add(messageLabel);
    messagePanel.add(messageField);

    // Buttons

    messageButton = new JButton("Enviar mensaje");
    messageButton.setBackground(MAIN_COLOR);
    messageButton.setForeground(BACKGROUND);
    messageButton.setFont(biggerFont);
    messageButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    messageButton.setMaximumSize(new Dimension(480, 40));

    messageButton.addActionListener(e -> {
      this.controlador.sendMessage(messageField, ipField);
    });

    fileButton = new JButton("Enviar archivo");
    fileButton.setBackground(MAIN_COLOR);
    fileButton.setForeground(BACKGROUND);
    fileButton.setFont(biggerFont);
    fileButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    fileButton.setMaximumSize(new Dimension(480, 40));

    fileButton.addActionListener(e -> {
      this.controlador.sendFile();
    });

    videoButton = new JButton("Videollamada");
    videoButton.setBackground(MAIN_COLOR);
    videoButton.setForeground(BACKGROUND);
    videoButton.setFont(biggerFont);
    videoButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    videoButton.setMaximumSize(new Dimension(480, 40));

    videoButton.addActionListener(e -> {
      this.controlador.videoCall();
    });

    mainPanel.add(ipPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(480, 30)));
    mainPanel.add(scroll);
    mainPanel.add(Box.createRigidArea(new Dimension(480, 30)));
    mainPanel.add(messagePanel);
    mainPanel.add(Box.createRigidArea(new Dimension(480, 10)));
    mainPanel.add(messageButton);
    mainPanel.add(Box.createRigidArea(new Dimension(480, 10)));
    mainPanel.add(fileButton);
    mainPanel.add(Box.createRigidArea(new Dimension(480, 10)));
    mainPanel.add(videoButton);

    add(mainPanel, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }
}
