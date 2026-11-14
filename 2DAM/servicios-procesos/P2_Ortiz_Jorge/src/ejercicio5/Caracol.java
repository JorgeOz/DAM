package ejercicio5;

import java.util.Random;

public class Caracol extends Thread {

    private static final int META = 100;

    private int distancia;
    private long instanteLlegada;
    private Random rnd;

    public Caracol(String nombre) {
        super(nombre);
        this.distancia = 0;
        this.rnd = new Random();
    }

    @Override
    public void run() {
        while (distancia < META) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            distancia += rnd.nextInt(10) + 1;   // avance de 1 a 10 metros
            if (distancia > META) {
                distancia = META;
            }
            System.out.println(getName() + ": " + distancia + " m");
        }
        instanteLlegada = System.currentTimeMillis();
    }

    public long getInstanteLlegada() {
        return instanteLlegada;
    }
}