package controllers.cook;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CookService;
import services.SalesOrderService;
import controllers.AbstractController;
import domain.SalesOrder;


@Controller
@RequestMapping("/salesOrder/cook")
public class SalesOrderCookController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private CookService cossService;
	
	//Constructor------------------------------------------------------
	public SalesOrderCookController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/forCooking", method = RequestMethod.GET)
	public ModelAndView listForCooking(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllForCooking();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/cook/forCooking.do");
		
		return result;
	}
	
	@RequestMapping(value = "/forPrepared", method = RequestMethod.GET)
	public ModelAndView listForPrepared(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllForPrepared();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/cook/forPrepared.do");
		
		return result;
	}
	
	@RequestMapping(value = "/assignAndCooking", method = RequestMethod.GET)
	public ModelAndView assignAndCooking(@RequestParam int salesOrderId){
		ModelAndView result;
		
		cossService.assignAndCooking(salesOrderId);

		result = new ModelAndView("redirect:/salesOrder/cook/forCooking.do");
		
		return result;
	}
	
	@RequestMapping(value = "/prepared", method = RequestMethod.GET)
	public ModelAndView prepared(@RequestParam int salesOrderId){
		ModelAndView result;
		
		cossService.prepared(salesOrderId);

		result = new ModelAndView("redirect:/salesOrder/cook/forPrepared.do");
		
		return result;
	}
}
