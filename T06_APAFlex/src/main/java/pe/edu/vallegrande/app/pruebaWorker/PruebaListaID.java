package pe.edu.vallegrande.app.pruebaWorker;

import pe.edu.vallegrande.app.model.Worker;
import pe.edu.vallegrande.app.service.CrudWorkerService;

public class PruebaListaID {

//lista mediante el id=4
	public static void main(String[] args) {
		try {
			CrudWorkerService workerService = new CrudWorkerService();
			Worker bean = workerService.getForId("2");
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
