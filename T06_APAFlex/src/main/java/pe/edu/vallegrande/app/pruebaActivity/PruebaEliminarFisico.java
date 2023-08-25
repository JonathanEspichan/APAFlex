package pe.edu.vallegrande.app.pruebaActivity;

import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaEliminarFisico {

	// eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "10";
			// Proceso
			CrudActivityService activityService = new CrudActivityService();
			activityService.delete(id);
			System.out.println(" upp el Registro fue eliminado, listo");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}