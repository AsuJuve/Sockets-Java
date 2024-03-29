package cliente.cliente.udp;

import java.net.*;
import java.io.*;
 
//declaramos la clase udp envia
public class ClienteEnviaUDP{
    protected BufferedReader in;
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_SERVER;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    protected final String SERVER;
    
    public ClienteEnviaUDP(DatagramSocket nuevoSocket, String servidor, int puertoServidor){
        socket = nuevoSocket;
        SERVER=servidor;
        PUERTO_SERVER=puertoServidor;
    }
    
    public void enviarMensaje(String mensaje) {

        byte[] mensaje_bytes;
        mensaje_bytes=mensaje.getBytes();

        try {
            address=InetAddress.getByName(SERVER);
            paquete = new DatagramPacket(mensaje_bytes,mensaje.length(),address,PUERTO_SERVER);
            socket.send(paquete);
            
        }
        catch (Exception e) {
            System.err.println("Exception "+e.getMessage());
            System.exit(1);
        }
    }
}
