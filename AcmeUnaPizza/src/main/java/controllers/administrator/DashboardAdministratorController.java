package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CookService;
import services.CustomerService;
import services.DeliveryManService;
import services.ProductService;
import services.PurchaseOrderService;
import services.RepairService;
import services.SalesOrderService;
import services.StuffService;
import controllers.AbstractController;
import domain.Cook;
import domain.Customer;
import domain.DeliveryMan;
import domain.Product;
import domain.PurchaseOrder;
import domain.Stuff;


@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DeliveryManService deliveryManService;
	
	@Autowired
	private RepairService repairService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private StuffService stuffService;
	
	
	//Constructor------------------------------------------------------
	public DashboardAdministratorController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listDashboardAdmin(){
		ModelAndView result;
		
		Double investedMoney;
		Collection<Customer> customerMoreComplaints;
		Double salesMoney;
		Double netSalesMoney;
		Double totalCostRepairs;
		Collection<Customer> customerMoreOrders;
		Double avgOrders;
		Collection<DeliveryMan> deliveryManMoreOrders;
		Collection<Customer> customerMoreMoneySpent;
		Collection<Cook> cookMoreOrders;
		Double totalMoneyUndeliveredOrders;
		Collection<PurchaseOrder> purchaseOrdersMoreExpensive;
		Collection<Product> moreSoldPizza;
		Collection<Product> lessSoldPizza;
		Collection<Product> moreSoldComplement;
		Collection<Product> lessSoldComplement;
		Collection<Product> moreSoldDrink;
		Collection<Product> lessSoldDrink;
		Collection<Product> moreSoldDessert;
		Collection<Product> lessSoldDessert;
		Collection<Stuff> stuffMoreRepaired;
		
		investedMoney = purchaseOrderService.findInvestedMoney();
		customerMoreComplaints = customerService.findCustomerMoreComplaints();
		salesMoney = salesOrderService.findSalesMoney();
		totalCostRepairs = repairService.findTotalCostRepairs();
		netSalesMoney = salesMoney - investedMoney - totalCostRepairs;
		customerMoreOrders = customerService.findCustomerMoreOrders();
		avgOrders = salesOrderService.findAvgOrders();
		deliveryManMoreOrders = deliveryManService.findDeliveryManMoreOrders();
		customerMoreMoneySpent = customerService.findCustomerMoreMoneySpent();
		cookMoreOrders = cookService.findCookMoreOrders();
		totalMoneyUndeliveredOrders = salesOrderService.findTotalMoneyUndeliveredOrders();
		purchaseOrdersMoreExpensive = purchaseOrderService.findPurchaseOrdersMoreExpensive();
		
		moreSoldPizza = productService.findMoreSoldPizza();
		lessSoldPizza = productService.findLessSoldPizza();
		moreSoldComplement = productService.findMoreSoldComplement();
		lessSoldComplement = productService.findLessSoldComplement();
		moreSoldDrink = productService.findMoreSoldDrink();
		lessSoldDrink = productService.findLessSoldDrink();
		moreSoldDessert = productService.findMoreSoldDessert();
		lessSoldDessert = productService.findLessSoldDessert();
		
		stuffMoreRepaired = stuffService.findStuffMoreRepaired();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("investedMoney", investedMoney);
		result.addObject("customerMoreComplaints", customerMoreComplaints);
		result.addObject("salesMoney", salesMoney);
		result.addObject("netSalesMoney", netSalesMoney);
		result.addObject("customerMoreOrders", customerMoreOrders);
		result.addObject("avgOrders", avgOrders);
		result.addObject("deliveryManMoreOrders", deliveryManMoreOrders);
		result.addObject("customerMoreMoneySpent", customerMoreMoneySpent);
		result.addObject("cookMoreOrders", cookMoreOrders);
		result.addObject("totalMoneyUndeliveredOrders", totalMoneyUndeliveredOrders);
		result.addObject("purchaseOrdersMoreExpensive", purchaseOrdersMoreExpensive);
		result.addObject("moreSoldPizza", moreSoldPizza);
		result.addObject("lessSoldPizza", lessSoldPizza);
		result.addObject("moreSoldComplement", moreSoldComplement);
		result.addObject("lessSoldComplement", lessSoldComplement);
		result.addObject("moreSoldDrink", moreSoldDrink);
		result.addObject("lessSoldDrink", lessSoldDrink);
		result.addObject("moreSoldDessert", moreSoldDessert);
		result.addObject("lessSoldDessert", lessSoldDessert);
		result.addObject("stuffMoreRepaired", stuffMoreRepaired);
		
		result.addObject("dashboard", true);
		
		return result;
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/ordersSuggestion", method = RequestMethod.GET)
	public ModelAndView listOrdersSuggestionAdmin() {
		ModelAndView result;

		Collection<Product> stockMinPizzas;
		Collection<Product> stockMinComplements;
		Collection<Product> stockMinDesserts;
		Collection<Product> stockMinDrinks;
		
		stockMinPizzas = productService.findStockMinPizzas();
		stockMinComplements = productService.findStockMinComplements();
		stockMinDesserts = productService.findStockMinDesserts();
		stockMinDrinks = productService.findStockMinDrinks();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("stockMinPizzas", stockMinPizzas);
		result.addObject("stockMinComplements", stockMinComplements);
		result.addObject("stockMinDesserts", stockMinDesserts);
		result.addObject("stockMinDrinks", stockMinDrinks);
		result.addObject("dashboard", false);
		
		return result;
	}
		
}
