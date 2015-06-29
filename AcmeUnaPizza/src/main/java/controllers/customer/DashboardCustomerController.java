package controllers.customer;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;


@Controller
@RequestMapping("/dashboard/customer")
public class DashboardCustomerController {
	//Services--------------------------------------------------------
	@Autowired 
	private CustomerService customerService;
	
	//Constructor------------------------------------------------------
	public DashboardCustomerController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listDashboardAdmin(){
		ModelAndView result;
		
		Integer totalNumberOrders;
		Collection<Date> dateLastOrder;
		
		totalNumberOrders = customerService.findTotalNumberOrders();
		dateLastOrder = customerService.findDateLastOrder();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("totalNumberOrders", totalNumberOrders);
		
		if(dateLastOrder != null && !dateLastOrder.isEmpty())
			result.addObject("dateLastOrder", dateLastOrder.iterator().next());
		
		return result;
	}
}
