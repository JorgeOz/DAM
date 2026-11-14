package ejercicio5;

public class Ejercicio5 {
    public static void main(String[] args) {
        Caracol[] caracoles = new Caracol[5];
        for (int i = 0; i < caracoles.length; i++) {
            caracoles[i] = new Caracol("Caracol-" + (i + 1));
        }

        long inicio = System.currentTimeMillis();

        // Arrancamos todos con un bucle
        for (Caracol c : caracoles) {
            c.start();
        }

        // Esperamos a todos con otro bucle
        for (Caracol c : caracoles) {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // El main calcula y muestra los tiempos
        System.out.println("\n=== RESULTADOS ===");
        for (Caracol c : caracoles) {
            long tiempo = c.getInstanteLlegada() - inicio;
            System.out.println(c.getName() + ": " + tiempo + " ms");
        }
    }
}
