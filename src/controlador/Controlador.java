package controlador;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import cliente.udp.ClienteUDP;

public class Controlador {
  private ClienteUDP cliente;

  public Controlador(ClienteUDP cliente){
    this.cliente = cliente;
  }
  
  public void sendMessage(JTextField messageField, JTextField ipField) {
    String message = messageField.getText();
    if (message.isBlank()) {
      System.out.println("Mensaje vacio");
      return;
    }
    messageField.setText("");
    System.out.println("TODO!");
  }

  public void sendFile() {
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
      FileInputStream fs = null;
      try {
        fs = new FileInputStream(file);
        byte[] data = fs.readAllBytes();
      } catch (IOException e) {

      } finally {
        if (fs != null) {
          try {
            fs.close();
          } catch (IOException e) {

          }
        }
      }
      System.out.println("TODO!");
    }
  }

  public void videoCall() {
    System.out.println("TODO!");
  }
}
