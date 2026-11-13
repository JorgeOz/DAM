package ejercicio2;

public class Vector extends Thread {

    private int[] datos;
    private String nombre;
    private int tamanyo;
    private int suma;
    private double media;

    public Vector(int tamanyo, String nombre) {
        this.tamanyo = tamanyo;
        this.nombre = nombre;
        this.suma = 0;
        this.media = 0;
        this.datos = new int[tamanyo];
        for (int i = 0; i < tamanyo; i++) {
            datos[i] = i;
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < tamanyo; i++) {
            System.out.println(nombre + ": " + i);
            suma += datos[i];
        }
        media = (double) suma / tamanyo;
    }

    public int getSuma() {
        return suma;
    }

    public double getMedia() {
        return media;
    }
}
