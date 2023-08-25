package pe.edu.vallegrande.app.pruebaWorker;

import pe.edu.vallegrande.app.model.Worker;
import pe.edu.vallegrande.app.service.CrudWorkerService;

public class PruebaCrear {

	//CREAR de datos en la db
	public static void main(String[] args) {
		try {
			// Datos
			Worker bean = new Worker(0,"DNI","71888950","3","S");
			// Proceso
			CrudWorkerService workerService = new CrudWorkerService();
			workerService.insert(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}