package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.CacheService;
import AppService.JsonService;
import AppService.ResponseService;
import AppService.UserService;
import DbModel.Customer;
import ViewModel.ShoppingCart;

/**
 * Servlet implementation class AndroidLogin
 */
@WebServlet("/AndroidLogin")
public class AndroidLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Receive Login Request");
        Customer c = (Customer)JsonService.GetObjectFromJson(JsonService.GetJsonFromRequest(request.getReader()), Customer.class);
		UserService svc = new UserService();
		Customer cust = svc.getCustomerByEmailAndPassword(c.email, c.password);
		if(cust != null){
			CacheService.AddCache(request, CacheService.USER_CACHE_NAME, cust);
			CacheService.AddCache(request, CacheService.CART, new ShoppingCart());
			ResponseService.SendJson(response, cust);
		}
		else 
		{	
			ResponseService.SendJson(response, "fail");
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
