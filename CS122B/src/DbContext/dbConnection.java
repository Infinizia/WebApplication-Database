package DbContext;
import java.sql.*;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.ReplicationDriver;
import com.sun.xml.internal.fastinfoset.sax.Properties;


public final class dbConnection {
	
//	private static Connection appDbConnection = null;
//	private static Connection masterConnection = null;
//	private static Connection slaveConnection = null;
//	private static String myLogin = "root";
//	private static String myPass = "nguyen";
//	private static String myUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";

	
	public static Connection GetConnection(String setting){
		try{

			//Database connection pooling
			Context initCtx = new InitialContext();
            if (initCtx == null)
                System.out.println("initCtx is NULL");

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                System.out.println("envCtx is NULL");
            // Look up our data source
            DataSource dataSourceMaster = (DataSource) envCtx.lookup("jdbc/DbMasterConnection");
            DataSource dataSourceSlave = (DataSource) envCtx.lookup("jdbc/DbSlaveConnection");
            
            if(setting.equals("master")){
            	return dataSourceMaster.getConnection();
            }
            else{
            	Random rand = new Random();
            	int n = rand.nextInt(100);
            	if(n%2 == 0)
            		return dataSourceMaster.getConnection();
            	else
            		return dataSourceSlave.getConnection();
            }
		}
		catch(Exception e){
			System.out.println("Could not make connection to database. Please check your connection or your login credentials");
			return null;
		}
	}
}
