package cliente.cliente.udp;

import java.net.*;
import javax.swing.JTextArea;
import java.io.*;

import cliente.vista.GUI;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
 
//declaramos la clase udp escucha
public class ClienteEscuchaUDP extends Thread{
    protected BufferedReader in;
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=32768;
    protected final int PUERTO_CLIENTE;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket servPaquete;
    //protected String SERVER;
    
    private GUI gui;

    public  ClienteEscuchaUDP(DatagramSocket socketNuevo){
        socket=socketNuevo;
        //SERVER=servidor;
        PUERTO_CLIENTE=socket.getLocalPort();
    }
    public void run() {
        
        String cadenaMensaje;

        byte[] recogerServidor_bytes;

        try {
            do {
                recogerServidor_bytes = new byte[MAX_BUFFER];

                //Esperamos a recibir un paquete
                servPaquete = new DatagramPacket(recogerServidor_bytes,MAX_BUFFER);
                socket.receive(servPaquete);

                //Convertimos el mensaje recibido en un string
                cadenaMensaje = new String(recogerServidor_bytes).trim();

                //Imprimimos el paquete recibido
                JSONObject data = (JSONObject) JSONValue.parse(cadenaMensaje);
                String mensaje = (String) data.get("mensaje");
                String ip = servPaquete.getAddress().toString();
                String tipo = (String) data.get("tipo");

                if (tipo.equals("mensaje")) {
                  JTextArea chatArea = gui.getChatArea();
                  chatArea.setText(chatArea.getText()+"\n->"+ip+": "+mensaje);
                } else if (tipo.equals("video")) {
                  System.out.println("Video recibido");
                }
            } while (true);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Excepcion C: "+e.getMessage());
            System.exit(1);
        }
    }

    public void setGUI(GUI gui){
        this.gui = gui;
    }
}
