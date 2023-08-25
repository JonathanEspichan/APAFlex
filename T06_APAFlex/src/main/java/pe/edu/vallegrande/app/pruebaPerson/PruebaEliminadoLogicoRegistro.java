package pe.edu.vallegrande.app.pruebaPerson;

import pe.edu.vallegrande.app.service.CrudPersonService;

public class PruebaEliminadoLogicoRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "25";
			// Proceso
			CrudPersonService personService = new CrudPersonService();
			personService.EliminadoLogico(id);
			System.out.println(" El registro se ELIMINO de manera LÓGICA  el estado paso a I");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}