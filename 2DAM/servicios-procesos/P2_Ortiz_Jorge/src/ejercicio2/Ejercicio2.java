package ejercicio2;

public class Ejercicio2 {
    public static void main(String[] args) {
        // Array de 100 enteros (aquí: 1, 2, ..., 100)
        int[] numeros = new int[100];
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = i + 1;
        }

        SumaParcial hilo1 = new SumaParcial(numeros, 0, 24);
        SumaParcial hilo2 = new SumaParcial(numeros, 25, 49);
        SumaParcial hilo3 = new SumaParcial(numeros, 50, 74);
        SumaParcial hilo4 = new SumaParcial(numeros, 75, 99);

        // Primero arrancamos los cuatro
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();

        // Después esperamos a los cuatro
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Solo ahora es seguro leer las sumas parciales
        int total = hilo1.getSumaParcial()
                  + hilo2.getSumaParcial()
                  + hilo3.getSumaParcial()
                  + hilo4.getSumaParcial();

        System.out.println("Suma total: " + total);
    }
}
