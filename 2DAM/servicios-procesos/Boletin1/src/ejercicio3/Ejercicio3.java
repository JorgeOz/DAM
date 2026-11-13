package ejercicio3;

import java.util.*;

public class Ejercicio3 {

    static class Hilo extends Thread {

        private int id;

        public Hilo(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            Random random = new Random();
            int espera = random.nextInt(500, 10001); 

            System.out.println("Hola, soy el hilo " + id + " y voy a dormir " + espera + " milisegundos.");

            try {
                Thread.sleep(espera);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Hilo " + id + " ha sido interrumpido");
                return;
            }

            System.out.println("Hola, soy el hilo " + id + " y ya me he despertado");
        }
    }

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("=== EJERCICIO 3 - Jorge Ortiz ===");
        System.out.println("=================================");

        List<Hilo> listaHilos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Hilo hilo = new Hilo(i);
            hilo.start();
            listaHilos.add(hilo);
        }

        for (Hilo hilo : listaHilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("El hilo principal ha terminado");
    }
}
