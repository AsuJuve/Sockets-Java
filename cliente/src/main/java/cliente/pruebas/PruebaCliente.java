package cliente.pruebas;
import cliente.cliente.udp.ClienteUDP;
import cliente.cliente.tcp.ClienteTCP;
import cliente.controlador.Controlador;
import cliente.vista.GUI;

public class PruebaCliente {
    public static void main(String[] args) throws Exception{
        //Envio de mensajes
        ClienteUDP clienteUDP =new ClienteUDP("127.0.0.1",50000);

        //Envío de archivos
        ClienteTCP clienteTCP =new ClienteTCP("127.0.0.1",60000);

        //Controlador y GUI
        Controlador controlador = new Controlador(clienteUDP,clienteTCP);
        
        GUI gui = new GUI(controlador);
        clienteUDP.setGUI(gui);
        clienteTCP.setGUI(gui);
        clienteUDP.iniciaEscucha();
        clienteTCP.iniciaEscucha();
    }
}
