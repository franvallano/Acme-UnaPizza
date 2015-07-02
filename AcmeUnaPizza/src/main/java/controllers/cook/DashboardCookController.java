package controllers.cook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PurchaseOrderService;
import services.SalesOrderService;
import controllers.AbstractController;


@Controller
@RequestMapping("/dashboard/cook")
public class DashboardCookController extends AbstractController {
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	//Constructor------------------------------------------------------
	public DashboardCookController(){
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
		
		totalSalesOrdersByStaff = salesOrderService.findTotalSalesOrderByStaffOrAll();
		moreExpensiveSalesOrderByStaff = salesOrderService.findMoreExpensiveSalesOrderByStaffOrAll();
		lessExpensiveSalesOrderByStaff = salesOrderService.findLessExpensiveSalesOrderByStaffOrAll();
		avgSalesOrderByStaff = salesOrderService.findAvgSalesOrderByStaffOrAll();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("totalSalesOrdersByStaff", totalSalesOrdersByStaff);
		result.addObject("moreExpensiveSalesOrderByStaff", moreExpensiveSalesOrderByStaff);
		result.addObject("lessExpensiveSalesOrderByStaff", lessExpensiveSalesOrderByStaff);
		result.addObject("avgSalesOrderByStaff", avgSalesOrderByStaff);
		
		return result;
	}
}
