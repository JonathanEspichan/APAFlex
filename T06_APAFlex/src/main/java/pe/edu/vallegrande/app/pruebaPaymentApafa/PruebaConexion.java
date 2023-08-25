package pe.edu.vallegrande.app.pruebaPaymentApafa;

import java.sql.Connection;

import pe.edu.vallegrande.app.db.AccesoDB;

public class PruebaConexion {

	public static void main(String[] args) {
		try {
			Connection cn = AccesoDB.getConnection();
			System.out.println("BUENA!!! la Conexión fue Establecida (ok).");
			cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}