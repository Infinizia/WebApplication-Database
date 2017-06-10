package DbContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ReplicationDriver;

import DbModel.Movie;
import DbModel.Star;

public class dbStars extends dbContext {
	public static final String id_col = "id";
	public static final String first_name_col = "first_name";
	public static final String last_name_col = "last_name";
	public static final String dob_col = "dob";
	public static final String photo_url_col = "photo_url";
	public static final String table_name = "stars";
	public static final String movie_id_col = "movie_id";
	
	public dbStars(String setting){
		super(setting);
		this.tableName = dbStars.table_name;
	}
	
	public ArrayList<Star> GetAllStarMappedWithMovie(){
		try{
			String selectAllQuery = String.format("select * from %s s join stars_in_movies sm on s.id = sm.star_id join movies m on m.id = sm.movie_id  order by s.id", this.tableName);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			ArrayList<Star> allStar = new ArrayList<Star>();
			HashMap<Integer, Star> starMapped = new HashMap<Integer, Star>();
			Star s = null;
		    while (r.next())
		    {
		    	int key = r.getInt(dbStars.id_col);
		    	if(starMapped.containsKey(key)){
		    		s = starMapped.get(key);
		    	}
		    	else{
		    		if(s != null){
		    			allStar.add(s);
		    		}
		    		
			    	s = new Star();
			      	s.setId(r.getInt(dbStars.id_col));
			    	s.setFirst_name(r.getString(dbStars.first_name_col));
			    	s.setLast_name(r.getString(dbStars.last_name_col));
			    	s.setDob(r.getDate(dbStars.dob_col));
			    	s.setPhoto_url(r.getString(dbStars.photo_url_col));
			    	
		    	}
	    	   	Movie movie = new Movie();
		    	movie.setId(r.getInt(dbStars.movie_id_col));
		    	movie.setTitle(r.getString(dbMovie.title_col));
		    	movie.setYear(r.getInt(dbMovie.year_col));
		    	movie.setDirector(r.getString(dbMovie.director_col));
		    	movie.setBanner_url(r.getString(dbMovie.banner_url_col));
		    	movie.setTrailer_url(r.getString(dbMovie.trailer_url_col));
		    	
		    	s.getMovieList().add(movie);
	    		starMapped.put(key, s);

		    }
			return allStar;
		}
		catch(Exception e){
			System.out.println("Error occured getting customers");
			return null;
		}
	}
	public HashSet<String> GetAllStarName(){
		try{
			String selectAllQuery = String.format("select %s, %s from %s", dbStars.first_name_col, dbStars.last_name_col, this.tableName);
			PreparedStatement ps = sqlConnection.prepareStatement(selectAllQuery);
			ResultSet r = ps.executeQuery();
			HashSet<String> uniqueStars = new HashSet<String>();
		    while (r.next())
		    {
		    	String key = r.getString(dbStars.first_name_col) + r.getString(dbStars.last_name_col);
		    	uniqueStars.add(key.toLowerCase());
		    }
		    return uniqueStars;
		}
		catch(Exception e){
			System.out.println("Error occured getting stars");
			return null;
		}
	}
	
	public int InsertStar(Star s){
		try{
			//Determine if the star already exists in the database
			String selectQuery = String.format("select * from %s where stars.first_name = \"%s\" and stars.last_name = \"%s\" ",
					this.tableName,s.getFirst_name(),s.getLast_name());
			
			ResultSet rs = super.ExecuteQuery(selectQuery);
			if (rs.next())
			{
				rs.close();
				return -1;
			}
			else
			{	
				//If not, then insert new star to the database
				String insertQuery = String.format("insert into %s (%s, %s) values('%s', '%s')", 
						this.tableName, dbStars.first_name_col, dbStars.last_name_col,
						s.getFirst_name(), s.getLast_name());
				
				return super.ExecuteUpdate(insertQuery);
			}
		}
		catch(Exception e){
			System.out.println("Failed to insert Star");
			e.printStackTrace();
			return -1;
		}
	}

	public List<Star> BatchInsert(List<Star> starList){
		HashSet<String> allUniqueStar = this.GetAllStarName();
		List<Star> rejectedList = new ArrayList<Star>();
		int batchCount = 0;
		
		try {
			PreparedStatement ps = sqlConnection.prepareStatement("INSERT INTO STARS (first_name, last_name, dob) "
																	+ "VALUES(?, ?, ?)");
			for(Star s : starList){
				try{
					String key = s.getFirst_name() + s.getLast_name();
					
					if(allUniqueStar.contains(key.toLowerCase().trim()) || s.getFirst_name() == "" || s.getLast_name() == "" || key == ""){
						rejectedList.add(s);
						continue;
					}
					else{
						ps.clearParameters();
						ps.setString(1, s.getFirst_name());
						ps.setString(2, s.getLast_name());
						ps.setDate(3, s.getDob());
						ps.addBatch();
						allUniqueStar.add(key);
						batchCount++;
						
						if(batchCount % 500 == 0){
							ps.executeBatch();
							ps.clearBatch();
						}
					}
				}
				catch(Exception e){
					rejectedList.add(s);
				}
			}
			ps.executeBatch();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return rejectedList;
	}
	public List<Star> BatchInsertMovieMap(List<Star> starList){
		List<Star> rejectedList = new ArrayList<Star>();
		
		int batchCount = 0;
		
		try {
			PreparedStatement ps = sqlConnection.prepareStatement("{Call add_star_movie_map(?,?, ?)}");
			for(Star s : starList){
				try{
					if(s.getFirst_name() == null){
						continue;
					}
					String[] name = s.getFirst_name().split(" ");
					if(name.length > 1){
						s.setFirst_name(name[0]);
						s.setLast_name(name[1]);
					}
					else{
						s.setLast_name(s.getFirst_name());
						s.setFirst_name("");
					}
					ps.clearParameters();
					ps.setString(1, s.movie);
					ps.setString(2, s.getFirst_name());
					ps.setString(3, s.getLast_name());
					ps.addBatch();
					batchCount++;
					
					if(batchCount % 500 == 0){
						ps.executeBatch();
						ps.clearBatch();
					}
				}
				catch(Exception e){
					e.printStackTrace();
					rejectedList.add(s);
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
