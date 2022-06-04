package servidortcp.servidor.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ManejadorCliente {
  private Socket cliente;
  private DataInputStream clienteIn;
  private DataOutputStream clienteOut;

  public ManejadorCliente(Socket cliente, DataInputStream clienteIn, DataOutputStream clienteOut) {
    this.cliente = cliente;
    this.clienteIn = clienteIn;
    this.clienteOut = clienteOut;
  }

  public Socket getCliente() {
    return cliente;
  }

  public DataInputStream getClienteIn() {
    return clienteIn;
  }

  public DataOutputStream getClienteOut() {
    return clienteOut;
  }

}
