package pe.edu.vallegrande.app.pruebaActivity;

import java.util.List;

import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaListarNombre {
	
	//listar por nombre y apellido dentro de nuestra tabla student

	public static void main(String[] args) {
		try {
			// Datos
			Activity bean = new Activity();
			bean.setSingle_amount("  ");
			bean.setNames(" madre");
			// Proceso
			CrudActivityService activityService = new CrudActivityService();
			List<Activity> lista = activityService.get(bean);
			for (Activity activity : lista) {
				System.out.println(activity);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}