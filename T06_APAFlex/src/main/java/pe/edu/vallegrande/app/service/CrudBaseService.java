package pe.edu.vallegrande.app.service;

import java.util.List;

import pe.edu.vallegrande.app.model.Activity;
import pe.edu.vallegrande.app.service.spec.CrudServiceMaestros;

public class CrudBaseService implements CrudServiceMaestros<Activity> {

	@Override
	public List<Activity> getActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getInactive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity getForId(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> get(Activity bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Activity bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Activity bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String identifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(String identifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminate(String identifier) {
		// TODO Auto-generated method stub
		
	}
	
	

}
