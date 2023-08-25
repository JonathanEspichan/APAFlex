package pe.edu.vallegrande.app.pruebaPaymentApafa;

import pe.edu.vallegrande.app.model.PaymentApafa;
import pe.edu.vallegrande.app.service.CrudPaymentApafa;

public class PruebaModificar {

	// actualizar un registro de la tabla

	public static void main(String[] args) {
		try {
			// Datos
			PaymentApafa bean = new PaymentApafa(19,"T","40.00","08/08/2023","4","6");
			// Proceso
			CrudPaymentApafa payment_apafaService = new CrudPaymentApafa();
			payment_apafaService.update(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}