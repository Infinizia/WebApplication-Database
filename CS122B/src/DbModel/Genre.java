package DbModel;

import java.util.ArrayList;

public class Genre {
	private int id = -1;
	private String name = null;
	private ArrayList<Integer> movieIdList = null;
	
	public Genre(){
		movieIdList = new ArrayList<Integer>();
	}
	public ArrayList<Integer> getMovieIdList() {
		return movieIdList;
	}

	public void setMovieIdList(ArrayList<Integer> movieIdList) {
		this.movieIdList = movieIdList;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
