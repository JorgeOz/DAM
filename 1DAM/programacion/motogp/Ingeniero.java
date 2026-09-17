package motogp;

import java.util.Random;

public class Ingeniero extends MiembroEquipo implements Competidor, Comparable<Ingeniero> {

    // Atributos
    private String especialidad;
    private String escuderia;
    private int experiencia;

    // Constructor
    public Ingeniero (String nombre, int edad, double sueldoBase, String especialidad, String escuderia, int experiencia) {
        super (nombre, edad, sueldoBase);
        this.especialidad = especialidad;
        this.escuderia = escuderia;
        this.experiencia = experiencia;
    }

    // Getters & Setters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEscuderia() {
        return escuderia;
    }

    public void setEscuderia(String escuderia) {
        this.escuderia = escuderia;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    // Ordenar los ingenieros por experiencia y por especialidad en caso de empate
    public int compareTo(Ingeniero otroIngeniero) {

        if (this.experiencia == otroIngeniero.getExperiencia()) {
            return this.especialidad.compareTo(otroIngeniero.getEspecialidad());
        }

        return Integer.compare(otroIngeniero.getExperiencia(), this.experiencia);
    }

    // Devuelve un bonus que mejora o empeora al piloto
    public int aportarMejora() {

        Random random = new Random();
        return random.nextInt(-70, 101) + this.experiencia;
    }

    // No se utilizan en esta clase
    @Override
    public int competir() {
        return 0;
    }

    @Override
    public String realizarTarea() {
        return "";
    }

}
