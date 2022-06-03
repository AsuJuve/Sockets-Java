package cliente.cliente.tcp;

import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import cliente.vista.GUI;

// importar la libreria java.net
import java.io.*;
// importar la libreria java.io

// declararamos la clase clientetcp
public class ClienteEscuchaTCP extends Thread {
  protected DataInputStream in;
  // declaramos un objeto socket para realizar la comunicación
  protected Socket socket;
  private GUI gui;

  public ClienteEscuchaTCP(Socket socket) throws Exception {

    // Instanciamos un socket con la dirección del destino y el
    // puerto que vamos a utilizar para la comunicación
    this.socket = socket;

    in = new DataInputStream(socket.getInputStream());
  }

  public void run() {
    // declaramos una variable de tipo string

    // Declaramos un bloque try y catch para controlar la ejecución del subprograma
    try {
      // Creamos un bucle do while en el que enviamos al servidor el mensaje
      // los datos que hemos obtenido despues de ejecutar la función
      // "readLine" en la instancia "in"
      do {

        String mensaje = "";
        int size;
        size = in.readInt();
        System.out.println(size);

        if (size > 0) {
          byte[] archivoBytes = new byte[size];
          in.readFully(archivoBytes, 0, archivoBytes.length);
          mensaje = new String(archivoBytes);
          JSONObject data = (JSONObject) JSONValue.parse(mensaje);

          System.out.println(data.toString());
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

  public void setGUI(GUI gui) {
    this.gui = gui;
  }
}
