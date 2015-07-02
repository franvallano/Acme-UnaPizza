package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.AdministratorService;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/register/administrator")
public class RegisterAdministratorController extends AbstractController{
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructors -----------------------------------------------------------
	
	public RegisterAdministratorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	// Creation ---------------------------------------------------------------
	
	// Edition ----------------------------------------------------------------	
	
	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		AdministratorForm administratorForm;
		
		administratorForm = new AdministratorForm();

		result = createEditModelAndView(administratorForm);
		result.addObject("action", "register/administrator/register.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid AdministratorForm administratorForm,
			BindingResult binding) {
		ModelAndView result;
		String repeatedPass;
		boolean duplicate;
		Administrator administrator;
		
		repeatedPass = administratorForm.getRepeatedPass();

		if (binding.hasErrors()) {
			result = createEditModelAndView(administratorForm);
		} else { 
			try {
				
				administrator = administratorService.reconstruct(administratorForm);
				administratorService.save(administrator, repeatedPass);
				
				result = new ModelAndView("redirect:/welcome/index.do");
				
			} catch (Throwable oops) {
				
				if(oops instanceof DataIntegrityViolationException)
					result = createEditModelAndView(administratorForm, "commit.duplicatedUser");
				else
					result = createEditModelAndView(administratorForm, "commit.error");
				
				duplicate = administratorService.rPassword(administratorForm);
				result.addObject("duplicate", duplicate);
			}
		}

		return result;
	}

	
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm) {
		ModelAndView result;

		result = createEditModelAndView(administratorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm, String message) {
		ModelAndView result;

		result = new ModelAndView("register/register");
		result.addObject("administratorForm", administratorForm);
		result.addObject("userForm", "administratorForm");
		result.addObject("message", message);
		result.addObject("isAdministrator", true);

		return result;
	}

}
