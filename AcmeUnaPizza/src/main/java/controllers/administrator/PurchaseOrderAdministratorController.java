package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProductService;
import services.PurchaseOrderService;
import controllers.AbstractController;
import domain.Product;
import domain.PurchaseOrder;
import forms.PurchaseOrderForm;


@Controller
@RequestMapping("/purchaseOrder/administrator")
public class PurchaseOrderAdministratorController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	private ProductService productService;
	
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
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int purchaseOrderId) {
		ModelAndView result;
		PurchaseOrder purchaseOrder;
		
		purchaseOrder = purchaseOrderService.findOne(purchaseOrderId);
		
		result = new ModelAndView("purchaseOrder/edit");
		result.addObject("purchaseOrder", purchaseOrder);
		result.addObject("details", true);
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PurchaseOrderForm purchaseOrderForm;

		purchaseOrderForm = purchaseOrderService.createForm();
		
		result = createEditModelAndView(purchaseOrderForm);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PurchaseOrderForm purchaseOrderForm, BindingResult binding){
		ModelAndView result;
		PurchaseOrder purchaseOrder;

		if(binding.hasErrors()){
			result = createEditModelAndView(purchaseOrderForm);
		}else{
			try{
				purchaseOrder = purchaseOrderService.reconstruct(purchaseOrderForm);
				purchaseOrderService.save(purchaseOrder);
				result = new ModelAndView("redirect:/purchaseOrder/administrator/list.do");
			}catch (Throwable oops){
				purchaseOrderForm.setTotalCost(0.0);
				result = createEditModelAndView(purchaseOrderForm, "purchaseOrder.commit.error");
			}
		}
		
		return result;
	}
	
	public ModelAndView createEditModelAndView(PurchaseOrderForm purchaseOrder){
		ModelAndView result;
		
		result = createEditModelAndView(purchaseOrder, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(PurchaseOrderForm purchaseOrderForm, String message){
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
		
		
		pizzas = productService.findAllPizzas();
		complements = productService.findAllComplements();
		desserts = productService.findAllDesserts();
		drinks = productService.findAllDrinks();
		
		totalAmount = purchaseOrderService.getTotalAmount();
		
		idPizzas = productService.findAllIdsPizzas();
		idComplements = productService.findAllIdsComplements();
		idDesserts = productService.findAllIdsDesserts();
		idDrinks = productService.findAllIdsDrinks();
		
		purchaseOrderForm = purchaseOrderService.setAllIdProducts(
				purchaseOrderForm, idPizzas, idComplements, idDesserts, idDrinks);
		
		res = new ModelAndView("purchaseOrder/edit");
		res.addObject("purchaseOrderForm", purchaseOrderForm);
		res.addObject("message", message);
		res.addObject("requestURI", "purchaseOrder/administrator/edit.do");	
		res.addObject("edit", true);
		res.addObject("pizzas", pizzas);
		res.addObject("pizzas", pizzas);
		res.addObject("complements", complements);
		res.addObject("desserts", desserts);
		res.addObject("drinks", drinks);
		res.addObject("totalAmount", totalAmount);
	
		return res;
	}
}
