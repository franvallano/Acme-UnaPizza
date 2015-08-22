package controllers.boss;

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
@RequestMapping("/workshop/boss")
public class WorkShopBossController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private WorkShopService workshopService;
	
	// Constructors -----------------------------------------------------------
	
	public WorkShopBossController() {
		super();
	}
	
	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		WorkShop workShop;
		
		workShop = workshopService.create();

		result = createEditModelAndView(workShop);

		return result;
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<WorkShop> workshops;
		String uri;
		
		workshops = workshopService.findAll();
		
		uri = "workshop/boss/list.do";
		result = new ModelAndView("workshop/list");
		result.addObject("requestURI", uri);
		result.addObject("workshops", workshops);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int workshopId) {
		ModelAndView result;
		WorkShop workShop;
		
		workShop = workshopService.findOne(workshopId);
		
		result = new ModelAndView("workshop/edit");
		result.addObject("workshop", workShop);
		result.addObject("details", true);
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int workshopId) {
		ModelAndView result;
		WorkShop workShop;
		
		workShop = workshopService.findOne(workshopId);
		
		result = createEditModelAndView(workShop);
		result.addObject("edit", true);
		result.addObject("requestURI", "workshop/boss/edit.do");
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid WorkShop workShop, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(workShop);
			result.addObject("edit", true);
		} else {
			try {
				workshopService.save(workShop);
				result = new ModelAndView("redirect:/workshop/boss/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(workShop, "commit.error");
				result.addObject("edit", true);
			}
		}

		return result;
	}
	
	public ModelAndView createEditModelAndView(WorkShop workShop){
		ModelAndView result;
		
		result = createEditModelAndView(workShop, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(WorkShop workShop, String message){
		ModelAndView res;
		
		res = new ModelAndView("workshop/edit");
		res.addObject("workShop", workShop);
		res.addObject("message", message);
		res.addObject("requestURI", "workshop/boss/edit.do");	
		res.addObject("edit", true);
	
		return res;
	}

	
	// Edition ----------------------------------------------------------------
	
}
