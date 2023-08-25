package pe.edu.vallegrande.app.model;

public class PaymentApafa {

	private Integer id;
	private String tipe_pay;
	private String amount;
	private String dates;
	private String person_id;
	private String activity_id;

	public PaymentApafa() {

	}

	public PaymentApafa(Integer id, String tipe_pay, String amount, String dates, String person_id,
			String activity_id) {
		super();
		this.id = id;
		this.tipe_pay = tipe_pay;
		this.amount = amount;
		this.dates = dates;
		this.person_id = person_id;
		this.activity_id = activity_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipe_pay() {
		return tipe_pay;
	}

	public void setTipe_pay(String tipe_pay) {
		this.tipe_pay = tipe_pay;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	@Override
	public String toString() {
		String data = "[id: " + this.id;
		data += ", tipe_pay: " + this.tipe_pay;
		data += ", amount: " + this.amount;
		data += ", dates: " + this.dates;
		data += ", person_id: " + this.person_id;
		data += ", activity_id: " + this.activity_id + "]";
		return data;
	}

//	@Override
//	public String toString() {
//	    StringBuilder sb = new StringBuilder();
//	    String horizontalLine = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
//	    sb.append(horizontalLine).append("\n");
//	    sb.append("|  ID  | TIPO PAGO |   MONTO DE APAFA   |     FECHA REGISTRO     |  PERSONA ID  | ACTIVIDAD ID |\n");
//	    sb.append(horizontalLine).append("\n");
//	    sb.append(String.format("|%6d|%16s|%14s|%13s|%17s|\n", id, tipe_pay, amount, dates, person_id, activity_id ));
//	    sb.append(horizontalLine);
//
//	    return sb.toString();
//	}

}