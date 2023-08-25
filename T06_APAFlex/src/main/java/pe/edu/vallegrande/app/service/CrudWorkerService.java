package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Worker;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpecWorker;
import pe.edu.vallegrande.app.service.spec.RowMapperWorker;

public class CrudWorkerService implements CrudServiceSpecWorker<Worker>, RowMapperWorker<Worker> {

	// Definiendo cosas
	private final String SQL_SELECT_BASE = "SELECT * FROM list_worker";

//	private final String SQL_SELECT_BASE = "SELECT\r\n" +
//    "    wr.id AS 'id',\r\n" +
//    "    wr.document_type AS 'Tipo_Documento',\r\n" +
//    "    wr.number_document AS 'Numero_Documento',\r\n" +
//    "    p.names + '  ' + p.last_name AS 'Persona_ID',\r\n" +
//    "    CASE \r\n" +
//    "        WHEN wr.charges = 'D' THEN 'Directora' \r\n" +
//    "        WHEN wr.charges = 'S' THEN 'Secretaria' \r\n" +
//    "        WHEN wr.charges = 'P' THEN 'Presidenta' \r\n" +
//    "        WHEN wr.charges = 'T' THEN 'Tesorera' \r\n" +
//    "        WHEN wr.charges = 'F' THEN 'Fiscal' \r\n" +
//    "        ELSE 'Vocal' \r\n" +
//    "    END AS 'Cargo'\r\n" +
//    "FROM \r\n" +
//    "	  worker wr\r\n" +
//    "JOIN \r\n" +
//    "    person p ON wr.person_id=p.id";
	private final String SQL_INSERT = "SET IDENTITY_INSERT worker ON INSERT INTO worker(id, document_type, number_document, person_id, charges ) VALUES(?,?,?,?,?)SET IDENTITY_INSERT worker OFF";
	private final String SQL_UPDATE = "UPDATE worker SET document_type=?, number_document=?, person_id=?, charges=? WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM worker WHERE id=?";

	// Método para obtener una conexión a la base de datos
	private Connection getConnection() throws SQLException {
		return AccesoDB.getConnection();
	}

	// listamos nuestra DB persona hacemos que solo aparezcan los estados A
	public List<Worker> getAll() {
		List<Worker> notes = new ArrayList<>();

		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_BASE);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String document_type = resultSet.getString("Tipo_Documento");
				String number_document = resultSet.getString("Numero_Documento");
				String person_id = resultSet.getString("Persona_ID");
				String charges = resultSet.getString("Cargo");

				Worker noteObj = new Worker();
				noteObj.setId(id);
				noteObj.setDocument_type(document_type);
				noteObj.setNumber_document(number_document);
				noteObj.setPerson_id(person_id);
				noteObj.setCharges(charges);

				notes.add(noteObj);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notes;
	}

	// listar el dato de la tabla
	public Worker getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Worker bean = null;
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
	public List<Worker> get(Worker bean) {
		// Variables
		Connection cn = null;
		List<Worker> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Worker item;
		String sql;
		String charges;
		String document_type;
		// Preparar los datos
		charges = "%" + UtilService.setStringVacio(bean.getCharges()) + "%";
		document_type = "%" + UtilService.setStringVacio(bean.getDocument_type()) + "%";
		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE + " WHERE Cargo LIKE ? AND Tipo_Documento LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, charges);
			pstm.setString(2, document_type);
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

//	// Listar registros por nombre de estudiante
//	public List<Note_detail> buscarStudent(String studentName) {
//		List<Note_detail> notesByStudent = new ArrayList<>();
//
//		try {
//			Connection connection = getConnection();
//			PreparedStatement statement = connection
//					.prepareStatement("SELECT * FROM list_worker WHERE ESTUDIANTE = ?");
//			statement.setString(1, studentName);
//			ResultSet resultSet = statement.executeQuery();
//
//			while (resultSet.next()) {
//				int id = resultSet.getInt("ID");
//				String studentId = resultSet.getString("ESTUDIANTE");
//				String note = resultSet.getString("NOTA");
//				String category = resultSet.getString("CATEGORIA");
//				String dateNote = resultSet.getString("F. DE REGISTRO");
//
//				Note_detail noteObj = new Note_detail();
//				noteObj.setId(id);
//				noteObj.setStudent_id(studentId);
//				noteObj.setNote(note);
//				noteObj.setCategory(category);
//				noteObj.setDate_note(dateNote);
//
//				notesByStudent.add(noteObj);
//			}
//
//			resultSet.close();
//			statement.close();
//			connection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return notesByStudent;
//	}

	// insertar datos
	@Override
	public void insert(Worker bean) {
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
			sql = "SELECT valor FROM controller WHERE parametro='worker'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstm.close();
				throw new SQLException("Contador de worker no existe.");
			}
			id = Integer.parseInt(rs.getString("valor"));
			rs.close();
			pstm.close();
			// Actualizar contador
			id++;
			sql = "UPDATE controller SET valor = ? WHERE parametro='worker'";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, id + "");
			pstm.executeUpdate();
			pstm.close();
			// Insertar nuevo empleado
			pstm = cn.prepareStatement(SQL_INSERT);
			pstm.setString(1, id + "");
			pstm.setString(2, bean.getDocument_type());
			pstm.setString(3, bean.getNumber_document());
			pstm.setString(4, bean.getPerson_id());
			pstm.setString(5, bean.getCharges());
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
	public void update(Worker bean) {
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
			pstm.setString(1, bean.getDocument_type());
			pstm.setString(2, bean.getNumber_document());
			pstm.setString(3, bean.getPerson_id());
			pstm.setString(4, bean.getCharges());
			pstm.setInt(5, bean.getId());
			
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException(
						"Error en Worker, Rapido!! verifique sus datos e intentelo nuevamente.");
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
				throw new SQLException("No se pudo eliminar el worker, no esta en la DB.");
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
	public Worker mapRow(ResultSet rs) throws SQLException {
		Worker bean = new Worker();
		// Columnas: id, name; lastName;typeDocument;
		// numberDocument;email;nationality;academicDegree;educationalStatus;
		bean.setId(rs.getInt("id"));
		bean.setDocument_type(rs.getString("Tipo_Documento"));
		bean.setNumber_document(rs.getString("Numero_Documento"));
		bean.setPerson_id(rs.getString("Persona_ID"));
		bean.setCharges(rs.getString("Cargo"));


		return bean;
	}

}
