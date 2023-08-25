package pe.edu.vallegrande.app.pruebaWorker;

import java.util.List;

import pe.edu.vallegrande.app.model.Worker;
import pe.edu.vallegrande.app.service.CrudWorkerService;

public class PruebaListaTodos {
	
	//listar tabla completa
	public static void main(String[] args) {
        try {
            CrudWorkerService workerService = new CrudWorkerService();
            List<Worker> lista = workerService.getAll();
            for (Worker worker : lista) {
                System.out.println(worker);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}