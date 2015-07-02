package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import controllers.AbstractController;
import domain.Customer;
import forms.CustomerForm;
 
@Controller
@RequestMapping("/register/customer")
public class RegisterCustomerController extends AbstractController{
	
	@Autowired
	private CustomerService customerService;
	
	public RegisterCustomerController(){
		super();
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		CustomerForm customerForm;

		customerForm = new CustomerForm();
		result = createEditModelAndView(customerForm);
		
		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CustomerForm customerForm, BindingResult binding){
		ModelAndView result;
		Customer customer;
		String repeatedPass;
		boolean duplicate;
		
		repeatedPass = customerForm.getRepeatedPass();
		
		if(binding.hasErrors()){
			result = createEditModelAndView(customerForm);
		}else{
			try{
				customer = customerService.reconstruct(customerForm);
				customerService.save(customer, repeatedPass, customerForm.isAgree());
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				
				
				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(customerForm, "commit.duplicatedUser");
				else
					result = createEditModelAndView(customerForm, "commit.error");
				
				duplicate = customerService.rPassword(customerForm);
				result.addObject("duplicate", duplicate);
				result.addObject("agree", customerForm.isAgree());
				result.addObject("checkBoxCreditCard", customerForm.isCheckBoxCreditCard());
			}
		}
		
		return result;
	}
	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(CustomerForm customerForm){
		ModelAndView result;
		
		result = createEditModelAndView(customerForm, null);
		result.addObject("checkBoxCreditCard", customerForm.isCheckBoxCreditCard());
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(CustomerForm customerForm, String message){
		ModelAndView result;
		
		result = new ModelAndView("register/register");
		result.addObject("customerForm", customerForm);
		// Se añade esta linea de abajo porque el modelAttribute es una variable y no una cadena,
		// si fuese modelAttribute="customerForm" con la linea de arriba seria suficiente
		result.addObject("userForm", "customerForm");
		result.addObject("message", message);
		result.addObject("url", "register/customer/register.do");

		
		return result;
	}
	

}
