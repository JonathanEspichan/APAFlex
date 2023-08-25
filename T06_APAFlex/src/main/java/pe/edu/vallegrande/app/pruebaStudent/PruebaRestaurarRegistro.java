package pe.edu.vallegrande.app.pruebaStudent;

import pe.edu.vallegrande.app.service.CrudStudentService;

public class PruebaRestaurarRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "36";
			// Proceso
			CrudStudentService studentService = new CrudStudentService();
			studentService.Restaurar(id);
			System.out.println("Muy bien el registro Se RESTAURO a estado A");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}