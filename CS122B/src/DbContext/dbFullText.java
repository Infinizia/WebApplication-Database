package DbContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import DbModel.MovieTitleSearch;


public class dbFullText extends dbContext {
	public static final String entry_col = "entry";
	public static final String movieId_col = "movieID";

	public dbFullText(String setting){
		super(setting);
		this.tableName = "employees";
	}
	
	public ArrayList<MovieTitleSearch> GetMovie(Logger logger, String searchTextQuery){

		try{
			String selectQuery = String.format("select * from ft where match(entry) against('%s' in boolean mode)", searchTextQuery);
			long startTime = System.nanoTime();
			PreparedStatement ps = sqlConnection.prepareStatement(selectQuery);
			ResultSet r = ps.executeQuery();
			ArrayList<MovieTitleSearch> movieList = new ArrayList<MovieTitleSearch>();
			
		    while (r.next())
		    {
		    	MovieTitleSearch movie = new MovieTitleSearch();
		    	movie.movieId = r.getInt(movieId_col);
		    	movie.title = r.getString(entry_col);
		    	movieList.add(movie);
		    }
			long endTime = System.nanoTime();
			logger.debug(String.format("TJ_MEASURE:%d", endTime - startTime));
			return movieList;
		}
		catch(Exception e){
			System.out.println("Error occured, credit card does not exist");
			return null;
		}
	}

}