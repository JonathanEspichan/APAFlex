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
import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.model.PaymentApafa;
import pe.edu.vallegrande.app.service.CrudPersonService;
import pe.edu.vallegrande.app.service.CrudActivityService;
import pe.edu.vallegrande.app.service.CrudPaymentApafa;

@WebServlet({ "/PaymentApafaBuscar", "/PaymentApafaBuscar2", "/PaymentApafaProcesar", "/SelectListarTodoPerson", "/SelectListarTodoActivity" })
public class PaymentApafaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CrudPaymentApafa service = new CrudPaymentApafa();
	private CrudPersonService service2 = new CrudPersonService();
	private CrudActivityService service3 = new CrudActivityService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/PaymentApafaBuscar":
			buscar(request, response);
			break;
		case "/PaymentApafaBuscar2":
			buscar2(request, response);
			break;
		case "/PaymentApafaProcesar":
			procesar(request, response);
			break;

		case "/SelectListarTodoPerson":
			buscarTodosPerson(request, response);
			break;

		case "/SelectListarTodoActivity":
			buscarTodosActivity(request, response);
			break;

		}
	}

	private void buscarTodosPerson(HttpServletRequest request, HttpServletResponse response) {
		List<Person> lista = service2.getAll();
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

	private void buscarTodosActivity(HttpServletRequest request, HttpServletResponse response) {
		List<Activity> lista = service3.getAll();
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		String accion = request.getParameter("accion");
		PaymentApafa bean = new PaymentApafa();
		bean.setId(Integer.parseInt(request.getParameter("id")));
		bean.setTipe_pay(request.getParameter("tipe_pay"));
		bean.setAmount(request.getParameter("amount"));
		bean.setDates(request.getParameter("dates"));
		bean.setPerson_id(request.getParameter("person_id"));
		bean.setActivity_id(request.getParameter("activity_id"));

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
		String person_id = request.getParameter("person_id");
		String activity_id = request.getParameter("activity_id");
		String tipe_pay = request.getParameter("tipe_pay");

		PaymentApafa bean = new PaymentApafa();
		bean.setPerson_id(person_id);
		bean.setActivity_id(activity_id);
		bean.setTipe_pay(tipe_pay);
		List<PaymentApafa> lista = service.get(bean);

		request.setAttribute("listado", lista);
		RequestDispatcher rd = request.getRequestDispatcher("paymentApafa.jsp");
		rd.forward(request, response);
	}

	private void buscar2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String person_id = request.getParameter("person_id");
		String activity_id = request.getParameter("activity_id");
		String tipe_pay = request.getParameter("tipe_pay");

		PaymentApafa bean = new PaymentApafa();
		bean.setPerson_id(person_id);
		bean.setActivity_id(activity_id);
		bean.setTipe_pay(tipe_pay);
		List<PaymentApafa> lista = service.get(bean);

		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);
	}

}