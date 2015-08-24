package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import controllers.AbstractController;
import domain.Offer;
import forms.OfferForm;


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
		OfferForm offerForm;
		Collection<String> ranges;

		offerForm = new OfferForm();
		
		ranges = offerService.getAllRanges();

		result = createEditModelAndView(offerForm);
		result.addObject("register", true);
		result.addObject("ranges", ranges);

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
		result.addObject("requestURI", "offer/administrator/list.do");
		
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
		result.addObject("requestURI", "offer/administrator/list.do");
		
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
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int offerId) {
		ModelAndView result;
		Offer offer;
		OfferForm offerForm;
		
		offer = offerService.findOne(offerId);
		
		offerForm = offerService.desreconstruct(offer);
		
		result = createEditModelAndView(offerForm);
		
		result.addObject("edit", true);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid OfferForm offerForm, BindingResult binding){
		ModelAndView result;
		Offer offer;

		if(binding.hasErrors()){
			result = createEditModelAndView(offerForm);
			result.addObject("edit", true);
			result.addObject("register", true);
		}else{
			try{
				offer = offerService.reconstruct(offerForm);
				offerService.save(offer);
				result = new ModelAndView("redirect:/offer/administrator/list.do");
			}catch (Throwable oops){
				result = createEditModelAndView(offerForm, "offer.commit.errorDateDays");
				result.addObject("edit", true);
				result.addObject("register", true);
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid OfferForm offerForm, BindingResult binding){
		ModelAndView result;
		Offer offer;

		if(binding.hasErrors()){
			result = createEditModelAndView(offerForm);
			result.addObject("edit", true);
		}else{
			try{
				offer = offerService.reconstruct(offerForm);
				offerService.save(offer);
				result = new ModelAndView("redirect:/offer/administrator/list.do");
			}catch (Throwable oops){
				result = createEditModelAndView(offerForm, "offer.commit.errorDateDays");
				result.addObject("edit", true);
			}
		}
		
		return result;
	}
	
	
	public ModelAndView createEditModelAndView(OfferForm offerForm){
		ModelAndView result;
		
		result = createEditModelAndView(offerForm, null);
		result.addObject("checkMonday", offerForm.isMonday());
		result.addObject("checkTuesday", offerForm.isTuesday());
		result.addObject("checkWednesday", offerForm.isWednesday());
		result.addObject("checkThursday", offerForm.isThursday());
		result.addObject("checkFriday", offerForm.isFriday());
		result.addObject("checkSaturday", offerForm.isSaturday());
		result.addObject("checkSunday", offerForm.isSunday());
		
		return result;
	}

	public ModelAndView createEditModelAndView(OfferForm offerForm, String message){
		ModelAndView res;
		Collection<String> ranges;
		
		ranges = offerService.getAllRanges();
		
		res = new ModelAndView("offer/edit");
		res.addObject("offerForm", offerForm);
		res.addObject("message", message);
		res.addObject("requestURI", "offer/administrator/edit.do");	
		res.addObject("edit", true);
		res.addObject("ranges", ranges);
	
		return res;
	}
}
