package ejemplos;

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

public class Ex2_PreparedStatement {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql;
		Integer vid, result;
		String vnombre, vnomeq;
		
		try {
			
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();

			System.out.println("Conectado");
			
			// Modificar el equipo de un representante, al usuario le pides el ID del representante y el nuevo equipo
			
			// Lectura de parámetros del usuario
			Scanner tec = new Scanner(System.in);

			System.out.println("Nomeq nuevo equipo: ");
			vnomeq = tec.nextLine();
			System.out.println("ID representante: ");
			vid = tec.nextInt();
			tec.nextLine();

			sql = "UPDATE representante SET nom_eq=? WHERE id=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vnomeq);
			pstmt.setInt(2, vid);
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