package pe.edu.vallegrande.app.pruebaStudent;

import java.util.List;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class PruebaListaNombreApe {
	//listar por nombre y apellido dentro de nuestra tabla student

	public static void main(String[] args) {
		try {
			// Datos
			Student bean = new Student();
			bean.setLast_name("    ");
			bean.setNames("   ");
			bean.setDocument_type("CNE");
			// Proceso
			CrudStudentService studentService = new CrudStudentService();
			List<Student> lista = studentService.get(bean);
			for (Student student : lista) {
				System.out.println(student);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
