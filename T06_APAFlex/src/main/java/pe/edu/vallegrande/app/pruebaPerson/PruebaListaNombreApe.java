package pe.edu.vallegrande.app.pruebaPerson;

import java.util.List;

import pe.edu.vallegrande.app.model.Person;
import pe.edu.vallegrande.app.service.CrudPersonService;

public class PruebaListaNombreApe {

	// listar por nombre y apellido dentro de nuestra tabla student

	public static void main(String[] args) {
		try {
			// Datos
			Person bean = new Person();
			bean.setLast_name("    ");
			bean.setNames(" ");
			bean.setDocument_type(" CNE ");
			// Proceso
			CrudPersonService personService = new CrudPersonService();
			List<Person> lista = personService.get(bean);
			for (Person person : lista) {
				System.out.println(person);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
