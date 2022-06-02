package pruebas;
import cliente.udp.ClienteUDP;
import controlador.Controlador;
import vista.GUI;

public class PruebaCliente {
    public static void main(String[] args) throws Exception{
        ClienteUDP clienteUDP =new ClienteUDP("127.0.0.1",50000);
        clienteUDP.iniciaEscucha();
        Controlador controlador = new Controlador(clienteUDP);

        GUI gui = new GUI(controlador);
    }
}
