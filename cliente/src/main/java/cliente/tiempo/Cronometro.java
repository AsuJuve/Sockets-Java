package cliente.tiempo;

public class Cronometro {
    private long milisegundos = System.currentTimeMillis();

    public double obtenerTiempoTranscurrido() {
        return (System.currentTimeMillis() - this.milisegundos) / 1000.0;
    }

    public void reiniciar() {
        this.milisegundos = System.currentTimeMillis();
    }
}
