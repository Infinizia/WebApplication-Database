package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AppService.CacheService;
import AppService.JsonService;
import AppService.ResponseService;
import DbModel.Customer;
import ViewModel.ShoppingCart;
/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/UpdateCart")
public class UpdateCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getCart(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		updateCart(request, response);
	}
	
	public void getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart cart = (ShoppingCart)CacheService.GetCache(request, CacheService.CART);
		ResponseService.SendJson(response, cart);
	}
	
	public void updateCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart cart = (ShoppingCart)CacheService.GetCache(request, CacheService.CART);
		ShoppingCart newCart = (ShoppingCart) JsonService.GetObjectFromJson(JsonService.GetJsonFromRequest(request.getReader()), ShoppingCart.class);
		boolean isInCart = false;
		
		try {
			for(int i = 0; i < newCart.getMoviesToAdd().size(); ++i) {
				if(cart.getMoviesToAdd().size() == 0) {
					cart.getMoviesToAdd().add(newCart.getMoviesToAdd().get(i));
					continue;
				}
				for(int j = 0; j < cart.getMoviesToAdd().size(); ++j) {
					if(cart.getMoviesToAdd().get(j).getId() == newCart.getMoviesToAdd().get(i).getId()) {
						if(newCart.getMoviesToAdd().get(i).getIsUpdate()) {
							cart.getMoviesToAdd().get(j).setInCart(newCart.getMoviesToAdd().get(i).getInCart());
						}
						else {
							cart.getMoviesToAdd().get(j).setInCart(cart.getMoviesToAdd().get(j).getInCart() + newCart.getMoviesToAdd().get(i).getInCart());
						}
						isInCart = true;
					}
				}
				if(!isInCart) {
					cart.getMoviesToAdd().add(newCart.getMoviesToAdd().get(i));
					isInCart = false;
				}
			}
			if(newCart.getMoviesToRemove() != null){
				for(int i = 0; i < newCart.getMoviesToRemove().size(); ++i) {
					if(cart.getMoviesToAdd().size() != 0) {
						for(int j = 0; j < cart.getMoviesToAdd().size(); ++j) {
							if(cart.getMoviesToAdd().get(j).getId() == newCart.getMoviesToRemove().get(i).getId()) {
								cart.getMoviesToAdd().remove(j);
							}
						}
					}
				}
			}
			ResponseService.SendJson(response, cart);
		}
		catch(Exception e) {
			response.sendError(500);
		}
		
		CacheService.AddCache(request, CacheService.CART, cart);
	}
}