package AppService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import DbModel.Star;

public class CastXmlParseService extends DefaultHandler {
	public final String TITLE_TAG_NAME = "t";
	public final String ACTOR_TAG_NAME = "a";

	private String tempVal;
	public List<Star> addStarList = null;
	private Star tempStar = null;
	private String tempMovie = null;
	
    /** The main method sets things up for parsing */
    public CastXmlParseService() {
    	addStarList = new ArrayList<Star>();
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
           if (qName.equalsIgnoreCase(ACTOR_TAG_NAME)) {
        	   tempStar = new Star();
           }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
                  throws SAXException {
		if (qName.equalsIgnoreCase(ACTOR_TAG_NAME)) {
			tempStar.setFirst_name(tempVal);
			tempStar.movie = tempMovie;
			addStarList.add(tempStar);
	    }
		else if (qName.equalsIgnoreCase(TITLE_TAG_NAME)) {
			tempMovie = tempVal;
		} 
    }
    
}
