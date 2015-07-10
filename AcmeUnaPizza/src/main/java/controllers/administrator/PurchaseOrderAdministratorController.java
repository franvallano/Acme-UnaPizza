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
import domain.Offer;
import domain.Product;
import domain.PurchaseOrder;
import domain.Stuff;


@Controller
@RequestMapping("/purchaseOrder/administrator")
public class PurchaseOrderAdministratorController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	//Constructor------------------------------------------------------
	public PurchaseOrderAdministratorController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPurchaseOrders(){
		ModelAndView result;
		
		Collection<PurchaseOrder> purchaseOrders;
		
		purchaseOrders = purchaseOrderService.findAll();
		
		result = new ModelAndView("purchaseOrder/list");
		result.addObject("purchaseOrders", purchaseOrders);
		result.addObject("requestURI", "purchaseOrder/administrator/list.do");
		
		return result;
	}
		
}
