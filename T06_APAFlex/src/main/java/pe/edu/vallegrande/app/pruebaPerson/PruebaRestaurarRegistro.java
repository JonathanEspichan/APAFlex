package pe.edu.vallegrande.app.pruebaPerson;

import pe.edu.vallegrande.app.service.CrudPersonService;

public class PruebaRestaurarRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "25";
			// Proceso
			CrudPersonService personService = new CrudPersonService();
			personService.Restaurar(id);
			System.out.println("Muy bien el registro Se RESTAURO a estado A");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}