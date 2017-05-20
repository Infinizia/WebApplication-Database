package AppService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CacheService {
	
	//private static HttpSession session = null;
	
	public static final String USER_CACHE_NAME = "USER";
	public static final String FULL_MOVIE_LIST_CACHE_NAME = "MOVIE_LIST";
	public static final String CURRENT_MOVIE_LIST_CACHE_NAME = "CURRENT_LIST";
	public static final String GENRE_CACHE_NAME = "GENRE";
	public static final String STAR_CACHE_NAME = "STAR";
	public static final String FILTER_CACHE_NAME = "FILTER";
	public static final String MOVIE_CACHE_NAME = "MOVIE";
	public static final String TEMP_ID_CACHE_NAME = "TEMP_ID";
	public static final String CART = "CART";
	public static final String ADMIN_CACHE_NAME = "ADMIN";
	public CacheService(){
		
	}
	
	public static Boolean AddCache(HttpServletRequest request, String key, Object o){
		try{
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(60*60);
			session.setAttribute(key, o);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	public static Object GetCache(HttpServletRequest request, String key){
		try{
			HttpSession session = request.getSession(true);
			return session.getAttribute(key);
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static void ClearCache(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if (session != null) {
		    session.invalidate();
		}
	}
}
