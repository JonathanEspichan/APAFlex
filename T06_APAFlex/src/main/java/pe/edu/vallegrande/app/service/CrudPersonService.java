package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Person;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpecPerson;
import pe.edu.vallegrande.app.service.spec.RowMapperPerson;

public class CrudPersonService implements CrudServiceSpecPerson<Person>, RowMapperPerson<Person> {

	// Definiendo cosas
	private final String SQL_SELECT_BASE = "SELECT id, names, last_name, document_type,number_document, cell_phone, email, sex, states, civil_status FROM person ";
	private final String SQL_INSERT = "SET IDENTITY_INSERT person ON INSERT INTO person (id, names, last_name, document_type,number_document, cell_phone, email, sex, states, civil_status) VALUES(?,?,?,?,?,?,?,?,?,?) SET IDENTITY_INSERT person OFF";
	private final String SQL_UPDATE = "UPDATE person SET names=?, last_name=?, document_type=?,number_document=?, cell_phone=?, email=?, sex=?, states=?, civil_status=? WHERE id=?";
	private final String SQL_RESTAURE = "UPDATE person SET states = 'A' WHERE id=?";
	private final String SQL_ELIMINATE = "UPDATE  person SET states = 'I' WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM person WHERE id=?";


	// listamos nuestra DB persona  hacemos que solo aparezcan los estados A
	public List<Person> getAll() {
		// Variables
		Connection cn = null;
		List<Person> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Person bean;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE states='A'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow(rs);
				lista.add(bean);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	
	// listamos INACTIVOS nuestra DB persona que nos muestren los estados I
		public List<Person> getInactivos() {
			// Variables
			Connection cn = null;
			List<Person> lista = new ArrayList<>();
			PreparedStatement pstm = null;
			ResultSet rs = null;
			Person bean;
			String sql;
			// Proceso
			try {
				cn = AccesoDB.getConnection();
				sql = SQL_SELECT_BASE + " WHERE states='I' ORDER BY names, last_name";
				pstm = cn.prepareStatement(sql);
				rs = pstm.executeQuery();
				while (rs.next()) {
					bean = mapRow(rs);
					lista.add(bean);
				}
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				try {
					cn.close();
				} catch (Exception e2) {
				}
			}
			return lista;
		}
	
	
	// listar el dato de la tabla
	public Person getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Person bean = null;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(id));
			rs = pstm.executeQuery();
			if (rs.next()) {
				bean = mapRow(rs);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return bean;
	}

