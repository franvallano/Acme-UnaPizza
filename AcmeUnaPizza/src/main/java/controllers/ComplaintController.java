package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/actor")
public class ComplaintController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ComplaintService complaintService;
	
	// Constructors -----------------------------------------------------------
	
	public ComplaintController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Complaint> complaints;
		String uri;
		
		complaints = complaintService.findByPrincipal();
		
		uri = "complaint/actor/list.do";
		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", uri);
		result.addObject("complaints", complaints);
		
		return result;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int complaintId) {
		ModelAndView result;
		Complaint complaint;
		String uri;
		
		complaint = complaintService.findOneIfOwner(complaintId);
		
		uri = "complaint/actor/view.do";
		result = new ModelAndView("complaint/view");
		result.addObject("requestURI", uri);
		result.addObject("complaint", complaint);
		
		return result;
	}
	
	// Edition ----------------------------------------------------------------
	
}
