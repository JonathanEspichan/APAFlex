package pe.edu.vallegrande.app.service.spec;

import java.sql.ResultSet;
import java.sql.SQLException; 

public interface RowMapperActivity<T> {
	

	T mapRow(ResultSet rs) throws SQLException;

	void delete(String id);

}
