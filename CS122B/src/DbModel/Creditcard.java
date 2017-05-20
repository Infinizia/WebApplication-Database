package DbModel;
import java.sql.Date;


public class Creditcard {
	public String cc_id  = null;
	public String first_name = null;
	public String last_name = null;
	public String expiration = null;
	
	public Creditcard(){
		
	}
	public String getId() {
		return cc_id ;
	}

	public void setId(String id) {
		this.cc_id  = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
}
