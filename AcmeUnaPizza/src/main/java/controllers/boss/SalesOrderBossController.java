package controllers.boss;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BossService;
import services.SalesOrderService;
import controllers.AbstractController;
import domain.Note;
import domain.SalesOrder;


@Controller
@RequestMapping("/salesOrder/boss")
public class SalesOrderBossController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private BossService bossService;
	
	//Constructor------------------------------------------------------
	public SalesOrderBossController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listSalesOrders(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAll();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/boss/listAll.do");
		
		return result;
	}
	
	@RequestMapping(value = "/listOpened", method = RequestMethod.GET)
	public ModelAndView listSalesOrdersOpened(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllSalesOrderOpened();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/boss/listOpened.do");
		result.addObject("opened", true);
		
		return result;
	}
	
	@RequestMapping(value = "/listInProcess", method = RequestMethod.GET)
	public ModelAndView listSalesOrdersInProcess(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllSalesOrderInProcess();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/boss/listInProcess.do");
		result.addObject("inProcess", true);
		
		return result;
	}
	
	@RequestMapping(value = "/assignBoss", method = RequestMethod.GET)
	public ModelAndView assignBoss(@RequestParam int salesOrderId){
		ModelAndView result;
		
		bossService.assignBoss(salesOrderId);

		result = new ModelAndView("redirect:/salesOrder/boss/listOpened.do");
		
		return result;
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancelSalesOrder(@RequestParam int salesOrderId){
		ModelAndView result;
		
		salesOrderService.cancelSalesOrder(salesOrderId);

		result = new ModelAndView("redirect:/salesOrder/boss/listOpened.do");
		
		return result;
	}
	
	@RequestMapping(value = "/listUndelivered", method = RequestMethod.GET)
	public ModelAndView listSalesOrdersUndelivered(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllSalesOrderUndelivered();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/boss/listUndelivered.do");
		result.addObject("note", true);
		
		return result;
	}
	
	@RequestMapping(value = "/listCompleted", method = RequestMethod.GET)
	public ModelAndView listSalesOrdersCompleted(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllSalesOrderDelivered();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/boss/listCompleted.do");
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int salesOrderId) {
		ModelAndView result;
		SalesOrder salesOrder;
		
		salesOrder = salesOrderService.findOneCheckBoss(salesOrderId);
		
		result = new ModelAndView("salesOrder/edit");
		result.addObject("salesOrder", salesOrder);
		result.addObject("details", true);
		
		return result;
	}
}
