package ejemplos;

public class Socio {

	// Atributos
	private String Nombre;
	private Integer Num_socio;
	
	// Constructores
	public Socio() {
		super();
	}

	public Socio(String nombre, Integer num_socio) {
		super();
		Nombre = nombre;
		Num_socio = num_socio;
	}

	// Getters & Setters
	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public Integer getNum_socio() {
		return Num_socio;
	}

	public void setNum_socio(Integer num_socio) {
		Num_socio = num_socio;
	}

	@Override
	public String toString() {
		return "Socio [Nombre=" + Nombre + ", Num_socio=" + Num_socio + "]";
	}
	
}
