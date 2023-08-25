package pe.edu.vallegrande.app.model;

import java.sql.Date;

public class Activity {

	private Integer id;
	private String names;
	private Date date_hour;
	private String budget;
	private String single_amount;
	private String states;
	private String worker_id;

	public Activity() {
		// TODO Auto-generated constructor stub
	}

	public Activity(Integer id, String names, Date date_hour, String budget, String single_amount, String states,
			String worker_id) {
		super();
		this.id = id;
		this.names = names;
		this.date_hour = date_hour;
		this.budget = budget;
		this.single_amount = single_amount;
		this.states = states;
		this.worker_id = worker_id;
	}

	//BORRADO logico
	public Activity(String names, String budget, String single_amount, String worker_id) {
		super();
		this.names = names;
		this.budget = budget;
		this.single_amount = single_amount;
		this.worker_id = worker_id;
	}

	
	//RESTAURAR el eliminado
	public Activity(Integer id, String names, String budget, String single_amount, String worker_id) {
		super();
		this.id = id;
		this.names = names;
		this.budget = budget;
		this.single_amount = single_amount;
		this.worker_id = worker_id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Date getDate_hour() {
		return date_hour;
	}

	public void setDate_hour(Date date_hour) {
		this.date_hour = date_hour;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getSingle_amount() {
		return single_amount;
	}

	public void setSingle_amount(String single_amount) {
		this.single_amount = single_amount;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getWorker_id() {
		return worker_id;
	}

	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String data = "[id: " + this.id;
		data += ", names: " + this.names;
		data += ", date_hour: " + this.date_hour;
		data += ", budget: " + this.budget;
		data += ", single_amount: " + this.single_amount;
		data += ", states: " + this.states;
		data += ", worker_id: " + this.worker_id + "]";
		return data;
	}

}
