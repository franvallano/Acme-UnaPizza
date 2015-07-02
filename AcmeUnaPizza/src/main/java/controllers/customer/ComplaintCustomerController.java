package controllers.customer;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ComplaintService complaintService;
	
	
	// Constructors -----------------------------------------------------------
	
	public ComplaintCustomerController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Complaint complaint;
		
		complaint = complaintService.create();
		
		result = createModelAndView(complaint);
		
		return result;
	}
	
	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Complaint complaint, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createModelAndView(complaint);
		} else {
			try {
				complaintService.save(complaint);
				result = new ModelAndView("redirect:../actor/list.do");
			} catch (Throwable oops) {
				result = createModelAndView(complaint, "complaint.commit.error");
			}
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createModelAndView(Complaint complaint) {
		ModelAndView result;
		
		result = createModelAndView(complaint, null);
		
		return result;
	}
	
	protected ModelAndView createModelAndView(Complaint complaint, String msg) {
		ModelAndView result;
		
		result = new ModelAndView("complaint/create");
		result.addObject("complaint", complaint);
		result.addObject("message", msg);
		
		return result;
	}
	
}
