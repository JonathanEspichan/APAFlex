package pe.edu.vallegrande.app.model;

public class Worker {

	private Integer id;
	private String document_type;
	private String number_document;
	private String person_id;
	private String charges;

	
	public Worker() {
		// TODO Auto-generated constructor stub
	}

	
	public Worker(Integer id, String document_type, String number_document, String person_id, String charges) {
		super();
		this.id = id;
		this.document_type = document_type;
		this.number_document = number_document;
		this.person_id = person_id;
		this.charges = charges;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
	}

	@Override
	public String toString() {
		String data = "[id: " + this.id;
		data += ", document_type: " + this.document_type;
		data += ", number_document: " + this.number_document;
		data += ", person_id: " + this.person_id;
		data += ", charges: " + this.charges + "]";
		return data;
	}

}
