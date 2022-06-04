package servidortcp.servidor.tcp;

import java.net.InetAddress;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * ManejadorClienteHilo
 */
public class ManejadorClienteHilo extends Thread {

  ManejadorCliente manejadorCliente;
  protected ManejadorCliente[] clientes;

  public ManejadorClienteHilo(ManejadorCliente manejadorCliente, ManejadorCliente[] clientes) {
    this.manejadorCliente = manejadorCliente;
    this.clientes = clientes;
  }

  @Override
  public void run() {

    try {
      do {
        String mensaje = "";
        int size;
        size = manejadorCliente.getClienteIn().readInt();

        if (size > 0) {
          byte[] archivoBytes = new byte[size];
          manejadorCliente.getClienteIn().readFully(archivoBytes, 0, archivoBytes.length);
          mensaje = new String(archivoBytes);
          JSONObject data = (JSONObject) JSONValue.parse(mensaje);

          String addressClienteDestino = InetAddress.getByName((String) data.get("ipDestino")).toString();
          enviaArchivo(archivoBytes, addressClienteDestino);
        }

      } while (true);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void enviaArchivo(byte[] archivoBytes, String ipCliente) throws Exception {
    for (ManejadorCliente cliente : clientes) {
      if (cliente.getCliente().getInetAddress().toString().equals(ipCliente)) {
        System.out.println("Enviando a " + ipCliente);
        cliente.getClienteOut().writeInt(archivoBytes.length);
        cliente.getClienteOut().write(archivoBytes);
      }
    }
  }
}
