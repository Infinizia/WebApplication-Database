package DbContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import DbModel.Genre;
import DbModel.Movie;
import DbModel.Star;

public class dbMovie extends dbContext {
	public static final String id_col = "id";
	public static final String title_col = "title";
	public static final String year_col = "year";
	public static final String director_col = "director";
	public static final String banner_url_col = "banner_url";
	public static final String trailer_url_col = "trailer_url";
	public static final String table_name = "movies";
	public static final String star_mapping_table_name = "stars_in_movies";
	
	public dbMovie(){
		super();
		this.tableName = dbMovie.table_name;
	}
	private List<Movie> ExecuteQueryStatement(String query){

		try{
			PreparedStatement ps = sqlConnection.prepareStatement(query);
			ResultSet r = ps.executeQuery();
			ArrayList<Movie> movieResult = new ArrayList<Movie>();
			dbGenre genreDb = new dbGenre();
			dbStars starDb = new dbStars();
			
			ArrayList<Genre> allGenre = genreDb.GetAllGenreMappedMovie();
			ArrayList<Star> allStar = starDb.GetAllStarMappedWithMovie();
		    while (r.next())
		    {	
		    	Movie movie = new Movie();
		    	movie.setId(r.getInt(dbMovie.id_col));
		    	movie.setTitle(r.getString(dbMovie.title_col));
		    	movie.setYear(r.getInt(dbMovie.year_col));
		    	movie.setDirector(r.getString(dbMovie.director_col));
		    	movie.setBanner_url(r.getString(dbMovie.banner_url_col));
		    	movie.setTrailer_url(r.getString(dbMovie.trailer_url_col));
		    	
		    	movie.setGenres(allGenre.stream().filter(x -> x.getMovieIdList().contains(movie.getId())).collect(Collectors.toList()));
		    	movie.setStars(allStar.stream().filter(x -> x.getMovieList().stream().anyMatch(y -> y.getId() == movie.getId())).collect(Collectors.toList()));
		    	
		    	movieResult.add(movie);
		    }
		    return movieResult;
		}
		catch(Exception e){
			System.out.println("Error occured getting movies");
			return null;
		}
		
		
	}
	
	public List<Movie> GetAllMovies(){
		String selectQuery = "select * from movies m group by title";
		
		return this.ExecuteQueryStatement(selectQuery);
	}
	
	public HashSet<String> GetAllMovieName(){
		try{
			String selectAllQuery = String.format("select %s from %s", dbMovie.title_col, this.tableName);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			HashSet<String> uniqueMovies = new HashSet<String>();
		    while (r.next())
		    {
		    	String key = r.getString(dbMovie.title_col);
		    	uniqueMovies.add(key.toLowerCase());
		    }
			return uniqueMovies;
		}
		catch(Exception e){
			System.out.println("Error occured getting stars");
			return null;
		}
	}
	
	public List<Movie> BatchInsert(List<Movie> movieList){
		HashSet<String> allUniqueMovie = this.GetAllMovieName();
		
		List<Movie> rejectedList = new ArrayList<Movie>();
		int batchCount = 0;
		
		try {
			PreparedStatement ps = sqlConnection.prepareStatement("INSERT INTO MOVIES (title, year, director) "
																	+ "VALUES(?, ?, ?)");
			for(Movie m : movieList){
				try{
					String key = m.title;
					
					if(allUniqueMovie.contains(key.toLowerCase().trim())|| key == null || key == ""){
						rejectedList.add(m);
						continue;
					}
					else{
						ps.clearParameters();
						ps.setString(1, m.getTitle());
						ps.setInt(2, m.getYear());
						ps.setString(3, m.getDirector());
						ps.addBatch();
						allUniqueMovie.add(key);
						batchCount++;
						
						if(batchCount % 500 == 0){
							ps.executeBatch();
							ps.clearBatch();
						}	
					}
				}
				catch(Exception e){	rejectedList.add(m);}
			}
			ps.executeBatch();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return rejectedList;
	}
	public List<Movie> BatchInsertGenreMap(List<Movie> movieList){
		List<Movie> rejectedList = new ArrayList<Movie>();
		
		int batchCount = 0;
		
		try {
			PreparedStatement ps = sqlConnection.prepareStatement("{Call add_genre_movie_map(?,?)}");
			for(Movie m : movieList){
				try{
					if(m.title == "" || (m.title != "" && m.getGenres().isEmpty())){
						continue;
					}
					else if(!m.getGenres().isEmpty())
					{
						if(m.getGenres().get(0).getName().equals(""))
							continue;
					}
					for(Genre g : m.getGenres()){
						ps.clearParameters();
						ps.setString(1, m.getTitle());
						ps.setString(2, g.getName());
						ps.addBatch();
						batchCount++;
						if(batchCount % 500 == 0){
							ps.executeBatch();
							ps.clearBatch();
						}			
					}
				}
				catch(Exception e){
					e.printStackTrace();
					rejectedList.add(m);
				}
			}
			ps.executeBatch();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return rejectedList;
	}

}

