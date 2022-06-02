package cliente.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Base64;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cliente.cliente.tcp.ClienteTCP;
import cliente.cliente.udp.ClienteUDP;

import org.json.simple.JSONObject;

public class Controlador {
  private ClienteUDP clienteUDP;
  private ClienteTCP clienteTCP;

  public Controlador(ClienteUDP clienteUDP, ClienteTCP clienteTCP){
    this.clienteUDP = clienteUDP;
    this.clienteTCP = clienteTCP;
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

  public void sendFile(JTextArea chatArea, JTextField ipField) {
    String ip = ipField.getText();
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
      FileInputStream fs = null;
      try {
        fs = new FileInputStream(file);
        byte[] bytes = fs.readAllBytes();
        String codificado = Base64.getEncoder().encodeToString(bytes);
        String nombreArchivo = file.getName();

        JSONObject data = new JSONObject();
        data.put("tipo", "archivo");
        data.put("ipDestino", ip);
        data.put("mensaje", codificado);
        data.put("nombreArchivo", nombreArchivo);

        String mensaje = " --- Archivo '"+nombreArchivo+"' enviado ---";
        chatArea.setText(chatArea.getText()+"\n->Yo: "+mensaje);
        clienteTCP.enviarArchivo(data.toString());

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
    }
  }

  public void videoCall() {
    System.out.println("TODO!");
  }
}
