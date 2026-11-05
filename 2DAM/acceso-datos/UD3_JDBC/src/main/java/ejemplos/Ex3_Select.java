package ejemplos;

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

public class Ex3_Select {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String vequipo;
		Integer vcont;

		try {
			
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();

			System.out.println("Conectado");

			// Obtener cuántos ciclistas tiene el equipo que introduzca el usuario
			
			// Interfaz de datos de entrada
			Scanner tec = new Scanner(System.in);

			System.out.println("Introduce el nombre del equipo: ");
			vequipo = tec.nextLine();

			// Operaciones BD DAO
			sql = "SELECT COUNT(*), nomeq FROM ciclista WHERE nomeq = ?";

			// Crear PreparedStatement
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, vequipo);
			rs = pstmt.executeQuery();

			if (rs.first() && rs.getInt(1) != 0) {
				vcont = rs.getInt(1);
				vequipo = rs.getString(2);

				// Si el equipo tiene más de 10 integrantes escribiremos el "nombre del equipo" es numeroso, sino es pequeño
				if (vcont > 10)
					System.out.println("El equipo " + vequipo + " tiene " + vcont + " ciclistas, es numeroso");
				else
					System.out.println("El equipo " + vequipo + " tiene " + vcont + " ciclistas, es pequeño");

			} else
				
				System.out.println("El equipo no tiene ciclistas");

		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {

			// Cerramos la conexión
			try {
				if (con != null && !con.isClosed()) {
					con.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
	}
}