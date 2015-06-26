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
import forms.RegistrationAdministratorForm;

@Controller
@RequestMapping("/administrator/administrator")
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

	@RequestMapping(value = "/registerAdministrator", method = RequestMethod.GET)
	public ModelAndView registerAdministrator() {
		ModelAndView result;
		RegistrationAdministratorForm registrationAdministratorForm;
		
		registrationAdministratorForm = new RegistrationAdministratorForm();

		result = createEditModelAndView(registrationAdministratorForm);
		result.addObject("action", "administrator/administrator/registerAdministrator.do");

		return result;
	}

	@RequestMapping(value = "/registerAdministrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid RegistrationAdministratorForm registrationAdministratorForm,
			BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		if (binding.hasErrors()) {
			result = createEditModelAndView(registrationAdministratorForm);
			result.addObject("action", "administrator/administrator/registerAdministrator.do");
		} else { 
			try {
				
				administrator = administratorService.create();
				administrator = (Administrator) administratorService.convertToAdministrator(administrator, registrationAdministratorForm);
				administratorService.save(administrator);
				
				result = new ModelAndView("redirect:/");
				
			} catch(IllegalArgumentException exp){
				
				result = createEditModelAndView(registrationAdministratorForm,"administrator.passDuplicate");
				result.addObject("action", "administrator/administrator/registerAdministrator.do");
			}
			catch (Throwable oops) {
				if(oops instanceof DataIntegrityViolationException){
					result = createEditModelAndView(registrationAdministratorForm, "administrator.duplicatedUser");
					result.addObject("action", "administrator/administrator/registerAdministrator.do");
				}else{
					result = createEditModelAndView(registrationAdministratorForm, "administrator.commit.error");
					result.addObject("action", "administrator/administrator/registerAdministrator.do");
				}
			}
		}

		return result;
	}

	
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(
			RegistrationAdministratorForm registrationAdministratorForm) {
		ModelAndView result;

		result = createEditModelAndView(registrationAdministratorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			RegistrationAdministratorForm registrationAdministratorForm, String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/register");
		result.addObject("RegistrationAdministratorForm", registrationAdministratorForm);
		result.addObject("message", message);

		return result;
	}

}
