package pe.edu.vallegrande.app.pruebaStudent;

import java.util.List;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

public class PruebaListarInactivos {
	// listar tabla completa de INACTIVOS
	public static void main(String[] args) {
		try {
			CrudStudentService studentService = new CrudStudentService();
			List<Student> lista = studentService.getInactivos();
			for (Student  student : lista) {
				System.out.println( student);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
