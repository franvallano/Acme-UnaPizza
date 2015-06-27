package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CustomerService;
import services.DeliveryManService;
import services.RepairService;
import domain.Customer;
import domain.DeliveryMan;


@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {
	//Services--------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DeliveryManService deliveryManService;
	
	@Autowired
	private RepairService repairService;
	
	//Constructor------------------------------------------------------
	public DashboardAdministratorController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listDashboardAdmin(){
		ModelAndView result;
		
		Double investedMoney;
		Collection<Customer> customerMoreComplaints;
		Double salesMoney;
		Double netSalesMoney;
		Double totalCostRepairs;
		Collection<Customer> customerMoreOrders;
		Double avgOrders;
		Collection<DeliveryMan> deliveryManMoreOrders;
		
		investedMoney = administratorService.findInvestedMoney();
		customerMoreComplaints = customerService.findCustomerMoreComplaints();
		salesMoney = administratorService.findSalesMoney();
		totalCostRepairs = repairService.findTotalCostRepairs();
		netSalesMoney = salesMoney - investedMoney - totalCostRepairs;
		customerMoreOrders = customerService.findCustomerMoreOrders();
		avgOrders = administratorService.findAvgOrders();
		deliveryManMoreOrders = deliveryManService.findDeliveryManMoreOrders();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("investedMoney", investedMoney);
		result.addObject("customerMoreComplaints", customerMoreComplaints);
		result.addObject("salesMoney", salesMoney);
		result.addObject("netSalesMoney", netSalesMoney);
		result.addObject("customerMoreOrders", customerMoreOrders);
		result.addObject("avgOrders", avgOrders);
		result.addObject("deliveryManMoreOrders", deliveryManMoreOrders);
		
		return result;
	}
}
