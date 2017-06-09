package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

import AppService.CacheService;
import AppService.JsonService;
import AppService.ResponseService;
import DbContext.dbConnection;
import DbModel.Filters;
import DbModel.Genre;
import DbModel.Movie;
import DbModel.Star;

/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get data from http request
		Movie movie = (Movie)JsonService.GetObjectFromJson(request.getParameter("movie"), Movie.class);
		Star star = (Star)JsonService.GetObjectFromJson(request.getParameter("star"), Star.class);
		Genre genre = (Genre)JsonService.GetObjectFromJson(request.getParameter("genre"), Genre.class);
		
		//Call database procedure
		CallableStatement callStatement = null;
		String callAddMovie = "{Call add_movie(?,?,?,?,?,?,?,?)}";
		try
		{
			callStatement = dbConnection.GetConnection("master").prepareCall(callAddMovie);
			callStatement.setString(1,movie.getTitle());
			callStatement.setInt(2,movie.getYear());
			callStatement.setString(3,movie.getDirector());
			callStatement.setString(4,movie.getBanner_url());
			callStatement.setString(5,movie.getTrailer_url());
			callStatement.setString(6,star.getFirst_name());
			callStatement.setString(7,star.getLast_name());
			callStatement.setString(8,genre.getName());
			int count = callStatement.executeUpdate();
			if (count > 0)
				ResponseService.SendJson(response, "success");
		
		}catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		ResponseService.SendJson(response, "fail");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
