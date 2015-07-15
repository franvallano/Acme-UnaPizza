package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Customer;
import forms.AdministratorProfileForm;
import forms.CustomerProfileForm;
import forms.PasswordForm;
 
@Controller
@RequestMapping("/profile/administrator")
public class ProfileAdministratorController extends AbstractController{
	
	@Autowired
	private AdministratorService administratorService;
	
	public ProfileAdministratorController(){
		super();
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editProfile() {
		ModelAndView result;
		AdministratorProfileForm administratorProfileForm;
		Administrator administrator;
		
		administrator = administratorService.findByPrincipal();

		administratorProfileForm = administratorService.desreconstructProfile(administrator);
		
		result = createEditModelAndView(administratorProfileForm);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid AdministratorProfileForm administratorProfileForm, BindingResult binding){
		ModelAndView result;
		Administrator administrator;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(administratorProfileForm);
		}else{
			try{
				administrator = administratorService.reconstructProfile(administratorProfileForm);
				administratorService.saveProfile(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				
				
				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(administratorProfileForm, "commit.duplicatedUser");
				else
					result = createEditModelAndView(administratorProfileForm, "commit.error");
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
		Administrator administrator;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(passwordForm);
		}else{
			try{
				administrator = administratorService.reconstructPassword(passwordForm);
				administratorService.saveProfile(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				result = createEditModelAndView(passwordForm, "password.commit.error");
			}
		}
		
		return result;
	}
	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(AdministratorProfileForm administratorProfileForm){
		ModelAndView result;
		
		result = createEditModelAndView(administratorProfileForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(AdministratorProfileForm administratorProfileForm, String message){
		ModelAndView result;
		
		result = new ModelAndView("profile/edit");
		result.addObject("administratorProfileForm", administratorProfileForm);
		result.addObject("userForm", "administratorProfileForm");
		result.addObject("message", message);
		result.addObject("url", "profile/administrator/edit.do");
		
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
		result.addObject("url", "profile/administrator/changePassword.do");
		
		return result;
	}
}
