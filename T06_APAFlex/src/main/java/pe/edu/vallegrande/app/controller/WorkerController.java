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
import pe.edu.vallegrande.app.model.Worker;
import pe.edu.vallegrande.app.service.CrudPersonService;
import pe.edu.vallegrande.app.service.CrudWorkerService;

@WebServlet({ "/WorkerBuscar", "/WorkerBuscar2", "/WorkerProcesar", "/SelectListarTodo" })
public class WorkerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudWorkerService service = new CrudWorkerService();
	private CrudPersonService service2 = new CrudPersonService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/WorkerBuscar":
			buscar(request, response);
			break;
		case "/WorkerBuscar2":
			buscar2(request, response);
			break;
		case "/WorkerProcesar":
			procesar(request, response);
			break;

		case "/SelectListarTodo":
			buscarTodos(request, response);
			break;

		}
	}

	private void buscarTodos(HttpServletRequest request, HttpServletResponse response) {
		List<Person> lista = service2.getAll();
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		String accion = request.getParameter("accion");
		Worker bean = new Worker();
		bean.setId(Integer.parseInt(request.getParameter("id")));
		bean.setDocument_type(request.getParameter("document_type"));
		bean.setNumber_document(request.getParameter("number_document"));
		bean.setPerson_id(request.getParameter("person_id"));
		bean.setCharges(request.getParameter("charges"));

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

			default:
				throw new IllegalArgumentException("Unexpected value: " + accion);
			}
			ControllerUtil.responseJson(response, "EL proceso se GUARDO, Click en BUSCAR");

		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String charges = request.getParameter("charges");
		String document_type = request.getParameter("document_type");

		Worker bean = new Worker();
		bean.setCharges(charges);
		bean.setDocument_type(document_type);
		List<Worker> lista = service.get(bean);

		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("worker.jsp");
		rd.forward(request, response);
	}

	private void buscar2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String charges = request.getParameter("charges");
		String document_type = request.getParameter("document_type");

		Worker bean = new Worker();
		bean.setCharges(charges);
		bean.setDocument_type(document_type);
		List<Worker> lista = service.get(bean);

		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);
	}

}
