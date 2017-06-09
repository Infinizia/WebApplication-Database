package DbContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DbModel.Sale;

public class dbSales extends dbContext{
	public static final String id_col = "id";
	public static final String customer_id_col = "customer_id";
	public static final String movie_id_col = "movie_id";
	public static final String sale_date_col = "sale_date";
	public static final String table_name = "sales";
	
	public dbSales(String setting){
		super(setting);
		this.tableName = dbSales.table_name;
	}
	public int AddSales(List<Sale> sales)
	{
		try {
			int count = 0;
			String insertAllQuery = "";
			for(int i = 0; i < sales.size(); ++i) {
				insertAllQuery = String.format("insert into %s (customer_id, movie_id, sale_date) values (%s,%s,CURDATE());",
												this.tableName, sales.get(i).getCustomer_id(), sales.get(i).getMovie_id());
				count += super.ExecuteUpdate(insertAllQuery);
			}
			ps.close();
			return count;
		}
		catch (Exception e) {
			return 0;
		}
	}
}
