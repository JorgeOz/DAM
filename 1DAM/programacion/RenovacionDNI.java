// Este código reconstruye DNIs dañados calculando qué números encajan con la letra de control proporcionada según un patrón de error.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RenovacionDNI {

    private static final String RESET = "\u001B[0m";
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";
    private static final String ROJO = "\u001B[31m";
    private static final String CIAN = "\u001B[36m";

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        // Variables
        String DNI;
        String patron;
        String[] posiblesDNI = new String[100];
        boolean dniValido;
        int total;

        System.out.println(AMARILLO + "\n               ===== RENOVACIÓN DEL DNI =====");
        System.out.println("------------------------------------------------------------" + RESET);
        Thread.sleep(500);
        System.out.println(AMARILLO + "------------------ " + RESET + "Hecho por " + AZUL + "Jorge Ortiz" + RESET
                + AMARILLO + " -------------------\n" + RESET);
        Thread.sleep(500);

        do {
            System.out.print(
                    "Introduce un DNI con un " + ROJO + "máximo " + RESET + "de 2 errores (" + ROJO + "ATENCIÓN" + RESET
                            + " - No pueden estar seguidos ni tampoco al principio ni antes de la letra): ");
            DNI = scanner.nextLine().toUpperCase();

            while (DNI.isEmpty()) {
                System.out.println(AMARILLO + "----------------------" + RESET);
                System.out.print(ROJO + "El DNI no puede estar vacío. Introduce un DNI: " + RESET);
                DNI = scanner.nextLine().toUpperCase();
            }

            System.out.println(AMARILLO + "----------------------" + RESET);
            System.out.println(VERDE + DNI + RESET);

            System.out.println(AMARILLO + "----------------------" + RESET);
            System.out.print("Introduce el patrón de error: ");
            patron = scanner.nextLine().toUpperCase();

            System.out.println(AMARILLO + "----------------------" + RESET);
            System.out.println(VERDE + patron + RESET);

            String regex = "[0-9" + patron + "]{8}[A-Z]";
            dniValido = DNI.matches(regex);

            if (!dniValido) {
                System.out.println(AMARILLO + "----------------------" + RESET);
                System.out.println(ROJO + "DNI inválido. Debe tener 8 dígitos o '" + patron + "' y una letra al final."
                        + RESET);
                System.out.println(AMARILLO + "----------------------" + RESET);
            }

        } while (!dniValido);

        if (!DNI.contains(patron)) {
            int numero = Integer.parseInt(DNI.substring(0, 8));
            char letraCalculada = calculaDNI(numero);

            if (letraCalculada == DNI.charAt(8)) {
                System.out.println(AMARILLO + "----------------------" + RESET);
                System.out.println("DNI válido, no es necesario reparar: " + VERDE + DNI + RESET);
                System.out.println(AMARILLO + "----------------------" + RESET);
            } else {
                System.out.println(AMARILLO + "----------------------" + RESET);
                System.out.println(ROJO + "DNI inválido." + RESET + " La letra no coincide con el número.");
                System.out.println(AMARILLO + "----------------------" + RESET);
            }

        } else if (DNI.contains(patron)) {
            System.out.println(AMARILLO + "----------------------" + RESET);
            System.out.println("DNI dañado, preparando reparación...");

            total = repararDNI(DNI, patron, posiblesDNI);

            Thread.sleep(1000);

            if (total == 0) {
                System.out.println(AMARILLO + "----------------------" + RESET);
                System.out.println(ROJO + "No se han encontrado DNI válidos con las condiciones dadas." + RESET);
                System.out.println(AMARILLO + "----------------------" + RESET);
            } else {
                System.out.println(AMARILLO + "----------------------" + RESET);
                System.out.println("Se han encontrado " + CIAN + total + RESET + " posibles DNI válidos:");
                System.out.println(AMARILLO + "----------------------" + RESET);

                for (int i = 0; i < total; i++) {
                    System.out.println(VERDE + posiblesDNI[i] + RESET);
                }

                System.out.println(AMARILLO + "----------------------" + RESET);
            }
        }

        scanner.close();
    }

    public static char calculaDNI(int dni) {
        int indice;
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";

        indice = dni % 23;
        return letras.charAt(indice);
    }

    public static int repararDNI(String DNI, String patron, String[] posiblesDNI) {

        int posicion, posicion1, posicion2;
        int numeroDNI;
        int contador = 0;
        char letraCalculada;

        List<Integer> posicionesDanyadas = new ArrayList<>();

        for (int i = 0; i < DNI.length() - 1; i++) {
            if (DNI.charAt(i) == patron.charAt(0)) {
                posicionesDanyadas.add(i);
            }
        }

        // Validaciones
        for (int pos : posicionesDanyadas) {
            if (pos == 0 || pos == 7) { // Ni el primero ni el último dígito
                return 0;
            }
        }

        if (posicionesDanyadas.size() == 2) {
            if (posicionesDanyadas.get(1) == posicionesDanyadas.get(0) + 1) {
                return 0; // No pueden estar seguidos
            }
        }

        if (posicionesDanyadas.size() == 1) { // 1 Error
            posicion = posicionesDanyadas.get(0);

            for (char digito = '0'; digito <= '9'; digito++) {
                StringBuilder dniReparado = new StringBuilder(DNI);
                dniReparado.setCharAt(posicion, digito);
                numeroDNI = Integer.parseInt(dniReparado.substring(0, 8));
                letraCalculada = calculaDNI(numeroDNI);

                if (letraCalculada == dniReparado.charAt(8)) {
                    posiblesDNI[contador] = dniReparado.toString();
                    contador++;
                }
            }

        } else if (posicionesDanyadas.size() == 2) { // 2 Errores
            posicion1 = posicionesDanyadas.get(0);
            posicion2 = posicionesDanyadas.get(1);

            for (char digito1 = '0'; digito1 <= '9'; digito1++) {
                for (char digito2 = '0'; digito2 <= '9'; digito2++) {
                    StringBuilder dniReparado = new StringBuilder(DNI);
                    dniReparado.setCharAt(posicion1, digito1);
                    dniReparado.setCharAt(posicion2, digito2);
                    numeroDNI = Integer.parseInt(dniReparado.substring(0, 8));
                    letraCalculada = calculaDNI(numeroDNI);

                    if (letraCalculada == dniReparado.charAt(8)) {
                        posiblesDNI[contador] = dniReparado.toString();
                        contador++;
                    }
                }
            }

        } else if (posicionesDanyadas.size() > 2) { // No Válido
            return 0;
        }

        return contador;
    }
}
