package cliente.cliente.tcp;

import java.net.Socket;

import cliente.vista.GUI;

public class ClienteTCP{
    protected final String SERVER;
    protected final int PUERTO_SERVER;
    private ClienteEnviaTCP clienteTCP;
    private ClienteEscuchaTCP clienteEscuchaTCP;
    
    public ClienteTCP(String servidor,int puertoS) throws Exception{
        SERVER=servidor;
        PUERTO_SERVER=puertoS;
        Socket socket = new Socket(servidor, puertoS);
        clienteTCP = new ClienteEnviaTCP(socket);
        clienteEscuchaTCP = new ClienteEscuchaTCP(socket);
    }

    public void enviarArchivo(String archivo_bytes){
        clienteTCP.enviarArchivo(archivo_bytes);
    }

    public void setGUI(GUI gui) {
      clienteEscuchaTCP.setGUI(gui);
    }

    public void iniciaEscucha() {
      clienteEscuchaTCP.start();
    }
}
