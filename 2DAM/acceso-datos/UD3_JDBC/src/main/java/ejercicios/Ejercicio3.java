package ejercicios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Ejercicio3 {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String vequipo, vciudadSalida;
		Scanner tec = null;

		try {

			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();

			System.out.println("Conectado a la Base de Datos");

			// -- Obtener la ciudad de salida de las etapas que han ganado ciclistas del
			// equipo que introducirá el usuario por teclado --

			// Interfaz de datos de entrada
			tec = new Scanner(System.in);

			System.out.print("Introduce el nombre del equipo: ");
			vequipo = tec.nextLine();

			// Operación BD DAO
			sql = "SELECT salida FROM ciclista c JOIN etapa e ON c.dorsal = e.dorsal WHERE nomeq = ?";

			// Creamos el PreparedStatement con la consulta
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, vequipo);

			// Ejecutamos la consulta
			rs = pstmt.executeQuery();

			// Bandera para comprobar si el equipo tiene alguna victoria
			boolean haGanadoEtapas = false;

			System.out.println("Resultados para el equipo: " + vequipo);

			// Recorremos el ResultSet fila a fila
			while (rs.next()) {
				haGanadoEtapas = true;

				// Extraemos el valor de la columna 'salida' devuelta por el SELECT
				vciudadSalida = rs.getString("salida");
				System.out.println("- Ciudad de salida: " + vciudadSalida);
			}

			// Si el cursor nunca avanzó, significa que la consulta no devolvió filas
			if (!haGanadoEtapas) {
				System.out.println("El equipo no tiene ciclistas que hayan ganado etapas o el equipo no existe.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// Cerramos los recursos en orden inverso a su apertura
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
				if (tec != null) {
					tec.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}