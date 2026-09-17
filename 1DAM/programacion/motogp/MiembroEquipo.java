package motogp;

import java.util.Objects;

public abstract class MiembroEquipo {

    // Atributos
    protected String nombre;
    protected int edad;
    protected double sueldoBase;

    // Constructor
    public MiembroEquipo(String nombre, int edad, double sueldoBase) {
        this.nombre = nombre;
        this.edad = edad;
        this.sueldoBase = sueldoBase;
    }

    // Getters & Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public abstract String realizarTarea();

    // Para evitar miembros duplicados
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MiembroEquipo that = (MiembroEquipo) o;
        return edad == that.edad && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, edad);
    }

}