package pe.edu.vallegrande.app.pruebaPerson;

	import pe.edu.vallegrande.app.model.Person;
	import pe.edu.vallegrande.app.service.CrudPersonService;

	public class PruebaModificar {
		
		//actualizar un registro de la tabla

		public static void main(String[] args) {
			try {
				// Datos
				Person bean = new Person(24,"Flor","Quispe","DNI","98725476","123328849","flor@gmail.com","M","A","Casado");
				// Proceso
				CrudPersonService personService = new CrudPersonService();
				personService.update(bean);
				System.out.println(bean);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

	}