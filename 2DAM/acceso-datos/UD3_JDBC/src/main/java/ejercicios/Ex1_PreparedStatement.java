package ejercicios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Ex1_PreparedStatement {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql;
		Integer vid;
		String vnombre, vnomeq;
		
		try {
			
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();

			System.out.println("Conectado");
			// Insertar un nuevo directorio con los valores que me dé el usuario

			// Lectura de parámetros del usuario
			Scanner tec = new Scanner(System.in);

			System.out.println("Nombre representante: ");
			vnombre = tec.nextLine();
			System.out.println("Nombre equipo: ");
			vnomeq = tec.nextLine();
			System.out.println("Código: ");
			vid = tec.nextInt();
			tec.nextLine();

			sql = "INSERT INTO representante values (?,?,?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setObject(1, vid);
			pstmt.setString(2, vnombre);
			pstmt.setString(3, vnomeq);
			pstmt.executeUpdate();

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
