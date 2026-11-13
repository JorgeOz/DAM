package ejemplo;

public class Principal {

	private static int contador = 0;

	static class Incrementador extends Thread {
		public void run() {
			for (int i = 0; i < 100000; i++) {
				synchronized (Principal.class) {
					contador++;
				}
			}
		}
	}

	public static void main(String[] args) {

		Incrementador i1 = new Incrementador();
		Incrementador i2 = new Incrementador();

		i1.start();
		i2.start();

		try {
			i1.join();
			i2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("La espera del hilo principal fue interrumpida");
			e.printStackTrace();
		}

		System.out.println("Contador final: " + contador);
	}
}
