package cliente.controlador;

import cliente.tiempo.Cronometro;
import cliente.cliente.tcp.ClienteTCP;

import java.util.Base64;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EnviarArchivo extends Thread{
    private String ip; 
    private Cronometro cronometro;
    private JTextArea logArea;
    private JTextArea chatArea;
    private ClienteTCP clienteTCP;

    public EnviarArchivo(JTextArea chatArea, JTextArea logArea, JTextField ipField){
        ip = ipField.getText();
        cronometro = new Cronometro();
        this.logArea = logArea;
        this.chatArea = chatArea;
    }
    
    public void run(){
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            FileInputStream fs = null;
            boolean primerPaquete = true;

            try {
                fs = new FileInputStream(file);
                String nombreArchivo = file.getName();
                cronometro.reiniciar();
        
                while (true) {
                byte[] bytes = new byte[1500];
                int r = fs.readNBytes(bytes, 0, 1500);
                if (r == 0) {
                    break;
                }
                // System.out.println("Enviando " + r + " bytes");
                // System.out.println("Restantes " + fs.available() + " bytes");
                String codificado = Base64.getEncoder().encodeToString(bytes);
        
                JSONObject data = new JSONObject();
                data.put("tipo", "archivo");
                data.put("ipDestino", ip);
                data.put("mensaje", codificado);
                data.put("nombreArchivo", nombreArchivo);
        
                if (primerPaquete){
                    data.put("accion", "iniciar");
                    primerPaquete = false;
                }else{
                    if (fs.available() == 0) {
                    data.put("accion", "finalizar");
                    } else {
                    data.put("accion", "guardar");
                    }
                }
        
                
                clienteTCP.enviarArchivo(data.toString());
                double segundosTranscurridos = cronometro.obtenerTiempoTranscurrido();
                if (segundosTranscurridos >= 1.0) {
                    double bps = (r*8)/segundosTranscurridos;
                    logArea.setText(logArea.getText() + bps+"bps\n");
                    cronometro.reiniciar();
                }
                }
        
                String mensaje = " --- Archivo '" + nombreArchivo + "' enviado ---";
                chatArea.setText(chatArea.getText() + "\n->Yo: " + mensaje);
        
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
        }
    }
}
