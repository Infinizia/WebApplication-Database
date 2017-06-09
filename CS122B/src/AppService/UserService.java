package AppService;

import DbContext.dbCustomer;
import DbModel.Customer;

public class UserService {
	private dbCustomer db = null;
	
	public UserService(){
		db = new dbCustomer("");
	}
	
	public Customer getCustomerByEmailAndPassword(String email, String password){
		return db.GetCustomerByEmailAndPassword(email, password);
	}
}
