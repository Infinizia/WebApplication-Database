package DbContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DbModel.Employee;



public class dbEmployee extends dbContext {
	public static final String email_col = "email";
	public static final String password_col = "password";
	public static final String fullname_col = "fullname";

	public dbEmployee(String setting){
		super(setting);
		this.tableName = "employees";
	}
	
	public Employee GetEmployee(String email, String password){
		try{
			String selectQuery = String.format("select * from %s where email = '%s' and password='%s'", this.tableName, email, password);
			PreparedStatement ps = sqlConnection.prepareStatement(selectQuery);
			ResultSet r = ps.executeQuery();
			Employee emp = null;
			
		    while (r.next())
		    {
		    	emp = new Employee();
		    	emp.email = r.getString(email_col);
		    	emp.password = r.getString(password_col);
		    	emp.fullname = r.getString(fullname_col);
		    }
		    return emp;
		}
		catch(Exception e){
			System.out.println("Error occured, credit card does not exist");
			return null;
		}
	}

}