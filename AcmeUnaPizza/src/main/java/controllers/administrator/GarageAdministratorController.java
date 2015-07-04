package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.GarageService;
import services.MotorbikeService;
import controllers.AbstractController;
import domain.Garage;

@Controller
@RequestMapping("/garage/administrator")
public class GarageAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private GarageService garageService;
	@Autowired
	private MotorbikeService motorbikeService;
	
	// Constructors -----------------------------------------------------------
	
	public GarageAdministratorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Garage> garages;
		Collection<Integer> freeParkings;
		String uri;
		
		garages = garageService.findAll();
		freeParkings = new ArrayList<Integer>();
		
		for(Garage garage : garages)
			freeParkings.add(garage.getSize() - garage.getMotorbikes().size());
		
		
		
		uri = "garage/administrator/list.do";
		result = new ModelAndView("garage/list");
		result.addObject("requestURI", uri);
		result.addObject("garages", garages);
		result.addObject("freeParkings", freeParkings);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int garageId) {
		ModelAndView result;
		Garage garage;
		Integer totalMotorbikesByGarage;
		
		garage = garageService.findOne(garageId);
		
		totalMotorbikesByGarage = motorbikeService.findTotalMotorbikesByGarage(garage.getId());
		
		result = new ModelAndView("garage/edit");
		result.addObject("garage", garage);
		result.addObject("details", true);
		result.addObject("totalMotorbikesByGarage", totalMotorbikesByGarage);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int garageId) {
		ModelAndView result;
		Garage garage;
		Integer totalMotorbikesByGarage;
		
		garage = garageService.findOne(garageId);
		totalMotorbikesByGarage = motorbikeService.findTotalMotorbikesByGarage(garage.getId());
		
		result = createEditModelAndView(garage);
		result.addObject("requestURI", "garage/administrator/edit.do");
		result.addObject("edit", true);
		result.addObject("totalMotorbikesByGarage", totalMotorbikesByGarage);

		
		return result;
	}
	
	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Garage garage;
		
		garage = garageService.create();
		
		result = createEditModelAndView(garage);
		result.addObject("edit", true);

		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Garage garage, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(garage);
			result.addObject("edit", true);
		} else {
			try {
				garageService.save(garage);
				result = new ModelAndView("redirect:/garage/administrator/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(garage, "garage.commit.errorSize");
				result.addObject("edit", true);
			}
		}
		
		return result;
	}
	
	
	public ModelAndView createEditModelAndView(Garage garage){
		ModelAndView result;
		
		result = createEditModelAndView(garage, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(Garage garage, String message){
		ModelAndView res;
		Integer totalMotorbikesByGarage;
		
		totalMotorbikesByGarage = motorbikeService.findTotalMotorbikesByGarage(garage.getId());
		
		res = new ModelAndView("garage/edit");
		res.addObject("garage", garage);
		res.addObject("message", message);
		res.addObject("requestURI", "garage/administrator/edit.do");	
		res.addObject("requestChangeGarageURI", "garage/administrator/changeGarage.do");	
		res.addObject("edit", true);
		res.addObject("totalMotorbikesByGarage", totalMotorbikesByGarage);
	
		return res;
	}
}
