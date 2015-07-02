package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.GarageService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Garage;

@Controller
@RequestMapping("/garage/administrator")
public class GarageAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private GarageService garageService;
	
	// Constructors -----------------------------------------------------------
	
	public GarageAdministratorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Garage> garages;
		String uri;
		
		garages = garageService.findAll();
		
		uri = "garage/administrator/list.do";
		result = new ModelAndView("garage/list");
		result.addObject("requestURI", uri);
		result.addObject("garages", garages);
		
		return result;
	}
	
	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Garage garage;
		
		garage = garageService.create();
		
		result = createModelAndView(garage);

		return result;
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Garage garage, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createModelAndView(garage);
		} else {
			try {
				garageService.save(garage);
				result = new ModelAndView("redirect:../actor/list.do");
			} catch (Throwable oops) {
				result = createModelAndView(garage, "garage.commit.error");
			}
		}
		
		return result;
	}
	
	
	public ModelAndView createModelAndView(Garage entity){
			return createModelAndView(entity, null);
	}

	public ModelAndView createModelAndView(Garage garage, String message){
		ModelAndView res;
		
		res = new ModelAndView("garage/create");
		res.addObject("garage", garage);
		res.addObject("message", message);
		res.addObject("requestURI", "garage/administrator/create.do");			
	
		return res;
	}
}
