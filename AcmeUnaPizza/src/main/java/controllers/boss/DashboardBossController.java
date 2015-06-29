package controllers.boss;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SalesOrderService;
import domain.SalesOrder;


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
		Collection<SalesOrder> salesOrderMinDrivingTime;
		Collection<SalesOrder> salesOrderMaxDrivingTime;
		
		totalSalesOrder = salesOrderService.findTotalSalesOrder();
		totalMoreExpensiveSalesOrder = salesOrderService.findMoreExpensiveSalesOrder();
		totalLessExpensiveSalesOrder = salesOrderService.findLessExpensiveSalesOrder();
		avgSalesOrder = salesOrderService.findAvgOrders();
		salesOrderMinDrivingTime = salesOrderService.findSalesOrderWithMinDrinvingTime();
		salesOrderMaxDrivingTime = salesOrderService.findSalesOrderWithMaxDrinvingTime();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("totalSalesOrder", totalSalesOrder);
		result.addObject("totalMoreExpensiveSalesOrder", totalMoreExpensiveSalesOrder);
		result.addObject("totalLessExpensiveSalesOrder", totalLessExpensiveSalesOrder);
		result.addObject("avgSalesOrder", avgSalesOrder);
		result.addObject("salesOrderMaxDrivingTime",salesOrderMaxDrivingTime);
		result.addObject("salesOrderMinDrivingTime",salesOrderMinDrivingTime);
		
		return result;
	}
}
