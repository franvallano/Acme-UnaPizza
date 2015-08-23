package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DeliveryManService;
import services.GarageService;
import services.MotorbikeService;
import controllers.AbstractController;
import domain.DeliveryMan;
import domain.Garage;
import domain.Motorbike;

@Controller
@RequestMapping("/motorbike/administrator")
public class MotorbikeAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private MotorbikeService motorbikeService;
	@Autowired
	private DeliveryManService deliveryManService;
	@Autowired
	private GarageService garageService;
	
	// Constructors -----------------------------------------------------------
	
	public MotorbikeAdministratorController() {
		super();
	}
	
	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Motorbike motorbike;
		Collection<Garage> availableGarages;
		
		motorbike = motorbikeService.create();
		availableGarages = garageService.findFreeGarages();

		result = createEditModelAndView(motorbike);
		result.addObject("register", true);
		result.addObject("availableGarages", availableGarages);

		return result;
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Motorbike> motorbikes;
		String uri;
		
		motorbikes = motorbikeService.findAll();
		
		uri = "motorbike/administrator/list.do";
		result = new ModelAndView("motorbike/list");
		result.addObject("requestURI", uri);
		result.addObject("motorbikes", motorbikes);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int motorbikeId) {
		ModelAndView result;
		Motorbike motorbike;
		DeliveryMan deliveryMan;
		
		motorbike = motorbikeService.findOne(motorbikeId);
		
		deliveryMan = deliveryManService.findDeliveryManByMotorbike(motorbikeId);
		
		result = new ModelAndView("motorbike/edit");
		result.addObject("motorbike", motorbike);
		result.addObject("details", true);
		
		if(deliveryMan != null)
			result.addObject("deliveryMan", deliveryMan.getName());
		
		return result;
	}
	
	@RequestMapping(value = "/changeGarage", method = RequestMethod.GET)
	public ModelAndView changeGarage(@RequestParam int motorbikeId) {
		ModelAndView result;
		Motorbike motorbike;
		Collection<Garage> availableGarages;
		
		motorbike = motorbikeService.findOne(motorbikeId);
		availableGarages = garageService.findFreeGarages();
		
		result = new ModelAndView("motorbike/edit");
		result.addObject("motorbike", motorbike);
		result.addObject("changeGarage", true);
		result.addObject("requestURI", "motorbike/administrator/edit.do");
		result.addObject("availableGarages", availableGarages);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int motorbikeId) {
		ModelAndView result;
		Motorbike motorbike;
		boolean canDelete;
		
		motorbike = motorbikeService.findOne(motorbikeId);
		canDelete = motorbikeService.canDeleteMotorbike(motorbikeId);
		
		result = createEditModelAndView(motorbike);
		result.addObject("requestURI", "motorbike/administrator/edit.do");
		result.addObject("edit", true);
		result.addObject("canDelete", canDelete);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Motorbike motorbike, BindingResult binding) {
		ModelAndView result;
		Collection<Garage> availableGarages;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(motorbike);
			result.addObject("edit", true);
			result.addObject("register", true);
			availableGarages = garageService.findFreeGarages();
			result.addObject("availableGarages", availableGarages);
		} else {
			try {
				motorbikeService.save(motorbike);
				result = new ModelAndView("redirect:/motorbike/administrator/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(motorbike, "motorbike.commit.errorLicensePlate");
				result.addObject("edit", true);
				result.addObject("register", true);
				availableGarages = garageService.findFreeGarages();
				result.addObject("availableGarages", availableGarages);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Motorbike motorbike, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(motorbike);
			result.addObject("edit", true);
		} else {
			try {
				motorbikeService.save(motorbike);
				result = new ModelAndView("redirect:/motorbike/administrator/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(motorbike, "motorbike.commit.error");
				result.addObject("edit", true);
			}
		}

		return result;
	}
	
	public ModelAndView createEditModelAndView(Motorbike motorbike){
		ModelAndView result;
		
		result = createEditModelAndView(motorbike, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(Motorbike motorbike, String message){
		ModelAndView res;
		
		res = new ModelAndView("motorbike/edit");
		res.addObject("motorbike", motorbike);
		res.addObject("message", message);
		res.addObject("requestURI", "motorbike/administrator/edit.do");	
		res.addObject("edit", true);
	
		return res;
	}
	
	@RequestMapping(value = "/changeGarage", method = RequestMethod.POST, params = "save")
	public ModelAndView changeGarage(@Valid Motorbike motorbike, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(motorbike);
			result.addObject("edit", false);
			result.addObject("changeGarage", true);
		} else {
			try {
				motorbikeService.save(motorbike);
				result = new ModelAndView("redirect:/motorbike/administrator/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(motorbike, "commit.error");
				result.addObject("edit", false);
				result.addObject("changeGarage", true);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Motorbike motorbike, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(motorbike);
			result.addObject("edit", true);
		}else{
			try{
				motorbikeService.delete(motorbike);
				result = new ModelAndView("redirect:/motorbike/administrator/list.do");
			}catch (Throwable oops){
				result = createEditModelAndView(motorbike, "commit.error");
				result.addObject("edit", true);
			}
		}
		return result;
	}
	
	// Edition ----------------------------------------------------------------
	
}
