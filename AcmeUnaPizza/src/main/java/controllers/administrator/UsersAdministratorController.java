package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.BossService;
import services.CookService;
import services.CustomerService;
import services.DeliveryManService;
import services.StaffService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Boss;
import domain.Cook;
import domain.Customer;
import domain.DeliveryMan;
import domain.Staff;
import forms.AdministratorForm;


@Controller
@RequestMapping("/user/administrator")
public class UsersAdministratorController extends AbstractController {
	//Services--------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private BossService bossService;
	@Autowired
	private DeliveryManService deliveryManService;
	@Autowired
	private CookService cookService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private StaffService staffService;
	
	//Constructor------------------------------------------------------
	public UsersAdministratorController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/listAdministrators", method = RequestMethod.GET)
	public ModelAndView listAdministrators(){
		ModelAndView result;
		Administrator administrator;
		Collection<Administrator> administrators;
		
		administrator = administratorService.findByPrincipal();
		
		administrators = administratorService.findAll();
		
		result = new ModelAndView("administrator/list");
		result.addObject("administrators", administrators);
		result.addObject("requestURI", "user/administrator/listAdministrators.do");
		result.addObject("adminId", administrator.getId());
		
		return result;
	}
	
	@RequestMapping(value = "/listBosses", method = RequestMethod.GET)
	public ModelAndView listBosses(){
		ModelAndView result;

		Collection<Boss> staffs;
		
		staffs = bossService.findAll();
		
		result = new ModelAndView("staff/list");
		result.addObject("staffs", staffs);
		result.addObject("requestURI", "user/administrator/listBosses.do");
		result.addObject("staffType", "Boss");
		
		return result;
	}
	
	@RequestMapping(value = "/listDeliveryMen", method = RequestMethod.GET)
	public ModelAndView listDeliveryMen(){
		ModelAndView result;

		Collection<DeliveryMan> staffs;
		
		staffs = deliveryManService.findAll();
		
		result = new ModelAndView("staff/list");
		result.addObject("staffs", staffs);
		result.addObject("requestURI", "user/administrator/listDeliveryMen.do");
		result.addObject("staffType", "DeliveryMan");
		
		return result;
	}
	
	@RequestMapping(value = "/listCooks", method = RequestMethod.GET)
	public ModelAndView listCooks(){
		ModelAndView result;

		Collection<Cook> staffs;
		
		staffs = cookService.findAll();
		
		result = new ModelAndView("staff/list");
		result.addObject("staffs", staffs);
		result.addObject("requestURI", "user/administrator/listCooks.do");
		result.addObject("staffType", "Cook");
		
		return result;
	}
	
	@RequestMapping(value = "/listCustomers", method = RequestMethod.GET)
	public ModelAndView listCustomers(){
		ModelAndView result;

		Collection<Customer> customers;
		
		customers = customerService.findAll();
		
		result = new ModelAndView("customer/list");
		result.addObject("customers", customers);
		result.addObject("requestURI", "user/administrator/listCustomers.do");

		
		return result;
	}
	
	@RequestMapping(value = "/detailsAdministrator", method = RequestMethod.GET)
	public ModelAndView detailsActor(@RequestParam int administratorId){
		ModelAndView result;
		Administrator administrator;
		
		administrator = administratorService.findOne(administratorId);
		
		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("details", true);
		
		return result;
	}
	
	@RequestMapping(value = "/staff/detailsStaff", method = RequestMethod.GET)
	public ModelAndView detailsStaff(@RequestParam int staffId){
		ModelAndView result;
		Staff staff;
		
		staff = staffService.findOne(staffId);
		
		String staffType = "";
		
		if(staff.getClass().equals(DeliveryMan.class))
			staffType = "deliveryMan";
		
		result = new ModelAndView("staff/edit");
		result.addObject("staff", staff);
		result.addObject("staffType", staffType);
		result.addObject("details", true);
		
		return result;
	}
	
	@RequestMapping(value = "/customer/detailsCustomer", method = RequestMethod.GET)
	public ModelAndView detailsCustomer(@RequestParam int customerId){
		ModelAndView result;
		Customer customer;
		
		customer = customerService.findOne(customerId);
		
		result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("details", true);
		
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int administratorId) {
		ModelAndView result;
		Administrator administrator;
		AdministratorForm administratorForm;

		administrator = administratorService.findOne(administratorId);
		
		administratorForm = administratorService.desreconstruct(administrator);
		
		result = createEditModelAndView(administratorForm);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm){
		ModelAndView result;
		
		result = createEditModelAndView(administratorForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm, String message){
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("edit", true);
		result.addObject("message", message);
		result.addObject("administratorForm", administratorForm);
		result.addObject("url","user/administrator/edit.do");
		
		return result;
	}
}
