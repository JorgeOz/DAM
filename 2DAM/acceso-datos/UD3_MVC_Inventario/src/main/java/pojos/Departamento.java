package pojos;

import java.util.Objects;

public class Departamento {
	
	// Atributos acorde al nombre de las columnas de la BD
	private Integer iddepartamento;
	private String nombre;
	
	// Constructor (Superclase)
	public Departamento() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// Constructor (Campos)
	public Departamento(Integer iddepartamento, String nombre) {
		super();
		this.iddepartamento = iddepartamento;
		this.nombre = nombre;
	}
	
	// Getters & Setters
	public Integer getIddepartamento() {
		return iddepartamento;
	}

	public void setIddepartamento(Integer iddepartamento) {
		this.iddepartamento = iddepartamento;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// hashCode & toString()
	@Override
	public int hashCode() {
		return Objects.hash(iddepartamento, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		return Objects.equals(iddepartamento, other.iddepartamento) && Objects.equals(nombre, other.nombre);
	}

	// compareTo()
	public int compareTo(Integer anotherInteger) {
		return iddepartamento.compareTo(anotherInteger);
	}

}
