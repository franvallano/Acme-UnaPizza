package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CookService;
import services.CustomerService;
import services.DeliveryManService;
import services.PurchaseOrderService;
import services.RepairService;
import services.SalesOrderService;
import domain.Cook;
import domain.Customer;
import domain.DeliveryMan;
import domain.PurchaseOrder;


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
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
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
		Collection<Customer> customerMoreMoneySpent;
		Collection<Cook> cookMoreOrders;
		Double totalMoneyUndeliveredOrders;
		Collection<PurchaseOrder> totalPurcharseOrders;
		
		investedMoney = purchaseOrderService.findInvestedMoney();
		customerMoreComplaints = customerService.findCustomerMoreComplaints();
		salesMoney = salesOrderService.findSalesMoney();
		totalCostRepairs = repairService.findTotalCostRepairs();
		netSalesMoney = salesMoney - investedMoney - totalCostRepairs;
		customerMoreOrders = customerService.findCustomerMoreOrders();
		avgOrders = salesOrderService.findAvgOrders();
		deliveryManMoreOrders = deliveryManService.findDeliveryManMoreOrders();
		customerMoreMoneySpent = customerService.findCustomerMoreMoneySpent();
		cookMoreOrders = cookService.findCookMoreOrders();
		totalMoneyUndeliveredOrders = salesOrderService.findTotalMoneyUndeliveredOrders();
		totalPurcharseOrders = purchaseOrderService.findAll();
		
		result = new ModelAndView("dashboard/list");
		result.addObject("investedMoney", investedMoney);
		result.addObject("customerMoreComplaints", customerMoreComplaints);
		result.addObject("salesMoney", salesMoney);
		result.addObject("netSalesMoney", netSalesMoney);
		result.addObject("customerMoreOrders", customerMoreOrders);
		result.addObject("avgOrders", avgOrders);
		result.addObject("deliveryManMoreOrders", deliveryManMoreOrders);
		result.addObject("customerMoreMoneySpent", customerMoreMoneySpent);
		result.addObject("cookMoreOrders", cookMoreOrders);
		result.addObject("totalMoneyUndeliveredOrders", totalMoneyUndeliveredOrders);
		result.addObject("totalPurcharseOrders", totalPurcharseOrders);
		
		return result;
	}
}
