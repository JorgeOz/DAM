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

public class Ejercicio5 {

	/*
	 * ENUNCIADO: 5. Mostrar un mensaje que diga si el ciclista con un dorsal que
	 * facilite el usuario ha ganado o no alguna etapa.
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

			tec = new Scanner(System.in);
			System.out.print("Introduce el dorsal del ciclista: ");
			int dorsal = tec.nextInt();

			// Contar cuántas etapas ha ganado el ciclista dado
			String sql = "SELECT COUNT(*) AS ganadas FROM etapa WHERE dorsal = ?";
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, dorsal);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int ganadas = rs.getInt("ganadas");
				if (ganadas > 0) {
					System.out.println(
							"El ciclista con dorsal " + dorsal + " SÍ ha ganado etapas (" + ganadas + " ganadas).");
				} else {
					System.out.println("El ciclista con dorsal " + dorsal + " NO ha ganado ninguna etapa.");
				}
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