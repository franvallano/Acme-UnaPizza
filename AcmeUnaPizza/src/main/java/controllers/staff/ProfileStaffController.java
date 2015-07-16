package controllers.staff;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.StaffService;
import controllers.AbstractController;
import domain.Boss;
import domain.Cook;
import domain.DeliveryMan;
import domain.Staff;
import forms.PasswordForm;
import forms.StaffProfileForm;
 
@Controller
@RequestMapping("/profile/staff")
public class ProfileStaffController extends AbstractController{
	
	@Autowired
	private StaffService staffService;
	
	public ProfileStaffController(){
		super();
	}
	
	@RequestMapping(value = "/editBoss", method = RequestMethod.GET)
	public ModelAndView editProfileBoss() {
		ModelAndView result;
		StaffProfileForm staffProfileForm;
		Staff staff;
		
		staff = staffService.findByPrincipal();

		staffProfileForm = staffService.desreconstructProfile(staff, Authority.BOSS);
		
		result = createEditModelAndView(staffProfileForm, Authority.BOSS);
		
		return result;
	}
	
	@RequestMapping(value = "/editDeliveryMan", method = RequestMethod.GET)
	public ModelAndView editProfileDeliveryMan() {
		ModelAndView result;
		StaffProfileForm staffProfileForm;
		Staff staff;
		
		staff = staffService.findByPrincipal();

		staffProfileForm = staffService.desreconstructProfile(staff, Authority.DELIVERY_MAN);
		
		result = createEditModelAndView(staffProfileForm, Authority.DELIVERY_MAN);
		
		return result;
	}
	
	@RequestMapping(value = "/editCook", method = RequestMethod.GET)
	public ModelAndView editProfileCook() {
		ModelAndView result;
		StaffProfileForm staffProfileForm;
		Staff staff;
		
		staff = staffService.findByPrincipal();

		staffProfileForm = staffService.desreconstructProfile(staff, Authority.COOK);
		
		result = createEditModelAndView(staffProfileForm, Authority.COOK);
		
		return result;
	}
	
	@RequestMapping(value = "/editBoss", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBoss(@Valid StaffProfileForm staffProfileForm, BindingResult binding){
		ModelAndView result;
		Boss boss;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(staffProfileForm, Authority.BOSS);
		}else{
			try{
				boss = (Boss) staffService.reconstructProfile(staffProfileForm, Authority.BOSS);
				staffService.saveProfile(boss);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				
				
				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(staffProfileForm, "commit.duplicatedUser", Authority.BOSS);
				else
					result = createEditModelAndView(staffProfileForm, "commit.error", Authority.BOSS);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/editDeliveryMan", method = RequestMethod.POST, params = "save")
	public ModelAndView saveDeliveryMan(@Valid StaffProfileForm staffProfileForm, BindingResult binding){
		ModelAndView result;
		DeliveryMan deliveryMan;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(staffProfileForm, Authority.DELIVERY_MAN);
		}else{
			try{
				deliveryMan = (DeliveryMan) staffService.reconstructProfile(staffProfileForm, Authority.DELIVERY_MAN);
				staffService.saveProfile(deliveryMan);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(staffProfileForm, "commit.duplicatedUser", Authority.DELIVERY_MAN);
				else
					result = createEditModelAndView(staffProfileForm, "commit.error", Authority.DELIVERY_MAN);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/editCook", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCook(@Valid StaffProfileForm staffProfileForm, BindingResult binding){
		ModelAndView result;
		Cook cook;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(staffProfileForm, Authority.COOK);
		}else{
			try{
				cook = (Cook) staffService.reconstructProfile(staffProfileForm, Authority.COOK);
				staffService.saveProfile(cook);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(staffProfileForm, "commit.duplicatedUser", Authority.COOK);
				else
					result = createEditModelAndView(staffProfileForm, "commit.error", Authority.COOK);
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
		Staff staff;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(passwordForm);
		}else{
			try{
				staff = staffService.reconstructPassword(passwordForm);
				staffService.saveProfile(staff);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops){
				result = createEditModelAndView(passwordForm, "password.commit.error");
			}
		}
		
		return result;
	}
	
	//Ancillary methods
	
	protected ModelAndView createEditModelAndView(StaffProfileForm staffProfileForm, String authority){
		ModelAndView result;
		
		result = createEditModelAndView(staffProfileForm, null, authority);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(StaffProfileForm staffProfileForm, String message, String authority){
		ModelAndView result;
		
		result = new ModelAndView("profile/edit");
		result.addObject("staffProfileForm", staffProfileForm);
		result.addObject("userForm", "staffProfileForm");
		result.addObject("message", message);
		
		if(authority.equals(Authority.BOSS))
			result.addObject("url", "profile/staff/editBoss.do");
		else if(authority.equals(Authority.DELIVERY_MAN)) {
			result.addObject("url", "profile/staff/editDeliveryMan.do");
			result.addObject("isDeliveryMan", true);
		} else if(authority.equals(Authority.COOK))
			result.addObject("url", "profile/staff/editCook.do");
		
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
		result.addObject("url", "profile/staff/changePassword.do");
		
		return result;
	}
}
