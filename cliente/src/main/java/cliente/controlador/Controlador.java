package cliente.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import cliente.cliente.udp.ClienteUDP;

import org.json.simple.JSONObject;

public class Controlador {
  private ClienteUDP cliente;

  public Controlador(ClienteUDP cliente){
    this.cliente = cliente;
  }
  
  public void sendMessage(JTextField messageField, JTextField ipField) {
    String mensaje = messageField.getText();
    String ip = ipField.getText();
    if (mensaje.isBlank() || ip.isBlank()) {
      System.out.println("Mensaje vacio");
      return;
    }

    messageField.setText("");

    JSONObject data = new JSONObject();
    data.put("tipo", "mensaje");
    data.put("ipDestino", ip);
    data.put("mensaje", mensaje);
    
    cliente.enviar(data.toString());
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
