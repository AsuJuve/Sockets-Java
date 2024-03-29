package cliente.cliente.tcp;
import java.net.*;
// importar la libreria java.net
import java.io.*;
// importar la libreria java.io
 
// declararamos la clase clientetcp
public class ClienteEnviaTCP{
    // declaramos un objeto socket para realizar la comunicación
    protected Socket socket;
    protected DataOutputStream out;
    
    public ClienteEnviaTCP(Socket socket) throws Exception{
        
        // Instanciamos un socket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación
        this.socket = socket;
        
        // Declaramos e instanciamos el objeto DataOutputStream
        // que nos valdrá para enviar datos al servidor destino
        out =new DataOutputStream(socket.getOutputStream());
    }
    
    public void enviarArchivo(String archivo_bytes) {
        // Declaramos un bloque try y catch para controlar la ejecución del subprograma
        try {
            byte[] bytes = archivo_bytes.getBytes();
            out.writeInt(bytes.length);
            out.write(bytes);
        }
        // utilizamos el catch para capturar los errores que puedan surgir
        catch (Exception e) {
            // si existen errores los mostrará en la consola y después saldrá del
            // programa
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
