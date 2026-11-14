package ejercicio3;

public class Estadistica extends Thread {

    public static final int MAXIMO = 0;
    public static final int MINIMO = 1;
    public static final int SUMA = 2;
    public static final int PARES = 3;

    private int[] datos;
    private int operacion;
    private long resultado;

    public Estadistica(int[] datos, int operacion) {
        this.datos = datos;
        this.operacion = operacion;
        this.resultado = 0;
    }

    @Override
    public void run() {
        switch (operacion) {
            case MAXIMO:
                resultado = datos[0];
                for (int n : datos) {
                    if (n > resultado) {
                        resultado = n;
                    }
                }
                break;
            case MINIMO:
                resultado = datos[0];
                for (int n : datos) {
                    if (n < resultado) {
                        resultado = n;
                    }
                }
                break;
            case SUMA:
                for (int n : datos) {
                    resultado += n;
                }
                break;
            case PARES:
                for (int n : datos) {
                    if (n % 2 == 0) {
                        resultado++;
                    }
                }
                break;
        }
    }

    public long getResultado() {
        return resultado;
    }
}
