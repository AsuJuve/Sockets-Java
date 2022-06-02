package pruebas;
import cliente.udp.ClienteUDP;
import vista.GUI;

public class PruebaCliente {
    public static void main(String[] args) throws Exception{
        ClienteUDP clienteUDP =new ClienteUDP("192.168.100.3",50000);
        clienteUDP.inicia();

        GUI gui = new GUI();
    }
}
