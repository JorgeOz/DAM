package ejercicio1;

public class Ejercicio1 {
	
    public static void main(String[] args) {
        Descarga hilo1 = new Descarga("archivo 1", 3000);
        Descarga hilo2 = new Descarga("archivo 2", 2000);
        Descarga hilo3 = new Descarga("archivo 3", 4000);

        // 1. Primero arrancamos los tres hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // 2. Después, el main espera a que terminen los tres
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Solo cuando han acabado todos
        System.out.println("Todas las descargas han finalizado");
    }
}