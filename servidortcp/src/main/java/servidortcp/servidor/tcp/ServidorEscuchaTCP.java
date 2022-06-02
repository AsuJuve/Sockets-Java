package servidortcp.servidor.tcp;

import java.net.*;
//importar la libreria java.net
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
//importar la libreria java.io
// declaramos la clase servidortcp

public class ServidorEscuchaTCP extends Thread {
  // declaramos un objeto ServerSocket para realizar la comunicación
  protected ServerSocket socket;
  protected DataInputStream in;
  protected Socket socket_cli;
  protected ManejadorCliente[] clientes = new ManejadorCliente[2];
  protected final int PUERTO_SERVER;

  public ServidorEscuchaTCP(int puertoS) throws Exception {
    PUERTO_SERVER = puertoS;
    // Instanciamos un ServerSocket con la dirección del destino y el
    // puerto que vamos a utilizar para la comunicación

    socket = new ServerSocket(PUERTO_SERVER);
  }

  // método principal main de la clase
  public void run() {
    // Declaramos un bloque try y catch para controlar la ejecución del subprograma
    try {
      // Creamos un socket_cli al que le pasamos el contenido del objeto socket
      // después
      // de ejecutar la función accept que nos permitirá aceptar conexiones de
      // clientes
      for (int i = 0; i < 2; i++) {
        socket_cli = socket.accept();
        ManejadorCliente manejador = new ManejadorCliente(socket_cli,
            new DataInputStream(socket_cli.getInputStream()),
            new DataOutputStream(socket_cli.getOutputStream()));
        clientes[i] = manejador;
      }

      // Creamos un bucle do while en el que recogemos el mensaje
      // que nos ha enviado el cliente y después lo mostramos
      // por consola
      do {
        for (ManejadorCliente cliente : clientes) {
          String mensaje = "";
          int size;
          size = cliente.getClienteIn().readInt();

          if (size > 0) {
            byte[] archivoBytes = new byte[size];
            in.readFully(archivoBytes, 0, archivoBytes.length);
            mensaje = new String(archivoBytes);
            JSONObject data = (JSONObject) JSONValue.parse(mensaje);

            String addressClienteDestino = InetAddress.getByName((String) data.get("ip")).toString();
            enviaArchivo(archivoBytes, addressClienteDestino);
          }

        }
      } while (true);
    }
    // utilizamos el catch para capturar los errores que puedan surgir
    catch (Exception e) {

      // si existen errores los mostrará en la consola y después saldrá del
      // programa
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  private void enviaArchivo(byte[] archivoBytes, String ipCliente) throws Exception {
    for (ManejadorCliente cliente : clientes) {
      if (cliente.getCliente().getLocalAddress().toString().equals(ipCliente)) {
        System.out.println("Enviando a " + ipCliente);
        cliente.getClienteOut().writeInt(archivoBytes.length);
        cliente.getClienteOut().write(archivoBytes);
      }
    }
  }
}
