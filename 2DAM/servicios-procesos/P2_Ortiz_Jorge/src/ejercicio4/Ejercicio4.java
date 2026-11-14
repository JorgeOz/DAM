package ejercicio4;

public class Ejercicio4 {

    public static void main(String[] args) {

        // ---------- VERSIÓN A: run() en lugar de start() ----------
        System.out.println("=== VERSIÓN A: run() ===");
        Tarea a1 = new Tarea("Hilo-1", 2000);
        Tarea a2 = new Tarea("Hilo-2", 2000);
        Tarea a3 = new Tarea("Hilo-3", 2000);

        long inicio = System.currentTimeMillis();
        a1.run();
        a2.run();
        a3.run();
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo total versión A: " + (fin - inicio) + " ms\n");

        // ---------- VERSIÓN B: start() + join() uno detrás de otro ----------
        System.out.println("=== VERSIÓN B: start() y join() seguidos ===");
        Tarea b1 = new Tarea("Hilo-1", 2000);
        Tarea b2 = new Tarea("Hilo-2", 2000);
        Tarea b3 = new Tarea("Hilo-3", 2000);

        inicio = System.currentTimeMillis();
        try {
            b1.start();
            b1.join();
            b2.start();
            b2.join();
            b3.start();
            b3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fin = System.currentTimeMillis();
        System.out.println("Tiempo total versión B: " + (fin - inicio) + " ms\n");

        // ---------- VERSIÓN C: start() de los tres y después join() de los tres ----------
        System.out.println("=== VERSIÓN C: start() de todos, luego join() ===");
        Tarea c1 = new Tarea("Hilo-1", 2000);
        Tarea c2 = new Tarea("Hilo-2", 2000);
        Tarea c3 = new Tarea("Hilo-3", 2000);

        inicio = System.currentTimeMillis();
        c1.start();
        c2.start();
        c3.start();
        try {
            c1.join();
            c2.join();
            c3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fin = System.currentTimeMillis();
        System.out.println("Tiempo total versión C: " + (fin - inicio) + " ms");
    }
}