package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.xml.sax.InputSource;

import AppService.ActorXmlParseService;
import AppService.CastXmlParseService;
import AppService.MainXmlParseService;
import AppService.ResponseService;
import DbContext.dbGenre;
import DbContext.dbMovie;
import DbContext.dbStars;
import DbModel.Movie;
import DbModel.Star;
/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/XmlActor")
@MultipartConfig
public class XmlActor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XmlActor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String type = request.getParameter("type");
			HashSet<Movie> movieErrotList = new HashSet<Movie>();
			HashSet<Star> starErrorList = new HashSet<Star>();
			if(type.equals("actor")){
				ActorXmlParseService parser = new ActorXmlParseService();
				Part filePart = request.getPart("xmlFile");
				InputStream fileContent = filePart.getInputStream();
				InputSource source = new InputSource(fileContent);
				dbStars starDb = new dbStars("master");
				parser.parseXml(source);
				starErrorList.addAll(starDb.BatchInsert(parser.addStarList));
				ResponseService.SendJson(response, starErrorList);
			}
			else if (type.equals("main")){
				MainXmlParseService parser = new MainXmlParseService();
				Part filePart = request.getPart("xmlFile"); 
				InputStream fileContent = filePart.getInputStream();
				InputSource source = new InputSource(fileContent);		
				parser.parseXml(source);			
				dbMovie movieDb = new dbMovie("master");
				movieErrotList.addAll(movieDb.BatchInsert(parser.addMovieList));
				dbGenre genreDb = new dbGenre("master");
				genreDb.BatchInsert(parser.addGenreList);
				movieErrotList.addAll(movieDb.BatchInsertGenreMap(parser.addMovieList));
				ResponseService.SendJson(response, movieErrotList);
			}
			else if(type.equals("cast")){
				CastXmlParseService parser = new CastXmlParseService();
				Part filePart = request.getPart("xmlFile"); 
				InputStream fileContent = filePart.getInputStream();
				InputSource source = new InputSource(fileContent);		
				parser.parseXml(source);			
				dbStars starDb = new dbStars("master");
				starErrorList.addAll(starDb.BatchInsertMovieMap(parser.addStarList));
				ResponseService.SendJson(response, starErrorList);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.sendError(500);
		}
	}
	
}