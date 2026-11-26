package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import excepciones.BusinessException;
import jdbc.ConexionJdbc;
import pojos.Departamento;

public class DaoDepartamento extends DaoGenerico<Departamento, Integer> {

	// Sobreescribir método grabar() que viene de la clase DaoGenerico
	@Override
	public void grabar(Departamento d) throws BusinessException {
		
		PreparedStatement pstm = null;
		String sql;
		ResultSet rs;
		
		// Obtengo la conexión a la BD
		Connection con = ConexionJdbc.getConnection();
		
		try {
			sql = "INSERT INTO departamento(nombre) values(?)";
			pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, d.getNombre());
			pstm.executeUpdate();
			
			// Obtener el valor de la clave primaria
			rs = pstm.getGeneratedKeys();
			
			if (rs.first()) {
				Integer id = rs.getInt(1);
				d.setIddepartamento(id);
				System.out.print("Insertado: " + d.getNombre() + " - " + d.getIddepartamento());
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new BusinessException("ERROR AL INSERTAR");
		} finally {
			ConexionJdbc.cerrar(pstm);
		}
	}
	
	// Sobreescribir método actualizar() que viene de la clase DaoGenerico
	@Override
	public void actualizar(Departamento d) throws BusinessException {
		
		PreparedStatement pstm = null;
		String sql;
		ResultSet rs;
		
		// Obtengo la conexión a la BD
		Connection con = ConexionJdbc.getConnection();
		
		try {
			sql = "UPDATE departamento SET nombre=? WHERE iddepartamento=?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, d.getNombre());
			pstm.setInt(2, d.getIddepartamento());
			pstm.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new BusinessException("ERROR AL ACTUALIZAR");
		} finally {
			ConexionJdbc.cerrar(pstm);
		}
	}
}
