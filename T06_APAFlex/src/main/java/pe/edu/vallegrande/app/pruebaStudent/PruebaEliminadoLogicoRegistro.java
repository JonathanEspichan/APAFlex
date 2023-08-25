package pe.edu.vallegrande.app.pruebaStudent;

import pe.edu.vallegrande.app.service.CrudStudentService;

public class PruebaEliminadoLogicoRegistro {

	//eliminar registro

	public static void main(String[] args) {
		try {
			// Datos
			String id = "25";
			// Proceso
			CrudStudentService studentService = new CrudStudentService();
			studentService.EliminadoLogico(id);
			System.out.println(" El registro se ELIMINO de manera LÓGICA  el estado paso a I");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}