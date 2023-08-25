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

import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.CrudActivityService;

@WebServlet({ "/ActivityBuscar", "/ActivityBuscar2", "/ActivityProcesar", "/ActivityInactivos" })
public class ActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudActivityService service = new CrudActivityService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/ActivityBuscar":
			buscar(request, response);
			break;
		case "/ActivityBuscar2":
			buscar2(request, response);
			break;
		case "/ActivityProcesar":
			procesar(request, response);
			break;
		case "/ActivityInactivos":
			inactivos(request, response);
			break;

		}

	}

	private void inactivos(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Activity> lista = service.getInactivos();
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);

	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String accion = request.getParameter("accion");
		Activity bean = new Activity();
		bean.setId(Integer.parseInt(request.getParameter("id")));
		bean.setNames(request.getParameter("names"));
		bean.setBudget(request.getParameter("budget"));
		bean.setSingle_amount(request.getParameter("single_amount"));
		bean.setStates(request.getParameter("states"));
		bean.setWorker_id(request.getParameter("Worker_id"));

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
			ControllerUtil.responseJson(response, "Muy bien, el proceso fue EXITOSA");

		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Datos
		String single_amount = request.getParameter("single_amount");
		String names = request.getParameter("names");
		// Proceso
		Activity bean = new Activity();
		bean.setSingle_amount(single_amount);
		bean.setNames(names);
		List<Activity> lista = service.get(bean);
		// Reporte
		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("activity.jsp");
		rd.forward(request, response);
	}

	private void buscar2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Datos
		String single_amount = request.getParameter("single_amount");
		String names = request.getParameter("names");
		// Proceso
		Activity bean = new Activity();
		bean.setSingle_amount(single_amount);
		bean.setNames(names);
		List<Activity> lista = service.get(bean);
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);

	}

}
