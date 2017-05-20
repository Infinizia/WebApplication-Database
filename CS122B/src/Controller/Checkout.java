package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import AppService.CacheService;
import AppService.JsonService;
import AppService.ResponseService;
import AppService.ShoppingCartService;
import DbModel.Creditcard;
import DbModel.Customer;
import ViewModel.ShoppingCart;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ShoppingCartService _cartSvc = null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        _cartSvc = new ShoppingCartService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkout(request, response);
	}
	
	private void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Creditcard card = (Creditcard) JsonService.GetObjectFromJson(JsonService.GetJsonFromRequest(request.getReader()), Creditcard.class);
			Customer customer = (Customer)CacheService.GetCache(request, CacheService.USER_CACHE_NAME);
			ShoppingCart cart = (ShoppingCart)CacheService.GetCache(request, CacheService.CART);
			JsonObject message = new JsonObject();
			if (_cartSvc.checkout(customer, cart, card) <= 0) {
				throw new Exception();
			}
			ResponseService.SendJson(response, message);
		}
		catch(Exception e) {
			response.sendError(500);
		}	
	}
}
