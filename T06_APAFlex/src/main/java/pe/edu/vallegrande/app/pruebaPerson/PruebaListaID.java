package pe.edu.vallegrande.app.pruebaPerson;

import pe.edu.vallegrande.app.model.Person;
import pe.edu.vallegrande.app.service.CrudPersonService;

public class PruebaListaID {

//lista por ID=2
	public static void main(String[] args) {
		try {
			CrudPersonService personService = new CrudPersonService();
			Person bean = personService.getForId("6");
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
