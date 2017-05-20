package Controller;

import java.io.IOException;
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
import DbModel.Star;
import ViewModel.DetailViewModel;

/**
 * Servlet implementation class GetDetail
 */
@WebServlet("/GetDetail")
public class GetDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DetailViewModel vm = (DetailViewModel)JsonService.GetObjectFromJson(request.getParameter("vm"), DetailViewModel.class);
		
		if(vm == null){
			vm = (DetailViewModel)CacheService.GetCache(request, CacheService.TEMP_ID_CACHE_NAME);
			if(vm == null){
				vm = new DetailViewModel();
			}
		}
		int temp_id = vm.temp_id;
		if(vm.type.equals("movie")){
			@SuppressWarnings("unchecked")
			List<Movie> allMovie = (List<Movie>)CacheService.GetCache(request, CacheService.FULL_MOVIE_LIST_CACHE_NAME);
			vm.movieDetail = allMovie.stream().filter(x -> x.getId() == temp_id).findFirst().get();
		}
		else{
			@SuppressWarnings("unchecked")
			List<Star> allStar = (List<Star>)CacheService.GetCache(request, CacheService.STAR_CACHE_NAME);
			vm.starDetail = allStar.stream().filter(x -> x.getId() == temp_id).findFirst().get();
		}
		
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
