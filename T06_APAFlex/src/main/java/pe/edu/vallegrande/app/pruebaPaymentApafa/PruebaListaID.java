package pe.edu.vallegrande.app.pruebaPaymentApafa;

import pe.edu.vallegrande.app.model.PaymentApafa;
import pe.edu.vallegrande.app.service.CrudPaymentApafa;

public class PruebaListaID {

	// lista por ID=2
	public static void main(String[] args) {
		try {
			CrudPaymentApafa payment_apafaService = new CrudPaymentApafa();
			PaymentApafa bean = payment_apafaService.getForId("6");
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}