package pruebas;
import cliente.udp.ClienteUDP;
import controlador.Controlador;
import vista.GUI;

public class PruebaCliente {
    public static void main(String[] args) throws Exception{
        ClienteUDP clienteUDP =new ClienteUDP("192.168.100.3",50000);
        clienteUDP.iniciaEscucha();
        Controlador controlador = new Controlador(clienteUDP);

        GUI gui = new GUI(controlador);
    }
}
