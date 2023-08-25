package pe.edu.vallegrande.app.pruebaActivity;

import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.CrudActivityService;

public class PruebaListaID {

//lista por ID=2
	public static void main(String[] args) {
		try {
			CrudActivityService activityService = new CrudActivityService();
			Activity bean = activityService.getForId("4");
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
