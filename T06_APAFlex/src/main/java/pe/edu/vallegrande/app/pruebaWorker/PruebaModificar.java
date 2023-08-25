package pe.edu.vallegrande.app.pruebaWorker;

import pe.edu.vallegrande.app.model.Worker;
import pe.edu.vallegrande.app.service.CrudWorkerService;

public class PruebaModificar {
	
	//MODIFICAR un registro de la tabla

	public static void main(String[] args) {
		try {
			// Datos
			Worker bean = new Worker(30,"CNE","718889250","3","S");
			// Proceso
			CrudWorkerService workerService = new CrudWorkerService();
			workerService.update(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
