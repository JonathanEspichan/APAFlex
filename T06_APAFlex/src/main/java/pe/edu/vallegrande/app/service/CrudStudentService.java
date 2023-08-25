package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec;
import pe.edu.vallegrande.app.service.spec.RowMapper;

public class CrudStudentService implements CrudServiceSpec<Student>, RowMapper<Student> {

	// Definiendo cosas
	private final String SQL_SELECT_BASE = "SELECT id, names, last_name, document_type,number_document, cell_phone, email, nationality, academic_degree, states, persona_id  FROM student ";
	private final String SQL_INSERT = "SET IDENTITY_INSERT student ON INSERT INTO student(id, names, last_name, document_type, number_document,cell_phone, email, nationality, academic_degree, states, persona_id ) VALUES(?,?,?,?,?,?,?,?,?,?,?)SET IDENTITY_INSERT student OFF";
	private final String SQL_UPDATE = "UPDATE student SET names=?, last_name=?,document_type=?, number_document=?, cell_phone=?, email=?, nationality=?, academic_degree=?, states=?, persona_id=? WHERE id=?";
	private final String SQL_RESTAURE = "UPDATE student SET states = 'A' WHERE id=?";
	private final String SQL_ELIMINATE = "UPDATE  student SET states = 'I' WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM student WHERE id=?";
	

	// listamos nuestra DB studiante  hacemos que solo aparezcan los estados A
	public List<Student> getAll() {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean;
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
	public List<Student> getInactivos() {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE states='I' ORDER BY names, last_name" ;
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
	public Student getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean = null;
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

	/**
	 * Realiza la busqueda por apellido y nombre.
	 */
	@Override
	public List<Student> get(Student bean) {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student item;
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
			sql = SQL_SELECT_BASE + " WHERE last_name LIKE ? AND names LIKE ? AND document_type LIKE ?  AND states ='A' ORDER BY names, last_name" ;
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

	// insertar datos
	@Override
	public void insert(Student bean) {
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
			sql = "SELECT valor FROM controller WHERE parametro='student'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstm.close();
				throw new SQLException("Contador de student no existe.");
			}
			id = Integer.parseInt(rs.getString("valor"));
			rs.close();
			pstm.close();
			// Actualizar contador
			id++;
			sql = "UPDATE controller SET valor = ? WHERE parametro='student'";
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
			pstm.setString(8, bean.getNationality());
			pstm.setString(9, bean.getAcademic_degree());
			pstm.setString(10, bean.getStates());
			pstm.setString(11, bean.getPersona_id());
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

	// actualizar datos
	@Override
	public void update(Student bean) {
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
			pstm.setString(7, bean.getNationality());
			pstm.setString(8, bean.getAcademic_degree());
			pstm.setString(9, bean.getStates());
			pstm.setString(10, bean.getPersona_id());
			pstm.setInt(11, bean.getId());
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("Error, verifique sus datos e intentelo nuevamente, tu problemas esta en el orden de los datos.");
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

	// eliminar datos
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
					throw new SQLException("No se pudo eliminar el estudiante, no esta en la DB.");
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
				throw new SQLException("Listo ya se ELIMINO de manera LOGICO el registro a 'I'");
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

	@Override
	public Student mapRow(ResultSet rs) throws SQLException {
		Student bean = new Student();
		// Columnas: id, name; lastName;typeDocument;
		// numberDocument;email;nationality;academicDegree;educationalStatus;
		bean.setId(rs.getInt("id"));
		bean.setNames(rs.getString("names"));
		bean.setLast_name(rs.getString("last_name"));
		bean.setDocument_type(rs.getString("document_type"));
		bean.setNumber_document(rs.getString("number_document")); 
		bean.setCell_phone(rs.getString("cell_phone"));
		bean.setEmail(rs.getString("email"));
		bean.setNationality(rs.getString("nationality"));
		bean.setAcademic_degree(rs.getString("academic_degree"));
		bean.setStates(rs.getString("states"));
		bean.setPersona_id(rs.getString("persona_id"));
		return bean;
	}
}
