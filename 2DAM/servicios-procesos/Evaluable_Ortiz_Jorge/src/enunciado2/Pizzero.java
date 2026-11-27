package enunciado2;

public class Pizzero extends Thread implements Runnable {

	// Atributos
	private String nombre;
	private int pizzasPorPizzero;

	// Constructor
	public Pizzero(String nombre, int pizzasPorPizzero) {
		this.nombre = nombre;
		this.pizzasPorPizzero = pizzasPorPizzero;
	}

	// Getters & Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPizzasPorPizzero() {
		return pizzasPorPizzero;
	}

	public void setPizzasPorPizzero(int pizzasPorPizzero) {
		this.pizzasPorPizzero = pizzasPorPizzero;
	}
	
	// run()
	@Override
	public void run() {
		
		for (int i = 1; i <= pizzasPorPizzero; i++) {
			System.out.println(nombre + ": pizza " + i + " de " + this.pizzasPorPizzero);
			try {
				Thread.sleep(300);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(nombre + " ha terminado su trabajo.");
	}
}
