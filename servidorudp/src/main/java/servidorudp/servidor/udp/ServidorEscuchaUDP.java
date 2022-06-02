package servidorudp.servidor.udp;

import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;

public class ServidorEscuchaUDP extends Thread{
    protected DatagramSocket socket;
    protected final int PUERTO_SERVER;
    protected int puertoCliente=0;
    
    protected InetAddress addressCliente;
    protected byte[] mensaje2_bytes;
    protected final int MAX_BUFFER=256;
    protected DatagramPacket paquete;
    protected byte[] mensaje_bytes;
    protected DatagramPacket envPaquete;
    
    public ServidorEscuchaUDP(int puertoS) throws Exception{
        //Creamos el socket
        PUERTO_SERVER=puertoS;
        socket = new DatagramSocket(puertoS);
    }
    public void run() {
        try {
            
            String mensaje ="";
                       
            //Iniciamos el bucle
            do {
                // Recibimos el paquete
                mensaje_bytes=new byte[MAX_BUFFER];
                paquete = new DatagramPacket(mensaje_bytes,MAX_BUFFER);
                socket.receive(paquete);
                
                // Lo formateamos
                mensaje_bytes=new byte[paquete.getLength()];
                mensaje_bytes=paquete.getData();
                mensaje = new String(mensaje_bytes,0,paquete.getLength()).trim();

                JSONObject data = (JSONObject) JSONValue.parse(mensaje);
                
                //Obtenemos IP Y PUERTO
                puertoCliente = 40000;
                addressCliente = InetAddress.getByName((String) data.get("ip"));

                enviaMensaje(mensaje);

            } while (!mensaje.startsWith("fin"));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    private void enviaMensaje(String mensajeComp) throws Exception{
        mensaje2_bytes = new byte[mensajeComp.length()];
        mensaje2_bytes = mensajeComp.getBytes();
    
        //Preparamos el paquete que queremos enviar
        envPaquete = new DatagramPacket(mensaje2_bytes,mensaje2_bytes.length,addressCliente,puertoCliente);

        // realizamos el envio
        socket.send(envPaquete);
        System.out.println("Mensaje saliente del servidor \""+
                (new String(envPaquete.getData(),0,envPaquete.getLength()))+
                "\" al cliente " + addressCliente + ": "+puertoCliente);
    }
}
