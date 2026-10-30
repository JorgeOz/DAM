package ejercicio1;

public class Ejercicio1 {
    static class Saludador extends Thread {
        private final int id;
        public Saludador(int id) { this.id = id; }

        @Override
        public void run() {
            String texto = (id == 0) ? "Hola" : "Adiós";
            for (int i = 0; i < 3; i++) {
                System.out.println(texto);
            }
        }
    }

    public static void main(String[] args) {
        Saludador h1 = new Saludador(0);
        Saludador h2 = new Saludador(1);
        h1.start();
        h2.start();
    }
}