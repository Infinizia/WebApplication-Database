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
		System.out.println("Search request received");
		String searchText = request.getParameter("searchText");
		if (searchText == null)
		{
			InputStream is = request.getInputStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        String line = "";
	        StringBuffer buffer = new StringBuffer();
	        while ((line = reader.readLine()) != null)
	        {
	            buffer.append(line);
	        }
	        searchText = buffer.toString();
		}
		System.out.println(searchText);
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
		ArrayList<MovieTitleSearch> movieList = ftDb.GetMovie(searchQuery.toString());
		System.out.println("Search result " + movieList.size());
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
