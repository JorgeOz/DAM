package ejercicio2;

public class Ejercicio2 {
    public static void main(String[] args) {
        Vector a = new Vector(3, "a");
        Vector b = new Vector(5, "b");

        a.start();
        b.start();

        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Suma de a: " + a.getSuma() + ", media: " + a.getMedia());
        System.out.println("Suma de b: " + b.getSuma() + ", media: " + b.getMedia());
    }
}
