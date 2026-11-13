package ejercicio3;

import java.util.Random;

public class Ejercicio3 {

	static class Caballo extends Thread {
		private final int id;
		private final Random random = new Random();

		public Caballo(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			int posicion = 0;
			while (posicion < 100) {
				posicion += random.nextInt(10) + 1;          // 1 - 10 metros
				System.out.println("Caballo " + id + ": " + posicion + " metros");
				try {
					Thread.sleep(100 + random.nextInt(401)); // 100 - 500 ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("¡El caballo " + id + " ha llegado a la meta!");
		}
	}

	public static void main(String[] args) {
		Caballo[] caballos = new Caballo[5];
		for (int i = 0; i < caballos.length; i++) {
			caballos[i] = new Caballo(i + 1);
		}
		for (Caballo caballo : caballos) {
			caballo.start();
		}
	}
}