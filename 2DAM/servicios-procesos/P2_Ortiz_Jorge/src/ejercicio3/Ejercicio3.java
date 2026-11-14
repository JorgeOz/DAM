package ejercicio3;

import java.util.Random;

public class Ejercicio3 {
    public static void main(String[] args) {
        // 1.000 números aleatorios entre 0 y 10.000 (ambos incluidos)
        Random rnd = new Random();
        int[] numeros = new int[1000];
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = rnd.nextInt(10001);
        }

        Estadistica maximo = new Estadistica(numeros, Estadistica.MAXIMO);
        Estadistica minimo = new Estadistica(numeros, Estadistica.MINIMO);
        Estadistica suma = new Estadistica(numeros, Estadistica.SUMA);
        Estadistica pares = new Estadistica(numeros, Estadistica.PARES);

        // Arrancamos los cuatro a la vez
        maximo.start();
        minimo.start();
        suma.start();
        pares.start();

        // Esperamos a los cuatro
        try {
            maximo.join();
            minimo.join();
            suma.join();
            pares.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ahora sí es seguro leer los resultados
        System.out.println("Máximo: " + maximo.getResultado());
        System.out.println("Mínimo: " + minimo.getResultado());
        System.out.println("Suma: " + suma.getResultado());
        System.out.println("Cantidad de pares: " + pares.getResultado());
    }
}
