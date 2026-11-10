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

public class Ejercicio2 {

	/*
	 * ENUNCIADO: 2. Obtener el número de la etapa, y el nombre del ciclista cuyo
	 * dorsal nos de el usuario.
	 */
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner tec = null;

		try {
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();

			System.out.println("Conectado a la Base de Datos");

			// Solicitar dorsal al usuario
			tec = new Scanner(System.in);
			System.out.print("Introduce el dorsal del ciclista: ");
			int dorsal = tec.nextInt();

			// Consulta SQL uniendo etapas y ciclistas
			String sql = "SELECT e.netapa, c.nombre FROM etapa e JOIN ciclista c ON e.dorsal = c.dorsal WHERE c.dorsal = ?";
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, dorsal);
			rs = pstmt.executeQuery();

			boolean encontrado = false;
			System.out.println("\n--- Resultados para el dorsal " + dorsal + " ---");

			// Recorrer los resultados
			while (rs.next()) {
				encontrado = true;
				int netap = rs.getInt("netapa");
				String nombreCiclista = rs.getString("nombre");
				System.out.println("Etapa: " + netap + " - Ciclista: " + nombreCiclista);
			}

			if (!encontrado) {
				System.out.println("No se encontraron etapas ganadas por el ciclista con dorsal " + dorsal);
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
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