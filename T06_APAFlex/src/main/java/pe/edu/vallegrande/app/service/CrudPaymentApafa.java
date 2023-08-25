package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.PaymentApafa;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpecPaymentApafa;
import pe.edu.vallegrande.app.service.spec.RowMapperPaymentApafa;

public class CrudPaymentApafa
		implements CrudServiceSpecPaymentApafa<PaymentApafa>, RowMapperPaymentApafa<PaymentApafa> {

	// Definiendo cosas
	private final String SQL_SELECT_BASE = "SELECT * FROM list_payment_apafa";

//	private final String SQL_SELECT_BASE = "SELECT\r\n" +
//		    "    pf.id AS 'id',\r\n" +
//		    "    pf.amount AS 'Monto_APAFA',\r\n" +
//		    "    FORMAT(pf.dates, 'dd - MMM - yyyy') AS 'fecha_Registro',\r\n" +
//		    "    p.names + ' ' + p.last_name AS 'Persona_ID',\r\n" +
//		    "    a.names + ' ' + CAST(a.budget AS VARCHAR(50)) AS 'Actividad_ID',\r\n" +
//		    "    CASE \r\n" +
//		    "        WHEN pf.tipe_pay = 'E' THEN 'EFECTIVO' \r\n" +
//		    "        WHEN pf.tipe_pay = 'T' THEN 'TRANSFERENCIA' \r\n" +
//		    "        ELSE 'YAPE' \r\n" +
//		    "    END AS 'TIPO_PAGO'\r\n" +
//		    "FROM \r\n" +
//		    "    payment_apafa pf\r\n" +
//		    "JOIN \r\n" +
//		    "    person p ON pf.person_id = p.id\r\n" +
//		    "JOIN\r\n" +
//		    "    activity a ON pf.activity_id = a.id";
	private final String SQL_INSERT = "SET IDENTITY_INSERT payment_apafa ON INSERT INTO payment_apafa (id, tipe_pay, amount, dates,person_id, activity_id) VALUES(?,?,?,?,?,?) SET IDENTITY_INSERT payment_apafa OFF";
	private final String SQL_UPDATE = "UPDATE payment_apafa SET tipe_pay=?, amount=?, dates=?,person_id=?, activity_id=?  WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM payment_apafa WHERE id=?";

	// Método para obtener una conexión a la base de datos
	private Connection getConnection() throws SQLException {
		return AccesoDB.getConnection();
	}

	// listamos nuestra DB persona hacemos que solo aparezcan los estados A
	public List<PaymentApafa> getAll() {
		List<PaymentApafa> notes = new ArrayList<>();

		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_BASE);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String tipe_pay = resultSet.getString("TIPO_PAGO");
				String amount = resultSet.getString("Monto_APAFA");
				String dates = resultSet.getString("fecha_Registro");
				String person_id = resultSet.getString("Persona_ID");
				String activity_id = resultSet.getString("Actividad_ID");

				PaymentApafa noteObj = new PaymentApafa();
				noteObj.setId(id);
				noteObj.setTipe_pay(tipe_pay);
				noteObj.setAmount(amount);
				noteObj.setDates(dates);
				noteObj.setPerson_id(person_id);
				noteObj.setActivity_id(activity_id);

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

	public void insert(PaymentApafa bean) {
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
			sql = "SELECT valor FROM controller WHERE parametro='payment_apafa'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				rs.close();
				pstm.close();
				throw new SQLException("Contador de pago no existe");
			}
			id = Integer.parseInt(rs.getString("valor"));
			rs.close();
			pstm.close();
			// Actualizar contador
			id++;
			sql = "UPDATE controller SET valor = ? WHERE parametro='payment_apafa'";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, id + "");
			pstm.executeUpdate();
			pstm.close();
			// Insertar nuevo teacher
			pstm = cn.prepareStatement(SQL_INSERT);
			pstm.setString(1, id + "");
			pstm.setString(2, bean.getTipe_pay());
			pstm.setString(3, bean.getAmount());
			pstm.setString(4, bean.getDates());
			pstm.setString(5, bean.getPerson_id());
			pstm.setString(6, bean.getActivity_id());
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

	// listar por ID
	public PaymentApafa getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PaymentApafa bean = null;
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
	public List<PaymentApafa> get(PaymentApafa bean) {
		// Variables
		Connection cn = null;
		List<PaymentApafa> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PaymentApafa item;
		String sql;
		String person_id;
		String activity_id;
		String tipe_pay;
		// Preparar los datos
		person_id = "%" + UtilService.setStringVacio(bean.getPerson_id()) + "%";
		activity_id = "%" + UtilService.setStringVacio(bean.getActivity_id()) + "%";
		tipe_pay = "%" + UtilService.setStringVacio(bean.getTipe_pay()) + "%";
		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE + " WHERE Persona_ID LIKE ? AND Actividad_ID LIKE ? AND TIPO_PAGO LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, person_id);
			pstm.setString(2, activity_id);
			pstm.setString(3, tipe_pay);
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

	// actualizar lista
	public void update(PaymentApafa bean) {
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
			pstm.setString(1, bean.getTipe_pay());
			pstm.setString(2, bean.getAmount());
			pstm.setString(3, bean.getDates());
			pstm.setString(4, bean.getPerson_id());
			pstm.setString(5, bean.getActivity_id());
			pstm.setInt(6, bean.getId());
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException(
						"Error en CrudPaymentApafa, Rapido!! verifique sus datos e intentelo nuevamente.");
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

	public PaymentApafa mapRow(ResultSet rs) throws SQLException {
		PaymentApafa bean = new PaymentApafa();
		// Columnas: id, TIPO PAGO; MONTO PAGO;FECHA DE REGISTRO;
		// PERSONA ID;ACTIVIDAD ID;
		bean.setId(rs.getInt("id"));
		bean.setTipe_pay(rs.getString("TIPO_PAGO"));
		bean.setAmount(rs.getString("Monto_APAFA"));
		bean.setDates(rs.getString("fecha_Registro"));
		bean.setPerson_id(rs.getString("Persona_ID"));
		bean.setActivity_id(rs.getString("Actividad_ID"));

		return bean;
	}

}