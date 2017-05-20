package DbContext;
import java.sql.*;


public final class dbConnection {
	
	private static Connection appDbConnection = null;
	private static String myLogin = "root";
	private static String myPass = "nguyen";
	private static String myUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
	private static String driverName = "com.mysql.jdbc.Driver";
	
	public static boolean SetConnection(String username, String password){
		try{
			if(appDbConnection == null){
				 Class.forName(driverName).newInstance();
				 
				 if(username != null && !username.isEmpty() && password != null && !password.isEmpty()){
					 appDbConnection = DriverManager.getConnection(myUrl, username, password); 
				 }				
			}
			return true;
		}
		catch(Exception e){
			System.out.println("Could not make connection to database. Please check your connection or your login credentials");
			return false;
		}
	}
	
	public static boolean DropConnection(){
		try{
			if(appDbConnection != null){
				 appDbConnection = null; 	
			}
			return true;
		}
		catch(Exception e){
			System.out.println("Could not drop connection to database");
			return false;
		}
	}
	
	public static Connection GetConnection(){
		try{
			if(appDbConnection == null){
				SetConnection(myLogin, myPass);
			}
			return appDbConnection;
		}
		catch(Exception e){
			System.out.println("Could not make connection to database. \nException:");
			e.printStackTrace();
			return null;
		}
	}
}
