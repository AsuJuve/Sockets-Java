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
import cliente.tiempo.Cronometro;

import org.json.simple.JSONObject;

public class Controlador {
  private ClienteUDP clienteUDP;
  private ClienteTCP clienteTCP;
  private Cronometro cronometro;

  public Controlador(ClienteUDP clienteUDP, ClienteTCP clienteTCP) {
    this.clienteUDP = clienteUDP;
    this.clienteTCP = clienteTCP;
    cronometro = new Cronometro();
  }

  public void sendMessage(JTextArea chatArea, JTextField messageField, JTextField ipField) {
    String mensaje = messageField.getText();
    String ip = ipField.getText();
    if (mensaje.isBlank() || ip.isBlank()) {
      System.out.println("Mensaje vacio");
      return;
    }

    chatArea.setText(chatArea.getText() + "\n->Yo: " + mensaje);
    messageField.setText("");

    JSONObject data = new JSONObject();
    data.put("tipo", "mensaje");
    data.put("ipDestino", ip);
    data.put("mensaje", mensaje);

    clienteUDP.enviarMensaje(data.toString());
  }

  public void sendFile(JTextArea chatArea, JTextArea logArea, JTextField ipField) {
    String ip = ipField.getText();
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
      FileInputStream fs = null;
      boolean primerPaquete = true;

      try {
        fs = new FileInputStream(file);
        String nombreArchivo = file.getName();

        while (true) {
          cronometro.reiniciar();
          byte[] bytes = new byte[1500];
          int r = fs.readNBytes(bytes, 0, 1500);
          if (r == 0) {
            break;
          }
          // System.out.println("Enviando " + r + " bytes");
          // System.out.println("Restantes " + fs.available() + " bytes");
          String codificado = Base64.getEncoder().encodeToString(bytes);

          JSONObject data = new JSONObject();
          data.put("tipo", "archivo");
          data.put("ipDestino", ip);
          data.put("mensaje", codificado);
          data.put("nombreArchivo", nombreArchivo);

          if (primerPaquete){
            data.put("accion", "iniciar");
            primerPaquete = false;
          }else{
            if (fs.available() == 0) {
              data.put("accion", "finalizar");
            } else {
              data.put("accion", "guardar");
            }
          }

          
          clienteTCP.enviarArchivo(data.toString());
          double segundosTranscurridos = cronometro.obtenerTiempoTranscurrido();
          double bps = (r*8)/segundosTranscurridos;
          logArea.setText(logArea.getText() + bps+"bps");
        }

        String mensaje = " --- Archivo '" + nombreArchivo + "' enviado ---";
        chatArea.setText(chatArea.getText() + "\n->Yo: " + mensaje);

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
