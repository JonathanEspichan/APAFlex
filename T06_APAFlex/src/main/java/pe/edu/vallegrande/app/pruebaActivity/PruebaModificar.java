package pe.edu.vallegrande.app.pruebaActivity;


import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaModificar {

	// actualizar un registro de la tabla

	public static void main(String[] args) {
		try {
			// Datos
			Activity bean = new Activity(13,"Dia de la bandera","4000.00"," 4.00","3");
			
			// Proceso
			CrudActivityService activityService = new CrudActivityService();
			activityService.update(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}