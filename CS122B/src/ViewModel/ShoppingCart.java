package ViewModel;

import java.util.ArrayList;
import java.util.List;

import DbModel.Customer;
import DbModel.Movie;

public class ShoppingCart {
	private List<Movie> moviesToAdd = null;
	private List<Movie> moviesToRemove = null;
	
	public ShoppingCart() {
		moviesToAdd = new ArrayList<Movie>();
		moviesToRemove = new ArrayList<Movie>();
	}
	
	public List<Movie> getMoviesToAdd() {
		return moviesToAdd;
	}
	public void setMoviesToAdd(List<Movie> moviesToAdd) {
		this.moviesToAdd = moviesToAdd;
	}
	public List<Movie> getMoviesToRemove() {
		return moviesToRemove;
	}
	public void setMoviesToRemove(List<Movie> moviesToRemove) {
		this.moviesToRemove = moviesToRemove;
	}
}
