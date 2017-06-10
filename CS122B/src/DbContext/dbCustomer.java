package DbContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DbModel.Creditcard;
import DbModel.Customer;

public class dbCustomer extends dbContext{
	public static final String id_col = "id";
	public static final String first_name_col = "first_name";
	public static final String last_name_col = "last_name";
	public static final String cc_id_col = "cc_id";
	public static final String address_col = "address";
	public static final String email_col = "email";
	public static final String password_col = "password";
	public static final String table_name = "customers";
	
	public dbCustomer(String setting){
		super(setting);
		this.tableName = dbCustomer.table_name;
	}
	
	public ArrayList<Customer> GetAllCustomer(){
		try{
			String selectAllQuery = String.format("select * from %s", this.tableName);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			ArrayList<Customer> allCustomers = new ArrayList<Customer>();
		    while (r.next())
		    {
		    	Customer cust = new Customer();
		    	cust.setId(r.getInt(dbCustomer.id_col));
		    	cust.setFirst_name(r.getString(dbCustomer.first_name_col));
		    	cust.setLast_name(r.getString(dbCustomer.last_name_col));
		    	cust.setCc_id(r.getString(dbCustomer.cc_id_col));
		    	cust.setAddress(r.getString(dbCustomer.address_col));
		    	cust.setEmail(r.getString(dbCustomer.email_col));
		    	cust.setPassword(r.getString(dbCustomer.password_col));
		    	allCustomers.add(cust);
		    }
			return allCustomers;
		}
		catch(Exception e){
			System.out.println("Error occured getting customers");
			return null;
		}
	}
	
	public Customer GetCustomerByEmailAndPassword(String email, String password){
		try{
			String query = String.format("select * from %s where %s=\'%s\' and %s=\'%s\'", this.tableName, dbCustomer.email_col, email, dbCustomer.password_col, password);
			PreparedStatement ps = sqlConnection.prepareStatement(query);
			ResultSet r = ps.executeQuery();
			Customer cust = null;
		    while (r.next())
		    {
		    	cust = new Customer();
		    	cust.setId(r.getInt(dbCustomer.id_col));
		    	cust.setFirst_name(r.getString(dbCustomer.first_name_col));
		    	cust.setLast_name(r.getString(dbCustomer.last_name_col));
		    	cust.setCc_id(r.getString(dbCustomer.cc_id_col));
		    	cust.setAddress(r.getString(dbCustomer.address_col));
		    	cust.setEmail(r.getString(dbCustomer.email_col));
		    	cust.setPassword(r.getString(dbCustomer.password_col));
		    }
		    r.close();
		    ps.close();
		    return cust;
		}
		catch(Exception e){
			System.out.println("Error occured getting customers");
			System.out.println("Exception: ");
			e.printStackTrace();
			return null;
		}
	}
	
	public int InsertCustomer(Customer c){
		dbCreditcards dbcc = new dbCreditcards(this.setting);
		Creditcard cc = dbcc.GetCreditcard(c.cc_id);
		
		if(cc != null){
			if(c != null){
				if( (c.last_name == null || c.last_name.isEmpty()) && !c.first_name.isEmpty()){
					c.last_name = c.first_name;
					c.first_name = "";
				}
				else if(c.first_name == null){
					c.first_name = "";
				}
				
				String insertQuery = String.format("insert into %s (%s, %s, %s, %s, %s, %s) values('%s', '%s', '%s', '%s', '%s', '%s')", 
													this.tableName, dbCustomer.first_name_col, dbCustomer.last_name_col, dbCustomer.cc_id_col, dbCustomer.address_col, dbCustomer.email_col, dbCustomer.password_col,
													c.first_name, c.last_name, c.cc_id, c.address, c.email, c.password);
				return super.ExecuteUpdate(insertQuery);
			}
		}
		return -1;
	}
	
	public int DeleteCustomerById(int customer_id){
		try{
			dbSales salesDb = new dbSales(this.setting);
			if(customer_id != -1){
				if(salesDb.DeleteSaleByCustomerId(customer_id) != -1){
					String deleteQuery = String.format("delete from %s where id=%d", this.tableName, customer_id);
					return super.ExecuteUpdate(deleteQuery);
				}
			}
		}
		catch(Exception e){
			System.out.println("Error occured deleting customers");
		}
		return -1;
	}

}
