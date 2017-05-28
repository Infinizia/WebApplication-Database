package DbContext;

import java.sql.ResultSet;
import java.util.ArrayList;

import DbModel.MovieTitleSearch;


public class dbFullText extends dbContext {
	public static final String entry_col = "entry";
	public static final String movieId_col = "movieID";

	public dbFullText(){
		super();
		this.tableName = "employees";
	}
	
	public ArrayList<MovieTitleSearch> GetMovie(String searchTextQuery){
		String selectQuery = String.format("select * from ft where match(entry) against('%s' in boolean mode) limit 5", searchTextQuery);
		ResultSet r = super.ExecuteQuery(selectQuery);
		ArrayList<MovieTitleSearch> movieList = new ArrayList<MovieTitleSearch>();
		
		try{
		    while (r.next())
		    {
		    	MovieTitleSearch movie = new MovieTitleSearch();
		    	movie.movieId = r.getInt(movieId_col);
		    	movie.title = r.getString(entry_col);
		    	movieList.add(movie);
		    }
		}
		catch(Exception e){
			System.out.println("Error occured, credit card does not exist");
			return null;
		}
		return movieList;
		
	}

}