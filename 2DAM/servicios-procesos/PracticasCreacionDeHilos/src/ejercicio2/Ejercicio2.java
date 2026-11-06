package ejercicio2;

public class Ejercicio2 {

	static class MiHilo extends Thread {
		@Override
		public void run() {
			System.out.println("run() ejecutado por: " + Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) {
		MiHilo h1 = new MiHilo();
		h1.run();                       

		MiHilo h2 = new MiHilo();
		h2.setName("Trabajador");
		h2.start();                     

		System.out.println("Hilo principal: " + Thread.currentThread().getName());

		// h2.start(); --> Si se descomenta salta una excepción
	}
}
