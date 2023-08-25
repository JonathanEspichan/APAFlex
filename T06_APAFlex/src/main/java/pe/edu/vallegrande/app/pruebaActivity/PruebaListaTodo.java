package pe.edu.vallegrande.app.pruebaActivity;

import java.util.List;

import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaListaTodo {
	// listar tabla completa
	public static void main(String[] args) {
		try {
			CrudActivityService activityService = new CrudActivityService();
			List<Activity> lista = activityService.getAll();
			for (Activity activity : lista) {
				System.out.println(activity);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
