package DbContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import DbModel.Genre;

public class dbGenre extends dbContext {
	public static final String id_col = "id";
	public static final String name_col = "name";
	public static final String movie_id_col = "movie_id";
	public dbGenre(){
		super();
		this.tableName = "genres";
	}
	
	public ArrayList<Genre> GetAllGenre(){

		try{
			String selectAllQuery = String.format("select * from %s", this.tableName);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			ArrayList<Genre> allGenre = new ArrayList<Genre>();

			Genre g = null;
		    while (r.next())
		    {
	    		g = new Genre();
		      	g.setId(r.getInt(dbGenre.id_col));
		    	g.setName(r.getString(dbGenre.name_col));
		    	allGenre.add(g);
		    }
			return allGenre;
		}
		catch(Exception e){
			System.out.println("Error occured getting customers");
			return null;
		}
	}

	public ArrayList<Genre> GetAllGenreMappedMovie(){
		try{
			String selectAllQuery = String.format("select * from %s join genres_in_movies on id = genre_id order by name", this.tableName);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			ArrayList<Genre> allGenre = new ArrayList<Genre>();
			HashMap<Integer, Genre> genreMapped = new HashMap<Integer, Genre>();
			Genre g = null;
		    while (r.next())
		    {
		    	int key = r.getInt(dbGenre.id_col);
		    	if(genreMapped.containsKey(key)){
		    		g = genreMapped.get(key);
		    		g.getMovieIdList().add(r.getInt(dbGenre.movie_id_col));
		    	}
		    	else{
		    		if(g != null){
		    			allGenre.add(g);
		    		}
		    		g = new Genre();
			      	g.setId(r.getInt(dbGenre.id_col));
			    	g.setName(r.getString(dbGenre.name_col));
			    	g.getMovieIdList().add(r.getInt(dbGenre.movie_id_col));
		    	}
		    	genreMapped.put(key, g);
		    }
			return allGenre;
		}
		catch(Exception e){
			System.out.println("Error occured getting customers");
			return null;
		}
	
	}
	
	public HashSet<String> GetAllGenreName(){
		try{
			String selectAllQuery = String.format("select %s from %s", dbGenre.name_col, this.tableName);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			HashSet<String> uniqueGenres = new HashSet<String>();
		    while (r.next())
		    {
		    	String key = r.getString(dbGenre.name_col);
		    	uniqueGenres.add(key.toLowerCase());
		    }
			return uniqueGenres;
		}
		catch(Exception e){
			System.out.println("Error occured getting stars");
			return null;
		}
	
	}
	
	public List<String> BatchInsert(HashSet<String> genreList){
		HashSet<String> allUniqueGenre = this.GetAllGenreName();
		List<String> rejectedList = new ArrayList<String>();
		int batchCount = 0;
		
		try {
			PreparedStatement ps = sqlConnection.prepareStatement("INSERT INTO GENRES (name) "
																	+ "VALUES(?)");
			for(String g : genreList){
				try{
					String key = g;

					if(allUniqueGenre.contains(key.toLowerCase().trim()) || key == null || key == ""){
						rejectedList.add(g);
						continue;
					}
					else{
						ps.clearParameters();
						ps.setString(1, g);
						ps.addBatch();
						allUniqueGenre.add(key);
						batchCount++;
						
						if(batchCount % 500 == 0){
							ps.executeBatch();
							ps.clearBatch();
						}
					}
				}
				catch(Exception e){rejectedList.add(g);}
			}
			ps.executeBatch();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return rejectedList;
	}

}
