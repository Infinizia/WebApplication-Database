package DbModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Metadata {
	private String tableName = null;
	private Map columnAttribute;
	
	public Metadata()
	{
		columnAttribute= new HashMap();
	}
	public Map getColumnAttribute()
	{
		return columnAttribute;
	}
	public void setColumnAttribute(String label, String type)
	{
		columnAttribute.put(label, type);
	}
	public String getTableName()
	{
		return tableName;
	}
	
	public void setTableName(String tn)
	{
		tableName = tn;
	}
	
}
