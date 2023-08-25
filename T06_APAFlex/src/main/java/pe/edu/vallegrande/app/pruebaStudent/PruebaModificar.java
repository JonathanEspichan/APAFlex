package pe.edu.vallegrande.app.pruebaStudent;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class PruebaModificar {
	
	//MODIFICAR un registro de la tabla

	public static void main(String[] args) {
		try {
			// Datos
			Student bean = new Student(36,"jonathan javier","espichan quinto","DNI","71899876","970443396","quintojavier@gmail.com","peruano","5to A.","A","2");
			// Proceso
			CrudStudentService studentService = new CrudStudentService();
			studentService.update(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
