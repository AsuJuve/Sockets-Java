package cliente.cliente.tcp;

public class ClienteTCP{
    protected final String SERVER;
    protected final int PUERTO_SERVER;
    private ClienteEnviaTCP clienteTCP;
    
    public ClienteTCP(String servidor,int puertoS) throws Exception{
        SERVER=servidor;
        PUERTO_SERVER=puertoS;
        clienteTCP = new ClienteEnviaTCP(SERVER,PUERTO_SERVER);
    }

    public void enviarArchivo(String archivo_bytes){
        clienteTCP.enviarArchivo(archivo_bytes);
    }
}
