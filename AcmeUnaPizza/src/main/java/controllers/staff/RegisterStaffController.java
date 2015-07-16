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
import forms.StaffForm;

@Controller
@RequestMapping("/register/staff")
public class RegisterStaffController extends AbstractController{
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private StaffService staffService;
	
	// Constructors -----------------------------------------------------------
	
	public RegisterStaffController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	// Creation ---------------------------------------------------------------
	
	// Edition ----------------------------------------------------------------	
	
	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/registerBoss", method = RequestMethod.GET)
	public ModelAndView registerBoss() {
		ModelAndView result;
		StaffForm staffForm;
		
		staffForm = new StaffForm();

		result = createEditModelAndView(staffForm, true);
		result.addObject("action", "register/staff/register.do");
		result.addObject("isDeliveryMan", false);

		return result;
	}
	
	@RequestMapping(value = "/registerDeliveryMan", method = RequestMethod.GET)
	public ModelAndView registerDeliveryMan() {
		ModelAndView result;
		StaffForm staffForm;
		
		staffForm = new StaffForm();

		result = createEditModelAndView(staffForm, true);
		result.addObject("action", "register/staff/register.do");
		result.addObject("isDeliveryMan", true);

		return result;
	}
	
	@RequestMapping(value = "/registerCook", method = RequestMethod.GET)
	public ModelAndView registerCook() {
		ModelAndView result;
		StaffForm staffForm;
		
		staffForm = new StaffForm();

		result = createEditModelAndView(staffForm, false);
		result.addObject("action", "register/staff/register.do");
		result.addObject("isDeliveryMan", false);

		return result;
	}
	
	@RequestMapping(value = "/registerBoss", method = RequestMethod.POST, params = "save")
	public ModelAndView registerBoss(@Valid StaffForm staffForm, BindingResult binding) {
		ModelAndView result;
		String repeatedPass;
		boolean duplicate;
		Boss boss;
		
		repeatedPass = staffForm.getRepeatedPass();

		if (binding.hasErrors()) {
			result = createEditModelAndView(staffForm, false);
		} else { 
			try {
				
				boss = (Boss) staffService.reconstruct(staffForm, Authority.BOSS);
				staffService.save(boss, repeatedPass);
				
				result = new ModelAndView("redirect:/welcome/index.do");
				
			} catch (Throwable oops) {

				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(staffForm, "duplicatedUser", false);
				else
					result = createEditModelAndView(staffForm, "commit.error", false);

				
				duplicate = staffService.rPassword(staffForm);
				result.addObject("duplicate", duplicate);
			}
		}

		return result;
	}
	

	@RequestMapping(value = "/registerDeliveryMan", method = RequestMethod.POST, params = "save")
	public ModelAndView registerDeliveryMan(@Valid StaffForm staffForm, BindingResult binding) {
		ModelAndView result;
		String repeatedPass;
		boolean duplicate;
		DeliveryMan deliveryMan;
		
		repeatedPass = staffForm.getRepeatedPass();

		if (binding.hasErrors()) {
			result = createEditModelAndView(staffForm, true);
		} else { 
			try {
				
				deliveryMan = (DeliveryMan) staffService.reconstruct(staffForm, Authority.DELIVERY_MAN);
				staffService.save(deliveryMan, repeatedPass);
				
				result = new ModelAndView("redirect:/welcome/index.do");
				
			} catch (Throwable oops) {

				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(staffForm, "duplicatedUser", true);
				else
					result = createEditModelAndView(staffForm, "commit.error", true);

				
				duplicate = staffService.rPassword(staffForm);
				result.addObject("duplicate", duplicate);
			}
		}

		return result;
	}

	@RequestMapping(value = "/registerCook", method = RequestMethod.POST, params = "save")
	public ModelAndView registerCook(@Valid StaffForm staffForm, BindingResult binding) {
		ModelAndView result;
		String repeatedPass;
		boolean duplicate;
		Cook cook;
		
		repeatedPass = staffForm.getRepeatedPass();

		if (binding.hasErrors()) {
			result = createEditModelAndView(staffForm, false);
		} else { 
			try {
				
				cook = (Cook) staffService.reconstruct(staffForm, Authority.COOK);
				staffService.save(cook, repeatedPass);
				
				result = new ModelAndView("redirect:/welcome/index.do");
				
			} catch (Throwable oops) {

				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(staffForm, "duplicatedUser", false);
				else
					result = createEditModelAndView(staffForm, "commit.error", false);
				
				duplicate = staffService.rPassword(staffForm);
				result.addObject("duplicate", duplicate);
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(StaffForm staffForm, boolean isDeliveryMan) {
		ModelAndView result;

		result = createEditModelAndView(staffForm, null, isDeliveryMan);

		return result;
	}

	protected ModelAndView createEditModelAndView(StaffForm staffForm, String message, boolean isDeliveryMan) {
		ModelAndView result;

		result = new ModelAndView("register/register");
		result.addObject("staffForm", staffForm);
		result.addObject("userForm", "staffForm");
		result.addObject("message", message);
		result.addObject("isDeliveryMan", isDeliveryMan);

		return result;
	}

}
