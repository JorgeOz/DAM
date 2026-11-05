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

public class Ex4_Select {

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

			// Obtener cuántos ciclistas tiene cada equipo y si tiene más de 10 ciclistas es grande, sino es pequeño

			// Operaciones BD DAO
			sql = "SELECT COUNT(*), nomeq FROM ciclista GROUP BY nomeq";

			// Crear PreparedStatement
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vcont = rs.getInt(1);
				vequipo = rs.getString(2);

				// Si el equipo tiene más de 10 integrantes escribiremos el "nombre del equipo" es numeroso, sino es pequeño
				if (vcont > 10)
					System.out.println("El equipo " + vequipo + " tiene " + vcont + " ciclistas, es numeroso");
				else
					System.out.println("El equipo " + vequipo + " tiene " + vcont + " ciclistas, es pequeño");
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
