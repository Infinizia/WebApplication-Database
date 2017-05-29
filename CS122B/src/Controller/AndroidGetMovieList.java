package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.CacheService;
import AppService.ResponseService;
import AppService.SearchService;
import DbContext.dbGenre;
import DbContext.dbMovie;
import DbContext.dbStars;
import DbModel.Genre;
import DbModel.Movie;
import DbModel.Star;
import DbModel.Filters;
import ViewModel.MovieListViewModel;

/**
 * Servlet implementation class AndroidGetMovieList
 */
@WebServlet("/AndroidGetMovieList")
public class AndroidGetMovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidGetMovieList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Get Movie Request");
		dbMovie movieQuery = new dbMovie();
		List<Movie> movieList = new ArrayList<Movie>();
		
		movieList = movieQuery.GetAllMovies();
		ResponseService.SendJson(response, movieList);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
