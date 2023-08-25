package pe.edu.vallegrande.app.pruebaWorker;

import pe.edu.vallegrande.app.service.CrudWorkerService;

public class PruebaEliminarRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "7";
			// Proceso
			CrudWorkerService workerService = new CrudWorkerService();
			workerService.delete(id);
			System.out.println("Registro eliminado pipipii.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
