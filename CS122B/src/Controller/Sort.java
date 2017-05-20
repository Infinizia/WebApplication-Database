package Controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.CacheService;
import AppService.JsonService;
import AppService.ResponseService;
import DbModel.Filters;
import DbModel.Movie;

@WebServlet("/Sort")
public class Sort extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sort() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Filters filter = (Filters)JsonService.GetObjectFromJson(request.getParameter("filter"), Filters.class);
		
		if(filter == null){
			filter = (Filters)CacheService.GetCache(request, CacheService.FILTER_CACHE_NAME);
			if(filter == null){
				filter = new Filters();
			}
		}
		
		@SuppressWarnings("unchecked")
		List<Movie> movieResult = (List<Movie>)CacheService.GetCache(request, CacheService.CURRENT_MOVIE_LIST_CACHE_NAME);
		
		if(filter.orderBy.equals("title")){
			if(filter.isDesc){
				movieResult = movieResult.stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());
			}
			else{
				movieResult = movieResult.stream().sorted(Comparator.comparing(Movie::getTitle).reversed()).collect(Collectors.toList());
			}
		}
		else{
			if(filter.isDesc){
				movieResult = movieResult.stream().sorted(Comparator.comparing(Movie::getYear).reversed()).collect(Collectors.toList());
			}
			else{
				movieResult = movieResult.stream().sorted(Comparator.comparing(Movie::getYear)).collect(Collectors.toList());
			}
		}
		CacheService.AddCache(request, CacheService.FILTER_CACHE_NAME, filter);
		CacheService.AddCache(request, CacheService.CURRENT_MOVIE_LIST_CACHE_NAME, movieResult);
		movieResult = movieResult.subList((filter.currentPage -1)*filter.pageLength, Math.min(movieResult.size(), (filter.currentPage)*filter.pageLength));
		
		ResponseService.SendJson(response, movieResult);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}