package controllers.boss;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SalesOrderService;


@Controller
@RequestMapping("/dashboard/boss")
public class DashboardBossController {
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	//Constructor------------------------------------------------------
	public DashboardBossController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listDashboardAdmin(){
		ModelAndView result;
		
		Integer totalSalesOrder;
		Double totalMoreExpensiveSalesOrder;
		Double totalLessExpensiveSalesOrder;
		Double avgSalesOrder;
		
		totalSalesOrder = salesOrderService.findTotalSalesOrder();
		totalMoreExpensiveSalesOrder = salesOrderService.findMoreExpensiveSalesOrder();
		totalLessExpensiveSalesOrder = salesOrderService.findLessExpensiveSalesOrder();
		avgSalesOrder = salesOrderService.findAvgOrders();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("totalSalesOrder", totalSalesOrder);
		result.addObject("totalMoreExpensiveSalesOrder", totalMoreExpensiveSalesOrder);
		result.addObject("totalLessExpensiveSalesOrder", totalLessExpensiveSalesOrder);
		result.addObject("avgSalesOrder", avgSalesOrder);
		
		return result;
	}
}
