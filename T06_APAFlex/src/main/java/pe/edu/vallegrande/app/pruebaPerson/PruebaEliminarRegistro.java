package pe.edu.vallegrande.app.pruebaPerson;

import pe.edu.vallegrande.app.service.CrudPersonService;

public class PruebaEliminarRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "21";
			// Proceso
			CrudPersonService personService = new CrudPersonService();
			personService.delete(id);
			System.out.println(" upp el Registro fue eliminado, listo");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}