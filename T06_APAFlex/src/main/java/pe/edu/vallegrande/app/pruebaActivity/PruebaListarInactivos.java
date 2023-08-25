package pe.edu.vallegrande.app.pruebaActivity;

import java.util.List;

import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaListarInactivos {
	// listar tabla completa de INACTIVOS
	public static void main(String[] args) {
		try {
			CrudActivityService activityService = new CrudActivityService();
			List<Activity> lista = activityService.getInactivos();
			for (Activity activity : lista) {
				System.out.println(activity);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
