package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.ResponseService;
import DbContext.dbFullText;
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
		
		dbFullText ftDb= new dbFullText();
		ArrayList<MovieTitleSearch> movieList = ftDb.GetMovie(searchQuery.toString());
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
