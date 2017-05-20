package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.SearchService;
import DbModel.Movie;
import DbContext.dbMovie;

/**
 * Servlet implementation class BrowseService
 */
@WebServlet("/Browse")
public class Browse extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		SearchService search = new SearchService();
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		dbMovie selectedMovie = new dbMovie();  
		String word = request.getParameter("text");
		
		//If the word length is more than 1, it means browse by genre otherwise is the first char of a title
//		if (word.length() > 1)
//			vieList = selectedMovie.GetMovieByGenre(word);
//		else
//			ovieList = selectedMovie.GetMovieByStartTitle(word);
		
		List<Movie> newMovieList = new ArrayList<Movie>();
		String attribute = "title";

		for (int i = 0; i < movieList.size();i++)
		{
			String title = movieList.get(i).getTitle();
			Movie movie = search.getSingleMovie(title, attribute);
			if (movie != null)
				newMovieList.add(movie);
		}
		request.setAttribute("movieList", newMovieList);
    	RequestDispatcher rd = request.getRequestDispatcher("/DisplayResults.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
