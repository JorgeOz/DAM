package motogp;

public class MainMotoGP {
    public static void main(String[] args) throws InterruptedException {

        GranPremio gpValencia = new GranPremio("Circuit Ricardo Tormo");

        System.out.println("\n**************************************************");
        System.out.println("¡Bienvenidos al Gran Premio de Valencia de MotoGP!");
        System.out.println("**************************************************");
        System.out.println("CIRCUITO: Ricardo Tormo");
        System.out.println("UBICACIÓN: Valencia");
        System.out.println("METEOROLOGÍA: 22ºC");

        Thread.sleep(500);
        gpValencia.mostrarParticipantes();
        Thread.sleep(500);
        gpValencia.simularCarrera();
        gpValencia.mostrarResultados();

        Thread.sleep(500);
        System.out.println("\n*************************************");
        System.out.println("F I N  D E L  G R A N  P R E M I O");
        System.out.println("*************************************");
    }
}
