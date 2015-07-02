package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MotorbikeService;
import controllers.AbstractController;
import domain.Motorbike;

@Controller
@RequestMapping("/motorbike/administrator")
public class MotorbikeAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private MotorbikeService motorbikeService;
	
	// Constructors -----------------------------------------------------------
	
	public MotorbikeAdministratorController() {
		super();
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
	
	// Edition ----------------------------------------------------------------
	
}
