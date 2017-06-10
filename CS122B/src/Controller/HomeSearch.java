package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.CacheService;
import AppService.JsonService;
import AppService.ResponseService;
import AppService.SearchService;
import DbContext.dbGenre;
import DbContext.dbMovie;
import DbContext.dbStars;
import DbModel.Filters;
import DbModel.Genre;
import DbModel.Movie;
import DbModel.Star;
import ViewModel.DetailViewModel;
import ViewModel.MovieListViewModel;
import ViewModel.ShoppingCart;

/**
 * Servlet implementation class HomeSearch
 */
@WebServlet("/HomeSearch")
public class HomeSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Filters filter = new Filters();
		filter.searchWord = request.getParameter("searchWord");
		
		SearchService searchRequest = new SearchService();
		MovieListViewModel vm = new MovieListViewModel();
		
		@SuppressWarnings("unchecked")
		List<Movie> allMovie = (List<Movie>)CacheService.GetCache(request, CacheService.FULL_MOVIE_LIST_CACHE_NAME);
		@SuppressWarnings("unchecked")
		List<Genre> allGenre = (List<Genre>)CacheService.GetCache(request, CacheService.GENRE_CACHE_NAME);
		@SuppressWarnings("unchecked")
		List<Star> allStars = (List<Star>)CacheService.GetCache(request, CacheService.STAR_CACHE_NAME);

		if(allMovie == null){
			dbMovie movieDb = new dbMovie("");
			allMovie = movieDb.GetAllMovies();
			CacheService.AddCache(request, CacheService.FULL_MOVIE_LIST_CACHE_NAME, allMovie);

		}
		if(allGenre == null){
			dbGenre genreDb = new dbGenre("");
			allGenre = genreDb.GetAllGenreMappedMovie();
			CacheService.AddCache(request, CacheService.GENRE_CACHE_NAME, allGenre);
		}
		if(allStars == null){
			dbStars starDb = new dbStars("");
			allStars = starDb.GetAllStarMappedWithMovie();
			CacheService.AddCache(request, CacheService.STAR_CACHE_NAME, allStars);
		}
		
		List<Movie> movieResult = searchRequest.getMovieList(allMovie, filter);
		CacheService.AddCache(request, CacheService.CURRENT_MOVIE_LIST_CACHE_NAME, movieResult);
		CacheService.AddCache(request, CacheService.FILTER_CACHE_NAME, filter);
		vm.setGenreList(allGenre);
		if((filter.currentPage-1)*filter.pageLength > movieResult.size()){
			filter.currentPage = 1;
		}
		vm.setMovieList(movieResult.subList(Math.min((filter.currentPage-1)*filter.pageLength, movieResult.size()), Math.min(movieResult.size(), filter.currentPage*filter.pageLength)));
		vm.setMovieCount(movieResult.size());
		vm.setFilters(filter);
		
		ResponseService.SendJson(response, vm);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
