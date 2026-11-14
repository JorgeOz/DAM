package ejercicio4;

public class Tarea extends Thread {

    private int tiempo;

    public Tarea(String nombre, int tiempo) {
        super(nombre);          // asigna el nombre al hilo
        this.tiempo = tiempo;
    }

    @Override
    public void run() {
        System.out.println("Tarea ejecutada por: " + Thread.currentThread().getName() + " (comienza)");
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrumpido");
            return;
        }
        System.out.println("Tarea ejecutada por: " + Thread.currentThread().getName() + " (termina)");
    }
}