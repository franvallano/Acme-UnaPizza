package controllers.deliveryMan;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SalesOrderService;


@Controller
@RequestMapping("/dashboard/deliveryMan")
public class DashboardDeliveryManController {
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	//Constructor------------------------------------------------------
	public DashboardDeliveryManController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listDashboardAdmin(){
		ModelAndView result;
		
		Integer totalSalesOrdersByStaff;
		Double moreExpensiveSalesOrderStaff;
		Double lessExpensiveSalesOrderStaff;
		Double avgSalesOrderByStaff;
		
		totalSalesOrdersByStaff = salesOrderService.findTotalSalesOrderByStaffOrAll();
		moreExpensiveSalesOrderStaff = salesOrderService.findMoreExpensiveSalesOrderByStaffOrAll();
		lessExpensiveSalesOrderStaff = salesOrderService.findLessExpensiveSalesOrderByStaffOrAll();
		avgSalesOrderByStaff = salesOrderService.findAvgSalesOrderByStaffOrAll();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("totalSalesOrdersByStaff", totalSalesOrdersByStaff);
		result.addObject("moreExpensiveSalesOrderStaff", moreExpensiveSalesOrderStaff);
		result.addObject("lessExpensiveSalesOrderStaff", lessExpensiveSalesOrderStaff);
		result.addObject("avgSalesOrderByStaff", avgSalesOrderByStaff);
		
		return result;
	}
}
