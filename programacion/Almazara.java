// Este código gestiona la producción de una almazara mediante un calendario mensual, calculando el rendimiento de aceite según la calidad de la aceituna y determinando cuántos días se necesitan para procesar una cantidad específica de kilos.

import java.util.Random;
import java.util.Scanner;

public class Almazara {

    private static final String RESET = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";
    private static final String VERDE = "\u001B[32m";
    private static final String MORADO = "\u001B[35m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";
    private static final String CIAN = "\u001B[36m";

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        // Matrices
        int[][] citas = new int[4][7];
        String[][] dias_semana = new String[4][7];

        // Variables
        int kilos;
        int numKilos = 0;
        String calidad;

        System.out.println(AMARILLO + "\n                  ===== LA ALMAZARA =====");
        System.out.println("------------------------------------------------------------" + RESET);
        Thread.sleep(500);
        System.out.println(AMARILLO + "------------------ " + RESET + "Hecho por " + MORADO + "Jorge Ortiz" + RESET
                + AMARILLO + " -------------------\n" + RESET);
        Thread.sleep(500);

        // Crear e inicializar calendario
        crear_calendario(citas, dias_semana);

        System.out.print(
                "Introduce el valor a sumar a los posibles 1000KG que la almazara puede procesar " + VERDE
                        + "(Entre 0 y 1000): " + RESET);

        while (!scanner.hasNextInt()) {
            System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);
            System.out.print(ROJO + "Por favor, introduce un número válido: " + RESET);
            scanner.next();
        }
        kilos = scanner.nextInt();

        // Rellenar kilos por día
        rellenar_kilos_dia(kilos, citas);

        // Mostrar calendario citas
        mostrar_calendario_citas(citas);

        System.out.println(AZUL + "------------------------------------------------------------" + RESET);
        System.out.print("Introduce los kilos que quieres llevar a la almazara: ");

        while (!scanner.hasNextInt()) {
            System.out.println(AMARILLO + "-------------------------------------------------------" + RESET);
            System.out.print(ROJO + "Por favor, introduce un número válido: " + RESET);
            scanner.next();
        }
        kilos = scanner.nextInt();

        System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);
        System.out.print("Introduce la calidad " + VERDE + "(Suprema/Buena/Regular/Mala): " + RESET);
        calidad = scanner.next();
        System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);
        System.out.println("Para la calidad " + VERDE + calidad + RESET
                + ", los litros de aceite que corresponden son: " + CIAN + (int) litrosAceite(calidad, kilos) + RESET);

        numKilos = kilos;

        System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);
        System.out.print("Los días para realizar aceite son los siguientes: " + CIAN
                + dias_para_hacer_aceite(numKilos, citas, dias_semana) + RESET);
        System.out.println(AMARILLO + "\n------------------------------------------------------------" + RESET);

        // Mostrar calendario días semana
        mostrar_calendario_dias(dias_semana);

        scanner.close();
    }

    // CREAR CALENDARIO
    public static void crear_calendario(int[][] citas, String[][] dias_semana) {
        for (int i = 0; i < citas.length; i++) {
            for (int j = 0; j < citas[i].length; j++) {
                citas[i][j] = 0;
                dias_semana[i][j] = "*";
            }
        }
    }

    // MOSTRAR CALENDARIO (CITAS)
    public static void mostrar_calendario_citas(int[][] citas) {
        System.out.println(AZUL + "\n                            CALENDARIO");
        System.out.println(
                "------------------------------------------------------------" + RESET);
        System.out
                .println(AMARILLO + "    LUN     MAR     MIE     JUE     VIE     SAB     DOM" + RESET);
        System.out.println(AZUL +
                "------------------------------------------------------------" + RESET);

        for (int i = 0; i < citas.length; i++) {
            for (int j = 0; j < citas[i].length; j++) {
                System.out.printf(" %6d ", citas[i][j]);
            }
            System.out.println();
        }
    }

    // MOSTRAR CALENDARIO (DÍAS SEMANA)
    public static void mostrar_calendario_dias(String[][] dias_semana) {
        for (int i = 0; i < dias_semana.length; i++) {
            for (int j = 0; j < dias_semana[i].length; j++) {
                System.out.printf(" %6s ", dias_semana[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // RELLENAR KILOS POR DÍA
    public static void rellenar_kilos_dia(int kilos, int[][] citas) {
        Random random = new Random();
        int diaCerrado, kilosDia;

        // Marcar un día cerrado por semana
        for (int i = 0; i < citas.length; i++) {
            diaCerrado = random.nextInt(7);
            citas[i][diaCerrado] = -1;
        }

        // Rellenar kilos en días abiertos
        for (int i = 0; i < citas.length; i++) {
            for (int j = 0; j < citas[i].length; j++) {
                if (citas[i][j] != -1) {
                    kilosDia = random.nextInt(1000) + 1 + kilos;
                    citas[i][j] = kilosDia;
                }
            }
        }
    }

    // LITROS DE ACEITE
    public static float litrosAceite(String calidad, int kilos) {
        float litros = 0;

        switch (calidad.toLowerCase()) {
            case "suprema":
                litros = 0.28f * kilos;
                break;
            case "buena":
                litros = 0.22f * kilos;
                break;
            case "regular":
                litros = 0.15f * kilos;
                break;
            case "mala":
                litros = 0.08f * kilos;
                break;
            default:
                litros = -1;
                break;
        }
        return litros;
    }

    // DÍAS PARA REALIZAR ACEITE
    public static int dias_para_hacer_aceite(int numKilos, int[][] citas, String[][] dias_semana) {
        int kilosRestantes = numKilos;
        int diasUsados = 0;
        int fila = 0;
        int columna = 0;

        while (kilosRestantes > 0 && fila < citas.length) {

            if (citas[fila][columna] == -1) {
                // Nada
            } else {

                dias_semana[fila][columna] = dia(columna);
                diasUsados++;

                if (citas[fila][columna] >= kilosRestantes) {
                    kilosRestantes = 0;
                    return diasUsados;
                } else {
                    kilosRestantes -= citas[fila][columna];
                    citas[fila][columna] = 0;
                }
            }

            columna++;
            if (columna == 7) {
                columna = 0;
                fila++;
            }
        }

        return diasUsados;
    }

    public static String dia(int num) {
        String dia = "";

        switch (num) {
            case 0:
                dia = "Lunes";
                break;
            case 1:
                dia = "Martes";
                break;
            case 2:
                dia = "Miércoles";
                break;
            case 3:
                dia = "Jueves";
                break;
            case 4:
                dia = "Viernes";
                break;
            case 5:
                dia = "Sábado";
                break;
            case 6:
                dia = "Domingo";
                break;
            default:
                dia = "";
                break;
        }

        return dia;
    }
}
