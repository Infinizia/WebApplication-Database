package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.CacheService;
import AppService.JsonService;
import AppService.ResponseService;
import AppService.UserService;
import AppService.VerifyUtils;
import DbContext.dbEmployee;
import DbModel.Customer;
import DbModel.Employee;
import DbModel.Movie;
import ViewModel.ShoppingCart;
import ViewModel.reCaptchaConfig;
import ViewModel.reCaptchaException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String LOGIN_VIEW = "../Login.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResponseService.SendResponse(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Customer c = (Customer)JsonService.GetObjectFromJson(JsonService.GetJsonFromRequest(request.getReader()), Customer.class);
			//Uncomment this to enable recaptcha
			//boolean isValid = VerifyUtils.verify(c.recaptcha);
			
			boolean isValid = true;
			if(!isValid) {
				//throw new reCaptchaException();
			}
			else {
				UserService svc = new UserService();
				
				dbEmployee employeeDb = new dbEmployee("");
				Employee emp = employeeDb.GetEmployee(c.email, c.password);
				
				if(emp != null){
					CacheService.AddCache(request, CacheService.USER_CACHE_NAME, c);
					CacheService.AddCache(request, CacheService.CART, new ShoppingCart());
					CacheService.AddCache(request, CacheService.ADMIN_CACHE_NAME, true);
					ResponseService.SendJson(response, c);
				}
				else{
					Customer cust = svc.getCustomerByEmailAndPassword(c.email, c.password);
					if(cust != null){
						CacheService.AddCache(request, CacheService.USER_CACHE_NAME, cust);
						CacheService.AddCache(request, CacheService.CART, new ShoppingCart());
						ResponseService.SendJson(response, cust);
					}
					else {
						throw new Exception();
					}
				}
			}
		}
		catch(reCaptchaException e) {
			response.sendError(520);
		}
		catch(Exception e) {
			response.sendError(500);
		}
		
	}

}
