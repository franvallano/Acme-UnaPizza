package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.DiscussionMessageService;
import domain.Complaint;
import domain.DiscussionMessage;

@Controller
@RequestMapping("/discussionMessage/actor")
public class DiscussionMessageController extends AbstractController {
	// Services ---------------------------------------------------------------
	
	@Autowired
	private DiscussionMessageService discussionMessageService;
	
	@Autowired
	private ComplaintService complaintService;
	
	// Constructors -----------------------------------------------------------
	
	public DiscussionMessageController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int complaintId) {
		ModelAndView result;
		Complaint complaint;
		DiscussionMessage complaintMessage;
		
		complaint = complaintService.findOneIfOwner(complaintId);
		
		complaintMessage = discussionMessageService.create(complaint);
		
		result = createModelAndView(complaintMessage);
		
		return result;
	}
	
	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid DiscussionMessage discussionMessage, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createModelAndView(discussionMessage);
		} else {
			try {
				discussionMessageService.save(discussionMessage);
				result = new ModelAndView("redirect:/complaint/actor/list.do");
			} catch (Throwable oops) {
				result = createModelAndView(discussionMessage, "discussionMessage.commit.error");
			}
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createModelAndView(DiscussionMessage discussionMessage) {
		ModelAndView result;
		
		result = createModelAndView(discussionMessage, null);
		
		return result;
	}
	
	protected ModelAndView createModelAndView(DiscussionMessage discussionMessage, String message) {
		ModelAndView result;
		
		result = new ModelAndView("complaintMessage/create");
		result.addObject("discussionMessage", discussionMessage);
		result.addObject("message", message);
		
		return result;
	}
}
