package controllers.deliveryMan;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SalesOrderService;
import controllers.AbstractController;
import domain.SalesOrder;


@Controller
@RequestMapping("/dashboard/deliveryMan")
public class DashboardDeliveryManController extends AbstractController {
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
		Double moreExpensiveSalesOrderByStaff;
		Double lessExpensiveSalesOrderByStaff;
		Double avgSalesOrderByStaff;
		Collection<SalesOrder> salesOrderMinDrivingTime;
		Collection<SalesOrder> salesOrderMaxDrivingTime;
		
		totalSalesOrdersByStaff = salesOrderService.findTotalSalesOrderByStaffOrAll();
		moreExpensiveSalesOrderByStaff = salesOrderService.findMoreExpensiveSalesOrderByStaffOrAll();
		lessExpensiveSalesOrderByStaff = salesOrderService.findLessExpensiveSalesOrderByStaffOrAll();
		avgSalesOrderByStaff = salesOrderService.findAvgSalesOrderByStaffOrAll();
		salesOrderMinDrivingTime = salesOrderService.findSalesOrderWithMinDrinvingTime();
		salesOrderMaxDrivingTime = salesOrderService.findSalesOrderWithMaxDrinvingTime();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("totalSalesOrdersByStaff", totalSalesOrdersByStaff);
		result.addObject("moreExpensiveSalesOrderByStaff", moreExpensiveSalesOrderByStaff);
		result.addObject("lessExpensiveSalesOrderByStaff", lessExpensiveSalesOrderByStaff);
		result.addObject("avgSalesOrderByStaff", avgSalesOrderByStaff);
		result.addObject("salesOrderMaxDrivingTime",salesOrderMaxDrivingTime);
		result.addObject("salesOrderMinDrivingTime",salesOrderMinDrivingTime);
		
		return result;
	}
}
