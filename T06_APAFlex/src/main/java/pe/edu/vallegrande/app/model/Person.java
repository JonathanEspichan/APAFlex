package pe.edu.vallegrande.app.model;

public class Person {

	private Integer id;
	private String names;
	private String last_name;
	private String document_type;
	private String number_document;
	private String cell_phone;
	private String email;
	private String sex;
	private String states;
	private String civil_status;
	
	
	public Person() {
		super();
	}
	
	public Person(Integer id, String names, String last_name, String document_type, String number_document,
			String cell_phone, String email, String sex, String states, String civil_status) {
		super();
		this.setId(id);
		this.setNames(names);
		this.setLast_name(last_name);
		this.setDocument_type(document_type);
		this.setNumber_document(number_document);
		this.setCell_phone(cell_phone);
		this.setEmail(email);
		this.setSex(sex);
		this.setStates(states);
		this.setCivil_status(civil_status);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}

	public String getNumber_document() {
		return number_document;
	}

	public void setNumber_document(String number_document) {
		this.number_document = number_document;
	}

	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getCivil_status() {
		return civil_status;
	}

	public void setCivil_status(String civil_status) {
		this.civil_status = civil_status;
	}

	@Override
	public String toString() {
		String data = "[id: " + this.id;
		data += ", names: " + this.names;
		data += ", last_name: " + this.last_name;
		data += ", document_type: " + this.document_type;
		data += ", number_document: " + this.number_document;
		data += ", cell_phone: " + this.cell_phone;
		data += ", email: " + this.email;
		data += ", sex: " + this.sex;
		data += ", states: " + this.states;
		data += ", civil_status: " + this.civil_status + "]";
		return data;
	}
}