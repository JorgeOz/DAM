package ejercicio4;

public class Ejercicio4 {

	static class Tabla extends Thread {
		private final int numero;

		public Tabla(int numero) {
			this.numero = numero;
		}

		@Override
		public void run() {
			for (int i = 1; i <= 10; i++) {
				System.out.println(numero + " x " + i + " = " + (numero * i));
			}
		}
	}

	public static void main(String[] args) {
		for (int n = 1; n <= 10; n++) {
			new Tabla(n).start();
		}
	}
}
