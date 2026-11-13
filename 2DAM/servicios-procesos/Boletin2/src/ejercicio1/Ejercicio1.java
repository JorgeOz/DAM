package ejercicio1;

public class Ejercicio1 {

	public static void main(String[] args) throws InterruptedException {
		Sumador hilo = new Sumador();
		hilo.start();

		hilo.join(); // PRUEBA: comenta esta línea y vuelve a ejecutar

		System.out.println("Main: el resultado es " + hilo.getResultado());
	}
}