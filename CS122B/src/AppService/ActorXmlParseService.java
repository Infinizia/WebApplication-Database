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

public class ActorXmlParseService extends DefaultHandler {
	public final String MAIN_TAG_NAME = "actor";
	public final String FIRSTNAME_TAG_NAME = "firstname";
	public final String LAST_TAG_NAME = "familyname";
	public final String DOB_TAG_NAME = "dob";
	private String tempVal;
	public List<Star> addStarList = null;
	private Star tempStar = null;
    /** The main method sets things up for parsing */
    public ActorXmlParseService() {
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
           if (qName.equalsIgnoreCase(MAIN_TAG_NAME)) {
        	   tempStar = new Star();
           }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
                  throws SAXException {
		if (qName.equalsIgnoreCase(MAIN_TAG_NAME)) {
			addStarList.add(tempStar);
	    }
		else if (qName.equalsIgnoreCase(FIRSTNAME_TAG_NAME)) {
			tempStar.setFirst_name(tempVal);
		} 
		else if (qName.equalsIgnoreCase(LAST_TAG_NAME)) {
			tempStar.setLast_name(tempVal);
		} 
		else if (qName.equalsIgnoreCase(DOB_TAG_NAME)) {	
			try{
				tempStar.setDob(java.sql.Date.valueOf(tempVal + "-01-01"));
			}
			catch(Exception e){}
		}
    }
    
}
