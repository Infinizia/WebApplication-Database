package DbContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DbModel.Metadata;


public class dbContext{
	protected Connection sqlConnection = null;
	public String tableName = null;
	public String setting;
	
	public dbContext(String setting){
		try{
			this.setting = setting;
			sqlConnection = dbConnection.GetConnection(setting);
			if(sqlConnection == null){
				System.out.println("We do not have connection to databse");
			}
				
		}
		catch(Exception e){
			System.out.println("We do not have connection to databse");
		}
	}
	protected ResultSet ExecuteQuery(String query){
		try{
			 Statement select = sqlConnection.createStatement();
			 return select.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Could not execute query");
			return null;
		}
	}
	
	protected int ExecuteUpdate(String updateQuery){
		try{
			 Statement select = sqlConnection.createStatement();
			 return select.executeUpdate(updateQuery);
		}
		catch(Exception e){
			System.out.println("Could not execute update");
			System.out.println("Exception: " + e.toString());
			return -1;
		}
	}
	
	public ArrayList<Metadata> PrintMetaData(){
		Metadata db = new Metadata();
		ArrayList<Metadata> dataList = new ArrayList<Metadata>();
		try{
			DatabaseMetaData md = sqlConnection.getMetaData();
			ResultSet rs = md.getTables(null,null,"%",null);
			while(rs.next())
			{
				db = new Metadata();
				tableName = rs.getString("TABLE_NAME");
				db.setTableName(tableName);
				String selectQuery = String.format("select * from %s limit 1", tableName);
				ResultSet r = this.ExecuteQuery(selectQuery);
				ResultSetMetaData metadata = r.getMetaData();

				// Print type of each attribute
				for (int i = 1; i <= metadata.getColumnCount(); i++)
				{	
					 String colLabel = metadata.getColumnLabel(i);
					 String colType = metadata.getColumnTypeName(i);
					 db.setColumnAttribute(colLabel, colType);

				}
				dataList.add(db);
			}
		}
		catch(Exception e){
			System.out.println("Failed to print meta data");
		}
		return dataList;
		
	}

}
