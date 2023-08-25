package pe.edu.vallegrande.app.service.spec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import pe.edu.vallegrande.app.model.Person;

public interface CrudServiceSpecPerson<T> {

	/*
	 * Consulta todos los registros de la tabla.*@return Retorna una lista.
	 */

	List<T> getAll();

	/*
	 * 
	 * Retorna un registro basado en el id.*
	 * 
	 * @param id
	 * 
	 * @return
	 */
	T getForId(String id);

	/*
	 * 
	 * Consulta datos en base a un criterio.*
	 * 
	 * @param bean Datos para establecer el criterio.*@return Retorna una lista.
	 */

	List<T> get(T bean);

	/*
	 * 
	 * Insertar nuevo registro.*
	 * 
	 * @param bean Datos del nuevo registro.
	 */

	void insert(T bean);

	/*
	 * 
	 * Actualiza datos de un registro especifico.*
	 * 
	 * @param bean Datos del registro.
	 */

	void update(T bean);

	Person mapRow(ResultSet rs) throws SQLException;

	void delete(String id);

	/*
	 * 
	 * Elimina un registro de la base de datos.*
	 * 
	 * @param id El id del registro a eliminar.
	 */

}
