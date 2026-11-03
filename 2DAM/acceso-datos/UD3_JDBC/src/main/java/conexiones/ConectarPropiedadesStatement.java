package conexiones;

import java.sql.Connection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class ConectarPropiedadesStatement {

	public static void main(String[] args) {

		Connection con = null;
		Statement stmt = null;
		Integer result;
		String sql;

		try {
			
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("configuracion\\PropiedadesCiclismo"));
			
			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			
			System.out.println("Conectado");
			stmt = con.createStatement();
			
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
