package AppService;

import java.util.ArrayList;
import java.util.List;

import DbContext.dbCreditcards;
import DbContext.dbSales;
import DbModel.Creditcard;
import DbModel.Customer;
import DbModel.Sale;
import ViewModel.ShoppingCart;

public class ShoppingCartService {
	private dbCreditcards cards = null;
	private dbSales sales = null;
	
	public ShoppingCartService() {
		cards = new dbCreditcards();
		sales = new dbSales();
	}
	
	public int checkout(Customer customer, ShoppingCart cart, Creditcard card) {
		if(cardExists(card)) {
			List<Sale> sales = new ArrayList<Sale>();
			for(int i = 0; i < cart.getMoviesToAdd().size(); ++i) {
				for(int j = 0; j < cart.getMoviesToAdd().get(i).getInCart(); ++j) {
					Sale sale = new Sale();
					sale.setCustomer_id(customer.getId());
					sale.setMovie_id(cart.getMoviesToAdd().get(i).getId());
					sales.add(sale);
				}
			}
			return this.sales.AddSales(sales);
		}
		return 0;
	}
	
	private boolean cardExists(Creditcard card) {
		return cards.GetCreditcard(card.getId(), card.getFirst_name(), card.getLast_name(), card.getExpiration()) != null;
	}
}
