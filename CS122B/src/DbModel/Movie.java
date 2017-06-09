package DbModel;

import java.util.ArrayList;
import java.util.List;


public class Movie {
	private int id = -1;
	public String title = null;
	private int year = -1;
	private String director = null;
	private List<Genre> genres = null;
	private List<Star> stars = null;
	private String banner_url = null;
	private String trailer_url = null;
	private int inCart = 1;
	private boolean isUpdate = false;
	
	public Movie(){
		genres = new ArrayList<Genre>();
		stars = new ArrayList<Star>();
	}
	
//	public void Print()
//	{	
//	    out.println("<table border=\"1\"><tbody>"
//	    		+ "<tr><td>Movie title</td>"
//	    		+ "<td>"+ title + "</td></tr>"
//	    		+ "<tr><td>ID</td>"
//	    		+ "<td>" + id + "</td></tr>"
//	    		+ "<tr><td>Year</td>"
//	    		+ "<td>" + year + "</td></tr>"
//	    		+ "<tr><td>Director</td>"
//	    		+ "<td>" + director + "</td></tr>"
//	    		+ "<tr><td>Genres</td><td>");
//	    		for (int i = 0; i < genres.size(); i++)
//	    			out.print(genres.get(i) + " ");
//	    out.println("</td></tr><tr><td>Featured stars</td><td><a href=\"\">");
//			    for (int i = 0; i < stars.size(); i++)
//					out.print(stars.get(i).getFirst_name() + " " + stars.get(i).getLast_name() + ", ");
//	    out.println("</a></td></tr></tbody></table><br>");
//
//		
//	}
	public List<Genre> getGenres(){
		return genres;
	}
	public void setGenres(List<Genre> g){
		genres = g;
	}
	public List<Star> getStars(){
		return stars;
	}
	public void setStars(List<Star>  list)
	{
		stars = list;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getBanner_url() {
		return banner_url;
	}
	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}
	public String getTrailer_url() {
		return trailer_url;
	}
	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}
	public int getInCart() {
		return inCart;
	}
	public void setInCart(int inCart) {
		this.inCart = inCart;
	}
	public boolean getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
}