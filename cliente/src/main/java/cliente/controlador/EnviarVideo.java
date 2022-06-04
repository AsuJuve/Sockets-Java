package cliente.controlador;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import com.github.sarxos.webcam.Webcam;

import cliente.cliente.udp.ClienteUDP;
import cliente.compresion.Compresor;

public class EnviarVideo extends Thread {
  private Webcam webcam;
  private ClienteUDP clienteUDP;
  private String ip;
  private boolean active;

  public void setActive(boolean active) {
    this.active = active;
  }

  public EnviarVideo(ClienteUDP clienteUDP, JTextField ipField) {
    this.webcam = Webcam.getDefault();
    this.clienteUDP = clienteUDP;
    this.ip = ipField.getText();
    this.active = true;
  }

  @Override
  public void run() {
    webcam.open();
    do {
      BufferedImage image = webcam.getImage();

      image = Compresor.compress(image, 0.1f);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
        ImageIO.write(image, "jpg", baos);

        byte[] archivoBytes = baos.toByteArray();

        String mensaje = Base64.getEncoder().encodeToString(archivoBytes);

        JSONObject data = new JSONObject();
        data.put("tipo", "video");
        data.put("ipDestino", ip);
        data.put("mensaje", mensaje);

        clienteUDP.enviarMensaje(data.toString());
      } catch (IOException e) {
        break;
      }
    } while (active);

    webcam.close();

  }
}
