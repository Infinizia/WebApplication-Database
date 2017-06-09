package ViewModel;

import DbModel.Movie;
import DbModel.Star;

public class DetailViewModel {
	public Movie movieDetail = null;
	public Star starDetail = null;
	public String type = null;
	public int temp_id = -1;
	
	public DetailViewModel(){
		
	}
	
	
	public Movie getMovieDetail() {
		return movieDetail;
	}

	public void setMovieDetail(Movie movieDetail) {
		this.movieDetail = movieDetail;
	}

	public Star getStarDetail() {
		return starDetail;
	}

	public void setStarDetail(Star starDetail) {
		this.starDetail = starDetail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
