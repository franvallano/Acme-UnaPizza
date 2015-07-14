package controllers.customer;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.OfferService;
import services.ProductService;
import services.SalesOrderService;
import controllers.AbstractController;
import domain.Offer;
import domain.Product;
import domain.PurchaseOrder;
import domain.SalesOrder;
import forms.PurchaseOrderForm;
import forms.SalesOrderForm;


@Controller
@RequestMapping("/salesOrder/customer")
public class SalesOrderCustomerController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OfferService offerService;
	
	//Constructor------------------------------------------------------
	public SalesOrderCustomerController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listSalesOrders(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllByCustomerId();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/customer/list.do");
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int salesOrderId) {
		ModelAndView result;
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOne(salesOrderId);
		
		result = new ModelAndView("salesOrder/edit");
		result.addObject("salesOrder", salesOrder);
		result.addObject("details", true);
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SalesOrderForm salesOrderForm;

		salesOrderForm = salesOrderService.createForm();

		result = createEditModelAndView(salesOrderForm);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SalesOrderForm salesOrderForm, BindingResult binding){
		ModelAndView result;
		SalesOrder salesOrder;

		if(binding.hasErrors()){
			result = createEditModelAndView(salesOrderForm);
		}else{
			try{
				salesOrder = salesOrderService.reconstruct(salesOrderForm);
				salesOrderService.save(salesOrder);
				result = new ModelAndView("redirect:/salesOrder/customer/list.do");
			}catch (Throwable oops){
				salesOrderForm.setTotalCost(0.0);
				result = createEditModelAndView(salesOrderForm, "salesOrder.commit.error");
			}
		}
		
		return result;
	}
	
	public ModelAndView createEditModelAndView(SalesOrderForm salesOrderForm){
		ModelAndView result;
		
		result = createEditModelAndView(salesOrderForm, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(SalesOrderForm salesOrderForm, String message){
		ModelAndView res;
		Collection<Product> pizzas;
		Collection<Product> complements;
		Collection<Product> desserts;
		Collection<Product> drinks;
		Collection<Integer> totalAmount;
		Collection<Integer> idPizzas;
		Collection<Integer> idComplements;
		Collection<Integer> idDesserts;
		Collection<Integer> idDrinks;
		Collection<Offer> availableOffers;
		String range;
		
		range = customerService.findByPrincipal().getRangee();
		
		if(range.equals("STANDARD"))
			availableOffers = offerService.findOffersSTANDARD();
		else if(range.equals("SILVER"))
			availableOffers = offerService.findOffersSILVER();
		else if(range.equals("GOLD"))
			availableOffers = offerService.findOffersGOLD();
		else if(range.equals("VIP"))
			availableOffers = offerService.findOffersVIP();
		else
			availableOffers = new ArrayList<Offer>();
		
		pizzas = productService.findAllPizzasMin(5);
		complements = productService.findAllComplementsMin(5);
		desserts = productService.findAllDessertsMin(5);
		drinks = productService.findAllDrinksMin(5);
		
		totalAmount = new ArrayList<Integer>();
		idPizzas = new ArrayList<Integer>();
		idComplements = new ArrayList<Integer>();
		idDesserts = new ArrayList<Integer>();
		idDrinks = new ArrayList<Integer>();
		
		for(int i=1;i<=6;i++)
			totalAmount.add(i);
		
		idPizzas = productService.findAllIdsPizzasMin(5);
		idComplements = productService.findAllIdsComplementsMin(5);
		idDesserts = productService.findAllIdsDessertsMin(5);
		idDrinks = productService.findAllIdsDrinksMin(5);
		
		salesOrderForm.setIdPizzas(idPizzas);
		salesOrderForm.setIdComplements(idComplements);
		salesOrderForm.setIdDesserts(idDesserts);
		salesOrderForm.setIdDrinks(idDrinks);

		res = new ModelAndView("salesOrder/edit");
		res.addObject("salesOrderForm", salesOrderForm);
		res.addObject("message", message);
		res.addObject("requestURI", "salesOrder/customer/edit.do");	
		res.addObject("edit", true);
		res.addObject("pizzas", pizzas);
		res.addObject("pizzas", pizzas);
		res.addObject("complements", complements);
		res.addObject("desserts", desserts);
		res.addObject("drinks", drinks);
		res.addObject("totalAmount", totalAmount);
		res.addObject("availableOffers", availableOffers);
	
		return res;
	}
}
