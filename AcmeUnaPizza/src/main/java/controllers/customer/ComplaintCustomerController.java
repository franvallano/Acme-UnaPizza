package controllers.customer;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		result = createEditModelAndView(complaint);
		return result;
	}
	
	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int complaintId) {
		ModelAndView result;
		Complaint complaint;
		
		complaint = complaintService.findOne(complaintId);
		
		result = createEditModelAndView(complaint);
		result.addObject("edit", true);
		result.addObject("requestURI", "complaint/customer/edit.do");
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Complaint complaint, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(complaint);
			result.addObject("edit", true);
		} else {
			try {
				complaintService.save(complaint);
				result = new ModelAndView("redirect:/complaint/actor/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(complaint, "commit.error");
				result.addObject("edit", true);
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Complaint complaint) {
		ModelAndView result;
		
		result = createEditModelAndView(complaint, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Complaint complaint, String message) {
		ModelAndView result;
		
		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", message);
		result.addObject("requestURI", "complaint/customer/edit.do");	
		result.addObject("edit", true);
		
		return result;
	}
	
}
