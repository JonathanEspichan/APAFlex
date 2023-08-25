package pe.edu.vallegrande.app.pruebaPaymentApafa;


import pe.edu.vallegrande.app.model.PaymentApafa;
import pe.edu.vallegrande.app.service.CrudPaymentApafa;

public class PruebaCrear {

	//insertación un registro en la tabla person
	public static void main(String[] args) {
		try {
			// Datos
			PaymentApafa bean = new PaymentApafa (0,"Y","40.00","08/08/2023","1","6");
			// Proceso
			CrudPaymentApafa payment_apafaService = new CrudPaymentApafa();
			payment_apafaService.insert(bean);
			System.out.println(bean);
		} 	catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}