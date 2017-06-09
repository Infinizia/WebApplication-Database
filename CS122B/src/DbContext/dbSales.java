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
	
	public dbSales(){
		super();
		this.tableName = dbSales.table_name;
	}
	
	public ArrayList<Sale> GetSalesByCustomerId(int id){
		try{
			String selectAllQuery = String.format("select * from %s where %s = %d", this.tableName, dbSales.customer_id_col, id);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			ArrayList<Sale> allSales = new ArrayList<Sale>();
		    while (r.next())
		    {
		    	Sale sale = new Sale();
		    	sale.setId(r.getInt(dbSales.id_col));
		    	sale.setCustomer_id(r.getInt(dbSales.customer_id_col));
		    	sale.setMovie_id(r.getInt(dbSales.movie_id_col));
		    	sale.setSale_date(r.getDate(dbSales.sale_date_col));

		    	allSales.add(sale);
		    }
		    return allSales;
		}
		catch(Exception e){
			System.out.println("Error occured getting sales");
			return null;
		}
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
			return count;
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	public int DeleteSaleByCustomerId(int customer_id){
		String deleteQuery = String.format("delete from %s where %s=%d", this.tableName, dbSales.customer_id_col, customer_id);
		return super.ExecuteUpdate(deleteQuery);
	}
}
