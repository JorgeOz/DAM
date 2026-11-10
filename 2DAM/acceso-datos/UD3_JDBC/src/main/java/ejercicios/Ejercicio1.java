package ejercicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Ejercicio1 {

	/*
	 * ENUNCIADO: 1. Insertar un nuevo maillot, el usuario introducirá los
	 * siguientes datos: codigo: RE tipo: regularidad color: azul premio: 20000
	 */
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		Scanner tec = null;

		try {
			// Cargar configuración de la base de datos
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();

			System.out.println("Conectado a la Base de Datos");

			// Entrada de datos por teclado
			tec = new Scanner(System.in);
			System.out.print("Introduce el código del maillot (ej. RE): ");
			String codigo = tec.nextLine();
			System.out.print("Introduce el tipo de maillot (ej. regularidad): ");
			String tipo = tec.nextLine();
			System.out.print("Introduce el color del maillot (ej. azul): ");
			String color = tec.nextLine();
			System.out.print("Introduce el premio: ");
			double premio = tec.nextDouble();

			// Preparar la consulta de inserción
			String sql = "INSERT INTO maillot (codigo, tipo, color, premio) VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, codigo);
			pstmt.setString(2, tipo);
			pstmt.setString(3, color);
			pstmt.setDouble(4, premio);

			// Ejecutar la inserción
			int filasAfectadas = pstmt.executeUpdate();
			System.out.println("Maillot insertado correctamente. Filas afectadas: " + filasAfectadas);

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Cierre seguro de recursos en orden inverso
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null && !con.isClosed())
					con.close();
				if (tec != null)
					tec.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}