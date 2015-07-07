package controllers.administrator;

import java.util.ArrayList;
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

import services.OfferService;
import controllers.AbstractController;
import domain.Garage;
import domain.Motorbike;
import domain.Offer;
import domain.Product;


@Controller
@RequestMapping("/offer/administrator")
public class OfferAdministratorController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private OfferService offerService;
	
	//Constructor------------------------------------------------------
	public OfferAdministratorController(){
		super();
	}
	
	// Creation ----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Offer offer;
		Collection<String> ranges;
		Collection<String> days;

		ranges = new ArrayList<String>();
		days = new ArrayList<String>();
		
		offer = offerService.create();
		ranges.add("STANDARD");
		ranges.add("SILVER");
		ranges.add("GOLD");
		ranges.add("VIP");
		
		days.add("L");
		days.add("M");
		days.add("X");
		days.add("J");
		days.add("V");
		days.add("S");
		days.add("D");

		result = createEditModelAndView(offer);
		result.addObject("register", true);
		result.addObject("offer", offer);
		result.addObject("ranges", ranges);
		result.addObject("days", days);

		return result;
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listOffers(){
		ModelAndView result;
		
		Collection<Offer> offers;
		
		offers = offerService.findAll();
		
		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		
		return result;
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/listCurrentOffers", method = RequestMethod.GET)
	public ModelAndView listPizzas() {
		ModelAndView result;

		Collection<Offer> offers;
		
		offers = offerService.findCurrentOffers();
		
		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int offerId) {
		ModelAndView result;
		Offer offer;
		
		offer = offerService.findOne(offerId);
		
		result = new ModelAndView("offer/edit");
		result.addObject("offer", offer);
		result.addObject("details", true);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Offer offer, BindingResult binding) {
		ModelAndView result;
		Collection<String> ranges;
		Collection<String> days;
		
		ranges = new ArrayList<String>();
		days = new ArrayList<String>();
		
		Assert.notNull(offer);
		
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(offer);
			
			ranges.add("STANDARD");
			ranges.add("SILVER");
			ranges.add("GOLD");
			ranges.add("VIP");
			
			days.add("L");
			days.add("M");
			days.add("X");
			days.add("J");
			days.add("V");
			days.add("S");
			days.add("D");
			
			result.addObject("edit", true);
			result.addObject("register", true);
			result.addObject("ranges", ranges);
			result.addObject("days", days);
		} else {
			try {
				offerService.save(offer);
				result = new ModelAndView("redirect:/offer/administrator/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(offer, "offer.commit.errorDate");
				ranges.add("STANDARD");
				ranges.add("SILVER");
				ranges.add("GOLD");
				ranges.add("VIP");
				
				days.add("L");
				days.add("M");
				days.add("X");
				days.add("J");
				days.add("V");
				days.add("S");
				days.add("D");
				
				result.addObject("edit", true);
				result.addObject("register", true);
				result.addObject("ranges", ranges);
				result.addObject("days", days);
			}
		}

		return result;
	}
	
	public ModelAndView createEditModelAndView(Offer offer){
		ModelAndView result;
		
		result = createEditModelAndView(offer, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(Offer offer, String message){
		ModelAndView res;
		
		res = new ModelAndView("offer/edit");
		res.addObject("offer", offer);
		res.addObject("message", message);
		res.addObject("requestURI", "offer/administrator/edit.do");	
		res.addObject("edit", true);
	
		return res;
	}
}
