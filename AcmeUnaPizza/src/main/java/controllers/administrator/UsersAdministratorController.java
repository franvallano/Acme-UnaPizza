package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.AdministratorService;
import services.BossService;
import services.CookService;
import services.CustomerService;
import services.DeliveryManService;
import services.StaffService;
import domain.Administrator;
import domain.Boss;
import domain.Cook;
import domain.Customer;
import domain.DeliveryMan;
import domain.Staff;


@Controller
@RequestMapping("/users/administrator")
public class UsersAdministratorController {
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
		result.addObject("requestURI", "users/administrator/listAdministrators.do");
		result.addObject("adminId", administrator.getId());
		
		return result;
	}
	
	@RequestMapping(value = "/listBosses", method = RequestMethod.GET)
	public ModelAndView listBosses(){
		ModelAndView result;

		Collection<Boss> bosses;
		
		bosses = bossService.findAll();
		
		result = new ModelAndView("boss/list");
		result.addObject("bosses", bosses);
		result.addObject("requestURI", "users/administrator/listBosses.do");

		
		return result;
	}
	
	@RequestMapping(value = "/listDeliveryMen", method = RequestMethod.GET)
	public ModelAndView listDeliveryMen(){
		ModelAndView result;

		Collection<DeliveryMan> deliveryMen;
		
		deliveryMen = deliveryManService.findAll();
		
		result = new ModelAndView("deliveryMan/list");
		result.addObject("deliveryMen", deliveryMen);
		result.addObject("requestURI", "users/administrator/listDeliveryMen.do");

		
		return result;
	}
	
	@RequestMapping(value = "/listCooks", method = RequestMethod.GET)
	public ModelAndView listCooks(){
		ModelAndView result;

		Collection<Cook> cooks;
		
		cooks = cookService.findAll();
		
		result = new ModelAndView("cook/list");
		result.addObject("cooks", cooks);
		result.addObject("requestURI", "users/administrator/listCooks.do");

		
		return result;
	}
	
	@RequestMapping(value = "/listCustomers", method = RequestMethod.GET)
	public ModelAndView listCustomers(){
		ModelAndView result;

		Collection<Customer> customers;
		
		customers = customerService.findAll();
		
		result = new ModelAndView("customer/list");
		result.addObject("customers", customers);
		result.addObject("requestURI", "users/administrator/listCustomers.do");

		
		return result;
	}
	
	@RequestMapping(value = "/detailsAdministrator", method = RequestMethod.GET)
	public ModelAndView detailsActor(@RequestParam int administratorId){
		ModelAndView result;
		Administrator administrator;
		
		administrator = administratorService.findOne(administratorId);
		
		result = new ModelAndView("administrator/details");
		result.addObject("administrator", administrator);
		
		
		return result;
		
	}
	
	@RequestMapping(value = "/staff/detailsStaff", method = RequestMethod.GET)
	public ModelAndView detailsStaff(@RequestParam int staffId){
		ModelAndView result;
		Staff staff;
		
		staff = staffService.findOne(staffId);
		
		boolean isDeliveryMan = false;
		
		if(staff.getClass().equals(DeliveryMan.class))
			isDeliveryMan = true;
		
		result = new ModelAndView("staff/details");
		result.addObject("staff", staff);
		result.addObject("isDeliveryMan", isDeliveryMan);
		
		
		return result;
		
	}
}
