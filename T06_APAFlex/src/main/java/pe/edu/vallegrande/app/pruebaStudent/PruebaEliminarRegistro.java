package pe.edu.vallegrande.app.pruebaStudent;

import pe.edu.vallegrande.app.service.CrudStudentService;

public class PruebaEliminarRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "7";
			// Proceso
			CrudStudentService studentService = new CrudStudentService();
			studentService.delete(id);
			System.out.println("Registro eliminado pipipii.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
