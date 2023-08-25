package pe.edu.vallegrande.app.pruebaPaymentApafa;

import pe.edu.vallegrande.app.service.CrudPaymentApafa;

public class PruebaEliminarRegistro {

	// eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "10";
			// Proceso
			CrudPaymentApafa payment_apafaService = new CrudPaymentApafa();
			payment_apafaService.delete(id);
			System.out.println(" upp el Registro fue eliminado, listo");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}