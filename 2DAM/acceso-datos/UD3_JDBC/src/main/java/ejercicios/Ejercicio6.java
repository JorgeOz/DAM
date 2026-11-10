package ejercicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Ejercicio6 {

	/*
	 * ENUNCIADO: 6. Realiza la inserción de un nuevo ciclista, pero comprobando que
	 * un equipo no puede tener más de 15 ciclistas inscritos.
	 */
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmtCount = null;
		PreparedStatement pstmtInsert = null;
		ResultSet rs = null;
		Scanner tec = null;

		try {
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();

			System.out.println("Conectado a la Base de Datos");

			tec = new Scanner(System.in);
			System.out.print("Introduce el dorsal del nuevo ciclista: ");
			int dorsal = tec.nextInt();
			tec.nextLine(); // Limpiar el buffer de entrada
			System.out.print("Introduce el nombre del ciclista: ");
			String nombre = tec.nextLine();
			System.out.print("Introduce el equipo del ciclista: ");
			String equipo = tec.nextLine();

			// 1. Comprobar número actual de ciclistas en el equipo
			String sqlCount = "SELECT COUNT(*) AS total FROM ciclista WHERE nomeq = ?";
			pstmtCount = con.prepareStatement(sqlCount, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmtCount.setString(1, equipo);
			rs = pstmtCount.executeQuery();

			if (rs.next()) {
				int totalCiclistas = rs.getInt("total");
				// Validación del límite de 15 ciclistas
				if (totalCiclistas >= 15) {
					System.out.println("Inserción cancelada: El equipo '" + equipo + "' ya tiene " + totalCiclistas
							+ " ciclistas (máximo 15 permitido).");
					return;
				}
			}

			// 2. Insertar ciclista si cumple la condición
			String sqlInsert = "INSERT INTO ciclista (dorsal, nombre, nomeq) VALUES (?, ?, ?)";
			pstmtInsert = con.prepareStatement(sqlInsert);
			pstmtInsert.setInt(1, dorsal);
			pstmtInsert.setString(2, nombre);
			pstmtInsert.setString(3, equipo);

			int filas = pstmtInsert.executeUpdate();
			if (filas > 0) {
				System.out.println("Ciclista insertado correctamente en el equipo " + equipo);
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmtCount != null)
					pstmtCount.close();
				if (pstmtInsert != null)
					pstmtInsert.close();
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