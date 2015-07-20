package controllers.deliveryMan;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DeliveryManService;
import services.SalesOrderService;
import controllers.AbstractController;
import domain.SalesOrder;
import forms.DrivingTimeForm;
import forms.NoteDrivingTimeForm;


@Controller
@RequestMapping("/salesOrder/deliveryMan")
public class SalesOrderDeliveryManController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private DeliveryManService deliveryManService;
	
	//Constructor------------------------------------------------------
	public SalesOrderDeliveryManController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/listOnItsWay", method = RequestMethod.GET)
	public ModelAndView listOnItsWay(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findAllOnItsWay();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/deliveryMan/listOnItsWay.do");
		
		return result;
	}
	
	@RequestMapping(value = "/onItsWay", method = RequestMethod.GET)
	public ModelAndView onItsWay(@RequestParam int salesOrderId){
		ModelAndView result;
		
		deliveryManService.onItsWay(salesOrderId);
		
		result = new ModelAndView("redirect:/salesOrder/deliveryMan/listOnItsWay.do");

		result.addObject("requestURI", "salesOrder/deliveryMan/listOnItsWay.do");
		
		return result;
	}
	
	@RequestMapping(value = "/finish", method = RequestMethod.GET)
	public ModelAndView listToFinish(){
		ModelAndView result;
		
		Collection<SalesOrder> salesOrders;
		
		salesOrders = salesOrderService.findOneToFinish();
		
		result = new ModelAndView("salesOrder/list");
		result.addObject("salesOrders", salesOrders);
		result.addObject("requestURI", "salesOrder/deliveryMan/finish.do");
		
		return result;
	}
	
	@RequestMapping(value = "/toFinish", method = RequestMethod.GET)
	public ModelAndView finishSalesOrder(@RequestParam int salesOrderId) {
		ModelAndView result;
		DrivingTimeForm drivingTimeForm;
		SalesOrder salesOrder;
		
		drivingTimeForm = new DrivingTimeForm();
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrderId);
		Assert.isTrue(salesOrder.getDeliveryMan().getId() == deliveryManService.findByPrincipal().getId());
		Assert.isTrue(salesOrder.getId() == salesOrderId);
		Assert.isTrue(salesOrder.getState().equals("ONITSWAY"));
		
		drivingTimeForm.setSalesOrderId(salesOrderId);
		
		result = createEditModelAndView(drivingTimeForm);
		
		result.addObject("delivered", true);
		
		return result;
	}
	
	@RequestMapping(value = "/notFinish", method = RequestMethod.GET)
	public ModelAndView notFinishSalesOrder(@RequestParam int salesOrderId) {
		ModelAndView result;
		NoteDrivingTimeForm noteDrivingTimeForm;
		SalesOrder salesOrder;
		
		noteDrivingTimeForm = new NoteDrivingTimeForm();
		
		salesOrder = salesOrderService.findOneCheckDeliveryMan(salesOrderId);
		Assert.isTrue(salesOrder.getDeliveryMan().getId() == deliveryManService.findByPrincipal().getId());
		Assert.isTrue(salesOrder.getId() == salesOrderId);
		Assert.isTrue(salesOrder.getState().equals("ONITSWAY"));
		
		noteDrivingTimeForm.setSalesOrderId(salesOrderId);

		result = createEditModelAndView(noteDrivingTimeForm);
		
		return result;
	}
	
	@RequestMapping(value = "/toFinish", method = RequestMethod.POST, params = "save")
	public ModelAndView saveToFinish(@Valid DrivingTimeForm drivingTimeForm, BindingResult binding){
		ModelAndView result;
		SalesOrder salesOrder;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(drivingTimeForm);
		}else{
			try{
				salesOrder = salesOrderService.reconstructDrivingTime(drivingTimeForm);
				salesOrderService.saveByDeliveryMan(salesOrder);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				result = createEditModelAndView(drivingTimeForm, "commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/notFinish", method = RequestMethod.POST, params = "save")
	public ModelAndView saveNotFinish(@Valid NoteDrivingTimeForm noteDrivingTimeForm, BindingResult binding){
		ModelAndView result;
		SalesOrder salesOrder;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(noteDrivingTimeForm);
		}else{
			try{
				salesOrder = salesOrderService.reconstructNoteDrivingTime(noteDrivingTimeForm);
				salesOrderService.saveByDeliveryMan(salesOrder);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				result = createEditModelAndView(noteDrivingTimeForm, "commit.error");
			}
		}
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(DrivingTimeForm drivingTimeForm){
		ModelAndView result;
		
		result = createEditModelAndView(drivingTimeForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(NoteDrivingTimeForm noteDrivingTimeForm){
		ModelAndView result;
		
		result = createEditModelAndView(noteDrivingTimeForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(NoteDrivingTimeForm noteDrivingTimeForm, String message){
		ModelAndView result;
		Collection<String> causes;
		
		causes = new ArrayList<String>();
		
		causes.add("CANCELLED");
		causes.add("JOKE");
		causes.add("OTHER");
		
		result = new ModelAndView("salesOrder/finish");
		result.addObject("noteDrivingTimeForm", noteDrivingTimeForm);
		result.addObject("requestURI", "salesOrder/deliveryMan/notFinish.do");
		result.addObject("message", message);
		result.addObject("causes", causes);
		result.addObject("undelivered", true);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(DrivingTimeForm drivingTimeForm, String message){
		ModelAndView result;
		
		result = new ModelAndView("salesOrder/finish");
		result.addObject("drivingTimeForm", drivingTimeForm);
		result.addObject("requestURI", "salesOrder/deliveryMan/toFinish.do");
		result.addObject("message", message);
		result.addObject("delivered", true);
		
		return result;
	}
	
}
