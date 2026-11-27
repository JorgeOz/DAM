package enunciado2;

public class Main {

	public static void main(String[] args) {
	
		Pizzero p1 = new Pizzero("Marco", 4);
		Pizzero p2 = new Pizzero("Lucía", 6);
		Pizzero p3 = new Pizzero("Paolo", 3);

		long inicio = System.currentTimeMillis();
		
		System.out.println("Pizzería abierta. Los pizzeros empiezan a trabajar...\n");
		p1.run();
		p2.run();
		p3.run();

		try {
			p1.join();
			p2.join();
			p3.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\n ===== RESUMEN DEL DÍA =====");
		System.out.println(p1.getNombre() + " ha preparado " + p1.getPizzasPorPizzero());
		System.out.println(p2.getNombre() + " ha preparado " + p2.getPizzasPorPizzero());
		System.out.println(p3.getNombre() + " ha preparado " + p3.getPizzasPorPizzero());
		
		long total = p1.getPizzasPorPizzero() + p2.getPizzasPorPizzero() + p3.getPizzasPorPizzero();
		System.out.println("Total de pizzas de la pizzería: " + total);
		
		System.out.println("Pizzero que más pizzas ha preparado: " + p2.getNombre() + " " + p2.getPizzasPorPizzero());
		
		long finalizado = System.currentTimeMillis();
		System.out.println("Tiempo total del proceso: " + (finalizado - inicio) + " ms");
	}
}

