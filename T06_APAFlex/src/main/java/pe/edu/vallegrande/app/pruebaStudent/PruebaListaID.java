package pe.edu.vallegrande.app.pruebaStudent;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class PruebaListaID {

//lista mediante el id=4
	public static void main(String[] args) {
		try {
			CrudStudentService studentService = new CrudStudentService();
			Student bean = studentService.getForId("2");
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
