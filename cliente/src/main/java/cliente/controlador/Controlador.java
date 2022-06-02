package cliente.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cliente.cliente.tcp.ClienteTCP;
import cliente.cliente.udp.ClienteUDP;

import org.json.simple.JSONObject;

public class Controlador {
  private ClienteUDP clienteUDP;
  private ClienteTCP clientTCP;

  public Controlador(ClienteUDP clienteUDP, ClienteTCP clienteTCP){
    this.clienteUDP = clienteUDP;
    this.clientTCP = clienteTCP;
  }
  
  public void sendMessage(JTextArea chatArea, JTextField messageField, JTextField ipField) {
    String mensaje = messageField.getText();
    String ip = ipField.getText();
    if (mensaje.isBlank() || ip.isBlank()) {
      System.out.println("Mensaje vacio");
      return;
    }

    chatArea.setText(chatArea.getText()+"\n->Yo: "+mensaje);
    messageField.setText("");

    JSONObject data = new JSONObject();
    data.put("tipo", "mensaje");
    data.put("ipDestino", ip);
    data.put("mensaje", mensaje);
    
    clienteUDP.enviarMensaje(data.toString());
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
        e.printStackTrace();
      } finally {
        if (fs != null) {
          try {
            fs.close();
          } catch (IOException e) {
            e.printStackTrace();
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
