package DbContext;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.ReplicationDriver;
import com.sun.xml.internal.fastinfoset.sax.Properties;


public final class dbConnection {
	
	private static Connection appDbConnection = null;
	private static Connection masterConnection = null;
	private static Connection slaveConnection = null;
	private static String myLogin = "root";
	private static String myPass = "nguyen";
	private static String myUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
	private static String driverName = "com.mysql.jdbc.Driver";
	
	public static boolean SetConnection(String username, String password){
		try{

			//Database connection pooling
			Context initCtx = new InitialContext();
            if (initCtx == null)
                System.out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                System.out.println("envCtx is NULL");

            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/DbConnection");
            DataSource dataSourceMaster = (DataSource) envCtx.lookup("jdbc/MasterInstance");
            DataSource dataSourceSlave = (DataSource) envCtx.lookup("jdbc/SlaveInstance");
            
            if (ds == null)
                System.out.println("ds is null.");
            
            masterConnection = dataSourceMaster.getConnection();
            slaveConnection = dataSourceSlave.getConnection();
            appDbConnection = ds.getConnection();
            
            if (appDbConnection == null)
                System.out.println("dbcon is null.");
            
//			Uncomment this to do db connection without pooling            	
//			if(appDbConnection == null){
//				 Class.forName(driverName).newInstance();
//				 
//				 if(username != null && !username.isEmpty() && password != null && !password.isEmpty()){
//					 appDbConnection = DriverManager.getConnection(myUrl, username, password); 
//				 }				
//			}
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