	// INSERTAMOS nuevos datos
	public void insert(Person bean) {
		// Variables
		Connection cn = null;
		String sql = null;
		PreparedStatement pstm = null;
		ResultSet rs;
		Integer id = 0;
		// Proceso
		try {
			// Iniciar la Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			// Traer contador
			sql = "SELECT valor FROM controller WHERE parametro='person'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstm.close();
				throw new SQLException("Contador de persona no existe.");
			}
			id = Integer.parseInt(rs.getString("valor"));
			rs.close();
			pstm.close();
			// Actualizar contador
			id++;
			sql = "UPDATE controller SET valor = ? WHERE parametro='person'";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, id + "");
			pstm.executeUpdate();
			pstm.close();
			// Insertar nuevo empleado
			pstm = cn.prepareStatement(SQL_INSERT);
			pstm.setString(1, id + "");
			pstm.setString(2, bean.getNames());
			pstm.setString(3, bean.getLast_name());
			pstm.setString(4, bean.getDocument_type());
			pstm.setString(5, bean.getNumber_document());  
			pstm.setString(6, bean.getCell_phone());
			pstm.setString(7, bean.getEmail());
			pstm.setString(8, bean.getSex());
			pstm.setString(9, bean.getStates());
			pstm.setString(10, bean.getCivil_status());
			pstm.executeUpdate();
			pstm.close();
			// Fin de Tx
			bean.setId(id);
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
				cn.close();
			} catch (Exception e2) {
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
	}

	@Override
	public void update(Person bean) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas;
		// Proceso
		try {
			// Iniciar la Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			// Actualizar el registro
			pstm = cn.prepareStatement(SQL_UPDATE);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getLast_name());
			pstm.setString(3, bean.getDocument_type());
			pstm.setString(4, bean.getNumber_document()); 
			pstm.setString(5, bean.getCell_phone());
			pstm.setString(6, bean.getEmail());
			pstm.setString(7, bean.getSex());
			pstm.setString(8, bean.getStates());
			pstm.setString(9, bean.getCivil_status());
			pstm.setInt(10, bean.getId());
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("Error en CrudPersonService, Rapido!! verifique sus datos e intentelo nuevamente.");
			}
			// Fin de Tx
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
				cn.close();
			} catch (Exception e2) {
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
	}

	// ELIMINAMOS los datos de una fila en la tabla persona
	@Override
	public void delete(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas = 0;

		// Proceso
		try {
			// Inicio de Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			// Proceso
			pstm = cn.prepareStatement(SQL_DELETE);
			pstm.setInt(1, Integer.parseInt(id));
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("uyyyy no se pudo eliminar el empleado, vuelve a intentarlo, F.");
			}
			// Confirmar Tx
			cn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}

	}

	
	
	
	// ELIMINAMOS los datos de una fila en la tabla persona I
		
		public void EliminadoLogico(String id) {
			// Variables
			Connection cn = null;
			PreparedStatement pstm = null;
			int filas = 0;

			// Proceso
			try {
				// Inicio de Tx
				cn = AccesoDB.getConnection();
				cn.setAutoCommit(false);
				// Proceso
				pstm = cn.prepareStatement(SQL_ELIMINATE);
				pstm.setInt(1, Integer.parseInt(id));
				filas = pstm.executeUpdate();
				pstm.close();
				if (filas != 1) {
					throw new SQLException("Listo ya se ELIMINO de manera LOGICO el registro a 'I' ");
				}
				// Confirmar Tx
				cn.commit();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				try {
					cn.close();
				} catch (Exception e2) {
				}
			}

		}
		
		
		// RESTAURAR los datos de una fila en la tabla persona
		
		public void Restaurar(String id) {
			// Variables
			Connection cn = null;
			PreparedStatement pstm = null;
			int filas = 0;

			// Proceso
			try {
				// Inicio de Tx
				cn = AccesoDB.getConnection();
				cn.setAutoCommit(false);
				// Proceso
				pstm = cn.prepareStatement(SQL_RESTAURE);
				pstm.setInt(1, Integer.parseInt(id));
				filas = pstm.executeUpdate();
				pstm.close();
				if (filas != 1) {
					throw new SQLException("Listo ya se RESTAURO el registro a 'A' ");
				}
				// Confirmar Tx
				cn.commit();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				try {
					cn.close();
				} catch (Exception e2) {
				}
			}

		}


	// Realiza la busqueda por apellido y nombre dentro de la tabla.

	@Override
	public List<Person> get(Person bean) {
		// Variables
		Connection cn = null;
		List<Person> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Person item;
		String sql;
		String last_name;
		String names;
		String document_type;
		// Preparar los datos
		last_name = "%" + UtilService.setStringVacio(bean.getLast_name()) + "%";
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		document_type = "%" + UtilService.setStringVacio(bean.getDocument_type()) + "%";
		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE + " WHERE last_name LIKE ? AND names LIKE ? AND document_type LIKE ?  AND states ='A' ORDER BY names, last_name";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, last_name);
			pstm.setString(2, names);
			pstm.setString(3, document_type);
			rs = pstm.executeQuery();
			while (rs.next()) {
				item = mapRow(rs);
				lista.add(item);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	@Override
	public Person mapRow(ResultSet rs) throws SQLException {
		Person bean = new Person();
		// Columnas: id, names; last_name;document_type;
		// cell_phone;email;sex;civil_status;
		bean.setId(rs.getInt("id"));
		bean.setNames(rs.getString("names"));
		bean.setLast_name(rs.getString("last_name"));
		bean.setDocument_type(rs.getString("document_type")); 
		bean.setNumber_document(rs.getString("number_document")); 
		bean.setCell_phone(rs.getString("cell_phone"));
		bean.setEmail(rs.getString("email"));
		bean.setSex(rs.getString("sex"));
		bean.setStates(rs.getString("states"));
		bean.setCivil_status(rs.getString("civil_status"));

		return bean;
	}

}
