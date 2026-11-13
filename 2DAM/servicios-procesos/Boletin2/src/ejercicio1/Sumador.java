package ejercicio1;

public class Sumador extends Thread {

	int resultado = 0;

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			resultado += i;
			System.out.println("Hilo: sumo " + i);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
		}
	}

	public int getResultado() {
		return resultado;
	}

}