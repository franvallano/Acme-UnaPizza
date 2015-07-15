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
import forms.CustomerProfileForm;
import forms.PasswordForm;
 
@Controller
@RequestMapping("/profile/customer")
public class ProfileCustomerController extends AbstractController{
	
	@Autowired
	private CustomerService customerService;
	
	public ProfileCustomerController(){
		super();
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editProfile() {
		ModelAndView result;
		CustomerProfileForm customerProfileForm;
		Customer customer;
		
		customer = customerService.findByPrincipal();

		customerProfileForm = customerService.desreconstructProfile(customer);
		
		result = createEditModelAndView(customerProfileForm);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CustomerProfileForm customerProfileForm, BindingResult binding){
		ModelAndView result;
		Customer customer;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(customerProfileForm);
		}else{
			try{
				customer = customerService.reconstructProfile(customerProfileForm);
				customerService.save(customer);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				
				
				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(customerProfileForm, "commit.duplicatedUser");
				else
					result = createEditModelAndView(customerProfileForm, "commit.error");
				
				result.addObject("checkBoxCreditCard", customerProfileForm.isCheckBoxCreditCard());
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword() {
		ModelAndView result;
		PasswordForm passwordForm;
		
		passwordForm = new PasswordForm();
		
		result = createEditModelAndView(passwordForm);
		
		return result;
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PasswordForm passwordForm, BindingResult binding){
		ModelAndView result;
		Customer customer;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(passwordForm);
		}else{
			try{
				customer = customerService.reconstructPassword(passwordForm);
				customerService.save(customer);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				result = createEditModelAndView(passwordForm, "password.commit.error");
			}
		}
		
		return result;
	}
	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(CustomerProfileForm customerProfileForm){
		ModelAndView result;
		
		result = createEditModelAndView(customerProfileForm, null);
		result.addObject("checkBoxCreditCard", customerProfileForm.isCheckBoxCreditCard());
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(CustomerProfileForm customerProfileForm, String message){
		ModelAndView result;
		
		result = new ModelAndView("profile/edit");
		result.addObject("customerProfileForm", customerProfileForm);
		result.addObject("userForm", "customerProfileForm");
		result.addObject("message", message);
		result.addObject("url", "profile/customer/edit.do");
		result.addObject("edit", true);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(PasswordForm passwordForm){
		ModelAndView result;
		
		result = createEditModelAndView(passwordForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(PasswordForm passwordForm, String message){
		ModelAndView result;
		
		result = new ModelAndView("profile/changePassword");
		result.addObject("passwordForm", passwordForm);
		result.addObject("passForm", "passwordForm");
		result.addObject("message", message);
		result.addObject("url", "profile/customer/changePassword.do");
		
		return result;
	}
}
