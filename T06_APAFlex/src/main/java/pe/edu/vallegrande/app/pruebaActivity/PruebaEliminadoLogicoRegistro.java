package pe.edu.vallegrande.app.pruebaActivity;

import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaEliminadoLogicoRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "12";
			// Proceso
			CrudActivityService activityService = new CrudActivityService();
			activityService.EliminadoLogico(id);
			System.out.println(" El registro se ELIMINO de manera LÓGICA  el estado paso a I");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}