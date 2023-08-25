package pe.edu.vallegrande.app.pruebaPerson;

import java.util.List;

import pe.edu.vallegrande.app.model.Person;
import pe.edu.vallegrande.app.service.CrudPersonService;

public class PruebaListarInactivos {
	// listar tabla completa de INACTIVOS
	public static void main(String[] args) {
		try {
			CrudPersonService personService = new CrudPersonService();
			List<Person> lista = personService.getInactivos();
			for (Person person : lista) {
				System.out.println(person);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
