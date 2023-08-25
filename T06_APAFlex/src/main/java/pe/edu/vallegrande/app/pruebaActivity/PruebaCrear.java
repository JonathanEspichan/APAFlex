
package pe.edu.vallegrande.app.pruebaActivity;

import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.CrudActivityService;


public class PruebaCrear {

	// insertación un registro en la tabla person
	public static void main(String[] args) {
		try {
			// Datos
			Activity bean = new Activity ("Prueba22","4000.00","4.00","2");
			// Proceso
			CrudActivityService activityService = new CrudActivityService();
			activityService.insert(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}