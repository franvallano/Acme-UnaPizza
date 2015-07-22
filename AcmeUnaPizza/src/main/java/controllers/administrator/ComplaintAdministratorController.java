package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	/*
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Complaint> complaints;
		
		complaints = complaintService.findAll();
		
		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/administrator/listAll.do");
		result.addObject("complaints", complaints);
		
		return result;
	}*/
	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assignSave(@RequestParam int complaintId) {
		ModelAndView result;
		Complaint complaint;
		
		complaint = complaintService.findOneIfAvailable(complaintId);
		
		complaintService.assignComplaint(complaint);
		
		result = new ModelAndView("redirect:/complaint/administrator/listAvailables.do");
		
		return result;
	}
	
	@RequestMapping(value = "/modifyStateCancelled", method = RequestMethod.GET)
	public ModelAndView modifyStateCancelled(@RequestParam int complaintId) {
		ModelAndView result;
		Complaint complaint;

		complaint = complaintService.findOneIfAdministratorOwner(complaintId);
		
		complaintService.modifyStateCancelled(complaint);
		
		complaintService.save(complaint);
		
		result = new ModelAndView("redirect:/complaint/actor/list.do");

		return result;
	}
	
	
	@RequestMapping(value = "/addResolution", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int complaintId) {
		ModelAndView result;
		Complaint complaint;
		
		complaint = complaintService.findOne(complaintId);	
		
		Assert.notNull(complaint);
		result = addResolutionModelAndView(complaint);
		
		return result;
	}
	
	@RequestMapping(value = "/addResolution", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Complaint complaint, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = addResolutionModelAndView(complaint);
		} else {
			try {
				complaintService.addResolution(complaint);
				complaintService.save(complaint);
				result = new ModelAndView("redirect:/complaint/administrator/listAvailables.do");
			} catch (Throwable oops) {
				result = addResolutionModelAndView(complaint, "commit.error");
			}
		}
		
		return result;
	}
	
	
	protected ModelAndView addResolutionModelAndView(Complaint complaint) {
		ModelAndView result;

		result = addResolutionModelAndView(complaint, null);

		return result;
	}
	
	protected ModelAndView addResolutionModelAndView(Complaint complaint, String message) {
		ModelAndView result;

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", message);
		result.addObject("addResolution", true);

		return result;
	}

}