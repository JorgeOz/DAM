package ejercicio2;

public class SumaParcial extends Thread {

    private int[] datos;
    private int inicio;
    private int fin;        // inclusive
    private int sumaParcial;

    public SumaParcial(int[] datos, int inicio, int fin) {
        this.datos = datos;
        this.inicio = inicio;
        this.fin = fin;
        this.sumaParcial = 0;
    }

    @Override
    public void run() {
        for (int i = inicio; i <= fin; i++) {
            sumaParcial += datos[i];
        }
        System.out.println(getName() + " (" + inicio + "-" + fin + "): suma parcial = " + sumaParcial);
    }

    public int getSumaParcial() {
        return sumaParcial;
    }
}
