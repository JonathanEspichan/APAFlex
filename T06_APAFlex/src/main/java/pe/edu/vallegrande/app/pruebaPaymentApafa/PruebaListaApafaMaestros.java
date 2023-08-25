package pe.edu.vallegrande.app.pruebaPaymentApafa;

import java.util.List;

import pe.edu.vallegrande.app.model.PaymentApafa;
import pe.edu.vallegrande.app.service.CrudPaymentApafa;

public class PruebaListaApafaMaestros {

	// listar por nombre y apellido dentro de nuestra tabla pago de apafa

	public static void main(String[] args) {
		try {
			// Datos
			PaymentApafa bean = new PaymentApafa();
			bean.setPerson_id(" ");
			bean.setActivity_id("  madre");
			bean.setTipe_pay(" ");
			// Proceso
			CrudPaymentApafa payment_apafaService = new CrudPaymentApafa();
			List<PaymentApafa> lista = payment_apafaService.get(bean);
			for (PaymentApafa payment_apafa : lista) {
				System.out.println(payment_apafa);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}