package controllers.administrator;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/administrator")
public class ComplaintAdministratorController extends AbstractController{
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ComplaintService complaintService;
	
	// Constructors -----------------------------------------------------------
	
	public ComplaintAdministratorController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/listAvailables", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		ModelAndView result;
		Collection<Complaint> complaints;
		
		complaints = complaintService.findAvailables();
		
		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/administrator/listAvailables.do");
		result.addObject("complaints", complaints);
		
		return result;
	}
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Complaint> complaints;
		
		complaints = complaintService.findAll();
		
		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/administrator/listAll.do");
		result.addObject("complaints", complaints);
		
		return result;
	}
	// Edition ----------------------------------------------------------------
	
	

}