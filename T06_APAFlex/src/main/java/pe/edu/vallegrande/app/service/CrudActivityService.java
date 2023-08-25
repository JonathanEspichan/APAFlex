package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpecActivity;
import pe.edu.vallegrande.app.service.spec.RowMapperActivity;

public class CrudActivityService implements CrudServiceSpecActivity<Activity>, RowMapperActivity<Activity> {

	// Definiendo cosas
	private final String SQL_SELECT_BASE = "SELECT id, names, date_hour, budget, single_amount, states, worker_id FROM activity ";
	private final String SQL_INSERT = "INSERT INTO activity (names, date_hour, budget, single_amount, worker_id) VALUES(?,GETDATE() ,?,?,?)";
	private final String SQL_UPDATE = "UPDATE activity SET names=?, budget=?, single_amount=?, worker_id=? WHERE id=?";
	private final String SQL_RESTAURE = "UPDATE activity SET states = 'A' WHERE id=?";
	private final String SQL_ELIMINATE = "UPDATE  activity SET states = 'I' WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM activity WHERE id=?";

	// listamos nuestra DB persona hacemos que solo aparezcan los estados A
	public List<Activity> getAll() {
		// Variables
		Connection cn = null;
		List<Activity> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Activity bean;
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
	public List<Activity> getInactivos() {
		// Variables
		Connection cn = null;
		List<Activity> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Activity bean;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE states='I'";
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
	public Activity getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Activity bean = null;
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
	public void insert(Activity bean) {
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
			sql = "SELECT valor FROM controller WHERE parametro='Activity'";
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
			sql = "UPDATE controller SET valor = ? WHERE parametro='Activity'";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, id + "");
			pstm.executeUpdate();
			pstm.close();
			// Insertar nuevo empleado
			pstm = cn.prepareStatement(SQL_INSERT);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getBudget());
			pstm.setString(3, bean.getSingle_amount());
			pstm.setString(4, bean.getWorker_id());
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
	public void update(Activity bean) {
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
			pstm.setString(2, bean.getBudget());
			pstm.setString(3, bean.getSingle_amount());
			pstm.setString(4, bean.getWorker_id());
			pstm.setInt(5, bean.getId());

			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("Error, verifique sus datos e inténtelo nuevamente.");
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
				throw new SQLException("uyyyy no se pudo eliminar la actividad, vuelve a intentarlo, F.");
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
	public List<Activity> get(Activity bean) {
		// Variables
		Connection cn = null;
		List<Activity> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Activity item;
		String sql;
		String single_amount;
		String names;
		// Preparar los datos
		single_amount = "%" + UtilService.setStringVacio(bean.getSingle_amount()) + "%";
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		// Procesos
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE + " WHERE single_amount LIKE ? AND names LIKE ?   AND states ='A'";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, single_amount);
			pstm.setString(2, names);
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
	public Activity mapRow(ResultSet rs) throws SQLException {
		Activity bean = new Activity();
		// Columnas: id, names; last_name;document_type;
		// cell_phone;email;sex;civil_status;
		bean.setId(rs.getInt("id"));
		bean.setNames(rs.getString("names"));
		bean.setDate_hour(rs.getDate("date_hour"));
		bean.setBudget(rs.getString("budget"));
		bean.setSingle_amount(rs.getString("single_amount"));
		bean.setStates(rs.getString("states"));
		bean.setWorker_id(rs.getString("worker_id"));

		return bean;
	}

}
