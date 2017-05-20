package AppService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import DbModel.Genre;
import DbModel.Movie;



public class MainXmlParseService extends DefaultHandler {
	public final String MAIN_MOVIE_TAG_NAME = "film";
	public final String TITLE_TAG_NAME = "t";
	public final String YEAR_TAG_NAME = "year";
	public final String MAIN_GENRE_TAG_NAME = "cat";
	public final String MAIN_DIRECTOR_TAG_NAME = "dirname";
	
	private String tempVal;
	public HashSet<String> addGenreList = null;
	public List<Movie> addMovieList = null;
	private Movie tempMovie = null;
	private String director_name = null;
	
    /** The main method sets things up for parsing */
    public MainXmlParseService() {
    	addGenreList = new HashSet<String>();
    	addMovieList = new ArrayList<Movie>();
    }
    public void parseXml(InputSource xml){
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(xml, this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
    }

    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
    	tempVal = new String(buffer, start, length);
    }
   

    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */ 
    public void startElement(String uri, String localName,
                  String qName, Attributes attributes) throws SAXException {
    				tempVal = "";
           if (qName.equalsIgnoreCase(MAIN_MOVIE_TAG_NAME)) {
        	   tempMovie = new Movie();
           }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
                  throws SAXException {
		if (qName.equalsIgnoreCase(MAIN_DIRECTOR_TAG_NAME)) {
			director_name = tempVal;
	    }
		else if (qName.equalsIgnoreCase(MAIN_MOVIE_TAG_NAME)) {
			tempMovie.setDirector(director_name);
			addMovieList.add(tempMovie);
		} 
		else if (qName.equalsIgnoreCase(TITLE_TAG_NAME)) {
			tempMovie.setTitle(tempVal);
		} 
		else if (qName.equalsIgnoreCase(YEAR_TAG_NAME)) {
			try{
				tempMovie.setYear(Integer.parseInt(tempVal));
			}
			catch(Exception e){
				
			}
		} 
		else if (qName.equalsIgnoreCase(MAIN_GENRE_TAG_NAME)) {	
			Genre g = new Genre();
			g.setName(tempVal);
			tempMovie.getGenres().add(g);
			if(!addGenreList.contains(tempVal))
				addGenreList.add(tempVal);
		}
    }
}
