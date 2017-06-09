package DbModel;

public class Customer {
	public int id = -1;
	public String first_name = null;
	public String last_name = null;
	public String cc_id = null;
	public String address = null;
	public String email = null;
	public String password = null;
	public String expiration = null;
	public String recaptcha = null;
	
	public Customer(){
		
	}
	public void Print(){
		System.out.println(String.format("ID:%d, first_name:%s, last_name:%s, cc_id:%s, address:%s, email:%s, password:%s",
				this.id, this.first_name, this.last_name, this.cc_id, this.address, this.email, this.password));
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getCc_id() {
		return cc_id;
	}
	public void setCc_id(String cc_id) {
		this.cc_id = cc_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


}
