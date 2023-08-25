package pe.edu.vallegrande.app.pruebaPaymentApafa;

import java.util.List;

import pe.edu.vallegrande.app.model.PaymentApafa;
import pe.edu.vallegrande.app.service.CrudPaymentApafa;

public class PruebaListaTodo {
	// listar tabla completa
	public static void main(String[] args) {
		try {
			CrudPaymentApafa payment_apafaService = new CrudPaymentApafa();
			List<PaymentApafa> lista = payment_apafaService.getAll();
			for (PaymentApafa payment_apafa : lista) {
				System.out.println(payment_apafa);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}