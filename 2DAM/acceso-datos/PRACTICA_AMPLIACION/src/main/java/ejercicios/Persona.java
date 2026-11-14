package ejercicios;

public class Persona {
	
    private String nombre;
    private String apellidos;
    private int id_persona;
    private String City;

    public Persona() {
    }

    public Persona(String nombre, String apellidos, int id_persona, String city) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.id_persona = id_persona;
        this.City = city;
    }

    public String getNombre() { 
    	return nombre; 
    }
    
    public String getApellidos() { 
    	return apellidos; 
    }
    
    public int getId_persona() { 
    	return id_persona; 
    }
    
    public String getCity() { 
    	return City; 
    }

    @Override
    public String toString() {
        return "Persona [id=" + id_persona + ", nombre=" + nombre
                + ", apellidos=" + apellidos + ", city=" + City + "]";
    }
}
