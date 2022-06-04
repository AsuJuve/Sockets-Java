package cliente.controlador;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Base64;
import java.util.zip.Deflater;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cliente.cliente.tcp.ClienteTCP;
import cliente.cliente.udp.ClienteUDP;
import cliente.compresion.Compresor;
import cliente.tiempo.Cronometro;

import org.json.simple.JSONObject;

import com.github.sarxos.webcam.Webcam;

public class Controlador {
  private ClienteUDP clienteUDP;
  private ClienteTCP clienteTCP;
  EnviarVideo enviarVideo;

  public Controlador(ClienteUDP clienteUDP, ClienteTCP clienteTCP) {
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

    chatArea.setText(chatArea.getText() + "\n->Yo: " + mensaje);
    messageField.setText("");

    JSONObject data = new JSONObject();
    data.put("tipo", "mensaje");
    data.put("ipDestino", ip);
    data.put("mensaje", mensaje);

    clienteUDP.enviarMensaje(data.toString());
  }

  public void sendFile(JTextArea chatArea, JTextArea logArea, JTextField ipField) {
    EnviarArchivo enviarArchivo = new EnviarArchivo(chatArea, logArea, ipField, clienteTCP);
    enviarArchivo.start();
  }

  public void videoCall(JTextField ipField, JButton videoButton) {
    String textoButton = videoButton.getText();
    if (textoButton.equals("Videollamada")) {
      enviarVideo = new EnviarVideo(this.clienteUDP, ipField);
      enviarVideo.start();
      videoButton.setText("Detener videollamada");
    } else {
      enviarVideo.setActive(false);
      videoButton.setText("Videollamada");
    }
  }
}
