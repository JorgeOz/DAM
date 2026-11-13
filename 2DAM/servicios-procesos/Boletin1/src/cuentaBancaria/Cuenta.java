package cuentaBancaria;

public class Cuenta {

	private static int saldo = 1000;

	static class Ingresador extends Thread {
		public void run() {
			for (int i = 0; i < 1000; i++) {
				synchronized (Cuenta.class) {
					saldo = saldo + 10;
				}
			}
		}
	}

	static class Retirador extends Thread {
		public void run() {
			for (int i = 0; i < 500; i++) {
				synchronized (Cuenta.class) {
					saldo = saldo - 10;
				}
			}
		}
	}

	public static void main(String[] args) {

		Ingresador i1 = new Ingresador();
		Retirador r1 = new Retirador();

		i1.start();
		r1.start();

		try {
			i1.join();
			r1.join();

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("La espera del hilo principal fue interrumpida");
			e.printStackTrace();
		}

		System.out.println("Saldo final: " + saldo);
	}
}
