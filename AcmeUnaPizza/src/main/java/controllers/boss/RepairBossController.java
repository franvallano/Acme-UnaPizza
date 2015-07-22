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

import services.RepairService;
import services.StuffService;
import services.WorkShopService;
import controllers.AbstractController;
import domain.Repair;
import domain.Stuff;
import domain.WorkShop;

@Controller
@RequestMapping("/repair/boss")
public class RepairBossController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private RepairService repairService;
	
	@Autowired
	private WorkShopService workShopService;

	@Autowired
	private StuffService stuffService;
	
	// Constructors -----------------------------------------------------------
	
	public RepairBossController() {
		super();
	}
	
	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int workshopId) {
		ModelAndView result;
		Repair repair;
		WorkShop repairWorkshop;
		
		repair = repairService.create();
		
		repairWorkshop = workShopService.findOne(workshopId);
		repair.setWorkShop(repairWorkshop);

		result = createEditModelAndView(repair);

		return result;
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Repair> repairs;
		String uri;
		
		repairs = repairService.findAll();
		
		uri = "repair/boss/list.do";
		result = new ModelAndView("repair/list");
		result.addObject("requestURI", uri);
		result.addObject("repair", repairs);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int repairId) {
		ModelAndView result;
		Repair repair;
		
		repair = repairService.findOne(repairId);
		
		result = new ModelAndView("repair/edit");
		result.addObject("repair", repair);
		result.addObject("details", true);
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int repairId) {
		ModelAndView result;
		Repair repair;
		
		repair = repairService.findOne(repairId);
		
		result = createEditModelAndView(repair);
		result.addObject("edit", true);
		result.addObject("requestURI", "repair/boss/edit.do");
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Repair repair, BindingResult binding) {
		ModelAndView result;
		Stuff stuff;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(repair);
			result.addObject("edit", true);
		} else {
			try {
				//Set stuff status to OK
				stuff = repair.getStuff();
				stuff.setStatus("OK");
				stuffService.save(stuff);
				
				repairService.save(repair);
				result = new ModelAndView("redirect:/repair/boss/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(repair, "commit.error");
				result.addObject("edit", true);
			}
		}

		return result;
	}
	
	public ModelAndView createEditModelAndView(Repair repair){
		ModelAndView result;
		
		result = createEditModelAndView(repair, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(Repair repair, String message){
		ModelAndView res;
		Collection<Stuff> reparableStuff;
		WorkShop repairWorkshop;
		
		repairWorkshop = repair.getWorkShop();
		reparableStuff = stuffService.findMalfunctioningStuff(repairWorkshop);
		
		res = new ModelAndView("repair/edit");
		res.addObject("repair", repair);
		res.addObject("message", message);
		res.addObject("reparableStuff", reparableStuff);
		res.addObject("requestURI", "repair/boss/edit.do");	
		res.addObject("edit", true);
	
		return res;
	}

	
	// Edition ----------------------------------------------------------------
	
}