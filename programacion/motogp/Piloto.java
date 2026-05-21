package motogp;

import java.util.Random;

public class Piloto extends MiembroEquipo implements Competidor, Comparable<Piloto>{

    // Atributos
    private int dorsal;
    private String escuderia;
    private int puntosCampeonato;
    private double desgasteNeumaticos = 20.0;

    // Constructor
    public Piloto(String nombre, int edad, double sueldoBase, int dorsal, String escuderia, int puntosCampeonato) {
        super(nombre, edad, sueldoBase);
        this.dorsal = dorsal;
        this.escuderia = escuderia;
        this.puntosCampeonato = puntosCampeonato;
    }

    // Getters & Setters
    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getEscuderia() {
        return escuderia;
    }

    public void setEscuderia(String escuderia) {
        this.escuderia = escuderia;
    }

    public int getPuntosCampeonato() {
        return puntosCampeonato;
    }

    public void setPuntosCampeonato(int puntosCampeonato) {
        this.puntosCampeonato = puntosCampeonato;
    }

    public double getDesgasteNeumaticos() {
        return desgasteNeumaticos;
    }

    public void setDesgasteNeumaticos(double desgasteNeumaticos) {
        this.desgasteNeumaticos = desgasteNeumaticos;
    }

    // Calcula lo que sucede en la vuelta basándose en la suerte
    @Override
    public String realizarTarea() {

        this.desgasteNeumaticos += 2.0;

        Random random = new Random();
        int numeroSuerte = random.nextInt(0, 31);

        if (numeroSuerte + (this.desgasteNeumaticos / 4) > 95) {
            return "Accidente";
        } else if (this.desgasteNeumaticos > 80) {
            return "Entrada a Boxes";
        }

        return "Vuelta Limpia";
    }

    // Se añade a la edad del piloto un número aleatorio para determinar su suerte
    @Override
    public int competir() {

        Random random = new Random();
        return this.edad + random.nextInt(100) + 1;
    }

    // Ordenar los pilotos por puntos de manera descendente
    public int compareTo(Piloto otroPiloto) {
        return Integer.compare(otroPiloto.getPuntosCampeonato(), this.puntosCampeonato);
    }

}
