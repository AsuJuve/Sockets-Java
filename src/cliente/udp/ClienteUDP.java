package cliente.udp;

import java.net.*;
import java.io.*;
 
//declaramos la clase udp
public class ClienteUDP{
    protected final int PUERTO_SERVER;
    protected final String SERVER;
    protected ClienteEnviaUDP clienteEnvUDP;
    protected ClienteEscuchaUDP clienteEscUDP;
    
    public ClienteUDP(String servidor, int puertoS)throws Exception{
        PUERTO_SERVER=puertoS;
        SERVER=servidor;

        DatagramSocket socket = new DatagramSocket();
        clienteEscUDP = new ClienteEscuchaUDP(socket);
        clienteEnvUDP = new ClienteEnviaUDP(socket, SERVER, PUERTO_SERVER);
    }
    
    public void iniciaEscucha(){
        clienteEscUDP.start();
    }

    public void enviar(String mensaje){
        clienteEnvUDP.enviar(mensaje);
    }
}
