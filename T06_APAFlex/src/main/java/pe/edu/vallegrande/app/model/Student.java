package pe.edu.vallegrande.app.model;

public class Student {

	private Integer id;
	private String names;
	private String last_name;
	private String document_type;
	private String number_document;
	private String cell_phone;
	private String email;
	private String nationality;
	private String academic_degree;
	private String states;
	private String persona_id;

	public Student() {

	}

	public Student(Integer id, String names, String last_name, String document_type, String number_document,
			String cell_phone, String email, String nationality, String academic_degree, String states,
			String persona_id) {
		super();
		this.id = id;
		this.names = names;
		this.last_name = last_name;
		this.document_type = document_type;
		this.number_document = number_document;
		this.cell_phone = cell_phone;
		this.email = email;
		this.nationality = nationality;
		this.academic_degree = academic_degree;
		this.states = states;
		this.persona_id = persona_id;

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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAcademic_degree() {
		return academic_degree;
	}

	public void setAcademic_degree(String academic_degree) {
		this.academic_degree = academic_degree;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getPersona_id() {
		return persona_id;
	}

	public void setPersona_id(String persona_id) {
		this.persona_id = persona_id;
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
		data += ", nationality: " + this.nationality;
		data += ", academic_degree: " + this.academic_degree;
		data += ", states: " + this.states;
		data += ", persona_id: " + this.persona_id + "]";
		return data;
	}

}