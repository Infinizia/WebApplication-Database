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
import DbModel.Filters;
import DbModel.Movie;


@WebServlet("/Page")
public class Page extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Page() {
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
		CacheService.AddCache(request, CacheService.FILTER_CACHE_NAME, filter);
		@SuppressWarnings("unchecked")
		List<Movie> movieResult = (List<Movie>)CacheService.GetCache(request, CacheService.CURRENT_MOVIE_LIST_CACHE_NAME);
		
		movieResult = movieResult.subList((filter.currentPage -1)*filter.pageLength, Math.min(movieResult.size(), (filter.currentPage)*filter.pageLength));
		
		ResponseService.SendJson(response, movieResult);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}