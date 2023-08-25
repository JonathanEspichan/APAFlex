package pe.edu.vallegrande.app.pruebaPerson;

import pe.edu.vallegrande.app.model.Person;
import pe.edu.vallegrande.app.service.CrudPersonService;

public class PruebaCrear {

	//insertación un registro en la tabla person
	public static void main(String[] args) {
		try {
			// Datos
			Person bean = new Person (0,"zzzzz","Sanchez ","CNE", "171999785","902876995","zzzz@gmail.com","M","A","Soltero");
			// Proceso
			CrudPersonService personService = new CrudPersonService();
			personService.insert(bean);
			System.out.println(bean);
		} 	catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}