package cliente.controlador;

import cliente.tiempo.Cronometro;
import cliente.cliente.tcp.ClienteTCP;

import java.util.Base64;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EnviarArchivo extends Thread {
  private String ip;
  private Cronometro cronometro;
  private JTextArea logArea;
  private JTextArea chatArea;
  private ClienteTCP clienteTCP;

  public EnviarArchivo(JTextArea chatArea, JTextArea logArea, JTextField ipField, ClienteTCP clienteTCP) {
    ip = ipField.getText();
    cronometro = new Cronometro();
    this.logArea = logArea;
    this.chatArea = chatArea;
    this.clienteTCP = clienteTCP;
  }

  public void run() {
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
      FileInputStream fs = null;
      boolean primerPaquete = true;
      double enviados = 0;
      double tiempoTranscurrido = 0;

      try {
        fs = new FileInputStream(file);
        String nombreArchivo = file.getName();
        cronometro.reiniciar();

        while (true) {
          byte[] bytes = new byte[1500];
          int r = fs.readNBytes(bytes, 0, 1500);
          enviados += r;
          if (r == 0) {
            double segundosTranscurridos = cronometro.obtenerTiempoTranscurrido();
            tiempoTranscurrido += segundosTranscurridos;
            double bps = (enviados * 8) / segundosTranscurridos;
            logArea.setText(logArea.getText() + (int) bps + " bps\n");
            logArea.setText(logArea.getText() + "Tiempo total: " + (int) tiempoTranscurrido + " segundos\n");
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

          if (primerPaquete) {
            data.put("accion", "iniciar");
            primerPaquete = false;
          } else {
            if (fs.available() == 0) {
              data.put("accion", "finalizar");
            } else {
              data.put("accion", "guardar");
            }
          }

          clienteTCP.enviarArchivo(data.toString());
          double segundosTranscurridos = cronometro.obtenerTiempoTranscurrido();
          if (segundosTranscurridos >= 1.0) {
            double bps = (enviados * 8) / segundosTranscurridos;
            double tiempoRestante = (fs.available()) / (bps / 8);
            tiempoTranscurrido += segundosTranscurridos;
            logArea.setText(logArea.getText() + (int) bps + "bps\n");
            logArea.setText(logArea.getText() + (int) tiempoTranscurrido + " segundos transcurridos\n");
            logArea.setText(logArea.getText() + (int) tiempoRestante + " segundos restantes\n");
            cronometro.reiniciar();
            enviados = 0;
          }
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
}
