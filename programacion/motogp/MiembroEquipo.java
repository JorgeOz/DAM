package motogp;

public abstract class MiembroEquipo {

    protected String nombre;
    protected int edad;
    protected double sueldoBase;

    public MiembroEquipo(String nombre, int edad, double sueldoBase) {
        this.nombre = nombre;
        this.edad = edad;
        this.sueldoBase = sueldoBase;
    }

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
}