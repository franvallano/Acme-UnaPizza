package controllers.staff;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.WorkShopService;
import controllers.AbstractController;
import domain.WorkShop;

@Controller
@RequestMapping("/workshop/staff")
public class WorkShopStaffController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private WorkShopService workshopService;
	
	// Constructors -----------------------------------------------------------
	
	public WorkShopStaffController() {
		super();
	}
	
	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		WorkShop workshop;
		
		workshop = workshopService.create();

		result = createEditModelAndView(workshop);
		result.addObject("register", true);

		return result;
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<WorkShop> workshops;
		String uri;
		
		workshops = workshopService.findAll();
		
		uri = "workshop/staff/list.do";
		result = new ModelAndView("workshop/list");
		result.addObject("requestURI", uri);
		result.addObject("workshops", workshops);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int workshopId) {
		ModelAndView result;
		WorkShop workshop;
		
		workshop = workshopService.findOne(workshopId);
		
		result = new ModelAndView("workshop/edit");
		result.addObject("workshop", workshop);
		result.addObject("details", true);
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int workshopId) {
		ModelAndView result;
		WorkShop workshop;
		
		workshop = workshopService.findOne(workshopId);
		
		result = createEditModelAndView(workshop);
		result.addObject("requestURI", "workshop/staff/edit.do");
		result.addObject("edit", true);

		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid WorkShop workshop, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(workshop);
			result.addObject("edit", true);
			result.addObject("register", true);
		} else {
			try {
				workshopService.save(workshop);
				result = new ModelAndView("redirect:/workshop/staff/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(workshop, "commit.error");
				result.addObject("edit", true);
				result.addObject("register", true);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid WorkShop workshop, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(workshop);
			result.addObject("edit", true);
		} else {
			try {
				workshopService.save(workshop);
				result = new ModelAndView("redirect:/workshop/staff/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(workshop, "commit.error");
				result.addObject("edit", true);
			}
		}

		return result;
	}
	
	public ModelAndView createEditModelAndView(WorkShop workshop){
		ModelAndView result;
		
		result = createEditModelAndView(workshop, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(WorkShop workshop, String message){
		ModelAndView res;
		
		res = new ModelAndView("workshop/edit");
		res.addObject("workshop", workshop);
		res.addObject("message", message);
		res.addObject("requestURI", "workshop/staff/edit.do");	
		res.addObject("edit", true);
	
		return res;
	}
	
	/*
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid WorkShop workshop, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(workshop);
			result.addObject("edit", true);
		}else{
			try{
				workshopService.delete(workshop);
				result = new ModelAndView("redirect:/workshop/staff/list.do");
			}catch (Throwable oops){
				result = createEditModelAndView(workshop, "commit.error");
				result.addObject("edit", true);
			}
		}
		return result;
	}*/
	
	// Edition ----------------------------------------------------------------
	
}
