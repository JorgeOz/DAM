package ejercicio1;

public class Descarga extends Thread {

    private String archivo;
    private int tiempo;

    public Descarga(String archivo, int tiempo) {
        this.archivo = archivo;
        this.tiempo = tiempo;
    }

    @Override
    public void run() {
        System.out.println("Comienza la descarga de " + archivo);
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            System.out.println("Descarga de " + archivo + " interrumpida");
            return;
        }
        System.out.println("Ha terminado la descarga de " + archivo);
    }
}
