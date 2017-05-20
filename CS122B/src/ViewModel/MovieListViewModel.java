package ViewModel;
import java.util.List;
import DbModel.Filters;
import DbModel.Genre;
import DbModel.Movie;


public class MovieListViewModel {
	private List<Movie> movieList = null;
	private List<Genre> genreList = null;
	private Filters filters = null;
	private int movieCount = 0;
	
	public int getMovieCount() {
		return movieCount;
	}

	public void setMovieCount(int movieCount) {
		this.movieCount = movieCount;
	}

	public MovieListViewModel(){
		filters = new Filters();
	}
	
	public List<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<Movie> movieList) {
		this.movieList = movieList;
	}

	public List<Genre> getGenreList() {
		return genreList;
	}

	public void setGenreList(List<Genre> genreList) {
		this.genreList = genreList;
	}

	public Filters getFilters() {
		return filters;
	}

	public void setFilters(Filters filters) {
		this.filters = filters;
	}
	
	public String GetJson(){
		String jsonFormat = "{\"movieList\":[{\"id\":%d, \"title\":%s}]}";
		StringBuilder json = new StringBuilder();
		for(Movie m:this.movieList){
			json.append(String.format(jsonFormat, m.getId(), m.getTitle()));
			return json.toString();
		}
		return json.toString();
	}


}
