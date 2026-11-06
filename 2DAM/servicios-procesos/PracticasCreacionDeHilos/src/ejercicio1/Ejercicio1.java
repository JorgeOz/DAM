package ejercicio1;

public class Ejercicio1 {

	static class Contador implements Runnable {
		private final String nombre;
		private final int n;

		public Contador(String nombre, int n) {
			this.nombre = nombre;
			this.n = n;
		}

		@Override
		public void run() {
			for (int i = 1; i <= n; i++) {
				System.out.println(nombre + ": " + i);
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new Contador("Contador A", 5)).start();
		new Thread(new Contador("Contador B", 10)).start();
		new Thread(new Contador("Contador C", 15)).start();
	}
}
