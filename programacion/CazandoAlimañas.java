// Este código es un juego de "hundir la flota" simplificado donde el jugador tiene 8 disparos para eliminar a una criatura oculta en diferentes posiciones de un tablero de 4x4.

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class CazandoAlimañas {

    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";
    private static final String ROJO = "\u001B[31m";
    private static final String VERDE = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        // Variables
        int disparos = 8;
        int disparo;
        int partesAlimanya = 4;
        char opcion = ' ';
        boolean menuMostrado = false;

        String menu = """
                Disparar (d)
                Nueva partida (n)
                Salir (s)
                   """;

        // Tablero
        char[][] tablero = new char[4][4];

        System.out.println(AMARILLO + "\n               ===== CAZANDO ALIMAÑAS =====");
        System.out.println("------------------------------------------------------------" + RESET);
        Thread.sleep(500);
        System.out.println(AMARILLO + "------------------ " + RESET + "Hecho por " + AZUL + "Jorge Ortiz" + RESET
                + AMARILLO + " -------------------\n" + RESET);
        Thread.sleep(500);

        System.out.println("Dispones de " + ROJO + "8 disparos" + RESET
                + " para eliminar a la alimaña que se encuentra en el tablero. Suerte!");
        System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);

        // Inicializar Tablero
        inicializarTablero(tablero);

        // Generar Alimaña
        generarAlimanya(tipoAlimanya(), tablero);

        do {

            if (!menuMostrado) {
                System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);
                System.out.printf("%20s", menu);
                System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);
                System.out.print("Selecciona una opción: ");
                opcion = scanner.next().toLowerCase().charAt(0);
                System.out.println(AMARILLO + "------------------------------------------------------------" + RESET);
                menuMostrado = true;
            }

            switch (opcion) {

                case 'd':
                    if (disparos == 0 || partesAlimanya == 0) {
                        System.out.println(AMARILLO + "------------------------------" + RESET);
                        System.out.println("La partida ha terminado.");
                        menuMostrado = false;
                        opcion = ' ';
                        break;
                    }

                    try {
                        System.out.print("Introduce un disparo " + VERDE + "(0-15): " + RESET);
                        disparo = scanner.nextInt();
                        System.out.println();

                        if (disparo < 0 || disparo > 15) {
                            System.out.println(AMARILLO + "------------------------------" + RESET);
                            System.out.println(ROJO + "Disparo fuera de rango." + RESET);
                            System.out.println(AMARILLO + "------------------------------" + RESET);
                            break;
                        }

                        boolean acierto = disparar(disparo, tablero);

                        disparos--;
                        mostrarTablero(tablero);
                        System.out.println();

                        if (acierto) {
                            partesAlimanya--;
                            System.out.println(VERDE + "¡ACIERTO!" + RESET);
                            System.out.println(AMARILLO + "------------------------------" + RESET);
                        } else {
                            System.out.println(ROJO + "FALLO" + RESET);
                            System.out.println(AMARILLO + "------------------------------" + RESET);
                        }

                        System.out.println("Disparos restantes: " + ROJO + disparos + RESET);
                        System.out.println(AMARILLO + "------------------------------" + RESET);

                        if (partesAlimanya == 0) {
                            System.out.println(VERDE + "¡HAS CAZADO LA ALIMAÑA!" + RESET);
                        } else if (disparos == 0) {
                            System.out.println(ROJO + "Te has quedado sin disparos." + RESET);
                        }

                    } catch (InputMismatchException e) {
                        System.out.println(ROJO + "Entrada inválida. Introduce un número." + RESET);
                        scanner.next();
                    }
                    break;

                case 'n':
                    Thread.sleep(500);
                    System.out.println("Dispones de " + ROJO + "8 disparos" + RESET
                            + " para eliminar a la alimaña que se encuentra en el tablero. Suerte!");
                    System.out
                            .println(AMARILLO + "------------------------------------------------------------" + RESET);
                    inicializarTablero(tablero);
                    generarAlimanya(tipoAlimanya(), tablero);
                    disparos = 8;
                    partesAlimanya = 4;
                    menuMostrado = false;
                    opcion = ' ';
                    break;

                case 's':
                    System.out.println("Saliendo del juego...");
                    Thread.sleep(1000);
                    break;

                default:
                    Thread.sleep(500);
                    System.out.println(ROJO + "* OPCIÓN INVÁLIDA *" + RESET);
                    menuMostrado = false;
                    break;
            }

        } while (opcion != 's');

        System.out.println();

        scanner.close();
    }

    // INICIALIZAR TABLERO
    public static void inicializarTablero(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '*';
            }
        }
    }

    // MOSTRAR TABLERO
    public static void mostrarTablero(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            System.out.printf("%25s", "");
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    // DISPARAR
    public static boolean disparar(int disparo, char[][] tablero) {
        int fila = disparo / 4;
        int columna = disparo % 4;

        if (tablero[fila][columna] == '@') {
            tablero[fila][columna] = 'X';
            return true;
        } else if (tablero[fila][columna] == '*') {
            tablero[fila][columna] = 'O';
        }
        return false;
    }

    // TIPO DE ALIMAÑA
    public static int tipoAlimanya() {
        Random random = new Random();
        return random.nextInt(0, 4);
    }

    // GENERAR ALIMAÑA
    public static void generarAlimanya(int forma, char[][] tablero) {
        Random random = new Random();
        char alimanya = '@';
        int fila = random.nextInt(0, 4);
        int columna = random.nextInt(0, 4);

        switch (forma) {
            case 0:
                for (int i = 0; i < tablero.length; i++) {
                    tablero[fila][i] = alimanya;
                }
                break;

            case 1:
                for (int i = 0; i < tablero.length; i++) {
                    tablero[i][columna] = alimanya;
                }
                break;

            case 2:
                for (int i = 0; i < tablero.length; i++) {
                    tablero[i][i] = alimanya;
                }
                break;

            case 3:
                for (int i = 0; i < tablero.length; i++) {
                    tablero[i][tablero.length - 1 - i] = alimanya;
                }
                break;
        }

        mostrarTablero(tablero);
    }
}
