package pe.edu.vallegrande.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.CrudStudentService;

@WebServlet({ "/StudentBuscar", "/StudentBuscar2", "/StudentProcesar", "/StudentInactivos", "/StudentEliminadoFisico" })
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudStudentService service = new CrudStudentService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/StudentBuscar":
			buscar(request, response);
			break;
		case "/StudentBuscar2":
			buscar2(request, response);
			break;
		case "/StudentProcesar":
			procesar(request, response);
			break;
		case "/StudentInactivos":
			inactivos(request, response);
			break;
//		case "/StudentEliminadoFisico":
//			eliminarFisico(request, response);
//			break;
		}
	}

	private void inactivos(HttpServletRequest request, HttpServletResponse response) {
		List<Student> lista = service.getInactivos();
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		String accion = request.getParameter("accion");
		Student bean = new Student();
		bean.setId(Integer.parseInt(request.getParameter("id")));
		bean.setNames(request.getParameter("names"));
		bean.setLast_name(request.getParameter("last_name"));
		bean.setDocument_type(request.getParameter("document_type"));
		bean.setNumber_document(request.getParameter("number_document"));
		bean.setCell_phone(request.getParameter("cell_phone"));
		bean.setEmail(request.getParameter("email"));
		bean.setNationality(request.getParameter("nationality"));
		bean.setAcademic_degree(request.getParameter("academic_degree"));
		bean.setStates(request.getParameter("states"));
		bean.setPersona_id(request.getParameter("persona_id"));

		try {
			switch (accion) {
			case ControllerUtil.CRUD_NUEVO:
				service.insert(bean);
				break;

			case ControllerUtil.CRUD_EDITAR:
				service.update(bean);
				break;

			case ControllerUtil.CRUD_ELIMINAR:
				service.delete(bean.getId().toString());
				break;

			case ControllerUtil.CRUD_RESTAURE:
				service.Restaurar(bean.getId().toString());
				break;

			case ControllerUtil.CRUD_ELIMINATE:
				service.EliminadoLogico(bean.getId().toString());
				break;

//			case ControllerUtil.CRUD_ELIMINADO_FISICO:
//				service.EliminadoLogico(bean.getId().toString());
//				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + accion);
			}
			ControllerUtil.responseJson(response, "EL proceso se GUARDO, Click en BUSCAR");

		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String last_name = request.getParameter("last_name");
		String names = request.getParameter("names");
		String document_type = request.getParameter("document_type");

		Student bean = new Student();
		bean.setLast_name(last_name);
		bean.setNames(names);
		bean.setDocument_type(document_type);
		List<Student> lista = service.get(bean);

		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("student.jsp");
		rd.forward(request, response);
	}

	private void buscar2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String last_name = request.getParameter("last_name");
		String names = request.getParameter("names");
		String document_type = request.getParameter("document_type");

		Student bean = new Student();
		bean.setLast_name(last_name);
		bean.setNames(names);
		bean.setDocument_type(document_type);
		List<Student> lista = service.get(bean);

		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);
	}

}
