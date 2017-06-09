package DbModel;


public class Filters {
	public String genre = null;
	public String searchWord = null;
	public String type = null;
	public int pageLength = 10;
	public int currentPage = 1;
	public String orderBy = "title";
	public Boolean isDesc = true;
	
	public Filters(){
		genre = "All";
		searchWord = "";
		type = "";
	}
}
