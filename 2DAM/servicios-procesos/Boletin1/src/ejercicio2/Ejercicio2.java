package ejercicio2;

import java.awt.Frame;

public class Ejercicio2 {
    static class Saludador extends Frame implements Runnable {
		private static final long serialVersionUID = 1L;
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
        Thread h1 = new Thread(new Saludador(0));
        Thread h2 = new Thread(new Saludador(1));
        h1.start();
        h2.start();
    }
}