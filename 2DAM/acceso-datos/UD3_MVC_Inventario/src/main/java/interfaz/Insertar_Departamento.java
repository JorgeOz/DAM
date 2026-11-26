package interfaz;

import java.util.Scanner;

import dao.DaoDepartamento;
import jdbc.ConexionJdbc;
import pojos.Departamento;

public class Insertar_Departamento {

	public static void main(String[] args) {
		
		// Interfaz --> Pojo --> DAO
		ConexionJdbc conJdbc = null;
		Departamento dep = null;
		DaoDepartamento daoDepartamento = null;
		String nombre = null;
		
		// Pedir datos de nuevo departamento al usuario
		Scanner tec = new Scanner(System.in);
		System.out.print("Nombre del departamento: ");
		nombre = tec.nextLine();
		
		try {

			// Conectar con la BD
			conJdbc = new ConexionJdbc("Configuracion/PropiedadesInventario");
			conJdbc.conectar();
			
			// Crear un pojo departamento(d) con la información que nos da el usuario
			dep = new Departamento(null, nombre);
			
			// Llamar al método dao grabar()
			daoDepartamento = new DaoDepartamento();
			daoDepartamento.grabar(dep);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conJdbc.desconectar();
		}
	}
}
