package ejerciciosciclismo;

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

public class Ejercicio7AC {

	/*
	 * ENUNCIADO: - APARTADO A: Al insertar un nuevo puerto en una etapa, comprobar
	 * que la etapa existe y que esa etapa no tiene más de 2 puertos de primera
	 * categoría. - APARTADO C: Al introducir un puerto nuevo asigna la categoría de
	 * forma automática: > 1500 m -> 'E' | 1000-1500 m -> '1' | 800-1000 m -> '2' |
	 * < 800 m -> '3'
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
			System.out.print("Introduce el nombre del puerto: ");
			String nompuerto = tec.nextLine();
			System.out.print("Introduce la altura del puerto (en metros): ");
			double altura = tec.nextDouble();
			System.out.print("Introduce la pendiente: ");
			double pendiente = tec.nextDouble();
			System.out.print("Introduce el número de etapa a la que pertenece: ");
			int netapa = tec.nextInt();

			// ==========================================
			// INICIO COMPROBACIONES APARTADO A
			// ==========================================
			System.out.println("\n[Apartado A] Comprobando restricciones de la etapa...");

			// 1. Comprobar que la etapa existe
			String sqlCheckEtapa = "SELECT COUNT(*) AS existe FROM etapa WHERE netapa = ?";
			pstmt = con.prepareStatement(sqlCheckEtapa, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, netapa);
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getInt("existe") == 0) {
				System.out.println(
						"ERROR [Apartado A]: No se puede realizar la inserción. La etapa " + netapa + " no existe.");
				return;
			}
			rs.close();
			pstmt.close();

			// 2. Comprobar que la etapa no tiene más de 2 puertos de primera categoría
			// ('1')
			String sqlCountCat1 = "SELECT COUNT(*) AS total_cat1 FROM puerto WHERE netapa = ? AND categoria = '1'";
			pstmt = con.prepareStatement(sqlCountCat1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, netapa);
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getInt("total_cat1") >= 2) {
				System.out.println("ERROR [Apartado A]: No se puede realizar la inserción. La etapa " + netapa
						+ " ya tiene 2 puertos de primera categoría.");
				return;
			}
			rs.close();
			pstmt.close();
			System.out.println("[Apartado A] ¡Restricciones de etapa superadas con éxito!");
			// ==========================================
			// FIN COMPROBACIONES APARTADO A
			// ==========================================

			// ==========================================
			// INICIO ASIGNACIÓN APARTADO C
			// ==========================================
			System.out.println("\n[Apartado C] Calculando categoría automática según la altura (" + altura + "m)...");
			String categoria;
			if (altura > 1500) {
				categoria = "E";
			} else if (altura >= 1000) {
				categoria = "1";
			} else if (altura >= 800) {
				categoria = "2";
			} else {
				categoria = "3";
			}
			System.out.println("[Apartado C] Categoría asignada automáticamente: " + categoria);
			// ==========================================
			// FIN ASIGNACIÓN APARTADO C
			// ==========================================

			// Inserción final del nuevo puerto
			String sqlInsert = "INSERT INTO puerto (nompuerto, altura, pendiente, categoria, netapa) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, nompuerto);
			pstmt.setDouble(2, altura);
			pstmt.setDouble(3, pendiente);
			pstmt.setString(4, categoria);
			pstmt.setInt(5, netapa);

			int filas = pstmt.executeUpdate();
			if (filas > 0) {
				System.out.println("\n¡Puerto insertado correctamente en la base de datos!");
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
