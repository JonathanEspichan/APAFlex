package pe.edu.vallegrande.app.pruebaWorker;

import java.util.List;

import pe.edu.vallegrande.app.model.Worker;
import pe.edu.vallegrande.app.service.CrudWorkerService;

public class PruebaListaNombreApe {
	//listar por nombre y apellido dentro de nuestra tabla student

	public static void main(String[] args) {
		try {
			// Datos
			Worker bean = new Worker();
			bean.setCharges("T");
			bean.setDocument_type("");
			// Proceso
			CrudWorkerService workerService = new CrudWorkerService();
			List<Worker> lista = workerService.get(bean);
			for (Worker worker : lista) {
				System.out.println(worker);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
