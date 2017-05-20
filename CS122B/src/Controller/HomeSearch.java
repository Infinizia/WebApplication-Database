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
import ViewModel.DetailViewModel;
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
		if(CacheService.GetCache(request, CacheService.USER_CACHE_NAME) != null){
			//Filters f = (Filters) JsonService.GetObjectFromJson(JsonService.GetJsonFromRequest(request.getReader()), Filters.class);
			Filters f = (Filters)JsonService.GetObjectFromJson(request.getParameter("filters"), Filters.class);
			
			Filters filter = (Filters)CacheService.GetCache(request, CacheService.FILTER_CACHE_NAME);
			if(filter == null){
				filter = new Filters();
			}
			
			filter.searchWord = f.searchWord;
			filter.type = f.type;
			
			CacheService.AddCache(request, CacheService.FILTER_CACHE_NAME, filter);
		}
		ResponseService.SendResponse(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
