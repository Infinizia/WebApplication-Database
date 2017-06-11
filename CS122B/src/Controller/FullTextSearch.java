package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import AppService.JsonService;
import AppService.ResponseService;
import DbContext.dbFullText;
import DbModel.Customer;
import DbModel.MovieTitleSearch;

/**
 * Servlet implementation class FullTextSearch
 */
@WebServlet("/FullTextSearch")
public class FullTextSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FullTextSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger logger = LogManager.getLogger("mainLogger");
		long startTime = System.nanoTime();
		
		
		String searchText = request.getParameter("searchText");
		String[] text = searchText.split(" ");
		StringBuilder searchQuery = new StringBuilder();
		
		if(text.length > 1){
			for(String s : text){
				searchQuery.append(String.format("+%s ", s));
			}
			searchQuery.deleteCharAt(searchQuery.length()-1);
			searchQuery.append("*");
		}
		else{
			searchQuery.append(String.format("%s*", searchText));
		}
		
		dbFullText ftDb= new dbFullText("");
		ArrayList<MovieTitleSearch> movieList = ftDb.GetMovie(logger, searchQuery.toString());
		long endTime = System.nanoTime();
		logger.debug(String.format("TS_MEASURE:%d", endTime - startTime));
		ftDb.Close();
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
