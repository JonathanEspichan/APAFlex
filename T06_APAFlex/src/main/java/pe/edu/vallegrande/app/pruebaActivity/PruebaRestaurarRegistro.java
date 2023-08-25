package pe.edu.vallegrande.app.pruebaActivity;

import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaRestaurarRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "12";
			// Proceso
			CrudActivityService activityService = new CrudActivityService();
			activityService.Restaurar(id);
			System.out.println("Muy bien el registro Se RESTAURO a estado A");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}