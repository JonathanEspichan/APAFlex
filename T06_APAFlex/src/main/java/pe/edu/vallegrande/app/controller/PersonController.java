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

import pe.edu.vallegrande.app.model.Person;
import pe.edu.vallegrande.app.service.CrudPersonService;

@WebServlet({ "/PersonBuscar", "/PersonBuscar2", "/PersonProcesar" , "/PersonInactivos"})
public class PersonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudPersonService service = new CrudPersonService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/PersonBuscar":
			buscar(request, response);
			break;
		case "/PersonBuscar2":
			buscar2(request, response);
			break;
		case "/PersonProcesar":
			procesar(request, response);
			break;
		case "/PersonInactivos":
			inactivos(request, response);
			break;
		}

	}

	private void inactivos(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Person> lista = service.getInactivos();
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);
		
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String accion = request.getParameter("accion");
		Person bean = new Person();
		bean.setId(Integer.parseInt(request.getParameter("id")));
		bean.setNames(request.getParameter("names"));
		bean.setLast_name(request.getParameter("last_name"));
		bean.setDocument_type(request.getParameter("document_type"));
		bean.setNumber_document(request.getParameter("number_document"));
		bean.setCell_phone(request.getParameter("cell_phone"));
		bean.setEmail(request.getParameter("email"));
		bean.setSex(request.getParameter("sex"));
		bean.setStates(request.getParameter("states"));
		bean.setCivil_status(request.getParameter("civil_status"));

		// Proceso
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

			default:
				throw new IllegalArgumentException("Unexpected value: " + accion);
			}
			ControllerUtil.responseJson(response, "EL proceso se GUARDO, Click BUSCAR");

		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Datos
		String last_name = request.getParameter("last_name");
		String names = request.getParameter("names");
		// Proceso
		Person bean = new Person();
		bean.setLast_name(last_name);
		bean.setNames(names);
		List<Person> lista = service.get(bean);
		// Reporte
		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("person.jsp");
		rd.forward(request, response);
	}

	private void buscar2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Datos
		String last_name = request.getParameter("last_name");
		String names = request.getParameter("names");
		String document_type = request.getParameter("document_type");
		// Proceso
		Person bean = new Person();
		bean.setLast_name(last_name);
		bean.setNames(names);
		bean.setDocument_type(document_type);
		List<Person> lista = service.get(bean);
		lista.forEach(valor -> System.out.println("Persona: " + valor.toString()));
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);

	}

}
